package Entitys;

public class Funcionario {

    private int Cod_Funcionario;
    private float Salario;
    private String Nome;

    public int getCod_Funcionario() {
        return Cod_Funcionario;
    }
    
    public String getNome() {
        return Nome;
    }

    public float getSalario() {
        return Salario;
    }

    public void setCod_Funcionario(int cod_Funcionario) {
        Cod_Funcionario = cod_Funcionario;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public void setSalario(float salario) {
        Salario = salario;
    }
    
}
