 package igu;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import logica.Socio;

import org.jvnet.substance.SubstanceLookAndFeel;

import base.datos.GestorBaseDatos;

//=======================================================
//                                                      *
// Existen dos formas de acceder a la aplicación:       *
// Como administrador => Poner "admin" en el campo user *
//                                                      *
// Como usuario => Poner "user" en el campo user        *
//                                                      *
// Se proporcionan distintas opciones opciones de menú  * 
//                                                      *
//=======================================================
public class PanelInicio extends JFrame {

	private JPanel contentPane;
	public JTextField textFieldInicio;
	private GestorBaseDatos gbd;
	private JPasswordField passwordField;

	/**
	 * Launch the application. 
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JFrame.setDefaultLookAndFeelDecorated(true);
					JDialog.setDefaultLookAndFeelDecorated(true);
					SubstanceLookAndFeel 
							.setSkin("org.jvnet.substance.skin.MistSilverSkin");
					PanelInicio frame = new PanelInicio();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Create the frame.
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public PanelInicio() throws SQLException, ClassNotFoundException {
		setIconImage(Toolkit.getDefaultToolkit().getImage(PanelInicio.class.getResource("/img/logocentrosintitulo.png")));
		setTitle("Centro de Deportes S.A");
		gbd = new GestorBaseDatos();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 641, 515);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		
		JLabel lblBienvenida = new JLabel("INICIO");
		lblBienvenida.setHorizontalAlignment(SwingConstants.CENTER);
		lblBienvenida.setForeground(Color.DARK_GRAY);
		lblBienvenida.setFont(new Font("Trebuchet MS", Font.PLAIN, 79));
		lblBienvenida.setBounds(84, 148, 474, 72);
		contentPane.add(lblBienvenida);
		
		textFieldInicio = new JTextField();
		textFieldInicio.setForeground(Color.DARK_GRAY);
		textFieldInicio.setFont(new Font("Trebuchet MS", Font.PLAIN, 15));
		textFieldInicio.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldInicio.setBounds(221, 251, 208, 20);
		contentPane.add(textFieldInicio);
		textFieldInicio.setColumns(10);
		
		JButton btnInicio = new JButton("Entrar");
		btnInicio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mostrarVentanaRegistro();
			}
		});
		btnInicio.setFont(new Font("Tahoma", Font.PLAIN, 30));
		btnInicio.setBounds(221, 375, 208, 72);
		contentPane.add(btnInicio);
		
		JLabel lbUsuario = new JLabel("User:");
		lbUsuario.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lbUsuario.setBounds(288, 225, 50, 20);
		contentPane.add(lbUsuario);
		
		JLabel lbPassword = new JLabel("Password:");
		lbPassword.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lbPassword.setBounds(288, 282, 93, 28);
		contentPane.add(lbPassword);
		
		passwordField = new JPasswordField();
		passwordField.setHorizontalAlignment(SwingConstants.CENTER);
		passwordField.setBounds(221, 328, 208, 20);
		contentPane.add(passwordField);
		
		JLabel lbLogo = new JLabel("");
		lbLogo.setIcon(new ImageIcon(PanelInicio.class.getResource("/img/Imagen123.png")));
		lbLogo.setBounds(152, 0, 322, 159);
		contentPane.add(lbLogo);
	}
	
	public String getNombreUsuario(){
		return textFieldInicio.getText();
	}
	
	@SuppressWarnings("deprecation")
	private void mostrarVentanaRegistro(){
		if(textFieldInicio.getText().toString().toLowerCase().equals("user")){
			VentanaPrincipal vPrin = new VentanaPrincipal(this);
			vPrin.setLocationRelativeTo(null);
			vPrin.setVisible(true);
			dispose();
		}
		
			VentanaPrincipal vPrin = new VentanaPrincipal(this);
			vPrin.setLocationRelativeTo(null);
			vPrin.setVisible(true);
			dispose();
	}
	public String getUser(){
		return textFieldInicio.getText().toString();
	}
}
