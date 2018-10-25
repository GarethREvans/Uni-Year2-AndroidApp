package com.example.co5025.co5025assignment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class stats_screen extends AppCompatActivity implements View.OnClickListener {

    //Create relevant textviews and button variables
    TextView mPlayed;
    TextView mWon;
    TextView mDrawn;
    TextView mLost;
    TextView hPlayed;
    TextView hWon;
    TextView hDrawn;
    TextView hLost;
    TextView iPlayed;
    TextView iWon;
    TextView iDrawn;
    TextView iLost;
    Button resetStats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats_screen);

        //Set ids of textviews (to display user statistics) and reset stat button and set an on click listener for the button
        mPlayed = (TextView) findViewById(R.id.mgames_played_value);
        mWon = (TextView) findViewById(R.id.mgames_won_value);
        mDrawn = (TextView) findViewById(R.id.mgames_drawn_value);
        mLost = (TextView) findViewById(R.id.mgames_lost_value);
        hPlayed = (TextView) findViewById(R.id.hgames_played_value);
        hWon = (TextView) findViewById(R.id.hgames_won_value);
        hDrawn = (TextView) findViewById(R.id.hgames_drawn_value);
        hLost = (TextView) findViewById(R.id.hgames_lost_value);
        iPlayed = (TextView) findViewById(R.id.igames_played_value);
        iWon = (TextView) findViewById(R.id.igames_won_value);
        iDrawn = (TextView) findViewById(R.id.igames_drawn_value);
        iLost = (TextView) findViewById(R.id.igames_lost_value);
        resetStats = (Button) findViewById(R.id.reset_stats_button);
        resetStats.setOnClickListener(this);
        //Call set stats method to display sats of logged in user in the text views
        setStats();

    }

    public void setStats() {
        //Set the games played, won drawn and lost stats for each difficulty. The values for which are retrieved from the arraylist which stores all of the users details. The logged in index is used to get the details of the correct user
        mPlayed.setText(Integer.toString(UserClass.Players.get(UserClass.getLoggedInIndex()).getmGamesPlayed()));
        // Calculation that works out a win percentage based on the number of games won and games played for medium difficulty
        double mWonPerc = (double)(Math.round(((double)UserClass.Players.get(UserClass.getLoggedInIndex()).getmGamesWon()/(double)UserClass.Players.get(UserClass.getLoggedInIndex()).getmGamesPlayed())*1000))/10;
        //Set text to the number of medium difficulty wins and the win percentage variable
        mWon.setText(Integer.toString(UserClass.Players.get(UserClass.getLoggedInIndex()).getmGamesWon()) + " (" + mWonPerc + "%)");
        // Calculation that works out a loss percentage based on the number of games lost and games played for medium difficulty
        double mLostPerc = (double)(Math.round(((double)UserClass.Players.get(UserClass.getLoggedInIndex()).getmGamesLost()/(double)UserClass.Players.get(UserClass.getLoggedInIndex()).getmGamesPlayed())*1000))/10;
        //Set text to the number of medium difficulty losses and the loss percentage variable
        mLost.setText(Integer.toString(UserClass.Players.get(UserClass.getLoggedInIndex()).getmGamesLost())  + " (" + mLostPerc + "%)");
        //Create and initialise a string that reads as 0%
        String mDrawnPerc = " (0.0%)";
        //Check if the user has played a medium game and set the percentage equal to 100 minus win and loss percentage
        if(UserClass.Players.get(UserClass.getLoggedInIndex()).getmGamesPlayed() > 0) {
            mDrawnPerc = " (" + (100-mWonPerc-mLostPerc) + "%)";
        }
        //Set medium draws text equal to games played minus games won and lost and draw percentage
        mDrawn.setText(Integer.toString(Integer.parseInt(mPlayed.getText().toString())-UserClass.Players.get(UserClass.getLoggedInIndex()).getmGamesWon()-UserClass.Players.get(UserClass.getLoggedInIndex()).getmGamesLost()) + mDrawnPerc);

        hPlayed.setText(Integer.toString(UserClass.Players.get(UserClass.getLoggedInIndex()).gethGamesPlayed()));
        // Calculation that works out a win percentage based on the number of games won and games played for hard difficulty
        double hWonPerc = (double)(Math.round(((double)UserClass.Players.get(UserClass.getLoggedInIndex()).gethGamesWon()/(double)UserClass.Players.get(UserClass.getLoggedInIndex()).gethGamesPlayed())*1000))/10;
        //Set text to the number of hard difficulty wins and the win percentage variable
        hWon.setText(Integer.toString(UserClass.Players.get(UserClass.getLoggedInIndex()).gethGamesWon()) + " (" + hWonPerc + "%)");
        // Calculation that works out a loss percentage based on the number of games lost and games played for hard difficulty
        double hLostPerc = (double)(Math.round(((double)UserClass.Players.get(UserClass.getLoggedInIndex()).gethGamesLost()/(double)UserClass.Players.get(UserClass.getLoggedInIndex()).gethGamesPlayed())*1000))/10;
        //Set text to the number of hard difficulty losses and the loss percentage variable
        hLost.setText(Integer.toString(UserClass.Players.get(UserClass.getLoggedInIndex()).gethGamesLost())  + " (" + hLostPerc + "%)");
        //Create and initialise a string that reads as 0%
        String hDrawnPerc = " (0.0%)";
        //Check if the user has played a hard game and set the percentage equal to 100 minus win and loss percentage
        if(UserClass.Players.get(UserClass.getLoggedInIndex()).gethGamesPlayed() > 0) {
            hDrawnPerc = " (" + (100-hWonPerc-hLostPerc) + "%)";
        }
        //Set hard draws text equal to games played minus games won and lost and draw percentage
        hDrawn.setText(Integer.toString(Integer.parseInt(hPlayed.getText().toString())-UserClass.Players.get(UserClass.getLoggedInIndex()).gethGamesWon()-UserClass.Players.get(UserClass.getLoggedInIndex()).gethGamesLost()) + hDrawnPerc);

        iPlayed.setText(Integer.toString(UserClass.Players.get(UserClass.getLoggedInIndex()).getiGamesPlayed()));
        // Calculation that works out a win percentage based on the number of games won and games played for impossible difficulty
        double iWonPerc = (double)(Math.round(((double)UserClass.Players.get(UserClass.getLoggedInIndex()).getiGamesWon()/(double)UserClass.Players.get(UserClass.getLoggedInIndex()).getiGamesPlayed())*1000))/10;
        //Set text to the number of impossible difficulty wins and the win percentage variable
        iWon.setText(Integer.toString(UserClass.Players.get(UserClass.getLoggedInIndex()).getiGamesWon()) + " (" + iWonPerc + "%)");
        // Calculation that works out a loss percentage based on the number of games lost and games played for impossible difficulty
        double iLostPerc = (double)(Math.round(((double)UserClass.Players.get(UserClass.getLoggedInIndex()).getiGamesLost()/(double)UserClass.Players.get(UserClass.getLoggedInIndex()).getiGamesPlayed())*1000))/10;
        //Set text to the number of impossible difficulty losses and the loss percentage variable
        iLost.setText(Integer.toString(UserClass.Players.get(UserClass.getLoggedInIndex()).getiGamesLost())  + " (" + iLostPerc + "%)");
        //Create and initialise a string that reads as 0%
        String iDrawnPerc = " (0.0%)";
        //Check if the user has played an impossible game and set the percentage equal to 100 minus win and loss percentage
        if(UserClass.Players.get(UserClass.getLoggedInIndex()).getiGamesPlayed() > 0) {
            iDrawnPerc = " (" + (100-iWonPerc-iLostPerc) + "%)";
        }
        //Set impossible draws text equal to games played minus games won and lost and draw percentage
        iDrawn.setText(Integer.toString(Integer.parseInt(iPlayed.getText().toString())-UserClass.Players.get(UserClass.getLoggedInIndex()).getiGamesWon()-UserClass.Players.get(UserClass.getLoggedInIndex()).getiGamesLost()) + iDrawnPerc);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.reset_stats_button):
                //Create alert dialog that asks the user if they are sure they want to reset their stats with Yes/No response
                AlertDialog.Builder builders = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.AlertDialog_style));
                builders.setTitle(R.string.reset_stats_question)
                        .setPositiveButton(R.string.alert_response_positive, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //Call reset stats method from UserClass
                                UserClass.resetStats();
                                try {
                                    //Create file output stream, output stream writer, and buffered writer to write to file
                                    FileOutputStream fos = openFileOutput("Users.txt", Context.MODE_PRIVATE);
                                    OutputStreamWriter osw = new OutputStreamWriter(fos);
                                    BufferedWriter bw = new BufferedWriter(osw);
                                    //Call write to file method from UserClass and pass the buffered reader in to it
                                    UserClass.writeToFile(bw);
                                    bw.close();
                                    //Call set stats method
                                    setStats();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        })
                        .setNegativeButton(R.string.alert_response_negative, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
                //Create alert dialog
                AlertDialog alert = builders.create();
                //Show alert dialog
                alert.show();
                break;
        }
    }
    @Override
    public void onBackPressed() {
        //Creare intent to create home screen activity and set animation for this and home screen activity
        Intent i = new Intent(stats_screen.this, HomeScreen.class);
        startActivity(i);
        overridePendingTransition(R.anim.activity_fade_in_back, R.anim.activity_fade_out_back);
    }
}
