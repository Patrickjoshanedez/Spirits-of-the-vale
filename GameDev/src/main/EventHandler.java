package main;

import java.awt.Rectangle;

public class EventHandler {
    
    GamePanel gp;
    Rectangle eventRect;
    int eventRectDefaultX, eventRectDefaultY;
    
    public EventHandler(GamePanel gp) {
        this.gp = gp;
        
        eventRect = new Rectangle();
        eventRect.x = 23;
        eventRect.y = 23;
        eventRect.width = 2;
        eventRect.height = 2;
        eventRectDefaultX = eventRect.x;
        eventRectDefaultY = eventRect.y;
    }
    
    public void checkEvent() {
        // Simplified condition check
        if (hit(4, 1, "right")) {
            damagePit(gp.dialogueState);
        }
    }
    
    public boolean hit(int eventCol, int eventRow, String reqDirection) {
        boolean hit = false;
        
        // Save original player solid area
        int playerSolidX = gp.player.solidArea.x;
        int playerSolidY = gp.player.solidArea.y;
        
        // Update player solid area to world coordinates
        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
        
        // Update event rectangle position
        eventRect.x = eventCol * gp.tileSize + eventRectDefaultX;
        eventRect.y = eventRow * gp.tileSize + eventRectDefaultY;
        
        // Check intersection
        if (gp.player.solidArea.intersects(eventRect)) {
            if (gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")) {
                hit = true;
            }
        }
        
        // Restore original player solid area
        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;
        
        // Reset event rectangle position
        eventRect.x = eventRectDefaultX;
        eventRect.y = eventRectDefaultY;
        
        return hit;
    }
    
    public void damagePit(int gameState) {
        gp.gameState = gameState;
        gp.ui.currentDialogue = "You fell, but no one will catch you";
        gp.player.life -= 1;
        
//        // Optional: Add additional effects
//        if (gp.player.life <= 0) {
//            gp.gameState = gp.gameOverState; // Assuming you have a game over state
//        }
    }
}