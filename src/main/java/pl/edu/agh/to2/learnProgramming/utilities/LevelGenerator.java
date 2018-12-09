package pl.edu.agh.to2.learnProgramming.utilities;

import pl.edu.agh.to2.learnProgramming.model.*;

public class LevelGenerator {

    private boolean hasNext;
    private ParserJSON parser;

    public LevelGenerator() {
        hasNext = true;
        String path = this.getClass().getResource("/configs/levels.json").getPath().replaceAll("%20"," ");
        try {
            parser = new ParserJSON(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean hasNext() {
        return hasNext;
    }

    public Level generate(int levelNumber) {
        parser.setNumberLevel(levelNumber);
        Level level = new Level(parser.getSizeBoard(), parser.getAvailableMoves(), parser.getLevelFields(), parser.getTurtlePosition());
        hasNext = levelNumber < parser.getAmountLevels();
        return level;
    }
}
