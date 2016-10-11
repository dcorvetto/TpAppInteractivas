package vista.reclamos;

import controlador.Sistema;
import negocio.views.ClienteView;
import negocio.views.ReclamoView;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;

public class VistaNuevoReclamoCompuesto extends JFrame {
	private JComboBox<String> comboBoxClientes;
	private JComboBox<Integer> comboBoxReclamos;
	private JTable tableReclamos;
	private TableModel model;

	private Integer codigoUsuario;

	private Collection<ReclamoView> reclamosSimples;

	private Vector<String> dataReclamos = new Vector<>();
	private Vector<Vector<String>> data =  new Vector<>();

	public VistaNuevoReclamoCompuesto(Integer codigoUsuario) {
		this.setBounds(0, 0, 341, 250);
		setTitle("Reclamo Compuesto");
		this.codigoUsuario = codigoUsuario;
		getContentPane().setLayout(null);
		
		JLabel lblCliente = new JLabel("Cliente:");
		lblCliente.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCliente.setBounds(10, 11, 46, 14);
		getContentPane().add(lblCliente);
		
		comboBoxClientes = new JComboBox<>();
		comboBoxClientes.setBounds(66, 10, 87, 20);
		for (ClienteView clienteView : Sistema.getInstancia().getClientes()) {
			comboBoxClientes.addItem(clienteView.getDni());
		}
		getContentPane().add(comboBoxClientes);
		
		JLabel lblReclamos = new JLabel("Reclamo ID:");
		lblReclamos.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblReclamos.setBounds(10, 42, 75, 14);
		getContentPane().add(lblReclamos);
		
		comboBoxReclamos = new JComboBox<>();
		comboBoxReclamos.setBounds(95, 41, 87, 20);
		reclamosSimples = Sistema.getInstancia().getReclamosSimples();
		for (ReclamoView reclamoView : reclamosSimples) {
			comboBoxReclamos.addItem(reclamoView.getNumero());
		}
		getContentPane().add(comboBoxReclamos);

		//Columnas de la tabla
		Vector<String> nombresColumnas = new Vector<>();
		nombresColumnas.add("ID");
		nombresColumnas.add("Tipo Reclamo");
		nombresColumnas.add("Descripcion");
		
		JButton btnAgregar = new JButton("Agregar");
		btnAgregar.setBounds(226, 40, 89, 23);
		btnAgregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Integer selectedItem = (Integer) comboBoxReclamos.getSelectedItem();
				dataReclamos.add(String.valueOf(selectedItem));
				ReclamoView reclamoView = new ReclamoView();
				for (ReclamoView reclamoSimple : reclamosSimples) {
					if(reclamoSimple.getNumero()==selectedItem){
						reclamoView = reclamoSimple;
					}
				}
				dataReclamos.add(reclamoView.getTipoReclamo());
				dataReclamos.add(reclamoView.getDescripcion());
				data.add(dataReclamos);
				dataReclamos = new Vector<>();
				model = new DefaultTableModel(data, nombresColumnas);
				TableModelEvent tableModelEvent = new TableModelEvent(model);
				tableReclamos.tableChanged(tableModelEvent);
			}
		});
		getContentPane().add(btnAgregar);
		
		JScrollPane scrollPaneReclamos = new JScrollPane();
		scrollPaneReclamos.setBounds(10, 72, 305, 95);
		getContentPane().add(scrollPaneReclamos);

		model = new DefaultTableModel(data, nombresColumnas);
		tableReclamos = new JTable(model);
		scrollPaneReclamos.setViewportView(tableReclamos);
		
		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.setBounds(10, 177, 89, 23);
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<Integer> idsReclamos = new ArrayList<>();
				for (int i = 0; i < data.size(); i++) {
					idsReclamos.add(Integer.valueOf(data.elementAt(i).elementAt(0))); //elementAt(0) = id de reclamo
				}
				if(idsReclamos.size()>1){
					Sistema.getInstancia().crearReclamoCompuesto(Integer.parseInt((String) comboBoxClientes.getSelectedItem()), idsReclamos);
					JOptionPane.showMessageDialog(null, "Reclamo agregado correctamente");
					dataReclamos.clear();
					data.clear();
					setVisible(false);
				}else{
					JOptionPane.showMessageDialog(null, "Error: Debe agregar por lo menos 2 reclamos");
				}
			}
		});
		getContentPane().add(btnAceptar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(226, 177, 89, 23);
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dataReclamos.clear();
				data.clear();
				setVisible(false);
			}
		});
		getContentPane().add(btnCancelar);
	}
}
