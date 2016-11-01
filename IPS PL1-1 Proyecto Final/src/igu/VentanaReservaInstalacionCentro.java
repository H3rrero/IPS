package igu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import logica.Instalacion;
import logica.Reserva;
import logica.Socio;
import BussinesException.BusinessException;
import base.acciones.gestion.GestionApliIMP;
import base.datos.GestorBaseDatos;

public class VentanaReservaInstalacionCentro extends JDialog {

	final long MILLSECS_PER_DAY = 24 * 60 * 60 * 1000;

	java.util.Date hoy = new Date();
	List<Instalacion> instalaciones;
	private JPanel contentPane;
	private JPanel panel;
	private JLabel lbPeriodo;
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
	private GestorBaseDatos gbd;
	private JComboBox cbInstalaciones;
	private JLabel lbHora00;
	private JLabel label;
	private JSpinner spInicio;
	private JSpinner spFin;
	private GestionApliIMP gestion;
	private long diferencia;
	private List<Socio> socios;
	private JSpinner spinnerSemanas;
	private JSpinner spinnerAno;
	private JSpinner spinnerMes;
	
	
	/**
	 * Create the frame.
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public VentanaReservaInstalacionCentro(VentanaPrincipal vp)
			throws ClassNotFoundException, SQLException {
		gbd = new GestorBaseDatos();
		gestion = new GestionApliIMP();
		socios = gbd.listarSocios();
		instalaciones = gbd.listarInstalaciones();
		setResizable(false);
		setTitle("Formulario de reserva");
		setBackground(Color.WHITE);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 741, 452);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		contentPane.add(getPanel(), BorderLayout.CENTER);
		this.vp = vp;

	}

	private JPanel getPanel() throws ClassNotFoundException, SQLException {
		if (panel == null) {
			panel = new JPanel();
			panel.setBackground(Color.WHITE);
			panel.setLayout(null);
			panel.add(getLbPeriodo());
			panel.add(getPanel_1());
			panel.add(getPanel_2());
			panel.add(getBtnSig());
			panel.add(getBtnNewButton());
			
			spinnerSemanas = new JSpinner();
			spinnerSemanas.setModel(new SpinnerNumberModel(0, 0, 3, 1));
			spinnerSemanas.setBorder(new TitledBorder(null, "Semanas", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			spinnerSemanas.setBounds(206, 75, 85, 58);
			panel.add(spinnerSemanas);
			
			spinnerMes = new JSpinner();
			spinnerMes.setModel(new SpinnerNumberModel(0, 0, 11, 1));
			spinnerMes.setBorder(new TitledBorder(null, "Meses", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			spinnerMes.setBounds(317, 75, 85, 58);
			panel.add(spinnerMes);
			
			spinnerAno = new JSpinner();
			spinnerAno.setModel(new SpinnerNumberModel(0, 0, 2, 1));
			spinnerAno.setBorder(new TitledBorder(null, "A\u00F1os", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			spinnerAno.setBounds(422, 75, 93, 58);
			panel.add(spinnerAno);
		}
		return panel;
	}

	private JLabel getLbPeriodo() {
		if (lbPeriodo == null) {
			lbPeriodo = new JLabel("Periodo: ");
			lbPeriodo.setFont(new Font("Tahoma", Font.PLAIN, 13));
			lbPeriodo.setBounds(139, 77, 57, 14);
		}
		return lbPeriodo;
	}

	private JPanel getPanel_1() {
		if (panel_1 == null) {
			panel_1 = new JPanel();
			panel_1.setEnabled(false);
			panel_1.setBorder(new TitledBorder(null, "Fecha de reserva",
					TitledBorder.LEADING, TitledBorder.TOP, null, null));
			panel_1.setBackground(Color.WHITE);
			panel_1.setBounds(139, 244, 446, 76);
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
			panel_1.add(getSpInicio());
			panel_1.add(getSpFin());
		}
		return panel_1;
	}

	private JPanel getPanel_2() throws ClassNotFoundException, SQLException {
		if (panel_2 == null) {
			panel_2 = new JPanel();
			panel_2.setBorder(new TitledBorder(null, "Datos de registro",
					TitledBorder.LEADING, TitledBorder.TOP, null, null));
			panel_2.setBackground(Color.WHITE);
			panel_2.setBounds(196, 144, 319, 76);
			panel_2.setLayout(null);
			panel_2.add(getLbInstalacion());
			panel_2.add(getCbInstalaciones());

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
			btnSig.setBounds(242, 370, 155, 32);
			btnSig.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					try {

						if (reservarInstalacion() == (-1)) {
							return;
						}
						int id = getIDInstalacion(String
								.valueOf(cbInstalaciones.getSelectedItem()));
						int hi = Integer.parseInt(spInicio.getValue()
								.toString());
						int hf = Integer.parseInt(spFin.getValue().toString());
						
						if (cbInstalaciones.getSelectedIndex() == 0) {
							JOptionPane.showMessageDialog(null,
									"Se debe seleccionar una instalacion de las ofertadas",
									"Error selección actividad", JOptionPane.ERROR_MESSAGE);
							return;
						}
						JOptionPane.showMessageDialog(null,
								"La reserva se ha registrado con éxito",
								"Reserva Registrada", JOptionPane.PLAIN_MESSAGE);

						String name = (String) cbInstalaciones
									.getSelectedItem();
						
						double importe = 0;
						
						Calendar fechaFinal = Calendar.getInstance();
						
						Calendar fechaInicial = Calendar.getInstance();
						fechaInicial.set(Calendar.DAY_OF_MONTH, Integer.parseInt(cbDias.getSelectedItem().toString()));
						fechaInicial.set(Calendar.MONTH, Integer.parseInt(cbMes.getSelectedItem().toString()));
						fechaInicial.set(Calendar.YEAR, Integer.parseInt(cbAno.getSelectedItem().toString()));

						fechaFinal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(cbDias.getSelectedItem().toString()));
						fechaFinal.set(Calendar.MONTH, Integer.parseInt(cbMes.getSelectedItem().toString()));
						fechaFinal.set(Calendar.YEAR, Integer.parseInt(cbAno.getSelectedItem().toString()));

						fechaFinal.add(Calendar.YEAR, Integer.parseInt(spinnerAno.getValue().toString()));
						fechaFinal.add(Calendar.MONTH, Integer.parseInt(spinnerMes.getValue().toString()));
						fechaFinal.add(Calendar.DATE, 7*Integer.parseInt(spinnerSemanas.getValue().toString())-1);
						String aviso = "";
						
						for(Calendar today = fechaInicial; today.before(fechaFinal); today.add(Calendar.DATE, 7)){
							String fecha;
							if(today.get(Calendar.MONTH) == 0){

								fecha = today.get(Calendar.DATE)
										+ "/" + 12
										+ "/" + today.get(Calendar.YEAR);							
								
							}
							else{
								fecha = today.get(Calendar.DATE)
										+ "/" + today.get(Calendar.MONTH)
										+ "/" + today.get(Calendar.YEAR);

							}

							String horario = fecha;
							String[] horas= fecha.split("/");
							if(horas[1].equals("12")){
								horario = horas[0]+"/"+horas[1]+"/"+"2014";
							}
							
						
							aviso += reservaDuplicada(hi, hf, horario);

							gestion.añadirReserva("CENTRO",
									id, hi, hf, horario, true,importe, 1234);

							
						}					
						if(!aviso.equals("")){
							JOptionPane.showMessageDialog(null,
									aviso, "Aviso", JOptionPane.WARNING_MESSAGE);
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
			btnNewButton.setBounds(405, 370, 85, 32);
		}
		return btnNewButton;
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
//		if (horaFin - horaInicio > 2) {
//			JOptionPane
//					.showMessageDialog(
//							null,
//							"El plazo de una actividad debe estar comprendido entre 1-2 horas.\nRevise las horas indicadas.",
//							"Periodo de actividad no válido",
//							JOptionPane.ERROR_MESSAGE);
//			return -1;
//		}
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
				panel_1.setEnabled(true);
				cbDias.setEnabled(true);
				cbMes.setEnabled(true);
				cbAno.setEnabled(true);
				spInicio.setEnabled(true);
				spFin.setEnabled(true);
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
//			spInicio.addChangeListener(new ChangeListener() {
//				public void stateChanged(ChangeEvent arg0) {
//					spFin.setModel(new SpinnerNumberModel(((Integer) getSpInicio().getValue()) + 1, ((Integer) getSpInicio().getValue()) + 1, 
//							((Integer) getSpInicio().getValue()) + 2, 1));
//				}
//			});
			spInicio.setModel(new SpinnerNumberModel(8, 8, 22, 1));
			spInicio.setBounds(332, 13, 59, 20);
		}
		return spInicio;
	}

	private JSpinner getSpFin() {
		if (spFin == null) {
			spFin = new JSpinner();
			spFin.setEnabled(false);
			spFin.setModel(new SpinnerNumberModel(9, 9, 23, 1));
//			spFin.setModel(new SpinnerNumberModel(((Integer) getSpInicio().getValue()) + 1, ((Integer) getSpInicio().getValue()) + 1, 
//					((Integer) getSpInicio().getValue()) + 2, 1));
			spFin.setBounds(332, 43, 59, 20);
		}
		return spFin;
	}

	private int getIDInstalacion(String valueOf) throws ClassNotFoundException,
			SQLException {
		List<Instalacion> insta = gbd.listarInstalaciones();
		for (Instalacion ins : insta) {
			if (ins.getNombre().equals(valueOf)) {
				return ins.getId();
			}
		}
		return 0;
	}

	public boolean comprobarDNIBase(String dni) throws ClassNotFoundException,
			SQLException {
		List<Socio> socios = gbd.listarSocios();
		for (Socio s : socios) {
			if (s.getDni().equals(dni)) {
				return true;
			}
		}
		return false;
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

	
	public String reservaDuplicada(int fi, int ff, String horario) throws ClassNotFoundException, SQLException
	{   
		int finter=0;
		boolean rel=false;
		if(ff-fi==2)
			finter=ff-1;
		String aviso = "";
		
		List<Reserva> reservas = gbd.listarReservas();
		for (Reserva r : reservas) {
			if(r.getHorario().equals(horario)&& (r.getHoraInicio()==fi||r.getHoraInicio()==finter|| r.getHoraFin()
					==ff||r.getHoraFin()==finter))
			{
				
					aviso += "Se ha sustituido la reserva " + r.getCodigoReserva() + " en el horario  -->"+r.getHoraInicio()+":00-"+r.getHoraFin()
							+":00 a día " + r.getHorario() + 
							" para el usuario cuyo dni es " + r.getDNI() + "\n";
					try {
						gestion.EliminarReserva(r.getCodigoReserva());
					} catch (BusinessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					
				}
				
			}

		}
		
		return aviso;
	}
}
