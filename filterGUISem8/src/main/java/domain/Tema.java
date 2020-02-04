package domain;

public class Tema extends Entity<String>{
    private String descriere;
    private Integer startweek, deadlineweek;

    public Tema(String id,String descriere, Integer startweek, Integer deadlineweek) {
        this.setId(id);
        this.descriere = descriere;
        this.startweek = startweek;
        this.deadlineweek = deadlineweek;
    }

    @Override
    public String toString() {
        return descriere + ' ' +
                "startweek:" + startweek +
                " deadlineweek:" + deadlineweek
                ;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public Integer getStartweek() {
        return startweek;
    }

    public void setStartweek(Integer startweek) {
        this.startweek = startweek;
    }

    public Integer getDeadlineweek() {
        return deadlineweek;
    }

    public void setDeadlineweek(Integer deadlineweek) {
        this.deadlineweek = deadlineweek;
    }
}

