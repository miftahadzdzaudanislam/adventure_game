package main;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame(); // instance JFrame
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // tombol clode
        window.setResizable(false); // tombol maximum
        window.setTitle("My Game"); // judul

        ImageIcon icon = new ImageIcon("./res/player/depan_diam.png"); // path ikon
        window.setIconImage(icon.getImage()); // memuat icon

        GamePanel gamePanel = new GamePanel(); // instance gamePanel
        window.add(gamePanel); // tambah panel

        window.pack(); // atur ukuran jendela

        window.setLocationRelativeTo(null); // jendela ditengah 
        window.setVisible(true); // jendela terlihat

        gamePanel.setupGame(); // men-setup game
        gamePanel.startGameThread(); // memulai game thred
    }
}
