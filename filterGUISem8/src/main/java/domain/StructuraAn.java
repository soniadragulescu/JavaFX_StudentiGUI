package domain;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.Locale;

public class StructuraAn extends Entity<Long> {
    private Integer an, sem;
    private String inceputSem1, sfSem1,inceputVIarna,sfVIarna, inceputSem2,inceputVPaste, sfVPaste, sfSem2;
    private LocalDate data;

    public StructuraAn() {
        this.data=LocalDate.now();
        this.an = data.getYear();
        inceputSem1="30-09-2019";
        inceputVIarna="21-12-2019";
        sfVIarna="05-01-2020";
        sfSem1="18-01-2020";
        inceputSem2="24-02-2020";
        inceputVPaste="18-04-2020";
        sfVPaste="26-04-2020";
        sfSem2="06-06-2020";
        if(data.isAfter(LocalDate.parse(sfSem1, DateTimeFormatter.ofPattern("dd-MM-yyyy"))))
            sem=2;
        else
            sem=1;
    }

    public Integer getSem() {
        return sem;
    }

    public int saptamana(){
        int s=0;
        int curent = data.get(WeekFields.of(Locale.FRANCE).weekOfWeekBasedYear());
        int inceputS1 = LocalDate.parse(inceputSem1, DateTimeFormatter.ofPattern("dd-MM-yyyy")).get(WeekFields.of(Locale.FRANCE).weekOfWeekBasedYear());
        int inceputVI = LocalDate.parse(inceputVIarna, DateTimeFormatter.ofPattern("dd-MM-yyyy")).get(WeekFields.of(Locale.FRANCE).weekOfWeekBasedYear());
        int sfVI = LocalDate.parse(sfVIarna, DateTimeFormatter.ofPattern("dd-MM-yyyy")).get(WeekFields.of(Locale.FRANCE).weekOfWeekBasedYear());
        int sfS1=LocalDate.parse(sfSem1, DateTimeFormatter.ofPattern("dd-MM-yyyy")).get(WeekFields.of(Locale.FRANCE).weekOfWeekBasedYear());
        int inceputS2=LocalDate.parse(inceputSem2, DateTimeFormatter.ofPattern("dd-MM-yyyy")).get(WeekFields.of(Locale.FRANCE).weekOfWeekBasedYear());
        int inceputVP=LocalDate.parse(inceputVPaste, DateTimeFormatter.ofPattern("dd-MM-yyyy")).get(WeekFields.of(Locale.FRANCE).weekOfWeekBasedYear());
        int sfVP=LocalDate.parse(sfVPaste, DateTimeFormatter.ofPattern("dd-MM-yyyy")).get(WeekFields.of(Locale.FRANCE).weekOfWeekBasedYear());
        int sfS2=LocalDate.parse(sfSem2, DateTimeFormatter.ofPattern("dd-MM-yyyy")).get(WeekFields.of(Locale.FRANCE).weekOfWeekBasedYear());

        if(sem==1) {
            s = curent - inceputS1 + 1;
            if (curent > inceputVI)
                s = s + sfVI - inceputVI;
        }
        else {
            s=curent-inceputS2;
            if(curent>inceputVP)
                s=s+sfVP-inceputVP;
        }
        return s;
    }
}
