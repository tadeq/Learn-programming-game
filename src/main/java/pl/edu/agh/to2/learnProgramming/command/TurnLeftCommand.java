package pl.edu.agh.to2.learnProgramming.command;

import pl.edu.agh.to2.learnProgramming.model.Turtle;

public class TurnLeftCommand implements MoveCommand {
    private Turtle turtle;

    private String path;

    public TurnLeftCommand(Turtle turtle) {
        this.turtle = turtle;
        this.path = "/images/left.png";
    }

    @Override
    public void execute() {
        turtle.turnLeft();
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
