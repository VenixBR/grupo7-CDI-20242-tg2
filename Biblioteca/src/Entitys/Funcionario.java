package Entitys;

import java.math.BigDecimal;

public class Funcionario {

    private int Cod_Funcionario;
    private BigDecimal Salario;
    private String Nome;

    public int getCod_Funcionario() {
        return Cod_Funcionario;
    }
    
    public String getNome() {
        return Nome;
    }

    public BigDecimal getSalario() {
        return Salario;
    }

    public void setCod_Funcionario(int cod_Funcionario) {
        Cod_Funcionario = cod_Funcionario;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public void setSalario(BigDecimal salario) {
        Salario = salario;
    }
}