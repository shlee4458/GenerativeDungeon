package byow.Core.BackGround;

import byow.Core.Position;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.Random;

public class Staircase {

    int width;
    int height;
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
        tiles[foundPos.getX()][foundPos.getY()] = Tileset.STAIRS;
    }
}
