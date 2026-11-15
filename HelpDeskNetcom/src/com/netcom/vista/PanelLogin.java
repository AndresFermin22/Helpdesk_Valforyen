package com.netcom.vista;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class PanelLogin extends JPanel {

    private static final long serialVersionUID = 1L;

    // Componentes del formulario
    private JTextField txtUsuario;
    private JPasswordField txtContrasena;
    private JCheckBox chkMostrarClave;
    private JButton btnIngresar;
    private JLabel lblOlvidoClave;

    // Variables para la lógica de requisitos (RF-04: Bloqueo al 5to intento)
    private int intentosFallidos = 0;
    private boolean cuentaBloqueada = false;

    // Referencia a la ventana principal para poder cambiar de pantalla después
    private ventanaPrincipal marcoPadre;

    public PanelLogin(ventanaPrincipal marcoPadre) {
        this.marcoPadre = marcoPadre;

        // 1. CONFIGURACIÓN DEL FONDO GENERAL (#F4F9FB)
        setBackground(Color.decode("#F4F9FB"));
        setSize(1100, 680);
        setLayout(null); // Permite mover elementos libremente en WindowBuilder

        // 2. TARJETA CENTRAL (Contenedor de 760x420 centrado en la pantalla de 1100x680)
        JPanel panelTarjeta = new JPanel();
        panelTarjeta.setBounds(170, 110, 760, 420);
        panelTarjeta.setBackground(Color.WHITE);
        panelTarjeta.setBorder(new LineBorder(new Color(224, 224, 224), 1, true));
        panelTarjeta.setLayout(null);
        add(panelTarjeta);

        // =========================================================================
        // 3. MITAD IZQUIERDA: PANEL AZUL ÍNDIGO (#2B3990)
        // =========================================================================
        JPanel panelIzquierdo = new JPanel();
        panelIzquierdo.setBackground(Color.decode("#2B3990"));
        panelIzquierdo.setBounds(0, 0, 380, 420);
        panelIzquierdo.setLayout(null);
        panelTarjeta.add(panelIzquierdo);

     // Representación del Logo NETCOM (Usando nuestra clase de utilidad)
        JLabel lblLogo = new JLabel("");
        
        // Llamamos al GestorImagenes, le pasamos la ruta de la foto, y le decimos que la queremos de 260x90 píxeles
        lblLogo.setIcon(com.netcom.utilidades.GestorImagenes.obtenerImagenEscalada("/com/netcom/recursos/logo netcom.png", 260, 90));
        
        // Posicionamos la imagen en el panel azul (X, Y, Ancho, Alto)
        lblLogo.setBounds(60, 110, 260, 90);
        panelIzquierdo.add(lblLogo);

        // Título "Portal de Soporte TI"
        JLabel lblTituloPortal = new JLabel("Portal de Soporte TI");
        lblTituloPortal.setForeground(Color.WHITE);
        lblTituloPortal.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTituloPortal.setBounds(45, 220, 290, 32);
        panelIzquierdo.add(lblTituloPortal);

        // Descripción inferior (usamos HTML básico en el JLabel para que haga salto de línea automático)
        JLabel lblDescripcion = new JLabel("<html>Acceso exclusivo para gestión de incidentes, mesa de ayuda y control de inventario de hardware.</html>");
        lblDescripcion.setForeground(new Color(210, 225, 245));
        lblDescripcion.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblDescripcion.setBounds(45, 260, 290, 45);
        panelIzquierdo.add(lblDescripcion);

        // =========================================================================
        // 4. MITAD DERECHA: FORMULARIO BLANCO DE INICIO DE SESIÓN
        // =========================================================================
        JLabel lblIniciarSesion = new JLabel("Iniciar Sesión", SwingConstants.CENTER);
        lblIniciarSesion.setForeground(new Color(33, 33, 33));
        lblIniciarSesion.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblIniciarSesion.setBounds(420, 55, 300, 30);
        panelTarjeta.add(lblIniciarSesion);

        // Etiqueta y Caja de Texto: Usuario
        JLabel lblUsuario = new JLabel("Usuario de Red Institucional");
        lblUsuario.setForeground(new Color(66, 66, 66));
        lblUsuario.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblUsuario.setBounds(420, 105, 300, 20);
        panelTarjeta.add(lblUsuario);

        txtUsuario = new JTextField();
        txtUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtUsuario.setBounds(420, 128, 300, 38);
        // Borde gris suave + margen interno de 10px a la izquierda para que el texto no quede pegado
        txtUsuario.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(210, 215, 220), 1, true),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        panelTarjeta.add(txtUsuario);

        // Etiqueta y Caja de Contraseña
        JLabel lblContrasena = new JLabel("Contraseña");
        lblContrasena.setForeground(new Color(66, 66, 66));
        lblContrasena.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblContrasena.setBounds(420, 180, 300, 20);
        panelTarjeta.add(lblContrasena);

        txtContrasena = new JPasswordField();
        txtContrasena.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtContrasena.setEchoChar('•'); // Carácter de puntos para ocultar la clave
        txtContrasena.setBounds(420, 203, 300, 38);
        txtContrasena.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(210, 215, 220), 1, true),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        panelTarjeta.add(txtContrasena);

        // Casilla "Mostrar contraseña" (RF-03)
        chkMostrarClave = new JCheckBox("Mostrar contraseña");
        chkMostrarClave.setBackground(Color.WHITE);
        chkMostrarClave.setForeground(new Color(110, 110, 110));
        chkMostrarClave.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        chkMostrarClave.setFocusPainted(false);
        chkMostrarClave.setCursor(new Cursor(Cursor.HAND_CURSOR));
        chkMostrarClave.setBounds(416, 250, 145, 23);
        panelTarjeta.add(chkMostrarClave);

        // Enlace "¿Olvidó su contraseña?" (RF-09)
        lblOlvidoClave = new JLabel("¿Olvidó su contraseña?", SwingConstants.RIGHT);
        lblOlvidoClave.setForeground(Color.decode("#2B3990"));
        lblOlvidoClave.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblOlvidoClave.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblOlvidoClave.setBounds(570, 250, 150, 23);
        panelTarjeta.add(lblOlvidoClave);

        // Botón "Ingresar al Sistema" (#0095D9)
        btnIngresar = new JButton("Ingresar al Sistema");
        btnIngresar.setBackground(Color.decode("#0095D9"));
        btnIngresar.setForeground(Color.WHITE);
        btnIngresar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnIngresar.setFocusPainted(false);
        btnIngresar.setBorderPainted(false);
        btnIngresar.setOpaque(true);
        btnIngresar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnIngresar.setBounds(420, 290, 300, 40);
        panelTarjeta.add(btnIngresar);

        // =========================================================================
        // 5. EVENTOS Y LÓGICA DE REQUISITOS (RF-01, RF-03, RF-04 y RF-09)
        // =========================================================================

        // Evento RF-03: Mostrar u ocultar contraseña
        chkMostrarClave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (chkMostrarClave.isSelected()) {
                    txtContrasena.setEchoChar((char) 0); // Muestra el texto claro
                } else {
                    txtContrasena.setEchoChar('•'); // Vuelve a poner puntos
                }
            }
        });

        // Evento RF-01 y RF-04: Validar ingreso al pulsar el botón
        btnIngresar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                validarIngreso();
            }
        });

        // Evento RF-09: Clic en "¿Olvidó su contraseña?"
     // Evento RF-09: Clic en "¿Olvidó su contraseña?" -> Abre Pantalla 4
        lblOlvidoClave.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (marcoPadre != null) {
                    marcoPadre.mostrarPantalla("PANTALLA_RECUPERAR_ACCESO");
                }
            }
        });
    }

    /**
     * Método que aplica las reglas de negocio RF-01, RF-04 y RNF-22
     */
    private void validarIngreso() {
        // 1. Verificar si la cuenta ya está bloqueada por RF-04
        if (cuentaBloqueada) {
            JOptionPane.showMessageDialog(this,
                    "Su cuenta se encuentra bloqueada temporalmente por 15 minutos debido a 5 intentos fallidos.",
                    "Acceso Bloqueado", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 2. Capturamos los textos y aplicamos .trim() (RNF-22: eliminar espacios sobrantes)
        String usuario = txtUsuario.getText().trim();
        String clave = new String(txtContrasena.getPassword()).trim();

        // 3. RF-01: Exigir que ambos campos estén diligenciados
        if (usuario.isEmpty() || clave.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Por favor diligencie tanto el usuario como la contraseña.",
                    "Campos Obligatorios", JOptionPane.WARNING_MESSAGE);
            return;
        }
     // 4. Direccionamiento según el rol (Admin/Ingeniero -> Pantalla 11 | Médico -> Pantalla 6 filtrada)
        if (usuario.equals("admin") && clave.equals("Admin123*")) {
            intentosFallidos = 0;
            marcoPadre.iniciarSesionUsuario("Ing. Andrés F. (Admin TI)", "Soporte TI", "Administrador de TI", "PANTALLA_BANDEJA_GLOBAL");
            
        } else if (usuario.equals("ingeniero") && clave.equals("Soporte123*")) {
            intentosFallidos = 0;
            marcoPadre.iniciarSesionUsuario("Ing. Andrés F.", "Soporte TI", "Ingeniero de Soporte", "PANTALLA_BANDEJA_GLOBAL");
            
        } else if (usuario.equals("medico") && clave.equals("Clinica123*")) {
            intentosFallidos = 0;
            marcoPadre.iniciarSesionUsuario("Fermin López", "Urgencias", "Médico General", "PANTALLA_BANDEJA_TICKETS");
            
        } else if (usuario.equals("karen") && clave.equals("Clinica123*")) {
            intentosFallidos = 0;
            marcoPadre.iniciarSesionUsuario("Karen Rueda", "Urgencias", "Médico General", "PANTALLA_BANDEJA_TICKETS");

        
        }else {
            // Si falla: aumentamos el registro de intentos (RF-04)
            intentosFallidos++;

            if (intentosFallidos >= 5) {
                cuentaBloqueada = true;
                btnIngresar.setEnabled(false); // Deshabilitamos el botón
                JOptionPane.showMessageDialog(this,
                        "Ha alcanzado 5 intentos fallidos consecutivos.\nSu cuenta ha sido bloqueada durante 15 minutos.",
                        "Cuenta Bloqueada", JOptionPane.ERROR_MESSAGE);
            } else {
                // RF-01 Criterio de aceptación: Mensaje único sin especificar cuál de los dos falló
                JOptionPane.showMessageDialog(this,
                        "Usuario o contraseña incorrectos.\nIntentos restantes: " + (5 - intentosFallidos),
                        "Error de Autenticación", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}