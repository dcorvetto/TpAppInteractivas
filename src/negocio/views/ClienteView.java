package negocio.views;

public class ClienteView {

    private String codigo_cliente;
    private String nombre;
    private String cantidad_reclamos;
    private String mail;

    public ClienteView(String codigo_cliente, String nombre, String cantidad_reclamos, String mail) {
        this.codigo_cliente = codigo_cliente;
        this.nombre = nombre;
        this.cantidad_reclamos = cantidad_reclamos;
        this.mail = mail;
    }

    public String getCodigo_cliente() {
        return codigo_cliente;
    }

    public void setCodigo_cliente(String codigo_cliente) {
        this.codigo_cliente = codigo_cliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCantidad_reclamos() {
        return cantidad_reclamos;
    }

    public void setCantidad_reclamos(String cantidad_reclamos) {
        this.cantidad_reclamos = cantidad_reclamos;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

}
