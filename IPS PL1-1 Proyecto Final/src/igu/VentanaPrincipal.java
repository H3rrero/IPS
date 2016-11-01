package igu;


import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.Document;

import java.awt.Toolkit;

public class VentanaPrincipal extends JFrame {

	private JPanel contentPane;
	private JLabel lblLbpcaccesorios;
	private JButton btnBtcancelar;
	private JButton btnListarInstalaciones;
	private JButton btnHorarios;
	private JButton btnNewButton;
	private PanelInicio panelInicio;
	private JButton btEliminarReserva;
	private JButton btModificarReserva;
	private JPanel pnReserva;
	private JPanel panel;
	private JPanel panel_1;
	private JPanel panel_2;
	private JButton btnRealizarPagoEn;
	private Document doc;
	private JButton btListarActividadesSocio;
	private JButton btBotonCuota;
	private JButton btCrearActividadPuntual;
	private JButton btActividadesCompletas;
	private JPanel panel_3;
	private String usuario;
	private JButton btListarReservas;
	private JButton btCrearActividadPeriodica;
	private JButton btInscribirSocioActividad;
	private JButton btCancelarActividadSocio;
	private JButton btListarSociosAsistenTodas;
	private JButton btListarSociosNoAsistenTodas;

	
	/**
	 * Create the frame.
	 */
	public VentanaPrincipal(PanelInicio panelInicio) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(VentanaPrincipal.class.getResource("/img/logocentrosintitulo.png")));
		setResizable(false);		
		this.panelInicio = panelInicio;
		setTitle("Centro de Deportes S.A");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 875, 671);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getLblLbpcaccesorios());
		contentPane.add(getPnReserva());
		contentPane.add(getPanel());
		contentPane.add(getPanel_1());
		contentPane.add(getBtnBtcancelar());
		contentPane.add(getPanel_2());
		contentPane.add(getPanel_3());
		
		JPanel panel_4 = new JPanel();
		panel_4.setLayout(null);
		panel_4.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 3, true), "Gestion de Actividades", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		panel_4.setBounds(295, 365, 275, 256);
		contentPane.add(panel_4);
		
		JButton buttonListarActividades = new JButton("Listar Actividades");
		buttonListarActividades.setBounds(10, 22, 252, 47);
		panel_4.add(buttonListarActividades);
		panel_4.add(getBtListarActividadesSocio());
		panel_4.add(getBtListarSociosAsistenTodas());
		panel_4.add(getBtListarSociosNoAsistenTodas());
		buttonListarActividades.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					mostrarListadoActividades();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		setLocationRelativeTo(null);
		this.usuario=panelInicio.textFieldInicio.getText().toString();
		comprobarIdentidad(this.usuario);
		
		
	}
	
	private JLabel getLblLbpcaccesorios() {
		String elem = null;
		if (lblLbpcaccesorios == null) {
			if(panelInicio.getUser().equals("")){
				 elem = "admin";
			}
			if(this.usuario == null){
				this.usuario = "admin";
			}
			lblLbpcaccesorios = new JLabel("Usted se ha registrado como: "+ this.usuario + ". Elija una opcion");
			lblLbpcaccesorios.setFont(new Font("Tahoma", Font.PLAIN, 23));
			lblLbpcaccesorios.setForeground(Color.DARK_GRAY);
			lblLbpcaccesorios.setHorizontalAlignment(SwingConstants.CENTER);
			lblLbpcaccesorios.setBounds(108, 21, 648, 72);
		}
		return lblLbpcaccesorios;
	}
	
	
	private void mostrarVentanaRegistro() throws ClassNotFoundException, SQLException{
		VentanaReservaInstalacion vRegistro = new VentanaReservaInstalacion(this);
		vRegistro.setLocationRelativeTo(null);
		vRegistro.setModal(true);
		vRegistro.setVisible(true);
	}
	
	
	private JButton getBtnBtcancelar() {
		if (btnBtcancelar == null) {
			btnBtcancelar = new JButton("Salir");
			btnBtcancelar.setBounds(768, 598, 91, 33);
			btnBtcancelar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.exit(0);
				}
			});
		}
		return btnBtcancelar;
	}
	
	
	public void inicializar() {
		
	}
	private JButton getBtnListarInstalaciones() {
		if (btnListarInstalaciones == null) {
			btnListarInstalaciones = new JButton("Listar instalaciones");
			btnListarInstalaciones.setBounds(10, 24, 258, 47);
			btnListarInstalaciones.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					try {
						mostrarListaInstalaciones();
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
		return btnListarInstalaciones;
	}
	private JButton getBtnHorarios() {
		if (btnHorarios == null) {
			btnHorarios = new JButton("Horarios instalaciones");
			btnHorarios.setBounds(10, 82, 258, 47);
			btnHorarios.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					try {
						mostrarHorariosInstalaciones();
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
		return btnHorarios;
	}
	private JButton getBtnNewButton() {
		if (btnNewButton == null) {
			btnNewButton = new JButton("Reservar instalacion de car\u00E1cter puntual");
			btnNewButton.setBounds(10, 23, 253, 49);
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					try {
						mostrarVentanaRegistro();
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
		return btnNewButton;
	}
	private JButton getBtEliminarReserva() {
		if (btEliminarReserva == null) {
			btEliminarReserva = new JButton("Eliminar reserva de instalacion");
			btEliminarReserva.setBounds(10, 210, 255, 47);
			btEliminarReserva.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					try {
						mostrarVentanaCancelacionReserva();
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
		return btEliminarReserva;
	}
	protected void mostrarVentanaCancelacionReserva() throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		VentanaCancelacionReserva vCancelacionReserva = new VentanaCancelacionReserva(this);
		vCancelacionReserva.setLocationRelativeTo(null);
		vCancelacionReserva.setModal(true);
		vCancelacionReserva.setVisible(true);
	}
	private JButton getBtModificarReserva() {
		if (btModificarReserva == null) {
			btModificarReserva = new JButton("Modificar reserva de instalacion");
			btModificarReserva.setBounds(10, 147, 253, 49);
			btModificarReserva.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					try {
						mostrarModificacionReserva();
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
		return btModificarReserva;
	}
	private void mostrarModificacionReserva() throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		VentanaModificarReserva vCancelacionReserva = new VentanaModificarReserva(this);
		vCancelacionReserva.setLocationRelativeTo(null);
		vCancelacionReserva.setModal(true);
		vCancelacionReserva.setVisible(true);
	}
	private void mostrarListaInstalaciones() throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		VentanaListarInstalaciones vIns = new VentanaListarInstalaciones();
		vIns.setLocationRelativeTo(null);
		vIns.setModal(true);
		vIns.setVisible(true);
	}
	
	private void mostrarHorariosInstalaciones() throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		VentanaMostrarHorarios vHor = new VentanaMostrarHorarios();
		vHor.setLocationRelativeTo(null);
		vHor.setModal(true);
		vHor.setVisible(true);
	}
	private void mostrarListadoReservas() throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		VentanaListarReservas vHor = new VentanaListarReservas();
		vHor.setLocationRelativeTo(null);
		vHor.setModal(true);
		vHor.setVisible(true);
	}
	private void mostrarListadoActividades() throws ClassNotFoundException, SQLException {
		VentanaListarActividades vHor = new VentanaListarActividades();
		vHor.setLocationRelativeTo(null);
		vHor.setModal(true);
		vHor.setVisible(true);
	}
	private JPanel getPnReserva() {
		if (pnReserva == null) {
			pnReserva = new JPanel();
			pnReserva.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 3, true), "Reservas", TitledBorder.CENTER, TitledBorder.TOP, null, null));
			pnReserva.setBounds(580, 105, 275, 326);
			pnReserva.setLayout(null);
			pnReserva.add(getBtnNewButton());
			pnReserva.add(getBtModificarReserva());
			pnReserva.add(getBtEliminarReserva());
			
			
			
			JButton btReservaPeriodica = new JButton("Reservar instalacion de car\u00E1cter peri\u00F3dico");
			btReservaPeriodica.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					try {
						mostrarVentanaRegistroPerio();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			btReservaPeriodica.setBounds(10, 83, 253, 49);
			pnReserva.add(btReservaPeriodica);
			pnReserva.add(getBtListarReservas());
		
		}
		return pnReserva;
	}
	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 3, true), "Instalaciones", TitledBorder.CENTER, TitledBorder.TOP, null, null));
			panel.setBounds(10, 105, 278, 141);
			panel.setLayout(null);
			panel.add(getBtnListarInstalaciones());
			panel.add(getBtnHorarios());
		}
		return panel;
	}
	private JPanel getPanel_1() {
		if (panel_1 == null) {
			panel_1 = new JPanel();
			panel_1.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 3, true), "Actividades", TitledBorder.CENTER, TitledBorder.TOP, null, null));
			panel_1.setBounds(295, 105, 275, 256);
			panel_1.setLayout(null);
			panel_1.add(getBtCrearActividadPuntual());
			panel_1.add(getBtCrearActividadPeriodica());
			panel_1.add(getBtInscribirSocioActividad());
			panel_1.add(getBtCancelarActividadSocio());
		}
		return panel_1;
	}
	private JPanel getPanel_2() {
		if (panel_2 == null) {
			panel_2 = new JPanel();
			panel_2.setLayout(null);
			panel_2.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 3, true), "Pagos", TitledBorder.CENTER, TitledBorder.TOP, null, null));
			panel_2.setBounds(10, 285, 275, 146);
			panel_2.add(getBtnRealizarPagoEn());
			panel_2.add(getBtBotonCuota());
		}
		return panel_2;
	}
	private JButton getBtnRealizarPagoEn() {
		if (btnRealizarPagoEn == null) {
			btnRealizarPagoEn = new JButton("Realizar pago en met\u00E1lico");
			btnRealizarPagoEn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					mostrarVentanaPagoMetalico();
				}
			});
			btnRealizarPagoEn.setBounds(10, 23, 253, 49);
		}
		return btnRealizarPagoEn;
	}
	private void mostrarVentanaPagoMetalico() {
		// TODO Auto-generated method stub
		VentanaPagoMetalico vPm= new VentanaPagoMetalico(this);
		vPm.setLocationRelativeTo(null);
		vPm.setModal(true);
		vPm.setVisible(true);
	}
	private JButton getBtListarActividadesSocio() {
		if (btListarActividadesSocio == null) {
			btListarActividadesSocio = new JButton("Listar actividades de un socio");
			btListarActividadesSocio.setBounds(10, 78, 252, 47);
			btListarActividadesSocio.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					mostrarVentanaListarActividadesSocio();
				}
			});
		}
		return btListarActividadesSocio;
	}
	
	private void mostrarVentanaListarActividadesSocio() {
		VentanaListarActividadesSocio vPm= new VentanaListarActividadesSocio(this);
		vPm.setLocationRelativeTo(null);
		vPm.setModal(true);
		vPm.setVisible(true);
	}
	private JButton getBtBotonCuota() {
		if (btBotonCuota == null) {
			btBotonCuota = new JButton("Realizar pago en cuota socio");
			btBotonCuota.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					mostrarVentanaPagoCuota();
				}
			});
			btBotonCuota.setBounds(10, 83, 253, 49);
		}
		return btBotonCuota;
	}
	private void mostrarVentanaPagoCuota() {
		// TODO Auto-generated method stub
		VentanaPagoCuota vPm= new VentanaPagoCuota(this);
		vPm.setLocationRelativeTo(null);
		vPm.setModal(true);
		vPm.setVisible(true);
	}
	private void mostrarVentanaRegistroPerio() throws ClassNotFoundException, SQLException {
		VentanaReservaInstalacionCentro vPm= new VentanaReservaInstalacionCentro(this);
		vPm.setLocationRelativeTo(null);
		vPm.setModal(true);
		vPm.setVisible(true);
		
	}
	private JButton getBtCrearActividadPuntual() {
		if (btCrearActividadPuntual == null) {
			btCrearActividadPuntual = new JButton("Crear actividad de caracter puntual");
			btCrearActividadPuntual.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					try {
						mostrarVentanaCrearActividadPuntual();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			btCrearActividadPuntual.setBounds(10, 24, 252, 47);
		}
		return btCrearActividadPuntual;
	}
	private void mostrarVentanaCrearActividadPuntual() throws ClassNotFoundException, SQLException {
		VentanaCrearActividadPuntual vPm= new VentanaCrearActividadPuntual(this);
		vPm.setLocationRelativeTo(null);
		vPm.setModal(true);
		vPm.setVisible(true);
		
	}
	private JButton getBtActividadesCompletas() {
		if (btActividadesCompletas == null) {
			btActividadesCompletas = new JButton("Listar actividades sin plazas libres");
			btActividadesCompletas.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					try {
						mostrarVentanaComprobarActividadesLlenas();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			btActividadesCompletas.setBounds(10, 30, 252, 47);
		}
		return btActividadesCompletas;
	}
	private JPanel getPanel_3() {
		if (panel_3 == null) {
			panel_3 = new JPanel();
			panel_3.setLayout(null);
			panel_3.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 3, true), "Gesti\u00F3n de actividades", TitledBorder.CENTER, TitledBorder.TOP, null, null));
			panel_3.setBounds(10, 475, 275, 146);
			panel_3.add(getBtActividadesCompletas());
		}
		return panel_3;
	}
	private void mostrarVentanaComprobarActividadesLlenas() throws ClassNotFoundException, SQLException {
		VentanaComprobarActividadesLlenas vPm= new VentanaComprobarActividadesLlenas(this);
		vPm.setLocationRelativeTo(null);
		vPm.setModal(true);
		vPm.setVisible(true);
		
	}
	private void comprobarIdentidad(String usuario) {
		if(usuario.equalsIgnoreCase("user")){
			btActividadesCompletas.setEnabled(false);
			btBotonCuota.setEnabled(false);
			btCrearActividadPuntual.setEnabled(false);
			btListarActividadesSocio.setEnabled(false);
			btnListarInstalaciones.setEnabled(false);
			btnRealizarPagoEn.setEnabled(false);
			btListarReservas.setEnabled(false);
			btCrearActividadPuntual.setEnabled(false);
			btCrearActividadPeriodica.setEnabled(false);
			btListarSociosAsistenTodas.setEnabled(false);
			btListarSociosNoAsistenTodas.setEnabled(false);
		}
		
	}
	private JButton getBtListarReservas() {
		if (btListarReservas == null) {
			btListarReservas = new JButton("Listar reservas");
			btListarReservas.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					try {
						mostrarListadoReservas();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			btListarReservas.setBounds(8, 268, 255, 47);
		}
		return btListarReservas;
	}
	private JButton getBtCrearActividadPeriodica() {
		if (btCrearActividadPeriodica == null) {
			btCrearActividadPeriodica = new JButton("Crear actividad de caracter periodico");
			btCrearActividadPeriodica.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					try {
						mostrarVentanaInscribirActividadPeriodica();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			btCrearActividadPeriodica.setBounds(10, 82, 252, 47);
		}
		return btCrearActividadPeriodica;
	}
	private JButton getBtInscribirSocioActividad() {
		if (btInscribirSocioActividad == null) {
			btInscribirSocioActividad = new JButton("Inscribir a socio en actividad");
			btInscribirSocioActividad.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					try {
						mostrarVentanaInscribirSocioActividad();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			btInscribirSocioActividad.setBounds(10, 140, 252, 47);
		}
		return btInscribirSocioActividad;
	}
	private JButton getBtCancelarActividadSocio() {
		if (btCancelarActividadSocio == null) {
			btCancelarActividadSocio = new JButton("Cancelar inscripcion socio actividad");
			btCancelarActividadSocio.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					try {
						mostrarVentanaCancelarSocioActividad();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			btCancelarActividadSocio.setBounds(10, 198, 252, 47);
		}
		return btCancelarActividadSocio;
	}
	private JButton getBtListarSociosAsistenTodas() {
		if (btListarSociosAsistenTodas == null) {
			btListarSociosAsistenTodas = new JButton("Listar socios asisten todas sesiones");
			btListarSociosAsistenTodas.setBounds(10, 136, 252, 47);
			btListarSociosAsistenTodas.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					try {
						mostrarVentanaSociosAsistenTodas();
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
		return btListarSociosAsistenTodas;
	}
	private JButton getBtListarSociosNoAsistenTodas() {
		if (btListarSociosNoAsistenTodas == null) {
			btListarSociosNoAsistenTodas = new JButton("Listar socios no asisten todas sesiones");
			btListarSociosNoAsistenTodas.setBounds(10, 194, 252, 47);
			btListarSociosNoAsistenTodas.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					try {
						mostrarVentanaSociosNoAsistenTodas();
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
		return btListarSociosNoAsistenTodas;
	}
	private void mostrarVentanaInscribirSocioActividad() throws ClassNotFoundException, SQLException {
		IncluirSocioEnActividad vPm= new IncluirSocioEnActividad(this);
		vPm.setLocationRelativeTo(null);
		vPm.setModal(true);
		vPm.setVisible(true);
		
	}
	private void mostrarVentanaCancelarSocioActividad() throws ClassNotFoundException, SQLException {
		EliminarInscripcionSocio vPm= new EliminarInscripcionSocio(this);
		vPm.setLocationRelativeTo(null);
		vPm.setModal(true);
		vPm.setVisible(true);
		
	}
	private void mostrarVentanaSociosAsistenTodas() throws ClassNotFoundException, SQLException {
		ListarSociosSinFaltasaActividades vPm= new ListarSociosSinFaltasaActividades(this);
		vPm.setLocationRelativeTo(null);
		vPm.setModal(true);
		vPm.setVisible(true);
		
	}
	private void mostrarVentanaInscribirActividadPeriodica() throws ClassNotFoundException, SQLException {
		VentanaCrearActividadPeriodica vPm= new VentanaCrearActividadPeriodica(this);
		vPm.setLocationRelativeTo(null);
		vPm.setModal(true);
		vPm.setVisible(true);
		
	}
	private void mostrarVentanaSociosNoAsistenTodas() throws ClassNotFoundException, SQLException {
		VentanaListarSociosNoAsistentes vPm= new VentanaListarSociosNoAsistentes(this);
		vPm.setLocationRelativeTo(null);
		vPm.setModal(true);
		vPm.setVisible(true);
		
	}
}
