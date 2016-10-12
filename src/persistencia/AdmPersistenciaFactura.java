package persistencia;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

import negocio.Cliente;
import negocio.Factura;
import negocio.Factura;

public class AdmPersistenciaFactura extends AdministradorPersistencia {
	
	private static AdmPersistenciaFactura instancia;
	
	private AdmPersistenciaFactura()
	{
		
	}
	public static AdmPersistenciaFactura getInstancia()
	{
		if (instancia == null)
			instancia = new AdmPersistenciaFactura();
		return instancia;
	}

	@Override
	public void insert(Object o) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Object o) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Object d) {
		// TODO Auto-generated method stub

	}

	@Override
	public Vector<Object> select(Object o) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Factura buscarPorId(int codigoFactura) {
		Connection connection=null;
		try {
			Factura factura = null;
			connection = PoolConnection.getPoolConnection().getConnection();
			PreparedStatement statement = connection.prepareStatement("select f.* from Factura f where idFactura = ?");
			statement.setInt(1, codigoFactura);
			ResultSet result = statement.executeQuery();
			while (result.next())
			{
				int idFactura = result.getInt(1);
				Date fecha = result.getDate(2);
				int idCliente = result.getInt(3);
				//Se trae el cliente por no hacemos lazy loading.
				Cliente cliente = AdmPersistenciaCliente.getInstancia().buscarCliente(idCliente);
				factura = new Factura(idFactura, new java.util.Date(fecha.getTime()), cliente);
			}
			
			return factura;
			
		} catch (Exception e) {
			System.out.println("No se encontro factura");
			e.printStackTrace();
		} finally {
			PoolConnection.getPoolConnection().realeaseConnection(connection);
		}
		return null;
		
	}
	
	public Vector<Factura> selectAll()
	{
		try
		{
			Vector <Factura>rta = new Vector<Factura>();
			Connection c = PoolConnection.getPoolConnection().getConnection();
			PreparedStatement s = c.prepareStatement( "Select * from Factura");
			ResultSet result = s.executeQuery();
			while (result.next())
			{
				int idFactura =result.getInt(1);
				Date fecha = result.getDate(2);
				int idCliente = result.getInt(3);
				Factura fact = new  Factura();
				fact.setCliente(Cliente.buscarPorCodigo(idCliente));
				fact.setIdFactura(idFactura);
				fact.setFecha(fecha);
				rta.add(fact);
				
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
	public int obtenerCliente(int idFactura) {
		try
		{
			int idCliente = 0;
			Connection c = PoolConnection.getPoolConnection().getConnection();
			PreparedStatement s = c.prepareStatement( "Select idCliente from Factura where idFactura=? ");
			s.setInt(1, idFactura);
			ResultSet result = s.executeQuery();
			Cliente cliente = new Cliente();
			while (result.next())
			{
				idCliente = result.getInt(1);
			}
			PoolConnection.getPoolConnection().realeaseConnection(c);
			return idCliente;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return 0;
	}

}
