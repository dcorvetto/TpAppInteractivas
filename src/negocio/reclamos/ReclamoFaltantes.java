package negocio.reclamos;

import java.util.Collection;

public class ReclamoFaltantes {
 	private Collection<ItemProductoReclamoFaltantes> items;

	public ReclamoFaltantes(Collection<ItemProductoReclamoFaltantes> items) {
		this.items = items;
	}

	public Collection<ItemProductoReclamoFaltantes> getItems() {
		return items;
	}
}
