package main;
import java.awt.*;
import javax.swing.*;

import entity.Entity;
import entity.Player;
import object.SuperObject;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable{
    // Screen Setting
    final int oriTileSize = 32; // 16x16 tile / ubin
    final int scale = 2; // sekala perbesaran gambar

    public final int tileSize = oriTileSize * scale; // ukuran akhir tile 48
    public final int maxScreenCol = 12; // jumlah lebar layar
    public final int maxScreenRow = 10; // jumlah tinggi layar
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
    public KeyHandler keyH = new KeyHandler(this); // instance kontrol
    Sound music = new Sound(); // instance sound music
    Sound se = new Sound(); // instance sound soundeffect
    public CollisionChecker cChecker = new CollisionChecker(this); // instance pengecekan tabrakan
    public AssetsSetter aSetter = new AssetsSetter(this); // instance asset setter
    public UI ui = new UI(this); // instance UI
    Thread gameThread; // program untuk menjalankan game loop

    // ENTITY AND OBJECT
    public Player player = new Player(this, keyH); // instance player
    public SuperObject obj[] = new SuperObject[10]; // instance objek (objek hanya 10)
    public Entity npc[] = new Entity[10];

    // GAME STATE
    public int gameState; // var status game
    public final int playState = 1; // var game play
    public final int pauseState = 2; // var game pause
    public final int dialogueState = 3; // var dialog


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
        aSetter.setNPC(); // memuat NPC

        playMusic(0); // memulai backsound
        // stopMusic(); // memberhentikan backsound
        gameState = playState; // status game play
    }

    // fungsi memulai thread game  
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    // fungsi loop delta time
    @Override
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
        if (gameState == playState) { // jika game mulai
            player.update(); // memperbaharui pemain

            // memperbaharui NPC
            for (int i = 0; i < npc.length; i++) {
                if (npc[i] != null) {
                    npc[i].update();
                }
            }
        } else if (gameState == pauseState) { // jika game pause
            
        }
    }

    // membuat gambar
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // bersihkan layar sebelum menggambar
        Graphics2D g2 = (Graphics2D) g; // ubah graphic jadi graphic2d

        // DEBUG
        long drawStart = 0;
        if (keyH.cekDrawTime == true) {
            drawStart = System.nanoTime();
        }

        tileM.draw(g2); // mengambil fungsi draw TILE (gambar TILE)
        
        // OBJEK
        for (int i = 0; i < obj.length; i++) { // cek gambar pada nomor apa
            if (obj[i] != null) {
                obj[i].draw(g2, this); // membuat gambar sesuai nomor
            }
        }

        // NPC
        for (int i = 0; i < npc.length; i++) {
            if (npc[i] != null) {
                npc[i].draw(g2); // membuat gambar sesuai nomor
            }
        }
        
        // PLAYER (gambar PLAYER)
        player.draw(g2); 

        // UI (gambar UI)
        ui.draw(g2); 

        // DEBUG
        if (keyH.cekDrawTime == true) {
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;
            g2.setColor(Color.white);
            g2.drawString("Time: " + passed, 10, 400);
            System.out.println("Time: " + passed);
        }

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
