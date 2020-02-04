package service;

import domain.Student;
import repositories.XMLStudentFileRepository;
import utils.Observable;
import utils.Observer;
import utils.PossibleOperations;
import utils.StudentEvent;
import validators.StudentValidator;
import validators.ValidatorException;

import javax.xml.bind.ValidationException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StudentService implements Observable {
    private XMLStudentFileRepository fileRepo;
    private StudentValidator validator;
    private List<Observer> observers=new ArrayList<>();
    public StudentService(StudentValidator validator, XMLStudentFileRepository fileRepo) {
        this.validator=validator;
        this.fileRepo=fileRepo;
    }
    public Student adauga(String id, String nume, Integer grupa, String email) throws ValidationException{
        Student s=new Student(id,nume,grupa,email);
        this.validator.validate(s);
        Student val= this.fileRepo.save(s);
        if(val == null) {
            //notifyObservers(new StudentEvent(PossibleOperations.ADD, s));
            notifyObservers();
        }
        return val;
    }
    public Student sterge(String id){
        if(id.equals("")){
            throw new ValidatorException("Id nu poate fi null! ");
        }
        Student val= fileRepo.delete(id);
        notifyObservers();
        //notifyObservers(new StudentEvent(PossibleOperations.DELETE, val));
        return val;
    }
    public Student modifica(String id,String nume, Integer gr, String e) throws ValidationException{
        Student newS=new Student(id,nume,gr,e);
        this.validator.validate(newS);
        Student val= this.fileRepo.update(newS);
        if(val == null) {
            notifyObservers();
            //notifyObservers(new StudentEvent(PossibleOperations.UPDATE, newS));
        }
        return val;
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

    @Override
    public void addObserver(Observer e) {
        observers.add(e);
    }

    @Override
    public void removeObserver(Observer e) {
        observers.remove(e);
    }

    @Override
    public void notifyObservers() {
        observers.forEach(Observer::update);
    }
   /* private List<Observer<StudentEvent>> observers = new ArrayList<>();

    *//***
     * Function for adding an observer
     * @param e: -observer to be added
     *//*
    @Override
    public void addObserver(Observer<StudentEvent> e) {
        this.observers.add(e);
    }

    *//**
     * Function to remove an observer
     * @param e - observer to be removed
     *//*
    @Override
    public void removeObserver(Observer<StudentEvent> e) {
        this.observers.remove(e);
    }

    *//**
     * Function which notifies all observers from the list
     * @param t - item to be updated
     *//*
    @Override
    public void notifyObservers(StudentEvent t) {
        this.observers.forEach(x -> x.update(t));
    }*/

}

