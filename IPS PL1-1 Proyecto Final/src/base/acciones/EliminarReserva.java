package base.acciones;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import base.datos.Conexion;

public class EliminarReserva {
	
	Conexion con = new Conexion();

	public void execute(int ID_Reserva) throws ClassNotFoundException, SQLException {
		con.conectar();
		PreparedStatement pst = null;
		boolean cancelada= true;
		try {
			
			 pst = con.getCon().prepareStatement("UPDATE Reservas SET Cancelada='"+ cancelada +"' WHERE CodigoReserva='"+ID_Reserva+"'");
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
