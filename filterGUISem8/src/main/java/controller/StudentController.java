package controller;

import domain.Student;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import service.ServiceManager;
import service.StudentService;
import utils.Observer;
import utils.StudentEvent;

import javax.xml.bind.ValidationException;
import java.util.List;
import java.util.Observable;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import utils.StudentEvent;

public class StudentController implements Observer{
    private ObservableList<Student> modelGrade = FXCollections.observableArrayList();
    private StudentService studentService;


    @FXML
    TableColumn<Student, String> tableColumnName;
    @FXML
    TableColumn<Student, Integer> tableColumnGrupa;
    @FXML
    TableColumn<Student, String> tableColumnEmail;


    @FXML
    TableView<Student> tableViewStudenti;
    //----------------------end TableView fx:id----------------

    @FXML
    TextField textFieldId;
    @FXML
    TextField textFieldName;

    @FXML
    TextField textFieldGrupa;

    @FXML
    TextField textFieldEmail;

    @FXML
    Button butonAdauga;
    @FXML
    Button butonSterge;
    @FXML
    Button butonUpdate;

    @FXML
    public void initialize() {
        tableColumnName.setCellValueFactory(new PropertyValueFactory<Student, String>("name"));
        tableColumnGrupa.setCellValueFactory(new PropertyValueFactory<Student, Integer>("group"));
        tableColumnEmail.setCellValueFactory(new PropertyValueFactory<Student, String>("email"));
        tableViewStudenti.setItems(modelGrade);
        //tableViewStudenti.setItems(getStudentList());

        this.tableViewStudenti.getSelectionModel().selectedItemProperty().addListener(
                (observable,oldValue,newValue)->{

                        Student s=newValue;
                        textFieldId.setText("" + s.getId());
                        textFieldName.setText("" + s.getName());
                        textFieldGrupa.setText("" + s.getGroup().toString());
                        textFieldEmail.setText("" + s.getEmail());


                });


        /*tableViewStudenti.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Student>() {
            @Override
            public void changed(ObservableValue<? extends Student> observable, Student oldValue, Student newValue) {
                //Student s=tableViewStudenti.getSelectionModel().getSelectedItem();
                //Student s=newValue;
                if(newValue==null)
                    clearFields();
                else {
                    Student s=newValue;
                    textFieldId.setText("" + s.getId());
                    textFieldName.setText("" + s.getName());
                    textFieldGrupa.setText("" + s.getGroup().toString());
                    textFieldEmail.setText("" + s.getEmail());
                }
                //showStudentsDetails(newValue);

            }
        });*/

        textFieldName.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                handleFilter();
            }
        });

        butonAdauga.setOnAction(x->handleAdauga());
        butonSterge.setOnAction(x->handleSterge());
        butonUpdate.setOnAction(x->handleUpdate());


    }

    //private List<Student> getStudentList() {return studentService.findAllStudents();}
    private void populateList(){
        Iterable<Student> students = this.studentService.toate();
        students.forEach(x->this.modelGrade.add(x));
    }

    private ObservableList<Student> getStudentList() {

        //return studentService.findAllStudents();
        return this.modelGrade;
    }

    private void handleAdauga(){
        try{
            String id=textFieldId.getText();
            String name=textFieldName.getText();
            Integer gr=Integer.parseInt(textFieldGrupa.getText());
            String email=textFieldEmail.getText();

            Student s=studentService.adauga(id,name,gr,email);
            if(s!=null){
                showErrorMessage("Studentul exista deja!");
            }
            clearFields();
        }catch(ValidationException | IllegalArgumentException e){
            showErrorMessage("Incorrect params!");
        }

    }

    private void handleSterge(){
        try{
            String id=textFieldId.getText();

            Student s=studentService.sterge(id);
            if(s==null){
                showErrorMessage("Studentul nu exista!");
            }
            clearFields();
        }catch(IllegalArgumentException e){
            showErrorMessage("Incorrect params!");
        }

    }

    private void handleUpdate(){
        try{
            String id=textFieldId.getText();
            String name=textFieldName.getText();
            Integer gr=Integer.parseInt(textFieldGrupa.getText());
            String email=textFieldEmail.getText();

            Student s=studentService.modifica(id,name,gr,email);
            if(s!=null){
                showErrorMessage("Studentul nu exista!");
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
            List<Student> list = getStudentList();
            List<Student> listaFiltrata = list.stream()
                    .filter(n -> n.getName().startsWith(textFieldName.getText()))
                    .collect(Collectors.toList());
        ObservableList<Student> students = FXCollections.observableArrayList(listaFiltrata);
        tableViewStudenti.setItems(students);
        //modelGrade.setAll(listaFiltrata);
    }

    private void clearFields(){
        textFieldId.setText("");
        textFieldName.setText("");
        textFieldGrupa.setText("");
        textFieldEmail.setText("");
    }
    private void showStudentDetails(Student s){
        if(s==null)
            clearFields();
        else
            textFieldId.setText((""+s.getId()));
            textFieldName.setText(""+s.getName());
            textFieldGrupa.setText(""+s.getGroup().toString());
            textFieldEmail.setText(""+s.getEmail());
    }

    public void setService(StudentService service) {
        this.studentService = service;
        studentService.addObserver(this);
        //modelGrade.setAll(getStudentList());
        initModel();
        /*
        tableViewStudenti.getSelectionModel().selectedItemProperty().addListener((
                (observable, oldValue, newValue) -> {
                    if(newValue!=null)
                        setSelectedItem(newValue);
                }));*/
        //modelGrade.setAll(getStudentList());
       // populateList();

    }
    private void setSelectedItem(Student s){
        textFieldId.setText(s.getId().toString());
        textFieldName.setText(s.getName());
        textFieldGrupa.setText(s.getGroup().toString());
        textFieldEmail.setText(s.getEmail());
    }

    private void initModel(){
        List<Student> list = StreamSupport.stream(this.studentService.toate().spliterator(),false)
                .collect(Collectors.toList());
        modelGrade.setAll(list);
    }

    @Override
    public void update() {
        modelGrade.setAll(StreamSupport.stream(studentService.toate().spliterator(),false)
                .collect(Collectors.toList()));
    }
}
