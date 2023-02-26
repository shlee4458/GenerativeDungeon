package byow.Core.FixedObject;

import byow.Core.Position;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.Random;

public class FixedObject {
    int width;
    int height;
    int xPos;
    int yPos;
    Random random;
    TETile[][] tiles;

    /** Constructor */
    public FixedObject(TETile[][] tiles, Random random) {
        this.tiles = tiles;
        this.random = random;
        this.width = tiles.length;
        this.height = tiles[0].length;
    }

    /** Creates a FixedObject in a random valid position.
     * @Param TETile tileType: image of the tile to be placed in the position */
    public void create(TETile tileType) {
        int x;
        int y;
        while (true) {
            x = random.nextInt(5, width - 5);
            y = random.nextInt(5, height - 5);
            if (isValidStart(x, y)) {
                this.setX(x);
                this.setY(y);
                draw(x, y, tileType);
                break;
            }
        }
    }

    /** If the fixedObject is obtained by the avatar, moves the fixedObject to the position where the Avatar cannot access */
    public void obtained() {
        xPos = -1;
        yPos = -1;
    }

    /** Draw a fixedObject in the given x, y coordinate and the type of the tile */
    public void draw(int x, int y, TETile tileType) {
        tiles[x][y] = tileType;
    }

    /** Returns true if the tile at x, y coordinate is a floor tile */
    public boolean isValidStart(int x, int y) {
        return tiles[x][y] == Tileset.FLOOR;
    }

    /** Returns the name of the object type */
    public String toString() {
        return "Name of the object type";
    }

    /** Get X Position of the instance */
    public int getX() {
        return xPos;
    }

    /** Get Y Position of the instance */
    public int getY() {
        return yPos;
    }

    /** Set X position of the instance */
    public void setX(int x) {
        xPos = x;
    }
    /** Set Y position of the instacne */
    public void setY(int y) {
        yPos = y;
    }

    /** Clone a fixed object*/
    public FixedObject clone() {
        FixedObject fixedObject = new FixedObject(tiles, random);
        fixedObject.setX(this.getX());
        fixedObject.setY(this.getY());
        return fixedObject;
    }

    /** Return a Position object of x, y coordinate */
    public Position getPosition() {
        return new Position(getX(), getY());
    }


}
