package Exceptions;

public class CentroLinhaNaoSelecionada extends Exception{
    public String toString(){
        return "Nao foi selecionada nenhuma linha para edicao.";
    }
}
