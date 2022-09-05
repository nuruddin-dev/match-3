import java.util.*;

// class Board
class Board
{
    static final int CELL_EMPTY = 0; 
    static final String[] CELL_LABELS = {"1","2","3","4","5","6","7","8","9"};
    int numrows,numcols;
    int[][] board;
    //this is a special valued array in which I put the value such as it can be divided in four parts that helps to put values randomly
    int arr[] = {0,	1,	4,	5,	8,	9 , 2,	3,	6,	7,	10,	11, 12,	13,	16,	17,	20,	21, 14,	15,	18,	19,	22,	23};
    int newArr[] = new int[24];
    int location, arrayPart=1;
    
    //method that will setup the random numbers for whole board
    public void setValue() {
    	for(int i=1; i<9; i++) {
    		for(int j=0; j<3; j++) {
    			arrayPart = arrayPart%4;
        		location = RandomValueGenerator(arrayPart);
    			arr[location] = -1;
    			newArr[location] = i;
    			arrayPart ++;
    		}
    	}
    	for(int i=0;i < numrows; i++) {
            for(int j=0;j < numcols; j++) {
                board[i][j] = newArr[i*4 + j];
            }
        }
    }
    
    int RandomValueGenerator(int arrayPart) {
    	int val = (int)(Math.random()*6);
    	if(arrayPart == 2)
			val = val+6;
		if(arrayPart ==3)
			val = val+12;
		if(arrayPart ==0)
			val = val+18;
		if(arr[val] == -1) {
		   return RandomValueGenerator(arrayPart);
		}
    	return val;
    }

    // create an empty board
    Board(int numrows, int numcols)
    {
        this.numrows = numrows;
        this.numcols = numcols;
        board = new int[numrows][numcols];
        for(int i=0;i < numrows; i++) {
            for(int j=0;j < numcols; j++) {
                board[i][j] = CELL_EMPTY;
            }
        }
    }

    // construct a copy of an existing board
    Board(Board oldboard) {
        numrows = oldboard.getNumRows();
        numcols = oldboard.getNumCols();
        board = new int[numrows][numcols];
        for(int i=0;i < numrows; i++) {
            for(int j=0;j < numcols; j++) {
                board[i][j] = oldboard.board[i][j];
            }
        }
    }

    public int getNumRows() {
        return numrows;
    }

    public int getNumCols() {
        return numcols;
    }

    // set the entire board to empty cells
    public void resetBoard() {
        for(int i=0;i < numrows; i++) {
            for(int j=0;j < numcols; j++) {
                board[i][j] = CELL_EMPTY;
            }
        }
    }

    public int getValueAt(int row, int col) {
        return board[row][col];
    }
}

