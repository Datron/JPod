
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
    private long position;
    private boolean isPaused = true; //true is pause and false is play
    ArrayList<MediaPlayer> playerArrayList = new ArrayList<>(10);
    MediaView mediaView;
    MediaPlayer oldPlayer;
    Player(){
        mediaView = new MediaView();
    }
    public void addPlayer(MediaPlayer track){
        playerArrayList.add(track);
    }
    public MediaPlayer getMediaPlayer(){
        return  playerArrayList.remove(0);
    }

    public MediaPlayer load(String mp3url) throws IOException {
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
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        if (oldPlayer != null){
            oldPlayer.stop();
        }
        mediaView.setMediaPlayer(mediaPlayer);
        mediaPlayer.setOnReady(new Runnable() {
            @Override
            public void run() {
                System.out.println("Duration:"+media.getDuration().toMinutes());
            }
        });
        return mediaPlayer;
    }

    synchronized void playOrPause(){
        if (!isPaused) {
            oldPlayer = mediaView.getMediaPlayer();
            mediaView.getMediaPlayer().pause();
            isPaused = true;
        }
        else {
                oldPlayer = mediaView.getMediaPlayer();
                mediaView.getMediaPlayer().play();
                isPaused = false;
            }
    }
    void nextTrack(){
        System.out.println("No of tracks:"+playerArrayList.size());
        mediaView.setMediaPlayer(playerArrayList.remove(0));
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
