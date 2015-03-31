package wildwestshootout.level.tile;

import wildwestshootout.graphics.Screen;
import wildwestshootout.graphics.Sprite;

/**
 *
 * @author Sami
 */
class VoidTile extends Tile {

    public VoidTile(Sprite sprite) {
        super(sprite);
    }
    
    public void render(int x, int y, Screen screen) {
        screen.renderTile(x, y, this);
    }
    
}
