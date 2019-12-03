package service;

import domain.Student;
import java.util.Arrays;
import java.util.List;

public class ServiceManager {

    public  List<Student> findAllStudents() {
        Student s1=new Student("1","Sonja",223,"dsir2504@scs.ubbcluj.ro");
        Student s2 = new Student("2", "Dan",224,"rdir@scs.ubbcluj.ro");
        Student s3 = new Student("3", "Vald",223,"vgsir@scs.ubbcluj.ro");
        Student s4 = new Student("4", "Mara",221,"dmir@scs.ubbcluj.ro");
        return Arrays.asList(s1, s2, s3, s4);
    }


}
