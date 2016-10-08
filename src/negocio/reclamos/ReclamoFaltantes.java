package negocio.reclamos;

import java.util.Collection;

import negocio.Reclamo;

public class ReclamoFaltantes extends Reclamo{
 	private Collection<ItemProductoReclamoFaltantes> items;

 	
	public ReclamoFaltantes() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ReclamoFaltantes(Collection<ItemProductoReclamoFaltantes> items) {
		this.items = items;
	}

	public Collection<ItemProductoReclamoFaltantes> getItems() {
		return items;
	}

	public void setItems(Collection<ItemProductoReclamoFaltantes> items) {
		this.items = items;
	}
	
	
}
