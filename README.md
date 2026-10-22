# Catálogo de la Biblioteca FISEI: Árbol General y ABB en Java

Deber individual de **Estructura de Datos**  
**Universidad Técnica de Ambato**  
Facultad de Ingeniería en Sistemas, Electrónica e Industrial  
Carrera de Software, Nivel III-B  

* **Estudiante:** Chico Yunda Juan Carlos  
* **Docente:** Ing. José Rubén Caiza Caizabuano  

---

## 1. Descripción del problema y objetivos

### 1.1. Contexto y caso práctico
La biblioteca de la FISEI requiere un sistema para estructurar jerárquicamente sus instalaciones físicas y administrar los catálogos bibliográficos alojados en sus estanterías:

1. **Estructura jerárquica organizacional (Árbol General n-ario):**  
   La biblioteca se compone de diversas dependencias organizadas por niveles:
   $$\text{BIBLIOTECA} \longrightarrow \text{ÁREA} \longrightarrow \text{ESTANTE}$$
   Cada nodo de la jerarquía puede poseer cualquier número arbitrario de subunidades respetando estrictamente esta jerarquía. Los nodos terminales de la organización son de tipo `ESTANTE`.

2. **Catálogo bibliográfico indexado (Árbol Binario de Búsqueda - ABB):**  
   Cada estante físico aloja su propia colección de libros. Para garantizar búsquedas, inserciones y eliminaciones eficientes en tiempo promedio $O(\log n)$, cada `ESTANTE` encapsula un **Árbol Binario de Búsqueda (ABB)** ordenado rigurosamente por el **código del libro** (clave primaria entera positiva, sin valores duplicados).

### 1.2. Objetivos académicos y restricciones
* Modelar ambas estructuras empleando principios de Programación Orientada a Objetos y **recursividad pura**.
* **Restricción estricta:** No se permite el uso de estructuras arbóreas o de ordenamiento automático de la API de Java (`TreeSet`, `TreeMap`, `PriorityQueue`, `NavigableSet`, `NavigableMap`, `Collections.sort` o `List.sort`).
* Todas las operaciones sobre los árboles (inserción, recorridos, podas, cálculo de altura, conteo de hojas y eliminación) deben resolverse mediante algoritmos recursivos definiendo claramente casos base y casos recursivos.

---

## 2. Arquitectura del software y diseño modular

El proyecto está organizado en 4 paquetes siguiendo separación de responsabilidades:

```
Deber-Arboles-ChicoJuan/
├── src/
│   ├── enums/
│   │   ├── TipoUnidad.java         # Tipos de nodo jerárquico y regla de descendencia
│   │   └── TipoRecorrido.java      # Estrategias de recorrido: PREORDEN, INORDEN, POSORDEN
│   ├── modelo/
│   │   ├── Libro.java              # Entidad libro con validación y clave inmutable
│   │   └── UnidadBiblioteca.java   # Unidad jerárquica (código normalizado en mayúsculas)
│   ├── negocio/
│   │   ├── NodoUnidad.java         # Nodo n-ario (lista de hijos y catálogo si es estante)
│   │   ├── NodoLibro.java          # Nodo binario (libro, hijo izquierdo, hijo derecho)
│   │   ├── ArbolBiblioteca.java    # Administrador del árbol general n-ario
│   │   └── CatalogoABB.java        # Administrador del ABB con retos algorítmicos
│   └── app/
│       ├── DatosPrueba.java        # Generador de la biblioteca inicial de prueba
│       ├── Main.java               # Menú interactivo por consola (Scanner)
│       └── PruebasBiblioteca.java  # Suite de 41 pruebas automatizadas
├── docs/
│   └── DIAGRAMAS.md                # Diagramas de clases, jerarquía y flujos
├── README.md                       # Documentación técnica general
├── TRAZAS.md                       # Trazas analíticas a mano y comparativa con el programa
└── .gitignore
```

### 2.1. Responsabilidad de clases

