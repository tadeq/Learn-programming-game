/**
 * @author
 *      Maciej Moskal
 *      Jakub Pajor
 *      Michał Zadora
 *
 * Model - level.
 * Contains information about:
 *     List<CommandType> commandTypes
 *     List<LevelPoint> fieldCoordinates
 *     List<Loop> loops
 *     Turtle turtle
 *     int size
 */
package pl.edu.agh.to2.learnProgramming.model;

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
     * @return true : false
     */
    public boolean areAllFieldsVisited() {
        return this.fieldCoordinates.stream().allMatch(LevelPoint::isVisited);
    }

    /**
     * Checks if turtle position after move (command) execution is still on required path
     * @return true : false
     */
    private boolean isMoveCorrect() {
        return this.fieldCoordinates.stream()
                .anyMatch(field -> field.getX() == turtle.getCoordinates().getX() && field.getY() == turtle.getCoordinates().getY());
    }

    /**
     * Sets given point (int x, int y) as visited.
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
     * @param movesToExecute - list of moves (commands) selected by user
     * @param loopsRepeatList - list of loops' repetitions number
     * @return - prepared list of commands (moves) to execute
     */
    private List<CommandType> prepareCommands(List<CommandType> movesToExecute, List<Integer> loopsRepeatList) {
        List<CommandType> commands = new ArrayList<>();
        int loopCounter = -1;
        int currCounter = 0;
        for (CommandType command : movesToExecute) {
            switch (command) {
                case STARTLOOP:
                    // Biore pierwszy element i po jego odczytaniu usuwam go z listy. Start loopow nie bedzie wiecej niz
                    // elementow w liscie wiec nie trzeba sprawdzacz czy nie pusta
                    currCounter = loopsRepeatList.get(0);
                    loopsRepeatList.remove(0);
                    this.loops.add(new Loop(currCounter > 0 ? currCounter : 1));
                    loopCounter++;
                    commands.add(command);
                    break;
                case ENDLOOP:
                    for (int x = 0; x < this.loops.get(loopCounter).getCounter() - 1; x++) {
                        commands.addAll(this.loops.get(loopCounter).getCommands());
                    }
                    loopCounter--;
                    if (loopCounter >= 0) {
                        for (int x = 0; x < this.loops.get(loopCounter + 1).getCounter(); x++) {
                            this.loops.get(loopCounter).addCommands(this.loops.get(loopCounter + 1).getCommands());
                        }
                    }
                    this.loops.remove(loopCounter + 1);
                    commands.add(command);
                    break;
                default:
                    if (loopCounter >= 0) {
                        this.loops.get(loopCounter).addCommand(command);
                        commands.add(command);
                    } else
                        commands.add(command);
            }
        }
        return commands;
    }

    /**
     * Executes chosen by user moves (commands) for turtle.
     *
     * @param movesToExecute - list of moves (commands) selected by user
     * @param loopsRepeatList - list of loops' repetitions number
     * @return true - if moves (commands) executed correctly, false - otherwise
     */
    public boolean executeMoves(List<CommandType> movesToExecute, List<Integer> loopsRepeatList) {
        List<CommandType> moves = prepareCommands(movesToExecute, loopsRepeatList);
        setPointVisited(turtle.getCoordinates().getX(), turtle.getCoordinates().getY());
        for (CommandType moveToExecute : moves) {
            switch (moveToExecute) {
                case FORWARD:
                    turtle.moveForward();
                    break;
                case LEFT:
                    turtle.turnLeft();
                    break;
                case RIGHT:
                    turtle.turnRight();
                    break;
            }
            if (isMoveCorrect())
                setPointVisited(turtle.getCoordinates().getX(), turtle.getCoordinates().getY());
            else
                return false;
//            try {
//                Thread.sleep(500);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }
        return true;
    }

}
