package com.game.blackjack.observer_pattern;

public interface Subject {

    void addObserver(Observer observer);

    void deleteObserver(Observer observer);

    void notifyObservers();

}
