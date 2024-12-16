package Exceptions;

public class LinhaNaoSelecionada extends Exception{
    public String toString(){
        return "Nao foi selecionada nenhuma linha para edicao.";
    }
}
