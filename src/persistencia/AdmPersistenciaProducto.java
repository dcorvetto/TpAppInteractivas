package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import negocio.Producto;
import negocio.EnumRoles;
import negocio.Producto;

public class AdmPersistenciaProducto extends AdministradorPersistencia 
{
	private static AdmPersistenciaProducto instancia;
	
	private AdmPersistenciaProducto()
	{
		
	}
	public static AdmPersistenciaProducto getInstancia()
	{
		if (instancia == null)
			instancia = new AdmPersistenciaProducto();
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
	public Vector<Producto> selectAll()
	{
		try
		{
			Vector <Producto>rta = new Vector<Producto>();
			Connection c = PoolConnection.getPoolConnection().getConnection();
			Statement s = c.createStatement();
			String senten = "Select * from Producto";
			ResultSet result = s.executeQuery(senten);
			while (result.next())
			{
				int codigo = result.getInt(1);
				String titulo = result.getString(2);
				String descripcion = result.getString(3);
				float precio = result.getFloat(4);
				Producto prod = new  Producto(codigo,titulo,descripcion,precio);
				rta.add(prod);
				
			}
			PoolConnection.getPoolConnection().realeaseConnection(c);
			return rta;
		}
		catch(Exception e)
		{
			
		}
		return null;
	}
	
	public Producto buscarProducto(int numero)
	{
		try
		{
			Producto cli = null;
			Connection con = PoolConnection.getPoolConnection().getConnection();
			PreparedStatement s = con.prepareStatement("select c.* from Producto c where codigo=?");
			s.setString(1, String.valueOf(numero));
			ResultSet result = s.executeQuery();
			while (result.next())
			{
				int codigo = result.getInt(1);
				String titulo = result.getString(2);
				String descripcion = result.getString(3);
				float precio = result.getFloat(4);
				cli = new  Producto(codigo,titulo,descripcion,precio);
			}
			
			PoolConnection.getPoolConnection().realeaseConnection(con);
			return cli;
		}
		catch (Exception e)
		{
			System.out.println();
		}
		return null;
	}
	
	
	
	
}
