package vista.reportes;

import controlador.Sistema;
import negocio.views.ReclamoTPromXOperadorView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class VistaTiempoRespuesta extends JFrame {
	private static final long serialVersionUID = -826489153078003468L;
	private JTable table;
	
	public VistaTiempoRespuesta() {
		setTitle("Tiempo promedio de respuesta");
		this.setBounds(0,0, 368,231);
		getContentPane().setLayout(null);
		
		JLabel lblTiempoPromedioDe = new JLabel("Tiempo promedio de respuesta por responsable:");
		lblTiempoPromedioDe.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblTiempoPromedioDe.setBounds(10, 11, 334, 17);
		getContentPane().add(lblTiempoPromedioDe);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 39, 334, 112);
		getContentPane().add(scrollPane);

		//Columnas de la tabla
		Vector<String> nombresColumnas = new Vector<>();
		nombresColumnas.add("Responsable");
		nombresColumnas.add("Tiempo promedio de respuesta");

		//Se pasan valores de la collection de reclamos a vectores porque la tabla solo acepta Vectores de vectores... :@
		Vector<String> dataTiempoResponsable = new Vector<>();
		Vector<Vector<String>> data = new Vector<>();
		for (ReclamoTPromXOperadorView reclamoTPromXOperadorView : Sistema.getInstancia().getTiemposPromedioXResponsable()) {
			dataTiempoResponsable.add(reclamoTPromXOperadorView.getUsuarioOperador());
			dataTiempoResponsable.add(reclamoTPromXOperadorView.getTiempoPromedio());
			data.add(dataTiempoResponsable);
			dataTiempoResponsable = new Vector<>();
		}

		TableModel model = new DefaultTableModel(data, nombresColumnas);

		table = new JTable(model);
		scrollPane.setViewportView(table);
		
		JButton btnSalir = new JButton("Salir");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		btnSalir.setBounds(253, 162, 89, 23);
		getContentPane().add(btnSalir);
	}

}
