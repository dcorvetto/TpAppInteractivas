package vista;


import controlador.Sistema;
import negocio.NuevoReclamoObs;
import negocio.Reclamo;
import negocio.views.ReclamoView;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

public class VistaReclamos extends JFrame implements NuevoReclamoObs{


	
	private static final long serialVersionUID = 6972042471104413480L;
	private static VistaReclamos instancia;

	private JPanel contentPane;
    private Integer codigoUsuario;
    private JTable table;
    private JScrollPane jScrollPane;
    private JButton btnVerEventos;
    private JLabel lbAlerta;
    private Vector<String> dataReclamo = new Vector<>();
    private Vector<Vector<String>> data = new Vector<>();
   
    
    
    
    private VistaReclamos(Integer codigoUsuario) {
    	
        this.codigoUsuario = codigoUsuario;

        setTitle("Reclamos");
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setBounds(100, 100, 425, 320);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        
      

        //Columnas de la tabla
        Vector<String> nombresColumnas = new Vector<>();
        nombresColumnas.add("Numero");
        nombresColumnas.add("Tipo de reclamo");
        nombresColumnas.add("Solucionado");
        nombresColumnas.add("Descripcion");
        
        lbAlerta = new JLabel("Ultimo Reclamo creado :" + Sistema.getInstancia().getUltimoReclamo().getDescripcion());
        lbAlerta.setBounds(10,5,400,20);
        contentPane.add(lbAlerta);

        //Se pasan valores de la collection de reclamos a vectores porque la tabla solo acepta Vectores de vectores... :@
       
        Collection<ReclamoView> reclamosParaUsuario = Sistema.getInstancia().getReclamosParaUsuario(codigoUsuario);
	  
        for (ReclamoView reclamoView : reclamosParaUsuario) {
            dataReclamo.add(String.valueOf(reclamoView.getNumero()));
            dataReclamo.add(String.valueOf(reclamoView.getTipoReclamo()));
            String resp = "";
            if(reclamoView.isEstaSolucionado()){
            	resp = "Si";
            }
            else{
            	resp="No";
            }
            	
            dataReclamo.add(resp);
            dataReclamo.add(String.valueOf(reclamoView.getDescripcion()));
            data.add(dataReclamo);
            dataReclamo = new Vector<>();
        }

		TableModel model = new DefaultTableModel(data, nombresColumnas);

        table = new JTable(model);
        table.setBounds(0, 20, 389, 185);
        table.setFillsViewportHeight(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        for (int c = 0; c < table.getColumnCount(); c++)
        {
            Class<?> col_class = table.getColumnClass(c);
            table.setDefaultEditor(col_class, null);        // remove editor
        }
        
        ListSelectionModel listSelectionModel = table.getSelectionModel();
        listSelectionModel.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                ListSelectionModel lsm = (ListSelectionModel) e.getSource();
                btnVerEventos.setEnabled(!lsm.isSelectionEmpty());
            }
        });
        jScrollPane = new JScrollPane(table);
        jScrollPane.setBounds(10, 50, table.getWidth(), table.getHeight());
        contentPane.add(jScrollPane);

        JButton btnAceptar = new JButton("Aceptar");
        btnAceptar.setBounds(10, 258, 89, 23);
        btnAceptar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
        contentPane.add(btnAceptar);

        btnVerEventos = new JButton("Ver eventos");
        btnVerEventos.setBounds(142, 258, 120, 23);
        btnVerEventos.setEnabled(false);
        btnVerEventos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	int numeroReclamoElegido = Integer.parseInt(data.get(table.getSelectedRow()).get(0)); //Obtengo el numero del reclamo seleccionado en la tabla
            	JFrame vistaEventoReclamo = new VistaEventoReclamo(numeroReclamoElegido, Sistema.getInstancia().puedeCrearEventos(codigoUsuario), codigoUsuario);
            	setVisible(false);
            	vistaEventoReclamo.setVisible(true);
            }
        });
        contentPane.add(btnVerEventos);
        
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
         		setVisible(false);
        	}
        });
        btnCancelar.setBounds(310, 258, 89, 23);
        contentPane.add(btnCancelar);
    }

    public static VistaReclamos getInstancia(Integer codigoUsuario){
    	if(instancia==null){
    		instancia = new VistaReclamos(codigoUsuario);
    	}
    	else{
    		instancia.actualizarTabla(instancia.dataReclamo, instancia.data, Sistema.getInstancia().getUltimoReclamo());
    	}
    	return instancia;
    }
    
	@Override
	public void update(Reclamo r) {
		System.out.println("A refrescar la pantalla!!!!");
		
		actualizarTabla(dataReclamo, data, r);
        this.repaint();
        this.revalidate();
     //   this.setVisible(false);
      // this.setVisible(true);

	}	

	public void actualizarTabla(Vector<String> dataReclamo, Vector<Vector<String>> data, Reclamo r){
		lbAlerta.setText("Ultimo Reclamo creado :" + r.getDescripcion());
        Collection<ReclamoView> reclamosParaUsuario = Sistema.getInstancia().getReclamosParaUsuario(codigoUsuario);
  	  
        for (ReclamoView reclamoView : reclamosParaUsuario) {
            dataReclamo.add(String.valueOf(reclamoView.getNumero()));
            dataReclamo.add(String.valueOf(reclamoView.getTipoReclamo()));
            String resp = "";
            if(reclamoView.isEstaSolucionado()){
            	resp = "Si";
            }
            else{
            	resp="No";
            }
            	
            dataReclamo.add(resp);
            dataReclamo.add(String.valueOf(reclamoView.getDescripcion()));
            data.add(dataReclamo);
            dataReclamo = new Vector<>();
        }
	}
    
}
