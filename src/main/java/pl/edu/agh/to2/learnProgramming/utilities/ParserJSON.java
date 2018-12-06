package pl.edu.agh.to2.learnProgramming.utilities;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import pl.edu.agh.to2.learnProgramming.model.*;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ParserJSON {

    private JSONObject jsonFile;
    private int amountLevels;
    private int numberLevel;

    public ParserJSON(String path) throws IOException, ParseException {
        this.jsonFile = readJson(path);
        this.amountLevels = this.jsonFile.size();
    }

    public void setNumberLevel(int numberLevel) {
        this.numberLevel = numberLevel;
    }

    private JSONObject readJson(String path) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        File json = new File(path);
        FileReader f = new FileReader(json);
        return (JSONObject) parser.parse(f);
    }

    public int getSizeBoard() {
        JSONObject level = (JSONObject) this.jsonFile.get(String.valueOf(numberLevel));
        JSONObject board = (JSONObject) level.get("Board");
        return ((Long) board.get("size")).intValue();
    }

    public Turtle getTurtlePosition() {
        JSONObject level = (JSONObject) this.jsonFile.get(String.valueOf(numberLevel));
        JSONObject turtlePosition = (JSONObject) level.get("TurtlePosition");
        int x = ((Long) turtlePosition.get("x")).intValue();
        int y = ((Long) turtlePosition.get("y")).intValue();
        String direction = (String) turtlePosition.get("direction");
        TurtleDirection turtleDirection = TurtleDirection.valueOf(direction);
        return new Turtle(x, y, turtleDirection);
    }

    public List<CommandType> getAvailableMoves() {
        JSONObject level = (JSONObject) this.jsonFile.get(String.valueOf(numberLevel));
        JSONArray moves = (JSONArray) level.get("MoveTypes");
        List<CommandType> availableMoves = new ArrayList<>();
        for (int i = 0; i < moves.size(); i++) {
            availableMoves.add(CommandType.valueOf((String) moves.get(i)));
        }
        return availableMoves;
    }

    public List<LevelPoint> getLevelFields() {
        JSONObject level = (JSONObject) this.jsonFile.get(String.valueOf(numberLevel));
        JSONObject board = (JSONObject) level.get("Board");
        JSONArray map = (JSONArray) board.get("map");
        int size = ((Long) board.get("size")).intValue();
        List<LevelPoint> fieldList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            String row = (String) map.get(i);
            for (int j = 0; j < row.length(); j++) {
                if (row.toCharArray()[j] == '*')
                    fieldList.add(new LevelPoint(j, i));
            }
        }
        return fieldList;
    }

    public int getAmountLevels() {
        return amountLevels;
    }
}
