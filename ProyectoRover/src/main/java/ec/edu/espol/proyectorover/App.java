package ec.edu.espol.proyectorover;

import static Data.CraterData.cargarCrater;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import modelo.Coordenada;
import modelo.Crater;
import modelo.Rover;
import vista.VistaExplorar;

/**
 * JavaFX App
 */
public class App extends Application {

    public static String ruta = "src\\main\\java\\recursos/";
    public static final double DISTANCE_X = 20;
    public static final double DISTANCE_Y = 20;
    private static Scene scene;
    public static Boolean isActive = false;
    public static ArrayList<Crater> listaCrateres = new ArrayList<>();
    public static ArrayList<Rover> rovers = new ArrayList<>();

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("Principal"), 640, 480);
        // scene= new Scene(new VistaExplorar().getRaiz(), 1350, 675);
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(e -> {
            serializar();
            guardarRovers();
            Platform.exit();

            e.consume();

        });

    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        leerRovers();
        deserializar();
        launch();

    }

    private static void deserializar() {
        try (ObjectInputStream st = new ObjectInputStream(new FileInputStream(ruta + "crateres.ser"))) {
            listaCrateres = (ArrayList<Crater>) st.readObject();
            System.out.println("Deserializando...");
        } catch (IOException e) {
            System.out.println("No se encuentra creado el archivo");
            cargarCrater();
        } catch (ClassNotFoundException e) {

            System.out.println(e);
        }
    }

    public static void serializar() {
        try {
            FileOutputStream flujo = new FileOutputStream(ruta + "crateres.ser");
            ObjectOutputStream archivo = new ObjectOutputStream(flujo);
            archivo.writeObject(listaCrateres);
            archivo.close();
            System.out.println("Serialización con exito");
        } catch (IOException e) {
            System.out.println("No se encuentra creado el archivo");
            cargarCrater();
        }
    }

    public static void leerRovers() {
        try (BufferedReader bf = new BufferedReader(new FileReader(App.ruta + "rovers.txt"))) {
            String linea;
            Rover r;
            while ((linea = bf.readLine()) != null) {
                String c[] = linea.split(",");
                Coordenada ubi = new Coordenada(Double.parseDouble(c[1]), Double.parseDouble(c[2]));
                r = new Rover(c[0], ubi, c[3]);
                rovers.add(r);
            }
        } catch (FileNotFoundException ex) {

        } catch (IOException ex) {
            System.err.println(ex);
        }

    }

    public static void guardarRovers() {
        try (BufferedWriter w = new BufferedWriter(new FileWriter(App.ruta + "rovers.txt"))) {
            for (Rover r : rovers) {
                w.write(r.getNombre() + "," + r.getRover().getLayoutX() + "," + r.getRover().getLayoutY()  + ",");
                w.write(r.getTipo() + "\n");
            }
            w.close();
            System.out.println("guarando los rovers...");
        } catch (IOException ex) {

            System.out.println("Excepcion en metodo escribirArchivos" + ex);

        }
    }
}
