package com.game.blackjack.factory_method.factory;

import android.content.Context;
import com.game.blackjack.factory_method.classes.Card;
import com.game.blackjack.factory_method.classes.CardPoker;
import com.game.blackjack.factory_method.classes.CardSpanish;
import com.game.blackjack.factory_method.interfaces.Creator;
import java.util.ArrayList;

public class CardFactory implements Creator {

    @Override
    public ArrayList<Card> createCards(boolean cardsPoker, Context context) {
        ArrayList<Card> listCards = new ArrayList<>();

        if (cardsPoker) {
            createCardPoker(listCards, context);
        } else {
            createCardSpanish(listCards, context);
        }
        return listCards;
    }

    public void createCardSpanish(ArrayList<Card> listCards, Context context){
        String[] types = {"bastos","oros","espadas","copas"};

        for (int i = 0; i < types.length; i++){
            for (int j = 1; j <= 12; j++){
                CardSpanish card = new CardSpanish(j, types[i]);
                card.createFigure(context);
                listCards.add(card);
            }
        }
    }

    public void createCardPoker(ArrayList<Card> listCards, Context context){
        String[] types = {"clubs","diamonds","hearts","spades"};

        for (int i = 0; i < types.length; i++){
            for (int j = 1; j <= 13; j++){
                CardPoker card = new CardPoker(j, types[i]);
                card.createFigure(context);
                listCards.add(card);
            }
        }
    }

}
