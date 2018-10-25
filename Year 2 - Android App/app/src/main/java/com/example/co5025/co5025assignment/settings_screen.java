package com.example.co5025.co5025assignment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

public class settings_screen extends AppCompatActivity {

    //Create Radio Button for each radio button in the Stats layout
    RadioButton med_difficulty;
    RadioButton hard_difficulty;
    RadioButton impos_difficulty;
    RadioButton light_theme;
    RadioButton dark_theme;
    RadioButton red_theme;
    RadioButton player_first;
    RadioButton player_second;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_screen);

        //Set ids for each Radio Button variable
        med_difficulty = (RadioButton) findViewById(R.id.medium_difficulty);
        hard_difficulty = (RadioButton) findViewById(R.id.hard_difficulty);
        impos_difficulty = (RadioButton) findViewById(R.id.impossible_difficulty);
        light_theme = (RadioButton) findViewById(R.id.light_theme);
        dark_theme = (RadioButton) findViewById(R.id.dark_theme);
        red_theme = (RadioButton) findViewById(R.id.red_theme);
        player_first = (RadioButton) findViewById(R.id.player_first);
        player_second = (RadioButton) findViewById(R.id.player_second);

        //Call method to set radio button states
        setCurrentProperties();
    }

    public void setCurrentProperties() {

        //Sets the state of each radio button depending on the value of the game difficulty, colour theme and player turn variables in Users class
        if (UserClass.getGame_difficulty() == 1) {
            med_difficulty.setChecked(true);
            hard_difficulty.setChecked(false);
            impos_difficulty.setChecked(false);
        } else if (UserClass.getGame_difficulty() == 2) {
            med_difficulty.setChecked(false);
            hard_difficulty.setChecked(true);
            impos_difficulty.setChecked(false);
        } else if (UserClass.getGame_difficulty() == 3) {
            med_difficulty.setChecked(false);
            hard_difficulty.setChecked(false);
            impos_difficulty.setChecked(true);
        }
        if(UserClass.getColour_theme() == 1) {
            light_theme.setChecked(true);
            dark_theme.setChecked(false);
            red_theme.setChecked(false);
        } else if (UserClass.getColour_theme() == 2) {
            light_theme.setChecked(false);
            dark_theme.setChecked(true);
            red_theme.setChecked(false);
        } else if (UserClass.getColour_theme() == 3) {
            light_theme.setChecked(false);
            dark_theme.setChecked(false);
            red_theme.setChecked(true);
        }
        if(UserClass.getPlayer_turn() == 1) {
            player_first.setChecked(true);
            player_second.setChecked(false);
        } else if (UserClass.getPlayer_turn() == 2) {
            player_first.setChecked(false);
            player_second.setChecked(true);
        }
    }

    public void setSharedPreferences() {
        //Set the values for each item in shared preferences
        SharedPreferences pref = getApplicationContext().getSharedPreferences("UserPref", MODE_PRIVATE);
        SharedPreferences.Editor pref_editor = pref.edit();
        pref_editor.putInt("game_difficulty", UserClass.getGame_difficulty());
        pref_editor.putInt("colour_theme", UserClass.getColour_theme());
        pref_editor.putInt("player_turn", UserClass.getPlayer_turn());
        pref_editor.apply();
    }

    public void onDifficultyClick(View view) {
        //Set the game difficulty variable in UserClass depemding on wjich radio button is clicked
        switch (view.getId()) {
            case (R.id.medium_difficulty):
                UserClass.setGame_difficulty(1);
                break;
            case (R.id.hard_difficulty):
                UserClass.setGame_difficulty(2);
                break;
            case (R.id.impossible_difficulty):
                UserClass.setGame_difficulty(3);
                break;
        }
        //Call these methods to update shared preferences and set the states of the radio buttons
        setSharedPreferences();
        setCurrentProperties();
    }

    public void onThemeClick(View view) {
        //Set the colour theme variable in UserClass depemding on wjich radio button is clicked
        switch (view.getId()) {
            case (R.id.light_theme):
                UserClass.setColour_theme(1);
                break;
            case (R.id.dark_theme):
                UserClass.setColour_theme(2);
                break;
            case (R.id.red_theme):
                UserClass.setColour_theme(3);
                break;
        }
        //Call these methods to update shared preferences and set the states of the radio buttons
        setSharedPreferences();
        setCurrentProperties();
    }

    public void onPlayerTurnClick(View view) {
        //Set the player turn variable in UserClass depemding on wjich radio button is clicked
        switch (view.getId()) {
            case (R.id.player_first):
                UserClass.setPlayer_turn(1);
                break;
            case (R.id.player_second):
                UserClass.setPlayer_turn(2);
                break;
        }
        //Call these methods to update shared preferences and set the states of the radio buttons
        setSharedPreferences();
        setCurrentProperties();
    }

    @Override
    public void onBackPressed() {
        //Start new intent that creates home screen activity and sets relevant animation
        Intent i = new Intent(settings_screen.this, HomeScreen.class);
        startActivity(i);
        overridePendingTransition(R.anim.activity_fade_in_back, R.anim.activity_fade_out_back);
    }
}
