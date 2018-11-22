package pl.edu.agh.to2.learnProgramming.model;

import pl.edu.agh.to2.learnProgramming.controllers.LevelController;

import java.util.List;

public class Level {
    private List<MoveType> moveTypes;
    private List<LevelPoint> fieldCoordinates;
    private Turtle turtle;
    private LevelController levelController;

    public Level(List<MoveType> moveTypes, List<LevelPoint> fieldCoordinates, Turtle turtle) {
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

    public void setLevelController(LevelController levelController) {
        this.levelController = levelController;
    }

    public boolean allVisited() {
        return this.fieldCoordinates.stream().allMatch(LevelPoint::isVisited);
    }

    public boolean isMoveCorrect() {
        return this.fieldCoordinates.stream()
                .anyMatch(field -> field.x == turtle.getCoordinates().x && field.y == turtle.getCoordinates().y);
    }

    public void visitPoint(int x, int y) {
        for (LevelPoint field : fieldCoordinates) {
            if (field.x == x && field.y == y)
                field.setVisited();
        }
    }

    public boolean executeMoves(List<MoveType> movesToExecute) {
        visitPoint(turtle.getCoordinates().x,turtle.getCoordinates().y);
        for (MoveType moveToExecute : movesToExecute) {
            turtle.move(moveToExecute);
            if (isMoveCorrect())
                visitPoint(turtle.getCoordinates().x, turtle.getCoordinates().y);
            else
                return false;
            this.levelController.setTurtleImagePosition(new Point(turtle.getCoordinates().x, turtle.getCoordinates().y), turtle.getTurtleDirection());
        }
        return true;
    }
}
