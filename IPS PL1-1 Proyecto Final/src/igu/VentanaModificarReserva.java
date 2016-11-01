package igu;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import logica.Instalacion;
import logica.Reserva;
import BussinesException.BusinessException;
import base.acciones.gestion.GestionApli;
import base.acciones.gestion.GestionApliIMP;
import base.datos.GestorBaseDatos;

public class VentanaModificarReserva extends JDialog {

	private int filas=0;
	java.util.Date hoy = new Date();
	final long MILLSECS_PER_DAY = 24*60*60*1000;
	private final JPanel contentPanel = new JPanel();
	private JLabel lbDNISocio;
	private JButton btBuscar;
	private JScrollPane scTable;
	private JTable tableReserva;
	private ModeloNoEditable modeloTabla;
	private JButton btModificarReserva;
	private JButton btCancelar;
	private GestorBaseDatos gbd;
	private String DNI;
	private String Horario;
	private int instalacion;
	private GestionApli gestion;
	private Integer ID;
	private VentanaPrincipal vp;
	private JLabel lblIdDeLa;
	private JTextField textIDReserva;
	private JLabel lblNuevaInstalacion;
	private JPanel panel;
	private JComboBox comboBoxDIA;
	private JComboBox comboBoxMES;
	private JComboBox comboBoxAÑO;
	private JLabel lblHoraInicio;
	private JLabel lblHoraFin;
	private JSpinner spinnerHoraInicio;
	private JSpinner spinnerHoraFin;
	private JLabel lblh;
	private JLabel lblh_1;
	private int IDInstalacionEscogida;
	private JTextField txDNI;
	private JComboBox cbInstalaciones;
	private JLabel lbPrecioNuevaInstalacion;
	private JTextField txPrecioNuevaInstalacion;
	/**
	 * Launch the application.
	 */
	

