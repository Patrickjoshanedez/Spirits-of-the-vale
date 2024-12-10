package object;

import java.io.IOException;
import javax.imageio.ImageIO;

import entity.Entity;
import main.GamePanel;
import main.UtilityTool; // Make sure to import the UtilityTool class

public class OBJ_Boots extends Entity {

    public OBJ_Boots(GamePanel gp) {
        super(gp);
        
        name = "Boots";
        description = "{"+name+"}\nGood ol' boots";
        down1 = setup("/objects/boots", gp.tileSize, gp.tileSize);
        speed = 8;
        collision = false;
        isStatic = true;

        solidArea.x = 0;
        solidArea.y = 16;
        solidArea.width = 48;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
}