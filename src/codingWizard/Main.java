package codingWizard;

import codingWizard.mainstagepackage.MainSController;
import codingWizard.model.DataSource;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private HostServices hostServices = getHostServices();

    @Override
    public void start(Stage primaryStage) throws Exception{
        initializeStage( primaryStage);
    }

    /**
     * Initializes the stage
     * @param primaryStage The primary stage
     * @throws IOException Throws this exception if the fxmlloader can't load the fxml.
     */
    public void initializeStage(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader( getClass().getResource( "/codingWizard/mainstagepackage/mainStage.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene( root, 800, 475);

        MainSController controller = fxmlLoader.getController();

        primaryStage.setTitle("CodingWizard");
        primaryStage.setScene( scene);
        primaryStage.show();
    }

    public void init() {
        DataSource.open();
    }

    public void stop() {
        DataSource.close();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void sendMail() {
        hostServices.showDocument("mailto:cs-project-17@googlegroups.com");
    }
}
