import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class HangFrame extends JFrame {

    //main panels and required buttons
    private JPanel hangPanel, inputPanel, wrongPanel, wordPanel, buttonPanel;
    public JTextField textInput;
    private JTextArea instructionTextArea;
    private JButton buttonLetter, buttonWord;
    private JLabel hangLabel, wordLabel, badGuessLabel, triesLabel, instructionLabel;
    private ImageIcon hangPic;

    Font fontyLarge = new Font("fonty2", Font.BOLD, 80);
    Font fontySmall = new Font("fonty", Font.BOLD, 20);
    Font fontyMed = new Font("fonty3", Font.BOLD, 40);

    public HangFrame(Hangman hang) {

    }

    public void init(Hangman hang) {
        //closes without fucking things up
        //dispose on close will close window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //title
        setTitle("Hangman App");

        //sets size and location
        setSize(new Dimension(1500, 1500));
        setLocationRelativeTo(null);

        //show
        setVisible(true);
        Container c = getContentPane();
        c.setBackground(Color.WHITE);
        c.setLayout(new BorderLayout());

        //creates center panel for hangman picture
        hangPic = new ImageIcon(getClass().getResource("img\\Hangman0.png"));
        hangLabel = new JLabel(hangPic);
        hangPanel = new JPanel();
        hangPanel.setBackground(Color.WHITE);
        c.add(hangPanel, BorderLayout.CENTER);
        //add and resize
        hangPanel.add(hangLabel);
        hangLabel.setPreferredSize(new Dimension(600, 800));


        //creates top panel for wrong letters and tries left
        wrongPanel = new JPanel();
//        wrongPanel.setBackground(Color.WHITE);
        c.add(wrongPanel, BorderLayout.NORTH);
        wrongPanel.setLayout(new BorderLayout());
        //PRINTS THE BAD GUESSES
        badGuessLabel = new JLabel("Already Used: " + hang.olderGuess());
        badGuessLabel.setFont(fontyMed);
        //PRINTS THE NUMBER OF TRIES LEFT
        triesLabel = new JLabel(hang.formalTriesRemaining());
        triesLabel.setFont(fontyMed);
        //add
        wrongPanel.add(triesLabel, BorderLayout.EAST);
        wrongPanel.add(badGuessLabel, BorderLayout.WEST);


        //creates bottom panel for word itself
        wordPanel = new JPanel();
        c.add(wordPanel, BorderLayout.SOUTH);
        wordLabel = new JLabel(hang.currentGuess());
        wordLabel.setFont(fontyLarge);
        //add
        wordPanel.add(wordLabel);
        ///////////////////////////////////////////////////////


        //creates right panel for input
        inputPanel = new JPanel();
        c.add(inputPanel, BorderLayout.EAST);
        inputPanel.setLayout(new BorderLayout());
        //layout for instruction area
        instructionTextArea = new JTextArea("You get 6 tries. Guess a letter or try and guess the whole word! (WARNING: you only get one shot to guess the whole word");
        instructionTextArea.setFont(fontySmall);
        instructionTextArea.setWrapStyleWord(true);
        instructionTextArea.setLineWrap(true);
        instructionTextArea.setPreferredSize(new Dimension(300, 130));
        //layout for text field
        textInput = new JTextField();
        textInput.setFont(fontyLarge);


        //button resize
        Dimension buttonResize = new Dimension(145, 50);
        buttonLetter = new JButton("Letter");
        buttonLetter.setPreferredSize(buttonResize);
        buttonLetter.setFont(fontySmall);
        buttonWord = new JButton("Word");
        buttonWord.setPreferredSize(buttonResize);
        buttonWord.setFont(fontySmall);
        buttonPanel = new JPanel();
        //add
        inputPanel.add(instructionTextArea, BorderLayout.NORTH);
        inputPanel.add(textInput, BorderLayout.CENTER);
        inputPanel.add(buttonPanel, BorderLayout.SOUTH);
        buttonPanel.add(buttonLetter, BorderLayout.WEST);
        buttonPanel.add(buttonWord, BorderLayout.EAST);

        this.getRootPane().setDefaultButton(buttonLetter);


        //makes buttons do things
        buttonWord.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    String t = textInput.getText();
                    textInput.setText("");
                    t = t.trim();
                    t = t.toLowerCase();
                    System.out.println(t);
                    if (hang.mysteryWord.length() == t.length()) {
                        for (int i = 0; i < t.length(); i++) {
                            if (hang.mysteryWord.charAt(i) != t.charAt(i)) {
                                //if not the right size, LOSE GAME
                                hang.currentTry = 6;
                                hang.gameOver = true;
                                gameOverStatus(hang, false);
                                updateScreen(hang);
                                break;
                            }
                            hang.playerGuess = t.charAt(i);
                            hang.addGuessedLetter();
                            updateScreen(hang);
                        }
                        //if its the right word, WIN GAME
                        if (!hang.gameOver) {
                            hang.gameOver = true;
                            gameOverStatus(hang, true);
                        }
                    } else {
                        //otherwise, LOSE GAME
                        hang.gameOver = true;
                        hang.currentTry = 6;
                        updateScreen(hang);
                        gameOverStatus(hang, false);
                    }
                } catch (StringIndexOutOfBoundsException e) {
                    //PRINT AN ERROR
                    System.out.println("No Input.");
                }
            }
        });

        buttonLetter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    String t = textInput.getText();
                    textInput.setText("");
                    hang.playerGuess = t.charAt(0);
                    if (hang.inTheWord()) {
                        hang.addGuessedLetter();
                    } else {
                        if (!hang.previousGuess.contains(hang.playerGuess)) {
                            hang.currentTry++;
                            hang.previousGuess.add(hang.playerGuess);
                            badGuessLabel.setText("Already Used: " + hang.olderGuess());
                        }
                    }
                    hang.printStatus();
                    updateScreen(hang);
                    if (hang.isGameOver()) {
                        hang.gameOver = true;
                        System.out.println("game is over: " + hang.verifyWord());
                        gameOverStatus(hang, hang.verifyWord());
                    }
                } catch (StringIndexOutOfBoundsException e) {
                    //PRINT NO INPUT ERROR
                    System.out.println("No Input.");
                }


            }
        });

        pack();

    }

    public void updateScreen(Hangman hang) {
        hangLabel.setIcon(new ImageIcon(getClass().getResource(hang.drawPicture())));
        wordLabel.setText(hang.currentGuess());
        triesLabel.setText(hang.formalTriesRemaining());
    }


    public void gameOverStatus(Hangman hang, boolean bool) {
        if (hang.gameOver) {
            GameOver gameOver = new GameOver(this, hang, bool);
            gameOver.setVisible(true);
        }
    }

}

