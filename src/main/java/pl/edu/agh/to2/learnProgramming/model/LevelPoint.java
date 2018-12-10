/**
 * @author
 *      Maciej Moskal
 *      Jakub Pajor
 *      Michał Zadora
 *
 * Model - level point.
 * Contains information about:
 *      IntegerProperty x, y (observable coordinates)
 *      BooleanProperty visited (observable, if has been visited).
 */

package pl.edu.agh.to2.learnProgramming.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class LevelPoint extends Point {
    private BooleanProperty visited;

    public LevelPoint(int x, int y) {
        super(x, y);
        this.visited = new SimpleBooleanProperty(false);
    }

    public BooleanProperty visitedProperty() {
        return this.visited;
    }

    public boolean isVisited() {
        return this.visited.get();
    }

    public void setVisited() {
        this.visited.setValue(true);
    }

}
