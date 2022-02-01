/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import Data.EstadoCrater;
import static Data.EstadoCrater.NO_EXPLORADO;
import Data.Visita;
import java.io.Serializable;
import java.util.ArrayList;
/**
 * Clase que nos permitira mas adelante ubicar en el mapa los crateres presentes
 * en un arreglo generado por la clase CraterData
 * @author Adrian Lautaro
 */
public class Crater {
    private String idCrater;
    private String nombreCrater;
    private double radio;
    private Coordenada ubicacion;
    private EstadoCrater estadoCrater;
    private ArrayList<Mineral> minerales;
    private Visita estadoVisita;
    public static Coordenada ultiUbi;
    
    public Crater(String idCrater, String nombreCrater, Coordenada ubicacion, double radio) {
        this.idCrater = idCrater;
        this.nombreCrater = nombreCrater;
        this.ubicacion = ubicacion;
        this.radio = radio;
        minerales = new ArrayList<>();
        estadoCrater = NO_EXPLORADO;
    }
    public String getIdCrater() {
        return idCrater;
    }

    public void setIdCrater(String idCrater) {
        this.idCrater = idCrater;
    }

    public String getNombreCrater() {
        return nombreCrater;
    }

    public void setNombreCrater(String nombreCrater) {
        this.nombreCrater = nombreCrater;
    }

    public double getRadio() {
        return radio;
    }

    public void setRadio(double radio) {
        this.radio = radio;
    }

    public Coordenada getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(Coordenada ubicacion) {
        this.ubicacion = ubicacion;
    }

    public EstadoCrater getEstadoCrater() {
        return estadoCrater;
    }

    public void setEstadoCrater(EstadoCrater estadoCrater) {
        this.estadoCrater = estadoCrater;
    }

    public ArrayList<Mineral> getMinerales() {
        return minerales;
    }

    public void setMinerales(ArrayList<Mineral> minerales) {
        this.minerales = minerales;
    }

    public Visita getEstadoVisita() {
        return estadoVisita;
    }

    public static Coordenada getUltiUbi() {
        return ultiUbi;
    }

    public void setEstadoVisita(Visita estadoVisita) {
        this.estadoVisita = estadoVisita;
    }

    public static void setUltiUbi(Coordenada ultiUbi) {
        Crater.ultiUbi = ultiUbi;
    }
        

    @Override
    public String toString() {
        return "IDCrater:" + idCrater + ", Nombre:" + nombreCrater + ", Ubicacion:" + ubicacion.toString() + ", Radio:" + radio + ", Estado:" + estadoCrater.toString();
    }
    @Override
    public boolean equals(Object o) {
        if (o != null && o instanceof Crater) {
            Crater c = (Crater) o;
            if (nombreCrater.equals(c.getNombreCrater())) {
                return true;
            }
        }
        return false;
    }
    
    
}
