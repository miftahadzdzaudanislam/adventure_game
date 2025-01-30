package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Entity {
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

    public Rectangle solidArea; // persegi transparan untuk collision
    public boolean collisionOn = false; // tabrakan default

    public int solidAreaDX, solidAreaDY; // var area solid object
}
