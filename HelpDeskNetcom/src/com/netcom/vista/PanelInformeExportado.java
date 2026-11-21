package com.netcom.vista;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class PanelInformeExportado extends JPanel {

    private static final long serialVersionUID = 1L;

    private JLabel lblMensajeFormato;
    private ventanaPrincipal marcoPadre;

    // Guardamos los parámetros del último reporte para mostrarlos al pulsar "Abrir Archivo"
    private String resumenUltimoReporte = "";

    public PanelInformeExportado(ventanaPrincipal marcoPadre) {
        this.marcoPadre = marcoPadre;

        // 1. Fondo general Hielo (#F4F9FB)
        setBackground(Color.decode("#F4F9FB"));
        setSize(1100, 680);
        setLayout(null);

        // 2. Tarjeta blanca central (360x290 centrada en la ventana)
        JPanel panelTarjeta = new JPanel();
        panelTarjeta.setBackground(Color.WHITE);
        panelTarjeta.setBounds(370, 185, 360, 290);
        panelTarjeta.setBorder(new LineBorder(new Color(224, 224, 224), 1, true));
        panelTarjeta.setLayout(null);
        add(panelTarjeta);

        // 3. Círculo verde claro con check oscuro (✔)
        JLabel lblIconoCheck = new JLabel("✔", SwingConstants.CENTER);
        lblIconoCheck.setOpaque(true);
        lblIconoCheck.setBackground(Color.decode("#E6F4EA"));
        lblIconoCheck.setForeground(new Color(45, 55, 72));
        lblIconoCheck.setFont(new Font("Segoe UI Emoji", Font.BOLD, 24));
        lblIconoCheck.setBounds(148, 30, 64, 64);
        lblIconoCheck.setBorder(new LineBorder(Color.decode("#D1EAD7"), 1, true));
        panelTarjeta.add(lblIconoCheck);

        // 4. Título "¡Informe Exportado!" en Azul Índigo (#2B3990)
        JLabel lblTitulo = new JLabel("¡Informe Exportado!", SwingConstants.CENTER);
        lblTitulo.setForeground(Color.decode("#2B3990"));
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 19));
        lblTitulo.setBounds(25, 110, 310, 26);
        panelTarjeta.add(lblTitulo);

        // 5. Mensaje dinámico con el formato elegido (Excel .xlsx o PDF .pdf)
        lblMensajeFormato = new JLabel(
                "<html><center>El reporte de tickets se ha generado y descargado<br>correctamente en formato Excel (.xlsx) en su equipo.</center></html>",
                SwingConstants.CENTER);
        lblMensajeFormato.setForeground(new Color(100, 116, 139));
        lblMensajeFormato.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblMensajeFormato.setBounds(20, 144, 320, 40);
        panelTarjeta.add(lblMensajeFormato);

        // 6. Botón "Abrir Archivo" (#0095D9)
        JButton btnAbrirArchivo = new JButton("Abrir Archivo");
        btnAbrirArchivo.setBackground(Color.decode("#0095D9"));
        btnAbrirArchivo.setForeground(Color.WHITE);
        btnAbrirArchivo.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnAbrirArchivo.setFocusPainted(false);
        btnAbrirArchivo.setBorderPainted(false);
        btnAbrirArchivo.setOpaque(true);
        btnAbrirArchivo.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAbrirArchivo.setBounds(28, 210, 304, 40);
        panelTarjeta.add(btnAbrirArchivo);

        // =========================================================================
        // EVENTO DEL BOTÓN "Abrir Archivo"
        // =========================================================================
        btnAbrirArchivo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(PanelInformeExportado.this,
                        "Simulación de apertura del archivo descargado:\n\n" + resumenUltimoReporte +
                        "\n\n(Al conectar MySQL aquí se abrirá el documento real en Excel o el visor PDF).",
                        "Vista Previa de Archivo Exportado", JOptionPane.INFORMATION_MESSAGE);

                if (marcoPadre != null) {
                    marcoPadre.mostrarPantalla("PANTALLA_BANDEJA_GLOBAL");
                }
            }
        });
    }

    /**
     * Recibe los filtros elegidos en la Pantalla 13 para mostrar el formato exacto en pantalla
     */
    public void cargarResumenExportacion(String rango, String area, String estado, String formato, boolean incluirDetalle) {
        lblMensajeFormato.setText(
                "<html><center>El reporte de tickets se ha generado y descargado<br>correctamente en formato "
                        + formato + " en su equipo.</center></html>");

        String extension = formato.contains(".pdf") ? ".pdf" : ".xlsx";
        this.resumenUltimoReporte =
                "• Archivo: Reporte_Tickets_NETCOM" + extension + "\n" +
                "• Rango de fechas: " + rango + "\n" +
                "• Área clínica: " + area + "\n" +
                "• Estado filtrado: " + estado + "\n" +
                "• Incluye descripción y comentarios: " + (incluirDetalle ? "Sí" : "No");
    }
}