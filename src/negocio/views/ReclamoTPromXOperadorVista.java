package negocio.views;

public class ReclamoTPromXOperadorVista {

    private String usuarioOperador;
    private String TiempoPromedio;

    public ReclamoTPromXOperadorVista(String usuarioOperador, String tiempoPromedio) {
        this.usuarioOperador = usuarioOperador;
        TiempoPromedio = tiempoPromedio;
    }

    public String getUsuarioOperador() {
        return usuarioOperador;
    }

    public String getTiempoPromedio() {
        return TiempoPromedio;
    }
}
