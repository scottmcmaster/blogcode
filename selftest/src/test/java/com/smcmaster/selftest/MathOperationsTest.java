package com.smcmaster.selftest;

import org.junit.runner.RunWith;

import com.smcmaster.selftest.annotations.SelfTestMethod;
import com.smcmaster.selftest.runner.SelfTestRunner;

/**
 * Self-test unit test for demonstration purposes.
 * When using the {@link SelfTestRunner}, self-tests will
 * be generated and executed on the object returned by the annotated
 * {@link SelfTestMethod}. They will be organized in a JUnit
 * "tree" as follows:
 *   - <test class name>
 *    - <self-test method name>
 *     - <method 1, self-test 1>
 *     - <method 1, self-test 2>
 *     - <method 2, self-test 1>
 *     - etc.
 *     
 * @author scott@smcmaster.com
 */
@RunWith(SelfTestRunner.class)
public class MathOperationsTest {
  @SelfTestMethod(name = "Default math operations")
  public MathOperations selfTestMathOperations() {
    return new MathOperations();
  }
}
