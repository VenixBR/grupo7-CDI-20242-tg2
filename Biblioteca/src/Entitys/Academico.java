package Entitys;

public class Academico {
    
    private int Edicao;
    private String Area;
    private int fk_Cod_Publicacao;

    public String getArea() {
        return Area;
    }

    public int getEdicao() {
        return Edicao;
    }

    public int getFk_Cod_Publicacao() {
        return fk_Cod_Publicacao;
    }

    public void setArea(String area) {
        Area = area;
    }

    public void setEdicao(int edicao) {
        Edicao = edicao;
    }

    public void setFk_Cod_Publicacao(int fk_Cod_Publicacao) {
        this.fk_Cod_Publicacao = fk_Cod_Publicacao;
    }

}