package pl.edu.agh.to2.sorryimchillin.Model;

public enum ButtonType {
    FORWARD("DUPA"),
    RIGHT("DUPA"),
    LEFT("DUPA"),
    STARTLOOP("DUPA"),
    ENDLOOP("DUPA");

    private final String path;

    private ButtonType(String path){
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
