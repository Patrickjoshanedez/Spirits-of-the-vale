package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

public class UI {

    GamePanel gp;
    Graphics2D g2;
    Font upheavtt;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public boolean gameFinished = false;
    public String currentDialogue = "";
//
//    double playTime;
//    DecimalFormat dFormat = new DecimalFormat("#0.00");

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

        if (gp.gameState == gp.playState) {
            // Draw game elements here (e.g., player stats, score, etc.)
        }

        if (gp.gameState == gp.pauseState) {
            drawPauseScreen(); // Call drawPauseScreen when the game is paused
        }
        
        if (gp.gameState == gp.dialogueState) {
            drawDialogueScreen(); // Call drawDialogueScreen when in dialogue state
        }

        // Handle other game states if necessary
    }
    
  //window
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
    	g2.setFont(g2.getFont().deriveFont(Font.PLAIN,80F));
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