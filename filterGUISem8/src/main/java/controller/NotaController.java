package controller;

import domain.Nota;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import service.NotaService;

import javax.xml.bind.ValidationException;
import java.util.List;
import java.util.stream.Collectors;

public class NotaController {

    ObservableList<Nota> modelGrade = FXCollections.observableArrayList();

    private NotaService notaService;

    @FXML
    TableColumn<Nota, Integer> tableColumnGrade;
    @FXML
    TableColumn<Nota, Integer> tableColumnPredare;
    @FXML
    TableColumn<Nota, String> tableColumnProfesor;
    @FXML
    TableColumn<Nota, String> tableColumnFeedback;

    @FXML
    TableView<Nota> tableViewNota;
    @FXML
    TableView<Nota> tableViewStudent;
    @FXML
    TableView<Nota> tableViewTema;
    //----------------------end TableView fx:id----------------

    @FXML
    TextField textFieldId;
    @FXML
    TextField textFieldGrade;

    @FXML
    TextField textFieldPredare;

    @FXML
    TextField textFieldProfesor;
    @FXML
    TextField textFieldFeedback;

    @FXML
    Button butonAdauga;

    @FXML
    public void initialize() {
        tableColumnGrade.setCellValueFactory(new PropertyValueFactory<Nota, Integer>("grade"));
        tableColumnPredare.setCellValueFactory(new PropertyValueFactory<Nota, Integer>("predare"));
        tableColumnProfesor.setCellValueFactory(new PropertyValueFactory<Nota, String>("profesor"));
        tableColumnFeedback.setCellValueFactory(new PropertyValueFactory<Nota, String>("feedback"));
        tableViewNota.setItems(modelGrade);


        /*tableViewTema.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tema>() {
            @Override
            public void changed(ObservableValue<? extends Tema> observable, Tema oldValue, Tema newValue) {
                //Student s=tableViewStudenti.getSelectionModel().getSelectedItem();
                Tema s=newValue;
                textFieldId.setText(""+s.getId());
                textFieldDescriere.setText(""+s.getDescriere());
                textFieldStartweek.setText(""+s.getStartweek().toString());
                textFieldDeadlineweek.setText(""+s.getDeadlineweek().toString());
                //showStudentsDetails(newValue);

            }
        });*/

       /* textFieldDescriere.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                handleFilter();
            }
        });*/

       // butonAdauga.setOnAction(x->handleAdauga());


    }

    private List<Nota> getNotaList() {
        return notaService.findAllGrades();
    }

   /* private void handleAdauga(){
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

    }*/


    private static void showErrorMessage(String err){
        Alert message = new Alert(Alert.AlertType.ERROR);
        message.setTitle("Error message!");
        message.setContentText(err);
        message.showAndWait();
    }
   /* private void handleFilter() {
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
    }*/

    public void setService(NotaService service) {
        this.notaService = service;
        modelGrade.setAll(getNotaList());

    }
}
