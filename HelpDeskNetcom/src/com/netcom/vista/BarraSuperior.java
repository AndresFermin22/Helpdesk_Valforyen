package com.netcom.vista;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import com.netcom.utilidades.GestorImagenes;

public class BarraSuperior extends JPanel {

    private static final long serialVersionUID = 1L;

    // Etiquetas dinámicas que cambiarán según el rol (Puntos 3 y 4)
    private JLabel lblTituloBarra;
    private JLabel lblPerfilUsuario;

    public BarraSuperior(ventanaPrincipal marcoPadre) {
        // 1. Fondo Azul Índigo corporativo (#2B3990) y ancho completo (1100x60)
        setBackground(Color.decode("#2B3990"));
        setBounds(0, 0, 1100, 60);
        setLayout(null);

        // 2. Logo de NETCOM reutilizando nuestro GestorImagenes (Punto 1)
        JLabel lblLogo = new JLabel("");
        lblLogo.setIcon(GestorImagenes.obtenerImagenEscalada("/com/netcom/recursos/logo netcom.png", 115, 42));
        lblLogo.setBounds(25, 9, 115, 42);
        add(lblLogo);

        // 3. Título dinámico ("Centro de Mando TI" o "Portal de Soporte TI")
        lblTituloBarra = new JLabel("Centro de Mando TI");
        lblTituloBarra.setForeground(Color.WHITE);
        lblTituloBarra.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblTituloBarra.setBounds(160, 18, 300, 24);
        add(lblTituloBarra);

        // 4. Icono y Nombre del Perfil activo a la derecha ("Admin. TI")
        lblPerfilUsuario = new JLabel("●  Admin. TI", SwingConstants.RIGHT);
        lblPerfilUsuario.setForeground(Color.WHITE);
        lblPerfilUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblPerfilUsuario.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblPerfilUsuario.setBounds(820, 18, 240, 24);
        lblPerfilUsuario.setToolTipText("Clic para cerrar sesión");
        add(lblPerfilUsuario);

     // Evento al hacer clic en el usuario arriba a la derecha: Abrir Perfil (Pantalla 10) o Cerrar Sesión
        lblPerfilUsuario.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String[] opciones = { "👤 Ir a Centro de Perfil", "📋 Ir a Bandeja Principal", "🚪 Cerrar Sesión" };
                int eleccion = JOptionPane.showOptionDialog(
                        BarraSuperior.this,
                        "Seleccione una acción para la cuenta activa:",
                        "Opciones de Usuario",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        opciones,
                        opciones[0]);

                if (marcoPadre != null) {
                    if (eleccion == 0) {
                        marcoPadre.abrirPerfilUsuarioActivo();
                    } else if (eleccion == 1) {
                        marcoPadre.volverPantallaInicio();
                    } else if (eleccion == 2) {
                        marcoPadre.mostrarPantalla("PANTALLA_LOGIN");
                    }
                }
            }
        });
        }

    /**
     * Este método cambia automáticamente los textos de la barra según quién inicie sesión
     */
    public void actualizarBarraSegunRol(String nombreMostrar, String rol) {
        if (rol.equalsIgnoreCase("Usuario Estándar")) {
            lblTituloBarra.setText("Portal de Solicitudes TI");
        } else {
            lblTituloBarra.setText("Centro de Mando TI");
        }
        lblPerfilUsuario.setText("●  " + nombreMostrar);
    }
    
    /**
     * Permite que cada pantalla asigne su título específico en la barra azul
     */
    public void cambiarTitulo(String nuevoTitulo, String nombreMostrar) {
        lblTituloBarra.setText(nuevoTitulo);
        lblPerfilUsuario.setText("●  " + nombreMostrar);
    }
}