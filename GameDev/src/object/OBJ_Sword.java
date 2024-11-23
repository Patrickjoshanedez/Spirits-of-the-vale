package object;

import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel;
import main.UtilityTool; // Ensure to import the UtilityTool class

public class OBJ_Sword extends SuperObjects {
    GamePanel gp;
    UtilityTool uTool; // Declare the UtilityTool instance

    public OBJ_Sword(GamePanel gp) {
        this.gp = gp;
        name = "Sword";
        uTool = new UtilityTool(); // Initialize the UtilityTool instance

        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/sword.png"));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize); // Use the uTool instance to scale the image
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}