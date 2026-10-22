package app;

import enums.TipoUnidad;
import modelo.Libro;
import modelo.UnidadBiblioteca;
import negocio.ArbolBiblioteca;
import negocio.CatalogoABB;

public class DatosPrueba {

    private static final int[] CODIGOS_SW_E1 = {60, 35, 80, 20, 45, 70, 90, 40, 75};
    private static final int[] CODIGOS_SW_E2 = {15, 10, 25};
    private static final int[] CODIGOS_TI_E1 = {50};

    public static ArbolBiblioteca construirBibliotecaInicial() {

        ArbolBiblioteca biblioteca = construirBibliotecaSoloRaiz();

        agregarUnidad(biblioteca, "BIB", "SW", "Software", TipoUnidad.AREA);
        agregarUnidad(biblioteca, "BIB", "TI", "Tecnologias de la Informacion", TipoUnidad.AREA);
        agregarUnidad(biblioteca, "BIB", "TEL", "Telecomunicaciones", TipoUnidad.AREA);
        agregarUnidad(biblioteca, "SW", "SW-E1", "Estante 1 de Software", TipoUnidad.ESTANTE);
        agregarUnidad(biblioteca, "SW", "SW-E2", "Estante 2 de Software", TipoUnidad.ESTANTE);
        agregarUnidad(biblioteca, "TI", "TI-E1", "Estante 1 de TI", TipoUnidad.ESTANTE);

        cargarLibros(biblioteca.buscar("SW-E1").getCatalogo(), CODIGOS_SW_E1);
        cargarLibros(biblioteca.buscar("SW-E2").getCatalogo(), CODIGOS_SW_E2);
        cargarLibros(biblioteca.buscar("TI-E1").getCatalogo(), CODIGOS_TI_E1);
        return biblioteca;
    }

    public static ArbolBiblioteca construirBibliotecaSoloRaiz() {

        UnidadBiblioteca raiz = new UnidadBiblioteca("BIB", "Biblioteca FISEI", TipoUnidad.BIBLIOTECA);
        return new ArbolBiblioteca(raiz);
    }

    private static void agregarUnidad(ArbolBiblioteca biblioteca, String padre, String codigo,
                                      String nombre, TipoUnidad tipo) {

        biblioteca.agregar(padre, new UnidadBiblioteca(codigo, nombre, tipo));
    }

    private static void cargarLibros(CatalogoABB catalogo, int[] codigos) {

        for (int codigo : codigos) {
            catalogo.insertar(libroDe(codigo));
        }
    }

    public static Libro libroDe(int codigo) {

        switch (codigo) {
            case 60:
                return new Libro(60, "Estructuras de datos y algoritmos en Java",
                        "Mark Allen Weiss", 2012);
            case 35:
                return new Libro(35, "Introduccion a los algoritmos", "Thomas Cormen", 2009);
            case 80:
                return new Libro(80, "Codigo limpio", "Robert C. Martin", 2008);
            case 20:
                return new Libro(20, "Ingenieria del software", "Ian Sommerville", 2011);
            case 45:
                return new Libro(45, "Patrones de diseno", "Erich Gamma", 1994);
            case 70:
                return new Libro(70, "Java: como programar", "Paul Deitel", 2016);
            case 90:
                return new Libro(90, "Refactorizacion", "Martin Fowler", 2018);
            case 40:
                return new Libro(40, "Arquitectura limpia", "Robert C. Martin", 2017);
            case 75:
                return new Libro(75, "Programacion orientada a objetos",
                        "Timothy Budd", 2002);
            case 15:
                return new Libro(15, "Redes de computadoras", "Andrew Tanenbaum", 2012);
            case 10:
                return new Libro(10, "Comunicaciones y redes de computadores",
                        "William Stallings", 2004);
            case 25:
                return new Libro(25, "TCP/IP Illustrated", "W. Richard Stevens", 2011);
            case 50:
                return new Libro(50, "Fundamentos de bases de datos",
                        "Abraham Silberschatz", 2019);
            default:
                return new Libro(codigo, "Libro " + codigo, "Autor desconocido", 2000);
        }
    }
}
