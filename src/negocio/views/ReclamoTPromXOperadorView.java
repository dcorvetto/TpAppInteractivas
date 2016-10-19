package negocio.views;

public class ReclamoTPromXOperadorView {

    private String usuarioOperador;
    private String TiempoPromedio;

    public ReclamoTPromXOperadorView(String usuarioOperador, String tiempoPromedio) {
        this.usuarioOperador = usuarioOperador;
        TiempoPromedio = tiempoPromedio;
    }

    public String getUsuarioOperador() {
        return usuarioOperador;
    }

    public String getTiempoPromedio() {
        return TiempoPromedio;
    }

	@Override
	public String toString() {
		return "ReclamoTPromXOperadorView [usuarioOperador=" + usuarioOperador
				+ ", TiempoPromedio=" + TiempoPromedio + "]";
	}

    
}


