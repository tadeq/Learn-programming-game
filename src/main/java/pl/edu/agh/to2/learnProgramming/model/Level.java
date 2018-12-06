package pl.edu.agh.to2.learnProgramming.model;

import java.util.List;

public class Level {
    private List<CommandType> commandTypes;
    private List<LevelPoint> fieldCoordinates;
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

    //private Loop loop; - obecnie otwarta petla, w klasie loop lista ruchow, ilosc wywolan

    public boolean executeMoves(List<CommandType> movesToExecute) {
        visitPoint(turtle.getCoordinates().getX(), turtle.getCoordinates().getY());
        for (CommandType moveToExecute : movesToExecute) {
            switch (moveToExecute) {
                case FORWARD:
                    // if loop!=null
                    turtle.moveForward();
                    // else loop.add(moveToExecute)
                    break;
                case LEFT:
                    turtle.turnLeft();
                    break;
                case RIGHT:
                    turtle.turnRight();
                    break;
                case STARTLOOP:
                    //loop = new Loop(calls) //calls - ilosc wywolan
                    break;
                case ENDLOOP:
                    //loop.execute(turtle)
                    break;
                //TODO loop
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
