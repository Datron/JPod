import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;

public class ListItemFactory {
    private String heading, description, mp3link, duration;
    private AnchorPane pane;

    public AnchorPane getPane() {
        return pane;
    }

    ListItemFactory(String heading, String description, String mp3link, String duration){
        this.heading = heading;
        this.duration = duration;
        this.description = description;
        this.mp3link = mp3link;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/cell.fxml"));
        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Button play = (Button) pane.lookup("#play");
        play.setText("play");
        Button add = (Button) pane.lookup("#add");
        add.setText("add...");
        pane.setStyle("-fx-pref-height: 200px;");
        VBox vBox = (VBox) pane.lookup("#vbox");
        Text head = (Text) pane.lookup("#heading");
        head.setStyle("-fx-font-size: 20px");
        head.setText(heading);
        Text des = (Text) pane.lookup("#description");
        des.setText(description);
        des.wrappingWidthProperty().bind(pane.widthProperty().multiply(0.35));
        VBox.setVgrow(des, Priority.ALWAYS);
    }
}
