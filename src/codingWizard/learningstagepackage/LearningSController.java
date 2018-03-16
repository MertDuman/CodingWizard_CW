package codingWizard.learningstagepackage;

import codingWizard.CWCompiler;
import codingWizard.Main;
import codingWizard.model.DataSource;
import codingWizard.model.Lecture;
import codingWizard.model.User;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.ImageInput;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.*;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

/**
 * Controls the learning stage..
 * @author CodingWizard
 * @version 03.05.2017.
 */
public class LearningSController {

    @FXML private BorderPane mainBorderPane;
    @FXML private Accordion accordion;
    @FXML private TitledPane lectureTP;
    @FXML private TitledPane assignmentTP;
    @FXML private TextArea codingArea;
    @FXML private TextArea consoleArea;
    @FXML private TextArea lectureArea;
    @FXML private TextArea assignmentArea;
    @FXML private ColorPicker colorPickerCA;
    @FXML private ColorPicker colorPickerTF;
    @FXML private ToggleButton resizeToggleButton;
    @FXML private Button profileButton;
    @FXML private Button skipButton;
    @FXML private Button nextButton;
    @FXML private Button compileButton;

    // Lectures
    @FXML private MenuItem introL;
    @FXML private MenuItem int_doubleL;
    @FXML private MenuItem booleanL;
    @FXML private MenuItem charL;
    @FXML private MenuItem variablesL;
    @FXML private MenuItem whitespaceL;
    @FXML private MenuItem stringL;
    @FXML private MenuItem ifstatementL;
    @FXML private MenuItem elsestatementL;
    @FXML private MenuItem else_ifL;
    @FXML private MenuItem forloopL;
    @FXML private MenuItem whileloopL;
    @FXML private MenuItem do_whileloopL;
    @FXML private MenuItem methodsL;

    // Lectures as list
    private ArrayList<MenuItem> lectureList = new ArrayList<>();

    // Lecture instance
    private Lecture lecture;
    private SimpleBooleanProperty isAcceptableCode = new SimpleBooleanProperty( false);

    public void initialize() {
        accordion.setExpandedPane( lectureTP);

        colorPickerCA.setValue( Color.valueOf( "#2b2b2b"));
        codingArea.setStyle( "-fx-control-inner-background: #2b2b2b");
        addChangeListenersToColorPickers();
        setPSNButtonEffects();
        setHowToUse();

        setDefaultCodingAreaValue();
        addLecturesToArrayList();
        disableLectures( User.getIsAdmin(), User.getCourseProgression());

        nextButton.disableProperty().bind( isAcceptableCode.not());

        introL.setOnAction( event -> handleLoadLecture( 1));
        int_doubleL.setOnAction( event -> handleLoadLecture( 2));
        booleanL.setOnAction( event -> handleLoadLecture( 3));
        charL.setOnAction( event -> handleLoadLecture( 4));
        variablesL.setOnAction( event -> handleLoadLecture( 5));
        whitespaceL.setOnAction( event -> handleLoadLecture( 6));
        stringL.setOnAction( event -> handleLoadLecture( 7));
        ifstatementL.setOnAction( event -> handleLoadLecture( 8));
        elsestatementL.setOnAction( event -> handleLoadLecture( 9));
        else_ifL.setOnAction( event -> handleLoadLecture( 10));
        forloopL.setOnAction( event -> handleLoadLecture( 11));
        whileloopL.setOnAction( event -> handleLoadLecture( 12));
        do_whileloopL.setOnAction( event -> handleLoadLecture( 13));
        methodsL.setOnAction( event -> handleLoadLecture( 14));

    }

    /**
     * The top level method that does the compilation and run of the user entered code.
     * Prints the results to a .txt file and also reads from it.
     * @throws Exception Throws exception if it can't compile.
     */
    @FXML
    public void handleCompile() throws Exception {
        writeToTxtFile();
        boolean compiledANDran = CWCompiler.compileANDrun( "code.txt", "output.txt");
        String output = readFromTxtFile( "output.txt");
        consoleArea.setText( output);

        if ( lecture != null && compiledANDran) {
            if (codingArea.getText().replaceAll( " ", "").contains( lecture.getAnswer().replaceAll( " ", ""))) {
                if ( lecture.getId() != 9 && lecture.getId() != 10 && lecture.getId() != 11 && lecture.getId() != 12) {
                    isAcceptableCode.set(true);
                    return;
                }
            }

            if (lecture.getId() == 9 || lecture.getId() == 10 ||lecture.getId() == 11 || lecture.getId() == 12) {
                if ( consoleArea.getText().replaceAll( " ", "").contains( lecture.getAnswer().replaceAll( " ", ""))) {
                    isAcceptableCode.set( true);
                    return;
                }
            }

            if ( lecture.getId() == 6 || lecture.getId() == 14) {
                isAcceptableCode.set( true);
            }
        }
    }

