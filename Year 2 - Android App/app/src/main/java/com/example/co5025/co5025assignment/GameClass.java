package com.example.co5025.co5025assignment;

import java.util.Random;

public class GameClass {

    // Create 2D array to store player and AI positions, and to store points generated, based on player positions They are initialised as a 3 by 3 array with values of 0
    int[][] behindBoard = {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};
    int[][] PlayerPointsBoard = {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};
    //Integers to store a winner value and positions for the AI to make its move
    int tempWinner = 0;
    int tempY = -1;
    int tempX = -1;
    //Random variable that will return a random value
    Random changeCell;

    //Set points for each cell based on the move made by the user, which is passed in to the method
    public void setGameBoardPoints(int move) {
        //Set behindboard value to 1 based on which cell was clicked. Also set relevant PlayerPointsBoard points depending on position
        switch (move) {
            case (1):
                behindBoard[0][0] = 1;
                PlayerPointsBoard[0][0] -= 100;
                PlayerPointsBoard[0][1] += 2;
                PlayerPointsBoard[0][2] += 5;
                PlayerPointsBoard[1][0] += 2;
                PlayerPointsBoard[2][0] += 5;
                PlayerPointsBoard[1][1] += 6;
                PlayerPointsBoard[2][2] += 5;
                break;
            case (2):
                behindBoard[0][1] = 1;
                PlayerPointsBoard[0][1] -= 100;
                PlayerPointsBoard[0][0] += 5;
                PlayerPointsBoard[0][2] += 5;
                PlayerPointsBoard[1][1] += 6;
                PlayerPointsBoard[2][1] += 2;
                break;
            case (3):
                behindBoard[0][2] = 1;
                PlayerPointsBoard[0][2] -= 100;
                PlayerPointsBoard[0][0] += 5;
                PlayerPointsBoard[0][1] += 2;
                PlayerPointsBoard[1][2] += 2;
                PlayerPointsBoard[2][2] += 5;
                PlayerPointsBoard[1][1] += 6;
                PlayerPointsBoard[2][0] += 5;
                break;
            case (4):
                behindBoard[1][0] = 1;
                PlayerPointsBoard[1][0] -= 100;
                PlayerPointsBoard[0][0] += 5;
                PlayerPointsBoard[2][0] += 5;
                PlayerPointsBoard[1][1] += 6;
                PlayerPointsBoard[1][2] += 2;
                break;
            case (5):
                behindBoard[1][1] = 1;
                PlayerPointsBoard[1][1] -= 100;
                PlayerPointsBoard[0][0] += 5;
                PlayerPointsBoard[0][1] += 2;
                PlayerPointsBoard[0][2] += 5;
                PlayerPointsBoard[1][0] += 2;
                PlayerPointsBoard[1][2] += 2;
                PlayerPointsBoard[2][0] += 5;
                PlayerPointsBoard[2][1] += 2;
                PlayerPointsBoard[2][2] += 5;
                break;
            case (6):
                behindBoard[1][2] = 1;
                PlayerPointsBoard[1][2] -= 100;
                PlayerPointsBoard[0][2] += 5;
                PlayerPointsBoard[2][2] += 5;
                PlayerPointsBoard[1][1] += 6;
                PlayerPointsBoard[1][0] += 2;
                break;
            case (7):
                behindBoard[2][0] = 1;
                PlayerPointsBoard[2][0] -= 100;
                PlayerPointsBoard[0][0] += 5;
                PlayerPointsBoard[1][0] += 2;
                PlayerPointsBoard[2][1] += 2;
                PlayerPointsBoard[2][2] += 5;
                PlayerPointsBoard[1][1] += 6;
                PlayerPointsBoard[0][2] += 5;
                break;
            case (8):
                behindBoard[2][1] = 1;
                PlayerPointsBoard[2][1] -= 100;
                PlayerPointsBoard[0][1] += 2;
                PlayerPointsBoard[1][1] += 6;
                PlayerPointsBoard[2][0] += 5;
                PlayerPointsBoard[2][2] += 5;
                break;
            case (9):
                behindBoard[2][2] = 1;
                PlayerPointsBoard[2][2] -= 100;
                PlayerPointsBoard[0][2] += 5;
                PlayerPointsBoard[1][2] += 2;
                PlayerPointsBoard[0][0] += 5;
                PlayerPointsBoard[1][1] += 6;
                PlayerPointsBoard[2][0] += 5;
                PlayerPointsBoard[2][1] += 2;
                break;
        }
    }

