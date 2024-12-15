/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package biblioteca;

import Entitys.Centro;
import Exceptions.NomeCentroGrande;
import Exceptions.NomeCentroVazio;
import Exceptions.SiglaCentroGrande;
import Exceptions.SiglaCentroVazio;
import biblioteca.DAOUser;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;
import java.sql.*;
import java.sql.SQLException;


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

	// Método para preencher a tabela com os centros
	private void carregarCentros() {
	    // Certifica-se de que a conexão foi estabelecida antes de usá-la
	    try {
	        // Conectar ao banco de dados, caso a conexão ainda não tenha sido feita
	        if (conn == null || conn.isClosed()) {
	            conectar();
	        }

	        // Query SQL para buscar os dados
	        String query = "SELECT * FROM Centro"; 
	        Statement stmt = conn.createStatement();
	        ResultSet rs = stmt.executeQuery(query);

	        // Atualiza a tabela com os dados recuperados
	        DefaultTableModel model = (DefaultTableModel) TB_Centro.getModel();
	        model.setRowCount(0);  // Limpa a tabela antes de adicionar novos dados

	        // Preenche a tabela com os resultados da consulta
	        while (rs.next()) {
	            // Adiciona os dados à tabela
	            Object[] row = { rs.getInt("cod_Centro"), rs.getString("sigla"), rs.getString("nome") };
	            model.addRow(row);
	        }

	        // Fecha os recursos (Statement e ResultSet)
	        rs.close();
	        stmt.close();

	    } catch (SQLException ex) {
	        JOptionPane.showMessageDialog(this, "Erro ao carregar dados: " + ex.getMessage());
	    }
	}

	// Método para fechar a conexão
	public void fecharConexao() throws SQLException {
	    if (conn != null && !conn.isClosed()) {
	        conn.close(); // Fecha a conexão ao banco de dados
	    }
	}

	// Método para preencher a tabela com os alunos
	private void carregarAlunos() {
	    // Certifica-se de que a conexão foi estabelecida antes de usá-la
	    try {
	        // Verifica e garante que a conexão com o banco de dados esteja aberta
	        if (conn == null || conn.isClosed()) {
	            conectar(); // Estabelece a conexão se ainda não foi feita
	        }

	        // Query SQL para buscar os dados dos alunos
	        String query = "SELECT * FROM Aluno"; 
	        Statement stmt = conn.createStatement();
	        ResultSet rs = stmt.executeQuery(query);

	        // Atualiza a tabela com os dados recuperados
	        DefaultTableModel model = (DefaultTableModel) jTable6.getModel();
	        model.setRowCount(0);  // Limpa a tabela antes de adicionar novos dados

	        // Preenche a tabela com os resultados da consulta
	        while (rs.next()) {
	            // Adiciona os dados de cada aluno à tabela
	            Object[] row = {
	                rs.getInt("Matricula"),  // Matricula do aluno
	                rs.getString("Nome"),     // Nome do aluno
	                rs.getInt("fk_Cod_Centro"), // Código do centro associado ao aluno
	                rs.getString("Endereco") // Endereço do aluno
	            };
	            model.addRow(row); // Adiciona a linha na tabela
	        }

	        // Fecha os recursos (Statement e ResultSet)
	        rs.close();
	        stmt.close();

	    } catch (SQLException ex) {
	        // Exibe a mensagem de erro caso ocorra
	        JOptionPane.showMessageDialog(this, "Erro ao carregar dados dos alunos: " + ex.getMessage());
	    }
	}
	
	private void carregarPublicacoes() {
	    // A consulta SQL agora faz joins com as tabelas relacionadas
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

	    // Declaração dos objetos fora do bloco try
	    Connection conn = null;
	    Statement stmt = null;
	    ResultSet rs = null;

	    try {
	        conn = SQL_connection.getConnection();  // Obtém a conexão do banco de dados
	        stmt = conn.createStatement();  // Cria o Statement
	        rs = stmt.executeQuery(query);  // Executa a consulta SQL

	        DefaultTableModel model = (DefaultTableModel) jTable5.getModel();
	        model.setRowCount(0);  // Limpa a tabela antes de adicionar novos dados

	        // Preenchendo a tabela com os resultados
	        while (rs.next()) {
	            Object[] row = {
	                rs.getInt("Cod_Publicacao"),  // Código da publicação
	                rs.getString("Nome"),  // Nome da publicação
	                rs.getInt("Ano"),  // Ano da publicação
	                rs.getString("Biblioteca"),  // Nome da biblioteca
	                rs.getString("Tipo"),  // Tipo de publicação
	                rs.getInt("Edicao"),  // Edição (de Academico)
	                rs.getString("Area"),  // Área (de Academico)
	                rs.getString("Genero_Textual"),  // Gênero textual (de Literatura)
	                rs.getString("Assunto"),  // Assunto (de Autoajuda)
	            };
	            model.addRow(row);  // Adiciona os dados à tabela
	        }
	    } catch (SQLException ex) {
	        JOptionPane.showMessageDialog(this, "Erro ao carregar dados: " + ex.getMessage());
	    } finally {
	        // Fechando o ResultSet e o Statement, mas NÃO fechando a conexão
	        try {
	            if (rs != null) {
	                rs.close();
	            }
	            if (stmt != null) {
	                stmt.close();
	            }
	        } catch (SQLException ex) {
	            ex.printStackTrace();  // Caso falhe ao fechar os recursos
	        }
	    }
	}

	private void carregarAutores() {
	    // Consulta SQL para buscar os autores diretamente da tabela
	    String query = "SELECT Cod_Autor, Nome, Pais FROM Autor";

	    // Declaração dos objetos fora do bloco try
	    Connection conn = null;
	    Statement stmt = null;
	    ResultSet rs = null;

	    try {
	        conn = SQL_connection.getConnection();  // Obtém a conexão com o banco de dados
	        stmt = conn.createStatement();  // Cria o Statement
	        rs = stmt.executeQuery(query);  // Executa a consulta SQL

	        DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
	        model.setRowCount(0);  // Limpa a tabela antes de adicionar novos dados

	        // Preenche a tabela com os dados dos autores
	        while (rs.next()) {
	            Object[] row = {
	                rs.getInt("Cod_Autor"),  // Código do autor
	                rs.getString("Nome"),     // Nome do autor
	                rs.getString("Pais")      // País do autor
	            };
	            model.addRow(row);  // Adiciona a linha à tabela
	        }
	    } catch (SQLException ex) {
	        JOptionPane.showMessageDialog(this, "Erro ao carregar dados: " + ex.getMessage());
	    } finally {
	        // Fechamento do ResultSet e do Statement, mas NÃO fechando a conexão
	        try {
	            if (rs != null) {
	                rs.close();
	            }
	            if (stmt != null) {
	                stmt.close();
	            }
	        } catch (SQLException ex) {
	            ex.printStackTrace();  // Caso falhe ao fechar os recursos
	        }
	    }
	}

	private void carregarBibliotecas() {
	    // Consulta SQL para buscar os dados das bibliotecas
	    String query = "SELECT Cod_Biblioteca, Nome, Endereco, Sigla FROM Biblioteca";

	    // Declaração dos objetos fora do bloco try
	    Connection conn = null;
	    Statement stmt = null;
	    ResultSet rs = null;

	    try {
	        conn = SQL_connection.getConnection();  // Obtém a conexão com o banco de dados
	        stmt = conn.createStatement();  // Cria o Statement
	        rs = stmt.executeQuery(query);  // Executa a consulta SQL

	        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
	        model.setRowCount(0);  // Limpa a tabela antes de adicionar novos dados

	        // Preenche a tabela com os dados das bibliotecas
	        while (rs.next()) {
	            Object[] row = {
	                rs.getInt("Cod_Biblioteca"),  // Código da biblioteca
                        rs.getString("Sigla"),         // Sigla da biblioteca
	                rs.getString("Nome"),         // Nome da biblioteca
	                rs.getString("Endereco")     // Endereço da biblioteca
	            };
	            model.addRow(row);  // Adiciona a linha à tabela
	        }
	    } catch (SQLException ex) {
	        JOptionPane.showMessageDialog(this, "Erro ao carregar dados: " + ex.getMessage());
	    } finally {
	        // Fechamento do ResultSet e do Statement, mas NÃO fechando a conexão
	        try {
	            if (rs != null) {
	                rs.close();
	            }
	            if (stmt != null) {
	                stmt.close();
	            }
	        } catch (SQLException ex) {
	            ex.printStackTrace();  // Caso falhe ao fechar os recursos
	        }
	    }
	}
	
	private void carregarFuncionarios() {
	    // Consulta SQL para buscar os dados dos funcionários
	    String query = "SELECT Cod_Funcionario, Nome, Salario FROM Funcionario";

	    // Declaração dos objetos fora do bloco try
	    Connection conn = null;
	    Statement stmt = null;
	    ResultSet rs = null;

	    try {
	        conn = SQL_connection.getConnection();  // Obtém a conexão com o banco de dados
	        stmt = conn.createStatement();  // Cria o Statement
	        rs = stmt.executeQuery(query);  // Executa a consulta SQL

	        DefaultTableModel model = (DefaultTableModel) jTable3.getModel();
	        model.setRowCount(0);  // Limpa a tabela antes de adicionar novos dados

	        // Preenche a tabela com os dados dos funcionários
	        while (rs.next()) {
	            Object[] row = {
	                rs.getInt("Cod_Funcionario"),  // Código do funcionário
	                rs.getString("Nome"),           // Nome do funcionário
	                rs.getBigDecimal("Salario")     // Salário do funcionário
	            };
	            model.addRow(row);  // Adiciona a linha à tabela
	        }
	    } catch (SQLException ex) {
	        JOptionPane.showMessageDialog(this, "Erro ao carregar dados: " + ex.getMessage());
	    } finally {
	        // Fechamento do ResultSet e do Statement, mas NÃO fechando a conexão
	        try {
	            if (rs != null) {
	                rs.close();
	            }
	            if (stmt != null) {
	                stmt.close();
	            }
	        } catch (SQLException ex) {
	            ex.printStackTrace();  // Caso falhe ao fechar os recursos
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

	    // Declaração dos objetos fora do bloco try
	    Connection conn = null;
	    Statement stmt = null;
	    ResultSet rs = null;

	    try {
	        conn = SQL_connection.getConnection();  // Obtém a conexão com o banco de dados
	        stmt = conn.createStatement();  // Cria o Statement
	        rs = stmt.executeQuery(query);  // Executa a consulta SQL

	        DefaultTableModel model = (DefaultTableModel) jTable4.getModel();
	        model.setRowCount(0);  // Limpa a tabela antes de adicionar novos dados

	        // Preenche a tabela com os dados dos empréstimos
	        while (rs.next()) {
	            Object[] row = {
	                rs.getInt("Cod_Emprestimo"),    // Código do empréstimo
	                rs.getDate("Data_"),            // Data do empréstimo
	                rs.getTime("Hora"),             // Hora do empréstimo
	                rs.getString("Funcionario"),    // Nome do funcionário que registrou o empréstimo
	                rs.getString("Publicacao"),     // Nome da publicação emprestada
	                rs.getString("Aluno")          // Nome do aluno que fez o empréstimo
	            };
	            model.addRow(row);  // Adiciona a linha à tabela
	        }
	    } catch (SQLException ex) {
	        JOptionPane.showMessageDialog(this, "Erro ao carregar dados: " + ex.getMessage());
	    } finally {
	        // Fechamento do ResultSet e do Statement, mas NÃO fechando a conexão
	        try {
	            if (rs != null) {
	                rs.close();
	            }
	            if (stmt != null) {
	                stmt.close();
	            }
	        } catch (SQLException ex) {
	            ex.printStackTrace();  // Caso falhe ao fechar os recursos
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
        carregarCentros(); // Chama o método para carregar os centros automaticamente
        carregarAlunos();
        carregarPublicacoes();  // Chama a função para carregar os dados de publicações
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
        jButton32 = new javax.swing.JButton();
        TF_Centro_Sigla = new javax.swing.JTextField();
        TF_Centro_Nome = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jButton33 = new javax.swing.JButton();
        Warning_Centro = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTable6 = new javax.swing.JTable();
        jTextField13 = new javax.swing.JTextField();
        jTextField14 = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jTextField15 = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jComboBox8 = new javax.swing.JComboBox<>();
        jLabel21 = new javax.swing.JLabel();
        jButton46 = new javax.swing.JButton();
        jButton47 = new javax.swing.JButton();
        jButton48 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable5 = new javax.swing.JTable();
        jComboBox6 = new javax.swing.JComboBox<>();
        jLabel15 = new javax.swing.JLabel();
        TF_Pub_Ano = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        TF_Pub_Nome = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        BT_Pub_Atualizar = new javax.swing.JButton();
        BT_Pub_Inserir = new javax.swing.JButton();
        jButton54 = new javax.swing.JButton();
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
        jPanel7 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jButton38 = new javax.swing.JButton();
        BT_Centro_Inserir3 = new javax.swing.JButton();
        jButton39 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextField9 = new javax.swing.JTextField();
        jLabel39 = new javax.swing.JLabel();
        jComboBox9 = new javax.swing.JComboBox<>();
        jLabel23 = new javax.swing.JLabel();
        jButton40 = new javax.swing.JButton();
        BT_Centro_Inserir4 = new javax.swing.JButton();
        jButton41 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        jTextField7 = new javax.swing.JTextField();
        jTextField8 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        jComboBox4 = new javax.swing.JComboBox<>();
        jButton34 = new javax.swing.JButton();
        BT_Centro_Inserir1 = new javax.swing.JButton();
        jButton35 = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jTextField5 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jButton36 = new javax.swing.JButton();
        BT_Centro_Inserir2 = new javax.swing.JButton();
        jButton37 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTabbedPane1.setPreferredSize(new java.awt.Dimension(1300, 640));

        TB_Centro.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Codigo", "Sigla", "Nome"
            }
        ));
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

        jButton32.setText("Remover");

        jLabel12.setText("Sigla:");

        jLabel13.setText("Nome:");

        jButton33.setText("Atualizar");

        Warning_Centro.setForeground(new java.awt.Color(255, 0, 51));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel13)
                                    .addComponent(jLabel12))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(TF_Centro_Sigla)
                                    .addComponent(TF_Centro_Nome))
                                .addGap(18, 18, 18))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(49, 49, 49)
                                .addComponent(BT_Centro_Cadastrar, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton33, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton32)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(Warning_Centro)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 831, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 599, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(Warning_Centro)
                .addGap(27, 27, 27)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TF_Centro_Nome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(TF_Centro_Sigla, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton33)
                    .addComponent(jButton32)
                    .addComponent(BT_Centro_Cadastrar))
                .addGap(19, 19, 19))
        );

        Warning_Centro.getAccessibleContext().setAccessibleName("Warning_Centro");

        jTabbedPane1.addTab("Centro", jPanel2);

        jTable6.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane6.setViewportView(jTable6);
        if (jTable6.getColumnModel().getColumnCount() > 0) {
            jTable6.getColumnModel().getColumn(0).setPreferredWidth(100);
            jTable6.getColumnModel().getColumn(1).setPreferredWidth(300);
            jTable6.getColumnModel().getColumn(2).setPreferredWidth(40);
            jTable6.getColumnModel().getColumn(3).setPreferredWidth(300);
        }

        jTextField13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField13ActionPerformed(evt);
            }
        });

        jLabel19.setText("Endereço:");

        jLabel20.setText("Nome:");

        jLabel22.setText("Matricula:");

        jComboBox8.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel21.setText("Centro:");

        jButton46.setText("Atualizar");

        jButton47.setText("Inserir");
        jButton47.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton47ActionPerformed(evt);
            }
        });

        jButton48.setText("Remover");

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
                            .addComponent(jTextField15)
                            .addComponent(jTextField14)
                            .addComponent(jTextField13)
                            .addComponent(jComboBox8, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton47, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton46, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton48)
                        .addGap(51, 51, 51)))
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
                    .addComponent(jTextField15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(jTextField13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(jComboBox8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton46)
                    .addComponent(jButton48)
                    .addComponent(jButton47))
                .addGap(19, 19, 19))
        );

        jTabbedPane1.addTab("Aluno", jPanel3);

        jTable5.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane5.setViewportView(jTable5);
        if (jTable5.getColumnModel().getColumnCount() > 0) {
            jTable5.getColumnModel().getColumn(0).setPreferredWidth(30);
            jTable5.getColumnModel().getColumn(1).setPreferredWidth(400);
            jTable5.getColumnModel().getColumn(2).setPreferredWidth(40);
            jTable5.getColumnModel().getColumn(3).setPreferredWidth(40);
            jTable5.getColumnModel().getColumn(4).setPreferredWidth(100);
        }

        jComboBox6.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel15.setText("Biblioteca:");

        jLabel16.setText("Ano:");

        jLabel18.setText("Tipo:");

        jLabel35.setText("Nome:");

        BT_Pub_Atualizar.setText("Atualizar");
        BT_Pub_Atualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BT_Pub_AtualizarActionPerformed(evt);
            }
        });

        BT_Pub_Inserir.setText("Inserir");
        BT_Pub_Inserir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BT_Pub_InserirActionPerformed(evt);
            }
        });

        jButton54.setText("Remover");

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
                                        .addComponent(jComboBox6, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(BT_Pub_Inserir, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(BT_Pub_Atualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton54)
                        .addGap(49, 49, 49)))
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
                    .addComponent(jComboBox6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                    .addComponent(BT_Pub_Atualizar)
                    .addComponent(jButton54)
                    .addComponent(BT_Pub_Inserir))
                .addGap(22, 22, 22))
        );

        jTabbedPane1.addTab("Publicação", jPanel4);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(jTable2);
        if (jTable2.getColumnModel().getColumnCount() > 0) {
            jTable2.getColumnModel().getColumn(0).setPreferredWidth(80);
            jTable2.getColumnModel().getColumn(1).setPreferredWidth(600);
            jTable2.getColumnModel().getColumn(2).setPreferredWidth(200);
        }

        jLabel3.setText("País:");

        jLabel4.setText("Nome:");

        jButton38.setText("Atualizar");

        BT_Centro_Inserir3.setText("Inserir");
        BT_Centro_Inserir3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BT_Centro_Inserir3ActionPerformed(evt);
            }
        });

        jButton39.setText("Remover");

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
                                .addComponent(jTextField3))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(27, 27, 27))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addComponent(BT_Centro_Inserir3, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton38, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton39)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 802, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton38)
                    .addComponent(jButton39)
                    .addComponent(BT_Centro_Inserir3))
                .addGap(17, 17, 17))
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 599, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Autor", jPanel7);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(1).setPreferredWidth(100);
            jTable1.getColumnModel().getColumn(2).setPreferredWidth(600);
            jTable1.getColumnModel().getColumn(3).setPreferredWidth(400);
        }

        jLabel1.setText("Endereço:");

        jLabel2.setText("Nome:");

        jTextField9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField9ActionPerformed(evt);
            }
        });

        jLabel39.setText("Sigla:");

        jComboBox9.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox9ActionPerformed(evt);
            }
        });

        jLabel23.setText("Centro:");

        jButton40.setText("Atualizar");

        BT_Centro_Inserir4.setText("Inserir");
        BT_Centro_Inserir4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BT_Centro_Inserir4ActionPerformed(evt);
            }
        });

        jButton41.setText("Remover");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(30, 30, 30)
                                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel39)
                                    .addComponent(jLabel23))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField1)
                                    .addComponent(jTextField9)
                                    .addComponent(jComboBox9, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addComponent(BT_Centro_Inserir4, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton40, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton41)))
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
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel39)
                    .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(jComboBox9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton40)
                    .addComponent(jButton41)
                    .addComponent(BT_Centro_Inserir4))
                .addGap(19, 19, 19))
        );

        jTabbedPane1.addTab("Biblioteca", jPanel1);

        jTable4.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane4.setViewportView(jTable4);
        if (jTable4.getColumnModel().getColumnCount() > 0) {
            jTable4.getColumnModel().getColumn(0).setPreferredWidth(40);
            jTable4.getColumnModel().getColumn(1).setPreferredWidth(80);
            jTable4.getColumnModel().getColumn(2).setPreferredWidth(60);
            jTable4.getColumnModel().getColumn(3).setPreferredWidth(180);
            jTable4.getColumnModel().getColumn(4).setPreferredWidth(200);
            jTable4.getColumnModel().getColumn(5).setPreferredWidth(200);
        }

        jLabel7.setText("Data:");

        jLabel8.setText("Hora:");

        jLabel9.setText("Aluno");

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel10.setText("Publicação:");

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel11.setText("Funcionário:");

        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jButton34.setText("Atualizar");

        BT_Centro_Inserir1.setText("Inserir");
        BT_Centro_Inserir1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BT_Centro_Inserir1ActionPerformed(evt);
            }
        });

        jButton35.setText("Remover");

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
                                    .addComponent(jComboBox2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jComboBox4, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel8))
                                .addGap(59, 59, 59)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField8)
                                    .addComponent(jTextField7)))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jComboBox3, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(18, 18, 18))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addComponent(BT_Centro_Inserir1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton34, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton35)
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
                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton34)
                    .addComponent(jButton35)
                    .addComponent(BT_Centro_Inserir1))
                .addGap(25, 25, 25))
        );

        jTabbedPane1.addTab("Emprestimo", jPanel5);

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(jTable3);
        if (jTable3.getColumnModel().getColumnCount() > 0) {
            jTable3.getColumnModel().getColumn(0).setPreferredWidth(80);
            jTable3.getColumnModel().getColumn(1).setPreferredWidth(800);
            jTable3.getColumnModel().getColumn(2).setPreferredWidth(200);
        }

        jLabel5.setText("Salário:");

        jLabel6.setText("Nome:");

        jButton36.setText("Atualizar");

        BT_Centro_Inserir2.setText("Inserir");
        BT_Centro_Inserir2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BT_Centro_Inserir2ActionPerformed(evt);
            }
        });

        jButton37.setText("Remover");

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
                                .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField5))))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addComponent(BT_Centro_Inserir2, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton36, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton37)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 819, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton36)
                    .addComponent(jButton37)
                    .addComponent(BT_Centro_Inserir2))
                .addGap(19, 19, 19))
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 599, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Funcionário", jPanel6);

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
        
        String nome = TF_Centro_Nome.getText();
        String sigla = TF_Centro_Sigla.getText();
        
        try{
            if(nome.isEmpty()){
                throw new NomeCentroVazio();
            }
            else if (sigla.isEmpty()){
                throw new SiglaCentroVazio();
            }
            else if (sigla.length()>10){
                throw new SiglaCentroGrande();
            }
            else if (nome.length()>50){
                throw new NomeCentroGrande();
            }
            
            
            new DAOUser().CadastrarCentro(new Centro(nome, sigla));
       
        
            int id = new DAOUser().ReturnLastCod("Centro");
        
            Centro temp = new Centro();
            temp = new DAOUser().BuscarCentro(id); 
        
            DefaultTableModel Table_Centro = (DefaultTableModel)TB_Centro.getModel();
            Object[] data = {temp.getCod_Centro(),  temp.getSigla(), temp.getNome()};
            Table_Centro.addRow(data);
        }
        catch(SiglaCentroGrande e){
            Warning_Centro.setText("Sigla pode ter no máximo 10 caráç");
        }
        catch(NomeCentroGrande e){
            Warning_Centro.setText("Nome pode ter no máximo 50 caractéres");
        }
        catch(NomeCentroVazio e){
            Warning_Centro.setText("Nome nao informado");
        }
        catch(SiglaCentroVazio e){
            Warning_Centro.setText("Sigla nao informada");
        }
        
            
              
    }//GEN-LAST:event_BT_Centro_CadastrarActionPerformed

    private void jTextField13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField13ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField13ActionPerformed

    private void jButton47ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton47ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton47ActionPerformed

    private void BT_Pub_InserirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BT_Pub_InserirActionPerformed

    }//GEN-LAST:event_BT_Pub_InserirActionPerformed

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

    private void BT_Pub_AtualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BT_Pub_AtualizarActionPerformed

    }//GEN-LAST:event_BT_Pub_AtualizarActionPerformed

    private void TF_Pub_GeneroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TF_Pub_GeneroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TF_Pub_GeneroActionPerformed

    private void BT_Centro_Inserir1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BT_Centro_Inserir1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BT_Centro_Inserir1ActionPerformed

    private void BT_Centro_Inserir2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BT_Centro_Inserir2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BT_Centro_Inserir2ActionPerformed

    private void BT_Centro_Inserir3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BT_Centro_Inserir3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BT_Centro_Inserir3ActionPerformed

    private void jTextField9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField9ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField9ActionPerformed

    private void jComboBox9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox9ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox9ActionPerformed

    private void BT_Centro_Inserir4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BT_Centro_Inserir4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BT_Centro_Inserir4ActionPerformed

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
    private javax.swing.JButton BT_Centro_Cadastrar;
    private javax.swing.JButton BT_Centro_Inserir1;
    private javax.swing.JButton BT_Centro_Inserir2;
    private javax.swing.JButton BT_Centro_Inserir3;
    private javax.swing.JButton BT_Centro_Inserir4;
    private javax.swing.JButton BT_Pub_Atualizar;
    private javax.swing.JButton BT_Pub_Inserir;
    private javax.swing.JRadioButton CheckBox_Academico;
    private javax.swing.JRadioButton CheckBox_Autoajuda;
    private javax.swing.JRadioButton CheckBox_Literatura;
    private javax.swing.JRadioButton CheckBox_Outro;
    private javax.swing.JTable TB_Centro;
    private javax.swing.JTextField TF_Centro_Nome;
    private javax.swing.JTextField TF_Centro_Sigla;
    private javax.swing.JTextField TF_Pub_Ano;
    private javax.swing.JTextField TF_Pub_Area;
    private javax.swing.JTextField TF_Pub_Assunto;
    private javax.swing.JTextField TF_Pub_Edicao;
    private javax.swing.JTextField TF_Pub_Genero;
    private javax.swing.JTextField TF_Pub_Nome;
    private javax.swing.JTextField TF_Pub_Tipo;
    private javax.swing.JLabel Warning_Centro;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton32;
    private javax.swing.JButton jButton33;
    private javax.swing.JButton jButton34;
    private javax.swing.JButton jButton35;
    private javax.swing.JButton jButton36;
    private javax.swing.JButton jButton37;
    private javax.swing.JButton jButton38;
    private javax.swing.JButton jButton39;
    private javax.swing.JButton jButton40;
    private javax.swing.JButton jButton41;
    private javax.swing.JButton jButton46;
    private javax.swing.JButton jButton47;
    private javax.swing.JButton jButton48;
    private javax.swing.JButton jButton54;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JComboBox<String> jComboBox6;
    private javax.swing.JComboBox<String> jComboBox8;
    private javax.swing.JComboBox<String> jComboBox9;
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
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTable4;
    private javax.swing.JTable jTable5;
    private javax.swing.JTable jTable6;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField13;
    private javax.swing.JTextField jTextField14;
    private javax.swing.JTextField jTextField15;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    // End of variables declaration//GEN-END:variables
}
