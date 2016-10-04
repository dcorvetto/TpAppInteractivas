package vista;

public class Menu extends javax.swing.JFrame {
	
	private static final long serialVersionUID = -7998003568770812583L;
	private JMenuBar jMenuBar1;
	private JMenuItem jMenuItem4;
	private JMenu jMenu1;
	private JMenuItem jMenuItem3;
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
		
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);
		this.setBounds(100, 100, 500, 500);
		this.setTitle("Sistema de Administracion de Reclamos");
		{
			jMenuBar1 = new JMenuBar();
			setJMenuBar(jMenuBar1);
			{
				jMenu2 = new JMenu();
				jMenuBar1.add(jMenu2);
				jMenu2.setText("Administracion");
				{
					jMenu3 = new JMenu();
					jMenu2.add(jMenu3);
					jMenu3.setText("Reclamos");
					jMenu3.setBounds(39, 0, 57, 23);
					{
						jMenuItem1 = new JMenuItem();
						jMenu3.add(jMenuItem1);
						jMenuItem1.setText("Alta Reclamo");
						jMenuItem1.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) 
							{
								/*AltaAfiliado.getInstancia().setLocationRelativeTo(null);
								AltaAfiliado.getInstancia().setVisible(true);*/
							}
						});
					}
					{
						jMenuItem2 = new JMenuItem();
						jMenu3.add(jMenuItem2);
						jMenuItem2.setText("Modificar Reclamo");
						jMenuItem2.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) 
							{
								System.out.println("jMenuItem2.actionPerformed, event="+evt);
								//TODO add your code for jMenuItem2.actionPerformed
							}
						});
					}
					{
						jMenuItem3 = new JMenuItem();
						jMenu3.add(jMenuItem3);
						jMenuItem3.setText("Baja Reclamo");
						jMenuItem3.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) 
							{
//									BajaAfiliado.getInstancia().setLocationRelativeTo(null);
//									BajaAfiliado.getInstancia().setVisible(true);
							}
						});
					}
				}
			}
			{
				jMenu1 = new JMenu();
				jMenuBar1.add(jMenu1);
				jMenu1.setText("Salir del Sistema");
				{
					jMenuItem4 = new JMenuItem();
					jMenu1.add(jMenuItem4);
					jMenuItem4.setText("Salir");
					jMenuItem4.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) 
						{
							System.exit(0);
						}
					});
				}
			}
		}
		pack();
		this.setSize(371, 235);
	}

}
