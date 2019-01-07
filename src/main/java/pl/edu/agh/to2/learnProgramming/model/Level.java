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
import pl.edu.agh.to2.learnProgramming.command.CommandType;
import pl.edu.agh.to2.learnProgramming.command.ComplexCommand;
import pl.edu.agh.to2.learnProgramming.command.MoveCommand;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Level {
    private List<CommandType> commandTypes;
    private List<LevelPoint> fieldCoordinates;
    private List<Loop> loops = new ArrayList<>();
    private Turtle turtle;
    private Turtle startingTurtle;
    private int size;

    public Level(int size, List<CommandType> commandTypes, List<LevelPoint> fieldCoordinates, Turtle turtle) {
        this.size = size;
        this.commandTypes = commandTypes;
        this.fieldCoordinates = fieldCoordinates;
        this.turtle = turtle;
        this.startingTurtle = new Turtle(turtle.getCoordinates().getX(), turtle.getCoordinates().getY(), turtle.getTurtleDirection());
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

    public Turtle getStartingTurtle() {
        return startingTurtle;
    }

    public int getSize() {
        return this.size;
    }

    public List<Loop> getLoops() {
        return this.loops;
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
    public void setPointVisited(int x, int y) {
        for (LevelPoint field : fieldCoordinates) {
            if (field.getX() == x && field.getY() == y)
                field.setVisited();
        }
    }

    /**
     * At first processes selected by user commands (moves) to prepare loops,
     * then checks if user has used loop(s) and if so, then add every commands required by user times to the list.
     * Executes chosen by user moves (commands) for turtle.
     *
     * @param movesToExecute  - list of moves (commands) selected by user
     * @param loopsRepeatList - list of loops' repetitions number
     * @return true - if moves (commands) executed correctly, false - otherwise
     */
    public boolean executeMoves(List<Command> movesToExecute) {
        List<Command> commands = new LinkedList<>();
        int loopCounter = -1;
        int currCounter = 0;
        for (Command command : movesToExecute) {
            command.setLevelCommands(commands);
            command.setLoopCounter(loopCounter);
            if (command.isComplex()) {
                ComplexCommand complexCommand = (ComplexCommand) command;
                complexCommand.setCurrCounter(currCounter);
                complexCommand.execute();
                loopCounter = complexCommand.getLoopCounter();
                currCounter = complexCommand.getCurrCounter();
            } else {
                MoveCommand moveCommand = (MoveCommand) command;
                moveCommand.prepare();
            }
        }
        setPointVisited(turtle.getCoordinates().getX(), turtle.getCoordinates().getY());
        for (Command command : commands) {
            MoveCommand moveCommand = (MoveCommand) command;
            moveCommand.setTurtle(turtle);
            moveCommand.execute();
            if (!isMoveCorrect())
                return false;
        }
        return true;
    }

}
