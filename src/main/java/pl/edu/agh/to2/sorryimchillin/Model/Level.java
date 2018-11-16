package pl.edu.agh.to2.sorryimchillin.Model;

import java.awt.*;
import java.util.List;

public class Level {
    private List<ButtonType> buttonTypes;
    private List<Point> Coordinates;
    private Point turtleCoordinates;

    public Level(List<ButtonType> buttonTypes, List<Point> coordinates, Point turtleCoordinates) {
        this.buttonTypes = buttonTypes;
        Coordinates = coordinates;
        this.turtleCoordinates = turtleCoordinates;
    }

    public List<ButtonType> getButtonTypes() {
        return buttonTypes;
    }

    public List<Point> getCoordinates() {
        return Coordinates;
    }

    public Point getTurtleCoordinates() {
        return turtleCoordinates;
    }
}
