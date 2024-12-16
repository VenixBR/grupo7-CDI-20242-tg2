package Entitys;

public class Autoajuda {
    
    private String Assunto;
    private int fk_Cod_Publicacao;
    
    public Autoajuda(){}
    
    public Autoajuda(String assunto, int cod_pub){
        Assunto = assunto;
        fk_Cod_Publicacao = cod_pub;
    }

    public String getAssunto() {
        return Assunto;
    }

    public int getFk_Cod_Publicacao() {
        return fk_Cod_Publicacao;
    }

    public void setAssunto(String assunto) {
        Assunto = assunto;
    }

    public void setFk_Cod_Publicacao(int fk_Cod_Publicacao) {
        this.fk_Cod_Publicacao = fk_Cod_Publicacao;
    }
    
}
