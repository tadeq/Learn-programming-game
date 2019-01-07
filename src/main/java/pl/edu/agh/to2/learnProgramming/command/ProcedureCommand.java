package pl.edu.agh.to2.learnProgramming.command;

import javafx.scene.Node;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import pl.edu.agh.to2.learnProgramming.controllers.LoopManager;
import pl.edu.agh.to2.learnProgramming.model.CommandType;
import pl.edu.agh.to2.learnProgramming.model.Loop;

import java.util.List;

public class ProcedureCommand implements ComplexCommand {
    private String name;

    private ImageView img;

    private List<Command> levelCommands;

    private List<Command> procedureCommands;

    private List<Loop> loops;

    private int loopCounter;

    private int currCounter = 0;

    public ProcedureCommand(String name, List<Command> procedureCommands, List<Loop> loops) {
        this.loops = loops;
        this.name = name;
        this.procedureCommands = procedureCommands;
        this.img = new ImageView(CommandType.PROCEDURE.getPath());
        img.setFitHeight(40);
        img.setFitWidth(40);
        Tooltip.install(img, new Tooltip(name));
    }

    @Override
    public void execute() {
        for (Command command : procedureCommands) {
            command.setLevelCommands(levelCommands);
            command.setLoopCounter(loopCounter);
            if (command.isLoop()) {
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
    public boolean isLoop() {
        return true;
    }

    @Override
    public Node getImage() {
        return img;
    }
}
