package pl.edu.agh.to2.learnProgramming.model;


public class Turtle {
    private Point coordinates;
    private TurtleDirection turtleDirection;

    public Turtle(int x, int y, TurtleDirection turtleDirection) {
        this.coordinates = new Point(x, y);
        this.turtleDirection = turtleDirection;
    }

    public void setTurtlePosition(int x, int y, TurtleDirection turtleDirection) {
        this.coordinates.setLocation(x, y);
        this.turtleDirection = turtleDirection;
    }

    public Point getCoordinates() {
        return this.coordinates;
    }

    public TurtleDirection getTurtleDirection() {
        return turtleDirection;
    }

    public void move(MoveType moveToExecute){
        switch (moveToExecute) {
            case LEFT: {
                turtleDirection = turtleDirection.turnLeft();
                break;
            }
            case RIGHT: {
                turtleDirection = turtleDirection.turnRight();
                break;
            }
            case FORWARD: {
                switch (turtleDirection) {
                    case N: {
                        getCoordinates().y--;
                        break;
                    }
                    case E: {
                        getCoordinates().x++;
                        break;

                    }
                    case S: {
                        getCoordinates().y++;
                        break;

                    }
                    case W: {
                        getCoordinates().x--;
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
