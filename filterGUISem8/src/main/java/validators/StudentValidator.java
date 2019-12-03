package validators;

import domain.Student;

import javax.xml.bind.ValidationException;

public class StudentValidator<E extends Student> implements Validator<E>{
    @Override
    public void validate(Student entity) throws ValidationException {
        String msj=new String("");
        if(entity.getId().equals(""))
            msj+="Id-ul nu poate fi vid! ";
        if(entity.getName().equals(""))
            msj="Numele nu poate fi vid! ";
        if(entity.getGroup()<1)
            msj+="Grupa trebuie sa fie un numar natural nenul! ";
        if(entity.getEmail().equals(""))
            msj+="Emailul nu poate fi vid! ";
        if(msj.length()>0)
            throw new ValidationException(msj);
    }
}
