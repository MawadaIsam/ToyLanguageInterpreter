package model.statements;

import exceptions.MyException;
import model.ADT.MyIDict;
import model.PrgState;
import model.types.Type;
import model.values.Value;

public class VarDeclStmt implements Stmt {
    String name;
    Type type;
    public VarDeclStmt(String name, Type type) {
        this.name = name;
        this.type = type;
    }
    public String toString() {
        return type + " " + name;
    }
    @Override
    public PrgState execute (PrgState state )throws MyException{
        MyIDict<String, Value> symTable=state.getSymTable();
        if(symTable.isDefined(name)){
            throw new MyException("the variable "+name+" is already defined");
    }
    /*if(type instanceof IntType){
          symTable.update(name, new IntValue(0));
      }
      else if(type instanceof BoolType){
    symTable.update(name, new BoolValue(false));
      }
      else{
          throw new MyException("the variable "+name+" is of unsupported type");
      }*/
    else symTable.update(name, type.defaultValue());
        return state;
    }
}
