package negocio;

import java.util.ArrayList;
import java.util.List;

import enums.TipoRecorrido;
import modelo.Libro;

public class CatalogoABB {

    private NodoLibro raiz;

    public CatalogoABB() {

        this.raiz = null;
    }

    public NodoLibro getRaiz() {

        return raiz;
    }

    public void setRaiz(NodoLibro raiz) {

        this.raiz = raiz;
    }

    public boolean insertar(Libro libro) {

        if (libro == null) {
            return false;
        }
        if (buscar(libro.getCodigo()) != null) {
            return false;
        }
        raiz = insertar(raiz, libro);
        return true;
    }

    private NodoLibro insertar(NodoLibro actual, Libro libro) {

        if (actual == null) {
            return new NodoLibro(libro);
        }
        if (libro.getCodigo() < actual.getDato().getCodigo()) {
            actual.setIzquierdo(insertar(actual.getIzquierdo(), libro));
        } else {
            actual.setDerecho(insertar(actual.getDerecho(), libro));
        }
        return actual;
    }

    public Libro buscar(int codigo) {

        NodoLibro nodo = buscar(raiz, codigo);
        return nodo == null ? null : nodo.getDato();
    }

    private NodoLibro buscar(NodoLibro actual, int codigo) {

        if (actual == null) {
            return null;
        }
        if (codigo == actual.getDato().getCodigo()) {
            return actual;
        }
        if (codigo < actual.getDato().getCodigo()) {
            return buscar(actual.getIzquierdo(), codigo);
        }
        return buscar(actual.getDerecho(), codigo);
    }

    public int contar() {

        return contar(raiz);
    }

    private int contar(NodoLibro actual) {

        if (actual == null) {
            return 0;
        }
        return 1 + contar(actual.getIzquierdo()) + contar(actual.getDerecho());
    }

    public List<Libro> recorrer(TipoRecorrido tipo) {

        if (tipo == null) {
            throw new IllegalArgumentException("El tipo de recorrido no puede ser nulo.");
        }
        List<Libro> resultado = new ArrayList<>();
        recorrer(raiz, tipo, resultado);
        return resultado;
    }

    private void recorrer(NodoLibro actual, TipoRecorrido tipo, List<Libro> resultado) {

        if (actual == null) {
            return;
        }
        if (tipo == TipoRecorrido.PREORDEN) {
            resultado.add(actual.getDato());
        }
        recorrer(actual.getIzquierdo(), tipo, resultado);
        if (tipo == TipoRecorrido.INORDEN) {
            resultado.add(actual.getDato());
        }
        recorrer(actual.getDerecho(), tipo, resultado);
        if (tipo == TipoRecorrido.POSORDEN) {
            resultado.add(actual.getDato());
        }
    }

    public String trazaRecorrido(TipoRecorrido tipo) {

        if (tipo == null) {
            throw new IllegalArgumentException("El tipo de recorrido no puede ser nulo.");
        }
        StringBuilder sb = new StringBuilder();
        trazar(raiz, tipo, 0, sb);
        sb.append("Secuencia ").append(tipo).append(":");
        for (Libro libro : recorrer(tipo)) {
            sb.append(" ").append(libro.getCodigo());
        }
        return sb.toString();
    }

    private void trazar(NodoLibro actual, TipoRecorrido tipo, int nivel, StringBuilder sb) {

        String sangria = "  ".repeat(nivel);
        if (actual == null) {
            sb.append(sangria).append("null -> regresa\n");
            return;
        }
        int codigo = actual.getDato().getCodigo();
        sb.append(sangria).append("entra ").append(codigo).append("\n");
        if (tipo == TipoRecorrido.PREORDEN) {
            sb.append(sangria).append("VISITA ").append(codigo).append("\n");
        }
        trazar(actual.getIzquierdo(), tipo, nivel + 1, sb);
        if (tipo == TipoRecorrido.INORDEN) {
            sb.append(sangria).append("VISITA ").append(codigo).append("\n");
        }
        trazar(actual.getDerecho(), tipo, nivel + 1, sb);
        if (tipo == TipoRecorrido.POSORDEN) {
            sb.append(sangria).append("VISITA ").append(codigo).append("\n");
        }
    }

    public boolean modificar(int codigo, String titulo, String autor, int anio) {

        NodoLibro nodo = buscar(raiz, codigo);
        if (nodo == null) {
            return false;
        }
        Libro validado = new Libro(codigo, titulo, autor, anio);
        nodo.getDato().setTitulo(validado.getTitulo());
        nodo.getDato().setAutor(validado.getAutor());
        nodo.getDato().setAnio(validado.getAnio());
        return true;
    }

