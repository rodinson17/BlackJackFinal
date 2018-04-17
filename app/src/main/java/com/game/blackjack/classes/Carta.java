package com.game.blackjack.classes;

public class Carta {


    private int number;
    private String tipo;
    private boolean estado;
    private int imagen;

    public Carta() { }

    /**
     * Constructor con parametros
     * @param number
     * @param tipo
     */
    public Carta(int number, String tipo, int imagen) {
        this.number = number;
        this.tipo = tipo;
        this.imagen = imagen;
    }


    // Getters and Setters
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

}
