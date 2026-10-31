# Guía de Laboratorio: Aplicación `DSY1102 Inventory`

Aplicación de escritorio para la gestión de inventario, desarrollada con **Java** y **JavaFX**. Es un proyecto educativo de referencia para la asignatura **DSY1102 - Programación Orientada al Objeto** (Duoc UC), que sirve como punto de partida práctico para explorar el desarrollo de interfaces gráficas con **FXML**, la arquitectura **Modelo-Vista-Controlador (MVC)**, el manejo de eventos, las operaciones **CRUD** y la persistencia de datos en archivos **JSON** mediante **Gson**.

---

## Tabla de Contenidos

- [Descripción](#descripción)
  - [Objetivos de Aprendizaje](#objetivos-de-aprendizaje)
- [Tecnologías y Requisitos](#tecnologías-y-requisitos)
- [Cómo Ejecutar el Proyecto](#cómo-ejecutar-el-proyecto)
  - [Opción 1: Mediante Maven Wrapper (Línea de comandos)](#opción-1-mediante-maven-wrapper-línea-de-comandos)
  - [Opción 2: Desde tu IDE (IntelliJ IDEA / Eclipse / VS Code)](#opción-2-desde-tu-ide-intellij-idea--eclipse--vs-code)
- [Conceptos Básicos de JavaFX](#conceptos-básicos-de-javafx)
  - [¿Qué es JavaFX? ¿Y OpenJFX?](#qué-es-javafx-y-openjfx)
  - [El ciclo de vida: Application, launch() y start()](#el-ciclo-de-vida-application-launch-y-start)
  - [Stage y Scene: la analogía del teatro](#stage-y-scene-la-analogía-del-teatro)
  - [FXML y Scene Builder](#fxml-y-scene-builder)
  - [Contenedores (layouts) más comunes](#contenedores-layouts-más-comunes)
  - [Controles más comunes](#controles-más-comunes)
  - [Manejo de eventos](#manejo-de-eventos)
- [Construcción del proyecto](#construcción-del-proyecto)
  - [1. Crear el proyecto](#1-crear-el-proyecto)
  - [2. Organizar el proyecto](#2-organizar-el-proyecto)
  - [3. Configurar module-info.java](#3-configurar-module-infojava)
  - [4. Crear el modelo Product](#4-crear-el-modelo-product)
  - [5. Crear la vista inventory-view.fxml](#5-crear-la-vista-inventory-viewfxml)
  - [6. Crear el controlador InventoryController](#6-crear-el-controlador-inventorycontroller)
  - [7. Crear el servicio ProductService](#7-crear-el-servicio-productservice)
  - [8. Modificar InventoryController](#8-modificar-inventorycontroller)
  - [9. Crear la vista product-view.fxml](#9-crear-la-vista-product-viewfxml)
  - [10. Crear el controlador ProductController](#10-crear-el-controlador-productcontroller)
  - [11. Integrar la navegación a la vista product-view.fxml](#11-integrar-la-navegación-a-la-vista-product-viewfxml)
  - [12. Aplicar estilos con BootstrapFX y CSS propio](#12-aplicar-estilos-con-bootstrapfx-y-css-propio)
  - [13. Persistir los datos con Gson](#13-persistir-los-datos-con-gson)
  - [14. Crear el modelo Category y su servicio](#14-crear-el-modelo-category-y-su-servicio)
  - [15. Relacionar Product con Category](#15-relacionar-product-con-category)
  - [16. Gestionar las categorías desde la aplicación](#16-gestionar-las-categorías-desde-la-aplicación)
  - [17. Cambiar el ícono de las ventanas (opcional)](#17-cambiar-el-ícono-de-las-ventanas-opcional)
  - [18. Revisar la estructura final del proyecto](#18-revisar-la-estructura-final-del-proyecto)
- [Conceptos Avanzados](#conceptos-avanzados)
  - [Inyección de dependencias](#inyección-de-dependencias)
  - [Repositorio genérico](#repositorio-genérico)
  - [Aplicar DI al proyecto](#aplicar-di-al-proyecto)
  - [Antes y después](#antes-y-después)
- [Autor](#autor)
- [Licencia](#licencia)

---

## Descripción

Este proyecto tiene como propósito guiar a los estudiantes en la transición desde aplicaciones basadas en consola hacia aplicaciones con **Interfaz Gráfica de Usuario (GUI)**. A través de un caso práctico de gestión de inventario, se exploran los fundamentos del diseño de interfaces, el manejo de eventos y la separación de responsabilidades en el paradigma orientado a objetos.

### Objetivos de Aprendizaje

- **Estructuración con Maven y Módulos**: Comprender la organización de un proyecto Java modular (`module-info.java`) y la gestión de dependencias con Maven.
- **Patrón MVC (Model-View-Controller)**:
  - **Vista (View)**: Definición declarativa de interfaces gráficas usando archivos **FXML** y diseño visual.
  - **Controlador (Controller)**: Captura y gestión de eventos de usuario (`@FXML`, manejadores de eventos y enlace con la vista).
  - **Modelo (Model)**: Modelamiento de entidades del dominio (productos, stock, categorías) aplicando encapsulamiento, herencia y colecciones.
- **Ciclo de vida de JavaFX**: Entender el rol de `Application`, `Stage`, `Scene` y `FXMLLoader`.
- **Navegación entre ventanas**: Abrir formularios en un `Stage` modal y compartir datos entre controladores mediante una capa de servicio.
- **Operaciones CRUD**: Crear, consultar, editar y eliminar productos desde la interfaz, con validación de los datos ingresados.
- **Relaciones entre entidades**: Separar la categoría en su propia entidad y relacionarla con los productos mediante su identificador, evitando eliminar categorías que estén en uso.
- **Inyección de dependencias y repositorio genérico**: Entender cómo reducir el acoplamiento entre capas, usar clases genéricas para evitar código duplicado y escribir pruebas con repositorios falsos (sección de conceptos avanzados).
- **Estilos con CSS y `BootstrapFX`**: Aplicar clases de estilo predefinidas y hojas de estilo propias para separar la apariencia de la lógica de la aplicación.
- **Persistencia de Datos con `Gson`**: Serializar y deserializar las entidades del dominio a formato **JSON** mediante la biblioteca **Gson**, de modo que el inventario sobreviva entre ejecuciones de la aplicación. Se abordan la lectura y escritura de archivos, el manejo de colecciones genéricas (`TypeToken`) y la separación entre la capa de persistencia y la lógica de negocio.
- **Buenas prácticas de POO**: Aplicar cohesión, bajo acoplamiento y validaciones en la capa de negocio.

---

## Tecnologías y Requisitos

- **Java Development Kit (JDK)**: Versión 21 o superior recomendada.
- **JavaFX**: Versión 21 (`javafx-controls`, `javafx-fxml`).
- **[BootstrapFX](https://github.com/kordamp/bootstrapfx)**: Versión 0.4.0, para aplicar estilos predefinidos a los controles.
- **[Gson](https://github.com/google/gson)**: Versión 2.11.0, para la persistencia de datos en formato JSON.
- **Apache Maven**: 3.8+ (incluye Maven Wrapper `mvnw`).
- **Herramientas sugeridas**:
  - **IDE**: IntelliJ IDEA, Eclipse o Visual Studio Code (con Extension Pack for Java).
  - **[Scene Builder](https://openjfx.io/scene-builder/)**: Herramienta visual recomendada para diseñar y editar interfaces `.fxml`.

---

## Cómo Ejecutar el Proyecto

Puedes compilar y ejecutar la aplicación mediante cualquiera de las siguientes alternativas:

### Opción 1: Mediante Maven Wrapper (Línea de comandos)

En Windows (PowerShell / CMD):

```powershell
.\mvnw.cmd clean javafx:run
```

En Linux / macOS:

```bash
./mvnw clean javafx:run
```

### Opción 2: Desde tu IDE (IntelliJ IDEA / Eclipse / VS Code)

1. Abre la carpeta del proyecto como proyecto Maven.
2. Verifica que el SDK del proyecto esté configurado con una versión de Java compatible (JDK 21).
3. Ejecuta la clase `Launcher` (`cl.duoc.inventory.Launcher`) o `InventoryApplication` (`cl.duoc.inventory.InventoryApplication`).

> **Tip pedagógico:** La clase `Launcher` contiene un método `main` que llama a `Application.launch(...)` sin heredar directamente de `Application`. Esto evita inconvenientes de carga de módulos de JavaFX al ejecutar directamente desde ciertos entornos de desarrollo.

---

## Conceptos Básicos de JavaFX

Antes de escribir código, conviene manejar el vocabulario mínimo de JavaFX. Esta sección es un repaso de referencia: puedes volver a ella cada vez que aparezca un término nuevo durante el laboratorio.

### ¿Qué es JavaFX? ¿Y OpenJFX?

**JavaFX** es la plataforma oficial de Java para construir aplicaciones de **escritorio con interfaz gráfica**. Reemplaza a las bibliotecas más antiguas (AWT y Swing) y aporta tres cosas que usaremos de forma intensiva:

- Un catálogo moderno de **controles** (botones, tablas, formularios).
- Un lenguaje declarativo, **FXML**, para describir la interfaz en archivos separados del código Java.
- Hojas de estilo **CSS**, para separar la apariencia de la lógica.

**OpenJFX** es el nombre del *proyecto open source* que desarrolla JavaFX. La distinción importa por un detalle práctico: desde **Java 11**, JavaFX ya **no viene incluido en el JDK**. Es una dependencia externa que hay que declarar explícitamente, y es exactamente lo que hace el `pom.xml` de este proyecto:

```xml
<dependency>
    <groupId>org.openjfx</groupId>
    <artifactId>javafx-controls</artifactId>
    <version>${javafx.version}</version>
</dependency>
```

> **Nota:** *JavaFX* es la tecnología; *OpenJFX* es la implementación que descargamos con Maven. En la práctica se usan como sinónimos.

### El ciclo de vida: `Application`, `launch()` y `start()`

Toda aplicación JavaFX hereda de la clase `Application` y sobrescribe el método `start(Stage stage)`. El arranque ocurre en tres pasos:

1. `Application.launch(...)` inicializa la plataforma gráfica (el *toolkit* de JavaFX).
2. JavaFX crea el **Stage principal** y lo entrega como parámetro.
3. Se ejecuta `start(...)`, donde nosotros construimos y mostramos la interfaz.

```java
public class InventoryApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                InventoryApplication.class.getResource("inventory-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 640, 480);
        stage.setTitle("DSY1102 Inventario");
        stage.setScene(scene);
        stage.show();
    }
}
```

Observa que **nunca llamamos a `start()` directamente**: lo invoca JavaFX por nosotros. Nuestro `main` solo delega en `launch(...)` (ver el *tip* sobre `Launcher` en la sección anterior).

### `Stage` y `Scene`: la analogía del teatro

Estos dos conceptos se confunden con frecuencia. La metáfora del teatro, que da nombre a las clases, ayuda a fijarlos:

| Concepto | Analogía | Qué es en JavaFX |
| :--- | :--- | :--- |
| `Stage` | El **escenario** | La ventana del sistema operativo: tiene título, tamaño, bordes y botones de minimizar y cerrar. |
| `Scene` | La **escena** (el acto) | El contenido que se muestra dentro de la ventana. Una misma ventana puede intercambiar escenas. |
| `Node` | Los **actores y la utilería** | Cada elemento visual: un botón, una etiqueta, una tabla, un contenedor. |

La relación es jerárquica y siempre la misma:

```text
Stage (ventana)
└── Scene (contenido)
    └── Root Node (contenedor raíz, p. ej. VBox)
        ├── Label
        ├── TextField
        └── Button
```

Esa jerarquía de nodos se llama **Scene Graph** (grafo de escena): un árbol donde cada nodo tiene un padre y, si es un contenedor, varios hijos. Entender el Scene Graph explica muchos comportamientos de la interfaz; por ejemplo, por qué ocultar un contenedor esconde también a todos sus hijos, o por qué un `Node` solo puede tener un padre a la vez.

Una aplicación puede abrir **varios `Stage`** (típicamente para diálogos modales), pero cada `Stage` muestra **una sola `Scene`** a la vez.

### FXML y Scene Builder

En lugar de crear los controles a mano en Java, describimos la vista en un archivo **FXML** (un XML especializado):

```xml
<VBox xmlns:fx="http://javafx.com/fxml"
      fx:controller="cl.duoc.inventory.controller.InventoryController"
      spacing="10">
    <Label fx:id="mensajeLabel" text="Inventario vacío" />
    <Button text="Agregar producto" onAction="#onAgregarProducto" />
</VBox>
```

Tres atributos son clave:

- `fx:controller` — indica **qué clase Java** maneja esta vista.
- `fx:id` — da un **nombre** a un control, para poder inyectarlo en el controlador.
- `onAction` — conecta un evento con un **método** del controlador (nota el prefijo `#`).

`FXMLLoader` lee ese archivo, construye el árbol de nodos y lo conecta con el controlador. **Scene Builder** es una herramienta visual que genera y edita estos archivos arrastrando componentes, sin escribir XML a mano.

> **Nota:** FXML materializa la **V** del patrón MVC. La vista queda en un archivo aparte y el controlador no contiene instrucciones de maquetación.

### Contenedores (layouts) más comunes

Los contenedores deciden **cómo se posicionan** sus hijos. Elegir bien el contenedor evita tener que calcular coordenadas a mano:

| Contenedor | Distribución | Uso típico |
| :--- | :--- | :--- |
| `VBox` | Apila en columna (vertical) | Formularios simples. |
| `HBox` | Apila en fila (horizontal) | Barras de botones. |
| `GridPane` | Cuadrícula de filas y columnas | Formularios etiqueta/campo. |
| `BorderPane` | Cinco zonas: top, bottom, left, right, center | Estructura general de la ventana. |
| `AnchorPane` | Ancla los hijos a los bordes | Diseños fijos (es el que genera Scene Builder por defecto). |
| `StackPane` | Apila los hijos uno sobre otro, centrados | Superponer elementos. |

### Controles más comunes

Estos son los controles que aparecerán en la aplicación de inventario:

| Control | Para qué sirve | Métodos frecuentes |
| :--- | :--- | :--- |
| `Label` | Mostrar texto no editable | `setText(...)`, `getText()` |
| `Button` | Ejecutar una acción | `setOnAction(...)`, `setDisable(...)` |
| `TextField` | Entrada de texto en una línea | `getText()`, `clear()` |
| `TextArea` | Entrada de texto multilínea | `getText()`, `appendText(...)` |
| `ComboBox<T>` | Seleccionar un valor de una lista | `getItems()`, `getValue()` |
| `ChoiceBox<T>` | Alternativa más simple al `ComboBox` | `getItems()`, `getValue()` |
| `CheckBox` | Opción booleana (sí/no) | `isSelected()` |
| `RadioButton` | Opción exclusiva dentro de un `ToggleGroup` | `isSelected()`, `setToggleGroup(...)` |
| `ListView<T>` | Lista de elementos seleccionables | `getItems()`, `getSelectionModel()` |
| `TableView<T>` | Tabla con columnas (`TableColumn`) | `setItems(...)`, `getColumns()` |
| `Spinner<T>` | Valor numérico con incremento y decremento | `getValue()` |
| `Alert` | Diálogo de información, confirmación o error | `showAndWait()` |

Los controles que manejan colecciones (`ComboBox`, `ListView`, `TableView`) trabajan con una **`ObservableList`**: una lista que *notifica* sus cambios. Si agregas un producto a la lista, la tabla se actualiza sola, sin que tengas que redibujarla.

```java
ObservableList<Producto> productos = FXCollections.observableArrayList();
tablaProductos.setItems(productos);
productos.add(new Producto("Teclado", 15000)); // la tabla se refresca automáticamente
```

### Manejo de eventos

Un **evento** es una notificación de que algo ocurrió: un clic, una tecla, un cambio de selección. El objeto que responde a un evento es un **manejador** (`EventHandler`). En JavaFX hay dos formas de declararlo.

**1. Desde FXML (la que usaremos en MVC):** el atributo `onAction` apunta a un método del controlador anotado con `@FXML`.

```java
public class InventoryController {

    @FXML
    private Label mensajeLabel;      // inyectado según el fx:id del FXML

    @FXML
    private TextField nombreField;

    @FXML
    protected void onAgregarProducto(ActionEvent event) {
        mensajeLabel.setText("Producto agregado: " + nombreField.getText());
    }
}
```

**2. Desde código Java,** registrando el manejador con una expresión lambda:

```java
agregarButton.setOnAction(event -> mensajeLabel.setText("Clic recibido"));
```

Puntos a tener en cuenta:

- La anotación `@FXML` permite que `FXMLLoader` acceda a campos y métodos **privados** mediante *reflexión*. Por eso el paquete del controlador necesita una línea `opens ... to javafx.fxml;` en `module-info.java`.
- El método manejador recibe un objeto de evento (`ActionEvent`, `KeyEvent`, `MouseEvent`, etc.) con el detalle de lo ocurrido. Si no lo necesitas, puedes declarar el método sin parámetros.
- El método `initialize()` del controlador se ejecuta **una sola vez**, después de que todos los campos `@FXML` fueron inyectados. Es el lugar correcto para cargar datos iniciales y configurar las columnas de una tabla; nunca el constructor, porque allí los controles todavía son `null`.
- Los eventos más usados son `onAction` (botones y campos de texto), `onMouseClicked`, `onKeyPressed` y los *listeners* de selección (`getSelectionModel().selectedItemProperty().addListener(...)`).

---

## Construcción del proyecto

### 1. Crear el proyecto

Comenzamos creando el proyecto base con IntelliJ IDEA, que generará una aplicación JavaFX mínima sobre la cual construiremos el resto de la guía.

Inicia IntelliJ IDEA y, desde la pantalla de bienvenida, selecciona **New Project**. Luego, completa la información del proyecto:

- **Generators:** `JavaFX`
- **Name:** `DSY1102 Inventory`
- **Location:** Selecciona la ubicación donde quieras guardar tu proyecto.
- **Create Git repository:** Actívalo para iniciar un repositorio Git en blanco (*opcional*).
- **Language:** `Java`
- **Build system:** `Maven`
- **Group:** Identificador único del proyecto. Usualmente, es el dominio de la organización al revés, por ejemplo `cl.duoc`
- **Artifact:** `inventory`

Presiona **Next** para continuar.

En la siguiente ventana podemos agregar dependencias adicionales. Por el momento no seleccionaremos ninguna: presiona **Create**.

IntelliJ generará la estructura inicial de nuestro proyecto:

```plaintext
dsy1102-inventory/
├── .mvn/                                  # Configuración de Maven Wrapper
├── src/
│   └── main/
│       ├── java/                          # Código fuente Java
│       │   ├── cl/duoc/inventory/
│       │   │   ├── HelloApplication.java  # Inicio de la aplicación JavaFX
│       │   │   ├── HelloController.java   # Controlador de la vista
│       │   │   └── Launcher.java          # Punto de entrada de la aplicación
│       │   └── module-info.java           # Declaración del módulo Java
│       └── resources/                     # Recursos de la aplicación
│           └── cl/duoc/inventory/
│               └── hello-view.fxml        # Diseño de la interfaz gráfica
├── mvnw                                   # Maven Wrapper para Linux/macOS
├── mvnw.cmd                               # Maven Wrapper para Windows
└── pom.xml                                # Configuración y dependencias de Maven
```

> **Concepto:** los archivos `mvnw` y `mvnw.cmd` permiten ejecutar las tareas de Maven usando la versión configurada para el proyecto, sin depender de que cada desarrollador tenga instalada globalmente esa misma versión.

Veamos los archivos contenidos en `src/main/java/` y `src/main/resources/`:

- **`HelloApplication.java`:** clase principal de la aplicación JavaFX. Inicializa la interfaz gráfica, carga la vista FXML y configura la ventana principal (`Stage`).
- **`HelloController.java`:** controla el comportamiento de la vista. Gestiona los eventos de los componentes gráficos, como los clics en botones, y coordina las acciones del usuario.
- **`Launcher.java`:** contiene el método `main()` que inicia la aplicación. Se utiliza como punto de entrada, especialmente en proyectos JavaFX modulares.
- **`hello-view.fxml`:** define la estructura visual de la interfaz mediante FXML. Permite declarar controles, contenedores, propiedades y la asociación con su controlador sin construir toda la interfaz mediante código Java.

**Verificación:** Ejecuta la aplicación con `.\mvnw.cmd clean javafx:run`. Deberías ver una ventana de ejemplo con un botón **Hello!**.

### 2. Organizar el proyecto

Nuestra aplicación no necesita todos los archivos que generó IntelliJ. En este paso los reorganizamos y preparamos las carpetas del patrón MVC. Primero, haz los siguientes cambios iniciales:

1. **Renombrar `HelloApplication.java`:** En el explorador de archivos del proyecto, selecciona el archivo `HelloApplication`, hazle clic con el botón derecho y luego, en **Rename**. En el cuadro de diálogo, escribe `InventoryApplication` y presiona **Refactor**.
2. **Eliminar `HelloController.java` y `hello-view.fxml`:** Selecciona independientemente cada archivo, y presiona el botón *Suprimir* o haz clic con el botón derecho y, luego, en **Delete**. En el siguiente cuadro de diálogo, presiona **Refactor**. Como estos archivos aún tienen dependencias en el proyecto, IntelliJ te va a preguntar si estás seguro, mostrando los conflictos existentes; como no los vamos a usar, presiona **Refactor anyway**.

A continuación, crea los paquetes necesarios para implementar el patrón **MVC**. En la ruta `src/main/java/cl/duoc/inventory/`, haz clic con el botón derecho, selecciona **New** → **New Package** y escribe el nombre `cl.duoc.inventory.controller`. Repite la acción para crear tres paquetes adicionales: `cl.duoc.inventory.model`, `cl.duoc.inventory.repository` y `cl.duoc.inventory.service`.

Luego, en la ruta `src/main/resources/cl/duoc/inventory/`, haz clic derecho, selecciona **New** → **New Directory** y crea las carpetas `view` y `css`.

Nuestro proyecto quedará con la siguiente estructura:

```plaintext
dsy1102-inventory/
├── .mvn/
├── src/
│   └── main/
│       ├── java/ 
│       │   ├── cl/duoc/inventory/
│       │   │   ├── controller/                # Controladores de las vistas
│       │   │   ├── model/                     # Modelos y datos de la aplicación
│       │   │   ├── repository/                # Repositorio de la aplicación
│       │   │   ├── service/                   # Servicios de la aplicación
│       │   │   ├── InventoryApplication.java  # Inicio de la aplicación JavaFX
│       │   │   └── Launcher.java              # Punto de entrada de la aplicación
│       │   └── module-info.java
│       └── resources/
│           └── cl/duoc/inventory/
│               ├── css/                       # Hojas de estilo CSS
│               └── view/                      # Vistas definidas en FXML
├── mvnw
├── mvnw.cmd
└── pom.xml
```

**Verificación:** Compara el explorador de archivos de IntelliJ con la estructura anterior. Por ahora no podemos ejecutar la aplicación, porque `InventoryApplication` sigue buscando la vista `hello-view.fxml` que eliminamos. Lo corregiremos en el paso 5.

### 3. Configurar `module-info.java`

El archivo `module-info.java` es el **descriptor del módulo** de nuestra aplicación (una característica de Java 9 en adelante). Se ubica en la raíz del código fuente y declara de forma explícita **qué dependencias necesita el módulo y qué paquetes propios expone hacia el exterior**. Esto nos da un control estricto sobre la visibilidad del código y evita los conflictos tradicionales del *classpath*.

Para que JavaFX pueda utilizar las clases que crearemos en `cl.duoc.inventory.controller` y `cl.duoc.inventory.model`, debemos declararlas en el archivo. Modifica `src/main/java/module-info.java` para que quede así:

```java
module cl.duoc.inventory {
    requires javafx.controls;
    requires javafx.fxml;

    opens cl.duoc.inventory to javafx.fxml;
    opens cl.duoc.inventory.controller to javafx.fxml;
    opens cl.duoc.inventory.model to javafx.base;

    exports cl.duoc.inventory;
}
```

Veamos qué declara cada línea:

- **`requires javafx.controls;` y `requires javafx.fxml;`:** indican las **dependencias** del módulo. La primera da acceso a los controles (`Button`, `Label`, `TableView`, etc.) y la segunda a `FXMLLoader`. Si falta alguna, el compilador no reconocerá las clases de ese paquete.
- **`opens cl.duoc.inventory.controller to javafx.fxml;`:** permite que `FXMLLoader` use *reflexión* sobre nuestros controladores: crear la instancia e inyectar los campos anotados con `@FXML`, incluso si son `private`. La línea `opens cl.duoc.inventory to javafx.fxml;` cumple el mismo rol para el paquete principal.
- **`opens cl.duoc.inventory.model to javafx.base;`:** permite que JavaFX lea las propiedades de `Product` también mediante reflexión. Lo usaremos en el paso 6, cuando la tabla necesite obtener los valores de cada producto.
- **`exports cl.duoc.inventory;`:** hace visible el paquete que contiene a `InventoryApplication`, para que JavaFX pueda instanciar nuestra aplicación.

> **Concepto:** la diferencia entre `exports` y `opens` es que `exports` permite usar las clases públicas de un paquete desde otros módulos. `opens` habilita, además, la reflexión en tiempo de ejecución sobre todos sus miembros, incluidos los privados. La parte `to javafx.fxml` restringe ese acceso a un módulo específico, en lugar de abrirlo a todos.

> **Tip pedagógico:** si olvidas un `opens`, el proyecto compila sin problemas pero falla al ejecutarse, con un error que indica que el módulo no abre el paquete a `javafx.fxml` o `javafx.base`. Si te ocurre, revisa primero este archivo.

**Verificación:** Por ahora no podemos ejecutar la aplicación. Si el IDE advierte que los paquetes `controller` y `model` están vacíos, es normal: la advertencia desaparecerá cuando creemos sus clases en los pasos 4 y 6.

### 4. Crear el modelo `Product`

Toda aplicación de inventario necesita una entidad que represente lo que se va a administrar: en nuestro caso, un producto. Esta es la **M** del patrón MVC, el modelo, que describe los datos del dominio sin saber nada de la interfaz.

Crea la clase `Product` en la ruta `src/main/java/cl/duoc/inventory/model/` con el siguiente código:

```java
package cl.duoc.inventory.model;

import java.util.UUID;

public class Product {
    private final String id;
    private String name;
    private String description;
    private String category;
    private double price;
    private int quantity;

    public Product(String name, String description, String category, double price, int quantity) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.description = description;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
```

Veamos las decisiones de diseño de esta clase:

- **Encapsulamiento:** todos los atributos son `private` y solo se leen o modifican a través de *getters* y *setters*. Así, la clase controla cómo cambia su estado y, más adelante, podremos agregar validaciones dentro de los *setters* sin tocar el resto de la aplicación.
- **Identificador inmutable:** `id` es `final`, se asigna una sola vez en el constructor y no tiene *setter*, porque el identificador de un producto no debería cambiar durante su vida. El constructor tampoco lo recibe como parámetro: la propia clase lo genera con `UUID.randomUUID().toString()`, que retorna un identificador único basado en [UUID](https://es.wikipedia.org/wiki/Identificador_%C3%BAnico_universal), de modo que nunca dos productos compartirán el mismo `id` ni necesitamos llevar un contador.
- **Nombres de los *getters*:** siguen la convención JavaBeans (`getName()`, `getPrice()`, etc.). No es solo una cuestión de estilo: en el paso 6, la tabla usará esos nombres para leer los datos de cada producto.

> **Nota:** usamos `double` para el precio por simplicidad. En un sistema real que maneje dinero es preferible `BigDecimal`, que evita los errores de redondeo propios de los números de punto flotante.

**Verificación:** La clase `Product` aún no se usa en ninguna parte de la aplicación; la conectaremos con la tabla en el paso 6.

### 5. Crear la vista `inventory-view.fxml`

Ahora construimos la **V** del patrón MVC, la vista. Describiremos la ventana principal de forma declarativa en un archivo FXML, separada del código Java.

Crea el archivo `inventory-view.fxml` en la ruta `src/main/resources/cl/duoc/inventory/view/`. Una vez creado, verás algo como esto:

```xml
<?xml version="1.0" encoding="UTF-8"?>

<?import java.lang.*?>
<?import java.util.*?>
<?import javafx.scene.*?>
<?import javafx.scene.control.*?>
<?import javafx.scene.layout.*?>

<AnchorPane xmlns="http://javafx.com/javafx"
            xmlns:fx="http://javafx.com/fxml"
            fx:controller="CONTROLLER_NAME"
            prefHeight="400.0" prefWidth="600.0">

</AnchorPane>
```

Este código es la plantilla base y vacía de una interfaz gráfica escrita en FXML, el lenguaje basado en XML que utiliza JavaFX para definir vistas (UI) de forma separada de la lógica del negocio. Observa el atributo `fx:controller`: por ahora tiene un marcador (`CONTROLLER_NAME`) que reemplazaremos por la clase de nuestro controlador, el cual crearemos en el paso 6.

Reemplaza todo el contenido del archivo con el siguiente código:

```xml
<?xml version="1.0" encoding="UTF-8"?>

<?import javafx.geometry.Insets?>
<?import javafx.scene.control.*?>
<?import javafx.scene.layout.*?>
<BorderPane xmlns:fx="http://javafx.com/fxml"
            xmlns="http://javafx.com/javafx"
            fx:controller="cl.duoc.inventory.controller.InventoryController"
            prefHeight="600.0" prefWidth="900.0">
    <top>
        <MenuBar>
            <Menu text="Archivo">
                <Menu text="Productos">
                    <MenuItem text="Nuevo"
                              onAction="#handleNewProductClick"/>
                </Menu>
                <MenuItem text="Categorías"/>
                <SeparatorMenuItem/>
                <MenuItem text="Salir"/>
            </Menu>
        </MenuBar>
    </top>
    <center>
        <VBox spacing="15.0">
            <padding>
                <Insets top="10.0" right="24.0"
                        bottom="24.0" left="24.0"/>
            </padding>
            <HBox alignment="CENTER_LEFT" spacing="10.0">
                <Label text="Productos registrados"/>
                <Region HBox.hgrow="ALWAYS"/>
                <Button text="Nuevo producto"
                        onAction="#handleNewProductClick"/>
            </HBox>
            <TableView fx:id="tblProducts"
                       VBox.vgrow="ALWAYS">
                <columnResizePolicy>
                    <TableView fx:constant="CONSTRAINED_RESIZE_POLICY"/>
                </columnResizePolicy>
                <columns>
                    <TableColumn fx:id="colName"
                                 text="Nombre"
                                 prefWidth="220.0"/>
                    <TableColumn fx:id="colCategory"
                                 text="Categoría"
                                 prefWidth="160.0"/>
                    <TableColumn fx:id="colPrice"
                                 text="Precio"
                                 prefWidth="120.0"/>
                    <TableColumn fx:id="colQuantity"
                                 text="Cantidad"
                                 prefWidth="100.0"/>
                </columns>
                <placeholder>
                    <Label text="No hay productos registrados"/>
                </placeholder>
            </TableView>
        </VBox>
    </center>
</BorderPane>
```

Veamos la vista de arriba hacia abajo:

- **`<?import ...?>`:** equivalen a los `import` de Java. Sin ellos, FXML no sabría a qué clase corresponde cada etiqueta (`BorderPane`, `Label`, `Insets`, etc.).
- **`BorderPane`:** es el contenedor raíz y divide la ventana en zonas. Usamos `top` para la barra de menú y `center` para el contenido, que ocupa todo el espacio restante y se ajusta al cambiar el tamaño de la ventana. Su atributo `fx:controller` ahora apunta a la clase `InventoryController`, y `prefWidth` y `prefHeight` definen el tamaño preferido de la vista.
- **`MenuBar`:** cada `Menu` es un menú desplegable que puede contener elementos (`MenuItem`), submenús (como "Productos") y separadores (`SeparatorMenuItem`). "Categorías" y "Salir" todavía no tienen acción: se la asignaremos en los siguientes pasos.
- **`VBox` con `padding`:** apila sus hijos en una columna, separados por `spacing`. `Insets` define el margen interior en el orden arriba, derecha, abajo e izquierda; dejamos el superior más pequeño porque la barra de menú ya aporta espacio visual.
- **`HBox` y `Region`:** forman la fila del título. `Region` es un nodo vacío e invisible; con `HBox.hgrow="ALWAYS"` se estira para ocupar todo el espacio libre, lo que empuja el botón hacia el extremo derecho.
- **`onAction="#handleNewProductClick"`:** el elemento de menú "Nuevo" y el botón "Nuevo producto" ejecutan el mismo método del controlador. Un mismo manejador puede atender a varios controles.
- **`TableView`:** su `fx:id="tblProducts"` nos permitirá inyectarla en el controlador. `CONSTRAINED_RESIZE_POLICY` reparte el ancho disponible entre las columnas, de modo que siempre se ajusten a la tabla sin barra de desplazamiento horizontal. Cada `TableColumn` también tiene su `fx:id`, y el contenido de `placeholder` es lo que se muestra mientras la tabla no tiene filas.

> **Concepto:** `HBox.hgrow` y `VBox.vgrow` usan la notación `Clase.propiedad`. Se llaman *propiedades adjuntas* (*attached properties*) porque las define el contenedor padre y se aplican al hijo. Aquí, `VBox.vgrow="ALWAYS"` le indica a la tabla que use todo el alto disponible dentro del `VBox`.

Con la vista creada, debemos actualizar `InventoryApplication` para que cargue esta escena. Modifica el archivo `src/main/java/cl/duoc/inventory/InventoryApplication.java`: corrige la ruta que recibe `FXMLLoader`, cambia el título del `Stage` por `"DSY1102 Inventario"` y define el tamaño de la ventana al crear la escena con `new Scene(...)`:

```java
package cl.duoc.inventory;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class InventoryApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                InventoryApplication.class.getResource("view/inventory-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 600);
        stage.setTitle("DSY1102 Inventario");
        stage.setScene(scene);
        stage.show();
    }
}
```

Veamos qué hace cada línea:

- **`getResource("view/inventory-view.fxml")`:** busca el archivo con una ruta **relativa al paquete de la clase** (`cl/duoc/inventory/`), por lo que apunta a `resources/cl/duoc/inventory/view/inventory-view.fxml`. Si la ruta tiene un error, `getResource` no encuentra el archivo y la aplicación falla al iniciar (el mensaje típico es `Location is not set`).
- **`fxmlLoader.load()`:** lee el FXML, construye el árbol de nodos, crea el controlador indicado en `fx:controller` y le inyecta los campos `@FXML`. Retorna el nodo raíz de la vista (nuestro `BorderPane`), que pasa a ser la raíz de la `Scene`.
- **`new Scene(..., 900, 600)`:** define el ancho y el alto de la escena en píxeles, y coinciden con el tamaño preferido declarado en la vista.
- **`setTitle(...)`, `setScene(...)` y `show()`:** configuran el `Stage`: le ponen título a la ventana, le asignan la escena y la hacen visible.
- **`throws IOException`:** es necesario porque `load()` puede fallar al leer el archivo FXML.

**Verificación:** Todavía no podemos ejecutar la aplicación: la vista hace referencia a `InventoryController`, que crearemos en el siguiente paso.

### 6. Crear el controlador `InventoryController`

El controlador es la **C** del patrón MVC: conecta la vista con el modelo y responde a las acciones del usuario. Crea la clase `InventoryController` en la ruta `src/main/java/cl/duoc/inventory/controller/` con el siguiente código:

```java
package cl.duoc.inventory.controller;

import cl.duoc.inventory.model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class InventoryController {
    private final ObservableList<Product> products = FXCollections.observableArrayList(
            new Product(
                    "Teclado mecánico",
                    "Teclado USB", "Periféricos", 45990.0, 15
            ),
            new Product(
                    "Monitor 24 pulgadas",
                    "Monitor Full HD", "Electrónica", 129990.0, 8
            )
    );
    @FXML
    private TableView<Product> tblProducts;
    @FXML
    private TableColumn<Product, String> colName;
    @FXML
    private TableColumn<Product, String> colCategory;
    @FXML
    private TableColumn<Product, Double> colPrice;
    @FXML
    private TableColumn<Product, Integer> colQuantity;

    @FXML
    protected void initialize() {
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        tblProducts.setItems(products);
    }

    @FXML
    protected void handleNewProductClick() {
        // Próximo paso: abrir el formulario de producto.
    }
}
```

Veamos el controlador por partes:

- **`products`:** es una `ObservableList<Product>` que contiene dos productos de ejemplo, para que la tabla muestre algo desde el primer momento. Más adelante la reemplazaremos por datos reales. Es `final` porque la referencia a la lista no cambia, aunque su contenido sí.
- **Campos `@FXML`:** sus nombres deben coincidir **exactamente** con los `fx:id` de la vista (`tblProducts`, `colName`, `colCategory`, etc.). Si un nombre no coincide, el campo queda en `null` y la aplicación falla al usarlo.
- **Tipos genéricos:** `TableView<Product>` indica que cada fila es un `Product`. En `TableColumn<Product, String>`, el primer tipo es el de la fila y el segundo el del valor que muestra la celda. Por eso `colPrice` usa `Double` y `colQuantity` usa `Integer`: los genéricos no admiten tipos primitivos, así que se usan sus clases envoltorio.
- **`initialize()`:** JavaFX lo ejecuta automáticamente una vez que los campos `@FXML` fueron inyectados, así que aquí ya podemos usar la tabla y las columnas sin riesgo de que sean `null`.
- **`setCellValueFactory(new PropertyValueFactory<>("name"))`:** le indica a la columna cómo obtener el valor de cada celda. Dado el texto `"name"`, busca primero un método `nameProperty()` y, si no existe, usa `getName()` de `Product`. Por eso importaban los nombres de los *getters* en el paso 4, y también el `opens ... to javafx.base` del paso 3.
- **`tblProducts.setItems(products)`:** conecta la tabla con la lista. Como la lista es observable, cualquier producto que agreguemos o quitemos después se reflejará en pantalla sin código adicional.
- **`handleNewProductClick()`:** es el método que enlazamos desde el FXML con `#handleNewProductClick`. Por ahora está vacío, y lo completaremos en el siguiente paso.

**Verificación:** Ejecuta la aplicación con `.\mvnw.cmd clean javafx:run`. Deberías ver la ventana principal con una tabla que muestra los dos productos de ejemplo.

### 7. Crear el servicio `ProductService`

Hasta ahora, los productos viven en la variable `products` del controlador `InventoryController`. Pero, ¿cómo pueden estar disponibles en toda la aplicación? La respuesta es un **servicio**.

En Java, un servicio corresponde a una clase que concentra toda la lógica de negocio. Es decir, en lugar de poner cálculos, validaciones complejas o reglas de la empresa dentro de un botón, un formulario o un controlador, todo eso vive dentro de la clase de servicio.

En nuestro caso, el servicio que vamos a crear nos permitirá encapsular la lógica para mostrar los productos, agregar nuevos y eliminar los ya existentes. De esta forma, la lógica de negocio no depende de ningún controlador.

Crea la clase `ProductService` en la ruta `src/main/java/cl/duoc/inventory/service/` con el siguiente código:

```java
package cl.duoc.inventory.service;

import cl.duoc.inventory.model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProductService {
    private final ObservableList<Product> products =
            FXCollections.observableArrayList();

    private ProductService() {
        products.addAll(
                new Product(
                        "Teclado mecánico",
                        "Teclado USB",
                        "Periféricos",
                        45990.0,
                        15
                ),
                new Product(
                        "Monitor 24 pulgadas",
                        "Monitor Full HD",
                        "Electrónica",
                        129990.0,
                        8
                )
        );
    }

    public static ProductService getInstance() {
        return InstanceHolder.INSTANCE;
    }

    public ObservableList<Product> getProducts() {
        return products;
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public boolean removeProduct(Product product) {
        return products.remove(product);
    }

    private static class InstanceHolder {
        private static final ProductService INSTANCE =
                new ProductService();
    }
}
```

Esta clase aplica el patrón de diseño **Singleton**, que garantiza que exista **una única instancia** de `ProductService` en toda la aplicación. Esto es justamente lo que necesitamos: si cada controlador creara su propio servicio, cada uno tendría su propia lista de productos y no verían los cambios del otro. Veamos cómo se logra:

- **Constructor `private`:** nadie fuera de la clase puede escribir `new ProductService()`. Aquí también cargamos los dos productos de ejemplo que antes estaban en el controlador.
- **`getInstance()`:** es el único punto de acceso al servicio. Siempre retorna la misma instancia, que vive dentro de `InstanceHolder`.
- **`InstanceHolder`:** es una clase interna estática. Java no la carga en memoria hasta que alguien la usa por primera vez, es decir, cuando se llama a `getInstance()`. En ese momento se crea `INSTANCE`, y la JVM garantiza que esa creación es segura incluso si varios hilos la solicitan al mismo tiempo, sin necesidad de usar `synchronized`.
- **`products`:** es la lista observable que contiene los datos. Es `private` y `final`, de modo que solo se modifica a través de los métodos del servicio.
- **`getProducts()`:** retorna la propia lista (y no una copia) porque la tabla necesita *observarla* para actualizarse sola.
- **`addProduct(...)` y `removeProduct(...)`:** agregan y quitan productos de la lista. `remove` retorna `true` si el producto existía y `false` en caso contrario.

> **Nota:** el servicio no tiene un método para editar. Un producto editado sigue siendo el mismo objeto que ya está en la lista, por lo que basta con modificarlo mediante sus *setters*. Lo veremos en el paso 10.

**Verificación:** El servicio todavía no se usa desde ninguna parte, por lo que no verás cambios en la aplicación. En el siguiente paso lo conectamos con el controlador.

### 8. Modificar `InventoryController`

Con el servicio listo, modificamos `InventoryController` para que obtenga los productos desde él y eliminamos las instrucciones que ya no necesita.

Primero, agrega la importación del servicio `ProductService`:

```java
import cl.duoc.inventory.service.ProductService;
```

Luego, modifica el método `initialize()` para que la tabla `tblProducts` obtenga los productos desde el servicio:

```java
tblProducts.setItems(ProductService.getInstance().getProducts());
```

`getInstance()` entrega la instancia única del servicio y `getProducts()` su lista observable. Con esto, la tabla muestra los datos del servicio y no los de una lista propia del controlador, por lo que cualquier cambio que se haga en el servicio se reflejará en pantalla.

Por último, elimina la declaración de la variable `products`, borrando estas líneas:

```java
private final ObservableList<Product> products = FXCollections.observableArrayList(
        new Product(
                "Teclado mecánico",
                "Teclado USB", "Periféricos", 45990.0, 15
        ),
        new Product(
                "Monitor 24 pulgadas",
                "Monitor Full HD", "Electrónica", 129990.0, 8
        )
);
```

Al borrar `products`, las importaciones de `FXCollections` y `ObservableList` quedan sin uso (IntelliJ las muestra atenuadas), así que elimínalas también.

Con estos cambios, `InventoryController` debe lucir así:

```java
package cl.duoc.inventory.controller;

import cl.duoc.inventory.model.Product;
import cl.duoc.inventory.service.ProductService;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class InventoryController {
    @FXML
    private TableView<Product> tblProducts;
    @FXML
    private TableColumn<Product, String> colName;
    @FXML
    private TableColumn<Product, String> colCategory;
    @FXML
    private TableColumn<Product, Double> colPrice;
    @FXML
    private TableColumn<Product, Integer> colQuantity;

    @FXML
    protected void initialize() {
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        tblProducts.setItems(ProductService.getInstance().getProducts());
    }

    @FXML
    protected void handleNewProductClick() {
        // Próximo paso: abrir el formulario de producto.
    }
}
```

Fíjate en que el controlador ya no almacena datos: solo conecta la vista con el servicio. Esta es la separación de responsabilidades que busca el patrón MVC, donde cada clase tiene un único motivo para cambiar.

**Verificación:** Ejecuta la aplicación con `.\mvnw.cmd clean javafx:run`. Deberías ver la misma tabla de antes, pero ahora los productos provienen del servicio.

### 9. Crear la vista `product-view.fxml`

Con el servicio funcionando, necesitamos un formulario para crear nuevos productos y editar o eliminar los existentes. Crea un nuevo archivo FXML llamado `product-view.fxml` en la ruta `src/main/resources/cl/duoc/inventory/view/`, con el siguiente código:

```xml
<?xml version="1.0" encoding="UTF-8"?>

<?import javafx.geometry.Insets?>
<?import javafx.scene.control.*?>
<?import javafx.scene.layout.*?>
<BorderPane xmlns:fx="http://javafx.com/fxml"
            xmlns="http://javafx.com/javafx"
            fx:controller="cl.duoc.inventory.controller.ProductController"
            prefHeight="400.0" prefWidth="600.0">
    <top>
        <VBox spacing="5.0">
            <padding>
                <Insets top="20.0" right="24.0"
                        bottom="15.0" left="24.0"/>
            </padding>
            <Label fx:id="lblTitle"
                   text="Nuevo producto"/>
            <Label text="Ingresa la información del producto"/>
        </VBox>
    </top>
    <center>
        <GridPane hgap="12.0" vgap="12.0">
            <padding>
                <Insets top="10.0" right="24.0"
                        bottom="20.0" left="24.0"/>
            </padding>
            <columnConstraints>
                <ColumnConstraints minWidth="100.0"/>
                <ColumnConstraints hgrow="ALWAYS"/>
            </columnConstraints>
            <Label text="Nombre:"
                   GridPane.rowIndex="0"
                   GridPane.columnIndex="0"/>
            <TextField fx:id="txtName"
                       promptText="Nombre del producto"
                       GridPane.rowIndex="0"
                       GridPane.columnIndex="1"/>
            <Label text="Descripción:"
                   GridPane.rowIndex="1"
                   GridPane.columnIndex="0"/>
            <TextArea fx:id="txtDescription"
                      promptText="Descripción del producto"
                      prefRowCount="3"
                      wrapText="true"
                      GridPane.rowIndex="1"
                      GridPane.columnIndex="1"/>
            <Label text="Categoría:"
                   GridPane.rowIndex="2"
                   GridPane.columnIndex="0"/>
            <ComboBox fx:id="cmbCategory"
                      promptText="Selecciona una categoría"
                      maxWidth="Infinity"
                      GridPane.rowIndex="2"
                      GridPane.columnIndex="1"/>
            <Label text="Precio:"
                   GridPane.rowIndex="3"
                   GridPane.columnIndex="0"/>
            <TextField fx:id="txtPrice"
                       promptText="Ej. 45990"
                       GridPane.rowIndex="3"
                       GridPane.columnIndex="1"/>
            <Label text="Cantidad:"
                   GridPane.rowIndex="4"
                   GridPane.columnIndex="0"/>
            <TextField fx:id="txtQuantity"
                       promptText="Ej. 15"
                       GridPane.rowIndex="4"
                       GridPane.columnIndex="1"/>
        </GridPane>
    </center>
    <bottom>
        <HBox alignment="CENTER_RIGHT" spacing="10.0">
            <padding>
                <Insets top="10.0" right="24.0"
                        bottom="20.0" left="24.0"/>
            </padding>
            <Button fx:id="btnDelete"
                    text="Eliminar"
                    onAction="#handleDeleteClick"/>
            <Region HBox.hgrow="ALWAYS"/>
            <Button text="Cancelar"
                    onAction="#handleCancelClick"/>
            <Button text="Guardar"
                    onAction="#handleSaveClick"/>
        </HBox>
    </bottom>
</BorderPane>
```

Esta vista usa de nuevo un `BorderPane`, ahora con tres zonas: `top` para el encabezado, `center` para el formulario y `bottom` para los botones. Veamos sus partes más importantes:

- **`lblTitle`:** el título tiene un `fx:id` porque el controlador cambiará su texto a "Editar producto" cuando el formulario se abra con un producto existente. De este modo, **la misma vista sirve para crear y para editar**.
- **`GridPane`:** organiza los controles en una cuadrícula. Cada hijo indica su posición con las propiedades adjuntas `GridPane.rowIndex` y `GridPane.columnIndex`, igual que `HBox.hgrow` en el paso 5. La primera columna contiene las etiquetas y la segunda, los campos. En `columnConstraints`, la primera columna tiene un ancho mínimo de `100` y la segunda usa `hgrow="ALWAYS"` para ocupar todo el espacio restante.
- **`promptText`:** es el texto de ayuda que aparece en gris dentro de un campo vacío y desaparece al escribir.
- **`TextArea`:** `prefRowCount="3"` define cuántas filas de texto se ven a la vez y `wrapText="true"` hace que el texto salte de línea al llegar al borde, en lugar de crecer hacia la derecha.
- **`ComboBox`:** `maxWidth="Infinity"` le permite estirarse hasta ocupar toda la columna; por defecto, solo tomaría el ancho necesario. Las categorías que muestra se cargarán desde el controlador.
- **Botones:** `Eliminar` queda a la izquierda, y `Region` con `hgrow="ALWAYS"` empuja `Cancelar` y `Guardar` hacia la derecha. El botón de eliminar tiene un `fx:id` porque el controlador lo ocultará cuando se esté creando un producto nuevo.

Cada atributo `onAction` (`#handleDeleteClick`, `#handleCancelClick` y `#handleSaveClick`) debe corresponder a un método del controlador `ProductController`, que crearemos a continuación. Si falta alguno, `FXMLLoader` fallará al cargar la vista.

**Verificación:** Aún no podemos ver este formulario en la aplicación: falta su controlador (paso 10) y la navegación desde la vista principal (paso 11).

### 10. Crear el controlador `ProductController`

El formulario necesita su propio controlador, que le dará comportamiento: validar los datos, guardar, cancelar y eliminar. Crea la clase `ProductController` en la ruta `src/main/java/cl/duoc/inventory/controller/` con el siguiente código:

```java
package cl.duoc.inventory.controller;

import cl.duoc.inventory.model.Product;
import cl.duoc.inventory.service.ProductService;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Optional;

public class ProductController {
    @FXML
    private Label lblTitle;

    @FXML
    private TextField txtName;

    @FXML
    private TextArea txtDescription;

    @FXML
    private ComboBox<String> cmbCategory;

    @FXML
    private TextField txtPrice;

    @FXML
    private TextField txtQuantity;

    @FXML
    private Button btnDelete;
    private Product product;

    @FXML
    protected void initialize() {
        cmbCategory.getItems().addAll("Periféricos", "Electrónica", "Accesorios");

        btnDelete.setVisible(false);
        btnDelete.setManaged(false);
    }

    @FXML
    protected void handleSaveClick() {
        if (!validateForm()) {
            return;
        }

        String name = txtName.getText().trim();
        String description = txtDescription.getText().trim();
        String category = cmbCategory.getValue();
        double price = Double.parseDouble(txtPrice.getText());
        int quantity = Integer.parseInt(txtQuantity.getText());

        if (product == null) {
            Product newProduct = new Product(
                    name, description, category, price, quantity
            );
            ProductService.getInstance().addProduct(newProduct);
        } else {
            product.setName(name);
            product.setDescription(description);
            product.setCategory(category);
            product.setPrice(price);
            product.setQuantity(quantity);
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Producto guardado");
        alert.setHeaderText("Operación exitosa");
        alert.setContentText("El producto se guardó correctamente.");
        alert.showAndWait();

        txtName.getScene().getWindow().hide();
    }

    @FXML
    protected void handleCancelClick() {
        txtName.getScene().getWindow().hide();
    }

    @FXML
    protected void handleDeleteClick() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Eliminar producto");
        alert.setHeaderText("¿Estás seguro de eliminar este producto?");
        alert.setContentText(
                "Producto: " + product.getName()
                        + "\nCódigo: " + product.getId()
        );

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent()
                && result.get() == ButtonType.OK) {

            ProductService.getInstance().removeProduct(product);

            Alert confirmation = new Alert(Alert.AlertType.INFORMATION);
            confirmation.setTitle("Producto eliminado");
            confirmation.setHeaderText("Operación exitosa");
            confirmation.setContentText(
                    "El producto se eliminó correctamente."
            );

            confirmation.showAndWait();

            txtName.getScene().getWindow().hide();
        }
    }

    public void setProduct(Product product) {
        this.product = product;

        lblTitle.setText("Editar producto");

        txtName.setText(product.getName());
        txtDescription.setText(product.getDescription());
        cmbCategory.setValue(product.getCategory());
        txtPrice.setText(String.valueOf(product.getPrice()));
        txtQuantity.setText(String.valueOf(product.getQuantity()));

        btnDelete.setVisible(true);
        btnDelete.setManaged(true);
    }

    private boolean validateForm() {
        if (txtName.getText().isBlank()
                || cmbCategory.getValue() == null
                || txtPrice.getText().isBlank()
                || txtQuantity.getText().isBlank()) {

            return false;
        }

        try {
            double price = Double.parseDouble(txtPrice.getText());
            int quantity = Integer.parseInt(txtQuantity.getText());

            return price >= 0 && quantity >= 0;

        } catch (NumberFormatException e) {
            return false;
        }
    }
}
```

Este controlador tiene más lógica que los anteriores. Veamos sus partes:

- **El campo `product`:** es la clave del diseño. Si es `null`, el formulario está en **modo creación**; si contiene un producto, está en **modo edición**. Casi todos los métodos preguntan por este valor para decidir qué hacer.
- **`initialize()`:** carga las categorías en el `ComboBox` (por ahora, una lista fija) y oculta el botón `Eliminar`, que no tiene sentido al crear un producto. Se necesitan dos llamadas: `setVisible(false)` lo hace invisible, pero seguiría ocupando espacio en la fila; `setManaged(false)` hace que el contenedor lo ignore al distribuir sus hijos.
- **`handleSaveClick()`:** primero valida el formulario y, si no es válido, termina con `return`. Luego lee los valores y los convierte: `trim()` elimina espacios sobrantes, y `Double.parseDouble` e `Integer.parseInt` transforman el texto en números, algo seguro en este punto porque ya fueron validados. Después, según el modo:
  - **Creación:** se instancia un nuevo `Product` y se agrega al servicio con `addProduct(...)`.
  - **Edición:** se modifica el mismo objeto con sus *setters*. No hace falta llamar al servicio, porque ese objeto ya está en su lista.
- **`Alert` y `showAndWait()`:** `Alert` es un diálogo predefinido; el tipo (`INFORMATION`, `CONFIRMATION`, etc.) define su ícono y sus botones. `showAndWait()` lo muestra y **detiene la ejecución** hasta que el usuario lo cierre.
- **`txtName.getScene().getWindow().hide()`:** cierra el formulario. Cualquier control sabe a qué `Scene` pertenece (por el *Scene Graph*), y la escena sabe en qué ventana está; de ahí llegamos al `Stage` y lo ocultamos. Usamos `txtName`, pero habría funcionado con cualquier otro control de la vista.
- **`handleCancelClick()`:** cierra la ventana sin guardar nada.
- **`handleDeleteClick()`:** muestra una alerta de confirmación. `showAndWait()` retorna un `Optional<ButtonType>` porque el usuario podría cerrar el diálogo sin presionar ningún botón; por eso se verifica `isPresent()` y que el botón sea `ButtonType.OK` antes de eliminar el producto con `removeProduct(...)`.
- **`setProduct(Product product)`:** no es un manejador de eventos, sino un método público que el controlador principal llamará para abrir el formulario en modo edición. Guarda el producto, cambia el título, rellena los campos y muestra el botón `Eliminar`. No podemos recibir el producto en `initialize()`, porque este se ejecuta durante la carga de la vista, antes de que quien la abre tenga la oportunidad de entregarlo.
- **`validateForm()`:** comprueba que nombre, categoría, precio y cantidad no estén vacíos (la descripción es opcional) y que el precio y la cantidad sean números no negativos. Si `parseDouble` o `parseInt` reciben un texto que no es numérico, lanzan `NumberFormatException`, que capturamos para retornar `false`. Por ahora, cuando la validación falla no se muestra ningún mensaje: simplemente no se guarda el producto.

**Verificación:** El formulario todavía no se puede abrir desde la aplicación. Conectamos la vista principal con él en el siguiente paso.

### 11. Integrar la navegación a la vista `product-view.fxml`

En este momento, tenemos la vista principal y el formulario de productos, pero aún no se comunican entre sí. Para poder hacerlo, vamos a implementar la navegación entre ambas vistas.

Primero, crea el método `openProductView(Product product)` en el controlador `InventoryController`:

```java
private void openProductView(Product product) {
    try {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/cl/duoc/inventory/view/product-view.fxml"
                )
        );
        Parent root = loader.load();

        ProductController controller = loader.getController();

        if (product != null) {
            controller.setProduct(product);
        }

        Stage stage = new Stage();
        stage.setTitle(
                product == null ? "Nuevo producto" : "Editar producto"
        );
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        tblProducts.refresh();
    } catch (IOException e) {
        e.printStackTrace();
    }
}
```

Este método muestra la vista para crear o editar un producto. El parámetro `product` indica en qué modo se abre: `null` significa crear y, si trae un producto, significa editar. Veamos cómo funciona:

- **`FXMLLoader` como objeto:** en el paso 5 pasamos el resultado de `load()` directo a la `Scene`. Aquí guardamos el `loader` en una variable porque lo necesitamos después para llamar a `getController()`, que entrega el `ProductController` que `FXMLLoader` creó. Gracias a esto podemos pasarle datos al formulario con `setProduct(...)`.
- **La ruta empieza con `/`:** una barra inicial indica que la ruta se calcula desde la raíz de los recursos, y no desde el paquete de la clase como en el paso 5.
- **`new Stage()`:** crea una **segunda ventana** para el formulario, ya que cada `Stage` muestra una sola `Scene`. Su título cambia según el modo, usando el operador ternario `condición ? valorSiVerdadero : valorSiFalso`.
- **`initModality(Modality.APPLICATION_MODAL)`:** vuelve la ventana *modal*, es decir, bloquea la interacción con el resto de la aplicación hasta que se cierre.
- **`showAndWait()`:** a diferencia de `show()`, muestra la ventana y **pausa este método** hasta que se cierre. Por eso la instrucción siguiente, `tblProducts.refresh()`, solo se ejecuta cuando el usuario termina con el formulario.
- **`tblProducts.refresh()`:** agregar o eliminar un producto actualiza la tabla automáticamente porque la lista es observable. Sin embargo, modificar los atributos de un `Product` existente no genera ningún aviso, ya que usa atributos simples y no propiedades observables. Con `refresh()` le pedimos a la tabla que vuelva a leer los datos.
- **`catch (IOException e)`:** se ejecuta si `load()` no puede leer el archivo FXML. Por ahora solo imprimimos el error en consola.

Luego, modifica el método `handleNewProductClick()` que se encuentra en el mismo controlador:

```java
@FXML
protected void handleNewProductClick() {
    openProductView(null);
}
```

Al pasar `null`, el formulario se abre vacío y en modo creación. Este método responde tanto al botón "Nuevo producto" como a la opción "Nuevo" del menú, ya que ambos lo referencian en el FXML.

Por último, modifica el método `initialize()` para integrar el evento que manejará el doble clic sobre un producto de la tabla y lo enviará al formulario:

```java
tblProducts.setRowFactory(tableView -> {
    TableRow<Product> row = new TableRow<>();
    row.setOnMouseClicked(event -> {
        if (event.getClickCount() == 2 && !row.isEmpty()) {
            Product selectedProduct = row.getItem();
            openProductView(selectedProduct);
        }
    });
    return row;
});
```

Por defecto, `TableView` crea sus filas automáticamente. Con `setRowFactory(...)` le entregamos una función que construye cada fila (`TableRow`), lo que nos permite personalizarla. En este caso le registramos un manejador de clics, con la misma técnica de lambdas vista en la sección de eventos:

- `event.getClickCount() == 2` detecta el **doble clic**.
- `!row.isEmpty()` evita abrir el formulario al hacer doble clic en las filas vacías, que la tabla dibuja para completar su altura.
- `row.getItem()` retorna el `Product` que muestra esa fila, el cual enviamos a `openProductView(...)`.

Con todas esas modificaciones, nuestro controlador debe lucir así. Fíjate en que también se agregaron nuevas importaciones (`FXMLLoader`, `Parent`, `Scene`, `TableRow`, `Modality`, `Stage` e `IOException`), necesarias para los métodos nuevos:

```java
package cl.duoc.inventory.controller;

import cl.duoc.inventory.model.Product;
import cl.duoc.inventory.service.ProductService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class InventoryController {
    @FXML
    private TableView<Product> tblProducts;
    @FXML
    private TableColumn<Product, String> colName;
    @FXML
    private TableColumn<Product, String> colCategory;
    @FXML
    private TableColumn<Product, Double> colPrice;
    @FXML
    private TableColumn<Product, Integer> colQuantity;

    @FXML
    protected void initialize() {
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        tblProducts.setItems(ProductService.getInstance().getProducts());

        tblProducts.setRowFactory(tableView -> {
            TableRow<Product> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    Product selectedProduct = row.getItem();
                    openProductView(selectedProduct);
                }
            });
            return row;
        });
    }

    @FXML
    protected void handleNewProductClick() {
        openProductView(null);
    }

    private void openProductView(Product product) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "/cl/duoc/inventory/view/product-view.fxml"
                    )
            );
            Parent root = loader.load();

            ProductController controller = loader.getController();

            if (product != null) {
                controller.setProduct(product);
            }

            Stage stage = new Stage();
            stage.setTitle(
                    product == null ? "Nuevo producto" : "Editar producto"
            );
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            tblProducts.refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

**Verificación:** Ejecuta la aplicación con `.\mvnw.cmd clean javafx:run`. Deberías ver los dos productos de ejemplo, poder crear nuevos con el botón **Nuevo producto**, y editar o eliminar uno existente haciendo doble clic sobre su fila.

### 12. Aplicar estilos con `BootstrapFX` y CSS propio

Ahora que nuestra aplicación funciona, vamos a darle estilo con dos herramientas: **BootstrapFX**, que aporta clases de estilo listas para usar, y nuestra propia hoja de estilos CSS. Comenzamos incluyendo la dependencia de `BootstrapFX`. Copia y pega el siguiente código en la sección `dependencies` del archivo `pom.xml`:

```xml
<dependency>
    <groupId>org.kordamp.bootstrapfx</groupId>
    <artifactId>bootstrapfx-core</artifactId>
    <version>0.4.0</version>
</dependency>
```

Luego, recarga el proyecto Maven (en IntelliJ, con el ícono que aparece en la esquina del editor) para que se descargue la biblioteca.

[BootstrapFX](https://github.com/kordamp/bootstrapfx) es una biblioteca que lleva a JavaFX las clases de estilo del popular framework web Bootstrap. En lugar de escribir CSS para cada botón, le asignamos clases ya definidas, como `btn` o `btn-primary`. Así conseguimos una apariencia consistente con muy poco código, y reservamos nuestro propio CSS para los detalles propios de la aplicación.

#### Declarar el módulo

Como nuestro proyecto es modular, también debemos indicar que usaremos la nueva biblioteca. Agrega la siguiente línea en `src/main/java/module-info.java`, junto a los otros `requires`:

```java
requires org.kordamp.bootstrapfx.core;
```

Sin ella, el compilador no encontraría las clases de BootstrapFX, aunque la dependencia esté en el `pom.xml`: Maven descarga la biblioteca, pero es `module-info.java` quien autoriza su uso dentro del módulo.

#### Cargar la hoja de estilos de BootstrapFX

Los estilos se aplican a una `Scene`, que es la que contiene los nodos. Modifica el método `start(...)` de `InventoryApplication` para agregar la hoja de estilos de BootstrapFX, justo después de crear la escena. También necesitas la importación `org.kordamp.bootstrapfx.BootstrapFX`:

```java
Scene scene = new Scene(fxmlLoader.load(), 900, 600);
scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
```

`getStylesheets()` retorna la lista de hojas de estilo de la escena, y `BootstrapFX.bootstrapFXStylesheet()` entrega la ubicación del archivo CSS incluido dentro de la biblioteca. Desde este momento, todos los nodos de la escena pueden usar sus clases.

Nuestro formulario de productos se abre en un `Stage` con su propia `Scene`, por lo que **no hereda** los estilos de la ventana principal. Hay que repetir el mismo paso en el método `openProductView(...)` de `InventoryController` (con la misma importación), reemplazando la línea `stage.setScene(new Scene(root));`:

```java
Scene scene = new Scene(root);
scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
stage.setScene(scene);
```

#### Crear nuestra hoja de estilos

BootstrapFX resuelve gran parte del diseño, pero siempre habrá detalles propios: tamaños de letra, colores del encabezado o de la fila seleccionada. Para eso, crea un archivo `styles.css` en la carpeta `css` que preparamos en el paso 2, es decir, en `src/main/resources/cl/duoc/inventory/css/`:

```css
/*
 * Estilos propios de la aplicación.
 * Se cargan después de BootstrapFX, por lo que pueden complementarlo
 * o sobrescribir alguna de sus reglas.
 */

/* Encabezados de las vistas */
.page-title {
    -fx-font-size: 22px;
    -fx-font-weight: bold;
    -fx-text-fill: #212529;
}

.page-subtitle {
    -fx-font-size: 13px;
    -fx-text-fill: #6c757d;
}

/* Etiquetas del formulario */
.form-label {
    -fx-font-weight: bold;
    -fx-text-fill: #495057;
}

/* Tabla de productos */
.table-view {
    -fx-font-size: 13px;
}

.table-view .column-header .label {
    -fx-font-weight: bold;
}

.table-row-cell:filled:selected {
    -fx-background-color: #0d6efd;
}

.table-row-cell:filled:selected .table-cell {
    -fx-text-fill: white;
}
```

Veamos cómo funciona este CSS. Se parece al CSS de la web, con dos diferencias importantes: las propiedades llevan el prefijo `-fx-` (por ejemplo, `-fx-font-size`) y los selectores se basan en las clases propias de cada control.

- **`.page-title`, `.page-subtitle` y `.form-label`:** son clases que **inventamos nosotros**. El punto indica que son clases de estilo, y luego las asignaremos a los nodos que queramos.
- **`.table-view`, `.column-header` y `.table-row-cell`:** son clases que JavaFX ya asigna a las partes de una `TableView`. Un selector con espacio, como `.table-view .column-header .label`, significa "las etiquetas dentro de los encabezados de columna de una tabla".
- **`:filled:selected`:** son *pseudoclases*, que representan un estado. En este caso, una fila que contiene datos (`filled`) y está seleccionada (`selected`). El filtro `filled` evita pintar las filas vacías que la tabla dibuja para completar su altura.

#### Asignar los estilos a las vistas

Falta conectar todo en los archivos FXML. Primero, cada vista debe cargar nuestra hoja de estilos. Agrega la importación `java.net.URL` junto a las demás y la sección `stylesheets` dentro de `BorderPane`, tanto en `inventory-view.fxml` como en `product-view.fxml`:

```xml
<?import java.net.URL?>
<!-- ...resto de las importaciones... -->
<BorderPane xmlns:fx="http://javafx.com/fxml"
            xmlns="http://javafx.com/javafx"
            fx:controller="..."
            prefHeight="..." prefWidth="...">
    <stylesheets>
        <URL value="@../css/styles.css"/>
    </stylesheets>
    <!-- ...contenido de la vista... -->
</BorderPane>
```

El símbolo `@` indica que la ruta es **relativa al archivo FXML**. Como las vistas están en `view/` y el CSS en `css/`, usamos `../` para subir una carpeta. Gracias al orden de prioridad de JavaFX, las hojas de estilo declaradas en un contenedor tienen más prioridad que las de la `Scene`, por lo que nuestras reglas pueden sobrescribir las de BootstrapFX.

Luego, asignamos las clases de estilo a los controles con el atributo `styleClass`. Si son varias, se separan con comas. En `inventory-view.fxml`, modifica el título y el botón de la fila superior:

```xml
<Label text="Productos registrados"
       styleClass="page-title"/>
<Region HBox.hgrow="ALWAYS"/>
<Button text="Nuevo producto"
        styleClass="btn, btn-primary"
        onAction="#handleNewProductClick"/>
```

Aquí `btn` es la clase base de BootstrapFX para los botones, y `btn-primary` les da el color azul de acción principal. Ambas se combinan.

En `product-view.fxml`, modifica los títulos de la parte superior:

```xml
<Label fx:id="lblTitle"
       text="Nuevo producto"
       styleClass="page-title"/>
<Label text="Ingresa la información del producto"
       styleClass="page-subtitle"/>
```

Agrega `styleClass="form-label"` a cada una de las cinco etiquetas del formulario (`Nombre:`, `Descripción:`, `Categoría:`, `Precio:` y `Cantidad:`), por ejemplo:

```xml
<Label text="Nombre:"
       styleClass="form-label"
       GridPane.rowIndex="0"
       GridPane.columnIndex="0"/>
```

Por último, modifica los botones de la parte inferior:

```xml
<Button fx:id="btnDelete"
        text="Eliminar"
        styleClass="btn, btn-danger"
        onAction="#handleDeleteClick"/>
<Region HBox.hgrow="ALWAYS"/>
<Button text="Cancelar"
        styleClass="btn, btn-default"
        onAction="#handleCancelClick"/>
<Button text="Guardar"
        styleClass="btn, btn-primary"
        onAction="#handleSaveClick"/>
```

Los colores comunican el significado de cada acción: `btn-danger` (rojo) para eliminar, `btn-default` (neutro) para cancelar y `btn-primary` (azul) para guardar. BootstrapFX incluye otras variantes, como `btn-success`, `btn-warning` e `btn-info`, y puedes probarlas cambiando el nombre de la clase.

> **Tip pedagógico:** la clase `btn` por sí sola ya cambia el aspecto del botón, pero es la segunda clase la que le da color. Prueba quitar una de las dos para ver qué aporta cada una.

**Verificación:** Ejecuta la aplicación con `.\mvnw.cmd clean javafx:run`. Deberías ver los botones con color, los títulos más grandes y la fila seleccionada de la tabla en azul, tanto en la ventana principal como en el formulario.

### 13. Persistir los datos con `Gson`

Hasta ahora, los productos viven solo en la memoria de la aplicación: si agregas, editas o eliminas un producto y cierras la ventana, al abrirla de nuevo todo vuelve al estado inicial. Para que el inventario sobreviva entre ejecuciones, debemos **persistir** los datos, es decir, guardarlos en un archivo y leerlos al iniciar.

Guardaremos los productos en formato **JSON**, un texto simple y legible que representa objetos y listas. Por ejemplo, un producto se ve así:

```json
{
  "id": "7bf0831b-608d-4dd4-af4c-852cbe162280",
  "name": "Teclado mecánico",
  "description": "Teclado USB",
  "category": "Periféricos",
  "price": 45990.0,
  "quantity": 15
}
```

Para convertir nuestros objetos `Product` en JSON (*serializar*) y el JSON de vuelta en objetos (*deserializar*), usaremos [Gson](https://github.com/google/gson), una biblioteca de Google.

#### Agregar la dependencia

Copia y pega el siguiente código en la sección `dependencies` del archivo `pom.xml`, y recarga el proyecto Maven:

```xml
<dependency>
    <groupId>com.google.code.gson</groupId>
    <artifactId>gson</artifactId>
    <version>2.11.0</version>
</dependency>
```

Luego, modifica `src/main/java/module-info.java` para declarar el módulo y permitir que Gson acceda a la clase `Product`:

```java
requires com.google.gson;

opens cl.duoc.inventory.model to javafx.base, com.google.gson;
```

Gson lee y escribe los atributos de `Product` mediante *reflexión*, igual que lo hace JavaFX con `PropertyValueFactory`. Por eso el paquete `model` debe estar abierto también a `com.google.gson`. Fíjate en que `opens` acepta varios módulos separados por comas.

#### Crear el repositorio `ProductRepository`

Hasta ahora, `ProductService` concentraba la lógica de negocio. Si además le agregáramos el código para leer y escribir archivos, tendría dos responsabilidades distintas. Por eso separamos el acceso al almacenamiento en una nueva capa: el **repositorio**, que solo sabe *cómo* guardar y recuperar datos, y no qué reglas de negocio aplican. Si algún día los datos se guardaran en una base de datos, solo cambiaría esta clase.

Crea la clase `ProductRepository` en el paquete `cl.duoc.inventory.repository` (que creaste en el paso 2), en la ruta `src/main/java/cl/duoc/inventory/repository/`:

```java
package cl.duoc.inventory.repository;

import cl.duoc.inventory.model.Product;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository {
    private static final Type PRODUCT_LIST_TYPE =
            new TypeToken<List<Product>>() {
            }.getType();

    private final Path file;
    private final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    public ProductRepository() {
        this(Path.of("data", "products.json"));
    }

    public ProductRepository(Path file) {
        this.file = file;
    }

    public boolean exists() {
        return Files.exists(file);
    }

    public List<Product> findAll() {
        if (!exists()) {
            return new ArrayList<>();
        }

        try {
            String json = Files.readString(file, StandardCharsets.UTF_8);
            List<Product> products = gson.fromJson(json, PRODUCT_LIST_TYPE);
            return products != null ? products : new ArrayList<>();
        } catch (IOException e) {
            throw new UncheckedIOException(
                    "No se pudo leer el archivo " + file, e
            );
        }
    }

    public void save(List<Product> products) {
        try {
            Files.createDirectories(file.toAbsolutePath().getParent());
            String json = gson.toJson(products, PRODUCT_LIST_TYPE);
            Files.writeString(file, json, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new UncheckedIOException(
                    "No se pudo guardar el archivo " + file, e
            );
        }
    }
}
```

Veamos las partes más importantes:

- **`file` y `Path`:** `Path` representa la ubicación de un archivo. El constructor sin parámetros usa `data/products.json`, una ruta relativa a la carpeta desde la que se ejecuta la aplicación (en nuestro caso, la raíz del proyecto). El segundo constructor permite indicar otra ruta, lo que será útil para hacer pruebas.
- **`Gson` con `setPrettyPrinting()`:** `GsonBuilder` configura el objeto antes de crearlo. Con `setPrettyPrinting()` el JSON se escribe con sangría y saltos de línea, para que sea fácil de leer.
- **`PRODUCT_LIST_TYPE` y `TypeToken`:** al compilar, Java *borra* los tipos genéricos, por lo que en tiempo de ejecución una `List<Product>` es solo una `List`, y Gson no sabría qué objetos crear. `TypeToken` es un truco para conservar esa información: las llaves `{}` crean una subclase anónima que "recuerda" el tipo completo `List<Product>`.
- **`exists()` y `findAll()`:** si el archivo no existe, `findAll()` retorna una lista vacía en lugar de fallar. Si existe, lo lee completo con `Files.readString(...)` y `gson.fromJson(...)` lo convierte en una lista de productos. El `null` ocurre cuando el archivo está vacío.
- **`save(...)`:** `Files.createDirectories(...)` crea la carpeta `data` si no existe, `gson.toJson(...)` convierte la lista en texto JSON y `Files.writeString(...)` lo escribe en el archivo, reemplazando su contenido anterior.
- **`StandardCharsets.UTF_8`:** indica la codificación del archivo. Sin ella, letras como la `é` de "Teclado mecánico" podrían guardarse mal según el sistema operativo.
- **`UncheckedIOException`:** leer y escribir archivos puede lanzar `IOException`, que es una excepción *verificada* y nos obligaría a declararla en todas las capas superiores. La envolvemos en una `UncheckedIOException`, que no exige declararla, junto con un mensaje que indica qué archivo falló.

> **Nota:** Gson crea los objetos `Product` sin llamar a su constructor. Asigna directamente cada atributo desde el JSON, incluido `id`, que es `final`. Por eso el producto conserva el mismo `id` que tenía cuando se guardó, en lugar de recibir uno nuevo.

#### Usar el repositorio desde `ProductService`

Ahora el servicio debe cargar los productos desde el repositorio al iniciar, y guardarlos cada vez que cambien. Modifica `ProductService` para que quede así:

```java
package cl.duoc.inventory.service;

import cl.duoc.inventory.model.Product;
import cl.duoc.inventory.repository.ProductRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProductService {
    private final ProductRepository repository = new ProductRepository();
    private final ObservableList<Product> products =
            FXCollections.observableArrayList();

    private ProductService() {
        products.addAll(repository.findAll());
    }

    public static ProductService getInstance() {
        return InstanceHolder.INSTANCE;
    }

    public ObservableList<Product> getProducts() {
        return products;
    }

    public void addProduct(Product product) {
        products.add(product);
        saveProducts();
    }

    public boolean removeProduct(Product product) {
        boolean removed = products.remove(product);
        if (removed) {
            saveProducts();
        }
        return removed;
    }

    public void saveProducts() {
        repository.save(products);
    }

    private static class InstanceHolder {
        private static final ProductService INSTANCE =
                new ProductService();
    }
}
```

Veamos los cambios respecto al paso 7:

- **El constructor:** carga los productos guardados con `repository.findAll()`. Ya no incluimos los productos de ejemplo: nos sirvieron en los pasos anteriores para tener algo que mostrar en la tabla, pero en una aplicación real los datos los ingresan los usuarios. La primera vez que se ejecute la aplicación, el archivo no existirá y `findAll()` retornará una lista vacía, por lo que la tabla comenzará sin productos.
- **`addProduct(...)` y `removeProduct(...)`:** después de modificar la lista, llaman a `saveProducts()`. En `removeProduct`, solo se guarda si realmente se eliminó algo.
- **`saveProducts()`:** es un método público que escribe la lista actual en el archivo. Lo necesitamos para la edición, como veremos a continuación.

Recuerda que, al editar, `ProductController` modifica el producto con sus *setters*, y el servicio no se entera de ese cambio. Por eso, en el método `handleSaveClick()` del `ProductController`, agrega la llamada al servicio justo después de actualizar el producto existente:

```java
} else {
    product.setName(name);
    product.setDescription(description);
    product.setCategory(category);
    product.setPrice(price);
    product.setQuantity(quantity);
    ProductService.getInstance().saveProducts();
}
```

#### Excluir los datos del repositorio Git

El archivo `data/products.json` contiene los datos de uso de cada persona, no código fuente, por lo que no debería subirse a Git. Agrega estas líneas al final del archivo `.gitignore`:

```text
### Datos de la aplicación (JSON)
data/
```

**Verificación:** Ejecuta la aplicación con `.\mvnw.cmd clean javafx:run`. La tabla aparecerá vacía, y el archivo `data/products.json` se creará en la raíz del proyecto cuando guardes tu primer producto. Para comprobar que todo funciona:

1. Crea al menos tres productos nuevos, edita uno y elimina otro.
2. Cierra la aplicación y vuelve a ejecutarla: los cambios deben seguir ahí.
3. Abre `data/products.json` en tu editor, cambia algún valor y vuelve a ejecutar la aplicación: verás el cambio reflejado en la tabla.
4. Si quieres volver al estado inicial, cierra la aplicación y elimina la carpeta `data`.

> **Tip pedagógico:** al editar a mano el JSON, cuida que el formato siga siendo válido (comillas, comas y llaves). Si lo dañas, Gson no podrá leerlo y la aplicación fallará al iniciar, lo que muestra por qué un repositorio real debería manejar este tipo de errores.

### 14. Crear el modelo `Category` y su servicio

Hoy las categorías están escritas directamente en el código de `ProductController` ("Periféricos", "Electrónica" y "Accesorios"), y cada producto guarda el nombre de su categoría como texto. Si quisiéramos agregar una categoría nueva, tendríamos que modificar el código y compilar de nuevo. Además, si renombráramos una categoría, los productos existentes quedarían con un nombre desactualizado.

La solución es tratar la categoría como una **entidad** propia, igual que `Product`: con su modelo, su repositorio y su servicio. En este paso creamos esas tres piezas; en los pasos siguientes las conectaremos con los productos y con la interfaz.

#### Crear el modelo `Category`

Crea la clase `Category` en la ruta `src/main/java/cl/duoc/inventory/model/` con el siguiente código:

```java
package cl.duoc.inventory.model;

import java.util.UUID;

public class Category {
    private final String id;
    private String name;

    public Category(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
```

Veamos sus características:

- **Identificador inmutable:** igual que en `Product`, `id` es `final` y lo genera la propia clase con un `UUID`. Más adelante, los productos usarán este `id` para referirse a su categoría.
- **`toString()`:** un `ComboBox` muestra cada elemento usando su método `toString()`. Al retornar el nombre, el formulario de productos mostrará "Periféricos" en lugar de algo como `cl.duoc.inventory.model.Category@3f2b8c1e`, aunque internamente el combo guarda el objeto completo.

#### Crear el repositorio `CategoryRepository`

Crea la clase `CategoryRepository` en la ruta `src/main/java/cl/duoc/inventory/repository/` con el siguiente código:

```java
package cl.duoc.inventory.repository;

import cl.duoc.inventory.model.Category;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CategoryRepository {
    private static final Type CATEGORY_LIST_TYPE =
            new TypeToken<List<Category>>() {
            }.getType();

    private final Path file;
    private final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    public CategoryRepository() {
        this(Path.of("data", "categories.json"));
    }

    public CategoryRepository(Path file) {
        this.file = file;
    }

    public boolean exists() {
        return Files.exists(file);
    }

    public List<Category> findAll() {
        if (!exists()) {
            return new ArrayList<>();
        }

        try {
            String json = Files.readString(file, StandardCharsets.UTF_8);
            List<Category> categories = gson.fromJson(json, CATEGORY_LIST_TYPE);
            return categories != null ? categories : new ArrayList<>();
        } catch (IOException e) {
            throw new UncheckedIOException(
                    "No se pudo leer el archivo " + file, e
            );
        }
    }

    public void save(List<Category> categories) {
        try {
            Files.createDirectories(file.toAbsolutePath().getParent());
            String json = gson.toJson(categories, CATEGORY_LIST_TYPE);
            Files.writeString(file, json, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new UncheckedIOException(
                    "No se pudo guardar el archivo " + file, e
            );
        }
    }
}
```

Esta clase es casi idéntica a `ProductRepository`: solo cambian el tipo de los elementos (`Category`) y el archivo donde se guardan (`data/categories.json`). Por eso no repetimos su explicación; puedes volver al paso 13 si necesitas repasarla.

> **Nota:** esta duplicación es una señal de que, en un proyecto más grande, convendría extraer una clase genérica que sirva para cualquier entidad. En esta guía mantenemos dos clases separadas para que cada una se pueda leer de forma independiente.

#### Crear el servicio `CategoryService`

Crea la clase `CategoryService` en la ruta `src/main/java/cl/duoc/inventory/service/` con el siguiente código:

```java
package cl.duoc.inventory.service;

import cl.duoc.inventory.model.Category;
import cl.duoc.inventory.repository.CategoryRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Optional;

public class CategoryService {
    public static final String NO_CATEGORY = "Sin categoría";

    private final CategoryRepository repository = new CategoryRepository();
    private final ObservableList<Category> categories =
            FXCollections.observableArrayList();

    private CategoryService() {
        if (repository.exists()) {
            categories.addAll(repository.findAll());
        } else {
            categories.addAll(
                    new Category("Periféricos"),
                    new Category("Electrónica"),
                    new Category("Accesorios")
            );
            saveCategories();
        }
    }

    public static CategoryService getInstance() {
        return InstanceHolder.INSTANCE;
    }

    public ObservableList<Category> getCategories() {
        return categories;
    }

    public Optional<Category> findById(String id) {
        return categories.stream()
                .filter(category -> category.getId().equals(id))
                .findFirst();
    }

    public String getCategoryName(String id) {
        return findById(id)
                .map(Category::getName)
                .orElse(NO_CATEGORY);
    }

    public boolean isNameTaken(String name, Category ignored) {
        return categories.stream()
                .filter(category -> category != ignored)
                .anyMatch(category -> category.getName().equalsIgnoreCase(name));
    }

    public void addCategory(Category category) {
        categories.add(category);
        saveCategories();
    }

    public boolean removeCategory(Category category) {
        boolean removed = categories.remove(category);
        if (removed) {
            saveCategories();
        }
        return removed;
    }

    public void saveCategories() {
        repository.save(categories);
    }

    private static class InstanceHolder {
        private static final CategoryService INSTANCE =
                new CategoryService();
    }
}
```

Veamos cómo funciona:

- **Singleton y lista observable:** sigue el mismo patrón que `ProductService` (paso 7): una única instancia, creada con `InstanceHolder`, que mantiene una `ObservableList` para que las vistas se actualicen solas.
- **El constructor:** si el archivo existe, carga las categorías guardadas. Si no existe, crea tres categorías iniciales y las guarda. A diferencia de los productos, aquí sí sembramos datos: las categorías son parte de la configuración de la aplicación, y sin ellas el formulario de productos no tendría opciones la primera vez que se ejecute. Más adelante, el usuario podrá modificarlas desde la interfaz.
- **`findById(...)`:** busca una categoría por su `id` y retorna un `Optional<Category>`.
- **`getCategoryName(...)`:** retorna el nombre de la categoría o el texto "Sin categoría" si no existe. Así, la tabla de productos nunca mostrará un valor vacío.
- **`isNameTaken(...)`:** indica si ya existe una categoría con ese nombre, sin distinguir mayúsculas. El segundo parámetro permite ignorar la categoría que se está editando, para que pueda conservar su propio nombre.
- **`addCategory(...)`, `removeCategory(...)` y `saveCategories()`:** igual que en `ProductService`, agregar y eliminar guardan automáticamente en el archivo.

> **Concepto:** un `Optional<T>` es un contenedor que puede tener un valor o estar vacío. Obliga a quien lo usa a decidir qué hacer cuando el valor no existe, por ejemplo con `orElse(...)`, en lugar de arriesgarse a un `NullPointerException`.

**Verificación:** Las clases nuevas todavía no se usan en ninguna vista, por lo que no verás cambios al ejecutar la aplicación. En el siguiente paso las conectamos con los productos.

### 15. Relacionar `Product` con `Category`

Ahora `Product` debe guardar una referencia a su categoría en lugar de su nombre. Usaremos el `id`: así, si una categoría cambia de nombre, todos sus productos siguen apuntando a ella sin necesidad de actualizarlos.

Este paso modifica varios archivos que dependen entre sí, así que la aplicación no compilará hasta completarlos todos.

#### Modificar `Product`

En `src/main/java/cl/duoc/inventory/model/Product.java`, reemplaza el atributo `category` por `categoryId`:

```java
private String categoryId;
```

Luego, actualiza el constructor para que reciba y asigne el `id` de la categoría:

```java
public Product(String name, String description, String categoryId, double price, int quantity) {
    this.id = UUID.randomUUID().toString();
    this.name = name;
    this.description = description;
    this.categoryId = categoryId;
    this.price = price;
    this.quantity = quantity;
}
```

Por último, reemplaza los métodos `getCategory()` y `setCategory(...)`:

```java
public String getCategoryId() {
    return categoryId;
}

public void setCategoryId(String categoryId) {
    this.categoryId = categoryId;
}
```

Con esto, `Product` ya no conoce el nombre de su categoría, solo su identificador. Es el mismo principio que usa una base de datos relacional con una clave foránea.

#### Consultar el uso de una categoría desde `ProductService`

Para evitar eliminar una categoría que tiene productos asociados, el servicio debe poder responder si una categoría está en uso. Agrega el siguiente método en `ProductService`:

```java
public boolean isCategoryInUse(String categoryId) {
    return products.stream()
            .anyMatch(product -> categoryId.equals(product.getCategoryId()));
}
```

`stream()` recorre la lista de productos y `anyMatch(...)` retorna `true` si al menos uno cumple la condición, que en este caso es tener ese `categoryId`.

#### Usar las categorías en `ProductController`

En `src/main/java/cl/duoc/inventory/controller/ProductController.java`, primero agrega las importaciones de `Category` y `CategoryService`:

```java
import cl.duoc.inventory.model.Category;
import cl.duoc.inventory.service.CategoryService;
```

Cambia el tipo del `ComboBox`, que ahora contiene objetos `Category` en lugar de texto:

```java
@FXML
private ComboBox<Category> cmbCategory;
```

En el método `initialize()`, reemplaza la línea que cargaba las categorías fijas por esta:

```java
cmbCategory.setItems(CategoryService.getInstance().getCategories());
```

En `handleSaveClick()`, la categoría seleccionada ahora es un objeto `Category`, y lo que guardamos en el producto es su `id`:

```java
Category category = cmbCategory.getValue();
```

```java
Product newProduct = new Product(
        name, description, category.getId(), price, quantity
);
```

```java
product.setCategoryId(category.getId());
```

Por último, en `setProduct(...)`, reemplaza la línea que asignaba la categoría al combo:

```java
cmbCategory.setValue(
        CategoryService.getInstance()
                .findById(product.getCategoryId())
                .orElse(null)
);
```

Veamos los cambios:

- **`setItems(...)` en lugar de `addAll(...)`:** `addAll` copiaba los valores una sola vez. `setItems` conecta el combo directamente con la lista observable del servicio, por lo que cualquier categoría que se agregue o elimine después aparecerá o desaparecerá del combo automáticamente.
- **`validateForm()` no cambia:** ya verificaba que `cmbCategory.getValue()` no fuera `null`, así que en `handleSaveClick()` podemos usar `category.getId()` sin riesgo.
- **`findById(...).orElse(null)`:** si la categoría del producto ya no existe, el combo queda sin selección, lo que obliga al usuario a elegir una al guardar.

#### Mostrar el nombre en `InventoryController`

La tabla de productos mostraba la categoría con `PropertyValueFactory<>("category")`, pero ahora `Product` solo tiene el `id`. Debemos calcular el texto a mostrar. En `InventoryController`, agrega las importaciones:

```java
import cl.duoc.inventory.service.CategoryService;
import javafx.beans.property.SimpleStringProperty;
```

Y en `initialize()`, reemplaza la configuración de `colCategory` por:

```java
colCategory.setCellValueFactory(cellData -> new SimpleStringProperty(
        CategoryService.getInstance()
                .getCategoryName(cellData.getValue().getCategoryId())
));
```

`setCellValueFactory` espera una función que, dada una fila (`cellData`), entregue el valor de la celda como un objeto observable. `PropertyValueFactory` lo hacía leyendo un getter, pero ahora el valor se obtiene consultando al servicio, así que escribimos la función nosotros. `SimpleStringProperty` envuelve el texto en el tipo que la tabla necesita.

> **Nota:** los productos guardados antes de este paso tienen el campo `category` en `data/products.json`. Gson ignora los campos que ya no existen en la clase, por lo que esos productos quedan sin categoría y la tabla mostrará "Sin categoría". Para empezar con datos nuevos, cierra la aplicación y elimina el archivo `data/products.json`.

**Verificación:** Ejecuta la aplicación con `.\mvnw.cmd clean javafx:run`. Al crear un producto, el combo de categorías debe mostrar "Periféricos", "Electrónica" y "Accesorios", y la tabla debe mostrar el nombre de la categoría elegida. Se habrá creado el archivo `data/categories.json` con las tres categorías iniciales.

### 16. Gestionar las categorías desde la aplicación

Las tres categorías iniciales son un punto de partida, pero el objetivo era que la lista se pudiera expandir. En este paso agregamos una ventana para crear, editar y eliminar categorías, a la que se accede desde el menú **Archivo → Categorías**, que hasta ahora no hacía nada.

#### Crear la vista `category-view.fxml`

Crea el archivo `category-view.fxml` en la ruta `src/main/resources/cl/duoc/inventory/view/` con el siguiente código:

```xml
<?xml version="1.0" encoding="UTF-8"?>

<?import java.net.URL?>
<?import javafx.geometry.Insets?>
<?import javafx.scene.control.*?>
<?import javafx.scene.layout.*?>
<BorderPane xmlns:fx="http://javafx.com/fxml"
            xmlns="http://javafx.com/javafx"
            fx:controller="cl.duoc.inventory.controller.CategoryController"
            prefHeight="450.0" prefWidth="500.0">
    <stylesheets>
        <URL value="@../css/styles.css"/>
    </stylesheets>
    <top>
        <VBox spacing="5.0">
            <padding>
                <Insets top="20.0" right="24.0"
                        bottom="15.0" left="24.0"/>
            </padding>
            <Label fx:id="lblTitle"
                   text="Nueva categoría"
                   styleClass="page-title"/>
            <Label text="Selecciona una categoría de la tabla para editarla o eliminarla"
                   styleClass="page-subtitle"/>
        </VBox>
    </top>
    <center>
        <VBox spacing="12.0">
            <padding>
                <Insets top="10.0" right="24.0"
                        bottom="10.0" left="24.0"/>
            </padding>
            <HBox alignment="CENTER_LEFT" spacing="10.0">
                <TextField fx:id="txtName"
                           promptText="Nombre de la categoría"
                           HBox.hgrow="ALWAYS"/>
                <Button text="Guardar"
                        styleClass="btn, btn-primary"
                        onAction="#handleSaveClick"/>
                <Button text="Nueva"
                        styleClass="btn, btn-default"
                        onAction="#handleNewClick"/>
            </HBox>
            <TableView fx:id="tblCategories"
                       VBox.vgrow="ALWAYS">
                <columnResizePolicy>
                    <TableView fx:constant="CONSTRAINED_RESIZE_POLICY"/>
                </columnResizePolicy>
                <columns>
                    <TableColumn fx:id="colName"
                                 text="Nombre"/>
                </columns>
                <placeholder>
                    <Label text="No hay categorías registradas"/>
                </placeholder>
            </TableView>
        </VBox>
    </center>
    <bottom>
        <HBox alignment="CENTER_RIGHT" spacing="10.0">
            <padding>
                <Insets top="10.0" right="24.0"
                        bottom="20.0" left="24.0"/>
            </padding>
            <Button fx:id="btnDelete"
                    text="Eliminar"
                    styleClass="btn, btn-danger"
                    onAction="#handleDeleteClick"/>
            <Region HBox.hgrow="ALWAYS"/>
            <Button text="Cerrar"
                    styleClass="btn, btn-default"
                    onAction="#handleCloseClick"/>
        </HBox>
    </bottom>
</BorderPane>
```

Es una vista similar a las anteriores, con algunas decisiones nuevas:

- **Una sola ventana para crear y editar:** el campo `txtName` y el botón **Guardar** sirven para ambas acciones. El título `lblTitle` indica en qué modo estamos, y el botón **Nueva** vuelve al modo de creación.
- **`HBox.hgrow="ALWAYS"` en el `TextField`:** hace que el campo ocupe todo el ancho disponible de la fila, dejando a los botones a su derecha con su tamaño natural.
- **`TableView` de una columna:** muestra las categorías existentes. Al seleccionar una fila, el controlador cargará su nombre en el campo para poder editarla.
- **`btnDelete` con `fx:id`:** el controlador lo mantiene desactivado mientras no haya una categoría seleccionada.

#### Crear el controlador `CategoryController`

Crea la clase `CategoryController` en la ruta `src/main/java/cl/duoc/inventory/controller/` con el siguiente código:

```java
package cl.duoc.inventory.controller;

import cl.duoc.inventory.model.Category;
import cl.duoc.inventory.service.CategoryService;
import cl.duoc.inventory.service.ProductService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.Optional;

public class CategoryController {
    @FXML
    private Label lblTitle;

    @FXML
    private TextField txtName;

    @FXML
    private TableView<Category> tblCategories;

    @FXML
    private TableColumn<Category, String> colName;

    @FXML
    private Button btnDelete;

    private Category selectedCategory;

    @FXML
    protected void initialize() {
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tblCategories.setItems(CategoryService.getInstance().getCategories());

        tblCategories.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        showEditMode(newValue);
                    }
                });

        showCreateMode();
    }

    @FXML
    protected void handleSaveClick() {
        String name = txtName.getText().trim();

        if (name.isEmpty()) {
            showWarning("Ingresa un nombre para la categoría.");
            return;
        }

        if (CategoryService.getInstance().isNameTaken(name, selectedCategory)) {
            showWarning("Ya existe una categoría con ese nombre.");
            return;
        }

        if (selectedCategory == null) {
            CategoryService.getInstance().addCategory(new Category(name));
        } else {
            selectedCategory.setName(name);
            CategoryService.getInstance().saveCategories();
            tblCategories.refresh();
        }

        showCreateMode();
    }

    @FXML
    protected void handleNewClick() {
        showCreateMode();
    }

    @FXML
    protected void handleDeleteClick() {
        if (ProductService.getInstance()
                .isCategoryInUse(selectedCategory.getId())) {
            showWarning(
                    "No se puede eliminar la categoría \""
                            + selectedCategory.getName()
                            + "\" porque tiene productos asociados."
            );
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Eliminar categoría");
        alert.setHeaderText("¿Estás seguro de eliminar esta categoría?");
        alert.setContentText("Categoría: " + selectedCategory.getName());

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            CategoryService.getInstance().removeCategory(selectedCategory);
            showCreateMode();
        }
    }

    @FXML
    protected void handleCloseClick() {
        txtName.getScene().getWindow().hide();
    }

    private void showCreateMode() {
        selectedCategory = null;
        tblCategories.getSelectionModel().clearSelection();

        lblTitle.setText("Nueva categoría");
        txtName.clear();
        btnDelete.setDisable(true);
    }

    private void showEditMode(Category category) {
        selectedCategory = category;

        lblTitle.setText("Editar categoría");
        txtName.setText(category.getName());
        btnDelete.setDisable(false);
    }

    private void showWarning(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Categorías");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
```

Veamos el controlador por partes:

- **El campo `selectedCategory`:** igual que `product` en `ProductController`, define el modo. Si es `null`, estamos creando; si contiene una categoría, la estamos editando.
- **El *listener* de selección:** `selectedItemProperty().addListener(...)` registra una función que JavaFX ejecuta cada vez que cambia la fila seleccionada. Recibe tres valores: la propiedad observada, el valor anterior y el nuevo. Si hay una fila seleccionada (`newValue != null`), pasamos al modo de edición.
- **`showCreateMode()` y `showEditMode(...)`:** concentran los cambios de la interfaz de cada modo: el título, el contenido del campo y si el botón **Eliminar** está activo. `clearSelection()` dispara el *listener* con un valor `null`, que ignoramos con el `if`.
- **`handleSaveClick()`:** valida que el nombre no esté vacío y que no exista otro igual, usando `isNameTaken(name, selectedCategory)`. A diferencia del formulario de productos, aquí sí avisamos al usuario con una alerta de tipo `WARNING`. Luego, según el modo, agrega una categoría nueva o modifica la existente con `setName(...)`. Como `Category` no usa propiedades observables, tras editar guardamos con `saveCategories()` y llamamos a `refresh()` para que la tabla vuelva a leer los nombres.
- **`handleDeleteClick()`:** antes de eliminar, consulta `ProductService.isCategoryInUse(...)`. Si hay productos asociados, muestra una advertencia y no elimina nada. Esto evita que queden productos apuntando a una categoría que ya no existe. Si no está en uso, pide confirmación igual que el formulario de productos.
- **`handleCloseClick()`:** cierra la ventana, con la misma técnica del paso 10.

#### Abrir la ventana desde el menú

Primero, conecta el elemento de menú **Categorías** con un manejador. En `inventory-view.fxml`, reemplaza la línea `<MenuItem text="Categorías"/>` por:

```xml
<MenuItem text="Categorías"
          onAction="#handleCategoriesClick"/>
```

Luego, en `InventoryController`, agrega el manejador y el método que abre la ventana, antes de `openProductView(...)`:

```java
@FXML
protected void handleCategoriesClick() {
    openCategoryView();
}

private void openCategoryView() {
    try {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/cl/duoc/inventory/view/category-view.fxml"
                )
        );
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setTitle("Categorías");
        Scene scene = new Scene(root);
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        tblProducts.refresh();
    } catch (IOException e) {
        e.printStackTrace();
    }
}
```

Es el mismo patrón de `openProductView(...)`, más simple porque no necesita enviarle datos al controlador. Al cerrar la ventana, `tblProducts.refresh()` actualiza la tabla de productos, por si se renombró alguna categoría que estaba en uso.

**Verificación:** Ejecuta la aplicación con `.\mvnw.cmd clean javafx:run` y abre **Archivo → Categorías**. Para comprobar que todo funciona:

1. Crea una categoría nueva, por ejemplo "Hogar", y verifica que aparece en la tabla.
2. Intenta crear otra con el mismo nombre: deberías ver una advertencia.
3. Abre **Nuevo producto**: el combo debe incluir "Hogar".
4. Crea un producto con la categoría "Hogar", vuelve a **Categorías**, renómbrala y ciérrala: la tabla de productos debe mostrar el nombre nuevo.
5. Intenta eliminar "Hogar" mientras tenga productos: deberías ver una advertencia. Elimina una categoría sin productos para comprobar que sí se elimina.
6. Cierra y vuelve a abrir la aplicación: las categorías deben mantenerse, porque se guardan en `data/categories.json`.

### 17. Cambiar el ícono de las ventanas (opcional)

Por defecto, las ventanas de una aplicación JavaFX muestran el ícono genérico de Java, tanto en la barra de título como en la barra de tareas. Este paso es opcional y te permite reemplazarlo por una imagen propia. El proyecto incluye un ejemplo (`logo.png`), pero puedes usar la imagen que prefieras.

#### Preparar la imagen

Crea la carpeta `images` en la ruta `src/main/resources/cl/duoc/inventory/`, junto a las carpetas `css` y `view` que creamos en el paso 2. Luego, copia allí la imagen que quieras usar como ícono. Para que se vea bien, considera lo siguiente:

- **Formato:** usa PNG, JPG, GIF o BMP. El formato `.ico` no es compatible con JavaFX.
- **Forma y tamaño:** una imagen cuadrada de al menos 256×256 píxeles. JavaFX la reduce según el lugar donde se muestre, pero no la amplía con buena calidad.
- **Fondo:** un PNG con fondo transparente se ve mejor, porque el ícono no queda dentro de un recuadro.
- **Nombre:** en esta guía usaremos `logo.png`. Si tu archivo se llama distinto, cambia el nombre en el código del siguiente apartado.

#### Crear la clase `WindowIcon`

Nuestra aplicación abre tres ventanas: la principal, el formulario de productos y la gestión de categorías. Cada una es un `Stage` independiente y necesita su propio ícono, así que, para no repetir el mismo código tres veces, lo concentramos en una clase de utilidad.

Crea la clase `WindowIcon` en un nuevo paquete `cl.duoc.inventory.util`, en la ruta `src/main/java/cl/duoc/inventory/util/`, con el siguiente código:

```java
package cl.duoc.inventory.util;

import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;

public final class WindowIcon {
    private static final String ICON_PATH =
            "/cl/duoc/inventory/images/logo.png";

    private WindowIcon() {
    }

    public static void apply(Stage stage) {
        try (InputStream stream =
                     WindowIcon.class.getResourceAsStream(ICON_PATH)) {
            if (stream != null) {
                stage.getIcons().add(new Image(stream));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

Veamos cómo funciona:

- **`final` y constructor `private`:** es una clase de utilidad, que solo ofrece métodos estáticos. Al impedir que se creen instancias, dejamos claro que se usa como `WindowIcon.apply(...)`.
- **`ICON_PATH`:** es la ruta de la imagen dentro de los recursos. Empieza con `/`, por lo que se calcula desde la raíz de los recursos, igual que la ruta de las vistas en el paso 11. Si tu imagen tiene otro nombre, cámbialo aquí.
- **`getResourceAsStream(...)`:** abre el archivo como un flujo de datos. Si no lo encuentra, retorna `null`; en ese caso el método no hace nada, y la ventana conserva el ícono por defecto en lugar de fallar. Esto es lo que hace que el paso sea opcional.
- **`try` con recursos:** el flujo declarado entre paréntesis se cierra automáticamente al terminar, incluso si ocurre un error.
- **`stage.getIcons()`:** es una lista de imágenes. JavaFX elige de ella la que mejor se ajusta a cada lugar donde se muestra el ícono. Con una sola imagen, la escala; si quisieras más nitidez, podrías agregar varias versiones de distintos tamaños.

#### Aplicar el ícono a las ventanas

En `InventoryApplication`, agrega la importación y llama a `WindowIcon.apply(...)` después de asignar el título:

```java
import cl.duoc.inventory.util.WindowIcon;
```

```java
stage.setTitle("DSY1102 Inventario");
WindowIcon.apply(stage);
```

En `InventoryController`, agrega la misma importación y aplica el ícono en los dos métodos que abren ventanas. En `openProductView(...)`, después de asignar el título:

```java
stage.setTitle(
        product == null ? "Nuevo producto" : "Editar producto"
);
WindowIcon.apply(stage);
```

Y en `openCategoryView()`:

```java
stage.setTitle("Categorías");
WindowIcon.apply(stage);
```

Cada `Stage` tiene su propia lista de íconos y no hereda la de la ventana principal, por eso hay que aplicarlo en las tres.

> **Nota:** los cuadros de diálogo (`Alert`) son ventanas independientes y no se modifican en este paso.

**Verificación:** Ejecuta la aplicación con `.\mvnw.cmd clean javafx:run`. Deberías ver tu imagen en la barra de título y en la barra de tareas, y también en las ventanas de producto y de categorías. Si sigues viendo el ícono de Java, revisa que el archivo esté en la carpeta `images` y que su nombre coincida con `ICON_PATH`. Usa siempre `clean` al ejecutar, para que Maven copie los recursos nuevos.

> **Tip pedagógico:** prueba con distintas imágenes. También puedes agregar dos versiones de tamaños diferentes con `stage.getIcons().addAll(...)` y ver cuál elige JavaFX en cada lugar.

### 18. Revisar la estructura final del proyecto

Con los 17 pasos completados, el proyecto contiene todos los archivos de la aplicación. Revisar su estructura final ayuda a ver cómo se organiza cada capa del patrón MVC y a comprobar que no falte ningún archivo. Junto a cada uno indicamos el paso en que lo creamos o lo modificamos:

```text
dsy1102-inventory/
├── .mvn/                                           # Maven Wrapper (paso 1)
├── data/                                           # Datos de la aplicación (se crea al ejecutarla)
│   ├── categories.json                             # Paso 14
│   └── products.json                               # Paso 13
├── src/
│   └── main/
│       ├── java/
│       │   ├── cl/duoc/inventory/
│       │   │   ├── controller/
│       │   │   │   ├── CategoryController.java     # Paso 16
│       │   │   │   ├── InventoryController.java    # Paso 6
│       │   │   │   └── ProductController.java      # Paso 10
│       │   │   ├── model/
│       │   │   │   ├── Category.java               # Paso 14
│       │   │   │   └── Product.java                # Paso 4
│       │   │   ├── repository/
│       │   │   │   ├── CategoryRepository.java     # Paso 14
│       │   │   │   └── ProductRepository.java      # Paso 13
│       │   │   ├── service/
│       │   │   │   ├── CategoryService.java        # Paso 14
│       │   │   │   └── ProductService.java         # Paso 7
│       │   │   ├── util/
│       │   │   │   └── WindowIcon.java             # Paso 17
│       │   │   ├── InventoryApplication.java       # Pasos 1 y 2
│       │   │   └── Launcher.java                   # Paso 1
│       │   └── module-info.java                    # Pasos 1 y 3
│       └── resources/cl/duoc/inventory/
│           ├── css/
│           │   └── styles.css                      # Paso 12
│           ├── images/
│           │   └── logo.png                        # Paso 17
│           └── view/
│               ├── category-view.fxml              # Paso 16
│               ├── inventory-view.fxml             # Paso 5
│               └── product-view.fxml               # Paso 9
├── target/                                         # Archivos compilados (los genera Maven)
├── .gitignore                                      # Paso 1 (si creaste el repositorio Git) y paso 13
├── mvnw                                            # Paso 1
├── mvnw.cmd                                        # Paso 1
└── pom.xml                                         # Pasos 1, 12 y 13
```

Veamos cómo se organiza:

- **Modelo (`model/`):** las entidades `Product` y `Category`, que solo describen datos.
- **Vista (`resources/.../view/`):** los tres archivos FXML. Junto a ellos, `css/` e `images/` contienen los recursos de apariencia: la hoja de estilos y el ícono de las ventanas.
- **Controlador (`controller/`):** un controlador por cada vista, que responde a las acciones del usuario.
- **Servicio (`service/`):** la lógica de negocio y los datos compartidos entre ventanas.
- **Repositorio (`repository/`):** el acceso a los archivos JSON.
- **Utilidades (`util/`):** `WindowIcon`, que no pertenece a ninguna capa del patrón porque solo ayuda a configurar las ventanas.
- **Arranque:** `Launcher` e `InventoryApplication` están en la raíz del paquete. `module-info.java` está un nivel más arriba, en la raíz de `java/`, porque describe el módulo completo.
- **Maven:** `pom.xml` define las dependencias, y `mvnw`, `mvnw.cmd` y la carpeta `.mvn/` forman el Maven Wrapper.
- **Archivos generados:** `target/` lo crea Maven al compilar, y `data/` lo crea la aplicación al guardar su primer dato. Ninguno de los dos se sube a Git, porque ambos están en el `.gitignore`.

Además de estos archivos, el repositorio incluye `README.md` y `LICENSE`, que no forman parte de la aplicación.

Si observas las importaciones de cada clase, verás que la dependencia fluye en un solo sentido: los controladores usan servicios, los servicios usan repositorios y todos usan el modelo. Ningún repositorio conoce a un servicio, ni ningún servicio a un controlador. Esa regla es la que mantiene separadas las responsabilidades de cada capa.

**Verificación:** Compara el explorador de archivos de tu IDE con la estructura anterior. Luego, ejecuta la aplicación con `.\mvnw.cmd clean javafx:run` y comprueba que todo funcione: la tabla de productos, el formulario, la gestión de categorías, el guardado de datos al cerrar y volver a abrir la aplicación, y el ícono de las ventanas.

## Conceptos Avanzados

Esta sección complementa los pasos anteriores y no modifica el proyecto que construiste. Presenta dos ideas que mejoran el diseño de la aplicación, la **inyección de dependencias** y el **repositorio genérico**, y muestra cómo podrían aplicarse a este proyecto.

### Inyección de dependencias

#### Definiciones

Antes de ver el código, conviene fijar el vocabulario:

- **Dependencia:** cualquier objeto que una clase necesita para hacer su trabajo. Por ejemplo, `ProductService` depende de un repositorio para guardar los productos, y `ProductController` depende de `ProductService` y de `CategoryService`.
- **Acoplamiento:** el grado en que una clase conoce los detalles de otra y depende de ellos. Con un acoplamiento alto, un cambio en una clase obliga a modificar las que la usan.
- **Inversión de control (IoC):** en lugar de que cada clase cree o busque por sí misma lo que necesita, un elemento externo se lo entrega. Se "invierte" quién tiene el control de crear los objetos.
- **Principio de inversión de dependencias (DIP):** es la letra **D** de los principios SOLID. Dice que las clases de alto nivel, que contienen la lógica, no deberían depender de clases concretas de bajo nivel, sino de **abstracciones** como las interfaces. En nuestro caso, `ProductService` no debería depender de `JsonRepository` (un detalle sobre cómo se guardan los datos), sino de una interfaz `Repository<Product>`.
- **Inyección de dependencias (DI):** es la técnica que aplica la inversión de control. Una clase **recibe** sus dependencias desde afuera, normalmente a través de su constructor, en lugar de crearlas con `new` o de pedirlas a un Singleton.
- **Raíz de composición (*composition root*):** el único lugar de la aplicación donde se crean los objetos concretos y se conectan entre sí. En nuestro proyecto, sería `InventoryApplication.start(...)`.
- **Pure DI:** hacer inyección de dependencias "a mano", usando solo constructores y código Java normal, sin ninguna biblioteca externa.
- **Contenedor de DI (*framework*):** una biblioteca que crea y conecta los objetos automáticamente, como Spring, Guice o Dagger. Resulta muy útil en aplicaciones grandes, pero no es necesaria para aplicar DI.

> **Concepto:** inyectar una dependencia no requiere nada sofisticado. Pasarle un objeto a un constructor ya es inyección de dependencias; los frameworks solo automatizan ese trabajo.

#### ¿Por qué es conveniente?

- **Dependencias explícitas:** basta con leer el constructor de una clase para saber qué necesita. Sin DI, esas necesidades quedan escondidas dentro de los métodos, en llamadas como `ProductService.getInstance()`.
- **Bajo acoplamiento:** como la clase depende de una interfaz y no de una implementación concreta, puede funcionar con cualquier objeto que cumpla ese contrato.
- **Facilidad para probar:** en una prueba podemos entregarle a un servicio un repositorio falso que guarda los datos en memoria, sin leer ni escribir archivos reales.
- **Facilidad para cambiar:** si más adelante los datos se guardaran en una base de datos, solo cambiaría la raíz de composición, que elegiría otra implementación de `Repository`. Los servicios y los controladores quedarían intactos.
- **Sin estado global oculto:** un Singleton con `getInstance()` es, en el fondo, una variable global: cualquier clase puede usarlo sin declararlo, y las pruebas comparten su estado.
- **Control sobre las instancias:** es la raíz de composición la que decide cuántas instancias existen y cuándo se crean, en lugar de que cada clase lo decida por su cuenta.

#### El acoplamiento en nuestro proyecto

Revisemos dos líneas de nuestro código actual. Dentro de `ProductService` (paso 13), el servicio crea su propio repositorio:

```java
private final ProductRepository repository = new ProductRepository();
```

Y dentro de `ProductController` (paso 10), el controlador busca el servicio a través de un Singleton:

```java
ProductService.getInstance().addProduct(newProduct);
```

Esto tiene varias consecuencias:

- Para probar `ProductService` habría que leer y escribir el archivo real `data/products.json`, porque no hay forma de reemplazar el repositorio.
- Las dependencias de los controladores no se ven en ninguna parte: solo se descubren leyendo todo el código.
- El servicio conoce un detalle que no le corresponde: que los datos se guardan en un archivo JSON, y en cuál.

> **Nota:** esto no significa que el Singleton haya sido un error. Para aprender JavaFX era la forma más simple de compartir datos entre ventanas. Aquí mostramos el paso siguiente, cuando el proyecto crece y queremos pruebas y flexibilidad.

#### Pure DI: sin frameworks

La idea de Pure DI es construir el grafo de objetos en un solo lugar, la raíz de composición, y pasarlos hacia abajo mediante constructores:

```text
InventoryApplication (raíz de composición)
├── crea  JsonRepository<Product>   ─┐
├── crea  JsonRepository<Category>   │ se entregan por constructor
├── crea  ProductService(repositorio)
├── crea  CategoryService(repositorio)
└── crea  ControllerFactory(servicios)
          └── crea los controladores con los servicios que necesitan
```

Este enfoque no necesita bibliotecas: es solo Java. Un framework de DI se justifica cuando hay decenas de clases y la raíz de composición se vuelve larga y repetitiva. En una aplicación de este tamaño, hacerlo a mano es más claro y permite ver exactamente qué ocurre.

### Repositorio genérico

#### El problema de la duplicación

`ProductRepository` (paso 13) y `CategoryRepository` (paso 14) son casi idénticas. Solo cambian tres detalles: el tipo de los elementos (`Product` o `Category`), el `TypeToken` que lo representa y el nombre del archivo. El resto del código, que lee y escribe JSON, es el mismo. Si encontráramos un error en una, tendríamos que corregirlo en ambas, y cada entidad nueva exigiría copiar la clase otra vez.

La solución es una **clase genérica**.

#### Clases genéricas

Una clase genérica declara uno o más **parámetros de tipo** entre `<` y `>`, que se reemplazan por un tipo concreto al usarla. Ya usamos clases genéricas sin definirlas: `ObservableList<Product>`, `TableView<Product>` y `Optional<Category>`. En `JsonRepository<T>`, la `T` es un tipo que se decide al crear el repositorio:

```java
new JsonRepository<>(Path.of("data", "products.json"), Product.class)
```

Aquí `T` pasa a ser `Product`, y el compilador garantiza que `findAll()` retorne una `List<Product>` y que `save(...)` solo acepte productos. Con `Category.class`, se obtiene un repositorio de categorías con el mismo código.

> **Concepto:** en tiempo de ejecución, Java "olvida" los tipos genéricos (*type erasure*): una `List<Product>` es solo una `List`. Gson necesita saber qué objetos crear, por eso `JsonRepository` recibe también la clase `Class<T>` (por ejemplo, `Product.class`) y construye con ella el tipo completo mediante `TypeToken.getParameterized(List.class, elementType)`. Es la versión genérica del `TypeToken` que usamos en el paso 13.

#### La interfaz `Repository<T>` y la clase `JsonRepository<T>`

Primero, definimos el contrato como una interfaz. Es la abstracción de la que dependerán los servicios:

```java
package cl.duoc.inventory.repository;

import java.util.List;

public interface Repository<T> {
    boolean exists();

    List<T> findAll();

    void save(List<T> items);
}
```

Luego, la implementación que guarda los datos en un archivo JSON. Es la combinación de las dos clases anteriores:

```java
package cl.duoc.inventory.repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class JsonRepository<T> implements Repository<T> {
    private final Path file;
    private final Type listType;
    private final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    public JsonRepository(Path file, Class<T> elementType) {
        this.file = file;
        this.listType = TypeToken
                .getParameterized(List.class, elementType)
                .getType();
    }

    @Override
    public boolean exists() {
        return Files.exists(file);
    }

    @Override
    public List<T> findAll() {
        if (!exists()) {
            return new ArrayList<>();
        }

        try {
            String json = Files.readString(file, StandardCharsets.UTF_8);
            List<T> items = gson.fromJson(json, listType);
            return items != null ? items : new ArrayList<>();
        } catch (IOException e) {
            throw new UncheckedIOException(
                    "No se pudo leer el archivo " + file, e
            );
        }
    }

    @Override
    public void save(List<T> items) {
        try {
            Files.createDirectories(file.toAbsolutePath().getParent());
            String json = gson.toJson(items, listType);
            Files.writeString(file, json, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new UncheckedIOException(
                    "No se pudo guardar el archivo " + file, e
            );
        }
    }
}
```

Veamos los puntos clave:

- **`implements Repository<T>`:** `JsonRepository` es una de las posibles implementaciones del contrato. Otra podría guardar en una base de datos, o en memoria para las pruebas.
- **El constructor:** recibe el archivo y la clase de los elementos. Con ella calcula una sola vez `listType`, el tipo `List<T>` que Gson necesita para leer y escribir.
- **`@Override`:** indica que el método implementa uno de la interfaz, y el compilador avisa si la firma no coincide.
- **El resto:** es idéntico a `ProductRepository` y `CategoryRepository`, pero escrito una sola vez.

Con esta clase, `ProductRepository` y `CategoryRepository` ya no hacen falta.

### Aplicar DI al proyecto

Veamos cómo quedaría el proyecto si aplicáramos Pure DI y el repositorio genérico. Los cambios son los siguientes.

#### Servicios con inyección por constructor

Los servicios ya no son Singletons: reciben su repositorio por el constructor, y dependen de la interfaz `Repository<T>`. Desaparecen `getInstance()` y `InstanceHolder`:

```java
package cl.duoc.inventory.service;

import cl.duoc.inventory.model.Product;
import cl.duoc.inventory.repository.Repository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProductService {
    private final Repository<Product> repository;
    private final ObservableList<Product> products =
            FXCollections.observableArrayList();

    public ProductService(Repository<Product> repository) {
        this.repository = repository;
        products.addAll(repository.findAll());
    }

    public ObservableList<Product> getProducts() {
        return products;
    }

    public void addProduct(Product product) {
        products.add(product);
        saveProducts();
    }

    public boolean removeProduct(Product product) {
        boolean removed = products.remove(product);
        if (removed) {
            saveProducts();
        }
        return removed;
    }

    public boolean isCategoryInUse(String categoryId) {
        return products.stream()
                .anyMatch(product -> categoryId.equals(product.getCategoryId()));
    }

    public void saveProducts() {
        repository.save(products);
    }
}
```

`CategoryService` cambia de la misma forma: su constructor recibe un `Repository<Category>`. Fíjate en que los servicios ya no saben que existe un archivo JSON ni cómo se llama: solo conocen el contrato.

#### Controladores y `ControllerFactory`

Los controladores también reciben sus dependencias por el constructor. Por ejemplo, `ProductController`:

```java
public class ProductController {
    private final ProductService productService;
    private final CategoryService categoryService;

    public ProductController(ProductService productService,
                             CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }
```

En el resto de la clase, `ProductService.getInstance()` se reemplaza por el campo `productService` (y lo mismo con `categoryService`). `InventoryController` y `CategoryController` se modifican igual.

Aparece un problema: normalmente es `FXMLLoader` quien crea los controladores, y para eso necesita un constructor sin parámetros. La solución es entregarle una **fábrica de controladores** con `setControllerFactory(...)`: una función que, dado el tipo de controlador que `FXMLLoader` necesita, retorna una instancia ya construida con sus dependencias:

```java
package cl.duoc.inventory.controller;

import cl.duoc.inventory.service.CategoryService;
import cl.duoc.inventory.service.ProductService;
import javafx.util.Callback;

public class ControllerFactory implements Callback<Class<?>, Object> {
    private final ProductService productService;
    private final CategoryService categoryService;

    public ControllerFactory(ProductService productService,
                             CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @Override
    public Object call(Class<?> controllerType) {
        if (controllerType == InventoryController.class) {
            return new InventoryController(
                    productService, categoryService, this
            );
        }
        if (controllerType == ProductController.class) {
            return new ProductController(productService, categoryService);
        }
        if (controllerType == CategoryController.class) {
            return new CategoryController(productService, categoryService);
        }
        throw new IllegalArgumentException(
                "Controlador desconocido: " + controllerType.getName()
        );
    }
}
```

Veamos cómo funciona:

- **`implements Callback<Class<?>, Object>`:** `Callback` es una interfaz de JavaFX con un método `call` que recibe un valor y retorna otro. Aquí recibe la clase del controlador y retorna el controlador.
- **Las dependencias:** la fábrica recibe los servicios una vez y los reparte a cada controlador que crea.
- **`this` en `InventoryController`:** la ventana principal abre las ventanas de producto y de categorías, y esas también necesitan crear sus controladores con la fábrica. Por eso `InventoryController` la recibe como dependencia y la usa en `openProductView(...)` y `openCategoryView()`:

```java
FXMLLoader loader = new FXMLLoader(
        getClass().getResource(
                "/cl/duoc/inventory/view/product-view.fxml"
        )
);
loader.setControllerFactory(controllerFactory);
Parent root = loader.load();
```

- **El `throw`:** si `FXMLLoader` pide un controlador que la fábrica no conoce, falla con un mensaje claro en lugar de devolver `null`.

#### La raíz de composición

Finalmente, `InventoryApplication` es el único lugar que crea objetos concretos y los conecta:

```java
package cl.duoc.inventory;

import cl.duoc.inventory.controller.ControllerFactory;
import cl.duoc.inventory.model.Category;
import cl.duoc.inventory.model.Product;
import cl.duoc.inventory.repository.JsonRepository;
import cl.duoc.inventory.repository.Repository;
import cl.duoc.inventory.service.CategoryService;
import cl.duoc.inventory.service.ProductService;
import cl.duoc.inventory.util.WindowIcon;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;
import java.nio.file.Path;

public class InventoryApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Repository<Product> productRepository = new JsonRepository<>(
                Path.of("data", "products.json"), Product.class
        );
        Repository<Category> categoryRepository = new JsonRepository<>(
                Path.of("data", "categories.json"), Category.class
        );

        ProductService productService = new ProductService(productRepository);
        CategoryService categoryService = new CategoryService(categoryRepository);

        FXMLLoader fxmlLoader = new FXMLLoader(
                InventoryApplication.class.getResource("view/inventory-view.fxml"));
        fxmlLoader.setControllerFactory(
                new ControllerFactory(productService, categoryService)
        );

        Scene scene = new Scene(fxmlLoader.load(), 900, 600);
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        stage.setTitle("DSY1102 Inventario");
        WindowIcon.apply(stage);
        stage.setScene(scene);
        stage.show();
    }
}
```

Aquí se decide todo lo que antes estaba disperso: dónde se guardan los datos, qué implementación de `Repository` se usa, cuántas instancias de cada servicio existen y cómo se entregan a los controladores. Para guardar en otro formato, bastaría con cambiar las dos primeras líneas de `start(...)`.

> **Nota:** para aplicar estos cambios en tu proyecto, te recomendamos este orden: (1) crear `Repository` y `JsonRepository` y eliminar los dos repositorios anteriores, (2) modificar los servicios, (3) modificar los tres controladores, (4) crear `ControllerFactory` y (5) modificar `InventoryApplication`. El proyecto no compilará hasta completar todos los cambios, porque las clases dependen unas de otras.

#### Probar con un repositorio falso

La gran ventaja práctica de este diseño aparece al escribir pruebas. Como `ProductService` depende de la interfaz `Repository<Product>`, en una prueba podemos entregarle una implementación falsa que guarda los datos en memoria:

```java
package cl.duoc.inventory.service;

import cl.duoc.inventory.model.Product;
import cl.duoc.inventory.repository.Repository;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProductServiceTest {

    private static class InMemoryRepository implements Repository<Product> {
        private List<Product> stored = new ArrayList<>();
        private int saves = 0;

        @Override
        public boolean exists() {
            return true;
        }

        @Override
        public List<Product> findAll() {
            return new ArrayList<>(stored);
        }

        @Override
        public void save(List<Product> items) {
            stored = new ArrayList<>(items);
            saves++;
        }
    }

    @Test
    void addProductSavesItInTheRepository() {
        InMemoryRepository repository = new InMemoryRepository();
        ProductService service = new ProductService(repository);

        service.addProduct(new Product("Teclado", "USB", "cat-1", 45990.0, 15));

        assertEquals(1, repository.stored.size());
        assertEquals(1, repository.saves);
    }

    @Test
    void isCategoryInUseDetectsProductsOfThatCategory() {
        ProductService service = new ProductService(new InMemoryRepository());
        service.addProduct(new Product("Teclado", "USB", "cat-1", 45990.0, 15));

        assertTrue(service.isCategoryInUse("cat-1"));
        assertFalse(service.isCategoryInUse("cat-2"));
    }
}
```

Veamos qué ocurre:

- **`InMemoryRepository`:** implementa el mismo contrato que `JsonRepository`, pero guarda la lista en un campo y cuenta cuántas veces se guardó. No toca el disco.
- **Cada prueba** crea su propio repositorio y su propio servicio, así que no comparten estado. Con un Singleton, esto no sería posible.
- **`assertEquals` y `assertTrue`:** verifican que, al agregar un producto, el servicio lo haya guardado en el repositorio y que `isCategoryInUse` distinga las categorías.

> **Nota:** para que `.\mvnw.cmd test` ejecute pruebas escritas con JUnit 5, agrega el siguiente plugin en la sección `<build><plugins>` de tu `pom.xml`. Con la versión de Surefire que trae Maven por defecto, el resultado sería `Tests run: 0` y las pruebas no se ejecutarían:
>
> ```xml
> <plugin>
>     <groupId>org.apache.maven.plugins</groupId>
>     <artifactId>maven-surefire-plugin</artifactId>
>     <version>3.2.5</version>
> </plugin>
> ```

### Antes y después

| Aspecto | Hoy (Singleton) | Con Pure DI y repositorio genérico |
| :--- | :--- | :--- |
| Obtener un servicio | `ProductService.getInstance()` en cualquier parte | Se recibe por el constructor |
| Repositorio del servicio | `new ProductRepository()` dentro de la clase | Una interfaz `Repository<Product>` recibida |
| Repositorios | Dos clases casi idénticas | Una sola clase `JsonRepository<T>` |
| Dependencias de una clase | Escondidas en el código | Visibles en el constructor |
| Pruebas | Usan los archivos reales | Usan un repositorio falso en memoria |
| Dónde se conecta todo | Disperso en varias clases | En `InventoryApplication` |
| Código adicional | Ninguno | `Repository`, `ControllerFactory` y la raíz de composición |

La última fila muestra el costo: hay un poco más de código de arranque. Por eso en esta guía empezamos con Singletons, que son más simples, y reservamos DI para cuando el proyecto necesita pruebas o crece. Es una decisión de diseño, no una regla: la inyección de dependencias paga su costo cuando el código debe ser fácil de probar y de cambiar.

---

## Autor

[José Miguel Candia](https://github.com/jmcandia)

---

## Licencia

Este proyecto está bajo la Licencia MIT. Consulta el archivo [LICENSE](LICENSE) para más detalles.
