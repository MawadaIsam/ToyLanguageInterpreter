package model.exp;

import exceptions.ExpressionException;
import exceptions.MyException;
import model.ADT.MyIDict;
import model.ADT.MyIHeap;
import model.types.BoolType;
import model.types.Type;
import model.values.BoolValue;
import model.values.Value;

public class NotExp implements Exp {
  Exp exp;

  public NotExp(Exp exp) {
    this.exp = exp;
  }

  @Override
  public Value eval(MyIDict<String, Value> tbl, MyIHeap<Integer, Value> hp)
      throws MyException, ExpressionException {
    Value value = exp.eval(tbl, hp);
    if (!value.getType().equals(new BoolType())) {
      throw new MyException("Not a valid expression");
    }
    boolean b = ((BoolValue) value).getValue();

    return new BoolValue(!b);
  }

  @Override
  public Type typecheck(MyIDict<String, Type> typeEnv) throws MyException {
    return new BoolType();
  }

  @Override
  public String toString() {
    return "( Not " + exp.toString() + " )";
  }
}
