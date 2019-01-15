package pl.edu.agh.to2.learnProgramming.command;

import javafx.scene.Node;
import javafx.scene.image.ImageView;
import pl.edu.agh.to2.learnProgramming.model.Loop;
import pl.edu.agh.to2.learnProgramming.model.Turtle;

import java.util.List;

public class TurnLeftCommand implements MoveCommand {
    private Turtle turtle;

    private ImageView img;

    private List<Command> commands;

    private List<Loop> loops;

    private int loopCounter;

    public TurnLeftCommand(List<Loop> loops) {
        this.loops = loops;
        this.img = new ImageView(CommandType.LEFT.getPath());
        img.setFitHeight(40);
        img.setFitWidth(40);
    }

    @Override
    public void execute() {
        turtle.turnLeft();
    }

    @Override
    public void prepare() {
        if (loopCounter >= 0) {
            this.loops.get(loopCounter).addCommand(this);
        }
        commands.add(this);
    }

    @Override
    public void setTurtle(Turtle turtle) {
        this.turtle = turtle;
    }

    @Override
    public void setLevelCommands(List<Command> levelCommands) {
        this.commands = levelCommands;
    }

    @Override
    public void setLoopCounter(int loopCounter) {
        this.loopCounter = loopCounter;
    }

    @Override
    public Node getImage() {
        return img;
    }
}
