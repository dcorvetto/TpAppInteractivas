package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import negocio.EnumRoles;
import negocio.Usuario;

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
				Usuario usu = new  Usuario(codigo, apellido, clave, nom, usuario);
				rta.add(usu);
				
			}
			PoolConnection.getPoolConnection().realeaseConnection(c);
			return rta;
		}
		catch(Exception e)
		{
			
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
				usu = new  Usuario(codigo, apellido, clave, nom, u);
			}
			List<EnumRoles> listaRoles = buscarRoles(usu.getCodigo());
			
			usu.setRoles(listaRoles);
			
			PoolConnection.getPoolConnection().realeaseConnection(con);
			return usu;
		}
		catch (Exception e)
		{
			System.out.println();
		}
		return null;
	}
	
	public Usuario buscarUsuario(int numero)
	{
		try
		{
			Usuario usu = null;
			Connection con = PoolConnection.getPoolConnection().getConnection();
			PreparedStatement s = con.prepareStatement("select u.* from usuario u where usuario=?");			//agregar campos
			s.setString(1, String.valueOf(numero));
			ResultSet result = s.executeQuery();
			while (result.next())
			{
				int codigo = result.getInt(1);
				String nom = result.getString(2);
				String apellido = result.getString(3);
				String u = result.getString(4);
				String clave = result.getString(5);
				usu = new  Usuario(codigo, apellido, clave, nom, u);
			}
			List<EnumRoles> listaRoles = buscarRoles(usu.getCodigo());
			
			usu.setRoles(listaRoles);
			
			PoolConnection.getPoolConnection().realeaseConnection(con);
			return usu;
		}
		catch (Exception e)
		{
			System.out.println();
		}
		return null;
	}
	
	
	public List<EnumRoles> buscarRoles(int idUsuario)
	{
		try
		{
			Connection con = PoolConnection.getPoolConnection().getConnection();
			PreparedStatement s = con.prepareStatement("select r.description from UsuarioRol ur"
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
			System.out.println();
		}
		return null;
	}
	
	
	@Override
	public int insertar(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	
}
