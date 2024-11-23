package main;

import entity.NPC_Mage1;
import object.SuperObjects; // Ensure this is the correct import for your SuperObjects class

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {
        // Example: Initialize game objects and add them to the obj array
        gp.obj[0] = new SuperObjects(); // Replace with your actual object class
        gp.obj[0].worldX = gp.tileSize * 5; // Set position
        gp.obj[0].worldY = gp.tileSize * 7; // Set position

        // Add more objects as needed
        gp.obj[1] = new SuperObjects(); // Another object
        gp.obj[1].worldX = gp.tileSize * 8; // Set position
        gp.obj[1].worldY = gp.tileSize * 4; // Set position

        // Continue to initialize additional objects as necessary
    }

    public void setNPC() {
        gp.npc[0] = new NPC_Mage1(gp);
        gp.npc[0].worldX = gp.tileSize * 10;
        gp.npc[0].worldY = gp.tileSize * 2;

        // Initialize more NPCs if needed
        gp.npc[1] = new NPC_Mage1(gp); // For example, another NPC
        gp.npc[1].worldX = gp.tileSize * 12; // Set position
        gp.npc[1].worldY = gp.tileSize * 3; // Set position
    }
}