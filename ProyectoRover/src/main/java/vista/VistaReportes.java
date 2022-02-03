/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import modelo.ExpReporte;
import Data.ExploracionData;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.util.StringConverter;

/**
 *Clase que crea los nodos necesarios para la interfaz de la ventana reportes
 * @author Adrian Lautaro
 */
public class VistaReportes {

    private VBox seccionContenido;
    private BorderPane raiz;
    private GridPane gridPane;
    private TableView<ExpReporte> tabla;
    private ObservableList<ExpReporte> reportes;
    private ArrayList<ExpReporte> reportesNoVisibles;
    private FilteredList<ExpReporte> reporFiltra;

    /**
     * Constructor de la clase que inicializa los nodos a implementar
     */
    public VistaReportes() {

            //Objetos que se agregan en la clase reporte se los obtendra del archivo
            reportesNoVisibles = ExploracionData.cargarExploracion();
            reportes = FXCollections.observableArrayList();
            for (ExpReporte exp : reportesNoVisibles) {
                reportes.add(exp);
            }
            seccionContenido = new VBox();
            raiz = new BorderPane();
            seccionContenido.setSpacing(15);
            seccionBusqueda();
            generarTabla();
     
    }

    /**
     * Metodo que agrega la interfaz de interaccion para que el usuario ingrese
     * los criterios de busqueda
     */
    public void seccionBusqueda() {
        gridPane = new GridPane();

        TextField fechaIni_text = new TextField();
        TextField fechaFin_text = new TextField();
        TextField mineral_text = new TextField();
        Button buscar = new Button("Buscar");
        Button limpiar = new Button("Limpiar");

        //Agregamos los elementos al GridPane
        gridPane.addRow(0, new Label("Fecha Inicio"), fechaIni_text, new Label("YYYY-MM-DD"));
        gridPane.addRow(1, new Label("Fecha Fin"), fechaFin_text, new Label("YYYY-MM-DD"));
        gridPane.addRow(2, new Label("Mineral"), mineral_text);
        gridPane.addRow(3, buscar, limpiar);

        //Damos un espacion vertical y horizontal sobre los elementos en pantalla
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        buscar.setOnAction((e) -> {
            validarFechas(fechaIni_text, fechaFin_text, mineral_text);
        });

        limpiar.setOnAction((e) -> {
            fechaIni_text.setText("");
            fechaFin_text.setText("");
            mineral_text.setText("");
        });

        seccionContenido.getChildren().add(gridPane);

        raiz.setLeft(seccionContenido);
    }

    /**
     * Convierte un String a un objeto LocalDate
     *
     * @param fecha: String que representa la fecha
     * @return LocalDate
     */
    public LocalDate convertirFecha(String fecha) {
        try {
            LocalDate fechaDate = LocalDate.parse(fecha);
            return fechaDate;
        } catch (DateTimeParseException e) {
            throw e;
        }
    }

    /**
     * Valida las fechas ingresadas por el usuario, e intenta instanciar dos
     * fechas de tipo LocalDate, para poder filtrar la tabla.
     *
     * @param cajaFInicio: TextField donde esta el String de la fecha de inicio
     * @param cajaFFin: TextField donde esta el String de la fecha de fin
     * @param mineral: TextField donde esta el String del mineral que se quiere
     * buscar
     */
    public void validarFechas(TextField cajaFInicio, TextField cajaFFin, TextField mineral) {

        String inicio = cajaFInicio.getText();
        String fin = cajaFFin.getText();
        String mineCrat = mineral.getText();
        LocalDate fechaI = convertirFecha(inicio);
        LocalDate fechaF = convertirFecha(fin);
        reporFiltra = new FilteredList<>(reportes, r -> true);
        Comparator<ExpReporte> compa;
        compa = new Comparator<ExpReporte>() {
            @Override
            public int compare(ExpReporte ex1, ExpReporte ex2) {
                int resu = 0;
                resu = ex1.getFechaExp().compareTo(ex2.getFechaExp());
                return resu;
            }
        };
        reporFiltra.setPredicate(s
                -> compa.compare(s, new ExpReporte(fechaI, "", "")) >= 0 && compa.compare(s, new ExpReporte(fechaF, "", "")) <= 0 && s.getMinerales().contains(mineCrat)
        );
        tabla.setItems(reporFiltra);
    }

    /**
     * Metodo que genera la tabla de presentar, seteando las propiedades de sus
     * columnas.
     *     
     */
    public void generarTabla() {
        tabla = new TableView();

        //Creamos las columnas y redimensionamos la tabla
        TableColumn<ExpReporte, LocalDate> fechaExp_c = new TableColumn("Fecha Exploracion");
        fechaExp_c.setCellValueFactory(new Callback< TableColumn.CellDataFeatures<ExpReporte, LocalDate>, ObservableValue<LocalDate>>() {
            @Override
            public ObservableValue<LocalDate> call(TableColumn.CellDataFeatures<ExpReporte, LocalDate> param) {
                return (ObservableValue<LocalDate>) param.getValue().FechaExpProperty();
            }
        });

        TableColumn<ExpReporte, String> nombreCrater_c = new TableColumn("Nombre Crater");
        nombreCrater_c.setCellValueFactory(new Callback< TableColumn.CellDataFeatures<ExpReporte, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ExpReporte, String> param) {
                return param.getValue().nombreCProperty();
            }
        });

        TableColumn<ExpReporte, String> mineral_c = new TableColumn("Minerales");

        mineral_c.setCellValueFactory(new Callback< TableColumn.CellDataFeatures<ExpReporte, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ExpReporte, String> param) {
                return param.getValue().MineralesProperty();
            }
        });

        fechaExp_c.setPrefWidth(250);
        nombreCrater_c.setPrefWidth(250);
        mineral_c.setPrefWidth(250);

        double ancho = fechaExp_c.getPrefWidth() + nombreCrater_c.getPrefWidth() + mineral_c.getPrefWidth();
        tabla.setMaxSize(ancho, 800);
        tabla.setEditable(true);

        //Agregamos sus respectivos elementos a sus contenedores correspondientes
        tabla.setItems(reportes);
        tabla.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tabla.getColumns().addAll(fechaExp_c, nombreCrater_c, mineral_c);

        seccionContenido.getChildren().add(tabla);

    }

    public BorderPane getRaiz() {
        return raiz;
    }

}
