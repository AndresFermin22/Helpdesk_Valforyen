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

public class PanelTicketResuelto extends JPanel {

    private static final long serialVersionUID = 1L;

    private JLabel lblMensajeTicket;
    private ventanaPrincipal marcoPadre;

    public PanelTicketResuelto(ventanaPrincipal marcoPadre) {
        this.marcoPadre = marcoPadre;

        // 1. Fondo general Hielo (#F4F9FB)
        setBackground(Color.decode("#F4F9FB"));
        setSize(1100, 680);
        setLayout(null);

        // 2. Tarjeta blanca central (360x310 centrada en la pantalla)
        JPanel panelTarjeta = new JPanel();
        panelTarjeta.setBackground(Color.WHITE);
        panelTarjeta.setBounds(370, 175, 360, 310);
        panelTarjeta.setBorder(new LineBorder(new Color(224, 224, 224), 1, true));
        panelTarjeta.setLayout(null);
        add(panelTarjeta);

        // 3. Círculo verde NETCOM (#8BC53F) con check blanco
        JLabel lblIconoCheck = new JLabel("✔", SwingConstants.CENTER);
        lblIconoCheck.setOpaque(true);
        lblIconoCheck.setBackground(Color.decode("#8BC53F"));
        lblIconoCheck.setForeground(Color.WHITE);
        lblIconoCheck.setFont(new Font("Segoe UI Emoji", Font.BOLD, 28));
        lblIconoCheck.setBounds(143, 30, 74, 74);
        lblIconoCheck.setBorder(new LineBorder(Color.decode("#ECFCCB"), 5, true));
        panelTarjeta.add(lblIconoCheck);

        // 4. Título "¡Ticket Resuelto!" en Azul Índigo (#2B3990)
        JLabel lblTitulo = new JLabel("¡Ticket Resuelto!", SwingConstants.CENTER);
        lblTitulo.setForeground(Color.decode("#2B3990"));
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitulo.setBounds(25, 122, 310, 28);
        panelTarjeta.add(lblTitulo);

        // 5. Mensaje dinámico con el código del ticket en negrita
        lblMensajeTicket = new JLabel(
                "<html><center>El ticket <font color='#2B3990'><b>TK-101</b></font> ha sido marcado como resuelto<br>correctamente y el usuario ha sido notificado.</center></html>",
                SwingConstants.CENTER);
        lblMensajeTicket.setForeground(new Color(100, 116, 139));
        lblMensajeTicket.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblMensajeTicket.setBounds(25, 158, 310, 42);
        panelTarjeta.add(lblMensajeTicket);

        // 6. Botón "Volver a la Bandeja" (#0095D9)
        JButton btnVolverBandeja = new JButton("Volver a la Bandeja");
        btnVolverBandeja.setBackground(Color.decode("#0095D9"));
        btnVolverBandeja.setForeground(Color.WHITE);
        btnVolverBandeja.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnVolverBandeja.setFocusPainted(false);
        btnVolverBandeja.setBorderPainted(false);
        btnVolverBandeja.setOpaque(true);
        btnVolverBandeja.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnVolverBandeja.setBounds(45, 225, 270, 42);
        panelTarjeta.add(btnVolverBandeja);

        btnVolverBandeja.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (marcoPadre != null) {
                    marcoPadre.volverPantallaInicio();
                }
            }
        });
    }

    /**
     * Inserta dinámicamente el código del ticket que se acaba de resolver (ej. TK-104)
     */
    public void mostrarTicketResuelto(String idTicket) {
        lblMensajeTicket.setText(
                "<html><center>El ticket <font color='#2B3990'><b>" + idTicket + "</b></font> ha sido marcado como resuelto<br>correctamente y el usuario ha sido notificado.</center></html>");
    }
}