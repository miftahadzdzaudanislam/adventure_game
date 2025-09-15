package main;

import entity.NPC_npc;
import object.OBJ_Boots;
import object.OBJ_Chest;
import object.OBJ_Door;
import object.OBJ_Key;

public class AssetsSetter {
    GamePanel gp; // referensi ke gamepanel

    // instance object
    public AssetsSetter(GamePanel gp) {
        this.gp = gp;
    }

    // fungsi memuat objek
    public void setObject() {

    }

    // fungsi untuk memuat NPC
    public void setNPC() {
        gp.npc[0] = new NPC_npc(gp); // instance NPC npc
        gp.npc[0].worldX = gp.tileSize*21; // posisi npc di x
        gp.npc[0].worldY = gp.tileSize*21; // posisi npc di y
    }
}
