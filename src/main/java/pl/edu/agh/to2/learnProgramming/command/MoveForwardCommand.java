package pl.edu.agh.to2.learnProgramming.command;

import pl.edu.agh.to2.learnProgramming.model.Turtle;

public class MoveForwardCommand implements Command {
    private Turtle turtle;

    public MoveForwardCommand(Turtle turtle) {
        this.turtle = turtle;
    }

    @Override
    public void execute() {
        turtle.moveForward();
    }
}
