/**
 * @author Maciej Moskal
 * Jakub Pajor
 * Michał Zadora
 * <p>
 * Model - turtle.
 * Contains information about:
 * Point coordinates
 * ObjectProperty<TurtleDirection> turtleDirection
 */

package pl.edu.agh.to2.learnProgramming.model;

import javafx.animation.PauseTransition;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.util.Duration;

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

    /**
     * Moves forward depending on the current direction.
     */
    public void moveForward() {
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

    /**
     * Sets its direction to the next left.
     */
    public void turnLeft() {
        turtleDirection.setValue(turtleDirection.get().turnLeft());
    }

    /**
     * Sets its direction to the next right.
     */
    public void turnRight() {
        turtleDirection.setValue(turtleDirection.get().turnRight());
    }
}
