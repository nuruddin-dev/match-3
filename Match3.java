import java.awt.*;
import java.awt.event.*;

import javax.swing.*;


// class Match3 implements ActionListener
// constructs the GUI and handles user input for the Match3 game
class Match3 implements ActionListener
{
    Board gameboard;
    final int numrows = 6, numcols = 4;
    boolean pieceSelected, pieceSelected2;
    int selectedRow, selectedCol, selectedRow2, selectedCol2;
    JButton boardButtons[][];
    JLabel statusBar, timeBar;
    JButton resetButton, quitButton;
    JPanel buttonBar, boardPanel, label;
    JFrame jfrm;
    final Color DESELECTED_COLOR = Color.LIGHT_GRAY;
    final Color SELECTED_COLOR[] = {Color.YELLOW,Color.green,Color.ORANGE,Color.white,Color.cyan,Color.MAGENTA,Color.red, Color.BLUE};
    int color=0, points = 0;
    int selectedButton[] = new int[24];
    
    //set all value of selectedButton[] -1 as default
    void DefaultSelectedButton(){
    	for(int i=0; i<selectedButton.length; i++) {
    		selectedButton[i] = -1;
    	}
    }
    
    //update value of selectedButton[]
    void UpdateSelectedButton(int row, int col) {
    	for(int i=0; i<24; i++) {
        	if(selectedButton[i] == -1) {
                selectedButton[i] = (row*10) + col;
                break;
        	}
        }
    }
    
    
    // construct the game board
    Match3() {
    	
        gameboard = new Board(numrows,numcols);
        boardButtons = new JButton[numrows][numcols];
        buttonBar = new JPanel(new FlowLayout());
        resetButton = new JButton("Reset Board");
        quitButton = new JButton("Exit");
        boardPanel = new JPanel(new GridLayout(numrows,numcols,3,3));
        label = new JPanel(new FlowLayout(FlowLayout.CENTER, 150, 0));
        statusBar = new JLabel("Points: " + String.valueOf(points));
        timeBar = new JLabel("Time: " + String.valueOf(points));
        jfrm = new JFrame("Match3");
        
        for(int i=0;i<numrows;i++) {
            for(int j=0;j<numcols;j++) {
                boardButtons[i][j] = new JButton(Board.CELL_LABELS[Board.CELL_EMPTY]);
                boardButtons[i][j].setFont(new Font("Monospaced",Font.PLAIN,20));
                boardButtons[i][j].setActionCommand(
                    String.valueOf(i) + " " + String.valueOf(j));
                boardButtons[i][j].addActionListener(this);
                boardPanel.add(boardButtons[i][j]);
                boardButtons[i][j].setBackground(DESELECTED_COLOR);
                boardButtons[i][j].setOpaque(true);
                boardButtons[i][j].setBorderPainted(false);
            }
        }

        resetButton.addActionListener(this);
        quitButton.addActionListener(this);
        buttonBar.add(resetButton);
        buttonBar.add(quitButton);
        label.add(statusBar);
        label.add(timeBar);
        
        jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        jfrm.add(statusBar,BorderLayout.SOUTH);
//        jfrm.add(timeBar,BorderLayout.AFTER_LAST_LINE);
        jfrm.add(label,BorderLayout.SOUTH);
        jfrm.add(buttonBar,BorderLayout.NORTH);
        jfrm.add(boardPanel,BorderLayout.CENTER);
        jfrm.setSize(500,500);
        jfrm.setResizable(false);
        jfrm.setVisible(true);

        reset();
        
    }

    // reset the board to a random state
    public void reset() {
    	selectedRow = -1;
        selectedCol = -1;
        selectedRow2 = -1;
        selectedCol2 = -1;
        pieceSelected = false;
        pieceSelected2 = false;
        gameboard.resetBoard();
        points = 0;
        setStatus("Points: " + String.valueOf(points));
        setTimeBar("Time: " + String.valueOf(points));
        redrawBoard();
        gameboard.setValue();
         
         DefaultSelectedButton(); // set all default value 
    }

