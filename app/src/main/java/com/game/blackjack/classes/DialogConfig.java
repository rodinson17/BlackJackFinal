package com.game.blackjack.classes;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.game.blackjack.R;
import com.game.blackjack.utilities.Constant;

import java.util.ArrayList;

public class DialogConfig extends Dialog {

    public interface DialogEventListener {

        void eventClickSave(DialogConfig dialog, boolean cardsPoker);

    }


    // region Attributes of class
    private TextView tvCancel, tvSave, tvMsgError;
    private RadioGroup rgTypesCards;
    private RadioButton rbCardsSpanish, rbCardsPoker;
    private EditText etNamePlayer;
    private Button btnCreate;
    private LinearLayout llContentListPlayers, llListPlayers;

    private DialogEventListener listener;
    private boolean cardsPoker;
    public static ArrayList<Player> listPlayers;
    private int countPlayers = 0;
    // endregion


    // region Constructors
    public DialogConfig(@NonNull Context context, boolean cardsPoker) {
        super(context);
        this.cardsPoker = cardsPoker;
        init();
    }

    private DialogConfig(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }
    // endregion


    public void init(){
        listPlayers = new ArrayList<>();

        // se configura la vista
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.dialog_config);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        // se instancia los elementos
        tvCancel = (TextView) findViewById(R.id.tv_cancel);
        tvSave = (TextView) findViewById(R.id.tv_save);
        tvMsgError = (TextView) findViewById(R.id.tv_msg_error);
        rgTypesCards = (RadioGroup) findViewById(R.id.rg_types_cards);
        rbCardsSpanish = (RadioButton) findViewById(R.id.rb_cards_spanish);
        rbCardsPoker = (RadioButton) findViewById(R.id.rb_cards_poker);
        etNamePlayer = (EditText) findViewById(R.id.et_name_player);
        btnCreate = (Button) findViewById(R.id.btn_create_player);
        llContentListPlayers = (LinearLayout) findViewById(R.id.ll_content_list_players);
        llListPlayers = (LinearLayout) findViewById(R.id.ll_list_players);

        // eventos click
        tvCancel.setOnClickListener(this::eventCancel);
        tvSave.setOnClickListener(this::eventSave);
        btnCreate.setOnClickListener(this::eventCreatePlayer);

        if (this.cardsPoker){
            rbCardsPoker.setChecked(true);
            rbCardsSpanish.setChecked(false);
        } else {
            rbCardsSpanish.setChecked(true);
            rbCardsPoker.setChecked(false);
        }

        rgTypesCards.setOnCheckedChangeListener((radioGroup, i) -> {
            if (i == R.id.rb_cards_spanish){
                this.cardsPoker = false;
            } else {
                this.cardsPoker = true;
            }
        });

        etNamePlayer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0){
                    tvMsgError.setVisibility(View.GONE);
                }
            }
        });
    }


    // region Listener
    private void eventCancel(View view) {
        listPlayers.clear();
        this.dismiss();
    }

    private void eventSave(View view){
        listener.eventClickSave(this, cardsPoker);
    }

    public void eventCreatePlayer(View view) {

        if (etNamePlayer.getText().toString().equals(Constant.STRING_EMPTY)){
            tvMsgError.setVisibility(View.VISIBLE);
            tvMsgError.setText(getContext().getResources().getString(R.string.name_empty));
            return;
        }

        if (listPlayers.size() < 2) {
            countPlayers++;
            Player player = new Player(etNamePlayer.getText().toString());
            listPlayers.add(player);

            llContentListPlayers.setVisibility(View.VISIBLE);
            TextView tv = new TextView(getContext());
            tv.setText("Player " + countPlayers + ": "+etNamePlayer.getText().toString());
            tv.setPadding(getPixelsViewFromdp(30), getPixelsViewFromdp(10), getPixelsViewFromdp(30), 0);
            tv.setTextColor(Color.BLACK);
            tv.setTextSize(14);
            llListPlayers.addView(tv);
            etNamePlayer.setText(Constant.STRING_EMPTY);
        } else {
            tvMsgError.setVisibility(View.VISIBLE);
            tvMsgError.setText(getContext().getResources().getString(R.string.max_player));
        }
    }
    // endregion


    public int getPixelsViewFromdp(int dp) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }


    // region Getters and Setters
    public void setListener(DialogEventListener listener) {
        this.listener = listener;
    }
    // endregion

}
