package DAOUser;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Entitys.Emprestimo;
import biblioteca.SQL_connection;
import java.sql.Statement;
import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;

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
    
    public static int PullFuncionario(String nome){
        
        int result = -1;
        String SQL_command = "SELECT Cod_Funcionario AS Codigo FROM Funcionario WHERE Nome=?";
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
    
    public static int PullPublicacao(String nome){
        
        int result = -1;
        String SQL_command = "SELECT Cod_Publicacao AS Codigo FROM Publicacao WHERE Nome=?";
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
    public static int PullAluno(String nome){
        
        int result = -1;
        String SQL_command = "SELECT matricula AS Codigo FROM Aluno WHERE Nome=?";
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
    
    public void EditarData(int id, java.sql.Date antiga, java.sql.Date nova){
        
        String SQL_command = "UPDATE Emprestimo SET Data=? where Data=? AND Cod_Emprestimo=?";
	PreparedStatement ps = null;
        
	try {
	    ps = SQL_connection.getConnection().prepareStatement(SQL_command, Statement.RETURN_GENERATED_KEYS);
	    ps.setDate(1, antiga);
	    ps.setDate(2, nova);
            ps.setInt(3, id);

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
    
    public void EditarHora(int id, java.sql.Time antiga, java.sql.Time nova){
        
        String SQL_command = "UPDATE Emprestimo SET Hora=? where Hora=? AND Cod_Emprestimo=?";
	PreparedStatement ps = null;
        
	try {
	    ps = SQL_connection.getConnection().prepareStatement(SQL_command, Statement.RETURN_GENERATED_KEYS);
	    ps.setTime(1, antiga);
	    ps.setTime(2, nova);
            ps.setInt(3, id);

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
    
    public void EditarFuncionario(int id, int antiga, int nova){
        
        String SQL_command = "UPDATE Emprestimo SET fk_Cod_Funcionario=? where fk_Cod_Funcionario=? AND Cod_Emprestimo=?";
	PreparedStatement ps = null;
        
	try {
	    ps = SQL_connection.getConnection().prepareStatement(SQL_command, Statement.RETURN_GENERATED_KEYS);
	    ps.setInt(1, antiga);
	    ps.setInt(2, nova);
            ps.setInt(3, id);

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
    
    public void EditarAluno(int id, int antiga, int nova){
        
        String SQL_command = "UPDATE Emprestimo SET fk_Matricula=? where fk_Matricula=? AND Cod_Emprestimo=?";
	PreparedStatement ps = null;
        
	try {
	    ps = SQL_connection.getConnection().prepareStatement(SQL_command, Statement.RETURN_GENERATED_KEYS);
	    ps.setInt(1, antiga);
	    ps.setInt(2, nova);
            ps.setInt(3, id);

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
    
    public void EditarPublicacao(int id, int antiga, int nova){
        
        String SQL_command = "UPDATE Emprestimo SET fk_Cod_Publicacao=? where fk_Cod_Publicacao=? AND Cod_Emprestimo=?";
	PreparedStatement ps = null;
        
	try {
	    ps = SQL_connection.getConnection().prepareStatement(SQL_command, Statement.RETURN_GENERATED_KEYS);
	    ps.setInt(1, antiga);
	    ps.setInt(2, nova);
            ps.setInt(3, id);

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
    

}