package com.example.co5025.co5025assignment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class activity_register_screen extends AppCompatActivity implements View.OnClickListener {

    //Variables for user input and buttons as well as a string to hold a username and password
    Button register;
    EditText inputUsername;
    EditText inputPassword;
    EditText inputCPassword;
    String nUsername;
    String nPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen);

        //Set relevant ids and an on click listener for the edittexts and button
        register = (Button) findViewById(R.id.registerButton);
        register.setOnClickListener(this);
        inputUsername = (EditText) findViewById(R.id.username_edit_text);
        inputPassword = (EditText) findViewById(R.id.password_edit_text);
        inputCPassword = (EditText) findViewById(R.id.cpassword_edit_text);

    }

    @Override
    public void onClick(View v) {
        //Check id of clicked object
        switch (v.getId()) {
            case (R.id.registerButton):
                //Checks if both password edit texts are equal to eachother
                if (inputPassword.getText().toString().equals(inputCPassword.getText().toString())) {
                    //Checks if any of the editexts are empty
                    if (inputUsername.getText().toString().equals("") || inputPassword.getText().toString().equals("") || inputCPassword.getText().toString().equals("")) {
                    } else {
                        //If not then set username and password strings as the values in the username and password edittexts
                        nUsername = inputUsername.getText().toString();
                        nPassword = inputPassword.getText().toString();
                        //Set unique id to 2. This will be used to check if the user's credentials are unique
                        int UniqueID = 2;
                        //Loop throug each record
                        for (int b = 0; b < UserClass.getNumberOfRecords(); b++) {
                            if ((UserClass.Players.get(b).getUsername().equals(nUsername))) {
                                //If the username alrady exists, set the unique id to 1;
                                UniqueID = 1;
                                break;
                            }
                        }
                        if (UniqueID == 2) {
                            //If the username is unique then call add player method from UserClass and pass username and password strings in to it
                            UserClass.addNewPlayer(nUsername, nPassword);
                            try {
                                //Create Folie output stream, output stream writer and buffered writer to allow data to be entered line by line
                                FileOutputStream fos = openFileOutput("Users.txt", Context.MODE_PRIVATE);
                                OutputStreamWriter osw = new OutputStreamWriter(fos);
                                BufferedWriter bw = new BufferedWriter(osw);
                                //Call method to write to file from UserClass and pass the buffered writer in to it.
                                UserClass.writeToFile(bw);
                                bw.close();
                                //Display toast telling the user that they are logged in along with their username
                                Context context = getApplicationContext();
                                String text = getApplicationContext().getString(R.string.logged_in_statement) + " " + UserClass.Players.get(UserClass.getNumberOfRecords() - 1).getUsername();
                                int duration = Toast.LENGTH_SHORT;
                                Toast toast = Toast.makeText(context, text, duration);
                                toast.show();
                                //Set the logged in ID, stored in  UserClass
                                UserClass.setLoggedInIndex(UserClass.getNumberOfRecords() - 1);

                                //Animation and intent that takes user back to home screen
                                Intent i = new Intent(activity_register_screen.this, HomeScreen.class);
                                startActivity(i);
                                overridePendingTransition(R.anim.activity_fade_in_back, R.anim.activity_fade_out_back);

                            } catch (IOException e) {
                                e.printStackTrace();
                                //Display toast telling user that they havef failed to register
                                Context context = getApplicationContext();
                                String text = getApplicationContext().getString(R.string.register_fail);
                                int duration = Toast.LENGTH_SHORT;
                                Toast toast = Toast.makeText(context, text, duration);
                                toast.show();
                            }
                        } else {
                            //Display toast telling user that the username already exists
                            Context context = getApplicationContext();
                            String text = getApplicationContext().getString(R.string.existing_username);
                            int duration = Toast.LENGTH_SHORT;
                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();
                        }
                    }
                } else {
                    //Toast that tells the user that the entered details are invalid
                    Context context = getApplicationContext();
                    String text = getApplicationContext().getString(R.string.register_validation);
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
        }

    }

    @Override
    public void onBackPressed() {
        //Overide back button so that the login screen activity is created with a suitable animation
        Intent i = new Intent(activity_register_screen.this, activity_login_screen.class);
        startActivity(i);
        overridePendingTransition(R.anim.activity_fade_in_back, R.anim.activity_fade_out_back);
    }
}
