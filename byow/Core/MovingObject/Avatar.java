package byow.Core.MovingObject;

import byow.Core.FixedObject.Fish;
import byow.Core.FixedObject.FixedObject;
import byow.Core.Position;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.*;

public class Avatar extends MovingObject implements MovingObjectInterface {

    private static final TETile TILETYPE = Tileset.AVATAR;

    int fish;
    HashMap<String, Integer> obtainedItems;

    public Avatar(TETile[][] tiles, int fish, int hp, Random random) {
        super(tiles, random);
        this.hp = hp;
        this.fish = fish;
        this.obtainedItems = new HashMap<>();
        create(TILETYPE);
    }

    @Override
    public void move(int x, int y, TETile TILETYPE) {
        super.move(x, y, TILETYPE);
    }

    @Override
    public void update(int x, int y) {
        move(x, y, TILETYPE);
    }

    /* Obtain an item */
    public void obtain(FixedObject item) {
        if (item instanceof Fish) {
            fish += 1;
        }
        obtainedItems.put(item.toString(), obtainedItems.getOrDefault(item, 0) + 1);
        item.obtained();
    }

    /* Eating acquired fish increases the avatar`s HP */
    public void eatFish() {
        if (fish > 0) {
            int randHp = random.nextInt(10);
            this.hp += randHp;
            this.fish -= 1;
        }
    }

    /* Get number of fish it has acquired */
    public int getFish() {
        return fish;
    }
    public int getHP() { return hp; }

    /* Get position of the avatar */
    public Position getPosition() {
        return new Position(getXPos(), getYPos());
    }
}
