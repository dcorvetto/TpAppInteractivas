package vista;

import controlador.Sistema;
import negocio.EnumEstado;
import negocio.views.EventoReclamoView;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Vector;

public class VistaEventoReclamo extends JFrame{
	private static final long serialVersionUID = -3565932713437507455L;
    private JTable table;
    private TableModel model;
    private JComboBox<String> comboBoxEstado;
    private JTextField textFieldFecha;
    private JTextArea textAreaDetalle;

    private Vector<String> dataEventos = new Vector<>();
    private Vector<Vector<String>> data =  new Vector<>();

    private Collection<EventoReclamoView> eventosNuevos = new ArrayList<>();

    public VistaEventoReclamo(Integer codigoReclamo, boolean puedeAgregarEventos, Integer codigoUsuario) {

        this.setBounds(0, 0, 381, 256);
        setTitle("Eventos");
        getContentPane().setLayout(null);

        //Columnas de la tabla
        Vector<String> nombresColumnas = new Vector<>();
        nombresColumnas.add("Estado");
        nombresColumnas.add("Fecha");
        nombresColumnas.add("Detalle");

        for (EventoReclamoView eventoReclamoView : Sistema.getInstancia().getTratamientoReclamo(codigoReclamo)) {
            dataEventos.add(String.valueOf(eventoReclamoView.getEstado()));
            dataEventos.add(String.valueOf(eventoReclamoView.getFecha()));
            dataEventos.add(String.valueOf(eventoReclamoView.getDetalle()));
            data.add(dataEventos);
            dataEventos = new Vector<>();
        }

        TableModel model = new DefaultTableModel(data, nombresColumnas);
        table = new JTable(model);
        table.setFillsViewportHeight(true);
        table.setBounds(0, 20, 343, 166);
        JScrollPane jScrollPane = new JScrollPane(table);
        jScrollPane.setBounds(10, 91, 343, 86);
        getContentPane().add(jScrollPane);

        JButton btnAceptar = new JButton("Aceptar");
        btnAceptar.setBounds(10, 184, 89, 23);
        btnAceptar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                for (EventoReclamoView eventosNuevo : eventosNuevos) {
                    Sistema.getInstancia().actualizarReclamo(eventosNuevo.getFecha(), eventosNuevo.getEstado(), codigoReclamo, eventosNuevo.getDetalle(), codigoUsuario);
                }
                JOptionPane.showMessageDialog(null, "Eventos agregados correctamente");
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
        
        JButton btnAgregar = new JButton("Agregar");
        btnAgregar.setBounds(138, 184, 89, 23);
        btnAgregar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String estado = String.valueOf(comboBoxEstado.getSelectedItem());
                    String fecha = textFieldFecha.getText();
                    String detalle = textAreaDetalle.getText();
                    
                    boolean error = false;
                    
                    for(int i=0;i<table.getModel().getRowCount();i++){
                      if(table.getModel().getValueAt(i, 0) == estado){
                    	  JOptionPane.showMessageDialog(null, "Ya existe un evento con ese estado");
                    	  error = true;
                      }
                    }
                    if(!error){
	                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	                    Date date = formatter.parse(textFieldFecha.getText());
	                    EventoReclamoView reclamoView = new EventoReclamoView(estado, date, detalle);
	                    eventosNuevos.add(reclamoView);
	
	                    dataEventos.add(estado);
	                    dataEventos.add(fecha);
	                    dataEventos.add(detalle);
	
	                    data.add(dataEventos);
	                    TableModel model = new DefaultTableModel(data, nombresColumnas);
	                    TableModelEvent tableModelEvent = new TableModelEvent(model);
	                    table.tableChanged(tableModelEvent);
	                    dataEventos = new Vector<>();
                    }
                } catch (ParseException e1) {
                    JOptionPane.showMessageDialog(null, "Formato de fecha incorrecto, debe ser dd/MM/yyyy");
                }
            }
        });
        getContentPane().add(btnAgregar);

        if(!puedeAgregarEventos){ //Deshabilito botones de aceptar y agregar eventos para quienes no posean el rol correspondiente
            btnAceptar.setEnabled(false);
            btnAgregar.setEnabled(false);
        }
        
        JLabel lblEstado = new JLabel("Estado:");
        lblEstado.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblEstado.setBounds(10, 11, 52, 14);
        getContentPane().add(lblEstado);
        
        comboBoxEstado = new JComboBox<>();
        comboBoxEstado.addItem(EnumEstado.INGRESADO.getTexto());
        comboBoxEstado.addItem(EnumEstado.EN_TRATAMIENTO.getTexto());
        comboBoxEstado.addItem(EnumEstado.CERRADO.getTexto());
        comboBoxEstado.addItem(EnumEstado.SOLUCIONADO.getTexto());
        comboBoxEstado.setBounds(72, 10, 109, 20);
        getContentPane().add(comboBoxEstado);
        
        JLabel lblFecha = new JLabel("Fecha:");
        lblFecha.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblFecha.setBounds(207, 11, 47, 14);
        getContentPane().add(lblFecha);
        
        textFieldFecha = new JTextField();
        textFieldFecha.setBounds(264, 10, 89, 20);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        
        textFieldFecha.setText(formatter.format(date));
        textFieldFecha.setColumns(10);
        getContentPane().add(textFieldFecha);

        JLabel lblDetalle = new JLabel("Detalle:");
        lblDetalle.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblDetalle.setBounds(10, 36, 52, 14);
        getContentPane().add(lblDetalle);
        
        textAreaDetalle = new JTextArea();
        textAreaDetalle.setBounds(0, 0, 281, 39);
        JScrollPane jScrollPaneDetalle = new JScrollPane(textAreaDetalle);
        jScrollPaneDetalle.setBounds(72, 41, textAreaDetalle.getWidth(), textAreaDetalle.getHeight());
        getContentPane().add(jScrollPaneDetalle);
    }
}
