package base.datos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class Conexion {

	private static Connection con;
	
	public void conectar() throws ClassNotFoundException, SQLException 
	{
		Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
		con=DriverManager.getConnection("jdbc:ucanaccess://IPS.accdb");
	}
	
	public void desconectar() throws SQLException{
		this.con.close();
	}
	
	public Connection getCon() {
		return con;
	}
	

//		 PreparedStatement pst = con.prepareStatement("INSERT INTO Socios "
//		           + "(DNI_Socio, Nombre) "
//		           + "VALUES (?, ?)");
//
//		 pst.setInt(1, 98799999);
//		 pst.setString(2, "Difficulty String");
//		  
//		 pst.executeUpdate();
//		 
//		 pst = con.prepareStatement("DELETE FROM Socios WHERE Nombre='Darío'");
//		 pst.executeUpdate();
//		 pst.close();
//		 rs.close(); 
//		 st.close(); 
//		 con.close();
		
	
}

