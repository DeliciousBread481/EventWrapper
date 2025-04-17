package io.github.lounode.eventwrapper.fabric;

import io.github.lounode.eventwrapper.EventsWrapper;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;
import net.fabricmc.loader.impl.ModContainerImpl;
import net.fabricmc.loader.impl.util.log.Log;
import net.fabricmc.loader.impl.util.log.LogCategory;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PreLaunchHandler implements PreLaunchEntrypoint {
    public static final LogCategory CATEGORY = LogCategory.create("Event Wrapper");
    private static final String[] SKIP_MODS = new String[] {
            "minecraft",
            "mixinextras",
            "java",
            "fabricloader",
    };

    @Override
    public void onPreLaunch() {
        EventsWrapper.WRAPPER_EVENT_BUS.start();

        var mods = FabricLoader.getInstance().getAllMods();

        for(var mod : mods) {
            String modId = mod.getMetadata().getId();
            if (modId.startsWith("fabric-") || Arrays.asList(SKIP_MODS).contains(modId)) {
                continue;
            }

            List<String> annotatedClasses = new ArrayList<>();
            List<Path> rootPaths = ((ModContainerImpl) mod).getRootPaths();
            //Use ASM to avoid loading mixin class before mixin system loaded
            for (Path rootPath : rootPaths) {
                try {
                    Files.walk(rootPath)
                            .filter(p -> p.toString().endsWith(".class"))
                            .forEach(classFile -> {
                                String className = convertToClassName(rootPath, classFile);

                                try (InputStream is = Files.newInputStream(classFile)) {
                                    ClassReader reader = new ClassReader(is);

                                    reader.accept(new ClassVisitor(Opcodes.ASM9) {
                                        @Override
                                        public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {

                                            if (descriptor.contains("EventBusSubscriberWrapper")) {
                                                annotatedClasses.add(className);
                                                try {
                                                    Class<?> clazz = Class.forName(className, false, getClass().getClassLoader());
                                                    EventsWrapper.register(clazz);
                                                } catch (ClassNotFoundException ignored) {}
                                            }
                                            return null;
                                        }
                                    }, ClassReader.SKIP_CODE | ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES);
                                } catch (IOException ignored) {

                                }
                            });
                } catch (IOException e) {
                    Log.warn(CATEGORY, "Failed to scan classes for mod " + modId, e);
                }
            }

            Log.info(CATEGORY, "Registered %d listeners for: %s",
                    annotatedClasses.size(),
                    modId);
        }
    }

    private String convertToClassName(Path root, Path classFile) {
        return root.relativize(classFile)
                .toString()
                .replace(".class", "")
                .replace(File.separatorChar, '.')
                .replace('/', '.');
    }
}
