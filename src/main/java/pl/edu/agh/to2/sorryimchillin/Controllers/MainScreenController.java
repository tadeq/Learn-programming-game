package pl.edu.agh.to2.sorryimchillin.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import pl.edu.agh.to2.sorryimchillin.Model.ButtonType;
import pl.edu.agh.to2.sorryimchillin.Model.Level;
import pl.edu.agh.to2.sorryimchillin.Model.Turtle;
import pl.edu.agh.to2.sorryimchillin.Model.TurtleDirection;
import pl.edu.agh.to2.sorryimchillin.Utilities.LevelPoint;

import java.awt.*;
import java.util.Arrays;
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

    private Level currentLevel;

    private HBox moves;

    private List<ButtonType> movesToExecute;

    @FXML
    void initialize() {
        movesToExecute = new LinkedList<>();
        List<ButtonType> buttonTypes = Arrays.asList(ButtonType.FORWARD, ButtonType.RIGHT, ButtonType.LEFT);
        List<LevelPoint> squares = Arrays.asList(new LevelPoint(2, 2), new LevelPoint(0, 1), new LevelPoint(1, 1), new LevelPoint(2, 1));
        Turtle turtlePosition = new Turtle(0, 1, TurtleDirection.E);
        currentLevel = new Level(buttonTypes, squares, turtlePosition);
        levelController.setMainScreenController(this);
        levelController.initializeLevel();
        moves = new HBox();
        moves.setSpacing(10);
    }

    public Level getCurrentLevel() {
        return currentLevel;
    }

    public void addButton(ButtonType type) {
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
        System.out.println(index);
        this.moves.getChildren().remove(mouseEvent.getSource());
    }

    @FXML
    public void playButtonClicked() {
        if (this.currentLevel.executeMoves(movesToExecute)) {
            Turtle turtle = this.currentLevel.getTurtle();
            levelController.setTurtlePosition(turtle.getCoordinates(), turtle.getTurtleDirection());
        } else {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.show();
        }
        this.moves.getChildren().clear();
        this.movesToExecute.clear();

    }

}
