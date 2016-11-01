package base.datos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import logica.Actividad;
import logica.Instalacion;
import logica.MatriculadoEn;
import logica.Reserva;
import logica.Socio;

public class GestorBaseDatos {

	private Conexion con = new Conexion();
	private List<Socio> socios = new ArrayList<Socio>();

	public List<Socio> listarSocios() throws SQLException,
			ClassNotFoundException {
		con.conectar();
		Statement st = con.getCon().createStatement();
		ResultSet rs = st.executeQuery("SELECT *  from Socios");
		while (rs.next()) {

			socios.add(new Socio(rs.getString("Nombre"), rs
					.getString("Apellidos"), rs.getString("DNI_Socio"), Integer
					.parseInt(rs.getString("Telefono"))));

		}
		// for(Socio s: socios){
		// System.out.println(s.toString()); //Muestra por pantalla los socios.
		// }
		st.close();
		rs.close();
		con.desconectar();
		return socios;

	}

	public List<Instalacion> listarInstalaciones() throws SQLException,
			ClassNotFoundException {
		List<Instalacion> instalaciones = new ArrayList<Instalacion>();
		con.conectar();
		Statement st = con.getCon().createStatement();
		ResultSet rs = st.executeQuery("SELECT *  from Instalaciones");
		while (rs.next()) {

			instalaciones.add(new Instalacion(rs.getString("Nombre"), Integer
					.parseInt(rs.getString("id")), Integer.parseInt(rs
					.getString("Precio_Hora"))));

		}
		// for(Instalacion s: instalaciones){
		// System.out.println(s.toString()); //Muestra por pantalla los socios.
		// }
		st.close();
		rs.close();
		con.desconectar();
		return instalaciones;

	}

	public List<Actividad> listarActividades() throws SQLException,
			ClassNotFoundException {
		List<Actividad> actividades = new ArrayList<Actividad>();
		con.conectar();
		Statement st = con.getCon().createStatement();
		ResultSet rs = st.executeQuery("SELECT *  from Actividades");
		while (rs.next()) {

			actividades.add(new Actividad(rs.getString("Nombre"), Integer
					.parseInt(rs.getString("ID_Actividad")), Integer
					.parseInt(rs.getString("Duracion_Semanas")), Integer
					.parseInt(rs.getString("Numero_plazas"))));

		}
		st.close();
		rs.close();
		con.desconectar();
		return actividades;

	}

	public List<MatriculadoEn> listarMatriculas() throws SQLException,
			ClassNotFoundException {
		List<MatriculadoEn> mEn = new ArrayList<MatriculadoEn>();
		con.conectar();
		Statement st = con.getCon().createStatement();
		ResultSet rs = st.executeQuery("SELECT *  from MatriculadoEn");
		while (rs.next()) {

			mEn.add(new MatriculadoEn(rs.getString("DNI_Socio"), rs
					.getInt("ID_Actividades"), Integer.parseInt(rs
					.getString("Asistencia_Semanas"))));

		}
		st.close();
		rs.close();
		con.desconectar();
		return mEn;
	}

	public List<Reserva> listarReservas() throws SQLException,
			ClassNotFoundException {
		List<Reserva> reservas = new ArrayList<Reserva>();
		con.conectar();
		Statement st = con.getCon().createStatement();
		ResultSet rs = st.executeQuery("SELECT *  from Reservas");
		while (rs.next()) {

			reservas.add(new Reserva(rs.getString("DNI_Socio"), Integer
					.parseInt(rs.getString("ID_Instalacion")), rs
					.getString("Horario_Reserva"), rs.getInt("Hora_Inicio"), rs
					.getInt("Hora_Fin"), Integer.parseInt(rs
					.getString("CodigoReserva")), rs.getBoolean("Cobro"), rs
					.getBoolean("Cancelada"), rs.getInt("Actividad")));
		}
		st.close();
		rs.close();
		con.desconectar();
		return reservas;
	}

	public String getNombrePorIdInstalacion(int idInstlacion)
			throws SQLException, ClassNotFoundException {
		con.conectar();
		Statement st = con.getCon().createStatement();
		ResultSet rs = st
				.executeQuery("SELECT nombre from Instalaciones where id = "
						+ idInstlacion);
		String nombre = "";
		while (rs.next()) {

			nombre = rs.getString("nombre");

		}
		st.close();
		rs.close();
		con.desconectar();
		return nombre;
	}

	public List<MatriculadoEn> listarMatriculados() throws SQLException,
			ClassNotFoundException {
		List<MatriculadoEn> matriculados = new ArrayList<MatriculadoEn>();
		con.conectar();
		Statement st = con.getCon().createStatement();
		ResultSet rs = st.executeQuery("SELECT *  from MatriculadoEn");
		while (rs.next()) {

			matriculados.add(new MatriculadoEn(rs.getString("DNI_Socio"),
					Integer.parseInt(rs.getString("ID_Actividades")), Integer
							.parseInt(rs.getString("Asistencia_Semanas"))));

		}
		st.close();
		rs.close();
		con.desconectar();
		return matriculados;

	}

}
