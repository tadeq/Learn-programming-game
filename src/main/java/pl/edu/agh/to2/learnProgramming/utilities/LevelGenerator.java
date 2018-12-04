package pl.edu.agh.to2.learnProgramming.utilities;

import pl.edu.agh.to2.learnProgramming.model.*;

import java.util.Arrays;
import java.util.List;

public class LevelGenerator {
    // TODO
    // otrzymuje numer poziomu i zwraca odpowiedni poziom wczytany z pliku json
    public Level generate(int levelNumber) {
        Level level;
        int width = 6;
        int height = 4;
        List<MoveType> moveTypes = Arrays.asList(MoveType.FORWARD, MoveType.RIGHT, MoveType.LEFT);
        List<LevelPoint> squares = Arrays.asList(new LevelPoint(2, 2), new LevelPoint(0, 1), new LevelPoint(1, 1), new LevelPoint(2, 1));
        Turtle turtlePosition = new Turtle(0, 1, TurtleDirection.E);
        level = new Level(width, height, moveTypes, squares, turtlePosition);
        return level;
    }
}
