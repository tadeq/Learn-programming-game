package pl.edu.agh.to2.learnProgramming.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;

public class ProceduresController {

    @FXML
    private Button addProcedureButton;

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
    private Button saveButton;

    @FXML
    private Button useButton;

    @FXML
    public void addProcedureClicked(ActionEvent actionEvent) {
        setButtonsVisibility(true);
    }

    @FXML
    public void saveClicked(ActionEvent actionEvent) {
        setButtonsVisibility(false);
    }

    @FXML
    public void useClicked(ActionEvent actionEvent) {
    }

    @FXML
    public void forwardClicked(ActionEvent actionEvent) {
    }

    @FXML
    public void rightClicked(ActionEvent actionEvent) {
    }

    @FXML
    public void leftClicked(ActionEvent actionEvent) {
    }

    @FXML
    public void startLoopClicked(ActionEvent actionEvent) {
    }

    @FXML
    public void endLoopClicked(ActionEvent actionEvent) {
    }

    @FXML
    public void initialize() {
        setButtonsVisibility(false);
        forwardButton.setTooltip(new Tooltip("Move forward"));
        rightButton.setTooltip(new Tooltip("Turn Right"));
        leftButton.setTooltip(new Tooltip("Turn left"));
        startLoopButton.setTooltip(new Tooltip("Start loop"));
        endLoopButton.setTooltip(new Tooltip("End loop"));
        saveButton.setTooltip(new Tooltip("Save procedure"));
        addProcedureButton.setTooltip(new Tooltip("Add procedure"));
        useButton.setTooltip(new Tooltip("Use procedure"));
    }

    private void setButtonsVisibility(boolean visible) {
        forwardButton.setVisible(visible);
        rightButton.setVisible(visible);
        leftButton.setVisible(visible);
        startLoopButton.setVisible(visible);
        endLoopButton.setVisible(visible);
    }
}
