package vista;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class VistaReclamoTPromXOperador extends JFrame {

	private static final long serialVersionUID = -393049433635275844L;
	private JPanel contentPane;
	private JTextField fieldUsuarioOperador;
	private JTextField fieldTiempoPromedio;

	public VistaReclamoTPromXOperador() {
		setFont(new Font("Tahoma", Font.PLAIN, 14));
		setTitle("Reclamo");
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblUsuarioOperador = new JLabel("Usuario Operador:");
		lblUsuarioOperador.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblUsuarioOperador.setBounds(10, 9, 112, 19);
		contentPane.add(lblUsuarioOperador);
		
		fieldUsuarioOperador = new JTextField();
		fieldUsuarioOperador.setEditable(false);
		fieldUsuarioOperador.setFont(new Font("Tahoma", Font.PLAIN, 14));
		fieldUsuarioOperador.setBounds(132, 10, 121, 20);
		contentPane.add(fieldUsuarioOperador);
		fieldUsuarioOperador.setColumns(10);
		
		JLabel lblTiempoPromedio = new JLabel("Tiempo Promedio:");
		lblTiempoPromedio.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTiempoPromedio.setBounds(10, 39, 112, 19);
		contentPane.add(lblTiempoPromedio);
		
		fieldTiempoPromedio = new JTextField();
		fieldTiempoPromedio.setEditable(false);
		fieldTiempoPromedio.setFont(new Font("Tahoma", Font.PLAIN, 14));
		fieldTiempoPromedio.setColumns(10);
		fieldTiempoPromedio.setBounds(132, 40, 121, 20);
		contentPane.add(fieldTiempoPromedio);
	}

}
