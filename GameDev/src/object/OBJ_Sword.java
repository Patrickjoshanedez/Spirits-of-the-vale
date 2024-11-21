package object;

import java.io.IOException;

import javax.imageio.ImageIO;

public class OBJ_Sword extends SuperObjects{

	public OBJ_Sword () {
		
		name = "Sword";
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/sword.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}

