package app;

import java.util.List;
import java.util.Scanner;

import enums.TipoRecorrido;
import enums.TipoUnidad;
import modelo.Libro;
import modelo.UnidadBiblioteca;
import negocio.ArbolBiblioteca;
import negocio.CatalogoABB;
import negocio.NodoUnidad;

public class Main {

    private static final Scanner sc = new Scanner(System.in);
    private static ArbolBiblioteca biblioteca = null;

    public static void main(String[] args) {

        int opcion;
        do {
            mostrarMenu();
            opcion = leerEntero("Seleccione una opcion: ");
            ejecutarOpcion(opcion);
        } while (opcion != 0);
        System.out.println("Programa finalizado.");
    }

    private static void mostrarMenu() {

        System.out.println();
        System.out.println("===== CATALOGO DE LA BIBLIOTECA FISEI =====");
        System.out.println("1. Cargar la estructura inicial");
        System.out.println("2. Mostrar la jerarquia");
        System.out.println("3. Agregar y buscar unidades");
        System.out.println("4. Consultar altura, hojas, nivel y total de libros");
        System.out.println("5. Insertar, buscar, modificar y eliminar libros de un estante");
        System.out.println("6. Mostrar los recorridos con su traza");
        System.out.println("7. Ejecutar los retos");
        System.out.println("0. Salir");
    }

    private static void ejecutarOpcion(int opcion) {

        if (opcion >= 2 && opcion <= 7 && biblioteca == null) {
            System.out.println("Primero cargue la estructura inicial (opcion 1).");
            return;
        }
        switch (opcion) {
            case 1:
                biblioteca = DatosPrueba.construirBibliotecaInicial();
                System.out.println("Estructura inicial cargada.");
                break;
            case 2:
                System.out.println();
                System.out.print(biblioteca.mostrarJerarquia());
                break;
            case 3:
                gestionarUnidades();
                break;
            case 4:
                consultarEstructura();
                break;
            case 5:
                gestionarLibros();
                break;
            case 6:
                mostrarRecorridos();
                break;
            case 7:
                ejecutarRetos();
                break;
            case 0:
                break;
            default:
                System.out.println("Opcion invalida.");
        }
    }

