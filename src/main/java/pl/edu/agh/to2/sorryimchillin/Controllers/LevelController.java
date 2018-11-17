package pl.edu.agh.to2.sorryimchillin.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import pl.edu.agh.to2.sorryimchillin.Model.ButtonType;
import pl.edu.agh.to2.sorryimchillin.Model.Level;
import pl.edu.agh.to2.sorryimchillin.Model.Turtle;

import java.awt.*;


public class LevelController {
    @FXML
    private ImageView turtle;

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

    public void setMainScreenController(MainScreenController mainScreenController) {
        this.mainScreenController = mainScreenController;
    }

    @FXML
    public void initialize() {
    }

    public void initializeLevel() {
        board.setStyle("-fx-background-color: skyblue; -fx-border-color: blue");
        Level level = mainScreenController.getCurrentLevel();
        for (Point p : level.getCoordinates()) {
            Pane pane = new Pane();
            pane.setStyle("-fx-background-color: darkolivegreen; -fx-border-color: darkgreen");
            board.add(pane, p.x, p.y);

        }
        //board.add(turtle, level.getTurtle().getCoordinates().x, level.getTurtle().getCoordinates().y);
        turtle.toFront();
        forwardButton.setVisible(level.getButtonTypes().contains(ButtonType.FORWARD));
        rightButton.setVisible(level.getButtonTypes().contains(ButtonType.RIGHT));
        leftButton.setVisible(level.getButtonTypes().contains(ButtonType.LEFT));
        startLoopButton.setVisible(level.getButtonTypes().contains(ButtonType.STARTLOOP));
        endLoopButton.setVisible(level.getButtonTypes().contains(ButtonType.ENDLOOP));
    }

    public void setTurtlePosition(Point turtleCords){
//        this.board.add(turtle, turtleCords.x, turtleCords.y);
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
