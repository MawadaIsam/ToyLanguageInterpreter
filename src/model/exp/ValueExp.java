package model.exp;

import exceptions.ExpressionException;
import model.ADT.MyIDict;
import model.ADT.MyIHeap;
import model.values.Value;

public class ValueExp implements Exp {
  private Value e;

  public ValueExp(Value e) {
    this.e = e;
  }

  @Override
  public Value eval(MyIDict<String, Value> tbl, MyIHeap<Integer, Value> hp)
      throws ExpressionException {
    return e;
  }

  @Override
  public String toString() {
    return e.toString();
  }
}
