/**
 * @author
 *      Maciej Moskal
 *      Jakub Pajor
 *      Michał Zadora
 *
 * Enum - turtle direction.
 * All directions contain its proper angle.
 * Available commands:
 *      N
 *      S
 *      W
 *      E
 */

package pl.edu.agh.to2.learnProgramming.model;

public enum TurtleDirection {
    N(-90),
    E(0),
    S(90),
    W(180);

    private final int rotation;

    TurtleDirection(int value) {
        this.rotation = value;
    }

    /**
     * Sets current direction to next on the right.
     * @return this
     */
    public TurtleDirection turnRight() {
        switch (this) {
            case N:
                return E;
            case E:
                return S;
            case S:
                return W;
            case W:
                return N;
        }
        return this;
    }


    /**
     * Sets current direction to next on the left.
     * @return this
     */
    public TurtleDirection turnLeft() {
        switch (this) {
            case N:
                return W;
            case W:
                return S;
            case S:
                return E;
            case E:
                return N;
        }
        return this;
    }

    public int getRotation() {
        return rotation;
    }
}
