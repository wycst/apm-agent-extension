package com.boco.mis.opentrace.json.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface JsonIgnore {

	/**
	 * 序列化
	 * @return
	 */
	public boolean serialize() default true;
	
	/**
	 * 反序列化
	 * @return
	 */
	public boolean deserialize() default true;
	
}
