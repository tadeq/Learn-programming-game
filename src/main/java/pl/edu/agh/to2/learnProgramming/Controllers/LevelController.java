package pl.edu.agh.to2.learnProgramming.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import pl.edu.agh.to2.learnProgramming.Model.ButtonType;
import pl.edu.agh.to2.learnProgramming.Model.Level;
import pl.edu.agh.to2.learnProgramming.Model.Turtle;
import pl.edu.agh.to2.learnProgramming.Model.TurtleDirection;
import pl.edu.agh.to2.learnProgramming.Utilities.LevelPoint;

import java.awt.*;
import java.util.Arrays;
import java.util.List;


public class LevelController {
    @FXML
    private ImageView turtleImage;

    @FXML
    private GridPane board;

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

    private MainScreenController mainScreenController;

    private Level level;

    public void setMainScreenController(MainScreenController mainScreenController) {
        this.mainScreenController = mainScreenController;
    }

    @FXML
    public void initialize() {
    }

    public void initializeLevel() {
        board.setStyle("-fx-background-color: skyblue; -fx-border-color: blue");

        // tu level będzie odczytywał currentLevel z mainScreenControllera i przekazywał go LevelGeneratorowi
        List<ButtonType> buttonTypes = Arrays.asList(ButtonType.FORWARD, ButtonType.RIGHT, ButtonType.LEFT);
        List<LevelPoint> squares = Arrays.asList(new LevelPoint(2, 2), new LevelPoint(0, 1), new LevelPoint(1, 1), new LevelPoint(2, 1));
        Turtle turtlePosition = new Turtle(0, 1, TurtleDirection.E);
        setTurtleImagePosition(turtlePosition.getCoordinates(), turtlePosition.getTurtleDirection());
        level = new Level(buttonTypes, squares, turtlePosition);

        level.setLevelController(this);
        for (Point p : level.getFieldCoordinates()) {
            Pane pane = new Pane();
            pane.setStyle("-fx-background-color: darkolivegreen; -fx-border-color: darkgreen");
            board.add(pane, p.x, p.y);

        }
        turtleImage.toFront();
        forwardButton.setVisible(level.getButtonTypes().contains(ButtonType.FORWARD));
        rightButton.setVisible(level.getButtonTypes().contains(ButtonType.RIGHT));
        leftButton.setVisible(level.getButtonTypes().contains(ButtonType.LEFT));
        startLoopButton.setVisible(level.getButtonTypes().contains(ButtonType.STARTLOOP));
        endLoopButton.setVisible(level.getButtonTypes().contains(ButtonType.ENDLOOP));
    }

    public void setTurtleImagePosition(Point turtleCoords, TurtleDirection direction) {
        GridPane.setColumnIndex(turtleImage, turtleCoords.x);
        GridPane.setRowIndex(turtleImage, turtleCoords.y);
        turtleImage.setRotate(direction.getRotation());
    }

    public boolean checkAndexecuteMoves(List<ButtonType> movesToExecute) {
        return this.level.executeMoves(movesToExecute) && this.level.allVisited();
        // TODO
        // turtleImage będzie poruszał się po jednym polu tak, aby można było zobaczyć poszczególne kroki
        // kolor odwiedzanych pól będzie zmieniany
    }


    @FXML
    public void forwardClicked(ActionEvent actionEvent) {
        mainScreenController.addButton(ButtonType.FORWARD);
    }

    @FXML
    public void rightClicked(ActionEvent actionEvent) {
        mainScreenController.addButton(ButtonType.RIGHT);
    }

    @FXML
    public void leftClicked(ActionEvent actionEvent) {
        mainScreenController.addButton(ButtonType.LEFT);
    }

    @FXML
    public void startLoopClicked(ActionEvent actionEvent) {
        mainScreenController.addButton(ButtonType.STARTLOOP);
    }

    @FXML
    public void endLoopClicked(ActionEvent actionEvent) {
        mainScreenController.addButton(ButtonType.ENDLOOP);
    }

}
