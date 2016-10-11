package negocio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;

import persistencia.AdmPersistenciaCliente;


public class Cliente {
    private int codigo_cliente;
    private String nombre;
    private int dni;
    private String domicilio;
    private String telefono;
    private String mail;
    private Collection<Reclamo> reclamos;
    private Usuario usuarioCreador;

    public Cliente(int codigo, String nombre, int dni, String dmicilio, String telefono, String mail) {
        codigo_cliente = codigo;
        this.nombre = nombre;
        this.dni = dni;
        this.domicilio = dmicilio;
        this.telefono = telefono;
        this.mail = mail;
        reclamos = new ArrayList<>();
    }

    public int getCantReclamos() {
        return reclamos.size();
    }

    private void agregarReclamo(Reclamo reclamo) {
        reclamos.add(reclamo);
    }

    private void removerReclamo(Reclamo reclamo) {
        reclamos.remove(reclamo);
    }

    public int getCodigo_cliente() {
        return codigo_cliente;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public void setCodigo_cliente(int codigo_cliente) {
        this.codigo_cliente = codigo_cliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Collection<Reclamo> getReclamos() {
        return reclamos;
    }

    public void setReclamos(Collection<Reclamo> reclamos) {
        this.reclamos = reclamos;
    }

    public Usuario getUsuarioCreador() {
        return usuarioCreador;
    }

    public void setUsuarioCreador(Usuario usuarioCreador) {
        this.usuarioCreador = usuarioCreador;
    }
    
    public static Cliente buscarPorCodigo(int codigoCliente) {
    	Cliente clienteEncontrado = AdmPersistenciaCliente.getInstancia().buscarCliente(codigoCliente);
    	return clienteEncontrado;
    }
    
    public static Vector<Cliente> obtenerTodos() {
    	Vector<Cliente> clientes = AdmPersistenciaCliente.getInstancia().selectAll();
    	return clientes;
    }

	public static Cliente buscarPorDni(int dni) {
		Cliente clienteEncontrado = AdmPersistenciaCliente.getInstancia().buscarPorDni(dni);
    	return clienteEncontrado;
	}

}
