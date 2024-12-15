package biblioteca;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Entitys.Biblioteca;
import Entitys.Centro;

public class DAOUser {

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
