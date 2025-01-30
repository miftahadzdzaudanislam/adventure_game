package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

import object.OBJ_Key;

public class UI {
    GamePanel gp; // referensi ke gamepanel
    Font arial_40, arial_80B; // var font arial dengan ukuran
    BufferedImage keyImage; // var gambar kunci
    public boolean messageOn = false; // var notifikasi
    public String message = ""; // var tulisan notifikasi
    int messageConter = 0; // var ?
    public boolean gameFinished = false; // var ??
    
    double playTime; // var untuk waktu main
    DecimalFormat dFormat = new DecimalFormat("#0.00"); // instance format waktu

    // fungsi tampilan UI
    public UI(GamePanel gp) {
        this.gp = gp; // instance gamepanel

        arial_40 = new Font("Arial", Font.PLAIN, 40); // instance font arial 40
        arial_80B = new Font("Arial", Font.BOLD, 80); // instance font arial 80
        OBJ_Key key = new OBJ_Key(); // instance objek key
        keyImage = key.image; // gambar kunci
    }

    // fungsi untuk notifikasi
    public void sendMessage(String text) {
        message = text;
        messageOn = true;
    }

    // fungsi menggambar UI
    public void draw(Graphics2D g2) {
        if (gameFinished ==  true) {
            g2.setFont(arial_40);
            g2.setColor(Color.white);

            String text;
            int textLength;
            int x;
            int y;

            text = "Kamu menemukan Hartakarun!!";
            textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = gp.screenWidth/2 - textLength/2;
            y = gp.screenHeight/2 - (gp.tileSize*3);
            g2.drawString(text, x, y);

            text = "Kamu bermain selama " + dFormat.format(playTime) + "!";
            textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = gp.screenWidth/2 - textLength/2;
            y = gp.screenHeight/2 + (gp.tileSize*4);
            g2.drawString(text, x, y);

            g2.setFont(arial_80B);
            g2.setColor(Color.yellow);
            text = "SELAMAT!!";
            textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = gp.screenWidth/2 - textLength/2;
            y = gp.screenHeight/2 + (gp.tileSize*2);
            g2.drawString(text, x, y);

            gp.gameThread = null;
        } else {
            g2.setFont(arial_40); // memuat font
            g2.setColor(Color.white); // memuat warna
            g2.drawImage(keyImage, gp.tileSize/2, gp.tileSize/2, gp.tileSize, gp.tileSize, null); // gambar key
            g2.drawString("x " + gp.player.hasKey, 74, 63); // menggambar string
    
            // TIME
            playTime += (double)1/60;
            g2.drawString("Time: " + dFormat.format(playTime), gp.tileSize*11, 63);

            // MESSAGE
            if (messageOn == true) {
                g2.setFont(g2.getFont().deriveFont(30F));
                g2.drawString(message, gp.tileSize/2, gp.tileSize*5);
    
                messageConter++;
    
                if (messageConter > 60) {
                    messageConter = 0;
                    messageOn = false;
                }
            }
        }
    }
}
