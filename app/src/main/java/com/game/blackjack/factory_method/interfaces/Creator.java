package com.game.blackjack.factory_method.interfaces;

import android.content.Context;
import com.game.blackjack.factory_method.classes.Card;
import java.util.ArrayList;

public interface Creator {

    ArrayList<Card> createCards(int number, Context context);

}
