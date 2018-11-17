package pl.edu.agh.to2.sorryimchillin.Utilities;

import java.awt.*;

public class LevelPoint extends Point {
    private boolean isVisited;
    public LevelPoint(int x, int y){
        super(x, y);
        this.isVisited = false;
    }

    public boolean getIsVisited(){
        return this.isVisited;
    }

    public void setIsVisited(){
        this.isVisited = true;
    }

    /* Method not really necessary */
    public void setIsNotVisited(){
        this.isVisited = false;
    }
}
