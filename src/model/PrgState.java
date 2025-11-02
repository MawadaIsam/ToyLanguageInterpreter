package model;

import exceptions.MyException;
import model.ADT.*;
import model.statements.Stmt;
import model.values.Value;

public class PrgState {
    MyIStack<Stmt> exeStack;
    MyIDict<String, Value> symTable;
    MyIList<Value> out;
    Stmt originalProgram;
    public PrgState(MyIStack<Stmt> stk, MyIDict<String, Value> symtbl, MyIList<Value> ot, Stmt prg) throws MyException {
        exeStack = stk;
        symTable = symtbl;
        out = ot;
        originalProgram = prg;// better do it with a deep copy
        stk.push(prg);
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

    @Override
    public String toString() {
        return "ExeStack: " + exeStack + "\nSymTable: " + symTable.getContent() + "\nOut: " + out +"\n";
    }
}
