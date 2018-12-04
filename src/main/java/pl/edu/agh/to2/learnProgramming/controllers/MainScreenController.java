package pl.edu.agh.to2.learnProgramming.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import pl.edu.agh.to2.learnProgramming.model.MoveType;

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

    private int currentLevel;

    private HBox moves;

    private List<MoveType> movesToExecute;

    @FXML
    void initialize() {
        currentLevel = 1;
        movesToExecute = new LinkedList<>();
        moves = new HBox();
        moves.setSpacing(10);
        levelController.setMainScreenController(this);
        levelController.initializeLevel();
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void addButton(MoveType type) {
        ImageView img = new ImageView(type.getPath());
        img.setFitHeight(40);
        img.setFitWidth(40);
        img.setOnMouseClicked(this::removeSelectedMove);
        moves.getChildren().add(img);
        this.selectedMovesPane.setContent(moves);
        this.movesToExecute.add(type);
    }

    public void removeSelectedMove(MouseEvent mouseEvent) {
        int index = this.moves.getChildren().indexOf(mouseEvent.getSource());
        this.movesToExecute.remove(index);
        this.moves.getChildren().remove(mouseEvent.getSource());
    }

    @FXML
    public void playButtonClicked() {
        Alert alert;
        if (this.levelController.checkAndExecuteMoves(movesToExecute)) {
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Level passed");
            alert.show();
            currentLevel++;
            levelController.initializeLevel();
            // TODO
            // zakończenie poziomu i przejście do kolejnego
        } else {
            this.moves.getChildren().clear();
            this.movesToExecute.clear();
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Try again");
            alert.show();
            initialize();
            levelController.initialize();
        }
        this.moves.getChildren().clear();
        this.movesToExecute.clear();
    }

    public BorderPane getMainBorderPane() {
        return mainBorderPane;
    }
}
