package entity;

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

    GamePanel gp;
    public BufferedImage up1, up2, up3, down1, down2, down3, left1, left2, left3, right1, right2, right3;
    public String direction;

    public int spriteCounter = 0;
    public int spriteNum = 1;
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;
    public int actionLockCounter = 0;

    public Entity(GamePanel gp) {
        this.gp = gp;
    }
    
    public void setAction() {
    	
    }
    
    public void update() {
    	setAction();
    	collisionOn = false;
    	gp.cChecker.checkTile(this);
    	gp.cChecker.checkObject(this, false);
    	gp.cChecker.checkPlayer(this);
    	
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
            
            // Determine the sprite image based on the direction
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

            // Draw the image on the screen
            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }
    }

    public BufferedImage setup(String imageName) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage scaledImage = null;

        try {
            scaledImage = ImageIO.read(getClass().getResourceAsStream(imageName + ".png"));
            scaledImage = uTool.scaleImage(scaledImage, gp.tileSize, gp.tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return scaledImage; // Return the scaled image
    }

    private BufferedImage getSpriteImage(BufferedImage img1, BufferedImage img2, BufferedImage img3) {
        // Manage sprite animation here (not implemented in the original code)
        spriteCounter++;
        if (spriteCounter > 10) { // Change this value to adjust the speed of sprite change
            spriteCounter = 0;
            spriteNum++;
            if (spriteNum > 3) {
                spriteNum = 1;
            }
        }

        switch (spriteNum) {
            case 1: return img1;
            case 2: return img2;
            case 3: return img3;
            default: return img1;
        }
    }
}