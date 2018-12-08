package pl.edu.agh.to2.learnProgramming.model;

import java.util.ArrayList;
import java.util.List;

public class Loop {
    private int counter;
    private List<CommandType> commands;
    private boolean isEnd;


    public Loop(int counter) {
        this.counter = counter;
        this.commands = new ArrayList<>();
        this.isEnd = false;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public int getCounter() {
        return counter;
    }

    public void addCommand(CommandType command) {
        this.commands.add(command);
    }
    public void addCommands(List<CommandType> commands){
        this.commands.addAll(commands);
    }

    public List<CommandType> getCommands() {
        return this.commands;
    }

    public void setEnd(){
        this.isEnd = true;
    }
}
