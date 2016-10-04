package negocio.reclamos;

import java.util.Collection;

public class ReclamoFacturacion {
	private Collection<ItemFacturaReclamo> items;

	public ReclamoFacturacion(Collection<ItemFacturaReclamo> items) {
		this.items = items;
	}

	public Collection<ItemFacturaReclamo> getItems() {
		return items;
	}
}
