package model.statements;

import exceptions.ExpressionException;
import exceptions.MyException;
import java.io.BufferedReader;
import model.ADT.*;
import model.PrgState;
import model.types.Type;
import model.values.StringValue;
import model.values.Value;

public class ForkStmt implements Stmt {
  private Stmt stmt;

  public ForkStmt(Stmt stmt) {
    this.stmt = stmt;
  }

  @Override
  public String toString() {
    return "ForkStmt [stmt=" + stmt.toString() + "]";
  }

  @Override
  public PrgState execute(PrgState state) throws MyException, ExpressionException {
    MyIList<Value> out = state.getOut();
    MyIDict<StringValue, BufferedReader> fileTable = state.getFileTable();
    MyIHeap<Integer, Value> heap = state.getHeap();
    MyIBarrier barrier = state.getBarrier();

    // cloning the symbol table => each thread has its own copy
    MyIDict<String, Value> newSymTable = cloneSymTable(state.getSymTable());

    // creating new execution stack with the fork statement
    MyIStack<Stmt> newExeStack = new MyStack<>();
    newExeStack.push(stmt);

    // creating and returning newPrgState (child thread)
    return new PrgState(newExeStack, newSymTable, out, fileTable, heap, barrier);
  }

  // deep copy of symbol table
  private MyIDict<String, Value> cloneSymTable(MyIDict<String, Value> original) throws MyException {
    MyIDict<String, Value> newSymTable = new MyDict<>();

    // copy everything from original to new
    for (String key : original.getKeys()) {
      Value val = original.find(key);
      newSymTable.update(key, val);
    }

    return newSymTable;
  }

  @Override
  public MyIDict<String, Type> typecheck(MyIDict<String, Type> typeEnv) throws MyException {
    MyIDict<String, Type> clonedTypeEnv = cloneTypeEnv(typeEnv);
    stmt.typecheck(clonedTypeEnv);
    return typeEnv;
  }

  private MyIDict<String, Type> cloneTypeEnv(MyIDict<String, Type> original) throws MyException {
    MyIDict<String, Type> newTypeEnv = new MyDict<>();
    for (String key : original.getKeys()) {
      Type type = original.find(key);
      newTypeEnv.update(key, type);
    }

    return newTypeEnv;
  }
}
