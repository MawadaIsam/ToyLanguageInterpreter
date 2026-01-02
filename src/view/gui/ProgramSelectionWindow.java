package view.gui;

import controller.Controller;
import exceptions.MyException;
import java.io.BufferedReader;
import java.util.List;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.ADT.*;
import model.PrgState;
import model.statements.Stmt;
import model.types.Type;
import model.values.StringValue;
import model.values.Value;
import repository.IRespository;
import repository.Repository;

public class ProgramSelectionWindow {
    private Stage stage;
    private List<Stmt> programs;

    public ProgramSelectionWindow(List<Stmt> programs) {
        this.programs = programs;
        this.stage = new Stage();
        initUI();
    }

    private void initUI() {
        stage.setTitle("Select a Program");
        //creating ListView to display programs
        ListView<String> programListView = new ListView<>();
        ObservableList<String> programDescriptions = FXCollections.observableArrayList(
                programs.stream()
                        .map(Stmt::toString)
                        .collect(Collectors.toList())
        );
        programListView.setItems(programDescriptions);

        // run button
        Button runButton = new Button("Run Selected Program");
        runButton.setOnAction(e -> {
            int selectedIndex = programListView.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                Stmt selectedProgram = programs.get(selectedIndex);
                runProgram(selectedProgram);
            }else{
                showAlert("No Selection", "Please Select a Program to Run it.");
            }
        });

        //layout
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));
        layout.getChildren().addAll(
                new Label("Select a Program to Execute:"),
                programListView,
                runButton
        );
        Scene scene = new Scene(layout,600,400);
        stage.setScene(scene);

    }
    private void runProgram(Stmt program) {
        try{
            //1 typecheck the program
            MyIDict<String, Type> typeEnv = new MyDict<>();
            program.typecheck(typeEnv);

            //2 creating program state
            MyIStack<Stmt> exeStack = new MyStack<>();
            MyIDict<String, Value> symTable= new MyDict<>();
            MyIList<Value> output = new MyList<>();
            MyIDict<StringValue, BufferedReader> fileTable= new MyDict<>();
            MyIHeap<Integer, Value> heap = new MyHeap<>();

            PrgState prgState = new PrgState(exeStack,symTable,output,fileTable,heap, program);

            // repo and controller
            IRespository respository= new Repository(prgState,"log_gui.txt");
            Controller controller= new Controller(respository);

            //4 opening main execution window
            MainExecutionWindow mainWindow = new MainExecutionWindow(controller);
            mainWindow.show();

            // close selection window
            stage.close();
        }catch (MyException e){
            showAlert("Type Check Error", "Type Checking failed: "+e.getMessage());
        }catch (Exception e){
            showAlert("Error", "Failed to run program: "+e.getMessage());
            e.printStackTrace();
        }
    }
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void show(){
        stage.show();
    }
}










