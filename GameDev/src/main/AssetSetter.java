package main;

import entity.NPC_Mage1;
import monster.MON_AdikEye;
import monster.MON_RedSlime;
import object.OBJ_Armor;
import object.OBJ_Boots;
import object.OBJ_Door;
import object.OBJ_Katana;


public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {
    	int i = 0;
    	gp.obj[i] = new OBJ_Boots(gp);
        gp.obj[i].worldX = gp.tileSize * 10;
        gp.obj[i].worldY = gp.tileSize * 2;
        i++;
        gp.obj[i] = new OBJ_Katana(gp);
        gp.obj[i].worldX = gp.tileSize * 10;
        gp.obj[i].worldY = gp.tileSize * 4;
        
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
//        gp.monster[i] = new MON_AdikEye(gp);
//        gp.monster[i].worldX = gp.tileSize * 11;
//        gp.monster[i].worldY = gp.tileSize * 5;
//        i++;
//        gp.monster[i] = new MON_AdikEye(gp);
//        gp.monster[i].worldX = gp.tileSize * 15;
//        gp.monster[i].worldY = gp.tileSize * 5;
//        i++;
//        gp.monster[i] = new MON_RedSlime(gp);
//        gp.monster[i].worldX = gp.tileSize * 13;
//        gp.monster[i].worldY = gp.tileSize * 4;
//        i++;
    }

}