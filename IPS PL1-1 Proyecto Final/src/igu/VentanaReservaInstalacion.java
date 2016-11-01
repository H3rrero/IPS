package igu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import logica.Instalacion;
import logica.Reserva;
import logica.Socio;
import BussinesException.BusinessException;
import base.acciones.gestion.GestionApliIMP;
import base.datos.Conexion;
import base.datos.GestorBaseDatos;

public class VentanaReservaInstalacion extends JDialog {

	final long MILLSECS_PER_DAY = 24 * 60 * 60 * 1000;

	java.util.Date hoy = new Date();
	private JPanel contentPane;
	private JPanel panel;
	private JLabel lbnombre;
	private JTextField txtNombre;
	private JLabel lblApellidos;
	private JTextField txtApellidos;
	private JPanel panel_1;
	private JPanel panel_2;
	private JLabel lbInstalacion;
	private JComboBox cbDias;
	private JComboBox cbMes;
	private JComboBox cbAno;
	private Action action;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private VentanaPrincipal vp;
	private JButton btnSig;
	private JButton btnNewButton;
	private JTextField txInstalacionHora;
	private JLabel lbDNI;
	private JTextField txDNI;
	private GestorBaseDatos gbd;
	private JComboBox cbInstalaciones;
	private JLabel lbHora00;
	private JLabel label;
	private JSpinner spInicio;
	private JSpinner spFin;
	private GestionApliIMP gestion;
	private JButton btLupa;
	private long diferencia;
	private JLabel lbTipoCliente;
	private JRadioButton rbSocio;
	private JRadioButton rbCentro;

