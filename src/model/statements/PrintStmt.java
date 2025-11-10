package model.statements;

import exceptions.ExpressionException;
import exceptions.MyException;
import model.ADT.*;
import model.PrgState;
import model.exp.Exp;
import model.values.Value;

public class PrintStmt implements Stmt {
    Exp exp;
    public PrintStmt(Exp exp) {
        this.exp = exp;
    }
    public String toString() {
        return "print("+exp.toString()+")";
    }
    @Override
    public PrgState execute (PrgState state) throws MyException, ExpressionException {
        MyIList<Value> out = state.getOut();
        out.add(exp.eval(state.getSymTable()));
        return state;


    }

}
