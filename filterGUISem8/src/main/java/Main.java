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
import service.*;
import validators.NotaValidator;
import validators.StudentValidator;
import validators.TemaValidator;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        StudentValidator studentValidator=new StudentValidator();
       // XMLStudentFileRepository studentFileRepo=new XMLStudentFileRepository("C:\\Users\\sonia\\IdeaProjects\\MAP_lab7GUI\\filterGUISem8\\src\\main\\java\\data\\studenti.xml");
        String fileS = ApplicationContext.getPROPERTIES().getProperty("data.students");
        XMLStudentFileRepository studentFileRepo=new XMLStudentFileRepository(fileS);
        StudentService studentService=new StudentService(studentValidator,studentFileRepo);

        String fileT = ApplicationContext.getPROPERTIES().getProperty("data.homeworks");
        TemaValidator temaValidator=new TemaValidator<>();
        //XMLTemaFileRepository temaFileRepo=new XMLTemaFileRepository("C:\\Users\\sonia\\IdeaProjects\\MAP_lab7GUI\\filterGUISem8\\src\\main\\java\\data\\teme.xml");
        XMLTemaFileRepository temaFileRepo=new XMLTemaFileRepository(fileT);
        TemaService temaService=new TemaService(temaValidator,temaFileRepo);

        String fileN = ApplicationContext.getPROPERTIES().getProperty("data.grades");
        NotaValidator notaValidator=new NotaValidator();
        //XMLNotaFileRepository notaFileRepo=new XMLNotaFileRepository("C:\\Users\\sonia\\IdeaProjects\\MAP_lab7GUI\\filterGUISem8\\src\\main\\java\\data\\note.xml");
        XMLNotaFileRepository notaFileRepo=new XMLNotaFileRepository(fileN);
        NotaService notaService=new NotaService(notaValidator,notaFileRepo, studentFileRepo,temaFileRepo);

        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/mainView.fxml"));
        AnchorPane root=loader.load();

        FilterService serviceFiltrari=new FilterService(studentService,temaService,notaService);

        MainController ctrl=loader.getController();
        ctrl.setService(studentService,temaService,notaService,serviceFiltrari);

        primaryStage.setScene(new Scene(root, 700, 500));
        primaryStage.setTitle("CATALOG");
        //serviceFiltrari.medie().forEach((x,y)-> System.out.println(x.toString()+' '+y.toString()));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
