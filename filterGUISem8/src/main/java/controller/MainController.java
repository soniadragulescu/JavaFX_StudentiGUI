package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import service.NotaService;
import service.StudentService;
import service.TemaService;

import java.awt.*;
import java.io.IOException;

public class MainController {
    private StudentService serviceS;
    private NotaService serviceN;
    private TemaService serviceT;

    @FXML
    javafx.scene.control.Button butonStudenti;
    @FXML
    javafx.scene.control.Button butonTeme;
    @FXML
    Button butonNote;

    public MainController() {
    }

    @FXML
    public void initialize(){
        butonStudenti.setOnAction(x-> {
            try {
                startStudent();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        butonTeme.setOnAction(x-> {
            try {
                startTema();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        butonNote.setOnAction(x-> {
            try {
                startNota();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void startStudent() throws IOException {
        Stage primaryStage=new Stage();

        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/studentView.fxml"));
        AnchorPane root=loader.load();

        StudentController ctrlS=loader.getController();
        ctrlS.setService(serviceS);

        primaryStage.setScene(new Scene(root, 700, 500));
        primaryStage.setTitle("STUDENTI");
        primaryStage.show();
    }

    @FXML

    public void startTema() throws IOException {
        Stage primaryStage=new Stage();

        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/temaView.fxml"));
        AnchorPane root=loader.load();

        TemaController ctrlT=loader.getController();
        ctrlT.setService(serviceT);

        primaryStage.setScene(new Scene(root, 700, 500));
        primaryStage.setTitle("TEME");
        primaryStage.show();
    }

    @FXML

    public void startNota() throws IOException {
        Stage primaryStage=new Stage();

        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/notaView.fxml"));
        AnchorPane root=loader.load();

        NotaController ctrlN=loader.getController();
        ctrlN.setService(serviceN);

        primaryStage.setScene(new Scene(root, 700, 500));
        primaryStage.setTitle("NOTE");
        primaryStage.show();
    }

    public void setService(StudentService serviceS,  TemaService serviceT,NotaService serviceN) {
        this.serviceS = serviceS;
        this.serviceN = serviceN;
        this.serviceT = serviceT;
    }

}
