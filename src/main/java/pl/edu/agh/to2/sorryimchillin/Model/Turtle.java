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

    public boolean makeMoves(List<ButtonType> movesToExecute, List<LevelPoint> coordinates) {
        int tmpX = this.coordinates.x;
        int tmpY = this.coordinates.y;
        TurtleDirection tmpTurtleDirection = this.turtleDirection;
        Turtle tmpTurtle = new Turtle(tmpX, tmpY, tmpTurtleDirection);

        for(int i = 0; i < movesToExecute.size(); i++){
            switch (movesToExecute.get(i)){
                case LEFT:{
                    tmpTurtleDirection = tmpTurtleDirection.turnLeft();
                    break;
                }
                case RIGHT:{
                    tmpTurtleDirection = tmpTurtleDirection.turnRight();
                    break;
                }
                case FORWARD:{
                    switch (tmpTurtleDirection){
                        case N:{
                            tmpY--;
                            break;
                        }
                        case E:{
                            tmpX++;
                            break;

                        }
                        case S:{
                            tmpY++;
                            break;

                        }
                        case W: {
                            tmpX--;
                            break;
                        }
                    }

                }

                // TODO
                //case STARTLOOP:
                //case ENDLOOP:
            }

            boolean isSuchAPoint = false;
            for (LevelPoint levelPoint : coordinates){
                if(levelPoint.x == tmpX && levelPoint.y == tmpY){
                    isSuchAPoint = true;
                    levelPoint.setIsVisited();
                }
            }

            if(!isSuchAPoint) {
                return false;
            }
        }

        this.setTurtlePosition(tmpX, tmpY, tmpTurtleDirection);
        return true;
    }

    public Point getCoordinates(){
        return this.coordinates;
    }

}
