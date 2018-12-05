package pl.edu.agh.to2.learnProgramming.utilities;

import pl.edu.agh.to2.learnProgramming.model.*;

import java.util.Arrays;
import java.util.List;

public class LevelGenerator {

    private boolean hasNext;

    public LevelGenerator() {
        hasNext = true;
    }

    public boolean hasNext() {
        return hasNext;
    }

    // TODO otrzymuje numer poziomu i zwraca odpowiedni poziom wczytany z pliku json
    public Level generate(int levelNumber) {
        Level level;
        int size;
        List<MoveType> moveTypes;
        List<LevelPoint> fields;
        Turtle turtle;
        if (levelNumber == 1) {
            size = 6;
            moveTypes = Arrays.asList(MoveType.FORWARD);
            fields = Arrays.asList(new LevelPoint(0, 1), new LevelPoint(1, 1), new LevelPoint(2, 1), new LevelPoint(3, 1));
            turtle = new Turtle(0, 1, TurtleDirection.E);
        } else {
            size = 4;
            moveTypes = Arrays.asList(MoveType.FORWARD, MoveType.RIGHT, MoveType.LEFT);
            fields = Arrays.asList(new LevelPoint(1, 0), new LevelPoint(1, 1), new LevelPoint(2, 1));
            turtle = new Turtle(1, 0, TurtleDirection.S);
        }
        hasNext = levelNumber < 2;  //TODO rozpoznawanie hasNext z pliku JSON
        level = new Level(size, moveTypes, fields, turtle);
        return level;
    }
}
