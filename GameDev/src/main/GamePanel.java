
package main;

import java.awt.Color;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JPanel;

import entity.Entity;
import entity.Player;
import tile.TileManager;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable {

    final int originalTileSize = 16;
    final int scale = 3;
    
    public final int tileSize = originalTileSize * scale;
    public final int maxScreenCol = 20;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;
    
    public final int maxWorldCol = 100;
    public final int maxWorldRow = 100;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;
    
    // FULLSCREEN
    int screenWidth2 = screenWidth;
    int screenHeight2 = screenHeight;
    BufferedImage tempScreen;
    Graphics2D g2;
    public boolean fullScreenOn = false;
    
    // FPS
    int FPS = 60;
    
    // SYSTEM
    TileManager tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler(this);
    Sound music = new Sound();
    Sound se = new Sound();
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    public EventHandler eHandler = new EventHandler(this);
    Thread gameThread;
    
    // ENTITY and OBJECT
    public Player player = new Player(this, keyH);
    public Entity obj[] = new Entity[15]; // Initialize obj array
    public Entity npc[] = new Entity[10];
    public Entity monster[] = new Entity[20];
    public ArrayList<Entity> projectileList = new ArrayList<>();
    ArrayList<Entity> entityList = new ArrayList<>();
    
    
    // GAME STATE    
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int characterState = 4;
    public final int gameOverState = 5;
    public final int optionState = 6;
    
    
    
    // Set player's default position
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 6;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setUpGame() {
        aSetter.setObject(); // Ensure objects are initialized
        aSetter.setNPC(); // Ensure NPCs are initialized
        aSetter.setMonster();
        gameState = titleState; // Ensure the title state is set initially
        
        tempScreen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
        g2 = (Graphics2D)tempScreen.getGraphics();
        
//        setFullScreen();
        
    }
    
    public void setFullScreen() {
    	//GET LOCAL SCREEN DEVICE
    	GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    	GraphicsDevice gd = ge.getDefaultScreenDevice();
    	gd.setFullScreenWindow(Main.window);
    	
    	//GET FULL SCREEN HEIGHT AND WIDTH
    	screenWidth2 = Main.window.getWidth();
    	screenHeight2 = Main.window.getHeight();
    	
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }
    
    public void run() {
        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;
        try {
        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;
            if (delta >= 1) {
                update();
                drawtoTempScreen(); //DRAW EVERYTHING TO BUFFERED IMAGE
                drawToScreen(); // DRAW BUFFERED IMAGE TO SCREEN
                delta--;
                drawCount++;
            }
            if (timer >= 1000000000) {
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
        }catch(Exception e) {
        	System.out.println("Image null");
        }
    }

    public void update() {
        if (gameState == playState) {
            // player
            player.update();
            // npc
            for (int i = 0; i < npc.length; i++) {
                if (npc[i] != null) {
                    npc[i].update();
                }
            }
            for (int i = 0; i < monster.length; i++) {
                if (monster[i] != null) {
                	if(monster[i].alive == true && monster[i].dying ==false) {
                		monster[i].update();
                	}
                	if(monster[i].alive == false) {
                		monster[i].checkDrop();
                		monster[i] = null;
                	}
                }
            }
            for (int i = 0; i < projectileList.size(); i++) {
                if (projectileList.get(i) != null) {
                	if(projectileList.get(i).alive == true) {
                		projectileList.get(i).update();
                	}
                	if(projectileList.get(i).alive == false) {
                		projectileList.remove(i);
                	}
                }
            }
            
            eHandler.checkEvent(); // Use the existing EventHandler instance
        }
    }
    
    public void drawtoTempScreen() {
    	 if (gameState == titleState) {
             ui.draw(g2); // Draw title screen
         } else {
             
         	tileM.draw(g2);
         	
         	entityList.add(player);
         	
         	//add entities to the list
         	for(int i = 0; i < npc.length; i++) {
         		if(npc[i] != null) {
         			entityList.add(npc[i]);
         			}
         		}
         	
         
 	        for(int i = 0; i < obj.length; i++) {
 	    		if(obj[i] != null) {
 	    			entityList.add(obj[i]);
 	    			}
 	    		}	
 	        for(int i = 0; i < monster.length; i++) {
 	    		if(monster[i] != null) {
 	    			entityList.add(monster[i]);
 	    			}
 	    		}
 	        for(int i = 0; i < projectileList.size(); i++) {
 	    		if(projectileList.get(i) != null) {
 	    			entityList.add(projectileList.get(i));
 	    			}
 	    		}
 	        //sort
 	        Collections.sort(entityList, new Comparator<Entity>() {

 				@Override
 				public int compare(Entity e1, Entity e2) {
 					int result = Integer.compare(e1.worldY, e2.worldY);
 					return result;
 				}
 	        	
 	        });
 	        try {
 	        //draw entities
 	        for(int i = 0; i < entityList.size(); i ++) {
 	        	entityList.get(i).draw(g2);
 	        }
 	        }catch(Exception e) {
 	        	System.out.println("image null");
 	        }
 	        
 	        // Draw entities
 	        entityList.clear();

 	        // Clear entity list after rendering
 	        entityList.clear();

 	        // Draw UI
 	        ui.draw(g2);

         
     }
    }

    public void drawToScreen() {
    	Graphics g = getGraphics();
    	g.drawImage(tempScreen, 0, 0, screenWidth2, screenHeight2, null);
    	g.dispose();
    }
    
    public void playMusic(int i) {
        music.setFile(i);
        music.play();
        music.loop();
    }

    public void stopMusic() {
        music.stop();
    }

    public void playSE(int i) {
        se.setFile(i);
        se.play();
    }
}
