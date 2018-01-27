import fxml.HomeController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

class ViewSwitcher {
    HashMap<String,Pane> views = new HashMap<>(4);

    public void setViews(String name, Pane root){
        views.put(name,root);
    }
    public Pane getViews(String name){
        return views.get(name);
    }

}
public class Main extends Application {
    private Stage stage;
    private XMLParser.Podcast pod;
    Player player;
    AnchorPane parent;
    ViewSwitcher switcher;
    Pane oldPane = null;
    public static void main(String[] args) {
        Application.launch(args);
    }
    private void initialize() throws IOException {
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
        player = new Player(pod.episodes.get(0).getMp3link());
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.stage = primaryStage;
        initialize();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/home.fxml"));
        FXMLLoader loader2 = new FXMLLoader(getClass().getResource("fxml/search.fxml"));
        FXMLLoader loader3 = new FXMLLoader(getClass().getResource("fxml/EpisodeView.fxml"));
        VBox vBox = loader.load();
        switcher = new ViewSwitcher();
        switcher.setViews("home",loader3.load());
        switcher.setViews("search",loader2.load());
        HomeController controller = loader.getController();
        Scene s = new Scene(vBox,900,800);
        s.getStylesheets().add("css/main.css");
        controller.setScene(s);
        controller.setDimens();
        controller.createToggleButtons();
        controller.configurePlayer(pod.episodes.get(0).getDuration());
        ToggleButton play = controller.getPlayPause();
        ToggleButton home = controller.getHome();
        ToggleButton search = controller.getSearch();
        parent = controller.getMainParent();
        play.setSelected(true);
        play.setOnMouseClicked(e -> player.playOrPause());
        home.setOnMouseClicked(e -> {
            switchView("home");
        });
        search.setOnMouseClicked(e -> {
            switchView("search");
        });
        primaryStage.setTitle("JPod");
        primaryStage.setScene(s);
        primaryStage.show();
    }

    private void switchView(String name) {
       if (oldPane != null)
            parent.getChildren().remove(oldPane);
       Pane p = switcher.getViews(name);
       oldPane = p;
       p.prefWidthProperty().bind(stage.getScene().widthProperty());
       p.prefHeightProperty().bind(stage.getScene().heightProperty());
       parent.getChildren().add(p);
       parent.setBottomAnchor(p, 0.0);
       parent.setTopAnchor(p, 0.0);
       parent.setLeftAnchor(p, 0.0);
       parent.setRightAnchor(p, 0.0);
    }
}
