package Entitys;

public class Escrito {
 
    private int fk_Cod_Publicacao;
    private int fk_Cod_Autor;
    
    public Escrito(){}
    
    public Escrito(int Cod_pub, int cod_autor){
        fk_Cod_Publicacao = Cod_pub;
        fk_Cod_Autor = cod_autor;
    }

    public int getFk_Cod_Autor() {
        return fk_Cod_Autor;
    }

    public int getFk_Cod_Publicacao() {
        return fk_Cod_Publicacao;
    }

    public void setFk_Cod_Autor(int fk_Cod_Autor) {
        this.fk_Cod_Autor = fk_Cod_Autor;
    }
    
    public void setFk_Cod_Publicacao(int fk_Cod_Publicacao) {
        this.fk_Cod_Publicacao = fk_Cod_Publicacao;
    }

}
