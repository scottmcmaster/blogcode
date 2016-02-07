package com.smcmaster.scalafun.implicits

import org.scalatest.FunSuite
import com.smcmaster.scalafun.implicits.ListMethods.Nth

/**
 * Tests for the implict List methods first-tenth.
 */
class ListMethodsTest extends FunSuite {
  val lst = List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)

  test("first method works") {
    assert(1 == lst.first)
  }
  
  test("second method works") {
    assert(2 == lst.second)
  }
  
  test("third method works") {
    assert(3 == lst.third)
  }
  
  test("fourth method works") {
    assert(4 == lst.fourth)
  }
  
  test("fifth method works") {
    assert(5 == lst.fifth)
  }
  
  test("sixth method works") {
    assert(6 == lst.sixth)
  }
  
  test("seventh method works") {
    assert(7 == lst.seventh)
  }
  
  test("eighth method works") {
    assert(8 == lst.eighth)
  }
  
  test("ninth method works") {
    assert(9 == lst.ninth)
  }
  
  test("tenth method works") {
    assert(10 == lst.tenth)
  }
}