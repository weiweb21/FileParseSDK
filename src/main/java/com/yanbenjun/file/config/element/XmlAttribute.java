package com.yanbenjun.file.config.element;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Inherited
@Target(value=ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface XmlAttribute
{
    String name();
    
    Class<? extends XElement> subElement() default XElement.class;
    
    boolean isAttr() default true;
}
