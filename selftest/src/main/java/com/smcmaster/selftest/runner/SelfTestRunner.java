package com.smcmaster.selftest.runner;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.Runner;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.TestClass;

import com.smcmaster.selftest.SelfTest;
import com.smcmaster.selftest.SelfTestCase;
import com.smcmaster.selftest.annotations.SelfTestMethod;

/**
 * JUnit {@link Runner} implementation that wraps {@link SelfTest}
 * for self-testing.
 * 
 * @author scott@smcmaster.com
 */
public class SelfTestRunner extends Runner {
  private final TestClass testClass;
  private Class<?> clazz;
  private List<SelfTest> selfTests;
  private Description topLevelDesc;

  public SelfTestRunner(Class<?> aClass) {
    clazz = aClass;
    testClass = new TestClass(aClass);
    populateSelfTests(aClass);
  }

  /**
   * Grabs all of the self-test methods off of the test class and creates/stashes
   * a {@link SelfTest} instance for each.
   */
  private void populateSelfTests(Class<?> aClass) {
    List<Method> selfTestMethods = getSelfTestMethods(aClass);

    selfTests = new ArrayList<>();
    for (Method method : selfTestMethods) {
      Object testClassInstance;
      try {
        testClassInstance = testClass.getOnlyConstructor().newInstance();
      } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
          | InvocationTargetException e) {
        throw new RuntimeException(e);
      }

      Object objectUnderTest;
      try {
        objectUnderTest = method.invoke(testClassInstance);
      } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
        throw new RuntimeException(e);
      }

      selfTests
          .add(new SelfTest(method.getAnnotation(SelfTestMethod.class).name(), objectUnderTest));
    }
  }

  /**
   * Gets all of the non-ignored self-test methods from the test class.
   */
  private List<Method> getSelfTestMethods(Class<?> aClass) {
    List<Method> selfTestMethods = new ArrayList<>();
    Method[] classMethods = aClass.getDeclaredMethods();
    for (Method classMethod : classMethods) {
      if (classMethod.getAnnotation(SelfTestMethod.class) != null) {
        selfTestMethods.add(classMethod);
      }
      if (classMethod.getAnnotation(Ignore.class) != null) {
        selfTestMethods.remove(classMethod);
      }
    }
    return selfTestMethods;
  }

  /**
   * Builds the tree of test suites/cases.
   */
  @Override
  public Description getDescription() {
    topLevelDesc = Description.createSuiteDescription(testClass.getName(),
        testClass.getJavaClass().getAnnotations());

    for (SelfTest selfTest : selfTests) {
      Description methodLevelDesc = Description.createSuiteDescription(selfTest.getName(),
          testClass.getJavaClass().getAnnotations());
      topLevelDesc.addChild(methodLevelDesc);

      for (SelfTestCase selfTestCase : selfTest.getSelfTestCases()) {
        Description caseLevelDesc = createTestCaseDescription(selfTestCase);
        methodLevelDesc.addChild(caseLevelDesc);
      }
    }

    return topLevelDesc;
  }

  /**
   * Runs the tests and reports results.
   */
  @Override
  public void run(RunNotifier runNotifier) {
    runNotifier.fireTestRunStarted(topLevelDesc);
    for (SelfTest selfTest : selfTests) {
      for (SelfTestCase selfTestCase : selfTest.getSelfTestCases()) {
        Description currentDesc = createTestCaseDescription(selfTestCase);
        runNotifier.fireTestStarted(currentDesc);

        try {
          selfTest.runTest(selfTestCase);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
            | AssertionError e) {
          runNotifier.fireTestFailure(new Failure(currentDesc, e));
        }
        runNotifier.fireTestFinished(currentDesc);
      }
    }
    // TODO: Fill in the Result instance appropriately.
    runNotifier.fireTestRunFinished(new Result());
  }

  /**
   * Helper method to create a test case description instance.
   */
  private Description createTestCaseDescription(SelfTestCase selfTestCase) {
    Description currentDesc =
        Description.createTestDescription(clazz, selfTestCase.getDescription());
    return currentDesc;
  }
}
