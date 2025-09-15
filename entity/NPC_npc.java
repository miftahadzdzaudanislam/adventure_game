package entity;

import java.util.Random;

import main.GamePanel;

public class NPC_npc extends Entity{
    // konstruktor
    public NPC_npc(GamePanel gp) {
        super(gp);

        direction = "down";
        speed = 1;

        getImage();
        setDialogue();
    }

    // fungsi untuk memuat gambar npc
    public void getImage() {
        up1 = setup("/npc/npc_up_1");
        up2 = setup("/npc/npc_up_2");
        down1 = setup("/npc/npc_down_1");
        down2 = setup("/npc/npc_down_2");
        left1 = setup("/npc/npc_left_1");
        left2 = setup("/npc/npc_left_2");
        right1 = setup("/npc/npc_right_1");
        right2 = setup("/npc/npc_right_2");
    }

    // fungsi memuat dialog NPC npc
    public void setDialogue() {
        dialogue[0] = "Hello, player";
        dialogue[1] = "dialogue 2 lorem ipsum \ndolor";
        dialogue[2] = "dialogue 3";
        dialogue[3] = "dialogue 4";
    }

    // fungsi untuk menggerakan NPC npc
    public void setAction() {
        actionLockConter++;

        // pergerakan berubah setelah 2 detik
        if (actionLockConter == 120) {
            Random random = new Random(); // instance random
            int i = random.nextInt(100)+1; // pilih angka random 1 - 100
    
            // Ai pergerakan NPC npc
            if (i <= 25) {
                direction = "up";
            } if (i > 25 && i <= 50) {
                direction = "down";
            } if (i > 50 && i <= 75) {
                direction = "left";
            } if (i > 75 && i <= 100) {
                direction = "right";
            }

            actionLockConter = 0;
        }
    }

    // fungsi pada saat NPC berbicara
    public void speak() {
        super.speak();
    }
}
