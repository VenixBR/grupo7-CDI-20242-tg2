package DAOUser;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

import Entitys.Publicacao;
import biblioteca.SQL_connection;

public class DAO_Publicacao {

	public void CadastrarPublicacao(Publicacao publicacao) {
	    String SQL_command = "INSERT INTO Publicacao (Cod_Publicacao, Tipo, Ano, Nome, fk_Cod_Biblioteca) VALUES (?, ?, ?, ?, ?)";

	    PreparedStatement ps = null;

	    try {
	        ps = SQL_connection.getConnection().prepareStatement(SQL_command);
	        ps.setInt(1, publicacao.getCod_Publicacao());
	        ps.setString(2, publicacao.getTipo());
	        ps.setDate(3, Date.valueOf(publicacao.getAno() + "-01-01"));
	        ps.setString(4, publicacao.getNome());
	        ps.setInt(5, publicacao.getFk_Cod_Biblioteca());

	        ps.execute();
	        ps.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

    // Função para buscar uma publicação pelo código
    public Publicacao BuscarPublicacao(int Cod_Publicacao) {
        Publicacao pub = new Publicacao();
        String SQL_command = "SELECT * FROM Publicacao WHERE Cod_Publicacao=?";
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = SQL_connection.getConnection().prepareStatement(SQL_command);
            ps.setInt(1, Cod_Publicacao);
            rs = ps.executeQuery();

            if (rs.next()) {
                pub = new Publicacao();
                pub.setCod_Publicacao(rs.getInt("Cod_Publicacao"));
                pub.setTipo(rs.getString("Tipo"));
                pub.setAno(rs.getDate("Ano"));
                pub.setNome(rs.getString("Nome"));
                pub.setFk_Cod_Biblioteca(rs.getInt("fk_Cod_Biblioteca"));
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
        return pub;
    }

    // Função para remover uma publicação pelo código
    public void RemoverPublicacao(int Cod_Publicacao) {
        String SQL_command = "DELETE FROM Publicacao WHERE Cod_Publicacao=?";
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = SQL_connection.getConnection().prepareStatement(SQL_command);

            ps.setInt(1, Cod_Publicacao);

            // Executa a consulta para remover a publicação
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
}