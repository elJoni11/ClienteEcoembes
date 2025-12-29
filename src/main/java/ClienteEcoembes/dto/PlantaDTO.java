package ClienteEcoembes.dto;

import java.time.LocalDate;
import java.util.Map;

public class PlantaDTO {
	private String plantaID;
    private String nombre;
    private String ubicacion;
    private Map<LocalDate, Integer> capacidadDeterminada;

    public PlantaDTO() {
    }

    public String getPlantaID() {
        return plantaID;
    }

    public void setPlantaID(String plantaID) {
        this.plantaID = plantaID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Map<LocalDate, Integer> getCapacidadDeterminada() {
        return capacidadDeterminada;
    }

    public void setCapacidadDeterminada(Map<LocalDate, Integer> capacidadDeterminada) {
        this.capacidadDeterminada = capacidadDeterminada;
    }
    
    // Método auxiliar para mostrar el nombre en el ComboBox de la GUI
    @Override
    public String toString() {
        return nombre + " (" + plantaID + ")";
    }
}
