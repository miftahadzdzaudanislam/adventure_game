package object;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.GamePanel;
import main.UtilityTool;

public class SuperObject {
    public BufferedImage image; // var gambar objek
    public String name; // var nama objek
    public boolean collision = false; // var tabrakan objek
    public int worldX, worldY; // var lokasi objek
    public Rectangle solidArea = new Rectangle(0, 0, 60, 60); // instance persegi area
    public int solidAreaDX = 0; // var area objek x
    public int solidAreaDY = 0; // var area objek y
    public UtilityTool uTool = new UtilityTool(); // instance utility tool

    // fungsi menggambar objek
    public void draw(Graphics2D g2, GamePanel gp) {
        int screenX = worldX - gp.player.worldX + gp.player.screenX; // posisi relatif tile x di jandela
        int screenY = worldY - gp.player.worldY + gp.player.screenY; // posisi relatif tile y di jendela

        if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX && // kanan tile > kiri jendela
            worldX - gp.tileSize < gp.player.worldX + gp.player.screenX && // kiri tile < kanan jendela
            worldY + gp.tileSize > gp.player.worldY - gp.player.screenY && // bawah tile > atas jendela
            worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) { // atas tile < bawah jendela

            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }
    }
}
