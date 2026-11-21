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

public class PanelUsuarioEliminado extends JPanel {

    private static final long serialVersionUID = 1L;
    private ventanaPrincipal marcoPadre;

    public PanelUsuarioEliminado(ventanaPrincipal marcoPadre) {
        this.marcoPadre = marcoPadre;

        // 1. Fondo general Hielo (#F4F9FB)
        setBackground(Color.decode("#F4F9FB"));
        setSize(1100, 680);
        setLayout(null);

        // 2. Tarjeta blanca central (390x295 centrada en la ventana)
        JPanel panelTarjeta = new JPanel();
        panelTarjeta.setBackground(Color.WHITE);
        panelTarjeta.setBounds(355, 185, 390, 295);
        panelTarjeta.setBorder(new LineBorder(new Color(224, 224, 224), 1, true));
        panelTarjeta.setLayout(null);
        add(panelTarjeta);

        // 3. Recuadro/círculo verde suave con check oscuro (✔)
        JLabel lblIconoCheck = new JLabel("✔", SwingConstants.CENTER);
        lblIconoCheck.setOpaque(true);
        lblIconoCheck.setBackground(Color.decode("#E6F4EA"));
        lblIconoCheck.setForeground(new Color(45, 55, 72));
        lblIconoCheck.setFont(new Font("Segoe UI Emoji", Font.BOLD, 24));
        lblIconoCheck.setBounds(164, 34, 62, 62);
        lblIconoCheck.setBorder(new LineBorder(Color.decode("#D1EAD7"), 1, true));
        panelTarjeta.add(lblIconoCheck);

        // 4. Título "¡Usuario Eliminado!" en Azul Índigo (#2B3990)
        JLabel lblTitulo = new JLabel("¡Usuario Eliminado!", SwingConstants.CENTER);
        lblTitulo.setForeground(Color.decode("#2B3990"));
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 19));
        lblTitulo.setBounds(30, 114, 330, 26);
        panelTarjeta.add(lblTitulo);

        // 5. Mensaje de confirmación
        JLabel lblMensaje = new JLabel(
                "<html><center>El usuario ha sido eliminado e inhabilitado exitosamente de<br>la base de datos.</center></html>",
                SwingConstants.CENTER);
        lblMensaje.setForeground(new Color(115, 125, 135));
        lblMensaje.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblMensaje.setBounds(30, 146, 330, 36);
        panelTarjeta.add(lblMensaje);

        // 6. Botón "Aceptar y Cerrar" (#0095D9)
        JButton btnAceptarCerrar = new JButton("Aceptar y Cerrar");
        btnAceptarCerrar.setBackground(Color.decode("#0095D9"));
        btnAceptarCerrar.setForeground(Color.WHITE);
        btnAceptarCerrar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnAceptarCerrar.setFocusPainted(false);
        btnAceptarCerrar.setBorderPainted(false);
        btnAceptarCerrar.setOpaque(true);
        btnAceptarCerrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAceptarCerrar.setBounds(40, 212, 310, 42);
        panelTarjeta.add(btnAceptarCerrar);

        // =========================================================================
        // EVENTO: Regresa a la Pantalla 17 (Administración de Usuarios)
        // =========================================================================
        btnAceptarCerrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (marcoPadre != null) {
                    marcoPadre.mostrarPantalla("PANTALLA_ADMIN_USUARIOS");
                }
            }
        });
    }
}