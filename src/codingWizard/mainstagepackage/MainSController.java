package codingWizard.mainstagepackage;

import codingWizard.model.DataSource;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.ImageInput;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

/**
 * Controls the main stage.
 * @author CodingWizard
 * @version 11.05.2017.
 */
public class MainSController {

    @FXML private GridPane gridPane;
    @FXML private Button helpButton;
    @FXML private Button aboutButton;
    @FXML private Label codingWizardLabel;
    @FXML private Label invalidLoginLabel;
    @FXML private TextField usernameField;
    @FXML private TextField passwordField;
    @FXML private Line fieldLine;
    @FXML private Line helpLine;
    @FXML private Label helpLabel;
    @FXML private Button loginButton;
    @FXML private Button signupButton;
    @FXML private ImageView personalPageIcon;
    @FXML private ImageView starIcon;

    // Clips for help
    private Rectangle clipLabel = new Rectangle(200, 150);
    private Line clipLine = new Line(0, 0, 0, 150);

    // Help Transitions
    private TranslateTransition helpOpenTransition = new TranslateTransition(Duration.millis( 250)); //helpLine
    private TranslateTransition helpOpenTransition2 = new TranslateTransition(Duration.millis( 500)); //helpLabel
    private TranslateTransition helpCloseTransition = new TranslateTransition(Duration.millis( 250)); //helpLine
    private TranslateTransition helpCloseTransition2 = new TranslateTransition(Duration.millis( 500)); //helpLabel
    private int helpClickCounter = 0;

    public void initialize() {
        // Set 3D effects on login/sign up buttons
        Light.Distant light = new Light.Distant();
        light.setElevation(55);
        light.setAzimuth(225);
        loginButton.setEffect( new Lighting( light));
        signupButton.setEffect( new Lighting( light));

        // DropShadow for the name
        codingWizardLabel.setEffect( new DropShadow( 8, 0, 5, Color.WHITE));

        // Add images to buttons
        helpButton.setEffect( new ImageInput( new Image( "/questionMark1v1.png", 45, 45, true, true)));
        aboutButton.setEffect( new ImageInput( new Image("/aboutIcon.png", 45, 45, true, true)));

        // Adds clips to user/pass fields to limit visibility
        addClipsToFields();

        // Translates in the user/pass fields
        doFieldTransitions();

        // Add clips to help line/label
        helpLabel.setClip(clipLabel);
        helpLabel.setText(" If you have an account,\n Enter your username.\n Enter your password.\n Then click on Log In.\n" +
                " If you don't have one,\n Click on Sign Up!");

        clipLine.setStrokeWidth(4);
        helpLine.setClip( clipLine);

        // Bind the translateX properties of clipLabel and helpLabel.
        clipLabel.translateXProperty().bind( helpLabel.translateXProperty().subtract(355).negate());

        // Bind the translateY properties of clipLine and helpLine.
        clipLine.translateYProperty().bind( helpLine.translateYProperty().subtract( 100).negate());

        // Fades in the CodingWizard label
        fadeInCodingWizard();

        // Clears the error label
        addFieldClickListeners();
    }

