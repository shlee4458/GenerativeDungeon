package byow.Core.MovingObject;

import byow.Core.FixedObject.*;
import byow.Core.Position;
import byow.Core.RandomUtils;
import byow.TileEngine.*;

import java.util.*;

public class Avatar extends MovingObject implements MovingObjectInterface {

    private static final TETile TILETYPE = Tileset.AVATAR;
    int floor;
    int fishObtained;

    /** Constructor */
    public Avatar(TETile[][] tiles, int floor, int hp, int fishObtained, Random random) {
        super(tiles, random);
        this.hp = hp;
        this.floor= floor;
        this.fishObtained = fishObtained;
        create(TILETYPE);
    }

    @Override
    public void move(int x, int y, TETile TILETYPE) {
        super.move(x, y, TILETYPE);

        /* If the Avatar bumps into the ghost object, diminishes HP by value randomly chosen from the
        * Poisson distribution with mean value of 5 + floor * 0.5 */
        if (tiles[x + getXPos()][y + getYPos()].equals(Tileset.GHOST)) {
            int damage = RandomUtils.poisson(random, 5 + floor * 0.5);
            hp -= damage;
            System.out.println("Boo!! A Ghost attacked you!");
            System.out.println("Your HP decreased by: " + damage);
        }
    }

    @Override
    public void update(int x, int y) {
        move(x, y, TILETYPE);
    }

    /** Executed when the avatar obtains a fixed object. If the obtained item is fish, increases
    * the count of the fish acquired by one, and stores that item in the */
    public void obtain(FixedObject obj) {
        obj.obtained();
        fishObtained += 1;
    }

    /** When the avatar eats the fish, the avatar`s HP increases randomly chosen from the Poisson distribution
     *  with mean value of 5 + floor * 0.5 and decreases the count of the fish in the itemObtained HashMap. */
    public void eatFish() {
        if (fishObtained > 0) {
            int lostHP = 100 - this.hp;
            int randHp = RandomUtils.poisson(random, 4.5);
            this.hp += Math.min(randHp, lostHP);
            System.out.println("Current HP is: " + this.hp);
        }
    }

    /** Get HP of the avatar */
    public int getHP() { return hp; }

    /** Set HP of the avatar */
    public void setHP(int HP) {
        this.hp = HP;
    }

    /* Get position of the avatar */
    public Position getPosition() {
        return new Position(getXPos(), getYPos());
    }
}
