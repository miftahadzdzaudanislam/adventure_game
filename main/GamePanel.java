package main;
import java.awt.*;
import javax.swing.*;

import entity.Player;
import object.SuperObject;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable{
    // Screen Setting
    final int oriTileSize = 16; // 16x16 tile / ubin
    final int scale = 3; // sekala perbesaran gambar

    public final int tileSize = oriTileSize * scale; // ukuran akhir tile 48
    public final int maxScreenCol = 16; // jumlah lebar layar
    public final int maxScreenRow = 12; // jumlah tinggi layar
    public final int screenWidth = tileSize * maxScreenCol; // lebar layar 768 pixels
    public final int screenHeight = tileSize * maxScreenRow; // tinggi layar 576 pixels

    // World Setting
    public final int maxWorldCol = 50; // ukuran maksiman map col
    public final int maxWorldRow = 50; // ukuran maksimal map row
    // public final int worldWidth = tileSize * maxWorldCol; // lebar map sesuai jendela
    // public final int worldHeight = tileSize * maxWorldRow; // tinggi map sesuai jendela

    // FPS
    int FPS = 60;

    // SYSTEM
    TileManager tileM = new TileManager(this); // instance pembuatan tile
    KeyHandler keyH = new KeyHandler(); // instance kontrol
    Sound music = new Sound(); // instance sound music
    Sound se = new Sound(); // instance sound soundeffect
    public CollisionChecker cChecker = new CollisionChecker(this); // instance pengecekan tabrakan
    public AssetsSetter aSetter = new AssetsSetter(this); // instance asset setter
    public UI ui = new UI(this); // instance UI
    Thread gameThread; // program untuk menjalankan game loop

    // ENTITY AND OBJECT
    public Player player = new Player(this, keyH); // instance player
    public SuperObject obj[] = new SuperObject[10]; // instance objek (objek hanya 10)

    // fungsi membuat jendela
    public GamePanel() {
        setPreferredSize(new Dimension(screenWidth, screenHeight)); // set ukuran jendela
        setBackground(Color.BLACK); // set warna bg
        setDoubleBuffered(true); // set buffring
        addKeyListener(keyH); // menambah setting kontrol
        setFocusable(true);
    }

    // fungsi setup game
    public void setupGame() {
        aSetter.setObject(); // memuat objek

        playMusic(0); // memulai backsound
    }

    // fungsi memulai thread game  
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    // public void run() {
    //     // Looping 
    //     /* 1. UPDATE: update informasi posisi char
    //      * 2. DRAW: draw screen dgn update informasi
    //      */

    //     double drawInterval = 1000000000/FPS; // ukuran gerak game / redraw per 0,016 dtk
    //     double nextDrawTime = System.nanoTime() + drawInterval;

    //     while (gameThread != null) {
    //         // long currentTime = System.nanoTime(); // waktu sistem
    //         // System.out.println("current time " + currentTime);

    //         // Update
    //         update();

    //         // Draw
    //         repaint();

    //         try {
    //             double remainingTime = nextDrawTime - System.nanoTime(); // waktu tersisa
    //             remainingTime = remainingTime/1000000; // ubah dari nanosecond ke milisecond 

    //             if (remainingTime < 0) {
    //                 remainingTime = 0;
    //             }

    //             Thread.sleep((long) remainingTime); // menghentikan game selama waktu tidur

    //             nextDrawTime += drawInterval; // saat terbangun, mengatur nextDrawTime baru
    //         } catch (InterruptedException e) {
    //             e.printStackTrace();
    //         }
    //     }
    // }

    // fungsi loop delta time
    public void run() {
        double drawInterval = 1000000000/FPS; // waktu antar frame nanodetik
        double delta = 0; // selisih waktu berlalu
        long lastTime = System.nanoTime(); // waktu saat terakhir frame diproses
        long currentTime;

        while (gameThread != null) {
            currentTime = System.nanoTime(); // waktu sekarang
            delta += (currentTime - lastTime) / drawInterval; // tambah waktu berlalu
            lastTime = currentTime; // memperbaharui waktu terakhir

            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
    }

    // fungsi update
    public void update() {
        player.update(); // memperbaharui pemain
    }

    // membuat gambar
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // bersihkan layar sebelum menggambar
        Graphics2D g2 = (Graphics2D) g; // ubah graphic jadi graphic2d

        tileM.draw(g2); // mengambil fungsi draw TILE (gambar TILE)
        
        // gambar OBJEK
        for (int i = 0; i < obj.length; i++) { // cek gambar pada nomor apa
            if (obj[i] != null) {
                obj[i].draw(g2, this); // membuat gambar sesuai nomor
            }
        }
        
        player.draw(g2); // mengambil fungsi draw PLAYER (gambar PLAYER)

        ui.draw(g2); // mengambil fungsi draw UI (gambar UI)

        g2.dispose(); // Membersihkan resource yang digunakan oleh Graphics2D
    }

    // fungsi untuk memulai musik
    public void playMusic(int i) {
        music.setFile(i); // memuat file sound
        music.play(); // memulai backsound
        music.loop(); // menloop backsound
    }

    // fungsi untuk menghentikan musik
    public void stopMusic() {
        music.stop(); // menghentikan backsund
    }

    // fungsi untuk memulai sound effect
    public void playSE(int i) {
        se.setFile(i); // memuat file sound
        se.play(); // memulai soundeffect
    }
}
