package view.gui;

import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.stage.Stage;
import model.exp.*;
import model.statements.*;
import model.types.BoolType;
import model.types.IntType;
import model.types.RefType;
import model.types.StringType;
import model.values.BoolValue;
import model.values.IntValue;
import model.values.StringValue;

public class Main extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) {
    // creating list of all example programs
    List<Stmt> programs = createProgramList();

    // showing program selection window
    ProgramSelectionWindow selectionWindow = new ProgramSelectionWindow(programs);
    selectionWindow.show();
  }

  // creating all example programs
  private List<Stmt> createProgramList() {
    List<Stmt> programs = new ArrayList<>();

    programs.add(
        new CompStmt(
            new VarDeclStmt("v", new IntType()),
            new CompStmt(
                new AssignStmt("v", new ValueExp(new IntValue(2))),
                new PrintStmt(new VarExp("v")))));

    programs.add(
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
                        new PrintStmt(new VarExp("b")))))));

    programs.add(
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
                        new PrintStmt(new VarExp("v")))))));

    programs.add(
        new CompStmt(
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
                                        new CloseRFile(new VarExp("varf")))))))))));

    programs.add(
        new CompStmt(
            new VarDeclStmt("x", new IntType()),
            new CompStmt(
                new AssignStmt("x", new ValueExp(new IntValue(10))),
                new CompStmt(
                    new IfStmt(
                        new RelExp(new VarExp("x"), new ValueExp(new IntValue(5)), ">"),
                        new CompStmt(new PrintStmt(new VarExp("x")), new NopStmt()),
                        new PrintStmt(new ValueExp(new IntValue(0)))),
                    new PrintStmt(new ValueExp(new IntValue(100)))))));

    programs.add(
        new CompStmt(
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
                                    1))))))));

    programs.add(
        new CompStmt(
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
                                1)))))));

    programs.add(
        new CompStmt(
            new VarDeclStmt("v", new RefType(new IntType())),
            new CompStmt(
                new NewStmt("v", new ValueExp(new IntValue(20))),
                new CompStmt(
                    new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                    new CompStmt(
                        new NewStmt("a", new VarExp("v")),
                        new CompStmt(
                            new NewStmt("v", new ValueExp(new IntValue(30))),
                            new PrintStmt(new ReadHeapExp(new ReadHeapExp(new VarExp("a"))))))))));

    programs.add(
        new CompStmt(
            new VarDeclStmt("v", new IntType()),
            new CompStmt(
                new AssignStmt("v", new ValueExp(new IntValue(4))),
                new CompStmt(
                    new WhileStmt(
                        new RelExp(new VarExp("v"), new ValueExp(new IntValue(0)), ">"),
                        new CompStmt(
                            new PrintStmt(new VarExp("v")),
                            new AssignStmt(
                                "v",
                                new ArithExp(new VarExp("v"), new ValueExp(new IntValue(1)), 2)))),
                    new PrintStmt(new VarExp("v"))))));

    // assigning boolean to integer variable
    programs.add(
        new CompStmt(
            new VarDeclStmt("v", new IntType()),
            new AssignStmt("v", new ValueExp(new BoolValue(true)))));

    // using undeclared variable
    programs.add(new CompStmt(new VarDeclStmt("v", new IntType()), new PrintStmt(new VarExp("x"))));

    programs.add(
        new CompStmt(
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
                                new PrintStmt(new ReadHeapExp(new VarExp("a"))))))))));
    Stmt fork =
        new ForkStmt(
            new CompStmt(
                new PrintStmt(new VarExp("v")),
                new CompStmt(
                    new AssignStmt(
                        "x", new ArithExp(new VarExp("x"), new ValueExp(new IntValue(1)), 2)),
                    new PrintStmt(new VarExp("x")))));

    programs.add(
        new CompStmt(
            new VarDeclStmt("v", new IntType()),
            new CompStmt(
                new VarDeclStmt("x", new IntType()),
                new CompStmt(
                    new AssignStmt("v", new ValueExp(new IntValue(3))),
                    new CompStmt(
                        new AssignStmt("x", new ValueExp(new IntValue(2))),
                        new CompStmt(
                            new RepeatStmt(
                                new CompStmt(
                                    fork,
                                    new AssignStmt(
                                        "v",
                                        new ArithExp(
                                            new VarExp("v"), new ValueExp(new IntValue(1)), 1))),
                                new RelExp(new VarExp("v"), new ValueExp(new IntValue(0)), "==")),
                            new CompStmt(
                                new NopStmt(),
                                new CompStmt(
                                    new NopStmt(),
                                    new CompStmt(
                                        new NopStmt(),
                                        new CompStmt(
                                            new NopStmt(),
                                            new CompStmt(
                                                new NopStmt(),
                                                new CompStmt(
                                                    new NopStmt(),
                                                    new CompStmt(
                                                        new NopStmt(),
                                                        new PrintStmt(new VarExp("x")))))))))))))));

    Stmt fork1 =
        new ForkStmt(
            new CompStmt(
                new AwaitStmt("id"),
                new CompStmt(
                    new WriteHeapStmt(
                        "v1",
                        new ArithExp(
                            new ReadHeapExp(new VarExp("v1")), new ValueExp(new IntValue(10)), 3)),
                    new CompStmt(
                        new NopStmt(),
                        new CompStmt(
                            new NopStmt(), new PrintStmt(new ReadHeapExp(new VarExp("v1"))))))));

    Stmt fork2 =
        new ForkStmt(
            new CompStmt(
                new AwaitStmt("id"),
                new CompStmt(
                    new WriteHeapStmt(
                        "v2",
                        new ArithExp(
                            new ReadHeapExp(new VarExp("v2")), new ValueExp(new IntValue(10)), 3)),
                    new CompStmt(
                        new NopStmt(),
                        new CompStmt(
                            new NopStmt(),
                            new CompStmt(
                                new NopStmt(),
                                new CompStmt(
                                    new WriteHeapStmt(
                                        "v2",
                                        new ArithExp(
                                            new ReadHeapExp(new VarExp("v2")),
                                            new ValueExp(new IntValue(10)),
                                            3)),
                                    new CompStmt(
                                        new WriteHeapStmt(
                                            "v2",
                                            new ArithExp(
                                                new ReadHeapExp(new VarExp("v2")),
                                                new ValueExp(new IntValue(10)),
                                                3)),
                                        new PrintStmt(new ReadHeapExp(new VarExp("v2")))))))))));

    Stmt fork3 =
        new ForkStmt(
            new CompStmt(
                new AwaitStmt("id"),
                new PrintStmt(
                    new ArithExp(
                        new ReadHeapExp(new VarExp("v3")), new ValueExp(new IntValue(40)), 3))));

    programs.add(
        new CompStmt(
            new VarDeclStmt("v1", new RefType(new IntType())),
            new CompStmt(
                new VarDeclStmt("v2", new RefType(new IntType())),
                new CompStmt(
                    new VarDeclStmt("v3", new RefType(new IntType())),
                    new CompStmt(
                        new VarDeclStmt("id", new IntType()),
                        new CompStmt(
                            new AssignStmt("id", new ValueExp(new IntValue(5))),
                            new CompStmt(
                                new NewStmt("v1", new ValueExp(new IntValue(2))),
                                new CompStmt(
                                    new NewStmt("v2", new ValueExp(new IntValue(3))),
                                    new CompStmt(
                                        new NewStmt("v3", new ValueExp(new IntValue(4))),
                                        new CompStmt(
                                            new NewBarrierStmt(
                                                "id", new ReadHeapExp(new VarExp("v2"))),
                                            new CompStmt(
                                                fork1,
                                                new CompStmt(
                                                    fork2,
                                                    new CompStmt(
                                                        fork3,
                                                        new PrintStmt(
                                                            new ReadHeapExp(
                                                                new VarExp("v3"))))))))))))))));

    return programs;
  }
}
