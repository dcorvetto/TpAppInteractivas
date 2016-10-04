package vista;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Vector;

public class VistaReclamos extends JFrame {

    private JPanel contentPane;

    private Integer codigoUsuario;
    private JTable table;
    JScrollPane jScrollPane;

    public VistaReclamos(Integer codigoUsuario) {
        setTitle("Reclamos");
        this.codigoUsuario = codigoUsuario;

        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setBounds(100, 100, 425, 302);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        Vector<String> nombresColumnas = new Vector<>();
        nombresColumnas.add("Numero");
        nombresColumnas.add("Detalle");
        nombresColumnas.add("Estado");
        nombresColumnas.add("Fecha");

        Vector<String> data = new Vector<>();

        table = new JTable(data, nombresColumnas);
        table.setBounds(0, 20, 389, 185);
        table.setFillsViewportHeight(true);
        jScrollPane = new JScrollPane(table);
        jScrollPane.setBounds(10, 10, table.getWidth(), table.getHeight());
        contentPane.add(jScrollPane);

        JButton btnAgregar = new JButton("Agregar");
        btnAgregar.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnAgregar.setBounds(10, 216, 89, 23);
        contentPane.add(btnAgregar);
    }
}
