package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

public class UI {
    GamePanel gp; // referensi ke gamepanel
    Graphics2D g2; // referensi ke grafik 2D
    Font maruMonica, purisaB; // var font maru Monice dan purisa Bold
    public boolean messageOn = false; // var notifikasi
    public String message = ""; // var text notifikasi
    int messageConter = 0; // var waktu tampilan notifikasi
    public boolean gameFinished = false; // var game selesai atau belum
    public String currentDialogue = ""; // vae text dialog
    
    double playTime; // var untuk waktu main
    DecimalFormat dFormat = new DecimalFormat("#0.00"); // instance format waktu

    // fungsi konstruktor tampilan UI
    public UI(GamePanel gp) {
        this.gp = gp; // instance gamepanel

        try {
            InputStream is = getClass().getResourceAsStream("/res/font/x12y16pxMaruMonica.ttf");
            maruMonica = Font.createFont(Font.TRUETYPE_FONT, is); // membuat font maru Monica

            is = getClass().getResourceAsStream("/res/font/Purisa Bold.ttf");
            purisaB = Font.createFont(Font.TRUETYPE_FONT, is); // membuat font purisa bold
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // fungsi untuk menampilkan notifikasi
    public void sendMessage(String text) {
        message = text;
        messageOn = true;
    }

    // fungsi menggambar UI
    public void draw(Graphics2D g2) {
        this.g2 = g2; // instance grafik 2D

        g2.setFont(maruMonica); // set font maru Monica
        // g2.setFont(purisaB); // set font Purisa Bold
        g2.setColor(Color.WHITE);

        // PLAY STATE
        if (gp.gameState == gp.playState) { 
            // 
        }
        // PAUSE STATE
        if (gp.gameState == gp.pauseState) { 
            drawPauseScreen(); // memuat fungsi pause
        }
        // DIALOGUE STATE
        if (gp.gameState == gp.dialogueState) {
            drawDialogueScreen(); // memuat fungsi dialog
        }
    }

    // fungsi saat game di pause
    public void drawPauseScreen() {
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,80F));
        String text = "PAUSED"; // teks saat pause
        int x = getXforCenteredText(text); // text berada ditengah

        int y = gp.screenHeight/2; // lokasi text pause y

        g2.drawString(text, x, y); // gambar text pause
    }

    // Fungsi memuat frame dialogue
    public void drawDialogueScreen() {
        int x = gp.tileSize; // lokasi dialog x / Horizontal
        int y = gp.tileSize/2; // lokasi dialog y / Vertikal
        int width = gp.screenWidth - (gp.tileSize*2 ); // lebar frame
        int height = gp.tileSize*3 + (gp.tileSize/2); // tinggi frame

        drawSubWindow(x, y, width, height); // memuat frame

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 32F)); // font dialog
        x += gp.tileSize/2; // lokasi text dialog x di frame
        y += gp.tileSize; // lokasi text dialog y di frame

        for (String line : currentDialogue.split("\n")) {
            g2.drawString(line, x, y); // memuat teks dialog
            y += 40;
        }        
    }

    // Fungsi untuk gambar sub window dialog
    public void drawSubWindow(int x, int y, int width, int height) {
        Color c = new Color(0, 0, 0, 210); // warna frame hitam
        g2.setColor(c); // memuat warna hitam
        g2.fillRoundRect(x, y, width, height, 35, 35); // gambar balok dialog

        c = new Color(255, 255, 255); // warna border putih
        g2.setColor(c); // memuat warna border putih
        g2.setStroke(new BasicStroke(5)); // memuat border ukuran 5
        g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25); // gambar border
    }

    // fungsi agar text berada ditengah
    public int getXforCenteredText(String text) {
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth(); // panjang text
        int x = gp.screenWidth/2 - length/2; // lokasi text pause x
        return x;
    }
}
