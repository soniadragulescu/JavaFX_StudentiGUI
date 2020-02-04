package controller;

import domain.*;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;
import service.FilterService;
import service.NotaService;

import javax.xml.bind.ValidationException;
//import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class NotaController {

    ObservableList<Nota> modelGrade = FXCollections.observableArrayList();

    private NotaService notaService;
    private FilterService filterService;
    private String informatii;

    @FXML
    TableColumn<Nota,String>tableColumnDespre;
    @FXML
    TableColumn<Nota, Integer> tableColumnGrade;
    @FXML
    TableColumn<Nota, Integer> tableColumnPredare;
    @FXML
    TableColumn<Nota, String> tableColumnProfesor;
    @FXML
    TableColumn<Nota, String> tableColumnFeedback;

    @FXML
    ComboBox<Student> comboStudent;

    public NotaService getNotaService() {
        return notaService;
    }

    @FXML
    ComboBox<Tema> comboTema;
    @FXML
    CheckBox checkboxMotivat;
    @FXML
    ComboBox<Integer> comboSaptamana;

    @FXML
    TableView<Nota> tableViewNota;
    //----------------------end TableView fx:id----------------

    @FXML
    TextField textFieldGrade;

    @FXML
    TextField textFieldProfesor;
    @FXML
    TextField textFieldFeedback;

    @FXML
    Button butonAdauga;

    @FXML
    Button butonMedie;

    @FXML
    public void initialize() {
        tableColumnGrade.setCellValueFactory(new PropertyValueFactory<Nota, Integer>("grade"));
        tableColumnPredare.setCellValueFactory(new PropertyValueFactory<Nota, Integer>("predare"));
        tableColumnProfesor.setCellValueFactory(new PropertyValueFactory<Nota, String>("profesor"));
        tableColumnFeedback.setCellValueFactory(new PropertyValueFactory<Nota, String>("feedback"));
        tableViewNota.setItems(modelGrade);
        //tableColumnDespre.setCellValueFactory(c->new SimpleStringProperty(c.getValue().getSId()+","+c.getValue().getTId()));

        butonAdauga.setOnAction(x->createNewWindow(informatii()));
        butonMedie.setOnAction(x->handleMedii());

    }

    private List<Nota> getNotaList() {
        return notaService.findAllGrades();
    }

    private List<Student> getStudentList() {
        return notaService.findAllStudents();
    }

    public String informatii(){
        Tema tema=comboTema.getSelectionModel().getSelectedItem();
        Integer grade=Integer.parseInt(textFieldGrade.getText());
        Integer saptamana=comboSaptamana.getValue();
        boolean motivat=checkboxMotivat.isSelected();
        Integer intarziere=saptamana-tema.getDeadlineweek();
        if(saptamana>tema.getDeadlineweek()&&!motivat) {
            if (intarziere <= 2) {
                grade = grade - intarziere;
            } else {
                grade = 1;
            }
        }

        String informatii="Nota pt. aceasta tema este "+grade.toString()+" deoarece studentul are o intarziere de "+intarziere+" saptamani ";
        if(motivat)
            informatii+=" si este motivat.";
        else informatii+=" nu este motivat.";
        return informatii;
    }
   private void handleAdauga(){
        try{
            String[] student=comboStudent.getEditor().getText().split(" ");
            String sId=student[0];
            String tId=comboTema.getSelectionModel().getSelectedItem().getId();
            Integer grade=Integer.parseInt(textFieldGrade.getText());
            Integer saptamana=comboSaptamana.getValue();
            String profesor=textFieldProfesor.getText();
            String feedback=textFieldFeedback.getText()+" "+informatii();
            boolean motivat=checkboxMotivat.isSelected();

            Nota n=notaService.adauga(sId,tId,grade,saptamana,profesor,feedback,motivat);
            modelGrade.setAll(getNotaList());
            tableViewNota.setItems(modelGrade);
            if(n == null) {
                showErrorMessage("Nota adaugata cu succes!");
            }
            else{
                showErrorMessage("exista deja nota!");
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

    private void clearFields(){
        textFieldProfesor.setText("");
        textFieldGrade.setText("");
    }

   public void setCombobox(){
       StructuraAn an=new StructuraAn();
       Integer sapt=an.saptamana();
       List<Student> listS=this.getStudentList();
       ObservableList<Student> obsListS= FXCollections.observableList(listS);
       comboStudent.setEditable(true);
       comboStudent.getEditor().setOnKeyTyped(x->{
           String text=comboStudent.getEditor().getText();
           List<Student> studenti=new ArrayList<>();
           List<Student> toti=getStudentList();
           if(text=="")
               studenti=toti;
           else{
           for(Student s:toti) {
               String nume = s.getName();
               if (nume.startsWith(text))
                   studenti.add(s);
                }
             }
           ObservableList<Student> list=FXCollections.observableList(studenti);
           comboStudent.setItems(list);
           comboStudent.show();
       });
       comboStudent.setItems(obsListS);

       List<Tema> listT=this.getNotaService().findAllTeme();
       ObservableList<Tema> obsListT= FXCollections.observableList(listT);
       comboTema.setItems(obsListT);
       Tema tema;
       for(Tema t :listT) {
           if (t.getStartweek().equals(sapt)) {
               tema = t;
               comboTema.getSelectionModel().select(tema);
           }
       }



       List<Integer> list = new ArrayList<Integer>();
       for(Integer i=1; i<=14; i++)
            list.add(i);
       ObservableList<Integer> obsList= FXCollections.observableList(list);
       comboSaptamana.setItems(obsList);
   }

    public void createNewWindow(String infos){
        HBox root = new HBox(5);
        root.setPadding(new Insets(40));
        root.setAlignment(Pos.BASELINE_RIGHT);
        Button okBtn = new Button("OK");
        Button cancelBtn = new Button("Cancel");
        TextArea textFieldCheck = new TextArea(infos);
        textFieldCheck.setEditable(false);
        root.getChildren().addAll(textFieldCheck, okBtn, cancelBtn);

        Scene scene = new Scene(root, 700, 200);
        Stage stage = new Stage();
        stage.setTitle("CONFIRMARE ADAUGARE NOTA");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();

        cancelBtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                stage.close();
            }
        });

        okBtn.setOnAction(x->handleAdauga());
    }

    public void handleMedii(){
        VBox root = new VBox(20);
        root.setPadding(new Insets(10));
        TableView<Medie> medii=new TableView<>();
        medii.prefWidthProperty().bind(root.widthProperty());
        Label temaGrea=new Label("Cea mai grea tema: "+filterService.ceaMaiGreaTema().toString());
        TableColumn<Medie, String> tableColumnName=new TableColumn<>("Student");
        TableColumn<Medie, String>tableColumnMedie=new TableColumn<>("Medie");
        TableColumn<Medie, Boolean>tableColumnExamen=new TableColumn<>("Poate intra in examen?");
        TableColumn<Medie,Boolean>tableColumnPredare=new TableColumn<>("Predat la timp temele?");

        tableColumnName.setCellValueFactory(c->new SimpleStringProperty(c.getValue().getStudent().getName()));
        tableColumnMedie.setCellValueFactory(c->new SimpleStringProperty(c.getValue().getMedie().toString()));
        tableColumnExamen.setCellValueFactory(c->new SimpleBooleanProperty(c.getValue().getExamen()));
        tableColumnPredare.setCellValueFactory(c->new SimpleBooleanProperty(c.getValue().getPredat()));
        List<Medie> lista2=new ArrayList<>();
        Iterable<Medie> toate=filterService.medie();
        for(Medie m:toate)
            lista2.add(m);
        ObservableList<Medie> lista=FXCollections.observableArrayList();
        lista.setAll(lista2);
        medii.setItems(lista);
        medii.getColumns().addAll(tableColumnName,tableColumnMedie,tableColumnExamen,tableColumnPredare);
        root.getChildren().addAll(temaGrea,medii);

        Scene scene = new Scene(root, 800, 500);
        Stage stage = new Stage();
        stage.setTitle("Filtrari");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    public void setService(NotaService service, FilterService filterService) {
        this.notaService = service;
        this.filterService=filterService;
        modelGrade.setAll(getNotaList());
        setCombobox();
        //this.informatii=informatii();
    }
}
