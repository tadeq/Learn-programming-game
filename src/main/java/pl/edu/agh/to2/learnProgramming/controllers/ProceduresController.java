package pl.edu.agh.to2.learnProgramming.controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import pl.edu.agh.to2.learnProgramming.command.*;
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
    private ScrollPane selectedCommandsPane;

    @FXML
    private ListView<Procedure> proceduresList;

    private ObservableList<Procedure> procedures;

    private Procedure currentProcedure;

    private List<Loop> loops;

    private LoopManager loopManager;

    private CommandBarController commandBarController;

    private MainScreenController mainScreenController;

    private LevelController levelController;

    @FXML
    public void initialize() {
        setButtonsVisibility(false);
        commandBarController = new CommandBarController(selectedCommandsPane);
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
            commandBarController.initializeMovesList();
            if (newValue != null) {
                loopManager = new LoopManager();
                commandBarController.setLoopManager(loopManager);
                addButton.setVisible(false);
                currentProcedure = newValue;
                setButtonsVisibility(true);
                List<Command> newCommands = newValue.getCommands();
                for (Command command : newCommands) {
                    commandBarController.addCommand(command);
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
        if (loopManager.loopsGood(commandBarController.getCommands())) {
            setButtonsVisibility(false);
            currentProcedure.getCommands().clear();
            for (Command command : commandBarController.getCommands()) {
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
        commandBarController.addCommand(new MoveForwardCommand(loops));
    }

    @FXML
    public void rightClicked(ActionEvent actionEvent) {
        commandBarController.addCommand(new TurnRightCommand(loops));
    }

    @FXML
    public void leftClicked(ActionEvent actionEvent) {
        commandBarController.addCommand(new TurnLeftCommand(loops));
    }

    @FXML
    public void startLoopClicked(ActionEvent actionEvent) {
        if (loopManager.openLoop())
            commandBarController.addCommand(new StartLoopCommand(levelController.getLevel().getLoops(), loopManager.getLoopsRepeatList()));
    }

    @FXML
    public void endLoopClicked(ActionEvent actionEvent) {
        if (loopManager.closeLoop())
            commandBarController.addCommand(new EndLoopCommand(levelController.getLevel().getLoops(), loopManager.getLoopsRepeatList()));
    }
}
