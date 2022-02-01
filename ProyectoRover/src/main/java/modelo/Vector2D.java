/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *Clase para manejos de vectores y fscilitar a posicion y ubicacion en el mapa
 * @author Adrian Lautaro
 */
public class Vector2D {

    private double x;
    private double y;

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Devuele la magnitud del vector return double
     *
     * @return Double
     */
    public double getLength() {
        return Math.sqrt(x * x + y * y);
    }

    public double getAngle() {
        return Math.atan2(y, x);
    }

    /**
     * Devuelve el vector normal del vector en cuestion
     *
     * @return Vector2D
     */
    public Vector2D getNormalized() {
        double magnitude = getLength();
        return new Vector2D(x / magnitude, y / magnitude);
    }

    /**
     * Devuelve el vector resultante de la resta de dos vectores
     *
     * @param v1: Vector 1
     * @param v2: Vector 2
     *
     * @return Vector2D
     */
    public static Vector2D subtract(Vector2D v1, Vector2D v2) {
        return new Vector2D(v1.x - v2.x, v1.y - v2.y);
    }

    /**
     * Devuelve el vector resultante de la resta de un vector ingresado con el vector de la clase
     *
     * @param v: Vector a ingresar   
     *
     * @return Vector2D
     */
    public Vector2D getSubtracted(Vector2D v) {
        return new Vector2D(this.x - v.x, this.y - v.y);
    }
    
}
