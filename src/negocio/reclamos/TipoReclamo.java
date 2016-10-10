package negocio.reclamos;

import java.util.Map;

public enum TipoReclamo {
	FACTURACION("Facturacion"),
	PRODUCTO("Producto"),
	CANTIDAD("Cantidad"),
	COMPUESTO("Compuesto"),
	ZONA("Zona"),
	FALTANTES("Faltantes");
	
	private final String tipo; 
	
	TipoReclamo(String tipoReclamo) {
		this.tipo = tipoReclamo;
		
	}
	
	public String getDescripcionTipo() {
		return tipo;
	}
	
	public static TipoReclamo getEnumValue(String descripcion) {
		for (TipoReclamo tipoReclamo : values()) {
            if (descripcion.equalsIgnoreCase(tipoReclamo.getDescripcionTipo())) {
                return tipoReclamo;
            }
        }
		return null;
	}
}
