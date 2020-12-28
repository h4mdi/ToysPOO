package Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class MainClass extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent anchorPane  = FXMLLoader.load(getClass().getResource("/FXML/login.fxml"));

        primaryStage.setTitle("Ma boutique");
        Scene scene = new Scene(anchorPane);
        scene.getStylesheets().add("/FXML/login.css");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);

    }
}
