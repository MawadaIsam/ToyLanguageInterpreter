package model.exp;

import exceptions.ExpressionException;
import model.ADT.MyIDict;
import model.values.Value;

public class ValueExp implements Exp {
  private Value e;

  public ValueExp(Value e) {
    this.e = e;
  }

  @Override
  public Value eval(MyIDict<String, Value> tbl) throws ExpressionException {
    return e;
  }

  @Override
  public String toString() {
    return e.toString();
  }
}
