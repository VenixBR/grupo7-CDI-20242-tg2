package Entitys;

public class Pertence {

    private int fk_Cod_Biblioteca;
    private int fk_Cod_Centro;
    
    public Pertence(){}
    
    public Pertence(int cod_bib, int cod_centro){
        fk_Cod_Biblioteca = cod_bib;
        fk_Cod_Centro = cod_centro;
    }

    public int getFk_Cod_Biblioteca() {
        return fk_Cod_Biblioteca;
    }

    public int getFk_Cod_Centro() {
        return fk_Cod_Centro;
    }

    public void setFk_Cod_Biblioteca(int fk_Cod_Biblioteca) {
        this.fk_Cod_Biblioteca = fk_Cod_Biblioteca;
    }

    public void setFk_Cod_Centro(int fk_Cod_Centro) {
        this.fk_Cod_Centro = fk_Cod_Centro;
    }
    
}
