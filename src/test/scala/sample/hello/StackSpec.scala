package sample.hello

import java.util

import org.scalatest.FlatSpec

class StackSpec extends FlatSpec{

  "A Stack" should "pop values in last-in-first-out order" in {
    val stack = new util.Stack[Int]
    stack.push(1)
    stack.push(2)
    assert(stack.pop() === 2)
    assert(stack.pop() === 1)
  }

  it should "throw NoSuchElementException if an empty stack is popped" in {
    val emptyStack = new util.Stack[String]
    intercept[NoSuchElementException] {
      emptyStack.pop()
    }
  }
}
