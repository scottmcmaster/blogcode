package com.smcmaster.selftest;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import com.smcmaster.selftest.annotations.Comparison;

public class SelfTestCase {

  private final Method method;
  private final Annotation selfTestAnnotation;
  private final int index;
  
  public SelfTestCase(Method method, Annotation selfTestAnnotation, int index) {
    this.method = method;
    this.selfTestAnnotation = selfTestAnnotation;
    this.index = index;
  }

  public Method getMethod() {
    return method;
  }

  public Annotation getAnnotation() {
    return selfTestAnnotation;
  }

  public String getDescription() {
    return method.getName() + " " + getDescriptionSuffix();
  }

  private String getDescriptionSuffix() {
    String description = "";
    if (selfTestAnnotation instanceof Comparison) {
      description = ((Comparison) selfTestAnnotation).description();
    }
    if (description != null && !description.isEmpty()) {
      return "" + index + ": " + description;
    }
    return "" + index;
  }  
}
