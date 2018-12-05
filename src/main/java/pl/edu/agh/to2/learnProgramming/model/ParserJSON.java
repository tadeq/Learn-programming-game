package pl.edu.agh.to2.learnProgramming.model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ParserJSON {

    private JSONObject jsonFile;
    private int amountLevels;

    public ParserJSON(String path) throws IOException, ParseException {

        this.jsonFile = readJson(path);
        this.amountLevels = this.jsonFile.size();
    }

    public JSONObject readJson(String path) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        File json = new File(path);
        FileReader f = new FileReader(json);
        return (JSONObject) parser.parse(f);
    }

    public int getSizeBoard(int numberLevel) {
        JSONObject level = (JSONObject) this.jsonFile.get(String.valueOf(numberLevel));
        JSONObject board = (JSONObject) level.get("Board");
        return (int) board.get("size");
    }

    public Point getTurtlePosition(int numberLevel) {
        JSONObject level = (JSONObject) this.jsonFile.get(String.valueOf(numberLevel));
        JSONObject turtlePosition = (JSONObject) level.get("TurtlePosition");
        int x = (int) turtlePosition.get("x");
        int y = (int) turtlePosition.get("y");
        return new Point(x, y);
    }

    public List<MoveType> getAvailableMoves(int numberLevel) {
        JSONObject level = (JSONObject) this.jsonFile.get(String.valueOf(numberLevel));
        JSONArray moves = (JSONArray) level.get("MoveTypes");
        List<MoveType> availableMoves = new ArrayList<MoveType>();
        for (int i = 0; i < moves.size(); i++) {
            availableMoves.add(MoveType.valueOf((String) moves.get(i)));
        }
        return availableMoves;
    }

    public List<Point> getLevelFields(int numberLevel) {
        JSONObject level = (JSONObject) this.jsonFile.get(String.valueOf(numberLevel));
        JSONObject board = (JSONObject) level.get("Board");
        JSONObject map = (JSONObject) board.get("map");
        JSONObject boardSize = (JSONObject) board.get("size");
        int x = (int) boardSize.get("x");
        int y = (int) boardSize.get("y");
        List<Point> fieldList = new ArrayList<>();
        for (int i = 0; i < y; i++) {
            String row = (String) map.get(String.valueOf(i));
            for (int j = 0; j < row.length(); j++) {
                if (row.toCharArray()[j] == '*')
                    fieldList.add(new Point(j,i));
            }
        }
        return fieldList;
    }

    public int getAmountLevels() {
        return amountLevels;
    }
}
