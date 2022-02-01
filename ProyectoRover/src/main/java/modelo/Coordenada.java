/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;

/**
 *
 * @author Hollouss
 */
public class Coordenada implements Serializable {

    private double latitud;
    private double longitud;

    public Coordenada(double latitud, double longitud) {
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public double getLatitud() {
        return latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLat(double latitud) {
        this.latitud = latitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    /**
     * Sirve para calcular la distancia entre dos coordenadas, a partir de sus
     * longitudes y latitud en el mapa
     *
     * @param c1: Coordenada para calcular la distancia con c2
     * @param c2: La coordenada para calcular con c1
     * @return c: Retorna un double que determina la distancia entre c1 y c2
     */
    public static double calcularDistanciaDospuntos(Coordenada c1, Coordenada c2) {
        double difLat;
        double difLon;
        double lat1 = c1.getLatitud();
        double lat2 = c2.getLatitud();
        double lon1 = c1.getLongitud();
        double lon2 = c2.getLongitud();
        double a;
        double c;
        difLat = Math.pow(lat2 - lat1, 2);
        difLon = Math.pow(lon2 - lon1, 2);
        a = difLat + difLon;
        c = Math.sqrt(a);
        return c;
    }

    @Override
    public String toString() {
        return "( " + latitud + " , " + longitud + " )";
    }

    @Override
    public int hashCode() {
        int hash = 5;
        return hash;
    }

    /**
     * Metodo que usaremos para determinar si el rover esta por encima de un
     * crater calculando la distancia con el radio del crater.
     *
     * @param obj Objeto que puede ser una Coordenada
     * @return true solo cuando son el mismo objeto
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Coordenada other = (Coordenada) obj;
        if (Double.doubleToLongBits(this.latitud) != Double.doubleToLongBits(other.latitud)) {
            return false;
        }
        if (Double.doubleToLongBits(this.longitud) != Double.doubleToLongBits(other.longitud)) {
            return false;
        }
        return true;
    }


}

