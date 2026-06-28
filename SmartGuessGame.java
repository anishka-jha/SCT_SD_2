import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Random;

public class SmartGuessGame extends JFrame {

    private int secretNumber;
    private int attempts;
    private int bestScore = 0;
    private final int maxAttempts = 10;

    private JTextField guessField;
    private JLabel messageLabel;
    private JLabel attemptsLabel;
    private JLabel bestScoreLabel;
    private JProgressBar progressBar;

    public SmartGuessGame() {
        setTitle("Smart Number Guess Challenge");
        setSize(650, 520);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        createUI();
        startNewGame();

        setVisible(true);
    }

    private void createUI() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(new Color(245, 247, 250));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        add(mainPanel);

        JLabel title = new JLabel("Smart Number Guess Challenge", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 27));
        title.setForeground(new Color(33, 37, 41));
        title.setBounds(60, 25, 520, 40);
        mainPanel.add(title);

        JLabel subtitle = new JLabel("Guess the hidden number between 1 and 100", SwingConstants.CENTER);
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subtitle.setForeground(new Color(90, 90, 90));
        subtitle.setBounds(80, 70, 480, 30);
        mainPanel.add(subtitle);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(null);
        inputPanel.setBackground(Color.WHITE);
        inputPanel.setBorder(BorderFactory.createLineBorder(new Color(215, 215, 215)));
        inputPanel.setBounds(65, 120, 510, 145);
        mainPanel.add(inputPanel);

        JLabel inputLabel = new JLabel("Enter Your Guess");
        inputLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        inputLabel.setBounds(35, 25, 200, 25);
        inputPanel.add(inputLabel);

        guessField = new JTextField();
        guessField.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        guessField.setBounds(35, 60, 220, 38);
        inputPanel.add(guessField);

        JButton guessButton = new JButton("Guess");
        guessButton.setFont(new Font("Segoe UI", Font.BOLD, 15));
        guessButton.setBounds(280, 58, 90, 40);
        inputPanel.add(guessButton);

        JButton restartButton = new JButton("Restart");
        restartButton.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        restartButton.setBounds(385, 58, 90, 40);
        inputPanel.add(restartButton);

        JPanel resultPanel = new JPanel();
        resultPanel.setLayout(null);
        resultPanel.setBackground(Color.WHITE);
        resultPanel.setBorder(BorderFactory.createLineBorder(new Color(215, 215, 215)));
        resultPanel.setBounds(65, 285, 510, 150);
        mainPanel.add(resultPanel);

        JLabel resultTitle = new JLabel("Game Status", SwingConstants.CENTER);
        resultTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        resultTitle.setBounds(150, 15, 210, 30);
        resultPanel.add(resultTitle);

        messageLabel = new JLabel("Start guessing!", SwingConstants.CENTER);
        messageLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        messageLabel.setForeground(new Color(25, 118, 210));
        messageLabel.setBounds(50, 55, 410, 30);
        resultPanel.add(messageLabel);

        attemptsLabel = new JLabel("Attempts: 0 / 10");
        attemptsLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        attemptsLabel.setBounds(35, 100, 180, 25);
        resultPanel.add(attemptsLabel);

        bestScoreLabel = new JLabel("Best Score: --");
        bestScoreLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        bestScoreLabel.setBounds(335, 100, 150, 25);
        resultPanel.add(bestScoreLabel);

        progressBar = new JProgressBar(0, maxAttempts);
        progressBar.setValue(0);
        progressBar.setStringPainted(true);
        progressBar.setBounds(65, 445, 400, 25);
        mainPanel.add(progressBar);

        JButton exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        exitButton.setBounds(485, 445, 90, 25);
        mainPanel.add(exitButton);

        guessButton.addActionListener(e -> checkGuess());
        restartButton.addActionListener(e -> startNewGame());
        exitButton.addActionListener(e -> System.exit(0));

        guessField.addActionListener(e -> checkGuess());
    }

    private void startNewGame() {
        Random random = new Random();
        secretNumber = random.nextInt(100) + 1;
        attempts = 0;

        guessField.setText("");
        guessField.setEnabled(true);

        messageLabel.setText("Start guessing!");
        messageLabel.setForeground(new Color(25, 118, 210));

        attemptsLabel.setText("Attempts: 0 / " + maxAttempts);
        progressBar.setValue(0);
        progressBar.setString("0 / " + maxAttempts);
    }

    private void checkGuess() {
        String input = guessField.getText().trim();

        if (input.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a number first.");
            return;
        }

        int userGuess;

        try {
            userGuess = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter numbers only.");
            guessField.setText("");
            return;
        }

        if (userGuess < 1 || userGuess > 100) {
            JOptionPane.showMessageDialog(this, "Please enter a number between 1 and 100.");
            guessField.setText("");
            return;
        }

        attempts++;

        attemptsLabel.setText("Attempts: " + attempts + " / " + maxAttempts);
        progressBar.setValue(attempts);
        progressBar.setString(attempts + " / " + maxAttempts);

        if (userGuess == secretNumber) {
            messageLabel.setText("Correct! You guessed it.");
            messageLabel.setForeground(new Color(46, 125, 50));

            if (bestScore == 0 || attempts < bestScore) {
                bestScore = attempts;
                bestScoreLabel.setText("Best Score: " + bestScore);
            }

            JOptionPane.showMessageDialog(
                    this,
                    "Congratulations!\nYou guessed the number in " + attempts + " attempts.",
                    "You Won",
                    JOptionPane.INFORMATION_MESSAGE
            );

            guessField.setEnabled(false);
        } else if (attempts >= maxAttempts) {
            messageLabel.setText("Game Over! Number was " + secretNumber);
            messageLabel.setForeground(new Color(198, 40, 40));

            JOptionPane.showMessageDialog(
                    this,
                    "Game Over!\nThe correct number was: " + secretNumber,
                    "Game Over",
                    JOptionPane.ERROR_MESSAGE
            );

            guessField.setEnabled(false);
        } else if (userGuess > secretNumber) {
            messageLabel.setText("Too High! Try a smaller number.");
            messageLabel.setForeground(new Color(239, 108, 0));
        } else {
            messageLabel.setText("Too Low! Try a bigger number.");
            messageLabel.setForeground(new Color(123, 31, 162));
        }

        guessField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SmartGuessGame::new);
    }
}