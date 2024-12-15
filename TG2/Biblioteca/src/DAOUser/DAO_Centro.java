package DAOUser;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Entitys.Centro;
import biblioteca.SQL_connection;

public class DAO_Centro{
    
    public void CadastrarCentro(Centro centro){

        String SQL_command = "INSERT INTO Centro (Nome, Sigla) VALUES (?, ?)";

        PreparedStatement ps = null;

        try {
            ps = SQL_connection.getConnection().prepareStatement(SQL_command);
            ps.setString(1, centro.getNome());
            ps.setString(2, centro.getSigla());

            ps.execute();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    public Centro BuscarCentro(int Cod_Centro){

        Centro lib = new Centro();
        String SQL_command = "SELECT * FROM Centro WHERE Cod_Centro=?";
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = SQL_connection.getConnection().prepareStatement(SQL_command);
            ps.setInt(1, Cod_Centro);
            rs = ps.executeQuery();

            if(rs.next()){
                lib = new Centro();
                lib.setCod_Centro(rs.getInt("Cod_Centro"));
                lib.setNome(rs.getString("Nome"));
                lib.setSigla(rs.getString("Sigla"));
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
        return lib;
    }
    
    public void RemoverCentro(int Cod_Centro){

        String SQL_command = "DELETE FROM Centro WHERE Cod_Centro=?";
        PreparedStatement ps = null;

        try {
            ps = SQL_connection.getConnection().prepareStatement(SQL_command);
            ps.setInt(1, Cod_Centro);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public static boolean TestaSigla(String sigla){
        
        boolean flag = false;
        String SQL_command = "SELECT COUNT(*) AS Result FROM Centro WHERE Sigla=" + sigla +"'";
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = SQL_connection.getConnection().prepareStatement(SQL_command);
            rs = ps.executeQuery();
            if (rs.getInt("Result") > 0) {
                flag = true;
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
        
        System.out.println(flag);
        return flag;
        
        
    }
    
    
    
}
