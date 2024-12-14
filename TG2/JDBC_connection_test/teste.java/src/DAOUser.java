import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import entitys.Biblioteca;

public class DAOUser {

    public void CadastrarBiblioteca(Biblioteca biblioteca){

        String SQL_command = "INSERT INTO Biblioteca (Cod_Biblioteca, Endereco, Nome, Sigla) VALUES (?, ?, ?, ?)";

        PreparedStatement ps = null;

        try {
            ps = conection.getConnection().prepareStatement(SQL_command);
            ps.setInt(1, biblioteca.getCod_Biblioteca());
            ps.setString(2,biblioteca.getEndereco());
            ps.setString(3, biblioteca.getNome());
            ps.setString(4, biblioteca.getSigla());

            ps.execute();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




    public Biblioteca BuscarBiblioteca(int Cod_Biblioteca){

        Biblioteca lib = new Biblioteca();
        String SQL_command = "SELECT * FROM Biblioteca WHERE Cod_Biblioteca=?";
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conection.getConnection().prepareStatement(SQL_command);
            ps.setInt(1, Cod_Biblioteca);
            rs = ps.executeQuery();

            if(rs.next()){
                lib = new Biblioteca();
                lib.setCod_Biblioteca(rs.getInt("Cod_Biblioteca"));
                lib.setEndereco(rs.getString("Endereco"));
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
    
    // Função para excluir a biblioteca pelo código
    public void ExcluirBiblioteca(int Cod_Biblioteca){
        String SQL_command = "DELETE FROM Biblioteca WHERE Cod_Biblioteca=?";
        PreparedStatement ps = null;

        try {
            ps = conection.getConnection().prepareStatement(SQL_command);
            ps.setInt(1, Cod_Biblioteca);

            int linhasAfetadas = ps.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Biblioteca excluída com sucesso!");
            } else {
                System.out.println("Nenhuma biblioteca encontrada para excluir.");
            }
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
