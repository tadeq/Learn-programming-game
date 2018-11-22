package pl.edu.agh.to2.learnprogramming;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.logging.Logger;

public class Main extends Application {

    private static final Logger log = Logger.getLogger(Main.class.toString());

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/views/MainScreen.fxml"));
        BorderPane pane = new BorderPane(loader.load());
        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
