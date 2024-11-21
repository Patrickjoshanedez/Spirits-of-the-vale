package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;
import main.GamePanel;

public class TileManager {

    GamePanel gp;
    public Tile[] tile;
    public int mapTileNum[][];

    public TileManager(GamePanel gp) {
        this.gp = gp;

        tile = new Tile[155]; // Increased to accommodate tile index 154
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];

        getTileImage();
        loadMap("/maps/map1.txt");
    }

    public void getTileImage() {
        try {
            for (int i = 0; i < tile.length; i++) {
                tile[i] = new Tile();
                tile[i].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + String.format("%03d", i) + ".png"));

                // Set collision for specific tiles based on their index
                if ((i >= 1 && i <= 39) || // 001 to 039
                    (i >= 59 && i <= 69) || // 059 to 069
                    (i == 71) || // 071
                    (i >= 73 && i <= 75) || // 073 to 075
                    (i >= 77 && i <= 78) || // 077 to 078
                    (i >= 80 && i <= 83) || // 080 to 083
                    (i == 92) || // 092
                    (i >= 93 && i <= 94) || // 093 to 094
                    (i >= 99 && i <= 106) || // 099 to 106
                    (i == 109) || // 109
                    (i >= 111 && i <= 133) || // 111 to 133
                    (i == 142) || // 142
                    (i == 151)) {
                    tile[i].collision = true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMap(String filePath) {
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int row = 0;

            while (row < gp.maxWorldRow) {
                String line = br.readLine();

                if (line == null) break; // Check for end of file

                String numbers[] = line.split(" ");

                // Ensure we don't exceed the max columns
                for (int col = 0; col < gp.maxWorldCol && col < numbers.length; col++) {
                    int num = Integer.parseInt(numbers[col]);

                    // Validate the tile number
                    if (num < 0 || num >= tile.length) {
                        System.out.println("Invalid tile number: " + num + " at row: " + row + " col: " + col);
                        mapTileNum[col][row] = 0; // Use tile index 0 as a default (assuming it's a valid tile)
                    } else {
                        mapTileNum[col][row] = num;
                    }
                }

                row++;
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        for (int worldRow = 0; worldRow < gp.maxWorldRow; worldRow++) {
            for (int worldCol = 0; worldCol < gp.maxWorldCol; worldCol++) {
                int tileNum = mapTileNum[worldCol][worldRow];

                // Validate the tile number before drawing
                if (tileNum >= 0 && tileNum < tile.length) {
                    int worldX = worldCol * gp.tileSize;
                    int worldY = worldRow * gp.tileSize;
                    int screenX = worldX - gp.player.worldX + gp.player.screenX;
                    int screenY = worldY - gp.player.worldY + gp.player.screenY;

                    if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                        worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                        worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                        worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

                        g2.drawImage(tile[tileNum].image, screenX, screenY , gp.tileSize, gp.tileSize, null);
                    }
                }
            }
        }
    }
}