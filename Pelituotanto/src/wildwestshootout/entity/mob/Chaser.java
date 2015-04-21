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
package wildwestshootout.entity.mob;

import java.util.List;
import wildwestshootout.graphics.AnimatedSprite;
import wildwestshootout.graphics.Screen;
import wildwestshootout.graphics.Sprite;
import wildwestshootout.graphics.SpriteSheet;

/**
 *
 * @author Sami Nurmivaara
 */
public class Chaser extends Mob {

    private AnimatedSprite right = new AnimatedSprite(SpriteSheet.civilian_right, 32, 32, 8);
    private AnimatedSprite left = new AnimatedSprite(SpriteSheet.civilian_left, 32, 32, 8);
    private AnimatedSprite up = new AnimatedSprite(SpriteSheet.civilian_up, 32, 32, 8);
    private AnimatedSprite down = new AnimatedSprite(SpriteSheet.civilian_down, 32, 32, 8);

    private AnimatedSprite animSprite = down;

    private int xa = 0;
    private int ya = 0;

    public Chaser(int x, int y) {
        this.x = x << 4;
        this.y = y << 4;
        sprite = Sprite.civilian;
    }

    private void move() {
        xa = 0;
        ya = 0;

        List<Player> players = level.getPlayers(this, 50);
        Player player = players.get(0);

        if (players.size() > 0) {
            if (x < player.getX()) {
                xa++;
            }
            if (x > player.getX()) {
                xa--;
            }
            if (y < player.getY()) {
                ya++;
            }
            if (y < player.getY()) {
                ya--;
            }
        }

        if (xa != 0 || ya != 0) {
            move(xa, ya);
            walking = true;
        } else {
            walking = false;
        }
    }

    @Override
    public void update() {

        move();

        if (walking) {
            animSprite.update();
        } else {
            animSprite.setFrame(0);
        }

        if (ya < 0) {
            animSprite = up;
            direction = Direction.UP;
        } else if (ya > 0) {
            animSprite = down;
            direction = Direction.DOWN;
        }
        if (xa < 0) {
            animSprite = left;
            direction = Direction.LEFT;
        } else if (xa > 0) {
            animSprite = right;
            direction = Direction.RIGHT;
        }

        sprite = animSprite;
    }

    @Override
    public void render(Screen screen) {
        sprite = animSprite.getSprite();
        screen.renderMob(x - 16, y - 16, this);
    }

}