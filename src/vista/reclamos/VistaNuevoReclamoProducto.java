package vista.reclamos;

import controlador.Sistema;
import negocio.AlarmaNuevoReclamo;
import negocio.NuevoReclamoObs;
import negocio.reclamos.TipoReclamo;
import negocio.views.ClienteView;
import negocio.views.UsuarioView;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.Vector;

public class VistaNuevoReclamoProducto extends JFrame implements NuevoReclamoObs{

    private static final long serialVersionUID = -277076000511824707L;
    private Integer codigoUsuario;
    private JComboBox<String> comboBoxClientes;
    private JTextField textFieldCantidad;
    private JComboBox<String> comboBoxProductos;
    private JTable table;
    private TableModel model;
    private JTextArea textAreaDescripcion;
    private JButton btnEliminar;
    private JComboBox<String> comboBoxResp;

    private Vector<Vector<String>> data =  new Vector<>();
    private Vector<String> dataProducto = new Vector<>();
    

    public VistaNuevoReclamoProducto(Integer codigoUsuario) {
    	this.setBounds(0, 0, 381, 313);
    	setTitle("Reclamo Producto");
        this.codigoUsuario = codigoUsuario;
        getContentPane().setLayout(null);
        
        comboBoxClientes = new JComboBox<>();
        comboBoxClientes.setBounds(76, 13, 82, 20);
        for (ClienteView clienteView : Sistema.getInstancia().getClientes()) {
            comboBoxClientes.addItem(clienteView.getDni());
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
        

        /*Combo responsables */
        JLabel lblResponsable = new JLabel("Responsable:");
        lblResponsable.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblResponsable.setBounds(168, 14, 85, 14);
        getContentPane().add(lblResponsable);   
        
        comboBoxResp = new JComboBox<>();
        comboBoxResp.setBounds(263, 13, 90, 20);
        
        List<UsuarioView> lista = Sistema.getInstancia().getUsuariosResponsables(TipoReclamo.PRODUCTO.toString());
        for (UsuarioView usuarioView : lista) {
        	comboBoxResp.addItem(usuarioView.getUsuario());
        }
        getContentPane().add(comboBoxResp);
        
        /*Fin combo responsables*/
        
        JLabel lblCodigo = new JLabel("Codigo:");
        lblCodigo.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblCodigo.setBounds(10, 71, 56, 16);
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
            	boolean error = false;
                for(int i=0;i<table.getModel().getRowCount();i++){
                    if(table.getModel().getValueAt(i, 0) == String.valueOf(comboBoxProductos.getSelectedItem())){
                  	  JOptionPane.showMessageDialog(null, "Ya existe un item con ese producto");
                  	  error = true;
                    }
                  }
           	if(!error){
                dataProducto.add(String.valueOf(comboBoxProductos.getSelectedItem()));
                dataProducto.add(textFieldCantidad.getText());
                data.add(dataProducto);
                model = new DefaultTableModel(data, nombresColumnas);
                TableModelEvent tableModelEvent = new TableModelEvent(model);
                table.tableChanged(tableModelEvent);
                dataProducto = new Vector<>();
           	}
            }
        });
        btnAgregar.setBounds(312, 68, 41, 23);
        getContentPane().add(btnAgregar);

        model = new DefaultTableModel(data, nombresColumnas);
        table = new JTable(model);
        table.setFillsViewportHeight(true);
        table.setBounds(0, 20, 343, 78);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        for (int c = 0; c < table.getColumnCount(); c++)
        {
            Class<?> col_class = table.getColumnClass(c);
            table.setDefaultEditor(col_class, null);        // remove editor
        }
        JScrollPane jScrollPane = new JScrollPane(table);
        jScrollPane.setBounds(10, 151, table.getWidth(), table.getHeight());
        getContentPane().add(jScrollPane);
        ListSelectionModel listSelectionModel = table.getSelectionModel();
        listSelectionModel.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                ListSelectionModel lsm = (ListSelectionModel) e.getSource();
                btnEliminar.setEnabled(!lsm.isSelectionEmpty());
            }
        });

        JButton btnAceptar = new JButton("Aceptar");
        btnAceptar.setBounds(10, 240, 89, 23);
        btnAceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HashMap<Integer, Integer> mapCodigoCantidad = new HashMap<>();
                //Esta es la unica forma de pasar de el vector de vectores de string (que es lo que pide el JTable) a un map
                for (int i = 0; i < data.size(); i++) {
                    //elementAt(0)=codigo del producto, y elementAt(1) = cantidad
                	String[] parts = String.valueOf(data.elementAt(i).elementAt(0)).split("-");
                    String codigo = parts[0];
                    mapCodigoCantidad.put(Integer.valueOf(codigo), Integer.valueOf(data.elementAt(i).elementAt(1)));
                }
                Sistema.getInstancia().crearReclamoProducto(Integer.parseInt(String.valueOf(
                		comboBoxClientes.getSelectedItem())), mapCodigoCantidad, textAreaDescripcion.getText(),
                		String.valueOf(comboBoxResp.getSelectedItem()));
                JOptionPane.showMessageDialog(null, "Reclamo agregado correctamente");
                
                AlarmaNuevoReclamo alarma = new AlarmaNuevoReclamo();
                alarma.notifyObservers();
                
                data.clear();
                setVisible(false);
                
            }
        });
        getContentPane().add(btnAceptar);
        
        btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(120,240,120,23);
        btnEliminar.setEnabled(false);
        btnEliminar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	DefaultTableModel modelo = (DefaultTableModel)table.getModel(); 
            	modelo.removeRow(table.getSelectedRow()); 
            }
        });
        getContentPane().add(btnEliminar);
        
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
                data.clear();
                setVisible(false);
        	}
        });
        btnCancelar.setBounds(264, 240, 89, 23);
        getContentPane().add(btnCancelar);
        
        JLabel lblDescripcion = new JLabel("Descripcion:");
        lblDescripcion.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblDescripcion.setBounds(10, 98, 80, 20);
        getContentPane().add(lblDescripcion);
        
        textAreaDescripcion = new JTextArea();
        textAreaDescripcion.setBounds(0, 0, 253, 39);
        JScrollPane jScrollPaneDescripcion = new JScrollPane(textAreaDescripcion);
        jScrollPaneDescripcion.setBounds(100, 101, textAreaDescripcion.getWidth(), textAreaDescripcion.getHeight());
        getContentPane().add(jScrollPaneDescripcion);
    }


	@Override
	public void update() {
		// TODO Auto-generated method stub
		System.out.println("A refrescar!!!!");
	}
}
