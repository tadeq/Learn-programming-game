package pl.edu.agh.to2.learnProgramming.command;

import pl.edu.agh.to2.learnProgramming.controllers.LevelController;
import pl.edu.agh.to2.learnProgramming.model.Loop;

import java.util.List;

public interface LoopCommand extends Command {

    void execute(List<Loop> loops, List<Integer> loopsRepeats, List<Command> commands);

    void onRemove(int index, LevelController levelController, List<Command> movesToExecute);

    void setLoopCounter(int loopCounter);

    void setCurrCounter(int currCounter);

    int getLoopCounter();

    int getCurrCounter();

    int changeLoopsOpened(int loopsOpened);
}
