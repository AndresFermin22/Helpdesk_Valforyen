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

public class PanelAdministracionUsuarios extends JPanel {

    private static final long serialVersionUID = 1L;

    private BarraSuperior barraSuperior;
    private ventanaPrincipal marcoPadre;

    private JTextField txtBuscarUsuario;
    private JTable tablaUsuarios;
    private DefaultTableModel modeloUsuarios;
    private TableRowSorter<DefaultTableModel> filtroUsuarios;

    public PanelAdministracionUsuarios(ventanaPrincipal marcoPadre) {
        this.marcoPadre = marcoPadre;

        setBackground(Color.decode("#F4F9FB"));
        setSize(1100, 680);
        setLayout(null);

        // =========================================================================
        // 1. BARRA SUPERIOR ("Centro de Mando TI")
        // =========================================================================
        barraSuperior = new BarraSuperior(marcoPadre);
        barraSuperior.cambiarTitulo("Centro de Mando TI", "Admin. TI");
        add(barraSuperior);

        // =========================================================================
        // 2. SUB-MENÚ DE 4 PESTAÑAS (Administración de Usuarios ACTIVA)
        // =========================================================================
        JPanel panelSubMenu = new JPanel();
        panelSubMenu.setBackground(Color.WHITE);
        panelSubMenu.setBounds(0, 60, 1100, 44);
        panelSubMenu.setBorder(new MatteBorder(0, 0, 1, 0, new Color(225, 230, 235)));
        panelSubMenu.setLayout(null);
        add(panelSubMenu);

        // Pestaña 1: Bandeja Global de Tickets (Regresa a Pantalla 11)
        JLabel lblTabBandejaGlobal = new JLabel("Bandeja Global de Tickets", SwingConstants.CENTER);
        lblTabBandejaGlobal.setForeground(new Color(110, 120, 130));
        lblTabBandejaGlobal.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblTabBandejaGlobal.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblTabBandejaGlobal.setBounds(25, 0, 160, 44);
        panelSubMenu.add(lblTabBandejaGlobal);

        lblTabBandejaGlobal.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (marcoPadre != null) {
                    marcoPadre.mostrarPantalla("PANTALLA_BANDEJA_GLOBAL");
                }
            }
        });

        // Pestaña 2: Extractor de Inventario
        JLabel lblTabInventario = new JLabel("Extractor de Inventario", SwingConstants.CENTER);
        lblTabInventario.setForeground(new Color(110, 120, 130));
        lblTabInventario.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblTabInventario.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblTabInventario.setBounds(200, 0, 145, 44);
        panelSubMenu.add(lblTabInventario);

        // Pestaña 3: Dashboard KPIs
        JLabel lblTabDashboard = new JLabel("Dashboard KPIs", SwingConstants.CENTER);
        lblTabDashboard.setForeground(new Color(110, 120, 130));
        lblTabDashboard.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblTabDashboard.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblTabDashboard.setBounds(360, 0, 110, 44);
        panelSubMenu.add(lblTabDashboard);

        // Pestaña 4 (ACTIVA): Administración de Usuarios
        JLabel lblTabUsuarios = new JLabel("Administración de Usuarios", SwingConstants.CENTER);
        lblTabUsuarios.setForeground(Color.decode("#0095D9"));
        lblTabUsuarios.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblTabUsuarios.setBorder(new MatteBorder(0, 0, 3, 0, Color.decode("#0095D9")));
        lblTabUsuarios.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblTabUsuarios.setBounds(485, 0, 170, 44);
        panelSubMenu.add(lblTabUsuarios);

        // =========================================================================
        // 3. BARRA DE ACCIONES: "+ Crear Nuevo Usuario" y Buscador por Cédula/Nombre
        // =========================================================================
        JButton btnNuevoUsuario = new JButton("+ Crear Nuevo Usuario");
        btnNuevoUsuario.setBackground(Color.decode("#0095D9"));
        btnNuevoUsuario.setForeground(Color.WHITE);
        btnNuevoUsuario.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnNuevoUsuario.setFocusPainted(false);
        btnNuevoUsuario.setBorderPainted(false);
        btnNuevoUsuario.setOpaque(true);
        btnNuevoUsuario.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnNuevoUsuario.setBounds(25, 125, 165, 36);
        add(btnNuevoUsuario);

        txtBuscarUsuario = new JTextField();
        txtBuscarUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtBuscarUsuario.setToolTipText("Buscar por cédula, nombre o usuario de red...");
        txtBuscarUsuario.setBounds(815, 125, 245, 36);
        txtBuscarUsuario.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(210, 215, 220), 1, true),
                BorderFactory.createEmptyBorder(4, 10, 4, 10)));
        add(txtBuscarUsuario);

        // =========================================================================
        // 4. TABLA DE USUARIOS CON ENCABEZADO AZUL ÍNDIGO (#2B3990) (Imagen 2)
        // =========================================================================
        String[] columnas = {
            "Cédula", "Nombre Completo", "Usuario Red", "Cargo / Área", "Rol", "Estado", "Acciones"
        };

        modeloUsuarios = new DefaultTableModel(null, columnas) {
            private static final long serialVersionUID = 1L;
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Filas iniciales exactas de tu Pantalla 17
        modeloUsuarios.addRow(new Object[] { "1.098.765.432", "Fermin López", "f.lopez", "Médico / Urgencias", "Usuario Estándar", "Activo", "✏ Editar  |  🗑 Eliminar" });
        modeloUsuarios.addRow(new Object[] { "1.098.234.890", "Karen Rueda", "k.rueda", "Médico / Consulta Externa", "Usuario Estándar", "Activo", "✏ Editar  |  🗑 Eliminar" });
        modeloUsuarios.addRow(new Object[] { "1.095.912.311", "Andres Ortega", "a.ortega", "Técnico / Soporte TI", "Ingeniero de Soporte", "Inactivo", "✏ Editar  |  🗑 Eliminar" });

        tablaUsuarios = new JTable(modeloUsuarios);
        tablaUsuarios.setRowHeight(44);
        tablaUsuarios.setShowVerticalLines(false);
        tablaUsuarios.setGridColor(new Color(240, 243, 246));
        tablaUsuarios.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tablaUsuarios.setSelectionBackground(new Color(238, 245, 252));
        tablaUsuarios.setSelectionForeground(Color.BLACK);
        tablaUsuarios.setCursor(new Cursor(Cursor.HAND_CURSOR));
     // Ajustamos el ancho de las 7 columnas para que "Editar | Eliminar" quepa en una sola línea
        tablaUsuarios.getColumnModel().getColumn(0).setPreferredWidth(115); // Cédula
        tablaUsuarios.getColumnModel().getColumn(1).setPreferredWidth(175); // Nombre Completo
        tablaUsuarios.getColumnModel().getColumn(2).setPreferredWidth(110); // Usuario Red
        tablaUsuarios.getColumnModel().getColumn(3).setPreferredWidth(190); // Cargo / Área
        tablaUsuarios.getColumnModel().getColumn(4).setPreferredWidth(150); // Rol
        tablaUsuarios.getColumnModel().getColumn(5).setPreferredWidth(90);  // Estado
        tablaUsuarios.getColumnModel().getColumn(6).setPreferredWidth(195); // Acciones (Ancho amplio)

        // Encabezado en Azul Índigo (#2B3990) idéntico a la Pantalla 17
        JTableHeader header = tablaUsuarios.getTableHeader();
        header.setPreferredSize(new Dimension(100, 40));
        header.setReorderingAllowed(false);
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            private static final long serialVersionUID = 1L;
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel lbl = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                lbl.setBackground(Color.decode("#2B3990"));
                lbl.setForeground(Color.WHITE);
                lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
                lbl.setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 5));
                return lbl;
            }
        });

        RenderizadorTablaUsuarios renderUsuarios = new RenderizadorTablaUsuarios();
        for (int i = 0; i < tablaUsuarios.getColumnCount(); i++) {
            tablaUsuarios.getColumnModel().getColumn(i).setCellRenderer(renderUsuarios);
        }

        filtroUsuarios = new TableRowSorter<>(modeloUsuarios);
        tablaUsuarios.setRowSorter(filtroUsuarios);

        JScrollPane scrollUsuarios = new JScrollPane(tablaUsuarios);
        scrollUsuarios.setBounds(25, 180, 1035, 380);
        scrollUsuarios.getViewport().setBackground(Color.WHITE);
        scrollUsuarios.setBorder(new LineBorder(new Color(225, 230, 235), 1, true));
        add(scrollUsuarios);

        // =========================================================================
        // 5. EVENTOS: CREAR, BUSCAR, EDITAR Y ELIMINAR (INHABILITAR)
        // =========================================================================

        // Botón "+ Crear Nuevo Usuario": Abre Pantalla 2 en modo creación limpio
        btnNuevoUsuario.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (marcoPadre != null) {
                    marcoPadre.abrirFormularioNuevoUsuario();
                }
            }
        });

        // Buscador en tiempo real por cédula o nombre (RF-12)
        txtBuscarUsuario.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String texto = txtBuscarUsuario.getText().trim();
                if (texto.isEmpty()) {
                    filtroUsuarios.setRowFilter(null);
                } else {
                    filtroUsuarios.setRowFilter(RowFilter.regexFilter("(?i)" + texto));
                }
            }
        });

        // Clic sobre la columna "Acciones" (✏ Editar | 🗑 Eliminar)
        tablaUsuarios.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int filaVista = tablaUsuarios.rowAtPoint(e.getPoint());
                int columnaVista = tablaUsuarios.columnAtPoint(e.getPoint());

                if (filaVista != -1 && columnaVista == 6) {
                    int filaModelo = tablaUsuarios.convertRowIndexToModel(filaVista);

                    // Calculamos si hizo clic en la mitad izquierda ("Editar") o derecha ("Eliminar")
                    int xDentroDeCelda = e.getPoint().x - tablaUsuarios.getCellRect(filaVista, columnaVista, true).x;
                    int anchoCelda = tablaUsuarios.getColumnModel().getColumn(columnaVista).getWidth();

                    String nombre = modeloUsuarios.getValueAt(filaModelo, 1).toString();
                    String usuarioRed = modeloUsuarios.getValueAt(filaModelo, 2).toString();
                    String cargoArea = modeloUsuarios.getValueAt(filaModelo, 3).toString();
                    String rol = modeloUsuarios.getValueAt(filaModelo, 4).toString();
                    String estadoActual = modeloUsuarios.getValueAt(filaModelo, 5).toString();

                    if (xDentroDeCelda < (anchoCelda / 2)) {
                        // Hizo clic en "✏ Editar" -> Llevamos los datos a la Pantalla 2
                        String areaExtraida = cargoArea.contains("/") ? cargoArea.split("/")[1].trim() : "Urgencias";
                        if (marcoPadre != null) {
                            marcoPadre.abrirFormularioEdicionUsuario(filaModelo, nombre, usuarioRed, areaExtraida, rol);
                        }
                    } else {
                        // Hizo clic en "🗑 Eliminar" -> Confirmación en Java (RF-17)
                        if (estadoActual.equalsIgnoreCase("Activo")) {
                            int confirmar = JOptionPane.showConfirmDialog(PanelAdministracionUsuarios.this,
                                    "¿Está seguro de que desea inhabilitar/eliminar el acceso del usuario:\n" +
                                    nombre + " (" + usuarioRed + ")?\n\n" +
                                    "Su estado pasará a 'Inactivo' conservando su historial de tickets (RF-17).",
                                    "Confirmar Baja de Usuario", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

                            if (confirmar == JOptionPane.YES_OPTION) {
                                modeloUsuarios.setValueAt("Inactivo", filaModelo, 5);
                                JOptionPane.showMessageDialog(PanelAdministracionUsuarios.this,
                                        "¡El usuario " + nombre + " ha sido pasado a estado Inactivo!",
                                        "Usuario Inhabilitado", JOptionPane.INFORMATION_MESSAGE);
                            }
                        } else {
                            int reactivar = JOptionPane.showConfirmDialog(PanelAdministracionUsuarios.this,
                                    "El usuario " + nombre + " ya se encuentra Inactivo.\n¿Desea reactivar su cuenta a estado 'Activo'?",
                                    "Reactivar Usuario", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                            if (reactivar == JOptionPane.YES_OPTION) {
                                modeloUsuarios.setValueAt("Activo", filaModelo, 5);
                            }
                        }
                    }
                }
            }
        });
    }

    /**
     * Agrega un nuevo usuario creado en la Pantalla 2 o actualiza sus datos si venía de "Editar"
     */
    public void guardarOActualizarUsuarioEnTabla(int filaEditar, String nombre, String usuarioRed, String area, String rol) {
        if (filaEditar == -1) {
            // Es un usuario nuevo
            modeloUsuarios.addRow(new Object[] {
                "1.098.555.123", nombre, usuarioRed, "Colaborador / " + area, rol, "Activo", "✏ Editar  |  🗑 Eliminar"
            });
        } else {
            // Está editando un usuario existente (RF-15)
            modeloUsuarios.setValueAt(nombre, filaEditar, 1);
            modeloUsuarios.setValueAt(usuarioRed, filaEditar, 2);
            modeloUsuarios.setValueAt("Colaborador / " + area, filaEditar, 3);
            modeloUsuarios.setValueAt(rol, filaEditar, 4);
        }
    }

    public BarraSuperior getBarraSuperior() {
        return barraSuperior;
    }

    private class RenderizadorTablaUsuarios extends DefaultTableCellRenderer {
        private static final long serialVersionUID = 1L;

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {

            JLabel celda = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            String texto = (value != null) ? value.toString() : "";
            celda.setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 5));
            celda.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            celda.setForeground(new Color(55, 65, 81));
            celda.setBackground(isSelected ? new Color(238, 245, 252) : Color.WHITE);

            if (column == 5) { // Estado: Activo en Verde, Inactivo en Rojo
                if (texto.equalsIgnoreCase("Activo")) {
                    celda.setForeground(Color.decode("#65A30D"));
                    celda.setFont(new Font("Segoe UI", Font.BOLD, 12));
                } else {
                    celda.setForeground(Color.decode("#DC2626"));
                    celda.setFont(new Font("Segoe UI", Font.BOLD, 12));
                }
            } else if (column == 6) { // Acciones en una sola línea sin cortarse
                celda.setText("<html><div style='white-space:nowrap;'><font color='#0095D9'><b>✏ Editar</b></font> &nbsp;|&nbsp; <font color='#0095D9'><b>🗑 Eliminar</b></font></div></html>");
            }
            return celda;
        }
    }
}