package igu;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import BussinesException.BusinessException;
import base.acciones.gestion.GestionApli;
import base.acciones.gestion.GestionApliIMP;
import base.datos.Conexion;
import base.datos.GestorBaseDatos;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

import logica.Actividad;
import logica.Instalacion;
import logica.Reserva;
import logica.Socio;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;

import org.w3c.dom.CDATASection;

import java.awt.Font;

import javax.swing.JSpinner;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.SpinnerNumberModel;

public class VentanaCrearActividadPeriodica extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JLabel lbNombre;
	private JTextField txNombre;
	private JLabel lbID;
	private JTextField txID;
	private JButton btComprobar;
	private JPanel pnDatosActividad;
	private GestorBaseDatos gbd;
	private JLabel lbTic;
	private JPanel pnInstalacion;
	private JLabel lbSeleccioInstalacin;
	private JComboBox cbInstalaciones;
	private JPanel pnFechaActividad;
	private JComboBox cbDias;
	private JComboBox cbMes;
	private JComboBox cbAnio;
	private JLabel lbHoraInicio;
	private JLabel lbHoraFin;
	private JSpinner spInicio;
	private JSpinner spFin;
	private JPanel pnPlazas;
	private JRadioButton rbSinLimite;
	private JRadioButton rbConLimite;
	private JSpinner spinner;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JSpinner spinnerSemanas;
	private JSpinner spinnerMes;
	private JSpinner spinnerAno;
	private GestionApliIMP gestion;

	/**
	 * Create the dialog.
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public VentanaCrearActividadPeriodica(VentanaPrincipal vp)
			throws ClassNotFoundException, SQLException {
		setTitle("Crear actividad de caracter periodica");
		gbd = new GestorBaseDatos();
		setBounds(100, 100, 691, 466);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		contentPanel.add(getPnDatosActividad());
		contentPanel.add(getPnInstalacion());
		contentPanel.add(getPnFechaActividad());
		contentPanel.add(getPnPlazas());
		gestion = new GestionApliIMP();

		JButton btAceptar = new JButton("Aceptar");
		btAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {

					int id = getIDInstalacion(String.valueOf(cbInstalaciones
							.getSelectedItem()));
					int hi = Integer.parseInt(spInicio.getValue().toString());
					int hf = Integer.parseInt(spFin.getValue().toString());

					String name = (String) cbInstalaciones.getSelectedItem();

					double importe = 0;
					Calendar fechaFinal = Calendar.getInstance();

					Calendar fechaInicial = Calendar.getInstance();
					fechaInicial.set(Calendar.DAY_OF_MONTH, Integer
							.parseInt(cbDias.getSelectedItem().toString()));
					fechaInicial.set(Calendar.MONTH, Integer.parseInt(cbMes
							.getSelectedItem().toString()));
					fechaInicial.set(Calendar.YEAR, Integer.parseInt(cbAnio
							.getSelectedItem().toString()));

					fechaFinal.set(Calendar.DAY_OF_MONTH, Integer
							.parseInt(cbDias.getSelectedItem().toString()));
					fechaFinal.set(Calendar.MONTH, Integer.parseInt(cbMes
							.getSelectedItem().toString()));
					fechaFinal.set(Calendar.YEAR, Integer.parseInt(cbAnio
							.getSelectedItem().toString()));

					fechaFinal.add(Calendar.YEAR,
							Integer.parseInt(spinnerAno.getValue().toString()));
					fechaFinal.add(Calendar.MONTH,
							Integer.parseInt(spinnerMes.getValue().toString()));
					fechaFinal.add(Calendar.DATE, 7 * Integer
							.parseInt(spinnerSemanas.getValue().toString()) - 1);
					String aviso = "";

					int semanas = 0;
					for (Calendar today = fechaInicial; today
							.before(fechaFinal); today.add(Calendar.DATE, 7)) {
						semanas++;
					}

					fechaInicial.set(Calendar.DAY_OF_MONTH, Integer
							.parseInt(cbDias.getSelectedItem().toString()));
					fechaInicial.set(Calendar.MONTH, Integer.parseInt(cbMes
							.getSelectedItem().toString()));
					fechaInicial.set(Calendar.YEAR, Integer.parseInt(cbAnio
							.getSelectedItem().toString()));

					if (getRbSinLimite().isSelected()) {
						gestion.crearActividad(
								txNombre.getText().toString(),
								Integer.parseInt(getTxID().getText().toString()),
								10000, semanas);
					}
					if (getRbConLimite().isSelected()) {

						gestion.crearActividad(
								txNombre.getText().toString(),
								Integer.parseInt(getTxID().getText().toString()),
								Integer.parseInt(spinner.getValue().toString()),
								semanas);

					}

					importe = getPrecioIDInstalacion(id)
							* (Integer.parseInt(spFin.getValue().toString()) - Integer
									.parseInt(spInicio.getValue().toString()));

					for (Calendar today = fechaInicial; today
							.before(fechaFinal); today.add(Calendar.DATE, 7)) {
						String fecha;
						if (today.get(Calendar.MONTH) == 0) {

							fecha = today.get(Calendar.DATE) + "/" + 12 + "/"
									+ today.get(Calendar.YEAR);

						} else {
							fecha = today.get(Calendar.DATE) + "/"
									+ today.get(Calendar.MONTH) + "/"
									+ today.get(Calendar.YEAR);

						}

						String horario = fecha;
						String[] horas = fecha.split("/");
						if (horas[1].equals("12")) {
							horario = horas[0] + "/" + horas[1] + "/" + "2014";
						}

						int idInstalacion = getIDInstalacion(cbInstalaciones
								.getSelectedItem().toString());
						
						if (reservaDuplicada(Integer.parseInt(spInicio.getValue()
								.toString()), Integer.parseInt(spFin.getValue()
								.toString()), horario, getIDInstalacion(String
								.valueOf(cbInstalaciones.getSelectedItem())))) {
							return;
						}
						try {
							gestion.añadirReserva("CENTRO", id, Integer
									.parseInt(spInicio.getValue().toString()),
									Integer.parseInt(spFin.getValue()
											.toString()), horario, true,
									importe, Integer.parseInt(txID.getText()
											.toString()));

						} catch (BusinessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
					if (!aviso.equals("")) {
						JOptionPane.showMessageDialog(null, aviso, "Aviso",
								JOptionPane.WARNING_MESSAGE);
					}
					JOptionPane.showMessageDialog(null, "La creación de la actividad se ha registrado con éxito");
					dispose();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btAceptar.setBounds(213, 393, 89, 23);
		contentPanel.add(btAceptar);

		JButton btCancelar = new JButton("Cancelar");
		btCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btCancelar.setBounds(312, 393, 89, 23);
		contentPanel.add(btCancelar);
	}

	private int getPrecioIDInstalacion(int id) throws ClassNotFoundException,
			SQLException {
		List<Instalacion> instalaciones = gbd.listarInstalaciones();
		for (Instalacion i : instalaciones) {
			if (i.getId() == id) {
				return i.getPrecioHora();
			}
		}
		return 0;
	}

	private JLabel getLbNombre() {
		if (lbNombre == null) {
			lbNombre = new JLabel("Nombre:");
			lbNombre.setBounds(10, 30, 54, 14);
		}
		return lbNombre;
	}

	private JTextField getTxNombre() {
		if (txNombre == null) {
			txNombre = new JTextField();
			txNombre.setBounds(66, 25, 169, 24);
			txNombre.setColumns(10);
		}
		return txNombre;
	}

	private JLabel getLbID() {
		if (lbID == null) {
			lbID = new JLabel("ID de actividad:");
			lbID.setBounds(265, 30, 97, 14);
		}
		return lbID;
	}

	private JTextField getTxID() {
		if (txID == null) {
			txID = new JTextField();
			txID.setBounds(351, 24, 142, 25);
			txID.setColumns(10);
		}
		return txID;
	}

	private JButton getBtComprobar() {
		if (btComprobar == null) {
			btComprobar = new JButton("Comprobar");
			btComprobar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						if (txNombre.getText().toString().equals("")
								|| txID.getText().toString().equals("")) {
							JOptionPane.showMessageDialog(null,
									"Existen campos obligatorios sin rellenar",
									"Campos sin rellenar",
									JOptionPane.ERROR_MESSAGE);
							return;
						}
						if (txID.getText().toString().length() != 5) {
							JOptionPane.showMessageDialog(null,
									"El ID debe ser numérico y de longitud 5",
									"ID erroneo", JOptionPane.ERROR_MESSAGE);
							return;
						}
						if (!comprobarNombreEID(txNombre.getText().toString(),
								txID.getText().toString())) {
							JOptionPane
									.showMessageDialog(
											null,
											"Ya existe una actividad con ese nombre  o ID",
											"Error en el nombrado",
											JOptionPane.ERROR_MESSAGE);
							return;
						}
						lbTic.setVisible(true);
						lbSeleccioInstalacin.setEnabled(true);
						pnInstalacion.setEnabled(true);
						cbInstalaciones.setEnabled(true);

					} catch (NumberFormatException e1) {
						// TODO Auto-generated catch block

					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
			btComprobar.setBounds(526, 26, 108, 23);
		}
		return btComprobar;
	}

	private JPanel getPnDatosActividad() {
		if (pnDatosActividad == null) {
			pnDatosActividad = new JPanel();
			pnDatosActividad.setBorder(new TitledBorder(null,
					"Datos de actividad", TitledBorder.LEADING,
					TitledBorder.TOP, null, null));
			pnDatosActividad.setBounds(10, 11, 655, 103);
			pnDatosActividad.setLayout(null);
			pnDatosActividad.add(getLbNombre());
			pnDatosActividad.add(getTxNombre());
			pnDatosActividad.add(getLbID());
			pnDatosActividad.add(getTxID());
			pnDatosActividad.add(getBtComprobar());
			pnDatosActividad.add(getLbTic());
		}
		return pnDatosActividad;
	}

	private boolean comprobarNombreEID(String nombre, String id)
			throws ClassNotFoundException, SQLException {
		int newId = Integer.parseInt(id);
		List<Actividad> actividades = gbd.listarActividades();
		for (Actividad a : actividades) {
			if (a.getNombre().equalsIgnoreCase(nombre)
					|| a.getId_actividad() == newId) {
				return false;
			}
		}
		return true;
	}

	private JLabel getLbTic() {
		if (lbTic == null) {
			lbTic = new JLabel("");
			lbTic.setVisible(false);
			lbTic.setIcon(new ImageIcon(VentanaCrearActividadPeriodica.class
					.getResource("/img/Imagen1.png")));
			lbTic.setBounds(275, 44, 54, 48);
		}
		return lbTic;
	}

	private JPanel getPnInstalacion() throws ClassNotFoundException,
			SQLException {
		if (pnInstalacion == null) {
			pnInstalacion = new JPanel();
			pnInstalacion.setEnabled(false);
			pnInstalacion.setBorder(new TitledBorder(null,
					"Selecci\u00F3n de instalaci\u00F3n", TitledBorder.LEADING,
					TitledBorder.TOP, null, null));
			pnInstalacion.setBounds(10, 119, 655, 78);
			pnInstalacion.setLayout(null);
			pnInstalacion.add(getLbSeleccioInstalacin());
			pnInstalacion.add(getCbInstalaciones());
		}
		return pnInstalacion;
	}

	private JLabel getLbSeleccioInstalacin() {
		if (lbSeleccioInstalacin == null) {
			lbSeleccioInstalacin = new JLabel(
					"Seleccione la instalaci\u00F3n: ");
			lbSeleccioInstalacin.setEnabled(false);
			lbSeleccioInstalacin.setBounds(242, 11, 136, 14);
		}
		return lbSeleccioInstalacin;
	}

	private JComboBox getCbInstalaciones() throws ClassNotFoundException,
			SQLException {
		if (cbInstalaciones == null) {
			cbInstalaciones = new JComboBox();
			cbInstalaciones.setEnabled(false);
			cbInstalaciones.setBounds(201, 36, 207, 31);
			List<Instalacion> instalaciones = gbd.listarInstalaciones();
			for (Instalacion i : instalaciones) {
				cbInstalaciones.addItem(String.valueOf(i.getNombre()));
			}
			cbInstalaciones.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					pnFechaActividad.setEnabled(true);
					cbDias.setEnabled(true);
					cbMes.setEnabled(true);
					cbAnio.setEnabled(true);
					spInicio.setEnabled(true);
					spFin.setEnabled(true);
					lbHoraInicio.setEnabled(true);
					lbHoraFin.setEnabled(true);
				}
			});
		}
		return cbInstalaciones;
	}

	private JPanel getPnFechaActividad() {
		if (pnFechaActividad == null) {
			pnFechaActividad = new JPanel();
			pnFechaActividad.setEnabled(false);
			pnFechaActividad.setBorder(new TitledBorder(null,
					"Fecha/Horario de la actividad", TitledBorder.LEADING,
					TitledBorder.TOP, null, null));
			pnFechaActividad.setBounds(10, 201, 655, 103);
			pnFechaActividad.setLayout(null);
			pnFechaActividad.add(getCbDias());
			pnFechaActividad.add(getCbMes());
			pnFechaActividad.add(getCbAnio());
			pnFechaActividad.add(getLbHoraInicio());
			pnFechaActividad.add(getLbHoraFin());
			pnFechaActividad.add(getSpInicio());
			pnFechaActividad.add(getSpFin());

			JLabel label = new JLabel("Periodo: ");
			label.setFont(new Font("Tahoma", Font.PLAIN, 13));
			label.setBounds(321, 33, 57, 14);
			pnFechaActividad.add(label);

			spinnerSemanas = new JSpinner();
			spinnerSemanas.setModel(new SpinnerNumberModel(new Integer(0),
					new Integer(0), null, new Integer(1)));
			spinnerSemanas.setBorder(new TitledBorder(null, "Semanas",
					TitledBorder.LEADING, TitledBorder.TOP, null, null));
			spinnerSemanas.setBounds(388, 31, 80, 58);
			pnFechaActividad.add(spinnerSemanas);

			spinnerMes = new JSpinner();
			spinnerMes.setModel(new SpinnerNumberModel(new Integer(0),
					new Integer(0), null, new Integer(1)));
			spinnerMes.setBorder(new TitledBorder(null, "Meses",
					TitledBorder.LEADING, TitledBorder.TOP, null, null));
			spinnerMes.setBounds(478, 31, 83, 58);
			pnFechaActividad.add(spinnerMes);

			spinnerAno = new JSpinner();
			spinnerAno.setModel(new SpinnerNumberModel(new Integer(0),
					new Integer(0), null, new Integer(1)));
			spinnerAno.setBorder(new TitledBorder(null, "A\u00F1os",
					TitledBorder.LEADING, TitledBorder.TOP, null, null));
			spinnerAno.setBounds(571, 31, 75, 58);
			pnFechaActividad.add(spinnerAno);
		}
		return pnFechaActividad;
	}

	private JComboBox getCbDias() {
		if (cbDias == null) {
			cbDias = new JComboBox();
			cbDias.setModel(new DefaultComboBoxModel(new String[] { "1", "2",
					"3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13",
					"14", "15", "16", "17", "18", "19", "20", "21", "22", "23",
					"24", "25", "26", "27", "28", "29", "30", "31" }));
			cbDias.setEnabled(false);
			cbDias.setBounds(55, 69, 42, 20);
		}
		return cbDias;
	}

	private JComboBox getCbMes() {
		if (cbMes == null) {
			cbMes = new JComboBox();
			cbMes.setModel(new DefaultComboBoxModel(new String[] { "1", "2",
					"3", "4", "5", "6", "7", "8", "9", "10", "11", "12" }));
			cbMes.setEnabled(false);
			cbMes.setBounds(122, 69, 59, 20);
			cbMes.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					pnPlazas.setEnabled(true);
					rbConLimite.setEnabled(true);
					rbSinLimite.setEnabled(true);
				}
			});
		}
		return cbMes;
	}

	private JComboBox getCbAnio() {
		if (cbAnio == null) {
			cbAnio = new JComboBox();
			cbAnio.setEnabled(false);
			cbAnio.setBounds(191, 69, 73, 20);
			for (int i = 2014; i <= 2030; i++) {
				cbAnio.addItem(String.valueOf(i));
			}
		}
		return cbAnio;
	}

	private JLabel getLbHoraInicio() {
		if (lbHoraInicio == null) {
			lbHoraInicio = new JLabel("Hora Inicio:");
			lbHoraInicio.setFont(new Font("Tahoma", Font.PLAIN, 13));
			lbHoraInicio.setBounds(10, 33, 73, 14);
		}
		return lbHoraInicio;
	}

	private JLabel getLbHoraFin() {
		if (lbHoraFin == null) {
			lbHoraFin = new JLabel("Hora Fin:");
			lbHoraFin.setFont(new Font("Tahoma", Font.PLAIN, 13));
			lbHoraFin.setBounds(149, 33, 59, 14);
		}
		return lbHoraFin;
	}

	private JSpinner getSpInicio() {
		if (spInicio == null) {
			spInicio = new JSpinner();
			spInicio.setModel(new SpinnerNumberModel(8, 8, 23, 1));
			spInicio.setEnabled(false);
			spInicio.setBounds(82, 31, 42, 20);
		}
		return spInicio;
	}

	private JSpinner getSpFin() {
		if (spFin == null) {
			spFin = new JSpinner();
			spFin.setModel(new SpinnerNumberModel(9, 9, 23, 1));
			spFin.setEnabled(false);
			spFin.setBounds(206, 31, 59, 20);
		}
		return spFin;
	}

	private JPanel getPnPlazas() {
		if (pnPlazas == null) {
			pnPlazas = new JPanel();
			pnPlazas.setEnabled(false);
			pnPlazas.setBorder(new TitledBorder(null, "Numero de plazas",
					TitledBorder.LEADING, TitledBorder.TOP, null, null));
			pnPlazas.setBounds(10, 315, 655, 56);
			pnPlazas.setLayout(null);
			pnPlazas.add(getRbSinLimite());
			pnPlazas.add(getRbConLimite());
			pnPlazas.add(getSpinner());
		}
		return pnPlazas;
	}

	private JRadioButton getRbSinLimite() {
		if (rbSinLimite == null) {
			rbSinLimite = new JRadioButton("Sin l\u00EDmite");
			rbSinLimite.setSelected(true);
			rbSinLimite.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					spinner.setEnabled(false);
				}
			});
			rbSinLimite.setEnabled(false);
			buttonGroup.add(rbSinLimite);
			rbSinLimite.setBounds(119, 26, 79, 23);
		}
		return rbSinLimite;
	}

	private JRadioButton getRbConLimite() {
		if (rbConLimite == null) {
			rbConLimite = new JRadioButton("Con l\u00EDmite");
			rbConLimite.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					spinner.setEnabled(true);
				}
			});
			rbConLimite.setEnabled(false);
			buttonGroup.add(rbConLimite);
			rbConLimite.setBounds(339, 26, 93, 23);
		}
		return rbConLimite;
	}

	private JSpinner getSpinner() {
		if (spinner == null) {
			spinner = new JSpinner();
			spinner.setEnabled(false);
			spinner.setBounds(438, 27, 44, 22);
		}
		return spinner;
	}

	public boolean reservaDuplicada(int fi, int ff, String horario,
			int idInstalacion) throws ClassNotFoundException, SQLException {
		int finter = 0;
		boolean rel = false;
		if (ff - fi == 2)
			finter = ff - 1;
		List<Reserva> reservas = gbd.listarReservas();
		for (Reserva r : reservas) {
			if (r.getId_instalacion() == idInstalacion) {
				if (r.getHorario().equals(horario)
						&& (r.getHoraInicio() == fi
								|| r.getHoraInicio() == finter
								|| r.getHoraFin() == ff || r.getHoraFin() == finter)
						&& r.isCancelada() == false) {

					rel = true;
					JOptionPane.showMessageDialog(
							null,
							"Existe una reserva de un socio en ese periodo ["
									+ r.getHoraInicio() + ":00-"
									+ r.getHoraFin() + ":00]\n",
							"Reserva solpada", JOptionPane.ERROR_MESSAGE);
					String nombreIns = String.valueOf(cbInstalaciones
							.getSelectedItem());

					Calendar fechaFinal = Calendar.getInstance();

					Calendar fechaInicial = Calendar.getInstance();
					fechaInicial.set(Calendar.DAY_OF_MONTH, Integer
							.parseInt(cbDias.getSelectedItem().toString()));
					fechaInicial.set(Calendar.MONTH, Integer.parseInt(cbMes
							.getSelectedItem().toString()));
					fechaInicial.set(Calendar.YEAR, Integer.parseInt(cbAnio
							.getSelectedItem().toString()));

					fechaFinal.set(Calendar.DAY_OF_MONTH, Integer
							.parseInt(cbDias.getSelectedItem().toString()));
					fechaFinal.set(Calendar.MONTH, Integer.parseInt(cbMes
							.getSelectedItem().toString()));
					fechaFinal.set(Calendar.YEAR, Integer.parseInt(cbAnio
							.getSelectedItem().toString()));

					fechaFinal.add(Calendar.YEAR,
							Integer.parseInt(spinnerAno.getValue().toString()));
					fechaFinal.add(Calendar.MONTH,
							Integer.parseInt(spinnerMes.getValue().toString()));
					fechaFinal
							.add(Calendar.DATE, 7 * Integer
									.parseInt(spinnerSemanas.getValue()
											.toString()) - 1);
					String aviso = "";

					for (Calendar today = fechaInicial; today
							.before(fechaFinal); today.add(Calendar.DATE, 7)) {
						String fecha;
						if (today.get(Calendar.MONTH) == 0) {

							fecha = today.get(Calendar.DATE) + "/" + 12 + "/"
									+ today.get(Calendar.YEAR);

						} else {
							fecha = today.get(Calendar.DATE) + "/"
									+ today.get(Calendar.MONTH) + "/"
									+ today.get(Calendar.YEAR);

						}

						String[] horas = fecha.split("/");
						if (horas[1].equals("12")) {
							horario = horas[0] + "/" + horas[1] + "/" + "2014";
						}

						Socio s = comprobarIdentidadSocio(fecha, fi, ff,
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
							JOptionPane.showMessageDialog(null,
									"Socio afectado por la reserva para el centro: \n"
											+ "Nombre: " + s.getNombre() + "\n"
											+ "Apellidos: " + s.getApellidos()
											+ "\n" + "DNI: " + s.getDni()
											+ "\n" + "Teléfono de contacto: "
											+ s.getTelefono());
						} else {
							JOptionPane
									.showMessageDialog(null,
											"Ya existe una reserva para el centro en ese horario");
							return true;
						}

						reemplazarReserva(
								getIDInstalacion(String.valueOf(cbInstalaciones
										.getSelectedItem())), fi, ff, horario);

						gestion = new GestionApliIMP();

					}

					dispose();
				} 
//				else {
//					rel = true;
//					JOptionPane
//							.showMessageDialog(
//									null,
//									"Ya existe una reserva para esa instalacion con la misma fecha y el siguiente horario ["
//											+ r.getHoraInicio()
//											+ ":00-"
//											+ r.getHoraFin() + ":00] ",
//									"Reserva duplicada",
//									JOptionPane.ERROR_MESSAGE);
//				}
			}
		}

		return rel;
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

	private void reemplazarReserva(int selectedItem, int fi, int ff,
			String horario) throws ClassNotFoundException, SQLException {
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
}
