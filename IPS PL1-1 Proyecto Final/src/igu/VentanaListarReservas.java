package igu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

import logica.Reserva;
import base.datos.GestorBaseDatos;



public class VentanaListarReservas extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private final JPanel panelPrincipal = new JPanel();
	private JTable tablaListadoReservas;
	private ModeloNoEditable modeloTabla;
	private GestorBaseDatos gbd;
	
	/**
	 * Create the dialog.
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public VentanaListarReservas() throws ClassNotFoundException, SQLException {
		setTitle("Listado de Reservas");
		gbd = new GestorBaseDatos();
		setBounds(100, 100, 707, 328);
		getContentPane().setLayout(new BorderLayout());
		panelPrincipal.setBackground(Color.LIGHT_GRAY);
		panelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(panelPrincipal, BorderLayout.CENTER);
		panelPrincipal.setLayout(new BorderLayout(0, 0));
		{
			JScrollPane spTabla = new JScrollPane();
			panelPrincipal.add(spTabla, BorderLayout.CENTER);
			{
				modeloTabla = new ModeloNoEditable(new String[] {"DNI Socio", "ID Instalacion", "Fecha de Reserva","Hora de Inicio","Hora de Finalizacion"}, 0);
				tablaListadoReservas = new JTable();
				tablaListadoReservas.setModel(modeloTabla);
				tablaListadoReservas.setBackground(Color.WHITE);
				spTabla.setViewportView(tablaListadoReservas);
				{
					JPanel panel = new JPanel();
					getContentPane().add(panel, BorderLayout.SOUTH);
					{
						JButton btAceptar = new JButton("Aceptar");
						btAceptar.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {
								dispose();
							}
						});
						panel.add(btAceptar);
					}
				}
				meterTablas();
			}
		}
	}
	
	public void meterTablas() throws ClassNotFoundException, SQLException{
		List<Reserva> reservas = gbd.listarReservas();
		Object[] line = new Object[5];
		for(int i = 0; i < reservas.size(); i++){
			line[0] = reservas.get(i).getDNI();
			line[1] = reservas.get(i).getId_instalacion();
			line[2] = reservas.get(i).getHorario();
			line[3] = reservas.get(i).getHoraInicio();
			line[4] = reservas.get(i).getHoraFin();
			
			modeloTabla.addRow(line);

		}
	}

}
