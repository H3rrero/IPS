package base.acciones;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import base.datos.Conexion;

public class AñadirMatriculadoEn {

	Conexion con = new Conexion();

	@SuppressWarnings("resource")
	public void execute(String DNI_Socio, int idActividad, int asistenciaSemanal) throws ClassNotFoundException, SQLException {
		con.conectar();
		PreparedStatement pst = null;
		try {
			
			pst = con.getCon().prepareStatement("INSERT INTO MatriculadoEn "
					+ "(DNI_Socio, ID_Actividades,Asistencia_Semanas) "
					+ "VALUES (?, ?, ?)");
			pst.setString(1, DNI_Socio);
			pst.setInt(2, idActividad);
			pst.setInt(3, asistenciaSemanal);
			pst.executeUpdate();
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			pst.close();
			con.desconectar();
		}
	}
}
