package object;

import java.io.IOException;

import javax.imageio.ImageIO;

public class OBJ_Door extends SuperObject{
    // fungsi untuk memuat objek door
    public OBJ_Door() {
        name = "Door"; // nama objek
        // mengambil gambar objek
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/objects/door.png"));
        } catch (IOException e) {
            // TODO: handle exception
        }

        collision = true; // objek solid/padat
    }
}
