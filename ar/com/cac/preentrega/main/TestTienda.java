package ar.com.cac.preentrega.main;

import ar.com.cac.preentrega.exception.StockInsuficienteException;
import ar.com.cac.preentrega.model.*;
import ar.com.cac.preentrega.service.TiendaService;
import java.util.ArrayList;
import java.util.List;

public class TestTienda {
    public static void main(String[] args) {
        TiendaService service = new TiendaService();

        Producto p1 = new ProductoPerecedero("Leche", 100.0, 10, 5);
        Producto p2 = new ProductoElectronico("Mouse", 500.0, 5, 12);
        service.agregarProducto(p1);
        service.agregarProducto(p2);

        System.out.println("--- Productos Iniciales ---");
        service.listarProductos().forEach(System.out::println);

        try {
            List<LineaPedido> lineas = new ArrayList<>();
            lineas.add(new LineaPedido(p1, 2));
            lineas.add(new LineaPedido(p2, 1));
            Pedido ped = service.crearPedido(lineas);
            System.out.println("\n--- Pedido Creado ---");
            System.out.println(ped);
            System.out.println("Nuevo stock Leche: " + p1.getStock()); 
            System.out.println("Nuevo stock Mouse: " + p2.getStock()); 
        } catch (StockInsuficienteException e) {
            System.out.println("ERROR: " + e.getMessage());
        }

        try {
            System.out.println("\n--- Intentando pedido sin stock ---");
            List<LineaPedido> lineas = new ArrayList<>();
            lineas.add(new LineaPedido(p2, 10)); 
            service.crearPedido(lineas);
        } catch (StockInsuficienteException e) {
            System.out.println("Capturada excepción esperada: " + e.getMessage());
        }

        System.out.println("\n--- Pruebas finalizadas con éxito ---");
    }
}
