package com.example.co5025.co5025assignment;



import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

public class UserClass {
    //Create new arraylist called Players
    static ArrayList<Users> Players = new ArrayList<>();
    //Create and initialise integers for number of records, logged in index, game difficulty, colour theme and player turn
    static int NumberOfRecords = 0;
    static int loggedInIndex = -1;
    static int game_difficulty = 0;
    static int colour_theme = 0;
    static int player_turn = 0;

    public static void createArrayList(BufferedReader br) {
        try {
            //Create new instance of User class and set each field to a new line that is read from the Users file
            Users User = new Users();
            User.setPersonID(Integer.parseInt(br.readLine()));
            User.setUsername(br.readLine());
            User.setPassword(br.readLine());
            User.setmGamesPlayed(Integer.parseInt(br.readLine()));
            User.setmGamesWon(Integer.parseInt(br.readLine()));
            User.setmGamesLost(Integer.parseInt(br.readLine()));
            User.sethGamesPlayed(Integer.parseInt(br.readLine()));
            User.sethGamesWon(Integer.parseInt(br.readLine()));
            User.sethGamesLost(Integer.parseInt(br.readLine()));
            User.setiGamesPlayed(Integer.parseInt(br.readLine()));
            User.setiGamesWon(Integer.parseInt(br.readLine()));
            User.setiGamesLost(Integer.parseInt(br.readLine()));
            //Add the new instance of User to the arraylist
            Players.add(User);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Set the number of records variable
    public static void setNumberOfRecords(int x) {
        NumberOfRecords = x;
    }

    //Return number of records variable
    public static int getNumberOfRecords() {
        return NumberOfRecords;
    }

    public static void addNewPlayer(String Username, String Password) {
        //Add a new player to the arraylist
        int newID;
        //Checks if there are no records in the file and sets new id accordingly
        if (NumberOfRecords == 0) {
            newID = 0;
        } else {
            newID = Players.get(NumberOfRecords-1).getPersonID()+1;
        }
        //Create new inastance of User class and set the relevant fields with the user credentials and default stats
        Users User = new Users();
        User.setPersonID(newID);
        User.setUsername(Username);
        User.setPassword(Password);
        User.setmGamesPlayed(0);
        User.setmGamesWon(0);
        User.setmGamesLost(0);
        User.sethGamesPlayed(0);
        User.sethGamesWon(0);
        User.sethGamesLost(0);
        User.setiGamesPlayed(0);
        User.setiGamesWon(0);
        User.setiGamesLost(0);
        //Add user to arraylist
        Players.add(User);
        //Increase number of records by 1
        NumberOfRecords++;
    }

    public static void writeToFile(BufferedWriter bw) throws IOException {
        //Write each field of each object in the arraylist in to the Users file
        for (int c = 0; c < NumberOfRecords; c++) {
            bw.write(Integer.toString(Players.get(c).getPersonID()));
            bw.newLine();
            bw.write(Players.get(c).getUsername());
            bw.newLine();
            bw.write(Players.get(c).getPassword());
            bw.newLine();
            bw.write(Integer.toString(Players.get(c).getmGamesPlayed()));
            bw.newLine();
            bw.write(Integer.toString(Players.get(c).getmGamesWon()));
            bw.newLine();
            bw.write(Integer.toString(Players.get(c).getmGamesLost()));
            bw.newLine();
            bw.write(Integer.toString(Players.get(c).gethGamesPlayed()));
            bw.newLine();
            bw.write(Integer.toString(Players.get(c).gethGamesWon()));
            bw.newLine();
            bw.write(Integer.toString(Players.get(c).gethGamesLost()));
            bw.newLine();
            bw.write(Integer.toString(Players.get(c).getiGamesPlayed()));
            bw.newLine();
            bw.write(Integer.toString(Players.get(c).getiGamesWon()));
            bw.newLine();
            bw.write(Integer.toString(Players.get(c).getiGamesLost()));

            //Checks if a newline needs to be created in the Users file
            if (c < (NumberOfRecords - 1)) {
                bw.newLine();
            } else {
            }
        }
    }

    //set logged in index variable
    public static void setLoggedInIndex(int x) {
        loggedInIndex = x;
    }
    //Return logged in index variable
    public static int getLoggedInIndex() {
        return loggedInIndex;
    }

    public static void resetStats() {
        //Set currently logged in users stats to 0
        Players.get(getLoggedInIndex()).setmGamesPlayed(0);
        Players.get(getLoggedInIndex()).setmGamesWon(0);
        Players.get(getLoggedInIndex()).setmGamesLost(0);
        Players.get(getLoggedInIndex()).sethGamesPlayed(0);
        Players.get(getLoggedInIndex()).sethGamesWon(0);
        Players.get(getLoggedInIndex()).sethGamesLost(0);
        Players.get(getLoggedInIndex()).setiGamesPlayed(0);
        Players.get(getLoggedInIndex()).setiGamesWon(0);
        Players.get(getLoggedInIndex()).setiGamesLost(0);
    }

    //Set methods to set the game difficulty, colour theme and player turn values, as well as return methods for each of them values
    public static void setGame_difficulty(int x) {
        game_difficulty = x;
    }
    public static int getGame_difficulty() {
        return game_difficulty;
    }
    public static void setColour_theme(int x) { colour_theme = x;}
    public static int getColour_theme() {
        return colour_theme;
    }
    public static void setPlayer_turn(int x) {player_turn = x;}
    public static int getPlayer_turn() {
        return player_turn;
    }

    //Class which holds variables/fields to store the details for each user account
    public static class Users {
        //Variables to store User details
        private int personID;
        private String username;
        private String password;
        private int mGamesPlayed;
        private int mGamesWon;
        private int mGamesLost;
        private int hGamesPlayed;
        private int hGamesWon;
        private int hGamesLost;
        private int iGamesPlayed;
        private int iGamesWon;
        private int iGamesLost;

        //Set methods for each variable
        public void setPersonID(int x) {
            personID = x;
        }

        public void setUsername(String a) {
            username = a;
        }

        public void setPassword(String a) {
            password = a;
        }

        public void setmGamesPlayed(int x) {
            mGamesPlayed = x;
        }

        public void setmGamesWon(int x) {
            mGamesWon = x;
        }

        public void setmGamesLost(int x) {
            mGamesLost = x;
        }

        public void sethGamesPlayed(int x) {
            hGamesPlayed = x;
        }

        public void sethGamesWon(int x) {
            hGamesWon = x;
        }

        public void sethGamesLost(int x) {
            hGamesLost = x;
        }

        public void setiGamesPlayed(int x) {
            iGamesPlayed = x;
        }

        public void setiGamesWon(int x) {
            iGamesWon = x;
        }

        public void setiGamesLost(int x) {
            iGamesLost = x;
        }

        // Methods that return the value of each variable when the function is called

        public int getPersonID() {
            return personID;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }

        public int getmGamesPlayed() {
            return mGamesPlayed;
        }

        public int getmGamesWon() {
            return mGamesWon;
        }

        public int getmGamesLost() {
            return mGamesLost;
        }

        public int gethGamesPlayed() {
            return hGamesPlayed;
        }

        public int gethGamesWon() {
            return hGamesWon;
        }

        public int gethGamesLost() {
            return hGamesLost;
        }

        public int getiGamesPlayed() {
            return iGamesPlayed;
        }

        public int getiGamesWon() {
            return iGamesWon;
        }

        public int getiGamesLost() {
            return iGamesLost;
        }
    }
}
