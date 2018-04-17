package com.game.blackjack.factory_method.factory;

import com.game.blackjack.R;
import com.game.blackjack.activities.GameActivity;
import com.game.blackjack.factory_method.classes.Card;
import com.game.blackjack.factory_method.classes.CardPoker;
import com.game.blackjack.factory_method.classes.CardSpanish;
import com.game.blackjack.factory_method.interfaces.Creator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CardFactory implements Creator {

    //    @Override
//    public Card createCard(int number, String type) {
//
//        if (type.equalsIgnoreCase("oros") || type.equalsIgnoreCase("bastos") || type.equalsIgnoreCase("copas") || type.equalsIgnoreCase("espadas")){
//            return new CardSpanish(number, type);
//        } else {
//            return new CardPoker(number, type);
//        }
//
//    }


    @Override
    public ArrayList<Card> createCard(int number) {
        ArrayList<Card> listCards = new ArrayList<>();

        if (number == 48) {
            createCardSpanish(listCards);
        } else {
            createCardPoker(listCards);
        }

        return listCards;
    }


    public void createCardSpanish(ArrayList<Card> listCards){
        String[] types = {"bastos","oros","espadas","copas"};

        for (int i = 0; i < types.length; i++){
            for (int j = 1; j <= 12; j++){
                int drawableResourceId = GameActivity.context.getResources().getIdentifier(types[i] + "_" + j, "drawable", GameActivity.context.getPackageName());
                listCards.add(new CardSpanish(j, types[i], drawableResourceId));
            }
        }
    }


    public void createCardPoker(ArrayList<Card> listCards){
        int[] numbers = {1,2,3,4,5,6,7,8,9,10,10,10,10};
        String[] types = {"Corazones","Trebol","Diamantes","Picas"};

        for (int i = 0; i < types.length; i++){
            for (int j = 0; j < numbers.length; j++){
                //listCards.add(new CardPoker(numbers[j], types[i]));
            }
        }
    }


    /*private Map getNumberAndFigure(){
        Map<Integer, Integer> mapBastos = new HashMap<>();
        mapBastos.put(1, R.drawable.Bastos_1);

        return mapBastos;
    }*/


}
