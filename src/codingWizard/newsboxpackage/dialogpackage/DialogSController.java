package codingWizard.newsboxpackage.dialogpackage;

import codingWizard.ToDoItem;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * Created by PC on 8.05.2017.
 */
public class DialogSController {

    @FXML private TextField shortDescription;
    @FXML private TextArea detailsArea;

    /**
     * Processes the results of the dialog
     * @return Returns the corresponding to do item.
     */
    @FXML
    public ToDoItem processResults() {
        String shortDesc = shortDescription.getText().trim();
        String details = detailsArea.getText().trim();

        return new ToDoItem( shortDesc, details);
    }
}
