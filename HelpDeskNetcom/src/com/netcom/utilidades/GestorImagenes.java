package com.netcom.utilidades;

import java.awt.Image;
import javax.swing.ImageIcon;

public class GestorImagenes {

    /**
     * Este método estático carga una imagen desde la carpeta recursos,
     * le ajusta el tamaño con alta calidad (SCALE_SMOOTH) y la devuelve lista para usar.
     */
    public static ImageIcon obtenerImagenEscalada(String ruta, int ancho, int alto) {
        // 1. Busca el archivo original en la carpeta del proyecto
        ImageIcon iconoOriginal = new ImageIcon(GestorImagenes.class.getResource(ruta));
        
        // 2. Le cambia el tamaño (ancho y alto) para que no se vea deforme ni gigante
        Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
        
        // 3. Devuelve la imagen ya arreglada
        return new ImageIcon(imagenEscalada);
    }
}