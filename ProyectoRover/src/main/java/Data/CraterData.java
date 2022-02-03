/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Data;

import ec.edu.espol.proyectorover.App;
import modelo.Coordenada;
import modelo.Crater;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 *
 * @author Kevin APR
 */
public class CraterData {

    private static String FILE_PATH =  Paths.get(App.ruta+"crateres_info.txt").toString();

    /**
     * Esta funcion lee el archivo crateres_info.txt que se encuentra en el
     * paquete recursos y retorna un ArrayList con la info de los crateres
     * FORMATO ARCHIVO idCrater,nombre,distancia,latitud,longitud
     *
     * @return crater Arreglo que contiene todos los objeto de tipo crater
     * encontrados en el archivo crateres_info.txt
     * 
     */
    public static void cargarCrater() {
        
        try (BufferedReader bf = new BufferedReader(new FileReader(App.ruta+"crateres_info.txt"))) {
            String linea;
            while ((linea = bf.readLine()) != null) {
                String c[] = linea.split(",");

                String idCrater = c[0].trim();
                String nombreCrater = c[1].trim();
                double latitud = Double.valueOf(c[2].trim());
                double longitud = Double.valueOf(c[3].trim());
                double radio = Double.valueOf(c[4].trim());
                App.listaCrateres.add(new Crater(idCrater, nombreCrater, new Coordenada(latitud, longitud), radio));
            }
        } catch (FileNotFoundException ex) {
            System.out.println("No se pudo encontrar el archivo crateres ");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}