    /**
     * Logs into the account and directs to selection stage
     * @throws IOException May throw exception
     */
    @FXML
    public void handleLogIn() throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if ( DataSource.isUserExists( username, password)) {
            DataSource.handleUserLogin( username);

            BorderPane selectionPane = FXMLLoader.load( getClass().getResource( "/codingWizard/courseselectionpackage/selectionStage.fxml"));
            Scene selectionScene = new Scene( selectionPane, 800, 475);
            Stage selectionStage = new Stage();
            selectionStage.setScene( selectionScene);
            selectionStage.show();

            Stage mainStage = (Stage)signupButton.getScene().getWindow();
            mainStage.close();
        } else {
            invalidLoginLabel.setText( "Invalid username\n or invalid password.");
        }
    }

    /**
     * Opens the sign up stage and closes the main stage
     * @throws IOException May throw exception
     */
    @FXML
    public void openSignUpPage() throws IOException {
        GridPane signUpPane = FXMLLoader.load(getClass().getResource( "/codingWizard/signuppackage/signUpStage.fxml"));
        Scene signUpScene = new Scene( signUpPane, 800, 475);
        Stage signUpStage = new Stage();
        signUpStage.setScene( signUpScene);
        signUpStage.show();

        Stage mainStage = (Stage)signupButton.getScene().getWindow();
        mainStage.close();
    }

    /**
     * Opens about us stage and closes main stage
     * @throws IOException May throw exception
     */
    @FXML
    public void openAboutUsPage() throws IOException {
        GridPane aboutUsPane = FXMLLoader.load( getClass().getResource( "/codingWizard/aboutpackage/aboutUsStage.fxml"));
        Scene aboutUsScene = new Scene( aboutUsPane, 600, 475);
        Stage aboutUsStage = new Stage();
        aboutUsStage.getIcons().add( new Image( "/aboutIcon.png"));
        aboutUsStage.setScene( aboutUsScene);
        aboutUsStage.setTitle( "About Us");
        aboutUsStage.show();
    }

    /**
     * Shows an animated help label.
     */
    @FXML
    public void showHelpOptions() {
        if ( helpClickCounter % 2 == 0) {
            if ( helpCloseTransition2.getStatus() != Animation.Status.RUNNING &&
                    helpCloseTransition.getStatus() != Animation.Status.RUNNING) {
                helpOpenTransition.setNode( helpLine);
                helpOpenTransition.setToY(100);
                helpOpenTransition.play();

                helpOpenTransition.setOnFinished(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        helpOpenTransition2.setNode( helpLabel);
                        helpOpenTransition2.setToX(355);
                        helpOpenTransition2.play();
                    }
                });

                helpClickCounter++;
            }
        } else {
            if (helpOpenTransition.getStatus() != Animation.Status.RUNNING &&
                    helpOpenTransition2.getStatus() != Animation.Status.RUNNING) {
                helpCloseTransition2.setNode( helpLabel);
                helpCloseTransition2.setToX(155);
                helpCloseTransition2.play();

                helpCloseTransition2.setOnFinished(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        helpCloseTransition.setNode(helpLine);
                        helpCloseTransition.setToY(300);
                        helpCloseTransition.play();
                    }
                });

                helpClickCounter++;
            }
        }
    }

    /**
     * Shifts the fields 300px to the left
     * and adds clips to them to limit visibility.
     */
    private void addClipsToFields() {
        usernameField.setTranslateX( -300);
        passwordField.setTranslateX( -300);
        Rectangle clip1 = new Rectangle( 260, 50);
        Rectangle clip2 = new Rectangle( 260, 50);

        clip1.translateXProperty().bind( usernameField.translateXProperty().negate());
        clip2.translateXProperty().bind( passwordField.translateXProperty().negate());
        usernameField.setClip( clip1);
        passwordField.setClip( clip2);
    }

    /**
     * Translates in the pass/user fields while
     * simultaneously translating out the clips
     */
    private void doFieldTransitions() {
        ScaleTransition st1 = new ScaleTransition( Duration.millis( 600), fieldLine);
        st1.setToY( 1);
        st1.play();
        st1.setOnFinished( event -> {
            TranslateTransition tt1 = new TranslateTransition( Duration.millis( 800), usernameField);
            tt1.setToX( 0);
            tt1.play();
            tt1.setOnFinished( event1 -> {
                usernameField.setClip( null);
                ScaleTransition sst1 = new ScaleTransition( Duration.millis( 500), personalPageIcon);
                sst1.setToX( 1);
                sst1.play();
            });
            TranslateTransition tt2 = new TranslateTransition( Duration.millis( 1000), passwordField);
            tt2.setToX( 0);
            tt2.play();
            tt2.setOnFinished( event1 -> {
                passwordField.setClip( null);
                ScaleTransition sst2 = new ScaleTransition( Duration.millis( 500), starIcon);
                sst2.setToX( 1);
                sst2.play();
            });
        });
    }

    /**
     * Fades in the codingWizard label
     */
    private void fadeInCodingWizard() {
        codingWizardLabel.setOpacity( 0);
        FadeTransition ft1 = new FadeTransition( Duration.millis( 3000), codingWizardLabel);
        ft1.setToValue( 1);
        ft1.play();
    }

    /**
     * Adds listeners to the username and password fields that removes the text of the invalidLoginLabel
     */
    private void addFieldClickListeners() {
        usernameField.setOnMousePressed(event -> invalidLoginLabel.setText(""));
        passwordField.setOnMousePressed(event -> invalidLoginLabel.setText(""));
    }
}
