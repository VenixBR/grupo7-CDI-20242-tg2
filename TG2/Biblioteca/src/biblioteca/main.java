/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package biblioteca;

import DAOUser.DAO_Centro;
import Entitys.Centro;
import Exceptions.CentroNomeGrande;
import Exceptions.CentroSiglaGrande;
import DAOUser.DAO_General;
import Exceptions.CentroCamposNaoInformados;
import Exceptions.CentroEdicaoIgual;
import Exceptions.CentroLinhaNaoSelecionada;
import Exceptions.CentroSiglaUsada;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
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
	        model.setRowCount(0);  // Limpa a tabela antes de adicionar novos dados

	        while (rs.next()) {
	            Object[] row = { rs.getInt("cod_Centro"), rs.getString("sigla"), rs.getString("nome") };
	            model.addRow(row);
                    Itens_Aluno_Centro.addItem(rs.getString("Sigla"));
                    Itens_Bib_Centro.addItem(rs.getString("Sigla"));
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
	        String query = "SELECT * FROM Aluno"; 
	        Statement stmt = conn.createStatement();
	        ResultSet rs = stmt.executeQuery(query);

	        DefaultTableModel model = (DefaultTableModel) TB_Aluno.getModel();
	        model.setRowCount(0); 

	        while (rs.next()) {
	            Object[] row = {
	                rs.getInt("Matricula"),
	                rs.getString("Nome"),
	                rs.getInt("fk_Cod_Centro"),
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
	private void carregarAlunosNome(String nome) {
	    try {
	        if (conn == null || conn.isClosed()) {
	            conectar();
	        }

	        String query = "SELECT * FROM Aluno WHERE Nome=?"; 
                PreparedStatement ps = null;
                ps = SQL_connection.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, nome);
                ResultSet rs = ps.executeQuery();

	        DefaultTableModel model = (DefaultTableModel) TB_Centro.getModel();
	        model.setRowCount(0);  // Limpa a tabela antes de adicionar novos dados

	        while (rs.next()) {
	            Object[] row = { rs.getInt("cod_Centro"), rs.getString("sigla"), rs.getString("nome") };
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
	        model.setRowCount(0);  

	        while (rs.next()) {
	            Object[] row = {
	                rs.getInt("Cod_Autor"),
	                rs.getString("Nome"),
	                rs.getString("Pais")
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
	                rs.getBigDecimal("Salario")
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
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TB_Biblioteca = new javax.swing.JTable();
        TF_Bib_Endereco = new javax.swing.JTextField();
        TF_Bib_Nome = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        TF_Bib_Sigla = new javax.swing.JTextField();
        jLabel39 = new javax.swing.JLabel();
        Itens_Bib_Centro = new javax.swing.JComboBox<>();
        jLabel23 = new javax.swing.JLabel();
        BT_Biblioteca_Buscar = new javax.swing.JButton();
        BT_Biblioteca_Cadastrar = new javax.swing.JButton();
        BT_Biblioteca_Remover = new javax.swing.JButton();
        BT_Biblioteca_Editar = new javax.swing.JButton();
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
                            .addComponent(TF_Centro_Sigla, javax.swing.GroupLayout.DEFAULT_SIZE, 307, Short.MAX_VALUE)
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
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 842, Short.MAX_VALUE)
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
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(TF_Aluno_Matricula)
                            .addComponent(TF_Aluno_Nome)
                            .addComponent(TF_Aluno_Endereco)
                            .addComponent(Itens_Aluno_Centro, 0, 266, Short.MAX_VALUE))
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
                    .addComponent(jLabel22)
                    .addComponent(TF_Aluno_Matricula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TF_Aluno_Nome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20))
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
        jScrollPane5.setViewportView(TB_Publicacao);
        if (TB_Publicacao.getColumnModel().getColumnCount() > 0) {
            TB_Publicacao.getColumnModel().getColumn(0).setPreferredWidth(30);
            TB_Publicacao.getColumnModel().getColumn(1).setPreferredWidth(400);
            TB_Publicacao.getColumnModel().getColumn(2).setPreferredWidth(40);
            TB_Publicacao.getColumnModel().getColumn(3).setPreferredWidth(40);
            TB_Publicacao.getColumnModel().getColumn(4).setPreferredWidth(100);
        }

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

        TF_Pub_Area.setEditable(false);

        jLabel17.setText("Area:");

        TF_Pub_Edicao.setEditable(false);

        jLabel36.setText("Ediçao:");

        jLabel37.setText("Genero:");

        TF_Pub_Genero.setEditable(false);
        TF_Pub_Genero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TF_Pub_GeneroActionPerformed(evt);
            }
        });

        jLabel38.setText("Assunto:");

        TF_Pub_Tipo.setEditable(false);

        TF_Pub_Assunto.setEditable(false);

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

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel38)
                                    .addComponent(CheckBox_Autoajuda))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(TF_Pub_Assunto))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addGap(84, 84, 84)
                                .addComponent(TF_Pub_Area))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel16)
                                            .addComponent(jLabel35))
                                        .addGap(36, 36, 36)
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(TF_Pub_Nome, javax.swing.GroupLayout.DEFAULT_SIZE, 309, Short.MAX_VALUE)
                                            .addComponent(TF_Pub_Ano)))
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(jLabel15)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(Itens_Pub_Biblioteca, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel18)
                                    .addComponent(CheckBox_Academico)
                                    .addComponent(CheckBox_Literatura)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(CheckBox_Outro)
                                        .addGap(18, 18, 18)
                                        .addComponent(TF_Pub_Tipo)))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel17)
                                        .addGroup(jPanel4Layout.createSequentialGroup()
                                            .addComponent(jLabel36)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(TF_Pub_Edicao, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                        .addComponent(jLabel37)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(TF_Pub_Genero, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(18, 18, 18))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(BT_Publicacao_Cadastrar, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(BT_Publicacao_Buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(BT_Publicacao_Editar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(BT_Publicacao_Remover)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
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
                .addGap(14, 14, 14)
                .addComponent(CheckBox_Literatura)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel37)
                    .addComponent(TF_Pub_Genero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(CheckBox_Autoajuda)
                .addGap(2, 2, 2)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel38)
                    .addComponent(TF_Pub_Assunto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(TF_Pub_Tipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CheckBox_Outro))
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

        Itens_Bib_Centro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Itens_Bib_CentroActionPerformed(evt);
            }
        });

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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel2)
                            .addGap(30, 30, 30)
                            .addComponent(TF_Bib_Nome, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel1)
                                .addComponent(jLabel39)
                                .addComponent(jLabel23))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(TF_Bib_Endereco)
                                .addComponent(TF_Bib_Sigla)
                                .addComponent(Itens_Bib_Centro, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(BT_Biblioteca_Cadastrar, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(BT_Biblioteca_Buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(BT_Biblioteca_Editar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(BT_Biblioteca_Remover)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 824, Short.MAX_VALUE)
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
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(Itens_Bib_Centro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
        jScrollPane4.setViewportView(TB_Emprestimo);
        if (TB_Emprestimo.getColumnModel().getColumnCount() > 0) {
            TB_Emprestimo.getColumnModel().getColumn(0).setPreferredWidth(40);
            TB_Emprestimo.getColumnModel().getColumn(1).setPreferredWidth(80);
            TB_Emprestimo.getColumnModel().getColumn(2).setPreferredWidth(60);
            TB_Emprestimo.getColumnModel().getColumn(3).setPreferredWidth(180);
            TB_Emprestimo.getColumnModel().getColumn(4).setPreferredWidth(200);
            TB_Emprestimo.getColumnModel().getColumn(5).setPreferredWidth(200);
        }

        jLabel7.setText("Data:");

        jLabel8.setText("Hora:");

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
                                    .addComponent(Itens_Empres_Aluno, 0, 246, Short.MAX_VALUE)
                                    .addComponent(Itens_Empres_Publicacao, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel8))
                                .addGap(59, 59, 59)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(TF_Empres_Hora)
                                    .addComponent(TF_Empres_Data)))
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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 846, Short.MAX_VALUE)
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
                                .addComponent(BT_Funcionario_Remover)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 819, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
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
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 802, Short.MAX_VALUE)
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
                throw new CentroCamposNaoInformados();
            }
            else if (sigla.length()>10){
                throw new CentroSiglaGrande();
            }
            else if (nome.length()>50){
                throw new CentroNomeGrande();
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
            Itens_Bib_Centro.addItem(sigla);
            
            TF_Centro_Nome.setText("");
            TF_Centro_Sigla.setText("");
            
            JOptionPane.showMessageDialog(null, "Centro cadastrado com sucesso!", 
                                              "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        }
        catch(CentroSiglaGrande e){
            JOptionPane.showMessageDialog(null, "A sigla pode ter no máximo 10 caracteres!", 
                                                  "Erro", JOptionPane.ERROR_MESSAGE);
        }
        catch(CentroNomeGrande e){
            JOptionPane.showMessageDialog(null, "O nome pode ter no máximo 50 caracteres!", 
                                                  "Erro", JOptionPane.ERROR_MESSAGE);
        }
        catch(CentroCamposNaoInformados e){
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

    private void Itens_Bib_CentroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Itens_Bib_CentroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Itens_Bib_CentroActionPerformed

    private void BT_Centro_RemoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BT_Centro_RemoverActionPerformed
       
        //Testa se tem alguma linha selecionada
        if(TB_Centro.getSelectedRow() != -1){
        
            //Pega o codigo da linha selecionada
            int id = (int) TB_Centro.getValueAt(TB_Centro.getSelectedRow(), 0);
            
            DefaultTableModel Table_Centro = (DefaultTableModel)TB_Centro.getModel();
            Table_Centro.removeRow(TB_Centro.getSelectedRow());
           
            new DAO_Centro().RemoverCentro(id);
            
            String nome = TF_Centro_Nome.getText();
            TF_Centro_Nome.setText("");
            TF_Centro_Sigla.setText("");
            Itens_Aluno_Centro.removeItem(nome);
            Itens_Bib_Centro.removeItem(nome);
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
            //System.out.println(TB_Centro.getSelectedRow());
            if(TB_Centro.getSelectedRow()==-1){
                throw new CentroLinhaNaoSelecionada();
            }
            else{
                String nomeNew = TF_Centro_Nome.getText();
                String siglaNew = TF_Centro_Sigla.getText();
                String nomeOld = (String) TB_Centro.getValueAt(TB_Centro.getSelectedRow(),2);
                String siglaOld = (String) TB_Centro.getValueAt(TB_Centro.getSelectedRow(),1);
                int codigo = (int) TB_Centro.getValueAt(TB_Centro.getSelectedRow(),0);
                
                //Testa se tem alguma informação nos campos
                if(nomeNew.isEmpty() && siglaNew.isEmpty()){
                    System.out.println("oi");
                    throw new CentroCamposNaoInformados();
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
        catch(CentroCamposNaoInformados e){
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
        catch(CentroLinhaNaoSelecionada e){
            JOptionPane.showMessageDialog(null, "Por favor, selecione alguma linha para realizar a edição!", 
                                                  "Erro", JOptionPane.ERROR_MESSAGE);
        }
        
        
        
        
    }//GEN-LAST:event_BT_Centro_EditarActionPerformed

    private void BT_Aluno_EditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BT_Aluno_EditarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BT_Aluno_EditarActionPerformed

    private void BT_Aluno_BuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BT_Aluno_BuscarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BT_Aluno_BuscarActionPerformed

    private void BT_Aluno_CadastrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BT_Aluno_CadastrarActionPerformed
       
        
        
    }//GEN-LAST:event_BT_Aluno_CadastrarActionPerformed

    private void BT_Aluno_RemoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BT_Aluno_RemoverActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BT_Aluno_RemoverActionPerformed

    private void BT_Publicacao_EditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BT_Publicacao_EditarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BT_Publicacao_EditarActionPerformed

    private void BT_Publicacao_BuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BT_Publicacao_BuscarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BT_Publicacao_BuscarActionPerformed

    private void BT_Publicacao_CadastrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BT_Publicacao_CadastrarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BT_Publicacao_CadastrarActionPerformed

    private void BT_Publicacao_RemoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BT_Publicacao_RemoverActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BT_Publicacao_RemoverActionPerformed

    private void BT_Biblioteca_BuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BT_Biblioteca_BuscarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BT_Biblioteca_BuscarActionPerformed

    private void BT_Biblioteca_CadastrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BT_Biblioteca_CadastrarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BT_Biblioteca_CadastrarActionPerformed

    private void BT_Biblioteca_RemoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BT_Biblioteca_RemoverActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BT_Biblioteca_RemoverActionPerformed

    private void BT_Biblioteca_EditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BT_Biblioteca_EditarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BT_Biblioteca_EditarActionPerformed

    private void BT_Emprestimo_BuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BT_Emprestimo_BuscarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BT_Emprestimo_BuscarActionPerformed

    private void BT_Emprestimo_CadastrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BT_Emprestimo_CadastrarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BT_Emprestimo_CadastrarActionPerformed

    private void BT_Emprestimo_RemoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BT_Emprestimo_RemoverActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BT_Emprestimo_RemoverActionPerformed

    private void BT_Emprestimo_EditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BT_Emprestimo_EditarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BT_Emprestimo_EditarActionPerformed

    private void BT_Funcionario_BuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BT_Funcionario_BuscarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BT_Funcionario_BuscarActionPerformed

    private void BT_Funcionario_CadastrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BT_Funcionario_CadastrarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BT_Funcionario_CadastrarActionPerformed

    private void BT_Funcionario_RemoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BT_Funcionario_RemoverActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BT_Funcionario_RemoverActionPerformed

    private void BT_Funcionario_EditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BT_Funcionario_EditarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BT_Funcionario_EditarActionPerformed

    private void BT_Autor_BuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BT_Autor_BuscarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BT_Autor_BuscarActionPerformed

    private void BT_Autor_CadastrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BT_Autor_CadastrarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BT_Autor_CadastrarActionPerformed

    private void BT_Autor_RemoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BT_Autor_RemoverActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BT_Autor_RemoverActionPerformed

    private void BT_Autor_EditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BT_Autor_EditarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BT_Autor_EditarActionPerformed

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
    private javax.swing.JComboBox<String> Itens_Aluno_Centro;
    private javax.swing.JComboBox<String> Itens_Bib_Centro;
    private javax.swing.JComboBox<String> Itens_Empres_Aluno;
    private javax.swing.JComboBox<String> Itens_Empres_Funcionario;
    private javax.swing.JComboBox<String> Itens_Empres_Publicacao;
    private javax.swing.JComboBox<String> Itens_Pub_Biblioteca;
    private javax.swing.JTable TB_Aluno;
    private javax.swing.JTable TB_Autor;
    private javax.swing.JTable TB_Biblioteca;
    private javax.swing.JTable TB_Centro;
    private javax.swing.JTable TB_Emprestimo;
    private javax.swing.JTable TB_Funcionario;
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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
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
    private javax.swing.JTabbedPane jTabbedPane1;
    // End of variables declaration//GEN-END:variables
}
