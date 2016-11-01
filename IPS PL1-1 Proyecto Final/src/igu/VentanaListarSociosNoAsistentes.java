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

import logica.Actividad;
import logica.MatriculadoEn;
import logica.Reserva;
import base.datos.GestorBaseDatos;



public class VentanaListarSociosNoAsistentes extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private final JPanel panelPrincipal = new JPanel();
	private JTable tablaListadoReservas;
	private ModeloNoEditable modeloTabla;
	private GestorBaseDatos gbd;
	
	/**
	 * Create the dialog.
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public VentanaListarSociosNoAsistentes(VentanaPrincipal vp) throws ClassNotFoundException, SQLException {
		setTitle("Listar socios no asisten todas sesiones actividad");
		gbd = new GestorBaseDatos();
		setBounds(100, 100, 707, 328);
		getContentPane().setLayout(new BorderLayout());
		panelPrincipal.setBackground(Color.LIGHT_GRAY);
		panelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(panelPrincipal, BorderLayout.CENTER);
		panelPrincipal.setLayout(new BorderLayout(0, 0));
		{
			JScrollPane spTabla = new JScrollPane();
			panelPrincipal.add(spTabla, BorderLayout.CENTER);
			{
				modeloTabla = new ModeloNoEditable(new String[] {"DNI Socio","Actividad", "Horas asistidas","Horas totales"}, 0);
				tablaListadoReservas = new JTable();
				tablaListadoReservas.setModel(modeloTabla);
				tablaListadoReservas.setBackground(Color.WHITE);
				spTabla.setViewportView(tablaListadoReservas);
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
		List<MatriculadoEn> listaMatriculas = gbd.listarMatriculas();
		List<Actividad> listaActividades = gbd.listarActividades();

		Object[] line = new Object[4];
		for(int i = 0; i < listaMatriculas.size(); i++){
			for(int j = 0; j < listaActividades.size(); j++){
				
				if(listaMatriculas.get(i).getNombreActividad() == listaActividades.get(j).getId_actividad() && 
						listaMatriculas.get(i).getAsistenciaSemanal() < listaActividades.get(j).getDuracionSemanas()){
					
					line[0] = listaMatriculas.get(i).getDniSocio();
					line[1] = getNombreActividadByID(listaMatriculas.get(i).getNombreActividad());
					line[2] = listaMatriculas.get(i).getAsistenciaSemanal();
					line[3] = listaActividades.get(j).getDuracionSemanas();
					
					modeloTabla.addRow(line);

				}
			}

		}
	}
	public String getNombreActividadByID(int id) throws ClassNotFoundException, SQLException{
		List<Actividad> actividades = gbd.listarActividades();
		for(Actividad a: actividades){
			if(a.getId_actividad() == id){
				return a.getNombre();
			}
		}
		return null;
	}

}
