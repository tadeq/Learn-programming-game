package pl.edu.agh.to2.learnProgramming.command;

import pl.edu.agh.to2.learnProgramming.controllers.LoopManager;

import java.util.List;

public interface ComplexCommand extends Command {

    void onRemove(int index, LoopManager loopManager, List<Command> movesToExecute);

    void setCurrCounter(int currCounter);

    int getLoopCounter();

    int getCurrCounter();

    int changeLoopsOpened(int loopsOpened);
}
