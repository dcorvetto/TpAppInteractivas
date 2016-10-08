package negocio.reclamos;

import negocio.Reclamo;

public class ReclamoZona extends Reclamo{
	private String zona;

	
	public ReclamoZona() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ReclamoZona(String zona) {
		this.zona = zona;
	}

	public String getZona() {
		return zona;
	}

	public void setZona(String zona) {
		this.zona = zona;
	}
	
	
}

