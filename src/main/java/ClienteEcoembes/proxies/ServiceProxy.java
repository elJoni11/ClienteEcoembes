package ClienteEcoembes.proxies;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import ClienteEcoembes.dto.AsignacionDTO;
import ClienteEcoembes.dto.AsignacionRequestDTO;
import ClienteEcoembes.dto.LoginRequestDTO;
import ClienteEcoembes.dto.PlantaDTO;
import ClienteEcoembes.dto.ContenedorDTO;

// Patrón Service Proxy
public class ServiceProxy {
	// URL base del Servidor Ecoembes (Puerto 8082)
    private static final String BASE_URL = "http://localhost:8082/api";
    private final RestTemplate restTemplate;
    public ServiceProxy() {
        this.restTemplate = new RestTemplate();
    }

    // Realiza el login y devuelve el token.
    public String login(String email, String password) {
        String url = BASE_URL + "/login/iniciarSesion";
        
        LoginRequestDTO request = new LoginRequestDTO();
        request.setEmail(email);
        request.setPassword(password);

        try {
            // POST /api/login/iniciarSesion
            ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);
            
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return (String) response.getBody().get("token");
            }
        } catch (HttpClientErrorException e) {
            System.err.println("Error Login: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
        } catch (Exception e) {
            System.err.println("Error conexión: " + e.getMessage());
        }
        return null; // Login fallido
    }

    // Obtiene la lista de plantas disponibles.
    public List<PlantaDTO> getPlantas(String token) {
        // GET /api/plantas?token=...
        String url = BASE_URL + "/plantas?token=" + token;
        
        try {
            ResponseEntity<PlantaDTO[]> response = restTemplate.getForEntity(url, PlantaDTO[].class);
            if (response.getBody() != null) {
                return Arrays.asList(response.getBody());
            }
        } catch (Exception e) {
            System.err.println("Error obteniendo plantas: " + e.getMessage());
        }
        return List.of();
    }

    // Obtiene la lista de contenedores.
    public List<ContenedorDTO> getContenedores(String token) {
        // GET /api/contenedor?token=...
        String url = BASE_URL + "/contenedor?token=" + token;
        
        try {
            ResponseEntity<ContenedorDTO[]> response = restTemplate.getForEntity(url, ContenedorDTO[].class);
            if (response.getBody() != null) {
                return Arrays.asList(response.getBody());
            }
        } catch (Exception e) {
            System.err.println("Error obteniendo contenedores: " + e.getMessage());
        }
        return List.of();
    }

    // Envía una asignación al servidor.
    public boolean asignar(String token, AsignacionRequestDTO dto) {
        // POST /api/asignacion?token=...
        String url = BASE_URL + "/asignacion?token=" + token;
        
        try {
            ResponseEntity<AsignacionDTO> response = restTemplate.postForEntity(url, dto, AsignacionDTO.class);
            return response.getStatusCode().is2xxSuccessful();
        } catch (HttpClientErrorException e) {
            System.err.println("Error Asignación: " + e.getResponseBodyAsString());
            return false;
        } catch (Exception e) {
            System.err.println("Error conexión: " + e.getMessage());
            return false;
        }
    }
}
