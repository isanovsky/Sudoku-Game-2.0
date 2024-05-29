package sudokugame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class SudokuGenerator {
    public static SudokuPuzzle generateRandomSudoku(SudokuPuzzleType puzzleType) {
        SudokuPuzzle puzzle = new SudokuPuzzle(puzzleType.getRows(), puzzleType.getColumns(),
                puzzleType.getBoxWidth(), puzzleType.getBoxHeight(), puzzleType.getValidValues());

        fillDiagonalBoxes(puzzle);
        fillRemaining(puzzle, 0, puzzle.getBOXWIDTH());

        removeDigits(puzzle);
        return puzzle;
    }

    private static void fillDiagonalBoxes(SudokuPuzzle puzzle) {
        for (int i = 0; i < puzzle.getNumRows(); i += puzzle.getBOXHEIGHT()) {
            fillBox(puzzle, i, i);
        }
    }

    private static void fillBox(SudokuPuzzle puzzle, int row, int col) {
        Random random = new Random();
        List<String> numbers = new ArrayList<>(Arrays.asList(puzzle.getVALIDVALUES()));
        for (int r = 0; r < puzzle.getBOXHEIGHT(); r++) {
            for (int c = 0; c < puzzle.getBOXWIDTH(); c++) {
                int idx = random.nextInt(numbers.size());
                puzzle.makeMove(row + r, col + c, numbers.get(idx));
                numbers.remove(idx);
            }
        }
    }

    private static boolean fillRemaining(SudokuPuzzle puzzle, int i, int j) {
        if (j >= puzzle.getNumColumns() && i < puzzle.getNumRows() - 1) {
            i = i + 1;
            j = 0;
        }
        if (i >= puzzle.getNumRows() && j >= puzzle.getNumColumns()) {
            return true;
        }
        if (i < puzzle.getBOXHEIGHT()) {
            if (j < puzzle.getBOXWIDTH()) {
                j = puzzle.getBOXWIDTH();
            }
        } else if (i < puzzle.getNumRows() - puzzle.getBOXHEIGHT()) {
            if (j == (i / puzzle.getBOXHEIGHT()) * puzzle.getBOXHEIGHT()) {
                j = j + puzzle.getBOXWIDTH();
            }
        } else {
            if (j == puzzle.getNumRows() - puzzle.getBOXWIDTH()) {
                i = i + 1;
                j = 0;
                if (i >= puzzle.getNumRows()) {
                    return true;
                }
            }
        }
        for (String num : puzzle.getVALIDVALUES()) {
            if (isSafe(puzzle, i, j, num)) {
                puzzle.makeMove(i, j, num);
                if (fillRemaining(puzzle, i, j + 1)) {
                    return true;
                }
                puzzle.makeMove(i, j, "");
            }
        }
        return false;
    }

    private static boolean isSafe(SudokuPuzzle puzzle, int i, int j, String num) {
        return !puzzle.numInRow(i, num) && !puzzle.numInCol(j, num) && !puzzle.numInBox(i, j, num);
    }

    private static void removeDigits(SudokuPuzzle puzzle) {
        Random random = new Random();
        int cellsToRemove = 40;  // number of cells to be empty
        while (cellsToRemove != 0) {
            int i = random.nextInt(puzzle.getNumRows());
            int j = random.nextInt(puzzle.getNumColumns());
            if (!puzzle.board[i][j].isEmpty()) {
                puzzle.board[i][j] = "";
                cellsToRemove--;
            }
        }
    }
}
