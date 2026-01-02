package view;

import controller.Controller;
import exceptions.MyException;
import java.io.BufferedReader;
import model.*;
import model.ADT.*;
import model.exp.*;
import model.statements.*;
import model.types.BoolType;
import model.types.IntType;
import model.types.RefType;
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

  private static Stmt ex6() {
    return new CompStmt(
        new VarDeclStmt("v", new RefType(new IntType())),
        new CompStmt(
            new NewStmt("v", new ValueExp(new IntValue(20))),
            new CompStmt(
                new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                new CompStmt(
                    new NewStmt("a", new VarExp("v")),
                    new CompStmt(
                        new PrintStmt(new ReadHeapExp(new VarExp("v"))),
                        new PrintStmt(
                            new ArithExp(
                                new ReadHeapExp(new ReadHeapExp(new VarExp("a"))),
                                new ValueExp(new IntValue(5)),
                                1)))))));
  }

  private static Stmt ex7() {
    return new CompStmt(
        new VarDeclStmt("v", new RefType(new IntType())),
        new CompStmt(
            new NewStmt("v", new ValueExp(new IntValue(20))),
            new CompStmt(
                new PrintStmt(new ReadHeapExp(new VarExp("v"))),
                new CompStmt(
                    new WriteHeapStmt("v", new ValueExp(new IntValue(30))),
                    new PrintStmt(
                        new ArithExp(
                            new ReadHeapExp(new VarExp("v")),
                            new ValueExp(new IntValue(5)),
                            1))))));
  }

  private static Stmt ex8() {
    return new CompStmt(
        new VarDeclStmt("v", new RefType(new IntType())),
        new CompStmt(
            new NewStmt("v", new ValueExp(new IntValue(20))),
            new CompStmt(
                new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                new CompStmt(
                    new NewStmt("a", new VarExp("v")),
                    new CompStmt(
                        new NewStmt("v", new ValueExp(new IntValue(30))),
                        new PrintStmt(new ReadHeapExp(new ReadHeapExp(new VarExp("a")))))))));
  }

  private static Stmt ex9() {
    return new CompStmt(
        new VarDeclStmt("v", new IntType()),
        new CompStmt(
            new AssignStmt("v", new ValueExp(new IntValue(4))),
            new CompStmt(
                new WhileStmt(
                    new RelExp(new VarExp("v"), new ValueExp(new IntValue(0)), ">"),
                    new CompStmt(
                        new PrintStmt(new VarExp("v")),
                        new AssignStmt(
                            "v", new ArithExp(new VarExp("v"), new ValueExp(new IntValue(1)), 2)))),
                new PrintStmt(new VarExp("v")))));
  }

  private static Stmt ex12() {
    return new CompStmt(
        new VarDeclStmt("v", new IntType()),
        new CompStmt(
            new VarDeclStmt("a", new RefType(new IntType())),
            new CompStmt(
                new AssignStmt("v", new ValueExp(new IntValue(10))),
                new CompStmt(
                    new NewStmt("a", new ValueExp(new IntValue(22))),
                    new CompStmt(
                        new ForkStmt(
                            new CompStmt(
                                new WriteHeapStmt("a", new ValueExp(new IntValue(30))),
                                new CompStmt(
                                    new AssignStmt("v", new ValueExp(new IntValue(32))),
                                    new CompStmt(
                                        new PrintStmt(new VarExp("v")),
                                        new PrintStmt(new ReadHeapExp(new VarExp("a"))))))),
                        new CompStmt(
                            new PrintStmt(new VarExp("v")),
                            new PrintStmt(new ReadHeapExp(new VarExp("a")))))))));
  }

  private static Stmt exTypeError1() {
    // assigning boolean to integer variable
    return new CompStmt(
        new VarDeclStmt("v", new IntType()),
        new AssignStmt("v", new ValueExp(new BoolValue(true))));
  }

  private static Stmt exTypeError2() {
    // using undeclared variable
    return new CompStmt(new VarDeclStmt("v", new IntType()), new PrintStmt(new VarExp("x")));
  }

  private static PrgState createPrgState(Stmt originalPrg) throws MyException {
    MyIStack<Stmt> exeStack = new MyStack<>();
    MyIDict<String, Value> symTable = new MyDict<>();
    MyIList<Value> output = new MyList<>();
    MyIDict<StringValue, BufferedReader> fileTable = new MyDict<>();
    MyIHeap<Integer, Value> heap = new MyHeap<>();

    return new PrgState(exeStack, symTable, output, fileTable, heap, originalPrg);
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
    menu.addCommand(new RunExample("6", ex6(), createController(ex6(), "log6.txt")));
    menu.addCommand(new RunExample("7", ex7(), createController(ex7(), "log7.txt")));
    menu.addCommand(new RunExample("8", ex8(), createController(ex8(), "log8.txt")));
    menu.addCommand(new RunExample("9", ex9(), createController(ex9(), "log9.txt")));

    menu.addCommand(
        new RunExample("10", exTypeError1(), createController(exTypeError1(), "log10.txt")));
    menu.addCommand(
        new RunExample("11", exTypeError2(), createController(exTypeError2(), "log11.txt")));
    menu.addCommand(new RunExample("12", ex12(), createController(ex12(), "log12.txt")));

    menu.addCommand(new ExitCommand("0", "Exit"));

    menu.show();
  }
}
