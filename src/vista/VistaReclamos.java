package vista;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class VistaReclamos extends JFrame {

	private JPanel contentPane;
	
	private Integer codigoUsuario;
	private JTable table;

	public VistaReclamos(Integer codigoUsuario) {
		setTitle("Reclamos");
		this.codigoUsuario = codigoUsuario;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 425, 302);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		String[] nombresColumnas = {"Numero", "Detalle", "Estado", "Fecha"};
		
		table = new JTable(null, nombresColumnas);
		table.setBounds(10, 189, 389, -155);
		contentPane.add(table);
		
		JButton btnAgregar = new JButton("Agregar");
		btnAgregar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnAgregar.setBounds(10, 216, 89, 23);
		contentPane.add(btnAgregar);
	}
}
