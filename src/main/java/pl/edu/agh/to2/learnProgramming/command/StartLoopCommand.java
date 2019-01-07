package pl.edu.agh.to2.learnProgramming.command;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import pl.edu.agh.to2.learnProgramming.controllers.LoopManager;
import pl.edu.agh.to2.learnProgramming.model.CommandType;
import pl.edu.agh.to2.learnProgramming.model.Loop;

import java.util.List;

public class StartLoopCommand implements ComplexCommand {
    private int loopCounter;
    private int currCounter;

    private List<Command> commands;

    private List<Integer> loopsRepeatList;

    private List<Loop> loops;

    private HBox box;

    public StartLoopCommand(List<Loop> loops, List<Integer> loopsRepeatList) {
        this.loops = loops;
        this.loopsRepeatList = loopsRepeatList;
        ImageView img = new ImageView(CommandType.STARTLOOP.getPath());
        img.setFitHeight(40);
        img.setFitWidth(40);
        box = new HBox();
        box.setPrefSize(60, 40);
        box.getChildren().add(img);
        Label label = new Label(loopsRepeatList.get(loopsRepeatList.size() - 1).toString());
        label.setPrefSize(20, 40);
        box.getChildren().add(label);
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
    public Node getImage() {
        return box;
    }

    @Override
    public boolean isComplex() {
        return true;
    }

    @Override
    public void execute() {
        currCounter = loopsRepeatList.get(0);
        loopsRepeatList.remove(0);
        loops.add(new Loop(currCounter > 0 ? currCounter : 1));
        loopCounter++;
    }

    public void onRemove(int index, LoopManager loopManager, List<Command> movesToExecute) {
        loopManager.decLoopsOpened();
        int loopsBefore = 0;
        for (int i = 0; i < index; i++) {
            if (movesToExecute.get(i).getClass() == StartLoopCommand.class) {
                loopsBefore++;
            }
        }
        loopManager.removeLoop(loopsBefore);
    }

    public int changeLoopsOpened(int loopsOpened) {
        return ++loopsOpened;
    }
}
