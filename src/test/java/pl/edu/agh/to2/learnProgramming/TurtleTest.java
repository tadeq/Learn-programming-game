package pl.edu.agh.to2.learnProgramming;

import org.junit.Test;
import pl.edu.agh.to2.learnProgramming.model.Turtle;
import pl.edu.agh.to2.learnProgramming.model.TurtleDirection;

import static org.junit.Assert.*;

public class TurtleTest {
    private Turtle turtle = new Turtle(1,1, TurtleDirection.S);

    @Test
    public void testGetCoordinates(){
        assertEquals(1,turtle.getCoordinates().getX());
        assertEquals(1,turtle.getCoordinates().getY());
    }

    @Test
    public void testGetTurtleDirection(){
        assertEquals(TurtleDirection.S,turtle.getTurtleDirection());
    }

    @Test
    public void testTurn(){
        turtle.turnRight();
        turtle.turnRight();
        assertEquals(TurtleDirection.N, turtle.getTurtleDirection());
        turtle.turnLeft();
        assertEquals(TurtleDirection.W, turtle.getTurtleDirection());
    }

    @Test
    public void testMoveForward(){
        turtle.moveForward();
        assertEquals(1,turtle.getCoordinates().getX());
        assertEquals(2,turtle.getCoordinates().getY());
    }
}
