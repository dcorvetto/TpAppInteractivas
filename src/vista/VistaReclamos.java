package vista;


import controlador.Sistema;
import ejemploObservador.usuarioObservador;
import negocio.Observer;
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

public class VistaReclamos extends JFrame implements Observable{

	private static final long serialVersionUID = 6972042471104413480L;

	private JPanel contentPane;
    private Integer codigoUsuario;
    private JTable table;
    private JScrollPane jScrollPane;
    private JButton btnVerEventos;
    
    private ArrayList<Observer> observadores = new ArrayList<Observer>();
    
    
    public VistaReclamos(Integer codigoUsuario) {
        this.codigoUsuario = codigoUsuario;

        setTitle("Reclamos");
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setBounds(100, 100, 425, 281);
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

        //Se pasan valores de la collection de reclamos a vectores porque la tabla solo acepta Vectores de vectores... :@
        Vector<String> dataReclamo = new Vector<>();
        Vector<Vector<String>> data = new Vector<>();
        Collection<ReclamoView> reclamosParaUsuario = Sistema.getInstancia().getReclamosParaUsuario(codigoUsuario);
		//Notifico a mis Observadores que voy a relistar los Reclamos
        notifyObserver();
        
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
        jScrollPane.setBounds(10, 10, table.getWidth(), table.getHeight());
        contentPane.add(jScrollPane);

        JButton btnAceptar = new JButton("Aceptar");
        btnAceptar.setBounds(10, 208, 89, 23);
        btnAceptar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
        contentPane.add(btnAceptar);

        btnVerEventos = new JButton("Ver eventos");
        btnVerEventos.setBounds(142, 208, 120, 23);
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
        		data.clear();
        		setVisible(false);
        	}
        });
        btnCancelar.setBounds(310, 208, 89, 23);
        contentPane.add(btnCancelar);
    }

	@Override
	public void addObserver(Observer o) {
		// TODO Auto-generated method stub
		observadores.add(o);
	}

	@Override
	public void removeObserver(Observer o) {
		// TODO Auto-generated method stub
		observadores.remove(o);
	}

	@Override
	public void notifyObserver() {
		for (Observer observador :observadores){
			observador.update();
		
		}
	
	}
    
    
}
