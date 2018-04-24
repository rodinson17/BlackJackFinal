package com.game.blackjack.factory_method.factory;

import android.content.Context;
import com.game.blackjack.factory_method.classes.Card;
import com.game.blackjack.factory_method.classes.CardPoker;
import com.game.blackjack.factory_method.classes.CardSpanish;
import com.game.blackjack.factory_method.interfaces.Creator;
import com.game.blackjack.utilities.Constant;
import java.util.ArrayList;

public class CardFactory implements Creator {


    @Override
    public ArrayList<Card> createCards(int number, Context context) {
        ArrayList<Card> listCards = new ArrayList<>();

        if (number == Constant.NUMBER_CARDS_SPANISH) {
            createCardSpanish(listCards, context);
        } else {
            createCardPoker(listCards, context);
        }
        return listCards;
    }

    /**
     * Metodo para crear las cartas españolas
     * @param listCards
     * @param context
     */
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

    /**
     * Metodo para crear las cartas de poker
     * @param listCards
     * @param context
     */
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
