package pl.edu.agh.to2.sorryimchillin.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import pl.edu.agh.to2.sorryimchillin.Model.ButtonType;
import pl.edu.agh.to2.sorryimchillin.Model.Level;

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
        List<ButtonType> buttonTypes = Arrays.asList(ButtonType.FORWARD);
        List<Point> squares = Arrays.asList(new Point(2, 2), new Point(0, 1), new Point(1, 1));
        Point turtlePosition = new Point(1, 1);
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
        this.moves.getChildren().remove(mouseEvent.getSource());
    }

}
