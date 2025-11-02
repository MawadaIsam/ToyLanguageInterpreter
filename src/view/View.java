package view;

import controller.Controller;
import exceptions.MyException;
import java.util.Scanner;
import model.*;
import model.ADT.*;
import model.exp.ArithExp;
import model.exp.ValueExp;
import model.exp.VarExp;
import model.statements.*;
import model.types.BoolType;
import model.types.IntType;
import model.values.BoolValue;
import model.values.IntValue;
import model.values.Value;
import repository.*;

public class View {
  private final Controller ctrl1;
  private final Controller ctrl2;
  private final Controller ctrl3;

  public View() throws MyException {
    Stmt ex1 =
        new CompStmt(
            new VarDeclStmt("v", new IntType()),
            new CompStmt(
                new AssignStmt("v", new ValueExp(new IntValue(2))),
                new PrintStmt(new VarExp("v"))));

    Stmt ex2 =
        new CompStmt(
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

    Stmt ex3 =
        new CompStmt(
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

    ctrl1 = buildController(ex1);
    ctrl2 = buildController(ex2);
    ctrl3 = buildController(ex3);
  }

  private Controller buildController(Stmt stmt) throws MyException {
    MyIStack<Stmt> stack = new MyStack<>();
    MyIDict<String, Value> symTable = new MyDict<>();
    MyIList<Value> output = new MyList<>();

    PrgState prg = new PrgState(stack, symTable, output, stmt);
    IRespository repo = new Repository(prg);
    return new Controller(repo);
  }

  public void start() {
    Scanner scanner = new Scanner(System.in);
    boolean running = true;

    while (running) {
      System.out.println("\n TOY LANGUAGE INTERPRETER");
      System.out.println("1. Run example 1:int v; v=2;Print(v)");
      System.out.println("2. Run example 2:int a;int b; a=2+3*5;b=a+1;Print(b)");
      System.out.println(
          "3. Run example 3:bool a; int v; a=true;(If a Then v=2 Else v=3);Print(v)");
      System.out.println("0. Exit");
      System.out.println("Choose an option:");

      String choice = scanner.nextLine();

      try {
        switch (choice) {
          case "1":
            ctrl1.allStep();
            break;
          case "2":
            ctrl2.allStep();
            break;
          case "3":
            ctrl3.allStep();
            break;
          case "0":
            running = false;
            break;
          default:
            System.out.println("Invalid choice, Try again");
        }
      } catch (MyException e) {
        System.out.println("Error" + e.getMessage());
      }
    }
    System.out.println("See you next time :)");
  }
}
