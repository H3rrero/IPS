package logica;

public class Instalacion {
	
	private String nombre;
	private int id;
	
	
	private int precioHora;
	
	public Instalacion(String nombre, int id, 
			int precioHora) {
		
		this.nombre = nombre;
		this.id = id;
		this.precioHora = precioHora;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public int getPrecioHora() {
		return precioHora;
	}

	public void setPrecioHora(int precioHora) {
		this.precioHora = precioHora;
	}
	@Override
	public String toString() {
		return "Instalacion [nombre=" + nombre + ", id=" + id + ", precioHora="
				+ precioHora + "]";
	}
	
	
	
	

}
