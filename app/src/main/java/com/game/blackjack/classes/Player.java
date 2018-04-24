package com.game.blackjack.classes;

import android.util.Log;
import com.game.blackjack.observer_pattern.Observer;
import com.game.blackjack.observer_pattern.Subject;
import java.util.ArrayList;

public class Player implements Subject{

    // region Attributes of Class
    private int id;
    private String name;
    private int points;
    private ArrayList<Observer> observers = new ArrayList<>();
    // endregion


    // region Constrctors
    public Player() { }

    public Player(String name) { this.name = name; }

    public Player(int id, String name) {
        this.id = id;
        this.name = name;
    }
    // endregion

    //  url repositorio https://github.com/rodinson17/BlackJackFinal.git

    // region Methods Implements of Subject
    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void deleteObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        //Log.i(getClass().getName(), "Notificar suscritor player");
        for (Observer ob: observers){
            ob.update(this);
        }
    }
    //endregion


    // region Getters and Setters
    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public ArrayList<Observer> getObservers() { return observers; }

    public void setObservers(ArrayList<Observer> observers) { this.observers = observers; }

    public int getPoints() { return points; }

    public void setPoints(int points) {
        this.points += points;
        notifyObservers();
    }
    // endregion
}
