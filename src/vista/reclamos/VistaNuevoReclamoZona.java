package vista.reclamos;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class VistaNuevoReclamoZona extends JFrame {

	private static final long serialVersionUID = 2679709586176230119L;
	private JPanel contentPane;
    private Integer codigoUsuario;
    private JTextField textField;


    public VistaNuevoReclamoZona(Integer codigoUsuario) {
    	this.setBounds(0, 0, 342, 116);
    	setTitle("Reclamo Zona");
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
        btnAceptar.setBounds(10, 44, 89, 23);
        getContentPane().add(btnAceptar);
        
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        	}
        });
        btnCancelar.setBounds(227, 44, 89, 23);
        getContentPane().add(btnCancelar);
        
        JLabel lblZona = new JLabel("Zona:");
        lblZona.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblZona.setBounds(176, 16, 44, 14);
        getContentPane().add(lblZona);
        
        textField = new JTextField();
        textField.setBounds(230, 13, 86, 20);
        getContentPane().add(textField);
        textField.setColumns(10);

    }

}
