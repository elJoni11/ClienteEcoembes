package ClienteEcoembes.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import ClienteEcoembes.facade.EcoembesController;
import ClienteEcoembes.dto.ContenedorDTO;
import ClienteEcoembes.dto.PlantaDTO;

public class AsignacionWindow extends JFrame {
	private JComboBox<PlantaDTO> comboPlantas;
    private JTextField txtFecha;
    private JList<ContenedorDTO> listContenedores;
    private DefaultListModel<ContenedorDTO> modeloListaContenedores;
    private JButton btnAsignar;
    private JButton btnRefrescar;

    public AsignacionWindow() {
        setTitle("Ecoembes - Gestión de Asignaciones");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 500);
        
        initUI();
        cargarDatos();
    }

    private void initUI() {
        setLayout(new BorderLayout(10, 10));

        // --- PANEL SUPERIOR (Selección de Planta y Fecha) ---
        JPanel panelNorte = new JPanel(new GridLayout(2, 2, 5, 5));
        panelNorte.setBorder(BorderFactory.createTitledBorder("Configuración de Asignación"));

        panelNorte.add(new JLabel("Seleccionar Planta de Reciclaje:"));
        comboPlantas = new JComboBox<>();
        panelNorte.add(comboPlantas);

        panelNorte.add(new JLabel("Fecha Prevista (YYYY-MM-DD):"));
        // Ponemos la fecha de mañana por defecto
        txtFecha = new JTextField(LocalDate.now().plusDays(1).toString());
        panelNorte.add(txtFecha);

        add(panelNorte, BorderLayout.NORTH);

        // --- PANEL CENTRAL (Lista de Contenedores) ---
        modeloListaContenedores = new DefaultListModel<>();
        listContenedores = new JList<>(modeloListaContenedores);
        listContenedores.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        
        JScrollPane scrollPane = new JScrollPane(listContenedores);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Seleccione uno o más contenedores (Ctrl+Click):"));
        
        add(scrollPane, BorderLayout.CENTER);

        // --- PANEL INFERIOR (Botones) ---
        JPanel panelSur = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnRefrescar = new JButton("Refrescar Datos");
        btnAsignar = new JButton("Realizar Asignación");
        
        panelSur.add(btnRefrescar);
        panelSur.add(btnAsignar);
        
        add(panelSur, BorderLayout.SOUTH);

        // Listeners
        btnRefrescar.addActionListener(e -> cargarDatos());
        btnAsignar.addActionListener(e -> realizarAsignacion());
    }

    private void cargarDatos() {
        EcoembesController controller = EcoembesController.getInstance();
        
        // 1. Cargar Plantas
        comboPlantas.removeAllItems();
        List<PlantaDTO> plantas = controller.getPlantas();
        if (plantas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No se pudieron cargar las plantas o no hay disponibles.", 
            		"Aviso", JOptionPane.WARNING_MESSAGE);
        } else {
            for (PlantaDTO p : plantas) {
                comboPlantas.addItem(p);
            }
        }

        // 2. Cargar Contenedores
        modeloListaContenedores.clear();
        List<ContenedorDTO> contenedores = controller.getContenedores();
        if (contenedores.isEmpty()) {
            // Si está vacío puede ser error o que no hay contenedores
        } else {
            for (ContenedorDTO c : contenedores) {
                modeloListaContenedores.addElement(c);
            }
        }
    }

    private void realizarAsignacion() {
        PlantaDTO plantaSeleccionada = (PlantaDTO) comboPlantas.getSelectedItem();
        String fecha = txtFecha.getText().trim();
        List<ContenedorDTO> contenedoresSeleccionados = listContenedores.getSelectedValuesList();

        // Validaciones GUI
        if (plantaSeleccionada == null) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar una planta.", 
            		"Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!validarFecha(fecha)) {
            JOptionPane.showMessageDialog(this, "Formato de fecha inválido. Use YYYY-MM-DD.", 
            		"Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (contenedoresSeleccionados.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar al menos un contenedor.", 
            		"Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Obtener IDs
        List<String> idsContenedores = new ArrayList<>();
        for (ContenedorDTO c : contenedoresSeleccionados) {
            idsContenedores.add(c.getContenedorID());
        }

        // Llamar al controlador
        boolean exito = EcoembesController.getInstance().realizarAsignacion(
                plantaSeleccionada.getPlantaID(), 
                fecha, 
                idsContenedores
        );

        if (exito) {
            JOptionPane.showMessageDialog(this, "¡Asignación realizada con éxito!", 
            		"Éxito", JOptionPane.INFORMATION_MESSAGE);
            // Recargar para ver cambios (ej. si los contenedores cambian de estado)
            cargarDatos();
        } else {
            JOptionPane.showMessageDialog(this, "Error al realizar la asignación. Verifique los datos o la conexión.", 
            		"Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private boolean validarFecha(String fecha) {
        try {
            LocalDate.parse(fecha, DateTimeFormatter.ISO_DATE);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
