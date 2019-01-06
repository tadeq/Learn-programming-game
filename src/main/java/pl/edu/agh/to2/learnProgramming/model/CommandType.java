/**
 * @author Maciej Moskal
 * Jakub Pajor
 * Michał Zadora
 * <p>
 * Enum - command types.
 * All command types contain its picture path.
 * Available commands:
 * FORWARD
 * RIGHT
 * LEFT
 * STARTLOOP
 * ENDLOOP
 */
package pl.edu.agh.to2.learnProgramming.model;

public enum CommandType {
    FORWARD("/images/forward.png"),
    RIGHT("/images/right.png"),
    LEFT("/images/left.png"),
    STARTLOOP("/images/startLoop.png"),
    ENDLOOP("/images/endLoop.png"),
    PROCEDURE("/images/procedure.png");

    private final String path;

    private CommandType(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
