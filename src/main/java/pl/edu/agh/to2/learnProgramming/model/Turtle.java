package pl.edu.agh.to2.learnProgramming.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Turtle {
    private Point coordinates;
    private ObjectProperty<TurtleDirection> turtleDirection;

    public Turtle(int x, int y, TurtleDirection turtleDirection) {
        this.coordinates = new Point(x, y);
        this.turtleDirection = new SimpleObjectProperty<>(turtleDirection);
    }

    public Point getCoordinates() {
        return this.coordinates;
    }

    public TurtleDirection getTurtleDirection() {
        return turtleDirection.get();
    }

    public ObjectProperty<TurtleDirection> turtleDirectionProperty() {
        return turtleDirection;
    }

    public void move(MoveType moveToExecute) {
        switch (moveToExecute) {
            case LEFT: {
                turtleDirection.setValue(turtleDirection.get().turnLeft());
                break;
            }
            case RIGHT: {
                turtleDirection.setValue(turtleDirection.get().turnRight());
                break;
            }
            case FORWARD: {
                switch (turtleDirection.getValue()) {
                    case N: {
                        getCoordinates().yProperty().setValue(getCoordinates().getY() - 1);
                        break;
                    }
                    case E: {
                        getCoordinates().xProperty().setValue(getCoordinates().getX() + 1);
                        break;

                    }
                    case S: {
                        getCoordinates().yProperty().setValue(getCoordinates().getY() + 1);
                        break;

                    }
                    case W: {
                        getCoordinates().xProperty().setValue(getCoordinates().getX() - 1);
                        break;
                    }
                }
            }
            // TODO
            //case STARTLOOP:
            //case ENDLOOP:
        }
    }

}