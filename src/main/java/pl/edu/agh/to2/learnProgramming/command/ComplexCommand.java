package pl.edu.agh.to2.learnProgramming.command;

import pl.edu.agh.to2.learnProgramming.controllers.LoopManager;

import java.util.List;

public interface ComplexCommand extends Command {

    void onRemove(int index, LoopManager loopManager, List<Command> movesToExecute);

    void setLevelCommands(List<Command> levelCommands);

    void setCurrCounter(int currCounter);

    void setLoopCounter(int loopCounter);

    int getLoopCounter();

    int getCurrCounter();

    int changeLoopsOpened(int loopsOpened);

    default boolean isComplex() {
        return true;
    }
}
