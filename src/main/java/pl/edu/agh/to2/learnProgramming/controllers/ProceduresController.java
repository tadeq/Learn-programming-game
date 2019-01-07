package pl.edu.agh.to2.learnProgramming.controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import pl.edu.agh.to2.learnProgramming.command.*;
import pl.edu.agh.to2.learnProgramming.model.Level;
import pl.edu.agh.to2.learnProgramming.model.Loop;
import pl.edu.agh.to2.learnProgramming.model.Procedure;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class ProceduresController {
    @FXML
    private Button forwardButton;

    @FXML
    private Button rightButton;

    @FXML
    private Button leftButton;

    @FXML
    private Button startLoopButton;

    @FXML
    private Button endLoopButton;

    @FXML
    private Button addButton;

    @FXML
    private Button saveButton;

    @FXML
    private Button useButton;

    @FXML
    private Button deleteButton;

    @FXML
    public ListView<Procedure> proceduresList;

    @FXML
    public ScrollPane selectedCommandsPane;

    private HBox commands;

    private ObservableList<Procedure> procedures;

    private Procedure currentProcedure;

    private List<Command> currentCommands;

    private List<Loop> loops;

    private LoopManager loopManager;

    private MainScreenController mainScreenController;

    private LevelController levelController;

    @FXML
    public void initialize() {
        setButtonsVisibility(false);
        loopManager = new LoopManager();
        currentCommands = new LinkedList<>();
        forwardButton.setTooltip(new Tooltip("Move forward"));
        rightButton.setTooltip(new Tooltip("Turn Right"));
        leftButton.setTooltip(new Tooltip("Turn left"));
        startLoopButton.setTooltip(new Tooltip("Start loop"));
        endLoopButton.setTooltip(new Tooltip("End loop"));
        saveButton.setTooltip(new Tooltip("Save procedure"));
        addButton.setTooltip(new Tooltip("Add procedure"));
        useButton.setTooltip(new Tooltip("Use procedure"));
        deleteButton.setTooltip(new Tooltip("Delete procedure"));
        proceduresList.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
            initializeMovesList();
            if (newValue != null) {
                addButton.setVisible(false);
                currentProcedure = newValue;
                setButtonsVisibility(true);
                List<Command> commands = newValue.getCommands();
                for (Command command : commands) {
                    addCommand(command);
                }
            } else {
                addButton.setVisible(true);
            }
        }));
    }

    public void setMainScreenController(MainScreenController mainScreenController) {
        this.mainScreenController = mainScreenController;
    }

    public void setLevelController(LevelController levelController) {
        this.levelController = levelController;
    }

    public void setProcedures(ObservableList<Procedure> procedures) {
        this.procedures = procedures;
        this.proceduresList.setItems(this.procedures);
    }

    public void setLoops(List<Loop> loops) {
        this.loops = loops;
    }

    public LoopManager getLoopManager() {
        return loopManager;
    }

    private void setButtonsVisibility(boolean visible) {
        forwardButton.setVisible(visible);
        rightButton.setVisible(visible);
        leftButton.setVisible(visible);
        startLoopButton.setVisible(visible);
        endLoopButton.setVisible(visible);
        saveButton.setVisible(visible);
        deleteButton.setVisible(visible);
        useButton.setVisible(visible);
    }

    @FXML
    public void addClicked(ActionEvent actionEvent) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("New procedure");
        dialog.setHeaderText("Procedure name:");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            if (procedures.stream().anyMatch(procedure -> procedure.getName().equals(result.get()))) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("A procedure with the same name is already on the list");
                alert.showAndWait();
            } else {
                Procedure procedure = new Procedure(result.get());
                procedures.add(procedure);
                proceduresList.setItems(procedures);
                currentProcedure = procedure;
                proceduresList.getSelectionModel().select(procedure);
                setButtonsVisibility(true);
                useButton.setVisible(false);
            }
        }
    }

    @FXML
    public void saveClicked(ActionEvent actionEvent) {
        if (loopManager.loopsGood(currentCommands)) {
            setButtonsVisibility(false);
            currentProcedure.getCommands().clear();
            for (Command command : currentCommands) {
                currentProcedure.addCommand(command);
            }
            currentProcedure = null;
            proceduresList.getSelectionModel().clearSelection();
        }
    }

    @FXML
    public void deleteClicked(ActionEvent actionEvent) {
        setButtonsVisibility(false);
        procedures.remove(proceduresList.getSelectionModel().getSelectedIndex());
        proceduresList.setItems(procedures);
        proceduresList.getSelectionModel().clearSelection();
    }

    @FXML
    public void useClicked(ActionEvent actionEvent) {
        mainScreenController.addCommand(new ProcedureCommand(currentProcedure.getName(), currentProcedure.getCommands(), loops));
        ((Stage) useButton.getScene().getWindow()).close();
    }

    @FXML
    public void forwardClicked(ActionEvent actionEvent) {
        addCommand(new MoveForwardCommand(loops));
    }

    @FXML
    public void rightClicked(ActionEvent actionEvent) {
        addCommand(new TurnRightCommand(loops));
    }

    @FXML
    public void leftClicked(ActionEvent actionEvent) {
        addCommand(new TurnLeftCommand(loops));
    }

    @FXML
    public void startLoopClicked(ActionEvent actionEvent) {
        if (loopManager.openLoop())
            addCommand(new StartLoopCommand(levelController.getLevel().getLoops(), loopManager.getLoopsRepeatList()));
    }

    @FXML
    public void endLoopClicked(ActionEvent actionEvent) {
        if (loopManager.closeLoop())
            addCommand(new EndLoopCommand(levelController.getLevel().getLoops(), loopManager.getLoopsRepeatList()));
    }


    private void initializeMovesList() {
        currentCommands.clear();
        commands = new HBox();
        commands.setSpacing(10);
        this.selectedCommandsPane.setContent(commands);
    }

    public void addCommand(Command command) {
        Node img = command.getImage();
        img.setOnMouseClicked(this::removeSelectedMove);
        commands.getChildren().add(img);
        this.selectedCommandsPane.setContent(commands);
        currentCommands.add(command);
    }

    private void removeSelectedMove(MouseEvent mouseEvent) {
        int index = this.commands.getChildren().indexOf(mouseEvent.getSource());
        Command command = currentCommands.get(index);
        if (command.isLoop()) {
            ((LoopCommand) command).onRemove(index, levelController, currentCommands);
        }
        currentCommands.remove(index);
        this.commands.getChildren().remove(mouseEvent.getSource());
    }
}
