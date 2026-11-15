package com.netcom.vista;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class PanelSolicitudEnviada extends JPanel {

    private static final long serialVersionUID = 1L;
    private ventanaPrincipal marcoPadre;

    public PanelSolicitudEnviada(ventanaPrincipal marcoPadre) {
        this.marcoPadre = marcoPadre;

        // 1. Fondo general Hielo (#F4F9FB)
        setBackground(Color.decode("#F4F9FB"));
        setSize(1100, 680);
        setLayout(null);

        // 2. Tarjeta blanca central (390x390 centrada en la pantalla)
        JPanel panelTarjeta = new JPanel();
        panelTarjeta.setBackground(Color.WHITE);
        panelTarjeta.setBounds(355, 130, 390, 390);
        panelTarjeta.setBorder(new LineBorder(new Color(224, 224, 224), 1, true));
        panelTarjeta.setLayout(null);
        add(panelTarjeta);

        // Franja superior Verde NETCOM (#8BC53F)
        JPanel franjaVerde = new JPanel();
        franjaVerde.setBackground(Color.decode("#8BC53F"));
        franjaVerde.setBounds(0, 0, 390, 8);
        panelTarjeta.add(franjaVerde);

        // 3. Círculo verde claro con el check (✔)
        JLabel lblIconoCheck = new JLabel("✔", SwingConstants.CENTER);
        lblIconoCheck.setOpaque(true);
        lblIconoCheck.setBackground(Color.decode("#DCFCE7"));
        lblIconoCheck.setForeground(Color.decode("#16A34A"));
        lblIconoCheck.setFont(new Font("Segoe UI Emoji", Font.BOLD, 26));
        lblIconoCheck.setBounds(160, 32, 70, 70);
        lblIconoCheck.setBorder(new LineBorder(Color.decode("#BBF7D0"), 1, true));
        panelTarjeta.add(lblIconoCheck);

        // 4. Título "¡Solicitud Enviada!"
        JLabel lblTitulo = new JLabel("¡Solicitud Enviada!", SwingConstants.CENTER);
        lblTitulo.setForeground(new Color(25, 25, 25));
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitulo.setBounds(30, 118, 330, 28);
        panelTarjeta.add(lblTitulo);

        // 5. Recuadro gris muy claro con el mensaje informativo
        JPanel panelMensaje = new JPanel();
        panelMensaje.setBackground(Color.decode("#F8FAFC"));
        panelMensaje.setBorder(new LineBorder(Color.decode("#EEF2F6"), 1, true));
        panelMensaje.setBounds(35, 160, 320, 125);
        panelMensaje.setLayout(null);
        panelTarjeta.add(panelMensaje);

        JLabel lblTextoInfo = new JLabel(
                "<html><center><font color='#64748B'>Se ha notificado exitosamente al área de Soporte TI sobre el restablecimiento de su contraseña.</font><br><br><font color='#334155'><b>Recibirá instrucciones en su correo institucional o extensión telefónica en los próximos minutos.</b></font></center></html>",
                SwingConstants.CENTER);
        lblTextoInfo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblTextoInfo.setBounds(15, 10, 290, 105);
        panelMensaje.add(lblTextoInfo);

        // 6. Botón azul oscuro "➜ Volver al Inicio de Sesión" (#004A8F)
        JButton btnVolverLogin = new JButton("➜  Volver al Inicio de Sesión");
        btnVolverLogin.setBackground(Color.decode("#004A8F"));
        btnVolverLogin.setForeground(Color.WHITE);
        btnVolverLogin.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnVolverLogin.setFocusPainted(false);
        btnVolverLogin.setBorderPainted(false);
        btnVolverLogin.setOpaque(true);
        btnVolverLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnVolverLogin.setBounds(35, 310, 320, 42);
        panelTarjeta.add(btnVolverLogin);

        // =========================================================================
        // EVENTO DEL BOTÓN: REGRESAR A LA PANTALLA 1 (LOGIN)
        // =========================================================================
        btnVolverLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (marcoPadre != null) {
                    marcoPadre.mostrarPantalla("PANTALLA_LOGIN");
                }
            }
        });
    }
}