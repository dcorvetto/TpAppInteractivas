package negocio.reclamos;

import java.util.Collection;

import negocio.Reclamo;

public class ReclamoFacturacion extends Reclamo{
	private Collection<ItemFacturaReclamo> items;

	
	public ReclamoFacturacion() {
	}

	public ReclamoFacturacion(Collection<ItemFacturaReclamo> items) {
		this.items = items;
	}

	public Collection<ItemFacturaReclamo> getItems() {
		return items;
	}

	public void setItems(Collection<ItemFacturaReclamo> items) {
		this.items = items;
	}
	
	
}
