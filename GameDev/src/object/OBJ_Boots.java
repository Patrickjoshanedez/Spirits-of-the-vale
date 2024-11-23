package object;

import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel;
import main.UtilityTool; // Make sure to import the UtilityTool class

public class OBJ_Boots extends SuperObjects {
    GamePanel gp;
    UtilityTool uTool; // Declare the UtilityTool instance

    public OBJ_Boots(GamePanel gp) {
        this.gp = gp;
        name = "Boots";
        uTool = new UtilityTool(); // Initialize the UtilityTool instance

        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/boots.png"));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize); // Use the uTool instance to scale the image
        } catch (IOException e) {
            e.printStackTrace();
        }
        collision = true;
    }
}