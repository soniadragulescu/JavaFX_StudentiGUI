package domain;

import javafx.util.Pair;

public class Nota extends Entity<Pair<String,String>> {
    private Integer grade;
    private Integer predare;
    private String profesor;
    private String feedback;
    public String getProfesor() {
        return profesor;
    }

    @Override
    public String toString() {
        return "Nota{ (" +this.getId().getKey()+","+this.getId().getValue()+" )"+
                "grade=" + grade +
                ", predare= "+ predare+
                ", profesor='" + profesor + '\'' +
                ", feedback='" + feedback + '\'' +
                '}';
    }

    public Integer getPredare() {
        return predare;
    }

    public void setPredare(Integer predare) {
        this.predare = predare;
    }

    public void setProfesor(String profesor) {
        this.profesor = profesor;
    }



    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public Nota(String sId, String tId, Integer grade, Integer predare,String profesor,String feedback) {
        this.setId(new Pair(sId,tId));
        this.grade = grade;
        this.predare=predare;
        //this.data = data;
        this.profesor=profesor;
        this.feedback=feedback;
    }

    public String getSId(){
        return this.getId().getKey();
    }

    public String getTId(){
        return this.getId().getValue();
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

}
