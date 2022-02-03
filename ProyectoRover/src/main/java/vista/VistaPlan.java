/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import modelo.Coordenada;
import modelo.Crater;
import Data.Visita;
import static ec.edu.espol.proyectorover.App.listaCrateres;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
/**
 *
 * @author Kevin APR
 */
public class VistaPlan {
    
    private BorderPane raiz;
    private ArrayList<String> craterez;
    
    public VistaPlan() {
     
            raiz = new BorderPane();
            craterez = new ArrayList<>();
            for (Crater c : listaCrateres) {
                craterez.add(c.getNombreCrater());
            }
            Cuerpo();
      
    }

    /**
     * Metodo que crea los nodos que recibe los crateres ingresados por el
     * usuario
     */
    public void Cuerpo() {
        HBox cajaCrat = new HBox();
        TextField txt1 = new TextField();
        cajaCrat.getChildren().addAll(new Label("Nombre Crateres: "), txt1);
        cajaCrat.setAlignment(Pos.CENTER_LEFT);
        cajaCrat.setSpacing(10);
        raiz.setTop(cajaCrat);
        VBox cajaVC = new VBox();
        TextArea ordenCrat = new TextArea();
        cajaVC.getChildren().add(ordenCrat);
        raiz.setCenter(cajaVC);
        txt1.setOnKeyPressed((ev) -> {
            String tecla = ev.getCode().toString();
            String crateres = txt1.getCharacters().toString();
            if (tecla.equals("ENTER")) {
                try {
                    ordenCrat.clear();
                    System.out.println(crateres);
                 
                    String[] cr = crateres.split(",");
                    ArrayList<String> crx = ordenarCrat(cr);
                    System.out.println(crx);
                    for (int i = 0; i < crx.size(); i++) {
                        ordenCrat.appendText(String.valueOf(i + 1) + ". " + crx.get(i) + "\n");
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    /**
     * Metodo que ordena los crateres de acuerdo a la distancia que estan del
     * rover en el momento de su ejecucion
     *
     * @param cratos: Lista de crateres ingresados
     * @return ArrayList de objetos de tipo String
     * @throws java.io.IOException
     * 
     */
    public ArrayList<String> ordenarCrat(String[] cratos) throws IOException {
        ArrayList<Crater> crateres = new ArrayList<>();
        ArrayList<Double> distancias= new ArrayList<>();
        for (int i = 0; i < cratos.length; i++) {
            if (craterez.contains(cratos[i]) ) {
                Crater c= listaCrateres.get(craterez.indexOf(cratos[i]));
                if(!crateres.contains(c)){
                    crateres.add(c);
                    Coordenada actual= Crater.getUltiUbi();
                    Coordenada pas= c.getUbicacion();
                    double dis = Coordenada.calcularDistanciaDospuntos(actual, pas);
                    distancias.add(dis);
                }

            }
        }
        //Ordenar la lista de crateres.
        ArrayList<String> craterex = new ArrayList<>();
        int total= crateres.size();  
        while (craterex.size() < total) {
            Double minimum = Collections.min(distancias);
            int minArg = distancias.indexOf(minimum);
            Crater cactual= crateres.get(minArg);
            craterex.add(cactual.getNombreCrater());
            distancias.clear();
            crateres.remove(minArg);
            Crater.setUltiUbi(cactual.getUbicacion());
            for (Crater c: crateres){    
                 double dis = Coordenada.calcularDistanciaDospuntos(Crater.getUltiUbi(), c.getUbicacion());
                 distancias.add(dis);
            }   
        }
        return craterex;
    }

    public BorderPane getRaiz() {
        return raiz;
    }
}
