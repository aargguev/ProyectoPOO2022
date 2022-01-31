/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import modelo.Coordenada;
import modelo.Crater;
import modelo.Rover;
import Data.CraterData;
import Data.Visita;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

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
            for (Crater c : CraterData.cargarCrater()) {
                craterez.add(c.getNombreCrater());
            }
            Cuerpo();
      
    }
    
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
                    String[] cr = crateres.split(",");
                    ArrayList<String> crx = ordenarCrat(cr);
                    for (int i = 0; i < cr.length; i++) {
                        ordenCrat.appendText(String.valueOf(i + 1) + ". " + crx.get(i) + "\n");
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
    
    
    public ArrayList<String> ordenarCrat(String[] cratos) throws IOException {
        HashSet<Crater> crateres = new HashSet<>();
        ArrayList<Crater> cratRe = new ArrayList<>();
        ArrayList<Crater> cratVis = new ArrayList<>();
        for (int i = 0; i < cratos.length; i++) {
            if (craterez.contains(cratos[i])) {
                crateres.add(CraterData.cargarCrater().get(craterez.indexOf(cratos[i])));
            }
        }
        cratRe.addAll(crateres);
        ArrayList<String> craterex = new ArrayList<>();
        int index = 0;
        double minDis = Double.MAX_VALUE;
        while (craterex.size() < crateres.size()) {
            for (Crater c : cratRe) {
                if (String.valueOf(c.getEstadoVisita()).equals("NO_VISITADO")) {
                    double disDef = Coordenada.calcularDistanciaDospuntos(Crater.getUltiUbi(), c.getUbicacion());
                    if (disDef < minDis) {
                        minDis = disDef;
                        index = cratRe.indexOf(c);
                    }
                }
            }
            minDis = Double.MAX_VALUE;
            Crater.setUltiUbi(cratRe.get(index).getUbicacion());
            cratRe.get(index).setEstadoVisita(Visita.VISITADO);
            craterex.add(cratRe.get(index).getNombreCrater());
        }
        return craterex;
    }

    public BorderPane getRaiz() {
        return raiz;
    }
    
    
}
