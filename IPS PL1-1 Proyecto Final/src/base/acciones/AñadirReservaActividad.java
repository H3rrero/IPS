package base.acciones;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import base.datos.Conexion;

public class AñadirReservaActividad {

	Conexion con = new Conexion();

	@SuppressWarnings("resource")
	public void execute(String DNI_Socio, int ID_Instalacion,int Hora_Inicio,int Hora_fin, String horario,boolean cobro, double importe,int IdActividad) throws ClassNotFoundException, SQLException {
		con.conectar();
		PreparedStatement pst = null;
		try {
			pst= con.getCon().prepareStatement("UPDATE Socios SET Importe_Mensual='"+importe+"'WHERE DNI_Socio = '"+ DNI_Socio+"'");
			pst.executeUpdate();
			pst = con.getCon().prepareStatement("INSERT INTO Reservas "
					+ "(DNI_Socio, ID_Instalacion, Horario_Reserva,Hora_Inicio,Hora_Fin, Cobro, Cancelada,Actividad) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?,?)");
			pst.setString(1, DNI_Socio);
			pst.setInt(2, ID_Instalacion);
			pst.setString(3, horario);
			pst.setInt(4, Hora_Inicio);
			pst.setInt(5, Hora_fin);
			pst.setBoolean(6, cobro);
			pst.setBoolean(7, false);
			pst.setInt(8,IdActividad );
			pst.executeUpdate();
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			pst.close();
			con.desconectar();
		}

}}
