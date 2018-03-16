package codingWizard;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.stage.Popup;
import javafx.stage.Stage;

/**
 * Created by PC on 18.04.2017.
 */
public class CustomPopup extends Popup {

    public CustomPopup( Stage stage, double anchorX, double anchorY, double triangleY, String text) {
        Pane pane = new Pane();
        Polygon polygon = new Polygon( 10.0, 0.0,   30.0, 0.0,  0.0, 20.0);
        polygon.setFill( Color.rgb(255,255,255,0.8));
        polygon.setLayoutX( 5);
        polygon.setLayoutY( triangleY);
        Label label = new Label( text);
        label.setStyle( "-fx-background-color: rgba(255,255,255,0.8); -fx-background-insets: -5 -10 -5 -10, 0, 0, 0;" +
                "-fx-background-radius: 5px; -fx-font-family: Arial, serif; -fx-font-size: 17px; -fx-font-weight: bold");
        label.setLayoutX( 20);
        label.setLayoutY( 0);
        pane.getChildren().addAll( polygon, label);

        if ( !isShowing()) {
            if ( getContent().isEmpty()) {
                getContent().add( pane);
                setAutoFix( false);
                setAutoHide( true);
                setAnchorX( anchorX);
                setAnchorY( anchorY);
            }
            show( stage);
        } else {
            hide();
        }
    }
}
