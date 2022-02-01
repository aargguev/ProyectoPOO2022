/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import ec.edu.espol.proyectorover.App;
import static ec.edu.espol.proyectorover.App.guardarRovers;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import vista.VistaExplorar;
import vista.VistaPlan;
import vista.VistaReportes;


/**
 * FXML Controller class
 *
 * @author Hollouss
 */
public class PrincipalController implements Initializable {

    @FXML
    private Button exp;
    @FXML
    private Button plan;
    @FXML
    private Button report;
    @FXML
    private Button exit;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        exp.setOnAction((e) -> {
            Stage s = new Stage();      
            if(!App.isActive){
                VistaExplorar view= new VistaExplorar(); 
            Scene sc = new Scene(view.getRaiz(), 1350, 675);
            s.setScene(sc);
            s.show();
            s.setOnCloseRequest(f->{
                App.isActive=false;
                
            });
            }
        });

       report.setOnAction((e) -> {
            Stage st = new Stage();
            Scene sc = new Scene(new VistaReportes().getRaiz(), 751, 600);
            st.setScene(sc);
            st.show();
        });

        plan.setOnAction((e) -> {
            Stage s = new Stage();
            Scene sc = new Scene(new VistaPlan().getRaiz(), 700, 500);
            s.setScene(sc);
            s.show();
        });

        exit.setOnAction((e) -> {
            App.serializar();
            guardarRovers();
            System.exit(0);
        });
    }    

}
