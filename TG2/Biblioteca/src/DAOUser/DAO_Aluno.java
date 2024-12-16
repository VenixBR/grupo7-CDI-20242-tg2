package DAOUser;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Entitys.Aluno;
import biblioteca.SQL_connection;

public class DAO_Aluno {

    // Função para cadastrar um aluno no banco de dados
    public void CadastrarAluno(Aluno aluno) {
        String SQL_command = "INSERT INTO Aluno (Matricula, Endereco, Nome, fk_Cod_Centro) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = null;

        try {
            ps = SQL_connection.getConnection().prepareStatement(SQL_command);
            ps.setInt(1, aluno.getMatricula());
            ps.setString(2, aluno.getEndereco());
            ps.setString(3, aluno.getNome());
            ps.setInt(4, aluno.getFk_Cod_Centro());

            ps.execute();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Função para buscar um aluno no banco de dados
    public Aluno BuscarAluno(int Matricula) {
        Aluno aluno = new Aluno();
        String SQL_command = "SELECT * FROM Aluno WHERE Matricula=?";
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = SQL_connection.getConnection().prepareStatement(SQL_command);
            ps.setInt(1, Matricula);
            rs = ps.executeQuery();

            if (rs.next()) {
                aluno = new Aluno();
                aluno.setMatricula(rs.getInt("Matricula"));
                aluno.setEndereco(rs.getString("Endereco"));
                aluno.setNome(rs.getString("Nome"));
                aluno.setFk_Cod_Centro(rs.getInt("fk_Cod_Centro"));
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
        return aluno;
    }

    // Função para remover um aluno do banco de dados
    public void RemoverAluno(int Matricula) {
        String SQL_command = "DELETE FROM Aluno WHERE Matricula=?";
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = SQL_connection.getConnection().prepareStatement(SQL_command);
            ps.setInt(1, Matricula);

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
    
    public static boolean TestaMatricula(String matricula){
        
        boolean flag = false;
        String SQL_command = "SELECT COUNT(*) AS Result FROM Aluno WHERE Matricula=?";
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = SQL_connection.getConnection().prepareStatement(SQL_command);
            ps.setString(1, matricula);
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
    
    public static int PullCentro(String sigla){
        
        int result = -1;
        String SQL_command = "SELECT Cod_Centro AS Codigo FROM Centro WHERE Sigla=?";
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