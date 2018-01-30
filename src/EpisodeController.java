
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class EpisodeController {
    XMLParser parser;
    URL url;

    public void setUrl(URL url) {
        this.url = url;
    }

    XMLParser.Podcast podcast;
    XMLParser.Episodes episodes;
    Player player = new Player();
    static boolean isDone = false;
    public void setParser(XMLParser parser) {
        this.parser = parser;
    }
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
    public void initialize() {
        episode.setStyle("-fx-background-color: transparent;" +
                "-fx-padding: 0px");
//        podHeading.prefWidth(300);
        mainParent.setStyle("-fx-padding: 10px;");
        podHeading.setStyle("-fx-font-size: 30px");
        subscribe.setStyle("-fx-border-color: black;" +
                "-fx-border-width: 2px;");
        try {
            setUrl(new URL("https://rss.art19.com/masters-of-scale"));
            loadEpisodes();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
    public void loadEpisodes(){
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
            episode.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    AnchorPane p = episode.getSelectionModel().getSelectedItem();
                    System.out.println( ((Text) p.lookup("#mp3link")).getText());
                    try {
                        player.addPlayer(player.load(((Text) p.lookup("#mp3link")).getText()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    player.nextTrack();
                }
            });
            Image i = new Image(podcast.getImage());
            image.setImage(i);
            podHeading.setText(podcast.getPodName());
            podDescription.setText(podcast.getPodDescription());
        }
    public void configureSize(){
        if (!isDone) {
//            podDescription.setWrappingWidth(600);
            podDescription.wrappingWidthProperty().bind(mainParent.widthProperty().multiply(0.4));
            episode.prefWidthProperty().bind(mainParent.widthProperty().multiply(0.96));
            episode.prefHeightProperty().bind(mainParent.heightProperty().multiply(0.65));
            isDone = true;
        }
    }

}
