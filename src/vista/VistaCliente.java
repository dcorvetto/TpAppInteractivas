package vista;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class VistaCliente extends JFrame {

	private static final long serialVersionUID = -496712526467543446L;
	private JPanel contentPane;
	private JTextField fieldCodigoNro;
	private JTextField fieldNombre;
	private JTextField fieldCantidadReclamos;
	private JLabel lblMail;
	private JTextField fieldMail;

	public VistaCliente() {
		setFont(new Font("Tahoma", Font.PLAIN, 14));
		setTitle("Cliente");
		this.setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblCodigoNro = new JLabel("C\u00F3digo Cliente:");
		lblCodigoNro.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCodigoNro.setBounds(20, 9, 123, 19);
		contentPane.add(lblCodigoNro);
		
		fieldCodigoNro = new JTextField();
		fieldCodigoNro.setEditable(false);
		fieldCodigoNro.setFont(new Font("Tahoma", Font.PLAIN, 14));
		fieldCodigoNro.setBounds(153, 8, 118, 20);
		contentPane.add(fieldCodigoNro);
		fieldCodigoNro.setColumns(10);
		
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNombre.setBounds(20, 39, 123, 19);
		contentPane.add(lblNombre);
		
		fieldNombre = new JTextField();
		fieldNombre.setEditable(false);
		fieldNombre.setFont(new Font("Tahoma", Font.PLAIN, 14));
		fieldNombre.setColumns(10);
		fieldNombre.setBounds(153, 38, 118, 20);
		contentPane.add(fieldNombre);
		
		JLabel lblCantidadReclamos = new JLabel("Cantidad Reclamos:");
		lblCantidadReclamos.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCantidadReclamos.setBounds(20, 69, 123, 19);
		contentPane.add(lblCantidadReclamos);
		
		fieldCantidadReclamos = new JTextField();
		fieldCantidadReclamos.setEditable(false);
		fieldCantidadReclamos.setFont(new Font("Tahoma", Font.PLAIN, 14));
		fieldCantidadReclamos.setColumns(10);
		fieldCantidadReclamos.setBounds(153, 68, 118, 20);
		contentPane.add(fieldCantidadReclamos);
		
		lblMail = new JLabel("Mail:");
		lblMail.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblMail.setBounds(20, 100, 123, 19);
		contentPane.add(lblMail);
		
		fieldMail = new JTextField();
		fieldMail.setEditable(false);
		fieldMail.setFont(new Font("Tahoma", Font.PLAIN, 14));
		fieldMail.setColumns(10);
		fieldMail.setBounds(153, 99, 118, 20);
		contentPane.add(fieldMail);
	}

}
