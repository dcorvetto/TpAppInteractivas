package negocio.reclamos;

import negocio.Reclamo;

import java.util.Collection;

public class ReclamoCompuesto {
	private Collection<Reclamo> reclamos;

	public ReclamoCompuesto(Collection<Reclamo> reclamos) {
		this.reclamos = reclamos;
	}

	public Collection<Reclamo> getReclamos() {
		return reclamos;
	}
}
