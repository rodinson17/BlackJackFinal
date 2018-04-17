package com.game.blackjack.factory_method.classes;


public abstract class Card {

    // region Attributes of Class

    private int number;
    private String tipo;
    private boolean estado;
    private int imagen;

    // endregion


    /*public Card(int number, String tipo) {
        this.number = number;
        this.tipo = tipo;
    }*/

    public Card(int number, String tipo, int imagen) {
        this.number = number;
        this.tipo = tipo;
        this.imagen = imagen;
    }

    public abstract void createFigure();


    // region Getters and Setters

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }

    // endregion

}
