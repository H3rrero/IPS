package logica;

public class MatriculadoEn {
	
	private String dniSocio;
	private int nombreActividad;
	private int asistenciaSemanal;
	public MatriculadoEn(String dniSocio, int idActividad,int asistenciaSemanal) {
		super();
		this.dniSocio = dniSocio;
		this.nombreActividad = idActividad;
		this.asistenciaSemanal=asistenciaSemanal;
	}



	public String getDniSocio() {
		return dniSocio;
	}

	public void setDniSocio(String dniSocio) {
		this.dniSocio = dniSocio;
	}

	public int getNombreActividad() {
		return nombreActividad;
	}

	public void setNombreActividad(int idActividad) {
		this.nombreActividad = idActividad;
	}

	@Override
	public String toString() {
		return "MatriculadoEn [dniSocio=" + dniSocio + ", idActividad="
				+ nombreActividad + "]";
	}

	public int getAsistenciaSemanal() {
		return asistenciaSemanal;
	}

	public void setAsistenciaSemanal(int asistenciaSemanal) {
		this.asistenciaSemanal = asistenciaSemanal;
	}
	
	

}
