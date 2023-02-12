package byow.Core.MovingObject;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.*;

public class Avatar extends MovingObject implements MovingObjectInterface {

    private static final TETile TILETYPE = Tileset.AVATAR;
    private static final int HP = 100;

    public Avatar(TETile[][] tiles, Random random) {
        super(tiles, random);
        this.hp = HP;
        create(TILETYPE);
    }

    @Override
    public void move(int x, int y, TETile TILETYPE) {
        super.move(x, y, TILETYPE);
    }

    public void update(int x, int y) {
        move(x, y, TILETYPE);
    }

    /* Obtain an item */
    public void obtain() {

    }
}
