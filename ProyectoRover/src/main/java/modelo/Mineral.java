/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.io.Serializable;

/**
 *
 * @author Kevin APR
 */

public class Mineral implements Serializable {

    private String mineNombre;
    Coordenada ubiMineral;

    public Mineral(String mineNombre, Coordenada ubiMineral) {
        this.mineNombre = mineNombre;
        this.ubiMineral = ubiMineral;
    }

    /**
     * Devuelve el nombre del mineral
     *     
     * @return String
     */
    public String getMineNombre() {
        return mineNombre;
    }

    public void setMineNombre(String mineNombre) {
        this.mineNombre = mineNombre;
    }

    public Coordenada getUbiMineral() {
        return ubiMineral;
    }

    public void setUbiMineral(Coordenada ubiMineral) {
        this.ubiMineral = ubiMineral;
    }

    @Override
    public String toString() {
        return "Mineral: " + mineNombre + ", Ubicacion: " + ubiMineral;
    }


}


    
   
