import java.util.*;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;

public class Main extends JPanel implements ActionListener, KeyListener {

  private Timer  timer = new Timer(16, this); // Timer for game
    private int playerX = 100, playerY = 300; // Player's position
    private int playerVelX = 0, playerVelY = 0; // Player's velocity
    private boolean jumping = false;
    private boolean grounded = false;
    private final int gravity = 1;
    private final int jumpStrength = -15;
    private final int groundLevel = 400;

    private Rectangle player;
    private Rectangle[] platforms;

    public void PlatformerGame() {
        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.cyan);
        setFocusable(true);
        addKeyListener(this);

        timer.start();

        player = new Rectangle(playerX, playerY, 50, 50);
        platforms = new Rectangle[]{
            new Rectangle(100, 500, 200, 10),
            new Rectangle(400, 450, 200, 10),
            new Rectangle(200, 350, 150, 10)
        };
    }

    public void actionPerformed(ActionEvent e) {
        movePlayer();
        checkCollisions();
        repaint();
    }

    public void movePlayer() {
        playerX += playerVelX;
        playerY += playerVelY;

        // Apply gravity
        if (!grounded) {
            playerVelY += gravity;
        }

        // Check if player is on the ground
        if (playerY >= groundLevel - player.height) {
            playerY = groundLevel - player.height;
            grounded = true;
            playerVelY = 0;
        } else {
            grounded = false;
        }
    }

    public void checkCollisions() {
        // Check for collisions with platforms
        for (Rectangle platform : platforms) {
            if (player.intersects(platform)) {
                // Handle player landing on platform
                if (true) {
                    //playerVelY > 0 && player.getBounds().bottom =< platform.top
                    playerY = platform.y - player.height;
                    playerVelY = 0;
                    grounded = true;
                }
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawPlayer(g);
        drawPlatforms(g);
    }

    public void drawPlayer(Graphics g) {
        g.setColor(Color.red);
        g.fillRect(playerX, playerY, player.width, player.height);
    }

    public void drawPlatforms(Graphics g) {
        g.setColor(Color.green);
        for (Rectangle platform : platforms) {
            g.fillRect(platform.x, platform.y, platform.width, platform.height);
        }
    }

    // Key Listener
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT) {
            playerVelX = -5; // Move left
        }
        if (key == KeyEvent.VK_RIGHT) {
            playerVelX = 5; // Move right
        }
        if (key == KeyEvent.VK_SPACE && grounded) {
            playerVelY = jumpStrength; // Jump if grounded
            grounded = false;
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT) {
            playerVelX = 0; // Stop horizontal movement
        }
    }

    public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {
        JFrame frame = new JFrame("Platformer Game");
    }
}
