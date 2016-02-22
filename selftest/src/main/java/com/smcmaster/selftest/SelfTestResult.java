package com.smcmaster.selftest;

public class SelfTestResult {

  public enum ResultType {
    PASS, FAIL, ERROR
  }
  
  private ResultType type;

  public SelfTestResult(ResultType type) {
    this.type = type;
  }

  public ResultType getType() {
    return type;
  }
}
