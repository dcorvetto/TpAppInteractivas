package negocio.Reclamos;

import java.util.Collection;

public class ReclamoCantidad {

	private Collection<ItemProductoReclamo> items;

	public ReclamoCantidad(Collection<ItemProductoReclamo> items) {
		this.items = items;
	}

	public Collection<ItemProductoReclamo> getItems() {
		return items;
	}
}
