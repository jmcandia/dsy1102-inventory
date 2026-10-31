# Guía de Laboratorio: Aplicación `DSY1102 Inventory`

Proyecto pedagógico de referencia para la asignatura **DSY1102 - Programación Orientada al Objeto** (Duoc UC). Este repositorio sirve como punto de partida práctico para aprender a construir aplicaciones de escritorio en Java utilizando **JavaFX** y el patrón arquitectónico **Modelo-Vista-Controlador (MVC)**.

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
- **Persistencia de Datos con `Gson`**: Serializar y deserializar las entidades del dominio a formato **JSON** mediante la biblioteca **Gson**, de modo que el inventario sobreviva entre ejecuciones de la aplicación. Se abordan la lectura y escritura de archivos, el manejo de colecciones genéricas (`TypeToken`) y la separación entre la capa de persistencia y la lógica de negocio.
- **Buenas prácticas de POO**: Aplicar cohesión, bajo acoplamiento y validaciones en la capa de negocio.

---

## Tecnologías y Requisitos

- **Java Development Kit (JDK)**: Versión 21 o superior recomendada.
- **JavaFX**: Versión 21 (`javafx-controls`, `javafx-fxml`).
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

> **En resumen:** *JavaFX* es la tecnología; *OpenJFX* es la implementación que descargamos con Maven. En la práctica se usan como sinónimos.

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
        stage.setTitle("Inventario DSY1102");
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

> **Ventaja pedagógica:** FXML materializa la **V** del patrón MVC. La vista queda en un archivo aparte y el controlador no contiene instrucciones de maquetación.

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

## Autor

[José Miguel Candia](https://github.com/jmcandia)

---

## Licencia

Este proyecto está bajo la Licencia MIT. Consulta el archivo [LICENSE](LICENSE) para más detalles.