    //Determines who has won and sets the tempWinner value accordingly
    public int WhoWin() {
        //Set won = 0 This is used to check if anyone has won or drawn
        int won = 0;
        //setTempWinner equal to 3. This will be used to determine
        tempWinner = 3;
        //Checks if either of the diagonal lines are occupied by a row of 1s or -1s in the behind board array
        if ((behindBoard[0][0] == behindBoard[1][1] && behindBoard[0][0] == behindBoard[2][2] || behindBoard[2][0] == behindBoard[1][1] && behindBoard[2][0] == behindBoard[0][2]) && (behindBoard[1][1] == 1 || behindBoard[1][1] == -1)) {
            //If true, set the winning value (1 or -1) based on what value occupies the middle cell
            tempWinner = behindBoard[1][1];
            //Set won = 1
            won = 1;
        }
        //Checks if the game has already finished
        if (won != 1) {
            //Checks each column for a row of 1s or -1s
            for (int i = 0; i < 3; i++) {
                if ((behindBoard[0][i] == behindBoard[1][i] && behindBoard[0][i] == behindBoard[2][i]) && (behindBoard[0][i] == 1 || behindBoard[0][i] == -1)) {
                    //Sets winning value
                    tempWinner = behindBoard[1][i];
                    won = 1;
                }
            }
        }
        //Check if game is finished
        if (won != 1) {
            //Check each row for a row of 1s or -1s
            for (int i = 0; i < 3; i++) {
                if ((behindBoard[i][0] == behindBoard[i][1] && behindBoard[i][0] == behindBoard[i][2]) && (behindBoard[i][0] == 1 || behindBoard[i][0] == -1)) {
                    //Sets wining value
                    tempWinner = behindBoard[i][1];
                    won = 1;
                }
            }
        }
        //Check if game finished
        if (won != 1) {
            //Check every element in 2D array to see if any elements equal 0. If so, the game is not finished
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (behindBoard[i][j] == 0) {
                        //Set winning value to 3 which means no one has won
                        tempWinner = 3;
                        won = 1;
                    }
                }
            }
        }
        //If non of the other checks result in a win or there are no elements equal to 0 in the 2D array, the game must be a draw.
        if (won != 1) {
            //Set value to 2 which means a draw
            tempWinner = 2;
        }
        //Return the winning value
        return tempWinner;
    }

    public void aiTurn() {
        //Integer that is used to check if mave has been made
        int moveMade = 0;
        //Perform certain checks if difficulty, stored in Userclass, is medium
        if (UserClass.getGame_difficulty() == 1) {
            //The moveMade Integer is passed in to these methods and will return as 1 if the move has been made or 0 if not.
            moveMade = mediumAI(moveMade);
            //Execute default AI if move hasn't been made
            if (moveMade == 0) {
                moveMade = defaultAI(moveMade);
            }
        }
        //Perform certain checks if difficulty is hard
        else if (UserClass.getGame_difficulty() == 2) {
            moveMade = mediumAI(moveMade);
            //Hard AI is executed if no move is made
            if (moveMade == 0) {
                moveMade = hardAI(moveMade);
                //Default AI is executed if no move has been made
                if (moveMade == 0) {
                    moveMade = defaultAI(moveMade);
                }
            }
        }
        //Perform certain checks if difficulty is Impossible
        else if (UserClass.getGame_difficulty() == 3) {
            moveMade = mediumAI(moveMade);
            //Execute hard AI if move not made
            if (moveMade == 0) {
                moveMade = hardAI(moveMade);
                //Execute impossible AI if no move made
                if (moveMade == 0) {
                    moveMade = impossibleAI(moveMade);
                    //Execute default AI if move not made
                    if (moveMade == 0) {
                        moveMade = defaultAI(moveMade);
                    }
                }
            }
        }
    }

    public int defaultAI(int moveMade) {
        //Check if move made
        if (moveMade == 0) {
            //Initialise danger points and temporary 2D array pointers
            int dangerPoints = 0;
            int tempi = 0;
            int tempj = 0;
            //Go through every element in PlayerPointsBoard 2D array
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    //If current value is greater than danger points
                    if (PlayerPointsBoard[i][j] > dangerPoints) {
                        //Set danger points to current value
                        dangerPoints = PlayerPointsBoard[i][j];
                        //Set pointers for 2D array
                        tempi = i;
                        tempj = j;
                    }
                    //Check if current value is equal to danger points
                    else if (PlayerPointsBoard[i][j] == dangerPoints) {
                        //If the returned value from the random function is false
                        if(!randomValue()) {
                            //Setpointers for 2D array
                            tempi = i;
                            tempj = j;
                        }
                    }
                }
            }
            //Set value of -1 for chosen elemet in behindBoard 2D array
            behindBoard[tempi][tempj] = -1;
            //Set the Y and X variables to the i and j variables
            tempY = tempi;
            tempX = tempj;
            //Minus points from The curret position in the PlayerPointsBoard 2D array
            PlayerPointsBoard[tempi][tempj] -= 100;
        }
        //Return whether a move has been made
        return moveMade;
    }

    public int mediumAI(int moveMade) {
        // Add the 2 diagonal lines values from behind board 2D array
        int lineTotal1 = behindBoard[0][0] + behindBoard[1][1] + behindBoard[2][2];
        int lineTotal2 = behindBoard[0][2] + behindBoard[1][1] + behindBoard[2][0];
        //Check if line total 1 is equal to -2
        if (lineTotal1 == -2) {
            //Call line check method
            line1Check();
            //Move has been made so move made variable equals 1
            moveMade = 1;
        }
        //Check if line total 2 is equal to -2
        else if (lineTotal2 == -2) {
            //Call line check 2 method
            line2Check();
            //set move made variable equal to 1
            moveMade = 1;
        }
        //Check if move has been made
        else if (moveMade == 0) {
            //Loops through each column for a row of two -1 values in the behind board 2D array
            for (int j = 0; j < 3; j++) {
                //Check if each column is equal to -2
                if ((behindBoard[0][j] + behindBoard[1][j] + behindBoard[2][j]) == -2) {
                    //Call check column method and pass j integer in to it
                    checkColumn(j);
                    //Set move made variable equal to 1
                    moveMade = 1;
                    break;
                }
                //Loops through each row of behind board 2D array
                else if ((behindBoard[j][0] + behindBoard[j][1] + behindBoard[j][2]) == -2) {
                    //Call check row method and pass j integer in to it
                    checkRow(j);
                    moveMade = 1;
                }
            }
        }
        //Return move made integer
        return moveMade;
    }

    public int hardAI(int moveMade) {
        // Add the 2 diagonal lines values from behind board 2D array
        int lineTotal1 = behindBoard[0][0] + behindBoard[1][1] + behindBoard[2][2];
        int lineTotal2 = behindBoard[0][2] + behindBoard[1][1] + behindBoard[2][0];
        //Check if move has been made
        if (moveMade == 0) {
            //Check if line total 1 integer is equal to 2
            if (lineTotal1 == 2) {
                //Call line 1 check method
                line1Check();
                //Set move made integer to 1
                moveMade = 1;
            }
            //Check if line total 2 integer is equal to 2
            else if (lineTotal2 == 2) {
                //Call line 2 check method
                line2Check();
                //Set move made integer to 1
                moveMade = 1;
            }
            //Check if move has been made
            else if (moveMade == 0) {
                //Loop through each column of behind board 2D array
                for (int j = 0; j < 3; j++) {
                    //Check if a column of the behind boards 2D array, is equal to 2
                    if ((behindBoard[0][j] + behindBoard[1][j] + behindBoard[2][j]) == 2) {
                        //Call check column method and pass the j integer in to it
                        checkColumn(j);
                        //Set the mive made integer to 1
                        moveMade = 1;
                        break;
                    }
                    //Check if a row of the behind board 2D array, is equal to 2
                    else if ((behindBoard[j][0] + behindBoard[j][1] + behindBoard[j][2]) == 2) {
                        //Call check row method and pass j integer in to it
                        checkRow(j);
                        //Set move made integer equal to 1
                        moveMade = 1;
                    }
                }
            }
        }
        //Return the move made integer
        return moveMade;
    }

    public int impossibleAI(int moveMade) {
        //Checks if Player has a 1 positioned in opposite corners at the same time in the behind board 2D array
        if ((behindBoard[0][0] == 1 && behindBoard[2][2] == 1)||(behindBoard[0][2] == 1 && behindBoard[2][0] == 1)) {
            //Checks if any of the 4 middles around the edge of the game grid are occupied by the AI in the behind board 2D array
            if(behindBoard[0][1] != -1 && behindBoard[1][0] != -1 && behindBoard[1][2] != -1 && behindBoard[2][1] != -1) {
                //Checks if an outside middle is available and occupies the first one that is.
                //The relevant pointers to the location of the made move are set and the relevant values are given to the elements in the behind board and Player points arrays
                if(behindBoard[0][1] != 1) {
                    behindBoard[0][1] = -1;
                    tempY = 0;
                    tempX = 1;
                    PlayerPointsBoard[0][1] -= 100;
                    moveMade = 1;
                } else if(behindBoard[1][0] != 1) {
                    behindBoard[1][0] = -1;
                    tempY = 1;
                    tempX = 0;
                    PlayerPointsBoard[1][0] -= 100;
                    moveMade = 1;
                } else if(behindBoard[1][2] != 1) {
                    behindBoard[1][2] = -1;
                    tempY = 1;
                    tempX = 2;
                    PlayerPointsBoard[1][2] -= 100;
                    moveMade = 1;
                } else if(behindBoard[2][1] != 1) {
                    behindBoard[2][1] = -1;
                    tempY = 2;
                    tempX = 1;
                    PlayerPointsBoard[2][1] -= 100;
                    moveMade = 1;
                }
            }
        }
        //Return the move made integer
        return moveMade;
    }

    public void line1Check() {
        //Checks which element in the behind board 2D array is equal to 0 in the first diagonal line
        //The relevant pointers to the location of the made move are set and the relevant values are given to the elements in the behind board and Player points arrays
        if (behindBoard[0][0] == 0) {
            behindBoard[0][0] = -1;
            tempY = 0;
            tempX = 0;
            PlayerPointsBoard[0][0] -= 100;
        } else if (behindBoard[1][1] == 0) {
            behindBoard[1][1] = -1;
            tempY = 1;
            tempX = 1;
            PlayerPointsBoard[1][1] -= 100;
        } else if (behindBoard[2][2] == 0) {
            behindBoard[2][2] = -1;
            tempY = 2;
            tempX = 2;
            PlayerPointsBoard[2][2] -= 100;
        }
    }

    public void line2Check() {
        //Checks which element in the behind board 2D array is equal to 0 in the second diagonal line
        //The relevant pointers to the location of the made move are set and the relevant values are given to the elements in the behind board and Player points arrays
        if (behindBoard[0][2] == 0) {
            behindBoard[0][2] = -1;
            tempY = 0;
            tempX = 2;
            PlayerPointsBoard[0][2] -= 100;
        } else if (behindBoard[1][1] == 0) {
            behindBoard[1][1] = -1;
            tempY = 1;
            tempX = 1;
            PlayerPointsBoard[1][1] -= 100;
        } else if (behindBoard[2][0] == 0) {
            behindBoard[2][0] = -1;
            tempY = 2;
            tempX = 0;
            PlayerPointsBoard[2][0] -= 100;
        }
    }

    public void checkColumn(int j) {
        //Checks which element in the behind board 2D array is equal to 0 in each column
        //The relevant pointers to the location of the made move are set and the relevant values are given to the elements in the behind board and Player points arrays
        for (int i = 0; i < 3; i++) {
            if (behindBoard[i][j] == 0) {
                behindBoard[i][j] = -1;
                tempY = i;
                tempX = j;
                PlayerPointsBoard[i][j] -= 100;
                break;
            }
        }
    }

    public void checkRow(int j) {
        //Checks which element in the behind board 2D array is equal to 0 in each row
        //The relevant pointers to the location of the made move are set and the relevant values are given to the elements in the behind board and Player points arrays
        for (int i = 0; i < 3; i++) {
            if (behindBoard[j][i] == 0) {
                behindBoard[j][i] = -1;
                tempY = j;
                tempX = i;
                PlayerPointsBoard[j][i] -= 100;
                break;
            }
        }
    }

    public boolean randomValue() {

        //Set Random variable eqal to a new random boolean
        changeCell = new Random();

        //Return the boolean
        return changeCell.nextBoolean();
    }

}
