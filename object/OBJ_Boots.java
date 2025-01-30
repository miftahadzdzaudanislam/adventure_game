package object;

import java.io.IOException;

import javax.imageio.ImageIO;

public class OBJ_Boots extends SuperObject{
    // fungsi untuk memuat objek boots
    public OBJ_Boots() {
        name = "Boots"; // nama object
        // ambil gambar objek
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/objects/boots.png"));
        } catch (IOException e) {
            // TODO: handle exception
        }
    }
}
