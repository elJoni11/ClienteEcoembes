package ClienteEcoembes.dto;

import java.time.LocalDate;
import java.util.List;

public class AsignacionRequestDTO {
	private String plantaID;
    private LocalDate fechaPrevista;
    private List<String> listaContenedoresID;

    public AsignacionRequestDTO() {
    }

    public String getPlantaID() {
        return plantaID;
    }

    public void setPlantaID(String plantaID) {
        this.plantaID = plantaID;
    }

    public LocalDate getFechaPrevista() {
        return fechaPrevista;
    }

    public void setFechaPrevista(LocalDate fechaPrevista) {
        this.fechaPrevista = fechaPrevista;
    }

    public List<String> getListaContenedoresID() {
        return listaContenedoresID;
    }

    public void setListaContenedoresID(List<String> listaContenedoresID) {
        this.listaContenedoresID = listaContenedoresID;
    }
}
