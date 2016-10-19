package persistencia;

import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import negocio.Cliente;
import negocio.EnumRoles;
import negocio.Cliente;

public class AdmPersistenciaCliente extends AdministradorPersistencia 
{
	private static AdmPersistenciaCliente instancia;
	
	private AdmPersistenciaCliente()
	{
		
	}
	public static AdmPersistenciaCliente getInstancia()
	{
		if (instancia == null)
			instancia = new AdmPersistenciaCliente();
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
	public Vector<Cliente> selectAll()
	{
		try
		{
			
	
			Vector <Cliente>rta = new Vector<Cliente>();
			Connection c = PoolConnection.getPoolConnection().getConnection();
			Statement s = c.createStatement();
			String senten = "Select * from Cliente";
			ResultSet result = s.executeQuery(senten);
			while (result.next())
			{
				int codigo = result.getInt(1);
				String nom = result.getString(2);
				int dni = result.getInt(3);
				String domicilio = result.getString(4);
				String mail = result.getString(5);
				String telefono = result.getString(6);
				Cliente cli = new  Cliente(codigo,nom,dni, domicilio,telefono,mail);
				rta.add(cli);
				
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
	
	public Cliente buscarCliente(int numero)
	{
		try
		{
			Cliente cli = null;
			Connection con = PoolConnection.getPoolConnection().getConnection();
			PreparedStatement s = con.prepareStatement("select c.* from Cliente c where codigo_cliente=?");
			s.setString(1, String.valueOf(numero));
			ResultSet result = s.executeQuery();
			while (result.next())
			{
				int codigo = result.getInt(1);
				String nom = result.getString(2);
				int dni = result.getInt(3);
				String domicilio = result.getString(4);
				String mail = result.getString(5);
				String telefono = result.getString(6);
				cli = new  Cliente(codigo,nom,dni, domicilio,telefono,mail);
			}
			
			PoolConnection.getPoolConnection().realeaseConnection(con);
			return cli;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	public Cliente buscarPorDni(int dni) {
		try
		{
			Cliente cli = null;
			Connection con = PoolConnection.getPoolConnection().getConnection();
			PreparedStatement s = con.prepareStatement("select c.* from Cliente c where dni=?");
			s.setString(1, String.valueOf(dni));
			ResultSet result = s.executeQuery();
			while (result.next())
			{
				int codigo = result.getInt(1);
				String nom = result.getString(2);
				int dnif = result.getInt(3);
				String domicilio = result.getString(4);
				String mail = result.getString(5);
				String telefono = result.getString(6);
				cli = new  Cliente(codigo,nom,dnif, domicilio,telefono,mail);
			}
			
			PoolConnection.getPoolConnection().realeaseConnection(con);
			return cli;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	
	public List<Vector> getClientesOrdenadosPorCantidadReclamoDesc()
	{
		try
		{
			List <Vector>rta = new ArrayList<Vector>();
			Connection c = PoolConnection.getPoolConnection().getConnection();
			Statement s = c.createStatement();
			String senten = "select  dni, c.nombre, count(r.numero) as cantidad, c.mail "
					+ " from cliente c "
					+ "	left join reclamo r"
					+ " on c.codigo_cliente=r.cliente"
					+ " group by c.dni, c.nombre, c.mail"
					+ " order by count(r.numero) DESC, dni ASC, nombre ASC";
			ResultSet result = s.executeQuery(senten);
			while (result.next())
			{
				int codigo = result.getInt(1);
				String dni = result.getString(2);
				long cantidad = result.getLong(3);
				String mail = result.getString(4);
				Vector obj = new Vector(4);
				obj.add(codigo);
				obj.add(dni);
				obj.add(cantidad);
				obj.add(mail);
				rta.add(obj);
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
	
	
	
}
