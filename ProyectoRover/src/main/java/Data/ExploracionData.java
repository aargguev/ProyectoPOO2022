/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Data;

import ec.edu.espol.proyectorover.App;
import modelo.Crater;
import modelo.ExpReporte;
//import ClasesModelo.ExploracionReporte;
import modelo.Mineral;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Kevin APR
 */
public class ExploracionData {
    public static void escribirExploracion(ExpReporte e) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(App.ruta+"exploracion_Info.txt", true))) {
            String fechaExplo = e.getFechaExp().format(DateTimeFormatter.ISO_DATE);
            String nomCrater = e.getNombreC();
            String mineNombres = e.getMinerales();
            bw.write(fechaExplo + ";" + nomCrater + ";" + mineNombres);
            bw.newLine();
        }
    }
    
    public static ArrayList<ExpReporte> cargarExploracion(){
        ArrayList<ExpReporte> reportes = new ArrayList<>();
        try (BufferedReader bf = new BufferedReader(new FileReader(App.ruta+"exploracion_Info.txt"))) {
            String linea;
            while ((linea = bf.readLine()) != null) {
                String[] l = linea.trim().split(";");
                LocalDate fecha = LocalDate.parse(l[0], DateTimeFormatter.ISO_DATE);
                String nombCrat = l[1].trim();
                String mineNombres = l[2];
                reportes.add(new ExpReporte(fecha, nombCrat, mineNombres));
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return reportes;
    }
    
}
