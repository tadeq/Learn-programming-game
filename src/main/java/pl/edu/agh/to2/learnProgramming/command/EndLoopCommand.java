package pl.edu.agh.to2.learnProgramming.command;

import pl.edu.agh.to2.learnProgramming.model.Turtle;

public class EndLoopCommand implements Command {
    private Turtle turtle;

    public EndLoopCommand(Turtle turtle) {
        this.turtle = turtle;
    }

    @Override
    public void execute() {

    }
}