| Paquete | Clase / Tipo | Responsabilidad principal |
|---|---|---|
| `enums` | `TipoUnidad` | Define `BIBLIOTECA`, `AREA` y `ESTANTE`, y valida compatibilidad jerárquica con `puedeTenerHijo()`. |
| `enums` | `TipoRecorrido` | Define los recorridos en profundidad `PREORDEN`, `INORDEN` y `POSORDEN`. |
| `modelo` | `Libro` | Representa un libro. Clave `codigo` inmutable (`final`), valida año (1900–2026) y textos no vacíos. |
| `modelo` | `UnidadBiblioteca` | Almacena código en mayúsculas, nombre descriptivo y tipo jerárquico. |
| `negocio` | `NodoUnidad` | Nodo del árbol general con lista polimórfica de hijos y referencia a `CatalogoABB` (solo en estantes). |
| `negocio` | `NodoLibro` | Nodo binario con referencias `izquierdo` y `derecho` al subárbol menor y mayor. |
| `negocio` | `ArbolBiblioteca` | Lógica del árbol general: inserción validada, búsqueda, cálculo de altura, hojas, nivel y total de libros. |
| `negocio` | `CatalogoABB` | Lógica del ABB: inserción ordenada, búsqueda, eliminación (3 casos), trazas paso a paso y retos. |
| `app` | `DatosPrueba` | Construye el árbol general inicial con 7 unidades, 4 hojas y 13 libros cargados. |
| `app` | `Main` | Menú interactivo por consola para manipulación integral de la biblioteca. |
| `app` | `PruebasBiblioteca` | Batería de pruebas automatizadas con 13 casos de prueba y 41 aserciones. |

---

## 3. Estructura de datos inicial

El sistema se inicializa con la siguiente configuración oficial de prueba:

```
BIB - Biblioteca FISEI (raíz)
├── SW - Software (Área)
│   ├── SW-E1 - Estante 1 de Software (Estante) -> [60, 35, 80, 20, 45, 70, 90, 40, 75]
│   └── SW-E2 - Estante 2 de Software (Estante) -> [15, 10, 25]
├── TI - Tecnologias de la Informacion (Área)
│   └── TI-E1 - Estante 1 de TI (Estante)       -> [50]
└── TEL - Telecomunicaciones (Área, sin estantes)
```

### Métricas esperadas del árbol inicial
* **Total de unidades:** 7
* **Altura del árbol general:** 2 (medida en aristas)
* **Cantidad de hojas:** 4 (`SW-E1`, `SW-E2`, `TI-E1`, `TEL`)
* **Total de libros:** 13 libros distribuidos entre los 3 estantes

### Recorridos del catálogo SW-E1

| Recorrido | Secuencia de códigos |
|---|---|
| **Preorden** ($R \to I \to D$) | `60 35 20 45 40 80 70 75 90` |
| **Inorden** ($I \to R \to D$)  | `20 35 40 45 60 70 75 80 90` *(orden estrictamente ascendente)* |
| **Posorden** ($I \to D \to R$) | `20 40 45 35 75 70 90 80 60` |

---

## 4. Operaciones implementadas

### 4.1. Árbol General (`ArbolBiblioteca`)
* `boolean agregar(String codigoPadre, UnidadBiblioteca nueva)`: inserta una unidad validando unicidad de código y la regla `BIBLIOTECA -> AREA -> ESTANTE`.
* `NodoUnidad buscar(String codigo)`: búsqueda recursiva por código sin distinción de mayúsculas/minúsculas.
* `int altura()`: altura del árbol en aristas (caso base: hoja = 0, árbol vacío = -1).
* `int contarHojas()`: cantidad de nodos terminales sin descendientes.
* `int nivel(String codigo)`: distancia en aristas desde la raíz hasta el nodo indicado (-1 si no existe).
* `String mostrarJerarquia()`: representación visual jerárquica preorden con indentación de 2 espacios por nivel.
* `int contarLibrosTotal()`: acumulación recursiva del total de libros alojados en todos los estantes.

