
package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import entity.Entity;

import object.OBJ_Heart;


public class UI {

    GamePanel gp;
    Graphics2D g2;
    Font upheavtt;
    BufferedImage heart1, heart2, heart3;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public boolean gameFinished = false;
    public String currentDialogue = "";
    public int commandNum = 0;
    public int titleScreenState = 0; // 0: the first screen 1:the second screen

    // double playTime;
    // DecimalFormat dFormat = new DecimalFormat("#0.00");

    public UI(GamePanel gp) {
        this.gp = gp;

        try {
            InputStream is = getClass().getResourceAsStream("/font/upheavtt.ttf");
            upheavtt = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // CREATE HUD OBJECT
        Entity heart = new OBJ_Heart(gp);
        heart1 = heart.image;
        heart2 = heart.image2;
        heart3 = heart.image3;
    }

    public void showMessage(String text) {
        message = text;
        messageOn = true;
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;

        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setFont(upheavtt);
        g2.setColor(Color.white);

        // title state
        if (gp.gameState == gp.titleState) {
            drawTitleScreen();
        }
        // playstate
        if (gp.gameState == gp.playState) {
            drawPlayerLife();
            // Draw game elements here (e.g., player stats, score, etc.)
        }

        if (gp.gameState == gp.pauseState) {
            drawPlayerLife();
            drawPauseScreen(); // Call drawPauseScreen when the game is paused

        }

        if (gp.gameState == gp.dialogueState) {

            drawPlayerLife();
            drawDialogueScreen(); // Call drawDialogueScreen when in dialogue state

        }

        if (gp.gameState == gp.gameOverState) {
            drawGameOverScreen();
        }

        // Handle other game states if necessary
    }

    public void drawPlayerLife() {
        int x = gp.tileSize / 2;
        int y = gp.tileSize / 2;
        int i = 0;

        while (i < gp.player.maxLife / 2) {
            if (gp.player.life > (i * 2) + 1) { // Full heart
                g2.drawImage(heart1, x, y, null);
            } else if (gp.player.life == (i * 2) + 1) { // Half heart
                g2.drawImage(heart2, x, y, null);
            } else { // Empty heart
                g2.drawImage(heart3, x, y, null);
            }
            i++;
            x += gp.tileSize;
        }
    }

    // draw game over screen
    private void drawGameOverScreen() {
        g2.setColor(new Color(0, 0, 0, 150)); // Semi-transparent black
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 80F));
        String text = "GAME OVER";
        int x = getXforCenteredText(text);
        int y = gp.screenHeight / 2;

        // Shadow effect
        g2.setColor(Color.BLACK);
        g2 .drawString(text, x + 5, y + 5);

