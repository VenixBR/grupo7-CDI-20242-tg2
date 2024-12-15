package Exceptions;

public class CentroSiglaGrande extends Exception{
    public String toString(){
        return "Sigla deve ter menos que 10 caracteres";
    }
}
