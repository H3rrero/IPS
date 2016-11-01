package igu;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import BussinesException.BusinessException;
import base.acciones.gestion.GestionApliIMP;
import base.datos.GestorBaseDatos;
import logica.Actividad;
import logica.Instalacion;
import logica.MatriculadoEn;
import logica.Reserva;
import javax.swing.SwingConstants;

public class EliminarInscripcionSocio extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JScrollPane scrollPane;
	private JTable table;
	private JButton btnConfirmarCancelacion;
	private JButton btnCancelarProceso;
	private ModeloNoEditable modeloTabla;
	private JButton button;
	private JTextField textDNI;
	private GestorBaseDatos gbd;
	private GestionApliIMP gestion;
	private VentanaPrincipal vp;
	final long MILLSECS_PER_DAY =60*60*1000;
	private Date hoy = new Date();
	Calendar calendario = Calendar.getInstance();
	/**
	 * Create the dialog.
	 */
	public EliminarInscripcionSocio(VentanaPrincipal vp) {
		setTitle("Cancelar inscripcion de socio a actividad");
		calendario= new GregorianCalendar();
		gbd = new GestorBaseDatos();
		gestion= new GestionApliIMP();
		setBounds(100, 100, 649, 405);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblDniDelCliente = new JLabel("DNI del Cliente:");
			lblDniDelCliente.setBounds(109, 39, 102, 16);
			contentPanel.add(lblDniDelCliente);
		}
		contentPanel.add(getScrollPane());
		contentPanel.add(getBtnConfirmarCancelacion());
		contentPanel.add(getBtnCancelarProceso());
		contentPanel.add(getButton());
		contentPanel.add(getTextDNI());
	}
	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setBounds(12, 110, 607, 171);
			scrollPane.setViewportView(getTable());
		}
		return scrollPane;
	}
	private JTable getTable() {
		if (table == null) {
			table = new JTable();
			String[] nombreColumnas = {"DNI del socio","Nombre Actividad", "ID_ACtividad", "Asistencia"};
			modeloTabla = new ModeloNoEditable(nombreColumnas,0);
			table = new JTable(modeloTabla);
			table.getColumnModel().getColumn(2).setPreferredWidth(200);
			table.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					if(table.getRowCount() > 0){
						btnConfirmarCancelacion.setEnabled(true);
					}
				}
			});
		}
		return table;
	}
	private JButton getBtnConfirmarCancelacion() {
		if (btnConfirmarCancelacion == null) {
			btnConfirmarCancelacion = new JButton("Confirmar Cancelacion");
			btnConfirmarCancelacion.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					try {
						int plazas=0;
						int fila = table.getSelectedRow();
						int id = Integer.parseInt(String.valueOf(modeloTabla.getValueAt(fila, 2)));
						String dni = textDNI.getText().toString();
						List<Actividad> actividades= gbd.listarActividades();
						for (Actividad actividad : actividades) {
							if(actividad.getId_actividad()==id)
								plazas=actividad.getNumeroPlazas();
						}
						List<Reserva> reservas = gbd.listarReservas();
						for (Reserva reserva : reservas) {
							if(reserva.getId_actividad()==id)
								if(!comprobarHorario(reserva.getHorario(),reserva.getHoraInicio()))
								{return;}
						}
						gestion.UpdateActividad(id, plazas+1);
						gestion.EliminarMatricula(dni, id);
						JOptionPane.showMessageDialog(null, "La actividad se ha eliminado correctamente");
						limpiaTabla();

						
						List<MatriculadoEn> matriculados;
						matriculados = gbd.listarMatriculados();
						for (MatriculadoEn r: matriculados) {
							if(r.getDniSocio().equalsIgnoreCase(dni)){
								Object[] nuevaLinea = new Object[4];
								nuevaLinea[0] = r.getDniSocio();
								nuevaLinea[1] = getNombreActividad(r.getNombreActividad());
								nuevaLinea[2] = r.getNombreActividad();
								nuevaLinea[3] = r.getAsistenciaSemanal();
								modeloTabla.addRow(nuevaLinea);
								
							}
							}
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (BusinessException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}

				
			});
			btnConfirmarCancelacion.setEnabled(false);
			btnConfirmarCancelacion.setBounds(139, 315, 161, 25);
		}
		return btnConfirmarCancelacion;
	}
	void limpiaTabla(){
        try{
        	ModeloNoEditable temp = (ModeloNoEditable) table.getModel();
            int a =temp.getRowCount();
            for(int i=0; i<a; i++)
                temp.removeRow(0); 
            }catch(Exception e){
            System.out.println(e);
        }
    }
	private JButton getBtnCancelarProceso() {
		if (btnCancelarProceso == null) {
			btnCancelarProceso = new JButton("Cancelar el proceso de cancelacion");
			btnCancelarProceso.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					dispose();
				}
			});
			btnCancelarProceso.setBounds(312, 315, 249, 25);
		}
		return btnCancelarProceso;
	}
	private JButton getButton() {
		if (button == null) {
			button = new JButton("");
			button.setIcon(new ImageIcon(EliminarInscripcionSocio.class.getResource("/img/lupab.jpg")));
			button.setBounds(387, 26, 46, 42);
			
			button.addActionListener(new ActionListener() {
				

				public void actionPerformed(ActionEvent arg0) {
					
					String dni = textDNI.getText().toString();
					
					try {
						List<MatriculadoEn> matriculados;
						matriculados = gbd.listarMatriculados();
						for (MatriculadoEn r: matriculados) {
							if(r.getDniSocio().equalsIgnoreCase(dni)){
								Object[] nuevaLinea = new Object[4];
								nuevaLinea[0] = r.getDniSocio();
								nuevaLinea[1] = getNombreActividad(r.getNombreActividad());
								nuevaLinea[2] = r.getNombreActividad();
								nuevaLinea[3] = r.getAsistenciaSemanal();
								modeloTabla.addRow(nuevaLinea);
							}
						}
						
						if(table.getRowCount() == 0){
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
		}
		return button;
	}
	private String getNombreActividad(int idActividad) throws ClassNotFoundException, SQLException {
		List<Actividad> act = gbd.listarActividades();
		for(Actividad ins: act){
			if(ins.getId_actividad()==idActividad){
				
				return ins.getNombre();
			}
		}
		return null;
	}
	private JTextField getTextDNI() {
		if (textDNI == null) {
			textDNI = new JTextField();
			textDNI.setHorizontalAlignment(SwingConstants.CENTER);
			textDNI.setBounds(205, 36, 153, 22);
			textDNI.setColumns(10);
		}
		return textDNI;
	}
	public VentanaPrincipal getVp() {
		return vp;
	}
	public void setVp(VentanaPrincipal vp) {
		this.vp = vp;
	}
	private boolean comprobarHorario(String horario,int horainicio) {
		// TODO Auto-generated method stub
		int horaActual= calendario.get(Calendar.HOUR_OF_DAY);
		String [] array = horario.split("/");
		int dia = Integer.parseInt(array[0]);
		int mes = Integer.parseInt(array[1]);
		int año = Integer.parseInt(array[2]);
		Calendar calendar = new GregorianCalendar(año,mes-1,dia-1);
		if(calendar.DAY_OF_MONTH==calendario.DAY_OF_MONTH&&calendar.MONTH==calendario.MONTH&&calendar.YEAR==calendario.YEAR && horainicio<=horaActual){
			JOptionPane.showMessageDialog(null, "No se permite cancelar la actividad debido a que dicha actividad ya a dado comienzo","No se permite cancelar la reserva", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		return true;
	}
}
