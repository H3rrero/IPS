package igu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.awt.Font;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.jvnet.substance.api.renderers.SubstanceDefaultTableCellRenderer;

import logica.Actividad;
import base.datos.GestorBaseDatos;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaComprobarActividadesLlenas extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JLabel lbSinPlazas;
	private JScrollPane scTable;
	private JTable tableActividadesLlenas;
	private ModeloNoEditable modeloTabla;
	private GestorBaseDatos gbd;
	private JButton btAceptar;
	private JButton btCancelar;

	/**
	 * Create the dialog.
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public VentanaComprobarActividadesLlenas(VentanaPrincipal vp) throws ClassNotFoundException, SQLException {
		setTitle("Listado de actividades sin plazas disponibles");
		setBounds(100, 100, 761, 366);
		gbd = new GestorBaseDatos();
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		contentPanel.add(getLbSinPlazas());
		contentPanel.add(getScTable());
		contentPanel.add(getBtAceptar());
		contentPanel.add(getBtCancelar());
	}
	private JLabel getLbSinPlazas() {
		if (lbSinPlazas == null) {
			lbSinPlazas = new JLabel("Las siguientes actividades no disponen de plazas libres:");
			lbSinPlazas.setFont(new Font("Tahoma", Font.BOLD, 12));
			lbSinPlazas.setBounds(22, 23, 486, 14);
		}
		return lbSinPlazas;
	}
	private JScrollPane getScTable() throws ClassNotFoundException, SQLException {
		if (scTable == null) {
			scTable = new JScrollPane();
			scTable.setBounds(22, 52, 701, 213);
			scTable.setViewportView(getTableActividadesLlenas());
		}
		return scTable;
	}
	private JTable getTableActividadesLlenas() throws ClassNotFoundException, SQLException {
		if (tableActividadesLlenas == null) {
			String [] nombreCols = {"Nombre","ID Actividad","Numero de plazas","Duracion (semanas)"};
			modeloTabla = new ModeloNoEditable(nombreCols, 0);
			tableActividadesLlenas = new JTable(modeloTabla);
			tableActividadesLlenas.setDefaultRenderer(Object.class, new Renderer());
			añadirFilas();
		}
		return tableActividadesLlenas;
	}

	private void añadirFilas() throws ClassNotFoundException, SQLException {
		List<Actividad> actividades = gbd.listarActividades();
		Object [] nuevaFila = new Object [4];
		for(Actividad act: actividades){
			if(act.getNumeroPlazas() == 0){
				nuevaFila[0] = act.getNombre();
				nuevaFila[1] = act.getId_actividad();
				nuevaFila[2] = act.getNumeroPlazas();
				nuevaFila[3] = act.getDuracionSemanas();
				
				modeloTabla.addRow(nuevaFila);
			}
		}
		
	}
	public class Renderer extends SubstanceDefaultTableCellRenderer{

		private static final long serialVersionUID = 1L;

			@Override
		    public Component getTableCellRendererComponent(JTable table, Object value,
		            boolean isSelected, boolean hasFocus, int row, int column) {
		
		       super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		       this.setFont(new Font("Dialog", Font.PLAIN, 14));
		       if (column == 1)
			      {
		        	this.setForeground(Color.BLUE);
		        	this.setFont(new java.awt.Font("Dialog", Font.BOLD, 15));
			      }
		       if (column == 2)
			      {
		        	this.setForeground(Color.RED);
		        	this.setFont(new java.awt.Font("Dialog", Font.BOLD, 15));
			      }
		        return this;
		    }
			
			public Component getTableCellRendererComponent2(JTable table, Object value,
		            boolean isSelected, boolean hasFocus, int row, int column) {
		
		       super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		       this.setFont(new Font("Dialog", Font.PLAIN, 14));
		       if (column == 1)
			      {
		        	this.setForeground(Color.RED);
		        	this.setFont(new java.awt.Font("Dialog", Font.BOLD, 15));
			      }
		       if (column == 2)
			      {
		        	this.setForeground(Color.BLUE);
		        	this.setFont(new java.awt.Font("Dialog", Font.BOLD, 15));
			      }
		        return this;
		    }
		

	}
	private JButton getBtAceptar() {
		if (btAceptar == null) {
			btAceptar = new JButton("Aceptar");
			btAceptar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					dispose();
				}
			});
			btAceptar.setBounds(274, 276, 89, 42);
		}
		return btAceptar;
	}
	private JButton getBtCancelar() {
		if (btCancelar == null) {
			btCancelar = new JButton("Atr\u00E1s");
			btCancelar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					dispose();
				}
			});
			btCancelar.setBounds(381, 276, 89, 42);
		}
		return btCancelar;
	}
}

