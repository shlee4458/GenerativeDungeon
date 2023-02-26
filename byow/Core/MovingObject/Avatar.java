package byow.Core.MovingObject;

import byow.Core.FixedObject.*;
import byow.Core.Position;
import byow.TileEngine.*;

import java.util.*;

public class Avatar extends MovingObject implements MovingObjectInterface {

    private static final TETile TILETYPE = Tileset.AVATAR;
    HashMap<String, Integer> obtainedItems;

    /** Constructor */
    public Avatar(TETile[][] tiles, int fish, int hp, Random random) {
        super(tiles, random);
        this.hp = hp;
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

    /** Executed when the avatar obtains a fixed object. If the obtained item is fish, increases
    * the count of the fish acquired by one, and stores that item in the */
    public void obtain(FixedObject obj) {
        obj.obtained();
        String objType = obj.toString();
        obtainedItems.put(objType,obtainedItems.getOrDefault(objType, 0) + 1);
    }

    /** When the avatar eats the fish, the avatar`s HP increases randomly between 0 to 10,
     * and decreases the count of the fish in the itemObtained HashMap. */
    public void eatFish() {
        int fish = obtainedItems.getOrDefault("fish", 0);
        if (fish > 0) {
            int randHp = random.nextInt(10);
            hp += randHp;
            obtainedItems.put("fish", fish - 1);
        }
    }

    /** Get number of fish the avatar has acquired */
    public int getFish() {
        return obtainedItems.getOrDefault("fish", 0);
    }

    /** Get HP of the avatar */
    public int getHP() { return hp; }

    /* Get position of the avatar */
    public Position getPosition() {
        return new Position(getXPos(), getYPos());
    }
}
