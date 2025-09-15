package entity;

import java.awt.*;
import java.awt.image.BufferedImage;

import main.*;

public class Player extends Entity{
    KeyHandler keyH; // referensi keyhandler

    public final int screenX; // var layar x 
    public final int screenY; // var layar y

    int standConter = 0; // var pada saat diam

    // fungsi player
    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp); // referensi ke konstruktor entity
        this.keyH = keyH; // instansiasi keyHanlder

        screenX = gp.screenWidth/2 - (gp.tileSize/2); // player berada di tengah layar x
        screenY = gp.screenHeight/2 - (gp.tileSize/2); // player berada di tengah layar y

        solidArea = new Rectangle(); // instance area solid player
        solidArea.x = 14; // ukuran player dikecilkan 8 pada x
        solidArea.y = 40; // ukuran player dikecilkan 16 pada y
        solidAreaDX = solidArea.x; // ukuran area objek x
        solidAreaDY = solidArea.y; // ukuran area objek y
        solidArea.width = gp.tileSize - 27; // lebar blok solid player / 32
        solidArea.height = gp.tileSize - 45; // tinggi blok solid player / 32

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
        up = setup("/player/up_guard");
        up1 = setup("/player/up_1");
        up2 = setup("/player/up_2");
        down = setup("/player/down_guard");
        down1 = setup("/player/down_1");
        down2 = setup("/player/down_2");
        left = setup("/player/left_guard");
        left1 = setup("/player/left_1");
        left2 = setup("/player/left_2");
        right = setup("/player/right_guard");
        right1 = setup("/player/right_1");
        right2 = setup("/player/right_2");
    }

    // fungsi untuk memperbaharui status pemain
    public void update() {
        // periksa tombol yg ditekan (player jalan)
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

                    // CEK TABRAKAN TILE
                    collisionOn = false;
                    gp.cChecker.checkTile(this); // cek tile

                    // CEK TABRAKAN OBJEK
                    int objectIndex = gp.cChecker.checkObject(this, true); // cek objek
                    pickUpObj(objectIndex); // mengambil objek
                    
                    // CEK TABRAKAN NPC
                    int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
                    interactNPC(npcIndex);

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
                            spriteNum = 3;
                        } else if (spriteNum == 3) {
                            spriteNum = 1;
                        }
                        spriteCounter = 0;
                    }
        } else { // jika player diam
            standConter++;
            if (standConter == 20) {
                standConter = 0;
                spriteNum = 1;
            }
        }
    }

    // fungsi saat player menabrak objek
    public void pickUpObj(int i) {
        // jika index objek valid
        if (i != 999) {
            
        }
    }

    // fungsi interaksi npc
    public void interactNPC(int i) {
        if (i != 999) {
            // jika tombol enter di klik
            if (gp.keyH.enterPressed == true) {
                gp.gameState = gp.dialogueState; // memuat dialog
                gp.npc[i].speak();
            }
        }
        gp.keyH.enterPressed = false;
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
        g2.drawImage(image, screenX, screenY, null); // gambar player
        // cek area padat
        // g2.setColor(Color.red);
        // g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);
    }
}
