package igu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

import logica.Instalacion;
import base.datos.Conexion;
import base.datos.GestorBaseDatos;



public class VentanaListarInstalaciones extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private final JPanel panelPrincipal = new JPanel();
	private JTable tablaListadoInstalaciones;
	private ModeloNoEditable modeloTabla;
	private Conexion con;
	private GestorBaseDatos gbd;
	
	/**
	 * Create the dialog.
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public VentanaListarInstalaciones() throws ClassNotFoundException, SQLException {
		setTitle("Listado de instalaciones");
		con = new Conexion();
		gbd = new GestorBaseDatos();
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		panelPrincipal.setBackground(Color.LIGHT_GRAY);
		panelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(panelPrincipal, BorderLayout.CENTER);
		panelPrincipal.setLayout(new BorderLayout(0, 0));
		{
			JScrollPane spTabla = new JScrollPane();
			panelPrincipal.add(spTabla, BorderLayout.CENTER);
			{
				modeloTabla = new ModeloNoEditable(new String[] {"ID", "Nombre", "Disponibilidad"}, 0);
				tablaListadoInstalaciones = new JTable();
				tablaListadoInstalaciones.setModel(modeloTabla);
				tablaListadoInstalaciones.setBackground(Color.WHITE);
				spTabla.setViewportView(tablaListadoInstalaciones);
				{
					JPanel panel = new JPanel();
					getContentPane().add(panel, BorderLayout.SOUTH);
					{
						JButton btAceptar = new JButton("Aceptar");
						btAceptar.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {
								dispose();
							}
						});
						panel.add(btAceptar);
					}
				}
				meterTablas();
			}
		}
	}
	
	public void meterTablas() throws ClassNotFoundException, SQLException{
		List<Instalacion> listado = gbd.listarInstalaciones();
		Object[] line = new Object[2];
		for(int i = 0; i < listado.size(); i++){
			line[0] = listado.get(i).getId();
			line[1] = listado.get(i).getNombre();
			
			modeloTabla.addRow(line);

		}
	}

}