    public boolean eliminar(int codigo) {

        if (buscar(codigo) == null) {
            return false;
        }
        raiz = eliminar(raiz, codigo);
        return true;
    }

    private NodoLibro eliminar(NodoLibro actual, int codigo) {

        if (actual == null) {
            return null;
        }
        if (codigo < actual.getDato().getCodigo()) {
            actual.setIzquierdo(eliminar(actual.getIzquierdo(), codigo));
            return actual;
        }
        if (codigo > actual.getDato().getCodigo()) {
            actual.setDerecho(eliminar(actual.getDerecho(), codigo));
            return actual;
        }
        if (actual.getIzquierdo() == null) {
            return actual.getDerecho();
        }
        if (actual.getDerecho() == null) {
            return actual.getIzquierdo();
        }
        NodoLibro sucesor = minimo(actual.getDerecho());
        actual.setDato(sucesor.getDato());
        actual.setDerecho(eliminar(actual.getDerecho(), sucesor.getDato().getCodigo()));
        return actual;
    }

    private NodoLibro minimo(NodoLibro actual) {

        if (actual.getIzquierdo() == null) {
            return actual;
        }
        return minimo(actual.getIzquierdo());
    }

    public int contarEnRango(int a, int b) {

        return contarEnRango(raiz, Math.min(a, b), Math.max(a, b));
    }

    private int contarEnRango(NodoLibro actual, int a, int b) {

        if (actual == null) {
            return 0;
        }
        int codigo = actual.getDato().getCodigo();
        if (codigo < a) {
            return contarEnRango(actual.getDerecho(), a, b);
        }
        if (codigo > b) {
            return contarEnRango(actual.getIzquierdo(), a, b);
        }
        int izquierdo = contarEnRango(actual.getIzquierdo(), a, b);
        int derecho = contarEnRango(actual.getDerecho(), a, b);
        return 1 + izquierdo + derecho;
    }

    public List<Integer> caminoBusqueda(int codigo) {

        List<Integer> camino = new ArrayList<>();
        caminoBusqueda(raiz, codigo, camino);
        return camino;
    }

    private void caminoBusqueda(NodoLibro actual, int codigo, List<Integer> camino) {

        if (actual == null) {
            return;
        }
        int actualCodigo = actual.getDato().getCodigo();
        camino.add(actualCodigo);
        if (codigo < actualCodigo) {
            caminoBusqueda(actual.getIzquierdo(), codigo, camino);
        } else if (codigo > actualCodigo) {
            caminoBusqueda(actual.getDerecho(), codigo, camino);
        }
    }

    public boolean esABBValido() {

        return esABBValido(raiz, null, null);
    }

    private boolean esABBValido(NodoLibro actual, Integer minimo, Integer maximo) {

        if (actual == null) {
            return true;
        }
        int codigo = actual.getDato().getCodigo();
        if (minimo != null && codigo <= minimo) {
            return false;
        }
        if (maximo != null && codigo >= maximo) {
            return false;
        }
        return esABBValido(actual.getIzquierdo(), minimo, codigo)
                && esABBValido(actual.getDerecho(), codigo, maximo);
    }

    public static CatalogoABB desdePreorden(int[] preorden) {

        if (preorden == null) {
            throw new IllegalArgumentException("El preorden no puede ser nulo.");
        }
        CatalogoABB catalogo = new CatalogoABB();
        int[] indice = {0};
        catalogo.raiz = construir(preorden, indice, Long.MIN_VALUE, Long.MAX_VALUE);
        if (indice[0] != preorden.length) {
            throw new IllegalArgumentException("La secuencia no es un preorden de ABB.");
        }
        return catalogo;
    }

    private static NodoLibro construir(int[] preorden, int[] indice, long minimo, long maximo) {

        if (indice[0] >= preorden.length) {
            return null;
        }
        int codigo = preorden[indice[0]];
        if (codigo <= minimo || codigo >= maximo) {
            return null;
        }
        indice[0]++;
        Libro libro = new Libro(codigo, "Libro " + codigo, "Autor desconocido", 2000);
        NodoLibro nodo = new NodoLibro(libro);
        nodo.setIzquierdo(construir(preorden, indice, minimo, codigo));
        nodo.setDerecho(construir(preorden, indice, codigo, maximo));
        return nodo;
    }
}
