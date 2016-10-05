package vista;

import controlador.Sistema;
import negocio.EnumRoles;
import vista.reclamos.NuevoReclamoProductoVista;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;

public class Menu extends JFrame {

    private static final long serialVersionUID = -7998003568770812583L;
    private JMenuBar jMenuBar1;
    private JMenuItem jMenuItem3;
    private JMenu jMenu1;
    private JMenuItem jMenuItem2;
    private JMenuItem jMenuItem1;
    private JMenu jMenu3;
    private JMenu jMenu2;

    private Integer codigoUsuario;

    public Menu(Integer codigoUsuario) {
        super();
        this.codigoUsuario = codigoUsuario;
        setResizable(false);
        initGUI();
    }

    private void initGUI() {
        Collection<EnumRoles> roles = Sistema.getInstancia().rolesUsuario(codigoUsuario);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);
        this.setBounds(100, 100, 500, 500);
        this.setTitle("Sistema de Administracion de Reclamos");
        jMenuBar1 = new JMenuBar();
        setJMenuBar(jMenuBar1);
        jMenu2 = new JMenu();
        jMenuBar1.add(jMenu2);
        jMenu2.setText("Administracion");
        jMenu3 = new JMenu();
        jMenu2.add(jMenu3);
        jMenu3.setText("Reclamos");
        jMenu3.setBounds(39, 0, 57, 23);
        jMenuItem1 = new JMenuItem();
        if (roles.contains(EnumRoles.CALL_CENTER) || roles.contains(EnumRoles.ADMINISTRACION)) {
            jMenuItem2 = new JMenuItem();
            jMenu3.add(jMenuItem2);
            jMenuItem2.setText("Nuevo Reclamo");

            JMenu jMenuReclamoProducto = new JMenu(); // ReclamoProducto(int codigo_cliente, Map<Integer, Integer> mapCodigoCantidad) { // Map<codigo_producto,cantidad>
            jMenuReclamoProducto.setText("Producto");
            jMenuReclamoProducto.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JFrame nuevoReclamoProductoVista = new NuevoReclamoProductoVista(codigoUsuario);
                    nuevoReclamoProductoVista.setVisible(true);
                }
            });
            jMenu3.add(jMenuReclamoProducto);
            JMenu jMenuReclamoCantidades = new JMenu(); // ReclamoCantidades(int codigo_cliente, Map<Integer, Integer> mapCodigoCantidad) { // Map<codigo_producto,cantidad>
            jMenuReclamoCantidades.setText("Cantidades");
            jMenuReclamoCantidades.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                }
            });
            jMenu3.add(jMenuReclamoCantidades);
            JMenu jMenuReclamoZona = new JMenu(); // ReclamoZona(int codigo_cliente, String zona) {
            jMenuReclamoZona.setText("Zona");
            jMenuReclamoZona.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                }
            });
            jMenu3.add(jMenuReclamoZona);
            JMenu jMenuReclamoFactura = new JMenu(); // ReclamoFactura(int codigo_cliente, Date fecha, int cod_factura) {
            jMenuReclamoFactura.setText("Factura");
            jMenuReclamoFactura.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                }
            });
            jMenu3.add(jMenuReclamoFactura);
            JMenu jMenuReclamoFaltantes = new JMenu(); // ReclamoFaltantes(int codigo_cliente, int cod_producto, int cant_socilitada, int cant_recibidad) {
            jMenuReclamoFaltantes.setText("Faltantes");
            jMenuReclamoFaltantes.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                }
            });
            jMenu3.add(jMenuReclamoFaltantes);
        }
        jMenu3.add(jMenuItem1);
        jMenuItem1.setText("Ver Reclamos");
        jMenuItem1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                JFrame vistaReclamos = new VistaReclamos(codigoUsuario);
                vistaReclamos.setVisible(true);
            }
        });
//		jMenuItem2 = new JMenuItem();
//		jMenu3.add(jMenuItem2);
//		jMenuItem2.setText("Tratamiento reclamos");
//		jMenuItem2.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent evt) 
//			{
//			}
//		});
        jMenu1 = new JMenu();
        jMenuBar1.add(jMenu1);
        jMenu1.setText("Salir del Sistema");
        jMenuItem3 = new JMenuItem();
        jMenu1.add(jMenuItem3);
        jMenuItem3.setText("Salir");
        jMenuItem3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                System.exit(0);
            }
        });
        pack();
        this.setSize(371, 235);
    }

}
