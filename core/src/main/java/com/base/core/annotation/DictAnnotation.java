package com.base.core.annotation;

import com.base.core.jackson.jackson.DictSerializer;
import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.lang.annotation.*;

/**
 * 字典转换注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target( { ElementType.FIELD })
@Documented
@JacksonAnnotationsInside
@JsonSerialize(using = DictSerializer.class)
public @interface DictAnnotation {

	/**
	 * 分组
	 */
	String name();
}
