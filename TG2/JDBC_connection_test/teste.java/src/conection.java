import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class conection {
    
    private static final String url = "jdbc:mariadb://localhost:3306/teste";
    private static final String user = "admin";
    private static final String password = "1234";

    private static Connection conn;

    public static Connection getConnection() throws SQLException{

        try{
            if (conn == null){
                conn = DriverManager.getConnection(url, user, password);
                return conn;
            }
            else
                return conn;
        }
        catch(SQLException e){
            e.printStackTrace();
            return null;
        }

    }

}
