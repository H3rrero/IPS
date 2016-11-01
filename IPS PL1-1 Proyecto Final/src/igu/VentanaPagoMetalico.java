package igu;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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

import logica.Instalacion;
import logica.Reserva;
import logica.Socio;
import base.datos.Conexion;
import base.datos.GestorBaseDatos;

public class VentanaPagoMetalico extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JLabel lbDNI;
	private JTextField txDNI;
	private JButton btBuscar;
	private JScrollPane scTable;
	private JTable tableImpagos;
	private ModeloNoEditable modeloTabla;
	private GestorBaseDatos gbd;
	private static VentanaPrincipal vp;
	private JButton btPagar;
	private JButton btCancelar;
	private int codigo = 0000;

	/**
	 * Create the dialog.
	 */
	public VentanaPagoMetalico(VentanaPrincipal vp) {
		gbd = new GestorBaseDatos();
		setTitle("Realizar pago en met\u00E1lico");
		setBounds(100, 100, 857, 359);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		contentPanel.add(getLbDNI());
		contentPanel.add(getTxDNI());
		contentPanel.add(getBtBuscar());
		contentPanel.add(getScTable());
		contentPanel.add(getBtPagar());
		contentPanel.add(getBtCancelar());
		setLocationRelativeTo(null);
	}
	private JLabel getLbDNI() {
		if (lbDNI == null) {
			lbDNI = new JLabel("DNI:");
			lbDNI.setFont(new Font("Tahoma", Font.PLAIN, 13));
			lbDNI.setBounds(184, 22, 45, 14);
		}
		return lbDNI;
	}
	private JTextField getTxDNI() {
		if (txDNI == null) {
			txDNI = new JTextField();
			txDNI.setHorizontalAlignment(SwingConstants.CENTER);
			txDNI.setColumns(10);
			txDNI.setBounds(258, 20, 254, 20);
		}
		return txDNI;
	}
	private JButton getBtBuscar() {
		if (btBuscar == null) {
			btBuscar = new JButton("");
			btBuscar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					try {
						if(comprobarCentroBaseDatos(txDNI.getText().toString())){
							JOptionPane
							.showMessageDialog(
									null,
									"No se permite gestionar el pago de actividades ligadas al propio centro",
									"Reserva(s) del centro",
									JOptionPane.PLAIN_MESSAGE);
							txDNI.setText("");
							modeloTabla.getDataVector().clear();
							return;
						}
						if(comprobarSocioBaseDatos(txDNI.getText().toString())){
							añadirFilas(txDNI.getText().toString());
							if(tableImpagos.getRowCount() == 0){
								JOptionPane.showMessageDialog(null,
										"No se han encontrado recibos pendientes de pago para dicho socio",
										"No hay recibos impagados", JOptionPane.ERROR_MESSAGE);
							}
						}
						else{
							JOptionPane.showMessageDialog(null,
									"El socio no se encuentra registrado en la base de datos",
									"Socio no registrado", JOptionPane.ERROR_MESSAGE);
						}
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					catch (IllegalStateException e) {
						txDNI.setText("");
						modeloTabla.getDataVector().clear();
						return;
					}catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			btBuscar.setIcon(new ImageIcon(VentanaPagoMetalico.class.getResource("/img/lupab.jpg")));
			btBuscar.setBorder(null);
			btBuscar.setBounds(563, 11, 45, 42);
		}
		return btBuscar;
	}
	private JScrollPane getScTable() {
		if (scTable == null) {
			scTable = new JScrollPane();
			scTable.setBounds(10, 64, 821, 171);
			scTable.setViewportView(getTableImpagos());
		}
		return scTable;
	}
	private JTable getTableImpagos() {
		if (tableImpagos == null) {
			String [] nombreCols = {"DNI Socio","Instalacion","Horario","Inicio","Fin","Cod. Reserva","Importe","Cobro"};
			modeloTabla = new ModeloNoEditable(nombreCols,0);
			tableImpagos = new JTable(modeloTabla);
			tableImpagos.setDefaultRenderer(Object.class, new RendererSubstance());
			tableImpagos.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					btPagar.setEnabled(true);
				}
			});
		}
		return tableImpagos;
	}
	private void añadirFilas(String cadena) throws ClassNotFoundException, SQLException {
		gbd = new GestorBaseDatos();
		List<Reserva> reservas = gbd.listarReservas();
		modeloTabla.getDataVector().clear();
		Object [] nuevaFila = new Object[8];
		for(Reserva r: reservas){
			if(r.getDNI().equals(cadena)){
				if(r.isCobro() == false && r.isCancelada()==false){
					System.out.println();
					nuevaFila[0] = r.getDNI();
					nuevaFila[1] = getNombreInstalacion(r.getId_instalacion());
					nuevaFila[2] = r.getHorario();
					nuevaFila[3] = r.getHoraInicio();
					nuevaFila[4] = r.getHoraFin();
					nuevaFila[5] = r.getCodigoReserva();
					nuevaFila[6] = importeAPagar(r.getHoraInicio(), r.getHoraFin(), getNombreInstalacion(r.getId_instalacion())) + " €";
					nuevaFila[7] = getPagoRealizado(r.isCobro());
				
					modeloTabla.addRow(nuevaFila);
				}
			}
		}
		
	}
	public String getNombreInstalacion(int cad) throws ClassNotFoundException, SQLException{
		List<Instalacion> instalaciones = gbd.listarInstalaciones();
		for(Instalacion i: instalaciones){
			if(i.getId() == cad){
				return i.getNombre();
			}
		}
		return null;
	}
	public String getPagoRealizado(boolean valor){
		if(valor){
			return "PAGADO";
		}
		return "NO PAGADO";
	}
	private JButton getBtPagar() {
		if (btPagar == null) {
			btPagar = new JButton("Pagar");
			btPagar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					int fila = tableImpagos.getSelectedRow();
					String dni = String.valueOf(tableImpagos.getValueAt(fila, 0));
					String instalacion = String.valueOf(tableImpagos.getValueAt(fila, 1));
					String horario = String.valueOf(tableImpagos.getValueAt(fila, 2));
					String inicio = String.valueOf(tableImpagos.getValueAt(fila, 3));
					String fin = String.valueOf(tableImpagos.getValueAt(fila, 4));
					String codReserva = String.valueOf(tableImpagos.getValueAt(fila, 5));
					String importe = String.valueOf(tableImpagos.getValueAt(fila, 6));
					generarRecibo("RECIBOCOD"+codReserva,dni,instalacion,horario,inicio,fin,codReserva,importe);
					try {
						modificarPagoBaseDatos(codReserva);
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
						JOptionPane.showMessageDialog(null,
								"Se ha generado el recibo con ID: RECIBOCOD"+codReserva,
								"Recibo pagado", JOptionPane.PLAIN_MESSAGE);
						dispose();
				}
			});
			btPagar.setEnabled(false);
			btPagar.setBounds(309, 260, 89, 37);
		}
		return btPagar;
	}
	private JButton getBtCancelar() {
		if (btCancelar == null) {
			btCancelar = new JButton("Cancelar");
			btCancelar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					dispose();
				}
			});
			btCancelar.setBounds(408, 260, 89, 37);
		}
		return btCancelar;
	}
	public int getPrecioInstalacion(String nombre) throws ClassNotFoundException, SQLException{
		List<Instalacion> instalaciones = gbd.listarInstalaciones();
		for(Instalacion ins: instalaciones){
			if(ins.getNombre().equals(nombre)){
				return ins.getPrecioHora();
			}
		}
		return 0;
	}
	public int importeAPagar(int horaInicio,int horaFin, String nombre) throws ClassNotFoundException, SQLException{
		int diferencia = horaFin - horaInicio;
		int importeHora = getPrecioInstalacion(nombre);
		return importeHora * diferencia;
		
	}
	private void generarRecibo(String fileName,String dni, String instalacion,
			String horario, String inicio, String fin,
			String codReserva, String importe) {
		// TODO Auto-generated method stub
		FileWriter fichero=null;
		PrintWriter pw = null;
		try
		{
			fichero = new FileWriter(fileName);
			pw = new PrintWriter(fichero);
			
				pw.println();
				pw.println("-------------------------------RECIBO-------------------------------");
				pw.print("Centro de Deportes S.A recibe del socio con DNI: ");
				pw.println(dni + " ");
				pw.print("En concepto de alquiler de la instalacion: ");
				pw.println(instalacion + " " +"el dia: "+horario+ " en el horario: " + inicio + ":00 h -" + fin + ":00 h" + " con CODIGO DE RESERVA: " + codReserva);
				pw.println("El importe de: " + importe);
				pw.println("----------------------------<<<PAGADO>>>----------------------------");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != fichero)
					fichero.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
	private void modificarPagoBaseDatos(String codReserva) throws ClassNotFoundException, SQLException{
		Conexion con = new Conexion();
		con.conectar();
		PreparedStatement pst = null;
		try {
			 pst = con.getCon().prepareStatement("UPDATE Reservas SET Cobro='" + true + "' WHERE CodigoReserva = '"+ codReserva+"'");
			 pst.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			pst.close();
			con.desconectar();
		}
	}
	private boolean comprobarSocioBaseDatos(String dni) throws ClassNotFoundException, SQLException {
		List<Socio> socios = gbd.listarSocios();
		for(Socio s: socios){
			
			if(s.getDni().equals(dni)){
				return true;
			}
		}
		return false;
	}
	private boolean comprobarCentroBaseDatos(String dni){
		if(dni.equalsIgnoreCase("CENTRO")){
			return true;
			
		}
		return false;
	}
	
}
