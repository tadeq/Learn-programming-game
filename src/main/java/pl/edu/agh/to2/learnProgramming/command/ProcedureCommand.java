package pl.edu.agh.to2.learnProgramming.command;

import javafx.scene.Node;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import pl.edu.agh.to2.learnProgramming.model.CommandType;
import pl.edu.agh.to2.learnProgramming.model.Loop;
import pl.edu.agh.to2.learnProgramming.model.Turtle;

import java.util.List;

public class ProcedureCommand implements MoveCommand {
    private Turtle turtle;

    private String name;

    private ImageView img;

    private List<Command> commands;

    private List<Command> moves;

    private List<Loop> loops;

    private int loopCounter;

    public ProcedureCommand(String name, List<Command> moves, List<Loop> loops) {
        this.loops = loops;
        this.name = name;
        this.moves = moves;
        this.img = new javafx.scene.image.ImageView(CommandType.PROCEDURE.getPath());
        img.setFitHeight(40);
        img.setFitWidth(40);
        Tooltip.install(img, new Tooltip(name));
    }

    @Override
    public void execute() {
    }

    @Override
    public void prepare() {
        for (Command move : moves)
            ((MoveCommand) move).setTurtle(turtle);
        if (loopCounter >= 0) {
            this.loops.get(loopCounter).addCommands(moves);
        }
        commands.addAll(moves);
    }

    @Override
    public void setCommands(List<Command> commands) {
        this.commands = commands;
    }

    @Override
    public void setLoopCounter(int loopCounter) {
        this.loopCounter = loopCounter;
    }

    @Override
    public void setTurtle(Turtle turtle) {
        this.turtle = turtle;
    }

    @Override
    public boolean isLoop() {
        return false;
    }

    @Override
    public Node getImage() {
        return img;
    }
}
