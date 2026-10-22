package negocio;

import modelo.UnidadBiblioteca;

public class ArbolBiblioteca {

    private final NodoUnidad raiz;

    public ArbolBiblioteca(UnidadBiblioteca raiz) {

        if (raiz == null) {
            throw new IllegalArgumentException("La unidad raiz no puede ser nula.");
        }
        this.raiz = new NodoUnidad(raiz);
    }

    public NodoUnidad getRaiz() {

        return raiz;
    }

    public boolean agregar(String codigoPadre, UnidadBiblioteca nueva) {

        if (codigoPadre == null || nueva == null) {
            return false;
        }
        if (buscar(nueva.getCodigo()) != null) {
            return false;
        }
        NodoUnidad padre = buscar(codigoPadre);
        if (padre == null) {
            return false;
        }
        if (!padre.getDato().getTipo().puedeTenerHijo(nueva.getTipo())) {
            return false;
        }
        padre.agregarHijo(new NodoUnidad(nueva));
        return true;
    }

    public NodoUnidad buscar(String codigo) {

        if (codigo == null) {
            return null;
        }
        return buscar(raiz, codigo.trim().toUpperCase());
    }

    private NodoUnidad buscar(NodoUnidad actual, String codigo) {

        if (actual.getDato().getCodigo().equals(codigo)) {
            return actual;
        }
        for (NodoUnidad hijo : actual.getHijos()) {
            NodoUnidad encontrado = buscar(hijo, codigo);
            if (encontrado != null) {
                return encontrado;
            }
        }
        return null;
    }

    public int altura() {

        return altura(raiz);
    }

    private int altura(NodoUnidad actual) {

        if (actual.esHoja()) {
            return 0;
        }
        int mayor = 0;
        for (NodoUnidad hijo : actual.getHijos()) {
            mayor = Math.max(mayor, altura(hijo));
        }
        return 1 + mayor;
    }

    public int contarHojas() {

        return contarHojas(raiz);
    }

    private int contarHojas(NodoUnidad actual) {

        if (actual.esHoja()) {
            return 1;
        }
        int total = 0;
        for (NodoUnidad hijo : actual.getHijos()) {
            total += contarHojas(hijo);
        }
        return total;
    }

    public int nivel(String codigo) {

        if (codigo == null) {
            return -1;
        }
        return nivel(raiz, codigo.trim().toUpperCase(), 0);
    }

    private int nivel(NodoUnidad actual, String codigo, int nivelActual) {

        if (actual.getDato().getCodigo().equals(codigo)) {
            return nivelActual;
        }
        for (NodoUnidad hijo : actual.getHijos()) {
            int encontrado = nivel(hijo, codigo, nivelActual + 1);
            if (encontrado != -1) {
                return encontrado;
            }
        }
        return -1;
    }

    public String mostrarJerarquia() {

        StringBuilder sb = new StringBuilder();
        mostrarJerarquia(raiz, 0, sb);
        return sb.toString();
    }

    private void mostrarJerarquia(NodoUnidad actual, int nivelActual, StringBuilder sb) {

        sb.append("  ".repeat(nivelActual))
          .append(actual.getDato().getCodigo())
          .append(" - ")
          .append(actual.getDato().getNombre())
          .append("\n");
        for (NodoUnidad hijo : actual.getHijos()) {
            mostrarJerarquia(hijo, nivelActual + 1, sb);
        }
    }

    public int contarLibrosTotal() {

        return contarLibrosTotal(raiz);
    }

    private int contarLibrosTotal(NodoUnidad actual) {

        int total = actual.getCatalogo() == null ? 0 : actual.getCatalogo().contar();
        for (NodoUnidad hijo : actual.getHijos()) {
            total += contarLibrosTotal(hijo);
        }
        return total;
    }
}