    /**
     * Writes the users source code to the code.txt file.
     */
    private void writeToTxtFile() {
        String sourceCode = codingArea.getText();
        String[] sourceArr = sourceCode.split( "\n");

        try(BufferedWriter writer = new BufferedWriter( new FileWriter( "code.txt"))) {
            for ( String str : sourceArr) {
                writer.write( str + "\n");
            }
        } catch ( IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads from the output.txt file and returns the output
     * @param readLoc The read location.
     * @return Returns the output string.
     * @throws IOException Throws the file not found exception
     */
    private String readFromTxtFile( String readLoc) throws IOException {
        String output = "";

        try(Scanner scan = new Scanner( new BufferedReader( new FileReader( readLoc)))) {
            while ( scan.hasNextLine()) {
                output = output + scan.nextLine() + "\n";
            }
        }

        return output;
    }

    /**
     * Opens the profile page
     * @throws IOException If the corresponding fxml file is not found throws this exception.
     */
    @FXML
    public void openProfilePage() throws IOException {
        BorderPane profilePane = FXMLLoader.load( getClass().getResource( "/codingWizard/profilestagepackage/profileStage.fxml"));
        Scene profileScene = new Scene( profilePane, 900, 400);
        Stage profileStage = new Stage();
        profileStage.setScene( profileScene);
        profileStage.show();
    }

    /**
     * Resizes the coding area.
     */
    @FXML
    public void handleResize() {
        if ( resizeToggleButton.isSelected()) {
            lectureTP.setPrefWidth( 120);
            assignmentTP.setPrefWidth( 120);
        } else {
            lectureTP.setPrefWidth( 550);
            assignmentTP.setPrefWidth( 550);
        }
    }

    /**
     * Returns to the selection stage.
     * @throws IOException If the corresponding fxml file is not found throws this exception.
     */
    @FXML
    public void returnToSelectionStage() throws IOException {
        BorderPane selectionPane = FXMLLoader.load( getClass().getResource( "/codingWizard/courseselectionpackage/selectionStage.fxml"));
        Scene selectionScene = new Scene( selectionPane, 800, 475);
        Stage selectionStage = new Stage();
        selectionStage.setScene( selectionScene);
        selectionStage.show();

        Stage stage = (Stage) mainBorderPane.getScene().getWindow();
        stage.close();
    }

    /**
     * Logs out and returns to the main stage.
     * @throws IOException If the corresponding fxml file is not found throws this exception.
     */
    @FXML
    public void handleLogout() throws IOException {
        Stage mainStage = new Stage();
        Main main = new Main();
        main.initializeStage( mainStage);

        Stage stage = (Stage) mainBorderPane.getScene().getWindow();
        stage.close();
    }

    /**
     * Skips the current lecture by issuing a dialog.
     */
    @FXML
    public void handleSkip() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.getDialogPane().setMinWidth( 700);
        DialogPane dialogPane = dialog.getDialogPane();
        Stage stage = (Stage)dialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add( new Image( "/Icon.png" , 20, 20, true, true));

        dialogPane.getStylesheets().add( getClass().getResource( "/codingWizard/learningstagepackage/skipStyles.css").toExternalForm());

        dialog.setTitle( "Skipping Lecture");
        dialog.setHeaderText( "You are about to skip the lecture " + lecture.getTitle());
        dialog.setContentText( "You won't be able to collect full points for this course.");
        dialog.setGraphic( new ImageView( new Image( "/questionMark.png", 50, 50, true, true)));

        ButtonType okButton = new ButtonType( "Skip", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType( "I changed my mind", ButtonBar.ButtonData.CANCEL_CLOSE);

        dialog.getDialogPane().getButtonTypes().addAll( okButton, cancelButton);
        Optional<ButtonType> result = dialog.showAndWait();
        if ( result.isPresent() && result.get().getButtonData() == ButtonBar.ButtonData.OK_DONE) {
            if ( User.getCourseProgression() <= 12) {
                User.increaseCourseProgression();
            }

            DataSource.updateUser( User.getUsername(), User.getPoints(), User.getRanking(), User.getCourseProgression());
            enableLectures( User.getCourseProgression());
        }
    }

    /**
     * Exits the code.
     */
    @FXML
    public void handleExit() {
        Platform.exit();
        System.exit( 0);
    }

    /**
     * Opens the users email for a bug report.
     * @throws URISyntaxException If the mailto: method fails, throws this exception.
     */
    @FXML
    public void sendMail() throws URISyntaxException {
        Main main = new Main();
        main.sendMail();
    }

    /**
     * Opens the news box.
     * @throws IOException If the corresponding fxml file is not found throws this exception.
     */
    @FXML
    public void openNewsBox() throws IOException {
        Parent newsBoxPane = FXMLLoader.load( getClass().getResource( "/codingwizard/newsboxpackage/newsBoxStage.fxml"));
        Scene newsBoxScene = new Scene( newsBoxPane, 1200, 700);
        Stage newsBoxStage = new Stage();
        newsBoxStage.setScene( newsBoxScene);
        newsBoxStage.show();
    }

    /**
     * Loads the current lecture with the lectureID.
     * @param lectureID The Id of the lecture.
     */
    @FXML
    public void handleLoadLecture( int lectureID) {
        codingArea.clear();

        setDefaultCodingAreaValue();

        isAcceptableCode.set( false);

        lecture = DataSource.getLecture( lectureID);
        if ( lecture == null) {
            return;
        }

        lectureArea.setText( lecture.getExplanation());
        assignmentArea.setText( lecture.getAssignment());

        if ( lectureID == 9) {
            codingArea.setText( "class Main {\n" +
                    "\tpublic static void main(String[] args) {\n" +
                    "\n" +
                    "\t\tif (7 > 6) {\n" +
                    "\n" +
                    "\t\t\tSystem.out.println(\"Try again...\");\n" +
                    "\n" +
                    "\t\t} else {\n" +
                    "\n" +
                    "\t\t\tSystem.out.println(\"Success!\");\n" +
                    "\n" +
                    "\t\t}\n" +
                    "\t}\n" +
                    "}");
        } else if ( lectureID == 10) {
            codingArea.setText( "class Main {\n" +
                    "\tpublic static void main(String[] args) {\n" +
                    "\n" +
                    "\t\tint round = 20;\n" +
                    "\n" +
                    "\t\tif (round > 12) {\n" +
                    "\n" +
                    "\t\t\tSystem.out.println(\"The match is over!\");\n" +
                    "\n" +
                    "\t\t} else if (round > 0) {\n" +
                    "\n" +
                    "\t\t\tSystem.out.println(\"The match is underway!\");\n" +
                    "\n" +
                    "\t\t} else {\n" +
                    "\n" +
                    "\t\t\tSystem.out.println(\"The boxing match hasn't started yet.\");\n" +
                    "\n" +
                    "\t\t}\n" +
                    "\t}\n" +
                    "}");
        } else if ( lectureID == 12) {
            codingArea.setText( "class Main {\n" +
                    "\tpublic static void main(String[] args) {\n" +
                    "\n" +
                    "\t\tboolean understand = false;\n" +
                    "\n" +
                    "\t\twhile( understand === true) {\n" +
                    "\t\t\tSystem.out.println(\"I'm learning while loops!\");\n" +
                    "\t\t\tunderstand = false;\n" +
                    "\t\t}\n" +
                    "\t}\n" +
                    "}");
        }
    }

    /**
     * Opens up the next lecture for the user and adds the points.
     */
    @FXML
    public void handleNextButtonClick() {
        User.increasePoints( lecture.getPoints());
        if ( User.getCourseProgression() <= 12) {
            User.increaseCourseProgression();
        }

        DataSource.updateUser( User.getUsername(), User.getPoints(), User.getRanking(), User.getCourseProgression());
        enableLectures( User.getCourseProgression());

        isAcceptableCode.set( false);
    }

    /**
     * Disables the lectures depending on the user's course progression.
     * @param isAdmin is the user an admin
     * @param courseProgression the user's course progression.
     */
    private void disableLectures( boolean isAdmin, int courseProgression) {
        if ( isAdmin) {
            return;
        }

        for ( int i = courseProgression - 1; i < lectureList.size(); i++) {
            lectureList.get( i).setDisable( true);
        }
    }

    /**
     * Enables the lectures based on the course progression.
     * @param courseProgression User's course progression.
     */
    private void enableLectures( int courseProgression) {
        lectureList.get( courseProgression - 2).setDisable( false);
        if ( courseProgression == 13) {
            lectureList.get( 12).setDisable( false);
        }
    }

    /**
     * Sets the default value of the coding area.
     */
    private void setDefaultCodingAreaValue() {
        codingArea.setText( "class Main {\n" +
                "\tpublic static void main(String[] args) {\n" +
                "\t\t\n" +
                "\t\t\n" +
                "\t\t\n" +
                "\t\t\n" +
                "\t\t\n" +
                "\t\t\n" +
                "\t}\n" +
                "}");
    }

    /**
     * Listeners for the color pickers to change the coding area.
     */
    private void addChangeListenersToColorPickers() {
        colorPickerCA.getCustomColors().add( Color.valueOf( "#2b2b2b"));
        colorPickerCA.valueProperty().addListener(new ChangeListener<Color>() {
            @Override
            public void changed(ObservableValue<? extends Color> observable, Color oldValue, Color newValue) {
                // Changes the background color of textarea to colorpickers color.
                codingArea.setStyle( "-fx-control-inner-background: #" + colorPickerCA.getValue().toString().substring(2)
                        + "; " + "-fx-text-fill: #" + colorPickerTF.getValue().toString().substring(2));
            }
        });

        colorPickerTF.valueProperty().addListener(new ChangeListener<Color>() {
            @Override
            public void changed(ObservableValue<? extends Color> observable, Color oldValue, Color newValue) {
                // Changes the font color of textarea to colorpickers color.
                codingArea.setStyle( "-fx-control-inner-background: #" + colorPickerCA.getValue().toString().substring(2)
                        + "; " + "-fx-text-fill: #" + colorPickerTF.getValue().toString().substring(2));
            }
        });
    }

    /**
     * Adds images to profile, skip and next buttons.
     */
    private void setPSNButtonEffects() {
        profileButton.setEffect( new ImageInput( new Image( "/personalPageIcon.png", 50, 50, true, true)));
        skipButton.setEffect( new ImageInput( new Image( "/skipIcon.png", 60, 60, true, true)));
        nextButton.setEffect( new ImageInput( new Image( "/nextIcon.png", 60, 60, true, true)));
    }

    /**
     * Adds lectures to the lectures list.
     */
    private void addLecturesToArrayList() {
        lectureList.add( int_doubleL);
        lectureList.add( booleanL);
        lectureList.add( charL);
        lectureList.add( variablesL);
        lectureList.add( whitespaceL);
        lectureList.add( stringL);
        lectureList.add( ifstatementL);
        lectureList.add( elsestatementL);
        lectureList.add( else_ifL);
        lectureList.add( forloopL);
        lectureList.add( whileloopL);
        lectureList.add( do_whileloopL);
        lectureList.add( methodsL);
    }

    /**
     * Adjusts the lecture area to show a how to use text.
     */
    private void setHowToUse() {
        lectureArea.setText( "Welcome to the Learning Area\n" +
                             "Here you will be learning how to code in the Java programming language.\n" +
                             "How to Use:\n" +
                             "This area will be where the lectures are displayed. To display a lecture, go to Sections and choose the lecture " +
                             "you want to display here. At the bottom, you can see the Assignment tab, clicking it will show you the assignment " +
                             "for that lecture.\n" +
                             "The area on the right is where you will be coding. You can adjust its size and colors from the top left buttons. After " +
                             "you have written your code, click compile and run. Don't change the parts other than the part within the main(String[] args).\n" +
                             "The most right area is the console, where your code's output will be printed.\n" +
                             "After you finish a lecture, if you succeeded, the Next button will be clickable. Clicking it will unlock the next lecture which you can choose by going to " +
                             "Sections and choosing the corresponding lecture.\n" +
                             "Skip button will skip you the lecture and unlock the next one. You won't get any points for it!\n" +
                             "Profile button (bottom right) will display the profile page for you.");
    }
}
