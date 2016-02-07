package com.smcmaster.scalafun.implicits

/**
 * Implicit methods that add "first" through "tenth"
 * extraction methods to the Scala List.
 */
object ListMethods {
  implicit class Nth[T](lst: List[T]) {
    def first = lst(0)
    def second = lst(1)
    def third = lst(2)
    def fourth = lst(3)
    def fifth = lst(4)
    def sixth = lst(5)
    def seventh = lst(6)
    def eighth = lst(7)
    def ninth = lst(8)
    def tenth = lst(9)
  }
}
