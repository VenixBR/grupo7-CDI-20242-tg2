package DAOUser;

import Entitys.Academico;
import Entitys.Autoajuda;
import Entitys.Escrito;
import Entitys.Literatura;
import Entitys.Pertence;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

import Entitys.Publicacao;
import biblioteca.SQL_connection;
import java.sql.Statement;

public class DAO_Publicacao {

	public void CadastrarPublicacao(Publicacao publicacao) {
	    String SQL_command = "INSERT INTO Publicacao (Cod_Publicacao, Tipo, Ano, Nome, fk_Cod_Biblioteca) VALUES (?, ?, ?, ?, ?)";

	    PreparedStatement ps = null;
            System.out.println("ola");

	    try {
	        ps = SQL_connection.getConnection().prepareStatement(SQL_command);
	        ps.setInt(1, publicacao.getCod_Publicacao());
	        ps.setString(2, publicacao.getTipo());
	        ps.setInt(3, publicacao.getAno());
	        ps.setString(4, publicacao.getNome());
	        ps.setInt(5, publicacao.getFk_Cod_Biblioteca());

	        ps.execute();
	        ps.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

        public void EditarNome(int codigo, String nome){
        
        String SQL_command = "UPDATE Publicacao SET Nome=? where Cod_Publicacao=?";
	PreparedStatement ps = null;

	try {
	    ps = SQL_connection.getConnection().prepareStatement(SQL_command, Statement.RETURN_GENERATED_KEYS);
	    ps.setString(1, nome);
	    ps.setInt(2, codigo);

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
    
        public void EditarAno(int codigo, int ano){
        
        String SQL_command = "UPDATE Publicacao SET Ano=? where Cod_Publicacao=?";
	PreparedStatement ps = null;

	try {
	    ps = SQL_connection.getConnection().prepareStatement(SQL_command, Statement.RETURN_GENERATED_KEYS);
	    ps.setInt(1, ano);
	    ps.setInt(2, codigo);

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
        
        public void EditarBiblioteca(int codigo, int ano){
        
        String SQL_command = "UPDATE Publicacao SET fk_Cod_Biblioteca=? where Cod_Publicacao=?";
	PreparedStatement ps = null;

	try {
	    ps = SQL_connection.getConnection().prepareStatement(SQL_command, Statement.RETURN_GENERATED_KEYS);
	    ps.setInt(1, ano);
	    ps.setInt(2, codigo);

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
        
        public void EditarEdicao(int codigo, int edicao){
        
        String SQL_command = "UPDATE Academico SET Edicao=? where fk_Cod_Publicacao=?";
	PreparedStatement ps = null;

	try {
	    ps = SQL_connection.getConnection().prepareStatement(SQL_command, Statement.RETURN_GENERATED_KEYS);
	    ps.setInt(1, edicao);
	    ps.setInt(2, codigo);

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
        
        public void EditarArea(int codigo, String area){
        
        String SQL_command = "UPDATE Academico SET Area=? where fk_Cod_Publicacao=?";
	PreparedStatement ps = null;

	try {
	    ps = SQL_connection.getConnection().prepareStatement(SQL_command, Statement.RETURN_GENERATED_KEYS);
	    ps.setString(1, area);
	    ps.setInt(2, codigo);

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
        
        public void EditarGenero(int codigo, String genero){
        
        String SQL_command = "UPDATE Literatura SET Genero=? where fk_Cod_Publicacao=?";
	PreparedStatement ps = null;

	try {
	    ps = SQL_connection.getConnection().prepareStatement(SQL_command, Statement.RETURN_GENERATED_KEYS);
	    ps.setString(1, genero);
	    ps.setInt(2, codigo);

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
        
        public void EditarAssunto(int codigo, String assunto){
        
        String SQL_command = "UPDATE Autoajuda SET Assunto=? where fk_Cod_Publicacao=?";
	PreparedStatement ps = null;

	try {
	    ps = SQL_connection.getConnection().prepareStatement(SQL_command, Statement.RETURN_GENERATED_KEYS);
	    ps.setString(1, assunto);
	    ps.setInt(2, codigo);

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
        
        public void EditarTipo(int codigo, String tipo){
        
        String SQL_command = "UPDATE Publicacao SET Tipo=? where Cod_Publicacao=?";
	PreparedStatement ps = null;

	try {
	    ps = SQL_connection.getConnection().prepareStatement(SQL_command, Statement.RETURN_GENERATED_KEYS);
	    ps.setString(1, tipo);
	    ps.setInt(2, codigo);

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
        
        public static int PullBiblioteca(String sigla){
        
        int result = -1;
        String SQL_command = "SELECT Cod_Biblioteca AS Codigo FROM Biblioteca WHERE Sigla=?";
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

        public void CadastrarAcademico(Academico academico) {
	    String SQL_command = "INSERT INTO Academico (fk_Cod_Publicacao, Edicao, Area) VALUES (?, ?, ?)";

	    PreparedStatement ps = null;

	    try {
	        ps = SQL_connection.getConnection().prepareStatement(SQL_command);
	        ps.setInt(1, academico.getFk_Cod_Publicacao());
	        ps.setInt(2, academico.getEdicao());
	        ps.setString(3, academico.getArea());

	        ps.execute();
	        ps.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
        
        public void CadastrarLiteratura(Literatura literatura) {
	    String SQL_command = "INSERT INTO Literatura (fk_Cod_Publicacao, Genero_Textual) VALUES (?, ?)";

	    PreparedStatement ps = null;
            

	    try {
	        ps = SQL_connection.getConnection().prepareStatement(SQL_command);
	        ps.setInt(1, literatura.getFk_Cod_Publicacao());
	        ps.setString(2, literatura.getGenero_Textual());
	        ps.execute();
	        ps.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

        public void CadastrarAutoajuda(Autoajuda autoajuda) {
	    String SQL_command = "INSERT INTO Autoajuda (fk_Cod_Publicacao, Assunto) VALUES (?, ?)";

	    PreparedStatement ps = null;
            System.out.println("ola");

	    try {
	        ps = SQL_connection.getConnection().prepareStatement(SQL_command);
	        ps.setInt(1, autoajuda.getFk_Cod_Publicacao());
	        ps.setString(2, autoajuda.getAssunto());

	        ps.execute();
	        ps.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

        public void CadastrarEscrito(Escrito escrito) {
	    String SQL_command = "INSERT INTO Escrito (fk_Cod_Publicacao, fk_Cod_Autor) VALUES (?, ?)";

	    PreparedStatement ps = null;
            System.out.println("ola");

	    try {
	        ps = SQL_connection.getConnection().prepareStatement(SQL_command);
	        ps.setInt(1, escrito.getFk_Cod_Publicacao());
	        ps.setInt(2, escrito.getFk_Cod_Autor());

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
                pub.setAno(rs.getInt("Ano"));
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
    
        // Função para remover uma publicação pelo código
        public void RemoverAcademico(int Cod_Publicacao) {
        String SQL_command = "DELETE FROM Academico WHERE fk_Cod_Publicacao=?";
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

        // Função para remover uma publicação pelo código
        public void RemoverLiteratura(int Cod_Publicacao) {
        String SQL_command = "DELETE FROM Literatura WHERE fk_Cod_Publicacao=?";
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

        // Função para remover uma publicação pelo código
        public void RemoverAutoajuda(int Cod_Publicacao) {
        String SQL_command = "DELETE FROM Autoajuda WHERE fk_Cod_Publicacao=?";
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

        public Academico BuscarAcademico(int Cod_Publicacao) {
        Academico pub = null;
        String SQL_command = "SELECT * FROM Academico WHERE fk_Cod_Publicacao=?";
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = SQL_connection.getConnection().prepareStatement(SQL_command);
            ps.setInt(1, Cod_Publicacao);
            rs = ps.executeQuery();

            if (rs.next()) {
                pub = new Academico(); 
                pub.setFk_Cod_Publicacao(rs.getInt("fk_Cod_Publicacao"));
                pub.setEdicao(rs.getInt("Edicao"));
                pub.setArea(rs.getString("Area"));
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
    
        public String BuscarLiteratura(int Cod_Publicacao) {
        String result=null;
        String SQL_command = "SELECT * FROM Literatura WHERE fk_Cod_Publicacao=?";
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = SQL_connection.getConnection().prepareStatement(SQL_command);
            ps.setInt(1, Cod_Publicacao);
            rs = ps.executeQuery();

            if (rs.next()) {

                result = rs.getString("Genero_Textual");
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
    
        public String BuscarAutoajuda(int Cod_Publicacao) {
        String result=null;
        String SQL_command = "SELECT * FROM Autoajuda WHERE fk_Cod_Publicacao=?";
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = SQL_connection.getConnection().prepareStatement(SQL_command);
            ps.setInt(1, Cod_Publicacao);
            rs = ps.executeQuery();

            if (rs.next()) {

                result = rs.getString("Assunto");
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