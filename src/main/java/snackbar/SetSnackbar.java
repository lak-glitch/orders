package snackbar;

import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXSnackbarLayout;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.Objects;

public class SetSnackbar {
    public JFXSnackbar setSnackbarPane(Pane pane, String stylesheet, String context) {
        JFXSnackbar snackbar = new JFXSnackbar(pane);
        snackbar.setPrefWidth(1191);
        String css = Objects.requireNonNull(this.getClass().getResource(stylesheet)).toExternalForm();
        snackbar.getStylesheets().add(css);
        snackbar.enqueue(new JFXSnackbar.SnackbarEvent(new JFXSnackbarLayout(context), Duration.millis(1000)));
        return snackbar;
    }

    public JFXSnackbar setSnackbarAnchorPane(AnchorPane pane, String stylesheet, String context) {
        JFXSnackbar snackbar = new JFXSnackbar(pane);
        snackbar.setPrefWidth(1191);
        String css = Objects.requireNonNull(this.getClass().getResource(stylesheet)).toExternalForm();
        snackbar.getStylesheets().add(css);
        snackbar.enqueue(new JFXSnackbar.SnackbarEvent(new JFXSnackbarLayout(context), Duration.millis(1000)));
        return snackbar;
    }
}
