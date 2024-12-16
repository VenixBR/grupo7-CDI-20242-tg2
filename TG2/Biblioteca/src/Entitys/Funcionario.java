package Entitys;

public class Funcionario {

    private int Cod_Funcionario;
    private Float Salario;
    private String Nome;
    
    public Funcionario(){};
    
    public Funcionario(String Nome, Float Salario){
        this.Nome = Nome;
        this.Salario = Salario;
    }

    public int getCod_Funcionario() {
        return Cod_Funcionario;
    }
    
    public String getNome() {
        return Nome;
    }

    public Float getSalario() {
        return Salario;
    }

    public void setCod_Funcionario(int cod_Funcionario) {
        Cod_Funcionario = cod_Funcionario;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public void setSalario(Float salario) {
        Salario = salario;
    }
}