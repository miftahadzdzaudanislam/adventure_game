package main;

import entity.Entity;

public class CollisionChecker {
    GamePanel gp; // referensi gamepanel

    // fungsi konstruktor untuk inisialisasi cek tabrakan dengan GP
    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    // fungsi untuk memeriksa tabrakan entitas atau tile solid
    public void checkTile(Entity entity) {
        // koordinat sisi dari area solid pada player
        int entityLeftWorldX = entity.worldX + entity.solidArea.x; // sisi kiri (posisi x + jarak ke solid x)
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width; // sisi kanan (posisi x + jarak ke solid x + lebar solid)
        int entityTopWorldY = entity.worldY + entity.solidArea.y; // sisi atas (posisi y + jarak ke solid y)
        int entityBottomWorldY =  entity.worldY + entity.solidArea.y + entity.solidArea.height; // sisi bawah (posisi y + jarak ke solid y + tinggi solid)

        // col dan row area solid pada player dari koordinat
        int entityLeftCol = entityLeftWorldX/gp.tileSize; // nomor kolom kiri (sisi kiri / tile)
        int entityRightCol = entityRightWorldX/gp.tileSize; // nomor kolom kanan (sisi kanan / tile)
        int entityTopRow = entityTopWorldY/gp.tileSize; // nomor baris atas (sisi atas / tile)
        int entityBottomRow = entityBottomWorldY/gp.tileSize; // nomor baris bawah (sisi bawah / tile)

        int tileNum1, tileNum2; // var tile yang di cek

        // memeriksa arah gerakan entitas
        switch (entity.direction) {
            case "up":
                entityTopRow = (entityTopWorldY - entity.speed)/gp.tileSize; // ubah baris atas dari kecepatan
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow]; // nomor tile di atas kiri
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow]; // nomor tile di atas kanan
                // jika salah satu tile solid
                if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
                    entity.collisionOn = true; // tabrakan aktif
                }
                break;
            case "down":
                entityBottomRow = (entityBottomWorldY + entity.speed)/gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow]; // nomor tile bawah kiri
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow]; // nomor tile bawah kanan
                if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
                    entity.collisionOn = true;
                }
                break;
            case "left":
                entityLeftCol = (entityLeftWorldX - entity.speed)/gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];  // nomor tile kiri atas
                tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow]; // nomor tile kiri bawah
                if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
                    entity.collisionOn = true;
                }
                break;
            case "right":
                entityRightCol = (entityRightWorldX + entity.speed)/gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow]; // nomor tile kanan atas
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow]; // nomor tile kanan bawah
                if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
                    entity.collisionOn = true;
                }
                break;
        }
    }

    // fungsi untuk memeriksa apakah player menabrak object
    public int checkObject(Entity entity, boolean player) {
        int index = 999; // default nilai tidak ada objek

        for (int i = 0; i < gp.obj.length; i++) {
            if (gp.obj[i] != null) {
                // Menyimpan posisi area solid entitas
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;

                // Menyimpan posisi area solid objek
                gp.obj[i].solidArea.x = gp.obj[i].worldX + gp.obj[i].solidArea.x;
                gp.obj[i].solidArea.y = gp.obj[i].worldY + gp.obj[i].solidArea.y;

                // periksa tabrakan berdasarkan arah
                switch (entity.direction) {
                    case "up":
                        entity.solidArea.y -= entity.speed; // gerak ke atas
                        if (entity.solidArea.intersects(gp.obj[i].solidArea)) { // jika tabrakan
                            if (gp.obj[i].collision == true) { // jika objek solid
                                entity.collisionOn = true; // aktifkan tabrakan
                            }
                            if (player == true) { // jika entitas adalah player
                                index = i; // simpan index objek yang ditabrak
                            }
                        }
                        break;
                    case "down":
                        entity.solidArea.y += entity.speed; // gerak ke bawah
                        if (entity.solidArea.intersects(gp.obj[i].solidArea)) { // jika tabrakan
                            if (gp.obj[i].collision == true) { // jika objek solid
                                entity.collisionOn = true; // aktifkan tabrakan
                            }
                            if (player == true) { // jika entitas adalah player
                                index = i; // simpan index objek yang ditabrak
                            }
                        }
                        break;
                    case "left":
                        entity.solidArea.x -= entity.speed; // gerak ke kiri
                        if (entity.solidArea.intersects(gp.obj[i].solidArea)) { // jika tabrakan
                            if (gp.obj[i].collision == true) { // jika objek solid
                                entity.collisionOn = true; // aktifkan tabrakan
                            }
                            if (player == true) { // jika entitas adalah player
                                index = i; // simpan index objek yang ditabrak
                            }
                        }
                        break;
                    case "right":
                        entity.solidArea.x += entity.speed; // gerak ke kanan
                        if (entity.solidArea.intersects(gp.obj[i].solidArea)) { // jika tabrakan
                            if (gp.obj[i].collision == true) { // jika objek solid
                                entity.collisionOn = true; // aktifkan tabrakan
                            }
                            if (player == true) { // jika entitas adalah player
                                index = i; // simpan index objek yang ditabrak
                            }
                        }
                        break;
                }

                // mengembalikan posisi area solid ke posisi awal
                entity.solidArea.x = entity.solidAreaDX;
                entity.solidArea.y = entity.solidAreaDY;
                gp.obj[i].solidArea.x = gp.obj[i].solidAreaDX;
                gp.obj[i].solidArea.y = gp.obj[i].solidAreaDY;
            }
        }

        return index; // mengembalikan index
    }
}
