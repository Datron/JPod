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
    HomeController controller;
    EpisodeController episodeController;
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.stage = primaryStage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/home.fxml"));
        FXMLLoader loader2 = new FXMLLoader(getClass().getResource("fxml/search.fxml"));
        FXMLLoader loader3 = new FXMLLoader(getClass().getResource("fxml/EpisodeView.fxml"));
        VBox vBox = loader.load();
        switcher = new ViewSwitcher();
        switcher.setViews("home",loader3.load());
        switcher.setViews("search",loader2.load());
        controller = loader.getController();
        episodeController = loader3.getController();
        episodeController.configureSize();
        Scene s = new Scene(vBox,1280,720);
        s.getStylesheets().add("css/main.css");
        controller.setScene(s);
        controller.setDimens();
        controller.createToggleButtons();
        player = episodeController.getPlayer();
        controller.configurePlayer(episodeController.podcast.episodes.get(0).getDuration());
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
//       p.prefWidthProperty().bind(stage.getScene().widthProperty());
       p.prefHeightProperty().bind(stage.getScene().heightProperty());
       parent.getChildren().add(p);
       parent.setBottomAnchor(p, 0.0);
       parent.setTopAnchor(p, 0.0);
       parent.setLeftAnchor(p, 0.0);
       parent.setRightAnchor(p, 0.0);
    }
}
