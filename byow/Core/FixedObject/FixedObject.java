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
    int initialXPos; // Caches initial starting x position. Used when user restarts the game.
    int initialYPos; // Caches initial starting y position. Used when user restarts the game.
    Random random;
    private TETile tileType = Tileset.FISH;
    private TETile floor = Tileset.FLOOR;
    TETile[][] tiles;

    /* Cosntructor */
    public FixedObject(TETile[][] tiles, Random random) {
        this.tiles = tiles;
        this.random = random;
        this.width = tiles.length;
        this.height = tiles[0].length;
    }

    /* create */
    public void create(TETile tileType) {
        int x;
        int y;
        while (true) {
            x = random.nextInt(5, width - 5);
            y = random.nextInt(5, height - 5);
            if (isValidStart(x, y)) {
                initialXPos = x;
                initialYPos = y;
                draw(x, y, tileType);
                break;
            }
        }
    }

    /* Obtained - chages its location to where avatar cannot access */
    public void obtained() {
        xPos = -1;
        yPos = -1;
    }

    /* Draw */
    public void draw(int x, int y, TETile tileType) {
        tiles[x][y] = tileType;
    }

    /* Check whether the tile is a valid place for the fixed object */
    public boolean isValidStart(int x, int y) {
        return tiles[x][y] == Tileset.FLOOR;
    }

    /* Redraws at the initial position when the game starts again */
    public void newStart() {
        draw(initialXPos, initialYPos, tileType);
        xPos = initialXPos;
        yPos = initialYPos;
    }

    /* toString */
    public String toString() {
        return "Name of the object type";
    }

    /* Get X Position */
    public int getXPos() {
        return xPos;
    }
    /* Get Y Position */
    public int getYPos() {
        return yPos;
    }

    /* Get Position of the Fixed Object */
    public Position getPosition() {
        return new Position(getXPos(), getYPos());
    }
}
