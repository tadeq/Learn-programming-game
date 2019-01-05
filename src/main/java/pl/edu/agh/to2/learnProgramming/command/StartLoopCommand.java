package pl.edu.agh.to2.learnProgramming.command;

import pl.edu.agh.to2.learnProgramming.controllers.LevelController;
import pl.edu.agh.to2.learnProgramming.model.Loop;

import java.util.List;

public class StartLoopCommand implements LoopCommand {
    private String path;

    private int loopCounter;
    private int currCounter;

    public StartLoopCommand() {
        this.path = "/images/startLoop.png";
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
        currCounter = loopsRepeats.get(0);
        loopsRepeats.remove(0);
        loops.add(new Loop(currCounter > 0 ? currCounter : 1));
        loopCounter++;
    }

    @Override
    public String getPath() {
        return this.path;
    }

    public void onRemove(int index, LevelController levelController, List<Command> movesToExecute) {
        levelController.decLoopsOpened();
        int loopsBefore = 0;
        for (int i = 0; i < index; i++) {
            if (movesToExecute.get(i).getClass() == StartLoopCommand.class) {
                loopsBefore++;
            }
        }
        levelController.getLoopsRepeatList().remove(loopsBefore);
    }

    public int changeLoopsOpened(int loopsOpened) {
        return ++loopsOpened;
    }
}
