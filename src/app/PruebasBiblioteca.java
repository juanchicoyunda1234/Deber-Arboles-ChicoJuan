package app;

import java.util.List;

import enums.TipoRecorrido;
import enums.TipoUnidad;
import modelo.Libro;
import modelo.UnidadBiblioteca;
import negocio.ArbolBiblioteca;
import negocio.CatalogoABB;
import negocio.NodoLibro;

public class PruebasBiblioteca {

    private static int total = 0;
    private static int correctas = 0;

    public static void main(String[] args) {

        System.out.println("===== PRUEBAS BIBLIOTECA =====");
        pruebaBibliotecaSoloRaiz();
        pruebaBibliotecaInicial();
        pruebaNiveles();
        pruebaJerarquiaInvalida();
        pruebaRecorridos();
        pruebaInsertarRepetido();
        pruebaRetos();
        pruebaInsertarYEliminar();
        pruebaInexistentes();
        pruebaDatosInvalidos();
        pruebaABBInvalido();
        pruebaDesdePreorden();
        pruebaModificar();
        System.out.println();
        System.out.println(correctas + "/" + total + " pruebas OK");
    }

    private static CatalogoABB estanteSW1() {

        return DatosPrueba.construirBibliotecaInicial().buscar("SW-E1").getCatalogo();
    }

    private static String orden(CatalogoABB catalogo, TipoRecorrido tipo) {

        StringBuilder sb = new StringBuilder();
        for (Libro libro : catalogo.recorrer(tipo)) {
            sb.append(libro.getCodigo()).append(" ");
        }
        return sb.toString().trim();
    }

    private static void pruebaBibliotecaSoloRaiz() {

        System.out.println("[1] Biblioteca solo con la raiz");
        ArbolBiblioteca solo = DatosPrueba.construirBibliotecaSoloRaiz();
        verificar("altura", 0, solo.altura());
        verificar("hojas", 1, solo.contarHojas());
    }

    private static void pruebaBibliotecaInicial() {

        System.out.println("[2] Biblioteca inicial");
        ArbolBiblioteca biblioteca = DatosPrueba.construirBibliotecaInicial();
        System.out.print(biblioteca.mostrarJerarquia());
        verificar("altura", 2, biblioteca.altura());
        verificar("hojas", 4, biblioteca.contarHojas());
        verificar("libros", 13, biblioteca.contarLibrosTotal());
    }

    private static void pruebaNiveles() {

        System.out.println("[3] Niveles de BIB, sw, SW-E2 y XYZ");
        ArbolBiblioteca biblioteca = DatosPrueba.construirBibliotecaInicial();
        verificar("nivel BIB", 0, biblioteca.nivel("BIB"));
        verificar("nivel sw", 1, biblioteca.nivel("sw"));
        verificar("nivel SW-E2", 2, biblioteca.nivel("SW-E2"));
        verificar("nivel XYZ", -1, biblioteca.nivel("XYZ"));
    }

    private static void pruebaJerarquiaInvalida() {

        System.out.println("[4] Agregar un ESTANTE como hijo de BIBLIOTECA");
        ArbolBiblioteca biblioteca = DatosPrueba.construirBibliotecaInicial();
        UnidadBiblioteca estante = new UnidadBiblioteca("EST-X", "Estante invalido", TipoUnidad.ESTANTE);
        UnidadBiblioteca repetida = new UnidadBiblioteca("sw", "Otra area", TipoUnidad.AREA);
        verificar("agregar ESTANTE bajo BIBLIOTECA", false, biblioteca.agregar("BIB", estante));
        verificar("agregar unidad con codigo repetido", false, biblioteca.agregar("BIB", repetida));
    }

    private static void pruebaRecorridos() {

        System.out.println("[5] Recorridos de SW-E1");
        CatalogoABB catalogo = estanteSW1();
        verificar("preorden", "60 35 20 45 40 80 70 75 90", orden(catalogo, TipoRecorrido.PREORDEN));
        verificar("inorden", "20 35 40 45 60 70 75 80 90", orden(catalogo, TipoRecorrido.INORDEN));
        verificar("posorden", "20 40 45 35 75 70 90 80 60", orden(catalogo, TipoRecorrido.POSORDEN));
        String traza = catalogo.trazaRecorrido(TipoRecorrido.PREORDEN);
        String ultima = traza.substring(traza.lastIndexOf("\n") + 1);
        verificar("ultima linea de la traza", "Secuencia PREORDEN: 60 35 20 45 40 80 70 75 90", ultima);
    }

    private static void pruebaInsertarRepetido() {

        System.out.println("[6] Insertar un codigo repetido");
        CatalogoABB catalogo = estanteSW1();
        verificar("insertar 45 repetido", false, catalogo.insertar(DatosPrueba.libroDe(45)));
        verificar("libros tras el intento", 9, catalogo.contar());
    }

