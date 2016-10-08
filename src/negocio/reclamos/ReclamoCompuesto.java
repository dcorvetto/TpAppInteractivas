package negocio.reclamos;

import negocio.Reclamo;

import java.util.Collection;

public class ReclamoCompuesto extends Reclamo {
	private Collection<Reclamo> reclamos;

	
	public ReclamoCompuesto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ReclamoCompuesto(Collection<Reclamo> reclamos) {
		this.reclamos = reclamos;
	}

	public Collection<Reclamo> getReclamos() {
		return reclamos;
	}

	public void setReclamos(Collection<Reclamo> reclamos) {
		this.reclamos = reclamos;
	}
	
	
}
