package model.statements;

import exceptions.MyException;
import model.ADT.MyIDict;
import model.ADT.MyIStack;
import model.PrgState;
import model.exp.Exp;
import model.types.BoolType;
import model.types.Type;

public class RepeatStmt implements Stmt {
    Stmt stmt;
    Exp exp;

    public RepeatStmt(Stmt stmt, Exp exp) {
        this.stmt = stmt;
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) {
        MyIStack<Stmt> stack= state.getStk();

        Stmt converted = new CompStmt(stmt,
                new WhileStmt(exp,stmt));
        stack.push(converted);
        return null;
    }
    @Override
    public MyIDict<String , Type> typecheck(MyIDict<String , Type> typeEnv) throws MyException {
        Type typeExp=exp.typecheck(typeEnv);
        if(!typeExp.equals(new BoolType())){
            throw new MyException("Type Error");
        }
        stmt.typecheck(typeEnv);
        return typeEnv;
    }
    @Override
    public String toString() {
        return "Repeat("+stmt.toString()+")"+"As "+exp.toString();
    }
}
