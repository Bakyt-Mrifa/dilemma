package kg.rifah.Dilemma;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage mainStage) throws IOException {
        Parent parent= FXMLLoader.load(getClass().getResource("/DilemmaForm.fxml"));
        mainStage.setTitle("Дилемма");
        mainStage.setScene(new Scene(parent));
        mainStage.setResizable(false);
        mainStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