	/**
	 * Create the frame.
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public VentanaReservaInstalacion(VentanaPrincipal vp)
			throws ClassNotFoundException, SQLException {
		gbd = new GestorBaseDatos();
		gestion = new GestionApliIMP();
		setResizable(false);
		setTitle("Formulario de reserva");
		setBackground(Color.WHITE);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 741, 491);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		contentPane.add(getPanel(), BorderLayout.CENTER);
		cbInstalaciones.setEnabled(false);

	}

	private JPanel getPanel() throws ClassNotFoundException, SQLException {
		if (panel == null) {
			panel = new JPanel();
			panel.setBackground(Color.WHITE);
			panel.setLayout(null);
			panel.add(getLbnombre());
			panel.add(getTxtNombre());
			panel.add(getLblApellidos());
			panel.add(getTxtApellidos());
			panel.add(getPanel_1());
			panel.add(getPanel_2());
			panel.add(getBtnSig());
			panel.add(getBtnNewButton());
			panel.add(getLbDNI());
			panel.add(getTxDNI());
			panel.add(getBtLupa());
			panel.add(getLbTipoCliente());
		}
		return panel;
	}

	private JLabel getLbnombre() {
		if (lbnombre == null) {
			lbnombre = new JLabel("Nombre: ");
			lbnombre.setFont(new Font("Tahoma", Font.PLAIN, 13));
			lbnombre.setLabelFor(getTxtNombre());
			lbnombre.setBounds(139, 106, 57, 14);
		}
		return lbnombre;
	}

	private JTextField getTxtNombre() {
		if (txtNombre == null) {
			txtNombre = new JTextField();
			txtNombre.setHorizontalAlignment(SwingConstants.CENTER);
			txtNombre.setEditable(false);
			txtNombre.setBounds(202, 104, 128, 20);
			txtNombre.setColumns(10);
		}
		return txtNombre;
	}

	private JLabel getLblApellidos() {
		if (lblApellidos == null) {
			lblApellidos = new JLabel("Apellidos: ");
			lblApellidos.setFont(new Font("Tahoma", Font.PLAIN, 13));
			lblApellidos.setLabelFor(lblApellidos);
			lblApellidos.setBounds(336, 106, 72, 14);
		}
		return lblApellidos;
	}

	private JTextField getTxtApellidos() {
		if (txtApellidos == null) {
			txtApellidos = new JTextField();
			txtApellidos.setHorizontalAlignment(SwingConstants.CENTER);
			txtApellidos.setEditable(false);
			txtApellidos.setBounds(399, 104, 184, 20);
			txtApellidos.setColumns(10);
		}
		return txtApellidos;
	}

	private JPanel getPanel_1() {
		if (panel_1 == null) {
			panel_1 = new JPanel();
			panel_1.setEnabled(false);
			panel_1.setBorder(new TitledBorder(null, "Fecha de reserva",
					TitledBorder.LEADING, TitledBorder.TOP, null, null));
			panel_1.setBackground(Color.WHITE);
			panel_1.setBounds(139, 252, 446, 76);
			panel_1.setLayout(null);
			panel_1.add(getCbDias());
			panel_1.add(getCbMes());
			panel_1.add(getCbAno());
			JLabel lbHoraInicio = new JLabel("Hora Inicio:");
			lbHoraInicio.setFont(new Font("Tahoma", Font.PLAIN, 13));
			lbHoraInicio.setBounds(249, 15, 73, 14);
			panel_1.add(lbHoraInicio);

			JLabel lbHoraFin = new JLabel("Hora Fin:");
			lbHoraFin.setFont(new Font("Tahoma", Font.PLAIN, 13));
			lbHoraFin.setBounds(249, 45, 73, 14);
			panel_1.add(lbHoraFin);

			panel_1.add(getLbHora00());
			panel_1.add(getLabel());
			panel.add(getRbSocio());
			panel.add(getRbCentro());
			panel_1.add(getSpInicio());
			panel_1.add(getSpFin());

		}
		return panel_1;
	}

	private JPanel getPanel_2() throws ClassNotFoundException, SQLException {
		if (panel_2 == null) {
			panel_2 = new JPanel();
			panel_2.setEnabled(false);
			panel_2.setBorder(new TitledBorder(null, "Datos de registro",
					TitledBorder.LEADING, TitledBorder.TOP, null, null));
			panel_2.setBackground(Color.WHITE);
			panel_2.setBounds(196, 135, 319, 111);
			panel_2.setLayout(null);
			panel_2.add(getLbInstalacion());

			JLabel lblNewLabel = new JLabel("Precio Instalacion/h:");
			lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
			lblNewLabel.setBounds(36, 86, 123, 14);
			panel_2.add(lblNewLabel);

			txInstalacionHora = new JTextField();
			txInstalacionHora.setFont(new Font("Tahoma", Font.BOLD, 13));
			txInstalacionHora.setHorizontalAlignment(SwingConstants.CENTER);
			panel_2.add(getCbInstalaciones());
			lblNewLabel.setLabelFor(txInstalacionHora);
			txInstalacionHora.setEditable(false);
			txInstalacionHora.setBounds(202, 83, 86, 20);
			panel_2.add(txInstalacionHora);
			txInstalacionHora.setColumns(10);

		}
		return panel_2;
	}

	private JLabel getLbInstalacion() throws ClassNotFoundException,
			SQLException {
		if (lbInstalacion == null) {
			lbInstalacion = new JLabel("Instalaci\u00F3n: ");
			lbInstalacion.setLabelFor(cbInstalaciones);
			lbInstalacion.setFont(new Font("Tahoma", Font.PLAIN, 13));
			lbInstalacion.setBounds(36, 42, 73, 14);
		}
		return lbInstalacion;
	}

	private JComboBox getCbDias() {
		if (cbDias == null) {
			cbDias = new JComboBox();
			cbDias.setEnabled(false);
			cbDias.setModel(new DefaultComboBoxModel(new String[] { "1", "2",
					"3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13",
					"14", "15", "16", "17", "18", "19", "20", "21", "22", "23",
					"24", "25", "26", "27", "28", "29", "30", "31" }));
			cbDias.setBounds(33, 35, 42, 20);
		}
		return cbDias;
	}

	private JComboBox getCbMes() {
		if (cbMes == null) {
			cbMes = new JComboBox();
			cbMes.setEnabled(false);
			cbMes.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					String[] febrero = new String[] { "1", "2", "3", "4", "5",
							"6", "7", "8", "9", "10", "11", "12", "13", "14",
							"15", "16", "17", "18", "19", "20", "21", "22",
							"23", "24", "25", "26", "27", "28" };
					String[] Mes30 = new String[] { "1", "2", "3", "4", "5",
							"6", "7", "8", "9", "10", "11", "12", "13", "14",
							"15", "16", "17", "18", "19", "20", "21", "22",
							"23", "24", "25", "26", "27", "28", "29", "30" };
					String[] Mes31 = new String[] { "1", "2", "3", "4", "5",
							"6", "7", "8", "9", "10", "11", "12", "13", "14",
							"15", "16", "17", "18", "19", "20", "21", "22",
							"23", "24", "25", "26", "27", "28", "29", "30",
							"31" };

					if (cbMes.getSelectedItem().toString().equals("2")) {
						cbDias.setModel(new DefaultComboBoxModel(febrero));
					} else if (cbMes.getSelectedItem().toString().equals("1")
							|| cbMes.getSelectedItem().toString().equals("3")
							|| cbMes.getSelectedItem().toString().equals("5")
							|| cbMes.getSelectedItem().toString().equals("7")
							|| cbMes.getSelectedItem().toString().equals("8")
							|| cbMes.getSelectedItem().toString().equals("10")
							|| cbMes.getSelectedItem().toString().equals("12")) {
						cbDias.setModel(new DefaultComboBoxModel(Mes31));
					} else {
						cbDias.setModel(new DefaultComboBoxModel(Mes30));
					}
				}
			});

			cbMes.setModel(new DefaultComboBoxModel(new String[] { "1", "2",
					"3", "4", "5", "6", "7", "8", "9", "10", "11", "12" }));
			cbMes.setBounds(85, 35, 59, 20);
		}
		return cbMes;
	}

	private JComboBox getCbAno() {
		if (cbAno == null) {
			cbAno = new JComboBox();
			cbAno.setEnabled(false);
			String[] años = new String[86];

			for (int i = 14; i < 99; i++) {
				años[i - 14] = "20" + i;
			}
			cbAno.setModel(new DefaultComboBoxModel(años));
			cbAno.setBounds(154, 35, 73, 20);
		}
		return cbAno;
	}

	private class SwingAction extends AbstractAction {
		public SwingAction() {

		}

		public void actionPerformed(ActionEvent e) {
		}
	}

	private Action getAction1() {
		if (action == null) {
			action = new SwingAction();
		}
		return action;
	}

	private class SwingAction1 extends AbstractAction {
		public SwingAction1() {

		}

		public void actionPerformed(ActionEvent e) {
		}
	}

	private Action getAction() {
		if (action == null) {
			action = new SwingAction1();
		}
		return action;
	}

	public VentanaPrincipal getVp() {
		return vp;
	}

	private JButton getBtnSig() {
		if (btnSig == null) {
			btnSig = new JButton("Confirmar Reserva");
			btnSig.setBounds(242, 399, 155, 32);
			btnSig.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					try {

						if (reservarInstalacion() == (-1)) {
							return;
						}
						int id = getIDInstalacion(String
								.valueOf(cbInstalaciones.getSelectedItem()));
						String fecha = cbDias.getSelectedItem().toString()
								+ "/" + cbMes.getSelectedItem().toString()
								+ "/" + cbAno.getSelectedItem().toString();

						String horario = fecha;
						int hi = Integer.parseInt(spInicio.getValue()
								.toString());
						int hf = Integer.parseInt(spFin.getValue().toString());
						if (reservaDuplicada(hi, hf, horario) == true) {

							return;
						}

						if (cbInstalaciones.getSelectedIndex() == 0) {
							JOptionPane
									.showMessageDialog(
											null,
											"Se debe seleccionar una instalacion de las ofertadas",
											"Error selección actividad",
											JOptionPane.ERROR_MESSAGE);
							return;
						}
						JOptionPane.showMessageDialog(null,
								"La reserva se ha registrado con éxito");
						String name = (String) cbInstalaciones
								.getSelectedItem();
						double precio = getPrecioPorInstalacion(name);
						int diferencia = Integer.parseInt(String.valueOf(spFin
								.getValue()))
								- Integer.parseInt(String.valueOf(spInicio
										.getValue()));

						double importe = precio * diferencia;
						if (rbSocio.isSelected()) {
							gestion.añadirReserva(txDNI.getText().toString(),
									id, hi, hf, horario, false, importe, 1234);
						}
						if (rbCentro.isSelected()) {
							gestion.añadirReserva("CENTRO", id, hi, hf,
									horario, false, 0, 1234);
						}

						dispose();
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (BusinessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
		}
		return btnSig;
	}

	private JButton getBtnNewButton() {
		if (btnNewButton == null) {
			btnNewButton = new JButton("Atr\u00E1s");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					dispose();
				}
			});
			btnNewButton.setBounds(405, 399, 85, 32);
		}
		return btnNewButton;
	}

	private JLabel getLbDNI() {
		if (lbDNI == null) {
			lbDNI = new JLabel("DNI:");
			lbDNI.setFont(new Font("Tahoma", Font.PLAIN, 13));
			lbDNI.setBounds(151, 62, 45, 14);
		}
		return lbDNI;
	}

	private JTextField getTxDNI() {
		if (txDNI == null) {
			txDNI = new JTextField();
			txDNI.setHorizontalAlignment(SwingConstants.CENTER);
			txDNI.setColumns(10);
			txDNI.setBounds(225, 60, 254, 20);
		}
		return txDNI;
	}

	public boolean comprobacion() throws HeadlessException,
			ClassNotFoundException, SQLException {
		List<Socio> socios = gbd.listarSocios();
		if (txDNI.getText().toString().equals("")) {
			JOptionPane
					.showMessageDialog(
							null,
							"Se debe proporcionar el dni del socio en el campo pertinente",
							"Campos obligatorios", JOptionPane.ERROR_MESSAGE);
			txtNombre.setText(" ");
			txtApellidos.setText(" ");
			return false;
		}
		if (!comprobarDNIBase(txDNI.getText().toString())) {
			JOptionPane
					.showMessageDialog(
							null,
							"El socio no se encuentra registrado como tal en la base de datos",
							"Socio no registrado", JOptionPane.ERROR_MESSAGE);
			txtNombre.setText(" ");
			txtApellidos.setText(" ");
			return false;
		}
		for (Socio s : socios) {

			if (s.getDni().equals(txDNI.getText().toString())) {
				txtNombre.setText(s.getNombre());
				txtApellidos.setText(s.getApellidos());
				cbInstalaciones.setEnabled(true);
				return true;
			}

		}
		return false;
	}

	public int reservarInstalacion() throws ClassNotFoundException,
			SQLException {

		int dia = Integer.parseInt(String.valueOf(cbDias.getSelectedIndex()));
		int mes = Integer.parseInt(String.valueOf(cbMes.getSelectedItem()));
		int año = Integer.parseInt(String.valueOf(cbAno.getSelectedItem()));
		Calendar calendar = new GregorianCalendar(año, mes - 1, dia - 1);
		java.sql.Date fecha = new java.sql.Date(calendar.getTimeInMillis());
		this.diferencia = (fecha.getTime() - hoy.getTime()) / MILLSECS_PER_DAY
				+ 2;

		if (diferencia < 0) {
			JOptionPane.showMessageDialog(null,
					"La fecha de reserva debe ser posterior a la actual",
					"Fuera de plazo", JOptionPane.ERROR_MESSAGE);
			return -1;
		}
		if (diferencia == 0) {
			JOptionPane
					.showMessageDialog(
							null,
							"No se permite reservar con menos de 24h de antelación al inicio de la actividad",
							"Fuera de plazo", JOptionPane.ERROR_MESSAGE);
			return -1;
		}
		if (diferencia > 15) {
			JOptionPane.showMessageDialog(null,
					"La reserva excede el periodo de dos semanas",
					"Fuera de plazo", JOptionPane.ERROR_MESSAGE);
			return -1;
		}

		int horaInicio = Integer.parseInt(spInicio.getValue().toString());
		int horaFin = Integer.parseInt(spFin.getValue().toString());
		if (horaFin - horaInicio < 1) {
			JOptionPane
					.showMessageDialog(
							null,
							"La hora de finalización debe ser posterior a la de inicio",
							"Error en las horas de la actividad",
							JOptionPane.ERROR_MESSAGE);
			return -1;
		}
		if (rbSocio.isSelected()) {
			if (horaFin - horaInicio > 2) {
				JOptionPane
						.showMessageDialog(
								null,
								"El plazo de una actividad debe estar comprendido entre 1-2 horas.\nRevise las horas indicadas.",
								"Periodo de actividad no válido",
								JOptionPane.ERROR_MESSAGE);
				return -1;
			}
		} else {
			int inicio = Integer.parseInt(String.valueOf(spInicio.getValue()));
			int fin = Integer.parseInt(String.valueOf(spFin.getValue()));
			if (inicio >= fin) {
				JOptionPane
						.showMessageDialog(
								null,
								"La hora de inicio debe ser anterior a la de finalización.\nRevise las horas indicadas.",
								"Periodo de actividad no válido",
								JOptionPane.ERROR_MESSAGE);
				return -1;
			}
		}
		return 0;

	}

	private JComboBox getCbInstalaciones() throws ClassNotFoundException,
			SQLException {

		final List<Instalacion> instalaciones;
		if (cbInstalaciones == null) {
			cbInstalaciones = new JComboBox();
			cbInstalaciones.setBounds(185, 40, 124, 16);
			cbInstalaciones.setEnabled(true);
		}
		instalaciones = gbd.listarInstalaciones();
		cbInstalaciones.addItem("----------");
		for (Instalacion instalacion : instalaciones) {

			cbInstalaciones.addItem(instalacion.getNombre());

		}
		cbInstalaciones.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				txInstalacionHora.setText(String.valueOf(getPrecioHora(
						instalaciones, cbInstalaciones.getSelectedItem()
								.toString()))
						+ " €");
				txInstalacionHora.setEnabled(true);
				cbDias.setEnabled(true);
				cbMes.setEnabled(true);
				cbAno.setEnabled(true);
				spInicio.setEnabled(true);
				spFin.setEnabled(true);
				panel_1.setEnabled(true);
			}
		});
		return cbInstalaciones;
	}

	public int getPrecioHora(List<Instalacion> list, String nombre) {
		for (Instalacion ins : list) {
			if (ins.getNombre().equals(nombre)) {
				return ins.getPrecioHora();
			}
		}
		return 0;
	}

	private JLabel getLbHora00() {
		if (lbHora00 == null) {
			lbHora00 = new JLabel(":00h");
			lbHora00.setBounds(410, 16, 46, 14);
		}
		return lbHora00;
	}

	private JLabel getLabel() {
		if (label == null) {
			label = new JLabel(":00h");
			label.setBounds(410, 46, 33, 14);
		}
		return label;
	}

	private JSpinner getSpInicio() {
		if (spInicio == null) {
			spInicio = new JSpinner();
			spInicio.setEnabled(false);
			spInicio.setModel(new SpinnerNumberModel(8, 8, 22, 1));
			spInicio.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent arg0) {
					if (rbSocio.isSelected()) {
						spFin.setModel(new SpinnerNumberModel(
								((Integer) getSpInicio().getValue()) + 1,
								((Integer) getSpInicio().getValue()) + 1,
								((Integer) getSpInicio().getValue()) + 2, 1));
					}
				}
			});
			spInicio.setBounds(332, 13, 59, 20);
		}
		return spInicio;
	}

	private JSpinner getSpFin() {
		if (spFin == null) {
			spFin = new JSpinner();
			spFin.setModel(new SpinnerNumberModel(9, 9, 23, 1));
			spFin.setEnabled(false);
			if (rbSocio.isSelected()) {
				spFin.setModel(new SpinnerNumberModel(((Integer) getSpInicio()
						.getValue()) + 1,
						((Integer) getSpInicio().getValue()) + 1,
						((Integer) getSpInicio().getValue()) + 2, 1));
			}
			spInicio.setModel(new SpinnerNumberModel(8, 8, 22, 1));
			spFin.setBounds(332, 43, 59, 20);
		}
		return spFin;
	}

	public boolean comprobarDNIBase(String dni) throws ClassNotFoundException,
			SQLException {
		List<Socio> socios = gbd.listarSocios();
		for (Socio s : socios) {
			if (s.getDni().equals(dni)) {
				cbInstalaciones.setEnabled(true);
				return true;

			}
		}
		return false;
	}

	private boolean comprobarSocioReserva(String dni_socio, String horario,
			int hi, int hf) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		List<Reserva> reservas = gbd.listarReservas();
		for (Reserva r : reservas) {
			if (r.getDNI().equals(dni_socio) && r.getHorario().equals(horario)
					&& r.getHoraInicio() == hi && r.getHoraFin() == hf) {
				return false;
			}

		}
		return true;
	}

	private boolean comprobarHorarioReserva(int id, String horario)
			throws ClassNotFoundException, SQLException {
		List<Reserva> reservas = gbd.listarReservas();
		for (Reserva r : reservas) {
			if (r.getId_instalacion() == (id) && r.getHorario().equals(horario)) {
				return false;
			}

		}
		return true;
	}

	private JButton getBtLupa() {
		if (btLupa == null) {
			btLupa = new JButton("");
			btLupa.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					try {
						comprobacion();
						panel_2.setEnabled(true);
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
			btLupa.setIcon(new ImageIcon(VentanaReservaInstalacion.class
					.getResource("/img/lupab.jpg")));
			btLupa.setBorder(null);
			btLupa.setBounds(530, 51, 45, 42);
		}
		return btLupa;
	}

	public Socio getSocioDNI(String dni) throws ClassNotFoundException,
			SQLException {
		List<Socio> socios = gbd.listarSocios();
		for (Socio s : socios) {
			if (s.getDni().equals(dni)) {
				return s;
			}
		}
		return null;
	}

	private double getPrecioPorInstalacion(String instala)
			throws ClassNotFoundException, SQLException {
		List<Instalacion> instalaciones = gbd.listarInstalaciones();
		for (Instalacion insta : instalaciones) {
			if (insta.getNombre().equals(instala)) {
				return insta.getPrecioHora();
			}
		}
		return 0;
	}

	public boolean reservaDuplicada(int fi, int ff, String horario)
			throws ClassNotFoundException, SQLException {
		int finter = 0;
		boolean rel = false;
		if (ff - fi == 2)
			finter = ff - 1;
		List<Reserva> reservas = gbd.listarReservas();
		for (Reserva r : reservas) {
			if (r.getHorario().equals(horario)
					&& (r.getHoraInicio() == fi || r.getHoraInicio() == finter
							|| r.getHoraFin() == ff || r.getHoraFin() == finter)
					&& r.isCancelada() == false) {
				if (rbCentro.isSelected()) {
					rel = true;
					JOptionPane.showMessageDialog(
							null,
							"Existe una reserva de un socio en ese periodo ["
									+ r.getHoraInicio() + ":00-"
									+ r.getHoraFin() + ":00]\n",
							"Reserva solpada", JOptionPane.ERROR_MESSAGE);
					String nombreIns = String.valueOf(cbInstalaciones
							.getSelectedItem());
					Socio s = comprobarIdentidadSocio(horario, fi, ff,
							getIDInstalacion(nombreIns));
					if (s != null) {
						if (s.getDni().equalsIgnoreCase("CENTRO")) {
							JOptionPane
									.showMessageDialog(
											null,
											"Ya existe una reserva para el centro en ese horario\nImposible reservar",
											"Reserva para el Centro",
											JOptionPane.ERROR_MESSAGE);
							return true;
						}
						JOptionPane.showMessageDialog(
								null,
								"Socio afectado por la reserva para el centro: \n"
										+ "Nombre: " + s.getNombre() + "\n"
										+ "Apellidos: " + s.getApellidos()
										+ "\n" + "DNI: " + s.getDni() + "\n"
										+ "Teléfono de contacto: "
										+ s.getTelefono());
					} else {
						JOptionPane
								.showMessageDialog(null,
										"Ya existe una reserva para el centro en ese horario");
						return true;
					}

					borrarReserva(
							getIDInstalacion(String.valueOf(cbInstalaciones
									.getSelectedItem())), fi, ff, horario);

					JOptionPane
							.showMessageDialog(null,
									"Modificación realizada para actividad en el centro CORRECTA");
					dispose();
				} else {
					rel = true;
					JOptionPane
							.showMessageDialog(
									null,
									"Ya existe una reserva para esa instalacion con la misma fecha y el siguiente horario ["
											+ r.getHoraInicio()
											+ ":00-"
											+ r.getHoraFin() + ":00] ",
									"Reserva duplicada",
									JOptionPane.ERROR_MESSAGE);
				}
			}
		}

		return rel;
	}

	private void borrarReserva(int selectedItem, int fi, int ff, String horario)
			throws ClassNotFoundException, SQLException {
		Conexion con = new Conexion();
		con.conectar();
		PreparedStatement pst = null;
		try {
			pst = con.getCon().prepareStatement(
					"DELETE * FROM Reservas " + "WHERE ID_Instalacion = '"
							+ selectedItem + "' AND Horario_Reserva='"
							+ horario + "' AND Hora_Inicio='" + fi
							+ "' AND Hora_Fin='" + ff + "'");
			pst.executeUpdate();
			pst = con
					.getCon()
					.prepareStatement(
							"INSERT INTO RESERVAS"
									+ "(DNI_Socio, ID_Instalacion, Horario_Reserva,Hora_Inicio,Hora_Fin, Cobro) "
									+ "VALUES (?, ?, ?, ?, ?, ?)");
			pst.setString(1, "CENTRO");
			pst.setInt(2, getIDInstalacion(String.valueOf(cbInstalaciones
					.getSelectedItem())));
			pst.setString(3, horario);
			pst.setInt(4, fi);
			pst.setInt(5, ff);
			pst.setBoolean(6, true);

			pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			pst.close();
			con.desconectar();
		}

	}

	private JLabel getLbTipoCliente() {
		if (lbTipoCliente == null) {
			lbTipoCliente = new JLabel("Reservar para:");
			lbTipoCliente.setHorizontalAlignment(SwingConstants.CENTER);
			lbTipoCliente.setFont(new Font("Tahoma", Font.BOLD, 11));
			lbTipoCliente.setBounds(294, 9, 114, 14);
		}
		return lbTipoCliente;
	}

	private JRadioButton getRbSocio() {
		if (rbSocio == null) {
			rbSocio = new JRadioButton("Socio");
			rbSocio.setSelected(true);
			rbSocio.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					resetearDatos();
					btLupa.setEnabled(true);
					lbDNI.setEnabled(true);
					txDNI.setEnabled(true);
					btnSig.setEnabled(true);
					txtNombre.setEnabled(true);
					txtApellidos.setEnabled(true);
					lbnombre.setEnabled(true);
					lblApellidos.setEnabled(true);
					cbInstalaciones.setEnabled(false);
					spInicio.setModel(new SpinnerNumberModel(8, 8, 22, 1));
					spFin.setModel(new SpinnerNumberModel(9, 9, 23, 1));
					cbDias.setEnabled(false);
					cbMes.setEnabled(false);
					cbAno.setEnabled(false);
					spInicio.setEnabled(false);
					txInstalacionHora.setEnabled(false);
					spFin.setEnabled(false);
					panel_1.setEnabled(false);
				}
			});
			buttonGroup.add(rbSocio);
			rbSocio.setHorizontalAlignment(SwingConstants.CENTER);
			rbSocio.setBounds(225, 30, 109, 23);
		}
		return rbSocio;
	}

	private JRadioButton getRbCentro() {
		if (rbCentro == null) {
			rbCentro = new JRadioButton("Centro");
			rbCentro.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					resetearDatos();
					lbDNI.setEnabled(false);
					txDNI.setEnabled(false);
					btLupa.setEnabled(true);
					txtNombre.setEnabled(false);
					txtApellidos.setEnabled(false);
					lbnombre.setEnabled(false);
					lblApellidos.setEnabled(false);
					cbInstalaciones.setEnabled(true);
					spInicio.setModel(new SpinnerNumberModel(8, 8, 22, 1));
					spFin.setModel(new SpinnerNumberModel(9, 9, 23, 1));
					cbDias.setEnabled(false);
					cbMes.setEnabled(false);
					cbAno.setEnabled(false);
					spInicio.setEnabled(false);
					txInstalacionHora.setEnabled(false);
					spFin.setEnabled(false);
					panel_1.setEnabled(false);
				}
			});
			buttonGroup.add(rbCentro);
			rbCentro.setHorizontalAlignment(SwingConstants.CENTER);
			rbCentro.setBounds(368, 30, 109, 23);
		}
		return rbCentro;
	}

	public void resetearDatos() {
		txDNI.setText("");
		txtApellidos.setText("");
		txtNombre.setText("");
		cbInstalaciones.setSelectedIndex(0);
		txInstalacionHora.setText("");
		cbDias.setSelectedIndex(0);
		cbMes.setSelectedIndex(0);
		cbAno.setSelectedIndex(0);
	}

	public Socio comprobarIdentidadSocio(String horario, int inicio, int fin,
			int idInstalacion) throws ClassNotFoundException, SQLException {
		List<Reserva> reservas = gbd.listarReservas();
		Socio s;
		for (Reserva r : reservas) {
			if (r.getHorario().equals(horario) && r.getHoraInicio() == inicio
					&& r.getHoraFin() == fin
					&& r.getId_instalacion() == idInstalacion) {
				return buscarSocioPorDNI(r.getDNI());
			}
		}

		return null;
	}

	private Socio buscarSocioPorDNI(String dni) throws ClassNotFoundException,
			SQLException {
		List<Socio> socios = gbd.listarSocios();
		for (Socio s : socios) {
			if (s.getDni().equals(dni)) {
				return s;
			}
		}
		return null;
	}

	private int getIDInstalacion(String nombre) throws ClassNotFoundException,
			SQLException {
		List<Instalacion> instalaciones = gbd.listarInstalaciones();
		for (Instalacion i : instalaciones) {
			if (i.getNombre().equals(nombre)) {
				return i.getId();
			}
		}
		return 0;
	}
}
