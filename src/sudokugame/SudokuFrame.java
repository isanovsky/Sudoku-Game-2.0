package sudokugame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SudokuFrame extends JFrame {
    private final SudokuPanel sudokuPanel;
    private SudokuGenerator.Difficulty currentDifficulty = SudokuGenerator.Difficulty.EASY;
    private Clip musicClip;

    public SudokuFrame(SudokuGenerator.Difficulty difficulty) {
        super("Sudoku Game v2.0");

        // Set overall frame layout and background color
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(60, 63, 65));

        // Initialize the Sudoku panel
        JLabel timerLabel = new JLabel("Time: 00:00");
        timerLabel.setForeground(Color.WHITE);
        timerLabel.setFont(new Font("Serif", Font.BOLD, 18));
        sudokuPanel = new SudokuPanel(difficulty, timerLabel);
        add(sudokuPanel, BorderLayout.CENTER);

        // Create menu bar
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(new Color(43, 43, 43));
        menuBar.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));

        // Create Game menu
        JMenu gameMenu = createMenu("Game");
        JMenuItem newGameItem = createMenuItem("New Game");
        JMenuItem exitItem = createMenuItem("Exit");
        gameMenu.add(newGameItem);
        gameMenu.addSeparator();
        gameMenu.add(exitItem);
        menuBar.add(gameMenu);

        // Create Difficulty menu
        JMenu difficultyMenu = createMenu("Difficulty");
        ButtonGroup difficultyGroup = new ButtonGroup();
        JRadioButtonMenuItem easyItem = createRadioMenuItem("Easy", true);
        JRadioButtonMenuItem mediumItem = createRadioMenuItem("Medium", false);
        JRadioButtonMenuItem hardItem = createRadioMenuItem("Hard", false);
        difficultyGroup.add(easyItem);
        difficultyGroup.add(mediumItem);
        difficultyGroup.add(hardItem);
        difficultyMenu.add(easyItem);
        difficultyMenu.add(mediumItem);
        difficultyMenu.add(hardItem);
        menuBar.add(difficultyMenu);

        // Create Credit Menu
        JMenu helpMenu = createMenu("Help");
        JMenuItem creditItem = createMenuItem("Credits");
        helpMenu.add(creditItem);
        menuBar.add(helpMenu);

        // Set Menu Bar
        setJMenuBar(menuBar);

        // Create buttons with improved styling
        JButton hintButton = createButton("Hint");
        JButton clearButton = createButton("Clear");
        JButton resetTimeButton = createButton("Reset Time");
        JButton playMusicButton = createButton("Play Music");

        // Add Action Listeners
        newGameItem.addActionListener((ActionEvent _) -> sudokuPanel.startNewGame(currentDifficulty));
        exitItem.addActionListener((ActionEvent _) -> System.exit(0));

        easyItem.addActionListener((ActionEvent _) -> {
            currentDifficulty = SudokuGenerator.Difficulty.EASY;
            sudokuPanel.startNewGame(currentDifficulty);
            resetTime();
        });

        mediumItem.addActionListener(_ -> {
            currentDifficulty = SudokuGenerator.Difficulty.MEDIUM;
            sudokuPanel.startNewGame(currentDifficulty);
            resetTime();
        });

        hardItem.addActionListener(e -> {
            currentDifficulty = SudokuGenerator.Difficulty.HARD;
            sudokuPanel.startNewGame(currentDifficulty);
            resetTime();
        });

        hintButton.addActionListener(_ -> sudokuPanel.giveHint());
        creditItem.addActionListener(_ -> {
            JOptionPane.showMessageDialog(SudokuFrame.this,
                    "Sudoku Game\nDeveloped by:\n- Ichsan Ali Darmawan\n- Muhammad Hanan Zakian Farkhani\n- Rahmawati Wildatus Solekha " +
                            "\n- Source Code https://github.com/isanovsky/Sudoku-Game-2.0",
                    "Credits",
                    JOptionPane.INFORMATION_MESSAGE);
        });

        resetTimeButton.addActionListener(_ -> resetTime());
        clearButton.addActionListener(_ -> sudokuPanel.clearInputs());

        playMusicButton.addActionListener(e -> {
            if (musicClip == null || !musicClip.isActive()) {
                playMusic("src/resources/backgroundTest.wav", playMusicButton);
            } else {
                stopMusic(playMusicButton);
            }
        });

        // Control Panel
        JPanel controlPanel = new JPanel(new BorderLayout());
        controlPanel.setBackground(new Color(60, 63, 65));

        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(60, 63, 65));
        buttonPanel.add(hintButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(resetTimeButton);
        buttonPanel.add(timerLabel);
        buttonPanel.add(playMusicButton);

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

    private void resetTime() {
        sudokuPanel.resetTimer();
    }

    private void playMusic(String filePath, JButton playMusicButton) {
        try {
            File musicPath = new File("src/resources/musicBackground.wav");
            if (musicPath.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                musicClip = AudioSystem.getClip();
                musicClip.open(audioInput);
                musicClip.start();
                musicClip.loop(Clip.LOOP_CONTINUOUSLY);
                playMusicButton.setText("Stop Music");
            } else {
                JOptionPane.showMessageDialog(SudokuFrame.this, "Can't Find Music Files", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void stopMusic(JButton playMusicButton) {
        if (musicClip != null) {
            musicClip.stop();
            playMusicButton.setText("Play Music");
        }
    }

    private JMenu createMenu(String name) {
        JMenu menu = new JMenu(name);
        menu.setForeground(Color.BLACK);
        menu.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        return menu;
    }

    private JMenuItem createMenuItem(String name) {
        JMenuItem menuItem = new JMenuItem(name);
        menuItem.setBackground(new Color(60, 63, 65));
        menuItem.setForeground(Color.BLACK);
        menuItem.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        return menuItem;
    }

    private JRadioButtonMenuItem createRadioMenuItem(String name, boolean selected) {
        JRadioButtonMenuItem radioMenuItem = new JRadioButtonMenuItem(name, selected);
        radioMenuItem.setBackground(new Color(60, 63, 65));
        radioMenuItem.setForeground(Color.BLACK);
        radioMenuItem.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        return radioMenuItem;
    }

    private JButton createButton(String name) {
        JButton button = new JButton(name);
        button.setBackground(new Color(43, 43, 43));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        button.setFocusPainted(false);
        return button;
    }
}
