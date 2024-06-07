package sudokugame;

public class SudokuPuzzle {
    protected String[][] board;
    private final int ROWS;
    private final int COLUMNS;
    private final int BOXWIDTH;
    private final int BOXHEIGHT;
    private final String[] VALIDVALUES;

    public SudokuPuzzle(int rows, int columns, int boxwidth, int boxheight, String[] validvalues){
        this.ROWS = rows;
        this.COLUMNS = columns;
        this.BOXWIDTH = boxwidth;
        this.BOXHEIGHT = boxheight;
        this.VALIDVALUES = validvalues;
        this.board = new String[ROWS][COLUMNS];
        initializeBoard();
    }

    public int getNumRows() {
        return ROWS;
    }

    public int getNumColumns() {
        return COLUMNS;
    }

    public int getBOXWIDTH() {
        return BOXWIDTH;
    }

    public int getBOXHEIGHT() {
        return BOXHEIGHT;
    }

    public String[] getVALIDVALUES() {
        return VALIDVALUES;
    }
    @Override
    public String toString(){
        String str = "Game Board:\n";
        for (int row = 0; row < this.ROWS; row++) {
            for (int col = 0; col < this.COLUMNS; col++) {
                str += this.board[row][col] + " ";
            }
            str += "\n";
        }

        return str + "\n";
    }

    private void initializeBoard() {
        for (int row = 0; row < this.ROWS; row++) {
            for (int col = 0; col < this.COLUMNS; col++) {
                this.board[row][col] = "";
            }
        }
    }

    public void makeMove(int row, int col, String value) {
        this.board[row][col] = value;
    }

    public boolean numInRow(int row, String value){
        if (row <= this.ROWS){
            for (int col = 0; col < this.COLUMNS; col++) {
                if (this.board[row][col].equals(value)){
                    return false;
                }
            }
        }
        return true;
    }
    public boolean numInCol(int col, String value){
        if (col <= this.COLUMNS){
            for (int row = 0; row < this.ROWS; row++) {
                if (this.board[row][col].equals(value)){
                    return false;
                }
            }
        }
        return true;
    }
    public boolean numInBox(int row, int col, String value){
        if (inRange(row, col)){
            int boxRow = row / this.BOXHEIGHT;
            int boxCol = col / this.BOXWIDTH;
            int startingRow = boxRow * this.BOXHEIGHT;
            int startingCol = boxCol * this.BOXWIDTH;
            for (int r = startingRow; r <= (startingRow + this.BOXHEIGHT) - 1 ; r++) {
                for (int c = startingCol; c <= (startingCol + this.BOXWIDTH) - 1; c++) {
                    if (this.board[r][c].equals(value)){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean inRange(int row, int col) {
        return row <= this.ROWS && col <= this.COLUMNS && row >= 0 && col >= 0;
    }
}
