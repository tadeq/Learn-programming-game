package pl.edu.agh.to2.learnProgramming.command;

import javafx.scene.Node;

public interface Command {
    void execute();

    boolean isComplex();

    Node getImage();
}
