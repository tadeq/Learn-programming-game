/**
 * @author Maciej Moskal
 * Jakub Pajor
 * Michał Zadora
 * <p>
 * Controller - main screen.
 * Contains information about:
 * frontend:
 * BorderPane mainBorderPane
 * Button playButton
 * ScrollPane selectedCommandsPane
 * VBox levelNumbersBox
 * ToggleGroup levelNumbers
 * HBox commands
 * <p>
 * backend:
 * LevelController levelController
 * int currentLevel
 * List<CommandType> movesToExecute
 */

package pl.edu.agh.to2.learnProgramming.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import pl.edu.agh.to2.learnProgramming.command.Command;
import pl.edu.agh.to2.learnProgramming.command.LoopCommand;
import pl.edu.agh.to2.learnProgramming.model.Procedure;

import java.io.*;
import java.util.LinkedList;
import java.util.List;


public class MainScreenController {
    @FXML
    private BorderPane mainBorderPane;
    @FXML
    private Button playButton;
    @FXML
    private LevelController levelController;
    @FXML
    private ProceduresController proceduresController;
    @FXML
    private ScrollPane selectedCommandsPane;
    @FXML
    private VBox levelNumbersBox;
    @FXML
    private ToggleGroup levelNumbers;

    private int currentLevel;

    private HBox commands;

    private List<Command> commandsToExecute;

    private ObservableList<Procedure> procedures;

    @FXML
    public void initialize() {
        commandsToExecute = new LinkedList<>();
        mainBorderPane.setPrefWidth(1000);
        mainBorderPane.setPrefHeight(1000);
        currentLevel = 1;
        levelController.setMainScreenController(this);
        levelController.initializeLevel();
        initializeMovesList();
        levelNumbers = new ToggleGroup();
        addLevelNumberButton();
        levelNumbers.getToggles().get(0).setSelected(true);
        levelNumbers.selectedToggleProperty().addListener(((observable, oldValue, newValue) -> {
            if (newValue == null)
                oldValue.setSelected(true);
        }));
        procedures = FXCollections.observableArrayList();
    }

    /**
     * Initialize a HBox for commands.
     */
    private void initializeMovesList() {
        commandsToExecute.clear();
        commands = new HBox();
        commands.setSpacing(10);
        this.selectedCommandsPane.setContent(commands);
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public BorderPane getMainBorderPane() {
        return mainBorderPane;
    }

    public ObservableList<Procedure> getProcedures() {
        return this.procedures;
    }



    /**
     * Adds command do movesToExecute and sets it on the view.
     *
     * @param command - (Enum) CommandType
     */
    public void addCommand(Command command) {
        Node img = command.getImage();
        img.setOnMouseClicked(this::removeSelectedMove);
        commands.getChildren().add(img);
        this.selectedCommandsPane.setContent(commands);
        this.commandsToExecute.add(command);
    }

    /**
     * When user clicks on a previously selected command
     * then the action is delivered and the command is removed.
     *
     * @param mouseEvent - MouseEvent
     */
    private void removeSelectedMove(MouseEvent mouseEvent) {
        int index = this.commands.getChildren().indexOf(mouseEvent.getSource());
        Command command = commandsToExecute.get(index);
        if (command.isLoop()) {
            ((LoopCommand) command).onRemove(index, levelController, commandsToExecute);
        }
        commandsToExecute.remove(index);
        this.commands.getChildren().remove(mouseEvent.getSource());
    }

    /**
     * Adds level number button on right VBox in the view.
     */
    private void addLevelNumberButton() {
        ToggleButton button = new ToggleButton(Integer.toString(currentLevel));
        button.setPrefHeight(26);
        button.setPrefWidth(50);
        button.setSelected(true);
        button.setOnAction(this::levelChosen);
        levelNumbersBox.getChildren().add(button);
        levelNumbers.getToggles().add(button);
    }

    /**
     * Is invoked when user selects other available level.
     *
     * @param actionEvent
     */
    private void levelChosen(ActionEvent actionEvent) {
        currentLevel = Integer.parseInt(((ToggleButton) actionEvent.getSource()).getText());
        initializeMovesList();
        levelController.initializeLevel();
    }

    /**
     * Is invoked when user clicks "play" button.
     * It means, when user wants to run his commands.
     */
    @FXML
    public void playButtonClicked() {
        Alert alert;
        if (levelController.getLoopManager().loopsGood(commandsToExecute)) {
            if (this.levelController.checkAndExecuteMoves(commandsToExecute)) {
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Level passed");
                if (levelController.getLevelGenerator().hasNext())
                    currentLevel++;
                else
                    alert.setHeaderText(alert.getHeaderText() + "\nNo more levels available.");
                if (levelNumbers.getToggles().size() < currentLevel)
                    addLevelNumberButton();
                else
                    levelNumbers.getToggles().get(currentLevel - 1).setSelected(true);
                alert.showAndWait();
                levelController.initializeLevel();
            } else {
                this.commands.getChildren().clear();
                this.commandsToExecute.clear();
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Try again");
                alert.showAndWait();
                initializeMovesList();
                levelController.initializeLevel();
            }
            this.commands.getChildren().clear();
            this.commandsToExecute.clear();
        }
    }

    public void resetSaveClicked(){
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("./resources/config/gamesave"));
            writer.write(1);
            writer.close();

            if(currentLevel > 1){
                levelNumbersBox.getChildren().remove(1,levelNumbers.getToggles().size());
                levelNumbers.getToggles().remove(1, levelNumbers.getToggles().size());
            }

        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error while saving game.");
            alert.showAndWait();
        }
    }

    public void saveClicked(){
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("./resources/config/gamesave"));
            writer.write(levelNumbers.getToggles().size());
            writer.close();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error while saving game.");
            alert.showAndWait();
        }
    }

    public void loadLevelsClicked(){
        try {
            BufferedReader reader = new BufferedReader(new FileReader("./resources/config/gamesave"));
            int maxLevel = reader.read();
            reader.close();

            if(maxLevel > 0 && currentLevel < maxLevel){
                for(int i = currentLevel+1; i < maxLevel; i++){
                    ToggleButton button = new ToggleButton(Integer.toString(i));
                    button.setPrefHeight(26);
                    button.setPrefWidth(50);
                    button.setSelected(true);
                    //button.setOnAction(this::levelChosen);
                    levelNumbersBox.getChildren().add(button);
                    levelNumbers.getToggles().add(button);
                }
            }

        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error while reading save file.");
            alert.showAndWait();
        }
    }

}
