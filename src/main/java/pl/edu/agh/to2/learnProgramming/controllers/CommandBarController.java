package pl.edu.agh.to2.learnProgramming.controllers;

import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import pl.edu.agh.to2.learnProgramming.command.Command;
import pl.edu.agh.to2.learnProgramming.command.ComplexCommand;

import java.util.LinkedList;
import java.util.List;

public class CommandBarController {

    private ScrollPane selectedCommandsPane;

    private HBox commandsBox;

    private List<Command> commands;

    private LoopManager loopManager;


    public CommandBarController(ScrollPane selectedCommandsPane) {
        this.selectedCommandsPane = selectedCommandsPane;
        this.commands = new LinkedList<>();
        this.commandsBox = new HBox();
    }

    public List<Command> getCommands() {
        return commands;
    }

    public void setLoopManager(LoopManager loopManager) {
        this.loopManager = loopManager;
    }

    public void clearCommands() {
        this.commandsBox.getChildren().clear();
        this.commands.clear();
    }

    /**
     * Initialize a HBox for commandsBox.
     */
    public void initializeMovesList() {
        commands.clear();
        commandsBox = new HBox();
        commandsBox.setSpacing(10);
        this.selectedCommandsPane.setContent(commandsBox);
    }

    /**
     * Adds command do movesToExecute and sets it on the view.
     *
     * @param command - Command
     */
    public void addCommand(Command command) {
        Node img = command.getImage();
        img.setOnMouseClicked(this::removeSelectedMove);
        commandsBox.getChildren().add(img);
        this.selectedCommandsPane.setContent(commandsBox);
        commands.add(command);
    }

    /**
     * When user clicks on a previously selected command
     * then the action is delivered and the command is removed.
     *
     * @param mouseEvent - MouseEvent
     */
    public void removeSelectedMove(MouseEvent mouseEvent) {
        int index = this.commandsBox.getChildren().indexOf(mouseEvent.getSource());
        Command command = commands.get(index);
        if (command.isComplex()) {
            ((ComplexCommand) command).onRemove(index, loopManager, commands);
        }
        commands.remove(index);
        this.commandsBox.getChildren().remove(mouseEvent.getSource());
    }

}
