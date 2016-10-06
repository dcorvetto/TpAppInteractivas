package vista.reclamos;

import javax.swing.*;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VistaNuevoReclamoProducto extends JFrame {

    private static final long serialVersionUID = -277076000511824707L;
    private JPanel contentPane;
    private Integer codigoUsuario;
    private JTextField textField_1;


    public VistaNuevoReclamoProducto(Integer codigoUsuario) {
    	this.setBounds(0, 0, 381, 256);
    	setTitle("Reclamo Producto");
        this.codigoUsuario = codigoUsuario;
        getContentPane().setLayout(null);
        
        JComboBox comboBox = new JComboBox();
        comboBox.setBounds(76, 13, 90, 20);
        getContentPane().add(comboBox);
        
        JLabel lblCliente = new JLabel("Cliente:");
        lblCliente.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblCliente.setBounds(10, 14, 56, 14);
        getContentPane().add(lblCliente);
        
        JLabel lblProductos = new JLabel("Productos:");
        lblProductos.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblProductos.setBounds(10, 45, 80, 14);
        getContentPane().add(lblProductos);
        
        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblNombre.setBounds(10, 70, 56, 14);
        getContentPane().add(lblNombre);
        
        JLabel lblCantidad = new JLabel("Cantidad:");
        lblCantidad.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblCantidad.setBounds(178, 72, 64, 14);
        getContentPane().add(lblCantidad);
        
        textField_1 = new JTextField();
        textField_1.setColumns(10);
        textField_1.setBounds(252, 70, 50, 20);
        getContentPane().add(textField_1);
        
        JList list = new JList();
        list.setBounds(10, 174, 343, -78);
        getContentPane().add(list);
        
        JButton btnAceptar = new JButton("Aceptar");
        btnAceptar.setBounds(10, 184, 89, 23);
        getContentPane().add(btnAceptar);
        
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        	}
        });
        btnCancelar.setBounds(264, 184, 89, 23);
        getContentPane().add(btnCancelar);
        
        JButton btnAgregar = new JButton("+");
        btnAgregar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        	}
        });
        btnAgregar.setBounds(312, 68, 41, 23);
        getContentPane().add(btnAgregar);
        
        JComboBox comboBox_1 = new JComboBox();
        comboBox_1.setBounds(76, 70, 90, 20);
        getContentPane().add(comboBox_1);

    }
}
