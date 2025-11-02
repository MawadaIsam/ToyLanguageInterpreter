package model.statements;

import exceptions.MyException;
import model.PrgState;

public class NopStmt implements Stmt {
    @Override
    public PrgState execute (PrgState state) throws MyException{
        return state;
    }
    public String toString (){
        return "NopStmt";
    }

}
