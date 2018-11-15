package pl.edu.agh.to2.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
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
		loader.setLocation(this.getClass().getResource("/views/Level.fxml"));
		SplitPane pane2 = new SplitPane(loader.load());
		pane.setCenter(pane2);
		Scene scene = new Scene (pane);
		primaryStage.setScene(scene);
		primaryStage.show();
    }
}
