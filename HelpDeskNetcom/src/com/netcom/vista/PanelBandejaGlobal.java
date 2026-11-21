package com.netcom.vista;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;

public class PanelBandejaGlobal extends JPanel {

    private static final long serialVersionUID = 1L;

    private BarraSuperior barraSuperior;
    private ventanaPrincipal marcoPadre;

    // Contadores superiores
    private JLabel lblNumTotales;
    private JLabel lblNumAbiertos;
    private JLabel lblNumCriticos;

    // Buscador y Tabla Global (7 columnas completas)
    private JTextField txtBuscarGlobal;
    private JTable tablaGlobal;
    private DefaultTableModel modeloGlobal;
    private TableRowSorter<DefaultTableModel> filtroGlobal;

    public PanelBandejaGlobal(ventanaPrincipal marcoPadre) {
        this.marcoPadre = marcoPadre;

        setBackground(Color.decode("#F4F9FB"));
        setSize(1100, 680);
        setLayout(null);

        // =========================================================================
        // 1. BARRA SUPERIOR ("Centro de Mando TI")
        // =========================================================================
        barraSuperior = new BarraSuperior(marcoPadre);
        barraSuperior.cambiarTitulo("Centro de Mando TI", "Ing. Andrés F. (Admin TI)");
        add(barraSuperior);

        // =========================================================================
        // 2. SUB-MENÚ DE 4 PESTAÑAS PARA TI (RF-08)
        // =========================================================================
        JPanel panelSubMenu = new JPanel();
        panelSubMenu.setBackground(Color.WHITE);
        panelSubMenu.setBounds(0, 60, 1100, 44);
        panelSubMenu.setBorder(new MatteBorder(0, 0, 1, 0, new Color(225, 230, 235)));
        panelSubMenu.setLayout(null);
        add(panelSubMenu);

        // Pestaña 1 (ACTIVA): Bandeja Global de Tickets
        JLabel lblTabBandejaGlobal = new JLabel("Bandeja Global de Tickets", SwingConstants.CENTER);
        lblTabBandejaGlobal.setForeground(Color.decode("#0095D9"));
        lblTabBandejaGlobal.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblTabBandejaGlobal.setBorder(new MatteBorder(0, 0, 3, 0, Color.decode("#0095D9")));
        lblTabBandejaGlobal.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblTabBandejaGlobal.setBounds(25, 0, 160, 44);
        panelSubMenu.add(lblTabBandejaGlobal);

        // Pestaña 2: Extractor de Inventario (Pendiente próxima pantalla)
        JLabel lblTabInventario = new JLabel("Extractor de Inventario", SwingConstants.CENTER);
        lblTabInventario.setForeground(new Color(110, 120, 130));
        lblTabInventario.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblTabInventario.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblTabInventario.setBounds(200, 0, 145, 44);
        panelSubMenu.add(lblTabInventario);

        lblTabInventario.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (marcoPadre != null) {
                    marcoPadre.mostrarPantalla("PANTALLA_INVENTARIO");
                }
            }
        });

        // Pestaña 3: Dashboard KPIs (Pendiente próxima pantalla)
        JLabel lblTabDashboard = new JLabel("Dashboard KPIs", SwingConstants.CENTER);
        lblTabDashboard.setForeground(new Color(110, 120, 130));
        lblTabDashboard.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblTabDashboard.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblTabDashboard.setBounds(360, 0, 110, 44);
        panelSubMenu.add(lblTabDashboard);

        lblTabDashboard.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (marcoPadre != null) {
                    marcoPadre.abrirDashboardKPIs();
                }
            }
        });

        // Pestaña 4: Administración de Usuarios (Conecta a Pantalla 17)
        JLabel lblTabUsuarios = new JLabel("Administración de Usuarios", SwingConstants.CENTER);
        lblTabUsuarios.setForeground(new Color(110, 120, 130));
        lblTabUsuarios.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblTabUsuarios.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblTabUsuarios.setBounds(485, 0, 170, 44);
        panelSubMenu.add(lblTabUsuarios);

        lblTabUsuarios.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (marcoPadre != null) {
                    marcoPadre.mostrarPantalla("PANTALLA_ADMIN_USUARIOS");
                }
            }
        });

        // =========================================================================
        // 3. TARJETAS DE CONTEO GLOBAL (TICKETS TOTALES, ABIERTOS, URGENTES / CRÍTICOS)
        // =========================================================================
        JPanel cardTotales = crearTarjetaContador("TICKETS TOTALES", 30, 120, Color.decode("#1E293B"));
        lblNumTotales = (JLabel) cardTotales.getComponent(1);
        add(cardTotales);

        JPanel cardAbiertos = crearTarjetaContador("ABIERTOS", 165, 120, Color.decode("#1E293B"));
        lblNumAbiertos = (JLabel) cardAbiertos.getComponent(1);
        add(cardAbiertos);

        JPanel cardCriticos = crearTarjetaContador("URGENTES / CRÍTICOS", 300, 120, Color.decode("#DC2626"));
        lblNumCriticos = (JLabel) cardCriticos.getComponent(1);
        add(cardCriticos);

        // =========================================================================
        // 4. BOTÓN FILTROS AVANZADOS Y BUSCADOR GLOBAL (RF-30)
        // =========================================================================
        JButton btnFiltrosAvanzados = new JButton("⚙ Filtros Avanzados");
        btnFiltrosAvanzados.setBackground(Color.WHITE);
        btnFiltrosAvanzados.setForeground(Color.decode("#1E293B"));
        btnFiltrosAvanzados.setFont(new Font("Segoe UI", Font.BOLD, 11));
        btnFiltrosAvanzados.setBorder(new LineBorder(new Color(210, 215, 220), 1, true));
        btnFiltrosAvanzados.setFocusPainted(false);
        btnFiltrosAvanzados.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnFiltrosAvanzados.setBounds(715, 152, 130, 34);
        add(btnFiltrosAvanzados);

        txtBuscarGlobal = new JTextField();
        txtBuscarGlobal.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtBuscarGlobal.setToolTipText("Buscar por ID, Sede, Área, Categoría o Técnico...");
        txtBuscarGlobal.setBounds(852, 152, 168, 34);
        txtBuscarGlobal.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(210, 215, 220), 1, true),
                BorderFactory.createEmptyBorder(4, 10, 4, 10)));
        add(txtBuscarGlobal);

        JButton btnBuscar = new JButton("🔍");
        btnBuscar.setBackground(Color.decode("#0095D9"));
        btnBuscar.setForeground(Color.WHITE);
        btnBuscar.setFont(new Font("Segoe UI Emoji", Font.BOLD, 12));
        btnBuscar.setFocusPainted(false);
        btnBuscar.setBorderPainted(false);
        btnBuscar.setOpaque(true);
        btnBuscar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnBuscar.setBounds(1023, 152, 40, 34);
        add(btnBuscar);

        // =========================================================================
        // 5. TABLA GLOBAL DE 7 COLUMNAS COMPLETAS (Imagen 3)
        // =========================================================================
        String[] columnas = {
                "ID Ticket", "Fecha y Hora", "Solicitante", "Sede / Área", "Categoría", "Prioridad", "Estado", "Asignado A"
            };

            modeloGlobal = new DefaultTableModel(null, columnas) {
                private static final long serialVersionUID = 1L;
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            // Ahora incluye la columna Solicitante (Nombre | usuario_red)
            modeloGlobal.addRow(new Object[] { "TK-105", "24/10/2026 08:30 AM", "Laura Gómez | l.gomez", "PAS Malpaso | Farmacia", "Hardware (Impresora)", "ALTA", "ABIERTO", "? Sin asignar" });
            modeloGlobal.addRow(new Object[] { "TK-104", "23/10/2026 04:15 PM", "Fermin López | f.lopez", "Clínica Girón | Urgencias", "Software (Historia Clínica)", "MEDIA", "EN PROCESO", "AF Ing. Andrés F." });
            modeloGlobal.addRow(new Object[] { "TK-101", "21/10/2026 11:00 AM", "Diana Pérez | d.perez", "PAS Nuevo Girón | Recepción", "Redes (Sin Internet)", "BAJA", "RESUELTO", "JR Ing. Jhon R." });
            modeloGlobal.addRow(new Object[] { "TK-015", "25/09/2026 09:10 AM", "Karen Rueda | k.rueda", "Clínica Girón | Urgencias", "Hardware (PC, Impresoras)", "ALTA", "ABIERTO", "? Sin asignar" });
            modeloGlobal.addRow(new Object[] { "TK-013", "20/09/2026 02:40 PM", "Fermin López | f.lopez", "Clínica Girón | Urgencias", "Hardware (PC, Impresoras)", "MEDIA", "RESUELTO", "AF Ing. Andrés F." });
       
        tablaGlobal = new JTable(modeloGlobal);
        tablaGlobal.setRowHeight(46);
        tablaGlobal.setShowVerticalLines(false);
        tablaGlobal.setGridColor(new Color(240, 243, 246));
        tablaGlobal.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tablaGlobal.setSelectionBackground(new Color(232, 244, 252));
        tablaGlobal.setSelectionForeground(Color.BLACK);

     // Anchos calibrados para que las 8 columnas se vean completas y ordenadas
        tablaGlobal.getColumnModel().getColumn(0).setPreferredWidth(70);  // ID Ticket
        tablaGlobal.getColumnModel().getColumn(1).setPreferredWidth(130); // Fecha y Hora
        tablaGlobal.getColumnModel().getColumn(2).setPreferredWidth(140); // Solicitante (Nombre + usuario)
        tablaGlobal.getColumnModel().getColumn(3).setPreferredWidth(145); // Sede / Área
        tablaGlobal.getColumnModel().getColumn(4).setPreferredWidth(160); // Categoría
        tablaGlobal.getColumnModel().getColumn(5).setPreferredWidth(80);  // Prioridad
        tablaGlobal.getColumnModel().getColumn(6).setPreferredWidth(95);  // Estado
        tablaGlobal.getColumnModel().getColumn(7).setPreferredWidth(125); // Asignado A
       
        JTableHeader header = tablaGlobal.getTableHeader();
        header.setPreferredSize(new Dimension(100, 38));
        header.setReorderingAllowed(false);
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            private static final long serialVersionUID = 1L;
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel lbl = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                lbl.setBackground(Color.decode("#0095D9"));
                lbl.setForeground(Color.WHITE);
                lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
                lbl.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 5));
                return lbl;
            }
        });

        RenderizadorTablaGlobal render = new RenderizadorTablaGlobal();
        for (int i = 0; i < tablaGlobal.getColumnCount(); i++) {
            tablaGlobal.getColumnModel().getColumn(i).setCellRenderer(render);
        }

        filtroGlobal = new TableRowSorter<>(modeloGlobal);
        tablaGlobal.setRowSorter(filtroGlobal);

        JScrollPane scrollTabla = new JScrollPane(tablaGlobal);
        scrollTabla.setBounds(30, 204, 1033, 345);
        scrollTabla.getViewport().setBackground(Color.WHITE);
        scrollTabla.setBorder(new LineBorder(new Color(225, 230, 235), 1, true));
        add(scrollTabla);

        // =========================================================================
        // 6. BARRA INFERIOR DE OPERACIONES TI (Asignarme | Ver Detalles | Exportar)
        // =========================================================================
        JPanel panelInferior = new JPanel();
        panelInferior.setBackground(Color.WHITE);
        panelInferior.setBounds(30, 562, 1033, 56);
        panelInferior.setBorder(new LineBorder(new Color(225, 230, 235), 1, true));
        panelInferior.setLayout(null);
        add(panelInferior);

        // Botón 1: "👤 Asignarme Ticket" (RF-31)
        JButton btnAsignarme = new JButton("👤  Asignarme Ticket");
        btnAsignarme.setBackground(Color.decode("#0095D9"));
        btnAsignarme.setForeground(Color.WHITE);
        btnAsignarme.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnAsignarme.setFocusPainted(false);
        btnAsignarme.setBorderPainted(false);
        btnAsignarme.setOpaque(true);
        btnAsignarme.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAsignarme.setBounds(15, 10, 155, 36);
        panelInferior.add(btnAsignarme);

        // Botón 2: "👁 Ver Detalles / Gestionar" (RF-34, RF-36)
        JButton btnGestionar = new JButton("👁  Ver Detalles / Gestionar");
        btnGestionar.setBackground(Color.WHITE);
        btnGestionar.setForeground(Color.decode("#0095D9"));
        btnGestionar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnGestionar.setBorder(new LineBorder(Color.decode("#0095D9"), 1, true));
        btnGestionar.setFocusPainted(false);
        btnGestionar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnGestionar.setBounds(182, 10, 185, 36);
        panelInferior.add(btnGestionar);

        // Botón 3: "📄 Exportar Reportes (Excel/PDF)" (RF-52)
        JButton btnExportar = new JButton("📄  Exportar Reportes (Excel/PDF)");
        btnExportar.setBackground(Color.decode("#1E293B"));
        btnExportar.setForeground(Color.WHITE);
        btnExportar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnExportar.setFocusPainted(false);
        btnExportar.setBorderPainted(false);
        btnExportar.setOpaque(true);
        btnExportar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnExportar.setBounds(800, 10, 218, 36);
        panelInferior.add(btnExportar);

        // =========================================================================
        // 7. EVENTOS DE LA BANDEJA GLOBAL
        // =========================================================================
        btnBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                filtrarTablaGlobal();
            }
        });

        txtBuscarGlobal.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                filtrarTablaGlobal();
            }
        });

        // Filtro rápido por columna al pulsar "Filtros Avanzados"
        btnFiltrosAvanzados.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String[] opciones = { "Mostrar Todos", "Solo Prioridad ALTA", "Solo ABIERTO", "Solo EN PROCESO", "Solo Sin Asignar" };
                String seleccion = (String) JOptionPane.showInputDialog(PanelBandejaGlobal.this,
                        "Seleccione un criterio de filtrado rápido:", "Filtros Avanzados (RF-30)",
                        JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);

                if (seleccion != null) {
                    if (seleccion.equals("Mostrar Todos")) filtroGlobal.setRowFilter(null);
                    else if (seleccion.equals("Solo Prioridad ALTA")) filtroGlobal.setRowFilter(RowFilter.regexFilter("(?i)ALTA"));
                    else if (seleccion.equals("Solo ABIERTO")) filtroGlobal.setRowFilter(RowFilter.regexFilter("(?i)ABIERTO"));
                    else if (seleccion.equals("Solo EN PROCESO")) filtroGlobal.setRowFilter(RowFilter.regexFilter("(?i)EN PROCESO"));
                    else if (seleccion.equals("Solo Sin Asignar")) filtroGlobal.setRowFilter(RowFilter.regexFilter("(?i)Sin asignar"));
                    recalcularContadoresGlobales();
                }
            }
        });

        // Evento RF-31: Asignarme Ticket
        btnAsignarme.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int filaVista = tablaGlobal.getSelectedRow();
                if (filaVista == -1) {
                    JOptionPane.showMessageDialog(PanelBandejaGlobal.this,
                            "Seleccione un ticket de la tabla para asignárselo.",
                            "Asignar Ticket", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                int filaModelo = tablaGlobal.convertRowIndexToModel(filaVista);
                modeloGlobal.setValueAt("EN PROCESO", filaModelo, 5);
                modeloGlobal.setValueAt("AF Ing. Andrés F.", filaModelo, 6);
                recalcularContadoresGlobales();
                JOptionPane.showMessageDialog(PanelBandejaGlobal.this,
                        "Ticket asignado exitosamente a Ing. Andrés F. (Estado: EN PROCESO).",
                        "Asignación Exitosa", JOptionPane.INFORMATION_MESSAGE);
            }
        });

     // Botón "👁 Ver Detalles / Gestionar": Abre la Pantalla 9 con todos los datos
        btnGestionar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int filaVista = tablaGlobal.getSelectedRow();
                if (filaVista == -1) {
                    JOptionPane.showMessageDialog(PanelBandejaGlobal.this,
                            "Seleccione un ticket de la tabla para ver sus detalles.",
                            "Gestionar Ticket", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                int filaModelo = tablaGlobal.convertRowIndexToModel(filaVista);
                String id = modeloGlobal.getValueAt(filaModelo, 0).toString();
                String fecha = modeloGlobal.getValueAt(filaModelo, 1).toString();
                String solicitante = modeloGlobal.getValueAt(filaModelo, 2).toString().split("\\|")[0].trim();
                String area = modeloGlobal.getValueAt(filaModelo, 3).toString();
                String categoria = modeloGlobal.getValueAt(filaModelo, 4).toString();
                String prioridad = modeloGlobal.getValueAt(filaModelo, 5).toString();
                String estado = modeloGlobal.getValueAt(filaModelo, 6).toString();
                String asignado = modeloGlobal.getValueAt(filaModelo, 7).toString();

                if (marcoPadre != null) {
                    marcoPadre.abrirDetallesTicket(id, fecha, solicitante, area, categoria, prioridad, estado, asignado);
                }
            }
        });

        btnExportar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (marcoPadre != null) {
                    marcoPadre.mostrarPantalla("PANTALLA_EXPORTAR_TICKETS");
                }
            }
        });

        recalcularContadoresGlobales();
    }

    private JPanel crearTarjetaContador(String titulo, int x, int y, Color colorNumero) {
        JPanel card = new JPanel();
        card.setBackground(Color.WHITE);
        card.setBounds(x, y, 125, 72);
        card.setBorder(new LineBorder(new Color(225, 230, 235), 1, true));
        card.setLayout(null);

        JLabel lblTit = new JLabel(titulo);
        lblTit.setForeground(new Color(120, 130, 140));
        lblTit.setFont(new Font("Segoe UI", Font.BOLD, 9));
        lblTit.setBounds(12, 12, 108, 14);
        card.add(lblTit);

        JLabel lblNumero = new JLabel("0");
        lblNumero.setForeground(colorNumero);
        lblNumero.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblNumero.setBounds(12, 30, 95, 30);
        card.add(lblNumero);

        return card;
    }

    private void filtrarTablaGlobal() {
        String texto = txtBuscarGlobal.getText().trim();
        if (texto.isEmpty()) {
            filtroGlobal.setRowFilter(null);
        } else {
            filtroGlobal.setRowFilter(RowFilter.regexFilter("(?i)" + texto));
        }
        recalcularContadoresGlobales();
    }

    public void recalcularContadoresGlobales() {
        int totales = tablaGlobal.getRowCount();
        int abiertos = 0;
        int criticos = 0;

        for (int i = 0; i < totales; i++) {
            String prioridad = tablaGlobal.getValueAt(i, 5).toString().toUpperCase(); // Columna 5: Prioridad
            String estado = tablaGlobal.getValueAt(i, 6).toString().toUpperCase();    // Columna 6: Estado

            if (estado.equals("ABIERTO") || estado.equals("REABIERTO")) {
                abiertos++;
            }
            if ((prioridad.equals("ALTA") || prioridad.equals("CRÍTICA")) && !estado.equals("RESUELTO")) {
                criticos++;
            }
        }

        lblNumTotales.setText(String.valueOf(totales));
        lblNumAbiertos.setText(String.valueOf(abiertos));
        lblNumCriticos.setText(String.valueOf(criticos));
    }

    public void agregarTicketAGlobal(String idTicket, String fecha, String solicitante, String area, String categoria, String prioridad) {
        String usuarioRedGenerado = solicitante.toLowerCase().split(" ")[0].charAt(0) + "." + solicitante.toLowerCase().split(" ")[1];
        modeloGlobal.insertRow(0, new Object[] {
            idTicket, fecha + " 10:00 AM", solicitante + " | " + usuarioRedGenerado, "Clínica Girón | " + area, categoria, prioridad, "ABIERTO", "? Sin asignar"
        });
        recalcularContadoresGlobales();
    }

    public void actualizarEstadoTicketGlobal(String idTicket, String nuevoEstado) {
        for (int i = 0; i < modeloGlobal.getRowCount(); i++) {
            if (modeloGlobal.getValueAt(i, 0).toString().equalsIgnoreCase(idTicket)) {
                modeloGlobal.setValueAt(nuevoEstado, i, 6);
                break;
            }
        }
        recalcularContadoresGlobales();
    }
    
    /**
     * Devuelve todas las filas actuales de la Bandeja Global para calcular el Ranking Top 5 y la Gráfica
     */
    public java.util.List<Object[]> obtenerTodosLosTicketsGlobales() {
        java.util.List<Object[]> lista = new java.util.ArrayList<>();
        for (int i = 0; i < modeloGlobal.getRowCount(); i++) {
            Object[] fila = new Object[modeloGlobal.getColumnCount()];
            for (int j = 0; j < modeloGlobal.getColumnCount(); j++) {
                fila[j] = modeloGlobal.getValueAt(i, j);
            }
            lista.add(fila);
        }
        return lista;
    }

    
    public BarraSuperior getBarraSuperior() {
        return barraSuperior;
    }

    private class RenderizadorTablaGlobal extends DefaultTableCellRenderer {
        private static final long serialVersionUID = 1L;

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {

            JLabel celda = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            String texto = (value != null) ? value.toString() : "";
            celda.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 5));
            celda.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            celda.setForeground(new Color(55, 65, 81));
            celda.setBackground(isSelected ? new Color(232, 244, 252) : Color.WHITE);

            if (column == 0) { // ID Ticket
                celda.setForeground(Color.decode("#2B3990"));
                celda.setFont(new Font("Segoe UI", Font.BOLD, 12));
            } else if ((column == 2 || column == 3) && texto.contains("|")) { // Solicitante (Col 2) y Sede/Área (Col 3)
                String[] partes = texto.split("\\|");
                celda.setText("<html><b>" + partes[0].trim() + "</b><br><font color='#64748B' size='2'>" + partes[1].trim() + "</font></html>");
            } else if (column == 5) { // Prioridad
                String bg = "#F1F5F9", fg = "#475569";
                if (texto.equalsIgnoreCase("ALTA") || texto.equalsIgnoreCase("CRÍTICA")) {
                    bg = "#FEE2E2"; fg = "#DC2626";
                } else if (texto.equalsIgnoreCase("MEDIA")) {
                    bg = "#FFEDD5"; fg = "#D97706";
                } else if (texto.equalsIgnoreCase("BAJA")) {
                    bg = "#DCFCE7"; fg = "#16A34A";
                }
                celda.setText("<html><span style='background-color:" + bg + "; color:" + fg + "; font-weight:bold; padding:2px 7px;'>" + texto + "</span></html>");
            } else if (column == 6) { // Estado
                String bg = "#F1F5F9", fg = "#475569";
                if (texto.equalsIgnoreCase("ABIERTO")) {
                    bg = "#DBEAFE"; fg = "#2563EB";
                } else if (texto.equalsIgnoreCase("EN PROCESO")) {
                    bg = "#F3E8FF"; fg = "#7E22CE";
                } else if (texto.equalsIgnoreCase("RESUELTO")) {
                    bg = "#DCFCE7"; fg = "#16A34A";
                }
                celda.setText("<html><span style='background-color:" + bg + "; color:" + fg + "; font-weight:bold; padding:2px 7px;'>" + texto + "</span></html>");
            } else if (column == 7) { // Asignado A
                if (texto.contains("Sin asignar")) {
                    celda.setForeground(new Color(120, 130, 140));
                } else {
                    celda.setForeground(Color.decode("#1E293B"));
                    celda.setFont(new Font("Segoe UI", Font.BOLD, 12));
                }
            }
            return celda;
        }
    }
}