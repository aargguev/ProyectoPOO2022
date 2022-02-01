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
    private static String FILE_PATH =  Paths.get("src/recursos/crateres_info.txt").toString();
    
    public static ArrayList<Crater> cargarCrater() {
        ArrayList<Crater> crater = new ArrayList<>();
        try (BufferedReader bf = new BufferedReader(new FileReader("src\\main\\java\\recursos/crateres_info.txt"))) {
            String linea;
            while ((linea = bf.readLine()) != null) {
                String c[] = linea.split(",");

                String idCrater = c[0].trim();
                String nombreCrater = c[1].trim();
                double latitud = Double.valueOf(c[2].trim());
                double longitud = Double.valueOf(c[3].trim());
                double radio = Double.valueOf(c[4].trim());

                crater.add(new Crater(idCrater, nombreCrater, new Coordenada(latitud, longitud), radio));
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return crater;
    }
    
}
