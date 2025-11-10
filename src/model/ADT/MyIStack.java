package model.ADT;

import java.util.List;

public interface MyIStack<T> {
  void push(T element);

  T pop();

  boolean isEmpty();

  String toString();

  List<T> getReversed();
}
