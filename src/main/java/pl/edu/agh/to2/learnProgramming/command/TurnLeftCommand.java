package pl.edu.agh.to2.learnProgramming.command;

import pl.edu.agh.to2.learnProgramming.model.Turtle;

public class TurnLeftCommand implements Command {
    private Turtle turtle;

    public TurnLeftCommand(Turtle turtle) {
        this.turtle = turtle;
    }

    @Override
    public void execute() {
        turtle.turnLeft();
    }
}
