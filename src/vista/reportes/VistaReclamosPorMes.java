package vista.reportes;

import controlador.Sistema;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VistaReclamosPorMes extends JFrame {
	private static final long serialVersionUID = -642340579657406105L;
	private JComboBox<String> comboBoxMes;
	private JTextField textFieldCantidadReclamos;
	
	public VistaReclamosPorMes() {
		setTitle("Reclamos por mes");
		this.setBounds(0, 0, 249, 155);
		getContentPane().setLayout(null);
		
		JLabel lblElijaMes = new JLabel("Elija mes:");
		lblElijaMes.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblElijaMes.setBounds(10, 11, 62, 17);
		getContentPane().add(lblElijaMes);
		
		comboBoxMes = new JComboBox<>();
		comboBoxMes.setModel(new DefaultComboBoxModel<>(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"}));
		comboBoxMes.setSelectedIndex(0);
		comboBoxMes.setBounds(82, 11, 42, 20);
		getContentPane().add(comboBoxMes);
		
		JLabel lblCantidadDeRelcamos = new JLabel("Cantidad de reclamos");
		lblCantidadDeRelcamos.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblCantidadDeRelcamos.setBounds(10, 39, 148, 17);
		getContentPane().add(lblCantidadDeRelcamos);
		
		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textFieldCantidadReclamos.setText(String.valueOf(Sistema.getInstancia().getCantidadReclamosMes(Integer.parseInt(String.valueOf(comboBoxMes.getSelectedItem())))));
			}
		});
		btnAceptar.setBounds(134, 11, 89, 23);
		getContentPane().add(btnAceptar);
		
		JLabel lblIngresadosPorMes = new JLabel("ingresados por mes:");
		lblIngresadosPorMes.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblIngresadosPorMes.setBounds(10, 59, 140, 17);
		getContentPane().add(lblIngresadosPorMes);
		
		textFieldCantidadReclamos = new JTextField();
		textFieldCantidadReclamos.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textFieldCantidadReclamos.setEditable(false);
		textFieldCantidadReclamos.setBounds(175, 57, 48, 20);
		getContentPane().add(textFieldCantidadReclamos);
		textFieldCantidadReclamos.setColumns(10);
		
		JButton btnSalir = new JButton("Salir");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		btnSalir.setBounds(134, 88, 89, 23);
		getContentPane().add(btnSalir);
	}
}
