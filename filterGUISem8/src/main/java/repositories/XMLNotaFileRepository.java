package repositories;

import domain.Nota;
import javafx.util.Pair;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XMLNotaFileRepository extends XMLFileRepository<Pair<String,String>, Nota> {
    public XMLNotaFileRepository(String fileName) {
        super(fileName);
    }

    @Override
    protected Element createElementFromEntity(Document document, Nota nota) {
        Element element = document.createElement("nota");
        Element idStudent = document.createElement("idStudent");
        idStudent.setTextContent(nota.getId().getKey());
        element.appendChild(idStudent);
        Element idAssignment = document.createElement("idTema");
        idAssignment.setTextContent(nota.getId().getValue());
        element.appendChild(idAssignment);
        /*Element date = document.createElement("date");
        date.setTextContent(String.valueOf(nota.getData()));
        element.appendChild(date);*/
        Element grade = document.createElement("grade");
        grade.setTextContent(String.valueOf(nota.getGrade()));
        element.appendChild(grade);
        Element predare = document.createElement("predare");
        predare.setTextContent(String.valueOf(nota.getPredare()));
        element.appendChild(predare);
        Element profesor = document.createElement("profesor");
        profesor.setTextContent(String.valueOf(nota.getProfesor()));
        element.appendChild(profesor);
        Element feedback = document.createElement("feedback");
        feedback.setTextContent(String.valueOf(nota.getFeedback()));
        element.appendChild(feedback);
        return element;
    }

    @Override
    public Nota createEntityFromElement(Element el) {
        String idStudent = el.getElementsByTagName("idStudent").item(0).getTextContent();
        String idTema = el.getElementsByTagName("idTema").item(0).getTextContent();
        //String d = el.getElementsByTagName("date").item(0).getTextContent();
        //LocalDate date = LocalDate.parse(d, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        Integer value = Integer.parseInt(el.getElementsByTagName("grade").item(0).getTextContent());
        Integer predare = Integer.parseInt(el.getElementsByTagName("predare").item(0).getTextContent());
        String profesor = el.getElementsByTagName("profesor").item(0).getTextContent();
        String feedback = el.getElementsByTagName("feedback").item(0).getTextContent();
        Nota n = new Nota(idStudent,idTema,value,predare,profesor,feedback);
        return n;
    }
}
