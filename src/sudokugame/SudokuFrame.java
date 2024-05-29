package sudokugame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author ICHSAN ENTERPRISE
 */
public class SudokuFrame extends JFrame {

    private SudokuPanel sudokuPanel;

    public SudokuFrame() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Sudoku Game");

        // Create SudokuPanel and buttons
        sudokuPanel = new SudokuPanel();
        JButton newGameButton = new JButton("New Game");
        JButton hintButton = new JButton("Hint");

        // Add action listener to the buttons
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sudokuPanel.newGame();
            }
        });

        hintButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sudokuPanel.giveHint();
            }
        });

        // Create a panel to hold the buttons and the SudokuPanel
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BorderLayout());
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(newGameButton);
        buttonPanel.add(hintButton);
        controlPanel.add(buttonPanel, BorderLayout.NORTH);
        controlPanel.add(sudokuPanel, BorderLayout.CENTER);

        // Add control panel to the frame
        this.add(controlPanel);

        this.pack();
        this.setVisible(true);
    }
}