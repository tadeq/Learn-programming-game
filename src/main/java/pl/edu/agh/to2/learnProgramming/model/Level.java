package pl.edu.agh.to2.learnProgramming.model;

import java.util.List;

public class Level {
    private List<MoveType> moveTypes;
    private List<LevelPoint> fieldCoordinates;
    private Turtle turtle;
    private int width;
    private int height;

    public Level(int width, int height, List<MoveType> moveTypes, List<LevelPoint> fieldCoordinates, Turtle turtle) {
        this.width = width;
        this.height = height;
        this.moveTypes = moveTypes;
        this.fieldCoordinates = fieldCoordinates;
        this.turtle = turtle;
    }

    public List<MoveType> getMoveTypes() {
        return moveTypes;
    }

    public List<LevelPoint> getFieldCoordinates() {
        return fieldCoordinates;
    }

    public Turtle getTurtle() {
        return turtle;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean allVisited() {
        return this.fieldCoordinates.stream().allMatch(LevelPoint::isVisited);
    }

    public boolean isMoveCorrect() {
        return this.fieldCoordinates.stream()
                .anyMatch(field -> field.getX() == turtle.getCoordinates().getX() && field.getY() == turtle.getCoordinates().getY());
    }

    public void visitPoint(int x, int y) {
        for (LevelPoint field : fieldCoordinates) {
            if (field.getX() == x && field.getY() == y)
                field.setVisited();
        }
    }

    public boolean executeMoves(List<MoveType> movesToExecute) {
        // TODO rozdzielanie komend na pętle i ruchy, wzorzec command
        // TODO w zolwiu tylko metody moveForward(), turnRight(), turnLeft()
        visitPoint(turtle.getCoordinates().getX(), turtle.getCoordinates().getY());
        for (MoveType moveToExecute : movesToExecute) {
            switch (moveToExecute){
                case FORWARD:
                    turtle.moveForward();
                case LEFT:
                    turtle.turnLeft();
                case RIGHT:
                    turtle.turnRight();
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
