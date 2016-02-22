package com.smcmaster.selftest.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a comparison (actual vs. expected) test on a method.
 * 
 * @author scott@smcmaster.com
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Repeatable(Comparisons.class)
@SelfTestType
public @interface Comparison {

  public enum Type {
    EQUALS, GREATER_THAN, LESS_THAN, NOT_EQUAL, GREATER_THAN_OR_EQUALS, LESS_THAN_OR_EQUALS
  }
  
  String[] inputs() default "";
  
  String output() default "";
  
  String description() default "";
  
  Type type() default Type.EQUALS;
}
