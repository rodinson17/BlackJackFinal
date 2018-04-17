package com.game.blackjack.classes;

import com.game.blackjack.factory_method.classes.Card;
import com.game.blackjack.factory_method.factory.CardFactory;
import com.game.blackjack.factory_method.interfaces.Creator;
import java.util.ArrayList;

public class Deck {


    // region Attributes of Class

    private static Deck instance; // atributo para la instancia de la clase
    private ArrayList<Card> cartas;

    // endregion



    // region Constructors

    /**
     * Constructor privado para evitar instancias de la clase
     */
    private Deck() {}


    /**
     * Metodo static que retorna una instancia de la clase
     * @return object Deck
     */
    public static Deck getIntance() {
        if (instance == null) {
            instance = new Deck();
        }
        return instance;
    }

    // endregion



    /**
     * Metodo para crear las cartas
     */
    public void createCarta() {

        Creator cards = new CardFactory();
        cartas = cards.createCard(48);

    }


    /**
     * Metodo para obtener una nueva carta
     */
    public Card obtenerNuevaCarta() {
        Card newCarta = null;
        boolean disponible = false;

        while (!disponible){
            int pos = (int) (Math.random() * 48);

            if (this.cartas.get(pos).isEstado() == false){
                newCarta = this.cartas.get(pos);
                newCarta.setEstado(true);
                disponible = true;
            }
        }

        return newCarta;
    }



    // region Getters and Setters

    public ArrayList<Card> getCartas() {
        return cartas;
    }

    public void setCartas(ArrayList<Card> cartas) {
        this.cartas = cartas;
    }

    // endregion
}
