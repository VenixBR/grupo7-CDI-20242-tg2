package Entitys;

public class Literatura {

    private String Genero_Textual;
    private int fk_Cod_Publicacao;
    
    public Literatura(){}
    
    public Literatura(String genero, int cod_pub){
        Genero_Textual = genero;
        fk_Cod_Publicacao = cod_pub;
    }

    public int getFk_Cod_Publicacao() {
        return fk_Cod_Publicacao;
    }

    public String getGenero_Textual() {
        return Genero_Textual;
    }

    public void setFk_Cod_Publicacao(int fk_Cod_Publicacao) {
        this.fk_Cod_Publicacao = fk_Cod_Publicacao;
    }

    public void setGenero_Textual(String genero_Textual) {
        Genero_Textual = genero_Textual;
    }
    
}
