package com.game.blackjack.activities;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.game.blackjack.R;
import com.game.blackjack.classes.DialogConfig;
import com.game.blackjack.classes.Hand;
import com.game.blackjack.classes.Player;
import com.game.blackjack.factory_method.classes.Card;
import com.game.blackjack.observer_pattern.Observer;
import com.game.blackjack.utilities.Constant;
import java.util.ArrayList;

public class GameActivity extends AppCompatActivity implements Observer {

    // region Attributes of Activity
    LinearLayout llPlayer1, llPlayer2;
    TextView tvPedir, tvPlantar, tvPuntos, tvGanador, tvPlayer1, tvPlayer2, tvNewGame;

    private Hand hand;
    private int turn;
    private boolean play;
    private boolean cardsPoker;
    private ArrayList<Player> listPlayers;
    private int pointsPlayer1, pointsPlayer2;
    private final int WIDTH_CARD = 120;
    // endregion


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // recuperar datos
        cardsPoker = getIntent().getBooleanExtra("cardsPoker", false);
        listPlayers = DialogConfig.listPlayers; // recupero la lista

        // instanciar vista
        tvPlayer1 = (TextView) findViewById(R.id.tv_player1);
        llPlayer1 = (LinearLayout) findViewById(R.id.ll_jugador_1);
        tvPlayer2 = (TextView) findViewById(R.id.tv_player2);
        llPlayer2 = (LinearLayout) findViewById(R.id.ll_jugador_2);
        tvNewGame = (TextView) findViewById(R.id.tv_new_game);
        tvPedir = (TextView) findViewById(R.id.tv_pedir);
        tvPlantar = (TextView) findViewById(R.id.tv_plantar);
        tvPuntos = (TextView) findViewById(R.id.tv_puntaje);
        tvGanador = (TextView) findViewById(R.id.tv_ganador);

        tvNewGame.setOnClickListener(this::eventClickNewGame);

