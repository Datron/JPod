import fxml.HomeController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class Main extends Application {
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/home.fxml"));
        VBox vBox = loader.load();
//        AnchorPane pane = loader.load();
        Scene s = new Scene(vBox,800,450);
        primaryStage.setTitle("JPod");
        primaryStage.setScene(s);
        primaryStage.show();
    }
}
