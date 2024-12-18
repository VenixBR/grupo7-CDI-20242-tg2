package DAOUser;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Entitys.Biblioteca;
import Entitys.Pertence;
import biblioteca.SQL_connection;

public class DAO_Biblioteca{

    public void CadastrarBiblioteca(Biblioteca biblioteca){

        String SQL_command = "INSERT INTO Biblioteca (Cod_Biblioteca, Endereco, Nome, Sigla) VALUES (?, ?, ?, ?)";

        PreparedStatement ps = null;

        try {
            ps = SQL_connection.getConnection().prepareStatement(SQL_command);
            ps.setInt(1, biblioteca.getCod_Biblioteca());
            ps.setString(2,biblioteca.getEndereco());
            ps.setString(3, biblioteca.getNome());
            ps.setString(4, biblioteca.getSigla());

            ps.execute();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void CadastrarPertence(Pertence pertence){

        String SQL_command = "INSERT INTO Pertence (fk_Cod_Biblioteca, fk_Cod_Centro) VALUES (?, ?)";

        PreparedStatement ps = null;

        try {
            ps = SQL_connection.getConnection().prepareStatement(SQL_command);
            ps.setInt(1, pertence.getFk_Cod_Biblioteca());
            ps.setInt(2, pertence.getFk_Cod_Centro());
           
            ps.execute();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    

    public Biblioteca BuscarBiblioteca(int Cod_Biblioteca){

        Biblioteca lib = new Biblioteca();
        String SQL_command = "SELECT * FROM Biblioteca WHERE Cod_Biblioteca=?";
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = SQL_connection.getConnection().prepareStatement(SQL_command);
            ps.setInt(1, Cod_Biblioteca);
            rs = ps.executeQuery();

            if(rs.next()){
                lib = new Biblioteca();
                lib.setCod_Biblioteca(rs.getInt("Cod_Biblioteca"));
                lib.setEndereco(rs.getString("Endereco"));
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
    
    public void RemoverBiblioteca(int Cod_Biblioteca) {

        String SQL_command = "DELETE FROM Biblioteca WHERE Cod_Biblioteca=?";
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = SQL_connection.getConnection().prepareStatement(SQL_command);
            
            ps.setInt(1, Cod_Biblioteca);
            
            // Executa a consulta para remover a biblioteca
            ps.executeUpdate();  // Aqui usamos executeUpdate() porque estamos fazendo uma operação de DELETE

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
    }
    
     public void RemoverPertence(int Cod_Biblioteca) {

        String SQL_command = "DELETE FROM Pertence WHERE fk_Cod_Biblioteca=?";
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = SQL_connection.getConnection().prepareStatement(SQL_command);
            
            ps.setInt(1, Cod_Biblioteca);
            
            // Executa a consulta para remover a biblioteca
            ps.executeUpdate();  // Aqui usamos executeUpdate() porque estamos fazendo uma operação de DELETE

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
    }
    
    
    
    public static boolean TestaSigla(String sigla){
        
        boolean flag = false;
        String SQL_command = "SELECT COUNT(*) AS Result FROM Biblioteca WHERE Sigla=?";
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = SQL_connection.getConnection().prepareStatement(SQL_command);
            ps.setString(1,sigla);
            rs = ps.executeQuery();
           
            
        if(rs.next()){
            if (rs.getInt("Result") > 0) {
                flag = true;
            }
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
        
        return flag;
         
    } 
    
    
    
    public static int PullBiblioteca(String sigla){
        
        int result = -1;
        String SQL_command = "SELECT Cod_Biblioteca AS Codigo FROM Biblioteca WHERE Sigla=?";
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = SQL_connection.getConnection().prepareStatement(SQL_command);
            ps.setString(1, sigla);
            rs = ps.executeQuery();
           
            
        if(rs.next()){
            result = rs.getInt("Codigo");

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
        
        return result;
         
    }
    
    
    
}
