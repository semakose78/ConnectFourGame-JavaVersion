//Sema Köse
//141044002
//HW08

package hw08_141044002_sema_kose;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ConnectFour extends JFrame{
    private int size;
    private int singleOrMulti;
    private int playerOrComputer;
    private static int numberOfLivingCells;
    private JPanel mainPanel = new JPanel();
    private Cell[][] gameCells;
    private ConnectFourButtons[] mainButtons;
    public ConnectFour(){

    }
    public ConnectFour(int size, int singleOrMulti) throws IOException {
        //Game Initializations
        super("Connect Four");
        setSize(size);
        setSingleOrMulti(singleOrMulti);
        setPlayerOrComputer(0);
        mainButtons = new ConnectFourButtons[size];
        gameCells = new Cell[size][size+1];
        ImageIcon ic = new ImageIcon(System.getProperty("user.dir") + "/button.png");
        setSize(600,600);
        Image img = ic.getImage();
        Image img2 = img.getScaledInstance((600/(this.size+(this.size/2))), (600/(this.size+(this.size/2)))-(size*3), java.awt.Image.SCALE_SMOOTH);
        ic = new ImageIcon(img2);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        mainPanel.setLayout(new GridLayout(this.size+1,this.size));
        //First, create and add the buttons to top of the screen panel
        for(int i=0; i<this.size; ++i){
            mainButtons[i] = new ConnectFourButtons(ic);
            mainPanel.add(mainButtons[i]);
        }
        //And then create and add the game cells
        for(int i=0; i<this.size; ++i){
            for(int j=0; j<this.size; ++j){
                gameCells[i][j] = new Cell();
                gameCells[i][j].setEditable(false);
                mainPanel.add(gameCells[i][j]);
            }
        }
        add(mainPanel);
        setVisible(true);
    }

    public int playGame(){
        int ret = 0;
        if(getSingleOrMulti() == 1) //If one player
            ret = computerVersusPlayer();
        else if(getSingleOrMulti() == 2)
            ret = playerVersusPlayer(); //If two player
        return ret;
    }

    public int playerVersusPlayer(){
        //Add action listener to all buttons
        for (int i=0; i<getBoardSize(); ++i){
            int finalI = i;
            final boolean[] dummy = {false};
            final int[] isItScore = {0};
            mainButtons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    for(int i=0; i<getBoardSize(); ++i){
                        if(isBoardAvailable() && (gameCells[getBoardSize()-i-1][finalI].getSituation() == 0)){ //put the user to empty place
                            if((playerOrComputer == 0) && isBoardAvailable()){
                                gameCells[getBoardSize()-i-1][finalI].setBackground(Color.red);
                                gameCells[getBoardSize()-i-1][finalI].setSituation(1);
                                gameCells[getBoardSize()-i-1][finalI].setColumn(finalI);
                                gameCells[getBoardSize()-i-1][finalI].setRow(getBoardSize()-i-1);
                                isItScore[0] = isItAScore(getBoardSize()-i-1, finalI);
                                //is game over?
                                if(isItScore[0] == 1){
                                    JOptionPane.showMessageDialog(new Frame(),"Game Over! Player " + (playerOrComputer+1) + " won!");
                                    //if user wants to play a new game
                                    if(JOptionPane.showConfirmDialog(null, "Do you want to start a new game?", "New Game",
                                            JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
                                        try {
                                            newGame();
                                        } catch (IOException e1) {
                                            e1.printStackTrace();
                                        }
                                    }else{
                                        //If user doesn't want to play a new game, terminate the program
                                        Runtime.getRuntime().exit(1);
                                    }
                                }
                                if(!isBoardAvailable()){
                                    JOptionPane.showMessageDialog(new Frame(),"Board is filled!");
                                    //if user wants to play a new game
                                    if(JOptionPane.showConfirmDialog(null, "Do you want to start a new game?", "New Game",
                                            JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
                                        try {
                                            newGame();
                                        } catch (IOException e1) {
                                            e1.printStackTrace();
                                        }
                                    }else{
                                        //If user doesn't want to play a new game, terminate the program
                                        Runtime.getRuntime().exit(1);
                                    }
                                }
                                playerOrComputer = 1;
                            }else if((playerOrComputer == 1) && isBoardAvailable()){
                                gameCells[getBoardSize()-i-1][finalI].setBackground(Color.blue);
                                gameCells[getBoardSize()-i-1][finalI].setSituation(2);
                                gameCells[getBoardSize()-i-1][finalI].setColumn(finalI);
                                gameCells[getBoardSize()-i-1][finalI].setRow(getBoardSize()-i-1);
                                //is game over?
                                isItScore[0] = isItAScore(getBoardSize()-i-1, finalI);
                                if(isItScore[0] == 1){
                                    JOptionPane.showMessageDialog(new Frame(),"Game Over! Player " + (playerOrComputer+1) + " won!");
                                    //if user wants to play a new game
                                    if(JOptionPane.showConfirmDialog(null, "Do you want to start a new game?", "New Game",
                                            JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
                                        try {
                                            newGame();
                                        } catch (IOException e1) {
                                            e1.printStackTrace();
                                        }
                                    }else{
                                        //If user doesn't want to play a new game, terminate the program
                                        Runtime.getRuntime().exit(1);
                                    }
                                }
                                playerOrComputer = 0;
                                if(!isBoardAvailable()){
                                    JOptionPane.showMessageDialog(new Frame(),"Board is filled!");
                                    //if user wants to play a new game
                                    if(JOptionPane.showConfirmDialog(null, "Do you want to start a new game?", "New Game",
                                            JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
                                        try {
                                            newGame();
                                        } catch (IOException e1) {
                                            e1.printStackTrace();
                                        }
                                    }else{
                                        //If user doesn't want to play a new game, terminate the program
                                        Runtime.getRuntime().exit(1);
                                    }
                                }
                            }
                            dummy[0] = true;
                            break;
                        }
                    }
                    if(dummy[0] == false){
                        JOptionPane.showMessageDialog(new Frame(),"Please make another move!");
                    }
                    dummy[0] = false;
                }
            });
        }
        return 0;
    }

    public int computerVersusPlayer(){
        for (int i=0; i<getBoardSize(); ++i){
            int finalI = i;
            final boolean[] dummy = {false};
            final int[] isItScore = {0};
            mainButtons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    for(int i=0; i<getBoardSize(); ++i){
                        if(isBoardAvailable() && (gameCells[getBoardSize()-i-1][finalI].getSituation() == 0)){ //put the user to empty place
                            if((playerOrComputer == 0) && isBoardAvailable()){
                                gameCells[getBoardSize()-i-1][finalI].setBackground(Color.red);
                                gameCells[getBoardSize()-i-1][finalI].setSituation(1);
                                gameCells[getBoardSize()-i-1][finalI].setColumn(finalI);
                                gameCells[getBoardSize()-i-1][finalI].setRow(getBoardSize()-i-1);
                                isItScore[0] = isItAScore(getBoardSize()-i-1, finalI);
                                //is game over?
                                if(isItScore[0] == 1){
                                    JOptionPane.showMessageDialog(new Frame(),"Game Over! Player " + (playerOrComputer+1) + " won!");
                                    //if user wants to play a new game
                                    if(JOptionPane.showConfirmDialog(null, "Do you want to start a new game?", "New Game",
                                            JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
                                        try {
                                            newGame();
                                        } catch (IOException e1) {
                                            e1.printStackTrace();
                                        }
                                    }else{
                                        //If user doesn't want to play a new game, terminate the program
                                        Runtime.getRuntime().exit(1);
                                    }
                                }
                                playerOrComputer = 1;
                            }
                            dummy[0] = true;
                            break;
                        }
                    }
                    int temp = 0;
                    int temp2 = 0;
                    int[] odds = new int[getBoardSize()];
                    int[] oddsPlayer = new int[getBoardSize()];
                    if((playerOrComputer == 1) && isBoardAvailable()){
                        for(int compI=0; compI<getBoardSize(); ++compI){
                            for(int compJ=getBoardSize()-1; compJ>=0; --compJ){
                                if(gameCells[compJ][compI].getSituation() == 0){
                                    odds[compI] = bestOdd(compI, compJ);
                                    playerOrComputer = 0;
                                    oddsPlayer[compI] = bestOdd(compI, compJ);
                                    playerOrComputer = 1;
                                    temp = 1;
                                    break;
                                }
                            }
                            if(temp == 0){
                                odds[compI] = 0;
                                oddsPlayer[compI] = 0;
                            }
                        }
                        temp = odds[0];
                        temp2 = oddsPlayer[0];
                        int index = 0;
                        int index2 = 0;
                        for(int compI=0; compI<getBoardSize(); ++compI){
                            if(oddsPlayer[compI] >= temp2){
                                temp2 = oddsPlayer[compI];
                                index2 = compI;
                            }
                        }
                        for(int compI=0; compI<getBoardSize(); ++compI){
                            if(odds[compI] >= temp){
                                temp = odds[compI];
                                index = compI;
                            }
                        }
                        if((temp2>=2) && (temp<3))
                            index = index2;
                        for(int compJ=getBoardSize()-1; compJ>=0; --compJ){
                            if(gameCells[compJ][index].getSituation() == 0){
                                gameCells[compJ][index].setBackground(Color.blue);
                                gameCells[compJ][index].setSituation(2);
                                gameCells[compJ][index].setColumn(index);
                                gameCells[compJ][index].setRow(compJ);
                                //is game over?
                                isItScore[0] = isItAScore(compJ, index);
                                if(isItScore[0] == 1){
                                    JOptionPane.showMessageDialog(new Frame(),"Game Over! Computer won!");
                                    //if user wants to play a new game
                                    if(JOptionPane.showConfirmDialog(null, "Do you want to start a new game?", "New Game",
                                            JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
                                        try {
                                            newGame();
                                        } catch (IOException e1) {
                                            e1.printStackTrace();
                                        }
                                    }else{
                                        //If user doesn't want to play a new game, terminate the program
                                        Runtime.getRuntime().exit(1);
                                    }
                                }else if((isItScore[0] == 0) && (!isBoardAvailable())){
                                    JOptionPane.showMessageDialog(new Frame(),"Board is filled!");
                                    //if user wants to play a new game
                                    if(JOptionPane.showConfirmDialog(null, "Do you want to start a new game?", "New Game",
                                            JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
                                        try {
                                            newGame();
                                        } catch (IOException e1) {
                                            e1.printStackTrace();
                                        }
                                    }else{
                                        //If user doesn't want to play a new game, terminate the program
                                        Runtime.getRuntime().exit(1);
                                    }
                                }
                                playerOrComputer = 0;
                                break;
                            }
                        }
                    }
                    if(dummy[0] == false){
                        JOptionPane.showMessageDialog(new Frame(),"Please make another move!");
                    }
                    dummy[0] = false;
                }

            });
        }
        return 0;
    }

    //Take the new inputs and start a new game
    public void newGame() throws IOException {
        setVisible(false);
        ConnectFour game;
        JFrame frame = new JFrame("Size Input");
        String oneOrTwo = JOptionPane.showInputDialog("Please enter a 1 or 2 player: ");
        String size = JOptionPane.showInputDialog("Please enter a size(n) value for nxn game board: ");
        game = new ConnectFour(Integer.valueOf(size), Integer.valueOf(oneOrTwo));
        //Change the first player who starts the game
        if((this.singleOrMulti == 2) && (game.singleOrMulti == 2)){
            if(this.playerOrComputer == 0)
                game.setPlayerOrComputer(1);
            else
                game.setPlayerOrComputer(0);
        }
        game.playGame();
    }

    //check if the move is score
    public int isItAScore(int row, int column){
        int count = 0;
        int score = 0;
        //diagonally right up to left bottom
        count = 1;
        int i = row;
        int j = column;

        while(i<size && j>0 && (gameCells[j][i].getSituation() == playerOrComputer+1)){
            ++i;
            --j;
        }
        --i;
        ++j;
        if(getSingleOrMulti()==1){
            while(i>=0 && j<size && (gameCells[i][j].getSituation() == playerOrComputer+1)){
                --i;
                ++j;
                ++count;
            }
        }else if(getSingleOrMulti() == 2){
            while(i>=0 && j<size && (gameCells[j][i].getSituation() == playerOrComputer+1)){
                --i;
                ++j;
                ++count;
            }
        }
        if(count>=4)
            ++score;
        //diagonally left up to right bottom
        count = 1;
        i = column;
        j = row;

        while(i>0 && j>0 && (gameCells[j-1][i-1].getSituation() == playerOrComputer+1)){
            --i;
            --j;
        }
        ++i;
        ++j;
        while(i<size && j<size && (gameCells[j][i].getSituation() == playerOrComputer+1)){
            ++i;
            ++j;
            ++count;

        }
        if(count>=4)
            ++score;

        //vertical
        i=row;
        count = 0;
        while(i<size && gameCells[i][column].getSituation() == playerOrComputer+1){
            ++i;
            ++count;
        }
        if(count>=4)
            ++score;

        //horizontal
        count = 0;
        i = column;

        while(i>0 && gameCells[row][i-1].getSituation() == playerOrComputer+1){
            --i;
        }
        while(i<size && gameCells[row][i].getSituation() == playerOrComputer+1){
            ++i;
            ++count;
        }
        if(count>=4)
            ++score;

        return score;
    }
    public void setPlayerOrComputer(int i){
        playerOrComputer = i;
    }
    //calculate the move for computer
    public int bestOdd(int column, int row){
        int count = 0;
        int score = 0;
        //diagonally right up to left bottom
        count = 1;
        int i = row;
        int j = column;
        int[] counts = new int[4];
        int temp;

        while(i<size && j>0 && (gameCells[j][i].getSituation() == playerOrComputer+1)){
            ++i;
            --j;
        }
        --i;
        ++j;
        while(i>0 && j<size && (gameCells[j][i].getSituation() == playerOrComputer+1)){
            --i;
            ++j;
            ++count;
        }
        counts[0] = count;
        if(count>=4)
            ++score;
        //diagonally left up to right bottom
        count = 1;
        i = column;
        j = row;

        while(i>0 && j>0 && (gameCells[j-1][i-1].getSituation() == playerOrComputer+1)){
            --i;
            --j;
        }
        ++i;
        ++j;
        while(i<size && j<size && (gameCells[j][i].getSituation() == playerOrComputer+1)){
            ++i;
            ++j;
            ++count;

        }
        counts[1] = count;
        if(count>=4)
            ++score;

        //vertical
        i=row;
        count = 0;
        while(i<size-1 && gameCells[i+1][column].getSituation() == playerOrComputer+1){
            ++i;
            ++count;
        }
        counts[2] = count;
        if(count>=4)
            ++score;

        //horizontal
        count = 0;
        i = column;

        while(i>0 && gameCells[row][i-1].getSituation() == playerOrComputer+1){
            --i;
        }
        while(i<size && gameCells[row][i].getSituation() == playerOrComputer+1){
            ++i;
            ++count;
        }
        counts[3] = count;
        if(count>=4)
            ++score;

        temp = counts[0];
        for(int k=1; k<4; ++k){
            if(counts[k]>temp)
                temp = counts[k];

        }
        return temp;
    }
    public boolean isBoardAvailable(){
        boolean dummy = false;
        for(int i = 0; i<size; ++i){
            for(int j = 0; j<size; ++j){
                if(gameCells[i][j].getSituation()==0){
                    dummy = true;
                    break;
                }
            }
            if(dummy == true)
                break;
        }
        if(dummy == true)
            return true;
        else
            return false;
    }
    //The inner cell class for representing game cells
    private class Cell extends JTextField{ //holds the position of the cell
        public Cell() {
            column = 0;
            row = 0;
            situation = 0;
        }
        public Cell(char col, int row){}

        public int getColumn() {return column;} //inline function

        public int getRow() {return row;} //inline function

        public int getSituation() {return situation;} //inline function

        public void setColumn(int column){this.column = column;}

        public void setRow(int row){this.row = row;}

        public void setSituation(int situation){this.situation = situation;}

        private int column;
        private int row;
        private int situation; //0 for empty, 1 for user1, 2 for computer or user2
        ImageIcon X,O;

    }
    public void setSize(int size) {this.size = size;}
    public void setSingleOrMulti(int singleOrMulti){this.singleOrMulti = singleOrMulti;}
    public static int getNumberOfLivingCells() {return numberOfLivingCells;}
    public int getBoardSize() {return size;} //inline function returns the current width
    public int getSingleOrMulti() {return singleOrMulti;}

}
