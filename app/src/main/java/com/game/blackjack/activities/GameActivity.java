package com.game.blackjack.activities;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

        cardsPoker = getIntent().getBooleanExtra("cardsPoker", false);
        listPlayers = DialogConfig.listPlayers; // recupero la lista

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
        this.hand = new Hand(cardsPoker, this);
        createPlayers();
        showNamePlayers();
        this.hand.assignCardPlayerStart();
        showCards(this.hand.getCardsPlayer1(), 0);
        showCards(this.hand.getCardsPlayer2(), 1);
        this.turn = 0;
        showPoints(turn);
        this.play = continueGame(); // revisar esta parte

        if (this.play) {
            tvPedir.setOnClickListener(view -> {
                if (play) {
                    if (turn == 0) {
                        continuePlaying();
                    } else {
                        continuePlaying();
                    }
                } else {
                    tvPedir.setEnabled(false);
                }
            });

            tvPlantar.setOnClickListener(view -> {
                // analizar si el jugador 2 se planta o si hay un empate
                if (turn == 1) {
                    // revisar puntos o posible empate
                    checkScore();
                } else {
                    turn = 1;
                    changePlayerTextView();
                    play = true;
                    showPoints(turn);
                }
            });
        } else {
            tvNewGame.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Metodo para crear los jugadores y asignar el observer
     */
    public void createPlayers() {
        if (listPlayers == null || listPlayers.isEmpty()) {
            Player player1 = new Player(1, "Player One");
            this.hand.getListPlayers().add(player1);
            player1.addObserver(this);

            Player player2 = new Player(2, "Player Two");
            this.hand.getListPlayers().add(player2);
            player2.addObserver(this);
        } else {

            if (listPlayers.size() == 1) {
                Player player1 = listPlayers.get(0);
                player1.setId(1);
                player1.addObserver(this);
                this.hand.getListPlayers().add(player1);

                Player player2 = new Player(2, "Player Two");
                this.hand.getListPlayers().add(player2);
                player2.addObserver(this);
            } else {
                int id = 1;
                for (Player player : listPlayers) {
                    this.hand.getListPlayers().add(player);
                    player.setId(id);
                    player.addObserver(this);
                    id++;
                }
            }
        }
    }

    /**
     * Metodo que se encarga de continuar con el juego
     */
    public void continuePlaying() {
        // Pido la carta y la asigno al jugador correspondiente
        this.hand.assignCardPlayer(this.turn, this.hand.getDeck().getNewCard());

        if (this.turn == 0) {
            showCards(this.hand.getCardsPlayer1(), this.turn);
        } else {
            showCards(this.hand.getCardsPlayer2(), this.turn);
        }
        showPoints(this.turn);
        this.play = continueGame();

        if (!play) {
            tvNewGame.setVisibility(View.VISIBLE);
        }
    }


    // region Management Views

    /**
     * Metodo que permite mostrar las cartas en la vista
     *
     * @param cards
     * @param player
     */
    private void showCards(ArrayList<Card> cards, int player) {

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
    private void showPoints(int player) {
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
        if (pointsPlayer1 == pointsPlayer2) {
            setPlayerWinner(this.hand.getListPlayers().get(0).getName().toUpperCase());
            setPlayerWinner("Hay un EMPATE");
        } else if (pointsPlayer1 > pointsPlayer2) {
            setPlayerWinner(this.hand.getListPlayers().get(0).getName().toUpperCase());
            setPlayerWinner("El Ganador es:  " + this.hand.getListPlayers().get(0).getName().toUpperCase());
        } else {
            setPlayerWinner(this.hand.getListPlayers().get(0).getName().toUpperCase());
            setPlayerWinner("El Ganador es:  " + this.hand.getListPlayers().get(1).getName().toUpperCase());
        }
        tvNewGame.setVisibility(View.VISIBLE);
    }

    /**
     * Metodo para mostrar los nombres de los jugadores
     */
    public void showNamePlayers() {
        tvPlayer1.setText(this.hand.getListPlayers().get(0).getName());
        tvPlayer1.setTextColor(getResources().getColor(R.color.green));
        tvPlayer1.setTextSize(18);

        tvPlayer2.setText(this.hand.getListPlayers().get(1).getName());
        tvPlayer2.setTextColor(Color.BLACK);
        tvPlayer2.setTextSize(14);
    }

    /**
     * Metodo para cambiar el estilo del nombre del jugador actual
     */
    public void changePlayerTextView() {
        if (turn == 0) {
            tvPlayer1.setTextColor(getResources().getColor(R.color.green));
            tvPlayer1.setTextSize(18);
            tvPlayer2.setTextColor(Color.BLACK);
            tvPlayer2.setTextSize(14);
        } else {
            tvPlayer2.setTextColor(getResources().getColor(R.color.green));
            tvPlayer2.setTextSize(18);
            tvPlayer1.setTextColor(Color.BLACK);
            tvPlayer1.setTextSize(14);
        }
    }

    /**
     * Metodo que me permite saber si puedo continuar en el juego o no
     *
     * @return
     */
    public boolean continueGame() {
        boolean continuePlayer1 = this.hand.continuePlaying(pointsPlayer1);
        boolean continuePlayer2 = this.hand.continuePlaying(pointsPlayer2);

        if (!continuePlayer1 && !continuePlayer2) {
            return true;
        } else if (continuePlayer1) {

            if (pointsPlayer1 == Constant.SCORE_WINNER) {
                setPlayerWinner("El Ganador es:  " + this.hand.getListPlayers().get(0).getName().toUpperCase());
            } else {
                setPlayerWinner("El Ganador es:  " + this.hand.getListPlayers().get(1).getName().toUpperCase());
            }

        } else if (continuePlayer2) {

            if (pointsPlayer2 == Constant.SCORE_WINNER) {
                setPlayerWinner("El Ganador es:  " + this.hand.getListPlayers().get(1).getName().toUpperCase());
            } else {
                setPlayerWinner("El Ganador es:  " + this.hand.getListPlayers().get(0).getName().toUpperCase());
            }

        }
        return false;
    }

    /**
     * Metodo para setear el nombre del ganador
     *
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
     *
     * @param dp
     * @return
     */
    public int getPixelsViewFromdp(int dp) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }


    // region Events

    /**
     * evento click para iniciar nuevamente el juego
     *
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
        pointsPlayer1 = 0;
        pointsPlayer2 = 0;
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
