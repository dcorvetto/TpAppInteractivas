package negocio.Reclamos;

import negocio.Producto;

public class ItemProductoReclamoFaltantes {

	private Producto producto;
	private int cantidadFaltante;
	private int cantidadFacturada;
	
	public ItemProductoReclamoFaltantes(Producto producto,
			int cantidadFaltante, int cantidadFacturada) {
		super();
		this.producto = producto;
		this.cantidadFaltante = cantidadFaltante;
		this.cantidadFacturada = cantidadFacturada;
	}
	public Producto getProducto() {
		return producto;
	}
	public void setProducto(Producto producto) {
		this.producto = producto;
	}
	public int getCantidadFaltante() {
		return cantidadFaltante;
	}
	public void setCantidadFaltante(int cantidadFaltante) {
		this.cantidadFaltante = cantidadFaltante;
	}
	public int getCantidadFacturada() {
		return cantidadFacturada;
	}
	public void setCantidadFacturada(int cantidadFacturada) {
		this.cantidadFacturada = cantidadFacturada;
	}
	
}
