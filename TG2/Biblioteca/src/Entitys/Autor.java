package Entitys;

public class Autor {
    
    private int Cod_Autor;
    private String Nome;
    private String Pais;
    
    public Autor(){};
    
    public Autor(String Nome, String Pais){
        this.Nome = Nome;
        this.Pais = Pais;
    }

    public int getCod_Autor() {
        return Cod_Autor;
    }

    public String getNome() {
        return Nome;
    }

    public String getPais() {
        return Pais;
    }

    public void setCod_Autor(int cod_Autor) {
        Cod_Autor = cod_Autor;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public void setPais(String pais) {
        Pais = pais;
    }

}
