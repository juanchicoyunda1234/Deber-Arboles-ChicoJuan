# Diagramas del proyecto

Diagramas del catálogo de la Biblioteca FISEI.

## 1. Diagrama de clases: enumeraciones, modelo y nodos

```mermaid
classDiagram
direction TB
class TipoUnidad {
<<enumeration>>
BIBLIOTECA
AREA
ESTANTE
+puedeTenerHijo(TipoUnidad hijo) boolean
}
class TipoRecorrido {
<<enumeration>>
PREORDEN
INORDEN
POSORDEN
}
class Libro {
-int codigo
-String titulo
-String autor
-int anio
+Libro(int, String, String, int)
+getCodigo() int
+getTitulo() String
+getAutor() String
+getAnio() int
+setTitulo(String) void
+setAutor(String) void
+setAnio(int) void
+toString() String
}
class UnidadBiblioteca {
-String codigo
-String nombre
-TipoUnidad tipo
+UnidadBiblioteca(String, String, TipoUnidad)
+getCodigo() String
+getNombre() String
+getTipo() TipoUnidad
+toString() String
}
class NodoUnidad {
-UnidadBiblioteca dato
-List~NodoUnidad~ hijos
-CatalogoABB catalogo
+NodoUnidad(UnidadBiblioteca)
+getDato() UnidadBiblioteca
+getHijos() List~NodoUnidad~
+agregarHijo(NodoUnidad) void
+getCatalogo() CatalogoABB
+esHoja() boolean
}
class NodoLibro {
-Libro dato
-NodoLibro izquierdo
-NodoLibro derecho
+NodoLibro(Libro)
+getDato() Libro
+setDato(Libro) void
+getIzquierdo() NodoLibro
+getDerecho() NodoLibro
+setIzquierdo(NodoLibro) void
+setDerecho(NodoLibro) void
}
UnidadBiblioteca --> TipoUnidad : tipo
NodoUnidad o-- UnidadBiblioteca : dato
NodoUnidad "1" o-- "0..*" NodoUnidad : hijos
NodoLibro o-- Libro : dato
NodoLibro "1" o-- "0..2" NodoLibro : izquierdo y derecho
```

## 2. Diagrama de clases: lógica de negocio y aplicación

```mermaid
classDiagram
direction TB
class ArbolBiblioteca {
-NodoUnidad raiz
+ArbolBiblioteca(UnidadBiblioteca)
+agregar(String, UnidadBiblioteca) boolean
+buscar(String) NodoUnidad
+altura() int
+contarHojas() int
+nivel(String) int
+mostrarJerarquia() String
+contarLibrosTotal() int
}
class CatalogoABB {
-NodoLibro raiz
+insertar(Libro) boolean
+buscar(int) Libro
+contar() int
+recorrer(TipoRecorrido) List~Libro~
+trazaRecorrido(TipoRecorrido) String
+modificar(int, String, String, int) boolean
+eliminar(int) boolean
+getRaiz() NodoLibro
+setRaiz(NodoLibro) void
+contarEnRango(int, int) int
+caminoBusqueda(int) List~Integer~
+esABBValido() boolean
+desdePreorden(int[])$ CatalogoABB
}
class Main {
+main(String[] args)$ void
}
class DatosPrueba {
+construirBibliotecaInicial()$ ArbolBiblioteca
+construirBibliotecaSoloRaiz()$ ArbolBiblioteca
+libroDe(int)$ Libro
}
class PruebasBiblioteca {
+main(String[] args)$ void
}
class NodoUnidad
class NodoLibro
class TipoRecorrido {
<<enumeration>>
}
ArbolBiblioteca "1" o-- "1" NodoUnidad : raiz
NodoUnidad "1" o-- "0..1" CatalogoABB : catalogo
CatalogoABB "1" o-- "0..1" NodoLibro : raiz
CatalogoABB ..> TipoRecorrido
Main ..> ArbolBiblioteca
Main ..> CatalogoABB
Main ..> DatosPrueba
PruebasBiblioteca ..> DatosPrueba
PruebasBiblioteca ..> ArbolBiblioteca
PruebasBiblioteca ..> CatalogoABB
```

## 3. Árbol general de la biblioteca (estructura inicial)

La regla de la jerarquía es BIBLIOTECA → AREA → ESTANTE. Solo los nodos de tipo ESTANTE tienen un catálogo ABB.

```mermaid
graph TD
    BIB["BIB - Biblioteca FISEI<br/>BIBLIOTECA"]
    SW["SW - Software<br/>AREA"]
    TI["TI - Tecnologias de la Informacion<br/>AREA"]
    TEL["TEL - Telecomunicaciones<br/>AREA"]
    SWE1["SW-E1<br/>ESTANTE · 9 libros"]
    SWE2["SW-E2<br/>ESTANTE · 3 libros"]
    TIE1["TI-E1<br/>ESTANTE · 1 libro"]
    BIB --> SW
    BIB --> TI
    BIB --> TEL
    SW --> SWE1
    SW --> SWE2
    TI --> TIE1
```

Altura: 2 aristas. Hojas: 4 (SW-E1, SW-E2, TI-E1 y TEL). Libros en total: 13.

## 4. ABB del estante SW-E1

Códigos insertados en este orden: 60, 35, 80, 20, 45, 70, 90, 40, 75.

```mermaid
graph TD
    n60((60))
    n35((35))
    n80((80))
    n20((20))
    n45((45))
    n70((70))
    n90((90))
    n40((40))
    n75((75))
    n60 -->|I| n35
    n60 -->|D| n80
    n35 -->|I| n20
    n35 -->|D| n45
    n45 -->|I| n40
    n80 -->|I| n70
    n80 -->|D| n90
    n70 -->|D| n75
```

| Recorrido | Secuencia |
|---|---|
| Preorden | 60 35 20 45 40 80 70 75 90 |
| Inorden | 20 35 40 45 60 70 75 80 90 |
| Posorden | 20 40 45 35 75 70 90 80 60 |

## 5. Eliminación en el ABB

```mermaid
flowchart TD
    A([eliminar codigo]) --> B{nodo nulo?}
    B -- si --> C([regresa null])
    B -- no --> D{codigo menor que el del nodo?}
    D -- si --> E[bajar por la izquierda]
    D -- no --> F{codigo mayor que el del nodo?}
    F -- si --> G[bajar por la derecha]
    F -- no --> H{sin hijo izquierdo?}
    H -- si --> I([regresa el hijo derecho])
    H -- no --> J{sin hijo derecho?}
    J -- si --> K([regresa el hijo izquierdo])
    J -- no --> L[copiar el sucesor inorden: minimo del subarbol derecho]
    L --> M[eliminar el sucesor del subarbol derecho]
    M --> N([regresa el nodo])
```

Los tres casos: hoja (ambos hijos nulos, entra por el caso de hijo izquierdo nulo), un solo hijo (el hijo ocupa el lugar) y dos hijos (se usa el sucesor inorden).

## 6. Estado del ABB de SW-E1 tras insertar 42 y eliminar 20, 70 y 60

```mermaid
graph TD
    n75((75))
    n35((35))
    n80((80))
    n45((45))
    n40((40))
    n42((42))
    n90((90))
    n75 -->|I| n35
    n75 -->|D| n80
    n35 -->|D| n45
    n45 -->|I| n40
    n40 -->|D| n42
    n80 -->|D| n90
```

Preorden esperado: 75 35 45 40 42 80 90.
