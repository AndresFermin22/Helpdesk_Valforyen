package com.netcom.vista;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

import com.netcom.utilidades.CatalogoClinica;

public class PanelExportarTickets extends JPanel {

    private static final long serialVersionUID = 1L;

    private ventanaPrincipal marcoPadre;

    // Controles de filtros de exportación (RF-52)
    private JComboBox<String> cmbRangoFechas;
    private JComboBox<String> cmbAreaClinica;
    private JComboBox<String> cmbEstadoTicket;
    private JComboBox<String> cmbFormato;
    private JCheckBox chkIncluirDetalle;

    public PanelExportarTickets(ventanaPrincipal marcoPadre) {
        this.marcoPadre = marcoPadre;

        // 1. Fondo general Hielo (#F4F9FB)
        setBackground(Color.decode("#F4F9FB"));
        setSize(1100, 680);
        setLayout(null);

        // 2. Tarjeta modal central blanca (500x410 centrada en la ventana)
        JPanel panelTarjeta = new JPanel();
        panelTarjeta.setBackground(Color.WHITE);
        panelTarjeta.setBounds(300, 125, 500, 410);
        panelTarjeta.setBorder(new LineBorder(new Color(224, 224, 224), 1, true));
        panelTarjeta.setLayout(null);
        add(panelTarjeta);

        // =========================================================================
        // 3. ENCABEZADO DE LA TARJETA (Icono, "Exportar Reporte" y botón X)
        // =========================================================================
        JPanel panelCabecera = new JPanel();
        panelCabecera.setBackground(Color.WHITE);
        panelCabecera.setBounds(1, 1, 498, 66);
        panelCabecera.setBorder(new MatteBorder(0, 0, 1, 0, new Color(235, 240, 245)));
        panelCabecera.setLayout(null);
        panelTarjeta.add(panelCabecera);

        JLabel lblIconoDoc = new JLabel("📄", SwingConstants.CENTER);
        lblIconoDoc.setOpaque(true);
        lblIconoDoc.setBackground(Color.decode("#E0F2FE"));
        lblIconoDoc.setForeground(Color.decode("#0095D9"));
        lblIconoDoc.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 18));
        lblIconoDoc.setBounds(24, 15, 36, 36);
        panelCabecera.add(lblIconoDoc);

        JLabel lblTitulo = new JLabel("Exportar Reporte");
        lblTitulo.setForeground(Color.decode("#2B3990"));
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitulo.setBounds(72, 20, 250, 26);
        panelCabecera.add(lblTitulo);

        JLabel lblCerrarX = new JLabel("✕", SwingConstants.CENTER);
        lblCerrarX.setForeground(new Color(148, 163, 184));
        lblCerrarX.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblCerrarX.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblCerrarX.setBounds(450, 18, 30, 30);
        panelCabecera.add(lblCerrarX);

        // =========================================================================
        // 4. DESCRIPCIÓN Y FILTROS 2x2 (RF-52 y RNF-47)
        // =========================================================================
        JLabel lblInstrucciones = new JLabel(
                "<html>Seleccione los parámetros y filtros deseados para generar el consolidado de tickets de la plataforma.</html>");
        lblInstrucciones.setForeground(new Color(100, 116, 139));
        lblInstrucciones.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblInstrucciones.setBounds(26, 82, 448, 34);
        panelTarjeta.add(lblInstrucciones);

        // Fila 1 Izquierda: Rango de Fechas
        JLabel lblRango = new JLabel("Rango de Fechas");
        lblRango.setForeground(Color.decode("#2B3990"));
        lblRango.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblRango.setBounds(26, 130, 200, 16);
        panelTarjeta.add(lblRango);

        cmbRangoFechas = new JComboBox<String>();
        cmbRangoFechas.setModel(new DefaultComboBoxModel<String>(new String[] {
            "Últimos 7 días",
            "Últimos 15 días",
            "Últimos 30 días",
            "Últimos 2 meses",
            "Últimos 6 meses",
            "Último año",
            "Todo el historial"
        }));
        cmbRangoFechas.setBackground(Color.decode("#F8FAFC"));
        cmbRangoFechas.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        cmbRangoFechas.setBounds(26, 150, 212, 36);
        panelTarjeta.add(cmbRangoFechas);

        // Fila 1 Derecha: Área Clínica / Servicio (Desde CatalogoClinica)
        JLabel lblArea = new JLabel("Área Clínica / Servicio");
        lblArea.setForeground(Color.decode("#2B3990"));
        lblArea.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblArea.setBounds(262, 130, 200, 16);
        panelTarjeta.add(lblArea);

        cmbAreaClinica = new JComboBox<String>();
        cmbAreaClinica.setModel(new DefaultComboBoxModel<String>(CatalogoClinica.obtenerAreasClinicas(true)));
        cmbAreaClinica.setBackground(Color.decode("#F8FAFC"));
        cmbAreaClinica.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        cmbAreaClinica.setBounds(262, 150, 212, 36);
        panelTarjeta.add(cmbAreaClinica);

        // Fila 2 Izquierda: Estado del Ticket (6 estados oficiales de los requisitos)
        JLabel lblEstado = new JLabel("Estado del Ticket");
        lblEstado.setForeground(Color.decode("#2B3990"));
        lblEstado.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblEstado.setBounds(26, 202, 200, 16);
        panelTarjeta.add(lblEstado);

        cmbEstadoTicket = new JComboBox<String>();
        cmbEstadoTicket.setModel(new DefaultComboBoxModel<String>(new String[] {
            "Cualquier Estado",
            "Abierto",
            "En Proceso",
            "Pendiente de Información",
            "Resuelto",
            "Reabierto",
            "Anulado"
        }));
        cmbEstadoTicket.setBackground(Color.decode("#F8FAFC"));
        cmbEstadoTicket.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        cmbEstadoTicket.setBounds(26, 222, 212, 36);
        panelTarjeta.add(cmbEstadoTicket);

        // Fila 2 Derecha: Formato de Exportación (Excel o PDF)
        JLabel lblFormato = new JLabel("Formato de Exportación");
        lblFormato.setForeground(Color.decode("#2B3990"));
        lblFormato.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblFormato.setBounds(262, 202, 200, 16);
        panelTarjeta.add(lblFormato);

        cmbFormato = new JComboBox<String>();
        cmbFormato.setModel(new DefaultComboBoxModel<String>(new String[] {
            "📊 Hoja de Cálculo (.xlsx)",
            "📄 Documento PDF (.pdf)"
        }));
        cmbFormato.setBackground(Color.decode("#F8FAFC"));
        cmbFormato.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        cmbFormato.setBounds(262, 222, 212, 36);
        panelTarjeta.add(cmbFormato);

        // Casilla de verificación: Incluir descripción y comentarios
        chkIncluirDetalle = new JCheckBox("Incluir descripción del fallo y comentarios de solución en el reporte.");
        chkIncluirDetalle.setBackground(Color.WHITE);
        chkIncluirDetalle.setForeground(new Color(71, 85, 105));
        chkIncluirDetalle.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        chkIncluirDetalle.setFocusPainted(false);
        chkIncluirDetalle.setCursor(new Cursor(Cursor.HAND_CURSOR));
        chkIncluirDetalle.setBounds(23, 276, 450, 24);
        panelTarjeta.add(chkIncluirDetalle);

        // =========================================================================
        // 5. PIE DE TARJETA CON BOTONES "Cancelar" Y "⬇ Generar Archivo"
        // =========================================================================
        JPanel panelPie = new JPanel();
        panelPie.setBackground(Color.decode("#F8FAFC"));
        panelPie.setBounds(1, 330, 498, 79);
        panelPie.setBorder(new MatteBorder(1, 0, 0, 0, new Color(235, 240, 245)));
        panelPie.setLayout(null);
        panelTarjeta.add(panelPie);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBackground(Color.decode("#F8FAFC"));
        btnCancelar.setForeground(new Color(71, 85, 105));
        btnCancelar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnCancelar.setFocusPainted(false);
        btnCancelar.setBorderPainted(false);
        btnCancelar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCancelar.setBounds(215, 20, 90, 38);
        panelPie.add(btnCancelar);

        JButton btnGenerar = new JButton("⬇  Generar Archivo");
        btnGenerar.setBackground(Color.decode("#0095D9"));
        btnGenerar.setForeground(Color.WHITE);
        btnGenerar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnGenerar.setFocusPainted(false);
        btnGenerar.setBorderPainted(false);
        btnGenerar.setOpaque(true);
        btnGenerar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnGenerar.setBounds(315, 20, 158, 38);
        panelPie.add(btnGenerar);

        // =========================================================================
        // 6. EVENTOS DE EXPORTACIÓN Y CIERRE
        // =========================================================================
        lblCerrarX.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (marcoPadre != null) {
                    marcoPadre.volverPantallaAnterior();
                }
            }
        });

        btnCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (marcoPadre != null) {
                    marcoPadre.volverPantallaAnterior();
                }
            }
        });

        btnGenerar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String rango = cmbRangoFechas.getSelectedItem().toString();
                String area = cmbAreaClinica.getSelectedItem().toString();
                String estado = cmbEstadoTicket.getSelectedItem().toString();
                String formatoSeleccionado = cmbFormato.getSelectedItem().toString();
                boolean incluirComentarios = chkIncluirDetalle.isSelected();

                String formatoTexto = formatoSeleccionado.contains(".pdf") ? "PDF (.pdf)" : "Excel (.xlsx)";

                if (marcoPadre != null) {
                    marcoPadre.mostrarInformeExportado(rango, area, estado, formatoTexto, incluirComentarios);
                }
            }
        });
    }
}