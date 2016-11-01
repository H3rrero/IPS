package base.acciones;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import base.datos.Conexion;

public class ModificarReserva {

	private static Connection conexion;
	Conexion con = new Conexion();
	public void execute(int ID_Instalacion,int Hora_Inicio,int Hora_fin, String horario,int ID_Reserva) throws SQLException, ClassNotFoundException {


		con.conectar();
		conexion= con.getCon();	
		//System.out.println(" "+horario);
		 PreparedStatement ps= conexion.prepareStatement("UPDATE Reservas SET ID_Instalacion='"+ID_Instalacion+"',Horario_Reserva='"+horario+"',Hora_Inicio='"+Hora_Inicio+ "',Hora_Fin='"+Hora_fin+"'WHERE CodigoReserva = '"+ ID_Reserva+"'");
		
		 ps.executeUpdate();
		 conexion.commit();
		ps.close();
		 conexion.close(); 
		 con.desconectar();
	}

}
