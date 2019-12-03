package validators;

import javax.xml.bind.ValidationException;
/**
 * validates an entity
 * @param <E> - type of entities saved in repository
 */
public interface Validator<E> {
    /**
     *
     * @param entity the object to be validated
     * entity must not be null
     * @throws ValidationException
     */
    void validate(E entity) throws ValidationException;
}
