package style;

import javafx.scene.control.Button;

public class Style {

    // set style for buttons each time the user hover them

    public void setStyle(Button bt1, Button bt2, Button bt3, Button bt4) {
        bt1.setOnAction(event -> {
            if (!bt1.getStyleClass().contains("taskbar-style")) {
                bt1.getStyleClass().add("taskbar-style");
                bt2.getStyleClass().remove("taskbar-style");
                bt3.getStyleClass().remove("taskbar-style");
                bt4.getStyleClass().remove("taskbar-style");
            }
        });
    }
}
