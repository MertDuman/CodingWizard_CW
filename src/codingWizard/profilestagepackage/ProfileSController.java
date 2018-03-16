package codingWizard.profilestagepackage;

import codingWizard.model.DataSource;
import codingWizard.model.RankingContent;
import codingWizard.model.User;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;

/**
 * Created by PC on 11.05.2017.
 */
public class ProfileSController {

    @FXML private BorderPane mainBorderPane;
    @FXML private Label usernameLabel;
    @FXML private Label competenceStageLabel;
    @FXML private Label pointsLabel;
    @FXML private Label rankingLabel;
    @FXML private Label joinLabel;
    @FXML private TableView<RankingContent> tableView;
    @FXML private ProgressBar progressBar;

    public void initialize() {
        int userPoints = User.getPoints();
        if ( userPoints < 300) {
            competenceStageLabel.setText( "Competence Stage: Beginner");
        } else if ( userPoints < 600) {
            competenceStageLabel.setText( "Competence Stage: Novice");
        } else if ( userPoints < 900) {
            competenceStageLabel.setText( "Competence Stage: Advanced");
        } else {
            competenceStageLabel.setText( "Competence Stage: Wizard");
        }

        if ( User.getIsAdmin()) {
            competenceStageLabel.setText( "Competence Stage: Wizard");
        }

        ObservableList<RankingContent> list = DataSource.queryUserRanking();
        tableView.getItems().setAll( list);

        usernameLabel.setText( "Username: " + User.getUsername());
        pointsLabel.setText( "Points: " + User.getPoints());
        rankingLabel.setText( "Ranking: " + User.getRanking());
        joinLabel.setText( "Join Date: " + User.getJoinDate());


        int integ = User.getCourseProgression();
        double doub = (integ * 100) / 13;
        doub = doub / 100;
        SimpleDoubleProperty sip = new SimpleDoubleProperty( doub);
        progressBar.progressProperty().bind( sip);
    }
}
