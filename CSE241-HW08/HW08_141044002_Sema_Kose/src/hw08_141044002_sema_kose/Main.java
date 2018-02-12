//Sema Köse
//141044002
//HW08

package hw08_141044002_sema_kose;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        ConnectFour game;
        JFrame frame = new JFrame("Size Input");
        JOptionPane option = new JOptionPane();
        String oneOrTwo = option.showInputDialog("Please enter a 1 or 2 player: ");
        String size = option.showInputDialog("Please enter a size(n) value for nxn game board: ");
        game = new ConnectFour(Integer.valueOf(size), Integer.valueOf(oneOrTwo));
        game.playGame();
    }
}
