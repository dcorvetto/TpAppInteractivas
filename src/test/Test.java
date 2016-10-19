package test;

import persistencia.AdmPersistenciaCliente;
import persistencia.AdmPersistenciaProducto;
import persistencia.AdmPersistenciaReclamo;
import persistencia.AdmPersistenciaUsuario;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import negocio.Cliente;
import negocio.EnumEstado;
import negocio.EventoReclamo;
import negocio.Producto;
import negocio.Reclamo;
import negocio.Usuario;
import negocio.reclamos.ItemProductoReclamoFaltantes;
import negocio.reclamos.ReclamoCompuesto;
import negocio.reclamos.ReclamoFaltantes;
import negocio.reclamos.TipoReclamo;
import negocio.views.ClienteView;
import negocio.views.ReclamoTPromXOperadorView;

public class Test {

	public static void main(String[] args) {
	
		/*Ranking clientes por cant reclamos*/
		System.out.println("Ranking clientes por cantidad de reclamos");
		List<ClienteView> listview = Cliente.getRankingClientesPorCantidadReclamos();
		for(ClienteView v:listview){
			System.out.println(v.toString());
		}
		System.out.println();
		
		/*cantidad de reclamos ingresados por mes*/
		System.out.println("Cantidad de reclamos ingresados por mes");
		for(int i=1;i<=12;i++){
			System.out.println("Mes "+ i + " cantidad de reclamos ingresados: " +Reclamo.getCantidadReclamosPorMes(i));
			
		}
		System.out.println();
		
		/*ranking tratamiento de reclamos*/
		System.out.println("Ranking tratamiento de reclamos");
		List<Vector> lista = Reclamo.getRankingTratamientoReclamos();
		for(Vector obj : lista){
			System.out.println("Estado " + obj.get(0) + ":  " + obj.get(1));
		}
		System.out.println();
		
		/*Tiempo promedio respuesta por responsable*/
		System.out.println("Tiempo promedio respuesta por responsable");
		List<ReclamoTPromXOperadorView> listaView = Reclamo.getTiempoPromedioRespuestaPorResp();
		for(ReclamoTPromXOperadorView v:listaView){
			System.out.println(v.toString());
		}
	}
}
