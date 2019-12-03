package service;

import domain.Nota;
import domain.Student;
import domain.Tema;
import repositories.XMLNotaFileRepository;
import repositories.XMLStudentFileRepository;
import repositories.XMLTemaFileRepository;
import validators.NotaValidator;

import javax.xml.bind.ValidationException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class NotaService {
    private XMLNotaFileRepository fileRepoN;
    private XMLStudentFileRepository fileRepoS;
    private XMLTemaFileRepository fileRepoT;
    private NotaValidator validator;

    public NotaService(NotaValidator validator, XMLNotaFileRepository fileRepoN, XMLStudentFileRepository fileRepoS, XMLTemaFileRepository fileRepoT) {
        this.validator=validator;
        this.fileRepoN=fileRepoN;
        this.fileRepoS=fileRepoS;
        this.fileRepoT=fileRepoT;
    }
    public Nota adauga(String sId,String tId, Integer grade, Integer predare,String profesor, String feedback, Integer saptamana, boolean m) throws ValidationException {

        Student student = fileRepoS.findOne(sId);
        Tema tema = fileRepoT.findOne(tId);
        if (tema == null|| student==null) {
            throw new ValidationException("Nu exista student/tema!");
        }
        Integer intarziere=saptamana-tema.getDeadlineweek();
        if(saptamana>tema.getDeadlineweek()&&!m) {
            if (intarziere <= 2) {
                grade = grade - intarziere;
            } else {
                grade = 1;
            }
        }
        Nota n=new Nota(sId,tId,grade,predare,profesor,feedback);
        this.validator.validate(n);
        return this.fileRepoN.save(n);
    }
    public boolean isDeadlineInThePast(String tId, Integer saptamana){
        Tema t=fileRepoT.findOne(tId);
        return t.getDeadlineweek()<saptamana;
    }
    /*public Nota sterge(String id){
        if(id.equals("")){
            throw new ValidatorException("Id nu poate fi null! ");
        }
        return fileRepo.delete(id);
    }*/
    // public Tema modifica(String line) throws ValidationException;
   /* public Tema gaseste(String id){
        if(id.equals("")){
            throw new ValidatorException("Id nu poate fi null! ");
        }
        return fileRepo.findOne(id);
    }*/
    public Iterable<Nota> toate(){
        return fileRepoN.findAll();
    }

    public List<Nota> findAllGrades(){
        List<Nota> result = new ArrayList<Nota>();
        this.toate().forEach(result::add);
        return result
                .stream()
                .collect(Collectors.toList());
    }
}
