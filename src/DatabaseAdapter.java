import java.sql.*;

public class DatabaseAdapter {
    Connection conn;
    DatabaseAdapter(String dbName){
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:"+dbName);
            System.out.println("Connected");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public ResultSet getSubcribedImages(){
        ResultSet rs = null;
        try {
            Statement statement = conn.createStatement();
            rs = statement.executeQuery("SELECT image FROM subscribed");
            statement.close();
        } catch (SQLException e) {
            e.getMessage();
        }
        return rs;
    }
    public void newSubscription(String name,String image,String url,int episodeCount){
        try {
            PreparedStatement statement = conn.prepareStatement("INSERT INTO subscribed(name,image,url,episodeCount) VALUES(?,?,?,?)");
            statement.setString(1,name);
            statement.setString(2,image);
            statement.setString(3,url);
            statement.setInt(4,episodeCount);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void close(){
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
