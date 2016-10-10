package persistencia;

import negocio.EnumEstado;
import negocio.EventoReclamo;
import negocio.Reclamo;
import negocio.reclamos.ItemFacturaReclamo;
import negocio.reclamos.ItemProductoReclamo;
import negocio.reclamos.ItemProductoReclamoFaltantes;
import negocio.reclamos.ReclamoCantidad;
import negocio.reclamos.ReclamoCompuesto;
import negocio.reclamos.ReclamoFacturacion;
import negocio.reclamos.ReclamoFaltantes;
import negocio.reclamos.ReclamoProducto;
import negocio.reclamos.TipoReclamo;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Vector;


public class AdmPersistenciaReclamo extends AdministradorPersistencia 
{
	private static AdmPersistenciaReclamo instancia;
	
	private AdmPersistenciaReclamo()
	{
		
	}
	public static AdmPersistenciaReclamo getInstancia()
	{
		if (instancia == null)
			instancia = new AdmPersistenciaReclamo();
		return instancia;
	}

	@Override
	public void delete(Object d) 
	{
		try
		{
			Reclamo r = (Reclamo)d;
			Connection con = PoolConnection.getPoolConnection().getConnection();
			switch(r.getTipoReclamo()){
			case PRODUCTO:
				PreparedStatement s = con.prepareStatement("delete from ItemProductoReclamo where idReclamo=?");
				s.setString(1, String.valueOf(r.getNumero())); 
				s.execute();
				break;
			case FALTANTES:
				PreparedStatement sfalt = con.prepareStatement("delete from ItemProductoReclamoFaltantes where idReclamo=?");
				sfalt.setString(1, String.valueOf(r.getNumero())); 
				sfalt.execute();
				break;
			case CANTIDAD:
				PreparedStatement sc = con.prepareStatement("delete from ItemProductoReclamo where idReclamo=?");
				sc.setString(1, String.valueOf(r.getNumero())); 
				sc.execute();
				break;
			case FACTURACION:
				PreparedStatement sfact = con.prepareStatement("delete from ItemFacturaReclamo where idReclamo=?");
				sfact.setString(1, String.valueOf(r.getNumero())); 
				sfact.execute();
				break;
			case COMPUESTO:
				PreparedStatement scomp = con.prepareStatement("delete from ReclamoCompuesto where idReclamoPadre=?");			
				scomp.setString(1, String.valueOf(r.getNumero())); 
				scomp.execute();
				break;
			}
			
			/*Por las dudas borro posibles reclamos compuestos asociados*/
			PreparedStatement scomp = con.prepareStatement("delete from ReclamoCompuesto where idReclamoHijo=?");			
			scomp.setString(1, String.valueOf(r.getNumero())); 
			scomp.execute();
					
			PreparedStatement ser = con.prepareStatement("delete from EventoReclamo where idReclamo=?");			
			ser.setString(1, String.valueOf(r.getNumero())); 
			ser.execute();
			
			PreparedStatement s = con.prepareStatement("delete from Reclamo where numero = ?");
			s.setLong(1, r.getNumero());
			s.execute();
			PoolConnection.getPoolConnection().realeaseConnection(con);
			
		}
		catch (Exception e)
		{
			System.out.println("Error al borrar Reclamo");
			e.printStackTrace();
		}	

	}

