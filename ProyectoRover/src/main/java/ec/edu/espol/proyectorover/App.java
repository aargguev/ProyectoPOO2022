package ec.edu.espol.proyectorover;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import javafx.application.Platform;
import vista.VistaExplorar;

/**
 * JavaFX App
 */
public class App extends Application {
    public static String ruta="src\\main\\java\\recursos/";
    public static final double DISTANCE_X = 20;
    public static final double DISTANCE_Y = 20;
    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
      scene = new Scene(loadFXML("Principal"), 640, 480);
      // scene= new Scene(new VistaExplorar().getRaiz(), 1350, 675);
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(e->{
           // Optional<ButtonType> result = Alerta.confirmation();
            if(true){          
                Platform.exit();
            }else{
                e.consume();
            }});

      
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}