package ar.com.cac.preentrega.service;

import ar.com.cac.preentrega.exception.ProductoNoEncontradoException;
import ar.com.cac.preentrega.exception.StockInsuficienteException;
import ar.com.cac.preentrega.model.LineaPedido;
import ar.com.cac.preentrega.model.Pedido;
import ar.com.cac.preentrega.model.Producto;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TiendaService {
    private List<Producto> productos;
    private List<Pedido> pedidos;

    public TiendaService() {
        this.productos = new ArrayList<Producto>();
        this.pedidos = new ArrayList<Pedido>();
    }

    public void agregarProducto(Producto producto) {
        productos.add(producto);
    }

    public List<Producto> listarProductos() {
        return new ArrayList<Producto>(productos);
    }

    public Producto buscarProductoPorId(int id) throws ProductoNoEncontradoException {
        for (Producto p : productos) {
            if (p.getId() == id) {
                return p;
            }
        }
        throw new ProductoNoEncontradoException("Producto con ID " + id + " no encontrado.");
    }

    public List<Producto> buscarProductoPorNombre(String nombre) {
        List<Producto> encontrados = new ArrayList<Producto>();
        String nombreBuscado = nombre.toLowerCase();
        for (Producto p : productos) {
            if (p.getNombre().toLowerCase().contains(nombreBuscado)) {
                encontrados.add(p);
            }
        }
        return encontrados;
    }

    public boolean eliminarProducto(int id) {
        Iterator<Producto> it = productos.iterator();
        while (it.hasNext()) {
            if (it.next().getId() == id) {
                it.remove();
                return true;
            }
        }
        return false;
    }

    public Pedido crearPedido(List<LineaPedido> lineas) throws StockInsuficienteException {
        for (LineaPedido linea : lineas) {
            Producto p = linea.getProducto();
            if (p.getStock() < linea.getCantidad()) {
                throw new StockInsuficienteException("Stock insuficiente para el producto: " + p.getNombre() + 
                        " (Disponible: " + p.getStock() + ", Solicitado: " + linea.getCantidad() + ")");
            }
        }

        Pedido nuevoPedido = new Pedido();
        for (LineaPedido linea : lineas) {
            Producto p = linea.getProducto();
            p.setStock(p.getStock() - linea.getCantidad());
            nuevoPedido.agregarLinea(linea);
        }

        pedidos.add(nuevoPedido);
        return nuevoPedido;
    }

    public List<Pedido> listarPedidos() {
        return new ArrayList<Pedido>(pedidos);
    }
}
