package vista.reclamos;

import controlador.Sistema;
import negocio.Cliente;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class VistaNuevoReclamoFactura extends JFrame {

	private static final long serialVersionUID = -7732676766832254025L;
    private Integer codigoUsuario;
    private JTextField textFieldCodFactura;
    private JComboBox<String> comboBoxResp;
    private JTextField textFieldFecha;
    private JComboBox<String> comboBoxClientes;
    private JComboBox<String> comboBoxFacturas;
    private JTextArea textAreaDescripcion;
    private TableModel model;
    private JTable tableFacturas;
    private JButton btnEliminar;

    private Vector<String> dataFacturas = new Vector<>();
    private Vector<Vector<String>> data =  new Vector<>();


    public VistaNuevoReclamoFactura(Integer codigoUsuario) {
    	this.setBounds(0, 0, 341, 314);
    	setTitle("Reclamo Factura");
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
        lblResponsable.setBounds(178, 14, 56, 14);
        getContentPane().add(lblResponsable);   
        
        comboBoxResp = new JComboBox<>();
        comboBoxResp.setBounds(250, 14, 90, 20);
        
        List<UsuarioView> lista = Sistema.getInstancia().getUsuariosResponsables(TipoReclamo.CANTIDAD.toString());
        for (UsuarioView usuarioView : lista) {
        	comboBoxResp.addItem(usuarioView.getUsuario());
        }
        getContentPane().add(comboBoxResp);
        
        /*Fin combo responsables*/
        
        JButton btnAceptar = new JButton("Aceptar");
        btnAceptar.setBounds(10, 241, 89, 23);
        btnAceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    Map<Integer, Date> mapFechaId = new HashMap<>();
                    //Esta es la unica forma de pasar de el vector de vectores de string (que es lo que pide el JTable) a un map
                    for (int i = 0; i < data.size(); i++) {
                        //elementAt(0)=codigo del factura, y elementAt(1) = fecha
                        Date date = formatter.parse(data.elementAt(i).elementAt(1));
                        mapFechaId.put(Integer.valueOf(data.elementAt(i).elementAt(0)), date);
                    }
                    
                    int idFactura = Integer.parseInt(String.valueOf(comboBoxFacturas.getSelectedItem()));
                    int dniCliente = Integer.parseInt(String.valueOf(comboBoxClientes.getSelectedItem()));
                    int idCliente = Sistema.getInstancia().buscarClientePorDni(dniCliente);
                    if(Sistema.getInstancia().getClienteDeFactura(idFactura) != idCliente){
                    	JOptionPane.showMessageDialog(null, "La Factura elegida no corresponde al Cliente seleccionado");
                    }
                    else{
	                    Sistema.getInstancia().crearReclamoFactura(Integer.parseInt(String.valueOf(comboBoxClientes.getSelectedItem())), textAreaDescripcion.getText(), mapFechaId
	                    		,String.valueOf(comboBoxResp.getSelectedItem()));
	                    JOptionPane.showMessageDialog(null, "Reclamo agregado correctamente");
	                    //textFieldCodFactura.setText("");
	                    textFieldFecha.setText("");
	                    setVisible(false);
                    }
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
                data.clear();
               // textFieldCodFactura.setText("");
                textFieldFecha.setText("");
                setVisible(false);
        	}
        });
        btnCancelar.setBounds(227, 241, 89, 23);
        getContentPane().add(btnCancelar);
        
        JLabel lblIDFactura = new JLabel("ID Factura:");
        lblIDFactura.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblIDFactura.setBounds(10, 74, 71, 20);
        getContentPane().add(lblIDFactura);
        
        comboBoxFacturas = new JComboBox<>();
        comboBoxFacturas.setBounds(91, 76, 46, 20);
        int idCliente = Integer.parseInt(String.valueOf(comboBoxClientes.getSelectedItem()));
        for (String codigoFactura : Sistema.getInstancia().getCodigoFactura()) {
            comboBoxFacturas.addItem(codigoFactura);
        }
        getContentPane().add(comboBoxFacturas);
        /*textFieldCodFactura = new JTextField();
        textFieldCodFactura.setBounds(91, 76, 46, 20);
        getContentPane().add(textFieldCodFactura);
        textFieldCodFactura.setColumns(10);*/
        
        JLabel lblFecha = new JLabel("Fecha:");
        lblFecha.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblFecha.setBounds(147, 74, 41, 22);
        getContentPane().add(lblFecha);
        
        textFieldFecha = new JTextField();
        textFieldFecha.setColumns(10);
        textFieldFecha.setBounds(198, 77, 67, 20);
        textFieldFecha.setText("dd/MM/yyyy");
        getContentPane().add(textFieldFecha);
        
        JLabel lblDescripcion = new JLabel("Descripcion:");
        lblDescripcion.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblDescripcion.setBounds(10, 186, 75, 14);
        getContentPane().add(lblDescripcion);
        
        JScrollPane scrollPaneDescripcion = new JScrollPane();
        scrollPaneDescripcion.setBounds(95, 186, 221, 44);
        getContentPane().add(scrollPaneDescripcion);
        
        textAreaDescripcion = new JTextArea();
        scrollPaneDescripcion.setViewportView(textAreaDescripcion);
        
        JLabel lblNewLabel = new JLabel("Facturas:");
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblNewLabel.setBounds(10, 49, 67, 14);
        getContentPane().add(lblNewLabel);

        //Columnas de la tabla
        Vector<String> nombresColumnas = new Vector<>();
        nombresColumnas.add("ID");
        nombresColumnas.add("Fecha");
        
        JButton btnAgregar = new JButton("+");
        btnAgregar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	try{
	            	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	            	Date date = formatter.parse(textFieldFecha.getText());
	                
	            	boolean error = false;
	                for(int i=0;i<tableFacturas.getModel().getRowCount();i++){
	                    if(tableFacturas.getModel().getValueAt(i, 0) == String.valueOf(comboBoxFacturas.getSelectedItem())){
	                  	  JOptionPane.showMessageDialog(null, "Ya existe un item con esa factura");
	                  	  error = true;
	                    }
	                  }
	                if(!error){
		            	dataFacturas.add(String.valueOf(comboBoxFacturas.getSelectedItem()));
		                dataFacturas.add(textFieldFecha.getText());
		                data.add(dataFacturas);
		                dataFacturas = new Vector<>();
		                model = new DefaultTableModel(data, nombresColumnas);
		                TableModelEvent tableModelEvent = new TableModelEvent(model);
		                tableFacturas.tableChanged(tableModelEvent);
	                }
            	 } catch (ParseException e1) {
                     e1.printStackTrace();
                     JOptionPane.showMessageDialog(null, "Formato de fecha incorrecto, debe ser dd/MM/yyyy");
                 }
            }
        });
        btnAgregar.setBounds(275, 75, 41, 23);
        getContentPane().add(btnAgregar);
        
        JScrollPane scrollPaneFacturas = new JScrollPane();
        scrollPaneFacturas.setBounds(10, 105, 306, 70);
        getContentPane().add(scrollPaneFacturas);
        
        btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(120, 241, 89, 23);
        btnEliminar.setEnabled(false);
        btnEliminar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	DefaultTableModel modelo = (DefaultTableModel)tableFacturas.getModel(); 
            	modelo.removeRow(tableFacturas.getSelectedRow()); 
            }
        });
        getContentPane().add(btnEliminar);
        

        model = new DefaultTableModel(data, nombresColumnas);
        tableFacturas = new JTable(model);
        tableFacturas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        for (int c = 0; c < tableFacturas.getColumnCount(); c++)
        {
            Class<?> col_class = tableFacturas.getColumnClass(c);
            tableFacturas.setDefaultEditor(col_class, null);        // remove editor
        }
        scrollPaneFacturas.setViewportView(tableFacturas);
        ListSelectionModel listSelectionModel = tableFacturas.getSelectionModel();
        listSelectionModel.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                ListSelectionModel lsm = (ListSelectionModel) e.getSource();
                btnEliminar.setEnabled(!lsm.isSelectionEmpty());
            }
        });

    }
}
