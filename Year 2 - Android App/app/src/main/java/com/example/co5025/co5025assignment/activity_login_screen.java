package com.example.co5025.co5025assignment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class activity_login_screen extends AppCompatActivity implements View.OnClickListener {

    //Variables for user input and buttons
    EditText username;
    EditText password;
    Button login;
    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        //Set relevant ids and on click listeners for the edittexts and buttons
        username = (EditText) findViewById(R.id.username_edit_text);
        password = (EditText) findViewById(R.id.password_edit_text);
        login = (Button) findViewById(R.id.loginButton);
        login.setOnClickListener(this);
        register = (Button) findViewById(R.id.registerAccountButton);
        register.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        //Check id of clicked object
        switch (v.getId()) {
            case (R.id.loginButton):
                //Boolean to check if login is successful
                boolean loggedIn = false;
                //Loops through each record and checks if the username and password match an existing record's credentials that is being stored in the arraylist
                for (int c = 0; c < UserClass.getNumberOfRecords(); c++) {
                    if (username.getText().toString().equals(UserClass.Players.get(c).getUsername())) {
                        if (password.getText().toString().equals(UserClass.Players.get(c).getPassword())) {
                            //Display the username to the user to inform them that they have logged in
                            Context context = getApplicationContext();
                            String text = getApplicationContext().getString(R.string.logged_in_statement) + " " + UserClass.Players.get(c).getUsername();
                            int duration = Toast.LENGTH_SHORT;
                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();
                            //Set logged in index, so the logged in record can be referenced in the arraylist later on
                            UserClass.setLoggedInIndex(c);
                            //Animation and intent that takes user back to home screen
                            Intent i = new Intent(activity_login_screen.this, HomeScreen.class);
                            startActivity(i);
                            overridePendingTransition(R.anim.activity_fade_in_back, R.anim.activity_fade_out_back);
                            //Set logged in to true
                            loggedIn = true;
                            break;
                        }
                    }
                }
                //Checks if user is not logged in
                if (!loggedIn) {
                    //Clears edittexts and displays toast to inform user that they have entered invalid credentials
                    Context context = getApplicationContext();
                    String text = getApplicationContext().getString(R.string.login_validation);
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    username.setText("");
                    password.setText("");
                }
                break;
            case (R.id.registerAccountButton):
                //Start animation and intent to take user to register screen
                Intent i = new Intent(this, activity_register_screen.class);
                startActivity(i);
                overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
                break;
        }
    }
    @Override
    public void onBackPressed() {
        //Overide back button so that the home screen activity is created with a suitable animation
        Intent i = new Intent(activity_login_screen.this, HomeScreen.class);
        startActivity(i);
        overridePendingTransition(R.anim.activity_fade_in_back, R.anim.activity_fade_out_back);
    }
}
