package pl.edu.agh.to2.learnProgramming.command;

import pl.edu.agh.to2.learnProgramming.model.Turtle;

public class TurnRightCommand implements Command {
    private Turtle turtle;

    public TurnRightCommand(Turtle turtle) {
        this.turtle = turtle;
    }

    @Override
    public void execute() {
        turtle.turnRight();
    }
}
