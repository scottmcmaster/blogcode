package com.smcmaster.selftest.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Wrapper annotation that allows multiple {@link Comparision}s on a method.
 * 
 * @author scott@smcmaster.com
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@SelfTestType
public @interface Comparisons {
  Comparison[] value();
}
