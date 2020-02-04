package validators;

import domain.StructuraAn;
import domain.Tema;

import javax.xml.bind.ValidationException;

public class TemaValidator<E extends Tema> implements Validator<E> {
    private StructuraAn structuraAn;
    @Override
    public void validate(Tema entity) throws ValidationException {
        structuraAn=new StructuraAn();
        Integer saptamana=structuraAn.saptamana();
        String msj=new String("");
        if(entity.getId().equals(""))
            msj+="Id-ul nu poate fi vid";
        if(entity.getDescriere().equals(""))
            msj+="Deescrierea nu poate fi vida! ";
        if(entity.getDeadlineweek()<1||entity.getDeadlineweek()>14)
            msj+="Saptamana de sfarsit trebuie sa fie intre 1 si 14! ";
        if(entity.getStartweek()>=entity.getDeadlineweek())
            msj+="Startweek must be < than the deadlineweek! ";
        if(entity.getStartweek()<saptamana)
            msj+="Startweek can't be in the past! ";
        if(msj.length()>0)
            throw new ValidationException(msj);
    }
}