    // change the status bar text
    public void setStatus(String str) {
        statusBar.setText(str);
    }
 // change the status bar text
    public void setTimeBar(String str) {
        timeBar.setText(str);
    }

    // handle button presses
    public void actionPerformed(ActionEvent e) {
        setStatus("Points: " + String.valueOf(points));
        switch(e.getActionCommand()) {
            case "Exit":
                System.exit(0);
            case "Reset Board":
                new Match3();
                break;
            default: // board piece
                String[] cmd = e.getActionCommand().split(" ");
                int cmdrow = Integer.parseInt(cmd[0]);
                int cmdcol = Integer.parseInt(cmd[1]);
                handleClick(cmdrow,cmdcol);
                break;
        }
    }

    // handle a click event at (row,col)
    public void handleClick(int row,int col) {
    	boolean doubleClick = false;
    	//this loop checks if any enabled button clicked more than one and discard button action
    	for(int i=0; i<selectedButton.length; i++) {
        	if(selectedButton[i] == (row*10) + col) {
                doubleClick = true;
        	}
        }
    	//end of loop
    	if(!doubleClick) {
    		//if no button is currently active
    		if(!pieceSelected) {
    			pieceSelected = true;
    			selectedRow = row;
    			selectedCol = col;
    			
    			UpdateSelectedButton(row, col);
    			
    			boardButtons[row][col].setBackground(SELECTED_COLOR[color]);
    			boardButtons[row][col].setText(
    					Board.CELL_LABELS[ gameboard.getValueAt(row,col) ]);
    			return;
    		}
    		//if one button is active and second button is matched with first one
    		else if(!pieceSelected2&&Board.CELL_LABELS[ gameboard.getValueAt(row,col) ]==Board.CELL_LABELS[ gameboard.getValueAt(selectedRow,selectedCol) ]) {
    			pieceSelected2 = true;
    			selectedRow2 = row;
    			selectedCol2 = col;
    			
    			UpdateSelectedButton(row, col);
    			
    			boardButtons[row][col].setBackground(SELECTED_COLOR[color]);
    			boardButtons[row][col].setText(
    					Board.CELL_LABELS[ gameboard.getValueAt(row,col) ]);

    			return;
    		}
    		//if two buttons are active and current button is matched with both active button
    		else if(pieceSelected && pieceSelected2&&Board.CELL_LABELS[ gameboard.getValueAt(row,col) ]==Board.CELL_LABELS[ gameboard.getValueAt(selectedRow,selectedCol) ]) {
    			pieceSelected = false;
    			pieceSelected2 = false;
    			selectedRow = -1;
    			selectedCol = -1;
    			selectedRow2 = -1;
    			selectedCol2 = -1;
    			
    			UpdateSelectedButton(row, col);
    			
    			boardButtons[row][col].setBackground(SELECTED_COLOR[color]);
    			boardButtons[row][col].setText(
    					Board.CELL_LABELS[ gameboard.getValueAt(row,col) ]);

    			points++;
    			setStatus("Points: " + String.valueOf(points));
    			color++;
    			return;
    		}
    		//one or two button is active and current button is not matched with those
    		else {
    			boardButtons[row][col].setText(
    					Board.CELL_LABELS[ gameboard.getValueAt(row,col) ]);
    			//this timer hide the current button number after .2 second
    			Timer timer = new Timer(200, new ActionListener() {
    				public void actionPerformed(ActionEvent evt) {
    					boardButtons[row][col].setText(null);
    				}
    			});
    			timer.setRepeats(false);
    			timer.start();
    		}

    		setStatus("Points: " + String.valueOf(points));
    	}
    }

    // re-draw the board from underlying game data
    public void redrawBoard() {
        for(int i=0; i<numrows; i++) {
            for(int j=0; j<numcols; j++) {
                boardButtons[i][j].setBackground(DESELECTED_COLOR);
                boardButtons[i][j].setText(null);
            }
        }
    }

    // main
    public static void main(String args[])
    {
        new Match3();
    }
}

