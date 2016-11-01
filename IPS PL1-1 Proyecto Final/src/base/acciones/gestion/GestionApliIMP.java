package base.acciones.gestion;

import java.sql.SQLException;

import logica.MatriculadoEn;
import BussinesException.BusinessException;
import base.acciones.AñadirReserva;
import base.acciones.AñadirReservaActividad;
import base.acciones.CrearActividad;
import base.acciones.EliminarMatriculadoEn;
import base.acciones.EliminarReserva;
import base.acciones.ModificarReserva;

public class GestionApliIMP implements GestionApli {

	public void añadirReserva(String DNI_Socio, int ID_Instalacion,
			int Hora_Inicio, int Hora_fin, String horario, boolean cobro,
			double importe, int id_Actividad) throws BusinessException, ClassNotFoundException,
			SQLException {

		new AñadirReserva().execute(DNI_Socio, ID_Instalacion, Hora_Inicio,
				Hora_fin, horario, cobro, importe, id_Actividad);

	}

	public void EliminarReserva(int ID_reserva) throws BusinessException,
			ClassNotFoundException, SQLException {

		new EliminarReserva().execute(ID_reserva);

	}

	public void añadirMatricula(String DNI_Socio, int ID_Actividad,
			int asistencia) throws BusinessException, ClassNotFoundException,
			SQLException {

		new base.acciones.AñadirMatriculadoEn().execute(DNI_Socio,
				ID_Actividad, asistencia);
	}

	public void modificarReserva(int ID_Instalacion, int Hora_Inicio,
			int Hora_fin, String horario, int ID_Reserva)
			throws BusinessException, ClassNotFoundException, SQLException {

		new ModificarReserva().execute(ID_Instalacion, Hora_Inicio, Hora_fin,
				horario, ID_Reserva);
	}

	public void añadirReservaActividad(String DNI_Socio, int ID_Instalacion,
			int Hora_Inicio, int Hora_fin, String horario, boolean cobro,
			double importe, int idActividad) throws BusinessException,
			ClassNotFoundException, SQLException {

		new AñadirReservaActividad().execute(DNI_Socio, ID_Instalacion,
				Hora_Inicio, Hora_fin, horario, cobro, importe, idActividad);

	}

	public void EliminarMatricula(String DNI_Socio, int idActividad)
			throws BusinessException, ClassNotFoundException, SQLException {

		new EliminarMatriculadoEn().execute(DNI_Socio, idActividad);

	}

	public void crearActividad(String nombre, int idActividad, int numPlazas,
			int duracion_semanas) throws ClassNotFoundException, SQLException {
		new CrearActividad().execute(nombre, idActividad, numPlazas,
				duracion_semanas);

	}

	public void UpdateActividad(int id, int i) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		new base.acciones.UpdateActividad().execute(id, i);
	}

}