    private static void pruebaRetos() {

        System.out.println("[7] caminoBusqueda y contarEnRango");
        CatalogoABB catalogo = estanteSW1();
        verificar("caminoBusqueda(42)", "[60, 35, 45, 40]", catalogo.caminoBusqueda(42).toString());
        verificar("contarEnRango(40, 75)", 5, catalogo.contarEnRango(40, 75));
    }

    private static void pruebaInsertarYEliminar() {

        System.out.println("[8] Insertar 42 y eliminar 20, 70 y 60");
        CatalogoABB catalogo = estanteSW1();
        verificar("insertar 42", true, catalogo.insertar(DatosPrueba.libroDe(42)));
        verificar("eliminar 20 (hoja)", true, catalogo.eliminar(20));
        verificar("eliminar 70 (un hijo)", true, catalogo.eliminar(70));
        verificar("eliminar 60 (dos hijos)", true, catalogo.eliminar(60));
        verificar("preorden final", "75 35 45 40 42 80 90", orden(catalogo, TipoRecorrido.PREORDEN));
        verificar("sigue siendo ABB valido", true, catalogo.esABBValido());
    }

    private static void pruebaInexistentes() {

        System.out.println("[9] Buscar, modificar y eliminar un codigo inexistente");
        CatalogoABB catalogo = estanteSW1();
        verificar("buscar 999", null, catalogo.buscar(999));
        verificar("modificar 999", false, catalogo.modificar(999, "Titulo", "Autor", 2000));
        verificar("eliminar 999", false, catalogo.eliminar(999));
    }

    private static void pruebaDatosInvalidos() {

        System.out.println("[10] Libros con datos invalidos");
        verificarExcepcion("codigo 0", () -> new Libro(0, "Titulo", "Autor", 2000));
        verificarExcepcion("anio 1899", () -> new Libro(1, "Titulo", "Autor", 1899));
        verificarExcepcion("anio 2027", () -> new Libro(1, "Titulo", "Autor", 2027));
        verificarExcepcion("titulo vacio", () -> new Libro(1, " ", "Autor", 2000));
    }

    private static void pruebaABBInvalido() {

        System.out.println("[11] esABBValido con un nieto mal ubicado");
        CatalogoABB catalogo = new CatalogoABB();
        NodoLibro raiz = new NodoLibro(DatosPrueba.libroDe(60));
        NodoLibro izquierdo = new NodoLibro(DatosPrueba.libroDe(35));
        izquierdo.setDerecho(new NodoLibro(DatosPrueba.libroDe(65)));
        raiz.setIzquierdo(izquierdo);
        catalogo.setRaiz(raiz);
        verificar("esABBValido con 65 bajo 35 y raiz 60", false, catalogo.esABBValido());
        verificar("esABBValido de un catalogo vacio", true, new CatalogoABB().esABBValido());
    }

    private static void pruebaDesdePreorden() {

        System.out.println("[12] Extra: desdePreorden");
        int[] preorden = {60, 35, 20, 45, 40, 80, 70, 75, 90};
        CatalogoABB catalogo = CatalogoABB.desdePreorden(preorden);
        verificar("preorden reconstruido", "60 35 20 45 40 80 70 75 90",
                orden(catalogo, TipoRecorrido.PREORDEN));
        verificar("inorden reconstruido", "20 35 40 45 60 70 75 80 90",
                orden(catalogo, TipoRecorrido.INORDEN));
        verificar("es ABB valido", true, catalogo.esABBValido());
    }

    private static void pruebaModificar() {

        System.out.println("[13] Modificar un libro sin cambiar su codigo");
        CatalogoABB catalogo = estanteSW1();
        verificar("modificar 45", true, catalogo.modificar(45, "Patrones de diseno 2", "Gamma", 1995));
        Libro libro = catalogo.buscar(45);
        verificar("codigo intacto", 45, libro.getCodigo());
        verificar("titulo nuevo", "Patrones de diseno 2", libro.getTitulo());
        verificar("anio nuevo", 1995, libro.getAnio());
    }

    private static void verificar(String descripcion, Object esperado, Object obtenido) {

        total++;
        boolean ok = esperado == null ? obtenido == null : esperado.equals(obtenido);
        if (ok) {
            correctas++;
        }
        String estado = ok ? "OK" : "FALLA";
        System.out.println("  " + estado + " - " + descripcion
                + ": esperado=" + esperado + ", obtenido=" + obtenido);
    }

    private static void verificarExcepcion(String descripcion, Runnable accion) {

        total++;
        boolean lanzada = false;
        try {
            accion.run();
        } catch (IllegalArgumentException e) {
            lanzada = true;
        }
        if (lanzada) {
            correctas++;
        }
        String estado = lanzada ? "OK" : "FALLA";
        System.out.println("  " + estado + " - " + descripcion + ": IllegalArgumentException");
    }
}
