package pl.edu.agh.to2.learnProgramming.Model;

public enum TurtleDirection {
    N(-90),
    E(0),
    S(90),
    W(180);

    private final int rotation;

    TurtleDirection(int value) {
        this.rotation = value;
    }

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
