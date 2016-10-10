package negocio;

import java.util.Vector;

import persistencia.AdmPersistenciaProducto;

public class Producto {
	private int codigo;
	private String titulo;
	private String descripcion;
	private float precio;
	private Usuario creador;
	
	
	public Producto(int codigo, String titulo, String descripcion, float precio) {
		super();
		this.codigo = codigo;
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.precio = precio;
	}


	public int getCodigo() {
		return codigo;
	}


	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}


	public String getTitulo() {
		return titulo;
	}


	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}


	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	public float getPrecio() {
		return precio;
	}


	public void setPrecio(float precio) {
		this.precio = precio;
	}


	public Usuario getCreador() {
		return creador;
	}


	public void setCreador(Usuario creador) {
		this.creador = creador;
	}
	
	public static Producto buscarPorId(int codigoProducto) {
		return AdmPersistenciaProducto.getInstancia().buscarProducto(codigoProducto);
	}
	
	public static Vector<Producto> obtenerTodos() {
		Vector<Producto> productos = AdmPersistenciaProducto.getInstancia().selectAll();
		return productos;
	}
}
