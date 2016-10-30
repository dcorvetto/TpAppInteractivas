package vista.reportes;

import controlador.Sistema;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.Vector;

public class VistaRankingTratamientoReclamos extends JFrame {
	private static final long serialVersionUID = -7168322725661829180L;
	private JTable table;
	
	public VistaRankingTratamientoReclamos() {
		setTitle("Ranking tratamiento de reclamos");
		this.setBounds(0,0, 349, 242);
		getContentPane().setLayout(null);
		
		JLabel lblRankingTratamientos = new JLabel("Ranking de tratamientos de reclamos:");
		lblRankingTratamientos.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblRankingTratamientos.setBounds(10, 11, 261, 17);
		getContentPane().add(lblRankingTratamientos);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 36, 313, 122);
		getContentPane().add(scrollPane);

		//Columnas de la tabla
		Vector<String> nombresColumnas = new Vector<>();
		nombresColumnas.add("Estado");
		nombresColumnas.add("Cantidad");

		//Se pasan valores de la collection de reclamos a vectores porque la tabla solo acepta Vectores de vectores... :@
		Vector<String> dataRanking = new Vector<>();
		Vector<Vector<String>> data = new Vector<>();
		for (Map.Entry<String, Long> entry : Sistema.getInstancia().getRankingTratamientoReclamos().entrySet()) {
			dataRanking.add(entry.getKey());
			dataRanking.add(String.valueOf(entry.getValue()));
			data.add(dataRanking);
			dataRanking = new Vector<>();
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
		btnSalir.setBounds(234, 169, 89, 23);
		getContentPane().add(btnSalir);
	}
}
