package pl.edu.agh.to2.learnProgramming;

import pl.edu.agh.to2.learnProgramming.command.CommandType;
import pl.edu.agh.to2.learnProgramming.model.*;

import java.util.Arrays;

public class LevelTest {
    private Level level = new Level(4, Arrays.asList(CommandType.FORWARD, CommandType.RIGHT),
            Arrays.asList(new LevelPoint(1, 1), new LevelPoint(2, 1), new LevelPoint(3, 1), new LevelPoint(3, 2)),
            new Turtle(1, 1, TurtleDirection.E));

//    @Test
//    public void testFieldsVisiting() {
//        level.executeMoves(Arrays.asList(CommandType.FORWARD, CommandType.FORWARD, CommandType.RIGHT), null);
//        assertFalse(level.areAllFieldsVisited());
//        level.executeMoves(Arrays.asList(CommandType.FORWARD), null);
//        assertTrue(level.areAllFieldsVisited());
//    }
//
//    @Test
//    public void testGoodMoves() {
//        assertTrue(level.executeMoves(Arrays.asList(CommandType.FORWARD, CommandType.FORWARD, CommandType.RIGHT, CommandType.FORWARD), null));
//    }
//
//    @Test
//    public void testTooMuchMoves() {
//        assertFalse(level.executeMoves(Arrays.asList(CommandType.FORWARD, CommandType.FORWARD, CommandType.RIGHT, CommandType.FORWARD, CommandType.FORWARD), null));
//    }
//
//    @Test
//    public void testBadMove() {
//        assertFalse(level.executeMoves(Arrays.asList(CommandType.LEFT, CommandType.FORWARD), null));
//    }
}
