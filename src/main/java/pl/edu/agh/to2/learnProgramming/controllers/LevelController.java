package pl.edu.agh.to2.learnProgramming.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import pl.edu.agh.to2.learnProgramming.model.*;
import pl.edu.agh.to2.learnProgramming.utilities.LevelGenerator;

import java.util.List;


public class LevelController {
    @FXML
    private SplitPane levelScreen;

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

    private LevelGenerator generator;

    public void setMainScreenController(MainScreenController mainScreenController) {
        this.mainScreenController = mainScreenController;
    }

    @FXML
    public void initialize() {

    }

    public void initializeLevel(){
        generator = new LevelGenerator();
        level = generator.generate(mainScreenController.getCurrentLevel());
        board.prefHeight(400);
        board.prefWidth(400);
        for (int i=0;i<level.getWidth();i++){
            ColumnConstraints constraints = new ColumnConstraints();
            constraints.setPercentWidth(100.0/level.getWidth());
            board.getColumnConstraints().add(constraints);
        }
        for (int i=0;i<level.getHeight();i++){
            RowConstraints constraints= new RowConstraints();
            constraints.setPercentHeight(100.0/level.getHeight());
            board.getRowConstraints().add(constraints);
        }
        board.setHgap(0);
        board.setVgap(0);
        board.paddingProperty().setValue(new Insets(0));
        board.setStyle("-fx-background-color: skyblue; -fx-border-color: blue");
        setTurtleImagePosition(level.getTurtle().getCoordinates(),level.getTurtle().getTurtleDirection());
        for (Point p : level.getFieldCoordinates()) {
            Pane pane = new Pane();
            pane.setMinSize(board.getPrefWidth()/level.getWidth(),board.getPrefHeight()/level.getHeight());
            pane.setStyle("-fx-background-color: darkolivegreen; -fx-border-color: darkgreen");
            board.add(pane, p.getX(), p.getY());
        }
        forwardButton.setVisible(level.getMoveTypes().contains(MoveType.FORWARD));
        rightButton.setVisible(level.getMoveTypes().contains(MoveType.RIGHT));
        leftButton.setVisible(level.getMoveTypes().contains(MoveType.LEFT));
        startLoopButton.setVisible(level.getMoveTypes().contains(MoveType.STARTLOOP));
        endLoopButton.setVisible(level.getMoveTypes().contains(MoveType.ENDLOOP));
        level.getTurtle().getCoordinates().yProperty().addListener((observable, oldValue, newValue) -> {
            try {
                GridPane.setColumnIndex(turtleImage, (int) newValue);
            } catch (IllegalArgumentException e){
            }
        });
        level.getTurtle().getCoordinates().xProperty().addListener((observable, oldValue, newValue) -> {
            try {
                GridPane.setRowIndex(turtleImage, (int) newValue);
            }catch (IllegalArgumentException e){
            }
        });
        level.getTurtle().turtleDirectionProperty().addListener(((observable, oldValue, newValue) -> {
            turtleImage.setRotate(newValue.getRotation());
        }));
        turtleImage.toFront();
        mainScreenController.getMainBorderPane().setCenter(levelScreen);
    }

    public void setTurtleImagePosition(Point turtleCoords, TurtleDirection direction) {
        GridPane.setColumnIndex(turtleImage, turtleCoords.getX());
        GridPane.setRowIndex(turtleImage, turtleCoords.getY());
        turtleImage.setRotate(direction.getRotation());
    }

    public boolean checkAndExecuteMoves(List<MoveType> movesToExecute) {
        return this.level.executeMoves(movesToExecute) && this.level.allVisited();
        // TODO
        // turtleImage będzie poruszał się po jednym polu tak, aby można było zobaczyć poszczególne kroki
        // kolor odwiedzanych pól będzie zmieniany
    }


    @FXML
    public void forwardClicked(ActionEvent actionEvent) {
        mainScreenController.addButton(MoveType.FORWARD);
    }

    @FXML
    public void rightClicked(ActionEvent actionEvent) {
        mainScreenController.addButton(MoveType.RIGHT);
    }

    @FXML
    public void leftClicked(ActionEvent actionEvent) {
        mainScreenController.addButton(MoveType.LEFT);
    }

    @FXML
    public void startLoopClicked(ActionEvent actionEvent) {
        mainScreenController.addButton(MoveType.STARTLOOP);
    }

    @FXML
    public void endLoopClicked(ActionEvent actionEvent) {
        mainScreenController.addButton(MoveType.ENDLOOP);
    }

}
