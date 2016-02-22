package com.smcmaster.selftest.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a test class method as returning an object to self-test.
 * Usage:
 * <pre>
 * <code>
 * {@literal@}SelfTestMethod
 * public MyObjectToSelfTest toString() {
 *   return new MyObjectToSelfTest();
 * }
 * </code>
 * </pre>
 * 
 * @author scott@smcmaster.com
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface SelfTestMethod {
  String name();
}
