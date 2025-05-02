package io.github.lounode.eventwrapper.fabric;

import io.github.lounode.eventwrapper.EventsWrapper;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.LanguageAdapter;
import net.fabricmc.loader.impl.FabricLoaderImpl;
import net.fabricmc.loader.impl.ModContainerImpl;
import net.fabricmc.loader.impl.entrypoint.EntrypointStorage;
import net.fabricmc.loader.impl.metadata.EntrypointMetadata;
import net.fabricmc.loader.impl.util.log.Log;
import net.fabricmc.loader.impl.util.log.LogCategory;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AutoEventSubscriberRegistryFabric {
    public static final LogCategory CATEGORY = LogCategory.create("Event Wrapper");
    public static final boolean ENABLED = System.getProperty("fabric-api.gametest") != null;
    private static final String testPackage = "io.github.lounode.eventwrapper.test";

    public static void register(ModContainerImpl container) {
        List<String> annotatedClasses = new ArrayList<>();
        List<Path> rootPaths = container.getRootPaths();
        String modId = container.getMetadata().getId();

        //Use ASM to avoid loading mixin class before mixin system loaded
        for (Path rootPath : rootPaths) {
            try {
                Files.walk(rootPath)
                        .filter(p -> p.toString().endsWith(".class"))
                        .forEach(classFile -> {
                            String className = convertToClassName(rootPath, classFile);

                            if (!ENABLED && className.startsWith(testPackage)) return;

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

    private static String convertToClassName(Path root, Path classFile) {
        return root.relativize(classFile)
                .toString()
                .replace(".class", "")
                .replace(File.separatorChar, '.')
                .replace('/', '.');
    }
}
