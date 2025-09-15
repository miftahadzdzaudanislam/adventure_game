package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class OBJ_Key extends SuperObject{
    GamePanel gp; // referensi ke gamepanel

    public OBJ_Key(GamePanel gp) {
        name = "Key"; // nama objek
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/objects/key.png"));
            uTool.scaledImage(image, gp.tileSize, gp.tileSize); // ubah ukuran gambar sesuai tile
        } catch (IOException e) {
            // TODO: handle exception
        }
    }
}
