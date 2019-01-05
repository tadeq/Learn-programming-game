package pl.edu.agh.to2.learnProgramming.command;

import pl.edu.agh.to2.learnProgramming.model.Turtle;

public class MoveForwardCommand implements MoveCommand {
    private Turtle turtle;

    private String path;

    public MoveForwardCommand(Turtle turtle) {
        this.turtle = turtle;
        this.path = "/images/forward.png";
    }

    @Override
    public void execute() {
        turtle.moveForward();
    }

    @Override
    public String getPath() {
        return this.path;
    }

    @Override
    public boolean isLoop() {
        return false;
    }
}
