package base.acciones.gestion;

import java.sql.SQLException;

import BussinesException.BusinessException;

public interface GestionApli {

	
	void añadirReserva(String DNI_Socio,int ID_Instalacion,int Hora_Inicio,int Hora_fin, String horario,boolean cobro, double importe, int id_Actividad) throws BusinessException, ClassNotFoundException, SQLException;
	void EliminarReserva(int id_reserva) throws BusinessException, ClassNotFoundException, SQLException;
	void añadirMatricula(String DNI_Socio,int ID_Instalacion,int asistencia) throws BusinessException, ClassNotFoundException, SQLException;
	void EliminarMatricula(String DNI_Socio,int idactividad) throws BusinessException, ClassNotFoundException, SQLException;
	void modificarReserva( int ID_Instalacion,int Hora_Inicio,int Hora_fin, String horario,int ID_Reserva) throws BusinessException, ClassNotFoundException, SQLException;
	void añadirReservaActividad(String DNI_Socio,int ID_Instalacion,int Hora_Inicio,int Hora_fin, String horario,boolean cobro, double importe,int idActividad) throws BusinessException, ClassNotFoundException, SQLException;
	void crearActividad(String nombre, int idActividad, int numPlazas, int duracion_semanas) throws ClassNotFoundException, SQLException;
	void UpdateActividad(int idActividad, int numPlazas) throws ClassNotFoundException, SQLException;
}
