package service;

import domain.Tema;
import repositories.XMLTemaFileRepository;
import validators.TemaValidator;
import validators.ValidatorException;

import javax.xml.bind.ValidationException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TemaService {
    private XMLTemaFileRepository fileRepo;
    private TemaValidator validator;
    public TemaService(TemaValidator validator, XMLTemaFileRepository fileRepo) {
        this.validator=validator;
        this.fileRepo=fileRepo;
    }
    public Tema adauga(String id, String descriere, Integer startweek, Integer deadlineweek) throws ValidationException {
        Tema t=new Tema(id,descriere,startweek,deadlineweek);
        this.validator.validate(t);
        return this.fileRepo.save(t);
    }
    public Tema sterge(String id){
        if(id.equals("")){
            throw new ValidatorException("Id nu poate fi null! ");
        }
        return fileRepo.delete(id);
    }

    public Tema modifica(String id,String descriere, Integer s, Integer d) throws ValidationException{
        Tema newT=new Tema(id,descriere,s,d);
        this.validator.validate(newT);
        return this.fileRepo.update(newT);
    }

    public Tema gaseste(String id){
        if(id.equals("")){
            throw new ValidatorException("Id nu poate fi null! ");
        }
        return fileRepo.findOne(id);
    }
    public Iterable<Tema> toate(){
        return fileRepo.findAll();
    }

    public List<Tema> findAllHomeworks(){
        List<Tema> result = new ArrayList<Tema>();
        this.toate().forEach(result::add);
        return result
                .stream()
                .collect(Collectors.toList());
    }

}
