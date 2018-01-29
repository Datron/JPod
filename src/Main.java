import javafx.application.Application;
import javafx.concurrent.Worker;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaView;
import javafx.scene.web.WebEngine;
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
    MediaView mediaView;
    Player player;
    AnchorPane parent;
    ViewSwitcher switcher;
    Pane oldPane = null;
    HomeController controller;
    EpisodeController episodeController;
    DatabaseAdapter db;
    WebEngine engine;
    SearchController searchController;
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
        searchController = loader2.getController();
        engine = searchController.getEngine();
        setEngineEvents();
        episodeController.configureSize();
        Scene s = new Scene(vBox,1280,720);
        s.getStylesheets().add("css/main.css");
        controller.setScene(s);
        controller.setDimens();
        controller.createToggleButtons();
        controller.configurePlayer(episodeController.podcast.episodes.get(0).getDuration());
        ToggleButton play = controller.getPlayPause();
        ToggleButton home = controller.getHome();
        ToggleButton search = controller.getSearch();
        parent = controller.getMainParent();
        player = episodeController.getPlayer();
        mediaView = new MediaView(player.mediaPlayer);
        play.setSelected(true);
        db = new DatabaseAdapter("jpod.db");
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

    private void setEngineEvents() {
        final String[] prevUrl = new String[1];
        engine.getLoadWorker().stateProperty().addListener((observable,oldValue,newValue) ->{
            if (Worker.State.SUCCEEDED.equals(newValue)){
                if (engine.getLocation().contains("http://www.listennotes.com/search")){
                    int start = engine.getLocation().indexOf("=");
                    int end = engine.getLocation().indexOf("&");
                    String query = engine.getLocation().substring(start+1,end);
                    prevUrl[0] = engine.getLocation();
                    engine.executeScript(
                            "var feed = document.getElementsByClassName('ln-search-result-link');" +
                                    "for (var i = 0; i < feed.length; i++){" +
                                    "     feed[i].style.size = '50px';" +
                                    "     if ((feed[i].text != 'RSS')){" +
                                    "           feed[i].style.display = 'none';" +
                                    "}" +
                                    "}");
                    if (!(engine.getLocation().contains("&scope=podcast"))) {
                        engine.load("https://www.listennotes.com/search/?q=" + query + "&sort_by_date=0&scope=podcast&offset=0&language=Any%20language&len_min=0");
                        engine.executeScript(
                                "function setElements(){" +
                                        "var player = document.getElementsByClassName('ln-search-result-audio-player-container');" +
                                        "for (var i = 0; i < player.length; i++)" +
                                        "    player[i].style.display = 'none';" +
                                        "}"
                        );
                        engine.executeScript(
                                "var feed = document.getElementsByClassName('ln-search-result-link');" +
                                        "for (var i = 0; i < feed.length; i++){" +
                                        "     feed[i].style.size = '50px';" +
                                        "     if ((feed[i].text != 'RSS')){" +
                                        "           feed[i].style.display = 'none';" +
                                        "}" +
                                        "}");
                    }
                }
                else if (!(engine.getLocation().contains("listennotes"))){
                    System.out.println(engine.getLocation());
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "use the link " + engine.getLocation() + " ?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
                    alert.showAndWait();
                    if (alert.getResult() == ButtonType.YES){
                        try {
                            episodeController.setUrl(new URL(engine.getLocation()));
                            episodeController.loadEpisodes();
                            switchView("home");
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                    }
//                    engine.load(prevUrl[0]);
                }
            }
        });
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
