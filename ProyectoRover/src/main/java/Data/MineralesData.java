/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Data;

import ec.edu.espol.proyectorover.App;
import modelo.Coordenada;
import modelo.Mineral;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
/**
 *
 * @author Kevin APR
 */
public class MineralesData {
    public static ArrayList<Mineral> cargarMineral() throws IOException {
        ArrayList<Mineral> minerales = new ArrayList<>();
        try (BufferedReader bf = new BufferedReader(new FileReader(App.ruta+"minerales.txt"))) {
            String linea;
            while ((linea = bf.readLine()) != null) {
                String min[] = linea.split(",");

                String nomMine = min[0].trim();
                double latMine = Double.valueOf(min[1].trim());
                double lonMine = Double.valueOf(min[2].trim());
                Coordenada c = new Coordenada(latMine, lonMine);

                minerales.add(new Mineral(nomMine, c));
            }
        }
        return minerales;
    }
    
    public static void escribirMin() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(App.ruta+"minerales.txt"))) {
            int count = 0;
            String[] minerales = {"Carbonato", "Hematita", "Goethita", "Jarosita", "Yeso", "Filosilicatos", "Esmectita", "Sulfato de Hierro", "Sílice Opalina"};
            List<Integer> coordX = IntStream.rangeClosed(0, 1100).boxed().collect(Collectors.toList());
            List<Integer> coordY = IntStream.rangeClosed(0, 700).boxed().collect(Collectors.toList());
            while (count < 7700) {
                for (int i : coordX) {
                    if (i % 10 == 0 || i == 0) {
                        for (int j : coordY) {
                            if (j % 10 == 0 || j == 0) {
                                int index = new Random().nextInt(minerales.length);
                                bw.write(minerales[index] + "," + String.valueOf(i) + "," + String.valueOf(j));
                                bw.newLine();
                                count += 1;
                            }
                        }
                    }
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
