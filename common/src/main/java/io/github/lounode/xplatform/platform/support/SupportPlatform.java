package io.github.lounode.xplatform.platform.support;

import java.lang.annotation.*;

import io.github.lounode.xplatform.platform.Platform;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.PACKAGE, ElementType.ANNOTATION_TYPE })
public @interface SupportPlatform {
	Platform value();
}
