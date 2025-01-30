package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;

public class TileManager {
    GamePanel gp; // referensi gamepanel
    public Tile[] tile; // referensi tile
    public int mapTileNum[] []; // array 2D untuk simpan data peta

    // Konstruksi tilemanager
    public TileManager(GamePanel gp) {
        this.gp = gp;
        tile = new Tile[10]; // max 10 jenis tile
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow]; // peta sesuai dengan jendela

        getTileManager(); // memuat gambar tile
        loadMap("/res/maps/world01.txt"); // memuat peta
    }

    // fungsi untuk memuat gamabr tile
    public void getTileManager() {
        try {
            tile[0] = new Tile(); // tile 1 grass
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/bg_grass.png"));

            tile[1] = new Tile(); // tile 2 rock
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/bg_rock.png"));
            tile[1].collision = true; // rock padat

            tile[2] = new Tile(); // tile 3 water
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/bg_water.png"));
            tile[2].collision = true; // water padat

            tile[3] = new Tile(); // tile 4 earth
            tile[3].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/bg_earth.png"));

            tile[4] = new Tile(); // tile 5 tree
            tile[4].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/bg_tree.png"));
            tile[4].collision = true; // tree padat

            tile[5] = new Tile(); // tile 6 sand
            tile[5].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/bg_sand.png"));

            tile[6] = new Tile(); // tile 7 wall
            tile[6].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/bg_wall.png"));
            tile[6].collision = true; // wall padat
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // fungsi untuk memuat data peta
    public void loadMap(String filePath) {
        try {
            InputStream is = getClass().getResourceAsStream(filePath); // imoprt file txt
            BufferedReader br = new BufferedReader(new InputStreamReader(is)); // baca isi file txt

            int col = 0;
            int row = 0;

            // membaca data peta perbaris
            while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
                String line = br.readLine(); // baca satu baris awal

                while (col < gp.maxWorldCol) {
                    String numbers[] = line.split(" "); // membaca angka di baris

                    int num = Integer.parseInt(numbers[col]); // ubah string jadi integer

                    mapTileNum[col] [row] = num; // simpan nomor tile dalam array peta
                    col++;
                }
                if (col == gp.maxWorldCol) { // pindah baris selanjutnya
                    col = 0; // reset colom ke 0
                    row++; // pindah baris selanjutnya
                }
            }
            br.close(); // menutup render setelah selesai
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // fungsi menggambar peta
    public void draw(Graphics2D g2) {
        int worldCol = 0; // tile map awal col
        int worldRow = 0; // tile map awal row

        while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) { // gambar tile 1 per 1
            int tileNum = mapTileNum[worldCol] [worldRow]; // ambil nomor tile

            int worldX = worldCol * gp.tileSize; // posisi absolute tile x di map
            int worldY = worldRow * gp.tileSize; // posisi absolute tile y di map
            int screenX = worldX - gp.player.worldX + gp.player.screenX; // posisi relatif tile x di jandela
            int screenY = worldY - gp.player.worldY + gp.player.screenY; // posisi relatif tile y di jendela

            if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX && // kanan tile > kiri jendela
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX && // kiri tile < kanan jendela
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY && // bawah tile > atas jendela
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) { // atas tile < bawah jendela

                g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
            }
            worldCol++; // pindah kolom selanjutnya

            if (worldCol == gp.maxWorldCol) { // pindah ke baris selanjutnya
                worldCol = 0; // reset kolom ke 0
                worldRow++; // pindah baris selanjutnya
            }
        }
    }
}
