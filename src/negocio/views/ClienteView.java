package negocio.views;

public class ClienteView {

    private String dni;
    private String nombre;
    private String cantidad_reclamos;
    private String mail;

    public ClienteView(String dni, String nombre, String cantidad_reclamos, String mail) {
        this.dni = dni;
        this.nombre = nombre;
        this.cantidad_reclamos = cantidad_reclamos;
        this.mail = mail;
    }

     public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
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
