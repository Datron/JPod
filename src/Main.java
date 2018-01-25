import fxml.HomeController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class Main extends Application {
    private Stage stage;
    public static void main(String[] args) throws IOException, InterruptedException {
        URL url = null;
        try {
            url = new URL("https://www.npr.org/rss/podcast.php?id=510313");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        XMLParser parser = new XMLParser(url);
        XMLParser.Podcast pod = parser.getFeed();
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
        Scene s = new Scene(vBox,800,450);
        s.getStylesheets().add("css/main.css");
        VBox sidebar = (VBox) s.lookup("#sidebar");

        ToggleButton home = (ToggleButton) s.lookup("#home");
        ToggleButton search = (ToggleButton) s.lookup("#search");
        ToggleButton downloads = (ToggleButton) s.lookup("#download");
        ToggleButton queue = (ToggleButton) s.lookup("#queue");
        createToggleButtons(home,search,downloads,queue);
        sidebar.prefWidthProperty().bind(s.widthProperty().multiply(0.2));
        primaryStage.setTitle("JPod");
        primaryStage.setScene(s);
        primaryStage.show();
    }

    private void createToggleButtons(ToggleButton home,ToggleButton search,ToggleButton queue,ToggleButton downloads){
        Image imgHome = new Image("res/icons/dppgiadx.png",60,60,true,true);
        Image imgSearch = new Image("res/icons/search.png",60,60,true,true);
        Image imgDownload = new Image("res/icons/Download.png",60,60,true,true);
        Image imgQueue = new Image("res/icons/playlist.png",60,60,true,true);
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
