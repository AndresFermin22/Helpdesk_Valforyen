package com.netcom.vista;

import java.awt.CardLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ventanaPrincipal extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel panelContenedorMaestro;
    private CardLayout cardLayout;

    // Variables de sesión activa
    private String nombreSesionActiva = "Admin. TI";
    private String areaSesionActiva = "Urgencias";
    private String rolSesionActiva = "Administrador de TI";

    // Variables de Memoria de Navegación
    private String pantallaActual = "PANTALLA_LOGIN";
    private String pantallaAnterior = "PANTALLA_LOGIN";
    private String pantallaInicioRol = "PANTALLA_BANDEJA_GLOBAL";

    // Pantallas del sistema
    private PanelLogin pantallaLogin;
    private PanelCreacionUsuario pantallaCreacionUsuario;
    private PanelUsuarioCreado pantallaUsuarioCreado;
    private PanelRecuperarAcceso pantallaRecuperarAcceso;
    private PanelSolicitudEnviada pantallaSolicitudEnviada;
    private PanelBandejaTickets pantallaBandejaTickets;
    private PanelRegistroTicket pantallaRegistroTicket;
    private PanelTicketCreado pantallaTicketCreado;
    private PanelDetallesTicket pantallaDetallesTicket;
    private PanelBandejaGlobal pantallaBandejaGlobal;
    private PanelAdministracionUsuarios pantallaAdminUsuarios;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ventanaPrincipal frame = new ventanaPrincipal();
                    frame.setLocationRelativeTo(null);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public ventanaPrincipal() {
        setTitle("NETCOM | Portal de Soporte TI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1100, 680);
        setResizable(false);

        cardLayout = new CardLayout();
        panelContenedorMaestro = new JPanel();
        panelContenedorMaestro.setLayout(cardLayout);
        setContentPane(panelContenedorMaestro);

        // 1. Inicializamos todas las pantallas
        pantallaLogin = new PanelLogin(this);
        pantallaCreacionUsuario = new PanelCreacionUsuario(this);
        pantallaUsuarioCreado = new PanelUsuarioCreado(this);
        pantallaRecuperarAcceso = new PanelRecuperarAcceso(this);
        pantallaSolicitudEnviada = new PanelSolicitudEnviada(this);
        pantallaBandejaTickets = new PanelBandejaTickets(this);
        pantallaRegistroTicket = new PanelRegistroTicket(this);
        pantallaTicketCreado = new PanelTicketCreado(this);
        pantallaDetallesTicket = new PanelDetallesTicket(this);
        pantallaBandejaGlobal = new PanelBandejaGlobal(this);
        pantallaAdminUsuarios = new PanelAdministracionUsuarios(this);

        // 2. Las registramos en el CardLayout
        panelContenedorMaestro.add(pantallaLogin, "PANTALLA_LOGIN");
        panelContenedorMaestro.add(pantallaCreacionUsuario, "PANTALLA_CREACION_USUARIO");
        panelContenedorMaestro.add(pantallaUsuarioCreado, "PANTALLA_USUARIO_CREADO");
        panelContenedorMaestro.add(pantallaRecuperarAcceso, "PANTALLA_RECUPERAR_ACCESO");
        panelContenedorMaestro.add(pantallaSolicitudEnviada, "PANTALLA_SOLICITUD_ENVIADA");
        panelContenedorMaestro.add(pantallaBandejaTickets, "PANTALLA_BANDEJA_TICKETS");
        panelContenedorMaestro.add(pantallaRegistroTicket, "PANTALLA_REGISTRO_TICKET");
        panelContenedorMaestro.add(pantallaTicketCreado, "PANTALLA_TICKET_CREADO");
        panelContenedorMaestro.add(pantallaDetallesTicket, "PANTALLA_DETALLES_TICKET");
        panelContenedorMaestro.add(pantallaBandejaGlobal, "PANTALLA_BANDEJA_GLOBAL");
        panelContenedorMaestro.add(pantallaAdminUsuarios, "PANTALLA_ADMIN_USUARIOS");

        cardLayout.show(panelContenedorMaestro, "PANTALLA_LOGIN");
    }

    public void mostrarPantalla(String nuevaPantalla) {
        if (!this.pantallaActual.equals("PANTALLA_USUARIO_CREADO")
                && !this.pantallaActual.equals("PANTALLA_SOLICITUD_ENVIADA")
                && !this.pantallaActual.equals("PANTALLA_TICKET_CREADO")) {
            this.pantallaAnterior = this.pantallaActual;
        }
        this.pantallaActual = nuevaPantalla;
        cardLayout.show(panelContenedorMaestro, nuevaPantalla);
    }

    public void volverPantallaAnterior() {
        mostrarPantalla(this.pantallaAnterior);
    }

    public void volverPantallaInicio() {
        mostrarPantalla(this.pantallaInicioRol);
    }

    public String getNombreSesionActiva() {
        return nombreSesionActiva;
    }

    public void iniciarSesionUsuario(String nombreMostrar, String area, String cargoRol, String pantallaDestino) {
        this.nombreSesionActiva = nombreMostrar;
        this.areaSesionActiva = area;
        this.rolSesionActiva = cargoRol;
        this.pantallaInicioRol = pantallaDestino;

        // Actualizamos los títulos de las barras superiores en cada pantalla
        pantallaCreacionUsuario.getBarraSuperior().actualizarBarraSegunRol(nombreSesionActiva, rolSesionActiva);
        pantallaUsuarioCreado.getBarraSuperior().actualizarBarraSegunRol(nombreSesionActiva, rolSesionActiva);
        pantallaBandejaTickets.getBarraSuperior().cambiarTitulo("Centro de Bandejas Tickets", nombreSesionActiva);
        pantallaRegistroTicket.getBarraSuperior().cambiarTitulo("Centro de Registro Incidencias", nombreSesionActiva);
        pantallaDetallesTicket.getBarraSuperior().cambiarTitulo("Detalles Tickets", nombreSesionActiva);
        pantallaBandejaGlobal.getBarraSuperior().cambiarTitulo("Centro de Mando TI", nombreSesionActiva);
        pantallaAdminUsuarios.getBarraSuperior().cambiarTitulo("Centro de Mando TI", nombreSesionActiva);

        pantallaRegistroTicket.autocompletarSolicitante(nombreSesionActiva, areaSesionActiva, rolSesionActiva);
        pantallaBandejaTickets.filtrarPorSolicitanteActivo(nombreSesionActiva);

        mostrarPantalla(pantallaDestino);
    }

    public void abrirFormularioNuevoUsuario() {
        pantallaCreacionUsuario.prepararModoCreacion();
        mostrarPantalla("PANTALLA_CREACION_USUARIO");
    }

    public void abrirFormularioEdicionUsuario(int filaTabla, String nombre, String usuarioRed, String area, String rol) {
        pantallaCreacionUsuario.cargarDatosParaEditar(filaTabla, nombre, usuarioRed, area, rol);
        mostrarPantalla("PANTALLA_CREACION_USUARIO");
    }

    public void procesarGuardadoUsuario(int filaEditada, String nombre, String usuarioRed, String area, String rol) {
        pantallaAdminUsuarios.guardarOActualizarUsuarioEnTabla(filaEditada, nombre, usuarioRed, area, rol);

        if (filaEditada == -1) {
            pantallaUsuarioCreado.cargarDatosUsuarioCreado(nombre, usuarioRed);
            mostrarPantalla("PANTALLA_USUARIO_CREADO");
        } else {
            JOptionPane.showMessageDialog(this,
                    "Los datos del usuario " + nombre + " fueron actualizados correctamente (RF-15).",
                    "Usuario Actualizado", JOptionPane.INFORMATION_MESSAGE);
            mostrarPantalla("PANTALLA_ADMIN_USUARIOS");
        }
    }

    /**
     * Cuando se crea un ticket en la Pantalla 7, pasa directo a la Pantalla 8 mostrando el código TK generado
     */
    public void registrarTicketYMostrarConfirmacion(String fecha, String solicitante, String area, String categoria, String prioridad) {
        String codigoGenerado = pantallaBandejaTickets.agregarNuevoTicketATabla(fecha, solicitante, area, categoria, prioridad);
        pantallaBandejaGlobal.agregarTicketAGlobal(codigoGenerado, fecha, solicitante, area, categoria, prioridad);

        pantallaTicketCreado.mostrarCodigoTicket(codigoGenerado);
        mostrarPantalla("PANTALLA_TICKET_CREADO");
    }

    /**
     * Abre la Pantalla 9 ("Detalles Tickets") cargando todos los datos de la fila seleccionada
     */
    public void abrirDetallesTicket(String id, String fecha, String solicitante, String area,
            String categoria, String prioridad, String estado, String asignadoA) {
        pantallaDetallesTicket.cargarDatosTicket(id, fecha, solicitante, area, categoria, prioridad, estado, asignadoA);
        mostrarPantalla("PANTALLA_DETALLES_TICKET");
    }

    /**
     * Si se marca como Resuelto dentro de la Pantalla 9, actualiza ambas tablas automáticamente
     */
    public void sincronizarTicketResueltoEnTablas(String idTicket) {
        pantallaBandejaTickets.actualizarEstadoTicketEnBandeja(idTicket, "RESUELTO");
        pantallaBandejaGlobal.actualizarEstadoTicketGlobal(idTicket, "RESUELTO");
    }
}