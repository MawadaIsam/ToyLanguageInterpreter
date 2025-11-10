package controller;

import exceptions.ExpressionException;
import exceptions.MyException;
import model.ADT.MyIStack;
import model.PrgState;
import model.statements.Stmt;
import repository.IRespository;

public class Controller {
  private IRespository repo;

  public Controller(IRespository repo) {
    this.repo = repo;
  }

  public PrgState oneStep(PrgState state) throws MyException, ExpressionException {
    MyIStack<Stmt> stk = state.getStk();
    if (stk.isEmpty()) {
      throw new MyException("Empty stack");
    }
    Stmt currStmt = stk.pop();
    return currStmt.execute(state);
  }

  public void allStep() throws MyException, ExpressionException {
    PrgState prg = repo.getCrtPrg();
    // System.out.println("Initial State:\n" + prg);
    repo.logPrgStateExec();

    while (!prg.getStk().isEmpty()) {
      oneStep(prg);
      repo.logPrgStateExec();
      // System.out.println("Current State:\n" + prg);
    }
  }
}
