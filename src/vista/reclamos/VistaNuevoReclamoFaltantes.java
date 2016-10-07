package vista.reclamos;

import controlador.Sistema;
import negocio.views.ClienteView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VistaNuevoReclamoFaltantes extends JFrame {

    private static final long serialVersionUID = -345003172331978923L;
    private JPanel contentPane;
    private Integer codigoUsuario;
    private JTextField textFieldCantSolicitada;
    private JTextField textFieldCantRecibida;
    private JComboBox<String> comboBoxClientes;
    private JComboBox<String> comboBoxProductos;


    public VistaNuevoReclamoFaltantes(Integer codigoUsuario) {
        this.setBounds(0, 0, 373, 152);
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
        btnAceptar.setBounds(10, 75, 89, 23);
        btnAceptar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Sistema.getInstancia().crearReclamoFaltantes(Integer.parseInt(String.valueOf(comboBoxClientes.getSelectedItem())),
                        Integer.parseInt(String.valueOf(comboBoxProductos.getSelectedItem())),
                        Integer.parseInt(textFieldCantSolicitada.getText()), Integer.parseInt(textFieldCantRecibida.getText()));
                JOptionPane.showMessageDialog(null, "Reclamo agregado correctamente");
                textFieldCantRecibida.setText("");
                textFieldCantSolicitada.setText("");
                setVisible(false);
            }
        });
        getContentPane().add(btnAceptar);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                textFieldCantRecibida.setText("");
                textFieldCantSolicitada.setText("");
                setVisible(false);
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

        textFieldCantSolicitada = new JTextField();
        textFieldCantSolicitada.setColumns(10);
        textFieldCantSolicitada.setBounds(140, 46, 34, 20);
        getContentPane().add(textFieldCantSolicitada);

        comboBoxProductos = new JComboBox<>();
        comboBoxProductos.setBounds(252, 13, 90, 20);
        for (String codigoProducto : Sistema.getInstancia().getCodigoProductos()) {
            comboBoxProductos.addItem(codigoProducto);
        }
        getContentPane().add(comboBoxProductos);

        JLabel lblCantidadRecibida = new JLabel("Cantidad recibida:");
        lblCantidadRecibida.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblCantidadRecibida.setBounds(184, 44, 114, 20);
        getContentPane().add(lblCantidadRecibida);

        textFieldCantRecibida = new JTextField();
        textFieldCantRecibida.setColumns(10);
        textFieldCantRecibida.setBounds(308, 46, 34, 20);
        getContentPane().add(textFieldCantRecibida);

    }

}