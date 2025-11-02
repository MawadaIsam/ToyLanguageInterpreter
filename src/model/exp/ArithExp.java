package model.exp;

import exceptions.MyException;
import model.ADT.MyIDict;
import model.types.IntType;
import model.values.IntValue;
import model.values.Value;

public class ArithExp implements Exp {
  int op; // 1plus, 2minus, 3start, 4divide
  private Exp left;
  private Exp right;

  public ArithExp(Exp left, Exp right, int op) {
    this.left = left;
    this.right = right;
    this.op = op;
  }

  @Override
  public Value eval(MyIDict<String, Value> tbl) throws MyException {
    Value v1, v2;
    v1 = left.eval(tbl);
    if (v1.getType().equals(new IntType())) {
      v2 = right.eval(tbl);
      if (v2.getType().equals(new IntType())) {
        IntValue i1 = (IntValue) v1;
        IntValue i2 = (IntValue) v2;
        int n1 = i1.getValue();
        int n2 = i2.getValue();
        if (op == 1) {
          return new IntValue(n1 + n2);
        } else if (op == 2) {
          return new IntValue(n1 - n2);
        } else if (op == 3) {
          return new IntValue(n1 * n2);
        } else if (op == 4) {
          if (n2 == 0) throw new MyException("Divion by zero");
          else return new IntValue(n1 / n2);

        } else throw new MyException("Unknown operation");

      } else throw new MyException("Second operand is not an integer");
    } else throw new MyException("First operand is not an integer");
  }

  @Override
  public String toString() {
    String opSympol =
        switch (op) {
          case 1 -> "+";
          case 2 -> "-";
          case 3 -> "*";
          case 4 -> "/";
          default -> "?";
        };
    return left.toString() + " " + opSympol + " " + right.toString();
  }
}
