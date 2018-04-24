package com.game.blackjack.factory_method.classes;

import android.content.Context;

public class CardSpanish extends Card {


    public CardSpanish(int number, String type) {
        super(number, type);
    }


    @Override
    public void createFigure(Context context) {
        int drawableResourceId = context.getResources().getIdentifier(this.getType() + "_" + this.getNumber(), "drawable", context.getPackageName());
        this.setImage(drawableResourceId);
    }

}
