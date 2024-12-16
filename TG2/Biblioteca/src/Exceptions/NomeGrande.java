package Exceptions;

public class NomeGrande extends Exception{
    public String toString(){
        return "Nome deve ter menos que 50 caracteres";
    }
}
