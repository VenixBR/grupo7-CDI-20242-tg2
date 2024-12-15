package Entitys;

import java.sql.Date;

public class Publicacao {

    private int Cod_Publicacao;
    private String Tipo;
    private Date Ano;
    private String Nome;
    private int fk_Cod_Biblioteca;

    public Date getAno() {
        return Ano;
    }

    public int getCod_Publicacao() {
        return Cod_Publicacao;
    }

    public int getFk_Cod_Biblioteca() {
        return fk_Cod_Biblioteca;
    }

    public String getNome() {
        return Nome;
    }

    public String getTipo() {
        return Tipo;
    }

    public void setAno(Date ano) { 
        this.Ano = ano;
    }

    public void setCod_Publicacao(int cod_Publicacao) {
        Cod_Publicacao = cod_Publicacao;
    }

    public void setFk_Cod_Biblioteca(int fk_Cod_Biblioteca) {
        this.fk_Cod_Biblioteca = fk_Cod_Biblioteca;
    }
    
    public void setNome(String nome) {
        Nome = nome;
    }

    public void setTipo(String tipo) {
        Tipo = tipo;
    }

}
