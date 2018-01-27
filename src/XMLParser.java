import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class XMLParser {
    HttpURLConnection urlConnection;
    DocumentBuilder documentBuilder;
    Document doc;
    class Episodes {
        String title,description,mp3link,duration,date;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getMp3link() {
            return mp3link;
        }

        public void setMp3link(String mp3link) {
            this.mp3link = mp3link;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }
    class Podcast {
        ArrayList<Episodes> episodes;
        String podName;
        int numberOfEpisodes;
        Podcast(int numberOfEpisodes){
            this.numberOfEpisodes = numberOfEpisodes;
            episodes = new ArrayList<>(numberOfEpisodes);
        }
        public int getNumberOfEpisodes() {
            return numberOfEpisodes;
        }

        public void setNumberOfEpisodes(int numberOfEpisodes) {
            this.numberOfEpisodes = numberOfEpisodes;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        String image;
        String keywords;
        String podDescription;
        public String getPodName() {
            return podName;
        }

        public void setPodName(String podName) {
            this.podName = podName;
        }

        public String getKeywords() {
            return keywords;
        }

        public void setKeywords(String keywords) {
            this.keywords = keywords;
        }

        public String getPodDescription() {
            return podDescription;
        }

        public void setPodDescription(String podDescription) {
            this.podDescription = podDescription;
        }
        void addEpisode(Episodes e){
            episodes.add(e);
        }
        ArrayList<Episodes> getAllEpisodes(){
            return episodes;
        }

    }
    XMLParser(URL url){
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public Podcast getFeed() throws IOException,ConnectException {
        InputStreamReader in = new InputStreamReader(urlConnection.getInputStream());
        BufferedReader br = new BufferedReader(in);
        String line;
        String document = "";
        while((line = br.readLine())!= null) {
            document += line;
        }
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            documentBuilder = factory.newDocumentBuilder();
            doc = documentBuilder.parse(new ByteArrayInputStream(document.getBytes()));
            doc.getDocumentElement().normalize();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
         catch (SAXException e) {
            e.printStackTrace();
        }
        NodeList item = doc.getElementsByTagName("item");
        Podcast pod = new Podcast(item.getLength());
        pod.setPodName(doc.getElementsByTagName("title").item(0).getTextContent());
        pod.setPodDescription(doc.getElementsByTagName("description").item(0).getTextContent());
        pod.setImage(doc.getElementsByTagName("itunes:image").item(0).getAttributes().getNamedItem("href").getTextContent());
        for (int i=0; i < item.getLength();i++){
            NodeList items = item.item(i).getChildNodes();
            Episodes e = new Episodes();
            for (int j = 0; j < items.getLength();j++) {
                switch (items.item(j).getNodeName()){
                    case "itunes:title":
                        e.setTitle(items.item(j).getTextContent());
                        break;
                    case "itunes:summary":
                        e.setDescription(items.item(j).getTextContent());
                        break;
                    case "enclosure":
                        e.setMp3link(items.item(j).getAttributes().getNamedItem("url").getTextContent());
                        break;
                    case "pubDate":
                        e.setDate(items.item(j).getTextContent());
                        break;
                    case "itunes:duration":
                        e.setDuration(items.item(j).getTextContent());
                        break;
                }
            }
            pod.addEpisode(e);
//            System.out.println("Title:"+e.getTitle()+"\n"
//            +"Description:"+e.getDescription()+"\n"
//            +"Date:"+e.getDate()+"\n"
//            +"URL:"+e.getMp3link()+"\n"
//            +"Duration:"+e.getDuration());
//            System.out.println();
        }
        in.close();
        br.close();
        return pod;
    }

}
