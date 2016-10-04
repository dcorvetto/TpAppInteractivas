package negocio.Reclamos;

import java.util.Collection;

public class ReclamoProducto {

	private Collection<ItemProductoReclamo> items;

	public ReclamoProducto(Collection<ItemProductoReclamo> items) {
		this.items = items;
	}

	public Collection<ItemProductoReclamo> getItems() {
		return items;
	}
}
