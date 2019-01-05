package pl.edu.agh.to2.learnProgramming.command;

import pl.edu.agh.to2.learnProgramming.model.Turtle;

public class TurnRightCommand implements MoveCommand {
    private Turtle turtle;

    private String path;

    public TurnRightCommand(Turtle turtle) {
        this.turtle = turtle;
        this.path = "/images/right.png";
    }

    @Override
    public void execute() {
        turtle.turnRight();
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
