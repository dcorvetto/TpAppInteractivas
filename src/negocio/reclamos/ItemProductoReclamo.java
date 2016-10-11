package negocio.reclamos;

import negocio.Producto;

public class ItemProductoReclamo {

	private Producto producto;
	private int cantidad;
	
	

	public ItemProductoReclamo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ItemProductoReclamo(Producto producto, int cantidad) {
		this.producto = producto;
		this.cantidad = cantidad;
	}

	public Producto getProducto() {
		return producto;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	@Override
	public String toString() {
		return "ItemProductoReclamo [producto=" + producto + ", cantidad="
				+ cantidad + "]";
	}
	
	
	
	
}
