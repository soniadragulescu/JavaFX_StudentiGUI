package utils;

//public interface Observer<E extends Event>  {
public interface Observer{
    /**
     * Update function for an observer object
     */
    //void update(E e);
    void update();
}