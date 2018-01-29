
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Pattern;


public class Player {
    private Media media;
    MediaPlayer mediaPlayer;
    private long position;
    private boolean isPaused = true; //true is pause and false is play
    ArrayList<MediaPlayer> playerArrayList = new ArrayList<>(10);
    public void addPlayer(MediaPlayer track){
        playerArrayList.add(track);
    }
    public MediaPlayer getMediaPlayer(){
        return  playerArrayList.remove(0);
    }
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

    synchronized void playOrPause(){
        if (!isPaused) {
            mediaPlayer.pause();
            isPaused = true;
        }
        else {
                mediaPlayer.play();
                isPaused = false;
            }
    }
    void nextTrack(){

    }
    void forward(){

    }
    void backward(){

    }
    void resume(){

    }
    void goToPosition(){

    }

}
