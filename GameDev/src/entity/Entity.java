package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel;
import main.UtilityTool;

public class Entity {

    public int worldX, worldY;
    public int speed;
    public boolean isStatic = false; // Flag to check if the entity is static (no animation)

    GamePanel gp;
    public BufferedImage up1, up2, up3, down1, down2, down3, left1, left2, left3, right1, right2, right3;
    public BufferedImage attackUp1,  attackUp2,  attackUp3,  attackDown1, attackDown2, attackDown3, attackRight1,  attackRight2, attackRight3, attackLeft1, attackLeft2, attackLeft3;
    public String direction = "down";

    public int spriteCounter = 0;
    public int spriteNum = 1;
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;
    public int actionLockCounter = 0;
    public boolean invincible = false;
    public int invincibleCounter = 0;
    public String[] dialogue = new String[20]; // Initialize dialogue array
    public int dialogueIndex = 0;
    public BufferedImage image, image2, image3;
    public String name;
    public boolean collision = false;
    public int type; 
    boolean attacking = false;

    // CHARACTER STATUS
    public int maxLife;
    public int life;

    public Entity(GamePanel gp) {
        this.gp = gp;
    }

    public void setAction() {
        // Override in subclasses
    }

    public void speak() {
        if (dialogue[dialogueIndex] == null) {
            dialogueIndex = 0; // Reset to the first dialogue if out of bounds
        }
        gp.ui.currentDialogue = dialogue[dialogueIndex];
        dialogueIndex++;
        // Change direction based on player's direction
        switch (gp.player.direction) {
            case "up":
                direction = "down";
                break;
            case "down":
                direction = "up";
                break;
            case "left":
                direction = "right";
                break;
            case "right":
                direction = "left";
                break;
        }
    }

    public void update() {
        setAction();
        collisionOn = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this, false);
        gp.cChecker.checkEntity(this, gp.npc);
        gp.cChecker.checkEntity(this, gp.monster);
        gp.cChecker.checkPlayer(this);
        boolean contactPlayer = gp.cChecker.checkPlayer(this);
        
        
        if(this.type == 2 && contactPlayer == true) {
        	if(gp.player.invincible == false) {
        		gp.player.life -= 1;
        		gp.player.invincible = true;
        	}
        }
        

        // If no collision, move the entity
        if (!collisionOn && !isStatic) { // Don't move if it's static
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
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        // Calculate screen position
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        // Check if the entity is within the player's view
        if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
            worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
            worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
            worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

            // Choose the appropriate sprite
            if (isStatic) {
                image = down1; // Use a static image for non-animated objects
            } else {
                // Determine sprite based on direction
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
            }

            // Calculate offsets for non-standard sprite sizes (e.g., attack sprites)
            int drawWidth = image.getWidth();   // Use the sprite's width
            int drawHeight = image.getHeight(); // Use the sprite's height
            int drawX = screenX - (drawWidth - gp.tileSize) / 2; // Center horizontally
            int drawY = screenY - (drawHeight - gp.tileSize);    // Adjust vertically for taller sprites

            // Debugging: Draw a red bounding box for visual validation
            g2.setColor(Color.RED);
            g2.drawRect(drawX, drawY, drawWidth, drawHeight);

            // Draw the image
            g2.drawImage(image, drawX, drawY, drawWidth, drawHeight, null);
        }
    }



        
    

    private boolean isInView() {
        return worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
               worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
               worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
               worldY - gp.tileSize < gp.player.worldY + gp.player.screenY;
    }

    public BufferedImage setup(String imagePath, int width, int height) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try {
            image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
            image = uTool.scaleImage(image, width, height);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image; // Ensure this is only called once during object initialization
    }

    private BufferedImage getSpriteImage(BufferedImage img1, BufferedImage img2, BufferedImage img3) {
        // If the entity is static (like the door), don't animate
        if (isStatic) {
            return img1; // Always use the first image for static objects
        }

        // Manage sprite animation here (for moving entities like NPCs)
        spriteCounter++;
        if (spriteCounter > 25) { // Change this value to adjust the speed of sprite change
            spriteCounter = 0;
            spriteNum++;
            if (spriteNum > 3) {
                spriteNum = 1;
            }
        }

        switch (spriteNum) {
            case 1:
                return img1;
            case 2:
                return img2;
            case 3:
                return img3;
            default:
                return img1;
        }
    }
}