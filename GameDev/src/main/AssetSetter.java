package main;

import object.OBJ_Boots;
import object.OBJ_Chest;
import object.OBJ_Door;
import object.OBJ_Key;
import object.OBJ_Sword;

public class AssetSetter {

	GamePanel gp;
	
	public AssetSetter (GamePanel gp) {
		this.gp = gp;
	}
	
	public void setObject () {
		
		gp.obj[0] = new OBJ_Key();
		gp.obj[0].worldX = 1 * gp.tileSize;
		gp.obj[0].worldY = 7 * gp.tileSize;
		
		gp.obj[1] = new OBJ_Key();
		gp.obj[1].worldX = 1 * gp.tileSize;
		gp.obj[1].worldY = 23 * gp.tileSize;
	
		gp.obj[2] = new OBJ_Sword();
		gp.obj[2].worldX = 8 * gp.tileSize;
		gp.obj[2].worldY = 18 * gp.tileSize;
		
		gp.obj[3] = new OBJ_Door();
		gp.obj[3].worldX = 6 * gp.tileSize;
		gp.obj[3].worldY = 10 * gp.tileSize;
		
		gp.obj[4] = new OBJ_Door();
		gp.obj[4].worldX = 6 * gp.tileSize;
		gp.obj[4].worldY = 13 * gp.tileSize;
		
		gp.obj[5] = new OBJ_Boots();
		gp.obj[5].worldX = 8 * gp.tileSize;
		gp.obj[5].worldY = 16 * gp.tileSize;
		
		gp.obj[6] = new OBJ_Chest();
		gp.obj[6].worldX = 10 * gp.tileSize;
		gp.obj[6].worldY = 25 * gp.tileSize;
	
		
	}
}
