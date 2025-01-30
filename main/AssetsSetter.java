package main;

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
        gp.obj[0] = new OBJ_Key(); // instance objek key 1
        gp.obj[0].worldX = 23 * gp.tileSize; // lokasi key 1 x / di kolom 24
        gp.obj[0].worldY = 26 * gp.tileSize; // lokasi key 1 y / di baris 27

        gp.obj[1] = new OBJ_Key(); // instance objek key 2
        gp.obj[1].worldX = 23 * gp.tileSize; // lokasi key 2 x / di kolom 24
        gp.obj[1].worldY = 35 * gp.tileSize; // lokasi key 2 y / di baris 36

        gp.obj[2] = new OBJ_Chest(); // instance objek chest 1
        gp.obj[2].worldX = 21 * gp.tileSize; // lokasi chest 1 x / di kolom 22
        gp.obj[2].worldY = 12 * gp.tileSize; // lokasi chest 1 y / di baris 13

        gp.obj[3] = new OBJ_Door(); // instance objek door 1
        gp.obj[3].worldX = 21 * gp.tileSize; // lokasi door 1 x / di kolom 22
        gp.obj[3].worldY = 15 * gp.tileSize; // lokasi door 1 y / di baris 16

        gp.obj[4] = new OBJ_Door(); // instance objek door 1
        gp.obj[4].worldX = 23 * gp.tileSize; // lokasi door 1 x / di kolom 24
        gp.obj[4].worldY = 33 * gp.tileSize; // lokasi door 1 y / di baris 34

        gp.obj[5] = new OBJ_Boots(); // instance objek boots 1
        gp.obj[5].worldX = 18 * gp.tileSize; // lokasi boots 1 x / di kolom 19
        gp.obj[5].worldY = 26 * gp.tileSize; // lokasi boots 1 y / di baris 27
    }
}
