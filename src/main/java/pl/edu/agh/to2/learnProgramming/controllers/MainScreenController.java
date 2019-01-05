/**
 * @author
 *      Maciej Moskal
 *      Jakub Pajor
 *      Michał Zadora
 *
 * Controller - main screen.
 * Contains information about:
 *      frontend:
 *          BorderPane mainBorderPane
 *          Button playButton
 *          ScrollPane selectedMovesPane
 *          VBox levelNumbersBox
 *          ToggleGroup levelNumbers
 *          HBox moves
 *
 *      backend:
 *          LevelController levelController
 *          int currentLevel
 *          List<CommandType> movesToExecute
 */

package pl.edu.agh.to2.learnProgramming.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
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

    /**
     * Initialize a HBox for moves.
     */
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

    /**
     * Adds command do movesToExecute and sets it on the view.
     * @param type - (Enum) CommandType
     */
    public void addCommand(CommandType type) {
        //TODO przeciąganie klocków na liście ruchów
        ImageView img = new ImageView(type.getPath());
        img.setFitHeight(40);
        img.setFitWidth(40);
        if (type == CommandType.STARTLOOP) {
            HBox box = new HBox();
            box.setPrefSize(60, 40);
            box.getChildren().add(img);
            Label label = new Label(levelController.getLoopsRepeatList().get(levelController.getLoopsRepeatList().size() - 1).toString());
            label.setPrefSize(20, 40);
            box.getChildren().add(label);
            box.setOnMouseClicked(this::removeSelectedMove);
            moves.getChildren().add(box);
        } else {
            img.setOnMouseClicked(this::removeSelectedMove);
            moves.getChildren().add(img);
        }
        this.selectedMovesPane.setContent(moves);
        this.movesToExecute.add(type);
    }

    /**
     * When user clicks on a previously selected command
     * then the action is delivered and the command is removed.
     * @param mouseEvent - MouseEvent
     */
    private void removeSelectedMove(MouseEvent mouseEvent) {
        int index = this.moves.getChildren().indexOf(mouseEvent.getSource());
        CommandType commandType = movesToExecute.get(index);
        if (commandType == CommandType.ENDLOOP)
            levelController.incLoopsOpened();
        else if (commandType == CommandType.STARTLOOP) {
            levelController.decLoopsOpened();
            int loopsBefore = 0;
            for (int i = 0; i < index; i++) {
                if (movesToExecute.get(i) == CommandType.STARTLOOP) {
                    loopsBefore++;
                }
            }
            levelController.getLoopsRepeatList().remove(loopsBefore);
        }
        movesToExecute.remove(index);
        this.moves.getChildren().remove(mouseEvent.getSource());
    }

    /**
     * Adds level number button on right VBox in the view.
     */
    private void addLevelNumberButton() {
        ToggleButton button = new ToggleButton(Integer.toString(currentLevel));
        button.setPrefHeight(26);
        button.setPrefWidth(50);
        button.setSelected(true);
        button.setOnAction(this::levelChosen);
        levelNumbersBox.getChildren().add(button);
        levelNumbers.getToggles().add(button);
    }

    /**
     * Is invoked when user selects other available level.
     * @param actionEvent
     */
    private void levelChosen(ActionEvent actionEvent) {
        currentLevel = Integer.parseInt(((ToggleButton) actionEvent.getSource()).getText());
        levelController.initializeLevel();
    }

    /**
     * Is invoked when user clicks "play" button.
     * It means, when user wants to run his commands.
     */
    @FXML
    public void playButtonClicked() {
        Alert alert;
        if (levelController.getLoopsOpened() != 0) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Number of startLoop has to be equal to number of endLoop. Check your program.");
            alert.show();
        } else {
            boolean loopsGood = true;
            int loopsOpened = 0;
            for (CommandType command : movesToExecute) {
                if (command == CommandType.STARTLOOP)
                    loopsOpened++;
                else if (command == CommandType.ENDLOOP)
                    loopsOpened--;
                if (loopsOpened < 0) {
                    loopsGood = false;
                    break;
                }
            }
            if (loopsGood) {
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
            } else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Can't end loop before starting it. Check your program.");
                alert.show();
            }
        }
    }

}
