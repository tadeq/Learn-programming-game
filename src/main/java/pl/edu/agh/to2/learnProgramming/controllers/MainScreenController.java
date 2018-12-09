package pl.edu.agh.to2.learnProgramming.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import pl.edu.agh.to2.learnProgramming.model.CommandType;

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
    private ScrollPane selectedMovesPane;
    @FXML
    private VBox levelNumbersBox;
    @FXML
    private ToggleGroup levelNumbers;

    private int currentLevel;

    private HBox moves;

    private List<CommandType> movesToExecute;

    @FXML
    public void initialize() {
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
    }

    private void initializeMovesList() {
        movesToExecute = new LinkedList<>();
        moves = new HBox();
        moves.setSpacing(10);
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public BorderPane getMainBorderPane() {
        return mainBorderPane;
    }

    public void addButton(CommandType type) {
        ImageView img = new ImageView(type.getPath());
        img.setFitHeight(40);
        img.setFitWidth(40);
        img.setOnMouseClicked(this::removeSelectedMove);
        moves.getChildren().add(img);
        this.selectedMovesPane.setContent(moves);
        this.movesToExecute.add(type);
    }

    private void removeSelectedMove(MouseEvent mouseEvent) {
        int index = this.moves.getChildren().indexOf(mouseEvent.getSource());
        CommandType commandType = movesToExecute.get(index);
        if (commandType.equals(CommandType.ENDLOOP))
            levelController.incLoopsOpended();
        else if (commandType.equals(CommandType.STARTLOOP))
            levelController.decLoopsOpened();
        movesToExecute.remove(index);
        this.moves.getChildren().remove(mouseEvent.getSource());
    }

    private void addLevelNumberButton() {
        ToggleButton button = new ToggleButton(Integer.toString(currentLevel));
        button.setPrefHeight(26);
        button.setPrefWidth(50);
        button.setSelected(true);
        button.setOnAction(this::levelChosen);
        levelNumbersBox.getChildren().add(button);
        levelNumbers.getToggles().add(button);
    }

    private void levelChosen(ActionEvent actionEvent) {
        currentLevel = Integer.parseInt(((ToggleButton) actionEvent.getSource()).getText());
        levelController.initializeLevel();
    }

    @FXML
    public void playButtonClicked() {
        Alert alert;
        if (levelController.getLoopsOpened() != 0) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Number of startLoop has to be equal to number of endLoop. Check your program.");
            alert.show();
        } else {
            if (this.levelController.checkAndExecuteMoves(movesToExecute)) {
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
                this.moves.getChildren().clear();
                this.movesToExecute.clear();
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Try again");
                alert.showAndWait();
                initializeMovesList();
                levelController.initializeLevel();
            }
            this.moves.getChildren().clear();
            this.movesToExecute.clear();
        }
    }

}
