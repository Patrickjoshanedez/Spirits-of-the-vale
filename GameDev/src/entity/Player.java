package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
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
    public int maxHealth = 6; // Maximum health

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
        getPlayerAttackImage();
        this.health = maxHealth; // Initialize health
    }

    public void setDefaultValues() {
        worldX = gp.tileSize * 2; //	 Starting X position
        worldY = gp.tileSize * 3;   // Starting Y position
        speed = 4;                   // Movement speed
        direction = "down";          // Default direction
        // PLAYER STATUS
        maxLife = 6;
        life = maxLife;
    }

    public void getPlayerImage() {
        up1 = setup("/player/up1", gp.tileSize, gp.tileSize);
        up2 = setup("/player/up2", gp.tileSize, gp.tileSize);
        up3 = setup("/player/up3", gp.tileSize, gp.tileSize);
        down1 = setup("/player/down1", gp.tileSize, gp.tileSize);
        down2 = setup("/player/down2", gp.tileSize, gp.tileSize);
        down3 = setup("/player/down3", gp.tileSize, gp.tileSize);
        left1 = setup("/player/left1", gp.tileSize, gp.tileSize);
        left2 = setup("/player/left2", gp.tileSize, gp.tileSize);
        left3 = setup("/player/left3", gp.tileSize, gp.tileSize);
        right1 = setup("/player/right1", gp.tileSize, gp.tileSize);
        right2 = setup("/player/right2", gp.tileSize, gp.tileSize);
        right3 = setup("/player/right3", gp.tileSize, gp.tileSize);
    }
    
    public void getPlayerAttackImage() {

        // Load attack images for Up and Down (32x64 dimensions)
        attackUp1 = setup("/player/up_attack0", gp.tileSize, gp.tileSize * 2);
        attackUp2 = setup("/player/up_attack1", gp.tileSize, gp.tileSize * 2);
        attackUp3 = setup("/player/up_attack2", gp.tileSize, gp.tileSize * 2);

        attackDown1 = setup("/player/down_attack0", gp.tileSize, gp.tileSize * 2);
        attackDown2 = setup("/player/down_attack1", gp.tileSize, gp.tileSize * 2);
        attackDown3 = setup("/player/down_attack2", gp.tileSize, gp.tileSize * 2);

        // Load attack images for Left and Right (64x32 dimensions)
        attackLeft1 = setup("/player/left_attack0", gp.tileSize * 2, gp.tileSize);
        attackLeft2 = setup("/player/left_attack1", gp.tileSize * 2, gp.tileSize);
        attackLeft3 = setup("/player/left_attack2", gp.tileSize * 2, gp.tileSize);

        attackRight1 = setup("/player/right_attack0", gp.tileSize * 2, gp.tileSize);
        attackRight2 = setup("/player/right_attack1", gp.tileSize * 2, gp.tileSize);
        attackRight3 = setup("/player/right_attack2", gp.tileSize * 2, gp.tileSize);
    }


    public void update() {
    	
    	if(attacking == true) {
    		attacking();
    	}
        boolean moving = keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed;

        if (moving) {
            if (keyH.upPressed) direction = "up";
            if (keyH.downPressed) direction = "down";
            if (keyH.leftPressed) direction = "left";
            if (keyH.rightPressed) direction = "right";

            collisionOn = false;

            // Check collisions
            gp.cChecker.checkTile(this);
            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(objIndex);
            int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
            interactNPC(npcIndex);
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            contactMonster(monsterIndex);
            gp.eHandler.checkEvent();

            if (!collisionOn) {
                switch (direction) {
                    case "up": worldY -= speed; break;
                    case "down": worldY += speed; break;
                    case "left": worldX -= speed; break;
                    case "right": worldX += speed; break;
                }
            }

            // Update sprite for animation
            spriteCounter++;
            if (spriteCounter > 12) {
                spriteNum = (spriteNum % 3) + 1; // Loop between 1, 2, 3
                spriteCounter = 0;
            }
        } else if (keyH.enterPressed) {
            // If standing still, allow interactions
            int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
            interactNPC(npcIndex);

            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(objIndex);

            gp.eHandler.checkEvent();
        }

        // Handle invincibility duration
        if (invincible) {
            invincibleCounter++;
            if (invincibleCounter > 60) {
                invincible = false;
                invincibleCounter = 0;
            }
        }

        System.out.println("Player update called. Game state: " + gp.gameState);
    }
    
    public void attacking() {
        spriteCounter++;

        if (spriteCounter <= 5) {
            spriteNum = 1; // First frame
        }
        if (spriteCounter > 5 && spriteCounter <= 15) {
            spriteNum = 2; // Second frame
        }
        if (spriteCounter > 15 && spriteCounter <= 25) {
            spriteNum = 3; // Third frame
        }
        if (spriteCounter > 25) {
            spriteNum = 1; // Reset to the first frame
            spriteCounter = 0;
            attacking = false; // Attack animation ends
        }
    }

    
    public void pickUpObject(int i) {
        if (i != 999) {
            // Logic to handle picking up an object
            // For example: gp.obj[i].interact(); or similar
        }
    }

    public void interactNPC(int i) {
    	if(gp.keyH.enterPressed == true) 
    	{
    	if (i != 999) {
        	      
                gp.gameState = gp.dialogueState; // Change game state to dialogue
                gp.npc[i].speak(); // Call the speak method on the NPC
            } else {

                	attacking = true;
                
            }
        }
        gp.keyH.enterPressed = false;
    }
        
    public void contactMonster(int i) {
    	if(i != 999) {
    		
    		if(invincible == false) {
    			life -= 1;
    			invincible = true;
    		}
    	}
    }
    
    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        if (attacking) {
            // Select attack sprites based on direction
            switch (direction) {
                case "up":
                    image = getSpriteImage(attackUp1, attackUp2, attackUp3);
                    break;
                case "down":
                    image = getSpriteImage(attackDown1, attackDown2, attackDown3);
                    break;
                case "left":
                    image = getSpriteImage(attackLeft1, attackLeft2, attackLeft3);
                    break;
                case "right":
                    image = getSpriteImage(attackRight1, attackRight2, attackRight3);
                    break;
            }
        } else {
            // Select movement sprites based on direction
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

        // Handle invincibility effect (if any)
        if (invincible) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f)); // Transparent effect
        }

        // Draw the current sprite
        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);

        // Reset alpha (if transparency was applied)
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
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