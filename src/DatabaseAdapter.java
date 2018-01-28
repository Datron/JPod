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

    public void newSubscription(String url){
        try {
            PreparedStatement statement = conn.prepareStatement("INSERT INTO subscribed(url) VALUES(?)");
            statement.setString(1,url);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
