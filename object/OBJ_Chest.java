package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class OBJ_Chest extends SuperObject{
    GamePanel gp; // referensi ke gamepanel

    // fungsi untuk memuat objek chest
    public OBJ_Chest(GamePanel gp) {
        name = "Chest"; // nama objek
        // mengambil gambar objek
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/objects/chest.png"));
            uTool.scaledImage(image, gp.tileSize, gp.tileSize); // ubah ukuran gambar sesuai tile
        } catch (IOException e) {
            // TODO: handle exception
        }

        collision = true; // objek solid
    }
}
