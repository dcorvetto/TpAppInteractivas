package negocio;

public enum EnumEstado {
	INGRESADO("Ingresado"), EN_TRATAMIENTO("En tratamiento"), CERRADO("Cerrado"), SOLUCIONADO("Solucionado");

	private String texto;

	EnumEstado(String texto) {
		this.texto = texto;
	}

	public String getTexto() {
		return texto;
	}
}