	@Override
	public void insert(Object o)
	{
		try
		{
			Reclamo r = (Reclamo)o;
			Connection con = PoolConnection.getPoolConnection().getConnection();
			PreparedStatement s = con.prepareStatement("insert into Reclamo values (?,?,?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
			//agregar campos
			s.setString(1, r.getDescripcion());
			s.setInt(2,r.getCliente().getCodigo_cliente());
			s.setInt(3, r.getOperador().getCodigo());
			s.setInt(4,r.getResponsable().getCodigo());
			s.setBoolean(5, false);
			s.setFloat(6, (float) r.getTiempoRespuesta());
			s.setString(7, r.getZona());
			s.setString(8, r.getTipoReclamo().getDescripcionTipo());
			s.execute();
			
			ResultSet rs = s.getGeneratedKeys();
			rs.next();
			int idReclamo = rs.getInt(1);
			
			/*insertar items*/
			switch(r.getTipoReclamo()){
			case PRODUCTO:
				ReclamoProducto rp = (ReclamoProducto) o;
				for(ItemProductoReclamo item: rp.getItems()){
					this.insertarItems(idReclamo, item);
				}
				break;
			case FALTANTES:
				ReclamoFaltantes rf = (ReclamoFaltantes) o;
				for(ItemProductoReclamoFaltantes item: rf.getItems()){
					this.insertarItems(idReclamo, item);
				}
				break;
			case CANTIDAD:
				ReclamoCantidad rc = (ReclamoCantidad) o;
				for(ItemProductoReclamo item: rc.getItems()){
					this.insertarItems(idReclamo, item);
				}
				break;
			case FACTURACION:
				ReclamoFacturacion rfact = (ReclamoFacturacion) o;
				for(ItemFacturaReclamo item: rfact.getItems()){
					this.insertarItems(idReclamo, item);
				}
				break;
			case COMPUESTO:
				ReclamoCompuesto rcomp = (ReclamoCompuesto) o;
				for(Reclamo rec: rcomp.getReclamos()){
					this.insertarReclamoCompuesto(idReclamo, rec);
				}
				break;
			}
			
			//Inserto eventos
			for(EventoReclamo er: r.getEventos()){
				this.insertEventoReclamo(idReclamo, er);
			}
			
			PoolConnection.getPoolConnection().realeaseConnection(con);
			
		}
		catch (Exception e)
		{
			System.out.println("Error al insertar Reclamo");
			e.printStackTrace();
		}
	}
	
	public void insertarReclamoCompuesto(int idReclamoPadre, Reclamo reclamo){
		try
		{
			Connection con = PoolConnection.getPoolConnection().getConnection();
			PreparedStatement s = con.prepareStatement("insert into ReclamoCompuesto values (?,?)");
			//agregar campos
			s.setInt(1,idReclamoPadre);
			s.setInt(2,reclamo.getNumero());
			
			s.execute();
			PoolConnection.getPoolConnection().realeaseConnection(con);
		}
		catch(Exception e){
			System.out.println("Error al insertar Reclamo Compuesto");
			e.printStackTrace();
		}
	}
	
	public void insertarItems(int reclamo, Object o){
		try
		{
			Reclamo r = buscarReclamo(reclamo);
			Connection con = PoolConnection.getPoolConnection().getConnection();
			switch(r.getTipoReclamo()){
			case PRODUCTO:
				ItemProductoReclamo i = (ItemProductoReclamo) o;
				PreparedStatement s = con.prepareStatement("insert into ItemProductoReclamo values (?,?,?)");
				//agregar campos
				s.setInt(1, r.getNumero());
				s.setInt(2, i.getCantidad());
				s.setInt(3, i.getProducto().getCodigo()); 
				s.execute();
				break;
			case FALTANTES:
				ItemProductoReclamoFaltantes ifalt = (ItemProductoReclamoFaltantes) o;
				PreparedStatement sfalt= con.prepareStatement("insert into ItemProductoReclamoFaltantes values (?,?,?,?)");
				//agregar campos
				sfalt.setInt(1, r.getNumero());
				sfalt.setInt(2, ifalt.getProducto().getCodigo());
				sfalt.setInt(3, ifalt.getCantidadFaltante());
				sfalt.setInt(4, ifalt.getCantidadFacturada());
				sfalt.execute();
				break;
			case CANTIDAD:
				ItemProductoReclamo ic = (ItemProductoReclamo) o;
				PreparedStatement sc = con.prepareStatement("insert into ItemProductoReclamo values (?,?,?)");
				//agregar campos
				sc.setInt(1, r.getNumero());
				sc.setInt(2, ic.getCantidad());
				sc.setInt(3, ic.getProducto().getCodigo()); 
				sc.execute();
				break;
			case FACTURACION:
				ItemFacturaReclamo ifact = (ItemFacturaReclamo) o;
				PreparedStatement sf = con.prepareStatement("insert into ItemFacturaReclamo values (?,?,?)");
				//agregar campos
				sf.setInt(1, r.getNumero());
				sf.setInt(2, ifact.getFactura().getIdFactura());
				sf.setTimestamp(3, new java.sql.Timestamp(ifact.getFechaFactura())); 
				sf.execute();
				break;
			}
			PoolConnection.getPoolConnection().realeaseConnection(con);
		}
		catch(Exception e)
		{
			System.out.println("Error al insertar Item Reclamos");
			e.printStackTrace();
		}
	}
	
	
	@Override
	public Vector<Object> select(Object o) 
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Object o) 
	{
		try
		{
		
			Reclamo r = (Reclamo)o;
			Connection con = PoolConnection.getPoolConnection().getConnection();
			PreparedStatement s = con.prepareStatement("update Reclamo " +
					"set descripcion = ?," +
					"set cliente =?," +
					"set usuario =?," +
					"set responsable =?," +
					"set solucionado =?," +
					"set tiempoRespuesta = ?)");
			//agregar campos
			s.setString(1, r.getDescripcion());
			s.setInt(2,r.getCliente().getCodigo_cliente());
			s.setInt(3, r.getOperador().getCodigo());
			s.setInt(4,r.getResponsable().getCodigo());
			s.setBoolean(5, r.isEstaSolucionado());
			s.setFloat(6, (float) r.getTiempoRespuesta());
			s.execute();
			PoolConnection.getPoolConnection().realeaseConnection(con);
		}
		catch (Exception e)
		{
			System.out.println("Error al actualizar Reclamo");
			e.printStackTrace();
		}
	}
	
