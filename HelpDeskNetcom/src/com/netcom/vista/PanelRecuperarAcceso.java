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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class PanelRecuperarAcceso extends JPanel {

    private static final long serialVersionUID = 1L;
    private JTextField txtUsuarioRed;
    private ventanaPrincipal marcoPadre;

    public PanelRecuperarAcceso(ventanaPrincipal marcoPadre) {
        this.marcoPadre = marcoPadre;

        // 1. Fondo general Hielo (#F4F9FB)
        setBackground(Color.decode("#F4F9FB"));
        setSize(1100, 680);
        setLayout(null);

        // 2. Tarjeta blanca central (420x390 centrada en la ventana)
        JPanel panelTarjeta = new JPanel();
        panelTarjeta.setBackground(Color.WHITE);
        panelTarjeta.setBounds(340, 130, 420, 390);
        panelTarjeta.setBorder(new LineBorder(new Color(224, 224, 224), 1, true));
        panelTarjeta.setLayout(null);
        add(panelTarjeta);

        // Franja superior gris suave de la tarjeta
        JPanel franjaSuperior = new JPanel();
        franjaSuperior.setBackground(Color.decode("#DCDCDC"));
        franjaSuperior.setBounds(0, 0, 420, 8);
        panelTarjeta.add(franjaSuperior);

        // 3. Círculo violeta/azul claro con el icono de llave
        JLabel lblIconoLlave = new JLabel("🔑", SwingConstants.CENTER);
        lblIconoLlave.setOpaque(true);
        lblIconoLlave.setBackground(Color.decode("#BAC8FF"));
        lblIconoLlave.setForeground(Color.decode("#1E2A78"));
        lblIconoLlave.setFont(new Font("Segoe UI Emoji", Font.BOLD, 22));
        lblIconoLlave.setBounds(180, 32, 60, 60);
        lblIconoLlave.setBorder(new LineBorder(Color.decode("#A5B8FF"), 1, true));
        panelTarjeta.add(lblIconoLlave);

        // 4. Título y descripción
        JLabel lblTitulo = new JLabel("Recuperar Acceso", SwingConstants.CENTER);
        lblTitulo.setForeground(new Color(33, 33, 33));
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitulo.setBounds(30, 108, 360, 28);
        panelTarjeta.add(lblTitulo);

        JLabel lblSubtitulo = new JLabel(
                "<html><center>Ingrese su usuario de red. Se enviará una solicitud al<br>equipo de TI para el restablecimiento de su clave.</center></html>",
                SwingConstants.CENTER);
        lblSubtitulo.setForeground(new Color(120, 130, 140));
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSubtitulo.setBounds(35, 140, 350, 36);
        panelTarjeta.add(lblSubtitulo);

        // 5. Campo "Usuario de Red"
        JLabel lblUsuario = new JLabel("Usuario de Red");
        lblUsuario.setForeground(new Color(66, 66, 66));
        lblUsuario.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblUsuario.setBounds(40, 192, 340, 18);
        panelTarjeta.add(lblUsuario);

        txtUsuarioRed = new JTextField();
        txtUsuarioRed.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtUsuarioRed.setBounds(40, 212, 340, 38);
        txtUsuarioRed.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(210, 215, 220), 1, true),
                BorderFactory.createEmptyBorder(5, 12, 5, 12)));
        panelTarjeta.add(txtUsuarioRed);

        // 6. Botón "Enviar Solicitud a TI" (#0095D9)
        JButton btnEnviarSolicitud = new JButton("Enviar Solicitud a TI");
        btnEnviarSolicitud.setBackground(Color.decode("#0095D9"));
        btnEnviarSolicitud.setForeground(Color.WHITE);
        btnEnviarSolicitud.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnEnviarSolicitud.setFocusPainted(false);
        btnEnviarSolicitud.setBorderPainted(false);
        btnEnviarSolicitud.setOpaque(true);
        btnEnviarSolicitud.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnEnviarSolicitud.setBounds(40, 270, 340, 40);
        panelTarjeta.add(btnEnviarSolicitud);

        // 7. Enlace inferior "← Volver al Inicio de Sesión"
        JLabel lblVolverLogin = new JLabel("←  Volver al Inicio de Sesión", SwingConstants.CENTER);
        lblVolverLogin.setForeground(new Color(110, 120, 130));
        lblVolverLogin.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblVolverLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblVolverLogin.setBounds(90, 328, 240, 25);
        panelTarjeta.add(lblVolverLogin);

        // =========================================================================
        // EVENTOS DE LA PANTALLA 4 (RF-09)
        // =========================================================================

        // Evento del botón "Enviar Solicitud a TI": valida y pasa a la Pantalla 5
        btnEnviarSolicitud.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                enviarSolicitudRecuperacion();
            }
        });

        // Evento de "Volver al Inicio de Sesión": regresa a la Pantalla 1 (Login)
        lblVolverLogin.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                txtUsuarioRed.setText("");
                if (marcoPadre != null) {
                    marcoPadre.mostrarPantalla("PANTALLA_LOGIN");
                }
            }
        });
    }

    private void enviarSolicitudRecuperacion() {
        String usuario = txtUsuarioRed.getText().trim();

        // Validamos que no esté vacío (RF-09 y RNF-22)
        if (usuario.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Por favor ingrese su usuario de red institucional.",
                    "Campo Obligatorio", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Limpiamos la caja y pasamos a la Pantalla 5 (Solicitud Enviada)
        txtUsuarioRed.setText("");
        if (marcoPadre != null) {
            marcoPadre.mostrarPantalla("PANTALLA_SOLICITUD_ENVIADA");
        }
    }
}