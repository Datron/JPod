
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Pattern;


public class Player {
    private Media media;
    private MediaPlayer mediaPlayer;
    private long position;
    private boolean isPaused;


    Player(String mp3url) throws IOException {
        String url;
        HttpURLConnection conn =(HttpURLConnection) new URL(mp3url).openConnection();
        int rs = conn.getResponseCode();
        System.out.println(rs);
        if ( rs == 301 || rs == 302 || rs == 303 || rs == 307 || rs == 308) {
            url = conn.getHeaderField("Location");
        }
        else
            url = mp3url;
        System.out.println(url);
        media = new Media(url);
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setOnReady(new Runnable() {
            @Override
            public void run() {
                System.out.println("Duration:"+media.getDuration().toMinutes());
            }
        });
    }

    synchronized void play(){
        mediaPlayer.play();
    }
    void forward(){

    }
    void backward(){

    }
    void pause(){

    }
    void resume(){

    }
    void goToPosition(){

    }
}
