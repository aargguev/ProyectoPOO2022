/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 *
 * @author Hollouss
 */
public interface RoverI {

    boolean avanzar();

    void girar(double g);

    void dirigirse(double x, double y);

    void sensar();
    
    void cargar();
}
