package Exceptions;

public class SiglaCentroGrande extends Exception{
    public String toString(){
        return "Sigla deve ter menos que 10 caracteres";
    }
}
