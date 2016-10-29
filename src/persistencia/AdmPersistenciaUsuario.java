package persistencia;

import negocio.EnumRoles;
import negocio.Reclamo;
import negocio.Usuario;
import negocio.reclamos.ItemFacturaReclamo;
import negocio.reclamos.ItemProductoReclamo;
import negocio.reclamos.ItemProductoReclamoFaltantes;
import negocio.reclamos.ReclamoCantidad;
import negocio.reclamos.ReclamoCompuesto;
import negocio.reclamos.ReclamoFacturacion;
import negocio.reclamos.ReclamoFaltantes;
import negocio.reclamos.ReclamoProducto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class AdmPersistenciaUsuario extends AdministradorPersistencia 
{
	private static AdmPersistenciaUsuario instancia;
	
	private AdmPersistenciaUsuario()
	{
		
	}
	public static AdmPersistenciaUsuario getInstancia()
	{
		if (instancia == null)
			instancia = new AdmPersistenciaUsuario();
		return instancia;
	}

	@Override
	public void delete(Object d) {
		// TODO Auto-generated method stub

	}

	@Override
	public void insert(Object o) {
		// TODO Auto-generated method stub

	}

	@Override
	public Vector<Object> select(Object o) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Object o) {
		// TODO Auto-generated method stub

	}
	public Vector<Usuario> selectAll()
	{
		try
		{
			
	
			Vector <Usuario>rta = new Vector<Usuario>();
			Connection c = PoolConnection.getPoolConnection().getConnection();
			Statement s = c.createStatement();
			String senten = "Select * from Usuario";
			ResultSet result = s.executeQuery(senten);
			while (result.next())
			{

				int codigo = result.getInt(1);
				String nom = result.getString(2);
				String apellido = result.getString(3);
				String usuario = result.getString(4);
				String clave = result.getString(5);
				Usuario usu = new  Usuario(nom, apellido, codigo, usuario, clave);
				rta.add(usu);
				
			}
			PoolConnection.getPoolConnection().realeaseConnection(c);
			return rta;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public Usuario buscarUsuario(String usuario, String password)
	{
		try
		{
			Usuario usu = null;
			Connection con = PoolConnection.getPoolConnection().getConnection();
			PreparedStatement s = con.prepareStatement("select u.* from usuario u where usuario=? and clave=?");			//agregar campos
			s.setString(1,usuario);
			s.setString(2,password);
			ResultSet result = s.executeQuery();			
			while (result.next())
			{
				int codigo = result.getInt(1);
				String nom = result.getString(2);
				String apellido = result.getString(3);
				String u = result.getString(4);
				String clave = result.getString(5);
				usu = new  Usuario(nom, apellido, codigo, u, clave);
			}
			if(usu!=null){
				List<EnumRoles> listaRoles = buscarRoles(usu.getCodigo());
				
				usu.setRoles(listaRoles);
			}
			
			PoolConnection.getPoolConnection().realeaseConnection(con);
			return usu;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public Usuario buscarUsuario(int numero)
	{
		try
		{
			Usuario usu = null;
			Connection con = PoolConnection.getPoolConnection().getConnection();
			PreparedStatement s = con.prepareStatement("select u.* from usuario u where codigo=?");
			s.setString(1, String.valueOf(numero));
			ResultSet result = s.executeQuery();
			while (result.next())
			{
				int codigo = result.getInt(1);
				String nom = result.getString(2);
				String apellido = result.getString(3);
				String u = result.getString(4);
				String clave = result.getString(5);
				usu = new  Usuario(nom, apellido, codigo, u, clave);
			}
			List<EnumRoles> listaRoles = buscarRoles(usu.getCodigo());
			
			usu.setRoles(listaRoles);
			
			PoolConnection.getPoolConnection().realeaseConnection(con);
			return usu;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	
	public List<EnumRoles> buscarRoles(int idUsuario)
	{
		try
		{
			Connection con = PoolConnection.getPoolConnection().getConnection();
			PreparedStatement s = con.prepareStatement("select r.descripcion from UsuarioRol ur"
					+ " join rol r on r.idRol=ur.idRol"
					+ " where idUsuario=?");			
			s.setInt(1,idUsuario);
			ResultSet result = s.executeQuery();
			List<EnumRoles> lista = new ArrayList<EnumRoles>();
			while (result.next())
			{ 
				switch(result.getString(1)){
				case "Distribucion": lista.add(EnumRoles.DISTRIBUCION);
					break;
				case "Administrador" : lista.add(EnumRoles.ADMINISTRACION);
					break;
				case "CallCenter" : lista.add(EnumRoles.CALL_CENTER);
					break;
				case "Consulta" : lista.add(EnumRoles.CONSULTA);
				 	break;
				case "Facturacion" : lista.add(EnumRoles.FACTURACION);
				 	break;
				case "ZonaEntrega" : lista.add(EnumRoles.ZONA_ENTREGA);
				 	break;
				
				}
			}
			
			PoolConnection.getPoolConnection().realeaseConnection(con);
			return lista;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	public String obtenerNombreCompleto(Integer idUsuario) {
		String nombre = "";
		try
		{
			Usuario usu = null;
			Connection con = PoolConnection.getPoolConnection().getConnection();
			PreparedStatement s = con.prepareStatement("select u.* from usuario u where codigo=?");
			s.setString(1, String.valueOf(idUsuario));
			ResultSet result = s.executeQuery();
			while (result.next())
			{
				int codigo = result.getInt(1);
				String nom = result.getString(2);
				String apellido = result.getString(3);
				String u = result.getString(4);
				String clave = result.getString(5);
				usu = new  Usuario(nom, apellido, codigo, u, clave);
				nombre = usu.getNombre() +" "+usu.getApellido();
			}
			
			PoolConnection.getPoolConnection().realeaseConnection(con);
			return nombre;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return nombre;
	}
	
	public List<Usuario> obtenerResponsables(String tipo) {
		try
		{
			String rol = "";
			switch(tipo.toUpperCase()){
			case "PRODUCTO":
				rol = "Distribucion";
				break;
			case "FALTANTES":
				rol = "Distribucion";
				break;
			case "CANTIDAD":
				rol = "Distribucion";
				break;
			case "FACTURACION":
				rol = "Facturacion";
				break;
			case "ZONA":
				rol = "ZonaEntrega";
				break;
			case "COMPUESTO":
				break;
			}

			List <Usuario>rta = new ArrayList<Usuario>();
			Connection c = PoolConnection.getPoolConnection().getConnection();
			PreparedStatement s;
			if(rol.equals("")){ //Si es compuesto traer todos
				s = c.prepareStatement("Select u.* from Usuario u join UsuarioRol ur "
						+ " on ur.idUsuario = u.codigo "
						+ " join Rol r"
						+ " on r.idRol=ur.idRol");
			}else{
				s = c.prepareStatement("Select u.* from Usuario u join UsuarioRol ur "
					+ " on ur.idUsuario = u.codigo "
					+ " join Rol r"
					+ " on r.idRol=ur.idRol"
					+ " where r.descripcion=?");
				s.setString(1,rol);
			}
			ResultSet result = s.executeQuery();
			while (result.next())
			{
				int codigo = result.getInt(1);
				String nom = result.getString(2);
				String apellido = result.getString(3);
				String usuario = result.getString(4);
				String clave = result.getString(5);
				Usuario usu = new  Usuario(nom, apellido, codigo, usuario, clave);
				rta.add(usu);
				
			}
			PoolConnection.getPoolConnection().realeaseConnection(c);
			return rta;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	public Usuario obtenerUsuarioPorUsuario(String responsable) {
		try
		{
			Usuario usu = null;
			Connection con = PoolConnection.getPoolConnection().getConnection();
			PreparedStatement s = con.prepareStatement("select u.* from usuario u where usuario=?");
			s.setString(1,responsable);
			ResultSet result = s.executeQuery();
			while (result.next())
			{
				int codigo = result.getInt(1);
				String nom = result.getString(2);
				String apellido = result.getString(3);
				String u = result.getString(4);
				String clave = result.getString(5);
				usu = new  Usuario(nom, apellido, codigo, u, clave);
			}
			List<EnumRoles> listaRoles = buscarRoles(usu.getCodigo());
			
			usu.setRoles(listaRoles);
			
			PoolConnection.getPoolConnection().realeaseConnection(con);
			return usu;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
}
