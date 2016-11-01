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
import BussinesException.BusinessException;
import base.acciones.gestion.GestionApliIMP;
import base.datos.GestorBaseDatos;

public class VentanaCancelacionReserva extends JDialog {
	
	final long MILLSECS_PER_DAY = 24*60*60*1000;

	private final JPanel contentPanel = new JPanel();
	private JLabel lbDNISocio;
	private JTextField txDNI;
	private JButton btBuscar;
	private JScrollPane scTable;
	private JTable tableReserva;
	private ModeloNoEditable modeloTabla;
	private JButton btCancelarReserva;
	private JButton btCancelar;
	private GestorBaseDatos gbd;
	private GestionApliIMP gestion;
	private Date hoy = new Date();

	
	/**
	 * Create the dialog.
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public VentanaCancelacionReserva(VentanaPrincipal vp) throws ClassNotFoundException, SQLException {
		gbd = new GestorBaseDatos();
		gestion = new GestionApliIMP();
		setTitle("Cancelar Reserva");
		setBounds(100, 100, 671, 356);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		contentPanel.add(getLbDNISocio());
		contentPanel.add(getTxDNI());
		contentPanel.add(getBtBuscar());
		contentPanel.add(getScTable());
		contentPanel.add(getBtCancelarReserva());
		contentPanel.add(getBtCancelar());
		setLocationRelativeTo(null);
	}
	private JLabel getLbDNISocio() {
		if (lbDNISocio == null) {
			lbDNISocio = new JLabel("DNI del Cliente:");
			lbDNISocio.setBounds(148, 26, 95, 14);
		}
		return lbDNISocio;
	}
	private JTextField getTxDNI() {
		if (txDNI == null) {
			txDNI = new JTextField();
			txDNI.setHorizontalAlignment(SwingConstants.CENTER);
			txDNI.setBounds(253, 23, 166, 20);
			txDNI.setColumns(10);
		}
		return txDNI;
	}
	private JButton getBtBuscar() {
		if (btBuscar == null) {
			btBuscar = new JButton("");
			btBuscar.setBorder(null);
			btBuscar.setIcon(new ImageIcon(VentanaCancelacionReserva.class.getResource("/img/lupab.jpg")));
			btBuscar.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent arg0) {
					
					String dni = txDNI.getText().toString();
					List<Reserva> reservas;
					try {
						reservas = gbd.listarReservas();
						for (Reserva r: reservas) {
							if(r.getDNI().equalsIgnoreCase(dni) && r.isCobro()==false && r.isCancelada()==false){
								Object[] nuevaLinea = new Object[6];
								nuevaLinea[0] = r.getDNI();
								nuevaLinea[1] = getNombreActividad(r.getId_instalacion());
								nuevaLinea[2] = r.getHorario();
								nuevaLinea[3] = r.getHoraInicio();
								nuevaLinea[4] = r.getHoraFin();
								nuevaLinea[5] = r.getCodigoReserva();
								modeloTabla.addRow(nuevaLinea);
							}
						}
						
						if(tableReserva.getRowCount() == 0){
							JOptionPane.showMessageDialog(null, "-DNI de socio no registrado.\n-No existen reservas relacionadas con dicho DNI","No se han encontrado resultados", JOptionPane.ERROR_MESSAGE);
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
			btBuscar.setBounds(429, 11, 45, 42);
		}
		return btBuscar;
	}
	private JScrollPane getScTable() throws ClassNotFoundException, SQLException {
		if (scTable == null) {
			scTable = new JScrollPane();
			
			scTable.setBounds(73, 79, 504, 161);
			scTable.setViewportView(getTableReserva());
		}
		return scTable;
	}
	private JTable getTableReserva() throws ClassNotFoundException, SQLException {
		if (tableReserva == null) {
			String[] nombreColumnas = {"DNI_Socio", "Instalacion", "Fecha Reserva","Hora de inicio","Hora de fin", "Codigo Reserva"};
			modeloTabla = new ModeloNoEditable(nombreColumnas,0);
			tableReserva = new JTable(modeloTabla);
			tableReserva.getColumnModel().getColumn(2).setPreferredWidth(200);
			tableReserva.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					if(tableReserva.getRowCount() > 0){
						btCancelarReserva.setEnabled(true);
					}
				}
			});
			
		}
		return tableReserva;
	}
	private JButton getBtCancelarReserva() {
		if (btCancelarReserva == null) {
			btCancelarReserva = new JButton("Cancelar reserva");
			btCancelarReserva.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					try {
						int fila = tableReserva.getSelectedRow();
						int id = Integer.parseInt(String.valueOf(modeloTabla.getValueAt(fila, 5)));
						String horario = String.valueOf(tableReserva.getValueAt(fila, 2));
						if(comprobarHorario(horario)){
							gestion.EliminarReserva(id);
						}
						else{
							return;
						}
						
						limpiaTabla();

						String dni = txDNI.getText().toString();
						List<Reserva> reservas;
							reservas = gbd.listarReservas();
							for (Reserva r: reservas) {
								if(r.getDNI().equalsIgnoreCase(dni) && r.isCobro()==false && r.isCancelada()==false){
									Object[] nuevaLinea = new Object[6];
									nuevaLinea[0] = r.getDNI();
									nuevaLinea[1] = getNombreActividad(r.getId_instalacion());
									nuevaLinea[2] = r.getHorario();
									nuevaLinea[3] = r.getHoraInicio();
									nuevaLinea[4] = r.getHoraFin();
									nuevaLinea[5] = r.getCodigoReserva();
									modeloTabla.addRow(nuevaLinea);
								
							}
							}
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (BusinessException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
			});
			btCancelarReserva.setEnabled(false);
			btCancelarReserva.setBounds(196, 271, 148, 23);
		}
		return btCancelarReserva;
	}
	private JButton getBtCancelar() {
		if (btCancelar == null) {
			btCancelar = new JButton("Cancelar");
			btCancelar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
			btCancelar.setBounds(354, 271, 89, 23);
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
	public int getIdActividad(String nombre) throws ClassNotFoundException, SQLException{
		List<Instalacion> act = gbd.listarInstalaciones();
		for(Instalacion ins: act){
			if(ins.getNombre().equals(nombre)){
				return ins.getId();
			}
		}
		return 0;
		
	}
	private boolean comprobarHorario(String horario) {
		// TODO Auto-generated method stub
		String [] array = horario.split("/");
		int dia = Integer.parseInt(array[0]);
		int mes = Integer.parseInt(array[1]);
		int año = Integer.parseInt(array[2]);
		Calendar calendar = new GregorianCalendar(año,mes-1,dia-1);
		java.sql.Date fecha= new java.sql.Date(calendar.getTimeInMillis());
		long diferencia = (fecha.getTime() - hoy .getTime())/MILLSECS_PER_DAY + 1;
		if(diferencia < 1 ){
			JOptionPane.showMessageDialog(null, "No se permite cancelar la reserva debido a que el comienzo de la actividad no sobrepasa las 24h","No se permite cancelar la reserva", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		JOptionPane.showMessageDialog(null, "La reserva se ha eliminado correctamente");
		return true;
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
