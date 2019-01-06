package pl.edu.agh.to2.learnProgramming.model;

import pl.edu.agh.to2.learnProgramming.command.Command;

import java.util.LinkedList;
import java.util.List;

public class Procedure {
    private String name;

    private List<Command> commands;

    public Procedure(String name) {
        this.name = name;
        commands = new LinkedList<>();
    }

    public String getName() {
        return name;
    }

    public void addCommand(Command command) {
        this.commands.add(command);
    }

    public List<Command> getCommands() {
        return commands;
    }

    public String toString() {
        return name;
    }
}
