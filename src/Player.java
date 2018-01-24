
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.regex.Pattern;


public class Player {
    private Media media;
    private MediaPlayer mediaPlayer;
    private long position;
    private boolean isPaused;


    Player(String mp3url){
        System.out.println(mp3url);
        media = new Media(mp3url);
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setOnReady(new Runnable() {
            @Override
            public void run() {
                System.out.println("Duration:"+media.getDuration().toMinutes());
                mediaPlayer.play();
            }
        });
    }

    void play(){
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
