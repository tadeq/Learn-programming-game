package pl.edu.agh.to2.sorryimchillin.Model;

import pl.edu.agh.to2.sorryimchillin.Utilities.LevelPoint;

import java.awt.*;
import java.util.List;

public class Level {
    private List<ButtonType> buttonTypes;
    private List<LevelPoint> Coordinates;
    private Turtle turtle;

    public Level(List<ButtonType> buttonTypes, List<LevelPoint> coordinates, Turtle turtle) {
        this.buttonTypes = buttonTypes;
        Coordinates = coordinates;
        this.turtle = turtle;
    }

    public List<ButtonType> getButtonTypes() {
        return buttonTypes;
    }

    public List<LevelPoint> getCoordinates() {
        return Coordinates;
    }

    public Turtle getTurtle() {
        return turtle;
    }

    public boolean executeMoves(List<ButtonType> movesToExecute) {
        int tmpX = this.turtle.getCoordinates().x;
        int tmpY = this.turtle.getCoordinates().y;
        TurtleDirection tmpTurtleDirection = this.turtle.getTurtleDirection();
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
            for (LevelPoint levelPoint : this.Coordinates){
                if(levelPoint.x == tmpX && levelPoint.y == tmpY){
                    isSuchAPoint = true;
                    levelPoint.setIsVisited();
                }
            }

            if(!isSuchAPoint) {
                return false;
            }
        }

        this.turtle.setTurtlePosition(tmpX, tmpY, tmpTurtleDirection);
        return true;
    }


}
