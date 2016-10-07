package vista.reclamos;

import controlador.Sistema;
import negocio.views.ClienteView;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class VistaNuevoReclamoProducto extends JFrame {

    private static final long serialVersionUID = -277076000511824707L;
    private Integer codigoUsuario;
    private JComboBox<String> comboBoxClientes;
    private JTextField textFieldCantidad;
    private JComboBox<String> comboBoxProductos;
    private JTable table;
    private TableModel model;

    private Vector<Vector<String>> data =  new Vector<>();
    private Vector<String> dataProducto = new Vector<>();

    public VistaNuevoReclamoProducto(Integer codigoUsuario) {
    	this.setBounds(0, 0, 381, 256);
    	setTitle("Reclamo Producto");
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
        
        JLabel lblProductos = new JLabel("Productos:");
        lblProductos.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblProductos.setBounds(10, 45, 80, 14);
        getContentPane().add(lblProductos);
        
        JLabel lblCodigo = new JLabel("Codigo:");
        lblCodigo.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblCodigo.setBounds(10, 70, 56, 14);
        getContentPane().add(lblCodigo);
        
        JLabel lblCantidad = new JLabel("Cantidad:");
        lblCantidad.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblCantidad.setBounds(178, 72, 64, 14);
        getContentPane().add(lblCantidad);
        
        textFieldCantidad = new JTextField();
        textFieldCantidad.setColumns(10);
        textFieldCantidad.setBounds(252, 70, 50, 20);
        getContentPane().add(textFieldCantidad);

        comboBoxProductos = new JComboBox<>();
        comboBoxProductos.setBounds(76, 70, 90, 20);
        for (String codigoProducto : Sistema.getInstancia().getCodigoProductos()) {
            comboBoxProductos.addItem(codigoProducto);
        }
        getContentPane().add(comboBoxProductos);

        //Columnas de la tabla
        Vector<String> nombresColumnas = new Vector<>();
        nombresColumnas.add("Codigo");
        nombresColumnas.add("Cantidad");

        //Agregar productos a la tabla
        JButton btnAgregar = new JButton("+");
        btnAgregar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dataProducto.add(String.valueOf(comboBoxProductos.getSelectedItem()));
                dataProducto.add(textFieldCantidad.getText());
                data.add(dataProducto);
                model = new DefaultTableModel(data, nombresColumnas);
                TableModelEvent tableModelEvent = new TableModelEvent(model);
                table.tableChanged(tableModelEvent);
                dataProducto = new Vector<>();
            }
        });
        btnAgregar.setBounds(312, 68, 41, 23);
        getContentPane().add(btnAgregar);

        model = new DefaultTableModel(data, nombresColumnas);
        table = new JTable(model);
        table.setFillsViewportHeight(true);
        table.setBounds(0, 20, 343, 78);
        JScrollPane jScrollPane = new JScrollPane(table);
        jScrollPane.setBounds(10, 100, table.getWidth(), table.getHeight());
        getContentPane().add(jScrollPane);

        JButton btnAceptar = new JButton("Aceptar");
        btnAceptar.setBounds(10, 184, 89, 23);
        btnAceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Map<Integer, Integer> mapCodigoCantidad = new HashMap<>();
                //Esta es la unica forma de pasar de el vector de vectores de string (que es lo que pide el JTable) a un map
                for (int i = 0; i < data.size(); i++) {
                    //elementAt(0)=codigo del producto, y elementAt(1) = cantidad
                    mapCodigoCantidad.put(Integer.valueOf(data.elementAt(i).elementAt(0)), Integer.valueOf(data.elementAt(i).elementAt(0)));
                }
                Sistema.getInstancia().crearReclamoProducto(Integer.parseInt(String.valueOf(comboBoxClientes.getSelectedItem())), mapCodigoCantidad);
                JOptionPane.showMessageDialog(null, "Reclamo agregado correctamente");
                data.clear();
                setVisible(false);
            }
        });
        getContentPane().add(btnAceptar);
        
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
                data.clear();
                setVisible(false);
        	}
        });
        btnCancelar.setBounds(264, 184, 89, 23);
        getContentPane().add(btnCancelar);
    }
}
