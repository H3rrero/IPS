package base.acciones;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import base.datos.Conexion;

public class EliminarMatriculadoEn {

	Conexion con = new Conexion();

	@SuppressWarnings("resource")
	public void execute(String DNI_Socio, int idActividad) throws ClassNotFoundException, SQLException {
		con.conectar();
		PreparedStatement pst = null;
		try {
			
			pst = con.getCon().prepareStatement("DELETE FROM MatriculadoEn "
					+ "WHERE DNI_Socio=? AND ID_Actividades=?");
			pst.setString(1, DNI_Socio);
			pst.setInt(2, idActividad);
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
