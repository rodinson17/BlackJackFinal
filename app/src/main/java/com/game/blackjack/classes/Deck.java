package com.game.blackjack.classes;

import android.content.Context;
import com.game.blackjack.factory_method.classes.Card;
import com.game.blackjack.factory_method.factory.CardFactory;
import com.game.blackjack.factory_method.interfaces.Creator;
import com.game.blackjack.utilities.Constant;
import java.util.ArrayList;

public class Deck {

    // region Attributes of Class
    private static Deck instance; // atributo para la instancia de la clase
    private ArrayList<Card> listCards;
    private boolean cardsPoker;
    private int numberCards;
    private Context context;
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
    public static Deck getInstance() {
        if (instance == null) {
            instance = new Deck();
        }
        return instance;
    }
    // endregion


    /**
     * Metodo para crear las cartas
     */
    public void createCards() {
        Creator cards = new CardFactory();

        listCards = cards.createCards(cardsPoker, context);
    }

    /**
     * Metodo para obtener una nueva carta
     */
    public Card getNewCard() {
        Card newCard = null;
        boolean available = false;

        while (!available){

            int pos = (int) (Math.random() * numberCards);

            if (this.listCards.get(pos).isAvailable() == false){
                newCard = this.listCards.get(pos);
                newCard.setAvailable(true);
                available = true;
            }
        }
        return newCard;
    }


    // region Getters and Setters
    public ArrayList<Card> getListCards() { return listCards; }

    public void setListCards(ArrayList<Card> listCards) { this.listCards = listCards; }

    public boolean isCardsPoker() { return cardsPoker; }

    public void setCardsPoker(boolean cardsPoker) {
        this.cardsPoker = cardsPoker;
        numberCards = Constant.NUMBER_CARDS_SPANISH;
        if (cardsPoker) numberCards = Constant.NUMBER_CARDS_POKER;
    }

    public Context getContext() { return context; }

    public void setContext(Context context) { this.context = context; }
    // endregion

}
