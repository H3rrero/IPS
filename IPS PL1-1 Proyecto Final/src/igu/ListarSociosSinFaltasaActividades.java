package igu;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import base.acciones.gestion.GestionApliIMP;
import base.datos.GestorBaseDatos;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

import logica.Actividad;
import logica.MatriculadoEn;
import logica.Socio;

public class ListarSociosSinFaltasaActividades extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JButton btnGenerarListaDe;
	private JScrollPane scrollPane;
	private JTable table;
	private JButton btnSalir;
	private ModeloNoEditable modeloTabla;
	private GestorBaseDatos gbd;
	private VentanaPrincipal vp;
	
	public ListarSociosSinFaltasaActividades(VentanaPrincipal vp) {
		setTitle("Lista socios asisten a todas las sesiones");
		gbd = new GestorBaseDatos();
		setBounds(100, 100, 601, 408);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		contentPanel.add(getBtnGenerarListaDe());
		contentPanel.add(getScrollPane());
		contentPanel.add(getBtnSalir());
	}
	private JButton getBtnGenerarListaDe() {
		if (btnGenerarListaDe == null) {
			btnGenerarListaDe = new JButton("Generar lista de socios ");
			btnGenerarListaDe.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					try {
						modeloTabla.getDataVector().clear();
						añadirFilas();
						if(table.getRowCount() == 0){
							Object [] fila = new Object[2];
							fila[0] = "NO EXISTEN SOCIOS ";
							fila[1] = "QUE ASISTEN AL TOTAL DE SESIONES";
							modeloTabla.addRow(fila);
						}
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			btnGenerarListaDe.setBounds(190, 286, 236, 40);
		}
		return btnGenerarListaDe;
	}
	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setBounds(109, 49, 386, 226);
			scrollPane.setViewportView(getTable());
		}
		return scrollPane;
	}
	private JTable getTable() {
		if (table == null) {
			table = new JTable();
			String[] nombreColumnas = {"Nombre del socio","Nombre Actividad"};
			modeloTabla = new ModeloNoEditable(nombreColumnas,0);
			table = new JTable(modeloTabla);
			table.getColumnModel().getColumn(1).setPreferredWidth(200);
		}
		return table;
	}
	private JButton getBtnSalir() {
		if (btnSalir == null) {
			btnSalir = new JButton("Salir");
			btnSalir.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					dispose();
				}
			});
			btnSalir.setBounds(486, 327, 89, 31);
		}
		return btnSalir;
	}
	
	private String getNombreSocio(String DNI) throws ClassNotFoundException, SQLException
	{
		
		List<Socio> socios= gbd.listarSocios();
		String nombre=null;
		for (Socio socio : socios) {
			if(socio.getDni().equalsIgnoreCase(DNI))
				nombre=socio.getNombre();
		}
		return nombre;
		
	}
	private String getNombreActividad(int id_actividad) throws ClassNotFoundException, SQLException
	{
		
		List<Actividad> actividades= gbd.listarActividades();
		String nombre=null;
		for (Actividad actividad : actividades) {
			if(actividad.getId_actividad()==id_actividad)
				nombre=actividad.getNombre();
		}
		return nombre;
		
	}
	private void añadirFilas() throws ClassNotFoundException, SQLException {
		List<MatriculadoEn> mEn = gbd.listarMatriculados();
		List<Actividad> act = gbd.listarActividades();
		Object [] nuevaFila = new Object [2];
		for(MatriculadoEn m: mEn){
			int idActividad= m.getNombreActividad();
			String dnii= m.getDniSocio();
			int asistencia=m.getAsistenciaSemanal();
			for (Actividad actividad : act) {
				if(idActividad==actividad.getId_actividad()&&asistencia==actividad.getDuracionSemanas()){
					nuevaFila[0] = getNombreSocio(dnii);
					nuevaFila[1] = getNombreActividad(idActividad);
					
					
					modeloTabla.addRow(nuevaFila);
				}
			}
			
		}
		
	}
	public VentanaPrincipal getVp() {
		return vp;
	}
	public void setVp(VentanaPrincipal vp) {
		this.vp = vp;
	}
}
