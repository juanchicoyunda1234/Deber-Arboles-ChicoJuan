package negocio;

import java.util.ArrayList;
import java.util.List;

import enums.TipoUnidad;
import modelo.UnidadBiblioteca;

public class NodoUnidad {

    private final UnidadBiblioteca dato;
    private final List<NodoUnidad> hijos;
    private final CatalogoABB catalogo;

    public NodoUnidad(UnidadBiblioteca dato) {

        if (dato == null) {
            throw new IllegalArgumentException("La unidad del nodo no puede ser nula.");
        }
        this.dato = dato;
        this.hijos = new ArrayList<>();
        this.catalogo = dato.getTipo() == TipoUnidad.ESTANTE ? new CatalogoABB() : null;
    }

    public UnidadBiblioteca getDato() {

        return dato;
    }

    public List<NodoUnidad> getHijos() {

        return hijos;
    }

    public void agregarHijo(NodoUnidad hijo) {

        if (hijo == null) {
            throw new IllegalArgumentException("El hijo no puede ser nulo.");
        }
        hijos.add(hijo);
    }

    public CatalogoABB getCatalogo() {

        return catalogo;
    }

    public boolean esHoja() {

        return hijos.isEmpty();
    }
}
