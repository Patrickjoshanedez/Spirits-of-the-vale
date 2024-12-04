
package main;

import java.awt.Rectangle;

public class EventHandler {

    GamePanel gp;
    Rectangle eventRect;
    int eventRectDefaultX, eventRectDefaultY;

    // Damage handling variables
    private boolean canTakeDamage = true;
    private int damageCooldown = 0;

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
        // Check for pit damage event
        if (hit(4, 1, "up")) {
            if (canTakeDamage) {
                damagePit(gp.dialogueState);
                canTakeDamage = false;
                damageCooldown = 60; // 1 second at 60 FPS
            }
        }

        // Cooldown logic
        if (!canTakeDamage) {
            damageCooldown--;
            if (damageCooldown <= 0) {
                canTakeDamage = true;
            }
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
            if (gp.player.direction.equals(reqDirection) || reqDirection.equals("any")) {
                hit = true;
            }
        }

        // Restore original player solid area
        gp.player.solidArea.x = playerSolidX;
        gp.player.solidArea.y = playerSolidY;

        // Reset event rectangle position
        eventRect.x = eventRectDefaultX;
        eventRect.y = eventRectDefaultY;

        return hit;
    }

    public void damagePit(int gameState) {
        if (gp == null || gp.ui == null || gp.player == null) {
            System.err.println("Critical error: Game components are null");
            return;
        }

        // Set dialogue state
        gp.gameState = gameState;
        gp.ui.currentDialogue = "You fell into a pit!";

        // Apply damage to player
        gp.player.life -= 1;

        // Play damage sound
        gp.playSE(3);

        // Check for game over
        if (gp.player.life <= 0) {
            gp.gameState = gp.gameOverState;
            gp.ui.currentDialogue = "You have perished...";
            gp.playSE(4);
        }
    }
}
