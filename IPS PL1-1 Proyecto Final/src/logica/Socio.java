package logica;

public class Socio {
	
	private static final double CUOTA_MENSUAL_SOCIO=35;
	
	private String nombre;
	private String apellidos;
	private String dni;
	private int telefono;
	private double importeMensual;
	
	public Socio(String nombre, String apellidos, String dni, int telefono) {
		
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.dni = dni;
		this.telefono = telefono;
		//this.importeMensual=CUOTA_MENSUAL_SOCIO;
	}
public Socio() {
	
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public int getTelefono() {
		return telefono;
	}

	public void setTelefono(int telefono) {
		this.telefono = telefono;
	}

	public double getImporteMensual() {
		return importeMensual;
	}

	public void setImporteMensual(double importeMensual) {
		this.importeMensual = importeMensual;
	}

	@Override
	public String toString() {
		return "Socio [nombre=" + nombre + ", apellidos=" + apellidos
				+ ", dni=" + dni + ", telefono=" + telefono
				+ ", importeMensual=" + importeMensual + "]";
	}

	
	
	
	

}
