package logica;

public class Actividad {
	
	private String nombre;
	private int id_actividad;
	private int duracionSemanas;
	private int numeroPlazas;
	
	public Actividad(String nombre,  int id_actividad, int duracionSemanas,int numeroPlazas) {
		super();
		this.nombre = nombre;
		this.id_actividad = id_actividad;
		this.duracionSemanas=duracionSemanas;
		this.numeroPlazas=numeroPlazas;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	

	public int getId_actividad() {
		return id_actividad;
	}

	public void setId_actividad(int id_actividad) {
		this.id_actividad = id_actividad;
	}

	public int getDuracionSemanas() {
		return duracionSemanas;
	}

	public void setDuracionSemanas(int duracionSemanas) {
		this.duracionSemanas = duracionSemanas;
	}

	public int getNumeroPlazas() {
		return numeroPlazas;
	}

	public void setNumeroPlazas(int numeroPlazas) {
		this.numeroPlazas = numeroPlazas;
	}
	
	
	

}
