package pl.edu.agh.to2.sorryimchillin.Model;

import java.util.Optional;

public enum TurtleDirection {
    N, E, S, W;

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
}
