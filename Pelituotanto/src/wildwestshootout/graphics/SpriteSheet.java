/* 
 * Copyright (C) 2015 Sami Nurmivaara
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package wildwestshootout.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Sami nurmivaara
 */
public class SpriteSheet {

    //Tekstuurit-kuvan lataaminen
    private String path;

    //kuvan koko ja pikselit sisältävä lista
    public final int SIZE;
    public final int WIDTH, HEIGHT;
    public int[] pixels;

    //Ladataan mahdolliset Sprite -taulukot (sijainti, koko)
    public static SpriteSheet tiles = new SpriteSheet("/textures/sheets/spritesheet.png", 256);
    public static SpriteSheet projectile_musket = new SpriteSheet("/textures/sheets/projectiles/projectile_musket.png", 48);
    
    //Pelaajan tekstuurit
    public static SpriteSheet player = new SpriteSheet("/textures/sheets/player/player_sheet.png", 48, 64);
    public static SpriteSheet player_right = new SpriteSheet(player, 0, 0, 3, 1, 16);
    public static SpriteSheet player_left = new SpriteSheet(player, 0, 1, 3, 1, 16);
    public static SpriteSheet player_up = new SpriteSheet(player, 0, 3, 3, 1, 16);
    public static SpriteSheet player_down = new SpriteSheet(player, 0, 2, 3, 1, 16);

    //Siviilin tekstuurit
    public static SpriteSheet civilian = new SpriteSheet("/textures/sheets/mobs/civilian_sheet.png",48, 64);
    public static SpriteSheet civilian_right = new SpriteSheet(civilian, 0, 0, 3, 1, 16);
    public static SpriteSheet civilian_left = new SpriteSheet(civilian, 0, 1, 3, 1, 16);
    public static SpriteSheet civilian_up = new SpriteSheet(civilian, 0, 3, 3, 1, 16);
    public static SpriteSheet civilian_down = new SpriteSheet(civilian, 0, 2, 3, 1, 16);
    
    private Sprite[] sprites;

    //Konstruktori jolle määritellään tiedoston polku, koko sekä pikseli lista vastaamaan koko * koko
    public SpriteSheet(String path, int size) {
        this.path = path;
        this.SIZE = size;
        this.WIDTH = size;
        this.HEIGHT = size;
        this.pixels = new int[SIZE * SIZE];
        load();
    }

    public SpriteSheet(SpriteSheet sheet, int x, int y, int width, int height, int spriteSize) {
        int xx = x * spriteSize;
        int yy = y * spriteSize;
        int h = height * spriteSize;
        int w = width * spriteSize;
        if (width == height) {
            SIZE = width;
        } else {
            SIZE = -1;
        }
        WIDTH = w;
        HEIGHT = h;
        pixels = new int[w * h];
        for (int y0 = 0; y0 < h; y0++) {
            int yp = yy + y0;
            for (int x0 = 0; x0 < w; x0++) {
                int xp = xx + x0;
                pixels[x0 + y0 * w] = sheet.pixels[xp + yp * sheet.WIDTH];
            }
        }

        int frame = 0;
        sprites = new Sprite[width * height];
        for (int ya = 0; ya < height; ya++) {
            for (int xa = 0; xa < width; xa++) {
                int[] spritePixels = new int[spriteSize * spriteSize];
                for (int y0 = 0; y0 < spriteSize; y0++) {
                    for (int x0 = 0; x0 < spriteSize; x0++) {
                        spritePixels[x0 + y0 * spriteSize] = pixels[(x0 + xa * spriteSize) + (y0 + ya * spriteSize) * WIDTH];
                    }
                }
                Sprite sprite = new Sprite(spritePixels, spriteSize, spriteSize);
                sprites[frame++] = sprite;
            }
        }

    }

    public SpriteSheet(String path, int width, int height) {
        this.path = path;
        this.SIZE = -1;
        this.WIDTH = width;
        this.HEIGHT = height;
        this.pixels = new int[WIDTH * HEIGHT];
        load();
    }

    public Sprite[] getSprites() {
        return sprites;
    }

    //Ladataan tekstuurit-kuva
    private void load() {
        try {
            BufferedImage image = ImageIO.read(SpriteSheet.class.getResource(path));
            int width = image.getWidth();
            int height = image.getHeight();
            image.getRGB(0, 0, width, height, pixels, 0, width);
        } catch (IOException ex) {
            Logger.getLogger(SpriteSheet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
