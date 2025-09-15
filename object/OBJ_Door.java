package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class OBJ_Door extends SuperObject{
    GamePanel gp; // referensi ke gamepanel

    // fungsi untuk memuat objek door
    public OBJ_Door(GamePanel gp) {
        name = "Door"; // nama objek
        // mengambil gambar objek
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/objects/door.png"));
            uTool.scaledImage(image, gp.tileSize, gp.tileSize); // ubah ukuran gambar sesuai tile
        } catch (IOException e) {
            // TODO: handle exception
        }

        collision = true; // objek solid/padat
    }
}
