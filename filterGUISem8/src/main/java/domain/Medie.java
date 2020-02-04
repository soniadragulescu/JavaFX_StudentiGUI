package domain;

import java.text.DecimalFormat;

public class Medie {
    private Student student;
    private Double medie;
    private Boolean examen;
    private Boolean  predat;

    public Boolean getPredat() {
        return predat;
    }

    public void setPredat(Boolean predat) {
        this.predat = predat;
    }

    public Boolean getExamen() {
        return examen;
    }

    public void setExamen(Boolean examen) {
        this.examen = examen;
    }

    public Medie(Student student, Double medie) {
        DecimalFormat df2=new DecimalFormat("#.##");
        this.student = student;
        this.medie = Double.valueOf(df2.format(medie));
        if(medie>=5)
            examen=true;
        else
            examen=false;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Double getMedie() {
        return medie;
    }

    public void setMedie(Double medie) {
        this.medie = medie;
    }
}
