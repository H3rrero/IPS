package igu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import logica.Instalacion;
import logica.Reserva;
import base.datos.Conexion;
import base.datos.GestorBaseDatos;


public class VentanaMostrarHorarios extends JDialog {

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
	private JComboBox cbInstalaciones;
	private JComboBox cbDias;
	private JComboBox cbMes;
	private JComboBox cbAno;
	private GestorBaseDatos gbd;
	private JButton btAceptar;

	
	/**
	 * Create the dialog.
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public VentanaMostrarHorarios() throws ClassNotFoundException, SQLException {
		setTitle("Listado de horarios");
		con = new Conexion();
		gbd = new GestorBaseDatos();
		setBounds(100, 100, 658, 376);
		getContentPane().setLayout(new BorderLayout());
		panelPrincipal.setToolTipText("\"Buscar\"");
		panelPrincipal.setBackground(Color.LIGHT_GRAY);
		panelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(panelPrincipal, BorderLayout.CENTER);
		panelPrincipal.setLayout(null);
		{
			JScrollPane spTabla = new JScrollPane();
			spTabla.setBounds(5, 5, 330, 288);
			panelPrincipal.add(spTabla);
			{
				modeloTabla = new ModeloNoEditable(new String[] {"Franja horaria", "Disponibilidad" }, 0);
				tablaListadoInstalaciones = new JTable();
				tablaListadoInstalaciones.setModel(modeloTabla);
				tablaListadoInstalaciones.setBackground(Color.WHITE);
				spTabla.setViewportView(tablaListadoInstalaciones);
				
				JPanel panelDiaConsulta = new JPanel();
				panelDiaConsulta.setToolTipText("Buscar");
				panelDiaConsulta.setLayout(null);
				panelDiaConsulta.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Seleccione el dia a consultar", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
				panelDiaConsulta.setBackground(UIManager.getColor("Button.background"));
				panelDiaConsulta.setBounds(345, 94, 279, 138);
				panelPrincipal.add(panelDiaConsulta);

				
				cbDias = new JComboBox();
				cbMes = new JComboBox();
				panelDiaConsulta.add(cbDias);
				panelDiaConsulta.add(cbMes);
				
				cbAno = new JComboBox();
				cbAno.setModel(new DefaultComboBoxModel(new String[]{"2014", "2015", "2016", "2017", "2018", "2019", "2020"}));
				cbAno.setBounds(197, 25, 73, 20);
				
				panelDiaConsulta.add(cbAno);

				cbDias.setBounds(21, 25, 42, 20);
				cbMes.setBounds(90, 25, 82, 20);
				
				final String[] febreroCompleto = new String[] { "1", "2", "3", "4", "5",
						"6", "7", "8", "9", "10", "11", "12", "13", "14",
						"15", "16", "17", "18", "19", "20", "21", "22",
						"23", "24", "25", "26", "27", "28"};
				
				final String[] Mes30Completo = new String[] { "1", "2", "3", "4", "5",
						"6", "7", "8", "9", "10", "11", "12", "13", "14",
						"15", "16", "17", "18", "19", "20", "21", "22",
						"23", "24", "25", "26", "27", "28", "29", "30" };
				final String[] Mes31Completo = new String[] { "1", "2", "3", "4", "5",
						"6", "7", "8", "9", "10", "11", "12", "13", "14",
						"15", "16", "17", "18", "19", "20", "21", "22",
						"23", "24", "25", "26", "27", "28", "29", "30",
						"31" };
				
				final Calendar fecha = Calendar.getInstance();
				
				final List<String> diasRestantesMes28 = new ArrayList<String>();
				final List<String> diasRestantesMes30 = new ArrayList<String>();
				final List<String> diasRestantesMes31 = new ArrayList<String>();
				

				for(int i = 0; i < 28; i++){
					if((i+1) >= fecha.get(Calendar.DAY_OF_MONTH)){
						diasRestantesMes28.add( "" + (i+1));
					}
				}
				
				for(int i = 0; i < 30; i++){
					if((i+1) >= fecha.get(Calendar.DAY_OF_MONTH)){
						diasRestantesMes30.add( "" + (i+1));
					}
				}
				
				for(int i = 0; i < 31; i++){
					if((i+1) >= fecha.get(Calendar.DAY_OF_MONTH)){
						diasRestantesMes31.add( "" + (i+1));
					}
				}

				cbMes.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {						
						
						if(cbAno.getSelectedItem().toString().equals("2014")){
							if(fecha.get(Calendar.MONTH) == 2 && cbMes.getSelectedItem().toString().equals("2")){	

								int itemCount = cbDias.getItemCount();

							    for(int i=0;i<itemCount;i++){
							    	cbDias.removeItemAt(0);
							     }
							    
								for(int i = 0; i < diasRestantesMes28.size(); i++){
									cbDias.addItem(diasRestantesMes28.get(i));
								}
							}
							
							if(fecha.get(Calendar.MONTH) != 2 && cbMes.getSelectedItem().toString().equals("2")){
								cbDias.setModel(new DefaultComboBoxModel(febreroCompleto));
							}
							

							if (cbMes.getSelectedItem().toString().equals("4") && fecha.get(Calendar.MONTH)+1 == 4
									|| cbMes.getSelectedItem().toString().equals("6") && fecha.get(Calendar.MONTH)+1 == 6
									|| cbMes.getSelectedItem().toString().equals("9") && fecha.get(Calendar.MONTH)+1 == 9
									|| cbMes.getSelectedItem().toString().equals("11") && fecha.get(Calendar.MONTH)+1 == 11) { 
								
								int itemCount = cbDias.getItemCount();

							    for(int i=0;i<itemCount;i++){
							    	cbDias.removeItemAt(0);
							     }
							    
							    
								for(int i = 0; i < diasRestantesMes30.size(); i++){
									cbDias.addItem(diasRestantesMes30.get(i));
								}

							}
							
							if (cbMes.getSelectedItem().toString().equals("4") && fecha.get(Calendar.MONTH)+1 != 4
									|| cbMes.getSelectedItem().toString().equals("6") && fecha.get(Calendar.MONTH)+1 != 6
									|| cbMes.getSelectedItem().toString().equals("9") && fecha.get(Calendar.MONTH)+1 != 9
									|| cbMes.getSelectedItem().toString().equals("11") && fecha.get(Calendar.MONTH)+1 != 11){
								cbDias.setModel(new DefaultComboBoxModel(Mes30Completo));

							}
							
							if (cbMes.getSelectedItem().toString().equals("1") && fecha.get(Calendar.MONTH)+1 == 1
									|| cbMes.getSelectedItem().toString().equals("3") && fecha.get(Calendar.MONTH)+1 == 3
									|| cbMes.getSelectedItem().toString().equals("5") && fecha.get(Calendar.MONTH)+1 == 5
									|| cbMes.getSelectedItem().toString().equals("7") && fecha.get(Calendar.MONTH)+1 == 7
									|| cbMes.getSelectedItem().toString().equals("8") &&fecha.get(Calendar.MONTH)+1 == 8
									|| cbMes.getSelectedItem().toString().equals("10") && fecha.get(Calendar.MONTH)+1 == 10
									|| cbMes.getSelectedItem().toString().equals("12") && fecha.get(Calendar.MONTH)+1 == 12) {
								

								int itemCount = cbDias.getItemCount();

							    for(int i=0;i<itemCount;i++){
							    	cbDias.removeItemAt(0);
							     }
							    
								for(int i = 0; i < diasRestantesMes31.size(); i++){
									cbDias.addItem(diasRestantesMes31.get(i));
								}
							}
							

							if (cbMes.getSelectedItem().toString().equals("1") && fecha.get(Calendar.MONTH)+1 != 1
									|| cbMes.getSelectedItem().toString().equals("3") && fecha.get(Calendar.MONTH)+1 != 3
									|| cbMes.getSelectedItem().toString().equals("5") && fecha.get(Calendar.MONTH)+1 != 5
									|| cbMes.getSelectedItem().toString().equals("7") && fecha.get(Calendar.MONTH)+1 != 7
									|| cbMes.getSelectedItem().toString().equals("8") &&fecha.get(Calendar.MONTH)+1 != 8
									|| cbMes.getSelectedItem().toString().equals("10") && fecha.get(Calendar.MONTH)+1 != 10
									|| cbMes.getSelectedItem().toString().equals("12") && fecha.get(Calendar.MONTH)+1 != 12){
								cbDias.setModel(new DefaultComboBoxModel(Mes31Completo));

							}
						}
						
						else{
							if (cbMes.getSelectedItem().toString().equals("2")) {
									cbDias.setModel(new DefaultComboBoxModel(febreroCompleto));
								
							} 
							
							if (cbMes.getSelectedItem().toString().equals("4")
									|| cbMes.getSelectedItem().toString().equals("6")
									|| cbMes.getSelectedItem().toString().equals("9")
									|| cbMes.getSelectedItem().toString().equals("11")) { 
								
									cbDias.setModel(new DefaultComboBoxModel(Mes30Completo));							
							}
							
							if (cbMes.getSelectedItem().toString().equals("1")
									|| cbMes.getSelectedItem().toString().equals("3")
									|| cbMes.getSelectedItem().toString().equals("5") 
									|| cbMes.getSelectedItem().toString().equals("7") 
									|| cbMes.getSelectedItem().toString().equals("8") 
									|| cbMes.getSelectedItem().toString().equals("10")
									|| cbMes.getSelectedItem().toString().equals("12")){					
								
									cbDias.setModel(new DefaultComboBoxModel(Mes31Completo));
														
							}
							
							
						}
					}
				});
				
				
				cbAno.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						if(cbAno.getSelectedItem().equals("2014")){
							cbMes.setModel(new DefaultComboBoxModel((new String[]{"11", "12"})));
						}
						else{
							cbMes.setModel(new DefaultComboBoxModel((new String[]{"1", "2", "3", "4", "5", "6", "7"
									, "8", "9", "10", "11", "12"})));
							
						}						
						

						if(cbAno.getSelectedItem().toString().equals("2014")){
							if(fecha.get(Calendar.MONTH) == 2 && cbMes.getSelectedItem().toString().equals("2")){	

								int itemCount = cbDias.getItemCount();

							    for(int i=0;i<itemCount;i++){
							    	cbDias.removeItemAt(0);
							     }
							    
								for(int i = 0; i < diasRestantesMes28.size(); i++){
									cbDias.addItem(diasRestantesMes28.get(i));
								}
							}
							
							if(fecha.get(Calendar.MONTH) != 2 && cbMes.getSelectedItem().toString().equals("2")){
								cbDias.setModel(new DefaultComboBoxModel(febreroCompleto));
							}
							

							if (cbMes.getSelectedItem().toString().equals("4") && fecha.get(Calendar.MONTH)+1 == 4
									|| cbMes.getSelectedItem().toString().equals("6") && fecha.get(Calendar.MONTH)+1 == 6
									|| cbMes.getSelectedItem().toString().equals("9") && fecha.get(Calendar.MONTH)+1 == 9
									|| cbMes.getSelectedItem().toString().equals("11") && fecha.get(Calendar.MONTH)+1 == 11) { 
								
								int itemCount = cbDias.getItemCount();

							    for(int i=0;i<itemCount;i++){
							    	cbDias.removeItemAt(0);
							     }
							    
							    
								for(int i = 0; i < diasRestantesMes30.size(); i++){
									cbDias.addItem(diasRestantesMes30.get(i));
								}

							}
							
							if (cbMes.getSelectedItem().toString().equals("4") && fecha.get(Calendar.MONTH)+1 != 4
									|| cbMes.getSelectedItem().toString().equals("6") && fecha.get(Calendar.MONTH)+1 != 6
									|| cbMes.getSelectedItem().toString().equals("9") && fecha.get(Calendar.MONTH)+1 != 9
									|| cbMes.getSelectedItem().toString().equals("11") && fecha.get(Calendar.MONTH)+1 != 11){
								cbDias.setModel(new DefaultComboBoxModel(Mes30Completo));

							}
							
							if (cbMes.getSelectedItem().toString().equals("1") && fecha.get(Calendar.MONTH)+1 == 1
									|| cbMes.getSelectedItem().toString().equals("3") && fecha.get(Calendar.MONTH)+1 == 3
									|| cbMes.getSelectedItem().toString().equals("5") && fecha.get(Calendar.MONTH)+1 == 5
									|| cbMes.getSelectedItem().toString().equals("7") && fecha.get(Calendar.MONTH)+1 == 7
									|| cbMes.getSelectedItem().toString().equals("8") &&fecha.get(Calendar.MONTH)+1 == 8
									|| cbMes.getSelectedItem().toString().equals("10") && fecha.get(Calendar.MONTH)+1 == 10
									|| cbMes.getSelectedItem().toString().equals("12") && fecha.get(Calendar.MONTH)+1 == 12) {
								

								int itemCount = cbDias.getItemCount();

							    for(int i=0;i<itemCount;i++){
							    	cbDias.removeItemAt(0);
							     }
							    
								for(int i = 0; i < diasRestantesMes31.size(); i++){
									cbDias.addItem(diasRestantesMes31.get(i));
								}
							}
							

							if (cbMes.getSelectedItem().toString().equals("1") && fecha.get(Calendar.MONTH)+1 != 1
									|| cbMes.getSelectedItem().toString().equals("3") && fecha.get(Calendar.MONTH)+1 != 3
									|| cbMes.getSelectedItem().toString().equals("5") && fecha.get(Calendar.MONTH)+1 != 5
									|| cbMes.getSelectedItem().toString().equals("7") && fecha.get(Calendar.MONTH)+1 != 7
									|| cbMes.getSelectedItem().toString().equals("8") &&fecha.get(Calendar.MONTH)+1 != 8
									|| cbMes.getSelectedItem().toString().equals("10") && fecha.get(Calendar.MONTH)+1 != 10
									|| cbMes.getSelectedItem().toString().equals("12") && fecha.get(Calendar.MONTH)+1 != 12){
								cbDias.setModel(new DefaultComboBoxModel(Mes31Completo));

							}
						}
						
						else{
							if (cbMes.getSelectedItem().toString().equals("2")) {
									cbDias.setModel(new DefaultComboBoxModel(febreroCompleto));
								
							} 
							
							if (cbMes.getSelectedItem().toString().equals("4")
									|| cbMes.getSelectedItem().toString().equals("6")
									|| cbMes.getSelectedItem().toString().equals("9")
									|| cbMes.getSelectedItem().toString().equals("11")) { 
								
									cbDias.setModel(new DefaultComboBoxModel(Mes30Completo));							
							}
							
							if (cbMes.getSelectedItem().toString().equals("1")
									|| cbMes.getSelectedItem().toString().equals("3")
									|| cbMes.getSelectedItem().toString().equals("5") 
									|| cbMes.getSelectedItem().toString().equals("7") 
									|| cbMes.getSelectedItem().toString().equals("8") 
									|| cbMes.getSelectedItem().toString().equals("10")
									|| cbMes.getSelectedItem().toString().equals("12")){					
								
									cbDias.setModel(new DefaultComboBoxModel(Mes31Completo));
														
							}
							
							
						}
						
						
					}
				});
				
				
				JButton btnBuscar = new JButton("");
				btnBuscar.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
				btnBuscar.setBounds(117, 81, 37, 35);
				panelDiaConsulta.add(btnBuscar);
				btnBuscar.setIcon(new ImageIcon(VentanaMostrarHorarios.class.getResource("/img/lupab.jpg")));
				btnBuscar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						limpiaTabla();
						try {
							meterTablas();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
			}
			
			cbInstalaciones = new JComboBox();			
			cbInstalaciones.setBounds(345, 35, 268, 27);
			panelPrincipal.add(cbInstalaciones);
			
			btAceptar = new JButton("Aceptar");
			btAceptar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					dispose();
				}
			});
			btAceptar.setBounds(305, 304, 89, 23);
			panelPrincipal.add(btAceptar);
			
			List<Instalacion> lista = gbd.listarInstalaciones();
			for(int i = 0; i < lista.size(); i++){
				
					cbInstalaciones.addItem(lista.get(i).getNombre());
				
			}
		}
	}
	
	void limpiaTabla(){
        try{
        	ModeloNoEditable temp = (ModeloNoEditable) tablaListadoInstalaciones.getModel();
            int a =temp.getRowCount();
            for(int i=0; i<a; i++)
                temp.removeRow(0); 
            }catch(Exception e){
            System.out.println(e);
        }
    }
	
	
	public void meterTablas() throws SQLException, ClassNotFoundException{
		List<Reserva> reservas = gbd.listarReservas();

		int id = getIDInstalacion(String
				.valueOf(cbInstalaciones.getSelectedItem()));

		String fecha = cbDias.getSelectedItem().toString()
				+ "/" + cbMes.getSelectedItem().toString()
				+ "/" + cbAno.getSelectedItem().toString();

		Object[] line = new Object[2];
		int horaInicial = 8;

		for(int i = 0; i < 15; i++){
			line[0] = "De " + (i + horaInicial) + ":00 a " + (i + horaInicial + 1) + ":00";
			line[1] = "Disponible";
			boolean seguir = true;

			for(int j = 0; j < reservas.size();j++){
				if(seguir){
					String nombreInstalacion = gbd.getNombrePorIdInstalacion(reservas.get(j).getId_instalacion());

					if(reservas.get(j).getHorario().equals(fecha) && reservas.get(j).getHoraInicio() == (horaInicial + i)
							&& reservas.get(j).getHoraFin() == (horaInicial + i + 1) 
							&& nombreInstalacion.equals(cbInstalaciones.getSelectedItem().toString()) 
							&& !reservas.get(j).isCancelada()){
						line[1] = "Reservada";
						seguir = false;

					}

					else if(reservas.get(j).getHorario().equals(fecha) && reservas.get(j).getHoraInicio() == (horaInicial + i)
							&& reservas.get(j).getHoraFin() == (horaInicial + i + 2) 
							&& nombreInstalacion.equals(cbInstalaciones.getSelectedItem().toString())
							&& !reservas.get(j).isCancelada()){
						line[1] = "Reservada";
						seguir = false;

					}
					
					else if(reservas.get(j).getHorario().equals(fecha) && reservas.get(j).getHoraInicio() == (horaInicial + i - 1)
							&& reservas.get(j).getHoraFin() == (horaInicial + i + 1) 
							&& nombreInstalacion.equals(cbInstalaciones.getSelectedItem().toString())
							&& !reservas.get(j).isCancelada()){
						line[1] = "Reservada";
						seguir = false;

					}

					
				}
			}

			modeloTabla.addRow(line);

		}
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

}

