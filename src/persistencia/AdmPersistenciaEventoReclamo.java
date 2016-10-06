package persistencia;

import negocio.EnumEstado;
import negocio.EnumRoles;
import negocio.EventoReclamo;
import negocio.Reclamo;
import negocio.reclamos.ItemFacturaReclamo;
import negocio.reclamos.ItemProductoReclamo;
import negocio.reclamos.ItemProductoReclamoFaltantes;

import java.sql.*;
import java.util.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;


public class AdmPersistenciaEventoReclamo extends AdministradorPersistencia 
{
	private static AdmPersistenciaEventoReclamo instancia;
	
	private AdmPersistenciaEventoReclamo()
	{
		
	}
	public static AdmPersistenciaEventoReclamo getInstancia()
	{
		if (instancia == null)
			instancia = new AdmPersistenciaEventoReclamo();
		return instancia;
	}

	@Override
	public void delete(Object d) 
	{
		try
		{
			EventoReclamo er = (EventoReclamo)d;
			Connection con = PoolConnection.getPoolConnection().getConnection();
			
					
			PreparedStatement s = con.prepareStatement("delete from EventoReclamo where idEventoReclamo = ?");
			s.setLong(1, er.getIdEventoReclamo());
			s.execute();
			PoolConnection.getPoolConnection().realeaseConnection(con);
		}
		catch (Exception e)
		{
			System.out.println("Error al borrar EventoReclamo");
			e.printStackTrace();
		}	

	}

		@Override
	public void update(Object o) 
	{
		try
		{
		
			EventoReclamo er = (EventoReclamo)o;
			Connection con = PoolConnection.getPoolConnection().getConnection();
			PreparedStatement s = con.prepareStatement("update EventoReclamo " +
					"set idReclamo = ?," +
					"set Estado =?," +
					"set Fecha =?," +
					"set Detalle =?)");
	
			s.setInt(1, er.getIdReclamo());
			s.setString(2,er.getEstado().toString());
			s.setDate(3, (java.sql.Date)er.getFecha());
			s.setString(4,er.getDetalle());
			s.execute();
			PoolConnection.getPoolConnection().realeaseConnection(con);
		}
		catch (Exception e)
		{
			System.out.println("Error al actualizar EventoReclamo");
			e.printStackTrace();
		}
	}
	

	public Collection<EventoReclamo> buscarEventosXReclamo(int idreclamo)
	{
		try
		{
			Collection<EventoReclamo> eventosreclamo = new ArrayList<>();
			Connection con = PoolConnection.getPoolConnection().getConnection();
			PreparedStatement s = con.prepareStatement("select * from EventoReclamo where idReclamo = ?");
			s.setInt(1, idreclamo);
		
			ResultSet result = s.executeQuery();
			while (result.next())
			{
				int cod = result.getInt(1);
				int idReclamo = result.getInt(2);
				String estado = result.getString(3);
				Date fecha = result.getDate(4);
				String detalle = result.getString(5);

				EventoReclamo er = new EventoReclamo();
				er.setDetalle(detalle);
				
				switch(estado){
				case "Ingresado": er.setEstado(EnumEstado.INGRESADO);
					break;
				case "En Tratamiento" : er.setEstado(EnumEstado.EN_TRATAMIENTO);
					break;
				case "Cerrado" : er.setEstado(EnumEstado.CERRADO);
					break;
				case "Solucionado" : er.setEstado(EnumEstado.SOLUCIONADO);
				 	break;
				
				}
				er.setIdEventoReclamo(cod);
				er.setFecha(fecha);
			    er.setIdReclamo(idReclamo);
				eventosreclamo.add(er);
			}

			PoolConnection.getPoolConnection().realeaseConnection(con);
			return eventosreclamo;
		}
		catch (Exception e)
		{
			System.out.println();
		}
		return null;
	}
	
	@Override
	public void insert(Object o) {
		try
		{
			EventoReclamo er = (EventoReclamo)o;
			Connection con = PoolConnection.getPoolConnection().getConnection();
			PreparedStatement s = con.prepareStatement("insert into EventoReclamo values (?,?,?,?)");
		
			s.setInt(1, er.getIdReclamo());
			s.setString(2,er.getEstado().toString());
			s.setDate(3, null); //PROBLEMA DATE
			s.setString(4,er.getDetalle());
			s.execute();
			
			PoolConnection.getPoolConnection().realeaseConnection(con);
		}
		catch (Exception e)
		{
			System.out.println("Error al insertar EventoReclamo");
			e.printStackTrace();
		}
		
	}
	@Override
	public int insertar(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public Vector<Object> select(Object o) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
