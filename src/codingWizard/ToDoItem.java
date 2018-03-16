package codingWizard;

/**
 * Created by PC on 16.02.2017.
 */
public class ToDoItem {

    // fields
    private String shortDescription;
    private String details;

    // constructor
    public ToDoItem(String shortDescription, String details) {
        this.shortDescription = shortDescription;
        this.details = details;
    }

    @Override
    public String toString() {
        return shortDescription;
    }

    // GETTERS SETTERS
    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
