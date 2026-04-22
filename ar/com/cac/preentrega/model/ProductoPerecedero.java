package ar.com.cac.preentrega.model;

public class ProductoPerecedero extends Producto {
    private int diasParaVencer;

    public ProductoPerecedero(String nombre, double precio, int stock, int diasParaVencer) {
        super(nombre, precio, stock);
        this.diasParaVencer = diasParaVencer;
    }

    public int getDiasParaVencer() {
        return diasParaVencer;
    }

    public void setDiasParaVencer(int diasParaVencer) {
        this.diasParaVencer = diasParaVencer;
    }

    @Override
    public String getTipo() {
        return "Perecedero";
    }

    @Override
    public String toString() {
        return super.toString() + " | Días para vencer: " + diasParaVencer;
    }
}
