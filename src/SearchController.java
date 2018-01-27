
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class SearchController {
    @FXML
    WebView webview;
    @FXML
    AnchorPane main;
    @FXML
    public void initialize(){
        WebEngine engine = webview.getEngine();
        engine.load("http://www.listennotes.com");
        main.setBottomAnchor(webview, 0.0);
        main.setTopAnchor(webview, 0.0);
        main.setLeftAnchor(webview, 0.0);
        main.setRightAnchor(webview, 0.0);
    }
}
