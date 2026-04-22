package ar.com.cac.preentrega.model;

public abstract class Producto {
    private static int contadorId = 0;
    private int id;
    private String nombre;
    private double precio;
    private int stock;

    public Producto(String nombre, double precio, int stock) {
        this.id = ++contadorId;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        if (precio >= 0) {
            this.precio = precio;
        }
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        if (stock >= 0) {
            this.stock = stock;
        }
    }

    public abstract String getTipo();

    @Override
    public String toString() {
        return String.format("ID: %d | [%s] %s | Precio: $%.2f | Stock: %d", 
                id, getTipo(), nombre, precio, stock);
    }
}
