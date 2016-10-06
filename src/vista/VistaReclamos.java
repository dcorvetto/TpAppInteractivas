package vista;


import controlador.Sistema;
import negocio.views.ReclamoView;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Vector;

public class VistaReclamos extends JFrame {

    private JPanel contentPane;

    private Integer codigoUsuario;
    private JTable table;
    private JScrollPane jScrollPane;

    public VistaReclamos(Integer codigoUsuario) {
        this.codigoUsuario = codigoUsuario;

        setTitle("Reclamos");
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setBounds(100, 100, 425, 302);
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
        Vector<String> dataNumero = new Vector<>();
        Vector<String> dataTipoReclamo = new Vector<>();
        Vector<String> dataEstaSolucionado = new Vector<>();
        Vector<String> dataDescripcion = new Vector<>();
        for (ReclamoView reclamoView : Sistema.getInstancia().getReclamosParaUsuario(codigoUsuario)) {
            dataNumero.add(String.valueOf(reclamoView.getNumero()));
            dataTipoReclamo.add(String.valueOf(reclamoView.getTipoReclamo()));
            dataEstaSolucionado.add(String.valueOf(reclamoView.isEstaSolucionado()));
            dataDescripcion.add(String.valueOf(reclamoView.getDescripcion()));
        }

        Vector<Vector<String>> data = new Vector<>();
        data.add(dataNumero);
        data.add(dataTipoReclamo);
        data.add(dataEstaSolucionado);
        data.add(dataDescripcion);

        TableModel model = new DefaultTableModel(data, nombresColumnas);

        table = new JTable(model);
        table.setBounds(0, 20, 389, 185);
        table.setFillsViewportHeight(true);
        jScrollPane = new JScrollPane(table);
        jScrollPane.setBounds(10, 10, table.getWidth(), table.getHeight());
        contentPane.add(jScrollPane);

        JButton btnAceptar = new JButton("Aceptar");
        btnAceptar.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnAceptar.setBounds(10, 216, 89, 23);
        btnAceptar.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
        contentPane.add(btnAceptar);
    }
}
