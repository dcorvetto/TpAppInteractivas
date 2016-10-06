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

public class VistaNuevoReclamoFaltantes extends JFrame {

	private static final long serialVersionUID = -345003172331978923L;
	private JPanel contentPane;
    private Integer codigoUsuario;
    private JTextField textField_1;
    private JTextField textField;


    public VistaNuevoReclamoFaltantes(Integer codigoUsuario) {
    	this.setBounds(0, 0, 373, 152);
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
        btnAceptar.setBounds(10, 75, 89, 23);
        getContentPane().add(btnAceptar);
        
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        	}
        });
        btnCancelar.setBounds(255, 75, 89, 23);
        getContentPane().add(btnCancelar);
        
        JLabel lblZona = new JLabel("Producto:");
        lblZona.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblZona.setBounds(176, 13, 70, 20);
        getContentPane().add(lblZona);
        
        JLabel lblFecha = new JLabel("Cantidad solicitada:");
        lblFecha.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblFecha.setBounds(8, 44, 122, 20);
        getContentPane().add(lblFecha);
        
        textField_1 = new JTextField();
        textField_1.setColumns(10);
        textField_1.setBounds(140, 46, 34, 20);
        getContentPane().add(textField_1);
        
        JComboBox comboBox_1 = new JComboBox();
        comboBox_1.setBounds(252, 13, 90, 20);
        getContentPane().add(comboBox_1);
        
        JLabel lblCantidadRecibida = new JLabel("Cantidad recibida:");
        lblCantidadRecibida.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblCantidadRecibida.setBounds(184, 44, 114, 20);
        getContentPane().add(lblCantidadRecibida);
        
        textField = new JTextField();
        textField.setColumns(10);
        textField.setBounds(308, 46, 34, 20);
        getContentPane().add(textField);

    }

}
