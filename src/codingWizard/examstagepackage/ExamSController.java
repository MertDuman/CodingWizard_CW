package codingWizard.examstagepackage;

import codingWizard.model.DataSource;
import codingWizard.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.effect.ImageInput;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by PC on 12.05.2017.
 */
public class ExamSController {

    @FXML private RadioButton cor1;
    @FXML private RadioButton cor2;
    @FXML private RadioButton cor3;
    @FXML private RadioButton cor4;
    @FXML private RadioButton cor5;
    @FXML private Label q1;
    @FXML private Label q2;
    @FXML private Label q3;
    @FXML private Label q4_1;
    @FXML private Label q4_2;
    @FXML private Label q5_1;
    @FXML private Label q5_2;
    @FXML private Label q5_3;
    @FXML private Label q5_4;
    @FXML private Label resultLabel;
    @FXML private Button returnButton;
    @FXML private Button submitButton;

    private int points = 0;
    private int counter = 0;

    public void initialize() {
        returnButton.setEffect( new ImageInput( new Image( "/leftArrow.png", 50, 50, true, true)));
    }

    /**
     * Calculates the points the user earned and disables the button.
     */
    @FXML
    public void handleSubmit() {
        if ( cor1.isSelected()) {
            points += 5;
            q1.setStyle( "-fx-text-fill: #21b303");
        }

        if ( cor2.isSelected()) {
            points += 5;
            q2.setStyle( "-fx-text-fill: #21b303");
        }

        if ( cor3.isSelected()) {
            points += 5;
            q3.setStyle( "-fx-text-fill: #21b303");
        }

        if ( cor4.isSelected()) {
            points += 5;
            q4_1.setStyle( "-fx-text-fill: #21b303");
            q4_2.setStyle( "-fx-text-fill: #21b303");
        }

        if ( cor5.isSelected()) {
            points += 5;
            q5_1.setStyle( "-fx-text-fill: #21b303");
            q5_2.setStyle( "-fx-text-fill: #21b303");
            q5_3.setStyle( "-fx-text-fill: #21b303");
            q5_4.setStyle( "-fx-text-fill: #21b303");
        }

        resultLabel.setText( "Points: " + points);

        User.increasePoints(points);
        DataSource.updateUser(User.getUsername(), User.getPoints(), User.getRanking(), User.getCourseProgression());

        submitButton.setDisable( true);
    }

    /**
     * Returns to the selection stage.
     * @throws IOException Throws this exception if the fxmlloader can't load the fxml.
     */
    @FXML
    public void returnToSelectionStage() throws IOException {
        BorderPane selectionPane = FXMLLoader.load( getClass().getResource( "/codingWizard/courseselectionpackage/selectionStage.fxml"));
        Scene selectionScene = new Scene( selectionPane, 800, 475);
        Stage selectionStage = new Stage();
        selectionStage.setScene( selectionScene);
        selectionStage.show();

        Stage stage = (Stage) resultLabel.getScene().getWindow();
        stage.close();
    }
}
