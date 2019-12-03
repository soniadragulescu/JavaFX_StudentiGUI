package repositories;

import domain.Student;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XMLStudentFileRepository extends XMLFileRepository<String, Student> {
    public XMLStudentFileRepository(String fileName) {
        super(fileName);
    }

    @Override
    protected Element createElementFromEntity(Document document, Student student) {
        Element element = document.createElement("student");
        element.setAttribute("id", student.getId().toString());
        Element name = document.createElement("name");
        name.setTextContent(student.getName());
        element.appendChild(name);
        Element grupa = document.createElement("grupa");
        grupa.setTextContent(student.getGroup().toString());
        element.appendChild(grupa);
        Element guidingProf = document.createElement("email");
        guidingProf.setTextContent(String.valueOf(student.getEmail()));
        element.appendChild(guidingProf);
        return element;
    }

    @Override
    public Student createEntityFromElement(Element el) {
        String id = el.getAttribute("id");
        String name = el.getElementsByTagName("name").item(0).getTextContent();
        Integer grupa = Integer.parseInt(el.getElementsByTagName("grupa").item(0).getTextContent());
        String email = el.getElementsByTagName("email").item(0).getTextContent();
        Student s = new Student( id,name,grupa,email);
        return s;
    }

    /*public void saveFeedback(GradeDTO g) throws IOException {
        File file = new File("data/" + g.getName() + ".txt");
        if (!file.exists()){
            file.createNewFile();
        }
        FileWriter writer = new FileWriter(file, true);
        BufferedWriter w = new BufferedWriter(writer);
        w.newLine();
        String a = "Assignment: " + String.valueOf(g.getAssignmentNumber());
        w.write(a);
        w.newLine();
        String grade = "Grade: " + String.valueOf(g.getGrade());
        w.write(grade);
        w.newLine();
        String week = "Week: " + String.valueOf(g.getWeek());
        w.write(week);
        w.newLine();
        String deadline = "Deadline: " + String.valueOf(g.getDeadline());
        w.write(deadline);
        w.newLine();
        String f = "Feedback: " + String.valueOf(g.getFeedback());
        w.write(f);
        w.newLine();
        w.close();
    }*/
}
