import controller.MainController;
import controller.StudentController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import repositories.XMLFileRepository;
import repositories.XMLNotaFileRepository;
import repositories.XMLStudentFileRepository;
import repositories.XMLTemaFileRepository;
import service.NotaService;
import service.StudentService;
import service.TemaService;
import validators.NotaValidator;
import validators.StudentValidator;
import validators.TemaValidator;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        /*FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/studentView.fxml"));
        AnchorPane root=loader.load();

        StudentValidator studentValidator=new StudentValidator();
        XMLStudentFileRepository studentFileRepo=new XMLStudentFileRepository("C:\\Users\\sonia\\IdeaProjects\\MAP_lab7GUI\\filterGUISem8\\src\\main\\java\\data\\studenti.xml");
        StudentService studentService=new StudentService(studentValidator,studentFileRepo);

        StudentController ctrlS=loader.getController();
        ctrlS.setService(studentService);

        primaryStage.setScene(new Scene(root, 700, 500));
        primaryStage.setTitle("STUDENTI");
        primaryStage.show();*/

        StudentValidator studentValidator=new StudentValidator();
        XMLStudentFileRepository studentFileRepo=new XMLStudentFileRepository("C:\\Users\\sonia\\IdeaProjects\\MAP_lab7GUI\\filterGUISem8\\src\\main\\java\\data\\studenti.xml");
        StudentService studentService=new StudentService(studentValidator,studentFileRepo);

        TemaValidator temaValidator=new TemaValidator<>();
        XMLTemaFileRepository temaFileRepo=new XMLTemaFileRepository("C:\\Users\\sonia\\IdeaProjects\\MAP_lab7GUI\\filterGUISem8\\src\\main\\java\\data\\teme.xml");
        TemaService temaService=new TemaService(temaValidator,temaFileRepo);

        NotaValidator notaValidator=new NotaValidator();
        XMLNotaFileRepository notaFileRepo=new XMLNotaFileRepository("C:\\Users\\sonia\\IdeaProjects\\MAP_lab7GUI\\filterGUISem8\\src\\main\\java\\data\\note.xml");
        NotaService notaService=new NotaService(notaValidator,notaFileRepo, studentFileRepo,temaFileRepo);

        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/mainView.fxml"));
        AnchorPane root=loader.load();

        MainController ctrl=loader.getController();
        ctrl.setService(studentService,temaService,notaService);

        primaryStage.setScene(new Scene(root, 700, 500));
        primaryStage.setTitle("CATALOG");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
