package pl.edu.agh.to2.learnProgramming.model;

import java.util.ArrayList;
import java.util.List;

public class Loop {
    private int counter;
    private List<CommandType> commands;

    public Loop(int counter) {
        this.counter = counter;
        this.commands = new ArrayList<>();
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public int getCounter() {
        return this.counter;
    }

    public void addCommand(CommandType command) {
        this.commands.add(command);
    }

    public void addCommands(List<CommandType> commands) {
        this.commands.addAll(commands);
    }

    public List<CommandType> getCommands() {
        return this.commands;
    }
}
