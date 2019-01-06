package pl.edu.agh.to2.learnProgramming.command;

import pl.edu.agh.to2.learnProgramming.model.Turtle;

public interface MoveCommand extends Command {
    void setTurtle(Turtle turtle);

    void prepare();
}
