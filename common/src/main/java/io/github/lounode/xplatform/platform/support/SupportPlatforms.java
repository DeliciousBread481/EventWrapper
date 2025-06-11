package io.github.lounode.xplatform.platform.support;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import io.github.lounode.xplatform.platform.Platform;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.PACKAGE, ElementType.ANNOTATION_TYPE })
public @interface SupportPlatforms {
	Platform[] value();
}
