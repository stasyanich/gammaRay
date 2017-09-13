import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {

    private static Stage stage;

    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getClassLoader().getResource("MainLayout.fxml"));
        AnchorPane pane = loader.load();
        primaryStage.setTitle("Gamma Ray - Corrected");
        primaryStage.getIcons().add(new Image("rad.png"));
        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        setStage(primaryStage);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static Stage getStage() {
        return stage;
    }

    public static void setStage(Stage stage) {
        Main.stage = stage;
    }
}
