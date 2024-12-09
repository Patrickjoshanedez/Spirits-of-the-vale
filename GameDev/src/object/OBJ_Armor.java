package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Armor extends Entity {
	public OBJ_Armor (GamePanel gp) {
		super(gp);
		
		name = "Armor";
		down1 = setup("/objects/armor", gp.tileSize, gp.tileSize);
		defenseValue = 1;
		description = "{"+name+"}\nA bit worn Armor but not bad";
	}
}
