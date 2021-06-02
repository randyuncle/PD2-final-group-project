package application;
import gamesettings.GameManager;
import gamesettings.SceneCreator;
import javafx.application.Application;
import javafx.stage.Stage;
/*
*To start the game
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        GameManager.setPrimaryStage(primaryStage);
        primaryStage.setTitle("PD2 final project");
        primaryStage.setScene(SceneCreator.createStartScene());
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
    