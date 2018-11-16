package pl.edu.agh.to2.sorryimchillin.Model;

public enum ButtonType {
    FORWARD("/../resources/images/forward.png"),
    RIGHT("/../resources/images/right.png"),
    LEFT("/../resources/images/left.png"),
    STARTLOOP("/../resources/images/startloop.png"),
    ENDLOOP("/../resources/images/endloop.png");

    private final String path;

    private ButtonType(String path){
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
