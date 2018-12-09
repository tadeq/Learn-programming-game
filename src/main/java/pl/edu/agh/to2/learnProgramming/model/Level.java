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

    public boolean allVisited() {
        return this.fieldCoordinates.stream().allMatch(LevelPoint::isVisited);
    }

    private boolean isMoveCorrect() {
        return this.fieldCoordinates.stream()
                .anyMatch(field -> field.getX() == turtle.getCoordinates().getX() && field.getY() == turtle.getCoordinates().getY());
    }

    private void visitPoint(int x, int y) {
        for (LevelPoint field : fieldCoordinates) {
            if (field.getX() == x && field.getY() == y)
                field.setVisited();
        }
    }

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

    public boolean executeMoves(List<CommandType> movesToExecute, List<Integer> loopsRepeatList) {
        List<CommandType> moves = prepareCommands(movesToExecute, loopsRepeatList);
        visitPoint(turtle.getCoordinates().getX(), turtle.getCoordinates().getY());
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
                visitPoint(turtle.getCoordinates().getX(), turtle.getCoordinates().getY());
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
