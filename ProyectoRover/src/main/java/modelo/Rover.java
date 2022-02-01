/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;


import Data.CraterData;
import Data.MineralesData;
import ec.edu.espol.proyectorover.App;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import javafx.application.Platform;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Point2D;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
/**
 *
 * @author Hollouss
 */
public class Rover implements RoverI {

    private ImageView rover;
    private Mineral minEnco;
    private String nombre; 
    private String tipo;
    private Coordenada ubicacion;
    
    /**
     * Constructor del rover
     * @param name del rover
     */
    public Rover(String name, Coordenada ubi, String tipo) {
        try {
            FileInputStream fr = new FileInputStream( App.ruta+name+".png");
            Image rov = new Image(fr);
            rover = new ImageView(rov);
            rover.setFitHeight(40);
            rover.setFitWidth(40);
            nombre= name;
            ubicacion=ubi;
            this.tipo=tipo;
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    /**
     *
     * Al ser utilizado, te devuelve el imageView del rover
     *
     * @return rover
     */
    public ImageView getRover() {
        return rover;
    }

    /**
     *
     * Al ser utilizado, permite avanzar al Rover en una dirección específica
     * que depende de su ángulo de inclinacion actual
     *
     * @return false Cuando el rover esta en los limites del mapa de Marte
     */
    @Override
    public boolean avanzar() {
        double angle = rover.getRotate();
        double oldX = rover.getLayoutX();
        double oldY = rover.getLayoutY();
        double radangle = Math.toRadians(angle);
        double y = App.DISTANCE_Y * Math.sin(radangle);
        double x = App.DISTANCE_X * Math.cos(radangle);

        rover.setLayoutX(oldX + x);
        rover.setLayoutY(oldY + y);

        if ((rover.getLayoutX() >= 1120 || rover.getLayoutX() <= -20) || (rover.getLayoutY() >= 620 || rover.getLayoutY() < 0)) {
            rover.setLayoutX(oldX);
            rover.setLayoutY(oldY);
            return false;
        }
        return true;
    }

    /**
     * Metodo que permite al rover desplazarse en cualquier distancia ingresada
     * de manera uniforme.
     *
     * @param espacio Distancia a recorrer en double
     */
    public void desplazar(double espacio) {
        double angle = rover.getRotate();
        double oldX = rover.getLayoutX();
        double oldY = rover.getLayoutY();
        double radangle = Math.toRadians(angle);
        double y = espacio * Math.sin(radangle);
        double x = espacio * Math.cos(radangle);

        rover.setLayoutX(oldX + x);
        rover.setLayoutY(oldY + y);

        if ((rover.getLayoutX() >= 1120 || rover.getLayoutX() <= -20) || (rover.getLayoutY() >= 620 || rover.getLayoutY() < 0)) {
            rover.setLayoutX(oldX);
            rover.setLayoutY(oldY);
        }

    }

    /**
     *
     * Al ser utilizado, el rover gira su angulo con respecto al angulo en el
     * que se econtraba antes.
     *
     * @param g Angulo en double
     */
    @Override
    public void girar(double g) {
        double angle = rover.getRotate();
        rover.setRotate(angle + g);
    }

    /**
     *
     * Al ser utilizado, permite avanzar al Rover en una dirección específica
     * que depende de su ángulo
     *
     * @param x Valor que representa la coordenada X a la cual queremos dirigir
     * al rover
     * @param y Valor que representa la coordenada Y a la cual queremos dirigir
     * al rover
     */
    @Override
    public void dirigirse(double x, double y) {
        Vector2D vectorA = new Vector2D(rover.getLayoutX(), rover.getLayoutY());
        Vector2D vectorB = new Vector2D(x, y);
        Vector2D vectorC = vectorB.getSubtracted(vectorA);
        Vector2D vecUniC = vectorC.getNormalized();
        double finAngle = Math.toDegrees(vecUniC.getAngle());
        double girAngle = finAngle - rover.getRotate();
        girar(girAngle);
        Thread movRov = new Thread(new ViajeRov(x, y));
        movRov.start();
    }
 /**
     *
     * Se comporta distinto para los rovers solares y los eólicos
     *En el caso de los rovers con panales solares al llamar a este método aparece el 
    mensaje “abriendo panales”
    * En el caso de los rovers que usan energía eólica al llamar a este método aparece 
el mensaje “despliegue de molinos” 
     */
    @Override
    public void cargar() {
        if ("solar".equals(this.tipo)){
            Alert alert = new Alert(AlertType.INFORMATION, "Abriendo paneles solares", ButtonType.OK);
            alert.show();
            rover.setLayoutX(100);
            rover.setLayoutY(100);
        }else{
             Alert alert = new Alert(AlertType.INFORMATION, "Despligue de molinos", ButtonType.OK);
            alert.show();
            rover.setRotate(0);
            
        }
    }

    /**
     * Inner Class de tipo runnable que sirve para dirigir al rover de un punto
     * a otro. Se lo usara para crear un hilo de ejecucion diferente del hilo
     * principal
     *
     * @author Kevin
     */
    private class ViajeRov implements Runnable {

        private double x;
        private double y;
        private double distance;

        public ViajeRov(double x, double y) {
            this.x = x;
            this.y = y;
            distance = Coordenada.calcularDistanciaDospuntos(new Coordenada(rover.getLayoutX(), rover.getLayoutY()), new Coordenada(x, y));
        }

        /**
         *
         * Metodo run implementado de Runnable para tener control de la llegada
         * del rover al punto destino
         *
         */
        @Override
        public void run() {
            while (distance > 0) {
                try {
                    Thread.sleep(25);
                    distance -= 1;
                    Platform.runLater(() -> {
                        desplazar(1);
                    });
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }

    }

    /**
     *
     * Al ser utilizado, muestra los minerales encontrados en una posición y si
     * estás en un cráter, pues también y además lo marca como explorado.
     *
     */
    @Override
    public void sensar() {
        try {
            Coordenada ubiRov = new Coordenada(rover.getLayoutX(), rover.getLayoutY());
            ArrayList<Mineral> minerales = MineralesData.cargarMineral();
            for (Mineral m : minerales) {
                if (m.getUbiMineral().equals(ubiRov)) {
                    this.setMinEnco(m);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     *
     * Al ser utilizado, te devuelve el mineral encontrado por el rover
     *
     * @return minEnco
     */
    public Mineral getMinEnco() {
        return minEnco;
    }

    /**
     *
     * Al ser utilizado, cambia el mineral inicial por el ingresado como
     * parámetro
     *
     * @param minEnco
     */
    public void setMinEnco(Mineral minEnco) {
        this.minEnco = minEnco;
    }

    @Override
    public String toString() {
        return "Rover: "  + nombre ;
    }
    
}