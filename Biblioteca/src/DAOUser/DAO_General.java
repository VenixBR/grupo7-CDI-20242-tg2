
package DAOUser;

import biblioteca.SQL_connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DAO_General {
    
    public int ReturnLastCod(String tabela){

        int id = -1;
        String SQL_command = "SELECT AUTO_INCREMENT FROM information_schema.TABLES WHERE TABLE_SCHEMA='CD_II_Work' AND TABLE_NAME='" + tabela + "'";
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = SQL_connection.getConnection().prepareStatement(SQL_command);
            rs = ps.executeQuery();

            if(rs.next()){
                id = rs.getInt("Auto_increment");
            } 
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return id-1;
    }
    
}
