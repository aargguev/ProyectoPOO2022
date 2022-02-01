/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;
import Data.CraterData;
import java.io.IOException;
import java.util.ArrayList;


/**
 *Clase prueba del metodo cararCrater
 * @author Adrian Lautaro
 */
public class TestCrater {
    public static void main(String[] args) {
        try {
            ArrayList<Crater> crateres = CraterData.cargarCrater();
            System.out.println(crateres);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }    
}
