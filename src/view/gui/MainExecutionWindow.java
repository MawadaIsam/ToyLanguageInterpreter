package view.gui;

import controller.Controller;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.ADT.MyIDict;
import model.PrgState;
import model.values.Value;

public class MainExecutionWindow {
  // ui components
  TextField nrPrgStatesField;
  TableView<HeapEntry> heapTableView;
  ListView<String> outListView;
  ListView<String> fileTableListView;
  ListView<Integer> prgStateIdsListView;
  TableView<SymTableEntry> symTableView;
  ListView<String> exeStackListView;
  Button runOneStepButton;
  private Stage stage;
  private Controller controller;

  public MainExecutionWindow(Controller controller) {
    this.controller = controller;
    this.stage = new Stage();
    initUI();
    populateUI();
  }

  private void initUI() {
    stage.setTitle("TOY LANGUAGE INTERPRETER");
    // TOP: number of program states
    nrPrgStatesField = new TextField();
    nrPrgStatesField.setEditable(false);
    nrPrgStatesField.setPrefWidth(100);
    HBox topBox = new HBox(10, new Label("Number of Program States:"), nrPrgStatesField);
    topBox.setPadding(new Insets(10));

    // left: heap table
    heapTableView = createHeapTableView();
    VBox heapBox = new VBox(5, new Label("Heap Table:"), heapTableView);

    // center-left: output and file table
    outListView = new ListView<>();
    fileTableListView = new ListView<>();
    VBox outBox = new VBox(5, new Label("Output:"), outListView);
    VBox fileBox = new VBox(5, new Label("File Table:"), fileTableListView);
    VBox centerLeftBox = new VBox(10, outBox, fileBox);

    // center_right: prgState IDs
    prgStateIdsListView = new ListView<>();
    prgStateIdsListView
        .getSelectionModel()
        .selectedItemProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              if (newValue != null) {
                updatePrgStateDetails(newValue);
              }
            });
    VBox idsBox = new VBox(5, new Label("Program State IDs:"), prgStateIdsListView);

    // right side : symtable and exestack
    symTableView = createSymTableView();
    exeStackListView = new ListView<>();
    VBox symTableBox = new VBox(5, new Label("Symbol Table:"), symTableView);
    VBox exeStackBox = new VBox(5, new Label("Execution Stack:"), exeStackListView);
    VBox rightBox = new VBox(10, symTableBox, exeStackBox);

    // bottom: Run one-step button
    runOneStepButton = new Button("Run One Step");
    runOneStepButton.setOnAction(event -> runOneStep());
    HBox bottomBox = new HBox(runOneStepButton);
    bottomBox.setPadding(new Insets(10));
    bottomBox.setAlignment(javafx.geometry.Pos.CENTER);

    // main layout
    GridPane mainGrid = new GridPane();
    mainGrid.setHgap(10);
    mainGrid.setVgap(10);
    mainGrid.setPadding(new Insets(10));

    // column 0: heap
    mainGrid.add(heapBox, 0, 0);
    GridPane.setRowSpan(heapBox, 2);

    // column 1: output and file table
    mainGrid.add(centerLeftBox, 1, 0);
    GridPane.setRowSpan(centerLeftBox, 2);

    // column 2: prgstate ids
    mainGrid.add(idsBox, 2, 0);

    // column 3: symtable and execution stack
    mainGrid.add(rightBox, 3, 0);
    GridPane.setRowSpan(rightBox, 2);

    // setting column constraints
    ColumnConstraints col0 = new ColumnConstraints();
    col0.setPercentWidth(25);
    ColumnConstraints col1 = new ColumnConstraints();
    col1.setPercentWidth(20);
    ColumnConstraints col2 = new ColumnConstraints();
    col2.setPercentWidth(15);
    ColumnConstraints col3 = new ColumnConstraints();
    col3.setPercentWidth(40);
    mainGrid.getColumnConstraints().addAll(col0, col1, col2, col3);

    // root layout
    BorderPane root = new BorderPane();
    root.setTop(topBox);
    root.setCenter(mainGrid);
    root.setBottom(bottomBox);

    Scene scene = new Scene(root, 1200, 700);
    stage.setScene(scene);
  }

  private TableView<HeapEntry> createHeapTableView() {
    TableView<HeapEntry> table = new TableView<>();

    TableColumn<HeapEntry, Integer> addressCol = new TableColumn<>("Address");
    addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
    addressCol.setPrefWidth(80);

    TableColumn<HeapEntry, String> valueCol = new TableColumn<>("Value");
    valueCol.setCellValueFactory(new PropertyValueFactory<>("value"));
    valueCol.setPrefWidth(150);

    table.getColumns().addAll(addressCol, valueCol);
    table.setPrefHeight(300);

    return table;
  }

  private TableView<SymTableEntry> createSymTableView() {
    TableView<SymTableEntry> table = new TableView<>();

    TableColumn<SymTableEntry, String> varNameCol = new TableColumn<>("Variable Name");
    varNameCol.setCellValueFactory(new PropertyValueFactory<>("varName"));
    varNameCol.setPrefWidth(120);

    TableColumn<SymTableEntry, String> valueCol = new TableColumn<>("Value");
    valueCol.setCellValueFactory(new PropertyValueFactory<>("value"));
    valueCol.setPrefWidth(150);

    table.getColumns().addAll(varNameCol, valueCol);
    table.setPrefHeight(250);

    return table;
  }

  private void populateUI() {
    updateAllComponents();
  }

  private void runOneStep() {
    try {
      // getting current program states
      List<PrgState> prgStates =
          controller.removeCompletedPrg(controller.getRepository().getPrgList());

      if (prgStates.isEmpty()) {
        showAlert("Execution Complete", " All Program States have been Executed");
        runOneStepButton.setDisable(true);
        return;
      }
      // execute one step for all program states
      controller.oneStepForAllPrg(prgStates);

      // update ui
      updateAllComponents();
    } catch (InterruptedException e) {
      showAlert("Execution Error", "Error during Execution:" + e.getMessage());
    }
  }

  private void updateAllComponents() {
    List<PrgState> prgStates = controller.getRepository().getPrgList();

    // updating no of prgstates
    nrPrgStatesField.setText(String.valueOf(prgStates.size()));

    // updating heap table
    if (!prgStates.isEmpty()) {
      updateHeapTable(prgStates.get(0));
      updateOutList(prgStates.get(0));
      updateFileTableList(prgStates.get(0));
    }

    // update program state ids
    updatePrgStateIdsList(prgStates);

    // update selected program state details
    Integer selectedId = prgStateIdsListView.getSelectionModel().getSelectedItem();
    if (selectedId != null) {
      updatePrgStateDetails(selectedId);
    } else if (!prgStates.isEmpty()) {
      prgStateIdsListView.getSelectionModel().selectFirst();
    }
  }

  private void updateHeapTable(PrgState prgState) {
    Map<Integer, Value> heap = prgState.getHeap().getContent();
    ObservableList<HeapEntry> heapEntries = FXCollections.observableArrayList();

    for (Map.Entry<Integer, Value> entry : heap.entrySet()) {
      heapEntries.add(new HeapEntry(entry.getKey(), entry.getValue().toString()));
    }

    heapTableView.setItems(heapEntries);
  }

  private void updateOutList(PrgState prgState) {
    ObservableList<String> outItems =
        FXCollections.observableArrayList(
            prgState.getOut().getAll().stream().map(Value::toString).collect(Collectors.toList()));
    outListView.setItems(outItems);
  }

  private void updateFileTableList(PrgState prgState) {
    ObservableList<String> fileItems =
        FXCollections.observableArrayList(
            prgState.getFileTable().getKeys().stream()
                .map(Object::toString)
                .collect(Collectors.toList()));
    fileTableListView.setItems(fileItems);
  }

  private void updatePrgStateIdsList(List<PrgState> prgStates) {
    ObservableList<Integer> ids =
        FXCollections.observableArrayList(
            prgStates.stream().map(PrgState::getId).collect(Collectors.toList()));
    prgStateIdsListView.setItems(ids);
  }

  private void updatePrgStateDetails(Integer prgStateId) {
    List<PrgState> prgStates = controller.getRepository().getPrgList();

    PrgState selectedPrgState =
        prgStates.stream().filter(p -> p.getId() == prgStateId).findFirst().orElse(null);

    if (selectedPrgState != null) {
      updateSymTable(selectedPrgState);
      updateExeStack(selectedPrgState);
    }
  }

  private void updateSymTable(PrgState prgState) {
    MyIDict<String, Value> symTable = prgState.getSymTable();
    ObservableList<SymTableEntry> symTableEntries = FXCollections.observableArrayList();

    for (String key : symTable.getKeys()) {
      Value value = symTable.find(key);
      symTableEntries.add(new SymTableEntry(key, value.toString()));
    }

    symTableView.setItems(symTableEntries);
  }

  private void updateExeStack(PrgState prgState) {
    ObservableList<String> stackItems =
        FXCollections.observableArrayList(
            prgState.getStk().getAll().stream().map(Object::toString).collect(Collectors.toList()));
    exeStackListView.setItems(stackItems);
  }

  private void showAlert(String title, String message) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }

  public void show() {
    stage.show();
  }

  // inner classes for table view entries
  public static class HeapEntry {
    private final SimpleIntegerProperty address;
    private final SimpleStringProperty value;

    public HeapEntry(int address, String value) {
      this.address = new SimpleIntegerProperty(address);
      this.value = new SimpleStringProperty(value);
    }

    public int getAddress() {
      return address.get();
    }

    public String getValue() {
      return value.get();
    }
  }

  public static class SymTableEntry {
    private final SimpleStringProperty varName;
    private final SimpleStringProperty value;

    public SymTableEntry(String varName, String value) {
      this.varName = new SimpleStringProperty(varName);
      this.value = new SimpleStringProperty(value);
    }

    public String getVarName() {
      return varName.get();
    }

    public String getValue() {
      return value.get();
    }
  }
}
