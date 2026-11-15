package com.netcom.vista;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class PanelCreacionUsuario extends JPanel {

    private static final long serialVersionUID = 1L;

    private BarraSuperior barraSuperior;
    private JLabel lblTitulo;
    private JButton btnCrearUsuario;

    private JTextField txtNombresApellidos;
    private JTextField txtNombreUsuario;
    private JComboBox<String> cmbArea;
    private JComboBox<String> cmbRol;
    private JTextField txtContrasenaTemporal;

    // Si es -1 estamos creando un usuario nuevo; si es >= 0 estamos editando esa fila de la Pantalla 17
    private int filaUsuarioEditando = -1;

    private ventanaPrincipal marcoPadre;

    public PanelCreacionUsuario(ventanaPrincipal marcoPadre) {
        this.marcoPadre = marcoPadre;

        setBackground(Color.decode("#F4F9FB"));
        setSize(1100, 680);
        setLayout(null);

        barraSuperior = new BarraSuperior(marcoPadre);
        add(barraSuperior);

        JPanel panelTarjeta = new JPanel();
        panelTarjeta.setBackground(Color.WHITE);
        panelTarjeta.setBounds(230, 125, 640, 430);
        panelTarjeta.setBorder(new LineBorder(new Color(224, 224, 224), 1, true));
        panelTarjeta.setLayout(null);
        add(panelTarjeta);

        lblTitulo = new JLabel("Gestión de Accesos");
        lblTitulo.setForeground(new Color(33, 33, 33));
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitulo.setBounds(40, 32, 350, 28);
        panelTarjeta.add(lblTitulo);

        JLabel lblSubtitulo = new JLabel("Creación y edición de usuario (Módulo restringido para Administración TI)");
        lblSubtitulo.setForeground(new Color(130, 130, 130));
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSubtitulo.setBounds(40, 62, 420, 20);
        panelTarjeta.add(lblSubtitulo);

        JLabel lblBadgeAdmin = new JLabel("Solo Administradores", SwingConstants.CENTER);
        lblBadgeAdmin.setOpaque(true);
        lblBadgeAdmin.setBackground(Color.decode("#004A8F"));
        lblBadgeAdmin.setForeground(Color.WHITE);
        lblBadgeAdmin.setFont(new Font("Segoe UI", Font.BOLD, 10));
        lblBadgeAdmin.setBounds(475, 60, 125, 22);
        panelTarjeta.add(lblBadgeAdmin);

        JLabel lblNombres = new JLabel("Nombres y Apellidos");
        lblNombres.setForeground(new Color(66, 66, 66));
        lblNombres.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblNombres.setBounds(40, 110, 260, 18);
        panelTarjeta.add(lblNombres);

        txtNombresApellidos = new JTextField();
        txtNombresApellidos.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtNombresApellidos.setBounds(40, 132, 265, 38);
        txtNombresApellidos.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(210, 215, 220), 1, true),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        panelTarjeta.add(txtNombresApellidos);

        JLabel lblUsuarioRed = new JLabel("Nombre de Usuario (Red)");
        lblUsuarioRed.setForeground(new Color(66, 66, 66));
        lblUsuarioRed.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblUsuarioRed.setBounds(335, 110, 260, 18);
        panelTarjeta.add(lblUsuarioRed);

        txtNombreUsuario = new JTextField();
        txtNombreUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtNombreUsuario.setBounds(335, 132, 265, 38);
        txtNombreUsuario.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(210, 215, 220), 1, true),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        panelTarjeta.add(txtNombreUsuario);

        JLabel lblArea = new JLabel("Área / Departamento");
        lblArea.setForeground(new Color(66, 66, 66));
        lblArea.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblArea.setBounds(40, 190, 260, 18);
        panelTarjeta.add(lblArea);

        cmbArea = new JComboBox<String>();
        cmbArea.setModel(new DefaultComboBoxModel<String>(new String[] {
            "Urgencias", "Consulta Externa", "Laboratorio", "Farmacia", "Facturación", "Administración", "Soporte TI"
        }));
        cmbArea.setBackground(Color.WHITE);
        cmbArea.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        cmbArea.setBounds(40, 212, 265, 38);
        panelTarjeta.add(cmbArea);

        JLabel lblRol = new JLabel("Rol de Sistema");
        lblRol.setForeground(new Color(66, 66, 66));
        lblRol.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblRol.setBounds(335, 190, 260, 18);
        panelTarjeta.add(lblRol);

        cmbRol = new JComboBox<String>();
        cmbRol.setModel(new DefaultComboBoxModel<String>(new String[] {
            "Usuario Estándar", "Ingeniero de Soporte", "Administrador de TI"
        }));
        cmbRol.setBackground(Color.WHITE);
        cmbRol.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        cmbRol.setBounds(335, 212, 265, 38);
        panelTarjeta.add(cmbRol);

        JLabel lblClaveTemp = new JLabel("Contraseña Temporal");
        lblClaveTemp.setForeground(new Color(66, 66, 66));
        lblClaveTemp.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblClaveTemp.setBounds(40, 270, 300, 18);
        panelTarjeta.add(lblClaveTemp);

        txtContrasenaTemporal = new JTextField();
        txtContrasenaTemporal.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtContrasenaTemporal.setBounds(40, 292, 560, 38);
        txtContrasenaTemporal.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(210, 215, 220), 1, true),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        panelTarjeta.add(txtContrasenaTemporal);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBackground(Color.WHITE);
        btnCancelar.setForeground(new Color(66, 66, 66));
        btnCancelar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnCancelar.setBorder(new LineBorder(new Color(210, 215, 220), 1, true));
        btnCancelar.setFocusPainted(false);
        btnCancelar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCancelar.setBounds(355, 360, 95, 38);
        panelTarjeta.add(btnCancelar);

        btnCrearUsuario = new JButton("Crear Usuario");
        btnCrearUsuario.setBackground(Color.decode("#004A8F"));
        btnCrearUsuario.setForeground(Color.WHITE);
        btnCrearUsuario.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnCrearUsuario.setFocusPainted(false);
        btnCrearUsuario.setBorderPainted(false);
        btnCrearUsuario.setOpaque(true);
        btnCrearUsuario.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCrearUsuario.setBounds(460, 360, 140, 38);
        panelTarjeta.add(btnCrearUsuario);

        btnCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                limpiarFormulario();
                if (marcoPadre != null) {
                    marcoPadre.volverPantallaAnterior();
                }
            }
        });

        btnCrearUsuario.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                registrarOActualizarUsuario();
            }
        });
    }

    /**
     * Prepara el formulario en limpio cuando pulsan "+ Crear Nuevo Usuario"
     */
    public void prepararModoCreacion() {
        this.filaUsuarioEditando = -1;
        lblTitulo.setText("Gestión de Accesos");
        btnCrearUsuario.setText("Crear Usuario");
        limpiarFormulario();
    }

    /**
     * Precarga los datos del usuario seleccionado cuando pulsan "✏ Editar" en la Pantalla 17 (RF-15)
     */
    public void cargarDatosParaEditar(int filaTabla, String nombre, String usuarioRed, String area, String rol) {
        this.filaUsuarioEditando = filaTabla;
        lblTitulo.setText("Editando Usuario: " + nombre);
        btnCrearUsuario.setText("Guardar Cambios");

        txtNombresApellidos.setText(nombre);
        txtNombreUsuario.setText(usuarioRed);
        cmbArea.setSelectedItem(area);
        cmbRol.setSelectedItem(rol);
        txtContrasenaTemporal.setText("ClaveActual123*");
    }

    private void registrarOActualizarUsuario() {
        String nombres = txtNombresApellidos.getText().trim();
        String usuarioRed = txtNombreUsuario.getText().trim();
        String area = cmbArea.getSelectedItem().toString();
        String rol = cmbRol.getSelectedItem().toString();
        String claveTemp = txtContrasenaTemporal.getText().trim();

        if (nombres.isEmpty() || usuarioRed.isEmpty() || claveTemp.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Todos los campos son obligatorios.",
                    "Campos Incompletos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (claveTemp.length() < 8) {
            JOptionPane.showMessageDialog(this,
                    "La contraseña debe tener al menos 8 caracteres.",
                    "Contraseña Débil", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int filaAfectada = this.filaUsuarioEditando;
        limpiarFormulario();

        if (marcoPadre != null) {
            marcoPadre.procesarGuardadoUsuario(filaAfectada, nombres, usuarioRed, area, rol);
        }
    }

    private void limpiarFormulario() {
        txtNombresApellidos.setText("");
        txtNombreUsuario.setText("");
        txtContrasenaTemporal.setText("");
        cmbArea.setSelectedIndex(0);
        cmbRol.setSelectedIndex(0);
    }

    public BarraSuperior getBarraSuperior() {
        return barraSuperior;
    }
}