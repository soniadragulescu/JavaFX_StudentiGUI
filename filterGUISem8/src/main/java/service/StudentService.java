package service;

import domain.Student;
import repositories.XMLStudentFileRepository;
import validators.StudentValidator;
import validators.ValidatorException;

import javax.xml.bind.ValidationException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StudentService {
    private XMLStudentFileRepository fileRepo;
    private StudentValidator validator;
    public StudentService(StudentValidator validator, XMLStudentFileRepository fileRepo) {
        this.validator=validator;
        this.fileRepo=fileRepo;
    }
    public Student adauga(String id, String nume, Integer grupa, String email) throws ValidationException{
        Student s=new Student(id,nume,grupa,email);
        this.validator.validate(s);
        return this.fileRepo.save(s);
    }
    public Student sterge(String id){
        if(id.equals("")){
            throw new ValidatorException("Id nu poate fi null! ");
        }
        return fileRepo.delete(id);
    }
    public Student modifica(String id,String nume, Integer gr, String e) throws ValidationException{
        Student newS=new Student(id,nume,gr,e);
        this.validator.validate(newS);
        return this.fileRepo.update(newS);
    }
    public Student gaseste(String id){
        if(id.equals("")){
            throw new ValidatorException("Id nu poate fi null! ");
        }
        return fileRepo.findOne(id);
    }
    public Iterable<Student> toate(){
        return fileRepo.findAll();
    }

    public List<Student> findAllStudents(){
        List<Student> result = new ArrayList<Student>();
        this.toate().forEach(result::add);
        return result
                .stream()
                .collect(Collectors.toList());
    }

}
