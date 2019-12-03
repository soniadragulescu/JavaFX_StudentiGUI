package repositories;

import domain.Entity;

import java.util.HashMap;
import java.util.Map;

public class InMemoryRepository<ID, E extends Entity<ID>> implements CrudRepository<ID,E> {
    protected Map<ID,E> entities;

    public InMemoryRepository(){
        this.entities=new HashMap<>();
    }

    @Override
    public E findOne(ID id) {
        if(id==null)
            throw new IllegalArgumentException(("The id must not be null!"));
        for(Map.Entry<ID,E> pereche:entities.entrySet()){
            if(pereche.getKey().equals(id))
                return pereche.getValue();
        }
        return null;
    }

    @Override
    public Iterable<E> findAll() {
        return entities.values();
    }

    @Override
    public E save(E entity)  {
        if(entity==null)
            throw new IllegalArgumentException("Entity must not be null!");
        E oldValue=entities.get(entity.getId());
        if(oldValue==null){
            entities.put(entity.getId(),entity);
            return null;
        }
        return entity;
    }

    @Override
    public E delete(ID id) {

        if(id==null){
            throw new IllegalArgumentException("The id must not be null!");
        }
        E entity=findOne(id);
        entities.remove(id, entity);
        return entity;

    }

    @Override
    public E update(E entity) {
        if(entity==null)
            throw new IllegalArgumentException("The entity must not be null!");
        E oldEntity=findOne(entity.getId());
        if(oldEntity==null)
            return entity;
        entities.replace(entity.getId(),entity);
        return null;
    }
}
