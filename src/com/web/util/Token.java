package com.web.util;


//@Target(ElementType.METHOD)
//@Retention(RetentionPolicy.RUNTIME)
public @interface Token {

    boolean save() default false;

    boolean remove() default false;
}