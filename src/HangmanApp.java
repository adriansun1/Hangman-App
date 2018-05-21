import java.awt.*;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;
import javax.swing.*;

public class HangmanApp {

    public HangmanApp() {
    }

    public static void main(String[] args) throws IOException {
        Scanner sc;
        HangmanApp hangApp = new HangmanApp();
        sc = new Scanner(System.in);
        System.out.println("six tries, guess the word.");
        System.out.println();

        //ALLOWS FOR MULTIPLE GAMES
        Hangman hang = new Hangman();
        //INITIATE UI.
        HangFrame gui = new HangFrame(hang);
        gui.init(hang);
        System.out.println(hang.mysteryWord);
//            hang.printCurrentGuess();
//            //CREATES A GAME
//            do {
//                try {
//                    System.out.println("Guess a letter:");
//                    hang.playerGuess = sc.next().toLowerCase().charAt(0);
//                    while (Character.isDigit(hang.playerGuess) || hang.alreadyGuessed()) {
//                        if (hang.alreadyGuessed()) {
//                            System.out.println("Already guessed this letter, try again.");
//                        } else {
//                            System.out.println("Bad input. Try again");
//                        }
//                        hang.playerGuess = sc.next().toLowerCase().charAt(0);
//                    }
//                } catch (InputMismatchException e) {
//                    System.out.println("Invalid input, please try again.");
//                }
//
//                if (hang.inTheWord()) {
//                    System.out.println(hang.playerGuess + " is in the word.");
//                    hang.addGuessedLetter();
//                } else {
//                    System.out.println(hang.playerGuess + " isn't in the word.");
//                    hang.previousGuess.add(hang.playerGuess);
////                    hang.incorrectGuess();
//                }
//                hang.printOlderGuess();
//                hang.printCurrentGuess();


//
//
//            //OPTION TO PLAY AGAIN
//            System.out.println("Play Again? (y/n)");
//            char playAgain = sc.next().toUpperCase().charAt(0);
//            if (playAgain == 'Y') {
//                hangApp.doYouWantToPlay = true;
//                gui.setVisible(false);
//                gui.dispose();
//            } else {
//                hangApp.doYouWantToPlay = false;
    }
}

