package pl.edu.agh.to2.learnProgramming.command;

import javafx.scene.Node;
import javafx.scene.image.ImageView;
import pl.edu.agh.to2.learnProgramming.model.Turtle;

public class MoveForwardCommand implements MoveCommand {
    private Turtle turtle;

    private ImageView img;

    public MoveForwardCommand() {
        this.img = new ImageView(CommandType.FORWARD.getPath());
        img.setFitHeight(40);
        img.setFitWidth(40);
    }

    @Override
    public void execute() {
        turtle.moveForward();
    }

    @Override
    public void setTurtle(Turtle turtle) {
        this.turtle = turtle;
    }

    @Override
    public Node getImage() {
        return img;
    }
}
