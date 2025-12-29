package ClienteEcoembes.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import ClienteEcoembes.facade.EcoembesController;

public class LoginWindow extends JFrame {
	private JTextField txtEmail;
    private JPasswordField txtPassword;
    private JButton btnLogin;

    public LoginWindow() {
        setTitle("Ecoembes - Inicio de Sesión");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setResizable(false);

        initUI();
    }

    private void initUI() {
        JPanel panelCentral = new JPanel(new GridLayout(3, 2, 10, 10));
        panelCentral.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panelCentral.add(new JLabel("Email:"));
        txtEmail = new JTextField("admin@ecoembes.com"); // Valor por defecto para facilitar pruebas
        panelCentral.add(txtEmail);

        panelCentral.add(new JLabel("Contraseña:"));
        txtPassword = new JPasswordField("password123");
        panelCentral.add(txtPassword);

        btnLogin = new JButton("Entrar");
        // Panel para el botón (para que no ocupe todo el ancho)
        JPanel panelBoton = new JPanel(); 
        panelBoton.add(btnLogin);

        // Listeners
        btnLogin.addActionListener(this::onLoginClick);
        
        // Permitir pulsar Enter en la contraseña para hacer login
        txtPassword.addActionListener(this::onLoginClick);

        add(panelCentral, BorderLayout.CENTER);
        add(panelBoton, BorderLayout.SOUTH);
    }

    private void onLoginClick(ActionEvent e) {
        String email = txtEmail.getText();
        String password = new String(txtPassword.getPassword());

        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, rellene todos los campos.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        btnLogin.setEnabled(false); // Evitar doble click

        // Llamada al Controlador (No bloqueante idealmente, pero simple para este prototipo)
        boolean exito = EcoembesController.getInstance().login(email, password);

        if (exito) {
            // Cierra login y abrir ventana principal
            AsignacionWindow mainWindow = new AsignacionWindow();
            mainWindow.setLocationRelativeTo(null);
            mainWindow.setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Credenciales inválidas o error de conexión.", "Login Fallido", JOptionPane.ERROR_MESSAGE);
            btnLogin.setEnabled(true);
        }
    }
}
