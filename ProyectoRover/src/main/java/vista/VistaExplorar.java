/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import modelo.Coordenada;
import modelo.Crater;
import modelo.ExpReporte;
import modelo.Mineral;
import modelo.Rover;
import Data.CraterData;
import static Data.EstadoCrater.EXPLORADO;
import Data.ExploracionData;
import Data.MineralesData;
import com.mycompany.proyectopoo.App;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
/**
 *
 * @author Hollouss
 */
public class VistaExplorar {

    private BorderPane raiz;
    private Pane exZone;
    private VBox commands;
    private ArrayList<Rover> rovers= new ArrayList<>();
    private Rover rover;
    private ImageView mars;
    private Label detalle;
    private ArrayList<Crater> tudoCrater;
    private ComboBox<Rover> combo= new ComboBox<>();

    /**
     * Contructor de clase que inicializa los nodos a implementar en la vista de
     * exploracion
     */
    public VistaExplorar() {
        try {
            raiz = new BorderPane();
            tudoCrater = CraterData.cargarCrater();
            fillComboBox();        
            expMarte();
            comandos();
        } catch (Exception ex) {
            ex.printStackTrace();
            Platform.exit();
        }
    }

    /**
     * Metodo que crear la visualizacion del mapa de Marte y los setea en el
     * centro de la raiz de la vista
     */
    public void expMarte() {
        try {
            FileInputStream fi = new FileInputStream(App.ruta + "Marte.jpg");
            Image marte = new Image(fi);
            mars = new ImageView(marte);
            mars.setFitHeight(660);
            mars.setFitWidth(1150);     
            rover= combo.getValue();
            exZone = new Pane(mars, rover.getRover());
            drawCrat();
            raiz.setCenter(exZone);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    private void fillComboBox() {
        try (BufferedReader bf = new BufferedReader(new FileReader(App.ruta + "rovers.txt"))) {
            String linea;
            Rover r;
            while ((linea = bf.readLine()) != null) {
                String c[] = linea.split(",");
                Coordenada ubi= new Coordenada(Double.parseDouble(c[1]), Double.parseDouble(c[2]));
                r= new Rover(c[0],ubi,c[3]);
                rovers.add(r);
            }
            combo.getItems().addAll(rovers);
            combo.getSelectionModel().selectFirst();
            combo.valueProperty().addListener((o) -> {
                rover=combo.getValue();
                expMarte();
            });
            
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    /**
     * Metodo que implementa en la raiz, los comandos del rover a modo de
     * interfaz para el usuario
     */
    public void comandos() {
        commands = new VBox();
        commands.setSpacing(10);
        commands.getChildren().add(combo);
        Label comtxt = new Label("Ingrese comando:");
        TextField comande = new TextField();
        comande.setMinWidth(-5);
        Label contxt = new Label("Comandos Ingresados");
        TextArea comandado = new TextArea();
        comandado.setMinWidth(-5);
        Label posicion = new Label("");
        commands.getChildren().addAll(comtxt, comande, contxt, comandado, posicion);
        raiz.setRight(commands);
        commands.setMaxWidth(200);
        commands.setPadding(new Insets(10));
        AnchorPane detail = new AnchorPane();
        detalle = new Label("Bienvenido");
        detail.getChildren().add(detalle);
        AnchorPane.setLeftAnchor(detalle, 10.0);
        raiz.setBottom(detail);

        comande.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ev) {
                String tecla = ev.getCode().toString();
                String comand = comande.getCharacters().toString();
                if (tecla.equals("ENTER")) {
                    if (comand.equalsIgnoreCase("avanzar")) {
                        if (rover.avanzar() == true) {
                            rover.avanzar();
                            comandado.appendText("avanzar" + "\n");
                        } else {
                            rover.avanzar();
                        }
                    } else if (comand.contains("girar")) {
                        String[] gradPrim = comand.split(":");
                        double grados = Double.valueOf(gradPrim[1]);
                        rover.girar(grados);
                        comandado.appendText(comand + "\n");
                    } else if (comand.equalsIgnoreCase("sensar")) {
                        try {
                            sacaMin(inOrOut());
                            comandado.appendText(comand + "\n");
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }

                    } else if (comand.contains("dirigir")) {
                        String[] pal = comand.trim().split(":");
                        String[] coord = pal[1].trim().split(",");
                        double x = Double.valueOf(coord[0]);
                        double y = Double.valueOf(coord[1]);
                        rover.dirigirse(x, y);
                        comandado.appendText(comand + "\n");
                    }else if (comand.contains("cargar")) {
                        rover.cargar();
                        comandado.appendText(comand + "\n");
                    }
                }
            }
        });
        mars.setOnMouseMoved(
                (MouseEvent e) -> {
                    double x = e.getX();
                    double y = e.getY();
                    posicion.setText("Coordenadas XY: " + String.valueOf(x) + "," + String.valueOf(y));
                }
        );
    }

    /**
     * Devuele el Crater encontrado en un senso, caso contrario, devuelve null
     *
     * @return c
     * @throws java.io.IOException
     */
    public Crater inOrOut() throws IOException {
        Coordenada ubiRov = new Coordenada(rover.getRover().getLayoutX(), rover.getRover().getLayoutY());
        for (Crater c : tudoCrater) {
            if (Coordenada.calcularDistanciaDospuntos(ubiRov, c.getUbicacion()) <= c.getRadio()) {
                return c;
            }
        }

        return null;
    }

    public void sacaMin(Crater c) throws IOException {
        if (c == null) {
            rover.sensar();
            if (rover.getMinEnco() == null) {
                detalle.setText("No hay mineral aquí.");
            } else {
                detalle.setText(rover.getMinEnco().getMineNombre() + "|" + rover.getMinEnco().getUbiMineral().toString());
            }
        } else {
            ArrayList<Mineral> minerales = MineralesData.cargarMineral();
            HashSet<Mineral> minCrat = new HashSet<>();
            HashSet<String> nomCrat = new HashSet<>();
            ArrayList<String> mineralesString = new ArrayList<>();
            for (Mineral m : minerales) {
                if (Coordenada.calcularDistanciaDospuntos(m.getUbiMineral(), c.getUbicacion()) <= c.getRadio()) {
                    nomCrat.add(m.getMineNombre());
                    minCrat.add(m);
                }
            }
            if (minCrat.isEmpty()) {
                detalle.setText("No hay mineral aquí.");
            } else {
//                ArrayList<Mineral> mina= new ArrayList<>();
                String lineMin = "";
                for (String min : nomCrat) {
                    mineralesString.add(min);
                }
                for (String min : mineralesString) {
                    if (mineralesString.indexOf(min) == (mineralesString.size() - 1)) {
                        lineMin += min;
                    } else {
                        lineMin += min + ",";
                    }
                }
                for (Crater cr : tudoCrater) {
                    if (cr.getNombreCrater().equals(c.getNombreCrater())) {
                        for (Mineral m : minCrat) {
                            cr.getMinerales().add(m);
                        }
                        cr.setEstadoCrater(EXPLORADO);
                    }
                }
                c.setEstadoCrater(EXPLORADO);
                ExpReporte exploReport = new ExpReporte(c.getNombreCrater(), lineMin);
                ExploracionData.escribirExploracion(exploReport);
                detalle.setText(c.toString() + " " + "Minerales: " + nomCrat.toString());
            }
        }
    }

    public void drawCrat() {
        for (Crater c : tudoCrater) {
            Circle circrat = new Circle(c.getRadio());
            circrat.setLayoutX(c.getUbicacion().getLatitud());
            circrat.setLayoutY(c.getUbicacion().getLongitud());
            circrat.setFill(Color.rgb(102, 228, 48, 0.5));
            circrat.setStroke(Color.BLACK);
            circrat.setStrokeWidth(3);
            exZone.getChildren().add(circrat);
            ArrayList<Circle> circles = new ArrayList<>();
            circles.add(circrat);
            circrat.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    detalle.setText(c.toString());
                    if (!detalle.getText().contains("NO_EXPLORADO")) {
                        for (Circle cl : circles) {
                            if (c.getRadio() == cl.getRadius()) {
                                cl.setFill(Color.rgb(255, 0, 0, 0.5));
                            }
                        }
                    }
                }
            });
        }

    }

    public Pane getRaiz() {
        return raiz;
    }

}
