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

public class PanelBandejaTickets extends JPanel {

    private static final long serialVersionUID = 1L;

    private BarraSuperior barraSuperior;
    private ventanaPrincipal marcoPadre;

    // Etiquetas de los 3 contadores dinámicos
    private JLabel lblNumTotales;
    private JLabel lblNumAbiertos;
    private JLabel lblNumPendientes;

    // Buscador y Tabla
    private JTextField txtBuscar;
    private JTable tablaTickets;
    private DefaultTableModel modeloTabla;
    private TableRowSorter<DefaultTableModel> filtroTabla;

    public PanelBandejaTickets(ventanaPrincipal marcoPadre) {
        this.marcoPadre = marcoPadre;

        // 1. Fondo general Hielo (#F4F9FB)
        setBackground(Color.decode("#F4F9FB"));
        setSize(1100, 680);
        setLayout(null);

        // =========================================================================
        // 2. BARRA SUPERIOR ("Centro de Bandejas Tickets")
        // =========================================================================
        barraSuperior = new BarraSuperior(marcoPadre);
        barraSuperior.cambiarTitulo("Centro de Bandejas Tickets", "Admin. TI");
        add(barraSuperior);

        // =========================================================================
        // 3. SUB-BARRA DE NAVEGACIÓN (2 Pestañas: Bandeja de Tickets | Registrar Incidencia)
        // =========================================================================
        JPanel panelSubMenu = new JPanel();
        panelSubMenu.setBackground(Color.WHITE);
        panelSubMenu.setBounds(0, 60, 1100, 44);
        panelSubMenu.setBorder(new MatteBorder(0, 0, 1, 0, new Color(225, 230, 235)));
        panelSubMenu.setLayout(null);
        add(panelSubMenu);

        // Pestaña 1 (ACTIVA): Bandeja de Tickets
        JLabel lblTabBandeja = new JLabel("Bandeja de Tickets", SwingConstants.CENTER);
        lblTabBandeja.setForeground(Color.decode("#0095D9"));
        lblTabBandeja.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblTabBandeja.setBorder(new MatteBorder(0, 0, 3, 0, Color.decode("#0095D9")));
        lblTabBandeja.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblTabBandeja.setBounds(30, 0, 120, 44);
        panelSubMenu.add(lblTabBandeja);

        // Pestaña 2: Registrar Incidencia (Cambia a la Pantalla 7)
        JLabel lblTabRegistrar = new JLabel("Registrar Incidencia", SwingConstants.CENTER);
        lblTabRegistrar.setForeground(new Color(110, 120, 130));
        lblTabRegistrar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblTabRegistrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblTabRegistrar.setBounds(165, 0, 130, 44);
        panelSubMenu.add(lblTabRegistrar);

        lblTabRegistrar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (marcoPadre != null) {
                    marcoPadre.mostrarPantalla("PANTALLA_REGISTRO_TICKET");
                }
            }
        });

        // =========================================================================
        // 4. TARJETAS DE CONTEO (TICKETS TOTALES, ABIERTOS, PENDIENTE)
        // =========================================================================
        // Tarjeta 1: Tickets Totales
        JPanel cardTotales = crearTarjetaContador("TICKETS TOTALES", 30, 122);
        lblNumTotales = (JLabel) cardTotales.getComponent(1);
        add(cardTotales);

        // Tarjeta 2: Abiertos
        JPanel cardAbiertos = crearTarjetaContador("ABIERTOS", 160, 122);
        lblNumAbiertos = (JLabel) cardAbiertos.getComponent(1);
        add(cardAbiertos);

        // Tarjeta 3: Pendiente
        JPanel cardPendientes = crearTarjetaContador("PENDIENTE", 290, 122);
        lblNumPendientes = (JLabel) cardPendientes.getComponent(1);
        add(cardPendientes);

        // =========================================================================
        // 5. BUSCADOR SUPERIOR DERECHO
        // =========================================================================
        txtBuscar = new JTextField();
        txtBuscar.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtBuscar.setToolTipText("Buscar por solicitante, ID, categoría o estado...");
        txtBuscar.setBounds(835, 140, 175, 36);
        txtBuscar.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(215, 220, 225), 1, true),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        add(txtBuscar);

        JButton btnBuscar = new JButton("🔍");
        btnBuscar.setBackground(Color.decode("#0095D9"));
        btnBuscar.setForeground(Color.WHITE);
        btnBuscar.setFont(new Font("Segoe UI Emoji", Font.BOLD, 13));
        btnBuscar.setFocusPainted(false);
        btnBuscar.setBorderPainted(false);
        btnBuscar.setOpaque(true);
        btnBuscar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnBuscar.setBounds(1015, 140, 48, 36);
        add(btnBuscar);

        // =========================================================================
        // 6. TABLA DE TICKETS CON COLORES DE PRIORIDAD Y ESTADO
        // =========================================================================
        String[] columnas = { "ID Ticket", "Fecha", "Solicitante", "Área", "Categoría", "Prioridad", "Estado" };

        modeloTabla = new DefaultTableModel(null, columnas) {
            private static final long serialVersionUID = 1L;
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Evita que editen el texto con doble clic
            }
        };

        // Datos iniciales de ejemplo idénticos a tu diseño de Figma
        modeloTabla.addRow(new Object[] { "TK-015", "25/09/2026", "Karen Rueda", "Urgencias", "Hardware", "ALTA", "ABIERTO" });
        modeloTabla.addRow(new Object[] { "TK-013", "20/09/2026", "Fermin López", "Urgencias", "Hardware", "MEDIA", "RESUELTO" });

        tablaTickets = new JTable(modeloTabla);
        tablaTickets.setRowHeight(42);
        tablaTickets.setShowVerticalLines(false);
        tablaTickets.setGridColor(new Color(240, 243, 246));
        tablaTickets.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tablaTickets.setSelectionBackground(new Color(232, 244, 252));
        tablaTickets.setSelectionForeground(Color.BLACK);

        // Configuración del encabezado azul (#0095D9)
        JTableHeader header = tablaTickets.getTableHeader();
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
                lbl.setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 5));
                return lbl;
            }
        });

        // Aplicamos el renderizador de colores a todas las columnas
        RenderizadorCeldasTickets renderizador = new RenderizadorCeldasTickets();
        for (int i = 0; i < tablaTickets.getColumnCount(); i++) {
            tablaTickets.getColumnModel().getColumn(i).setCellRenderer(renderizador);
        }

        // Activamos el filtro para el buscador y para filtrar por usuario
        filtroTabla = new TableRowSorter<>(modeloTabla);
        tablaTickets.setRowSorter(filtroTabla);

        JScrollPane scrollTabla = new JScrollPane(tablaTickets);
        scrollTabla.setBounds(30, 212, 1033, 335);
        scrollTabla.getViewport().setBackground(Color.WHITE);
        scrollTabla.setBorder(new LineBorder(new Color(225, 230, 235), 1, true));
        add(scrollTabla);

        // =========================================================================
        // 7. BARRA INFERIOR DE ACCIONES (Marcar Resuelto | Ver Detalles)
        // =========================================================================
        JPanel panelInferior = new JPanel();
        panelInferior.setBackground(Color.WHITE);
        panelInferior.setBounds(30, 562, 1033, 58);
        panelInferior.setBorder(new LineBorder(new Color(225, 230, 235), 1, true));
        panelInferior.setLayout(null);
        add(panelInferior);

        // Botón Verde "✔ Marcar Resuelto" (#8BC53F)
   //     JButton btnMarcarResuelto = new JButton("✔  Marcar Resuelto");
     //   btnMarcarResuelto.setBackground(Color.decode("#8BC53F"));
       // btnMarcarResuelto.setForeground(Color.WHITE);
        //btnMarcarResuelto.setFont(new Font("Segoe UI", Font.BOLD, 12));
       // btnMarcarResuelto.setFocusPainted(false);
        //btnMarcarResuelto.setBorderPainted(false);
        //btnMarcarResuelto.setOpaque(true);
        //btnMarcarResuelto.setCursor(new Cursor(Cursor.HAND_CURSOR));
       // btnMarcarResuelto.setBounds(15, 11, 155, 36);
        //panelInferior.add(btnMarcarResuelto);

        // Botón Azul "👁 Ver Detalles" (#0095D9) - Espacio listo para la Pantalla 9
        JButton btnVerDetalles = new JButton("👁  Ver Detalles");
        btnVerDetalles.setBackground(Color.decode("#0095D9"));
        btnVerDetalles.setForeground(Color.WHITE);
        btnVerDetalles.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnVerDetalles.setFocusPainted(false);
        btnVerDetalles.setBorderPainted(false);
        btnVerDetalles.setOpaque(true);
        btnVerDetalles.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnVerDetalles.setBounds(875, 11, 140, 36);
        panelInferior.add(btnVerDetalles);

        // =========================================================================
        // 8. EVENTOS DE BÚSQUEDA Y BOTONES
        // =========================================================================

        // Evento de buscar al pulsar el botón o al escribir
        btnBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                aplicarFiltroBusqueda();
            }
        });

        txtBuscar.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                aplicarFiltroBusqueda();
            }
        });

     // Evento "Marcar Resuelto" con ventana de confirmación en Java
       // btnMarcarResuelto.addActionListener(new ActionListener() {
            //public void actionPerformed(ActionEvent e) {
              //  int filaVista = tablaTickets.getSelectedRow();
              //  if (filaVista == -1) {
              //      JOptionPane.showMessageDialog(PanelBandejaTickets.this,
            //                "Por favor seleccione un ticket de la tabla primero.",
          //                  "Selección Requerida", JOptionPane.WARNING_MESSAGE);
        //            return;
      //          }
    //            int filaModelo = tablaTickets.convertRowIndexToModel(filaVista);
  //              String idTicket = modeloTabla.getValueAt(filaModelo, 0).toString();
