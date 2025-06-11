package io.github.lounode.xplatform.platform;

import com.mojang.serialization.Codec;

import net.minecraft.util.StringRepresentable;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import io.github.lounode.xplatform.platform.support.CrossPlatform;
import io.github.lounode.xplatform.platform.support.SupportPlatform;
import io.github.lounode.xplatform.platform.support.SupportPlatforms;

/**
 * For Modders to check what platform player is using
 * <blockquote>
 * <dl>
 * <dt>{@link #FORGE}</dt>
 * <dd>Player is using Forge (not neo-forge)</dd>
 * <dt>{@link #FABRIC}</dt>
 * <dd>Player is using Fabric</dd>
 * <dt>{@link #NEOFORGE}</dt>
 * <dd>Player is using NeoForge</dd>
 * <dt>{@link #QUILT}</dt>
 * <dd>Player is using Quilt</dd>
 * <dt>{@link #UNKNOWN}</dt>
 * <dd>Fail to get platform (Or default value)</dd>
 * </dl>
 * </blockquote>
 */
public enum Platform implements StringRepresentable {
	FORGE("forge"),
	FABRIC("fabric"),
	NEOFORGE("neoforge"),
	QUILT("quilt"),
	UNKNOWN("unknown");

	public static final Codec<Platform> CODEC = StringRepresentable.fromEnum(Platform::values);
	private final String key;

	Platform(String key) {
		this.key = key;
	}

	@Override
	public String getSerializedName() {
		return this.key;
	}

	/**
	 * Get platform which player is using.
	 * <br>
	 * <br>
	 * If player installed <code>Sinytra Connector</code> or <code>Kilt</code>, some problems may happen.
	 * <br>
	 * Use {@link #getCurrents} to get platforms player loaded.
	 * 
	 * @return {@link Platform}
	 */
	public static Platform getCurrent() {
		try {
			Class.forName("net.minecraftforge.fml.loading.FMLLoader");
			return FORGE;
		} catch (ClassNotFoundException ignored) {}

		try {
			Class.forName("net.neoforged.fml.loading.FMLLoader");
			return NEOFORGE;
		} catch (ClassNotFoundException ignored) {}

		try {
			Class.forName("net.fabricmc.loader.api.FabricLoader");
			return FABRIC;
		} catch (ClassNotFoundException ignored) {}

		try {
			Class.forName("org.quiltmc.loader.api.QuiltLoader");
			return QUILT;
		} catch (ClassNotFoundException ignored) {}

		return UNKNOWN;
	}

	/**
	 * If player installed <code>Sinytra Connector</code> or <code>Kilt</code>, some problems may happen.
	 * <br>
	 * Use this to get platforms player loaded.
	 * 
	 * @return {@link Platform Platform[]}
	 */
	public static Platform[] getCurrents() {
		List<Platform> platforms = new ArrayList<>();

		try {
			Class.forName("net.minecraftforge.fml.loading.FMLLoader");
			platforms.add(FORGE);
		} catch (ClassNotFoundException ignored) {}

		try {
			Class.forName("net.neoforged.fml.loading.FMLLoader");
			platforms.add(NEOFORGE);
		} catch (ClassNotFoundException ignored) {}

		try {
			Class.forName("net.fabricmc.loader.api.FabricLoader");
			platforms.add(FABRIC);
		} catch (ClassNotFoundException ignored) {}

		try {
			Class.forName("org.quiltmc.loader.api.QuiltLoader");
			platforms.add(QUILT);
		} catch (ClassNotFoundException ignored) {}

		return platforms.toArray(new Platform[0]);
	}

	private static final String CROSS_PLATFORM_DESCRIPTOR = "L" + CrossPlatform.class.getName().replace('.', '/') + ";";
	private static final String SUPPORT_PLATFORM_DESCRIPTOR = "L" + SupportPlatform.class.getName().replace('.', '/') + ";";
	private static final String SUPPORT_PLATFORMS_DESCRIPTOR = "L" + SupportPlatforms.class.getName().replace('.', '/') + ";";

	/**
	 * Require: {@link CrossPlatform} or {@link SupportPlatform} on the class.
	 * 
	 * @param className Class to check
	 * @return false if unsupport or default
	 */
	public static boolean isSupport(String className) {
		try {
			AtomicBoolean support = new AtomicBoolean();
			Set<Platform> supportedPlatforms = new LinkedHashSet<>();
			Platform[] currentPlatforms = getCurrents();

			ClassReader reader = new ClassReader(className);
			AnnotationVisitor av = new AnnotationVisitor(Opcodes.ASM9) {
				@Override
				public void visit(String name, Object value) {
					if (CROSS_PLATFORM_DESCRIPTOR.equals(name)) {
						support.set(true);
					}
				}
			};

			reader.accept(new ClassVisitor(Opcodes.ASM9) {
				@Override
				public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
					if (descriptor.equals(CROSS_PLATFORM_DESCRIPTOR)) {
						support.set(true);
					} else if (descriptor.equals(SUPPORT_PLATFORM_DESCRIPTOR)) {
						return new AnnotationVisitor(Opcodes.ASM9, av) {
							@Override
							public void visitEnum(String name, String desc, String value) {
								if ("value".equals(name)) {
									supportedPlatforms.add(Platform.valueOf(value));
								}
							}
						};
					} else if (descriptor.equals(SUPPORT_PLATFORMS_DESCRIPTOR)) {
						return new AnnotationVisitor(Opcodes.ASM9, av) {
							@Override
							public AnnotationVisitor visitArray(String name) {
								if ("value".equals(name)) {
									return new AnnotationVisitor(Opcodes.ASM9, this) {
										@Override
										public void visitEnum(String name, String desc, String value) {
											supportedPlatforms.add(Platform.valueOf(value));
										}
									};
								}
								return this;
							}
						};
					}
					return av;
				}
			}, ClassReader.SKIP_CODE | ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES);

			if (support.get()) {
				return true;
			}

			for (Platform platform : currentPlatforms) {
				if (supportedPlatforms.contains(platform)) {
					return true;
				}
			}

			return false;
		} catch (IOException e) {
			return false;
		}
	}
}
