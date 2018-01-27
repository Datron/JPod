
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import sun.plugin.javascript.navig.Anchor;

import java.util.HashMap;


public class HomeController {
    @FXML
    VBox parent;
    @FXML
    ToolBar playerRowTwo;
    @FXML
    ButtonBar playerRowOne;
    @FXML
    ToggleButton home;
    @FXML
    AnchorPane mainParent;
    @FXML
    ToggleButton search;
    @FXML
    ToggleButton download;
    @FXML
    ToggleButton queue;
    @FXML
    VBox sidebar;

    private Scene scene;
    Label duration = new Label();
    ToggleButton playPause = new ToggleButton();
    Button next = (Button) new Button();
    Button previous = (Button) new Button();
    Button forward = (Button) new Button();
    Button backward = (Button) new Button();
    Slider seek = (Slider) new Slider(); // and ye shall find


    public AnchorPane getMainParent() {
        return mainParent;
    }

    public ToggleButton getHome() {
        return home;
    }

    public ToggleButton getSearch() {
        return search;
    }

    public ToggleButton getDownload() {
        return download;
    }

    public ToggleButton getQueue() {
        return queue;
    }
    @FXML
    public void initialize(){
        System.out.println("Controller initialized");
    }
    public void setScene(Scene scene){
        this.scene = scene;
    }
    public void setDimens(){
        mainParent.prefWidthProperty().bind(scene.widthProperty().multiply(0.95));
        mainParent.prefHeightProperty().bind(scene.heightProperty().multiply(0.8));
        sidebar.prefWidthProperty().bind(scene.widthProperty().multiply(0.05));
        sidebar.prefHeightProperty().bind(scene.heightProperty().multiply(0.8));
        playerRowOne.prefHeightProperty().bind(scene.heightProperty().multiply(0.1));
        playerRowTwo.prefHeightProperty().bind(scene.heightProperty().multiply(0.2));
    }

    public Label getDuration() {
        return duration;
    }

    public ToggleButton getPlayPause() {
        return playPause;
    }

    public Button getNext() {
        return next;
    }

    public Button getPrevious() {
        return previous;
    }

    public Button getForward() {
        return forward;
    }

    public Button getBackward() {
        return backward;
    }

    public Slider getSeek() {
        return seek;
    }

    public void configurePlayer(String length){
        duration.setText(length);
        Image play = new Image("res/icons/button_play_red.png",60,60,true,true);
        Image pause = new Image("res/icons/pause_red.png",60,60,true,true);
        Image imgNext = new Image("res/icons/fastforward-red.png",60,60,true,true);
        Image imgPrevious = new Image("res/icons/fastbackward-512-red.png",60,60,true,true);
        Image imgForward = new Image("res/icons/skip30seconds.png",60,60,true,true);
        Image imgBackward = new Image("res/icons/backward-512-red.png",60,60,true,true);
        ImageView playPauseView = new ImageView();
        ImageView nextView = new ImageView(imgNext);
        ImageView previousView = new ImageView(imgPrevious);
        ImageView forwardView = new ImageView(imgForward);
        ImageView backwardView = new ImageView(imgBackward);
        playPauseView.imageProperty().bind(Bindings
                .when(playPause.selectedProperty())
                .then(play)
                .otherwise(pause));

        playPause.setGraphic(playPauseView);
        next.setGraphic(nextView);
        previous.setGraphic(previousView);
        forward.setGraphic(forwardView);
        backward.setGraphic(backwardView);
        //configure seek
        playerRowOne.getButtons().addAll(seek,duration);
        playerRowOne.setButtonUniformSize(seek,false);
        playerRowTwo.getItems().addAll(backward,previous,playPause,next,forward);
    }
    public void createToggleButtons() {
        Image imgHome = new Image("res/icons/dppgiadx.png",40,40,true,true);
        Image imgSearch = new Image("res/icons/search.png",40,40,true,true);
        Image imgDownload = new Image("res/icons/Download.png",40,40,true,true);
        Image imgQueue = new Image("res/icons/playlist.png",40,40,true,true);
        ImageView imgHomeView = new ImageView(imgHome);
        ImageView imgSearchView = new ImageView(imgSearch);
        ImageView imgDownloadView = new ImageView(imgDownload);
        ImageView imgQueueView = new ImageView(imgQueue);
        home.setGraphic(imgHomeView);
        search.setGraphic(imgSearchView);
        queue.setGraphic(imgDownloadView);
        download.setGraphic(imgQueueView);
    }

}
