package object;

import java.io.IOException;

import javax.imageio.ImageIO;

public class OBJ_Chest extends SuperObject{
    // fungsi untuk memuat objek chest
    public OBJ_Chest() {
        name = "Chest"; // nama objek
        // mengambil gambar objek
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/objects/chest.png"));
        } catch (IOException e) {
            // TODO: handle exception
        }

        collision = true; // objek solid
    }
}
