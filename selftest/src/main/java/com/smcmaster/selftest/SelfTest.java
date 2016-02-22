package com.smcmaster.selftest;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;

import com.smcmaster.selftest.SelfTestResult.ResultType;
import com.smcmaster.selftest.annotations.Comparison;
import com.smcmaster.selftest.annotations.Comparisons;
import com.smcmaster.selftest.annotations.SelfTestType;

/**
 * Manages self-tests by deciphering the self-test annotations and providing a means
 * to turn them into JUnit test cases and run them.
 * 
 * @author scott@smcmaster.com
 */
public class SelfTest {
  
  /** The object we're testing. */
  private final Object objectUnderTest;
  
  /** The test cases we generate */
  private final List<SelfTestCase> testCases;
  
  /** The display name of this self-test */
  private final String name;
  
  public SelfTest(String name, Object objectUnderTest) {
    this.objectUnderTest = objectUnderTest;
    testCases = buildSelfTestCases();
    this.name = name;
  }
  
  /**
   * Runs an individual self-test test case.
   * NOTE that the given method is presumed to have come from this
   * self-test instance. The somewhat weird pass-it-back-in design
   * was expedient to get the JUnit Runner wrapping up and going.
   */
  public SelfTestResult runTest(SelfTestCase method) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
    if (method.getAnnotation() instanceof Comparison) {
      return runComparisonTest(method.getMethod(), (Comparison) method.getAnnotation());
    }
    throw new UnsupportedOperationException();
  }

  public List<SelfTestCase> getSelfTestCases() {
    return testCases;
  }
  
  public String getName() {
    return name;
  }
  
  /**
   * Builds a set of {@link SelfTestCase}s from the annotations on the
   * object-under-test.
   */
  private List<SelfTestCase> buildSelfTestCases() {
    List<SelfTestCase> result = new ArrayList<>();

    Class<?> clazz = objectUnderTest.getClass();
    for (Method method : clazz.getDeclaredMethods()) {
      try {
        List<Annotation> selfTestAnnotations = getSelfTestAnnotations(method);
        int currentIdx = 1;
        for (Annotation annotation : selfTestAnnotations) {
          if (annotation instanceof Comparisons) {
            for (Comparison comparison : ((Comparisons) annotation).value()) {
              result.add(new SelfTestCase(method, comparison, currentIdx++));
            }
          }
        }
      } catch (Exception ex) {
        throw new RuntimeException(ex);
      }
    }
    return result;
  }
  
  /**
   * Runs a comparison-type test.
   * TODO: When there are multiple self-test-annotation types, move these
   * executions into their own classes.
   */
  @SuppressWarnings("rawtypes")
  private SelfTestResult runComparisonTest(Method method, Comparison comparison)
      throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
    Class<?>[] parameterTypes = method.getParameterTypes();
    String[] parameterValueDatas = comparison.inputs();
    Object[] parameterValues = coerceParameters(parameterValueDatas, parameterTypes);
    Object actual = method.invoke(objectUnderTest, parameterValues);
    if (!(actual instanceof Comparable)) {
      throw new IllegalStateException("Method does not return a Comparable");
    }

    Object expected = coerceValue(comparison.output(), method.getReturnType());
    return checkResult(comparison, (Comparable) actual, (Comparable) expected);
  }

  /**
   * Checks a comparison result, leveraging JUnit and Hamcrest matchers.
   */
  @SuppressWarnings("rawtypes")
  private SelfTestResult checkResult(Comparison comparison, Comparable actual, Comparable expected) {
    switch (comparison.type()) {
      case EQUALS:
        Assert.assertEquals(comparison.description(), expected, actual);
        break;
      case GREATER_THAN:
        Assert.assertThat(comparison.description(), actual, org.hamcrest.Matchers.greaterThan(expected));
        break;
      case GREATER_THAN_OR_EQUALS:
        Assert.assertThat(comparison.description(), actual, org.hamcrest.Matchers.greaterThanOrEqualTo(expected));
        break;
      case LESS_THAN:
        Assert.assertThat(comparison.description(), actual, org.hamcrest.Matchers.lessThan(expected));
        break;
      case LESS_THAN_OR_EQUALS:
        Assert.assertThat(comparison.description(), actual, org.hamcrest.Matchers.lessThanOrEqualTo(expected));
        break;
      case NOT_EQUAL:
        Assert.assertNotEquals(comparison.description(), expected, actual);
        break;
      default:
        break;
    }
    return new SelfTestResult(ResultType.PASS);
  }
  
  /**
   * Takes raw string data and coerces them into parameters with type based on the
   * given (assumed to be parallel) array of types.
   */
  private Object[] coerceParameters(String[] parameterValueDatas, Class<?>[] parameterTypes) {
    if (parameterTypes.length != parameterValueDatas.length) {
      throw new IllegalStateException("Actual parameter size doesn't match expected ("
          + parameterValueDatas.length + " != " + parameterTypes.length + ")");
    }

    Object[] parameterValues = new Object[parameterTypes.length];
    for (int i = 0; i < parameterTypes.length; i++) {
      parameterValues[i] = coerceValue(parameterValueDatas[i], parameterTypes[i]);
    }
    return parameterValues;
  }

  /**
   * Converts a string value to the target type.
   */
  private Object coerceValue(String rawData, Class<?> targetType) {
    switch (targetType.getName()) {
      case "int":
      case "java.lang.Integer":
        return Integer.parseInt(rawData);
      case "long":
      case "java.lang.Long":
        return Long.parseLong(rawData);
      case "double":
      case "java.lang.Double":
        return Double.parseDouble(rawData);
      case "float":
      case "java.lang.Float":
        return Float.parseFloat(rawData);
      case "byte":
      case "java.lang.Byte":
        return Byte.parseByte(rawData);
      case "boolean":
      case "java.lang.Boolean":
        return Boolean.parseBoolean(rawData);
      case "short":
      case "java.lang.Short":
        return Short.parseShort(rawData);
      case "char":
      case "java.lang.Character":
        if (rawData.length() != 1) {
          throw new IllegalStateException("Invalid character data: " + rawData);
        }
        return rawData.charAt(0);
      case "java.lang.String":
        return rawData;
      default:
        throw new IllegalArgumentException("Unsupported type: " + targetType.getName());
    }
  }

  /**
   * Gets all of the annotations that belong to self-test off the given method.
   */
  private List<Annotation> getSelfTestAnnotations(Method method) {
    List<Annotation> selfTestAnnotations = new ArrayList<>();
    for (Annotation annotation : method.getAnnotations()) {
      Class<?> annotationType = annotation.annotationType();
      if (annotationType.getAnnotation(SelfTestType.class) != null) {
        selfTestAnnotations.add(annotation);
      }
    }
    return selfTestAnnotations;
  }
}
