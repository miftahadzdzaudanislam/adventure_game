package main;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class UtilityTool {
    // Fungsi untuk mengubah ukuran gambar sesuai dengan lebar dan tinggi 
    public BufferedImage scaledImage(BufferedImage original, int width, int height) {
        BufferedImage scaledImage = new BufferedImage(width, height, original.getType()); // membuat gambar baru
        Graphics2D g2 = scaledImage.createGraphics(); // membuat objek untuk menggambar ulang gambar
        g2.drawImage(original, 0, 0, width, height, null); // gambar ulang gambar asli
        g2.dispose(); // membersihkan objek grafk

        return scaledImage; // Mengembalikan gambar yang telah diubah ukurannya
    }
}
