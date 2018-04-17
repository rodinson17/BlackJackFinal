package com.game.blackjack.factory_method.interfaces;

import com.game.blackjack.factory_method.classes.Card;

import java.util.ArrayList;

public interface Creator {

    //Card createCard(int number, String type);
    ArrayList<Card> createCard(int number);

}
