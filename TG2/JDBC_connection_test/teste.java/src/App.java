import entitys.Biblioteca;

public class App {
    public static void main(String[] args) throws Exception {
        
        Biblioteca bib = new Biblioteca();
        bib.setCod_Biblioteca(0);
        bib.setEndereco("Avenida Roraima");
        bib.setNome("Biblioteca Setorial do Centro de Tecnologia");
        bib.setSigla("BSCT");

        new DAOUser().CadastrarBiblioteca(bib);

        Biblioteca bib_consulta = new Biblioteca();
        bib_consulta = new DAOUser().BuscarBiblioteca(1);
         System.out.println(bib_consulta.getCod_Biblioteca());
         System.out.println(bib_consulta.getEndereco());
         System.out.println(bib_consulta.getNome());
         System.out.println(bib_consulta.getSigla());
    }
}


