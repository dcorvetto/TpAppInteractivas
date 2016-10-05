package negocio.views;

public class ReclamoView {
    private int numero;
    private String descripcion;
    private String tipoReclamo;
    private boolean estaSolucionado;

    public ReclamoView(int numero, String descripcion, String tipoReclamo, boolean estaSolucionado) {
        this.numero = numero;
        this.descripcion = descripcion;
        this.tipoReclamo = tipoReclamo;
        this.estaSolucionado = estaSolucionado;
    }

    public ReclamoView() {
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipoReclamo() {
        return tipoReclamo;
    }

    public void setTipoReclamo(String tipoReclamo) {
        this.tipoReclamo = tipoReclamo;
    }

    public boolean isEstaSolucionado() {
        return estaSolucionado;
    }

    public void setEstaSolucionado(boolean estaSolucionado) {
        this.estaSolucionado = estaSolucionado;
    }

}
