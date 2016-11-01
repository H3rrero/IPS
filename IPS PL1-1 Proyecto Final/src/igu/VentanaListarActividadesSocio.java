package igu;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import logica.Actividad;
import logica.MatriculadoEn;
import logica.Socio;
import base.datos.GestorBaseDatos;

public class VentanaListarActividadesSocio extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JLabel lbDNIUsuario;
	private JTextField txDNIUsuario;
	private JButton btBuscarDNI;
	private JScrollPane scTable;
	private JTable tableActividadesSocio;
	private JLabel lbDatosSocio;
	private JTextField textField;
	private ModeloNoEditable modeloTabla;
	private JLabel lbUsuarioActividades;
	private GestorBaseDatos gbd;
	java.util.Date fecha = new Date();
	private Date d;
	private Calendar c;
	private JButton btAceptar;

	
	/**
	 * Create the dialog.
	 */
	public VentanaListarActividadesSocio(VentanaPrincipal vp) {
		
		setTitle("Listar actividades de un socio");
		gbd = new GestorBaseDatos();
		setBounds(100, 100, 661, 426);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		contentPanel.add(getLbDNIUsuario());
		contentPanel.add(getTxDNIUsuario());
		contentPanel.add(getBtBuscarDNI());
		contentPanel.add(getScTable());
		contentPanel.add(getLbDatosSocio());
		contentPanel.add(getTextField());
		contentPanel.add(getLbUsuarioActividades());
		contentPanel.add(getBtAceptar());
		d = new Date();
		c = new GregorianCalendar(); 
		c.setTime(d);
	}
	private JLabel getLbDNIUsuario() {
		if (lbDNIUsuario == null) {
			lbDNIUsuario = new JLabel("DNI del Cliente:");
			lbDNIUsuario.setBounds(143, 26, 95, 14);
		}
		return lbDNIUsuario;
	}
	private JTextField getTxDNIUsuario() {
		if (txDNIUsuario == null) {
			txDNIUsuario = new JTextField();
			txDNIUsuario.setHorizontalAlignment(SwingConstants.CENTER);
			txDNIUsuario.setColumns(10);
			txDNIUsuario.setBounds(228, 23, 186, 20);
		}
		return txDNIUsuario;
	}
	private JButton getBtBuscarDNI() {
		if (btBuscarDNI == null) {
			btBuscarDNI = new JButton("");
			btBuscarDNI.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						comprobarCampos();
						rellenarCampos(txDNIUsuario.getText().toString());
						modeloTabla.getDataVector().clear();
						añadirFilas(txDNIUsuario.getText().toString());
						if(tableActividadesSocio.getRowCount() == 0){
							Object [] fila = new Object[4];
							fila[0] = txDNIUsuario.getText().toString();
							fila[1] = "NO 		HAY";
							fila[2] = "ACTIVIDADES";
							fila[3] = "DISPONIBLES";
							modeloTabla.addRow(fila);
						}
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
			btBuscarDNI.setIcon(new ImageIcon(VentanaListarActividadesSocio.class.getResource("/img/lupab.jpg")));
			btBuscarDNI.setBorder(null);
			btBuscarDNI.setBounds(424, 11, 45, 42);
		}
		return btBuscarDNI;
	}
	private JScrollPane getScTable() {
		if (scTable == null) {
			scTable = new JScrollPane();
			scTable.setBounds(10, 123, 625, 206);
			scTable.setViewportView(getTableActividadesSocio());
		}
		return scTable;
	}
	private JTable getTableActividadesSocio() {
		if (tableActividadesSocio == null) {
			tableActividadesSocio = new JTable();
			String [] nombreCols = { "DNI Socio", "Actividad", "ID Actividad", "Mes"};
			modeloTabla = new ModeloNoEditable(nombreCols, 0);
			tableActividadesSocio = new JTable(modeloTabla);
		}
		return tableActividadesSocio;
	}
	private JLabel getLbDatosSocio() {
		if (lbDatosSocio == null) {
			lbDatosSocio = new JLabel("Datos Usuario:");
			lbDatosSocio.setFont(new Font("Tahoma", Font.PLAIN, 11));
			lbDatosSocio.setBounds(126, 67, 81, 14);
		}
		return lbDatosSocio;
	}
	private JTextField getTextField() {
		if (textField == null) {
			textField = new JTextField();
			textField.setFont(new Font("Tahoma", Font.BOLD, 11));
			textField.setHorizontalAlignment(SwingConstants.CENTER);
			textField.setBounds(206, 64, 288, 20);
			textField.setColumns(10);
		}
		return textField;
	}
	private JLabel getLbUsuarioActividades() {
		if (lbUsuarioActividades == null) {
			lbUsuarioActividades = new JLabel("El usuario se encuentra matriculado en el mes actual en las siguientes actividades:");
			lbUsuarioActividades.setFont(new Font("Tahoma", Font.BOLD, 11));
			lbUsuarioActividades.setBounds(10, 98, 625, 14);
		}
		return lbUsuarioActividades;
	}
	public boolean isSocioRegistrado(String dni) throws ClassNotFoundException, SQLException{
		List<Socio> socios = gbd.listarSocios();
		for(Socio s: socios){
			if(s.getDni().equalsIgnoreCase(dni)){
				return true;
			}
		}
		return false;
	}
	private void comprobarCampos() throws ClassNotFoundException, SQLException {
		if(txDNIUsuario.getText().toString().equals("")){
			JOptionPane.showMessageDialog(null, "Se debe rellenar obligatoriamente el campo DNI antes de realizar la búsqueda","Campo DNI no rellenado", JOptionPane.ERROR_MESSAGE);
			return;
		}
		if(! isSocioRegistrado(txDNIUsuario.getText().toString())){
			JOptionPane.showMessageDialog(null, "El socio no se encuentra registrado en la base de datos","Socio no resgistrado", JOptionPane.ERROR_MESSAGE);
			return;
		}
	}

	private void añadirFilas(String dni) throws ClassNotFoundException, SQLException {
		List<MatriculadoEn> mEn = gbd.listarMatriculas();
		Object [] nuevaFila = new Object [4];
		@SuppressWarnings({ "deprecation", "unused" })
		String month = Integer.toString(c.get(Calendar.MONTH));
		for(MatriculadoEn m: mEn){
			if(m.getDniSocio().equalsIgnoreCase(dni)){
				nuevaFila[0] = m.getDniSocio();
				nuevaFila[1] = m.getNombreActividad();
				nuevaFila[2] = m.getNombreActividad();
				nuevaFila[3] = getMesLetra(month);
				
				modeloTabla.addRow(nuevaFila);
			}
		}
		
	}
	
	private int getIDActividad(String nombreActividad) throws ClassNotFoundException, SQLException {
		List<Actividad> actividades = gbd.listarActividades();
		for(Actividad a : actividades){
			if(a.getNombre().equals(nombreActividad)){
				return a.getId_actividad();
			}
		}
		return 0;
	}
	
	public String getNombreActividad(int id) throws ClassNotFoundException, SQLException{
		List<Actividad> actividades = gbd.listarActividades();
		for(Actividad a : actividades){
			if(a.getId_actividad() == id){
				return a.getNombre();
			}
		}
		return null;
	}
	public String getMesLetra(String num){
		String [] mes = {"Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"};
		return mes[Integer.parseInt(num)];
	}
	private void rellenarCampos(String dni) throws ClassNotFoundException, SQLException {
		String cad= "";
		List<Socio> socios = gbd.listarSocios();
		for(Socio s: socios){
			if(s.getDni().equalsIgnoreCase(dni)){
				cad += s.getNombre() + " | " + s.getApellidos() + " | " + s.getTelefono();
				break;
			}
		}
		textField.setText(cad);
	}
	private JButton getBtAceptar() {
		if (btAceptar == null) {
			btAceptar = new JButton("Aceptar");
			btAceptar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					
					dispose();
					
				}
			});
			btAceptar.setBounds(272, 353, 89, 23);
		}
		return btAceptar;
	}
}
