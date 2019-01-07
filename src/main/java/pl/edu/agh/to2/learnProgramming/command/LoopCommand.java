package pl.edu.agh.to2.learnProgramming.command;

import pl.edu.agh.to2.learnProgramming.controllers.LevelController;
import pl.edu.agh.to2.learnProgramming.controllers.ProceduresController;
import pl.edu.agh.to2.learnProgramming.model.Procedure;

import java.util.List;

public interface LoopCommand extends Command {

    void onRemove(int index, LevelController levelController, List<Command> movesToExecute);

    //void onRemove(int index, ProceduresController proceduresController, List<Command> movesToExecute);

    void setCurrCounter(int currCounter);

    int getLoopCounter();

    int getCurrCounter();

    int changeLoopsOpened(int loopsOpened);
}
