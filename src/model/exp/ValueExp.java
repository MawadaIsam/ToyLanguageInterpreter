package model.exp;

import exceptions.ExpressionException;
import exceptions.MyException;
import model.ADT.MyIDict;
import model.ADT.MyIHeap;
import model.types.Type;
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

  @Override
  public Type typecheck(MyIDict<String, Type> typeEnv) throws MyException {
    return e.getType();
  }
}
