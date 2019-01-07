package pl.edu.agh.to2.learnProgramming.command;

import javafx.scene.Node;
import javafx.scene.image.ImageView;
import pl.edu.agh.to2.learnProgramming.controllers.LoopManager;
import pl.edu.agh.to2.learnProgramming.model.CommandType;
import pl.edu.agh.to2.learnProgramming.model.Loop;

import java.util.List;

public class EndLoopCommand implements ComplexCommand {
    private int loopCounter;
    private int currCounter;

    private List<Command> commands;

    private List<Integer> loopsRepeatList;

    private List<Loop> loops;

    private ImageView img;

    public EndLoopCommand(List<Loop> loops, List<Integer> loopsRepeatList) {
        this.loops = loops;
        this.loopsRepeatList = loopsRepeatList;
        this.img = new ImageView(CommandType.ENDLOOP.getPath());
        img.setFitHeight(40);
        img.setFitWidth(40);
    }

    public void setLevelCommands(List<Command> levelCommands) {
        this.commands = levelCommands;
    }

    public void setLoopCounter(int loopCounter) {
        this.loopCounter = loopCounter;
    }

    public void setCurrCounter(int currCounter) {
        this.currCounter = currCounter;
    }

    public int getLoopCounter() {
        return loopCounter;
    }

    public int getCurrCounter() {
        return currCounter;
    }

    @Override
    public boolean isLoop() {
        return true;
    }

    @Override
    public Node getImage() {
        return img;
    }

    @Override
    public void execute() {
        for (int x = 0; x < loops.get(loopCounter).getCounter() - 1; x++) {
            commands.addAll(loops.get(loopCounter).getCommands());
        }
        loopCounter--;
        if (loopCounter >= 0) {
            for (int x = 0; x < loops.get(loopCounter + 1).getCounter(); x++) {
                loops.get(loopCounter).addCommands(loops.get(loopCounter + 1).getCommands());
            }
        }
        loops.remove(loopCounter + 1);
    }

    public void onRemove(int index, LoopManager loopManager, List<Command> movesToExecute) {
        loopManager.incLoopsOpened();
    }

    public int changeLoopsOpened(int loopsOpened) {
        return --loopsOpened;
    }
}
