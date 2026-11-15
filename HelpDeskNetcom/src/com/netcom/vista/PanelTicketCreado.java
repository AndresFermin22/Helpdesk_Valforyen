package com.netcom.vista;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class PanelTicketCreado extends JPanel {

    private static final long serialVersionUID = 1L;

    private JLabel lblNumeroTicket;
    private ventanaPrincipal marcoPadre;

    public PanelTicketCreado(ventanaPrincipal marcoPadre) {
        this.marcoPadre = marcoPadre;

        // 1. Fondo general Hielo (#F4F9FB)
        setBackground(Color.decode("#F4F9FB"));
        setSize(1100, 680);
        setLayout(null);

        // 2. Tarjeta blanca central (420x395 centrada en la ventana)
        JPanel panelTarjeta = new JPanel();
        panelTarjeta.setBackground(Color.WHITE);
        panelTarjeta.setBounds(340, 125, 420, 395);
        panelTarjeta.setBorder(new LineBorder(new Color(224, 224, 224), 1, true));
        panelTarjeta.setLayout(null);
        add(panelTarjeta);

        // Franja superior gris suave
        JPanel franjaSuperior = new JPanel();
        franjaSuperior.setBackground(Color.decode("#E2E8F0"));
        franjaSuperior.setBounds(0, 0, 420, 6);
        panelTarjeta.add(franjaSuperior);

        // 3. Círculo verde limón suave con el check verde
        JLabel lblIconoExito = new JLabel("✔", SwingConstants.CENTER);
        lblIconoExito.setOpaque(true);
        lblIconoExito.setBackground(Color.decode("#D9F99D"));
        lblIconoExito.setForeground(Color.decode("#65A30D"));
        lblIconoExito.setFont(new Font("Segoe UI Emoji", Font.BOLD, 24));
        lblIconoExito.setBounds(178, 30, 64, 64);
        lblIconoExito.setBorder(new LineBorder(Color.decode("#BEF264"), 1, true));
        panelTarjeta.add(lblIconoExito);

        // 4. Título "¡Incidencia Registrada!" y subtítulo
        JLabel lblTitulo = new JLabel("¡Incidencia Registrada!", SwingConstants.CENTER);
        lblTitulo.setForeground(new Color(25, 25, 25));
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitulo.setBounds(30, 110, 360, 28);
        panelTarjeta.add(lblTitulo);

        JLabel lblSubtitulo = new JLabel(
                "<html><center>El equipo de TI ha recibido su reporte y lo atenderá<br>según la prioridad asignada.</center></html>",
                SwingConstants.CENTER);
        lblSubtitulo.setForeground(new Color(115, 125, 135));
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSubtitulo.setBounds(40, 142, 340, 36);
        panelTarjeta.add(lblSubtitulo);

        // 5. Recuadro punteado con el código único del ticket (RF-22)
        JPanel panelCodigo = new JPanel();
        panelCodigo.setBackground(Color.decode("#F8FAFC"));
        panelCodigo.setBorder(BorderFactory.createDashedBorder(new Color(203, 213, 225), 1.2f, 4.0f, 2.0f, true));
        panelCodigo.setBounds(40, 195, 340, 95);
        panelCodigo.setLayout(null);
        panelTarjeta.add(panelCodigo);

        JLabel lblTextoCodigo = new JLabel("SU NÚMERO DE TICKET ES:", SwingConstants.CENTER);
        lblTextoCodigo.setForeground(new Color(100, 116, 139));
        lblTextoCodigo.setFont(new Font("Segoe UI", Font.BOLD, 10));
        lblTextoCodigo.setBounds(20, 18, 300, 16);
        panelCodigo.add(lblTextoCodigo);

        lblNumeroTicket = new JLabel("TK-015", SwingConstants.CENTER);
        lblNumeroTicket.setForeground(Color.decode("#0095D9"));
        lblNumeroTicket.setFont(new Font("Segoe UI", Font.BOLD, 30));
        lblNumeroTicket.setBounds(20, 38, 300, 40);
        panelCodigo.add(lblNumeroTicket);

        // 6. Botones inferiores: "Ver mis tickets" y "Registrar otro"
        JButton btnVerMisTickets = new JButton("Ver mis tickets");
        btnVerMisTickets.setBackground(Color.WHITE);
        btnVerMisTickets.setForeground(new Color(55, 65, 81));
        btnVerMisTickets.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnVerMisTickets.setBorder(new LineBorder(new Color(210, 215, 220), 1, true));
        btnVerMisTickets.setFocusPainted(false);
        btnVerMisTickets.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnVerMisTickets.setBounds(40, 315, 162, 40);
        panelTarjeta.add(btnVerMisTickets);

        JButton btnRegistrarOtro = new JButton("Registrar otro");
        btnRegistrarOtro.setBackground(Color.decode("#0095D9"));
        btnRegistrarOtro.setForeground(Color.WHITE);
        btnRegistrarOtro.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnRegistrarOtro.setFocusPainted(false);
        btnRegistrarOtro.setBorderPainted(false);
        btnRegistrarOtro.setOpaque(true);
        btnRegistrarOtro.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRegistrarOtro.setBounds(218, 315, 162, 40);
        panelTarjeta.add(btnRegistrarOtro);

        // =========================================================================
        // EVENTOS DE LOS DOS BOTONES (RF-22)
        // =========================================================================

        // Botón "Ver mis tickets": Lleva a la Bandeja de Tickets (Pantalla 6)
        btnVerMisTickets.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (marcoPadre != null) {
                    marcoPadre.mostrarPantalla("PANTALLA_BANDEJA_TICKETS");
                }
            }
        });

        // Botón "Registrar otro": Regresa al formulario limpio de la Pantalla 7
        btnRegistrarOtro.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (marcoPadre != null) {
                    marcoPadre.mostrarPantalla("PANTALLA_REGISTRO_TICKET");
                }
            }
        });
    }

    /**
     * Recibe el código generado (ej. TK-016) y lo muestra en grande
     */
    public void mostrarCodigoTicket(String codigo) {
        lblNumeroTicket.setText(codigo);
    }
}