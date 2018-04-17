package com.game.blackjack.factory_method.classes;

public class CardPoker extends Card {


    public CardPoker(int number, String tipo, int imagen) {
        super(number, tipo, imagen);
    }

    /*public CardPoker(int number, String tipo) {
        super(number, tipo);
    }*/

    @Override
    public void createFigure() {
        System.out.println("crear figura");
    }

}
