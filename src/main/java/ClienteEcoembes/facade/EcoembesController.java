package ClienteEcoembes.facade;

import java.util.List;

import ClienteEcoembes.dto.AsignacionRequestDTO;
import ClienteEcoembes.dto.PlantaDTO;
import ClienteEcoembes.dto.ContenedorDTO;
import ClienteEcoembes.proxies.ServiceProxy;

// Patrón Client Controller
// Implementa el patrón Singleton
public class EcoembesController {
	private static EcoembesController instance;
    private ServiceProxy serviceProxy;
    private String tokenSesion;

    // Constructor privado para Singleton.
    private EcoembesController() {
        this.serviceProxy = new ServiceProxy();
    }

    // Obtiene la instancia única del controlador.
    public static synchronized EcoembesController getInstance() {
        if (instance == null) {
            instance = new EcoembesController();
        }
        return instance;
    }

    /**
     * Intenta iniciar sesión en el servidor.
     * Si tiene éxito, guarda el token en memoria.
     */
    public boolean login(String email, String password) {
        // Delegamos en el Proxy
        String token = serviceProxy.login(email, password);
        
        if (token != null && !token.isEmpty()) {
            this.tokenSesion = token; // Guardamos el token para futuras llamadas
            return true;
        }
        return false;
    }

    // Obtiene la lista de plantas disponibles.
    public List<PlantaDTO> getPlantas() {
        if (tokenSesion == null) return List.of();
        return serviceProxy.getPlantas(tokenSesion);
    }

    // Obtiene la lista de contenedores.
    public List<ContenedorDTO> getContenedores() {
        if (tokenSesion == null) return List.of();
        return serviceProxy.getContenedores(tokenSesion);
    }

    // Realiza la asignación de contenedores a una planta.
    public boolean realizarAsignacion(String plantaId, String fecha, List<String> contenedoresIds) {
        // Validación básica antes de llamar al servidor
        if (tokenSesion == null || plantaId == null || fecha == null || contenedoresIds == null || contenedoresIds.isEmpty()) {
            return false;
        }

        try {
            // Crear el DTO de petición
            AsignacionRequestDTO dto = new AsignacionRequestDTO();
            dto.setPlantaID(plantaId);
            // Parsear la fecha string (YYYY-MM-DD) a LocalDate
            dto.setFechaPrevista(java.time.LocalDate.parse(fecha)); 
            dto.setListaContenedoresID(contenedoresIds);

            // Llamar al proxy con el token guardado
            return serviceProxy.asignar(tokenSesion, dto);
            
        } catch (Exception e) {
            System.err.println("Error en controlador al asignar: " + e.getMessage());
            return false;
        }
    }
    
    // Devuelve el token actual.
    public String getToken() {
        return tokenSesion;
    }
    
    // Cierra la sesión localmente.
    public void logout() {
        this.tokenSesion = null;
    }
}
