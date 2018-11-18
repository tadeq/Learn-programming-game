package pl.edu.agh.to2.sorryimchillin.Model;

import pl.edu.agh.to2.sorryimchillin.Utilities.LevelPoint;

import java.awt.*;
import java.util.List;

public class Turtle {
    private Point coordinates;
    private TurtleDirection turtleDirection;
    public Turtle(int x, int y, TurtleDirection turtleDirection){
        this.coordinates = new Point(x, y);
        this.turtleDirection = turtleDirection;
    }

    public void setTurtlePosition(int x, int y, TurtleDirection turtleDirection){
        this.coordinates.setLocation(x, y);
        this.turtleDirection = turtleDirection;
    }


    public Point getCoordinates(){
        return this.coordinates;
    }

    public TurtleDirection getTurtleDirection() {
        return turtleDirection;
    }
}
