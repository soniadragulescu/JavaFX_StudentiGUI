package validators;

import domain.Nota;

import javax.xml.bind.ValidationException;

public class NotaValidator<E extends Nota> implements Validator<E> {
    @Override
    public void validate(E entity) throws ValidationException {
        String msj=new String("");
        if(entity.getGrade()>10||entity.getGrade()<1)
            msj+="Id-ul nu poate fi vid! ";
        if(msj.length()>0)
            throw new ValidationException(msj);
    }
}