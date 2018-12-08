package pl.edu.agh.to2.learnProgramming.model;

public enum CommandType {
    FORWARD("/images/forward.png"),
    RIGHT("/images/right.png"),
    LEFT("/images/left.png"),
    STARTLOOP("/images/startLoop.png"),
    ENDLOOP("/images/endLoop.png");

    private final String path;

    private CommandType(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
