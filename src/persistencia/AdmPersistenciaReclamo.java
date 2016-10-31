package persistencia;

import negocio.AlarmaNuevoReclamo;
import negocio.EnumEstado;
import negocio.EventoReclamo;
import negocio.Reclamo;
import negocio.reclamos.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;


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
		Connection connection=null;
		try
		{
			Reclamo r = (Reclamo)d;
			connection = PoolConnection.getPoolConnection().getConnection();
			switch(r.getTipoReclamo()){
			case PRODUCTO:
				PreparedStatement s = connection.prepareStatement("delete from ItemProductoReclamo where idReclamo=?");
				s.setString(1, String.valueOf(r.getNumero())); 
				s.execute();
				break;
			case FALTANTES:
				PreparedStatement sfalt = connection.prepareStatement("delete from ItemProductoReclamoFaltantes where idReclamo=?");
				sfalt.setString(1, String.valueOf(r.getNumero())); 
				sfalt.execute();
				break;
			case CANTIDAD:
				PreparedStatement sc = connection.prepareStatement("delete from ItemProductoReclamo where idReclamo=?");
				sc.setString(1, String.valueOf(r.getNumero())); 
				sc.execute();
				break;
			case FACTURACION:
				PreparedStatement sfact = connection.prepareStatement("delete from ItemFacturaReclamo where idReclamo=?");
				sfact.setString(1, String.valueOf(r.getNumero())); 
				sfact.execute();
				break;
			case COMPUESTO:
				PreparedStatement scomp = connection.prepareStatement("delete from ReclamoCompuesto where idReclamoPadre=?");
				scomp.setString(1, String.valueOf(r.getNumero())); 
				scomp.execute();
				break;
			}
			
			/*Por las dudas borro posibles reclamos compuestos asociados*/
			PreparedStatement scomp = connection.prepareStatement("delete from ReclamoCompuesto where idReclamoHijo=?");
			scomp.setString(1, String.valueOf(r.getNumero())); 
			scomp.execute();
					
			PreparedStatement ser = connection.prepareStatement("delete from EventoReclamo where idReclamo=?");
			ser.setString(1, String.valueOf(r.getNumero())); 
			ser.execute();
			
			PreparedStatement s = connection.prepareStatement("delete from Reclamo where numero = ?");
			s.setLong(1, r.getNumero());
			s.execute();

			
		}
		catch (Exception e)
		{
			System.out.println("Error al borrar Reclamo");
			e.printStackTrace();
		}
		finally {
			PoolConnection.getPoolConnection().realeaseConnection(connection);
		}

	}

	@Override
	public void insert(Object o)
	{
		Connection connection=null;
		try
		{
			Reclamo r = (Reclamo)o;
			connection = PoolConnection.getPoolConnection().getConnection();
			PreparedStatement s = connection.prepareStatement("insert into Reclamo values (?,?,?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
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
			
			AlarmaNuevoReclamo alarma = new AlarmaNuevoReclamo();
	       

			/*insertar items*/
			switch(r.getTipoReclamo()){
			case PRODUCTO:
				ReclamoProducto rp = (ReclamoProducto) o;
				for(ItemProductoReclamo item: rp.getItems()){
					this.insertarItems(idReclamo, item);
				}
				alarma.notifyObservers(rp);
				break;
			case FALTANTES:
				ReclamoFaltantes rf = (ReclamoFaltantes) o;
				for(ItemProductoReclamoFaltantes item: rf.getItems()){
					this.insertarItems(idReclamo, item);
				}
				alarma.notifyObservers(rf);
				break;
			case CANTIDAD:
				ReclamoCantidad rc = (ReclamoCantidad) o;
				for(ItemProductoReclamo item: rc.getItems()){
					this.insertarItems(idReclamo, item);
				}
				alarma.notifyObservers(rc);
				break;
			case FACTURACION:
				ReclamoFacturacion rfact = (ReclamoFacturacion) o;
				for(ItemFacturaReclamo item: rfact.getItems()){
					this.insertarItems(idReclamo, item);
				}
				alarma.notifyObservers(rfact);
				break;
			case COMPUESTO:
				ReclamoCompuesto rcomp = (ReclamoCompuesto) o;
				for(Reclamo rec: rcomp.getReclamos()){
					this.insertarReclamoCompuesto(idReclamo, rec);
				}
				alarma.notifyObservers(rcomp);
				break;
			case ZONA:
				Reclamo rz = (Reclamo)o;
				alarma.notifyObservers(rz);
				break;
			}
			
			//Inserto eventos
			for(EventoReclamo er: r.getEventos()){
				this.insertEventoReclamo(idReclamo, er);
			}
			
		}
		catch (Exception e)
		{
			System.out.println("Error al insertar Reclamo");
			e.printStackTrace();
		}
		finally {
			PoolConnection.getPoolConnection().realeaseConnection(connection);
		}
	}
	
	public void insertarReclamoCompuesto(int idReclamoPadre, Reclamo reclamo){
		Connection connection=null;
		try
		{
			connection = PoolConnection.getPoolConnection().getConnection();
			PreparedStatement s = connection.prepareStatement("insert into ReclamoCompuesto values (?,?)");
			//agregar campos
			s.setInt(1,idReclamoPadre);
			s.setInt(2,reclamo.getNumero());
			
			s.execute();

		}
		catch(Exception e){
			System.out.println("Error al insertar Reclamo Compuesto");
			e.printStackTrace();
		}
		finally {
			PoolConnection.getPoolConnection().realeaseConnection(connection);
		}
	}
	
	public void insertarItems(int reclamo, Object o){
		Connection connection=null;
		try
		{
			Reclamo r = buscarReclamo(reclamo);
			connection = PoolConnection.getPoolConnection().getConnection();
			switch(r.getTipoReclamo()){
			case PRODUCTO:
				ItemProductoReclamo i = (ItemProductoReclamo) o;
				PreparedStatement s = connection.prepareStatement("insert into ItemProductoReclamo values (?,?,?)");
				//agregar campos
				s.setInt(1, r.getNumero());
				s.setInt(2, i.getProducto().getCodigo());
				s.setInt(3, i.getCantidad());
				s.execute();
				break;
			case FALTANTES:
				ItemProductoReclamoFaltantes ifalt = (ItemProductoReclamoFaltantes) o;
				PreparedStatement sfalt= connection.prepareStatement("insert into ItemProductoReclamoFaltantes values (?,?,?,?)");
				//agregar campos
				sfalt.setInt(1, r.getNumero());
				sfalt.setInt(2, ifalt.getProducto().getCodigo());
				sfalt.setInt(3, ifalt.getCantidadFaltante());
				sfalt.setInt(4, ifalt.getCantidadFacturada());
				sfalt.execute();
				break;
			case CANTIDAD:
				ItemProductoReclamo ic = (ItemProductoReclamo) o;
				PreparedStatement sc = connection.prepareStatement("insert into ItemProductoReclamo values (?,?,?)");
				//agregar campos
				sc.setInt(1, r.getNumero());
				sc.setInt(2, ic.getProducto().getCodigo()); 
				sc.setInt(3, ic.getCantidad());
				sc.execute();
				break;
			case FACTURACION:
				ItemFacturaReclamo ifact = (ItemFacturaReclamo) o;
				PreparedStatement sf = connection.prepareStatement("insert into ItemFacturaReclamo values (?,?,?)");
				//agregar campos
				sf.setInt(1, r.getNumero());
				sf.setInt(2, ifact.getFactura().getIdFactura());
				sf.setTimestamp(3, new java.sql.Timestamp(ifact.getFechaFactura())); 
				sf.execute();
				break;
			}

		}
		catch(Exception e)
		{
			System.out.println("Error al insertar Item Reclamos");
			e.printStackTrace();
		}
		finally{
			PoolConnection.getPoolConnection().realeaseConnection(connection);
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
		Connection connection=null;
		try
		{
		
			Reclamo r = (Reclamo)o;
			connection = PoolConnection.getPoolConnection().getConnection();
			PreparedStatement s = connection.prepareStatement("update Reclamo " +
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

		}
		catch (Exception e)
		{
			System.out.println("Error al actualizar Reclamo");
			e.printStackTrace();
		}
		finally{
			PoolConnection.getPoolConnection().realeaseConnection(connection);
		}
	}
	
	public Reclamo buscarReclamo(long numero)
	{
		Connection connection=null;
		try
		{
			Reclamo r = null;
			connection = PoolConnection.getPoolConnection().getConnection();
			PreparedStatement s = connection.prepareStatement("select * from Reclamo where numero = ?");
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


			return r;
		}
		catch (Exception e)
		{
			System.out.println();
		}
		finally {
			PoolConnection.getPoolConnection().realeaseConnection(connection);
		}
		return null;
	}

	public Collection<Reclamo> buscarReclamos()
	{
		Connection connection=null;
		try
		{
			Collection<Reclamo> reclamos = new ArrayList<>();
			connection = PoolConnection.getPoolConnection().getConnection();
			PreparedStatement s = connection.prepareStatement("select * from Reclamo");
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


			return reclamos;
		}
		catch (Exception e)
		{
			System.out.println();
		}
		finally {
			PoolConnection.getPoolConnection().realeaseConnection(connection);
		}
		return null;
	}
	
	
	
	public void updateEventoReclamo(int idReclamo, Object o) 
	{
		Connection connection=null;
		try
		{
		
			EventoReclamo er = (EventoReclamo)o;
			connection = PoolConnection.getPoolConnection().getConnection();
			PreparedStatement s = connection.prepareStatement("update EventoReclamo " +
					"set idReclamo = ?," +
					"set Estado =?," +
					"set Fecha =?," +
					"set Detalle =?)");
	
			s.setInt(1, idReclamo);
			s.setString(2,er.getEstado().toString());
			s.setDate(3, (java.sql.Date)er.getFecha());
			s.setString(4,er.getDetalle());
			s.execute();

		}
		catch (Exception e)
		{
			System.out.println("Error al actualizar EventoReclamo");
			e.printStackTrace();
		}
		finally {
			PoolConnection.getPoolConnection().realeaseConnection(connection);
		}
	}
	

	public List<EventoReclamo> buscarEventosXReclamo(int idreclamo)
	{
		Connection connection=null;
		try
		{
			List<EventoReclamo> eventosreclamo = new ArrayList<>();
			connection = PoolConnection.getPoolConnection().getConnection();
			PreparedStatement s = connection.prepareStatement("select idEventoReclamo, estado, fecha, detalle from EventoReclamo where idReclamo = ?");
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


			return eventosreclamo;
		}
		catch (Exception e)
		{
			System.out.println();
		}
		finally {
			PoolConnection.getPoolConnection().realeaseConnection(connection);
		}
		return null;
	}

	public void insertEventoReclamo(int idReclamo , Object o) {
		Connection connection=null;
		try
		{
			EventoReclamo er = (EventoReclamo)o;
			connection = PoolConnection.getPoolConnection().getConnection();
			PreparedStatement s = connection.prepareStatement("insert into EventoReclamo values (?,?,?,?)");
		
			s.setInt(1, idReclamo);
			s.setString(2,er.getEstado().toString());
			s.setTimestamp(3, new java.sql.Timestamp(er.getFecha().getTime())); 
			s.setString(4,er.getDetalle());
			s.execute();
			
			long tiempoRespuesta = -1;
			
			if(er.getEstado().equals(EnumEstado.SOLUCIONADO)){
				PreparedStatement st = connection.prepareStatement("select DATEDIFF(mi,r2.fecha,r.fecha)"
						+ "	from eventoReclamo r"
						+ "	left join EventoReclamo r2"
						+ "	on r2.idReclamo=r.idReclamo"
						+ "	AND r2.estado='INGRESADO'"
						+ "	where r.estado='SOLUCIONADO'"
						+ "	AND r.idReclamo=?");
				st.setInt(1, idReclamo);
				
				ResultSet resultt = st.executeQuery();
				while(resultt.next()){
					tiempoRespuesta = resultt.getLong(1);
				}
				
				PreparedStatement sr = connection.prepareStatement("update Reclamo set solucionado=1,"
						+ " tiempoRespuesta=? "
						+ "where numero=? ");
				sr.setLong(1, tiempoRespuesta);
				sr.setInt(2, idReclamo);
				sr.execute();
			}
			

		}
		catch (Exception e)
		{
			System.out.println("Error al insertar EventoReclamo");
			e.printStackTrace();
		}
		finally{
			PoolConnection.getPoolConnection().realeaseConnection(connection);
		}
		
	}
	
	public List<Reclamo> getCantReclamosPorMes(int mes){
		Connection connection=null;
		try
		{
			connection = PoolConnection.getPoolConnection().getConnection();
			PreparedStatement s = connection.prepareStatement("select "
					+ " numero from Reclamo r"
					+ " join eventoReclamo er"
					+ " on er.idReclamo=r.numero"
					+ " where MONTH(er.fecha) =?");
			s.setInt(1, mes);
	
			ResultSet result = s.executeQuery();
			List<Reclamo> lista = new ArrayList<Reclamo>();
			while (result.next())
			{
				Reclamo r = new Reclamo();
				r.setNumero(result.getInt(1));;
				lista.add(r);
			}
			return lista;
		}
		catch (Exception e)
		{
			System.out.println();
		}
		finally {
			PoolConnection.getPoolConnection().realeaseConnection(connection);
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	public Map<String, Long> getRankingTratamientoReclamos(){
		Connection connection=null;
		try
		{
			Map<String, Long> map = new HashMap<>();
			connection = PoolConnection.getPoolConnection().getConnection();
			PreparedStatement s = connection.prepareStatement("select "
					+ " estado, count(*) as cantidad"
					+ "	from EventoReclamo er"
					+ "	group by estado"
					+ "	order by  count(*) DESC");

			ResultSet result = s.executeQuery();
			while (result.next()){
				map.put(result.getString(1),result.getLong(2)); //1: estado, 2: cantidad
			}
			return map;
		}
		catch (Exception e)
		{
			System.out.println();
		}
		finally {
			PoolConnection.getPoolConnection().realeaseConnection(connection);
		}
		return null;
		
	}
	
	/*tiempo promedio de respuesta de los reclamos por responsable*/
	public List<Vector> getTiempoPromedioRespuestaPorResp() {
		Connection connection=null;
		try
		{
			List<Vector> lista = new ArrayList<Vector>();
			connection = PoolConnection.getPoolConnection().getConnection();
			PreparedStatement s = connection.prepareStatement("select "
					+ " SUM(tiempoRespuesta)/COUNT(responsable), "
					+ "	responsable from Reclamo"
					+ " where tiempoRespuesta!=-1"
					+ " group by responsable");

			ResultSet result = s.executeQuery();
			while (result.next())
			{
				Vector<Object> v = new Vector<Object>();
				v.add(result.getLong(1));
				v.add(result.getInt(2));
				lista.add(v);
			}
			return lista;
		}
		catch (Exception e)
		{
			System.out.println();
		}
		finally {
			PoolConnection.getPoolConnection().realeaseConnection(connection);
		}
		return null;
	}
	public Reclamo getUltimoReclamo() {
		Connection connection=null;
		try
		{
			Reclamo r = null;
			connection = PoolConnection.getPoolConnection().getConnection();
			PreparedStatement s = connection.prepareStatement("select top 1 * from Reclamo order by numero DESC");
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


			return r;
		}
		catch (Exception e)
		{
			System.out.println();
		}
		finally {
			PoolConnection.getPoolConnection().realeaseConnection(connection);
		}
		return null;
	}
	
	
}
