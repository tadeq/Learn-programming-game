package pl.edu.agh.to2.learnProgramming.command;

import javafx.scene.Node;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import pl.edu.agh.to2.learnProgramming.controllers.LoopManager;
import pl.edu.agh.to2.learnProgramming.model.Procedure;

import java.util.List;

public class ProcedureCommand implements ComplexCommand {
    private Procedure procedure;

    private ImageView img;

    private List<Command> levelCommands;

    private int loopCounter;

    private int currCounter;

    public ProcedureCommand(Procedure procedure) {
        this.procedure = procedure;
        currCounter = 0;
        this.img = new ImageView(CommandType.PROCEDURE.getPath());
        img.setFitHeight(40);
        img.setFitWidth(40);
        Tooltip.install(img, new Tooltip(procedure.getName()));
    }

    @Override
    public void execute() {
        for (Command command : procedure.getCommands()) {
            command.setLevelCommands(levelCommands);
            command.setLoopCounter(loopCounter);
            if (command.isComplex()) {
                ComplexCommand complexCommand = (ComplexCommand) command;
                complexCommand.setCurrCounter(currCounter);
                complexCommand.execute();
                loopCounter = complexCommand.getLoopCounter();
                currCounter = complexCommand.getCurrCounter();
            } else {
                MoveCommand moveCommand = (MoveCommand) command;
                moveCommand.prepare();
            }
        }
    }


    @Override
    public void setLevelCommands(List<Command> levelCommands) {
        this.levelCommands = levelCommands;
    }

    @Override
    public void setLoopCounter(int loopCounter) {
        this.loopCounter = loopCounter;
    }

    @Override
    public void onRemove(int index, LoopManager loopManager, List<Command> movesToExecute) {
    }

    @Override
    public void setCurrCounter(int currCounter) {
        this.currCounter = currCounter;
    }

    @Override
    public int getLoopCounter() {
        return this.loopCounter;
    }

    @Override
    public int getCurrCounter() {
        return this.currCounter;
    }

    @Override
    public int changeLoopsOpened(int loopsOpened) {
        return loopsOpened;
    }

    @Override
    public Node getImage() {
        return img;
    }
}
