package service;

import domain.Nota;
import domain.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FilterService {
    protected StudentService serviceS;
    protected TemaService serviceT;
    protected NotaService serviceN;

    public FilterService(StudentService serviceS, TemaService serviceT, NotaService serviceN) {
        this.serviceS = serviceS;
        this.serviceT = serviceT;
        this.serviceN = serviceN;
    }

    public Iterable<Student> getStudentsFromGroup(Integer group) {
        List<Student> result = new ArrayList<Student>();
        serviceS.toate().forEach(result::add);
        return result
                .stream()
                .filter(student -> student.getGroup().equals(group))
                //.map(x -> new Student(x.getId(), x.getName(), x.getGroup(), x.getEmail()))
                .collect(Collectors.toList());
    }

    public Iterable<Student> getStudentWithHomeworkGiven(String homeworkId) {
        List<Student> result = new ArrayList<Student>();
        serviceS.toate().forEach(result::add);
        return result
                .stream()
                .filter(student -> {
                    List<Nota> marks = new ArrayList<Nota>();
                    serviceN.toate().forEach(marks::add);
                    for (Nota mark : marks) {
                        if (mark.getId().getValue().equals(homeworkId) && mark.getId().getKey().equals(student.getId())) {
                            return true;
                        }
                    }
                    return false;
                })
                .collect(Collectors.toList());
    }

    public Iterable<Student> getStudentsWithHomeworkProfGiven(String homeworkId, String prof) {
        List<Student> result = new ArrayList<Student>();
        serviceS.toate().forEach(result::add);
        return result
                .stream()
                .filter(student -> {
                    List<Nota> marks = new ArrayList<Nota>();
                    serviceN.toate().forEach(marks::add);
                    for (Nota mark : marks) {
                        if (mark.getId().getValue().equals(homeworkId) && mark.getId().getKey().equals(student.getId()) && mark.getProfesor().equals(prof)) {
                            return true;
                        }
                    }
                    return false;
                })
                .collect(Collectors.toList());
    }

    public Iterable<Nota> getMarksForHomeworkWeekGiven(String homeworkId, Integer week) {
        List<Nota> result = new ArrayList<Nota>();
        serviceN.toate().forEach(result::add);
        return result
                .stream()
                .filter(mark -> {
                    final boolean b = mark.getId().getValue().equals(homeworkId) && mark.getPredare().equals(week);
                    return b;
                })
                .collect(Collectors.toList());
    }
}

