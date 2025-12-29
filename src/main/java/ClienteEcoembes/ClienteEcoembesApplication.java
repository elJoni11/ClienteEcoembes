package ClienteEcoembes;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import ClienteEcoembes.gui.LoginWindow;

public class ClienteEcoembesApplication {

	public static void main(String[] args) {
		// Establecer el Look and Feel del sistema operativo
		try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }

        // Arrancar la interfaz gráfica en el hilo de eventos de Swing
        SwingUtilities.invokeLater(() -> {
            LoginWindow login = new LoginWindow();
            login.setLocationRelativeTo(null); // Centrar en pantalla
            login.setVisible(true);
        });
    }
}
