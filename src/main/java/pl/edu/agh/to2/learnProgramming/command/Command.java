package pl.edu.agh.to2.learnProgramming.command;

import javafx.scene.Node;

import java.util.List;

public interface Command {
    void execute();

    boolean isLoop();

    Node getImage();

    void setLevelCommands(List<Command> levelCommands);

    void setLoopCounter(int loopCounter);
}
