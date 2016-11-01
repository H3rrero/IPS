package base.acciones;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import base.datos.Conexion;

public class UpdateActividad {

	private static Connection conexion;
	Conexion con = new Conexion();
	public void execute(int ID_Actividad,int plazas) throws SQLException, ClassNotFoundException {


		con.conectar();
		conexion= con.getCon();	
		//System.out.println(" "+horario);
		 PreparedStatement ps= conexion.prepareStatement("UPDATE Actividades SET Numero_Plazas='"+plazas+"'WHERE ID_Actividad = '"+ ID_Actividad+"'");
		
		 ps.executeUpdate();
		 conexion.commit();
		ps.close();
		 conexion.close(); 
		 con.desconectar();
	}
}
