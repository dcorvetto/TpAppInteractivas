package vista;

import controlador.Sistema;
import negocio.EnumRoles;
import vista.reclamos.*;
import vista.reportes.VistaRankingClientesReclamos;
import vista.reportes.VistaRankingTratamientoReclamos;
import vista.reportes.VistaReclamosPorMes;
import vista.reportes.VistaTiempoRespuesta;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;

public class VistaMenu extends JFrame {

    private static final long serialVersionUID = -7998003568770812583L;
    private JMenuBar jMenuBar1;
    private JMenuItem jMenuItem3;
    private JMenu jMenu1;
    private JMenu jMenu4;
    private JMenuItem jMenuItem1;
    private JMenu jMenu3;
    private JMenu jMenu2;
    private JMenu jMenuReportes;

    private Integer codigoUsuario;

    public VistaMenu(Integer codigoUsuario) {
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
        //Solo pueden crear reclamos los del call center y los admin
        if (roles.contains(EnumRoles.CALL_CENTER) || roles.contains(EnumRoles.ADMINISTRACION)) {
            jMenu4 = new JMenu();
            jMenu3.add(jMenu4);
            jMenu4.setText("Nuevo Reclamo");
            
            // Reclamo de producto: el cliente no recibe cierto producto y desea recibirlo. Se debe almacenar el producto y la cantidad pedida.
            JMenuItem jMenuReclamoProducto = new JMenuItem();
            jMenuReclamoProducto.setText("Producto");
            jMenuReclamoProducto.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JFrame nuevoReclamoProductoVista = new VistaNuevoReclamoProducto(codigoUsuario);
                    nuevoReclamoProductoVista.setVisible(true);
                }
            });
            jMenu4.add(jMenuReclamoProducto);
            
            // Reclamo de cantidades: el cliente solicita se le envie mayor cantidad de productos. El sistema debe registrar cada producto y su cantidad pedida.
            JMenuItem jMenuReclamoCantidades = new JMenuItem();
            jMenuReclamoCantidades.setText("Cantidades");
            jMenuReclamoCantidades.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {//igual que el de ReclamoProducto pero llamando al crearReclamoCantidades
                    JFrame nuevoReclamoCantidadesVista = new VistaNuevoReclamoCantidades(codigoUsuario);
                    nuevoReclamoCantidadesVista.setVisible(true);
                }
            });
            jMenu4.add(jMenuReclamoCantidades);
            
            // Reclamo de zona: el cliente ha detectado que un competidor esta entregando productos en su zona. Se debe informar la zona afectada.
            JMenuItem jMenuReclamoZona = new JMenuItem();
            jMenuReclamoZona.setText("Zona");
            jMenuReclamoZona.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	JFrame nuevoReclamoZonaVista = new VistaNuevoReclamoZona(codigoUsuario);
                    nuevoReclamoZonaVista.setVisible(true);
                }
            });
            jMenu4.add(jMenuReclamoZona);
            
            // Reclamo de facturacion: debe informar la fecha de factura en donde detecto inconsistencias y detallar la misma. Se pueden registrar varias facturas.
            JMenuItem jMenuReclamoFactura = new JMenuItem();
            jMenuReclamoFactura.setText("Factura");
            jMenuReclamoFactura.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	JFrame nuevoReclamoFacturaVista = new VistaNuevoReclamoFactura(codigoUsuario);
                    nuevoReclamoFacturaVista.setVisible(true);
                }
            });
            jMenu4.add(jMenuReclamoFactura);
            
            //	Reclamo de faltantes: el cliente recibio menos cantidad de la solicitada e informada.
            JMenuItem jMenuReclamoFaltantes = new JMenuItem();
            jMenuReclamoFaltantes.setText("Faltantes");
            jMenuReclamoFaltantes.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	JFrame nuevoReclamoFaltantesVista = new VistaNuevoReclamoFaltantes(codigoUsuario);
                    nuevoReclamoFaltantesVista.setVisible(true);
                }
            });
            jMenu4.add(jMenuReclamoFaltantes);

            //	Reclamo Compuesto
            JMenuItem jMenuReclamoCompuseto = new JMenuItem();
            jMenuReclamoCompuseto.setText("Compuesto");
            jMenuReclamoCompuseto.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JFrame nuevoReclamoCompuestoVista = new VistaNuevoReclamoCompuesto(codigoUsuario);
                    nuevoReclamoCompuestoVista.setVisible(true);
                }
            });
            jMenu4.add(jMenuReclamoCompuseto);
        }

        if(roles.contains(EnumRoles.ADMINISTRACION) || roles.contains(EnumRoles.CONSULTA)){
            jMenuReportes = new JMenu();
            jMenu2.add(jMenuReportes);
            jMenuReportes.setText("Reportes");

            JMenuItem jMenuRankingClientes = new JMenuItem();
            jMenuRankingClientes.setText("Ranking Clientes por reclamos");
            jMenuRankingClientes.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JFrame rankingClientesReclamos = new VistaRankingClientesReclamos();
                    rankingClientesReclamos.setVisible(true);
                }
            });
            jMenuReportes.add(jMenuRankingClientes);

            JMenuItem jMenuCantidadReclamosPorMes = new JMenuItem();
            jMenuCantidadReclamosPorMes.setText("Cantidad de reclamos por mes");
            jMenuCantidadReclamosPorMes.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JFrame cantidadReclamosPorMes = new VistaReclamosPorMes();
                    cantidadReclamosPorMes.setVisible(true);
                }
            });
            jMenuReportes.add(jMenuCantidadReclamosPorMes);

            JMenuItem jMenuRankingTratamiento = new JMenuItem();
            jMenuRankingTratamiento.setText("Ranking tratamiento de reclamos");
            jMenuRankingTratamiento.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JFrame rankingTratamiento = new VistaRankingTratamientoReclamos();
                    rankingTratamiento.setVisible(true);
                }
            });
            jMenuReportes.add(jMenuRankingTratamiento);

            JMenuItem jMenuTiempoPorResponsable = new JMenuItem();
            jMenuTiempoPorResponsable.setText("Tiempo de respuesta por responsable");
            jMenuTiempoPorResponsable.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JFrame tiempoPorResponsable = new VistaTiempoRespuesta();
                    tiempoPorResponsable.setVisible(true);
                }
            });
            jMenuReportes.add(jMenuTiempoPorResponsable);
        }

        jMenuItem1 = new JMenuItem();
        jMenu3.add(jMenuItem1);
        jMenuItem1.setText("Ver Reclamos");
        jMenuItem1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                JFrame vistaReclamos = new VistaReclamos(codigoUsuario);
                vistaReclamos.setVisible(true);
            }
        });
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
