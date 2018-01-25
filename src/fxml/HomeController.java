package fxml;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;


public class HomeController {
    @FXML
    VBox vBox;
    @FXML
    ToggleButton playPause,forward,backward,skip30forward,skip30backward;
    @FXML
    ToggleButton home,search,downloads,queue;
    @FXML
    VBox sidebar;
    @FXML
    ButtonBar slider;
    @FXML
    ToolBar controls;
    @FXML
    Label duration;

    Scene scene;
    @FXML
    public void initialize(){

    }
}
