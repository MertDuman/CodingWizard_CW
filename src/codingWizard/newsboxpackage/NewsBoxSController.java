package codingWizard.newsboxpackage;

import codingWizard.ToDoItem;
import codingWizard.model.User;
import codingWizard.newsboxpackage.dialogpackage.DialogSController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.Optional;

/**
 * Created by PC on 8.05.2017.
 */
public class NewsBoxSController {

    @FXML private BorderPane mainBorderPane;
    @FXML private TextArea messageArea;
    @FXML private ListView<ToDoItem> listView;
    @FXML private ObservableList<ToDoItem> listViewItems;
    @FXML private Button addButton;

    public void initialize() {
        listViewItems = FXCollections.observableArrayList();
        listView.setItems( listViewItems);

        listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ToDoItem>() {
            @Override
            public void changed(ObservableValue<? extends ToDoItem> observable, ToDoItem oldValue, ToDoItem newValue) {
                ToDoItem item = listView.getSelectionModel().getSelectedItem();
                messageArea.setText( item.getDetails());
            }
        });

        if ( !User.getIsAdmin()) {
            addButton.setVisible( false);
        }
    }

    /**
     * Opens the dialog stage.
     */
    @FXML
    public void openDialogStage() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner( mainBorderPane.getScene().getWindow());
        dialog.setTitle( "Add New Thread");
        dialog.setHeaderText( null);
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation( getClass().getResource("/codingWizard/newsboxpackage/dialogpackage/dialogStage.fxml"));
        try {
            dialog.getDialogPane().setContent( fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        dialog.getDialogPane().getButtonTypes().add( ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add( ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if ( result.isPresent() && result.get() == ButtonType.OK) {
            DialogSController controller = fxmlLoader.getController();
            ToDoItem newItem = controller.processResults();
            listViewItems.add( newItem);
            listView.getSelectionModel().select( newItem);
        }
    }

}
