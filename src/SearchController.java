
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
    WebEngine engine;

    public WebEngine getEngine() {
        return engine;
    }

    @FXML
    public void initialize(){
        engine = webview.getEngine();
        engine.load("http://www.listennotes.com");
        Document doc = engine.getDocument();
        main.setBottomAnchor(webview, 0.0);
        main.setTopAnchor(webview, 0.0);
        main.setLeftAnchor(webview, 0.0);
        main.setRightAnchor(webview, 0.0);
    }
}
