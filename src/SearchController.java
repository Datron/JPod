
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class SearchController {
    @FXML
    WebView webview;
    @FXML
    AnchorPane main;
    @FXML
    public void initialize(){
        WebEngine engine = webview.getEngine();
        engine.load("http://www.listennotes.com");
        Document doc = engine.getDocument();
        engine.getLoadWorker().stateProperty().addListener((observable,oldValue,newValue) ->{
            if (Worker.State.SUCCEEDED.equals(newValue)){
                if (engine.getLocation().contains("http://www.listennotes.com/search")){
                    int start = engine.getLocation().indexOf("=");
                    int end = engine.getLocation().indexOf("&");
                    String query = engine.getLocation().substring(start+1,end);
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
                    engine.getLoadWorker().cancel();
                    System.out.println(engine.getLocation());
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, " " + engine.getLocation() + " ?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
                    alert.showAndWait();
                    if (alert.getResult() == ButtonType.YES){
                        
                    }
                }
            }
        });
        main.setBottomAnchor(webview, 0.0);
        main.setTopAnchor(webview, 0.0);
        main.setLeftAnchor(webview, 0.0);
        main.setRightAnchor(webview, 0.0);
    }
}
