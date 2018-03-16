package codingWizard.courseselectionpackage;

import codingWizard.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.ImageInput;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by PC on 18.04.2017.
 */
public class SelectionSController {
    @FXML private BorderPane mainBorderPane;
    @FXML private Button learnButton;
    @FXML private Button examButton;
    @FXML private Button logoutButton;

    public void initialize() {
        Light.Distant light = new Light.Distant();
        light.setElevation( 55);
        light.setAzimuth( 225);
        learnButton.setEffect( new Lighting( light));
        examButton.setEffect( new Lighting( light));

        logoutButton.setEffect( new ImageInput( new Image( "/leftArrow.png", 50, 50, true, true)));
    }

    /**
     * Opens the learning stage and closes the current one.
     * @throws IOException Throws this exception if the fxmlloader can't load the fxml.
     */
    @FXML
    public void openLearningStage() throws IOException{
        BorderPane learningPane = FXMLLoader.load( getClass().getResource( "/codingWizard/learningstagepackage/learningStage.fxml"));
        Scene learningScene = new Scene( learningPane, 1922, 1000);
        Stage learningStage = new Stage();
        learningStage.setScene( learningScene);
        learningStage.setTitle( "CodingWizard");
        learningStage.setMaximized( true);
        learningStage.show();

        Stage selectionStage = (Stage) mainBorderPane.getScene().getWindow();
        selectionStage.close();
    }

    /**
     * Opens the exam stage.
     * @throws IOException Throws this exception if the fxmlloader can't load the fxml.
     */
    @FXML
    public void openExamStage() throws IOException{
        VBox examPane = FXMLLoader.load( getClass().getResource( "/codingWizard/examstagepackage/examStage.fxml"));
        Scene examScene = new Scene( examPane, 700, 900);
        Stage examStage = new Stage();
        examStage.setScene( examScene);
        examStage.show();

        Stage selectionStage = (Stage) mainBorderPane.getScene().getWindow();
        selectionStage.close();
    }

    /**
     * Logs out and goes to the main page.
     * @throws IOException Throws this exception if the fxmlloader can't load the fxml.
     */
    @FXML
    public void handleLogout() throws IOException {
        Stage mainStage = new Stage();
        Main main = new Main();
        main.initializeStage( mainStage);

        Stage selectionStage = (Stage) mainBorderPane.getScene().getWindow();
        selectionStage.close();
    }
}
