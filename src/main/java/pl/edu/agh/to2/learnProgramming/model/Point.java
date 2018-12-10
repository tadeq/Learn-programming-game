/**
 * @author
 *      Maciej Moskal
 *      Jakub Pajor
 *      Michał Zadora
 *
 * Model - point.
 * Contains information about:
 *      IntegerProperty x, y (observable coordinates)
 */

package pl.edu.agh.to2.learnProgramming.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Point {

    private IntegerProperty x;
    private IntegerProperty y;

    public Point(int x, int y) {
        this.x = new SimpleIntegerProperty(x);
        this.y = new SimpleIntegerProperty(y);
    }

    public int getX() {
        return x.get();
    }

    public IntegerProperty xProperty() {
        return x;
    }

    public int getY() {
        return y.get();
    }

    public IntegerProperty yProperty() {
        return y;
    }
}
