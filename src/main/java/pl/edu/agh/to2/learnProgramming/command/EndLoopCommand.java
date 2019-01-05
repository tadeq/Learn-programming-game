package pl.edu.agh.to2.learnProgramming.command;

import pl.edu.agh.to2.learnProgramming.controllers.LevelController;
import pl.edu.agh.to2.learnProgramming.model.Loop;

import java.util.List;

public class EndLoopCommand implements LoopCommand {
    private String path;

    private int loopCounter;
    private int currCounter;

    public EndLoopCommand() {
        this.path = "/images/endLoop.png";
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
    public void execute(List<Loop> loops, List<Integer> loopsRepeats, List<Command> commands) {
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

    @Override
    public String getPath() {
        return path;
    }

    public void onRemove(int index, LevelController levelController, List<Command> movesToExecute) {
        levelController.incLoopsOpened();
    }

    public int changeLoopsOpened(int loopsOpened) {
        return --loopsOpened;
    }
}
