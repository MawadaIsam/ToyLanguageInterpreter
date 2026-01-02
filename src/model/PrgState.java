package model;

import exceptions.ExpressionException;
import exceptions.MyException;
import java.io.BufferedReader;
import model.ADT.*;
import model.statements.Stmt;
import model.values.StringValue;
import model.values.Value;

public class PrgState {

  private static int nextId = 1;
  MyIStack<Stmt> exeStack;
  MyIDict<String, Value> symTable;
  MyIList<Value> out;
  MyIHeap<Integer, Value> heap;
  MyIDict<StringValue, BufferedReader> fileTable;
  Stmt originalProgram;
  private int id;

  public PrgState(
      MyIStack<Stmt> stk,
      MyIDict<String, Value> symtbl,
      MyIList<Value> ot,
      MyIDict<StringValue, BufferedReader> filetbl,
      MyIHeap<Integer, Value> hp,
      Stmt prg) {
    this.id = getNextId();

    exeStack = stk;
    symTable = symtbl;
    out = ot;
    fileTable = filetbl;
    heap = hp;
    originalProgram = deepCopy(prg); // better do it with a deep copy
    // stk.push(prg);
    if (originalProgram != null && this.exeStack.isEmpty()) {
      stk.push(originalProgram);
    }
  }

  // Constructor for fork(child thread)
  public PrgState(
      MyIStack<Stmt> stk,
      MyIDict<String, Value> symtbl,
      MyIList<Value> ot,
      MyIDict<StringValue, BufferedReader> filetbl,
      MyIHeap<Integer, Value> hp) {
    exeStack = stk;
    symTable = symtbl;
    out = ot;
    fileTable = filetbl;
    heap = hp;
    this.id = getNextId();
  }

  private static synchronized int getNextId() {
    return nextId++;
  }

  public boolean isNotCompleted() {
    return !exeStack.isEmpty();
  }

  public PrgState oneStep() throws MyException, ExpressionException {
    if (exeStack.isEmpty()) {
      throw new MyException("Execution Stack is empty");
    }
    Stmt crtStmt = exeStack.pop();
    return crtStmt.execute(this);
  }

  public int getId() {
    return id;
  }

  public MyIStack<Stmt> getStk() {
    return exeStack;
  }

  public MyIDict<String, Value> getSymTable() {
    return symTable;
  }

  public MyIList<Value> getOut() {
    return out;
  }

  public MyIDict<StringValue, BufferedReader> getFileTable() {
    return fileTable;
  }

  public void setFileTable(MyIDict<StringValue, BufferedReader> fileTable) {
    this.fileTable = fileTable;
  }

  public MyIHeap<Integer, Value> getHeap() {
    return heap;
  }

  @Override
  public String toString() {
    return "ID: "
        + id
        + "\n"
        + "ExeStack: "
        + exeStack.toString()
        + "\nSymTable: "
        + symTable.getContent().toString()
        + "\nOut: "
        + out.toString()
        + "\nFileTable: "
        + fileTable.getContent().toString()
        + "\nHeap: "
        + heap.getContent().toString()
        + "\n";
  }

  private Stmt deepCopy(Stmt prg) {
    return prg;
  }
}
