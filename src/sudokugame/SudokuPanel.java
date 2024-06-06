package sudokugame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class SudokuPanel extends JPanel {

    private SudokuPuzzle puzzle;
    private boolean[][] preGenerated;
    private int selectedRow = -1;
    private int selectedCol = -1;
    private int score = 0;
    private final JLabel scoreLabel;

    public SudokuPanel(SudokuGenerator.Difficulty difficulty, JLabel scoreLabel) {
        this.setPreferredSize(new Dimension(540, 450));
        this.scoreLabel = scoreLabel;
        startNewGame(difficulty);
    }

    public void startNewGame(SudokuGenerator.Difficulty difficulty) {
        this.puzzle = SudokuGenerator.generateRandomSudoku(SudokuPuzzleType.NINEBYNINE, difficulty);
        this.preGenerated = new boolean[puzzle.getNumRows()][puzzle.getNumColumns()];
        initializePreGenerated();
        repaint();
    }

    private void initializePreGenerated() {
        for (int row = 0; row < puzzle.getNumRows(); row++) {
            for (int col = 0; col < puzzle.getNumColumns(); col++) {
                preGenerated[row][col] = !puzzle.board[row][col].isEmpty();
            }
        }

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int slotWidth = getWidth() / 9;
                int slotHeight = getHeight() / 9;
                selectedRow = e.getY() / slotHeight;
                selectedCol = e.getX() / slotWidth;
                requestFocusInWindow();
                repaint();
            }
        });

        setFocusable(true);
        requestFocusInWindow();

        // Adding key bindings
        for (int i = 1; i <= 9; i++) {
            final int num = i;
            getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke((char) ('0' + i)), "input" + i);
            getActionMap().put("input" + i, new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (selectedRow != -1 && selectedCol != -1 && !preGenerated[selectedRow][selectedCol]) {
                        String value = String.valueOf(num);
                        if (Arrays.asList(puzzle.getVALIDVALUES()).contains(value)) {
                            puzzle.makeMove(selectedRow, selectedCol, value);
                            repaint();
                            checkCompletion();
                        }
                    }
                }
            });
        }
    }

    public void giveHint() {
        for (int row = 0; row < puzzle.getNumRows(); row++) {
            for (int col = 0; col < puzzle.getNumColumns(); col++) {
                if (puzzle.board[row][col].isEmpty() && !preGenerated[row][col]) {
                    String correctValue = findHintValue(row, col);
                    if (correctValue != null) {
                        puzzle.makeMove(row, col, correctValue);
                        repaint();
                        checkCompletion();
                        return;
                    }
                }
            }
        }
    }

    private String findHintValue(int row, int col) {
        for (String value : puzzle.getVALIDVALUES()) {
            if (!puzzle.numInRow(row, value) && !puzzle.numInCol(col, value) && !puzzle.numInBox(row, col, value)) {
                puzzle.makeMove(row, col, value);
                if (isSolvable(puzzle)) {
                    return value;
                } else {
                    puzzle.makeMove(row, col, ""); // undo move
                }
            }
        }
        return null;
    }

    private boolean isSolvable(SudokuPuzzle puzzle) {
        for (int row = 0; row < puzzle.getNumRows(); row++) {
            for (int col = 0; col < puzzle.getNumColumns(); col++) {
                if (puzzle.board[row][col].isEmpty()) {
                    for (String value : puzzle.getVALIDVALUES()) {
                        if (!puzzle.numInRow(row, value) && !puzzle.numInCol(col, value) && !puzzle.numInBox(row, col, value)) {
                            puzzle.makeMove(row, col, value);
                            if (isSolvable(puzzle)) {
                                puzzle.makeMove(row, col, ""); // undo move
                                return true;
                            }
                            puzzle.makeMove(row, col, ""); // undo move
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    private void checkCompletion() {
        if (isPuzzleComplete() && isPuzzleValid()) {
            score+=10;
            scoreLabel.setText("Score: " + score);
            JOptionPane.showMessageDialog(this, "Congratulations! You have completed the puzzle!");
        }
    }

    private boolean isPuzzleComplete() {
        for (int row = 0; row < puzzle.getNumRows(); row++) {
            for (int col = 0; col < puzzle.getNumColumns(); col++) {
                if (puzzle.board[row][col].isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isPuzzleValid() {
        return areAllRowsValid() && areAllColsValid() && areAllBoxesValid();
    }

    private boolean areAllRowsValid() {
        for (int row = 0; row < puzzle.getNumRows(); row++) {
            Set<String> seen = new HashSet<>();
            for (int col = 0; col < puzzle.getNumColumns(); col++) {
                String value = puzzle.board[row][col];
                if (!value.isEmpty() && !seen.add(value)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean areAllColsValid() {
        for (int col = 0; col < puzzle.getNumColumns(); col++) {
            Set<String> seen = new HashSet<>();
            for (int row = 0; row < puzzle.getNumRows(); row++) {
                String value = puzzle.board[row][col];
                if (!value.isEmpty() && !seen.add(value)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean areAllBoxesValid() {
        int boxWidth = puzzle.getBOXWIDTH();
        int boxHeight = puzzle.getBOXHEIGHT();
        for (int boxRow = 0; boxRow < puzzle.getNumRows(); boxRow += boxHeight) {
            for (int boxCol = 0; boxCol < puzzle.getNumColumns(); boxCol += boxWidth) {
                if (!isBoxValid(boxRow, boxCol, boxWidth, boxHeight)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isBoxValid(int startRow, int startCol, int boxWidth, int boxHeight) {
        Set<String> seen = new HashSet<>();
        for (int row = startRow; row < startRow + boxHeight; row++) {
            for (int col = startCol; col < startCol + boxWidth; col++) {
                String value = puzzle.board[row][col];
                if (!value.isEmpty() && !seen.add(value)) {
                    return false;
                }
            }
        }
        return true;
    }

    public void clearInputs() {
        for (int row = 0; row < puzzle.getNumRows(); row++) {
            for (int col = 0; col < puzzle.getNumColumns(); col++) {
                if (!preGenerated[row][col]) {
                    puzzle.board[row][col] = "";
                }
            }
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.WHITE);

        g2d.fillRect(0, 0, this.getWidth(), this.getHeight());

        int slotWidth = this.getWidth() / 9;
        int slotHeight = this.getHeight() / 9;

        // Define colors for each 3x3 box
        Color[] boxColors = {
                new Color(255, 235, 205), // Blanched Almond
                new Color(255, 222, 173), // Navajo White
                new Color(255, 248, 220), // Cornsilk
                new Color(230, 230, 250), // Lavender
                new Color(240, 255, 240), // Honeydew
                new Color(255, 228, 225), // Misty Rose
                new Color(255, 250, 240), // Floral White
                new Color(240, 248, 255), // Alice Blue
                new Color(248, 248, 255)  // Ghost White
        };

        // Fill each 3x3 box with different color
        for (int boxRow = 0; boxRow < 3; boxRow++) {
            for (int boxCol = 0; boxCol < 3; boxCol++) {
                g2d.setColor(boxColors[boxRow * 3 + boxCol]);
                g2d.fillRect(boxCol * 3 * slotWidth, boxRow * 3 * slotHeight, 3 * slotWidth, 3 * slotHeight);
            }
        }

        // Highlight selected cell
        if (selectedRow != -1 && selectedCol != -1) {
            g2d.setColor(new Color(173, 216, 230));
            g2d.fillRect(selectedCol * slotWidth, selectedRow * slotHeight, slotWidth, slotHeight);
        }

        // Draw numbers and highlight pre-generated cells
        g2d.setFont(new Font("SansSerif", Font.BOLD, 20));
        FontMetrics fm = g2d.getFontMetrics();
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                String value = puzzle.board[row][col];
                if (!value.isEmpty()) {
                    if (preGenerated[row][col]) {
                        g2d.setColor(Color.BLACK);
                    } else {
                        g2d.setColor(Color.BLACK);
                    }
                    int textWidth = fm.stringWidth(value);
                    int textHeight = fm.getAscent();
                    int x = col * slotWidth + (slotWidth - textWidth) / 2;
                    int y = row * slotHeight + (slotHeight + textHeight) / 2 - fm.getDescent();
                    g2d.drawString(value, x, y);
                }
            }
        }

        // Highlight duplicates
        g2d.setColor(new Color(255, 0, 0, 100)); // Semi-transparent red
        for (int row = 0; row < 9; row++) {
            Set<String> seenInRow = new HashSet<>();
            for (int col = 0; col < 9; col++) {
                String value = puzzle.board[row][col];
                if (!value.isEmpty() && !seenInRow.add(value)) {
                    g2d.fillRect(col * slotWidth, row * slotHeight, slotWidth, slotHeight);
                }
            }
        }
        for (int col = 0; col < 9; col++) {
            Set<String> seenInCol = new HashSet<>();
            for (int row = 0; row < 9; row++) {
                String value = puzzle.board[row][col];
                if (!value.isEmpty() && !seenInCol.add(value)) {
                    g2d.fillRect(col * slotWidth, row * slotHeight, slotWidth, slotHeight);
                }
            }
        }
        int boxWidth = puzzle.getBOXWIDTH();
        int boxHeight = puzzle.getBOXHEIGHT();
        for (int boxRow = 0; boxRow < puzzle.getNumRows(); boxRow += boxHeight) {
            for (int boxCol = 0; boxCol < puzzle.getNumColumns(); boxCol += boxWidth) {
                Set<String> seenInBox = new HashSet<>();
                for (int row = boxRow; row < boxRow + boxHeight; row++) {
                    for (int col = boxCol; col < boxCol + boxWidth; col++) {
                        String value = puzzle.board[row][col];
                        if (!value.isEmpty() && !seenInBox.add(value)) {
                            g2d.fillRect(col * slotWidth, row * slotHeight, slotWidth, slotHeight);
                        }
                    }
                }
            }
        }

        // Draw grid lines on top of everything
        for (int x = 0; x <= getWidth(); x += slotWidth) {
            g2d.setColor(Color.GRAY);
            g2d.drawLine(x, 0, x, getHeight());
        }
        for (int y = 0; y <= getHeight(); y += slotHeight) {
            g2d.setColor(Color.GRAY);
            g2d.drawLine(0, y, getWidth(), y);
        }

        // Thicker Boundaries on each Boxes
        g2d.setStroke(new BasicStroke(2));
        for (int i = 0; i <= 9; i += 3) {
            g2d.setColor(Color.BLACK);
            g2d.drawLine(i * slotWidth, 0, i * slotWidth, getHeight());
            g2d.drawLine(0, i * slotHeight, getWidth(), i * slotHeight);
        }
    }
}
