package object;

import java.io.IOException;
import javax.imageio.ImageIO;

import entity.Entity;
import main.GamePanel;
import main.UtilityTool; // Ensure to import the UtilityTool class

public class OBJ_Chest extends Entity {


    public OBJ_Chest(GamePanel gp) {
    	super(gp);
        name = "Chest";
        down1 = setup("/objects/chest", gp.tileSize, gp.tileSize);
        collision = true;
    }
}