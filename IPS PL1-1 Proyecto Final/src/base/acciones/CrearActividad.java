package base.acciones;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import base.datos.Conexion;

public class CrearActividad {
	
	Conexion con = new Conexion();

	@SuppressWarnings("resource")
	public void execute(String nombre, int idActividad, int numPlazas, int duracion_semanas) throws ClassNotFoundException, SQLException {
		con.conectar();
		PreparedStatement pst = null;
		try {
			pst = con.getCon().prepareStatement("INSERT INTO Actividades "
					+ "(Nombre, ID_Actividad, Numero_Plazas,Duracion_Semanas) "
					+ "VALUES (?, ?, ?, ?)");
			pst.setString(1, nombre);
			pst.setInt(2, idActividad);
			pst.setInt(3, numPlazas);
			pst.setInt(4, duracion_semanas);
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
