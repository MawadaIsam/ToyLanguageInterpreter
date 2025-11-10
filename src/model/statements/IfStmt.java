package model.statements;

import exceptions.ExpressionException;
import exceptions.MyException;
import model.ADT.MyIStack;
import model.PrgState;
import model.exp.Exp;
import model.types.BoolType;
import model.values.BoolValue;
import model.values.Value;

public class IfStmt implements Stmt {
  Exp exp;
  Stmt thenS;
  Stmt elseS;

  public IfStmt(Exp exp, Stmt thenS, Stmt elseS) {
    this.exp = exp;
    this.thenS = thenS;
    this.elseS = elseS;
  }

  public String toString() {
    return "(IF("
        + exp.toString()
        + ")THEN ("
        + thenS.toString()
        + ") ELSE("
        + elseS.toString()
        + "))";
  }

  @Override
  public PrgState execute(PrgState state) throws MyException, ExpressionException {
    Value condition = exp.eval(state.getSymTable());
    if (!condition.getType().equals(new BoolType())) {
      throw new MyException("Condition is not boolean");
    }
    MyIStack<Stmt> stack = state.getStk();
    if (((BoolValue) condition).getValue()) {
      stack.push(thenS);
    } else {
      stack.push(elseS);
    }
    return state;
  }
}
