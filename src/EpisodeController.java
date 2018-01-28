
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class EpisodeController {
    XMLParser parser;

    public void setParser(XMLParser parser) {
        this.parser = parser;
    }

    XMLParser.Podcast podcast;
    XMLParser.Episodes episodes;
    Player player;
    static boolean isDone = false;
    public Player getPlayer() {
        return player;
    }

    public void setPodcast(XMLParser.Podcast podcast) {
        this.podcast = podcast;
    }

    public void setEpisodes(XMLParser.Episodes episodes) {
        this.episodes = episodes;
    }

    @FXML
    Text podHeading;
    @FXML
    Text podDescription;
    @FXML
    AnchorPane mainParent;
    @FXML
    ImageView image;
    @FXML
    ListView<AnchorPane> episode;
    @FXML
    Button subscribe;
    @FXML
    public void initialize(){
        episode.setStyle("-fx-background-color: transparent");
//        podHeading.prefWidth(300);
        mainParent.setStyle("-fx-padding: 10px;");
        podHeading.setStyle("-fx-font-size: 30px");
        subscribe.setStyle("-fx-border-color: black;" +
                "-fx-border-width: 2px;");
        URL url = null;
        try {
            url = new URL("https://rss.art19.com/masters-of-scale");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        parser = new XMLParser(url);
        try {
            podcast = parser.getFeed();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Podcast:"+podcast.getPodName());
        System.out.println("Description:"+podcast.getPodDescription());
        System.out.println("Image:"+podcast.getImage());
        System.out.println();
        ArrayList<AnchorPane> al = new ArrayList<>(podcast.getNumberOfEpisodes());
        for (int i=0;i < podcast.getNumberOfEpisodes() ; i++) {
            ListItemFactory ls = new ListItemFactory(podcast.episodes.get(i).getTitle(),podcast.episodes.get(i).getDescription(),podcast.episodes.get(i).getMp3link(),podcast.episodes.get(i).getDuration());
            System.out.println("Episode Title:" + podcast.episodes.get(i).getTitle());
            System.out.println("Episode Description:" + podcast.episodes.get(i).getDescription());
            System.out.println("Episode Date:" + podcast.episodes.get(i).getDate());
            System.out.println("Episode Duration:" + podcast.episodes.get(i).getDuration());
            System.out.println("Episode Link:" + podcast.episodes.get(i).getMp3link());
            System.out.println();
            al.add(ls.getPane());
        }
        ObservableList<AnchorPane> listItemFactories = FXCollections.observableList(al);
        episode.setItems(listItemFactories);
        try {
            player = new Player(podcast.episodes.get(0).getMp3link());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image i = new Image(podcast.getImage());
        image.setImage(i);
        podHeading.setText(podcast.getPodName());
        podDescription.setText(podcast.getPodDescription());
    }
    public void configureSize(){
        if (!isDone) {
            podDescription.wrappingWidthProperty().bind(mainParent.widthProperty().multiply(0.25));
            episode.prefWidthProperty().bind(mainParent.widthProperty().multiply(0.96));
            episode.prefHeightProperty().bind(mainParent.heightProperty().multiply(0.65));
            isDone = true;
        }
    }

}
