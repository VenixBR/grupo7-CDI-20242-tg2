package DAOUser;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Entitys.Autor;
import biblioteca.SQL_connection;
import java.sql.Statement;

public class DAO_Autor {

    public void CadastrarAutor(Autor autor) {

        String SQL_command = "INSERT INTO Autor (Cod_Autor, Nome, Pais) VALUES (?, ?, ?)";

        PreparedStatement ps = null;

        try {
            ps = SQL_connection.getConnection().prepareStatement(SQL_command);
            ps.setInt(1, autor.getCod_Autor());
            ps.setString(2, autor.getNome());
            ps.setString(3, autor.getPais());

            ps.execute();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
     public void RemoverEscrito(int Cod_publicacao) {

        String SQL_command = "DELETE FROM Escrito WHERE fk_Cod_Publicacao=?";
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = SQL_connection.getConnection().prepareStatement(SQL_command);
            
            ps.setInt(1, Cod_publicacao);
            
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
    
    
    
    
    
    public static int PullAutor(String nome){
        
        int result = -1;
        String SQL_command = "SELECT Cod_Autor AS Codigo FROM Autor WHERE Nome=?";
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = SQL_connection.getConnection().prepareStatement(SQL_command);
            ps.setString(1, nome);
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
    
    public void EditarNome(int codigo, String nome){
        
        String SQL_command = "UPDATE Autor SET Nome=? where Cod_Autor=?";
	PreparedStatement ps = null;

	try {
	    ps = SQL_connection.getConnection().prepareStatement(SQL_command, Statement.RETURN_GENERATED_KEYS);
	    ps.setString(1, nome);
	    ps.setInt(2, codigo);

	    ps.executeUpdate();  // Executa a inserção no banco

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
    
    public void EditarPais(int codigo, String pais){
        
        String SQL_command = "UPDATE Autor SET Pais=? where Cod_Autor=?";
	PreparedStatement ps = null;

	try {
	    ps = SQL_connection.getConnection().prepareStatement(SQL_command, Statement.RETURN_GENERATED_KEYS);
	    ps.setString(1, pais);
	    ps.setInt(2, codigo);

	    ps.executeUpdate();  // Executa a inserção no banco

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

    public Autor BuscarAutor(int Cod_Autor) {

        Autor autor = new Autor();
        String SQL_command = "SELECT * FROM Autor WHERE Cod_Autor=?";
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = SQL_connection.getConnection().prepareStatement(SQL_command);
            ps.setInt(1, Cod_Autor);
            rs = ps.executeQuery();

            if (rs.next()) {
                autor.setCod_Autor(rs.getInt("Cod_Autor"));
                autor.setNome(rs.getString("Nome"));
                autor.setPais(rs.getString("Pais"));
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
        return autor;
    }

    public void RemoverAutor(int Cod_Autor) {

        String SQL_command = "DELETE FROM Autor WHERE Cod_Autor=?";
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = SQL_connection.getConnection().prepareStatement(SQL_command);

            ps.setInt(1, Cod_Autor);

            ps.executeUpdate();

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
}

