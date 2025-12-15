package model.exp;

import exceptions.ExpressionException;
import exceptions.MyException;
import model.ADT.MyIDict;
import model.ADT.MyIHeap;
import model.types.BoolType;
import model.types.Type;
import model.values.BoolValue;
import model.values.Value;

public class LogicExp implements Exp {
  int op; // 1 and, 2 or
  private Exp left;
  private Exp right;

  public LogicExp(Exp left, Exp right, int op) {
    this.left = left;
    this.right = right;
    this.op = op;
  }

  @Override
  public Value eval(MyIDict<String, Value> tbl, MyIHeap<Integer, Value> hp)
      throws ExpressionException, MyException {
    Value v1, v2;
    v1 = left.eval(tbl, hp);
    if (v1.getType().equals(new BoolType())) {
      v2 = right.eval(tbl, hp);
      if (v2.getType().equals(new BoolType())) {
        BoolValue b1 = (BoolValue) v1;
        BoolValue b2 = (BoolValue) v2;
        boolean n1 = b1.getValue();
        boolean n2 = b2.getValue();
        if (op == 1) {
          return new BoolValue(n1 && n2);
        } else if (op == 2) {
          return new BoolValue(n1 || n2);
        } else throw new ExpressionException("enter a valid operator");

      } else throw new ExpressionException("Second operand is not a boolean");
    } else throw new ExpressionException("First operand is not a boolean");
  }

  @Override
  public Type typecheck(MyIDict<String, Type> typeEnv) throws MyException {
    Type type1 = left.typecheck(typeEnv);
    Type type2 = right.typecheck(typeEnv);

    if (type1.equals(new BoolType())) {
      if (type2.equals(new BoolType())) {
        return new BoolType();
      } else {
        throw new MyException("Second operand is not a boolean");
      }
    } else {
      throw new MyException("First operand is not a boolean");
    }
  }

  @Override
  public String toString() {
    String opSymbol =
        switch (op) {
          case 1 -> "and";
          case 2 -> "or";
          default -> "?";
        };
    return left.toString() + " " + opSymbol + " " + right.toString();
  }
}
