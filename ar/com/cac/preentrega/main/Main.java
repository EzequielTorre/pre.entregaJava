package ar.com.cac.preentrega.main;

import ar.com.cac.preentrega.exception.ProductoNoEncontradoException;
import ar.com.cac.preentrega.exception.StockInsuficienteException;
import ar.com.cac.preentrega.model.*;
import ar.com.cac.preentrega.service.TiendaService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static TiendaService service = new TiendaService();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Precarga de algunos productos para facilitar la prueba
        service.agregarProducto(new ProductoPerecedero("Leche Entera", 1500.0, 50, 7));
        service.agregarProducto(new ProductoElectronico("Auriculares BT", 45000.0, 10, 12));
        service.agregarProducto(new ProductoPerecedero("Pan Artesanal", 1200.0, 20, 3));

        boolean salir = false;
        while (!salir) {
            mostrarMenu();
            int opcion = leerEntero("Seleccione una opción: ");

            switch (opcion) {
                case 1:
                    agregarProductoMenu();
                    break;
                case 2:
                    listarProductosMenu();
                    break;
                case 3:
                    buscarActualizarProductoMenu();
                    break;
                case 4:
                    eliminarProductoMenu();
                    break;
                case 5:
                    crearPedidoMenu();
                    break;
                case 6:
                    listarPedidosMenu();
                    break;
                case 0:
                    salir = true;
                    System.out.println("¡Gracias por usar el sistema!");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    private static void mostrarMenu() {
        System.out.println("\n--- SISTEMA DE GESTIÓN DE TIENDA ---");
        System.out.println("1. Agregar Producto");
        System.out.println("2. Listar Productos");
        System.out.println("3. Buscar/Actualizar Producto");
        System.out.println("4. Eliminar Producto");
        System.out.println("5. Crear Pedido");
        System.out.println("6. Listar Pedidos");
        System.out.println("0. Salir");
    }

    private static void agregarProductoMenu() {
        System.out.println("\n--- AGREGAR PRODUCTO ---");
        System.out.println("Tipo de producto: 1. Perecedero | 2. Electrónico");
        int tipo = leerEntero("Seleccione tipo: ");
        
        String nombre = leerCadena("Nombre: ");
        double precio = leerDouble("Precio: ");
        int stock = leerEntero("Stock inicial: ");

        if (tipo == 1) {
            int dias = leerEntero("Días para vencer: ");
            service.agregarProducto(new ProductoPerecedero(nombre, precio, stock, dias));
        } else if (tipo == 2) {
            int meses = leerEntero("Meses de garantía: ");
            service.agregarProducto(new ProductoElectronico(nombre, precio, stock, meses));
        } else {
            System.out.println("Tipo inválido. No se agregó el producto.");
            return;
        }
        System.out.println("Producto agregado con éxito.");
    }

    private static void listarProductosMenu() {
        System.out.println("\n--- LISTADO DE PRODUCTOS ---");
        List<Producto> productos = service.listarProductos();
        if (productos.isEmpty()) {
            System.out.println("No hay productos registrados.");
        } else {
            productos.forEach(System.out::println);
        }
    }

    private static void buscarActualizarProductoMenu() {
        System.out.println("\n--- BUSCAR / ACTUALIZAR PRODUCTO ---");
        int id = leerEntero("Ingrese ID del producto: ");
        try {
            Producto p = service.buscarProductoPorId(id);
            System.out.println("Producto encontrado: " + p);
            
            System.out.println("¿Desea actualizar los datos? (s/n)");
            if (scanner.nextLine().equalsIgnoreCase("s")) {
                System.out.println("Deje en blanco o 0 para no cambiar.");
                String nuevoNombre = leerCadena("Nuevo nombre (actual: " + p.getNombre() + "): ");
                double nuevoPrecio = leerDouble("Nuevo precio (actual: " + p.getPrecio() + "): ");
                int nuevoStock = leerEntero("Nuevo stock (actual: " + p.getStock() + "): ");
                
                if (!nuevoNombre.isEmpty()) p.setNombre(nuevoNombre);
                if (nuevoPrecio > 0) p.setPrecio(nuevoPrecio);
                if (nuevoStock >= 0) p.setStock(nuevoStock);
                
                System.out.println("Producto actualizado.");
            }
        } catch (ProductoNoEncontradoException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void eliminarProductoMenu() {
        System.out.println("\n--- ELIMINAR PRODUCTO ---");
        int id = leerEntero("Ingrese ID del producto a eliminar: ");
        System.out.println("¿Está seguro de eliminar el producto? (s/n)");
        if (scanner.nextLine().equalsIgnoreCase("s")) {
            if (service.eliminarProducto(id)) {
                System.out.println("Producto eliminado.");
            } else {
                System.out.println("No se encontró el producto.");
            }
        }
    }

    private static void crearPedidoMenu() {
        System.out.println("\n--- CREAR PEDIDO ---");
        List<LineaPedido> lineas = new ArrayList<>();
        boolean agregando = true;

        while (agregando) {
            listarProductosMenu();
            int id = leerEntero("ID del producto a agregar al pedido (0 para finalizar): ");
            if (id == 0) break;

            try {
                Producto p = service.buscarProductoPorId(id);
                int cant = leerEntero("Cantidad: ");
                lineas.add(new LineaPedido(p, cant));
                System.out.println("Agregado al carrito.");
            } catch (ProductoNoEncontradoException e) {
                System.out.println(e.getMessage());
            }

            System.out.println("¿Agregar otro producto? (s/n)");
            if (!scanner.nextLine().equalsIgnoreCase("s")) {
                agregando = false;
            }
        }

        if (!lineas.isEmpty()) {
            try {
                Pedido pedido = service.crearPedido(lineas);
                System.out.println("\n¡Pedido confirmado!");
                System.out.println(pedido);
            } catch (StockInsuficienteException e) {
                System.out.println("Error al procesar pedido: " + e.getMessage());
            }
        }
    }

    private static void listarPedidosMenu() {
        System.out.println("\n--- HISTORIAL DE PEDIDOS ---");
        List<Pedido> pedidos = service.listarPedidos();
        if (pedidos.isEmpty()) {
            System.out.println("No hay pedidos realizados.");
        } else {
            pedidos.forEach(p -> {
                System.out.println(p);
                System.out.println("-------------------------");
            });
        }
    }

    // Métodos utilitarios para lectura segura
    private static int leerEntero(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                int val = Integer.parseInt(scanner.nextLine());
                return val;
            } catch (NumberFormatException e) {
                System.out.println("Por favor, ingrese un número entero válido.");
            }
        }
    }

    private static double leerDouble(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                String input = scanner.nextLine();
                if (input.isEmpty()) return 0.0;
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Por favor, ingrese un número decimal válido.");
            }
        }
    }

    private static String leerCadena(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine();
    }
}