        // Main text
        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);
    }

    private void drawHorizontalSword(Graphics2D g2, int x, int y) {
        // Blade
        g2.setColor(Color.LIGHT_GRAY);
        g2.fillRect(x, y + 5, gp.tileSize, 8); // Blade rectangle

        // Hilt
        g2.setColor(Color.DARK_GRAY);
        g2.fillRect(x + gp.tileSize / 4, y, 10, 18); // Vertical hilt

        // Cross-guard
        g2.setColor(Color.YELLOW);
        g2.fillRect(x + gp.tileSize / 4 - 8, y + 4, 26, 5); // Horizontal cross-guard
    }

    public void drawTitleScreen() {
        if (titleScreenState == 0) {
            // Title Font
            Font titleFont = new Font("Serif", Font.BOLD | Font.ITALIC, 80);
            g2.setFont(titleFont);

            String text = "Spirits of The Vale";
            int x = getXforCenteredText(text);
            int y = gp.tileSize * 3;

            // Enable anti-aliasing for smoother text rendering
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            // Add a background (gradient or solid color)
            GradientPaint bgGradient = new GradientPaint(0, 0, new Color(0, 40, 80), 0, gp.screenHeight, new Color(0, 100, 50), false);
            g2.setPaint(bgGradient);
            g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight); // Fills the screen with the gradient background

            // Create a complementary gradient color for the text
            GradientPaint textGradient = new GradientPaint(0, y - 50, new Color(255, 165, 0), 0, y + 50, new Color(255, 215, 0), true); // Orange to Yellow
            g2.setPaint(textGradient);

            // Draw the text with the gradient
            g2.drawString(text, x, y);

            // Add a subtle shadow effect for depth
            g2.setColor(new Color(0, 0, 0, 150)); // Semi-transparent black shadow
            g2.drawString(text, x + 4, y + 4); // Slight offset for shadow

            // Title screen icon (Crossed Pixel Swords)
            // Scale factor for smaller swords
            double scale = 1 / 1.5;

            // Sword dimensions (scaled down)
            int swordWidth = (int) (10 * 3 * scale);
            int swordHeight = (int) (50 * 3 * scale);
            int hiltWidth = (int) (30 * 3 * scale);
            int hiltHeight = (int) (10 * 3 * scale);
            int gripWidth = (int) (10 * 3 * scale);
            int gripHeight = (int) (20 * 3 * scale);

            // Sword positions
            int centerX = gp.screenWidth / 2;
            int swordY = y + gp.tileSize * 1; // Increased space to avoid overlap with text

            // Sword colors
            Color bladeColor = new Color(200, 200, 255); // Light blue
            Color hiltColor = new Color(150, 75, 0); // Brown
            Color gripColor = new Color(100, 100, 100); // Gray

            // Draw first sword (angled to the left)
            g2.setColor(bladeColor);
            g2.rotate(Math.toRadians(-45), centerX, swordY + swordHeight / 2); // Rotate around center
            g2.fillRect(centerX - swordWidth / 2, swordY, swordWidth, swordHeight); // Vertical blade
            g2.setColor(hiltColor);
            g2.fillRect(centerX - hiltWidth / 2, swordY + swordHeight, hiltWidth, hiltHeight); // Hilt
            g2.setColor(gripColor);
            g2.fillRect(centerX - gripWidth / 2, swordY + swordHeight + hiltHeight, gripWidth, gripHeight); // Grip
            g2.rotate(Math.toRadians(45), centerX, swordY + swordHeight / 2); // Reset rotation

            // Draw second sword (angled to the right)
            g2 .setColor(bladeColor);
            g2.rotate(Math.toRadians(45), centerX, swordY + swordHeight / 2); // Rotate around center
            g2.fillRect(centerX - swordWidth / 2, swordY, swordWidth, swordHeight); // Vertical blade
            g2.setColor(hiltColor);
            g2.fillRect(centerX - hiltWidth / 2, swordY + swordHeight, hiltWidth, hiltHeight); // Hilt
            g2.setColor(gripColor);
            g2.fillRect(centerX - gripWidth / 2, swordY + swordHeight + hiltHeight, gripWidth, gripHeight); // Grip
            g2.rotate(Math.toRadians(-45), centerX, swordY + swordHeight / 2); // Reset rotation

            // Menu
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));

            // Gradient for the menu text (matching title screen)
            GradientPaint menuTextGradient = new GradientPaint(
                0, y - 50, new Color(255, 165, 0), // Orange
                0, y + 50, new Color(255, 215, 0), // Yellow
                true
            );

            // Draw "NEW GAME"
            g2.setPaint(menuTextGradient); // Reset gradient
            text = "NEW GAME";
            x = getXforCenteredText(text);
            y = gp.tileSize * 9; // Set the menu just below the swords
            g2.drawString(text, x, y);
            if (commandNum == 0) {
                drawHorizontalSword(g2, x - gp.tileSize, y - gp.tileSize / 3);
            }

            // Draw "LOAD GAME"
            g2.setPaint(menuTextGradient); // Reset gradient
            text = "LOAD GAME";
            x = getXforCenteredText(text);
            y += gp.tileSize; // Increase vertical spacing for next menu item
            g2.drawString(text, x, y);
            if (commandNum == 1) {
                drawHorizontalSword(g2, x - gp.tileSize, y - gp.tileSize / 3);
            }

            // Draw "EXIT"
            g2.setPaint(menuTextGradient); // Reset gradient
            text = "EXIT";
            x = getXforCenteredText(text);
            y += gp.tileSize; // Increase vertical spacing for next menu item
            g2.drawString(text, x, y);
            if (commandNum == 2) {
                drawHorizontalSword(g2, x - gp.tileSize, y - gp.tileSize / 3);
            }
        }
    }

    // window
    public void drawDialogueScreen() {
        int x = gp.tileSize * 2; // X position
        int y = gp.tileSize / 2; // Y position
        int width = gp.screenWidth - (gp.tileSize * 4); // Width of the dialogue box
        int height = gp.tileSize * 4; // Height of the dialogue box

        // Draw the background rectangle for the dialogue box
        drawSubWindow(x, y, width, height);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 25));
        x += gp.tileSize; // Adjust x position for padding
        y += gp.tileSize; // Adjust y position for padding

        // Split the current dialogue into lines
        String[] lines = currentDialogue.split("\n");

        // Draw each line of dialogue
        for (String line : lines) {
            g2.drawString(line, x, y); // Draw the current line
            y += 40; // Move down for the next line
        }
    }

    public void drawSubWindow(int x, int y, int width, int height) {
        // Draw the background
        Color backgroundColor = new Color(0, 0, 0, 190); // Black color for the background
        g2.setColor(backgroundColor);
        g2.fillRoundRect(x, y, width, height, 35, 35); // Draw a rounded rectangle for the background

        // Draw the border
        Color borderColor = new Color(255, 255, 255); // White color for the border
        g2.setColor(borderColor);
        g2.setStroke(new BasicStroke(5)); // Set the stroke width for the border

        // Draw the border as a larger rounded rectangle
        g2.drawRoundRect(x, y, width, height, 35, 35); // Draw the border
    }

    public void drawPauseScreen() {
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));
 String text = "PAUSED";
        int x = getXforCenteredText(text);
        int y = gp.screenHeight / 2;

        g2.drawString(text, x, y);
    }

    public int getXforCenteredText(String text) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth / 2 - length / 2;
        return x;
    }
}