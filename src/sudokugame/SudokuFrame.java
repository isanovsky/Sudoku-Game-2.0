package sudokugame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SudokuFrame extends JFrame {
    private final SudokuPanel sudokuPanel;
    private SudokuGenerator.Difficulty currentDifficulty = SudokuGenerator.Difficulty.EASY;
    private final JLabel scoreLabel;

    public SudokuFrame(SudokuGenerator.Difficulty difficulty) {
        super("Sudoku Game v2.0");

        // Initialize the Sudoku panel
        scoreLabel = new JLabel("Score: 0");
        sudokuPanel = new SudokuPanel(difficulty, scoreLabel);
        add(sudokuPanel, BorderLayout.CENTER);

        // Create menu bar
        JMenuBar menuBar = new JMenuBar();

        // Create Game menu
        JMenu gameMenu = new JMenu("Game");
        JMenuItem newGameItem = new JMenuItem("New Game");
        JMenuItem exitItem = new JMenuItem("Exit");
        gameMenu.add(newGameItem);
        gameMenu.addSeparator();
        gameMenu.add(exitItem);
        menuBar.add(gameMenu);

        // Create Difficulty menu
        JMenu difficultyMenu = new JMenu("Difficulty");
        ButtonGroup difficultyGroup = new ButtonGroup();
        JRadioButtonMenuItem easyItem = new JRadioButtonMenuItem("Easy", true);
        JRadioButtonMenuItem mediumItem = new JRadioButtonMenuItem("Medium");
        JRadioButtonMenuItem hardItem = new JRadioButtonMenuItem("Hard");
        difficultyGroup.add(easyItem);
        difficultyGroup.add(mediumItem);
        difficultyGroup.add(hardItem);
        difficultyMenu.add(easyItem);
        difficultyMenu.add(mediumItem);
        difficultyMenu.add(hardItem);
        menuBar.add(difficultyMenu);

        // Create Hint Button
        JButton hintButton = new JButton("Hint");

        // Create Credit Menu
        JMenu helpMenu = new JMenu("Help");
        JMenuItem creditItem = new JMenuItem("Credits");
        helpMenu.add(creditItem);
        menuBar.add(helpMenu);

        // Create Score Reset Button
        JButton resetScoreButton = new JButton("Reset Score");

        // Create Clear Button
        JButton clearButton = new JButton("Clear");

        // Add Action Listeners
        newGameItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sudokuPanel.startNewGame(currentDifficulty);
            }
        });

        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        easyItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentDifficulty = SudokuGenerator.Difficulty.EASY;
                sudokuPanel.startNewGame(currentDifficulty);
                resetScore();
            }
        });

        mediumItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentDifficulty = SudokuGenerator.Difficulty.MEDIUM;
                sudokuPanel.startNewGame(currentDifficulty);
                resetScore();
            }
        });

        hardItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentDifficulty = SudokuGenerator.Difficulty.HARD;
                sudokuPanel.startNewGame(currentDifficulty);
                resetScore();
            }
        });

        hintButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sudokuPanel.giveHint();
            }
        });

        creditItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(SudokuFrame.this,
                        "Sudoku Game\nDeveloped by:\n- Ichsan Ali Darmawan\n- Muhammad Hanan Zakian Farkhani\n- Rahmawati Wildatus Solekha " +
                                "\n- Source Code https://github.com/isanovsky/Sudoku-Game-2.0",
                        "Credits",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        resetScoreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetScore();
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sudokuPanel.clearInputs();
            }
        });

        // Set Menu Bar
        setJMenuBar(menuBar);

        // Control Panel
        JPanel controlPanel = new JPanel(new BorderLayout());

        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(hintButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(resetScoreButton);
        buttonPanel.add(scoreLabel);

        controlPanel.add(buttonPanel, BorderLayout.NORTH);
        controlPanel.add(sudokuPanel, BorderLayout.CENTER);

        this.add(controlPanel);

        this.pack();
        // Frame settings
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void resetScore() {
        scoreLabel.setText("Score: 0");
    }
}
