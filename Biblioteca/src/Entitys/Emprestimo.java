package Entitys;

import java.sql.Time;

public class Emprestimo {

    private int Cod_Emprestimo;
    private java.sql.Date Data_;  // Certifique-se de que é java.sql.Date, não java.util.Date
    private java.sql.Time Hora;
    private int fk_Cod_Publicacao;
    private int fk_Matricula;
    private int fk_Cod_Funcionario;

    public int getCod_Emprestimo() {
        return Cod_Emprestimo;
    }

    public java.sql.Date getData() {
        return Data_;
    }

    public int getFk_Cod_Funcionario() {
        return fk_Cod_Funcionario;
    }

    public int getFk_Cod_Publicacao() {
        return fk_Cod_Publicacao;
    }

    public int getFk_Matricula() {
        return fk_Matricula;
    }

    public Time getHora() {
        return Hora;
    }

    public void setCod_Emprestimo(int cod_Emprestimo) {
        Cod_Emprestimo = cod_Emprestimo;
    }

    public void setData(java.sql.Date data) {
        this.Data_ = data;
    }

    public void setFk_Cod_Funcionario(int fk_Cod_Funcionario) {
        this.fk_Cod_Funcionario = fk_Cod_Funcionario;
    }

    public void setFk_Cod_Publicacao(int fk_Cod_Publicacao) {
        this.fk_Cod_Publicacao = fk_Cod_Publicacao;
    }

    public void setFk_Matricula(int fk_Matricula) {
        this.fk_Matricula = fk_Matricula;
    }

    public void setHora(Time hora) {
        Hora = hora;
    }
    
}
