package com.game.blackjack.classes;

import com.game.blackjack.factory_method.classes.Card;
import com.game.blackjack.observer_pattern.Observer;
import com.game.blackjack.utilities.Constant;
import java.util.ArrayList;

public class Hand implements Observer {


    // region Attributes of Class

    private ArrayList<Player> jugadores;
    private Deck deck;
    private ArrayList<Card> cartasJugador1;
    private ArrayList<Card> cartasJugador2;

    // endregion



    // region Constructors

    /**
     * Constructor de la clase Mano
     */
    public Hand() {
        createPlayers();
        this.deck = Deck.getIntance();
        this.deck.createCarta();
    }

    // endregion


    /**
     * Matodo para crear los jugadores
     */
    public void createPlayers() {
        this.jugadores = new ArrayList<>();

        Player player1 = new Player( "player1");
        this.jugadores.add(player1);
        player1.addObserver(this);

        Player player2 = new Player( "player2");
        this.jugadores.add(player2);
        player2.addObserver(this);
    }



    /**
     * Metodo para asignar las dos primeras cartas a cada jugador
     */
    public void asignarCartaJugadorInicio(){
        this.cartasJugador1 = new ArrayList<>();
        this.cartasJugador2 = new ArrayList<>();

        Card carta = this.deck.obtenerNuevaCarta();
        this.cartasJugador1.add(carta);
        this.jugadores.get(0).setPoints(carta.getNumber());
        Card carta1 = this.deck.obtenerNuevaCarta();
        this.cartasJugador1.add(carta1);
        this.jugadores.get(0).setPoints(carta1.getNumber());
        Card carta3 = this.deck.obtenerNuevaCarta();
        this.cartasJugador2.add(carta3);
        this.jugadores.get(1).setPoints(carta3.getNumber());
        Card carta4 = this.deck.obtenerNuevaCarta();
        this.cartasJugador2.add(carta4);
        this.jugadores.get(1).setPoints(carta4.getNumber());
    }



    /**
     * Metodo para asignar una carta a un jugador
     * @param player
     * @param carta
     */
    public void asignarCartaJugador(int player, Card carta){
        if (player == 0) {
            this.cartasJugador1.add(carta);
        } else {
            this.cartasJugador2.add(carta);
        }
    }



    /**
     * Puntos de la mano
     * @param player
     * @return los puntos del jugador
     */
    public int puntosMano(int player){
        int puntos = 0;

        if (player == 0){
            for (Card c: this.cartasJugador1){
                puntos += c.getNumber();
            }
        } else {
            for (Card c: this.cartasJugador2){
                puntos += c.getNumber();
            }
        }
        return puntos;
    }


    /**
     * Metodo que retorna un valor falso o verdadero para determinar si el juego continua
     * @param puntos
     * @return
     */
    public boolean continuar(int puntos){
        if (puntos >= Constant.SCORE_WINNER){
            return true;
        }
        return false;
    }



    @Override
    public void update(int number) {
        System.out.println("cambiaron los puntos: "+number);
    }



    // region Getters and Setters

    public ArrayList<Player> getJugadores() {
        return jugadores;
    }

    public void setJugadores(ArrayList<Player> jugadores) {
        this.jugadores = jugadores;
    }

    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public ArrayList<Card> getCartasJugador1() {
        return cartasJugador1;
    }

    public void setCartasJugador1(ArrayList<Card> cartasJugador1) {
        this.cartasJugador1 = cartasJugador1;
    }

    public ArrayList<Card> getCartasJugador2() {
        return cartasJugador2;
    }

    public void setCartasJugador2(ArrayList<Card> cartasJugador2) {
        this.cartasJugador2 = cartasJugador2;
    }

    // endregion

}
