import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        URL url = null;
        try {
            url = new URL("https://rss.art19.com/masters-of-scale");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        XMLParser parser = new XMLParser(url);
        XMLParser.Podcast pod = parser.getFeed();
        System.out.println("Podcast:"+pod.getPodName());
        System.out.println("Description:"+pod.getPodDescription());
        System.out.println("Image:"+pod.getImage());
        System.out.println();
        for (int i=0;i < pod.numberOfEpisodes ; i++){
            System.out.println("Episode Title:"+pod.episodes.get(i).getTitle());
            System.out.println("Episode Description:"+pod.episodes.get(i).getDescription());
            System.out.println("Episode Date:"+pod.episodes.get(i).getDate());
            System.out.println("Episode Duration:"+pod.episodes.get(i).getDuration());
            System.out.println("Episode Link:"+pod.episodes.get(i).getMp3link());
            System.out.println();
        }

    }
}
