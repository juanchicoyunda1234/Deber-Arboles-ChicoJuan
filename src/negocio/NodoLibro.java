package negocio;

import modelo.Libro;

public class NodoLibro {

    private Libro dato;
    private NodoLibro izquierdo;
    private NodoLibro derecho;

    public NodoLibro(Libro dato) {

        if (dato == null) {
            throw new IllegalArgumentException("El libro del nodo no puede ser nulo.");
        }
        this.dato = dato;
        this.izquierdo = null;
        this.derecho = null;
    }

    public Libro getDato() {

        return dato;
    }

    public void setDato(Libro dato) {

        if (dato == null) {
            throw new IllegalArgumentException("El libro del nodo no puede ser nulo.");
        }
        this.dato = dato;
    }

    public NodoLibro getIzquierdo() {

        return izquierdo;
    }

    public NodoLibro getDerecho() {

        return derecho;
    }

    public void setIzquierdo(NodoLibro izquierdo) {

        this.izquierdo = izquierdo;
    }

    public void setDerecho(NodoLibro derecho) {

        this.derecho = derecho;
    }
}
