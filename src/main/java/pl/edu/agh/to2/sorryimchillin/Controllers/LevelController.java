package pl.edu.agh.to2.sorryimchillin.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;


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
    private Button backwardButton;

    @FXML
    private Button startLoopButton;

    @FXML
    private Button endLoopButton;

    MainScreenController mainScreenController;

    @FXML
    public void initialize() {
        for (Node square : board.getChildren()){
            square.setStyle("-fx-background-color: skyblue; -fx-border-color: blue");
        }
    }

    @FXML
    public void forwardClicked(ActionEvent actionEvent) {
        
    }
}
