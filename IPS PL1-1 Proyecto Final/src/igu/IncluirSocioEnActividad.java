package igu;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.HeadlessException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.UIManager;
import javax.swing.JComboBox;
import javax.swing.ImageIcon;

import BussinesException.BusinessException;
import base.acciones.gestion.GestionApliIMP;
import base.datos.GestorBaseDatos;
import logica.Actividad;
import logica.Instalacion;
import logica.MatriculadoEn;
import logica.Reserva;
import logica.Socio;
import javax.swing.SwingConstants;
import java.awt.Font;

public class IncluirSocioEnActividad extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JLabel lblDni;
	private JTextField textDNI;
	private JLabel lblNombre;
	private JTextField textNombre;
	private JLabel lblApellidos;
	private JTextField textApellidos;
	private JPanel panel;
	private JLabel lblActividad;
	private JComboBox comboBoxACtividades;
	private JLabel lblPlazasActividad;
	private JTextField textFieldPlazas;
	private JLabel lblDurancion;
	private JTextField textFieldDuracion;
	private JLabel lblSemanas;
	private JButton btnConfirmarInscripcion;
	private JButton btnCancelarTramite;
	private JButton button;
	private GestorBaseDatos gbd;
	private GestionApliIMP gestion;
	private String nombreActividad;
	private Date hoy = new Date();
	Calendar calendario = Calendar.getInstance();
	

	/**
	 * Create the dialog.
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public IncluirSocioEnActividad(VentanaPrincipal vp) throws ClassNotFoundException, SQLException {
		gbd = new GestorBaseDatos();
		gestion= new GestionApliIMP();
		setTitle("Reserva de Plazas");
		setBounds(100, 100, 701, 433);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		contentPanel.add(getLblDni());
		contentPanel.add(getTextDNI());
		contentPanel.add(getLblNombre());
		contentPanel.add(getTextNombre());
		contentPanel.add(getLabel_1());
		contentPanel.add(getTextApellidos());
		contentPanel.add(getPanel());
		contentPanel.add(getBtnConfirmarInscripcion());
		contentPanel.add(getBtnCancelarTramite());
		contentPanel.add(getButton());
	}

	private JLabel getLblDni() {
		if (lblDni == null) {
			lblDni = new JLabel("DNI:");
			lblDni.setBounds(176, 32, 38, 16);
		}
		return lblDni;
	}

	private JTextField getTextDNI() {
		if (textDNI == null) {
			textDNI = new JTextField();
			textDNI.setHorizontalAlignment(SwingConstants.CENTER);
			textDNI.setBounds(226, 29, 226, 22);
			textDNI.setColumns(10);
		}
		return textDNI;
	}

	private JLabel getLblNombre() {
		if (lblNombre == null) {
			lblNombre = new JLabel("Nombre: ");
			lblNombre.setBounds(62, 92, 56, 16);
		}
		return lblNombre;
	}

	private JTextField getTextNombre() {
		if (textNombre == null) {
			textNombre = new JTextField();
			textNombre.setEditable(false);
			textNombre.setBounds(130, 89, 193, 22);
			textNombre.setColumns(10);
		}
		return textNombre;
	}

	private JLabel getLabel_1() {
		if (lblApellidos == null) {
			lblApellidos = new JLabel("Apellidos: ");
			lblApellidos.setBounds(335, 92, 75, 16);
		}
		return lblApellidos;
	}

	private JTextField getTextApellidos() {
		if (textApellidos == null) {
			textApellidos = new JTextField();
			textApellidos.setEditable(false);
			textApellidos.setBounds(411, 89, 183, 22);
			textApellidos.setColumns(10);
		}
		return textApellidos;
	}

	private JPanel getPanel() throws ClassNotFoundException, SQLException {
		if (panel == null) {
			panel = new JPanel();
			panel.setEnabled(false);
			panel.setBorder(new TitledBorder(UIManager
					.getBorder("TitledBorder.border"),
					"Seleccione una Actividad", TitledBorder.LEADING,
					TitledBorder.TOP, null, new Color(255, 0, 255)));
			panel.setBounds(143, 136, 396, 163);
			panel.setLayout(null);
			panel.add(getLblActividad());
			panel.add(getComboBoxACtividades());
			panel.add(getLblPlazasActividad());
			panel.add(getTextFieldPlazas());
			panel.add(getLblDurancion());
			panel.add(getTextFieldDuracion());
			panel.add(getLblSemanas());
		}
		return panel;
	}

	private JLabel getLblActividad() {
		
		if (lblActividad == null) {
			lblActividad = new JLabel("Actividad: ");
			lblActividad.setBounds(12, 46, 76, 16);
		}
		return lblActividad;
	}

	@SuppressWarnings("unchecked")
	private JComboBox getComboBoxACtividades() throws ClassNotFoundException, SQLException {
		final List<Actividad> actividades;
		if (comboBoxACtividades == null) {
			comboBoxACtividades = new JComboBox();
			comboBoxACtividades.setEnabled(false);
			comboBoxACtividades.setBounds(100, 43, 200, 22);
		}
		actividades = gbd.listarActividades();
		comboBoxACtividades.addItem("----------");
		for (Actividad ac : actividades) {
			if (!ac.getNombre().equals("LIBRE")) {
				comboBoxACtividades.addItem(ac.getNombre());
			}

		}
		comboBoxACtividades.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(getPlazasACtividad(
						actividades, comboBoxACtividades.getSelectedItem()
						.toString()) >  200){
					textFieldPlazas.setText("Sin limite");
				}
				else{
				textFieldPlazas.setText(String.valueOf(getPlazasACtividad(
						actividades, comboBoxACtividades.getSelectedItem()
								.toString())));
				}
				textFieldDuracion.setText(String.valueOf(getDuracionActividad(
						actividades, comboBoxACtividades.getSelectedItem()
								.toString())));
				nombreActividad=comboBoxACtividades.getSelectedItem().toString();
			}
		});
		return comboBoxACtividades;
	}
	public int getPlazasACtividad(List<Actividad> list, String nombre) {
		for (Actividad ins : list) {
			if (ins.getNombre().equals(nombre)) {
				return ins.getNumeroPlazas();
			}
		}
		return 0;
	}
	public int getDuracionActividad(List<Actividad> list, String nombre) {
		for (Actividad ins : list) {
			if (ins.getNombre().equals(nombre)) {
				return ins.getDuracionSemanas();
			}
		}
		return 0;
	}
	public int getIdActividad(List<Actividad> list, String nombre) {
		for (Actividad ins : list) {
			if (ins.getNombre().equals(nombre)) {
				return ins.getId_actividad();
			}
		}
		return 0;
	}
	private JLabel getLblPlazasActividad() {
		if (lblPlazasActividad == null) {
			lblPlazasActividad = new JLabel("Plazas:");
			lblPlazasActividad.setBounds(12, 114, 56, 16);
		}
		return lblPlazasActividad;
	}

	private JTextField getTextFieldPlazas() {
		if (textFieldPlazas == null) {
			textFieldPlazas = new JTextField();
			textFieldPlazas.setFont(new Font("Tahoma", Font.BOLD, 11));
			textFieldPlazas.setHorizontalAlignment(SwingConstants.CENTER);
			textFieldPlazas.setEnabled(true);
			textFieldPlazas.setEditable(false);
			textFieldPlazas.setText("");
			textFieldPlazas.setBounds(65, 111, 76, 22);
			textFieldPlazas.setColumns(10);
		}
		return textFieldPlazas;
	}

	private JLabel getLblDurancion() {
		if (lblDurancion == null) {
			lblDurancion = new JLabel("Duracion:");
			lblDurancion.setBounds(188, 114, 69, 16);
		}
		return lblDurancion;
	}

	private JTextField getTextFieldDuracion() {
		if (textFieldDuracion == null) {
			textFieldDuracion = new JTextField();
			textFieldDuracion.setFont(new Font("Tahoma", Font.BOLD, 11));
			textFieldDuracion.setHorizontalAlignment(SwingConstants.CENTER);
			textFieldDuracion.setEditable(false);
			textFieldDuracion.setBounds(252, 111, 64, 22);
			textFieldDuracion.setColumns(10);
		}
		return textFieldDuracion;
	}

	private JLabel getLblSemanas() {
		if (lblSemanas == null) {
			lblSemanas = new JLabel("semanas");
			lblSemanas.setBounds(328, 114, 56, 16);
		}
		return lblSemanas;
	}

	private JButton getBtnConfirmarInscripcion() {
		if (btnConfirmarInscripcion == null) {
			btnConfirmarInscripcion = new JButton("Confirmar Inscripcion");
			btnConfirmarInscripcion.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					
					try {
						List<Actividad> actividades= gbd.listarActividades(); 
						int id=getIdActividad(actividades,nombreActividad);
						
						int plazas;
						if(textFieldPlazas.getText().equalsIgnoreCase("Sin limite")){
							plazas = 10000;
						}
						else{
							plazas= Integer.parseInt(textFieldPlazas.getText());
						}
						List<Reserva> reservas = gbd.listarReservas();
						for (Reserva reserva : reservas) {
							if(reserva.getId_actividad()==id)
								if(!comprobarHorario(reserva.getHorario(),reserva.getHoraInicio()))
								{return;}
						}
						if(comprobarSocio(textDNI.getText(), id))
							return;
						if(comprobarPlazas( id))
							return;
						gestion.UpdateActividad(id, plazas-1);
						gestion.añadirMatricula(textDNI.getText().toString(),getIdActividad(actividades,nombreActividad) , 0);
						JOptionPane.showMessageDialog(null, "La inscripción se ha registrado correctamente");
						dispose();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (BusinessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			btnConfirmarInscripcion.setBounds(143, 324, 177, 25);
		}
		return btnConfirmarInscripcion;
	}

	private JButton getBtnCancelarTramite() {
		if (btnCancelarTramite == null) {
			btnCancelarTramite = new JButton("Cancelar Tramite");
			btnCancelarTramite.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					dispose();
				}
			});
			btnCancelarTramite.setBounds(353, 324, 186, 25);
		}
		return btnCancelarTramite;
	}

	private JButton getButton() {
		if (button == null) {
			button = new JButton("");
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					try {
						comprobacion();
						panel.setEnabled(true);
					} catch (HeadlessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			button.setIcon(new ImageIcon(IncluirSocioEnActividad.class
					.getResource("/img/lupab.jpg")));
			button.setBorder(null);
			button.setBounds(462, 23, 45, 42);
		}
		return button;
	}

	public boolean comprobacion() throws HeadlessException,
			ClassNotFoundException, SQLException {
		List<Socio> socios = gbd.listarSocios();
		if (textDNI.getText().toString().equals("")) {
			JOptionPane
					.showMessageDialog(
							null,
							"Se debe proporcionar el dni del socio en el campo pertinente",
							"Campos obligatorios", JOptionPane.ERROR_MESSAGE);
			textNombre.setText(" ");
			textApellidos.setText(" ");
			return false;
		}
		if (!comprobarDNIBase(textDNI.getText().toString())) {
			JOptionPane
					.showMessageDialog(
							null,
							"El socio no se encuentra registrado como tal en la base de datos",
							"Socio no registrado", JOptionPane.ERROR_MESSAGE);
			textNombre.setText(" ");
			textApellidos.setText(" ");
			return false;
		}
		for (Socio s : socios) {

			if (s.getDni().equals(textDNI.getText().toString())) {
				textNombre.setText(s.getNombre());
				textApellidos.setText(s.getApellidos());
				comboBoxACtividades.setEnabled(true);
				return true;
			}

		}
		return false;
	}

	public boolean comprobarDNIBase(String dni) throws ClassNotFoundException,
			SQLException {
		List<Socio> socios = gbd.listarSocios();
		for (Socio s : socios) {
			if (s.getDni().equals(dni)) {
				comboBoxACtividades.setEnabled(true);
				return true;

			}
		}
		return false;
	}
	private boolean comprobarDiaAntes(String horario,int horainicio) {
		// TODO Auto-generated method stub
		int horaActual= calendario.get(Calendar.HOUR_OF_DAY);
		System.out.println("hora actual: " + horaActual);
		
		String [] array = horario.split("/");
		int dia = Integer.parseInt(array[0]);
		int mes = Integer.parseInt(array[1]);
		int año = Integer.parseInt(array[2]);
		Calendar calendar = new GregorianCalendar(año,mes-1,dia-1);
		if(calendar.DAY_OF_MONTH<calendario.DAY_OF_MONTH){


			JOptionPane.showMessageDialog(null, "No se permite inscribir en la actividad debido a que falta mas de un dia para el comienzo d ela actividad","No se permite cancelar la reserva", JOptionPane.ERROR_MESSAGE);
			return false;
		}


		return true;
	}
	private boolean comprobarHorario(String horario,int horainicio) {
		// TODO Auto-generated method stub
		int horaActual= calendario.get(Calendar.HOUR_OF_DAY);
		System.out.println("hora actual: " + horaActual);
		
		String [] array = horario.split("/");
		int dia = Integer.parseInt(array[0]);
		int mes = Integer.parseInt(array[1]);
		int año = Integer.parseInt(array[2]);
		Calendar calendar = new GregorianCalendar(año,mes-1,dia-1);
		if(calendar.DAY_OF_MONTH==calendario.DAY_OF_MONTH && calendar.MONTH==calendario.MONTH&&calendar.YEAR==calendario.YEAR && horainicio<= horaActual+1){


			JOptionPane.showMessageDialog(null, "No se permite inscribir en la actividad debido a que el comienzo de la actividad no sobrepasa la hora","No se permite cancelar la reserva", JOptionPane.ERROR_MESSAGE);
			return false;
		}


		return true;
	}
	
	private  boolean comprobarSocio(String dni,int idActividad) throws ClassNotFoundException, SQLException
	{
		
		List<MatriculadoEn> matriculados= gbd.listarMatriculados();
		for (MatriculadoEn matriculadoEn : matriculados) {
			if(matriculadoEn.getDniSocio().equalsIgnoreCase(dni) && matriculadoEn.getNombreActividad()==idActividad)
			{
				JOptionPane.showMessageDialog(null, "No se permite inscribir en la actividad debido a que el socio ya esta inscrito en ella","No se permite cancelar la reserva", JOptionPane.ERROR_MESSAGE);
				return true;
			}
		}
		return false;
	}
	private  boolean comprobarPlazas(int idActividad) throws ClassNotFoundException, SQLException
	{
		
		List<Actividad> actividades= gbd.listarActividades();
		for (Actividad actividad : actividades) {
			if(actividad.getId_actividad()==idActividad&&actividad.getNumeroPlazas()==0)
			{
				JOptionPane.showMessageDialog(null, "No se permite inscribir en la actividad debido a que no quedan plazas libres","No se permite cancelar la reserva", JOptionPane.ERROR_MESSAGE);
				return true;
			}
		}
		return false;
	}
}
