/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.time.LocalDate;
import java.time.Month;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Kevin APR
 */
public class ExpReporte {

    private final ObjectProperty<LocalDate> fechaExp;
    private final StringProperty nombreC;
    private final StringProperty minerales;

    public ExpReporte(LocalDate fechaExp, String nombreC, String minerales) {
        this.fechaExp = new SimpleObjectProperty<LocalDate>(fechaExp);
        this.nombreC = new SimpleStringProperty(this, "nombreC", nombreC);
        this.minerales = new SimpleStringProperty(this, "minerales", minerales);
    }

    public ExpReporte(String nombreC, String minerales) {
        this.fechaExp = new SimpleObjectProperty<LocalDate>(LocalDate.now());
        this.nombreC = new SimpleStringProperty(this, "nombreC", nombreC);
        this.minerales = new SimpleStringProperty(this, "minerales", minerales);
    }

    //Getters y Setters de la clase 
    /**
     * Devuelve la fecha de la exploracion
     *
     * @return LocalDate
     */
    public final LocalDate getFechaExp() {
        return this.fechaExp.get();
    }

    public final void setFechaExp(LocalDate date) {
        this.fechaExp.set(date);
    }

    public final ObjectProperty<LocalDate> FechaExpProperty() {
        return this.fechaExp;
    }

    public final String getNombreC() {
        return this.nombreC.get();
    }

    public final void setNombreC(String name) {
        this.nombreC.set(name);
    }

    public final StringProperty nombreCProperty() {
        return this.nombreC;
    }

    public final String getMinerales() {
        return this.minerales.get();
    }

    public final void setMinerales(String mine) {
        this.minerales.set(mine);
    }

    public final StringProperty MineralesProperty() {
        return this.minerales;
    }
    
}
