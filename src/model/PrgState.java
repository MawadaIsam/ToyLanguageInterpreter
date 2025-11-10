package model;

import exceptions.MyException;
import java.io.BufferedReader;
import model.ADT.*;
import model.statements.Stmt;
import model.values.StringValue;
import model.values.Value;

public class PrgState {
    MyIStack<Stmt> exeStack;
    MyIDict<String, Value> symTable;
    MyIList<Value> out;
    MyIDict<StringValue, BufferedReader> fileTable;
    Stmt originalProgram;

    public PrgState(MyIStack<Stmt> stk, MyIDict<String, Value> symtbl, MyIList<Value> ot,MyIDict<StringValue,BufferedReader> filetbl ,Stmt prg) throws MyException {
        exeStack = stk;
        symTable = symtbl;
        out = ot;
        fileTable = filetbl;
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
    public MyIDict<StringValue,BufferedReader> getFileTable() {
        return fileTable;
    }
    public void setFileTable(MyIDict<StringValue,BufferedReader> fileTable) {
        this.fileTable = fileTable;
    }

    @Override
    public String toString() {
        return "ExeStack: " + exeStack.toString()+
                "\nSymTable: " + symTable.getContent().toString() +
                "\nOut: " + out.toString()+
                "\nFileTable: " + fileTable.getContent().toString()+"\n";
    }
}
