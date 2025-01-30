package entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.*;

public class Player extends Entity{
    GamePanel gp; // referensi gamepanel
    KeyHandler keyH; // referensi keyhandler

    public final int screenX; // var layar x 
    public final int screenY; // var layar y

    public int hasKey = 0; // var punya kunci default

    // fungsi player
    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp; // instansiasi GamePanel
        this.keyH = keyH; // instansiasi keyHanlder

        screenX = gp.screenWidth/2 - (gp.tileSize/2); // player berada di tengah layar x
        screenY = gp.screenHeight/2 - (gp.tileSize/2); // player berada di tengah layar y

        solidArea = new Rectangle(); // instance area solid player
        solidArea.x = 8; // ukuran player dikecilkan 8 pada x
        solidArea.y = 16; // ukuran player dikecilkan 16 pada y
        solidAreaDX = solidArea.x; // ukuran area objek x
        solidAreaDY = solidArea.y; // ukuran area objek y
        solidArea.width = gp.tileSize - 16; // lebar blok solid player / 32
        solidArea.height = gp.tileSize - 16; // tinggi blok solid player / 32

        setDefaultValues(); // atur posisi awal
        getPalayerImage(); // memuat sprite player 
    }

    // fungsi untuk atur lokasi dan posisi awal player
    public void setDefaultValues() {
        worldX = gp.tileSize * 23; // posisi awal x / di kolom 24
        worldY = gp.tileSize * 21; // posisi awal y / di baris 22
        speed = 4; // kecepatan pemain
        direction = "down"; // awah awal pemain
    }

    // fungsi untuk memuat gambar player
    public void getPalayerImage() {
        try {
            // memuat gambar setiap arah
            up = ImageIO.read(getClass().getResourceAsStream("/res/player/belakang_diam.png"));
            up1 = ImageIO.read(getClass().getResourceAsStream("/res/player/belakang_kiri.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/res/player/belakang_kanan.png"));
            down = ImageIO.read(getClass().getResourceAsStream("/res/player/depan_diam.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/res/player/depan_kiri.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/res/player/depan_kanan.png"));
            left = ImageIO.read(getClass().getResourceAsStream("/res/player/kanan_diam.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/res/player/kanan_kiri.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/res/player/kanan_kanan.png"));
            right = ImageIO.read(getClass().getResourceAsStream("/res/player/kiri_diam.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/res/player/kiri_kiri.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/res/player/kiri_kanan.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // fungsi untuk memperbaharui status pemain
    public void update() {
        // periksa tombol yg ditekan
        if (keyH.upPressed == true || keyH.downPressed == true ||
                keyH.leftPressed == true || keyH.rightPressed == true) {
            
                    if (keyH.upPressed == true) {
                        direction = "up";
                    } else if (keyH.downPressed == true) {
                        direction = "down";
                    } else if (keyH.leftPressed == true) {
                        direction = "left";
                    } else if (keyH.rightPressed == true) {
                        direction = "right";
                    }

                    // cek tabrakan tile
                    collisionOn = false;
                    gp.cChecker.checkTile(this); // cek tile

                    // cek tabrakan objeck
                    int objectIndex = gp.cChecker.checkObject(this, true); // cek objek
                    pickUpObj(objectIndex); // mengambil objek
                    
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
                    if (spriteCounter > 10) {
                        if (spriteNum == 1) {
                            spriteNum = 2;
                        } else if (spriteNum == 2) {
                            spriteNum = 3;
                        } else if (spriteNum == 3) {
                            spriteNum = 1;
                        }
                        spriteCounter = 0;
                    }
        } else {
            spriteNum = 1;
        }
    }

    // fungsi saat player menabrak objek
    public void pickUpObj(int i) {
        // jika index objek valid
        if (i != 999) {
            String objName = gp.obj[i].name; // var nama objek yg ditabrak

            switch (objName) { // case objek yang ditabrak
                case "Key": // objek kunci
                    gp.playSE(1); // play SE 1
                    hasKey++; // tambah punci yang dimiliki
                    gp.obj[i] = null; // hapus objek kunci
                    gp.ui.sendMessage("Kamu mendapatkan kunci!"); // tampilkan notifikasi
                    break;
                case "Door": // objek pintu
                if (hasKey > 0) { // jika pemain punya kunci
                        gp.playSE(3); // play SE 3
                        gp.obj[i] = null; // hilangkan objek pintu
                        hasKey--; // kurangi jumlah kunci yang dimiliki
                        gp.ui.sendMessage("Kamu membuka pintu!");
                    } else {
                        gp.ui.sendMessage("Kamu membutuhkan kunci!");
                    }
                    break;
                case "Boots": // objek sepatu
                    gp.playSE(2); // play SE 2
                    speed += 1; // tambah speed player
                    gp.obj[i] = null; // hapus objek sepatu
                    gp.ui.sendMessage("Speed Up!");
                    break;
                case "Chest":
                    gp.ui.gameFinished = true;
                    gp.stopMusic();
                    gp.playSE(4);
                    break;
            }
        }
    }

    // fungsi untuk menggambar pemain
    public void draw(Graphics2D g2) {
        // g2.setColor(Color.WHITE); // warna objek
        // g2.fillRect(x, y, gp.tileSize, gp.tileSize); // lokasi dan ukuran objek
        
        BufferedImage image = null;

        // case arah pemain
        switch (direction) {
            case "up":
                if (spriteNum == 1) {
                    image = up;
                } if (spriteNum == 2) {
                    image = up1;
                } if (spriteNum == 3) {
                    image = up2;
                }
                break;
            case "down":
                if (spriteNum == 1) {
                    image = down;
                } if (spriteNum == 2) {
                    image = down1;
                } if (spriteNum == 3) {
                    image = down2;
                }
                break;
            case "left":
                if (spriteNum == 1) {
                    image = left;
                } if (spriteNum == 2) {
                    image = left1;
                } if (spriteNum == 3) {
                    image = left2;
                }
                break;
            case "right":
                if (spriteNum == 1) {
                    image = right;
                } if (spriteNum == 2) {
                    image = right1;
                } if (spriteNum == 3) {
                    image = right2;
                }
                break;
        }
        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null); // gambar player
    }
}
