package model.ADT;

public interface MyIStack<T> {
  void push(T element);

  T pop();

  boolean isEmpty();

  String toString();
}
