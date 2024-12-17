package Entitys;

public class Biblioteca {

    private int Cod_Biblioteca;
    private String Endereco;
    private String Nome;
    private String Sigla;
    
    public Biblioteca(){}
    
    public Biblioteca(String endereco, String nome, String sigla){
        Nome = nome;
        Endereco = endereco;
        Sigla = sigla;
    }

    public int getCod_Biblioteca() {
        return Cod_Biblioteca;
    }

    public String getEndereco() {
        return Endereco;
    }

    public String getNome() {
        return Nome;
    }

    public String getSigla() {
        return Sigla;
    }

    public void setCod_Biblioteca(int cod_Biblioteca) {
        Cod_Biblioteca = cod_Biblioteca;
    }

    public void setEndereco(String endereco) {
        Endereco = endereco;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public void setSigla(String sigla) {
        Sigla = sigla;
    }
}
