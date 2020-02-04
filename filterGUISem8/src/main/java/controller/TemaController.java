package controller;

import domain.StructuraAn;
import domain.Student;
import domain.Tema;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import service.TemaService;

import javax.xml.bind.ValidationException;
import java.util.List;
import java.util.stream.Collectors;

public class TemaController {
    ObservableList<Tema> modelGrade = FXCollections.observableArrayList();

    private TemaService temaService;


    @FXML
    TableColumn<Tema, String> tableColumnDescriere;
    @FXML
    TableColumn<Tema, Integer> tableColumnStartweek;
    @FXML
    TableColumn<Tema, Integer> tableColumnDeadlineweek;


    @FXML
    TableView<Tema> tableViewTema;
    //----------------------end TableView fx:id----------------

    @FXML
    TextField textFieldId;
    @FXML
    TextField textFieldDescriere;

    @FXML
    TextField textFieldDeadlineweek;

    @FXML
    TextField textFieldStartweek;

    @FXML
    Button butonAdauga;
    @FXML
    Button butonSterge;
    @FXML
    Button butonUpdate;

    @FXML
    public void initialize() {
        tableColumnDescriere.setCellValueFactory(new PropertyValueFactory<Tema, String>("descriere"));
        tableColumnStartweek.setCellValueFactory(new PropertyValueFactory<Tema, Integer>("startweek"));
        tableColumnDeadlineweek.setCellValueFactory(new PropertyValueFactory<Tema, Integer>("deadlineweek"));
        tableViewTema.setItems(modelGrade);


        tableViewTema.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tema>() {
            @Override
            public void changed(ObservableValue<? extends Tema> observable, Tema oldValue, Tema newValue) {
                Tema s=newValue;
                textFieldId.setText(""+s.getId());
                textFieldDescriere.setText(""+s.getDescriere());
                textFieldStartweek.setText(""+s.getStartweek().toString());
                textFieldDeadlineweek.setText(""+s.getDeadlineweek().toString());
                //showStudentsDetails(newValue);

            }
        });

        textFieldDescriere.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                handleFilter();
            }
        });

        butonAdauga.setOnAction(x->handleAdauga());
        butonSterge.setOnAction(x->handleSterge());
        butonUpdate.setOnAction(x->handleUpdate());


    }

    private List<Tema> getTemaList() {
        return temaService.findAllHomeworks();
    }

    private void handleAdauga(){
        try{
            String id=textFieldId.getText();
            String descriere=textFieldDescriere.getText();
            Integer startweek=Integer.parseInt(textFieldStartweek.getText());
            Integer deadlineweek=Integer.parseInt(textFieldDeadlineweek.getText());

            Tema s=temaService.adauga(id,descriere,startweek,deadlineweek);
            if(s!=null){
                showErrorMessage("Tema exista deja!");
            }
            clearFields();
        }catch(ValidationException | IllegalArgumentException e){
            showErrorMessage("Incorrect params!");
        }

    }

    private void handleSterge(){
        try{
            String id=textFieldId.getText();

            Tema s=temaService.sterge(id);
            if(s==null){
                showErrorMessage("Tema nu exista!");
            }
            clearFields();
        }catch(IllegalArgumentException e){
            showErrorMessage("Incorrect params!");
        }

    }

    private void handleUpdate(){
        try{
            String id=textFieldId.getText();
            String descriere=textFieldDescriere.getText();
            Integer startweek=Integer.parseInt(textFieldStartweek.getText());
            Integer deadlineweek=Integer.parseInt(textFieldDeadlineweek.getText());

            Tema s=temaService.modifica(id,descriere,startweek,deadlineweek);
            if(s!=null){
                showErrorMessage("Tema nu exista!");
            }
            clearFields();
        }catch(ValidationException | IllegalArgumentException e){
            showErrorMessage("Incorrect params!");
        }

    }
    private static void showErrorMessage(String err){
        Alert message = new Alert(Alert.AlertType.ERROR);
        message.setTitle("Error message!");
        message.setContentText(err);
        message.showAndWait();
    }
    private void handleFilter() {
        List<Tema> list=getTemaList();
        List<Tema> listaFiltrata=list.stream()
                .filter(n->n.getDescriere().startsWith(textFieldDescriere.getText()))
                .collect(Collectors.toList());
        modelGrade.setAll(listaFiltrata);

    }

    private void clearFields(){
        textFieldDescriere.setText("");
        textFieldStartweek.setText("");
        textFieldDeadlineweek.setText("");
    }

    public void setService(TemaService service) {
        this.temaService = service;
        modelGrade.setAll(getTemaList());

    }
}
