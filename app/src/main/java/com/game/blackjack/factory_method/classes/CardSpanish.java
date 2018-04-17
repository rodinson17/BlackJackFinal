package com.game.blackjack.factory_method.classes;

public class CardSpanish extends Card {


    public CardSpanish(int number, String tipo, int imagen) {
        super(number, tipo, imagen);
    }

    /*public CardSpanish(int number, String tipo) {
        super(number, tipo);
    }*/

    @Override
    public void createFigure() {
        System.out.println("crear figura");
    }

}
