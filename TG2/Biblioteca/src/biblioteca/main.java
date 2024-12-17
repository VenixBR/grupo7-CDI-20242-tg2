/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package biblioteca;

import DAOUser.DAO_Centro;
import DAOUser.DAO_Aluno;
import DAOUser.DAO_Publicacao;
import DAOUser.DAO_Biblioteca;
import DAOUser.DAO_Funcionario;
import DAOUser.DAO_Autor;
import DAOUser.DAO_Emprestimo;
import static DAOUser.DAO_Emprestimo.PullAluno;
import static DAOUser.DAO_Emprestimo.PullFuncionario;
import static DAOUser.DAO_Emprestimo.PullPublicacao;
import Entitys.Centro;
import Entitys.Aluno;
import Entitys.Academico;
import Entitys.Autor;
import Entitys.Literatura;
import Entitys.Autoajuda;
import Entitys.Publicacao;
import Entitys.Pertence;
import Entitys.Funcionario;
import Entitys.Biblioteca;
import Exceptions.NomeGrande;
import Exceptions.CentroSiglaGrande;
import Exceptions.PubAnoGrande;
import DAOUser.DAO_General;
import Entitys.Emprestimo;
import Entitys.Escrito;
import Exceptions.CamposNaoInformados;
import Exceptions.CentroEdicaoIgual;
import Exceptions.LinhaNaoSelecionada;
import Exceptions.CentroSiglaUsada;
import java.sql.Date;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.text.ParseException;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author desenvolvimento
 */
public class main extends javax.swing.JFrame {

	// Variável de conexão
	private Connection conn;

	// Método para conectar ao banco de dados (se necessário)
	private void conectar() throws SQLException {
	    // Verifica se a conexão não foi estabelecida ou está fechada
	    if (conn == null || conn.isClosed()) {
	        conn = SQL_connection.getConnection(); // Estabelece a conexão com o banco
	    }
	}
        
        public void fecharConexao() throws SQLException {
	    if (conn != null && !conn.isClosed()) {
	        conn.close(); // 
	    }
	}
        
        public boolean BibCentroIsEmpty(){
            for(int i=0 ; i<TB_Bib_Centros.getRowCount(); i++){
                if((Boolean)TB_Bib_Centros.getValueAt(i, 0) == true)
                    return false;
            }
            return true;
        }
        
        public boolean PubAutoresIsEmpty(){
            for(int i=0 ; i<TB_Pub_Autores.getRowCount(); i++){
                if((Boolean)TB_Pub_Autores.getValueAt(i, 0) == true)
                    return false;
            }
            return true;
        }
        
