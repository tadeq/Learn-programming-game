package pl.edu.agh.to2.learnProgramming.command;

import pl.edu.agh.to2.learnProgramming.model.Turtle;

public class StartLoopCommand implements Command {
    private Turtle turtle;

    public StartLoopCommand(Turtle turtle){
        this.turtle = turtle;
    }

    @Override
    public void execute() {

    }
}
