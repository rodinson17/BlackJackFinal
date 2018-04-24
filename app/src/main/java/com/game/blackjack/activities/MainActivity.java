package com.game.blackjack.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.game.blackjack.R;
import com.game.blackjack.classes.DialogConfig;

public class MainActivity extends AppCompatActivity implements DialogConfig.DialogEventListener{

    private TextView tvJugar, tvConfig;
    private boolean cardsPoker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvJugar = (TextView) findViewById(R.id.tv_play);
        tvConfig = (TextView) findViewById(R.id.tv_config);

        tvConfig.setOnClickListener(this::eventClickConfig);

        tvJugar.setOnClickListener(view -> {
            Intent intentMesa = new Intent(MainActivity.this, GameActivity.class);
            intentMesa.putExtra("cardsPoker", cardsPoker);
            startActivity(intentMesa);
        });
    }


    public void eventClickConfig(View v){
        DialogConfig dialog = new DialogConfig(this);
        dialog.init(this.cardsPoker);
        dialog.setListener(this);
        dialog.show();
    }

    @Override
    public void eventClickSave(DialogConfig dialog, boolean cardsPoker) {
        this.cardsPoker = cardsPoker;
        dialog.dismiss();
    }

}
