package com.game.blackjack.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.game.blackjack.R;

public class MainActivity extends AppCompatActivity {

    private TextView tvJugar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvJugar = (TextView) findViewById(R.id.tv_play);

        tvJugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentMesa = new Intent(MainActivity.this, GameActivity.class);
                startActivity(intentMesa);
            }
        });
    }
}