        startGame();
    }


    /**
     * Metodo encargado de iniciar el juego
     */
    private void startGame() {
        hand = new Hand(cardsPoker, this);
        createPlayers();
        showNamePlayersInView();
        hand.assignCardPlayerStart();
        showCardsInView(hand.getCardsPlayer1(), 0);
        showCardsInView(hand.getCardsPlayer2(), 1);
        turn = 0;
        showPointsInView(turn);
        play = continueGame();

        if (play) {
            tvPedir.setOnClickListener(this::onClickPedir);

            tvPlantar.setOnClickListener(this::onClickPlantar);

        } else {
            tvNewGame.setVisibility(View.VISIBLE);
//            tvPedir.setEnabled(false);
//            tvPlantar.setEnabled(false);
        }
    }

    /**
     * Metodo para crear los jugadores y asignar el observer
     */
    public void createPlayers() {

        if (listPlayers == null || listPlayers.isEmpty()) {
            // crear jugadores por defecto
            Player player1 = new Player(1, getResources().getString(R.string.player_one));
            hand.getListPlayers().add(player1);
            player1.addObserver(this);

            Player player2 = new Player(2, getResources().getString(R.string.player_two));
            this.hand.getListPlayers().add(player2);
            player2.addObserver(this);
            return;
        }

        if (listPlayers.size() == 1) {
            // asigno ese jugador
            Player player1 = listPlayers.get(0);
            player1.setId(1);
            player1.addObserver(this);
            this.hand.getListPlayers().add(player1);

            // el segundo se crea por defecto
            Player player2 = new Player(2, getResources().getString(R.string.player_two));
            this.hand.getListPlayers().add(player2);
            player2.addObserver(this);
            return;
        }

        // asignar jugadores de la lista
        int id = 1;
        for (Player player : listPlayers) {
            this.hand.getListPlayers().add(player);
            player.setId(id);
            player.addObserver(this);
            id++;
        }
    }

    /**
     * Metodo que se encarga de continuar con el juego
     */
    public void continuePlaying() {
        // Pido la carta y la asigno al jugador correspondiente
        hand.assignCardPlayer(turn, hand.getDeck().getNewCard());

        if (turn == 0) {
            showCardsInView(hand.getCardsPlayer1(), turn);
        } else {
            showCardsInView(hand.getCardsPlayer2(), turn);
        }
        showPointsInView(turn);
        play = continueGame();

        if (!play) {
            tvNewGame.setVisibility(View.VISIBLE);
        }
    }


    // region Management Views

    /**
     * Metodo que permite mostrar las cartas en la vista
     * @param cards
     * @param player
     */
    private void showCardsInView(ArrayList<Card> cards, int player) {
        int width = getPixelsViewFromdp(WIDTH_CARD);

        if (player == 0) {
            llPlayer1.removeAllViews();
        } else {
            llPlayer2.removeAllViews();
        }

        for (Card card : cards) {
            LinearLayout llCardView = new LinearLayout(this);
            llCardView.setLayoutParams(new LinearLayout.LayoutParams(width, ViewGroup.LayoutParams.MATCH_PARENT));
            llCardView.setBackgroundResource(card.getImage());

            View space = new View(getApplicationContext()); // vista para generar un espacio entre cartas
            space.setLayoutParams(new LinearLayout.LayoutParams(30, ViewGroup.LayoutParams.MATCH_PARENT));

            if (player == 0) {
                llPlayer1.addView(llCardView);
                llPlayer1.addView(space);
            } else {
                llPlayer2.addView(llCardView);
                llPlayer2.addView(space);
            }
        }
    }

    /**
     * Metodo que permite mostrar el puntaje para el jugador actual
     *
     * @param player
     */
    private void showPointsInView(int player) {
        if (player == 0) {
            tvPuntos.setText("Puntos:\n " + pointsPlayer1);
        } else {
            tvPuntos.setText("Puntos:\n " + pointsPlayer2);
        }
    }

    /**
     * Metodo para verificar el puntaje de los jugadores
     */
    public void checkScore() {

        System.out.println("pointsPlayer1: "+pointsPlayer1+"  pointsPlayer2: "+pointsPlayer2);
        if (pointsPlayer1 == pointsPlayer2) {
            setPlayerWinner(getResources().getString(R.string.empate));
        } else {

            if (pointsPlayer1 > pointsPlayer2) {
                setPlayerWinner(hand.getListPlayers().get(0).getName().toUpperCase());
                setPlayerWinner(getResources().getString(R.string.ganador) + hand.getListPlayers().get(0).getName().toUpperCase());
            } else {
                setPlayerWinner(hand.getListPlayers().get(0).getName().toUpperCase());
                setPlayerWinner(getResources().getString(R.string.ganador) + hand.getListPlayers().get(1).getName().toUpperCase());
            }
        }
        tvNewGame.setVisibility(View.VISIBLE);
    }

    /**
     * Metodo para mostrar los nombres de los jugadores
     */
    public void showNamePlayersInView() {
        tvPlayer1.setText(hand.getListPlayers().get(0).getName());
        tvPlayer1.setBackgroundResource(R.drawable.generic_border_radius_gray);
        tvPlayer1.setTextColor(getResources().getColor(R.color.green));
        tvPlayer1.setTextSize(18);

        tvPlayer2.setText(hand.getListPlayers().get(1).getName());
        tvPlayer2.setTextColor(Color.BLACK);
        tvPlayer2.setBackgroundResource(R.drawable.generic_border_radius_transparent);
        tvPlayer2.setTextSize(14);
    }

    /**
     * Metodo para cambiar el estilo del nombre del jugador actual
     */
    public void changePlayerTextView() {
        if (turn == 0) {
            tvPlayer1.setTextColor(getResources().getColor(R.color.green));
            tvPlayer1.setBackgroundResource(R.drawable.generic_border_radius_gray);
            tvPlayer1.setTextSize(18);

            tvPlayer2.setTextColor(Color.BLACK);
            tvPlayer2.setBackgroundResource(R.drawable.generic_border_radius_transparent);
            tvPlayer2.setTextSize(14);
            return;
        }

        tvPlayer2.setTextColor(getResources().getColor(R.color.green));
        tvPlayer2.setBackgroundResource(R.drawable.generic_border_radius_gray);
        tvPlayer2.setTextSize(18);

        tvPlayer1.setTextColor(Color.BLACK);
        tvPlayer1.setBackgroundResource(R.drawable.generic_border_radius_transparent);
        tvPlayer1.setTextSize(14);
    }

    /**
     * Metodo que me permite saber si puedo continuar en el juego o no
     * @return
     */
    public boolean continueGame() {
        boolean continuePlayer1 = hand.continuePlaying(pointsPlayer1);
        boolean continuePlayer2 = hand.continuePlaying(pointsPlayer2);

        if (!continuePlayer1 && !continuePlayer2) {
            return true;
        }

        if (pointsPlayer1 == pointsPlayer2) {
            setPlayerWinner(getResources().getString(R.string.empate));
            return false;
        }

        if (continuePlayer1) {

            if (pointsPlayer1 == Constant.SCORE_WINNER) {
                setPlayerWinner(getResources().getString(R.string.ganador) + hand.getListPlayers().get(0).getName().toUpperCase());
            } else {
                setPlayerWinner(getResources().getString(R.string.ganador) + hand.getListPlayers().get(1).getName().toUpperCase());
            }

        }

        if (continuePlayer2) {

            if (pointsPlayer2 == Constant.SCORE_WINNER) {
                setPlayerWinner(getResources().getString(R.string.ganador) + hand.getListPlayers().get(1).getName().toUpperCase());
            } else {
                setPlayerWinner(getResources().getString(R.string.ganador) + hand.getListPlayers().get(0).getName().toUpperCase());
            }

        }
        return false;
    }

    /**
     * Metodo para setear el nombre del ganador
     * @param winner
     */
    public void setPlayerWinner(String winner) {
        tvGanador.setVisibility(View.VISIBLE);
        tvGanador.setText(winner);
        tvPedir.setEnabled(false);
        tvPlantar.setEnabled(false);
    }
    // endregion


    /**
     * Metodo que me retorna un valor en pixeles dependiendo la resolucion del dispositivo
     * @param dp
     * @return
     */
    public int getPixelsViewFromdp(int dp) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }


    // region Listener
    public void onClickPedir(View view) {
        if (play) {
            if (turn == 0) {
                continuePlaying();
            } else {
                continuePlaying();
            }
        } else {
            tvPedir.setEnabled(false);
        }
    }

    public void onClickPlantar(View view) {
        // analizar si el jugador 2 se planta o si hay un empate
        if (turn == 1) {
            // revisar puntos o posible empate
            checkScore();
        } else {
            turn = 1;
            changePlayerTextView();
            play = true;
            showPointsInView(turn);
        }
    }


    /**
     * evento click para iniciar nuevamente el juego
     * @param view
     */
    public void eventClickNewGame(View view) {
        resetPlayers();
        startGame();
        tvPedir.setEnabled(true);
        tvPlantar.setEnabled(true);
        view.setVisibility(View.GONE);
        tvGanador.setVisibility(View.GONE);
    }

    public void resetPlayers() {
        if (listPlayers != null) {
            for (Player player : listPlayers) {
                player.setPoints(-player.getPoints()); // le resto los puntos
                player.deleteObserver(this); // elimino el objserver
            }
        }
    }

    @Override
    public void update(Player player) {
        //Log.i(getClass().getName(), "detalles id: "+player.getId()+" name: "+player.getName()+" points: "+player.getPoints());
        if (player.getId() == 1) {
            pointsPlayer1 = player.getPoints();
        } else {
            pointsPlayer2 = player.getPoints();
        }
    }
    // endregion


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        resetPlayers();
        tvPedir.setEnabled(true);
        tvPlantar.setEnabled(true);
        tvNewGame.setVisibility(View.GONE);
        tvGanador.setVisibility(View.GONE);
        finish();
    }

}
