package pl.edu.agh.to2.learnProgramming.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import pl.edu.agh.to2.learnProgramming.model.*;
import pl.edu.agh.to2.learnProgramming.model.Point;
import pl.edu.agh.to2.learnProgramming.utilities.LevelGenerator;

import java.awt.*;
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

    public LevelGenerator getLevelGenerator() {
        return generator;
    }

    @FXML
    public void initialize() {
        generator = new LevelGenerator();
    }

    public void initializeLevel() {
        levelScreen.prefHeightProperty().bind(mainScreenController.getMainBorderPane().prefHeightProperty().subtract(90));
        levelScreen.prefWidthProperty().bind(mainScreenController.getMainBorderPane().prefWidthProperty().subtract(75));
        level = generator.generate(mainScreenController.getCurrentLevel());
        board.getChildren().clear();
        board.getRowConstraints().clear();
        board.getColumnConstraints().clear();
        board.add(turtleImage, level.getTurtle().getCoordinates().getX(), level.getTurtle().getCoordinates().getY());
        board.setHgap(10);
        board.setVgap(10);
        board.paddingProperty().setValue(new Insets(2));
        for (int i = 0; i < level.getSize(); i++) {
            ColumnConstraints constraints = new ColumnConstraints();
            constraints.setPercentWidth(100.0 / level.getSize());
            constraints.setFillWidth(true);
            board.getColumnConstraints().add(constraints);
        }
        for (int i = 0; i < level.getSize(); i++) {
            RowConstraints constraints = new RowConstraints();
            constraints.setPercentHeight(100.0 / level.getSize());
            constraints.setFillHeight(true);
            board.getRowConstraints().add(constraints);
        }
        board.setStyle("-fx-background-color: skyblue; -fx-border-color: blue");
        setTurtleImagePosition(level.getTurtle().getCoordinates(), level.getTurtle().getTurtleDirection());
        for (Point p : level.getFieldCoordinates()) {
            Pane pane = new Pane();
            pane.setStyle("-fx-background-color: darkolivegreen; -fx-border-color: darkgreen");
            board.add(pane, p.getX(), p.getY());
        }
        forwardButton.setVisible(level.getCommandTypes().contains(CommandType.FORWARD));
        rightButton.setVisible(level.getCommandTypes().contains(CommandType.RIGHT));
        leftButton.setVisible(level.getCommandTypes().contains(CommandType.LEFT));
        startLoopButton.setVisible(level.getCommandTypes().contains(CommandType.STARTLOOP));
        endLoopButton.setVisible(level.getCommandTypes().contains(CommandType.ENDLOOP));
        addListeners();
        turtleImage.toFront();
        mainScreenController.getMainBorderPane().setCenter(levelScreen);
    }

    private void setTurtleImagePosition(Point turtleCoords, TurtleDirection direction) {
        GridPane.setColumnIndex(turtleImage, turtleCoords.getX());
        GridPane.setRowIndex(turtleImage, turtleCoords.getY());
        turtleImage.setRotate(direction.getRotation());
    }

    private void addListeners() {
        level.getTurtle().getCoordinates().yProperty().addListener((observable, oldValue, newValue) -> {
            try {
                GridPane.setRowIndex(turtleImage, (int) newValue);
            } catch (IllegalArgumentException e) {
            }
        });
        level.getTurtle().getCoordinates().xProperty().addListener((observable, oldValue, newValue) -> {
            try {
                GridPane.setColumnIndex(turtleImage, (int) newValue);
            } catch (IllegalArgumentException e) {
            }
        });
        level.getTurtle().turtleDirectionProperty().addListener(((observable, oldValue, newValue) -> {
            turtleImage.setRotate(newValue.getRotation());
        }));
        for (LevelPoint lp : level.getFieldCoordinates()) {
            lp.visitedProperty().addListener((observable, oldValue, newValue) -> {
                for (Node node : board.getChildren()) {
                    if (node.getClass() == Pane.class) {
                        Pane p = (Pane) node;
                        if (GridPane.getRowIndex(p) == lp.getY() && GridPane.getColumnIndex(p) == lp.getX())
                            p.setStyle("-fx-background-color: greenyellow; -fx-border-color: darkgreen");
                    }
                }
            });
        }
    }

    public boolean checkAndExecuteMoves(List<CommandType> movesToExecute) {
        return this.level.executeMoves(movesToExecute) && this.level.allVisited();
        // TODO
        // turtleImage będzie poruszał się po jednym polu tak, aby można było zobaczyć poszczególne kroki
        // kolor odwiedzanych pól będzie zmieniany
    }


    @FXML
    public void forwardClicked(ActionEvent actionEvent) {
        mainScreenController.addButton(CommandType.FORWARD);
    }

    @FXML
    public void rightClicked(ActionEvent actionEvent) {
        mainScreenController.addButton(CommandType.RIGHT);
    }

    @FXML
    public void leftClicked(ActionEvent actionEvent) {
        mainScreenController.addButton(CommandType.LEFT);
    }

    @FXML
    public void startLoopClicked(ActionEvent actionEvent) {
        mainScreenController.addButton(CommandType.STARTLOOP);
    }

    @FXML
    public void endLoopClicked(ActionEvent actionEvent) {
        mainScreenController.addButton(CommandType.ENDLOOP);
    }

}
