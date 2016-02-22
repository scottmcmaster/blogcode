package com.smcmaster.selftest.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Meta-annotation that marks another annotation as being of a type
 * that generates self-tests.
 * 
 * @author scott@smcmaster.com
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface SelfTestType {
}
