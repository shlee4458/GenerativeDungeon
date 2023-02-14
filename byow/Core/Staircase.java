package byow.Core;

import byow.Core.FixedObject.FixedObject;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.Random;

public class Staircase extends FixedObject {

    int width;
    int height;
    int xPos;
    int yPos;
    TETile[][] tiles;
    Random random;

    public Staircase(int width, int height, TETile[][] tiles, Random random) {
        this.width = width;
        this.height = height;
        this.tiles = tiles;
        this.random = random;
    }

    /* Finds position where stairs can be drawn;
    * stairs can be drawn where it is a Floor tile */
    public Position find() {
        Position stairPos;
        while (true) {
            int randX = random.nextInt(5, width - 5);
            int randY = random.nextInt(5, height - 5);
            TETile tile = tiles[randX][randY];
            if (tile.equals(Tileset.FLOOR)) {
                stairPos = new Position(randX, randY);
                break;
            }
        }
        return stairPos;
    }

    /* draws the staircase in the found position */
    public void draw() {
        Position foundPos = find();
        int x = foundPos.getX();
        int y = foundPos.getY();
        this.xPos = x;
        this.yPos = y;
        tiles[xPos][yPos] = Tileset.STAIRS;
    }

    public Position getPosition() {
        return new Position(xPos, yPos);
    }
}
