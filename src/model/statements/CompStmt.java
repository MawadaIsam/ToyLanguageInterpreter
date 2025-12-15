package model.statements;

import exceptions.MyException;
import model.ADT.MyIDict;
import model.ADT.MyIStack;
import model.PrgState;
import model.types.Type;

public class CompStmt implements Stmt {
  Stmt first;
  Stmt second;

  public CompStmt(Stmt first, Stmt second) {
    this.first = first;
    this.second = second;
  }

  public String toString() {
    return "(" + first.toString() + "; " + second.toString() + ")";
  }

  @Override
  public PrgState execute(PrgState state) throws MyException {
    MyIStack<Stmt> stk = state.getStk();
    stk.push(second);
    stk.push(first);
    return state;
  }

  @Override
  public MyIDict<String, Type> typecheck(MyIDict<String, Type> typeEnv) throws MyException {
    return second.typecheck(first.typecheck(typeEnv));
  }
}
