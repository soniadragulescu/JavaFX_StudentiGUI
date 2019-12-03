package validators;

import domain.Tema;

import javax.xml.bind.ValidationException;

public class TemaValidator<E extends Tema> implements Validator<E> {
    @Override
    public void validate(Tema entity) throws ValidationException {
        String msj=new String("");
        if(entity.getId().equals(""))
            msj+="Id-ul nu poate fi vid";
        if(entity.getDescriere().equals(""))
            msj+="Deescrierea nu poate fi vida! ";
        if(entity.getDeadlineweek()<1||entity.getDeadlineweek()>14)
            msj+="Saptamana de sfarsit trebuie sa fie intre 1 si 14! ";
        if(entity.getStartweek()>=entity.getDeadlineweek())
            msj+="Startweek must be < than the deadlineweek! ";
        if(msj.length()>0)
            throw new ValidationException(msj);
    }
}
