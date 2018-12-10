/**
 * @author
 *      Maciej Moskal
 *      Jakub Pajor
 *      Michał Zadora
 *
 * Contains information about:
 *      frontend:
 *          SplitPane levelScreen
 *          ImageView turtleImage
 *          GridPane board
 *          Button forwardButton
 *          Button rightButton
 *          Button leftButton
 *          Button startLoopButton
 *          Button endLoopButton
 *
 *      backend:
 *          MainScreenController mainScreenController
 *          private Level level
 *          LevelGenerator generator
 *          int loopsOpened
 *          List<Integer> loopsRepeatList
 */

package pl.edu.agh.to2.learnProgramming.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import pl.edu.agh.to2.learnProgramming.model.*;
import pl.edu.agh.to2.learnProgramming.model.Point;
import pl.edu.agh.to2.learnProgramming.utilities.LevelGenerator;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;


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

    private int loopsOpened;

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
        startLoopButton.setTooltip(new Tooltip("Start Loop"));
        endLoopButton.setTooltip(new Tooltip("End loop"));
    }

    /**
     * Set up lazily level using LevelGenerator generator,
     * based on currently selected level in mainScreenController:
     *      levelScreen
     *      level
     *      board
     *      turtle
     *      visible commands
     *
     * Observe turtle and change its position as well as current field color in the view.
     */
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
        loopsOpened = 0;
        mainScreenController.getMainBorderPane().setCenter(levelScreen);
    }

    /**
     * Sets turtle position and direction in the view.
     *
     * @param turtleCoords - Point
     * @param direction - (Enum) TurtleDirection
     */
    private void setTurtleImagePosition(Point turtleCoords, TurtleDirection direction) {
        GridPane.setColumnIndex(turtleImage, turtleCoords.getX());
        GridPane.setRowIndex(turtleImage, turtleCoords.getY());
        turtleImage.setRotate(direction.getRotation());
    }

    /**
     * Listens to current turtle positions in (this) Level
     * and sets its: position, direction, field color in the view.
     */
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


    /**
     * Asks this.level to execute given moves (commands) for turtle
     * and to checks if after the execution every field has been visited
     *
     * @param movesToExecute
     * @return true - if moves has been executed successfully and every field has been visited, false - otherwise
     */
    public boolean checkAndExecuteMoves(List<CommandType> movesToExecute) {
        return this.level.executeMoves(movesToExecute, loopsRepeatList) && this.level.areAllFieldsVisited();
        // TODO
        // turtleImage będzie poruszał się po jednym polu tak, aby można było zobaczyć poszczególne kroki
        // kolor odwiedzanych pól będzie zmieniany
    }


    /**
     * Adds FORWARD command after being chosen by user.
     * @param actionEvent
     */
    @FXML
    public void forwardClicked(ActionEvent actionEvent) {
        mainScreenController.addCommand(CommandType.FORWARD);
    }

    /**
     * Adds RIGHT command after being chosen by user.
     * @param actionEvent
     */
    @FXML
    public void rightClicked(ActionEvent actionEvent) {
        mainScreenController.addCommand(CommandType.RIGHT);
    }

    /**
     * Adds LEFT command after being chosen by user.
     * @param actionEvent
     */
    @FXML
    public void leftClicked(ActionEvent actionEvent) {
        mainScreenController.addCommand(CommandType.LEFT);
    }

    /**
     * Adds and opens LOOP command after being chosen by user.
     * @param actionEvent
     */
    @FXML
    public void startLoopClicked(ActionEvent actionEvent) {
        TextInputDialog dialog = new TextInputDialog();

        dialog.setTitle("Loop");
        dialog.setHeaderText("How many loop should repeat:");
        dialog.setContentText("Value:");

        // pobieram wartosc i wrzucam go do listy, bo moze byc kilka petli
        // lista bedzie przekazana po playu do Level.prepareCommands()
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            int number;
            if (result.get().equals(""))
                number = 1;
            else
                number = Integer.parseInt(result.get());
            if (result.get().equals("") || number <= 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Not allowed value. Repeats has been set to 1.");
                alert.showAndWait();
            }
            loopsRepeatList.add(number);
            loopsOpened++;
            mainScreenController.addCommand(CommandType.STARTLOOP);
        }
    }

    /**
     * Adds and closes LOOP command after being chosen by user.
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
            mainScreenController.addCommand(CommandType.ENDLOOP);
        }
    }

}