	public Reclamo buscarReclamo(long numero)
	{
		try
		{
			Reclamo r = null;
			Connection con = PoolConnection.getPoolConnection().getConnection();
			PreparedStatement s = con.prepareStatement("select * from Reclamo where numero = ?");
			s.setLong(1,numero);
			ResultSet result = s.executeQuery();
			while (result.next())
			{
				int cod = result.getInt(1);
				String descripcion = result.getString(2);
				int cliente = result.getInt(3);
				int usuario = result.getInt(4);
				int responsable = result.getInt(5);
				boolean solucionado = result.getBoolean(6);
				float tiempoRespuesta = result.getFloat(7);
				String zona = result.getString(8);
				String tipoReclamo = result.getString(9);

				r = new Reclamo();
				r.setNumero(cod);
				r.setDescripcion(descripcion);
				r.setEstaSolucionado(solucionado);
				r.setOperador(AdmPersistenciaUsuario.getInstancia().buscarUsuario(usuario));
				r.setResponsable(AdmPersistenciaUsuario.getInstancia().buscarUsuario(usuario));
				r.setTiempoRespuesta(tiempoRespuesta);
				r.setTipoReclamo(TipoReclamo.getEnumValue(tipoReclamo));
				r.setZona(zona);
			}

			PoolConnection.getPoolConnection().realeaseConnection(con);
			return r;
		}
		catch (Exception e)
		{
			System.out.println();
		}
		return null;
	}

	public Collection<Reclamo> buscarReclamos()
	{
		try
		{
			Collection<Reclamo> reclamos = new ArrayList<>();
			Connection con = PoolConnection.getPoolConnection().getConnection();
			PreparedStatement s = con.prepareStatement("select * from Reclamo");			//agregar campos
			ResultSet result = s.executeQuery();
			while (result.next())
			{
				int cod = result.getInt(1);
				String descripcion = result.getString(2);
				int cliente = result.getInt(3);
				int usuario = result.getInt(4);
				int responsable = result.getInt(5);
				boolean solucionado = result.getBoolean(6);
				float tiempoRespuesta = result.getFloat(7);
				String zona = result.getString(8);
				String tipoReclamo = result.getString(9);

				Reclamo r = new Reclamo();
				r.setNumero(cod);
				r.setDescripcion(descripcion);
				r.setEstaSolucionado(solucionado);
				r.setOperador(AdmPersistenciaUsuario.getInstancia().buscarUsuario(usuario));
				r.setResponsable(AdmPersistenciaUsuario.getInstancia().buscarUsuario(usuario));
				r.setTiempoRespuesta(tiempoRespuesta);
				r.setTipoReclamo(TipoReclamo.getEnumValue(tipoReclamo));
				r.setZona(zona);
				reclamos.add(r);
			}

			PoolConnection.getPoolConnection().realeaseConnection(con);
			return reclamos;
		}
		catch (Exception e)
		{
			System.out.println();
		}
		return null;
	}
	
	
	
	public void updateEventoReclamo(int idReclamo, Object o) 
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
	
			s.setInt(1, idReclamo);
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
	

	public List<EventoReclamo> buscarEventosXReclamo(int idreclamo)
	{
		try
		{
			List<EventoReclamo> eventosreclamo = new ArrayList<>();
			Connection con = PoolConnection.getPoolConnection().getConnection();
			PreparedStatement s = con.prepareStatement("select idEventoReclamo, estado, fecha, detalle from EventoReclamo where idReclamo = ?");
			s.setInt(1, idreclamo);
		
			ResultSet result = s.executeQuery();
			while (result.next())
			{
				int cod = result.getInt(1);
				String estado = result.getString(2);
				Date fecha = result.getDate(3);
				String detalle = result.getString(4);

				EventoReclamo er = new EventoReclamo();
				er.setDetalle(detalle);
				
				switch(estado.toLowerCase()){
				case "ingresado": er.setEstado(EnumEstado.INGRESADO);
					break;
				case "en_tratamiento" : er.setEstado(EnumEstado.EN_TRATAMIENTO);
					break;
				case "cerrado" : er.setEstado(EnumEstado.CERRADO);
					break;
				case "solucionado" : er.setEstado(EnumEstado.SOLUCIONADO);
				 	break;
				
				}
				er.setIdEventoReclamo(cod);
				er.setFecha(fecha);
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

	public void insertEventoReclamo(int idReclamo , Object o) {
		try
		{
			EventoReclamo er = (EventoReclamo)o;
			Connection con = PoolConnection.getPoolConnection().getConnection();
			PreparedStatement s = con.prepareStatement("insert into EventoReclamo values (?,?,?,?)");
		
			s.setInt(1, idReclamo);
			s.setString(2,er.getEstado().toString());
			s.setTimestamp(3, new java.sql.Timestamp(er.getFecha().getTime())); 
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

}
