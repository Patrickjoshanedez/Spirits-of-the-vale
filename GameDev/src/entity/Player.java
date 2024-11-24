package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity {

    KeyHandler keyH;

    public final int screenX;
    public final int screenY;
    int standCounter = 0;

    // Health attributes
    private int health; // Current health
    public int maxHealth = 100; // Maximum health

    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp);
        this.keyH = keyH;

        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        solidArea = new Rectangle();
        solidArea.x = 8;  // X offset for collision area
        solidArea.y = 16; // Y offset for collision area
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 30;  // Width of collision area
        solidArea.height = 30;  // Height of collision area

        setDefaultValues();
        getPlayerImage();
        this.health = maxHealth; // Initialize health
    }

    public void setDefaultValues() {
        worldX = gp.tileSize * 2; // Starting X position
        worldY = gp.tileSize * 3;   // Starting Y position
        speed = 4;                   // Movement speed
        direction = "down";          // Default direction
    }

    public void getPlayerImage() {
        up1 = setup("/player/up1");
        up2 = setup("/player/up2");
        up3 = setup("/player/up3");
        down1 = setup("/player/down1");
        down2 = setup("/player/down2");
        down3 = setup("/player/down3");
        left1 = setup("/player/left1");
        left2 = setup("/player/left2");
        left3 = setup("/player/left3");
        right1 = setup("/player/right1");
        right2 = setup("/player/right2");
        right3 = setup("/player/right3");
    }

    public int getHealth() {
        return health;
    }

    public void takeDamage(int damage) {
        health -= damage;
        if (health < 0) health = 0; // Prevent negative health
    }

    public void update() {
        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
            if (keyH.upPressed) {
                direction = "up";
            } else if (keyH.downPressed) {
                direction = "down";
            } else if (keyH.leftPressed) {
                direction = "left";
            } else if (keyH.rightPressed) {
                direction = "right";
            }

            // Reset collision flag
            collisionOn = false;

            // Check tile collision
            gp.cChecker.checkTile(this);

            // Check object collision
            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(objIndex);
            
            // Check NPC collision
            int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
            interactNPC(npcIndex);

            // If no collision, move the player
            if (!collisionOn) {
                switch (direction) {
                    case "up":
                        worldY -= speed;
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case "left":
                        worldX -= speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;
                }
            }

            // Update sprite for animation
            spriteCounter++;
            if (spriteCounter > 12) {
                spriteNum++;
                if (spriteNum > 3) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }
        System.out.println("Player update called. Game state: " + gp.gameState);
    }

    public void pickUpObject(int i) {
        if (i != 999) {
            // Logic to handle picking up an object
            // For example: gp.obj[i].interact(); or similar
        }
    }

    public void interactNPC(int i) {
        if (i != 999) {
            if (gp.keyH.enterPressed) {
                // If the index is valid, proceed to interact with the NPC
                gp.gameState = gp.dialogueState; // Change game state to dialogue
                gp.npc[i].speak(); // Call the speak method on the NPC
            } else {
                // Handle the case where the index is out of bounds
                System.out.println("You're hitting the npc!");
            }
        }
        gp.keyH.enterPressed = false;
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        switch (direction) {
            case "up":
                image = getSpriteImage(up1, up2, up3);
                break;
            case "down":
                image = getSpriteImage(down1, down2, down3);
                break;
            case "left":
                image = getSpriteImage(left1, left2, left3);
                break; 
            case "right":
                image = getSpriteImage(right1, right2, right3);
                break;
        }
        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
    }

    private BufferedImage getSpriteImage(BufferedImage sprite1, BufferedImage sprite2, BufferedImage sprite3) {
        switch (spriteNum) {
            case 1:
                return sprite1;
            case 2:
                return sprite2;
            case 3:
                return sprite3;
            default:
                return sprite1; // Fallback
        }
    }
}