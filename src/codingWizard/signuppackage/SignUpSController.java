package codingWizard.signuppackage;

import codingWizard.Main;
import codingWizard.mainstagepackage.MainSController;
import codingWizard.model.DataSource;
import javafx.animation.Animation;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.ImageInput;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

/**
 * Created by PC on 15.04.2017.
 */
public class SignUpSController {

    @FXML private Parent mainGridPane;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField1;
    @FXML private PasswordField passwordField2;
    @FXML private Line fieldLine;
    @FXML private Button signupButton;
    @FXML private Label signUpLabel;
    @FXML private Label duplicateUserLabel;
    @FXML private Label unmatchingPasswordLabel;
    @FXML private Button helpButton;
    @FXML private Button aboutButton;
    @FXML private Button homeButton;
    @FXML private Line helpLine;
    @FXML private Label helpLabel;
    @FXML private ImageView personalPageIcon;
    @FXML private ImageView starIcon1;
    @FXML private ImageView starIcon2;

    // Clips for help
    private Rectangle clipLabel = new Rectangle(220, 150);
    private Line clipLine = new Line(0, 0, 0, 120);

    // Help Transitions
    private TranslateTransition helpOpenTransition = new TranslateTransition(Duration.millis( 250)); //helpLine
    private TranslateTransition helpOpenTransition2 = new TranslateTransition(Duration.millis( 500)); //helpLabel
    private TranslateTransition helpCloseTransition = new TranslateTransition(Duration.millis( 250)); //helpLine
    private TranslateTransition helpCloseTransition2 = new TranslateTransition(Duration.millis( 500)); //helpLabel
    private int helpClickCounter = 0;

    public void initialize() {
        Light.Distant light = new Light.Distant();
        light.setElevation(55);
        light.setAzimuth(225);
        signupButton.setEffect( new Lighting( light));

        signUpLabel.setEffect( new DropShadow( 8, 0, 5, Color.WHITE));

        helpButton.setEffect( new ImageInput( new Image( "/questionMark1v1.png", 45, 45, true, true)));
        aboutButton.setEffect( new ImageInput( new Image("/aboutIcon.png", 45, 45, true, true)));
        homeButton.setEffect( new ImageInput( new Image("/homeIcon.png", 45, 45, true, true)));

        // Adds clips to user/pass fields to limit visibility
        addClipsToFields();

        // Translates in the user/pass fields
        doFieldTransitions();

        // Add clips to help line/label
        helpLabel.setClip(clipLabel);
        helpLabel.setText(" To create an account,\n Enter your username.\n Enter your password.\n Re-enter your password.\n" +
                " Then click on sign up!");

        clipLine.setStrokeWidth(4);
        helpLine.setClip( clipLine);

        // Bind the translateX properties of clipLabel and helpLabel.
        clipLabel.translateXProperty().bind( helpLabel.translateXProperty().subtract(345).negate());

        // Bind the translateY properties of clipLine and helpLine.
        clipLine.translateYProperty().bind( helpLine.translateYProperty().subtract( 140).negate());

        addFieldClickListeners();
    }

    /**
     * Checks if the password fields' contents match and then if the username is unique.
     * If both statements are true, then adds the user to the Database and returns to the main page.
     */
    @FXML
    public void handleSignUp() {
        String username = usernameField.getText();
        String password1 = passwordField1.getText();
        String password2 = passwordField2.getText();

        if ( !password1.equals( password2)) {
            unmatchingPasswordLabel.setText( "Unmatching password.");
            return;
        }

        if ( !DataSource.addUserToUsers( username, password1)) {
            duplicateUserLabel.setText( "Username already exists.");
            return;
        }

        try {
            returnToMainPage();
        } catch ( IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Opens the about us page
     * @throws IOException If the FXMLLoader can't find the fxml file, throws this exception.
     */
    @FXML
    public void openAboutUsPage() throws IOException {
        MainSController mainSController = new MainSController();
        mainSController.openAboutUsPage();
    }

    /**
     * Returns to the main page
     * @throws IOException If the FXMLLoader can't find the fxml file, throws this exception.
     */
    @FXML
    public void returnToMainPage() throws IOException {
        Stage mainStage = new Stage();
        Main main = new Main();
        main.initializeStage( mainStage);

        Stage stage = (Stage) mainGridPane.getScene().getWindow();
        stage.close();
    }

    /**
     * Makes the animations for the help options.
     */
    @FXML
    public void showHelpOptions() {
        if ( helpClickCounter % 2 == 0) {
            if ( helpCloseTransition2.getStatus() != Animation.Status.RUNNING &&
                    helpCloseTransition.getStatus() != Animation.Status.RUNNING) {
                helpOpenTransition.setNode( helpLine);
                helpOpenTransition.setToY( 140);
                helpOpenTransition.play();

                helpOpenTransition.setOnFinished(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        helpOpenTransition2.setNode( helpLabel);
                        helpOpenTransition2.setToX(345);
                        helpOpenTransition2.play();
                    }
                });

                helpClickCounter++;
            }
        } else {
            if (helpOpenTransition.getStatus() != Animation.Status.RUNNING &&
                    helpOpenTransition2.getStatus() != Animation.Status.RUNNING) {
                helpCloseTransition2.setNode( helpLabel);
                helpCloseTransition2.setToX(125);
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
     * Adds the clips to the username and password fields for animation purposes.
     */
    private void addClipsToFields() {
        usernameField.setTranslateX( -300);
        passwordField1.setTranslateX( -300);
        passwordField2.setTranslateX( -300);
        Rectangle clip1 = new Rectangle( 260, 50);
        Rectangle clip2 = new Rectangle( 260, 50);
        Rectangle clip3 = new Rectangle( 260, 50);

        clip1.translateXProperty().bind( usernameField.translateXProperty().negate());
        clip2.translateXProperty().bind( passwordField1.translateXProperty().negate());
        clip3.translateXProperty().bind( passwordField2.translateXProperty().negate());
        usernameField.setClip( clip1);
        passwordField1.setClip( clip2);
        passwordField2.setClip( clip3);
    }

    /**
     * Does the username and password field transitions.
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
            TranslateTransition tt2 = new TranslateTransition( Duration.millis( 1000), passwordField1);
            tt2.setToX( 0);
            tt2.play();
            tt2.setOnFinished( event2 -> {
                passwordField1.setClip( null);
                ScaleTransition sst1 = new ScaleTransition( Duration.millis( 500), starIcon1);
                sst1.setToX( 1);
                sst1.play();
            });
            TranslateTransition tt3 = new TranslateTransition( Duration.millis( 1200), passwordField2);
            tt3.setToX( 0);
            tt3.play();
            tt3.setOnFinished( event3 -> {
                passwordField2.setClip( null);
                ScaleTransition sst1 = new ScaleTransition( Duration.millis( 500), starIcon2);
                sst1.setToX( 1);
                sst1.play();
            });
        });
    }

    /**
     * Adds listeners to the username and password fields that removes the text of the invalidLoginLabel
     */
    private void addFieldClickListeners() {
        usernameField.setOnMousePressed(event -> duplicateUserLabel.setText(""));
        passwordField1.setOnMousePressed(event -> unmatchingPasswordLabel.setText(""));
        passwordField2.setOnMousePressed(event -> unmatchingPasswordLabel.setText(""));
    }
}
