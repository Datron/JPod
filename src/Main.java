import fxml.HomeController;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class Main extends Application {
    private Stage stage;
    private static XMLParser.Podcast pod;
    public static void main(String[] args) throws IOException, InterruptedException {
        URL url = null;
        try {
            url = new URL("https://www.npr.org/rss/podcast.php?id=510313");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        XMLParser parser = new XMLParser(url);
        pod = parser.getFeed();
        System.out.println("Podcast:"+pod.getPodName());
        System.out.println("Description:"+pod.getPodDescription());
        System.out.println("Image:"+pod.getImage());
        System.out.println();
        for (int i=0;i < pod.numberOfEpisodes ; i++) {
            System.out.println("Episode Title:" + pod.episodes.get(i).getTitle());
            System.out.println("Episode Description:" + pod.episodes.get(i).getDescription());
            System.out.println("Episode Date:" + pod.episodes.get(i).getDate());
            System.out.println("Episode Duration:" + pod.episodes.get(i).getDuration());
            System.out.println("Episode Link:" + pod.episodes.get(i).getMp3link());
            System.out.println();
        }
        Player player = new Player(pod.episodes.get(0).getMp3link());
        player.play();
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.stage = primaryStage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/home.fxml"));
        VBox vBox = loader.load();
//        AnchorPane pane = loader.load();
        Scene s = new Scene(vBox,800,500);
        s.getStylesheets().add("css/main.css");
        VBox sidebar = (VBox) s.lookup("#sidebar");
        ButtonBar buttonBar = (ButtonBar) s.lookup("#playerRowOne");
        ToolBar toolBar = (ToolBar) s.lookup("#playerRowTwo");
        //load sidebar buttons
        ToggleButton home = (ToggleButton) s.lookup("#home");
        ToggleButton search = (ToggleButton) s.lookup("#search");
        ToggleButton downloads = (ToggleButton) s.lookup("#download");
        ToggleButton queue = (ToggleButton) s.lookup("#queue");

        //create toggle button images
        createToggleButtons(home,search,downloads,queue);
        //setting height property for each player
        sidebar.prefHeightProperty().bind(s.heightProperty().multiply(0.8));
        buttonBar.prefHeightProperty().bind(s.heightProperty().multiply(0.1));
        toolBar.prefHeightProperty().bind(s.heightProperty().multiply(0.2));
        //load player buttons and align everything

        //configure the playa
        configurePlayer(buttonBar,toolBar);
        primaryStage.setTitle("JPod");
        primaryStage.setScene(s);
        primaryStage.show();
    }

    private void configurePlayer(ButtonBar buttonBar, ToolBar toolBar) {
        Label duration = new Label(pod.episodes.get(0).getDuration());
        ToggleButton playPause = new ToggleButton();
        Button next = (Button) new Button();
        Button previous = (Button) new Button();
        Button forward = (Button) new Button();
        Button backward = (Button) new Button();
        Slider seek = (Slider) new Slider(); // and ye shall find
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
        buttonBar.getButtons().addAll(seek,duration);
        buttonBar.setButtonUniformSize(seek,false);
        toolBar.getItems().addAll(backward,previous,playPause,next,forward);
    }
    private void createToggleButtons(ToggleButton home,ToggleButton search,ToggleButton queue,ToggleButton downloads){
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
        downloads.setGraphic(imgQueueView);
    }
}
