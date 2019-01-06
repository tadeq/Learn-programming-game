package pl.edu.agh.to2.learnProgramming.command;

import javafx.scene.Node;

import java.util.List;

public interface Command {
    void execute();

    boolean isLoop();

    Node getImage();

    void setCommands(List<Command> commands);

    void setLoopCounter(int loopCounter);
}
