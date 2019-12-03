package repositories;

import domain.Tema;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XMLTemaFileRepository extends XMLFileRepository<String,Tema> {
    public XMLTemaFileRepository(String fileName) {
        super(fileName);
    }

    @Override
    protected Element createElementFromEntity(Document document, Tema tema) {
        Element element = document.createElement("tema");
        element.setAttribute("id", tema.getId());
        Element description = document.createElement("descriere");
        description.setTextContent(tema.getDescriere());
        element.appendChild(description);
        Element startWeek = document.createElement("startweek");
        startWeek.setTextContent(String.valueOf(tema.getStartweek()));
        element.appendChild(startWeek);
        Element deadlineWeek = document.createElement("deadlineweek");
        deadlineWeek.setTextContent(String.valueOf(tema.getDeadlineweek()));
        element.appendChild(deadlineWeek);
        return element;
    }

    @Override
    public Tema createEntityFromElement(Element el) {
        String id = el.getAttribute("id");
        String description = el.getElementsByTagName("descriere").item(0).getTextContent();
        String startWeek = el.getElementsByTagName("startweek").item(0).getTextContent();
        String deadlineWeek = el.getElementsByTagName("deadlineweek").item(0).getTextContent();
        Tema t = new Tema(id, description, Integer.parseInt(startWeek), Integer.parseInt(deadlineWeek));
        return t;
    }
}
