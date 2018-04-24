package com.game.blackjack.factory_method.classes;

import android.content.Context;

public abstract class Card {

    // region Attributes of Class
    private int number;
    private String type;
    private boolean available;
    private int image;
    // endregion


    // region Constructors
    public Card(int number, String type) {
        this.number = number;
        this.type = type;
    }

    /*public Card(int number, String type, int image) {
        this.number = number;
        this.type = type;
        this.image = image;
    }*/
    // endregion


    public abstract void createFigure(Context context);


    // region Getters and Setters
    public int getNumber() { return number; }

    public void setNumber(int number) { this.number = number; }

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }

    public boolean isAvailable() { return available; }

    public void setAvailable(boolean available) { this.available = available; }

    public int getImage() { return image; }

    public void setImage(int image) { this.image = image; }
    // endregion

}
