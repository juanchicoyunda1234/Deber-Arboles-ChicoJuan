# Catálogo de la Biblioteca FISEI: árbol general y ABB en Java

Deber individual de Estructura de Datos (Universidad Técnica de Ambato, Facultad de Ingeniería en Sistemas, Electrónica e Industrial, Carrera de Software, Nivel III-B).

**Estudiante:** Chico Yunda Juan Carlos
**Docente:** Ing. José Rubén Caiza Caizabuano

## Descripción del problema

La biblioteca de la FISEI se organiza en una jerarquía: la biblioteca tiene áreas, y cada área tiene estantes. Cada estante guarda su propio catálogo de libros, ordenado por código para poder buscar rápido.

El programa modela esa situación con dos estructuras:

- **Árbol general** (`ArbolBiblioteca`): cada nodo (`NodoUnidad`) puede tener cualquier cantidad de hijos. La regla de la jerarquía es BIBLIOTECA → AREA → ESTANTE.
- **Árbol binario de búsqueda** (`CatalogoABB`): cada nodo de tipo ESTANTE tiene un catálogo de libros ordenado por código. Los menores van a la izquierda y los mayores a la derecha.

Todas las operaciones sobre los árboles son recursivas. No se usan `TreeSet`, `TreeMap`, `PriorityQueue`, `NavigableSet`, `NavigableMap`, `Collections.sort` ni `List.sort`.

## Estructura del proyecto

```
Deber-Arboles-ChicoJuan/
├── src/
│   ├── enums/
│   │   ├── TipoUnidad.java
│   │   └── TipoRecorrido.java
│   ├── modelo/
│   │   ├── Libro.java
│   │   └── UnidadBiblioteca.java
│   ├── negocio/
│   │   ├── NodoLibro.java
│   │   ├── NodoUnidad.java
│   │   ├── ArbolBiblioteca.java
│   │   └── CatalogoABB.java
│   └── app/
│       ├── DatosPrueba.java
│       ├── Main.java
│       └── PruebasBiblioteca.java
├── docs/
│   └── DIAGRAMAS.md
├── README.md
├── TRAZAS.md
└── .gitignore
```

## Operaciones implementadas

| Estructura | Operaciones |
|---|---|
| `ArbolBiblioteca` | agregar, buscar, altura, contarHojas, nivel, mostrarJerarquia, contarLibrosTotal |
| `CatalogoABB` | insertar, buscar, contar, modificar, eliminar (hoja, un hijo, dos hijos), recorridos preorden, inorden y posorden, trazaRecorrido |
| Retos | contarEnRango, caminoBusqueda, esABBValido |
| Extra | desdePreorden (reconstruye un ABB a partir de su recorrido en preorden) |

## Cómo compilar y ejecutar

Se necesita JDK 17 o superior. Desde la carpeta raíz del proyecto:

```bash
mkdir out
javac -d out $(find src -name "*.java")
```

Programa con menú:

```bash
java -cp out app.Main
```

Pruebas automáticas (13 grupos, 41 verificaciones):

```bash
java -cp out app.PruebasBiblioteca
```

La última línea de las pruebas debe ser `41/41 pruebas OK`.

En Windows, si `find` no está disponible, compile con:

```bash
javac -d out src/enums/*.java src/modelo/*.java src/negocio/*.java src/app/*.java
```

## Captura de PruebasBiblioteca

Salida de `java -cp out app.PruebasBiblioteca`, que termina con `41/41 pruebas OK`:

