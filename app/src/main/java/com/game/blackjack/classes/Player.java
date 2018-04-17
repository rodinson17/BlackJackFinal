package com.game.blackjack.classes;

import com.game.blackjack.observer_pattern.Observer;
import com.game.blackjack.observer_pattern.Subject;
import java.util.ArrayList;

public class Player implements Subject{


    // region Attributes of Class

    private String nombre;
    private int points;
    private ArrayList<Observer> observers = new ArrayList<>();

    // endregion



    // region Constrctors

    public Player() { }

    public Player(String nombre) {
        this.nombre = nombre;
    }

    public Player(String nombre, int point) {
        this.nombre = nombre;
        this.points = point;
    }

    // endregion



    // region Methods Implements

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
        System.out.println("notificar suscritores");
        for (Observer ob: observers){
            ob.update(this.points);
        }
    }

    //endregion



    // region Getters and Setters

    public ArrayList<Observer> getObservers() {
        return observers;
    }

    public void setObservers(ArrayList<Observer> observers) {
        this.observers = observers;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
        notifyObservers();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // endregion
}
