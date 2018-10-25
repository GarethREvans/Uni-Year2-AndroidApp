package com.example.co5025.co5025assignment;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //Create require variables to store media, buttons, animation, table rows, integers and GameClass
    Button[][] cells = new Button[3][3];
    Button newGame;
    int[][] ids = {{R.id.cell1, R.id.cell2, R.id.cell3}, {R.id.cell4, R.id.cell5, R.id.cell6}, {R.id.cell7, R.id.cell8, R.id.cell9}};
    GameClass game;
    Animation cellAnim;
    MediaPlayer winSoundEffect;
    MediaPlayer drawSoundEffect;
    MediaPlayer loseSoundEffect;
    MediaPlayer tapSoundEffect;
    TableRow firstRow;
    TableRow secondRow;
    TableRow thirdRow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Set animation for cellAnim
        cellAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.cell_anim);
        //Link sound file to be played for each MediaPlayer variable
        winSoundEffect = MediaPlayer.create(this, R.raw.win_se);    // POWER. (2015, April 25). Epic Win Sound Effects [Video file]. Retrieved from https://www.youtube.com/watch?v=GGoYBJvPEdY
        drawSoundEffect = MediaPlayer.create(this, R.raw.draw_se);  // POWER. (2015, April 25). Epic Fail Sound Effects [Video file]. Retrieved from https://www.youtube.com/watch?v=MDE-_UyRBbs
        loseSoundEffect = MediaPlayer.create(this, R.raw.lose_se);  // POWER. (2015, April 25). Epic Fail Sound Effects [Video file]. Retrieved from https://www.youtube.com/watch?v=MDE-_UyRBbs
        tapSoundEffect = MediaPlayer.create(this, R.raw.tap_se);    //SoundEffectsFactory. (2013, March 8). Typing On iPhone Sound Effect [VideoFile]. Retrieved from https://www.youtube.com/watch?v=johVa_3lnmY
        //Set ids for the table rows for the game grid
        firstRow = (TableRow) findViewById(R.id.first_row);
        secondRow = (TableRow) findViewById(R.id.second_row);
        thirdRow = (TableRow) findViewById(R.id.third_row);
        //Loops to set the id and on click listener for each button in the cells 2D array
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                cells[i][j] = (Button) findViewById(ids[i][j]);
                cells[i][j].setOnClickListener(this);

            }
        }
        //Call clear grid method to reset activity states and properties
        ClearGrid();

        //Set id and on click listener for the new game button
        newGame = (Button) findViewById(R.id.newGame);
        newGame.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        //Create intent that starts home screen activity and sets relevant animation
        Intent i = new Intent(MainActivity.this, HomeScreen.class);
        startActivity(i);
        overridePendingTransition(R.anim.activity_fade_in_back, R.anim.activity_fade_out_back);
    }

    public void ClearGrid() {
        //Create new instance of GameClass
        game = new GameClass();
        //Loop through the cells 2D array and claer each buttons text and animation and enable them
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                cells[i][j].setText("");
                cells[i][j].setEnabled(true);
                cells[i][j].clearAnimation();

                //Reset the colour of the text and backgrounds for the cells and table rows according to the colour theme variable value from UserClass
                if (UserClass.getColour_theme() == 3){
                    cells[i][j].setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
                    cells[i][j].setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent));
                    firstRow.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark));
                    secondRow.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark));
                    thirdRow.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark));
                } else if (UserClass.getColour_theme() == 2) {
                    cells[i][j].setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark));
                    cells[i][j].setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent));
                    firstRow.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
                    secondRow.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
                    thirdRow.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
                } else if (UserClass.getColour_theme() == 1){
                    cells[i][j].setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent));
                    cells[i][j].setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark));
                    firstRow.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
                    secondRow.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
                    thirdRow.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
                }
            }
        }
        //Make AI move if the player has selected to go second
        if (UserClass.getPlayer_turn() == 2) {
            game.PlayerPointsBoard[0][0] += 1;
            game.PlayerPointsBoard[0][2] += 1;
            game.PlayerPointsBoard[1][1] += 1;
            game.PlayerPointsBoard[2][0] += 1;
            game.PlayerPointsBoard[2][2] += 1;
            game.aiTurn();
            cells[game.tempY][game.tempX].setText("O");
            cells[game.tempY][game.tempX].setEnabled(false);
            game.PlayerPointsBoard[0][0] -= 1;
            game.PlayerPointsBoard[0][2] -= 1;
            game.PlayerPointsBoard[1][1] -= 1;
            game.PlayerPointsBoard[2][0] -= 1;
            game.PlayerPointsBoard[2][2] -= 1;
        }
    }

    @Override
    public void onClick(View v) {
        //Initialise the click success boolean. This checks whether a cell has been clicked
        boolean clickSuccess = false;
        //Call whoWin method from the game instance and assign its value to an integer variable
        int whoWin = game.WhoWin();
        //Check if game has finished (==3)
        if (whoWin == 3) {
            //Check which cell was clicked and set the relevant properties of that cell
            // Also, start the tap sound effect, and set clickSuccess to true as well passing a value to the set Game Board points method in the ga,e instance
            switch (v.getId()) {
                case R.id.cell1:
                    cells[0][0].setText("X");
                    cells[0][0].setEnabled(false);
                    game.setGameBoardPoints(1);
                    clickSuccess = true;
                    tapSoundEffect.start();
                    break;
                case R.id.cell2:
                    cells[0][1].setText("X");
                    cells[0][1].setEnabled(false);
                    game.setGameBoardPoints(2);
                    clickSuccess = true;
                    tapSoundEffect.start();
                    break;
                case R.id.cell3:
                    cells[0][2].setText("X");
                    cells[0][2].setEnabled(false);
                    game.setGameBoardPoints(3);
                    clickSuccess = true;
                    tapSoundEffect.start();
                    break;
                case R.id.cell4:
                    cells[1][0].setText("X");
                    cells[1][0].setEnabled(false);
                    game.setGameBoardPoints(4);
                    clickSuccess = true;
                    tapSoundEffect.start();
                    break;
                case R.id.cell5:
                    cells[1][1].setText("X");
                    cells[1][1].setEnabled(false);
                    game.setGameBoardPoints(5);
                    clickSuccess = true;
                    tapSoundEffect.start();
                    break;
                case R.id.cell6:
                    cells[1][2].setText("X");
                    cells[1][2].setEnabled(false);
                    game.setGameBoardPoints(6);
                    clickSuccess = true;
                    tapSoundEffect.start();
                    break;
                case R.id.cell7:
                    cells[2][0].setText("X");
                    cells[2][0].setEnabled(false);
                    game.setGameBoardPoints(7);
                    clickSuccess = true;
                    tapSoundEffect.start();
                    break;
                case R.id.cell8:
                    cells[2][1].setText("X");
                    cells[2][1].setEnabled(false);
                    game.setGameBoardPoints(8);
                    clickSuccess = true;
                    tapSoundEffect.start();
                    break;
                case R.id.cell9:
                    cells[2][2].setText("X");
                    cells[2][2].setEnabled(false);
                    game.setGameBoardPoints(9);
                    clickSuccess = true;
                    tapSoundEffect.start();
                    break;
            }
        }
        //Check if the new game button was clicked
        if (v.getId() == R.id.newGame) {
            //Call clear grid method to reset properties and states in the activity
            ClearGrid();

        }

        //Call whoWin method from the game instance and assign its value to an integer variable
        int whoWin2 = game.WhoWin();
        //Check if integer equal to 3 and click success is true
        if (whoWin2 == 3 && clickSuccess) {
            //Call AI turn method and set O in the cell that the method decided was correct and disable it
            game.aiTurn();
            cells[game.tempY][game.tempX].setText("O");
            cells[game.tempY][game.tempX].setEnabled(false);
        }
        //Call whoWin method from the game instance and assign its value to an integer variable
        int whoWin3 = game.WhoWin();
        //Check if integer equal to 2 (Draw)
        if (whoWin3 == 2) {
            //Loop through every cell in the cells 2D button array and start the relevant animation as well as set its background colour the draw colour
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    cells[i][j].setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorDraw));
                    cells[i][j].startAnimation(cellAnim);
                }
            }
            //Create and display a toast that informs the user that they have drawn
            Toast toast = Toast.makeText(getApplicationContext(), R.string.you_draw, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            //Start draw sound effect
            drawSoundEffect.start();
            //Check if integer equal to 1 (Win)
        } else if (whoWin3 == 1) {
            //Create and display a toast that informs the user that they have won
            Toast toast = Toast.makeText(getApplicationContext(), R.string.you_win, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            //Start win sound
            winSoundEffect.start();
            //Call method to determine colour of winner
            setWinningLineColour("X");
            //Record data if User logged in.
            if (UserClass.getLoggedInIndex() != -1) {
                //Add 1 to ganes won for curret difficulty
                if(UserClass.getGame_difficulty() == 1) {
                    UserClass.Players.get(UserClass.getLoggedInIndex()).setmGamesWon(UserClass.Players.get(UserClass.getLoggedInIndex()).getmGamesWon()+1);
                } else if(UserClass.getGame_difficulty() == 2) {
                    UserClass.Players.get(UserClass.getLoggedInIndex()).sethGamesWon(UserClass.Players.get(UserClass.getLoggedInIndex()).gethGamesWon()+1);
                } if(UserClass.getGame_difficulty() == 3) {
                    UserClass.Players.get(UserClass.getLoggedInIndex()).setiGamesWon(UserClass.Players.get(UserClass.getLoggedInIndex()).getiGamesWon()+1);
                }
                //Call update record to update Users file
                updatePlayerRecord();
            }
        }
        //Check if whoWin equal to -1 (Lose)
        else if (whoWin3 == -1) {
            //Create and display toast to inform user they have won
            Toast toast = Toast.makeText(getApplicationContext(), R.string.you_lose, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            //Start lose sound effect
            loseSoundEffect.start();
            //Call method to determine colour of winner
            setWinningLineColour("O");
            //Checks if User is logged in, else stats aren't recorded
            if (UserClass.getLoggedInIndex() != -1) {
                //Adds 1 to loss stat for curret difficulty
                if(UserClass.getGame_difficulty() == 1) {
                    UserClass.Players.get(UserClass.getLoggedInIndex()).setmGamesLost(UserClass.Players.get(UserClass.getLoggedInIndex()).getmGamesLost()+1);
                } else if(UserClass.getGame_difficulty() == 2) {
                    UserClass.Players.get(UserClass.getLoggedInIndex()).sethGamesLost(UserClass.Players.get(UserClass.getLoggedInIndex()).gethGamesLost()+1);
                } if(UserClass.getGame_difficulty() == 3) {
                    UserClass.Players.get(UserClass.getLoggedInIndex()).setiGamesLost(UserClass.Players.get(UserClass.getLoggedInIndex()).getiGamesLost()+1);
                }
                //Call update record to update Users file
                updatePlayerRecord();
            }
        }
        //Checks if game is finished and loops through each cell and disables it
        if (whoWin3 == 2||whoWin3 == 1||whoWin3 == -1) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    cells[i][j].setEnabled(false);
                }
            }
            //Checks if user is logged in
            if (UserClass.getLoggedInIndex() != -1) {
                //Adds 1 to games played for current difficulty
                if(UserClass.getGame_difficulty() == 1) {
                    UserClass.Players.get(UserClass.getLoggedInIndex()).setmGamesPlayed(UserClass.Players.get(UserClass.getLoggedInIndex()).getmGamesPlayed()+1);
                } else if(UserClass.getGame_difficulty() == 2) {
                    UserClass.Players.get(UserClass.getLoggedInIndex()).sethGamesPlayed(UserClass.Players.get(UserClass.getLoggedInIndex()).gethGamesPlayed()+1);
                } if(UserClass.getGame_difficulty() == 3) {
                    UserClass.Players.get(UserClass.getLoggedInIndex()).setiGamesPlayed(UserClass.Players.get(UserClass.getLoggedInIndex()).getiGamesPlayed()+1);
                }
                //Call update record to update Users file
                updatePlayerRecord();
            }
        }
    }

    public void setWinningLineColour(String winningCharacter) {
        //Initialise integer to determine winning colour and a boolean to determine whether a colour has been set
        int winningColour;
        boolean colourNotSet = true;
        //Checks who has won and sets the winning coloour accordingly
        if (winningCharacter.equals("X")) {
            winningColour = ContextCompat.getColor(getApplicationContext(), R.color.colorWin);
        } else {
            winningColour = ContextCompat.getColor(getApplicationContext(), R.color.colorLose);
        }
        //These if statements check if a diagonal line is a winning line and sets the background colour of these cells and starts the cell animation accordingly
        if (cells[0][0].getText() == winningCharacter && cells[1][1].getText() == winningCharacter && cells[2][2].getText() == winningCharacter) {
            cells[0][0].setBackgroundColor(winningColour);
            cells[0][0].startAnimation(cellAnim);
            cells[1][1].setBackgroundColor(winningColour);
            cells[1][1].startAnimation(cellAnim);
            cells[2][2].setBackgroundColor(winningColour);
            cells[2][2].startAnimation(cellAnim);
            colourNotSet = false;
        } else if (cells[0][2].getText() == winningCharacter && cells[1][1].getText() == winningCharacter && cells[2][0].getText() == winningCharacter) {
            cells[0][2].setBackgroundColor(winningColour);
            cells[0][2].startAnimation(cellAnim);
            cells[1][1].setBackgroundColor(winningColour);
            cells[1][1].startAnimation(cellAnim);
            cells[2][0].setBackgroundColor(winningColour);
            cells[2][0].startAnimation(cellAnim);
            colourNotSet = false;
        }
        //Checks if the colour hasn't been set
        if (colourNotSet) {
            //Loops through each row to see if there is a winning line and sets the baclground colour and starts the cell animation accordingly
            for (int i = 0; i < 3; i++) {
                if (cells[i][0].getText() == cells[i][1].getText() && cells[i][0].getText() == cells[i][2].getText() && cells[i][0].getText() == winningCharacter) {
                    cells[i][0].setBackgroundColor(winningColour);
                    cells[i][0].startAnimation(cellAnim);
                    cells[i][1].setBackgroundColor(winningColour);
                    cells[i][1].startAnimation(cellAnim);
                    cells[i][2].setBackgroundColor(winningColour);
                    cells[i][2].startAnimation(cellAnim);
                    colourNotSet = false;
                    break;
                }
            }
        }
        //Check if colour not set
        if (colourNotSet) {
            //Loops through each column to see if there is a winning line and sets the baclground colour and starts the cell animation accordingly
            for (int i = 0; i < 3; i++) {
                if (cells[0][i].getText() == cells[1][i].getText() && cells[0][i].getText() == cells[2][i].getText() && cells[0][i].getText() == winningCharacter) {
                    cells[0][i].setBackgroundColor(winningColour);
                    cells[0][i].startAnimation(cellAnim);
                    cells[1][i].setBackgroundColor(winningColour);
                    cells[1][i].startAnimation(cellAnim);
                    cells[2][i].setBackgroundColor(winningColour);
                    cells[2][i].startAnimation(cellAnim);
                    break;
                }
            }
        }
    }
    public void updatePlayerRecord() {
        try {
            //Write to Users file the updated array list
            FileOutputStream fos = openFileOutput("Users.txt", Context.MODE_PRIVATE);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            BufferedWriter bw = new BufferedWriter(osw);
            //Call write to file method to write the array list to the Users file
            UserClass.writeToFile(bw);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}