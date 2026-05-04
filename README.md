# 🛒 Proyecto: Sistema de Gestión de Tienda en Java

¡Hola! 👋 Soy alumno del curso de Java Talento Tech, y este es mi proyecto de pre-entrega. Aquí les cuento cómo armé esta aplicación para gestionar productos y pedidos, aplicando todo lo que vinimos viendo en las clases: Objetos, Colecciones, Excepciones y más.

## 🚀 ¿De qué trata el proyecto?:

La idea era crear un sistema que no solo guarde productos, sino que también permita manejar un stock real y crear pedidos que agrupen varios artículos. Me enfoqué en que sea ordenado y fácil de usar desde la consola.

---

## 🏗️ Cómo lo organicé (Estructura de Paquetes):

Para que el código no sea un lío, lo separé en paquetes. Aprendí que esto ayuda mucho a encontrar las cosas rápido:

- **`model`**: Acá están las "plantillas" de mis objetos.
  - `Producto`: Es la clase base (abstracta).
  - `ProductoPerecedero` y `ProductoElectronico`: Usé **Herencia** para especializarlos.
  - `Pedido` y `LineaPedido`: Para manejar las ventas.
- **`service`**: Acá puse la "inteligencia" del programa (`TiendaService`). Es donde se busca, se agrega y se valida el stock.
- **`exception`**: Creé mis propias excepciones para que el programa no "explote" si algo sale mal (como quedarse sin stock).
- **`main`**: El punto de entrada con el menú interactivo.

---

## 🧠 Lógica y Desafíos:

### 1. Pruebas Rápidas (Productos Hardcodeados):

Para no tener que cargar productos a mano cada vez que probaba el programa, **hardcodeé** algunos artículos iniciales (como Leche, Auriculares y Pan) directamente en el código. Esto me salvó la vida para testear rápido si los descuentos de stock y los totales de los pedidos funcionaban bien sin perder tiempo en el menú de carga.

### 2. Superando Obstáculos:

Durante el desarrollo me choqué con varios problemas que me hicieron renegar un poco:

- **El error de `Optional`**: Al principio usé una lógica más avanzada con `Streams` y `Optional` en la búsqueda, pero me tiraba errores de compatibilidad. Lo solucioné volviendo a lo básico: un bucle `for-each` y un `Iterator` para eliminar, lo que dejó el código mucho más estable.
- **Limpieza de código**: Tenía el código lleno de comentarios que me servían de guía mientras aprendía, pero para la entrega final decidí borrarlos todos. Quería que el código hablara por sí solo y que se vea lo más "limpio" y profesional posible.

### 3. El uso de POO (Programación Orientada a Objetos):

Lo más interesante fue aplicar **Polimorfismo**. Por ejemplo, aunque tenga productos electrónicos o comida, todos heredan de `Producto`. Esto me permitió guardarlos a todos en una sola lista: `ArrayList<Producto>`.

### 4. Gestión de Stock:

Cuando alguien arma un pedido, el sistema primero verifica si hay suficiente cantidad. Si no hay, lanza una `StockInsuficienteException`. Si todo está bien, descuenta automáticamente del stock. ¡Fue un desafío hacer que las cantidades coincidan!

### 5. El Menú Interactivo:

Quería que el usuario no rompiera el programa si ponía una letra en vez de un número. Por eso, usé `Scanner` dentro de bloques `try-catch` y métodos auxiliares para validar las entradas.

---

## 🛠️ Cómo correrlo:

Si querés probarlo, tenés que compilar todos los paquetes. Yo lo hago así desde la terminal:

1.  **Compilar:**
    ```bash
    "C:\Program Files\java\jdk-23\bin\javac.exe" -d . ar/com/cac/preentrega/exception/*.java ar/com/cac/preentrega/model/*.java ar/com/cac/preentrega/service/*.java ar/com/cac/preentrega/main/*.java
    ```
2.  **Ejecutar:**
    ```bash
    "C:\Program Files\java\jdk-23\bin\java.exe" -cp . ar.com.cac.preentrega.main.Main
    ```

---

## 📝 Conclusión:

Este proyecto me sirvió para entender cómo interactúan las clases entre sí y la importancia de validar los datos que ingresa el usuario. Todavía hay cosas para mejorar.

Profesor: Miguel Nefle

Tutora: Natalia Themtham

Alumno: Ezequiel Torres

¡Gracias por revisar mi código!
