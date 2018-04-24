package com.game.blackjack.factory_method.classes;

import android.content.Context;

public class CardPoker extends Card {


    public CardPoker(int number, String type) {
        super(number, type);
    }


    @Override
    public void createFigure(Context context) {
        // busco el recurso
        int drawableResourceId = context.getResources().getIdentifier(this.getType() + "_" + this.getNumber(), "drawable", context.getPackageName());
        this.setImage(drawableResourceId);

        // cambio el valor
        if (this.getNumber() == 11 || this.getNumber() == 12 || this.getNumber() == 13) {
            this.setNumber(10);
        }
    }

}