        private static boolean validarData(String data) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false); // Impede datas inválidas, como 31/02/2023
        try {
            sdf.parse(data);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
    
    // Método para validar a hora no formato HH:mm
    private static boolean validarHora(String hora) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        sdf.setLenient(false); // Impede horas inválidas como 25:00
        try {
            sdf.parse(hora);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
    
    public static Date converterStringParaSQLDate(String dataStr) {
        try {
            // Define o formato da data
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date dataUtil = sdf.parse(dataStr); // Converte para java.util.Date
            return new Date(dataUtil.getTime()); // Converte para java.sql.Date
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(null, "A data informada é inválida!", 
                                                  "Erro", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
    
    public static Time converterStringParaSQLTime(String horaStr) {
        try {
            // Define o formato da hora (HH:mm:)
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            java.util.Date dataUtil = sdf.parse(horaStr); // Converte para java.util.Date
            return new Time(dataUtil.getTime()); // Converte para java.sql.Time
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(null, "A hora informada é inválida!", 
                                                  "Erro", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
        

	// Método para preencher a tabela com os centros
	private void carregarCentros() {
	    try {
	        if (conn == null || conn.isClosed()) {
	            conectar();
	        }

	        String query = "SELECT * FROM Centro"; 
	        Statement stmt = conn.createStatement();
	        ResultSet rs = stmt.executeQuery(query);

	        DefaultTableModel model = (DefaultTableModel) TB_Centro.getModel();
                DefaultTableModel model2 = (DefaultTableModel) TB_Bib_Centros.getModel();
	        model.setRowCount(0);  // Limpa a tabela antes de adicionar novos dados

	        while (rs.next()) {
	            Object[] row = { rs.getInt("cod_Centro"), rs.getString("sigla"), rs.getString("nome") };
	            model.addRow(row);
                    Itens_Aluno_Centro.addItem(rs.getString("Sigla"));
                    Object[] row2 = {false, rs.getString("Sigla")}; 
                    model2.addRow(row2);
	        }

	        rs.close();
	        stmt.close();

	    } catch (SQLException ex) {
	        JOptionPane.showMessageDialog(this, "Erro ao carregar dados: " + ex.getMessage());
	    }
	}
      
        // Método para preencher a tabela com os centros com o nome indicado
	private void carregarCentrosNome(String nome) {
	    try {
	        if (conn == null || conn.isClosed()) {
	            conectar();
	        }

	        String query = "SELECT * FROM Centro WHERE Nome=?"; 
                PreparedStatement ps = null;
                ps = SQL_connection.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, nome);
	        //Statement stmt = conn.createStatement();
	        //ResultSet rs = stmt.executeQuery(query);
                ResultSet rs = ps.executeQuery();

	        DefaultTableModel model = (DefaultTableModel) TB_Centro.getModel();
	        model.setRowCount(0);  // Limpa a tabela antes de adicionar novos dados

	        while (rs.next()) {
	            Object[] row = { rs.getInt("cod_Centro"), rs.getString("sigla"), rs.getString("nome") };
	            model.addRow(row);
	        }
	        rs.close();
                ps.close();
	        //stmt.close();
	    } catch (SQLException ex) {
	        JOptionPane.showMessageDialog(this, "Erro ao carregar dados: " + ex.getMessage());
	    }
	}
        
        // Método para preencher a tabela com os centros com o nome indicado
	private void carregarCentrosSigla(String sigla) {
	    try {
	        if (conn == null || conn.isClosed()) {
	            conectar();
	        }

	        String query = "SELECT * FROM Centro WHERE Sigla=?"; 
                PreparedStatement ps = null;
                ps = SQL_connection.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, sigla);
	        //Statement stmt = conn.createStatement();
	        //ResultSet rs = stmt.executeQuery(query);
                ResultSet rs = ps.executeQuery();

	        DefaultTableModel model = (DefaultTableModel) TB_Centro.getModel();
	        model.setRowCount(0);  // Limpa a tabela antes de adicionar novos dados

	        while (rs.next()) {
	            Object[] row = { rs.getInt("cod_Centro"), rs.getString("sigla"), rs.getString("nome") };
	            model.addRow(row);
	        }
	        rs.close();
                ps.close();
	        //stmt.close();
	    } catch (SQLException ex) {
	        JOptionPane.showMessageDialog(this, "Erro ao carregar dados: " + ex.getMessage());
	    }
	}
        
         // Método para preencher a tabela com os centros com o nome e sigla indicados
        private void carregarCentrosNomeSigla(String nome, String sigla) {
	    try {
	        if (conn == null || conn.isClosed()) {
	            conectar();
	        }

	        String query = "SELECT * FROM Centro WHERE Nome=? AND Sigla=?"; 
                PreparedStatement ps = null;
                ps = SQL_connection.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, nome);
                ps.setString(2, sigla);
                ResultSet rs = ps.executeQuery();

	        DefaultTableModel model = (DefaultTableModel) TB_Centro.getModel();
	        model.setRowCount(0);  // Limpa a tabela antes de adicionar novos dados

	        while (rs.next()) {
	            Object[] row = { rs.getInt("cod_Centro"), rs.getString("sigla"), rs.getString("nome") };
	            model.addRow(row);
	        }
	        rs.close();
                ps.close();
	        //stmt.close();
	    } catch (SQLException ex) {
	        JOptionPane.showMessageDialog(this, "Erro ao carregar dados: " + ex.getMessage());
	    }
	}
        
	// Método para preencher a tabela com os alunos
	private void carregarAlunos() {
	    try {
	        if (conn == null || conn.isClosed()) {
	            conectar(); 
	        }
                


	        // Query SQL para buscar os dados dos alunos
	        //String query = "SELECT * FROM Aluno"; 
                String query = "SELECT Aluno.matricula, Aluno.nome, Centro.sigla, Aluno.endereco FROM Aluno JOIN Centro ON Aluno.fk_cod_centro = Centro.cod_centro";
	        Statement stmt = conn.createStatement();
	        ResultSet rs = stmt.executeQuery(query);

	        DefaultTableModel model = (DefaultTableModel) TB_Aluno.getModel();
	        model.setRowCount(0); 

	        while (rs.next()) {
	            Object[] row = {
	                rs.getInt("Matricula"),
	                rs.getString("Nome"),
	                rs.getString("Sigla"),
	                rs.getString("Endereco")
	            };
	            model.addRow(row);
                    Itens_Empres_Aluno.addItem(rs.getString("Nome"));
	        }

	        rs.close();
	        stmt.close();

	    } catch (SQLException ex) {
	        JOptionPane.showMessageDialog(this, "Erro ao carregar dados dos alunos: " + ex.getMessage());
	    }
	}
        
        // Método para preencher a tabela com os centros com o nome indicado
        private void carregarAlunosUm(Object parametro, String SQL) {
	    try {
	        if (conn == null || conn.isClosed()) {
	            conectar();
	        }

	        String query = SQL; 
                PreparedStatement ps = null;
                ps = SQL_connection.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                ps.setObject(1, parametro);
                ResultSet rs = ps.executeQuery();

	        DefaultTableModel model = (DefaultTableModel) TB_Aluno.getModel();
	        model.setRowCount(0);  // Limpa a tabela antes de adicionar novos dados

	        while (rs.next()) {
	            Object[] row = { rs.getInt("Matricula"), rs.getString("Nome"), rs.getString("sigla"), rs.getString("Endereco") };
	            model.addRow(row);
	        }
	        rs.close();
                ps.close();
	    } catch (SQLException ex) {
	        JOptionPane.showMessageDialog(this, "Erro ao carregar dados: " + ex.getMessage());
	    }
	}
        
        private void carregarAlunosDois(Object parametro1, Object parametro2, String SQL) {
	    try {
	        if (conn == null || conn.isClosed()) {
	            conectar();
	        }

	        String query = SQL; 
                PreparedStatement ps = null;
                ps = SQL_connection.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                ps.setObject(1, parametro1);
                ps.setObject(2, parametro2);
                ResultSet rs = ps.executeQuery();

	        DefaultTableModel model = (DefaultTableModel) TB_Aluno.getModel();
	        model.setRowCount(0);  // Limpa a tabela antes de adicionar novos dados

	        while (rs.next()) {
	            Object[] row = { rs.getInt("Matricula"), rs.getString("Nome"), rs.getString("sigla"), rs.getString("Endereco") };
	            model.addRow(row);
	        }
	        rs.close();
                ps.close();
	    } catch (SQLException ex) {
	        JOptionPane.showMessageDialog(this, "Erro ao carregar dados: " + ex.getMessage());
	    }
	}
        
        private void carregarAlunosTres(Object parametro1, Object parametro2 , Object parametro3, String SQL) {
	    try {
	        if (conn == null || conn.isClosed()) {
	            conectar();
	        }

	        String query = SQL; 
                PreparedStatement ps = null;
                ps = SQL_connection.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                ps.setObject(1, parametro1);
                ps.setObject(2, parametro2);
                ps.setObject(3, parametro3);
                ResultSet rs = ps.executeQuery();

	        DefaultTableModel model = (DefaultTableModel) TB_Aluno.getModel();
	        model.setRowCount(0);  // Limpa a tabela antes de adicionar novos dados

	        while (rs.next()) {
	            Object[] row = { rs.getInt("Matricula"), rs.getString("Nome"), rs.getString("sigla"), rs.getString("Endereco") };
	            model.addRow(row);
	        }
	        rs.close();
                ps.close();
	    } catch (SQLException ex) {
	        JOptionPane.showMessageDialog(this, "Erro ao carregar dados: " + ex.getMessage());
	    }
	}
        
        private void carregarAlunosQuatro(Object parametro1, Object parametro2 , Object parametro3, Object parametro4, String SQL) {
	    try {
	        if (conn == null || conn.isClosed()) {
	            conectar();
	        }

	        String query = SQL; 
                PreparedStatement ps = null;
                ps = SQL_connection.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                ps.setObject(1, parametro1);
                ps.setObject(2, parametro2);
                ps.setObject(3, parametro3);
                ps.setObject(4, parametro4);
                ResultSet rs = ps.executeQuery();

	        DefaultTableModel model = (DefaultTableModel) TB_Aluno.getModel();
	        model.setRowCount(0);  // Limpa a tabela antes de adicionar novos dados

	        while (rs.next()) {
	            Object[] row = { rs.getInt("Matricula"), rs.getString("Nome"), rs.getString("sigla"), rs.getString("Endereco") };
	            model.addRow(row);
	        }
	        rs.close();
                ps.close();
	    } catch (SQLException ex) {
	        JOptionPane.showMessageDialog(this, "Erro ao carregar dados: " + ex.getMessage());
	    }
	}

	private void carregarPublicacoes() {
	    String query = "SELECT p.Cod_Publicacao, p.Tipo, p.Ano, p.Nome, b.Sigla AS Biblioteca, " +
	                   "a.Edicao, a.Area, " +
	                   "l.Genero_Textual, " +
	                   "au.Assunto, " +
	                   "e.fk_Cod_Autor " +
	                   "FROM Publicacao p " +
	                   "JOIN Biblioteca b ON p.fk_Cod_Biblioteca = b.Cod_Biblioteca " +
	                   "LEFT JOIN Academico a ON p.Cod_Publicacao = a.fk_Cod_Publicacao " +
	                   "LEFT JOIN Literatura l ON p.Cod_Publicacao = l.fk_Cod_Publicacao " +
	                   "LEFT JOIN Autoajuda au ON p.Cod_Publicacao = au.fk_Cod_Publicacao " +
	                   "LEFT JOIN Escrito e ON p.Cod_Publicacao = e.fk_Cod_Publicacao";

	    Connection conn = null;
	    Statement stmt = null;
	    ResultSet rs = null;

	    try {
	        conn = SQL_connection.getConnection();  
	        stmt = conn.createStatement(); 
	        rs = stmt.executeQuery(query);  

	        DefaultTableModel model = (DefaultTableModel) TB_Publicacao.getModel();
	        model.setRowCount(0);  

	        while (rs.next()) {
	            Object[] row = {
	                rs.getInt("Cod_Publicacao"),  // Esta é uma coluna do tipo INT
	                rs.getString("Nome"),         // Esta é uma coluna do tipo String
	                rs.getInt("Ano"),            // Se "Ano" for uma coluna DATE, use rs.getDate() ao invés de rs.getInt()
	                rs.getString("Biblioteca"),   // Esta é uma coluna do tipo String
	                rs.getString("Tipo"),         // Esta é uma coluna do tipo String
	                rs.getInt("Edicao"),          // Esta é uma coluna do tipo INT
	                rs.getString("Area"),         // Esta é uma coluna do tipo String
	                rs.getString("Genero_Textual"),  // Esta é uma coluna do tipo String
	                rs.getString("Assunto"),      // Esta é uma coluna do tipo String
	            };
	            model.addRow(row);  // Adiciona a linha à tabela
                    Itens_Empres_Publicacao.addItem(rs.getString("Nome"));
	        }
	    } catch (SQLException ex) {
	        JOptionPane.showMessageDialog(this, "Erro ao carregar dados: " + ex.getMessage());
	    } finally {
	        try {
	            if (rs != null) {
	                rs.close();
	            }
	            if (stmt != null) {
	                stmt.close();
	            }
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        }
	    }
	}

	private void carregarAutores() {
	    String query = "SELECT Cod_Autor, Nome, Pais FROM Autor";

	    Connection conn = null;
	    Statement stmt = null;
	    ResultSet rs = null;

	    try {
	        conn = SQL_connection.getConnection(); 
	        stmt = conn.createStatement(); 
	        rs = stmt.executeQuery(query); 

	        DefaultTableModel model = (DefaultTableModel) TB_Autor.getModel();
                DefaultTableModel model2 = (DefaultTableModel) TB_Pub_Autores.getModel();
	        model.setRowCount(0);  
                model2.setRowCount(0); 

	        while (rs.next()) {
	            Object[] row = {
	                rs.getInt("Cod_Autor"),
	                rs.getString("Nome"),
	                rs.getString("Pais")
	            };
                    Object[] row2 ={false,rs.getString("Nome")};
	            model.addRow(row);
                    model2.addRow(row2);
	        }
	    } catch (SQLException ex) {
	        JOptionPane.showMessageDialog(this, "Erro ao carregar dados: " + ex.getMessage());
	    } finally {
	        try {
	            if (rs != null) {
	                rs.close();
	            }
	            if (stmt != null) {
	                stmt.close();
	            }
	        } catch (SQLException ex) {
	            ex.printStackTrace(); 
	        }
	    }
	}

        private void carregarFuncionariosSalarios_MIN_MAX_AVG() {
	    try {
	        if (conn == null || conn.isClosed()) {
	            conectar();
	        }
                
	        String query = "SELECT Salario FROM Funcionario WHERE Salario = (SELECT MAX(Salario) FROM Funcionario)"; 
                PreparedStatement ps = null;
                ps = SQL_connection.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                ResultSet rs = ps.executeQuery();
                
                rs.next(); //pula para salario
                Funcionario_Sal_Max.setText(rs.getString("salario"));
                
	        rs.close();
                ps.close();
                
                String query1 = "SELECT Salario FROM Funcionario WHERE Salario = (SELECT MIN(Salario) FROM Funcionario)"; 
                PreparedStatement ps1 = null;
                ps1 = SQL_connection.getConnection().prepareStatement(query1, Statement.RETURN_GENERATED_KEYS);
                ResultSet rs1 = ps1.executeQuery();
                
                rs1.next(); //pula para salario
                Funcionario_Sal_Min.setText(rs1.getString("salario"));
                
                String query2 = "SELECT ROUND(AVG(Salario),2) AS Media_Salarial FROM Funcionario;"; 
                PreparedStatement ps2 = null;
                ps2 = SQL_connection.getConnection().prepareStatement(query2, Statement.RETURN_GENERATED_KEYS);
                ResultSet rs2 = ps2.executeQuery();
                
                rs2.next(); //pula para salario
                Funcionario_Sal_Med.setText(rs2.getString("Media_Salarial"));
                
	        rs.close();
                ps.close();
	    } catch (SQLException ex) {
	        JOptionPane.showMessageDialog(this, "Erro ao carregar dados: " + ex.getMessage());
	    }
	}
        
	private void carregarFuncionariosNome(String nome) {
	    try {
	        if (conn == null || conn.isClosed()) {
	            conectar();
	        }
                
	        String query = "SELECT * FROM Funcionario WHERE Nome=?"; 
                PreparedStatement ps = null;
                ps = SQL_connection.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, nome);
                ResultSet rs = ps.executeQuery();

	        DefaultTableModel model = (DefaultTableModel) TB_Funcionario.getModel();
	        model.setRowCount(0);  // Limpa a tabela antes de adicionar novos dados

	        while (rs.next()) {
	            Object[] row = { rs.getInt("cod_Funcionario"), rs.getString("nome"), rs.getString("salario") };
	            model.addRow(row);
	        }
	        rs.close();
                ps.close();
	    } catch (SQLException ex) {
	        JOptionPane.showMessageDialog(this, "Erro ao carregar dados: " + ex.getMessage());
	    }
	}
      
	private void carregarFuncionariosSalario(String salario) {
	    try {
	        if (conn == null || conn.isClosed()) {
	            conectar();
	        }

	        String query = "SELECT * FROM Funcionario WHERE Salario=?"; 
                PreparedStatement ps = null;
                ps = SQL_connection.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, salario);
                ResultSet rs = ps.executeQuery();

	        DefaultTableModel model = (DefaultTableModel) TB_Funcionario.getModel();
	        model.setRowCount(0);  // Limpa a tabela antes de adicionar novos dados

	        while (rs.next()) {
	            Object[] row = { rs.getInt("cod_Funcionario"), rs.getString("nome"), rs.getString("salario") };
	            model.addRow(row);
	        }
	        rs.close();
                ps.close();
	    } catch (SQLException ex) {
	        JOptionPane.showMessageDialog(this, "Erro ao carregar dados: " + ex.getMessage());
	    }
	}
        
        private void carregarFuncionariosNomeSalario(String nome, String salario) {
	    try {
	        if (conn == null || conn.isClosed()) {
	            conectar();
	        }

	        String query = "SELECT * FROM Funcionario WHERE Nome=? AND Salario=?"; 
                PreparedStatement ps = null;
                ps = SQL_connection.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, nome);
                ps.setString(2, salario);
                ResultSet rs = ps.executeQuery();

	        DefaultTableModel model = (DefaultTableModel) TB_Funcionario.getModel();
	        model.setRowCount(0);  // Limpa a tabela antes de adicionar novos dados

	        while (rs.next()) {
	            Object[] row = { rs.getInt("cod_Funcionario"), rs.getString("nome"), rs.getString("salario") };
	            model.addRow(row);
	        }
	        rs.close();
                ps.close();
	    } catch (SQLException ex) {
	        JOptionPane.showMessageDialog(this, "Erro ao carregar dados: " + ex.getMessage());
	    }
	}

	private void carregarBibliotecas() {
	    String query = "SELECT Cod_Biblioteca, Nome, Endereco, Sigla FROM Biblioteca";

	    Connection conn = null;
	    Statement stmt = null;
	    ResultSet rs = null;

	    try {
	        conn = SQL_connection.getConnection();
	        stmt = conn.createStatement();
	        rs = stmt.executeQuery(query);

	        DefaultTableModel model = (DefaultTableModel) TB_Biblioteca.getModel();
	        model.setRowCount(0);

	        while (rs.next()) {
	            Object[] row = {
	                rs.getInt("Cod_Biblioteca"),
                        rs.getString("Sigla"),
	                rs.getString("Nome"),
	                rs.getString("Endereco")
	            };
	            model.addRow(row);
                    Itens_Pub_Biblioteca.addItem(rs.getString("Sigla"));
	        }
	    } catch (SQLException ex) {
	        JOptionPane.showMessageDialog(this, "Erro ao carregar dados: " + ex.getMessage());
	    } finally {
	        try {
	            if (rs != null) {
	                rs.close();
	            }
	            if (stmt != null) {
	                stmt.close();
	            }
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        }
	    }
	}
	
	private void carregarFuncionarios() {
	    String query = "SELECT Cod_Funcionario, Nome, Salario FROM Funcionario";

	    Connection conn = null;
	    Statement stmt = null;
	    ResultSet rs = null;
            
            carregarFuncionariosSalarios_MIN_MAX_AVG();

	    try {
	        conn = SQL_connection.getConnection();
	        stmt = conn.createStatement();
	        rs = stmt.executeQuery(query);

	        DefaultTableModel model = (DefaultTableModel) TB_Funcionario.getModel();
	        model.setRowCount(0);

	        while (rs.next()) {
	            Object[] row = {
	                rs.getInt("Cod_Funcionario"),
	                rs.getString("Nome"),
	                rs.getString("Salario")
	            };
	            model.addRow(row);
                    Itens_Empres_Funcionario.addItem(rs.getString("Nome"));
	        }
	    } catch (SQLException ex) {
	        JOptionPane.showMessageDialog(this, "Erro ao carregar dados: " + ex.getMessage());
	    } finally {
	        try {
	            if (rs != null) {
	                rs.close();
	            }
	            if (stmt != null) {
	                stmt.close();
	            }
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        }
	    }
	}
	
	private void carregarEmprestimos() {
	    // Consulta SQL para buscar os dados dos empréstimos
	    String query = "SELECT e.Cod_Emprestimo, e.Data_, e.Hora, " +
	                   "p.Nome AS Publicacao, a.Nome AS Aluno, f.Nome AS Funcionario " +
	                   "FROM Emprestimo e " +
	                   "JOIN Publicacao p ON e.fk_Cod_Publicacao = p.Cod_Publicacao " +
	                   "JOIN Aluno a ON e.fk_Matricula = a.Matricula " +
	                   "JOIN Funcionario f ON e.fk_Cod_Funcionario = f.Cod_Funcionario";

	    Connection conn = null;
	    Statement stmt = null;
	    ResultSet rs = null;

	    try {
	        conn = SQL_connection.getConnection();
	        stmt = conn.createStatement();
	        rs = stmt.executeQuery(query);

	        DefaultTableModel model = (DefaultTableModel) TB_Emprestimo.getModel();
	        model.setRowCount(0);

	        while (rs.next()) {
	            Object[] row = {
	                rs.getInt("Cod_Emprestimo"),
	                rs.getDate("Data_"),
	                rs.getTime("Hora"),
	                rs.getString("Funcionario"),
	                rs.getString("Publicacao"),
	                rs.getString("Aluno")
	            };
	            model.addRow(row);
	        }
	    } catch (SQLException ex) {
	        JOptionPane.showMessageDialog(this, "Erro ao carregar dados: " + ex.getMessage());
	    } finally {
	        try {
	            if (rs != null) {
	                rs.close();
	            }
	            if (stmt != null) {
	                stmt.close();
	            }
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        }
	    }
	}

        private void carregarAutorNome(String nome) {
	    try {
	        if (conn == null || conn.isClosed()) {
	            conectar();
	        }

	        String query = "SELECT * FROM Autor WHERE Nome=?"; 
                PreparedStatement ps = null;
                ps = SQL_connection.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, nome);
                ResultSet rs = ps.executeQuery();

	        DefaultTableModel model = (DefaultTableModel) TB_Autor.getModel();
	        model.setRowCount(0);  // Limpa a tabela antes de adicionar novos dados

	        while (rs.next()) {
	            Object[] row = { rs.getInt("cod_Autor"), rs.getString("nome"), rs.getString("pais") };
	            model.addRow(row);
	        }
	        rs.close();
                ps.close();
	    } catch (SQLException ex) {
	        JOptionPane.showMessageDialog(this, "Erro ao carregar dados: " + ex.getMessage());
	    }
	}
         
        private void carregarAutorPais(String pais) {
	    try {
	        if (conn == null || conn.isClosed()) {
	            conectar();
	        }

	        String query = "SELECT * FROM Autor WHERE Pais=?"; 
                PreparedStatement ps = null;
                ps = SQL_connection.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, pais);
                ResultSet rs = ps.executeQuery();

	        DefaultTableModel model = (DefaultTableModel) TB_Autor.getModel();
	        model.setRowCount(0);  // Limpa a tabela antes de adicionar novos dados

	        while (rs.next()) {
	            Object[] row = { rs.getInt("cod_Autor"), rs.getString("nome"), rs.getString("pais") };
	            model.addRow(row);
	        }
	        rs.close();
                ps.close();
	    } catch (SQLException ex) {
	        JOptionPane.showMessageDialog(this, "Erro ao carregar dados: " + ex.getMessage());
	    }
	}
        
        private void carregarAutorNomePais(String nome, String pais) {
	    try {
	        if (conn == null || conn.isClosed()) {
	            conectar();
	        }

	        String query = "SELECT * FROM Autor WHERE Nome=? AND Pais=?"; 
                PreparedStatement ps = null;
                ps = SQL_connection.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, nome);
                ps.setString(2, pais);
                ResultSet rs = ps.executeQuery();

	        DefaultTableModel model = (DefaultTableModel) TB_Autor.getModel();
	        model.setRowCount(0);  // Limpa a tabela antes de adicionar novos dados

	        while (rs.next()) {
	            Object[] row = { rs.getInt("cod_Autor"), rs.getString("nome"), rs.getString("pais") };
	            model.addRow(row);
	        }
	        rs.close();
                ps.close();
	    } catch (SQLException ex) {
	        JOptionPane.showMessageDialog(this, "Erro ao carregar dados: " + ex.getMessage());
	    }
	}
	
    /**
     * Creates new form main
     */
    public main() {
        initComponents();
        TF_Pub_Edicao.setEnabled(false);
        TF_Pub_Area.setEnabled(false);
        TF_Pub_Genero.setEnabled(false);
        TF_Pub_Assunto.setEnabled(false);
        TF_Pub_Tipo.setEnabled(false);
        carregarCentros(); 
        carregarAlunos();
        carregarPublicacoes(); 
        carregarAutores();
        carregarBibliotecas();
        carregarFuncionarios();
        carregarEmprestimos();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        buttonGroup4 = new javax.swing.ButtonGroup();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        TB_Centro = new javax.swing.JTable();
        BT_Centro_Cadastrar = new javax.swing.JButton();
        BT_Centro_Remover = new javax.swing.JButton();
        TF_Centro_Sigla = new javax.swing.JTextField();
        TF_Centro_Nome = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        BT_Centro_Editar = new javax.swing.JButton();
        BT_Centro_Buscar = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        TB_Aluno = new javax.swing.JTable();
        TF_Aluno_Endereco = new javax.swing.JTextField();
        TF_Aluno_Nome = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        TF_Aluno_Matricula = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        Itens_Aluno_Centro = new javax.swing.JComboBox<>();
        jLabel21 = new javax.swing.JLabel();
        BT_Aluno_Editar = new javax.swing.JButton();
        BT_Aluno_Buscar = new javax.swing.JButton();
        BT_Aluno_Cadastrar = new javax.swing.JButton();
        BT_Aluno_Remover = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        TB_Publicacao = new javax.swing.JTable();
        Itens_Pub_Biblioteca = new javax.swing.JComboBox<>();
        jLabel15 = new javax.swing.JLabel();
        TF_Pub_Ano = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        TF_Pub_Nome = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        CheckBox_Academico = new javax.swing.JRadioButton();
        CheckBox_Literatura = new javax.swing.JRadioButton();
        CheckBox_Autoajuda = new javax.swing.JRadioButton();
        CheckBox_Outro = new javax.swing.JRadioButton();
        TF_Pub_Area = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        TF_Pub_Edicao = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        TF_Pub_Genero = new javax.swing.JTextField();
        jLabel38 = new javax.swing.JLabel();
        TF_Pub_Tipo = new javax.swing.JTextField();
        TF_Pub_Assunto = new javax.swing.JTextField();
        BT_Publicacao_Editar = new javax.swing.JButton();
        BT_Publicacao_Buscar = new javax.swing.JButton();
        BT_Publicacao_Cadastrar = new javax.swing.JButton();
        BT_Publicacao_Remover = new javax.swing.JButton();
        jScrollPane9 = new javax.swing.JScrollPane();
        TB_Pub_Autores = new javax.swing.JTable();
        jLabel27 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TB_Biblioteca = new javax.swing.JTable();
        TF_Bib_Endereco = new javax.swing.JTextField();
        TF_Bib_Nome = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        TF_Bib_Sigla = new javax.swing.JTextField();
        jLabel39 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        BT_Biblioteca_Buscar = new javax.swing.JButton();
        BT_Biblioteca_Cadastrar = new javax.swing.JButton();
        BT_Biblioteca_Remover = new javax.swing.JButton();
        BT_Biblioteca_Editar = new javax.swing.JButton();
        jScrollPane8 = new javax.swing.JScrollPane();
        TB_Bib_Centros = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        TB_Emprestimo = new javax.swing.JTable();
        TF_Empres_Data = new javax.swing.JTextField();
        TF_Empres_Hora = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        Itens_Empres_Aluno = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        Itens_Empres_Funcionario = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        Itens_Empres_Publicacao = new javax.swing.JComboBox<>();
        BT_Emprestimo_Buscar = new javax.swing.JButton();
        BT_Emprestimo_Cadastrar = new javax.swing.JButton();
        BT_Emprestimo_Remover = new javax.swing.JButton();
        BT_Emprestimo_Editar = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        TB_Funcionario = new javax.swing.JTable();
        TF_Funci_Salario = new javax.swing.JTextField();
        TF_Funci_Nome = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        BT_Funcionario_Buscar = new javax.swing.JButton();
        BT_Funcionario_Cadastrar = new javax.swing.JButton();
        BT_Funcionario_Remover = new javax.swing.JButton();
        BT_Funcionario_Editar = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        Funcionario_Sal_Max = new javax.swing.JLabel();
        Funcionario_Sal_Min = new javax.swing.JLabel();
        Funcionario_Sal_Med = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        TB_Autor = new javax.swing.JTable();
        TF_Autor_Pais = new javax.swing.JTextField();
        TF_Autor_Nome = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        BT_Autor_Buscar = new javax.swing.JButton();
        BT_Autor_Cadastrar = new javax.swing.JButton();
        BT_Autor_Remover = new javax.swing.JButton();
        BT_Autor_Editar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTabbedPane1.setPreferredSize(new java.awt.Dimension(1300, 640));

        TB_Centro.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Codigo", "Sigla", "Nome"
            }
        ));
        TB_Centro.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TB_CentroMouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(TB_Centro);
        if (TB_Centro.getColumnModel().getColumnCount() > 0) {
            TB_Centro.getColumnModel().getColumn(0).setPreferredWidth(30);
            TB_Centro.getColumnModel().getColumn(1).setPreferredWidth(30);
            TB_Centro.getColumnModel().getColumn(2).setPreferredWidth(600);
        }

        BT_Centro_Cadastrar.setText("Cadastrar");
        BT_Centro_Cadastrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BT_Centro_CadastrarActionPerformed(evt);
            }
        });

        BT_Centro_Remover.setText("Remover");
        BT_Centro_Remover.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BT_Centro_RemoverActionPerformed(evt);
            }
        });

        jLabel12.setText("Sigla:");

        jLabel13.setText("Nome:");

        BT_Centro_Editar.setText("Editar");
        BT_Centro_Editar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BT_Centro_EditarActionPerformed(evt);
            }
        });

        BT_Centro_Buscar.setText("Buscar");
        BT_Centro_Buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BT_Centro_BuscarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13)
                            .addComponent(jLabel12))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(TF_Centro_Sigla, javax.swing.GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)
                            .addComponent(TF_Centro_Nome))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(BT_Centro_Cadastrar, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(BT_Centro_Buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(BT_Centro_Editar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(BT_Centro_Remover)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 959, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 599, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TF_Centro_Nome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(TF_Centro_Sigla, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BT_Centro_Editar)
                    .addComponent(BT_Centro_Remover)
                    .addComponent(BT_Centro_Cadastrar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(BT_Centro_Buscar)
                .addGap(2, 2, 2))
        );

        jTabbedPane1.addTab("Centro", jPanel2);

        TB_Aluno.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Matricula", "Nome", "Centro", "Endereço"
            }
        ));
        TB_Aluno.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TB_AlunoMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(TB_Aluno);
        if (TB_Aluno.getColumnModel().getColumnCount() > 0) {
            TB_Aluno.getColumnModel().getColumn(0).setPreferredWidth(100);
            TB_Aluno.getColumnModel().getColumn(1).setPreferredWidth(300);
            TB_Aluno.getColumnModel().getColumn(2).setPreferredWidth(40);
            TB_Aluno.getColumnModel().getColumn(3).setPreferredWidth(300);
        }

        TF_Aluno_Endereco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TF_Aluno_EnderecoActionPerformed(evt);
            }
        });

        jLabel19.setText("Endereço:");

        jLabel20.setText("Nome:");

        jLabel22.setText("Matricula:");

        Itens_Aluno_Centro.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "none" }));
        Itens_Aluno_Centro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Itens_Aluno_CentroActionPerformed(evt);
            }
        });

        jLabel21.setText("Centro:");

        BT_Aluno_Editar.setText("Editar");
        BT_Aluno_Editar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BT_Aluno_EditarActionPerformed(evt);
            }
        });

        BT_Aluno_Buscar.setText("Buscar");
        BT_Aluno_Buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BT_Aluno_BuscarActionPerformed(evt);
            }
        });

        BT_Aluno_Cadastrar.setText("Cadastrar");
        BT_Aluno_Cadastrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BT_Aluno_CadastrarActionPerformed(evt);
            }
        });

        BT_Aluno_Remover.setText("Remover");
        BT_Aluno_Remover.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BT_Aluno_RemoverActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel19)
                            .addComponent(jLabel20)
                            .addComponent(jLabel21)
                            .addComponent(jLabel22))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(TF_Aluno_Matricula)
                            .addComponent(TF_Aluno_Nome, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(TF_Aluno_Endereco, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Itens_Aluno_Centro, javax.swing.GroupLayout.Alignment.LEADING, 0, 500, Short.MAX_VALUE))
                        .addGap(18, 18, 18))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(BT_Aluno_Cadastrar, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(BT_Aluno_Buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(BT_Aluno_Editar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(BT_Aluno_Remover)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 847, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 599, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TF_Aluno_Nome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20))
                .addGap(12, 12, 12)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(TF_Aluno_Matricula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(TF_Aluno_Endereco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(Itens_Aluno_Centro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BT_Aluno_Editar)
                    .addComponent(BT_Aluno_Remover)
                    .addComponent(BT_Aluno_Cadastrar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(BT_Aluno_Buscar)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Aluno", jPanel3);

        TB_Publicacao.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Codigo", "Nome", "Ano", "Biblioteca", "Tipo"
            }
        ));
        TB_Publicacao.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TB_PublicacaoMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(TB_Publicacao);
        if (TB_Publicacao.getColumnModel().getColumnCount() > 0) {
            TB_Publicacao.getColumnModel().getColumn(0).setPreferredWidth(30);
            TB_Publicacao.getColumnModel().getColumn(1).setPreferredWidth(400);
            TB_Publicacao.getColumnModel().getColumn(2).setPreferredWidth(40);
            TB_Publicacao.getColumnModel().getColumn(3).setPreferredWidth(40);
            TB_Publicacao.getColumnModel().getColumn(4).setPreferredWidth(100);
        }

        Itens_Pub_Biblioteca.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "none" }));

        jLabel15.setText("Biblioteca:");

        jLabel16.setText("Ano:");

        jLabel18.setText("Tipo:");

        jLabel35.setText("Nome:");

        buttonGroup1.add(CheckBox_Academico);
        CheckBox_Academico.setText("Academico");
        CheckBox_Academico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CheckBox_AcademicoActionPerformed(evt);
            }
        });

        buttonGroup1.add(CheckBox_Literatura);
        CheckBox_Literatura.setText("Literatura");
        CheckBox_Literatura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CheckBox_LiteraturaActionPerformed(evt);
            }
        });

        buttonGroup1.add(CheckBox_Autoajuda);
        CheckBox_Autoajuda.setText("Autoajuda");
        CheckBox_Autoajuda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CheckBox_AutoajudaActionPerformed(evt);
            }
        });

        buttonGroup1.add(CheckBox_Outro);
        CheckBox_Outro.setText("Outro:");
        CheckBox_Outro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CheckBox_OutroActionPerformed(evt);
            }
        });

        jLabel17.setText("Area:");

        jLabel36.setText("Ediçao:");

        jLabel37.setText("Genero:");

        TF_Pub_Genero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TF_Pub_GeneroActionPerformed(evt);
            }
        });

        jLabel38.setText("Assunto:");

        BT_Publicacao_Editar.setText("Editar");
        BT_Publicacao_Editar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BT_Publicacao_EditarActionPerformed(evt);
            }
        });

        BT_Publicacao_Buscar.setText("Buscar");
        BT_Publicacao_Buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BT_Publicacao_BuscarActionPerformed(evt);
            }
        });

        BT_Publicacao_Cadastrar.setText("Cadastrar");
        BT_Publicacao_Cadastrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BT_Publicacao_CadastrarActionPerformed(evt);
            }
        });

        BT_Publicacao_Remover.setText("Remover");
        BT_Publicacao_Remover.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BT_Publicacao_RemoverActionPerformed(evt);
            }
        });

        TB_Pub_Autores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", "Autores"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane9.setViewportView(TB_Pub_Autores);
        if (TB_Pub_Autores.getColumnModel().getColumnCount() > 0) {
            TB_Pub_Autores.getColumnModel().getColumn(0).setPreferredWidth(30);
            TB_Pub_Autores.getColumnModel().getColumn(1).setPreferredWidth(500);
        }

        jLabel27.setText("Autores:");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap(14, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel16)
                                    .addComponent(jLabel35))
                                .addGap(36, 36, 36)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(TF_Pub_Nome)
                                    .addComponent(TF_Pub_Ano, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(Itens_Pub_Biblioteca, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel18))
                        .addGap(32, 32, 32))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(16, 16, 16)
                                .addComponent(BT_Publicacao_Cadastrar, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(BT_Publicacao_Buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(BT_Publicacao_Editar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(BT_Publicacao_Remover))))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel27)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addComponent(CheckBox_Autoajuda)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(CheckBox_Literatura)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel36)
                                            .addComponent(jLabel17))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(TF_Pub_Area, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(TF_Pub_Edicao, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(CheckBox_Academico)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel4Layout.createSequentialGroup()
                                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                                        .addGap(0, 0, Short.MAX_VALUE)
                                                        .addComponent(jLabel37))
                                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                                        .addGap(43, 43, 43)
                                                        .addComponent(jLabel38)))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                                            .addGroup(jPanel4Layout.createSequentialGroup()
                                                .addComponent(CheckBox_Outro)
                                                .addGap(42, 42, 42)))
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(TF_Pub_Tipo)
                                            .addComponent(TF_Pub_Assunto)
                                            .addComponent(TF_Pub_Genero, javax.swing.GroupLayout.DEFAULT_SIZE, 304, Short.MAX_VALUE))))
                                .addGap(18, 18, 18)))))
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 793, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(223, 223, 223))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 599, Short.MAX_VALUE))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel35)
                    .addComponent(TF_Pub_Nome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel16)
                    .addComponent(TF_Pub_Ano, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(Itens_Pub_Biblioteca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(CheckBox_Academico)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TF_Pub_Edicao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel36))
                .addGap(12, 12, 12)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TF_Pub_Area, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(CheckBox_Literatura)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel37)
                    .addComponent(TF_Pub_Genero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(CheckBox_Autoajuda)
                .addGap(2, 2, 2)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel38)
                    .addComponent(TF_Pub_Assunto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TF_Pub_Tipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CheckBox_Outro))
                .addGap(27, 27, 27)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel27)
                    .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BT_Publicacao_Editar)
                    .addComponent(BT_Publicacao_Remover)
                    .addComponent(BT_Publicacao_Cadastrar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(BT_Publicacao_Buscar)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Publicação", jPanel4);

        TB_Biblioteca.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Codigo", "Sigla", "Nome", "Endereço"
            }
        ));
        TB_Biblioteca.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TB_BibliotecaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(TB_Biblioteca);
        if (TB_Biblioteca.getColumnModel().getColumnCount() > 0) {
            TB_Biblioteca.getColumnModel().getColumn(1).setPreferredWidth(100);
            TB_Biblioteca.getColumnModel().getColumn(2).setPreferredWidth(600);
            TB_Biblioteca.getColumnModel().getColumn(3).setPreferredWidth(400);
        }

        jLabel1.setText("Endereço:");

        jLabel2.setText("Nome:");

        TF_Bib_Sigla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TF_Bib_SiglaActionPerformed(evt);
            }
        });

        jLabel39.setText("Sigla:");

        jLabel23.setText("Centro:");

        BT_Biblioteca_Buscar.setText("Buscar");
        BT_Biblioteca_Buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BT_Biblioteca_BuscarActionPerformed(evt);
            }
        });

        BT_Biblioteca_Cadastrar.setText("Cadastrar");
        BT_Biblioteca_Cadastrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BT_Biblioteca_CadastrarActionPerformed(evt);
            }
        });

        BT_Biblioteca_Remover.setText("Remover");
        BT_Biblioteca_Remover.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BT_Biblioteca_RemoverActionPerformed(evt);
            }
        });

        BT_Biblioteca_Editar.setText("Editar");
        BT_Biblioteca_Editar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BT_Biblioteca_EditarActionPerformed(evt);
            }
        });

        TB_Bib_Centros.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", "Centro"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane8.setViewportView(TB_Bib_Centros);
        if (TB_Bib_Centros.getColumnModel().getColumnCount() > 0) {
            TB_Bib_Centros.getColumnModel().getColumn(0).setResizable(false);
            TB_Bib_Centros.getColumnModel().getColumn(0).setPreferredWidth(20);
            TB_Bib_Centros.getColumnModel().getColumn(1).setPreferredWidth(250);
        }

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel39)
                            .addComponent(jLabel23))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(TF_Bib_Sigla, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(TF_Bib_Endereco, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(BT_Biblioteca_Cadastrar, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(BT_Biblioteca_Buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(BT_Biblioteca_Editar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(BT_Biblioteca_Remover))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(30, 30, 30)
                        .addComponent(TF_Bib_Nome, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1035, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 599, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TF_Bib_Nome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(TF_Bib_Endereco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel39)
                    .addComponent(TF_Bib_Sigla, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel23)
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BT_Biblioteca_Editar)
                    .addComponent(BT_Biblioteca_Remover)
                    .addComponent(BT_Biblioteca_Cadastrar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(BT_Biblioteca_Buscar)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Biblioteca", jPanel1);

        TB_Emprestimo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Codigo", "Data", "Hora", "Funcionário", "Publicação", "Aluno"
            }
        ));
        TB_Emprestimo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TB_EmprestimoMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(TB_Emprestimo);
        if (TB_Emprestimo.getColumnModel().getColumnCount() > 0) {
            TB_Emprestimo.getColumnModel().getColumn(0).setPreferredWidth(40);
            TB_Emprestimo.getColumnModel().getColumn(1).setPreferredWidth(80);
            TB_Emprestimo.getColumnModel().getColumn(2).setPreferredWidth(60);
            TB_Emprestimo.getColumnModel().getColumn(3).setPreferredWidth(180);
            TB_Emprestimo.getColumnModel().getColumn(4).setPreferredWidth(200);
            TB_Emprestimo.getColumnModel().getColumn(5).setPreferredWidth(200);
        }

        jLabel7.setText("Data(yyyy-mm-dd):");

        jLabel8.setText("Hora: (hh:mm:ss)");

        jLabel9.setText("Aluno");

        jLabel10.setText("Publicação:");

        jLabel11.setText("Funcionário:");

        BT_Emprestimo_Buscar.setText("Buscar");
        BT_Emprestimo_Buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BT_Emprestimo_BuscarActionPerformed(evt);
            }
        });

        BT_Emprestimo_Cadastrar.setText("Cadastrar");
        BT_Emprestimo_Cadastrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BT_Emprestimo_CadastrarActionPerformed(evt);
            }
        });

        BT_Emprestimo_Remover.setText("Remover");
        BT_Emprestimo_Remover.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BT_Emprestimo_RemoverActionPerformed(evt);
            }
        });

        BT_Emprestimo_Editar.setText("Editar");
        BT_Emprestimo_Editar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BT_Emprestimo_EditarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel9))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(Itens_Empres_Aluno, 0, 305, Short.MAX_VALUE)
                                    .addComponent(Itens_Empres_Publicacao, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel8))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(TF_Empres_Data)
                                    .addComponent(TF_Empres_Hora)))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(Itens_Empres_Funcionario, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(18, 18, 18))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(BT_Emprestimo_Cadastrar, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(BT_Emprestimo_Buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(BT_Emprestimo_Editar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(BT_Emprestimo_Remover)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 102, Short.MAX_VALUE)))
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 787, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 599, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(TF_Empres_Data, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TF_Empres_Hora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Itens_Empres_Funcionario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(Itens_Empres_Publicacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(Itens_Empres_Aluno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BT_Emprestimo_Editar)
                    .addComponent(BT_Emprestimo_Remover)
                    .addComponent(BT_Emprestimo_Cadastrar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(BT_Emprestimo_Buscar)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Emprestimo", jPanel5);

        TB_Funcionario.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Codigo", "Nome", "Salário"
            }
        ));
        TB_Funcionario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TB_FuncionarioMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(TB_Funcionario);
        if (TB_Funcionario.getColumnModel().getColumnCount() > 0) {
            TB_Funcionario.getColumnModel().getColumn(0).setPreferredWidth(80);
            TB_Funcionario.getColumnModel().getColumn(1).setPreferredWidth(800);
            TB_Funcionario.getColumnModel().getColumn(2).setPreferredWidth(200);
        }

        jLabel5.setText("Salário:");

        jLabel6.setText("Nome:");

        BT_Funcionario_Buscar.setText("Buscar");
        BT_Funcionario_Buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BT_Funcionario_BuscarActionPerformed(evt);
            }
        });

        BT_Funcionario_Cadastrar.setText("Cadastrar");
        BT_Funcionario_Cadastrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BT_Funcionario_CadastrarActionPerformed(evt);
            }
        });

        BT_Funcionario_Remover.setText("Remover");
        BT_Funcionario_Remover.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BT_Funcionario_RemoverActionPerformed(evt);
            }
        });

        BT_Funcionario_Editar.setText("Editar");
        BT_Funcionario_Editar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BT_Funcionario_EditarActionPerformed(evt);
            }
        });

        jLabel14.setText("Salário Máximo:  R$");

        jLabel24.setText("Salário Mínimo:   R$");

        jLabel25.setText("Média Salarial:    R$");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(TF_Funci_Nome, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(TF_Funci_Salario))))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(BT_Funcionario_Cadastrar, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(BT_Funcionario_Buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(BT_Funcionario_Editar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(BT_Funcionario_Remover))))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel6Layout.createSequentialGroup()
                                    .addComponent(jLabel24)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(Funcionario_Sal_Min, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel6Layout.createSequentialGroup()
                                    .addComponent(jLabel14)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(Funcionario_Sal_Max, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel25)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Funcionario_Sal_Med, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 1053, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(TF_Funci_Nome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(TF_Funci_Salario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(Funcionario_Sal_Max, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addComponent(jLabel24))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(Funcionario_Sal_Min, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel25)
                    .addComponent(Funcionario_Sal_Med, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(43, 43, 43)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BT_Funcionario_Editar)
                    .addComponent(BT_Funcionario_Remover)
                    .addComponent(BT_Funcionario_Cadastrar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(BT_Funcionario_Buscar)
                .addContainerGap())
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 599, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Funcionário", jPanel6);

        TB_Autor.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Codigo", "Nome", "País"
            }
        ));
        TB_Autor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TB_AutorMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(TB_Autor);
        if (TB_Autor.getColumnModel().getColumnCount() > 0) {
            TB_Autor.getColumnModel().getColumn(0).setPreferredWidth(80);
            TB_Autor.getColumnModel().getColumn(1).setPreferredWidth(600);
            TB_Autor.getColumnModel().getColumn(2).setPreferredWidth(200);
        }

        jLabel3.setText("País:");

        jLabel4.setText("Nome:");

        BT_Autor_Buscar.setText("Buscar");
        BT_Autor_Buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BT_Autor_BuscarActionPerformed(evt);
            }
        });

        BT_Autor_Cadastrar.setText("Cadastrar");
        BT_Autor_Cadastrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BT_Autor_CadastrarActionPerformed(evt);
            }
        });

        BT_Autor_Remover.setText("Remover");
        BT_Autor_Remover.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BT_Autor_RemoverActionPerformed(evt);
            }
        });

        BT_Autor_Editar.setText("Editar");
        BT_Autor_Editar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BT_Autor_EditarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(20, 20, 20)
                                .addComponent(TF_Autor_Pais))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(TF_Autor_Nome, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(27, 27, 27))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(BT_Autor_Cadastrar, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(BT_Autor_Buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(BT_Autor_Editar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(BT_Autor_Remover)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1013, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TF_Autor_Nome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(TF_Autor_Pais, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BT_Autor_Editar)
                    .addComponent(BT_Autor_Remover)
                    .addComponent(BT_Autor_Cadastrar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(BT_Autor_Buscar)
                .addContainerGap())
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 599, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Autor", jPanel7);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1216, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BT_Centro_CadastrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BT_Centro_CadastrarActionPerformed

        String nome = TF_Centro_Nome.getText();  // Assumindo que o TF_Centro_Nome é o campo para o Nome
        String sigla = TF_Centro_Sigla.getText(); // Assumindo que o TF_Centro_Sigla é o campo para a Sigla
    
        try{
            if(nome.isEmpty() || sigla.isEmpty()){
                throw new CamposNaoInformados();
            }
            else if (sigla.length()>10){
                throw new CentroSiglaGrande();
            }
            else if (nome.length()>50){
                throw new NomeGrande();
            }
            else if(DAO_Centro.TestaSigla(TF_Centro_Sigla.getText()) == true){
                throw new CentroSiglaUsada();
            }
            
            
            new DAO_Centro().CadastrarCentro(new Centro(nome, sigla));
       
        
            int id = new DAO_General().ReturnLastCod("Centro");
        
            //Centro temp = new Centro();
            //temp = new DAO_Centro().BuscarCentro(id); 
        
            DefaultTableModel Table_Centro = (DefaultTableModel)TB_Centro.getModel();
            Object[] data = {id, sigla, nome};
            Table_Centro.addRow(data);
            
            Itens_Aluno_Centro.addItem(sigla);

            
            TF_Centro_Nome.setText("");
            TF_Centro_Sigla.setText("");
            
            JOptionPane.showMessageDialog(null, "Centro cadastrado com sucesso!", 
                                              "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        }
        catch(CentroSiglaGrande e){
            JOptionPane.showMessageDialog(null, "A sigla pode ter no máximo 10 caracteres!", 
                                                  "Erro", JOptionPane.ERROR_MESSAGE);
        }
        catch(NomeGrande e){
            JOptionPane.showMessageDialog(null, "O nome pode ter no máximo 50 caracteres!", 
                                                  "Erro", JOptionPane.ERROR_MESSAGE);
        }
        catch(CamposNaoInformados e){
            JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos!", 
                                                  "Erro", JOptionPane.ERROR_MESSAGE);
        }
        catch(CentroSiglaUsada e){
            JOptionPane.showMessageDialog(null, "A sigla informada já está sendo usada!", 
                                                  "Erro", JOptionPane.ERROR_MESSAGE);
        }
        
            
              
    }//GEN-LAST:event_BT_Centro_CadastrarActionPerformed

    private void TF_Aluno_EnderecoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TF_Aluno_EnderecoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TF_Aluno_EnderecoActionPerformed

    private void CheckBox_AcademicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CheckBox_AcademicoActionPerformed
        TF_Pub_Edicao.setEnabled(true);
        TF_Pub_Area.setEnabled(true);
        TF_Pub_Genero.setEnabled(false);
        TF_Pub_Genero.setText("");
        TF_Pub_Assunto.setEnabled(false);
        TF_Pub_Assunto.setText("");
        TF_Pub_Tipo.setEnabled(false);
        TF_Pub_Tipo.setText("");

    }//GEN-LAST:event_CheckBox_AcademicoActionPerformed

    private void CheckBox_LiteraturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CheckBox_LiteraturaActionPerformed
        TF_Pub_Edicao.setEnabled(false);
        TF_Pub_Edicao.setText("");
        TF_Pub_Area.setEnabled(false);
        TF_Pub_Area.setText("");
        TF_Pub_Genero.setEnabled(true);
        TF_Pub_Assunto.setEnabled(false);
        TF_Pub_Assunto.setText("");
        TF_Pub_Tipo.setEnabled(false);
        TF_Pub_Tipo.setText("");
    }//GEN-LAST:event_CheckBox_LiteraturaActionPerformed

    private void CheckBox_AutoajudaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CheckBox_AutoajudaActionPerformed
        TF_Pub_Edicao.setEnabled(false);
        TF_Pub_Edicao.setText("");
        TF_Pub_Area.setEnabled(false);
        TF_Pub_Area.setText("");
        TF_Pub_Genero.setEnabled(false);
        TF_Pub_Genero.setText("");
        TF_Pub_Assunto.setEnabled(true);
        TF_Pub_Tipo.setEnabled(false);
        TF_Pub_Tipo.setText("");
    }//GEN-LAST:event_CheckBox_AutoajudaActionPerformed

    private void CheckBox_OutroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CheckBox_OutroActionPerformed
        TF_Pub_Edicao.setEnabled(false);
        TF_Pub_Edicao.setText("");
        TF_Pub_Area.setEnabled(false);
        TF_Pub_Area.setText("");
        TF_Pub_Genero.setEnabled(false);
        TF_Pub_Genero.setText("");
        TF_Pub_Assunto.setEnabled(false);
        TF_Pub_Assunto.setText("");
        TF_Pub_Tipo.setEnabled(true);
    }//GEN-LAST:event_CheckBox_OutroActionPerformed

    private void TF_Pub_GeneroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TF_Pub_GeneroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TF_Pub_GeneroActionPerformed

    private void TF_Bib_SiglaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TF_Bib_SiglaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TF_Bib_SiglaActionPerformed

    private void BT_Centro_RemoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BT_Centro_RemoverActionPerformed
       
        //Testa se tem alguma linha selecionada
        if(TB_Centro.getSelectedRow() != -1){
        
            //Pega o codigo da linha selecionada
            int id = (int) TB_Centro.getValueAt(TB_Centro.getSelectedRow(), 0);
            
            DefaultTableModel Table_Centro = (DefaultTableModel)TB_Centro.getModel();
            Table_Centro.removeRow(TB_Centro.getSelectedRow());
           
            new DAO_Centro().RemoverCentro(id);
            
            String sigla = TF_Centro_Sigla.getText();
            TF_Centro_Nome.setText("");
            TF_Centro_Sigla.setText("");
            Itens_Aluno_Centro.removeItem(sigla);

            JOptionPane.showMessageDialog(null, "Centro removido com sucesso.", 
                                              "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        }    
        else
            JOptionPane.showMessageDialog(null, "Por favor, selecione uma linha.", 
                                              "Erro", JOptionPane.ERROR_MESSAGE);
    }//GEN-LAST:event_BT_Centro_RemoverActionPerformed

    private void TB_CentroMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TB_CentroMouseClicked
        TF_Centro_Nome.setText((String) TB_Centro.getValueAt(TB_Centro.getSelectedRow(), 2));
        TF_Centro_Sigla.setText((String) TB_Centro.getValueAt(TB_Centro.getSelectedRow(), 1));
    }//GEN-LAST:event_TB_CentroMouseClicked

    private void BT_Centro_BuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BT_Centro_BuscarActionPerformed
        
        String nome = TF_Centro_Nome.getText();
        String sigla = TF_Centro_Sigla.getText();
        int caso = 0;
        
        

            if(!nome.isEmpty() && sigla.isEmpty())
                caso = 1;
            else if(nome.isEmpty() && !sigla.isEmpty())
                caso = 2;
            else if(!nome.isEmpty() && !sigla.isEmpty())
                caso = 3;
        
            switch(caso){
                case 1:
                    carregarCentrosNome(nome);
                    break;
                case 2:
                    carregarCentrosSigla(sigla);
                    break;
                case 3:
                    carregarCentrosNomeSigla(nome, sigla);
                    break;
                default:
                    carregarCentros();           
            }

        
            
        
        
        
    }//GEN-LAST:event_BT_Centro_BuscarActionPerformed

    private void BT_Centro_EditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BT_Centro_EditarActionPerformed

        
        try{
            if(TB_Centro.getSelectedRow()==-1){
                throw new LinhaNaoSelecionada();
            }
            else{
                String nomeNew = TF_Centro_Nome.getText();
                String siglaNew = TF_Centro_Sigla.getText();
                String nomeOld = (String) TB_Centro.getValueAt(TB_Centro.getSelectedRow(),2);
                String siglaOld = (String) TB_Centro.getValueAt(TB_Centro.getSelectedRow(),1);
                int codigo = (int) TB_Centro.getValueAt(TB_Centro.getSelectedRow(),0);
                
                //Testa se tem alguma informação nos campos
                if(nomeNew.isEmpty() && siglaNew.isEmpty()){
                    throw new CamposNaoInformados();
                }
            

            
                //testa se as informacoes fornecidas sao diferentes das ja cadastrados
                if(nomeNew.equals(nomeOld) && siglaNew.equals(siglaOld)){
                    throw new CentroEdicaoIgual();       
                }
                else{
          
                    //testa se o nome fornecido é diferente do já cadastrado
                    if(!nomeNew.equals(nomeOld)){
                        new DAO_Centro().EditarNome(codigo, nomeNew); 
                        TB_Centro.setValueAt(nomeNew, TB_Centro.getSelectedRow(), 2);
                    }
                
                    //testa se a sigla fornecida é diferente da já cadastrada
                    if(!siglaNew.equals(siglaOld)){

                        //testa se a sigla fornecida e diferente de alguma ja cadastrada
                        if(DAO_Centro.TestaSigla(siglaNew)==false){
                            new DAO_Centro().EditarSigla(codigo, siglaNew); 
                            TB_Centro.setValueAt(siglaNew, TB_Centro.getSelectedRow(), 1);
                        }
                        else{
                        throw new CentroSiglaUsada();
                        }
                    }
                
                    JOptionPane.showMessageDialog(null, "Edição realizada com sucesso.", 
                                              "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    TF_Centro_Nome.setText("");
                    TF_Centro_Sigla.setText("");

                }
            
            }
        }
        catch(CamposNaoInformados e){
            JOptionPane.showMessageDialog(null, "Por favor, informe algum campo para editar.", 
                                              "Erro", JOptionPane.ERROR_MESSAGE);
        }
        catch(CentroSiglaUsada e){
            JOptionPane.showMessageDialog(null, "A sigla informada já está sendo usada!", 
                                                  "Erro", JOptionPane.ERROR_MESSAGE);
        }
        catch(CentroEdicaoIgual e){
            JOptionPane.showMessageDialog(null, "Por favor, altere algum campo para realizar a edição!", 
                                                  "Erro", JOptionPane.ERROR_MESSAGE);
        }
        catch(LinhaNaoSelecionada e){
            JOptionPane.showMessageDialog(null, "Por favor, selecione alguma linha para realizar a edição!", 
                                                  "Erro", JOptionPane.ERROR_MESSAGE);
        }
        
        
        
        
    }//GEN-LAST:event_BT_Centro_EditarActionPerformed

    private void BT_Aluno_EditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BT_Aluno_EditarActionPerformed
        
        
        try{
            if(TB_Aluno.getSelectedRow()==-1){
                throw new LinhaNaoSelecionada();
            }
            else{
                int linha = TB_Aluno.getSelectedRow();
                String nomeNew = TF_Aluno_Nome.getText();
                String matriculaNew = TF_Aluno_Matricula.getText();
                String enderecoNew = TF_Aluno_Endereco.getText();
                String centroNew = (String) Itens_Aluno_Centro.getSelectedItem();
                
                String matriculaOld = String.valueOf( TB_Aluno.getValueAt(linha,0));
                String nomeOld = (String) TB_Aluno.getValueAt(linha,1);
                String centroOld = (String) TB_Aluno.getValueAt(linha,2);
                String enderecoOld = (String) TB_Aluno.getValueAt(linha,3);
                
                
                //Testa se tem alguma informação nos campos
                if(nomeNew.isEmpty() && matriculaNew.isEmpty() && enderecoNew.isEmpty()){
                    throw new CamposNaoInformados();
                }
            

            
                //testa se as informacoes fornecidas sao diferentes das ja cadastrados
                if(nomeNew.equals(nomeOld) && matriculaNew.equals(matriculaOld) && enderecoNew.equals(enderecoOld) && centroNew.equals(centroOld)){
                    throw new CentroEdicaoIgual();       
                }
                else{
          
                    //testa se o nome fornecido é diferente do já cadastrado
                    if(!nomeNew.equals(nomeOld)){
                        new DAO_Aluno().EditarNome(Integer.parseInt(matriculaOld), nomeNew); 
                        TB_Aluno.setValueAt(nomeNew, linha, 1);
                    }
                

                    if(!enderecoNew.equals(enderecoOld)){
                        new DAO_Aluno().EditarEndereco(Integer.parseInt(matriculaOld), enderecoNew); 
                        TB_Aluno.setValueAt(enderecoNew, linha, 3);
                    }
                    
                    if(!centroNew.equals(centroOld)){
                        new DAO_Aluno().EditarCentro(Integer.parseInt(matriculaOld), DAO_Aluno.PullCentro(centroNew)); 
                        TB_Aluno.setValueAt(centroNew, linha, 2);
                    }
                            
                            
                    //testa se a matricula fornecida é diferente da já cadastrada
                    if(!matriculaNew.equals(matriculaOld)){

                        //testa se a sigla fornecida e diferente de alguma ja cadastrada
                        if(DAO_Aluno.TestaMatricula(matriculaNew)==false){
                            new DAO_Aluno().EditarMatricula(Integer.parseInt(matriculaNew), Integer.parseInt(matriculaOld)); 
                            TB_Aluno.setValueAt(matriculaNew, linha, 0);
                            matriculaOld=matriculaNew;
                        }
                        else{
                        throw new CentroSiglaUsada();
                        }
                    }
                
                    JOptionPane.showMessageDialog(null, "Edição realizada com sucesso.", 
                                              "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    TF_Aluno_Nome.setText("");
                    TF_Aluno_Endereco.setText("");
                    TF_Aluno_Matricula.setText("");
                    Itens_Aluno_Centro.setSelectedIndex(0);

                }
            
            }
        }
        catch(CamposNaoInformados e){
            JOptionPane.showMessageDialog(null, "Por favor, informe algum campo para editar.", 
                                              "Erro", JOptionPane.ERROR_MESSAGE);
        }
        catch(CentroSiglaUsada e){
            JOptionPane.showMessageDialog(null, "A sigla informada já está sendo usada!", 
                                                  "Erro", JOptionPane.ERROR_MESSAGE);
        }
        catch(CentroEdicaoIgual e){
            JOptionPane.showMessageDialog(null, "Por favor, altere algum campo para realizar a edição!", 
                                                  "Erro", JOptionPane.ERROR_MESSAGE);
        }
        catch(LinhaNaoSelecionada e){
            JOptionPane.showMessageDialog(null, "Por favor, selecione alguma linha para realizar a edição!", 
                                                  "Erro", JOptionPane.ERROR_MESSAGE);
        }
        
        
        
    }//GEN-LAST:event_BT_Aluno_EditarActionPerformed

    private void BT_Aluno_BuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BT_Aluno_BuscarActionPerformed
        
        String nome = TF_Aluno_Nome.getText();
        String matricula = TF_Aluno_Matricula.getText();
        String endereco = TF_Aluno_Endereco.getText();
        String centro = (String) Itens_Aluno_Centro.getSelectedItem();
      
        int caso = 0;
                   
            if(nome.isEmpty() && matricula.isEmpty() && endereco.isEmpty() && !centro.equals("none"))
                caso = 1;
            else if(nome.isEmpty() && matricula.isEmpty() && !endereco.isEmpty() && centro.equals("none"))
                caso = 2;
            else if(nome.isEmpty() && matricula.isEmpty() && !endereco.isEmpty() && !centro.equals("none"))
                caso = 3;
            else if(nome.isEmpty() && !matricula.isEmpty() && endereco.isEmpty() && centro.equals("none"))
                caso = 4;
            else if(nome.isEmpty() && !matricula.isEmpty() && endereco.isEmpty() && !centro.equals("none"))
                caso = 5;
            else if(nome.isEmpty() && !matricula.isEmpty() && !endereco.isEmpty() && centro.equals("none"))
                caso = 6;
            else if(nome.isEmpty() && !matricula.isEmpty() && !endereco.isEmpty() && !centro.equals("none"))
                caso = 7;
            else if(!nome.isEmpty() && matricula.isEmpty() && endereco.isEmpty() && centro.equals("none"))
                caso = 8;
            else if(!nome.isEmpty() && matricula.isEmpty() && endereco.isEmpty() && !centro.equals("none"))
                caso = 9;
            else if(!nome.isEmpty() && matricula.isEmpty() && !endereco.isEmpty() && centro.equals("none"))
                caso = 10;
            else if(!nome.isEmpty() && matricula.isEmpty() && !endereco.isEmpty() && !centro.equals("none"))
                caso = 11;
            else if(!nome.isEmpty() && !matricula.isEmpty() && endereco.isEmpty() && centro.equals("none"))
                caso = 12;
            else if(!nome.isEmpty() && !matricula.isEmpty() && endereco.isEmpty() && !centro.equals("none"))
                caso = 13;
            else if(!nome.isEmpty() && !matricula.isEmpty() && !endereco.isEmpty() && centro.equals("none"))
                caso = 14;
            else if(!nome.isEmpty() && !matricula.isEmpty() && !endereco.isEmpty() && !centro.equals("none"))
                caso = 15;
            
            
            
            
            
            else if(!nome.isEmpty() && matricula.isEmpty() && endereco.isEmpty() && !centro.equals("none"))
                caso = 7;

        
            
            int fk = DAO_Aluno.PullCentro(centro);
            switch(caso){
                 case 1:
                    carregarAlunosUm(fk, "SELECT Aluno.matricula, Aluno.nome, Centro.sigla, Aluno.endereco FROM Aluno JOIN Centro ON Aluno.fk_cod_centro = Centro.cod_centro WHERE Aluno.fk_cod_centro=?");
                    break;
                case 2:
                    carregarAlunosUm(endereco, "SELECT Aluno.matricula, Aluno.nome, Centro.sigla, Aluno.endereco FROM Aluno JOIN Centro ON Aluno.fk_cod_centro = Centro.cod_centro WHERE Aluno.endereco=?");
                    break;
                case 3:
                    carregarAlunosDois(endereco, fk, "SELECT Aluno.matricula, Aluno.nome, Centro.sigla, Aluno.endereco FROM Aluno JOIN Centro ON Aluno.fk_cod_centro = Centro.cod_centro WHERE endereco=? AND Aluno.fk_cod_centro=?");
                    break;
                case 4:
                    carregarAlunosUm(matricula, "SELECT Aluno.matricula, Aluno.nome, Centro.sigla, Aluno.endereco FROM Aluno JOIN Centro ON Aluno.fk_cod_centro = Centro.cod_centro WHERE Matricula=?");
                    break;
                case 5:
                    carregarAlunosDois(matricula, fk, "SELECT Aluno.matricula, Aluno.nome, Centro.sigla, Aluno.endereco FROM Aluno JOIN Centro ON Aluno.fk_cod_centro = Centro.cod_centro WHERE matricula=? AND Aluno.fk_cod_centro=?");
                    break;
                case 6:
                    carregarAlunosDois(matricula, endereco, "SELECT Aluno.matricula, Aluno.nome, Centro.sigla, Aluno.endereco FROM Aluno JOIN Centro ON Aluno.fk_cod_centro = Centro.cod_centro WHERE matricula=? AND endereco=?");
                    break;
                case 7:
                    carregarAlunosTres(matricula, endereco, fk, "SELECT Aluno.matricula, Aluno.nome, Centro.sigla, Aluno.endereco FROM Aluno JOIN Centro ON Aluno.fk_cod_centro = Centro.cod_centro WHERE matricula=? AND endereco=? AND Aluno.fk_Cod_Centro=?");
                    break;
                case 8:
                    carregarAlunosUm(nome, "SELECT Aluno.matricula, Aluno.nome, Centro.sigla, Aluno.endereco FROM Aluno JOIN Centro ON Aluno.fk_cod_centro = Centro.cod_centro WHERE Aluno.nome=?");
                    break;
                case 9:
                    carregarAlunosDois(nome, fk, "SELECT Aluno.matricula, Aluno.nome, Centro.sigla, Aluno.endereco FROM Aluno JOIN Centro ON Aluno.fk_cod_centro = Centro.cod_centro WHERE Aluno.nome=? AND Aluno.fk_cod_centro=?");
                    break;
                case 10:
                    carregarAlunosDois(nome, endereco, "SELECT Aluno.matricula, Aluno.nome, Centro.sigla, Aluno.endereco FROM Aluno JOIN Centro ON Aluno.fk_cod_centro = Centro.cod_centro WHERE Aluno.nome=? AND endereco=?");
                    break;
                case 11:
                    carregarAlunosTres(nome, endereco, fk, "SELECT Aluno.matricula, Aluno.nome, Centro.sigla, Aluno.endereco FROM Aluno JOIN Centro ON Aluno.fk_cod_centro = Centro.cod_centro WHERE Aluno.nome=? AND endereco=? AND Aluno.fk_Cod_Centro=?");
                    break;
                case 12:
                    carregarAlunosDois(nome, matricula, "SELECT Aluno.matricula, Aluno.nome, Centro.sigla, Aluno.endereco FROM Aluno JOIN Centro ON Aluno.fk_cod_centro = Centro.cod_centro WHERE Aluno.nome=? AND matricula=?");
                    break;
                case 13:
                    carregarAlunosTres(nome, matricula, fk, "SELECT Aluno.matricula, Aluno.nome, Centro.sigla, Aluno.endereco FROM Aluno JOIN Centro ON Aluno.fk_cod_centro = Centro.cod_centro WHERE Aluno.nome=? AND matricula=? AND Aluno.fk_Cod_Centro=?");
                    break;
                case 14:
                    carregarAlunosTres(nome, matricula, endereco, "SELECT Aluno.matricula, Aluno.nome, Centro.sigla, Aluno.endereco FROM Aluno JOIN Centro ON Aluno.fk_cod_centro = Centro.cod_centro WHERE Aluno.nome=? AND matricula=? AND endereco=?");
                    break;
                case 15:
                    carregarAlunosQuatro(nome, matricula, endereco, fk, "SELECT Aluno.matricula, Aluno.nome, Centro.sigla, Aluno.endereco FROM Aluno JOIN Centro ON Aluno.fk_cod_centro = Centro.cod_centro WHERE Aluno.nome=? AND matricula=? AND endereco=? AND Aluno.fk_cod_centro=?");
                    break;    
                default:
                    carregarAlunos();           
            }

        
        
        
        
    }//GEN-LAST:event_BT_Aluno_BuscarActionPerformed

    private void BT_Aluno_CadastrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BT_Aluno_CadastrarActionPerformed
         
        String nome = TF_Aluno_Nome.getText(); 
        String endereco = TF_Aluno_Endereco.getText(); 
        String centro = (String)Itens_Aluno_Centro.getSelectedItem();
        String matricula = TF_Aluno_Matricula.getText();
    
        try{
            if(nome.isEmpty() || endereco.isEmpty() || matricula.isEmpty() || centro.equals("none")){
                throw new CamposNaoInformados();
            }
            else if (nome.length()>50){
                throw new CentroSiglaGrande();
            }
            else if (endereco.length()>100){
                throw new NomeGrande();
            }
            else if(DAO_Aluno.TestaMatricula(TF_Centro_Sigla.getText()) == true){
                throw new CentroSiglaUsada();
            }
            
            new DAO_Aluno().CadastrarAluno(new Aluno(Integer.parseInt(matricula), endereco, nome, DAO_Aluno.PullCentro(centro)));
          
            DefaultTableModel Table_Aluno = (DefaultTableModel)TB_Aluno.getModel();
            Object[] data = {matricula, nome, centro, endereco};
            Table_Aluno.addRow(data);
            
            Itens_Empres_Aluno.addItem(nome);
            
            TF_Aluno_Nome.setText("");
            TF_Aluno_Matricula.setText("");
            TF_Aluno_Endereco.setText("");
            
            JOptionPane.showMessageDialog(null, "Aluno cadastrado com sucesso!", 
                                              "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        }
        catch(CentroSiglaGrande e){
            JOptionPane.showMessageDialog(null, "O nome pode ter no máximo 50 caracteres!", 
                                                  "Erro", JOptionPane.ERROR_MESSAGE);
        }
        catch(NomeGrande e){
            JOptionPane.showMessageDialog(null, "O endereco pode ter no máximo 100 caracteres!", 
                                                  "Erro", JOptionPane.ERROR_MESSAGE);
        }
        catch(CamposNaoInformados e){
            JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos!", 
                                                  "Erro", JOptionPane.ERROR_MESSAGE);
        }
        catch(CentroSiglaUsada e){
            JOptionPane.showMessageDialog(null, "A matricula informada já está sendo usada!", 
                                                  "Erro", JOptionPane.ERROR_MESSAGE);
        }
        
        
        
    }//GEN-LAST:event_BT_Aluno_CadastrarActionPerformed

    private void BT_Aluno_RemoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BT_Aluno_RemoverActionPerformed
        
        //Testa se tem alguma linha selecionada
        if(TB_Aluno.getSelectedRow() != -1){
        
            //Pega o codigo da linha selecionada
            int id = (int) TB_Aluno.getValueAt(TB_Aluno.getSelectedRow(), 0);
            
            DefaultTableModel Table_Aluno = (DefaultTableModel)TB_Aluno.getModel();
            Table_Aluno.removeRow(TB_Aluno.getSelectedRow());
           
            new DAO_Aluno().RemoverAluno(id);
            
            String nome = TF_Aluno_Nome.getText();
            TF_Aluno_Nome.setText("");
            TF_Aluno_Matricula.setText("");
            TF_Aluno_Endereco.setText("");
            Itens_Aluno_Centro.setSelectedIndex(0);
            Itens_Empres_Aluno.removeItem(nome);
            JOptionPane.showMessageDialog(null, "Aluno removido com sucesso.", 
                                              "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        }    
        else
            JOptionPane.showMessageDialog(null, "Por favor, selecione uma linha.", 
                                              "Erro", JOptionPane.ERROR_MESSAGE);
        
        
    }//GEN-LAST:event_BT_Aluno_RemoverActionPerformed

    private void BT_Publicacao_EditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BT_Publicacao_EditarActionPerformed
        
        
        try{
            if(TB_Publicacao.getSelectedRow()==-1){
                throw new LinhaNaoSelecionada();
            }
            else{
                int linha = TB_Publicacao.getSelectedRow();
                String nomeNew = TF_Pub_Nome.getText();
                String anoNew = TF_Pub_Ano.getText();  
                String bibliotecaNew = (String) Itens_Pub_Biblioteca.getSelectedItem();
                String tipoNew = null;
                String edicaoNew = null;
                String areaNew = null;
                String generoNew = null;
                String assuntoNew = null;
                
                int id = Integer.parseInt(TB_Publicacao.getValueAt(linha, 0).toString());
                
                if(CheckBox_Academico.isSelected()){
                    tipoNew = "Acadêmico";
                    edicaoNew = TF_Pub_Edicao.getText();
                    areaNew = TF_Pub_Area.getText();
                }
                else if (CheckBox_Literatura.isSelected()){
                    tipoNew = "Literatura";
                    generoNew = TF_Pub_Genero.getText();
                }
                else if (CheckBox_Autoajuda.isSelected()){
                    tipoNew = "Autoajuda";
                    assuntoNew = TF_Pub_Assunto.getText();
                }
                else if (CheckBox_Outro.isSelected()) tipoNew = TF_Pub_Tipo.getText();
                
                String nomeOld = (String) TB_Publicacao.getValueAt(linha,1);
                String anoOld = String.valueOf( TB_Publicacao.getValueAt(linha,2));
                String bibliotecaOld = (String) TB_Publicacao.getValueAt(linha,3);
                String tipoOld = (String) TB_Publicacao.getValueAt(linha,4);
                String areaOld = null;
                String edicaoOld = null;
                String generoOld = null;
                String assuntoOld = null;
                
                if(tipoOld.equals("Acadêmico")) {
                    Academico temp = new DAO_Publicacao().BuscarAcademico(id);
                    areaOld = temp.getArea();
                    edicaoOld = String.valueOf(temp.getEdicao());
                }
                else if(tipoOld.equals("Literatura")) {
                    String temp = new DAO_Publicacao().BuscarLiteratura(id);
                    generoOld = temp;
                }
                else if(tipoOld.equals("Autoajuda")) {
                    String temp = new DAO_Publicacao().BuscarAutoajuda(id);
                    assuntoOld = temp;
                }
                
                
                //Testa se tem alguma informação nos campos
                if(nomeNew.isEmpty() || anoNew.isEmpty() || bibliotecaNew.equals("none") || 
                (CheckBox_Academico.isSelected() && (areaNew.isEmpty()||edicaoNew.isEmpty())) || 
                (CheckBox_Literatura.isSelected()&&generoNew.isEmpty()) || 
                (CheckBox_Autoajuda.isSelected()&&assuntoNew.isEmpty()) || 
                (CheckBox_Outro.isSelected() && tipoNew.isEmpty())){
                throw new CamposNaoInformados();
                }
                else if (nomeNew.length()>50){
                    throw new CentroSiglaGrande();
                }
                else if (tipoNew.length()>50){
                    throw new NomeGrande();
                }
                else if (anoNew.length()>4){
                    throw new PubAnoGrande();
                }
                
                
                //boolean teste1 = tipoNew.equals(tipoOld);
                boolean teste2 = tipoNew.equals(tipoOld)&&tipoNew.equals("Acadêmico")&&edicaoNew.equals(edicaoOld);
                boolean teste3 = tipoNew.equals(tipoOld)&&tipoNew.equals("Acadêmico")&&areaNew.equals(areaOld);
                boolean teste4 = tipoNew.equals(tipoOld)&&tipoNew.equals("Literatura")&&generoNew.equals(generoOld);
                boolean teste5 = tipoNew.equals(tipoOld)&&tipoNew.equals("Autoajuda")&&assuntoNew.equals(assuntoOld);
                boolean teste =  (teste2 && teste3) || teste4 || teste5;
                  
                //testa se as informacoes fornecidas sao diferentes das ja cadastrados
                
                if(nomeNew.equals(nomeOld) && anoNew.equals(anoOld) && bibliotecaNew.equals(bibliotecaOld) && teste){
                    throw new CentroEdicaoIgual();       
                }
                
                else{
          
                    //testa se o nome fornecido é diferente do já cadastrado
                    if(!nomeNew.equals(nomeOld)){
                        new DAO_Publicacao().EditarNome(id, nomeNew); 
                        TB_Publicacao.setValueAt(nomeNew, linha, 1);
                    }
                    
                    if(!anoNew.equals(anoOld)){
                        new DAO_Publicacao().EditarAno(id, Integer.parseInt(anoNew)); 
                        TB_Publicacao.setValueAt(anoNew, linha, 2);
                    }
                    
                    if(!bibliotecaNew.equals(bibliotecaOld)){
                        new DAO_Publicacao().EditarBiblioteca(id, DAO_Publicacao.PullBiblioteca(bibliotecaNew)); 
                        TB_Publicacao.setValueAt(bibliotecaNew, linha, 3);
                    }
                
                    if (tipoNew.equals(tipoOld)){
                        
                        if(tipoOld.equals("Acadêmico")){
                            if(!edicaoNew.equals(edicaoOld)){
                                new DAO_Publicacao().EditarEdicao(id, Integer.parseInt(edicaoNew)); 
                            }
                    
                            if(!areaNew.equals(areaOld)){
                                new DAO_Publicacao().EditarArea(id, areaNew); 
                            }
                        }
                        
                        else if(tipoOld.equals("Literatura")){
                            if(!generoNew.equals(generoOld)){
                                new DAO_Publicacao().EditarGenero(id, generoNew); 
                            }
                        }    
                        
                        else if(tipoOld.equals("Autoajuda")){
                            if(!assuntoNew.equals(assuntoOld)){
                                new DAO_Publicacao().EditarAssunto(id, assuntoNew); 
                            }
                        }    
                    }
                    
                    if (!tipoNew.equals(tipoOld)){
                        
                        if(tipoOld.equals("Acadêmico")){
                            new DAO_Publicacao().RemoverAcademico(id);
                        }
                        
                        if(tipoOld.equals("Literatura")){
                            new DAO_Publicacao().RemoverLiteratura(id);
                        }
                        
                        if(tipoOld.equals("Autoajuda")){
                            new DAO_Publicacao().RemoverAutoajuda(id);
                        }
    
                        if(tipoNew.equals("Acadêmico")){
                            new DAO_Publicacao().CadastrarAcademico(new Academico(Integer.parseInt(edicaoNew), areaNew, id));
                        }
                        if(tipoNew.equals("Literatura")){
                            new DAO_Publicacao().CadastrarLiteratura(new Literatura(generoNew, id));
                        }
                         if(tipoNew.equals("Autoajuda")){
                            new DAO_Publicacao().CadastrarAutoajuda(new Autoajuda(assuntoNew, id));
                        }
                        new DAO_Publicacao().EditarTipo(id, tipoNew);
                        TB_Publicacao.setValueAt(tipoNew, linha, 4);
                        
                    }
                        
                    JOptionPane.showMessageDialog(null, "Edição realizada com sucesso.", 
                                              "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    TF_Aluno_Nome.setText("");
                    TF_Aluno_Endereco.setText("");
                    TF_Aluno_Matricula.setText("");
                    Itens_Aluno_Centro.setSelectedIndex(0);

                }
            
            }
        }
        catch(CamposNaoInformados e){
            JOptionPane.showMessageDialog(null, "Por favor, informe todos campo para editar.", 
                                              "Erro", JOptionPane.ERROR_MESSAGE);
        }
        catch(CentroSiglaGrande e){
            JOptionPane.showMessageDialog(null, "A sigla pode ter no máximo 50 caracteres!", 
                                                  "Erro", JOptionPane.ERROR_MESSAGE);
        }
        catch(NomeGrande e){
            JOptionPane.showMessageDialog(null, "O nome pode ter no máximo 100 caracteres!", 
                                                  "Erro", JOptionPane.ERROR_MESSAGE);
        }
        catch(PubAnoGrande e){
            JOptionPane.showMessageDialog(null, "O ano pode ter no máximo 4 caracteres!", 
                                                  "Erro", JOptionPane.ERROR_MESSAGE);
        }
        catch(CentroEdicaoIgual e){
            JOptionPane.showMessageDialog(null, "Por favor, altere algum campo para realizar a edição!", 
                                                  "Erro", JOptionPane.ERROR_MESSAGE);
        }
        catch(LinhaNaoSelecionada e){
            JOptionPane.showMessageDialog(null, "Por favor, selecione alguma linha para realizar a edição!", 
                                                  "Erro", JOptionPane.ERROR_MESSAGE);
        }
        
        
        
        
    }//GEN-LAST:event_BT_Publicacao_EditarActionPerformed

    private void BT_Publicacao_BuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BT_Publicacao_BuscarActionPerformed
        JOptionPane.showMessageDialog(null, "Lula roubou a funcao deste botao.", 
                                              "Sucesso", JOptionPane.ERROR_MESSAGE);
    }//GEN-LAST:event_BT_Publicacao_BuscarActionPerformed

    private void BT_Publicacao_CadastrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BT_Publicacao_CadastrarActionPerformed
        
        int linha = TB_Publicacao.getSelectedRow(); 
        String nome = TF_Pub_Nome.getText(); 
        String ano = TF_Pub_Ano.getText(); 
        String biblioteca = (String) Itens_Pub_Biblioteca.getSelectedItem();
        int caso = 0;
        String area = TF_Pub_Area.getText(); 
        String edicao = TF_Pub_Edicao.getText(); 
        String genero = TF_Pub_Genero.getText(); 
        String assunto = TF_Pub_Assunto.getText();
        String tipo = null;
        
        if (CheckBox_Academico.isSelected()){
            tipo = "Acadêmico"; 
        }
        else if (CheckBox_Literatura.isSelected()){
            tipo = "Literatura";
        }
        else if (CheckBox_Autoajuda.isSelected()){
            tipo = "Autoajuda";
        }
        else
            tipo = TF_Pub_Tipo.getText();
        
        try{
            if(nome.isEmpty() || ano.isEmpty() || biblioteca.equals("none") || 
                (CheckBox_Academico.isSelected() && (area.isEmpty()||edicao.isEmpty())) || 
                (CheckBox_Literatura.isSelected()&&genero.isEmpty()) || 
                (CheckBox_Autoajuda.isSelected()&&assunto.isEmpty()) || 
                (CheckBox_Outro.isSelected() && tipo.isEmpty())){
                throw new CamposNaoInformados();
            }
            else if (nome.length()>50){
                throw new CentroSiglaGrande();
            }
            else if (tipo.length()>50){
                throw new NomeGrande();
            }
            else if (ano.length()>4){
                throw new PubAnoGrande();
            }
            
            int fk = DAO_Biblioteca.PullBiblioteca(biblioteca);    
            new DAO_Publicacao().CadastrarPublicacao(new Publicacao(tipo, Integer.parseInt(ano), nome, fk));
            int cod = new DAO_General().ReturnLastCod("Publicacao");
          
            DefaultTableModel Table_Aluno = (DefaultTableModel)TB_Publicacao.getModel();
            Object[] data = {cod, nome, ano, biblioteca, tipo};
            Table_Aluno.addRow(data);
            
            if(tipo.equals("Acadêmico")){
                new DAO_Publicacao().CadastrarAcademico(new Academico(Integer.parseInt(edicao), area, cod));
            }
            else if(tipo.equals("Literatura")){
                new DAO_Publicacao().CadastrarLiteratura(new Literatura(genero, cod));
            }
            else if(tipo.equals("Autoajuda")){
                new DAO_Publicacao().CadastrarAutoajuda(new Autoajuda(assunto, cod));
            }
            
            
            if(!PubAutoresIsEmpty()){

            for(int i=0 ; i<TB_Pub_Autores.getRowCount(); i++){
                if((Boolean)TB_Pub_Autores.getValueAt(i, 0) == true){
                    int temp = DAO_Autor.PullAutor((String)TB_Pub_Autores.getValueAt(i, 1));
                    new DAO_Publicacao().CadastrarEscrito(new Escrito(cod, temp));
                }
            }
   
            }
            
            DefaultTableModel model = (DefaultTableModel) TB_Pub_Autores.getModel();
            
            for(int i=0 ; i<TB_Pub_Autores.getRowCount(); i++){
                model.setValueAt(false, i, 0);   
            }
            
            
            Itens_Empres_Publicacao.addItem(nome);
            
            TF_Aluno_Nome.setText("");
            TF_Aluno_Matricula.setText("");
            TF_Aluno_Endereco.setText("");
            
            JOptionPane.showMessageDialog(null, "Publicacao cadastrada com sucesso!", 
                                              "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        }
        catch(CentroSiglaGrande e){
            JOptionPane.showMessageDialog(null, "O nome pode ter no máximo 50 caracteres!", 
                                                  "Erro", JOptionPane.ERROR_MESSAGE);
        }
        catch(NomeGrande e){
            JOptionPane.showMessageDialog(null, "O tipo pode ter no máximo 50 caracteres!", 
                                                  "Erro", JOptionPane.ERROR_MESSAGE);
        }
        catch(PubAnoGrande e){
            JOptionPane.showMessageDialog(null, "O ano pode ter no máximo 4 caracteres!", 
                                                  "Erro", JOptionPane.ERROR_MESSAGE);
        }
        catch(CamposNaoInformados e){
            JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos!", 
                                                  "Erro", JOptionPane.ERROR_MESSAGE);
        }/*
        catch(CentroSiglaUsada e){
            JOptionPane.showMessageDialog(null, "A matricula informada já está sendo usada!", 
                                                  "Erro", JOptionPane.ERROR_MESSAGE);
        }
        */
        
        
        
    }//GEN-LAST:event_BT_Publicacao_CadastrarActionPerformed

    private void BT_Publicacao_RemoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BT_Publicacao_RemoverActionPerformed
       
        //Testa se tem alguma linha selecionada
        if(TB_Publicacao.getSelectedRow() != -1){
        
            //Pega o codigo da linha selecionada
            int id = (int) TB_Publicacao.getValueAt(TB_Publicacao.getSelectedRow(), 0);
            String tipo = (String) TB_Publicacao.getValueAt(TB_Publicacao.getSelectedRow(), 4);
            
            DefaultTableModel Table_Aluno = (DefaultTableModel)TB_Publicacao.getModel();
            
            if (tipo.equals("Acadêmico")){
                new DAO_Publicacao().RemoverAcademico(id);
            }
            else if (tipo.equals("Literatura")){
                new DAO_Publicacao().RemoverLiteratura(id);
            }
            else if (tipo.equals("Autoajuda")){
                new DAO_Publicacao().RemoverAutoajuda(id);
            }
            
            if(!PubAutoresIsEmpty()){

            for(int i=0 ; i<TB_Pub_Autores.getRowCount(); i++){
                //System.out.println((Boolean)TB_Bib_Centros.getValueAt(i, 0));
                if((Boolean)TB_Pub_Autores.getValueAt(i, 0) == true){
                    new DAO_Autor().RemoverEscrito(id);
                }
            }}
            
            
            
            
            new DAO_Publicacao().RemoverPublicacao(id);
            Table_Aluno.removeRow(TB_Publicacao.getSelectedRow());
            
            String nome = TF_Pub_Nome.getText();
            
            DefaultTableModel model = (DefaultTableModel) TB_Pub_Autores.getModel();
            
            for(int i=0 ; i<TB_Pub_Autores.getRowCount(); i++){
                model.setValueAt(false, i, 0);   
            }
            
            
            
            TF_Pub_Nome.setText("");
            TF_Pub_Ano.setText("");
            TF_Pub_Edicao.setText("");
            TF_Pub_Genero.setText("");
            TF_Pub_Assunto.setText("");
            TF_Pub_Tipo.setText("");
            Itens_Pub_Biblioteca.setSelectedIndex(0);
            Itens_Empres_Publicacao.removeItem(nome);
            JOptionPane.showMessageDialog(null, "Aluno removido com sucesso.", 
                                              "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        }    
        else
            JOptionPane.showMessageDialog(null, "Por favor, selecione uma linha.", 
                                              "Erro", JOptionPane.ERROR_MESSAGE);



    }//GEN-LAST:event_BT_Publicacao_RemoverActionPerformed

    private void BT_Biblioteca_BuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BT_Biblioteca_BuscarActionPerformed

    }//GEN-LAST:event_BT_Biblioteca_BuscarActionPerformed

    private void BT_Biblioteca_CadastrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BT_Biblioteca_CadastrarActionPerformed
        
        String nome = TF_Bib_Nome.getText(); 
        String endereco = TF_Bib_Endereco.getText(); 
        String sigla = TF_Bib_Sigla.getText();
 
    
        try{
            if(nome.isEmpty() || endereco.isEmpty() || sigla.isEmpty()){
                throw new CamposNaoInformados();
            }
            else if (nome.length()>50){
                throw new CentroSiglaGrande();
            }
            else if (endereco.length()>100){
                throw new NomeGrande();
            }
            else if(DAO_Biblioteca.TestaSigla(TF_Bib_Sigla.getText()) == true){
                throw new CentroSiglaUsada();
            }
            
            new DAO_Biblioteca().CadastrarBiblioteca(new Biblioteca(endereco, nome, sigla));
            int id = new DAO_General().ReturnLastCod("Biblioteca");
          
            DefaultTableModel Table_Biblioteca = (DefaultTableModel)TB_Biblioteca.getModel();
            Object[] data = {id, sigla, nome, endereco};
            Table_Biblioteca.addRow(data);
            
            
            if(!BibCentroIsEmpty()){

            for(int i=0 ; i<TB_Bib_Centros.getRowCount(); i++){
                if((Boolean)TB_Bib_Centros.getValueAt(i, 0) == true){
                    int temp = DAO_Aluno.PullCentro((String)TB_Bib_Centros.getValueAt(i, 1));
                    System.out.println(temp);
                    new DAO_Biblioteca().CadastrarPertence(new Pertence(id, temp));
                }
            }
   
            }
            
            DefaultTableModel model = (DefaultTableModel) TB_Bib_Centros.getModel();
            
            for(int i=0 ; i<TB_Bib_Centros.getRowCount(); i++){
                model.setValueAt(false, i, 0);   
            }
            
            
            
            Itens_Pub_Biblioteca.addItem(nome);
            TF_Bib_Nome.setText("");
            TF_Bib_Sigla.setText("");
            TF_Bib_Endereco.setText("");

            
            JOptionPane.showMessageDialog(null, "Biblioteca cadastrada com sucesso!", 
                                              "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        }
        catch(CentroSiglaGrande e){
            JOptionPane.showMessageDialog(null, "O nome pode ter no máximo 50 caracteres!", 
                                                  "Erro", JOptionPane.ERROR_MESSAGE);
        }
        catch(NomeGrande e){
            JOptionPane.showMessageDialog(null, "O endereco pode ter no máximo 100 caracteres!", 
                                                  "Erro", JOptionPane.ERROR_MESSAGE);
        }
        catch(CamposNaoInformados e){
            JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos!", 
                                                  "Erro", JOptionPane.ERROR_MESSAGE);
        }
        catch(CentroSiglaUsada e){
            JOptionPane.showMessageDialog(null, "A sigla informada já está sendo usada!", 
                                                  "Erro", JOptionPane.ERROR_MESSAGE);
        }
        
        
    }//GEN-LAST:event_BT_Biblioteca_CadastrarActionPerformed

    private void BT_Biblioteca_RemoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BT_Biblioteca_RemoverActionPerformed
        //Testa se tem alguma linha selecionada
        if(TB_Biblioteca.getSelectedRow() != -1){
        
            //Pega o codigo da linha selecionada
            int id = (int) TB_Biblioteca.getValueAt(TB_Biblioteca.getSelectedRow(), 0);
            
            
            if(!BibCentroIsEmpty()){

            for(int i=0 ; i<TB_Bib_Centros.getRowCount(); i++){
                System.out.println((Boolean)TB_Bib_Centros.getValueAt(i, 0));
                if((Boolean)TB_Bib_Centros.getValueAt(i, 0) == true){
                    new DAO_Biblioteca().RemoverPertence(id);
                }
            }}
            
            new DAO_Biblioteca().RemoverBiblioteca(id);

            DefaultTableModel Table_Biblioteca = (DefaultTableModel)TB_Biblioteca.getModel();
            Table_Biblioteca.removeRow(TB_Biblioteca.getSelectedRow());
            
            DefaultTableModel model = (DefaultTableModel) TB_Bib_Centros.getModel();
            
            for(int i=0 ; i<TB_Bib_Centros.getRowCount(); i++){
                model.setValueAt(false, i, 0);   
            }
            
            
            String nome = TF_Bib_Nome.getText();
            TF_Bib_Nome.setText("");
            TF_Bib_Sigla.setText("");
            TF_Bib_Endereco.setText("");
            Itens_Pub_Biblioteca.removeItem(nome);
            JOptionPane.showMessageDialog(null, "Biblioteca removida com sucesso.", 
                                              "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        }    
        else
            JOptionPane.showMessageDialog(null, "Por favor, selecione uma linha.", 
                                              "Erro", JOptionPane.ERROR_MESSAGE);
        
    }//GEN-LAST:event_BT_Biblioteca_RemoverActionPerformed

    private void BT_Biblioteca_EditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BT_Biblioteca_EditarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BT_Biblioteca_EditarActionPerformed

    private void BT_Emprestimo_BuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BT_Emprestimo_BuscarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BT_Emprestimo_BuscarActionPerformed

    private void BT_Emprestimo_CadastrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BT_Emprestimo_CadastrarActionPerformed
        String data = TF_Empres_Data.getText(); 
        String hora = TF_Empres_Hora.getText(); 
        String funcionario = (String)Itens_Empres_Funcionario.getSelectedItem();
        String publicacao = (String)Itens_Empres_Publicacao.getSelectedItem();
        String aluno = (String)Itens_Empres_Aluno.getSelectedItem();
    
        try{
            if(data.isEmpty() || hora.isEmpty() || funcionario.isEmpty() || publicacao.isEmpty() || aluno.isEmpty()){
                throw new CamposNaoInformados();
            }
            
            
            new DAO_Emprestimo().CadastrarEmprestimo(new Emprestimo(converterStringParaSQLDate(data), converterStringParaSQLTime(hora), DAO_Emprestimo.PullPublicacao(publicacao), DAO_Emprestimo.PullAluno(aluno), DAO_Emprestimo.PullFuncionario(funcionario)));
          
            DefaultTableModel Table_Emprestimo = (DefaultTableModel)TB_Emprestimo.getModel();
            int id = new DAO_General().ReturnLastCod("Emprestimo");
            Object[] dados = {id, data, hora, funcionario, publicacao, aluno};
            Table_Emprestimo.addRow(dados);
            
            TF_Empres_Data.setText("");
            TF_Empres_Hora.setText("");
            
            JOptionPane.showMessageDialog(null, "Emprestimo cadastrado com sucesso!", 
                                              "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        }
        catch(CamposNaoInformados e){
            JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos!", 
                                                  "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_BT_Emprestimo_CadastrarActionPerformed

    private void BT_Emprestimo_RemoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BT_Emprestimo_RemoverActionPerformed
         if(TB_Emprestimo.getSelectedRow() != -1){
        
            //Pega o codigo da linha selecionada
            int id = (int) TB_Emprestimo.getValueAt(TB_Emprestimo.getSelectedRow(), 0);
            
            DefaultTableModel Table_Emprestimo = (DefaultTableModel)TB_Emprestimo.getModel();
            Table_Emprestimo.removeRow(TB_Emprestimo.getSelectedRow());
           
            new DAO_Emprestimo().RemoverEmprestimo(id);
            
            TF_Empres_Data.setText("");
            TF_Empres_Hora.setText("");
            Itens_Empres_Aluno.setSelectedIndex(0);
            Itens_Empres_Funcionario.setSelectedIndex(0);
            Itens_Empres_Publicacao.setSelectedIndex(0);
            JOptionPane.showMessageDialog(null, "Emprestimo removido com sucesso.", 
                                              "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        }    
        else
            JOptionPane.showMessageDialog(null, "Por favor, selecione uma linha.", 
                                              "Erro", JOptionPane.ERROR_MESSAGE);
    }//GEN-LAST:event_BT_Emprestimo_RemoverActionPerformed

    private void BT_Emprestimo_EditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BT_Emprestimo_EditarActionPerformed
         try{
            if(TB_Emprestimo.getSelectedRow()==-1){
                throw new LinhaNaoSelecionada();
            }
            else{
                int linha = TB_Emprestimo.getSelectedRow();
                Date DataNew = converterStringParaSQLDate(TF_Empres_Data.getText());
                Time HoraNew = converterStringParaSQLTime(TF_Empres_Hora.getText());
                String FuncionarioNew = (String) Itens_Empres_Funcionario.getSelectedItem();
                String AlunoNew = (String) Itens_Empres_Aluno.getSelectedItem();
                String PublicacaoNew = (String) Itens_Empres_Publicacao.getSelectedItem();
                
                // 1. Obter e converter o valor da coluna 1 para java.sql.Date
                Object valorData = TB_Emprestimo.getValueAt(linha, 1); // Obtém o valor como Object
                Date DataOld = null;

                if (valorData instanceof Date) {
                    DataOld = (Date) valorData; // Cast direto
                } else if (valorData instanceof String) {
                    // Caso o valor seja uma String, converte para java.sql.Date
                    DataOld = Date.valueOf((String) valorData);
                }

                // 2. Obter e converter o valor da coluna 2 para java.sql.Time
                Object valorHora = TB_Emprestimo.getValueAt(linha, 2); // Obtém o valor como Object
                Time HoraOld = null;

                if (valorHora instanceof Time) {
                    HoraOld = (Time) valorHora; // Cast direto
                } else if (valorHora instanceof String) {
                    // Caso o valor seja uma String, converte para java.sql.Time
                    HoraOld = Time.valueOf((String) valorHora);
                }
                
                int id = (int) TB_Emprestimo.getValueAt(linha,0);
                String FuncionarioOld = (String) TB_Emprestimo.getValueAt(linha,3);
                String AlunoOld = (String) TB_Emprestimo.getValueAt(linha,4);
                String PublicacaoOld = (String) TB_Emprestimo.getValueAt(linha,5);
            
                //testa se as informacoes fornecidas sao diferentes das ja cadastrados
                if(DataNew.compareTo(DataOld)==0 && HoraNew.compareTo(HoraOld)==0 && FuncionarioNew.equals(FuncionarioOld) && AlunoNew.equals(AlunoOld) && PublicacaoNew.equals(PublicacaoOld)){
                    throw new CentroEdicaoIgual();       
                }
                else{
          
                    //testa se o nome fornecido é diferente do já cadastrado
                    if(DataNew.compareTo(DataOld)!=0){
                        new DAO_Emprestimo().EditarData(id, DataOld, DataNew); 
                        TB_Emprestimo.setValueAt(DataNew, linha, 1);
                    }
                

                    if(HoraNew.compareTo(HoraOld)!=0){
                        new DAO_Emprestimo().EditarHora(id, HoraOld, HoraNew); 
                        TB_Emprestimo.setValueAt(HoraNew, linha, 2);
                    }
                    
                    if(!FuncionarioNew.equals(FuncionarioOld)){
                        new DAO_Emprestimo().EditarFuncionario(id, PullFuncionario(FuncionarioOld), PullFuncionario(FuncionarioNew)); 
                        TB_Emprestimo.setValueAt(FuncionarioNew, linha, 3);
                    }
                    
                    if(!PublicacaoNew.equals(PublicacaoOld)){
                        new DAO_Emprestimo().EditarPublicacao(id, PullPublicacao(PublicacaoOld), PullPublicacao(PublicacaoNew)); 
                        TB_Emprestimo.setValueAt(PublicacaoNew, linha, 4);
                    }
                    
                    if(!AlunoNew.equals(AlunoOld)){
                        new DAO_Emprestimo().EditarAluno(id, PullAluno(AlunoOld), PullAluno(AlunoNew)); 
                        TB_Emprestimo.setValueAt(AlunoNew, linha, 5);
                    }
                
                    JOptionPane.showMessageDialog(null, "Edição realizada com sucesso.", 
                                              "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    TF_Empres_Data.setText("");
                    TF_Empres_Hora.setText("");
                    Itens_Empres_Publicacao.setSelectedIndex(0);
                    Itens_Empres_Aluno.setSelectedIndex(0);
                    Itens_Empres_Funcionario.setSelectedIndex(0);

                }
            
            }
        }
        catch(CentroEdicaoIgual e){
            JOptionPane.showMessageDialog(null, "Por favor, altere algum campo para realizar a edição!", 
                                                  "Erro", JOptionPane.ERROR_MESSAGE);
        }
        catch(LinhaNaoSelecionada e){
            JOptionPane.showMessageDialog(null, "Por favor, selecione alguma linha para realizar a edição!", 
                                                  "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_BT_Emprestimo_EditarActionPerformed

    private void BT_Funcionario_BuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BT_Funcionario_BuscarActionPerformed
        String nome = TF_Funci_Nome.getText();
        String salario = TF_Funci_Salario.getText();
        int caso = 0;
        
        

            if(!nome.isEmpty() && salario.isEmpty())
                caso = 1;
            else if(nome.isEmpty() && !salario.isEmpty())
                caso = 2;
            else if(!nome.isEmpty() && !salario.isEmpty())
                caso = 3;
        
            switch(caso){
                case 1:
                    carregarFuncionariosNome(nome);
                    break;
                case 2:
                    carregarFuncionariosSalario(salario);
                    break;
                case 3:
                    carregarFuncionariosNomeSalario(nome, salario);
                    break;
                default:
                    carregarFuncionarios();           
            }
    }//GEN-LAST:event_BT_Funcionario_BuscarActionPerformed

    private void BT_Funcionario_CadastrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BT_Funcionario_CadastrarActionPerformed
       
        String nome = TF_Funci_Nome.getText();  // Assumindo que o TF_Funci_Nome é o campo para o Nome
        String salario = TF_Funci_Salario.getText(); // Assumindo que o TF_Funci_Salario é o campo para a Salario
    
        try{
            if(nome.isEmpty() || salario.isEmpty()){
                throw new CamposNaoInformados();
            }
            else if (nome.length()>50){
                throw new NomeGrande();
            }
            
            
            new DAO_Funcionario().CadastrarFuncionario(new Funcionario(nome, Float.parseFloat(salario)));
            carregarFuncionariosSalarios_MIN_MAX_AVG();
       
        
            int id = new DAO_General().ReturnLastCod("Funcionario");
        
            //Centro temp = new Centro();
            //temp = new DAO_Centro().BuscarCentro(id); 
        
            DefaultTableModel Table_Funcionario = (DefaultTableModel)TB_Funcionario.getModel();
            Object[] data = {id, nome, salario};
            Table_Funcionario.addRow(data);
            
            TF_Funci_Nome.setText("");
            TF_Funci_Salario.setText("");
            
            JOptionPane.showMessageDialog(null, "Funcionario cadastrado com sucesso!", 
                                              "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        }
        catch(NumberFormatException e){
            JOptionPane.showMessageDialog(null, "O Formato do Salário é inválido!", 
                                                  "Erro", JOptionPane.ERROR_MESSAGE);
        }
        catch(NomeGrande e){
            JOptionPane.showMessageDialog(null, "O nome pode ter no máximo 50 caracteres!", 
                                                  "Erro", JOptionPane.ERROR_MESSAGE);
        }
        catch(CamposNaoInformados e){
            JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos!", 
                                                  "Erro", JOptionPane.ERROR_MESSAGE);
        }
        
        
        
        
        
    }//GEN-LAST:event_BT_Funcionario_CadastrarActionPerformed

    private void BT_Funcionario_RemoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BT_Funcionario_RemoverActionPerformed
       
        //Testa se tem alguma linha selecionada
        if(TB_Funcionario.getSelectedRow() != -1){
        
            //Pega o codigo da linha selecionada
            int id = (int) TB_Funcionario.getValueAt(TB_Funcionario.getSelectedRow(), 0);
            
            DefaultTableModel Table_Funcionario = (DefaultTableModel)TB_Funcionario.getModel();
            Table_Funcionario.removeRow(TB_Funcionario.getSelectedRow());
           
            new DAO_Funcionario().RemoverFuncionario(id);
            
            carregarFuncionariosSalarios_MIN_MAX_AVG();
            TF_Funci_Nome.setText("");
            TF_Funci_Salario.setText("");
            JOptionPane.showMessageDialog(null, "Funcionario removido com sucesso.", 
                                              "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        }    
        else
            JOptionPane.showMessageDialog(null, "Por favor, selecione uma linha.", 
                                              "Erro", JOptionPane.ERROR_MESSAGE); 
    }//GEN-LAST:event_BT_Funcionario_RemoverActionPerformed

    private void BT_Funcionario_EditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BT_Funcionario_EditarActionPerformed
        
        try{
            //System.out.println(TB_Centro.getSelectedRow());
            if(TB_Funcionario.getSelectedRow()==-1){
                throw new LinhaNaoSelecionada();
            }
            else{
                String nomeNew = TF_Funci_Nome.getText();
                String salarioNew = TF_Funci_Salario.getText();
                String nomeOld = (String) TB_Funcionario.getValueAt(TB_Funcionario.getSelectedRow(),1);
                String salarioOld = (String) TB_Funcionario.getValueAt(TB_Funcionario.getSelectedRow(),2);
                int codigo = (int) TB_Funcionario.getValueAt(TB_Funcionario.getSelectedRow(),0);
                
                //Testa se tem alguma informação nos campos
                if(nomeNew.isEmpty() || salarioNew.isEmpty()){
                    throw new CamposNaoInformados();
                }
            

            
                //testa se as informacoes fornecidas sao diferentes das ja cadastrados
                if(nomeNew.equals(nomeOld) && salarioNew.equals(salarioOld)){
                    throw new CentroEdicaoIgual();       
                }
                else{
          
                    //testa se o nome fornecido é diferente do já cadastrado
                    if(!nomeNew.equals(nomeOld)){
                        new DAO_Funcionario().EditarNome(codigo, nomeNew); 
                        TB_Funcionario.setValueAt(nomeNew, TB_Funcionario.getSelectedRow(), 1);
                    }
                
                    //testa se a sigla fornecida é diferente da já cadastrada
                    if(!salarioNew.equals(salarioOld)){
                        new DAO_Funcionario().EditarSalario(codigo, Float.parseFloat(salarioNew)); 
                        TB_Funcionario.setValueAt(salarioNew, TB_Funcionario.getSelectedRow(), 2);
                    }
                
                    
                    JOptionPane.showMessageDialog(null, "Edição realizada com sucesso.", 
                                              "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    TF_Funci_Nome.setText("");
                    TF_Funci_Salario.setText("");

                }
            
            }
        }
        catch(CamposNaoInformados e){
            JOptionPane.showMessageDialog(null, "Por favor, informe algum campo para editar.", 
                                              "Erro", JOptionPane.ERROR_MESSAGE);
        }
        catch(CentroEdicaoIgual e){
            JOptionPane.showMessageDialog(null, "Por favor, altere algum campo para realizar a edição!", 
                                                  "Erro", JOptionPane.ERROR_MESSAGE);
        }
        catch(LinhaNaoSelecionada e){
            JOptionPane.showMessageDialog(null, "Por favor, selecione alguma linha para realizar a edição!", 
                                                  "Erro", JOptionPane.ERROR_MESSAGE);
        }catch(NumberFormatException e){
            JOptionPane.showMessageDialog(null, "O Formato do Salário é inválido!", 
                                                  "Erro", JOptionPane.ERROR_MESSAGE);
        }
        
        
    }//GEN-LAST:event_BT_Funcionario_EditarActionPerformed

    private void BT_Autor_BuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BT_Autor_BuscarActionPerformed
       String nome = TF_Autor_Nome.getText();
        String Pais = TF_Autor_Pais.getText();
        int caso = 0;
        
            if(!nome.isEmpty() && Pais.isEmpty())
                caso = 1;
            else if(nome.isEmpty() && !Pais.isEmpty())
                caso = 2;
            else if(!nome.isEmpty() && !Pais.isEmpty())
                caso = 3;
        
            switch(caso){
                case 1:
                    carregarAutorNome(nome);
                    break;
                case 2:
                    carregarAutorPais(Pais);
                    break;
                case 3:
                    carregarAutorNomePais(nome, Pais);
                    break;
                default:
                    carregarAutores();           
            }
    }//GEN-LAST:event_BT_Autor_BuscarActionPerformed

    private void BT_Autor_CadastrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BT_Autor_CadastrarActionPerformed
        String nome = TF_Autor_Nome.getText();  
        String pais = TF_Autor_Pais.getText();
    
        try{
            if(nome.isEmpty() || pais.isEmpty()){
                throw new CamposNaoInformados();
            }
            else if (nome.length()>50){
                throw new NomeGrande();
                
            }else if (pais.length()>50){
                throw new NomeGrande();
                
            }
            
            
            new DAO_Autor().CadastrarAutor(new Autor(nome, pais));
       
        
            int id = new DAO_General().ReturnLastCod("Autor");
        
            //Centro temp = new Centro();
            //temp = new DAO_Centro().BuscarCentro(id); 
        
            DefaultTableModel Table_Autor = (DefaultTableModel)TB_Autor.getModel();
            Object[] data = {id, nome, pais};
            Table_Autor.addRow(data);
            
            TF_Autor_Nome.setText("");
            TF_Autor_Pais.setText("");
            
            JOptionPane.showMessageDialog(null, "Autor cadastrado com sucesso!", 
                                              "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        }
        catch(NomeGrande e){
            JOptionPane.showMessageDialog(null, "Os campos podem ter no máximo 50 caracteres!", 
                                                  "Erro", JOptionPane.ERROR_MESSAGE);
        }
        catch(CamposNaoInformados e){
            JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos!", 
                                                  "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_BT_Autor_CadastrarActionPerformed

    private void BT_Autor_RemoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BT_Autor_RemoverActionPerformed
        //Testa se tem alguma linha selecionada
        if(TB_Autor.getSelectedRow() != -1){
        
            //Pega o codigo da linha selecionada
            int id = (int) TB_Autor.getValueAt(TB_Autor.getSelectedRow(), 0);
            
            DefaultTableModel Table_Autor = (DefaultTableModel)TB_Autor.getModel();
            Table_Autor.removeRow(TB_Autor.getSelectedRow());
           
            new DAO_Autor().RemoverAutor(id);
            
            TF_Autor_Nome.setText("");
            TF_Autor_Pais.setText("");
            JOptionPane.showMessageDialog(null, "Autor removido com sucesso.", 
                                              "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        }    
        else
            JOptionPane.showMessageDialog(null, "Por favor, selecione uma linha.", 
                                              "Erro", JOptionPane.ERROR_MESSAGE);
    }//GEN-LAST:event_BT_Autor_RemoverActionPerformed

    private void BT_Autor_EditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BT_Autor_EditarActionPerformed
        try{
            //System.out.println(TB_Centro.getSelectedRow());
            if(TB_Autor.getSelectedRow()==-1){
                throw new LinhaNaoSelecionada();
            }
            else{
                String nomeNew = TF_Autor_Nome.getText();
                String paisNew = TF_Autor_Pais.getText();
                String nomeOld = (String) TB_Autor.getValueAt(TB_Autor.getSelectedRow(),1);
                String paisOld = (String) TB_Autor.getValueAt(TB_Autor.getSelectedRow(),2);
                int codigo = (int) TB_Autor.getValueAt(TB_Autor.getSelectedRow(),0);
                
                //Testa se tem alguma informação nos campos
                if(nomeNew.isEmpty() || paisNew.isEmpty()){
                    throw new CamposNaoInformados();
                }
            

            
                //testa se as informacoes fornecidas sao diferentes das ja cadastrados
                if(nomeNew.equals(nomeOld) && paisNew.equals(paisOld)){
                    throw new CentroEdicaoIgual();       
                }
                else{
          
                    //testa se o nome fornecido é diferente do já cadastrado
                    if(!nomeNew.equals(nomeOld)){
                        new DAO_Autor().EditarNome(codigo, nomeNew); 
                        TB_Autor.setValueAt(nomeNew, TB_Autor.getSelectedRow(), 1);
                    }
                
                    //testa se a sigla fornecida é diferente da já cadastrada
                    if(!paisNew.equals(paisOld)){
                        new DAO_Autor().EditarPais(codigo, paisNew); 
                        TB_Autor.setValueAt(paisNew, TB_Autor.getSelectedRow(), 2);
                    }
                
                    JOptionPane.showMessageDialog(null, "Edição realizada com sucesso.", 
                                              "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    TF_Autor_Nome.setText("");
                    TF_Autor_Pais.setText("");

                }
            
            }
        }
        catch(CamposNaoInformados e){
            JOptionPane.showMessageDialog(null, "Por favor, informe algum campo para editar.", 
                                              "Erro", JOptionPane.ERROR_MESSAGE);
        }
        catch(CentroEdicaoIgual e){
            JOptionPane.showMessageDialog(null, "Por favor, altere algum campo para realizar a edição!", 
                                                  "Erro", JOptionPane.ERROR_MESSAGE);
        }
        catch(LinhaNaoSelecionada e){
            JOptionPane.showMessageDialog(null, "Por favor, selecione alguma linha para realizar a edição!", 
                                                  "Erro", JOptionPane.ERROR_MESSAGE);
        }catch(NumberFormatException e){
            JOptionPane.showMessageDialog(null, "O Formato do Salário é inválido!", 
                                                  "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_BT_Autor_EditarActionPerformed

    private void TB_AlunoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TB_AlunoMouseClicked
        TF_Aluno_Nome.setText((String) TB_Aluno.getValueAt(TB_Aluno.getSelectedRow(), 1));
        TF_Aluno_Matricula.setText(String.valueOf(TB_Aluno.getValueAt(TB_Aluno.getSelectedRow(), 0)));
        TF_Aluno_Endereco.setText((String) TB_Aluno.getValueAt(TB_Aluno.getSelectedRow(), 3));
        Itens_Aluno_Centro.setSelectedItem((String) TB_Aluno.getValueAt(TB_Aluno.getSelectedRow(), 2));    
    }//GEN-LAST:event_TB_AlunoMouseClicked

    private void TB_PublicacaoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TB_PublicacaoMouseClicked
        
        int linha = TB_Publicacao.getSelectedRow();
    
        String tipo = String.valueOf(TB_Publicacao.getValueAt(linha, 4));
        
        int id = (int) TB_Publicacao.getValueAt(linha, 0);
        System.out.println(id);
        
        if(tipo.equals("Acadêmico")){
            Academico temp = new DAO_Publicacao().BuscarAcademico(id);
            System.out.println(temp==null);
            String mensagem = String.format("Área: %s \n Edição: %s", temp.getArea(), temp.getEdicao());         
            JOptionPane.showMessageDialog(null, mensagem, null, JOptionPane.PLAIN_MESSAGE);
            CheckBox_Academico.setSelected(true);
            TF_Pub_Edicao.setEnabled(true);
            TF_Pub_Area.setEnabled(true);
            TF_Pub_Genero.setEnabled(false);
            TF_Pub_Genero.setText("");
            TF_Pub_Assunto.setEnabled(false);
            TF_Pub_Assunto.setText("");
            TF_Pub_Tipo.setEnabled(false);
            TF_Pub_Tipo.setText("");            
            TF_Pub_Edicao.setText(String.valueOf(temp.getEdicao()));
            TF_Pub_Area.setText(temp.getArea());
        }
        else if(tipo.equals("Literatura")){
            String temp = new DAO_Publicacao().BuscarLiteratura(id);
            String mensagem = String.format("Gênero Textual: %s", temp);         
            JOptionPane.showMessageDialog(null, mensagem, null, JOptionPane.PLAIN_MESSAGE);
            CheckBox_Literatura.setSelected(true);
            TF_Pub_Edicao.setEnabled(false);
            TF_Pub_Edicao.setText("");
            TF_Pub_Area.setEnabled(false);
            TF_Pub_Area.setText("");
            TF_Pub_Genero.setEnabled(true);
            TF_Pub_Assunto.setEnabled(false);
            TF_Pub_Assunto.setText("");
            TF_Pub_Tipo.setEnabled(false);
            TF_Pub_Tipo.setText("");            
            TF_Pub_Genero.setText(temp);
        }
        else if(tipo.equals("Autoajuda")){
            String temp = new DAO_Publicacao().BuscarAutoajuda(id);
            String mensagem = String.format("Assunto: %s", temp);         
            JOptionPane.showMessageDialog(null, mensagem, null, JOptionPane.PLAIN_MESSAGE);
            CheckBox_Autoajuda.setSelected(true);
            TF_Pub_Edicao.setEnabled(false);
            TF_Pub_Edicao.setText("");
            TF_Pub_Area.setEnabled(false);
            TF_Pub_Area.setText("");
            TF_Pub_Genero.setEnabled(false);
            TF_Pub_Genero.setText("");
            TF_Pub_Assunto.setEnabled(true);
            TF_Pub_Assunto.setText(temp);
            TF_Pub_Tipo.setEnabled(false);
            TF_Pub_Tipo.setText("");            
            TF_Pub_Genero.setText(temp);
        }
        else{
            CheckBox_Outro.setSelected(true);
            TF_Pub_Edicao.setEnabled(false);
            TF_Pub_Edicao.setText("");
            TF_Pub_Area.setEnabled(false);
            TF_Pub_Area.setText("");
            TF_Pub_Genero.setEnabled(false);
            TF_Pub_Genero.setText("");
            TF_Pub_Assunto.setEnabled(false);
            TF_Pub_Assunto.setText("");
            TF_Pub_Tipo.setEnabled(true);
            TF_Pub_Tipo.setText("");            
            TF_Pub_Tipo.setText((String) TB_Publicacao.getValueAt(TB_Publicacao.getSelectedRow(), 4));
        }
        
        int fk = (int)TB_Publicacao.getValueAt(linha, 0);
        
        try {
	        //String query = "SELECT * FROM Pertence where fk_Cod_Biblioteca=?"; 
                
                String query = "select distinct c.nome from Escrito join Autor c ON c.Cod_Autor=fk_Cod_Autor where fk_Cod_Publicacao=?";
                
                
                
                PreparedStatement ps = null;
                ResultSet rs = null;
                ps = SQL_connection.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, fk);
                rs = ps.executeQuery();
                
	        DefaultTableModel model = (DefaultTableModel) TB_Pub_Autores.getModel();
                
                for(int i=0 ; i<TB_Pub_Autores.getRowCount(); i++){
                            model.setValueAt(false, i, 0);
                    }
                

	        while (rs.next()) {

                    for(int i=0 ; i<TB_Pub_Autores.getRowCount(); i++){
                       // System.out.println(rs.getString("Nome"));
                        if((Boolean)TB_Pub_Autores.getValueAt(i, 1).equals(rs.getString("Nome"))){
                            model.setValueAt(true, i, 0);
                        }
                    }
                   
	        }

	        rs.close();
	        ps.close();

	    } catch (SQLException ex) {
	        JOptionPane.showMessageDialog(this, "Erro ao carregar dados: " + ex.getMessage());
	    }
        
        
            
        
        TF_Pub_Nome.setText(tipo);
        
        TF_Pub_Nome.setText((String) TB_Publicacao.getValueAt(TB_Publicacao.getSelectedRow(), 1));
        TF_Pub_Ano.setText(String.valueOf(TB_Publicacao.getValueAt(TB_Publicacao.getSelectedRow(), 2)));
        Itens_Pub_Biblioteca.setSelectedItem((String) TB_Publicacao.getValueAt(TB_Publicacao.getSelectedRow(), 3));
        

        
        
    }//GEN-LAST:event_TB_PublicacaoMouseClicked

    private void TB_FuncionarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TB_FuncionarioMouseClicked
        TF_Funci_Nome.setText((String) TB_Funcionario.getValueAt(TB_Funcionario.getSelectedRow(), 1));
        TF_Funci_Salario.setText((String) TB_Funcionario.getValueAt(TB_Funcionario.getSelectedRow(), 2));
    }//GEN-LAST:event_TB_FuncionarioMouseClicked

    private void TB_AutorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TB_AutorMouseClicked
        TF_Autor_Nome.setText((String) TB_Autor.getValueAt(TB_Autor.getSelectedRow(), 1));
        TF_Autor_Pais.setText((String) TB_Autor.getValueAt(TB_Autor.getSelectedRow(), 2));
    }//GEN-LAST:event_TB_AutorMouseClicked

    private void Itens_Aluno_CentroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Itens_Aluno_CentroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Itens_Aluno_CentroActionPerformed

    private void TB_BibliotecaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TB_BibliotecaMouseClicked
        TF_Bib_Nome.setText((String) TB_Biblioteca.getValueAt(TB_Biblioteca.getSelectedRow(), 2));
        String sigla = String.valueOf(TB_Biblioteca.getValueAt(TB_Biblioteca.getSelectedRow(), 1));
        TF_Bib_Sigla.setText(sigla);
        TF_Bib_Endereco.setText((String) TB_Biblioteca.getValueAt(TB_Biblioteca.getSelectedRow(), 3));
        
        int fk = DAO_Publicacao.PullBiblioteca(sigla);
        
        try {
	        //String query = "SELECT * FROM Pertence where fk_Cod_Biblioteca=?"; 
                
                String query = "select distinct c.sigla from Pertence join Centro c ON c.Cod_Centro=fk_Cod_centro where fk_Cod_Biblioteca=?";
                
                
                
                PreparedStatement ps = null;
                ResultSet rs = null;
                ps = SQL_connection.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, fk);
                rs = ps.executeQuery();
                
	        DefaultTableModel model = (DefaultTableModel) TB_Bib_Centros.getModel();
                
                for(int i=0 ; i<TB_Bib_Centros.getRowCount(); i++){
                            model.setValueAt(false, i, 0);
                    }
                

	        while (rs.next()) {

                    for(int i=0 ; i<TB_Bib_Centros.getRowCount(); i++){
                        System.out.println(rs.getString("Sigla"));
                        if((Boolean)TB_Bib_Centros.getValueAt(i, 1).equals(rs.getString("Sigla"))){
                            model.setValueAt(true, i, 0);
                        }
                    }
                   
	        }

	        rs.close();
	        ps.close();

	    } catch (SQLException ex) {
	        JOptionPane.showMessageDialog(this, "Erro ao carregar dados: " + ex.getMessage());
	    }
        
    }//GEN-LAST:event_TB_BibliotecaMouseClicked

    private void TB_EmprestimoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TB_EmprestimoMouseClicked
        // 1. Criando os formatadores
        SimpleDateFormat sdfData = new SimpleDateFormat("yyyy-MM-dd"); // Formato para Data
        SimpleDateFormat sdfHora = new SimpleDateFormat("HH:mm:ss");   // Formato para Hora

        // 2. Obtendo a data da tabela na coluna 1
        Object valorData = TB_Emprestimo.getValueAt(TB_Emprestimo.getSelectedRow(), 1);

        if (valorData instanceof Date) {
            // Se for do tipo Date, formata diretamente
            TF_Empres_Data.setText(sdfData.format((Date) valorData));
        } else if (valorData instanceof String) {
            // Se for uma String, exibe diretamente
            TF_Empres_Data.setText((String) valorData);
        } else {
            TF_Empres_Data.setText("Data inválida!"); // Tratamento de erro
        }

        // 3. Obtendo a hora da tabela na coluna 2
        Object valorHora = TB_Emprestimo.getValueAt(TB_Emprestimo.getSelectedRow(), 2);

        if (valorHora instanceof Time) {
            // Se for do tipo Time, formata diretamente
            TF_Empres_Hora.setText(sdfHora.format((Time) valorHora));
        } else if (valorHora instanceof String) {
            // Se for uma String, exibe diretamente
            TF_Empres_Hora.setText((String) valorHora);
        } else {
            TF_Empres_Hora.setText("Hora inválida!"); // Tratamento de erro
        }

        // 4. Configurando os demais campos
        Itens_Empres_Funcionario.setSelectedItem((String) TB_Emprestimo.getValueAt(TB_Emprestimo.getSelectedRow(), 3));
        Itens_Empres_Publicacao.setSelectedItem((String) TB_Emprestimo.getValueAt(TB_Emprestimo.getSelectedRow(), 4));
        Itens_Empres_Aluno.setSelectedItem((String) TB_Emprestimo.getValueAt(TB_Emprestimo.getSelectedRow(), 5));

    }//GEN-LAST:event_TB_EmprestimoMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BT_Aluno_Buscar;
    private javax.swing.JButton BT_Aluno_Cadastrar;
    private javax.swing.JButton BT_Aluno_Editar;
    private javax.swing.JButton BT_Aluno_Remover;
    private javax.swing.JButton BT_Autor_Buscar;
    private javax.swing.JButton BT_Autor_Cadastrar;
    private javax.swing.JButton BT_Autor_Editar;
    private javax.swing.JButton BT_Autor_Remover;
    private javax.swing.JButton BT_Biblioteca_Buscar;
    private javax.swing.JButton BT_Biblioteca_Cadastrar;
    private javax.swing.JButton BT_Biblioteca_Editar;
    private javax.swing.JButton BT_Biblioteca_Remover;
    private javax.swing.JButton BT_Centro_Buscar;
    private javax.swing.JButton BT_Centro_Cadastrar;
    private javax.swing.JButton BT_Centro_Editar;
    private javax.swing.JButton BT_Centro_Remover;
    private javax.swing.JButton BT_Emprestimo_Buscar;
    private javax.swing.JButton BT_Emprestimo_Cadastrar;
    private javax.swing.JButton BT_Emprestimo_Editar;
    private javax.swing.JButton BT_Emprestimo_Remover;
    private javax.swing.JButton BT_Funcionario_Buscar;
    private javax.swing.JButton BT_Funcionario_Cadastrar;
    private javax.swing.JButton BT_Funcionario_Editar;
    private javax.swing.JButton BT_Funcionario_Remover;
    private javax.swing.JButton BT_Publicacao_Buscar;
    private javax.swing.JButton BT_Publicacao_Cadastrar;
    private javax.swing.JButton BT_Publicacao_Editar;
    private javax.swing.JButton BT_Publicacao_Remover;
    private javax.swing.JRadioButton CheckBox_Academico;
    private javax.swing.JRadioButton CheckBox_Autoajuda;
    private javax.swing.JRadioButton CheckBox_Literatura;
    private javax.swing.JRadioButton CheckBox_Outro;
    private javax.swing.JLabel Funcionario_Sal_Max;
    private javax.swing.JLabel Funcionario_Sal_Med;
    private javax.swing.JLabel Funcionario_Sal_Min;
    private javax.swing.JComboBox<String> Itens_Aluno_Centro;
    private javax.swing.JComboBox<String> Itens_Empres_Aluno;
    private javax.swing.JComboBox<String> Itens_Empres_Funcionario;
    private javax.swing.JComboBox<String> Itens_Empres_Publicacao;
    private javax.swing.JComboBox<String> Itens_Pub_Biblioteca;
    private javax.swing.JTable TB_Aluno;
    private javax.swing.JTable TB_Autor;
    private javax.swing.JTable TB_Bib_Centros;
    private javax.swing.JTable TB_Biblioteca;
    private javax.swing.JTable TB_Centro;
    private javax.swing.JTable TB_Emprestimo;
    private javax.swing.JTable TB_Funcionario;
    private javax.swing.JTable TB_Pub_Autores;
    private javax.swing.JTable TB_Publicacao;
    private javax.swing.JTextField TF_Aluno_Endereco;
    private javax.swing.JTextField TF_Aluno_Matricula;
    private javax.swing.JTextField TF_Aluno_Nome;
    private javax.swing.JTextField TF_Autor_Nome;
    private javax.swing.JTextField TF_Autor_Pais;
    private javax.swing.JTextField TF_Bib_Endereco;
    private javax.swing.JTextField TF_Bib_Nome;
    private javax.swing.JTextField TF_Bib_Sigla;
    private javax.swing.JTextField TF_Centro_Nome;
    private javax.swing.JTextField TF_Centro_Sigla;
    private javax.swing.JTextField TF_Empres_Data;
    private javax.swing.JTextField TF_Empres_Hora;
    private javax.swing.JTextField TF_Funci_Nome;
    private javax.swing.JTextField TF_Funci_Salario;
    private javax.swing.JTextField TF_Pub_Ano;
    private javax.swing.JTextField TF_Pub_Area;
    private javax.swing.JTextField TF_Pub_Assunto;
    private javax.swing.JTextField TF_Pub_Edicao;
    private javax.swing.JTextField TF_Pub_Genero;
    private javax.swing.JTextField TF_Pub_Nome;
    private javax.swing.JTextField TF_Pub_Tipo;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.ButtonGroup buttonGroup4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane1;
    // End of variables declaration//GEN-END:variables
}
