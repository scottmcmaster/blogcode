package com.smcmaster.selftest;

import com.smcmaster.selftest.annotations.Comparison;
import com.smcmaster.selftest.annotations.Comparison.Type;

/**
 * Sample class for self-testing.
 * 
 * @author scott@smcmaster.com
 */
public class MathOperations {

  @Comparison(inputs = {"50"}, output="50")
  @Comparison(inputs = {"-40"}, output="40", description="Abs of a negative")
  @Comparison(inputs = {"-40"}, output="0", type=Type.GREATER_THAN_OR_EQUALS)
  public int abs(int input) {
    return input >= 0 ? input : -input;
  }
  
  @Comparison(inputs = {SelfTest.NULL_VALUE}, type=Type.NULL, description="Test a null output")
  public Integer abs2(Integer input) {
    if (input == null) {
      return null;
    }
    return input >= 0 ? input : -input;
  }
  
  @Comparison(inputs = {"2", "4"}, output="7", description="This test fails on purpose")
  @Comparison(inputs = {"-5", "10"}, output="5")
  @Comparison(inputs = {SelfTest.NULL_VALUE, "11"}, output="11", description="Test a null input")
  public int sum(Integer first, int second) {
    if (first == null) {
      return second;
    }
    return first + second;
  }
}
