package utils;

/*public interface Observable<E extends Event> {

    void addObserver(Observer<E> e);
    void removeObserver(Observer<E> e);
    void notifyObservers(E t);
}*/

public interface Observable {
    void addObserver(Observer e);
    //void removeObserver(Observer e);
    void notifyObservers();
}
