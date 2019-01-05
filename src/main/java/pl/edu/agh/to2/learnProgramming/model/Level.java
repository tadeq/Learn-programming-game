/**
 * @author Maciej Moskal
 * Jakub Pajor
 * Michał Zadora
 * <p>
 * Model - level.
 * Contains information about:
 * List<CommandType> commandTypes
 * List<LevelPoint> fieldCoordinates
 * List<Loop> loops
 * Turtle turtle
 * int size
 */
package pl.edu.agh.to2.learnProgramming.model;

import pl.edu.agh.to2.learnProgramming.command.Command;
import pl.edu.agh.to2.learnProgramming.command.LoopCommand;
import pl.edu.agh.to2.learnProgramming.command.MoveCommand;

import java.util.ArrayList;
import java.util.List;

public class Level {
    private List<CommandType> commandTypes;
    private List<LevelPoint> fieldCoordinates;
    private List<Loop> loops = new ArrayList<>();
    private Turtle turtle;
    private int size;

    public Level(int size, List<CommandType> commandTypes, List<LevelPoint> fieldCoordinates, Turtle turtle) {
        this.size = size;
        this.commandTypes = commandTypes;
        this.fieldCoordinates = fieldCoordinates;
        this.turtle = turtle;
    }

    public List<CommandType> getCommandTypes() {
        return commandTypes;
    }

    public List<LevelPoint> getFieldCoordinates() {
        return fieldCoordinates;
    }

    public Turtle getTurtle() {
        return turtle;
    }

    public int getSize() {
        return this.size;
    }

    /**
     * Checks if all required fields have visited status.
     *
     * @return true : false
     */
    public boolean areAllFieldsVisited() {
        return this.fieldCoordinates.stream().allMatch(LevelPoint::isVisited);
    }

    /**
     * Checks if turtle position after move (command) execution is still on required path
     *
     * @return true : false
     */
    private boolean isMoveCorrect() {
        return this.fieldCoordinates.stream()
                .anyMatch(field -> field.getX() == turtle.getCoordinates().getX() && field.getY() == turtle.getCoordinates().getY());
    }

    /**
     * Sets given point (int x, int y) as visited.
     *
     * @param x - turtle x position
     * @param y - turtle y position
     */
    private void setPointVisited(int x, int y) {
        for (LevelPoint field : fieldCoordinates) {
            if (field.getX() == x && field.getY() == y)
                field.setVisited();
        }
    }

    /**
     * Processes selected by user commands (moves) to prepare loops;
     * Checks if user has used loop(s) and if so, then add every commands required by user times to the list.
     *
     * @param movesToExecute  - list of moves (commands) selected by user
     * @param loopsRepeatList - list of loops' repetitions number
     * @return - prepared list of commands (moves) to execute
     */
    private List<Command> prepareCommands(List<Command> movesToExecute, List<Integer> loopsRepeatList) {
        List<Command> commands = new ArrayList<>();
        int loopCounter = -1;
        int currCounter = 0;
        for (Command command : movesToExecute) {
            if (command.isLoop()) {
                LoopCommand loopCommand = (LoopCommand) command;
                loopCommand.setCurrCounter(currCounter);
                loopCommand.setLoopCounter(loopCounter);
                loopCommand.execute(loops, loopsRepeatList, commands);
                loopCounter = loopCommand.getLoopCounter();
                currCounter = loopCommand.getCurrCounter();
            } else {
                if (loopCounter >= 0) {
                    this.loops.get(loopCounter).addCommand(command);
                }
                commands.add(command);
            }
        }
        return commands;
    }

    /**
     * Executes chosen by user moves (commands) for turtle.
     *
     * @param movesToExecute  - list of moves (commands) selected by user
     * @param loopsRepeatList - list of loops' repetitions number
     * @return true - if moves (commands) executed correctly, false - otherwise
     */
    public boolean executeMoves(List<Command> movesToExecute, List<Integer> loopsRepeatList) {
        List<Command> moves = prepareCommands(movesToExecute, loopsRepeatList);
        setPointVisited(turtle.getCoordinates().getX(), turtle.getCoordinates().getY());
        for (Command command : moves) {
            MoveCommand moveCommand = (MoveCommand) command;
            moveCommand.execute();
            if (isMoveCorrect())
                setPointVisited(turtle.getCoordinates().getX(), turtle.getCoordinates().getY());
            else
                return false;
        }
        return true;
    }

}