```text
===== PRUEBAS BIBLIOTECA =====
[1] Biblioteca solo con la raiz
  OK - altura: esperado=0, obtenido=0
  OK - hojas: esperado=1, obtenido=1
[2] Biblioteca inicial
BIB - Biblioteca FISEI
  SW - Software
    SW-E1 - Estante 1 de Software
    SW-E2 - Estante 2 de Software
  TI - Tecnologias de la Informacion
    TI-E1 - Estante 1 de TI
  TEL - Telecomunicaciones
  OK - altura: esperado=2, obtenido=2
  OK - hojas: esperado=4, obtenido=4
  OK - libros: esperado=13, obtenido=13
[3] Niveles de BIB, sw, SW-E2 y XYZ
  OK - nivel BIB: esperado=0, obtenido=0
  OK - nivel sw: esperado=1, obtenido=1
  OK - nivel SW-E2: esperado=2, obtenido=2
  OK - nivel XYZ: esperado=-1, obtenido=-1
[4] Agregar un ESTANTE como hijo de BIBLIOTECA
  OK - agregar ESTANTE bajo BIBLIOTECA: esperado=false, obtenido=false
  OK - agregar unidad con codigo repetido: esperado=false, obtenido=false
[5] Recorridos de SW-E1
  OK - preorden: esperado=60 35 20 45 40 80 70 75 90, obtenido=60 35 20 45 40 80 70 75 90
  OK - inorden: esperado=20 35 40 45 60 70 75 80 90, obtenido=20 35 40 45 60 70 75 80 90
  OK - posorden: esperado=20 40 45 35 75 70 90 80 60, obtenido=20 40 45 35 75 70 90 80 60
  OK - ultima linea de la traza: esperado=Secuencia PREORDEN: 60 35 20 45 40 80 70 75 90, obtenido=Secuencia PREORDEN: 60 35 20 45 40 80 70 75 90
[6] Insertar un codigo repetido
  OK - insertar 45 repetido: esperado=false, obtenido=false
  OK - libros tras el intento: esperado=9, obtenido=9
[7] caminoBusqueda y contarEnRango
  OK - caminoBusqueda(42): esperado=[60, 35, 45, 40], obtenido=[60, 35, 45, 40]
  OK - contarEnRango(40, 75): esperado=5, obtenido=5
[8] Insertar 42 y eliminar 20, 70 y 60
  OK - insertar 42: esperado=true, obtenido=true
  OK - eliminar 20 (hoja): esperado=true, obtenido=true
  OK - eliminar 70 (un hijo): esperado=true, obtenido=true
  OK - eliminar 60 (dos hijos): esperado=true, obtenido=true
  OK - preorden final: esperado=75 35 45 40 42 80 90, obtenido=75 35 45 40 42 80 90
  OK - sigue siendo ABB valido: esperado=true, obtenido=true
[9] Buscar, modificar y eliminar un codigo inexistente
  OK - buscar 999: esperado=null, obtenido=null
  OK - modificar 999: esperado=false, obtenido=false
  OK - eliminar 999: esperado=false, obtenido=false
[10] Libros con datos invalidos
  OK - codigo 0: IllegalArgumentException
  OK - anio 1899: IllegalArgumentException
  OK - anio 2027: IllegalArgumentException
  OK - titulo vacio: IllegalArgumentException
[11] esABBValido con un nieto mal ubicado
  OK - esABBValido con 65 bajo 35 y raiz 60: esperado=false, obtenido=false
  OK - esABBValido de un catalogo vacio: esperado=true, obtenido=true
[12] Extra: desdePreorden
  OK - preorden reconstruido: esperado=60 35 20 45 40 80 70 75 90, obtenido=60 35 20 45 40 80 70 75 90
  OK - inorden reconstruido: esperado=20 35 40 45 60 70 75 80 90, obtenido=20 35 40 45 60 70 75 80 90
  OK - es ABB valido: esperado=true, obtenido=true
[13] Modificar un libro sin cambiar su codigo
  OK - modificar 45: esperado=true, obtenido=true
  OK - codigo intacto: esperado=45, obtenido=45
  OK - titulo nuevo: esperado=Patrones de diseno 2, obtenido=Patrones de diseno 2
  OK - anio nuevo: esperado=1995, obtenido=1995

41/41 pruebas OK
```

## Menú del programa

Primero se debe cargar la estructura inicial (opción 1).

| Opción | Acción |
|---|---|
| 1 | Cargar la estructura inicial |
| 2 | Mostrar la jerarquía |
| 3 | Agregar y buscar unidades |
| 4 | Consultar altura, hojas, nivel y total de libros |
| 5 | Insertar, buscar, modificar y eliminar libros de un estante |
| 6 | Mostrar los recorridos con su traza |
| 7 | Ejecutar los retos |
| 0 | Salir |

## Datos iniciales

```
BIB - Biblioteca FISEI
  SW - Software
    SW-E1 - Estante 1 de Software   (60, 35, 80, 20, 45, 70, 90, 40, 75)
    SW-E2 - Estante 2 de Software   (15, 10, 25)
  TI - Tecnologias de la Informacion
    TI-E1 - Estante 1 de TI         (50)
  TEL - Telecomunicaciones
```

Resultados esperados de la estructura inicial: altura 2, 4 hojas y 13 libros.

Recorridos del estante SW-E1:

| Recorrido | Secuencia |
|---|---|
| Preorden | 60 35 20 45 40 80 70 75 90 |
| Inorden | 20 35 40 45 60 70 75 80 90 |
| Posorden | 20 40 45 35 75 70 90 80 60 |

## Decisiones de diseño

- El código de un libro es `final`: una vez creado no se puede cambiar, porque es la clave de ordenamiento del ABB.
- Insertar un código repetido devuelve `false` y no modifica el árbol.
- Eliminar un nodo con dos hijos usa el sucesor inorden (el menor del subárbol derecho).
- `esABBValido` pasa límites mínimo y máximo hacia abajo, así detecta también un nieto mal ubicado y no solo la relación padre-hijo.
- `contarEnRango` poda ramas: si el código del nodo es menor que el límite inferior solo baja por la derecha, y si es mayor que el superior solo baja por la izquierda.
- Los datos inválidos (código menor o igual a 0, textos vacíos, año fuera de 1900 a 2026) lanzan `IllegalArgumentException`.

## Entregables relacionados

- `docs/DIAGRAMAS.md`: diagramas de clases, árbol general, ABB de SW-E1 y flujo de eliminación, en Mermaid (se ven directo en GitHub).
- `TRAZAS.md`: resolución a mano del ABB con los códigos personales y comparación con la salida del programa.
- Informe en PDF `Deber-Arboles-ChicoJuan.pdf` entregado en Moodle.
