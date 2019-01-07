/**
 * @author Maciej Moskal
 * Jakub Pajor
 * Michał Zadora
 * <p>
 * Contains information about:
 * frontend:
 * SplitPane levelScreen
 * ImageView turtleImage
 * GridPane board
 * Button forwardButton
 * Button rightButton
 * Button leftButton
 * Button startLoopButton
 * Button endLoopButton
 * <p>
 * backend:
 * MainScreenController mainScreenController
 * private Level level
 * LevelGenerator generator
 * int loopsOpened
 * List<Integer> loopsRepeatList
 */

package pl.edu.agh.to2.learnProgramming.controllers;

import javafx.animation.*;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.effect.ColorInput;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import pl.edu.agh.to2.learnProgramming.command.*;
import pl.edu.agh.to2.learnProgramming.model.*;
import pl.edu.agh.to2.learnProgramming.model.Point;
import pl.edu.agh.to2.learnProgramming.utilities.LevelGenerator;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;


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

    @FXML
    public Button procedureButton;

    private MainScreenController mainScreenController;

    private ProceduresController proceduresController;

    private Level level;

    private LevelGenerator generator;

    private int loopsOpened;

    private List<String> steps;

    private List<Integer> loopsRepeatList = new LinkedList<>();

    public void setMainScreenController(MainScreenController mainScreenController) {
        this.mainScreenController = mainScreenController;
    }

    public LevelGenerator getLevelGenerator() {
        return generator;
    }

    public int getLoopsOpened() {
        return loopsOpened;
    }

    public void decLoopsOpened() {
        loopsOpened--;
    }

    public void incLoopsOpened() {
        loopsOpened++;
    }

    public List<Integer> getLoopsRepeatList() {
        return this.loopsRepeatList;
    }

    @FXML
    public void initialize() {
        generator = new LevelGenerator();
        forwardButton.setTooltip(new Tooltip("Move forward"));
        rightButton.setTooltip(new Tooltip("Turn Right"));
        leftButton.setTooltip(new Tooltip("Turn left"));
        startLoopButton.setTooltip(new Tooltip("Start loop"));
        endLoopButton.setTooltip(new Tooltip("End loop"));
        procedureButton.setTooltip(new Tooltip("Procedures menu"));
    }

    /**
     * Set up lazily level using LevelGenerator generator,
     * based on currently selected level in mainScreenController:
     * levelScreen
     * level
     * board
     * turtle
     * visible commands
     * <p>
     * Observe turtle and change its position as well as current field color in the view.
     */
    public void initializeLevel() {
        levelScreen.prefHeightProperty().bind(mainScreenController.getMainBorderPane().prefHeightProperty().subtract(90));
        levelScreen.prefWidthProperty().bind(mainScreenController.getMainBorderPane().prefWidthProperty().subtract(75));
        level = generator.generate(mainScreenController.getCurrentLevel());

        ImageView newTurtle = new ImageView("/images/turtle.png");
        newTurtle.setFitWidth(60);
        newTurtle.setFitHeight(60);
        GridPane.setHalignment(newTurtle, HPos.CENTER);
        GridPane.setValignment(newTurtle, VPos.CENTER);
        this.turtleImage = newTurtle;

        board.getChildren().clear();
        board.getRowConstraints().clear();
        board.getColumnConstraints().clear();
        setTurtleImagePosition(level.getTurtle().getCoordinates(), level.getTurtle().getTurtleDirection(), newTurtle);
        board.add(newTurtle, level.getTurtle().getCoordinates().getX(), level.getTurtle().getCoordinates().getY());
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
        procedureButton.setVisible(level.getCommandTypes().contains(CommandType.PROCEDURE));

        addListeners();
        turtleImage.toFront();
        loopsOpened = 0;
        mainScreenController.getMainBorderPane().setCenter(levelScreen);
    }

    /**
     * Sets turtle position and direction in the view.
     *
     * @param turtleCoords - Point
     * @param direction    - (Enum) TurtleDirection
     */
    private void setTurtleImagePosition(Point turtleCoords, TurtleDirection direction, ImageView newTurtle) {
        GridPane.setColumnIndex(newTurtle, turtleCoords.getX());
        GridPane.setRowIndex(newTurtle, turtleCoords.getY());
        newTurtle.setRotate(direction.getRotation());
    }


    /**
     * Listens to current turtle positions in (this) Level
     * and sets its: position, direction, field color in the view.
     */
    private void addListeners() {;
        this.steps = new LinkedList<>();
        level.getTurtle().getCoordinates().yProperty().addListener((observable, oldValue, newValue) -> {
                if((int) oldValue > (int) newValue)
                    steps.add("UP");
                else
                    steps.add("DOWN");
        });
        level.getTurtle().getCoordinates().xProperty().addListener((observable, oldValue, newValue) -> {
                if((int) oldValue > (int) newValue)
                    steps.add("LEFT");
                else
                    steps.add("RIGHT");
        });
        level.getTurtle().turtleDirectionProperty().addListener(((observable, oldValue, newValue) -> steps.add(String.valueOf(newValue.getRotation()))));
        for (LevelPoint lp : level.getFieldCoordinates()) {
            lp.visitedProperty().addListener((observable, oldValue, newValue) -> {
                for (Node node : board.getChildren()) {
                    if (node.getClass() == Pane.class) {
                        Pane p = (Pane) node;
                        if (GridPane.getRowIndex(p) == lp.getY() && GridPane.getColumnIndex(p) == lp.getX()) {
                            p.setStyle("-fx-background-color: greenyellow; -fx-border-color: darkgreen");
                        }
                    }
                }
            });
        }
    }

    /**
     * Asks this.level to execute given moves (commands) for turtle
     * and to checks if after the execution every field has been visited
     *
     * @param commandsToExecute
     * @return true - if moves has been executed successfully and every field has been visited, false - otherwise
     */
    public boolean checkAndExecuteMoves(List<Command> commandsToExecute) {
        boolean result = (this.level.executeMoves(commandsToExecute, loopsRepeatList) && this.level.areAllFieldsVisited());
        animate(level.getStartingTurtle().getCoordinates().getX(), level.getStartingTurtle().getCoordinates().getY());
        return result;

    }

    public void animate(int startX, int startY) {

        DoubleProperty x = new SimpleDoubleProperty(startX);
        DoubleProperty y = new SimpleDoubleProperty(startY);
        DoubleProperty r = new SimpleDoubleProperty(level.getStartingTurtle().getTurtleDirection().getRotation());
        SequentialTransition s = new SequentialTransition();

        int xEnd = x.intValue(), yEnd = y.intValue(), rEnd = r.intValue();

        for (String step : steps) {
            switch (step) {
                case "UP":
                    yEnd -= 1;
                    break;
                case "DOWN":
                    yEnd += 1;
                    break;
                case "LEFT":
                    xEnd -= 1;
                    break;
                case "RIGHT":
                    xEnd += 1;
                    break;
                case "0":
                    rEnd = 0;
                    break;
                case "90":
                    rEnd = 90;
                    break;
                case "180":
                    rEnd = 180;
                    break;
                case "270":
                    rEnd = 270;
                    break;
            }

            KeyValue px = new KeyValue(x, xEnd);
            KeyValue py = new KeyValue(y, yEnd);
            KeyValue pr = new KeyValue(r, rEnd);
            KeyFrame kf = new KeyFrame(Duration.seconds(0.6), px, py, pr);
            Timeline t = new Timeline(kf);
            Timeline pause = new Timeline(new KeyFrame(Duration.seconds(0.15)));

            s.getChildren().addAll(t,pause);

        }

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                turtleImage.setRotate(r.doubleValue());
                GridPane.setColumnIndex(turtleImage, x.intValue());
                GridPane.setRowIndex(turtleImage, y.intValue());
//                level.setPointVisited(x.intValue(),y.intValue());
            }
        };

        s.setOnFinished(event -> {

            timer.stop();
            //what to do after animation ends
        });
        timer.start();
        s.play();


    }

    /**
     * Adds FORWARD command after being chosen by user.
     *
     * @param actionEvent
     */
    @FXML
    public void forwardClicked(ActionEvent actionEvent) {
        mainScreenController.addCommand(new MoveForwardCommand(this.level.getLoops()));
    }

    /**
     * Adds RIGHT command after being chosen by user.
     *
     * @param actionEvent
     */
    @FXML
    public void rightClicked(ActionEvent actionEvent) {
        mainScreenController.addCommand(new TurnRightCommand(this.level.getLoops()));
    }

    /**
     * Adds LEFT command after being chosen by user.
     *
     * @param actionEvent
     */
    @FXML
    public void leftClicked(ActionEvent actionEvent) {
        mainScreenController.addCommand(new TurnLeftCommand(this.level.getLoops()));
    }

    /**
     * Adds and opens LOOP command after being chosen by user.
     *
     * @param actionEvent
     */
    @FXML
    public void startLoopClicked(ActionEvent actionEvent) {
        TextInputDialog dialog = new TextInputDialog();

        dialog.setTitle("Loop");
        dialog.setHeaderText("Loop repeats:");
        dialog.setContentText("Value:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            int number;
            if (result.get().equals(""))
                number = 1;
            else
                number = Integer.parseInt(result.get());
            if (result.get().equals("") || number <= 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Value not allowed. Repeats has been set to 1.");
                alert.showAndWait();
            }
            loopsRepeatList.add(number);
            loopsOpened++;
            mainScreenController.addCommand(new StartLoopCommand(this.level.getLoops(), this.loopsRepeatList));
        }
    }

    /**
     * Adds and closes LOOP command after being chosen by user.
     *
     * @param actionEvent
     */
    @FXML
    public void endLoopClicked(ActionEvent actionEvent) {
        if (loopsOpened == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("You have to start the loop before ending it");
            alert.show();
        } else {
            loopsOpened--;
            mainScreenController.addCommand(new EndLoopCommand(this.level.getLoops(), this.loopsRepeatList));
        }
    }

    @FXML
    public void procedureClicked(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/procedures.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Procedures");
        stage.setScene(new Scene(root));
        stage.show();
        ProceduresController proceduresController = loader.getController();
        proceduresController.setMainScreenController(this.mainScreenController);
        proceduresController.setProcedures(this.mainScreenController.getProcedures());
        proceduresController.setLoops(level.getLoops());
    }
}
