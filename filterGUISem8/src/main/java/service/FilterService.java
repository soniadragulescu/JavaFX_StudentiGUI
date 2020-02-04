package service;

import com.sun.org.apache.xpath.internal.operations.Bool;
import domain.Medie;
import domain.Nota;
import domain.Student;
import domain.Tema;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    /* Nota la laborator pentru fiecare student (media ponderată a notelor de la
temele de laborator; pondere tema=nr de săptămâni alocate temei).
*/
    public Iterable<Medie> medie(){
        List<Medie> medii=new ArrayList<>();
        //Map<Student,Double> medii=new HashMap<>();
        List<Student> studenti = new ArrayList<Student>();
        serviceS.toate().forEach(studenti::add);
        List<Tema> teme = new ArrayList<Tema>();
        serviceT.toate().forEach(teme::add);
        List<Nota> note = new ArrayList<Nota>();
        serviceN.toate().forEach(note::add);
        for (Student s:studenti)
        {
            Boolean predat=true;
            Double medie=0.0d;
            Integer nr=0;
            for(Tema t:teme){
                String sid=s.getId();
                String tid=t.getId();
                Pair<String,String> nid=new Pair(sid,tid);
                Nota n=serviceN.gaseste(nid);
                Integer pondere=t.getDeadlineweek()-t.getStartweek();
                if(n==null) {
                    medie += 1 * pondere;
                    predat=false;
                }
                else {
                    medie += pondere * n.getGrade();
                    if(n.getPredare()>t.getDeadlineweek())
                        predat=false;
                }
                nr+=pondere;
            }
            if(nr!=0)
            medie=(Double)medie/nr;
            else medie=1.0d;
            Medie m= new Medie(s,medie);
            m.setPredat(predat);
            medii.add(m);
        }
        return medii;

    }
    /*Cea mai grea tema: media notelor la tema respectivă este cea mai mică.
     */

    public Tema ceaMaiGreaTema(){
        List<Student> studenti = new ArrayList<Student>();
        serviceS.toate().forEach(studenti::add);
        List<Tema> teme = new ArrayList<Tema>();
        serviceT.toate().forEach(teme::add);
        List<Nota> note = new ArrayList<Nota>();
        serviceN.toate().forEach(note::add);
        Integer nrStudenti=studenti.size();
        Double minim=100.0d;
        Tema temaGrea=null;
        for( Tema t:teme){
            Double medie=0.0d;
            for(Student s:studenti){
                String sid=s.getId();
                String tid=t.getId();
                Pair<String,String> nid=new Pair(sid,tid);
                Nota n=serviceN.gaseste(nid);
                if(n!=null)
                    medie+=n.getGrade();
                else medie+=1;
            }
            if(nrStudenti!=0)
            if(minim>medie/nrStudenti)
                minim=medie/nrStudenti;
                temaGrea=t;
        }
        return temaGrea;
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