	/**
	 * Create the dialog.
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public VentanaModificarReserva(VentanaPrincipal vp) throws ClassNotFoundException, SQLException {
		gbd= new GestorBaseDatos();
		gestion = new GestionApliIMP();
		setTitle("Modificar Reserva");
		setBounds(100, 100, 928, 430);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		contentPanel.add(getLbDNISocio());
		contentPanel.add(getBtBuscar());
		contentPanel.add(getScTable());
		contentPanel.add(getBtModificarReserva());
		contentPanel.add(getBtCancelar());
		contentPanel.add(getLblIdDeLa());
		contentPanel.add(getTextIDReserva());
		contentPanel.add(getLblNuevaInstalacion());
		contentPanel.add(getPanel());
		contentPanel.add(getTxDNI());
		contentPanel.add(getCbInstalaciones());
		contentPanel.add(getLbPrecioNuevaInstalacion());
		contentPanel.add(getTxPrecioNuevaInstalacion());
	
	}
	private JLabel getLbDNISocio() {
		if (lbDNISocio == null) {
			lbDNISocio = new JLabel("DNI del Cliente:");
			lbDNISocio.setBounds(254, 27, 95, 14);
		}
		return lbDNISocio;
	}
	private JButton getBtBuscar() {
		if (btBuscar == null) {
			btBuscar = new JButton("");
			btBuscar.setBorder(null);
			btBuscar.setIcon(new ImageIcon(VentanaModificarReserva.class.getResource("/img/lupab.jpg")));
			btBuscar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					limpiaTabla();;
					String campo = txDNI.getText().toString();
					List<Reserva> reservas;
					try {
						reservas = gbd.listarReservas();
						for (Reserva r: reservas) {
							if(r.getDNI().equalsIgnoreCase(campo) && r.isCobro()==false && r.isCancelada()==false){
								Object[] nuevaLinea = new Object[5];
								nuevaLinea[0] = r.getDNI();
								nuevaLinea[1] = r.getHorario();
								nuevaLinea[2] = r.getHoraInicio();
								nuevaLinea[3] = r.getHoraFin();
								nuevaLinea[4] = r.getCodigoReserva();
								modeloTabla.addRow(nuevaLinea);
							}
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
			btBuscar.setBounds(555, 11, 45, 42);
		}
		return btBuscar;
	}
	private JScrollPane getScTable() throws ClassNotFoundException, SQLException {
		if (scTable == null) {
			scTable = new JScrollPane();
			
			scTable.setBounds(36, 78, 838, 113);
			scTable.setViewportView(getTableReserva());
		}
		return scTable;
	}
	private JTable getTableReserva() throws ClassNotFoundException, SQLException {
		if (tableReserva == null) {
			String[] nombreColumnas = {"DNI_Socio", "Fecha Reserva","Hora De Inicio","Hora de Fin","ID Reserva"};
			modeloTabla = new ModeloNoEditable(nombreColumnas,0);
			tableReserva = new JTable(modeloTabla);
			tableReserva.addMouseListener(new MouseAdapter() {
				

				@Override
				public void mouseClicked(MouseEvent arg0) {
					if(tableReserva.getRowCount() > 0){
						btModificarReserva.setEnabled(true);
						for(int i=0; i<modeloTabla.getRowCount();i++)
						{
							
							if(tableReserva.getSelectedRow()==i){
							
							ID = (Integer) modeloTabla.getValueAt(i,4);
							textIDReserva.setText(" "+ID);
							
							}
						}
					}
				}
			});
			
		}
		return tableReserva;
	}
	private JButton getBtModificarReserva() {
		if (btModificarReserva == null) {
			btModificarReserva = new JButton("Modificar reserva");
			btModificarReserva.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					
					reservarInstalacion();
					String hora=comboBoxDIA.getSelectedItem().toString()+"/"+comboBoxMES.getSelectedItem().toString()+"/"+comboBoxAÑO.getSelectedItem().toString();
					int hi=Integer.parseInt(spinnerHoraInicio.getValue().toString());
					int hf=Integer.parseInt(spinnerHoraFin.getValue().toString());
					
					
				try {
					
					if (reservaDuplicada(ID,IDInstalacionEscogida,hi, hf, hora)==true) {
						
						return;
					}
					else if(reservarInstalacion()==true){
						gestion.modificarReserva( IDInstalacionEscogida, hi,hf,hora, ID);
						JOptionPane.showMessageDialog(null,"Ha realizado su modificacion con exito" );
						dispose();
					}
					
					
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
			btModificarReserva.setEnabled(false);
			btModificarReserva.setBounds(320, 339, 148, 41);
		}
		return btModificarReserva;
	}
	private JButton getBtCancelar() {
		if (btCancelar == null) {
			btCancelar = new JButton("Cancelar");
			btCancelar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					dispose();
				}
			});
			btCancelar.setBounds(480, 339, 89, 41);
		}
		return btCancelar;
	}
	public String getNombreActividad(int id) throws ClassNotFoundException, SQLException{
		List<Instalacion> act = gbd.listarInstalaciones();
		for(Instalacion ins: act){
			if(ins.getId()==id){
				
				return ins.getNombre();
			}
		}
		return null;
	}
	private JLabel getLblIdDeLa() {
		if (lblIdDeLa == null) {
			lblIdDeLa = new JLabel("ID Reserva a modificar:");
			lblIdDeLa.setBounds(66, 208, 149, 16);
		}
		return lblIdDeLa;
	}
	private JTextField getTextIDReserva() {
		if (textIDReserva == null) {
			textIDReserva = new JTextField();
			textIDReserva.setEditable(false);
			textIDReserva.setBounds(240, 205, 116, 22);
			textIDReserva.setColumns(10);
		}
		return textIDReserva;
	}
	private JLabel getLblNuevaInstalacion() {
		if (lblNuevaInstalacion == null) {
			lblNuevaInstalacion = new JLabel("Cambio instalacion :");
			lblNuevaInstalacion.setBounds(66, 236, 149, 16);
		}
		return lblNuevaInstalacion;
	}
	
	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Nuevo horario de reserva", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			panel.setBounds(366, 203, 467, 125);
			panel.setLayout(null);
			panel.add(getComboBoxDIA());
			panel.add(getComboBoxMES());
			panel.add(getComboBox_1());
			panel.add(getLblHoraInicio());
			panel.add(getLblHoraFin());
			panel.add(getSpinnerHoraInicio());
			panel.add(getSpinnerHoraFin());
			panel.add(getLblh());
			panel.add(getLblh_1());
		}
		return panel;
	}
	private JComboBox getComboBoxDIA() {
		if (comboBoxDIA == null) {
			comboBoxDIA = new JComboBox();
			comboBoxDIA.setEnabled(false);
			
			comboBoxDIA.setBounds(10, 57, 54, 22);
			String[] Mes31= new String[]{"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"};
			comboBoxDIA.setModel(new DefaultComboBoxModel(Mes31));
		}
		return comboBoxDIA;
	}
	private JComboBox getComboBoxMES() {
		if (comboBoxMES == null) {
			comboBoxMES = new JComboBox();
			comboBoxMES.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"}));
			comboBoxMES.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					comboBoxDIA.setEnabled(true);
String[] febrero= new String[]{"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28"};
String[] Mes30= new String[]{"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30"};
String[] Mes31= new String[]{"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"};

		if(comboBoxMES.getSelectedItem().toString().equals("2"))
		{
			comboBoxDIA.setModel(new DefaultComboBoxModel(febrero));
		}
		else if(comboBoxMES.getSelectedItem().toString().equals("1")
				||comboBoxMES.getSelectedItem().toString().equals("3")||comboBoxMES.getSelectedItem().toString().equals("5")||
				comboBoxMES.getSelectedItem().toString().equals("7")||comboBoxMES.getSelectedItem().toString().equals("8")
				||comboBoxMES.getSelectedItem().toString().equals("10")||comboBoxMES.getSelectedItem().toString().equals("12"))
		{
			comboBoxDIA.setModel(new DefaultComboBoxModel(Mes31));
		}
		else
		{
			comboBoxDIA.setModel(new DefaultComboBoxModel(Mes30));
		}
				}
			});
			comboBoxMES.setBounds(76, 57, 68, 22);
		}
		return comboBoxMES;
	}
	private JComboBox getComboBox_1() {
		if (comboBoxAÑO == null) {
			comboBoxAÑO = new JComboBox();
			String[] años= new String[86];
			
			for(int i=14;i<99;i++)
			{
				años[i-14]="20"+i;
			}
			comboBoxAÑO.setModel(new DefaultComboBoxModel(años));
			comboBoxAÑO.setBounds(156, 57, 86, 22);
			
		}
		return comboBoxAÑO;
	}
	private JLabel getLblHoraInicio() {
		if (lblHoraInicio == null) {
			lblHoraInicio = new JLabel("Hora Inicio:");
			lblHoraInicio.setBounds(256, 46, 68, 16);
		}
		return lblHoraInicio;
	}
	private JLabel getLblHoraFin() {
		if (lblHoraFin == null) {
			lblHoraFin = new JLabel("Hora Fin:");
			lblHoraFin.setBounds(256, 75, 56, 16);
		}
		return lblHoraFin;
	}
	private JSpinner getSpinnerHoraInicio() {
		if (spinnerHoraInicio == null) {
			spinnerHoraInicio = new JSpinner();
			spinnerHoraInicio.setModel(new SpinnerNumberModel(8, 8, 22, 1));
			spinnerHoraInicio.setBounds(341, 43, 68, 22);
			spinnerHoraInicio.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent arg0) {
					spinnerHoraFin.setModel(new SpinnerNumberModel(((Integer) getSpinnerHoraInicio().getValue()) + 1, ((Integer) getSpinnerHoraInicio().getValue()) + 1, 
							((Integer) getSpinnerHoraInicio().getValue()) + 2, 1));
				}
			});
		}
		return spinnerHoraInicio;
	}
	private JSpinner getSpinnerHoraFin() {
		if (spinnerHoraFin == null) {
			spinnerHoraFin = new JSpinner();
			spinnerHoraFin.setModel(new SpinnerNumberModel(9, 9, 23, 1));
			spinnerHoraFin.setBounds(340, 72, 69, 22);
		}
		return spinnerHoraFin;
	}
	private JLabel getLblh() {
		if (lblh == null) {
			lblh = new JLabel(":00h");
			lblh.setBounds(411, 46, 56, 16);
		}
		return lblh;
	}
	private JLabel getLblh_1() {
		if (lblh_1 == null) {
			lblh_1 = new JLabel(":00h");
			lblh_1.setBounds(411, 75, 56, 16);
		}
		return lblh_1;
	}
	public boolean reservarInstalacion(){
		int dia= Integer.parseInt(String.valueOf(comboBoxDIA.getSelectedItem())); 
		int mes = Integer.parseInt(String.valueOf(comboBoxMES.getSelectedItem())); 
		int año = Integer.parseInt(String.valueOf(comboBoxAÑO.getSelectedItem()));
		Calendar calendar = new GregorianCalendar(año,mes-1,dia+1);
		java.sql.Date fecha= new java.sql.Date(calendar.getTimeInMillis());
		long diferencia = (fecha.getTime() - hoy.getTime())/MILLSECS_PER_DAY;
		int horaInicio = Integer.parseInt(spinnerHoraInicio.getValue().toString());
		int horaFin = Integer.parseInt(spinnerHoraFin.getValue().toString());

		boolean rel=false;
		if(cbInstalaciones.getSelectedItem().toString().equals("") ||textIDReserva.getText().toString().equals("")){
			JOptionPane.showMessageDialog(null, "Existen campos obligatorios sin rellenar","Campos obligatorios", JOptionPane.ERROR_MESSAGE);
			rel=false;
		}
		
		
		else if(diferencia < 0){
			JOptionPane.showMessageDialog(null, "La fecha de reserva debe ser posterior a la actual","Fuera de plazo", JOptionPane.ERROR_MESSAGE);
			rel=false;
		}
		else if(diferencia == 0){
			JOptionPane.showMessageDialog(null, "No se permite reservar con menos de 24h de antelación al inicio de la actividad","Fuera de plazo", JOptionPane.ERROR_MESSAGE);
			rel=false;
		}
		else if(diferencia > 15 ){
			JOptionPane.showMessageDialog(null, "La reserva excede el periodo de dos semanas","Fuera de plazo", JOptionPane.ERROR_MESSAGE);
			rel=false;
		}

		
		else if(horaFin - horaInicio > 2 || horaFin - horaInicio < 1){
			JOptionPane.showMessageDialog(null, "El plazo de una actividad debe estar comprendido entre 1-2 horas.\nRevise las horas indicadas.","Periodo de actividad no válido", JOptionPane.ERROR_MESSAGE);
			rel=false;
		}
		else 
			rel=true;
		return rel;
		
	}
	private JTextField getTxDNI() {
		if (txDNI == null) {
			txDNI = new JTextField();
			txDNI.setHorizontalAlignment(SwingConstants.CENTER);
			txDNI.setBounds(340, 24, 204, 23);
			txDNI.setColumns(10);
		}
		return txDNI;
	}
	private JComboBox getCbInstalaciones() throws ClassNotFoundException, SQLException {
		if (cbInstalaciones == null) {
			cbInstalaciones = new JComboBox();
			cbInstalaciones.setBounds(240, 233, 116, 22);
		}
		final List<Instalacion> instalaciones;

		instalaciones = gbd.listarInstalaciones();
		cbInstalaciones.addItem("----------");
		for(Instalacion inst: instalaciones){
			cbInstalaciones.addItem(inst.getNombre());
		}
		cbInstalaciones.addActionListener(new ActionListener() {
			

			public void actionPerformed(ActionEvent arg0) {
				
				for(int i=0;i<instalaciones.size();i++)
				{
					if(instalaciones.get(i).getNombre().equals(cbInstalaciones.getSelectedItem()))
					{
						IDInstalacionEscogida= instalaciones.get(i).getId();
						txPrecioNuevaInstalacion.setText(instalaciones.get(i).getPrecioHora() + "€");
					}
				}
			}
		});
		return cbInstalaciones;
	}
	private JLabel getLbPrecioNuevaInstalacion() {
		if (lbPrecioNuevaInstalacion == null) {
			lbPrecioNuevaInstalacion = new JLabel("Precio/h nueva instalacion:");
			lbPrecioNuevaInstalacion.setBounds(61, 263, 154, 16);
		}
		return lbPrecioNuevaInstalacion;
	}
	private JTextField getTxPrecioNuevaInstalacion() {
		if (txPrecioNuevaInstalacion == null) {
			txPrecioNuevaInstalacion = new JTextField();
			txPrecioNuevaInstalacion.setHorizontalAlignment(SwingConstants.CENTER);
			txPrecioNuevaInstalacion.setEnabled(false);
			txPrecioNuevaInstalacion.setEditable(false);
			txPrecioNuevaInstalacion.setBounds(240, 261, 116, 20);
			txPrecioNuevaInstalacion.setColumns(10);
		}
		return txPrecioNuevaInstalacion;
	}
	public boolean reservaDuplicada(int id,int id_instalacion,int fi, int ff, String horario) throws ClassNotFoundException, SQLException
	{   int finter=0;
		boolean rel=false;
		if(ff-fi==2)
			finter=ff-1;
		List<Reserva> reservas = gbd.listarReservas();
		for (Reserva r : reservas) {
			
			if(r.getCodigoReserva()==id&&r.getHorario().equals(horario)&&r.getId_instalacion()== id_instalacion)
				return false;
			if(r.getHorario().equals(horario)&& (r.getHoraInicio()==fi||r.getHoraInicio()==finter|| r.getHoraFin()
					==ff||r.getHoraFin()==finter)&&r.isCancelada()==false)
			{
				rel=true;
				JOptionPane
				.showMessageDialog(
						null,
						"Ya existe una reserva para esa instalacion con la misma fecha y el siguiente horario  -->"+r.getHoraInicio()+":00-"+r.getHoraFin()+":00",
						"Reserva duplicada",
						JOptionPane.ERROR_MESSAGE);
		
			}

		}
		
		return rel;
	}
	
	void limpiaTabla(){
        try{
        	ModeloNoEditable temp = (ModeloNoEditable) tableReserva.getModel();
            int a =temp.getRowCount();
            for(int i=0; i<a; i++)
                temp.removeRow(0); 
            }catch(Exception e){
            System.out.println(e);
        }
    }
	
}
