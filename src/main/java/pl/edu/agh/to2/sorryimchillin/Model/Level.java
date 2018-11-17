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
        return turtle.makeMoves(movesToExecute, this.Coordinates);
    }


}
