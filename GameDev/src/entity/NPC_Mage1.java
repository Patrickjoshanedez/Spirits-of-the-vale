package entity;

import java.util.Random;

import main.GamePanel;


public class NPC_Mage1 extends Entity{
	
	//replaced the original name NPC_Oldman
	public NPC_Mage1(GamePanel gp) {
	    super(gp);
	    direction = "left";
	    speed = 2;
	    getPlayerImage(); // Load NPC images
	}
	public void getPlayerImage() {
        up1 = setup("/npc/npc_up1");
        up2 = setup("/npc/npc_up2");
        up3 = setup("/npc/npc_up3");
        down1 = setup("/npc/npc_down1");
        down2 = setup("/npc/npc_down2");
        down3 = setup("/npc/npc_down3");
        left1 = setup("/npc/npc_left1");
        left2 = setup("/npc/npc_left2");
        left3 = setup("/npc/npc_left3");
        right1 = setup("/npc/npc_right1");
        right2 = setup("/npc/npc_right2");
        right3 = setup("/npc/npc_right3");
    }

	public void setAction(){
		actionLockCounter ++;
		if(actionLockCounter == 150) {
		Random random = new Random();
		int i = random.nextInt(100)+1;//pick up a number 1 - 100
		
		if(i <= 25) {
			direction = "up";
		}
		if( i > 25 && i <= 50) {
			direction = "down";
		}
		if( i > 50 && i <= 75) {
			direction = "left";
		}
		if( i > 75 && i <= 100) {
			direction = "right";
		}
		
		actionLockCounter = 0;
		}
	}
	
}
