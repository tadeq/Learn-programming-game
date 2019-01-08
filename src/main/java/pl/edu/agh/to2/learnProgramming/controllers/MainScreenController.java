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
 * HBox commandsBox
 * <p>
 * backend:
 * LevelController levelController
 * int currentLevel
 * List<CommandType> commandsToExecute
 */

package pl.edu.agh.to2.learnProgramming.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import pl.edu.agh.to2.learnProgramming.command.Command;
import pl.edu.agh.to2.learnProgramming.model.Procedure;

import java.io.*;


public class MainScreenController {
    @FXML
    private BorderPane mainBorderPane;

    @FXML
    private Button playButton;

    @FXML
    private LevelController levelController;

    @FXML
    private VBox levelNumbersBox;

    @FXML
    private ToggleGroup levelNumbers;

    @FXML
    private ScrollPane selectedCommandsPane;

    private int currentLevel;

    private ObservableList<Procedure> procedures;

    private CommandBarController commandBarController;

    @FXML
    public void initialize() {
        commandBarController = new CommandBarController(selectedCommandsPane);
        commandBarController.setLoopManager(levelController.getLoopManager());
        mainBorderPane.setPrefWidth(1000);
        mainBorderPane.setPrefHeight(1000);
        currentLevel = 1;
        levelController.setMainScreenController(this);
        levelController.initializeLevel();
        commandBarController.initializeMovesList();
        levelNumbers = new ToggleGroup();
        addLevelNumberButton();
        levelNumbers.getToggles().get(0).setSelected(true);
        levelNumbers.selectedToggleProperty().addListener(((observable, oldValue, newValue) -> {
            if (newValue == null)
                oldValue.setSelected(true);
        }));
        procedures = FXCollections.observableArrayList();
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
     * Adds command do commandsToExecute and sets it on the view.
     *
     * @param command - (Enum) CommandType
     */

    public void addCommand(Command command) {
        commandBarController.addCommand(command);
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
        commandBarController.initializeMovesList();
        levelController.initializeLevel();
    }

    /**
     * Is invoked when user clicks "play" button.
     * It means, when user wants to run his commands.
     */
    @FXML
    public void playButtonClicked() {
        if (levelController.getLoopManager().loopsGood(commandBarController.getCommands()))
            this.levelController.executeMoves(commandBarController.getCommands());
    }

    public void loadLevel() {
        Alert alert;
        if (levelController.wasLevelPassed()) {
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
            alert.show();
            alert.setOnHidden(event -> {
                levelController.initializeLevel();
                commandBarController.clearCommands();
            });
        } else {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Try again");
            alert.show();
            alert.setOnHidden(event -> {
                commandBarController.clearCommands();
                commandBarController.initializeMovesList();
                levelController.initializeLevel();
            });
        }
    }

    @FXML
    public void resetClicked() {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("All progress will be deleted");
            alert.showAndWait();
            if (alert.getResult() == ButtonType.OK) {
                BufferedWriter writer = new BufferedWriter(new FileWriter(this.getClass().getResource("/configs/gamesave.txt").getPath()));
                writer.write(1);
                writer.close();
                if (levelNumbers.getToggles().size() > 1) {
                    levelNumbersBox.getChildren().remove(3, levelNumbers.getToggles().size() + 2);
                    levelNumbers.getToggles().remove(1, levelNumbers.getToggles().size());
                    currentLevel = 1;
                    levelNumbers.getToggles().get(0).setSelected(true);
                    levelController.initializeLevel();
                    commandBarController.clearCommands();
                }
            }
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error while resetting save.");
            alert.showAndWait();
        }
    }

    @FXML
    public void saveClicked() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(this.getClass().getResource("/configs/gamesave.txt").getPath()));
            writer.write(levelNumbers.getToggles().size());
            writer.close();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error while saving game.");
            alert.showAndWait();
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Game saved successfully");
        alert.showAndWait();
    }

    @FXML
    public void loadClicked() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(this.getClass().getResource("/configs/gamesave.txt").getPath()));
            int maxLevel = reader.read();
            reader.close();
            if (maxLevel > 0 && levelNumbers.getToggles().size() < maxLevel) {
                for (currentLevel = levelNumbers.getToggles().size() + 1; currentLevel < maxLevel; currentLevel++)
                    addLevelNumberButton();
                addLevelNumberButton();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("No more levels passed");
                alert.showAndWait();
            }
            levelController.initializeLevel();
            commandBarController.clearCommands();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error while reading save file.");
            alert.showAndWait();
        }
    }
}
