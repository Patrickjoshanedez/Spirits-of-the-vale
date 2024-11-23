package object;

import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel;
import main.UtilityTool; // Ensure to import the UtilityTool class

public class OBJ_Door extends SuperObjects {
    GamePanel gp;
    UtilityTool uTool; // Declare the UtilityTool instance

    public OBJ_Door(GamePanel gp) {
        this.gp = gp;
        name = "Door";
        uTool = new UtilityTool(); // Initialize the UtilityTool instance

        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/door.png"));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize); // Use the uTool instance to scale the image
        } catch (IOException e) {
            e.printStackTrace();
        }
        collision = true;
    }
}