package pl.edu.agh.to2.sorryimchillin.Controllers;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import pl.edu.agh.to2.sorryimchillin.Model.ButtonType;
import pl.edu.agh.to2.sorryimchillin.Model.Level;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class MainScreenController {
    @FXML
    private SplitPane level;
    @FXML
    private BorderPane mainBorderPane;
    @FXML
    private Button playButton;
    @FXML
    private LevelController levelController;
    @FXML
    public ScrollPane selectedButtonsPane;

    private Level currentLevel;

    private HBox buttons;

    @FXML
    void initialize() {
        List<ButtonType> buttonTypes = Arrays.asList(ButtonType.FORWARD);
        List<Point> squares = Arrays.asList(new Point(2,2),new Point(0,1),new Point(1,1));
        Point turtlePosition = new Point(1,1);
        currentLevel = new Level(buttonTypes,squares,turtlePosition);
        levelController.setMainScreenController(this);
        levelController.initializeLevel();
        buttons = new HBox();
        buttons.setSpacing(10);
        buttons.setPadding(new Insets(10));
    }

    public Level getCurrentLevel() {
        return currentLevel;
    }

    public void addButton(Button button){
        buttons.getChildren().add(button);
        this.selectedButtonsPane.setContent(buttons);
    }

}
