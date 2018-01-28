
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
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
                if (engine.getLocation().contains("search")){
                    int start = engine.getLocation().indexOf("=");
                    int end = engine.getLocation().indexOf("&");
                    System.out.println(engine.getLocation());
                    System.out.println("start:"+start+"\t end:"+end);
                    String query = engine.getLocation().substring(start+1,end);
                    if (!(engine.getLocation().contains("&scope=podcast"))) {
                        engine.load("https://www.listennotes.com/search/?q=" + query + "&sort_by_date=0&scope=podcast&offset=0&language=Any%20language&len_min=0");
                        String js =
                                "var player = document.getElementsByClassName('ln-search-result-audio-player-container');" +
                                "for (var i = 0; i < player.length; i++) {" +
                                "    player[i].style.display = 'none';" +
                                "}";
                        System.out.println(engine.executeScript(js));
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
