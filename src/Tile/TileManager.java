package Tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import Main.GamePanel;

public class TileManager {

		GamePanel gp;
		public tile[] Tile;
		public int mapTileNum[][];
		
		public TileManager(GamePanel gp) {
			
			this.gp = gp;
			
			Tile = new tile[10];
			mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
			
			
			getTileImage();
			loadMap("/maps/map01.txt/");
		}
		public void getTileImage() {
			
			try {
				
				Tile[0] = new tile();
				Tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass.png/"));
				
				Tile[1] = new tile();
				Tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall.png/"));
				Tile[1].collision = true;
				
				Tile[2] = new tile();
				Tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water.png/"));
				Tile[2].collision = true;
				
				Tile[3] = new tile();
				Tile[3].image = ImageIO.read(getClass().getResourceAsStream("/tiles/land.png/"));
				
				Tile[4] = new tile();
				Tile[4].image = ImageIO.read(getClass().getResourceAsStream("/tiles/sand.png/"));
				
				Tile[5] = new tile();
				Tile[5].image = ImageIO.read(getClass().getResourceAsStream("/tiles/tree.png/"));
				Tile[5].collision = true;
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
		
		public void loadMap(String filePath) {
			try {
				
				InputStream is = getClass().getResourceAsStream(filePath);
				BufferedReader br = new BufferedReader(new InputStreamReader(is));
				
				int col = 0;
				int row = 0;
				
				while(col < gp.maxWorldCol && row < gp.maxWorldRow) {
					
					String line = br.readLine();
					
					while(col < gp.maxWorldCol) {
						
						String numbers[] = line.split(" ");
						
						int num = Integer.parseInt(numbers[col]);
						
						mapTileNum[col][row] = num;
						col++;
						
					}
					
					if(col == gp.maxWorldCol) {
						col = 0;
						row++;
					}
					
				}
				br.close();
				
			}catch(Exception e) {
				
			}
		}
		
		public void draw(Graphics2D g2) {
			int worldCol = 0;
			int worldRow = 0;
			while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {
				
				int tileNum = mapTileNum[worldCol][worldRow];
				//tracks players position & offset the center of the map
				int worldX = worldCol * gp.tileSize;
				int worldY = worldRow * gp.tileSize;
				int screenX = worldX - gp.player.worldX + gp.player.screenX;
				int screenY = worldY - gp.player.worldY + gp.player.screenY;
				//draw selected tiles on the center
				if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX && 
				   worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
				   worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
				   worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
					
					g2.drawImage(Tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
				}
				
				worldCol ++;
				

				if(worldCol == gp.maxWorldCol) {
					worldCol = 0;
					
					worldRow ++;
				
				}
			}
		}
}
