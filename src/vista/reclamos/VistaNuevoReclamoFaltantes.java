package vista.reclamos;

import controlador.Sistema;
import negocio.reclamos.TipoReclamo;
import negocio.views.ClienteView;
import negocio.views.UsuarioView;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class VistaNuevoReclamoFaltantes extends JFrame {

    private static final long serialVersionUID = -345003172331978923L;
    private JPanel contentPane;
    private Integer codigoUsuario;
    private JTextField textFieldCantSolicitada;
    private JTextField textFieldCantRecibida;
    private JComboBox<String> comboBoxClientes;
    private JComboBox<String> comboBoxProductos;
    private JComboBox<String> comboBoxResp;
    private JTextArea textAreaDescripcion;


    public VistaNuevoReclamoFaltantes(Integer codigoUsuario) {
        this.setBounds(0, 0, 373, 230);
        setTitle("Reclamo Faltantes");
        this.codigoUsuario = codigoUsuario;
        getContentPane().setLayout(null);

        comboBoxClientes = new JComboBox<>();
        comboBoxClientes.setBounds(76, 13, 90, 20);
        for (ClienteView clienteView : Sistema.getInstancia().getClientes()) {
            comboBoxClientes.addItem(clienteView.getDni());
        }
        getContentPane().add(comboBoxClientes);

        JLabel lblCliente = new JLabel("Cliente:");
        lblCliente.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblCliente.setBounds(10, 14, 56, 14);
        getContentPane().add(lblCliente);
        
        /*Combo responsables */
        JLabel lblResponsable = new JLabel("Responsable:");
        lblResponsable.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblResponsable.setBounds(10, 45, 81, 14);
        getContentPane().add(lblResponsable);   
        
        comboBoxResp = new JComboBox<>();
        comboBoxResp.setBounds(101, 44, 90, 20);
        
        List<UsuarioView> lista = Sistema.getInstancia().getUsuariosResponsables(TipoReclamo.FALTANTES.toString());
        for (UsuarioView usuarioView : lista) {
        	comboBoxResp.addItem(usuarioView.getUsuario());
        }
        getContentPane().add(comboBoxResp);
        
        /*Fin combo responsables*/

        JButton btnAceptar = new JButton("Aceptar");
        btnAceptar.setBounds(12, 157, 89, 23);
        btnAceptar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	String[] parts = String.valueOf(comboBoxProductos.getSelectedItem()).split("-");
                String codigo = parts[0];
                Sistema.getInstancia().crearReclamoFaltantes(Integer.parseInt(String.valueOf(comboBoxClientes.getSelectedItem())),
                        Integer.parseInt(codigo),
                        Integer.parseInt(textFieldCantSolicitada.getText()), Integer.parseInt(textFieldCantRecibida.getText()), textAreaDescripcion.getText(), String.valueOf(comboBoxResp.getSelectedItem()));
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
        btnCancelar.setBounds(257, 157, 89, 23);
        getContentPane().add(btnCancelar);

        JLabel lblZona = new JLabel("Producto:");
        lblZona.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblZona.setBounds(176, 13, 70, 20);
        getContentPane().add(lblZona);

        JLabel lblFecha = new JLabel("Cantidad solicitada:");
        lblFecha.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblFecha.setBounds(10, 75, 122, 20);
        getContentPane().add(lblFecha);

        textFieldCantSolicitada = new JTextField();
        textFieldCantSolicitada.setColumns(10);
        textFieldCantSolicitada.setBounds(142, 77, 34, 20);
        getContentPane().add(textFieldCantSolicitada);

        comboBoxProductos = new JComboBox<>();
        comboBoxProductos.setBounds(252, 13, 90, 20);
        for (String codigoProducto : Sistema.getInstancia().getCodigoProductos()) {
            comboBoxProductos.addItem(codigoProducto);
        }
        getContentPane().add(comboBoxProductos);

        JLabel lblCantidadRecibida = new JLabel("Cantidad recibida:");
        lblCantidadRecibida.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblCantidadRecibida.setBounds(186, 75, 114, 20);
        getContentPane().add(lblCantidadRecibida);

        textFieldCantRecibida = new JTextField();
        textFieldCantRecibida.setColumns(10);
        textFieldCantRecibida.setBounds(310, 77, 34, 20);
        getContentPane().add(textFieldCantRecibida);
        
        JLabel lblDescripcion = new JLabel("Descripcion:");
        lblDescripcion.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblDescripcion.setBounds(12, 106, 75, 14);
        getContentPane().add(lblDescripcion);
        
        JScrollPane scrollPaneDescripcion = new JScrollPane();
        scrollPaneDescripcion.setBounds(97, 106, 249, 40);
        getContentPane().add(scrollPaneDescripcion);
        
        textAreaDescripcion = new JTextArea();
        scrollPaneDescripcion.setViewportView(textAreaDescripcion);

    }

}
