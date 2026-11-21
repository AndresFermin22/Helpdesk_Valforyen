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

    // Variables de sesión activa (Se conectan automáticamente con el Perfil y Registro)
    private String nombreSesionActiva = "Fermin López";
    private String cedulaSesionActiva = "1.098.765.432";
    private String usuarioRedSesionActiva = "f.lopez";
    private String areaSesionActiva = "Urgencias";
    private String rolSesionActiva = "Médico General";

    // Variables de Memoria de Navegación
    private String pantallaActual = "PANTALLA_LOGIN";
    private String pantallaAnterior = "PANTALLA_LOGIN";
    private String pantallaInicioRol = "PANTALLA_BANDEJA_GLOBAL";

    // Todas las pantallas del sistema
    private PanelLogin pantallaLogin;
    private PanelCreacionUsuario pantallaCreacionUsuario;
    private PanelUsuarioCreado pantallaUsuarioCreado;
    private PanelRecuperarAcceso pantallaRecuperarAcceso;
    private PanelSolicitudEnviada pantallaSolicitudEnviada;
    private PanelBandejaTickets pantallaBandejaTickets;
    private PanelRegistroTicket pantallaRegistroTicket;
    private PanelTicketCreado pantallaTicketCreado;
    private PanelDetallesTicket pantallaDetallesTicket;
    private PanelPerfilUsuario pantallaPerfilUsuario;
    private PanelBandejaGlobal pantallaBandejaGlobal;
    private PanelTicketResuelto pantallaTicketResuelto;
    private PanelExportarTickets pantallaExportarTickets;
    private PanelInformeExportado pantallaInformeExportado;
    private PanelInventario pantallaInventario;
    private PanelDashboard pantallaDashboard;
    private PanelAdministracionUsuarios pantallaAdminUsuarios;
    private PanelEliminarUsuario pantallaEliminarUsuario;
    private PanelUsuarioEliminado pantallaUsuarioEliminado;

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
        pantallaPerfilUsuario = new PanelPerfilUsuario(this);
        pantallaBandejaGlobal = new PanelBandejaGlobal(this);
        pantallaTicketResuelto = new PanelTicketResuelto(this);
        pantallaExportarTickets = new PanelExportarTickets(this);
        pantallaInformeExportado = new PanelInformeExportado(this);
        pantallaInventario = new PanelInventario(this);
        pantallaDashboard = new PanelDashboard(this);
        pantallaAdminUsuarios = new PanelAdministracionUsuarios(this);
        pantallaEliminarUsuario = new PanelEliminarUsuario(this);
        pantallaUsuarioEliminado = new PanelUsuarioEliminado(this);

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
        panelContenedorMaestro.add(pantallaPerfilUsuario, "PANTALLA_PERFIL_USUARIO");
        panelContenedorMaestro.add(pantallaBandejaGlobal, "PANTALLA_BANDEJA_GLOBAL");
        panelContenedorMaestro.add(pantallaTicketResuelto, "PANTALLA_TICKET_RESUELTO");
        panelContenedorMaestro.add(pantallaExportarTickets, "PANTALLA_EXPORTAR_TICKETS");
        panelContenedorMaestro.add(pantallaInformeExportado, "PANTALLA_INFORME_EXPORTADO");
        panelContenedorMaestro.add(pantallaInventario, "PANTALLA_INVENTARIO");
        panelContenedorMaestro.add(pantallaDashboard, "PANTALLA_DASHBOARD");
        panelContenedorMaestro.add(pantallaAdminUsuarios, "PANTALLA_ADMIN_USUARIOS");
        panelContenedorMaestro.add(pantallaEliminarUsuario, "PANTALLA_ELIMINAR_USUARIO");
        panelContenedorMaestro.add(pantallaUsuarioEliminado, "PANTALLA_USUARIO_ELIMINADO");

        // 3. Arrancamos en el Login
        cardLayout.show(panelContenedorMaestro, "PANTALLA_LOGIN");
    }

    public void mostrarPantalla(String nuevaPantalla) {
        if (!this.pantallaActual.equals("PANTALLA_USUARIO_CREADO")
                && !this.pantallaActual.equals("PANTALLA_SOLICITUD_ENVIADA")
                && !this.pantallaActual.equals("PANTALLA_TICKET_CREADO")
                && !this.pantallaActual.equals("PANTALLA_INFORME_EXPORTADO")
                && !this.pantallaActual.equals("PANTALLA_TICKET_RESUELTO")
                && !this.pantallaActual.equals("PANTALLA_ELIMINAR_USUARIO")
                && !this.pantallaActual.equals("PANTALLA_USUARIO_ELIMINADO")) {
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

    /**
     * Inicia sesión guardando todos los datos del usuario para conectarlos con el Perfil, Registro y Barras Superiores
     */
    public void iniciarSesionUsuario(String nombreMostrar, String cedula, String usuarioRed, String area, String cargoRol, String pantallaDestino) {
        this.nombreSesionActiva = nombreMostrar;
        this.cedulaSesionActiva = cedula;
        this.usuarioRedSesionActiva = usuarioRed;
        this.areaSesionActiva = area;
        this.rolSesionActiva = cargoRol;
        this.pantallaInicioRol = pantallaDestino;

        // Actualizamos los títulos y el perfil en todas las barras superiores
        pantallaCreacionUsuario.getBarraSuperior().actualizarBarraSegunRol(nombreSesionActiva, rolSesionActiva);
        pantallaUsuarioCreado.getBarraSuperior().actualizarBarraSegunRol(nombreSesionActiva, rolSesionActiva);
        pantallaBandejaTickets.getBarraSuperior().cambiarTitulo("Centro de Bandejas Tickets", nombreSesionActiva);
        pantallaRegistroTicket.getBarraSuperior().cambiarTitulo("Centro de Registro Incidencias", nombreSesionActiva);
        pantallaDetallesTicket.getBarraSuperior().cambiarTitulo("Detalles Tickets", nombreSesionActiva);
        pantallaPerfilUsuario.getBarraSuperior().cambiarTitulo("Centro de Perfil", nombreSesionActiva);
        pantallaBandejaGlobal.getBarraSuperior().cambiarTitulo("Centro de Mando TI", nombreSesionActiva);
        pantallaInventario.getBarraSuperior().cambiarTitulo("Centro de Mando TI", nombreSesionActiva);
        pantallaDashboard.getBarraSuperior().cambiarTitulo("Centro de Mando TI", nombreSesionActiva);
        pantallaAdminUsuarios.getBarraSuperior().cambiarTitulo("Centro de Mando TI", nombreSesionActiva);

        pantallaRegistroTicket.autocompletarSolicitante(nombreSesionActiva, areaSesionActiva, rolSesionActiva);
        pantallaBandejaTickets.filtrarPorSolicitanteActivo(nombreSesionActiva);

        mostrarPantalla(pantallaDestino);
    }

    /**
     * Abre la Pantalla 10 (Centro de Perfil) calculando en vivo los tickets del usuario conectado
     */
    public void abrirPerfilUsuarioActivo() {
        int[] conteo = pantallaBandejaTickets.obtenerResumenPorSolicitante(nombreSesionActiva);
        pantallaPerfilUsuario.actualizarDatosPerfil(
                nombreSesionActiva, rolSesionActiva, cedulaSesionActiva, usuarioRedSesionActiva,
                areaSesionActiva, conteo[0], conteo[1], conteo[2]);
        mostrarPantalla("PANTALLA_PERFIL_USUARIO");
    }

    /**
     * Recalcula el Top 5, tiempos promedio y gráfica quincenal antes de abrir la Pantalla 16
     */
    public void abrirDashboardKPIs() {
        pantallaDashboard.actualizarMetricasDesdeBandeja(pantallaBandejaGlobal.obtenerTodosLosTicketsGlobales());
        mostrarPantalla("PANTALLA_DASHBOARD");
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
     * Abre la Pantalla 19 ("¿Eliminar Usuario?") con el nombre real de la fila seleccionada en la Pantalla 17
     */
    public void abrirConfirmacionEliminarUsuario(int filaTabla, String nombreUsuario) {
        pantallaEliminarUsuario.cargarUsuarioAEliminar(filaTabla, nombreUsuario);
        mostrarPantalla("PANTALLA_ELIMINAR_USUARIO");
    }

    /**
     * Al confirmar en la Pantalla 19, inhabilita al usuario en la tabla y abre la Pantalla 20 ("¡Usuario Eliminado!")
     */
    public void confirmarEliminacionUsuario(int filaTabla) {
        pantallaAdminUsuarios.inhabilitarUsuarioEnTabla(filaTabla);
        mostrarPantalla("PANTALLA_USUARIO_ELIMINADO");
    }

    public void registrarTicketYMostrarConfirmacion(String fecha, String solicitante, String area, String categoria, String prioridad) {
        String codigoGenerado = pantallaBandejaTickets.agregarNuevoTicketATabla(fecha, solicitante, area, categoria, prioridad);
        pantallaBandejaGlobal.agregarTicketAGlobal(codigoGenerado, fecha, solicitante, area, categoria, prioridad);
        pantallaDashboard.registrarHoraCreacionTicket(codigoGenerado);

        pantallaTicketCreado.mostrarCodigoTicket(codigoGenerado);
        mostrarPantalla("PANTALLA_TICKET_CREADO");
    }

    public void abrirDetallesTicket(String id, String fecha, String solicitante, String area,
            String categoria, String prioridad, String estado, String asignadoA) {
        pantallaDetallesTicket.cargarDatosTicket(id, fecha, solicitante, area, categoria, prioridad, estado, asignadoA);
        mostrarPantalla("PANTALLA_DETALLES_TICKET");
    }

    public void sincronizarTicketResueltoEnTablas(String idTicket) {
        pantallaBandejaTickets.actualizarEstadoTicketEnBandeja(idTicket, "RESUELTO");
        pantallaBandejaGlobal.actualizarEstadoTicketGlobal(idTicket, "RESUELTO");
        pantallaDashboard.registrarHoraResolucionTicket(idTicket);

        pantallaTicketResuelto.mostrarTicketResuelto(idTicket);
        mostrarPantalla("PANTALLA_TICKET_RESUELTO");
    }

    public void mostrarInformeExportado(String rango, String area, String estado, String formato, boolean incluirDetalle) {
        pantallaInformeExportado.cargarResumenExportacion(rango, area, estado, formato, incluirDetalle);
        mostrarPantalla("PANTALLA_INFORME_EXPORTADO");
    }
}