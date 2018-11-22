package pl.edu.agh.to2.learnprogramming.Model;

import pl.edu.agh.to2.learnprogramming.Controllers.LevelController;

import java.util.List;

public class Level {
    private List<ButtonType> buttonTypes;
    private List<LevelPoint> fieldCoordinates;
    private Turtle turtle;
    private LevelController levelController;

    public Level(List<ButtonType> buttonTypes, List<LevelPoint> fieldCoordinates, Turtle turtle) {
        this.buttonTypes = buttonTypes;
        this.fieldCoordinates = fieldCoordinates;
        this.turtle = turtle;
    }

    public List<ButtonType> getButtonTypes() {
        return buttonTypes;
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

    public boolean canMoveTo(int x, int y) {
        return this.fieldCoordinates.stream().anyMatch(levelPoint -> levelPoint.x == x && levelPoint.y == y);
    }

    public void visitPoint(int x, int y) {
        for (LevelPoint field : fieldCoordinates) {
            if (field.x == x && field.y == y)
                field.setVisited();
        }
    }

    public boolean executeMoves(List<ButtonType> movesToExecute) {
        int tmpX = this.turtle.getCoordinates().x;
        int tmpY = this.turtle.getCoordinates().y;
        visitPoint(tmpX, tmpY);
        TurtleDirection tmpTurtleDirection = this.turtle.getTurtleDirection();

        for (ButtonType moveToExecute : movesToExecute) {
            switch (moveToExecute) {
                case LEFT: {
                    tmpTurtleDirection = tmpTurtleDirection.turnLeft();
                    break;
                }
                case RIGHT: {
                    tmpTurtleDirection = tmpTurtleDirection.turnRight();
                    break;
                }
                case FORWARD: {
                    switch (tmpTurtleDirection) {
                        case N: {
                            tmpY--;
                            break;
                        }
                        case E: {
                            tmpX++;
                            break;

                        }
                        case S: {
                            tmpY++;
                            break;

                        }
                        case W: {
                            tmpX--;
                            break;
                        }
                    }
                    if (!canMoveTo(tmpX, tmpY))
                        return false;
                    else
                        visitPoint(tmpX, tmpY);
                    break;
                }
                // TODO
                //case STARTLOOP:
                //case ENDLOOP:
            }
            this.turtle.setTurtlePosition(tmpX, tmpY, tmpTurtleDirection);
            this.levelController.setTurtleImagePosition( new Point(tmpX, tmpY), tmpTurtleDirection);
        }
        return true;
    }
}
