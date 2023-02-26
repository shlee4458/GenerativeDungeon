package byow.Core.BackGround;

import byow.Core.Position;
import byow.TileEngine.*;

import java.util.Random;
public class Staircase {
    int width;
    int height;
    int xPos;
    int yPos;
    TETile[][] tiles;
    Random random;

    /** Constructor */
    public Staircase(int width, int height, TETile[][] tiles, Random random) {
        this.width = width;
        this.height = height;
        this.tiles = tiles;
        this.random = random;
    }

    /** Finds position where staircase can be drawn; staircase can be drawn
     * where the tile at the x, y coordinate is a floor tile. */
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

    /** draws the staircase in the position found through the find method */
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
