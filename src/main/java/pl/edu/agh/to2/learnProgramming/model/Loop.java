/**
 * @author Maciej Moskal
 * Jakub Pajor
 * Michał Zadora
 * <p>
 * Model - loop.
 * Contains information about:
 * List<CommandType> commands (commands inside the loop)
 * int counter (its repetition number)
 */

package pl.edu.agh.to2.learnProgramming.model;

import pl.edu.agh.to2.learnProgramming.command.Command;

import java.util.ArrayList;
import java.util.List;

public class Loop {
    private int counter;
    private List<Command> commands;

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

    public void addCommand(Command command) {
        this.commands.add(command);
    }

    public void addCommands(List<Command> commands) {
        this.commands.addAll(commands);
    }

    public List<Command> getCommands() {
        return this.commands;
    }
}
