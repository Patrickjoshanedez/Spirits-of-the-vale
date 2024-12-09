package main;

import entity.NPC_Mage1;
import monster.MON_AdikEye;
import monster.MON_RedSlime;
import object.OBJ_Door;


public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {

    }

    public void setNPC() {
        gp.npc[0] = new NPC_Mage1(gp);
        gp.npc[0].worldX = gp.tileSize * 10;
        gp.npc[0].worldY = gp.tileSize * 2;
        
        

    }
    public void setMonster() {
    	int i = 0;
    	gp.monster[i] = new MON_RedSlime(gp);
        gp.monster[i].worldX = gp.tileSize * 10;
        gp.monster[i].worldY = gp.tileSize * 4;
        i++;
        gp.monster[i] = new MON_AdikEye(gp);
        gp.monster[i].worldX = gp.tileSize * 11;
        gp.monster[i].worldY = gp.tileSize * 5;
        i++;
        gp.monster[i] = new MON_AdikEye(gp);
        gp.monster[i].worldX = gp.tileSize * 15;
        gp.monster[i].worldY = gp.tileSize * 5;
        i++;
        gp.monster[i] = new MON_RedSlime(gp);
        gp.monster[i].worldX = gp.tileSize * 13;
        gp.monster[i].worldY = gp.tileSize * 4;
        i++;
    }

}