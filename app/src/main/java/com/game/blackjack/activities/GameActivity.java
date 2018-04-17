package com.game.blackjack.activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.game.blackjack.R;
import com.game.blackjack.classes.Carta;
import com.game.blackjack.classes.Hand;
import com.game.blackjack.factory_method.classes.Card;
import com.game.blackjack.utilities.Constant;
import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {


    // region Attributes of Activity
    LinearLayout llJugador1, llJugador2;
    TextView tvPedir, tvPlantar, tvPuntos, tvGanador;

    private Hand hand;
    private int turno;
    private boolean continuar;
    public static Context context;
    // endregion


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        context = this;

        llJugador1 = (LinearLayout) findViewById(R.id.ll_jugador_1);
        llJugador2 = (LinearLayout) findViewById(R.id.ll_jugador_2);
        tvPedir = (TextView) findViewById(R.id.tv_pedir);
        tvPlantar = (TextView) findViewById(R.id.tv_plantar);
        tvPuntos = (TextView) findViewById(R.id.tv_puntaje);
        tvGanador = (TextView) findViewById(R.id.tv_ganador);

        // falta revisar mas cosas
        iniciarJuego();
    }


    /**
     * Metodo encargado de iniciar el juego
     */
    private void iniciarJuego(){
        this.hand = new Hand();
        this.hand.asignarCartaJugadorInicio();
        mostrarCartas(this.hand.getCartasJugador1(), 0);
        mostrarCartas(this.hand.getCartasJugador2(), 1);
        mostrarPuntos(0);
        this.turno = 0;
        this.continuar = continuar();

        if (this.continuar) {
            tvPedir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (continuar) {
                        if (turno == 0) {
                            continuarJuego();
                        } else {
                            continuarJuego();
                        }
                    } else {
                        tvPedir.setEnabled(true);
                    }
                }
            });

            tvPlantar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // analizar si el jugador 2 se planta o si hay un empate
                    if (turno == 1){
                        // revisar puntos o posible empate
                        revisarPuntajes();
                    } else {
                        turno = 1;
                        continuar = true;
                        mostrarPuntos(turno);
                    }
                }
            });
        }
    }


    public void revisarPuntajes(){
        int puntosJugador1 = this.hand.puntosMano(0);
        int puntosJugador2 = this.hand.puntosMano(1);

        if (puntosJugador1 == puntosJugador2){
            tvGanador.setText("Hay un EMPATE");
        } else if (puntosJugador1 > puntosJugador2) {
            tvGanador.setText("El Ganador es:  " + this.hand.getJugadores().get(0).getNombre().toUpperCase());
        } else {
            tvGanador.setText("El Ganador es:  " + this.hand.getJugadores().get(1).getNombre().toUpperCase());
        }
    }


    /**
     * Metodo que se encarga de continuar con el juego
     */
    public void continuarJuego(){
        // Pido la carta y la asigno al jugador correspondiente
        this.hand.asignarCartaJugador(this.turno, this.hand.getDeck().obtenerNuevaCarta());

        if (this.turno == 0){
            mostrarCartas(this.hand.getCartasJugador1(), this.turno);
        } else {
            mostrarCartas(this.hand.getCartasJugador2(), this.turno);
        }
        mostrarPuntos(this.turno);
        this.continuar = continuar();
    }


    /**
     * Metodo que me permite saber si puedo continuar en el juego o no
     * @return
     */
    public boolean continuar(){
        boolean continuarJugador1 = this.hand.continuar(this.hand.puntosMano(0));
        boolean continuarJugador2 = this.hand.continuar(this.hand.puntosMano(1));

        if (!continuarJugador1 && !continuarJugador2){
            return true;
        } else if (continuarJugador1){

            int puntosJugador1 = this.hand.puntosMano(0);

            if (puntosJugador1 == Constant.SCORE_WINNER){
                tvGanador.setText("El Ganador es:  " + this.hand.getJugadores().get(0).getNombre().toUpperCase());
            } else {
                tvGanador.setText("El Ganador es:  " + this.hand.getJugadores().get(1).getNombre().toUpperCase());
            }

        } else if(continuarJugador2){

            int puntosJugador2 = this.hand.puntosMano(1);

            if (puntosJugador2 == Constant.SCORE_WINNER){
                tvGanador.setText("El Ganador es:  " + this.hand.getJugadores().get(1).getNombre().toUpperCase());
            } else {
                tvGanador.setText("El Ganador es:  " + this.hand.getJugadores().get(0).getNombre().toUpperCase());
            }

        }
        return false;
    }


    /**
     * Metodo que permite mostrar las cartas en la vista
     * @param cartas
     * @param player
     */
    private void mostrarCartas(ArrayList<Card> cartas, int player){
        int width = getPixelsViewFromdp(120);
        int heigth = getPixelsViewFromdp(100);

        if (player == 0){
            llJugador1.removeAllViews();
        } else {
            llJugador2.removeAllViews();
        }

        for (Card carta: cartas){
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            LinearLayout viewCarta = (LinearLayout) inflater.inflate(R.layout.layout_card, null);

            TextView tvNumber = (TextView) viewCarta.findViewById(R.id.tv_numero_carta);
            TextView tvTipo = (TextView) viewCarta.findViewById(R.id.tv_tipo_Carta);

            // asigno valores
            tvNumber.setText("");
            //tvNumber.setText(""+carta.getNumber());
            //tvTipo.setText(carta.getTipo());
            tvTipo.setText("");
            //viewCarta.setLayoutParams(new LinearLayout.LayoutParams(width, heigth));
            viewCarta.setLayoutParams(new LinearLayout.LayoutParams(width, ViewGroup.LayoutParams.MATCH_PARENT));
            viewCarta.setBackgroundResource(carta.getImagen());

            View espacio = new View(getApplicationContext()); // vista para generar un espacio entre cartas
            espacio.setLayoutParams(new LinearLayout.LayoutParams(30, ViewGroup.LayoutParams.MATCH_PARENT));

            if (player == 0){
                //viewCarta.setBackgroundColor(getResources().getColor(R.color.gray));
                llJugador1.addView(viewCarta);
                llJugador1.addView(espacio);
            } else {
                //viewCarta.setBackgroundColor(getResources().getColor(R.color.yellow));
                llJugador2.addView(viewCarta);
                llJugador2.addView(espacio);
            }
        }
    }



    /**
     * Metodo que permite mostrar el puntaje para el jugador actual
     * @param player
     */
    private void mostrarPuntos(int player){
        tvPuntos.setText("Puntos:\n " + this.hand.puntosMano(player));
    }


    /**
     * Metodo que me retorna un valor en pixeles dependiendo la resolucion del dispositivo
     * @param dp
     * @return
     */
    public int getPixelsViewFromdp(int dp) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }


}
