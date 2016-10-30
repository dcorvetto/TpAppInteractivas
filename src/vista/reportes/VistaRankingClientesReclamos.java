package vista.reportes;

import controlador.Sistema;
import negocio.views.ClienteView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class VistaRankingClientesReclamos extends JFrame {
	private static final long serialVersionUID = 755981269010737322L;
	private JTable tableRanking;

	public VistaRankingClientesReclamos() {
		this.setBounds(0, 0, 441, 235);
		setTitle("Ranking Clientes por relcamos");
		getContentPane().setLayout(null);
		
		JLabel lblRanking = new JLabel("Ranking clientes por cantidad de reclamos:");
		lblRanking.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblRanking.setBounds(10, 11, 293, 17);
		getContentPane().add(lblRanking);
		
		JScrollPane scrollPaneRanking = new JScrollPane();
		scrollPaneRanking.setBounds(10, 39, 405, 113);
		getContentPane().add(scrollPaneRanking);

		//Columnas de la tabla
		Vector<String> nombresColumnas = new Vector<>();
		nombresColumnas.add("Nombre");
		nombresColumnas.add("DNI");
		nombresColumnas.add("Email");
		nombresColumnas.add("Cantidad Reclamos");

		//Se pasan valores de la collection de reclamos a vectores porque la tabla solo acepta Vectores de vectores... :@
		Vector<String> dataRanking = new Vector<>();
		Vector<Vector<String>> data = new Vector<>();
		for (ClienteView clienteView : Sistema.getInstancia().getClientesMayorCantReclamos()) {
			dataRanking.add(clienteView.getNombre());
			dataRanking.add(clienteView.getDni());
			dataRanking.add(clienteView.getMail());
			dataRanking.add(clienteView.getCantidad_reclamos());
			data.add(dataRanking);
			dataRanking = new Vector<>();
		}

		TableModel model = new DefaultTableModel(data, nombresColumnas);

		tableRanking = new JTable(model);
		scrollPaneRanking.setViewportView(tableRanking);
		
		JButton btnSalir = new JButton("Salir");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		btnSalir.setBounds(322, 162, 93, 23);
		getContentPane().add(btnSalir);
	}


}
