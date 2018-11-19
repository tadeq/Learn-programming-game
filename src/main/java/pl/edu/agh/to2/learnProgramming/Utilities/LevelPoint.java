package pl.edu.agh.to2.learnProgramming.Utilities;

import java.awt.*;

public class LevelPoint extends Point {
    private boolean visited;

    public LevelPoint(int x, int y) {
        super(x, y);
        this.visited = false;
    }

    public boolean isVisited() {
        return this.visited;
    }

    public void setVisited() {
        this.visited = true;
    }

    /* Method not really necessary */
    public void setNotVisited() {
        this.visited = false;
    }
}
