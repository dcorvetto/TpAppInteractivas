package vista.reclamos;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JFormattedTextField;

public class VistaNuevoReclamoFactura extends JFrame {

	private static final long serialVersionUID = -7732676766832254025L;
	private JPanel contentPane;
    private Integer codigoUsuario;
    private JTextField textField;
    private JTextField textField_1;


    public VistaNuevoReclamoFactura(Integer codigoUsuario) {
    	this.setBounds(0, 0, 341, 152);
    	setTitle("Reclamo Factura");
        this.codigoUsuario = codigoUsuario;
        getContentPane().setLayout(null);
        
        JComboBox comboBox = new JComboBox();
        comboBox.setBounds(76, 13, 90, 20);
        getContentPane().add(comboBox);
        
        JLabel lblCliente = new JLabel("Cliente:");
        lblCliente.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblCliente.setBounds(10, 14, 56, 14);
        getContentPane().add(lblCliente);
        
        JButton btnAceptar = new JButton("Aceptar");
        btnAceptar.setBounds(10, 77, 89, 23);
        getContentPane().add(btnAceptar);
        
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        	}
        });
        btnCancelar.setBounds(227, 77, 89, 23);
        getContentPane().add(btnCancelar);
        
        JLabel lblZona = new JLabel("Codigo Factura:");
        lblZona.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblZona.setBounds(10, 44, 98, 20);
        getContentPane().add(lblZona);
        
        textField = new JTextField();
        textField.setBounds(118, 44, 86, 20);
        getContentPane().add(textField);
        textField.setColumns(10);
        
        JLabel lblFecha = new JLabel("Fecha:");
        lblFecha.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblFecha.setBounds(176, 11, 41, 20);
        getContentPane().add(lblFecha);
        
        textField_1 = new JTextField();
        textField_1.setColumns(10);
        textField_1.setBounds(226, 13, 88, 20);
        getContentPane().add(textField_1);

    }
}
