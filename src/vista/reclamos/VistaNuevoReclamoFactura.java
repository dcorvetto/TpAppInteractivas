package vista.reclamos;

import controlador.Sistema;
import negocio.views.ClienteView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class VistaNuevoReclamoFactura extends JFrame {

	private static final long serialVersionUID = -7732676766832254025L;
	private JPanel contentPane;
    private Integer codigoUsuario;
    private JTextField textFieldCodFactura;
    private JTextField textFieldFecha;
    private JComboBox<String> comboBoxClientes;


    public VistaNuevoReclamoFactura(Integer codigoUsuario) {
    	this.setBounds(0, 0, 341, 152);
    	setTitle("Reclamo Factura");
        this.codigoUsuario = codigoUsuario;
        getContentPane().setLayout(null);
        
        comboBoxClientes = new JComboBox<>();
        comboBoxClientes.setBounds(76, 13, 90, 20);
        for (ClienteView clienteView : Sistema.getInstancia().getClientes()) {
            comboBoxClientes.addItem(clienteView.getCodigo_cliente());
        }
        getContentPane().add(comboBoxClientes);
        
        JLabel lblCliente = new JLabel("Cliente:");
        lblCliente.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblCliente.setBounds(10, 14, 56, 14);
        getContentPane().add(lblCliente);
        
        JButton btnAceptar = new JButton("Aceptar");
        btnAceptar.setBounds(10, 77, 89, 23);
        btnAceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    Date date = formatter.parse(textFieldFecha.getText());
                    Sistema.getInstancia().crearReclamoFactura(Integer.parseInt(String.valueOf(comboBoxClientes.getSelectedItem())), date, Integer.parseInt(textFieldCodFactura.getText()));
                    JOptionPane.showMessageDialog(null, "Reclamo agregado correctamente");
                    textFieldCodFactura.setText("");
                    textFieldFecha.setText("");
                    setVisible(false);
                } catch (ParseException e1) {
                    e1.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Formato de fecha incorrecto, debe ser dd/MM/yyyy");
                }
            }
        });
        getContentPane().add(btnAceptar);
        
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
                textFieldCodFactura.setText("");
                textFieldFecha.setText("");
                setVisible(false);
        	}
        });
        btnCancelar.setBounds(227, 77, 89, 23);
        getContentPane().add(btnCancelar);
        
        JLabel lblZona = new JLabel("Codigo Factura:");
        lblZona.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblZona.setBounds(10, 44, 98, 20);
        getContentPane().add(lblZona);
        
        textFieldCodFactura = new JTextField();
        textFieldCodFactura.setBounds(118, 44, 86, 20);
        getContentPane().add(textFieldCodFactura);
        textFieldCodFactura.setColumns(10);
        
        JLabel lblFecha = new JLabel("Fecha:");
        lblFecha.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblFecha.setBounds(176, 11, 41, 20);
        getContentPane().add(lblFecha);
        
        textFieldFecha = new JTextField();
        textFieldFecha.setColumns(10);
        textFieldFecha.setBounds(226, 13, 88, 20);
        textFieldFecha.setText("dd/MM/yyyy");
        getContentPane().add(textFieldFecha);

    }
}
