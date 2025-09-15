package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class Entity {
    GamePanel gp; // referensi ke game panel

    public int worldX, worldY; // var lokasi entitas
    public int speed; // var kecepatan entitas

    // var gambar entitas
    public BufferedImage up, up1, up2;
    public BufferedImage down, down1, down2;
    public BufferedImage left, left1, left2;
    public BufferedImage right, right1, right2;
    
    public String direction; // var arah entias

    public int spriteCounter = 0; // kapan animasi berganti
    public int spriteNum = 1; // nomor sprite yang ditampilkan

    public Rectangle solidArea = new Rectangle(12, 0, 40, 63); // persegi transparan untuk collision
    public boolean collisionOn = false; // tabrakan default

    public int solidAreaDX = solidArea.x;
    public int solidAreaDY = solidArea.y; // var area solid object

    public int actionLockConter = 0;
    public String dialogue[] = new String[20]; // var teks dialog panjang 20
    public int dialogueIndex = 0; // var index dialog

    // entity construktor (abstarak)
    public Entity(GamePanel gp) {
        this.gp = gp; // instance game panel
    }

    // fungsi untuk menggerakan NPC
    public void setAction() {}
    // fungsi pada saat NPC berbicara
    public void speak() {
        // jila dalog null
        if (dialogue[dialogueIndex] == null) {
            dialogueIndex = 0; // index dialog kembali 0
        }

        gp.ui.currentDialogue = dialogue[dialogueIndex]; // menampilkan dialog 
        dialogueIndex++; // index dialog nambah

        // arah NPC
        switch (gp.player.direction) {
            case "up":
                direction = "down";
                break;
            case "down":
                direction = "up";
                break;
            case "left":
                direction = "right";
                break;
            case "right":
                direction = "left";
                break;
        }
    }

    // fungsi update NPC
    public void update() {
        setAction(); // mengambil pergerakan ai NPC

        collisionOn = false; // tabrakan false
        gp.cChecker.checkTile(this); // cek tabrakan tile
        gp.cChecker.checkObject(this, false); // cek tabrakan objek
        gp.cChecker.checkPlayer(this); // cek tabrakan player

        // if collision = false, maka player diam
        if (collisionOn == false) {
            switch (direction) {
                case "up": worldY -= speed; break;
                case "down": worldY += speed; break;
                case "left": worldX -= speed; break;
                case "right": worldX += speed; break;
            }
        }

        // mengatur animasi gerakan
        spriteCounter++;
        if (spriteCounter > 12) {
            if (spriteNum == 1) {
                spriteNum = 2;
            } else if (spriteNum == 2) {
                spriteNum = 1;
            } 
            spriteCounter = 0;
        }
    }

    // fungsi untuk menggambar entitas
    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        int screenX = worldX - gp.player.worldX + gp.player.screenX; // posisi relatif tile x di jandela
        int screenY = worldY - gp.player.worldY + gp.player.screenY; // posisi relatif tile y di jendela

        if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX && // kanan tile > kiri jendela
            worldX - gp.tileSize < gp.player.worldX + gp.player.screenX && // kiri tile < kanan jendela
            worldY + gp.tileSize > gp.player.worldY - gp.player.screenY && // bawah tile > atas jendela
            worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) { // atas tile < bawah jendela

            // case arah pemain
            switch (direction) {
                case "up":
                    if (spriteNum == 1) {
                        image = up1;
                    } if (spriteNum == 2) {
                        image = up2;
                    }
                    break;
                case "down":
                    if (spriteNum == 1) {
                        image = down1;
                    } if (spriteNum == 2) {
                        image = down2;
                    }
                    break;
                case "left":
                    if (spriteNum == 1) {
                        image = left1;
                    } if (spriteNum == 2) {
                        image = left2;
                    }
                    break;
                case "right":
                    if (spriteNum == 1) {
                        image = right1;
                    } if (spriteNum == 2) {
                        image = right2;
                    }
                    break;
            }

            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);

            // Gambar outline rectangle merah
            // g2.setColor(Color.RED);
            // g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);
        }
    }

    // fungsi untuk setup gambar player/npc/monster
    public BufferedImage setup(String imagePath) {
        UtilityTool uTool = new UtilityTool(); // instanceutility tool
        BufferedImage image = null; // var path gambar

        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res" + imagePath + ".png"));
            image = uTool.scaledImage(image, gp.tileSize, gp.tileSize); // ubah ukuran gambar sesuai tile
        } catch (IOException e) {
            // TODO: handle exception
        }
        return image;
    }
}
