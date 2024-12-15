package DAOUser;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Entitys.Funcionario;
import biblioteca.SQL_connection;

public class DAO_Funcionario {

    public void CadastrarFuncionario(Funcionario funcionario) {
        String SQL_command = "INSERT INTO Funcionario (Cod_Funcionario, Salario, Nome) VALUES (?, ?, ?)";
        PreparedStatement ps = null;

        try {
            ps = SQL_connection.getConnection().prepareStatement(SQL_command);
            ps.setInt(1, funcionario.getCod_Funcionario());
            
            ps.setBigDecimal(2, funcionario.getSalario());  
            
            ps.setString(3, funcionario.getNome());
            ps.execute();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Funcionario BuscarFuncionario(int Cod_Funcionario) {
        Funcionario funcionario = new Funcionario();
        String SQL_command = "SELECT * FROM Funcionario WHERE Cod_Funcionario=?";
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = SQL_connection.getConnection().prepareStatement(SQL_command);
            ps.setInt(1, Cod_Funcionario);
            rs = ps.executeQuery();

            if(rs.next()) {
                funcionario.setCod_Funcionario(rs.getInt("Cod_Funcionario"));
                funcionario.setSalario(rs.getBigDecimal("Salario"));
                funcionario.setNome(rs.getString("Nome"));
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
        return funcionario;
    }

    public void RemoverFuncionario(int Cod_Funcionario) {
        String SQL_command = "DELETE FROM Funcionario WHERE Cod_Funcionario=?";
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = SQL_connection.getConnection().prepareStatement(SQL_command);
            ps.setInt(1, Cod_Funcionario);
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