class GameOver extends JFrame {

    private JButton buttonYes, buttonNo;
    private JPanel buttonPanel;
    private JTextArea textArea;

    public GameOver(HangFrame gui, Hangman hang, boolean bool) {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("Play Again?");
        setSize(300, 150);

        Container c = getContentPane();
        c.setLayout(new BorderLayout());

        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(gui.fontySmall);

        if (bool) {
            textArea.setText("Congratulations! \nYou Win! Play Again?");
        } else {

            textArea.setText("Word was: " + hang.mysteryWord.toUpperCase() + "\nYou Lose! Play Again?");
        }
        c.add(textArea, BorderLayout.CENTER);

        buttonPanel = new JPanel();
        c.add(buttonPanel, BorderLayout.SOUTH);

        buttonYes = new JButton("Yes!");
        buttonYes.setFont(gui.fontySmall);
        buttonPanel.add(buttonYes, BorderLayout.WEST);
        buttonNo = new JButton("No Thanks");
        buttonNo.setFont(gui.fontySmall);
        buttonPanel.add(buttonNo, BorderLayout.EAST);
        requestFocus();
        pack();

        this.getRootPane().setDefaultButton(buttonYes);
        buttonYes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                hang.reset();
                gui.updateScreen(hang);
                dispose();

            }
        });

        buttonNo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.exit(0);
            }
        });


    }


}
