package model.statements;

import exceptions.ExpressionException;
import exceptions.MyException;
import model.ADT.*;
import model.PrgState;
import model.exp.Exp;
import model.types.Type;
import model.values.Value;

public class PrintStmt implements Stmt {
  Exp exp;

  public PrintStmt(Exp exp) {
    this.exp = exp;
  }

  public String toString() {
    return "print(" + exp.toString() + ")";
  }

  @Override
  public MyIDict<String, Type> typecheck(MyIDict<String, Type> typeEnv) throws MyException {
    exp.typecheck(typeEnv);
    return typeEnv;
  }

  @Override
  public PrgState execute(PrgState state) throws MyException, ExpressionException {
    MyIList<Value> out = state.getOut();
    out.add(exp.eval(state.getSymTable(), state.getHeap()));
    return null;
  }
}
