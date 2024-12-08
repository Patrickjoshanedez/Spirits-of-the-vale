package monster;

import java.util.Random;

import entity.Entity;
import main.GamePanel;

public class MON_RedSlime extends Entity {

    public MON_RedSlime(GamePanel gp) {
        super(gp);
        name = "Red Slime";
        speed = 1;
        maxLife = 4;
        life = maxLife;

        solidArea.x = 3;
        solidArea.y = 18;
        solidArea.width = 42;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        
        getImage();
    }

    
    public void getImage() {

            up1 = setup("/monster/rSlime1");
            up3 = setup("/monster/rSlime2");
            up2 = setup("/monster/rSlime3");
            down1 = setup("/monster/rSlime1");
            down2 = setup("/monster/rSlime2");
            down3 = setup("/monster/rSlime3");
            left1 = setup("/monster/rSlime1");
            left2 = setup("/monster/rSlime2");
            left3 = setup("/monster/rSlime3");
            right1 = setup("/monster/rSlime1");
            right2 = setup("/monster/rSlime2");
            right3 = setup("/monster/rSlime3");
    }

    public void setAction() {
        actionLockCounter++;
        if (actionLockCounter >= 150) { // Randomize direction every 150 updates
            Random random = new Random();
            int i = random.nextInt(100) + 1; // Generate 1-100

            if (i <= 25) {
                direction = "up";
            } else if (i > 25 && i <= 50) {
                direction = "down";
            } else if (i > 50 && i <= 75) {
                direction = "left";
            } else {
                direction = "right";
            }        
            actionLockCounter = 0;
        }
    }
}