### 4.2. Catálogo ABB (`CatalogoABB`)
* `boolean insertar(Libro libro)`: inserción ordenada. Rechaza claves duplicadas retornando `false`.
* `Libro buscar(int codigo)`: búsqueda binaria recursiva $O(\log n)$.
* `int contar()`: conteo recursivo de nodos en el ABB.
* `boolean modificar(int codigo, String titulo, String autor, int anio)`: actualiza datos manteniendo inmutable la clave de ordenamiento.
* `boolean eliminar(int codigo)`: eliminación recursiva que cubre los 3 casos canónicos:
  1. **Nodo hoja:** se desvincula fijando la referencia del padre a `null`.
  2. **Nodo con un solo hijo:** se sustituye directamente por su único descendiente.
  3. **Nodo con dos hijos:** se reemplaza por su **sucesor inorden** (el menor del subárbol derecho) y se elimina recursivamente dicho sucesor.
* `String trazaRecorrido(TipoRecorrido tipo)`: traza didáctica y paso a paso que detalla entradas a nodos (`"entra X"`), visitas (`"VISITA X"`) y retrocesos por nulo (`"null -> regresa"`), finalizando con la secuencia generada.

### 4.3. Retos algorítmicos implementados
* `int contarEnRango(int a, int b)`: cuenta libros cuyos códigos se ubiquen en $[a, b]$ aplicando **poda de ramas**:
  * Si $\text{código} < a$, no explora el subárbol izquierdo.
  * Si $\text{código} > b$, no explora el subárbol derecho.
* `List<Integer> caminoBusqueda(int codigo)`: registra el camino de búsqueda desde la raíz hasta el nodo encontrado o hasta el punto donde se determina su inexistencia.
* `boolean esABBValido()`: verifica la integridad del ABB pasando cotas $(\min, \max)$ descendentes en el árbol, asegurando que ningún subnodo viole la propiedad de búsqueda respecto a todos sus ancestros.
* **Extra:** `static CatalogoABB desdePreorden(int[] preorden)`: reconstruye de manera determinista un ABB a partir de su secuencia preorden sin realizar inserciones estándar consecutivas.

---

## 5. Compilación, ejecución y pruebas

### 5.1. Requisitos
* JDK 17 o superior instalado.
* Terminal con soporte para comandos estándar o PowerShell.

### 5.2. Compilación
Desde la raíz del proyecto (`Deber-Arboles-ChicoJuan/`):

```bash
# Crear directorio de compilación
mkdir out

# Compilar todos los paquetes en Windows / Linux / macOS
javac -d out src/enums/*.java src/modelo/*.java src/negocio/*.java src/app/*.java
```

### 5.3. Ejecución del programa interactivo
```bash
java -cp out app.Main
```

### 5.4. Ejecución de la suite de pruebas automatizadas
```bash
java -cp out app.PruebasBiblioteca
```

La suite evalúa **13 grupos de prueba** y **41 verificaciones unitarias**:
1. Biblioteca solo con la raíz (altura 0, 1 hoja).
2. Biblioteca inicial (altura 2, 4 hojas, 13 libros).
3. Niveles de unidades (`BIB`, `sw`, `SW-E2`, nodo inexistente `XYZ`).
4. Inserción inválida en jerarquía (estante bajo biblioteca, código repetido).
5. Recorridos de SW-E1 (preorden, inorden, posorden y traza explicativa).
6. Rechazo de códigos duplicados en ABB.
7. Retos: camino de búsqueda y conteo con poda en rango.
8. Eliminaciones compuestas: hoja, un hijo y dos hijos (sucesor inorden).
9. Búsqueda, modificación y eliminación sobre códigos inexistentes.
10. Validaciones de dominio en Libro (`IllegalArgumentException` en código, año y título).
11. Detección de nieto mal ubicado con `esABBValido()`.
12. Reconstrucción de árbol con `desdePreorden()`.
13. Modificación de atributos sin alteración del código primario.

### 5.5. Captura de PruebasBiblioteca

Salida completa de `java -cp out app.PruebasBiblioteca`:

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

---

## 6. Documentación técnica complementaria

* [docs/DIAGRAMAS.md](docs/DIAGRAMAS.md): diagramas de clases, árbol general de la biblioteca, estructura del ABB de SW-E1 y flujos de eliminación de nodos.
* [TRAZAS.md](TRAZAS.md): resolución analítica a mano del ABB con los códigos personales y comparación rigurosa con la salida del programa.
