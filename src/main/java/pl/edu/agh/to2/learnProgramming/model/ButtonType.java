package pl.edu.agh.to2.learnProgramming.model;

public enum ButtonType {
    FORWARD("/images/forward.png"),
    RIGHT("/images/right.png"),
    LEFT("/images/left.png"),
    STARTLOOP("/images/startloop.png"),
    ENDLOOP("/images/endloop.png");

    private final String path;

    private ButtonType(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
