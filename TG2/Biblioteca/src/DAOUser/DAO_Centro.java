package DAOUser;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import Entitys.Centro;
import biblioteca.SQL_connection;

public class DAO_Centro{
    
	public void CadastrarCentro(Centro centro) {
	    String SQL_command = "INSERT INTO Centro (Nome, Sigla) VALUES (?, ?)";
	    PreparedStatement ps = null;

	    try {
	        ps = SQL_connection.getConnection().prepareStatement(SQL_command, Statement.RETURN_GENERATED_KEYS);
	        ps.setString(1, centro.getNome());
	        ps.setString(2, centro.getSigla());

	        ps.executeUpdate();  // Executa a inserção no banco

	        // Agora, use o método getUltimoCodigoInserido para obter o código do centro recém-criado
	        int codCentro = getUltimoCodigoInserido();
	        centro.setCod_Centro(codCentro);  // Define o código no objeto Centro

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
        ResultSet rs = null;

        try {
            ps = SQL_connection.getConnection().prepareStatement(SQL_command);
            ps.setInt(1, Cod_Centro);
            ps.execute();
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
    

    public int getUltimoCodigoInserido() {
        String SQL_command = "SELECT LAST_INSERT_ID()";  // Comando para obter o último ID inserido no banco
        Statement stmt = null;
        ResultSet rs = null;
        int ultimoCodigo = -1;  // Valor padrão para erro ou quando não encontrado

        try {
            stmt = SQL_connection.getConnection().createStatement();
            rs = stmt.executeQuery(SQL_command);  // Executa o comando

            if (rs.next()) {
                ultimoCodigo = rs.getInt(1);  // Recupera o valor da primeira coluna (último ID inserido)
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return ultimoCodigo;
    }
    
}
