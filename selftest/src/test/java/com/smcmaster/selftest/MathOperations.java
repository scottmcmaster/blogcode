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
  @Comparison(inputs = {"-40"}, output="40")
  @Comparison(inputs = {"-40"}, output="0", type=Type.GREATER_THAN_OR_EQUALS)
  public int abs(int input) {
    return input >= 0 ? input : -input;
  }
  
  @Comparison(inputs = {"2", "4"}, output="7", description="This test fails on purpose")
  @Comparison(inputs = {"-5", "10"}, output="5")
  public int sum(Integer first, int second) {
    return first + second;
  }
}
