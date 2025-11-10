package view;

import controller.Controller;
import exceptions.MyException;
import java.io.BufferedReader;
import model.*;
import model.ADT.*;
import model.exp.ArithExp;
import model.exp.RelExp;
import model.exp.ValueExp;
import model.exp.VarExp;
import model.statements.*;
import model.types.BoolType;
import model.types.IntType;
import model.types.StringType;
import model.values.BoolValue;
import model.values.IntValue;
import model.values.StringValue;
import model.values.Value;
import repository.*;
import view.command.ExitCommand;
import view.command.RunExample;

public class View {

  private static Stmt ex1() {
    return new CompStmt(
        new VarDeclStmt("v", new IntType()),
        new CompStmt(
            new AssignStmt("v", new ValueExp(new IntValue(2))), new PrintStmt(new VarExp("v"))));
  }

  private static Stmt ex2() {
    return new CompStmt(
        new VarDeclStmt("a", new IntType()),
        new CompStmt(
            new VarDeclStmt("b", new IntType()),
            new CompStmt(
                new AssignStmt(
                    "a",
                    new ArithExp(
                        new ValueExp(new IntValue(2)),
                        new ArithExp(
                            new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5)), 3),
                        1)),
                new CompStmt(
                    new AssignStmt(
                        "b", new ArithExp(new VarExp("a"), new ValueExp(new IntValue(1)), 1)),
                    new PrintStmt(new VarExp("b"))))));
  }

  private static Stmt ex3() {
    return new CompStmt(
        new VarDeclStmt("a", new BoolType()),
        new CompStmt(
            new VarDeclStmt("v", new IntType()),
            new CompStmt(
                new AssignStmt("a", new ValueExp(new BoolValue(true))),
                new CompStmt(
                    new IfStmt(
                        new VarExp("a"),
                        new AssignStmt("v", new ValueExp(new IntValue(2))),
                        new AssignStmt("v", new ValueExp(new IntValue(3)))),
                    new PrintStmt(new VarExp("v"))))));
  }

  private static Stmt ex4() {
    return new CompStmt(
        new VarDeclStmt("varf", new StringType()),
        new CompStmt(
            new AssignStmt("varf", new ValueExp(new StringValue("test.in"))),
            new CompStmt(
                new OpenRFile(new VarExp("varf")),
                new CompStmt(
                    new VarDeclStmt("varc", new IntType()),
                    new CompStmt(
                        new ReadFile(new VarExp("varf"), "varc"),
                        new CompStmt(
                            new PrintStmt(new VarExp("varc")),
                            new CompStmt(
                                new ReadFile(new VarExp("varf"), "varc"),
                                new CompStmt(
                                    new PrintStmt(new VarExp("varc")),
                                    new CloseRFile(new VarExp("varf"))))))))));
  }

  private static Stmt ex5() {
    return new CompStmt(
        new VarDeclStmt("x", new IntType()),
        new CompStmt(
            new AssignStmt("x", new ValueExp(new IntValue(10))),
            new CompStmt(
                new IfStmt(
                    new RelExp(new VarExp("x"), new ValueExp(new IntValue(5)), ">"),
                    new CompStmt(new PrintStmt(new VarExp("x")), new NopStmt()),
                    new PrintStmt(new ValueExp(new IntValue(0)))),
                new PrintStmt(new ValueExp(new IntValue(100))))));
  }

  private static PrgState createPrgState(Stmt originalPrg) throws MyException {
    MyIStack<Stmt> exeStack = new MyStack<>();
    MyIDict<String, Value> symTable = new MyDict<>();
    MyIList<Value> output = new MyList<>();
    MyIDict<StringValue, BufferedReader> fileTable = new MyDict<>();

    return new PrgState(exeStack, symTable, output, fileTable, originalPrg);
  }

  private static Controller createController(Stmt stmt, String logFilePath) throws MyException {
    PrgState prgState = createPrgState(stmt);
    IRespository repo = new Repository(prgState, logFilePath);
    return new Controller(repo);
  }

  public static void main(String[] args) throws MyException {
    TextMenu menu = new TextMenu();

    menu.addCommand(new RunExample("1", ex1(), createController(ex1(), "log1.txt")));
    menu.addCommand(new RunExample("2", ex2(), createController(ex2(), "log2.txt")));
    menu.addCommand(new RunExample("3", ex3(), createController(ex3(), "log3.txt")));
    menu.addCommand(new RunExample("4", ex4(), createController(ex4(), "log4.txt")));
    menu.addCommand(new RunExample("5", ex5(), createController(ex5(), "log5.txt")));

    menu.addCommand(new ExitCommand("0", "Exit"));

    menu.show();
  }
}
