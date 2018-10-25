package com.example.co5025.co5025assignment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class splashScreen extends AppCompatActivity implements View.OnClickListener {

    // Integer to store number of records, animation for splash screen loading and ImageView for app logo
    static int NumberOfRecords = 0;
    Animation app_logo_rotation;
    ImageView appLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //Set animation for loading effect
        app_logo_rotation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.app_logo_splash_anim);
        //Set ImageView to the id of the logo in the layout and start animation. Also, set an on click listener for it
        appLogo = (ImageView) findViewById(R.id.game_logo);
        appLogo.startAnimation(app_logo_rotation);
        appLogo.setOnClickListener(this);

        try {
            //Call readfile method to read all of the lines from the Users file
            ReadFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Call get preferences method to set the preference values in UserClass
        getPreferences();
    }

    @Override
    public void onClick(View v) {
        //Check if Image view was clicked
        switch (v.getId()) {
            case (R.id.game_logo):
                //Start home screen activity and set relevant animation
                Intent i = new Intent(splashScreen.this, HomeScreen.class);
                startActivity(i);
                overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
                break;
        }
    }

    public void getPreferences() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("UserPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor pref_editor = pref.edit();
        //Try to get game difficulty preference
        int prefCheck = pref.getInt("game_difficulty", -1);
        //If no preference was found. add a preference item for game difficulty, colour theme and player turn
        if (prefCheck == -1) {
            pref_editor.putInt("game_difficulty", 1);
            pref_editor.putInt("colour_theme", 1);
            pref_editor.putInt("player_turn", 1);
            pref_editor.apply();
        }

        //Set the preference variables in the UserClass to the values retrieved from Shared Preferences
        UserClass.setGame_difficulty(pref.getInt("game_difficulty", -1));
        UserClass.setColour_theme(pref.getInt("colour_theme", -1));
        UserClass.setPlayer_turn(pref.getInt("player_turn", -1));
    }

    public void ReadFile() throws IOException {
        //Set Number of records equal to the amount of lines read from the Users file divided by how many fields each record has
        NumberOfRecords = LineCount() / 12;
        UserClass.setNumberOfRecords(NumberOfRecords);
        FileInputStream fis = openFileInput("Users.txt");
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader br = new BufferedReader(isr);
        //Loop that loops around as many times as there are records
        for (int c = 0; c < NumberOfRecords; c++) {
            //Call create array list method in UserClass and pass the buffered reader in to it
            UserClass.createArrayList(br);
        }
        br.close();
    }

    public int LineCount() {
        //Initialise line count integer
        int lineCount = 0;
        try {
            //Try to read a file called Users.txt
            FileInputStream fis = openFileInput("Users.txt");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            //If file exists, read each line in file and increase count by 1 for each line read until no lines are left to read
            while (br.readLine() != null) {
                lineCount++;
            }
            br.close();
        } catch (IOException e) {
            //If the Users.txt file doesn't exist, write to a file called Users.txt and the output stream will create one automatically
            try {
                FileOutputStream fos = openFileOutput("Users.txt", Context.MODE_PRIVATE);
                OutputStreamWriter osw = new OutputStreamWriter(fos);
                BufferedWriter bw = new BufferedWriter(osw);
                //Write a test account in to the file
                bw.write("0");
                bw.newLine();
                bw.write("test");
                bw.newLine();
                bw.write("1234");
                bw.newLine();
                bw.write("0");
                bw.newLine();
                bw.write("0");
                bw.newLine();
                bw.write("0");
                bw.newLine();
                bw.write("0");
                bw.newLine();
                bw.write("0");
                bw.newLine();
                bw.write("0");
                bw.newLine();
                bw.write("0");
                bw.newLine();
                bw.write("0");
                bw.newLine();
                bw.write("0");
                bw.close();
                //Set line count for 1 record which equals 12
                lineCount = 12;
            } catch (IOException u) {
                u.printStackTrace();
            }
        }
        //Return the line count variable
        return lineCount;
    }
}
