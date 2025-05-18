package io.github.lounode.eventwrapper.fabric;

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
import java.util.List;
import java.util.Map;

public class AutoRegisterGameTest {
    public static final LogCategory CATEGORY = LogCategory.create("Event Wrapper");
    public static void register(ModContainerImpl container) {
        List<Path> rootPaths = container.getRootPaths();

        //Use ASM to avoid loading mixin class before mixin system loaded
        String targetPackage = "io.github.lounode.eventwrapper.test";

        for (Path rootPath : rootPaths) {
            try {
                Files.walk(rootPath)
                        .filter(p -> p.toString().endsWith(".class"))
                        .forEach(classFile -> {
                            String className = convertToClassName(rootPath, classFile);

                            if (!className.startsWith(targetPackage)) {
                                return;
                            }

                            try (InputStream is = Files.newInputStream(classFile)) {
                                ClassReader reader = new ClassReader(is);

                                reader.accept(new ClassVisitor(Opcodes.ASM9) {
                                    @Override
                                    public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
                                        if (descriptor.contains("GameTestHolder")) {
                                            try {
                                                Class<?> clazz = Class.forName(className, false, getClass().getClassLoader());
                                                registerGameTests(clazz);
                                            } catch (ClassNotFoundException ignored) {
                                                Log.warn(CATEGORY, "Failed to load GameTest class: " + className);
                                            }
                                        }
                                        return null;
                                    }
                                }, ClassReader.SKIP_CODE | ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES);
                            } catch (IOException ignored) {
                                Log.warn(CATEGORY, "Error reading class file: " + classFile, ignored);
                            }
                        });
            } catch (IOException e) {
                Log.warn(CATEGORY, "Failed to scan rootPath: " + rootPath, e);
            }
        }

    }

    private static String convertToClassName(Path root, Path classFile) {
        return root.relativize(classFile)
                .toString()
                .replace(".class", "")
                .replace(File.separatorChar, '.')
                .replace('/', '.');
    }

    private static void registerGameTests(Class<?> clazz) {
        FabricLoader loader = FabricLoader.getInstance();
        ModContainerImpl modContainer = (ModContainerImpl)loader.getModContainer("eventwrapper").orElseThrow();
        //GameTestRegistry.register(clazz);

        try {
            Field field = FabricLoaderImpl.class.getDeclaredField("entrypointStorage");
            Field adptMapField = FabricLoaderImpl.class.getDeclaredField("adapterMap");
            field.setAccessible(true);
            adptMapField.setAccessible(true);
            EntrypointStorage entrypointStorage = (EntrypointStorage) field.get(loader);
            @SuppressWarnings("unchecked")
            Map<String, LanguageAdapter> adptmap = (Map<String, LanguageAdapter>) adptMapField.get(loader);
            //entrypointStorage.addDeprecated(modContainer, "fabric-gametest", clazz.getName());
            entrypointStorage.add(modContainer, "fabric-gametest", new EntrypointMetadataImpl("default", clazz.getName()) ,adptmap);


        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

    static final class EntrypointMetadataImpl implements EntrypointMetadata {
        private final String adapter;
        private final String value;

        EntrypointMetadataImpl(String adapter, String value) {
            this.adapter = adapter;
            this.value = value;
        }

        @Override
        public String getAdapter() {
            return this.adapter;
        }

        @Override
        public String getValue() {
            return this.value;
        }
    }
}
