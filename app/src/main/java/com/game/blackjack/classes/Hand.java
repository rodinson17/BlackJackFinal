package com.game.blackjack.classes;

import android.content.Context;
import com.game.blackjack.factory_method.classes.Card;
import com.game.blackjack.utilities.Constant;
import java.util.ArrayList;

public class Hand {

    // region Attributes of Class
    private ArrayList<Player> listPlayers;
    private Deck deck;
    private ArrayList<Card> cardsPlayer1;
    private ArrayList<Card> cardsPlayer2;
    // endregion


    public Hand(boolean cardsPoker, Context context) {
        this.listPlayers = new ArrayList<>();
        this.deck = Deck.getInstance();
        this.deck.setContext(context);
        this.deck.setCardsPoker(cardsPoker);
        this.deck.createCards();
    }


    /**
     * Metodo para asignar las dos primeras cartas a cada jugador
     */
    public void assignCardPlayerStart(){
        this.cardsPlayer1 = new ArrayList<>();
        this.cardsPlayer2 = new ArrayList<>();

        createCardForPlayer(this.cardsPlayer1, 0);
        createCardForPlayer(this.cardsPlayer2, 1);
    }

    /**
     * Metodo para crear y asignar una carta a un jugador
     * @param list
     * @param player
     */
    public void createCardForPlayer(ArrayList<Card> list, int player){
        Card card = this.deck.getNewCard();
        list.add(card);
        this.listPlayers.get(player).setPoints(card.getNumber());

        Card card2 = this.deck.getNewCard();
        list.add(card2);
        this.listPlayers.get(player).setPoints(card2.getNumber());
    }


    /**
     * Metodo para asignar una carta a un jugador
     * @param player
     * @param card
     */
    public void assignCardPlayer(int player, Card card){
        if (player == 0) {
            this.cardsPlayer1.add(card);
            this.listPlayers.get(0).setPoints(card.getNumber());
        } else {
            this.cardsPlayer2.add(card);
            this.listPlayers.get(1).setPoints(card.getNumber());
        }
    }

    /**
     * Metodo que retorna un valor falso o verdadero para determinar si el juego continua
     * @param points
     * @return
     */
    public boolean continuePlaying(int points){
        if (points >= Constant.SCORE_WINNER){
            return true;
        }
        return false;
    }


    // region Getters and Setters
    public Deck getDeck() { return deck; }

    public void setDeck(Deck deck) { this.deck = deck; }

    public ArrayList<Player> getListPlayers() { return listPlayers; }

    public void setListPlayers(ArrayList<Player> listPlayers) { this.listPlayers = listPlayers; }

    public ArrayList<Card> getCardsPlayer1() { return cardsPlayer1; }

    public void setCardsPlayer1(ArrayList<Card> cardsPlayer1) { this.cardsPlayer1 = cardsPlayer1; }

    public ArrayList<Card> getCardsPlayer2() { return cardsPlayer2; }

    public void setCardsPlayer2(ArrayList<Card> cardsPlayer2) { this.cardsPlayer2 = cardsPlayer2; }
    // endregion

}
