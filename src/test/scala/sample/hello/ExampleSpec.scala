package sample.hello

import java.util

import org.scalatest.FunSpec


class ExampleSpec extends FunSpec{

  describe("A Stack") {
    it("should pop values in last-in-first-order") {
      val stack = new util.Stack[Int]
      stack.push(1)
      stack.push(2)
      assert(stack.pop() === 2)
      assert(stack.pop() === 1)

    }
    it("should throw NoSuchElementException if an empty stack is popped") {
      val emptyStack = new util.Stack[Int]
      intercept[NoSuchElementException] {
        emptyStack.pop()
      }
    }
  }
}
