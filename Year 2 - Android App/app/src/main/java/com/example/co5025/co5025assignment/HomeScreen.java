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
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class HomeScreen extends AppCompatActivity implements View.OnClickListener {

    //Create relevant Button and image variables for page
    Button newGame;
    Button account;
    Button stats;
    Button settings;
    ImageView info_logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        //Set ids and onclick listener for the buttons and imageview
        newGame = (Button) findViewById(R.id.menuNewGameButton);
        newGame.setOnClickListener(this);
        account = (Button) findViewById(R.id.menuAccountButton);
        account.setOnClickListener(this);
        stats = (Button) findViewById(R.id.menuStatsButton);
        stats.setOnClickListener(this);
        settings = (Button) findViewById(R.id.menuSettingsButton);
        settings.setOnClickListener(this);
        info_logo = (ImageView) findViewById(R.id.info_icon);
        info_logo.setOnClickListener(this);

        //Call this function to set the text of the account button
        setButtonText();

    }

    @Override
    public void onBackPressed() {
        //Create intent that puts app in background
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        //Create new intent
        Intent i;
        //Check what was clicked by id
        switch (v.getId()) {
            case (R.id.menuNewGameButton):
                //Start new intent that creates new activity
                i = new Intent(HomeScreen.this, MainActivity.class);
                startActivity(i);
                //Change activity animation
                overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
                //Check if user is logged in
                if (UserClass.getLoggedInIndex() == -1) {
                    //If they aren't logged in, display toast informing the user that their stats wont be recorded
                    Context context = getApplicationContext();
                    String text = getApplicationContext().getString(R.string.playing_as_guest);
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
                break;
            case (R.id.menuAccountButton):
                //Check if user is logged in
                if (UserClass.getLoggedInIndex() >= 0) {
                    //Display alert dialog that asks the user if they want to log out or delete account
                    final AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.AlertDialog_style));
                    //Set title of alert dialog
                    builder.setTitle(getApplicationContext().getString(R.string.user_options));
                    //Set poistive button to log out
                    builder.setPositiveButton(getApplicationContext().getString(R.string.logout), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int item) {
                            //Create another alert dialog that asks the user if they are sure with yes/no response
                            AlertDialog.Builder builders = new AlertDialog.Builder(new ContextThemeWrapper(HomeScreen.this, R.style.AlertDialog_style));
                            builders.setTitle(getApplicationContext().getString(R.string.logout_question))
                                    .setPositiveButton(R.string.alert_response_positive, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            //Toast letting the user know that they are logged out
                                            Context context = getApplicationContext();
                                            String text = getApplicationContext().getString(R.string.logged_out);
                                            int duration = Toast.LENGTH_SHORT;
                                            Toast toast = Toast.makeText(context, text, duration);
                                            toast.show();
                                            //Set logged out integer value to -1 in UserClass
                                            UserClass.setLoggedInIndex(-1);
                                            //Method to set text of Account button
                                            setButtonText();
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
                        }
                    });
                    //Set negative button to delete account
                    builder.setNegativeButton(getApplicationContext().getString(R.string.delete_account), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int item) {
                            //Create alert dialog asking if they are sure with Yes/No response
                            AlertDialog.Builder builders = new AlertDialog.Builder(new ContextThemeWrapper(HomeScreen.this, R.style.AlertDialog_style));
                            builders.setTitle(getApplicationContext().getString(R.string.delete_account_question))
                                    .setPositiveButton(R.string.alert_response_positive, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            try {
                                                //Create and display toast to inform user that the account has been deleted
                                                Context context = getApplicationContext();
                                                String text = getApplicationContext().getString(R.string.account_deleted_confirmation);
                                                int duration = Toast.LENGTH_SHORT;
                                                Toast toast = Toast.makeText(context, text, duration);
                                                toast.show();
                                                //Remove the user from the arraylist in UserClass
                                                UserClass.Players.remove(UserClass.getLoggedInIndex());
                                                //Set logged in to -1 because nobody is logged in
                                                UserClass.setLoggedInIndex(-1);
                                                //Reduce the number of records by 1
                                                int NumberOfRecords = UserClass.getNumberOfRecords() - 1;
                                                UserClass.setNumberOfRecords(NumberOfRecords);
                                                //Create file output stream, output stream writer and buffered writer to write to file
                                                FileOutputStream fos = openFileOutput("Users.txt", Context.MODE_PRIVATE);
                                                OutputStreamWriter osw = new OutputStreamWriter(fos);
                                                BufferedWriter bw = new BufferedWriter(osw);
                                                //Call UserClass write to file method and pass buffered reader in to it
                                                UserClass.writeToFile(bw);
                                                bw.close();
                                                //Set Account button text
                                                setButtonText();
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
                        }
                    });
                    //Create alert dialog
                    AlertDialog alert = builder.create();
                    //Show alert dialog
                    alert.show();
                } else {
                    //Go to login screen if user not logged in and set animation for creating new activity
                    i = new Intent(HomeScreen.this, activity_login_screen.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);

                }
                break;
            case (R.id.menuStatsButton):
                //Go to stats screen and set animation for creating new activity if user is logged in(UserClass logged in integer greater than -1)
                if (UserClass.getLoggedInIndex() > -1) {
                    i = new Intent(HomeScreen.this, stats_screen.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
                } else {
                    //Create and display toast informing user if they are not logged in
                    Context context = getApplicationContext();
                    String text = getApplicationContext().getString(R.string.stats_screen_prevention);
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
                break;
            case (R.id.menuSettingsButton):
                //Go to settings screen and set animation for creating new activity
                i = new Intent(HomeScreen.this, settings_screen.class);
                startActivity(i);
                overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
                break;
            case(R.id.info_icon):
                // Create alert dialog to show Game rules
                final AlertDialog.Builder builders = new AlertDialog.Builder(new ContextThemeWrapper(HomeScreen.this, R.style.AlertDialog_style));
                builders.setTitle(R.string.info_description)
                        .setMessage(getApplicationContext().getString(R.string.tic_tac_toe_rules))
                        .setNeutralButton(R.string.close_dialog, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //Close dialog
                                dialog.dismiss();
                            }
                        });
                //Create alert dialog
                AlertDialog alert = builders.create();
                //Show alert dialog
                alert.show();
                break;

        }
    }

    public void setButtonText() {
        //Check if user logged in and set relevant text if they are or relevant text if they are not for the account button
        if (UserClass.getLoggedInIndex() >= 0) {
            account.setText(R.string.logout_delete_account);
        } else {
            account.setText(R.string.menuAccountButton);
        }
    }
}
