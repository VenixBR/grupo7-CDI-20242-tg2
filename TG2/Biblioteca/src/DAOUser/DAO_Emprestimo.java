package DAOUser;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Entitys.Emprestimo;
import biblioteca.SQL_connection;

public class DAO_Emprestimo {

    // Função para cadastrar um novo empréstimo
    public void CadastrarEmprestimo(Emprestimo emprestimo) {
        String SQL_command = "INSERT INTO Emprestimo (Cod_Emprestimo, Data_, Hora, fk_Cod_Publicacao, fk_Matricula, fk_Cod_Funcionario) VALUES (?, ?, ?, ?, ?, ?)";

        PreparedStatement ps = null;

        try {
            ps = SQL_connection.getConnection().prepareStatement(SQL_command);
            ps.setInt(1, emprestimo.getCod_Emprestimo());
            ps.setDate(2, emprestimo.getData());
            ps.setTime(3, emprestimo.getHora());
            ps.setInt(4, emprestimo.getFk_Cod_Publicacao());
            ps.setInt(5, emprestimo.getFk_Matricula());
            ps.setInt(6, emprestimo.getFk_Cod_Funcionario());

            ps.execute();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Função para buscar um empréstimo pelo código
    public Emprestimo BuscarEmprestimo(int Cod_Emprestimo) {
        Emprestimo emprestimo = new Emprestimo();
        String SQL_command = "SELECT * FROM Emprestimo WHERE Cod_Emprestimo=?";
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = SQL_connection.getConnection().prepareStatement(SQL_command);
            ps.setInt(1, Cod_Emprestimo);
            rs = ps.executeQuery();

            if (rs.next()) {
                emprestimo = new Emprestimo();
                emprestimo.setCod_Emprestimo(rs.getInt("Cod_Emprestimo"));
                emprestimo.setData(rs.getDate("Data_"));
                emprestimo.setHora(rs.getTime("Hora"));
                emprestimo.setFk_Cod_Publicacao(rs.getInt("fk_Cod_Publicacao"));
                emprestimo.setFk_Matricula(rs.getInt("fk_Matricula"));
                emprestimo.setFk_Cod_Funcionario(rs.getInt("fk_Cod_Funcionario"));
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
        return emprestimo;
    }

    // Função para remover um empréstimo pelo código
    public void RemoverEmprestimo(int Cod_Emprestimo) {
        String SQL_command = "DELETE FROM Emprestimo WHERE Cod_Emprestimo=?";
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = SQL_connection.getConnection().prepareStatement(SQL_command);
            ps.setInt(1, Cod_Emprestimo);
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