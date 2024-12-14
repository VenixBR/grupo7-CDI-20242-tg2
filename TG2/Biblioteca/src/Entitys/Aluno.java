package Entitys;

public class Aluno {
    
    private int Matricula;
    private String Endereco;
    private String Nome;
    private int fk_Cod_Centro;

    public String getEndereco() {
        return Endereco;
    }

    public int getFk_Cod_Centro() {
        return fk_Cod_Centro;
    }

    public int getMatricula() {
        return Matricula;
    }

    public String getNome() {
        return Nome;
    }

    public void setEndereco(String endereco) {
        Endereco = endereco;
    }

    public void setFk_Cod_Centro(int fk_Cod_Centro) {
        this.fk_Cod_Centro = fk_Cod_Centro;
    }
    public void setMatricula(int matricula) {
        Matricula = matricula;
    }
    
    public void setNome(String nome) {
        Nome = nome;
    }

}
