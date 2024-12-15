package Entitys;

public class Centro {
    
    private int Cod_Centro;
    private String Nome;
    private String Sigla;
    
    public Centro(){};

    public Centro(String Nome, String Sigla){
        this.Nome = Nome;
        this.Sigla = Sigla;
    }
            
    public int getCod_Centro() {
        return Cod_Centro;
    }

    public String getNome() {
        return Nome;
    }

    public String getSigla() {
        return Sigla;
    }

    public void setCod_Centro(int cod_centro) {
        Cod_Centro = cod_centro;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public void setSigla(String sigla) {
        Sigla = sigla;
    }
    
}
