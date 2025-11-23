package model.exp;

import exceptions.ExpressionException;
import exceptions.MyException;
import model.ADT.MyIDict;
import model.ADT.MyIHeap;
import model.types.IntType;
import model.values.BoolValue;
import model.values.IntValue;
import model.values.Value;

public class RelExp implements Exp {
  private Exp left;
  private Exp right;
  private String op;

  public RelExp(Exp left, Exp right, String op) {
    this.left = left;
    this.right = right;
    this.op = op;
  }

  @Override
  public Value eval(MyIDict<String, Value> tbl, MyIHeap<Integer, Value> hp)
      throws ExpressionException, MyException {
    Value v1 = left.eval(tbl, hp);
    Value v2 = right.eval(tbl, hp);

    if (!v1.getType().equals(new IntType()))
      throw new ExpressionException("First operand is not a integer");
    if (!v2.getType().equals(new IntType()))
      throw new ExpressionException("Second operand is not a integer");

    int n1 = ((IntValue) v1).getValue();
    int n2 = ((IntValue) v2).getValue();

    return switch (op) {
      case "<" -> new BoolValue(n1 < n2);
      case ">" -> new BoolValue(n1 > n2);
      case "==" -> new BoolValue(n1 == n2);
      case "!=" -> new BoolValue(n1 != n2);
      case ">=" -> new BoolValue(n1 >= n2);
      case "<=" -> new BoolValue(n1 <= n2);
      default -> throw new ExpressionException("Invalid relational operator");
    };
  }

  @Override
  public String toString() {
    return left.toString() + " " + op + " " + right.toString();
  }
}
