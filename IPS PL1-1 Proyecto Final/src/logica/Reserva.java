package logica;

public class Reserva {
	
	private String DNI;
	private int id_instalacion;
	private String horario;
	private int codigoReserva;
	private int horaInicio;
	private int horaFin;
	private boolean cobro;
	private boolean cancelada;
	private int id_actividad;
	
	public Reserva(String dNI, int id_instalacion, String horario,int Hora_Inicio,int Hora_Fin, int codigoReserva, boolean isCobro, boolean cancelada,int idactividad) {
		super();
		this.DNI= dNI;
		this.id_instalacion = id_instalacion;
		this.horario = horario;
		this.codigoReserva= codigoReserva;
		this.horaInicio=Hora_Inicio;
		this.horaFin=Hora_Fin;
		this.cobro = isCobro;
		this.cancelada= cancelada;
		this.id_actividad=idactividad;
	}

	public boolean isCancelada() {
		return cancelada;
	}


	public void setCancelada(boolean cancelada) {
		this.cancelada = cancelada;
	}


	public int getHoraInicio() {
		return horaInicio;
	}


	public int getHoraFin() {
		return horaFin;
	}


	public String getDNI() {
		return DNI;
	}


	public void setDNI(String dNI) {
		DNI = dNI;
	}


	public int getId_instalacion() {
		return id_instalacion;
	}


	public void setId_instalacion(int id_instalacion) {
		this.id_instalacion = id_instalacion;
	}


	public String getHorario() {
		return horario;
	}


	public void setHorario(String horario) {
		this.horario = horario;
	}


	public int getCodigoReserva() {
		return codigoReserva;
	}


	public void setCodigoReserva(int codigoReserva) {
		this.codigoReserva = codigoReserva;
	}


	public boolean isCobro() {
		return cobro;
	}


	public void setCobro(boolean cobro) {
		this.cobro = cobro;
	}

	public int getId_actividad() {
		return id_actividad;
	}

	public void setId_actividad(int id_actividad) {
		this.id_actividad = id_actividad;
	}
	
	
	

}
