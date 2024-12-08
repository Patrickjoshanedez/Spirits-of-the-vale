package main;

import entity.NPC_Mage1;
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
    	gp.monster[0] = new MON_RedSlime(gp);
        gp.monster[0].worldX = gp.tileSize * 10;
        gp.monster[0].worldY = gp.tileSize * 4;
    }

}