import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Hangman {


    String mysteryWord;
    StringBuilder currentGuess;
    ArrayList<Character> previousGuess = new ArrayList<>();
    char playerGuess;
    boolean gameOver;
    //POSSIBLE NEEDS TO BE RESET FOR MULTIPLE GAMES

    int maxTries = 6;
    int currentTry = 0;

    ArrayList<String> dictionary = new ArrayList<String>();
    private static FileReader fileReader;
    private static BufferedReader bufferedFileReader;

    public Hangman() throws IOException {
        initializeStreams();
        mysteryWord = pickWord();
        currentGuess = initializeCurrentGuess();
    }

    public void reset() {
        mysteryWord = pickWord();
        currentGuess = initializeCurrentGuess();
        currentTry = 0;
        previousGuess.clear();
        gameOver = false;
        System.out.println(mysteryWord);
    }

    //takes in "newDictionary" file and adds each line into an arraylist
    public void initializeStreams() throws IOException {
        try {
            File inFile = new File("C:\\Users\\Adrian\\Desktop\\Programming\\HangmanApp\\src\\newDictionary1.txt");
            BufferedReader bufferedFileReader = new BufferedReader(new FileReader(inFile));
            String currentLine = bufferedFileReader.readLine();
            while (currentLine != null) {
                dictionary.add(currentLine);
                currentLine = bufferedFileReader.readLine();
            }
            bufferedFileReader.close();
        } catch (IOException e) {
            System.out.println("Stream Init Fail");
        }
    }

    //picks a word at random from the dictionary
    public String pickWord() {
        Random rand = new Random();
        int wordIndex = Math.abs(rand.nextInt() % dictionary.size());
        return dictionary.get(wordIndex);
    }

    //prints the a blank slate that is the length of the mystery word
    public StringBuilder initializeCurrentGuess() {
        StringBuilder current = new StringBuilder();
        for (int i = 0; i < mysteryWord.length(); i++) {
            current.append("_");
        }
        return current;
    }

    public String currentGuess() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < currentGuess.length(); i++) {
            s.append(currentGuess.charAt(i));
            s.append(" ");
        }
        return s.toString();
    }

    //used in console version of game
    public void printCurrentGuess() {
        System.out.println(currentGuess());
        System.out.println();
    }

    public String olderGuess() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < previousGuess.size(); i++) {
            s.append(previousGuess.get(i));
            s.append(" ");
        }
        return s.toString();
    }

    //used in console version of game
    public void printOlderGuess() {
        System.out.println(olderGuess());
        System.out.println();
    }

    //checks to see if the input character is in the word
    //replaces any instance of playerguess in the currentguess with the letter.
    public boolean inTheWord() {
        for (int i = 0; i < mysteryWord.length(); i++) {
            if (mysteryWord.charAt(i) == playerGuess) {
                return true;
            }
        }
        return false;
    }

    public String formalTriesRemaining() {
        return ("Guesses Left: " + (maxTries - currentTry));
    }


    //in the future, make it so that the player's guess needs to be added as variable, so that it can be changed for different conditions
    //ie for buttonWord win condition, i need to add a different word.
    public void addGuessedLetter() {
        for (int i = 0; i < mysteryWord.length(); i++) {
            if (mysteryWord.charAt(i) == playerGuess) {
                currentGuess.setCharAt(i, playerGuess);
            }
        }
    }

    public boolean alreadyGuessed() {
        for (int i = 0; i < previousGuess.size(); i++) {
            if (previousGuess.get(i) == playerGuess) {
                return true;
            }
        }
        return false;
    }

    //reflects game over status. game does not end until true.
    public boolean isGameOver() {
        for (int i = 0; i < mysteryWord.length(); i++) {
            if (currentGuess.charAt(i) == '_' && currentTry < 6) {
                return false;
            }
        }
        return true;
    }

    public boolean verifyWord(){
        for (int i = 0; i<mysteryWord.length();i++){
            if (mysteryWord.charAt(i) != currentGuess.charAt(i)){
                return false;
            }
        }
        return true;
    }


    //NEEDS TO SHOW JLABEL PICTURES
    //displays a picture of the current hangman status. might be moved to the gui.
    //sends instructions to the gui to print a certain pic dependant on the current guess
    public String drawPicture() {
        switch (currentTry) {
            default: //tell hangman app to display hangman 0
                return "img\\Hangman0.png";

            case 1:
                return "img\\Hangman1.png";

            case 2:
                return "img\\Hangman2.png";

            case 3:
                return "img\\Hangman3.png";

            case 4:
                return "img\\Hangman4.png";

            case 5:
                return "img\\Hangman5.png";

            case 6:
                return "img\\Hangman6.png";

        }
    }

    //BECAUSE I DONT KNOW HOW TO DEBUG THE RIGHT WAY YET
    public void printStatus() {
//        System.out.println(mysteryWord);
//        System.out.println("try #: " + currentTry);
//        System.out.println(gameOver);
    }
}