    private static void gestionarUnidades() {

        System.out.println("1. Agregar unidad   2. Buscar unidad");
        int opcion = leerEntero("Opcion: ");
        if (opcion == 1) {
            String padre = leerTexto("Codigo del padre: ");
            String codigo = leerTexto("Codigo de la nueva unidad: ");
            String nombre = leerTexto("Nombre de la nueva unidad: ");
            TipoUnidad tipo = leerTipoUnidad();
            if (tipo == null) {
                return;
            }
            try {
                UnidadBiblioteca nueva = new UnidadBiblioteca(codigo, nombre, tipo);
                if (biblioteca.agregar(padre, nueva)) {
                    System.out.println("Unidad agregada.");
                } else {
                    System.out.println("No se pudo agregar: padre inexistente,");
                    System.out.println("jerarquia invalida o codigo repetido.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Datos invalidos: " + e.getMessage());
            }
        } else if (opcion == 2) {
            NodoUnidad nodo = biblioteca.buscar(leerTexto("Codigo a buscar: "));
            if (nodo == null) {
                System.out.println("La unidad no existe.");
            } else {
                System.out.println("Encontrada: " + nodo.getDato());
            }
        } else {
            System.out.println("Opcion invalida.");
        }
    }

    private static TipoUnidad leerTipoUnidad() {

        System.out.println("Tipo: 1. BIBLIOTECA  2. AREA  3. ESTANTE");
        int opcion = leerEntero("Tipo: ");
        switch (opcion) {
            case 1:
                return TipoUnidad.BIBLIOTECA;
            case 2:
                return TipoUnidad.AREA;
            case 3:
                return TipoUnidad.ESTANTE;
            default:
                System.out.println("Tipo invalido.");
                return null;
        }
    }

    private static void consultarEstructura() {

        System.out.println("Altura (en aristas): " + biblioteca.altura());
        System.out.println("Numero de hojas: " + biblioteca.contarHojas());
        System.out.println("Total de libros: " + biblioteca.contarLibrosTotal());
        String codigo = leerTexto("Codigo de la unidad para consultar su nivel: ");
        int nivel = biblioteca.nivel(codigo);
        System.out.println("Nivel de " + codigo.toUpperCase() + ": " + nivel);
    }

    private static CatalogoABB pedirCatalogo() {

        String codigo = leerTexto("Codigo del estante: ");
        NodoUnidad nodo = biblioteca.buscar(codigo);
        if (nodo == null || nodo.getCatalogo() == null) {
            System.out.println("El estante no existe.");
            return null;
        }
        return nodo.getCatalogo();
    }

    private static void gestionarLibros() {

        CatalogoABB catalogo = pedirCatalogo();
        if (catalogo == null) {
            return;
        }
        System.out.println("1. Insertar  2. Buscar  3. Modificar  4. Eliminar");
        int opcion = leerEntero("Opcion: ");
        try {
            switch (opcion) {
                case 1:
                    int codigoNuevo = leerEntero("Codigo: ");
                    String titulo = leerTexto("Titulo: ");
                    String autor = leerTexto("Autor: ");
                    int anio = leerEntero("Anio: ");
                    Libro nuevo = new Libro(codigoNuevo, titulo, autor, anio);
                    System.out.println(catalogo.insertar(nuevo)
                            ? "Libro insertado." : "El codigo ya existe.");
                    break;
                case 2:
                    Libro libro = catalogo.buscar(leerEntero("Codigo a buscar: "));
                    System.out.println(libro == null
                            ? "El libro no existe." : "Encontrado: " + libro);
                    break;
                case 3:
                    int codigoMod = leerEntero("Codigo a modificar: ");
                    String nuevoTitulo = leerTexto("Nuevo titulo: ");
                    String nuevoAutor = leerTexto("Nuevo autor: ");
                    int nuevoAnio = leerEntero("Nuevo anio: ");
                    boolean modificado = catalogo.modificar(codigoMod, nuevoTitulo,
                            nuevoAutor, nuevoAnio);
                    System.out.println(modificado
                            ? "Libro modificado." : "El libro no existe.");
                    break;
                case 4:
                    System.out.println(catalogo.eliminar(leerEntero("Codigo a eliminar: "))
                            ? "Libro eliminado." : "El libro no existe.");
                    break;
                default:
                    System.out.println("Opcion invalida.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Datos invalidos: " + e.getMessage());
        }
    }

    private static void mostrarRecorridos() {

        CatalogoABB catalogo = pedirCatalogo();
        if (catalogo == null) {
            return;
        }
        for (TipoRecorrido tipo : TipoRecorrido.values()) {
            System.out.println();
            System.out.println("--- " + tipo + " ---");
            System.out.println(catalogo.trazaRecorrido(tipo));
        }
        System.out.println();
        System.out.println("Libros en INORDEN:");
        List<Libro> libros = catalogo.recorrer(TipoRecorrido.INORDEN);
        for (Libro libro : libros) {
            System.out.println("  " + libro);
        }
    }

    private static void ejecutarRetos() {

        CatalogoABB catalogo = pedirCatalogo();
        if (catalogo == null) {
            return;
        }
        int a = leerEntero("Limite inferior a: ");
        int b = leerEntero("Limite superior b: ");
        int enRango = catalogo.contarEnRango(a, b);
        System.out.println("contarEnRango(" + a + ", " + b + "): " + enRango);
        int codigo = leerEntero("Codigo para caminoBusqueda: ");
        List<Integer> camino = catalogo.caminoBusqueda(codigo);
        System.out.println("caminoBusqueda(" + codigo + "): " + camino);
        System.out.println("esABBValido(): " + catalogo.esABBValido());
        int[] preorden = new int[catalogo.contar()];
        List<Libro> libros = catalogo.recorrer(TipoRecorrido.PREORDEN);
        for (int i = 0; i < preorden.length; i++) {
            preorden[i] = libros.get(i).getCodigo();
        }
        CatalogoABB reconstruido = CatalogoABB.desdePreorden(preorden);
        List<Libro> nuevos = reconstruido.recorrer(TipoRecorrido.PREORDEN);
        StringBuilder sb = new StringBuilder();
        for (Libro libro : nuevos) {
            sb.append(libro.getCodigo()).append(" ");
        }
        System.out.println("desdePreorden reconstruye el preorden: " + sb.toString().trim());
    }

    private static int leerEntero(String mensaje) {

        while (true) {
            System.out.print(mensaje);
            String linea = sc.nextLine().trim();
            try {
                return Integer.parseInt(linea);
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un numero entero valido.");
            }
        }
    }

    private static String leerTexto(String mensaje) {

        while (true) {
            System.out.print(mensaje);
            String linea = sc.nextLine().trim();
            if (!linea.isEmpty()) {
                return linea;
            }
            System.out.println("El texto no puede estar vacio.");
        }
    }
}
