package vista;


import controlador.Sistema;
import negocio.views.ReclamoView;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
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

        //Se pasan valores de la collection de reclamos a vector porque la tabla solo acepta Vector :(
        Vector<String> data = new Vector<>();
        for (ReclamoView reclamoView : Sistema.getInstancia().getReclamosParaUsuario(codigoUsuario)) {
            data.add(String.valueOf(reclamoView.getNumero()));
            data.add(String.valueOf(reclamoView.getTipoReclamo()));
            data.add(String.valueOf(reclamoView.isEstaSolucionado()));
            data.add(String.valueOf(reclamoView.getDescripcion()));
        }

        table = new JTable(data, nombresColumnas);
        table.setBounds(0, 20, 389, 185);
        table.setFillsViewportHeight(true);
        jScrollPane = new JScrollPane(table);
        jScrollPane.setBounds(10, 10, table.getWidth(), table.getHeight());
        contentPane.add(jScrollPane);

//        JButton btnAgregar = new JButton("Agregar");
//        btnAgregar.setFont(new Font("Tahoma", Font.PLAIN, 14));
//        btnAgregar.setBounds(10, 216, 89, 23);
//        contentPane.add(btnAgregar);
    }
}
