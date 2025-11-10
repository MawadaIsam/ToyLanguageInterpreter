package model.ADT;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class MyStack<T> implements MyIStack<T> {
  Stack<T> stack = new Stack<>();

  public MyStack(Stack<T> stack) {
    this.stack = stack;
  }

  public MyStack() {
    this.stack = new Stack<>();
  }

  @Override
  public void push(T value) {
    stack.push(value);
  }

  @Override
  public T pop() {
    return stack.pop();
  }

  @Override
  public boolean isEmpty() {
    return stack.isEmpty();
  }

  @Override
  public String toString() {
    return stack.toString();
  }

  @Override
  public List<T> getReversed() {
    List<T> list = new ArrayList<>(stack);
    Collections.reverse(list);
    return list;
  }
}