//
      //          int confirmar = JOptionPane.showConfirmDialog(PanelBandejaTickets.this,
    //                    "¿Confirma que desea marcar el ticket " + idTicket + " como RESUELTO?",
  //                      "Confirmar Resolución", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
//
  //              if (confirmar == JOptionPane.YES_OPTION) {
//                    modeloTabla.setValueAt("RESUELTO", filaModelo, 6);
//                    recalcularContadores();
//                }
//            }
//        });

        // Evento "Ver Detalles" (Dejamos el puente listo para cuando creemos la Pantalla 9)
     // Evento "Ver Detalles": Abre la Pantalla 9 con los datos del ticket seleccionado
        btnVerDetalles.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int filaVista = tablaTickets.getSelectedRow();
                if (filaVista == -1) {
                    JOptionPane.showMessageDialog(PanelBandejaTickets.this,
                            "Seleccione un ticket de la tabla para ver sus detalles.",
                            "Ver Detalles", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                int filaModelo = tablaTickets.convertRowIndexToModel(filaVista);
                String id = modeloTabla.getValueAt(filaModelo, 0).toString();
                String fecha = modeloTabla.getValueAt(filaModelo, 1).toString();
                String solicitante = modeloTabla.getValueAt(filaModelo, 2).toString();
                String area = modeloTabla.getValueAt(filaModelo, 3).toString();
                String categoria = modeloTabla.getValueAt(filaModelo, 4).toString();
                String prioridad = modeloTabla.getValueAt(filaModelo, 5).toString();
                String estado = modeloTabla.getValueAt(filaModelo, 6).toString();

                if (marcoPadre != null) {
                    marcoPadre.abrirDetallesTicket(id, fecha, solicitante, area, categoria, prioridad, estado, "En revisión TI");
                }
            }
        });

        // Calculamos los contadores al iniciar
        recalcularContadores();
    }

    /**
     * Crea una tarjeta blanca de contador (Totales, Abiertos, Pendiente)
     */
    private JPanel crearTarjetaContador(String titulo, int x, int y) {
        JPanel card = new JPanel();
        card.setBackground(Color.WHITE);
        card.setBounds(x, y, 115, 72);
        card.setBorder(new LineBorder(new Color(225, 230, 235), 1, true));
        card.setLayout(null);

        JLabel lblTit = new JLabel(titulo);
        lblTit.setForeground(new Color(120, 130, 140));
        lblTit.setFont(new Font("Segoe UI", Font.BOLD, 10));
        lblTit.setBounds(14, 12, 95, 14);
        card.add(lblTit);

        JLabel lblNumero = new JLabel("0");
        lblNumero.setForeground(new Color(33, 33, 33));
        lblNumero.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblNumero.setBounds(14, 30, 85, 30);
        card.add(lblNumero);

        return card;
    }

    /**
     * Filtra la tabla en tiempo real por ID, solicitante, categoría o estado (RF-24)
     */
 // Guarda el nombre del médico conectado para mostrar SOLO sus tickets (RF-24 y RNF-32)
    private String solicitanteFiltroActual = "";

    public void filtrarPorSolicitanteActivo(String nombreSolicitante) {
        this.solicitanteFiltroActual = nombreSolicitante;
        txtBuscar.setText("");
        aplicarFiltroBusqueda();
    }

    private void aplicarFiltroBusqueda() {
        String textoBusqueda = txtBuscar.getText().trim();
        java.util.List<RowFilter<Object, Object>> listaFiltros = new java.util.ArrayList<>();

        // 1. Filtra siempre por la columna 2 (Solicitante) para que solo vea los suyos
        if (solicitanteFiltroActual != null && !solicitanteFiltroActual.isEmpty()) {
            listaFiltros.add(RowFilter.regexFilter("(?i)^" + solicitanteFiltroActual + "$", 2));
        }

        // 2. Si además escribió algo en el buscador, combina ambos filtros
        if (!textoBusqueda.isEmpty()) {
            listaFiltros.add(RowFilter.regexFilter("(?i)" + textoBusqueda));
        }

        if (listaFiltros.isEmpty()) {
            filtroTabla.setRowFilter(null);
        } else {
            filtroTabla.setRowFilter(RowFilter.andFilter(listaFiltros));
        }
        recalcularContadores();
    }

    /**
     * Recorre las filas visibles de la tabla y actualiza los 3 contadores automáticamente
     */
    public void recalcularContadores() {
        int totales = tablaTickets.getRowCount();
        int abiertos = 0;
        int pendientes = 0;

        for (int i = 0; i < totales; i++) {
            String estado = tablaTickets.getValueAt(i, 6).toString().toUpperCase();
            if (estado.equals("ABIERTO") || estado.equals("REABIERTO")) {
                abiertos++;
            } else if (estado.equals("PENDIENTE") || estado.equals("EN PROCESO") || estado.equals("RESUELTO")) {
                pendientes++;
            }
        }

        lblNumTotales.setText(String.valueOf(totales));
        lblNumAbiertos.setText(String.valueOf(abiertos));
        lblNumPendientes.setText(String.valueOf(pendientes));
    }

    /**
     * Permite agregar un nuevo ticket desde la Pantalla 7 y devuelve el código generado
     */
    public String agregarNuevoTicketATabla(String fecha, String solicitante, String area, String categoria, String prioridad) {
        int siguienteNumero = modeloTabla.getRowCount() + 15; // Sigue la secuencia TK-016...
        String nuevoId = "TK-0" + siguienteNumero;

        // Lo insertamos en la primera fila de la tabla
        modeloTabla.insertRow(0, new Object[] { nuevoId, fecha, solicitante, area, categoria, prioridad, "ABIERTO" });
        recalcularContadores();
        return nuevoId;
    }
    
    public void actualizarEstadoTicketEnBandeja(String idTicket, String nuevoEstado) {
        for (int i = 0; i < modeloTabla.getRowCount(); i++) {
            if (modeloTabla.getValueAt(i, 0).toString().equalsIgnoreCase(idTicket)) {
                modeloTabla.setValueAt(nuevoEstado, i, 6);
                break;
            }
        }
        recalcularContadores();
    }
    
    /**
     * Cuenta en tiempo real cuántos tickets tiene el usuario en cada estado para la Pantalla 10 (Perfil)
     */
    public int[] obtenerResumenPorSolicitante(String nombreSolicitante) {
        int abiertos = 0;
        int enProceso = 0;
        int cerrados = 0;

        for (int i = 0; i < modeloTabla.getRowCount(); i++) {
            String solicitanteFila = modeloTabla.getValueAt(i, 2).toString();
            String estadoFila = modeloTabla.getValueAt(i, 6).toString().toUpperCase();

            // Si es Admin cuenta todos, o si coincide con el nombre del médico conectado
            if (nombreSolicitante.contains("Admin") || nombreSolicitante.contains("Ing.")
                    || solicitanteFila.equalsIgnoreCase(nombreSolicitante)) {
                if (estadoFila.equals("ABIERTO") || estadoFila.equals("REABIERTO")) {
                    abiertos++;
                } else if (estadoFila.equals("EN PROCESO") || estadoFila.equals("PENDIENTE")) {
                    enProceso++;
                } else if (estadoFila.equals("RESUELTO") || estadoFila.equals("CERRADO")) {
                    cerrados++;
                }
            }
        }
        return new int[] { abiertos, enProceso, cerrados };
    }

    public BarraSuperior getBarraSuperior() {
        return barraSuperior;
    }

    // =========================================================================
    // CLASE INTERNA PARA PINTAR LOS COLORES DE PRIORIDAD Y ESTADO EN LA TABLA
    // =========================================================================
    private class RenderizadorCeldasTickets extends DefaultTableCellRenderer {
        private static final long serialVersionUID = 1L;

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {

            JLabel celda = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            String texto = (value != null) ? value.toString() : "";
            celda.setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 5));
            celda.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            celda.setForeground(new Color(50, 55, 60));
            celda.setBackground(isSelected ? new Color(232, 244, 252) : Color.WHITE);

            // Columna 0: ID Ticket en azul oscuro y negrita
            if (column == 0) {
                celda.setForeground(Color.decode("#004A8F"));
                celda.setFont(new Font("Segoe UI", Font.BOLD, 12));
            }
            // Columna 2: Solicitante en negrita
            else if (column == 2) {
                celda.setForeground(new Color(25, 25, 25));
                celda.setFont(new Font("Segoe UI", Font.BOLD, 12));
            }
            // Columna 5: PRIORIDAD (ALTA=Rojo, MEDIA=Naranja, BAJA=Verde, CRÍTICA=Rojo oscuro)
            else if (column == 5) {
                String bgHex = "#F1F5F9";
                String fgHex = "#475569";
                if (texto.equalsIgnoreCase("ALTA") || texto.equalsIgnoreCase("CRÍTICA")) {
                    bgHex = "#FEE2E2"; // Rojo suave
                    fgHex = "#DC2626"; // Texto rojo fuerte
                } else if (texto.equalsIgnoreCase("MEDIA")) {
                    bgHex = "#FFEDD5"; // Naranja suave
                    fgHex = "#D97706"; // Texto naranja
                } else if (texto.equalsIgnoreCase("BAJA")) {
                    bgHex = "#E0F2FE"; // Azul/Verde suave
                    fgHex = "#0284C7";
                }
                celda.setText("<html><span style='background-color:" + bgHex + "; color:" + fgHex
                        + "; font-weight:bold; padding:3px 8px;'>" + texto.toUpperCase() + "</span></html>");
            }
            // Columna 6: ESTADO (ABIERTO=Azul, RESUELTO=Verde, PENDIENTE=Ámbar, CERRADO=Gris)
            else if (column == 6) {
                String bgHex = "#F1F5F9";
                String fgHex = "#475569";
                if (texto.equalsIgnoreCase("ABIERTO") || texto.equalsIgnoreCase("REABIERTO")) {
                    bgHex = "#DBEAFE"; // Azul suave
                    fgHex = "#2563EB"; // Texto azul
                } else if (texto.equalsIgnoreCase("RESUELTO")) {
                    bgHex = "#DCFCE7"; // Verde suave
                    fgHex = "#16A34A"; // Texto verde
                } else if (texto.equalsIgnoreCase("PENDIENTE") || texto.equalsIgnoreCase("EN PROCESO")) {
                    bgHex = "#FEF3C7"; // Ámbar suave
                    fgHex = "#D97706";
                } else if (texto.equalsIgnoreCase("CERRADO") || texto.equalsIgnoreCase("ANULADO")) {
                    bgHex = "#E2E8F0"; // Gris suave
                    fgHex = "#475569";
                }
                celda.setText("<html><span style='background-color:" + bgHex + "; color:" + fgHex
                        + "; font-weight:bold; padding:3px 8px;'>" + texto.toUpperCase() + "</span></html>");
            }

            return celda;
        }
    }
}