package ClienteEcoembes.dto;

import java.time.LocalDateTime;

public class ContenedorDTO {
	private String contenedorID;
    private String ubicacion;
    private int capacidad;
    private int codPostal;
    private LocalDateTime fechaConsulta;
    private int envasesEstimados;
    private String nivelLlenado; // String para simplificar (o copia el Enum)

    public ContenedorDTO() {
    }

    public String getContenedorID() {
        return contenedorID;
    }

    public void setContenedorID(String contenedorID) {
        this.contenedorID = contenedorID;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }
    
    public int getCodPostal() {
		return codPostal;
	}
	public void setCodPostal(int codPostal) {
		this.codPostal = codPostal;
	}

    public LocalDateTime getFechaConsulta() {
        return fechaConsulta;
    }

    public void setFechaConsulta(LocalDateTime fechaConsulta) {
        this.fechaConsulta = fechaConsulta;
    }

    public int getEnvasesEstimados() {
        return envasesEstimados;
    }

    public void setEnvasesEstimados(int envasesEstimados) {
        this.envasesEstimados = envasesEstimados;
    }

    public String getNivelLlenado() {
        return nivelLlenado;
    }

    public void setNivelLlenado(String nivelLlenado) {
        this.nivelLlenado = nivelLlenado;
    }
}
