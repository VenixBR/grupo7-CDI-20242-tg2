package Exceptions;

public class CentroEdicaoIgual extends Exception{
    public String toString(){
        return "Os campos informados são iguais aos já cadastrados.";
    }
}
