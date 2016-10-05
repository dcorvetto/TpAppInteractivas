package persistencia;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Vector;

import negocio.Reclamo;
import negocio.reclamos.ItemFacturaReclamo;
import negocio.reclamos.ItemProductoReclamo;
import negocio.reclamos.ItemProductoReclamoFaltantes;

import java.sql.*;
import java.util.Collection;
import java.util.Collections;
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
			case "producto":
				PreparedStatement s = con.prepareStatement("delete from ItemProductoReclamo where idReclamo=?");
				s.setString(1, String.valueOf(r.getNumero())); 
				s.execute();
				break;
			case "faltante":
				PreparedStatement sfalt = con.prepareStatement("delete from ItemProductoReclamoFaltantes where idReclamo=?");
				sfalt.setString(1, String.valueOf(r.getNumero())); 
				sfalt.execute();
				break;
			case "cant":
				PreparedStatement sc = con.prepareStatement("delete from ItemProductoReclamo where idReclamo=?");
				sc.setString(1, String.valueOf(r.getNumero())); 
				sc.execute();
				break;
			case "facturacion":
				PreparedStatement sfact = con.prepareStatement("delete from ItemFacturaReclamo where idReclamo=?");
				sfact.setString(1, String.valueOf(r.getNumero())); 
				sfact.execute();
				break;
			case "compuesto":
				PreparedStatement scomp = con.prepareStatement("delete from ReclamoCompuesto where idReclamoPadre=?");			
				scomp.setString(1, String.valueOf(r.getNumero())); 
				scomp.execute();
				break;
			}
			
			/*Por las dudas borro posibles reclamos compuestos asociados*/
			PreparedStatement scomp = con.prepareStatement("delete from ReclamoCompuesto where idReclamoHijo=?");			
			scomp.setString(1, String.valueOf(r.getNumero())); 
			scomp.execute();
					
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
	public int insertar(Object o)
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
			s.setString(8, r.getTipoReclamo());
			s.execute();
			
			ResultSet rs = s.getGeneratedKeys();
			rs.next();
			int idReclamo = rs.getInt(1);
			
			PoolConnection.getPoolConnection().realeaseConnection(con);
			return idReclamo;
		}
		catch (Exception e)
		{
			System.out.println("Error al insertar Reclamo");
			e.printStackTrace();
		}
		return 0;
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
			case "producto":
				ItemProductoReclamo i = (ItemProductoReclamo) o;
				PreparedStatement s = con.prepareStatement("insert into ItemProductoReclamo values (?,?,?)");
				//agregar campos
				s.setInt(1, r.getNumero());
				s.setInt(2, i.getCantidad());
				s.setInt(3, i.getProducto().getCodigo()); 
				s.execute();
				break;
			case "faltante":
				ItemProductoReclamoFaltantes ifalt = (ItemProductoReclamoFaltantes) o;
				PreparedStatement sfalt= con.prepareStatement("insert into ItemProductoReclamoFaltantes values (?,?,?,?)");
				//agregar campos
				sfalt.setInt(1, r.getNumero());
				sfalt.setInt(2, ifalt.getProducto().getCodigo());
				sfalt.setInt(3, ifalt.getCantidadFaltante());
//				sfalt.setInt(4, ifalt.getCantidadFacturada());
				sfalt.execute();
				break;
			case "cant":
				ItemProductoReclamo ic = (ItemProductoReclamo) o;
				PreparedStatement sc = con.prepareStatement("insert into ItemProductoReclamo values (?,?,?)");
				//agregar campos
				sc.setInt(1, r.getNumero());
				sc.setInt(2, ic.getCantidad());
				sc.setInt(3, ic.getProducto().getCodigo()); 
				sc.execute();
				break;
			case "facturacion":
				ItemFacturaReclamo ifact = (ItemFacturaReclamo) o;
				PreparedStatement sf = con.prepareStatement("insert into ItemFacturaReclamo values (?,?,?)");
				//agregar campos
				sf.setInt(1, r.getNumero());
				sf.setInt(2, ifact.getFactura().getIdFactura());
				sf.setDate(3, (Date) ifact.getFechaFactura());
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
				r.setTipoReclamo(tipoReclamo);
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
			Collection<Reclamo> reclamos = Collections.emptyList();
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
				//r.setOperador(); /*llamar AdmPersistenciaUsuario buscarUsuario(int id)*/
				//r.setResponsable(); /*llamar AdmPersistenciaUsuario buscarUsuario(int id)*/
				r.setTiempoRespuesta(tiempoRespuesta);
				r.setTipoReclamo(tipoReclamo);
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
	
	@Override
	public void insert(Object o) {
		try
		{
			Reclamo r = (Reclamo)o;
			Connection con = PoolConnection.getPoolConnection().getConnection();
			PreparedStatement s = con.prepareStatement("insert into Reclamo values (?,?,?,?,?,?,?,?)");
			//agregar campos
			s.setString(1, r.getDescripcion());
			s.setInt(2,r.getCliente().getCodigo_cliente());
			s.setInt(3, r.getOperador().getCodigo());
			s.setInt(4,r.getResponsable().getCodigo());
			s.setBoolean(5, false);
			s.setFloat(6, (float) r.getTiempoRespuesta());
			s.setString(7, null);
			s.setString(8, r.getTipoReclamo());
			s.execute();
			
			PoolConnection.getPoolConnection().realeaseConnection(con);
		}
		catch (Exception e)
		{
			System.out.println("Error al insertar Reclamo");
			e.printStackTrace();
		}
		
	}
	
	

}
