package byow.Core.MovingObject;

import byow.Core.Position;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.Random;

public class MovingObject implements MovingObjectInterface {
    int width;
    int height;
    int xPos;
    int yPos;
    int hp;
    Random random;
    private TETile tileType = Tileset.SAND;
    TETile[][] tiles;

    /** Constructor */
    public MovingObject(TETile[][] tiles, Random random) {
        this.tiles = tiles;
        this.width = tiles.length;
        this.height = tiles[0].length;
        this.random = random;
    }

    /** If it is a valid move, move the moving object by x in x-axis, and y in y-axis.
     * Move the object by replacing the tile in the current position with the floor tile, and
     * place the moving object specific tile in the position where the object is moved to. */
    @Override
    public void move(int x, int y, TETile tileType) {
        if (isValidMove(x, y)) {  // Check if it is a valid move
            tiles[xPos][yPos] = Tileset.FLOOR;
            xPos = xPos + x;
            yPos = yPos + y;
            tiles[xPos][yPos] = tileType;
        }
    }

    /** Creates a moving object in a random position. Initiate x and y as 0, and iterate until the tile
     * in the randomly generate x, y coordinate is a valid start. In the valid position, place the moving
     * object specific tile. */
    @Override
    public void create(TETile tileType) {
        int x = 0;
        int y = 0;
        while (!isValidStart(x, y)) {
            x = random.nextInt(5, width - 5);
            y = random.nextInt(5, height - 5);
        }
        this.xPos = x;
        this.yPos = y;
        tiles[x][y] = tileType;
    }

    /** Returns true, if the tile in the x, y coordinate is a floor tile.
     * @Param int x, y: x, y coordinate of the moving object. */
    public boolean isValidStart(int x, int y) {
        return tiles[x][y].equals(Tileset.FLOOR);
    }

    /** Returns true, if shifting the moving object by x, y parameter does not place the object in the Wall tile.
     * @Param int x, y: marginal increase in x, y coordinate from the current position of the moving object. */
    public boolean isValidMove(int x, int y) {
        int newX = xPos + x;
        int newY = yPos + y;
        return !tiles[newX][newY].equals(Tileset.WALL);
    }

    /** Updates the moving object */
    public void update(int x, int y) { }

    /** Get x position of the moving object */
    public int getXPos() {
        return xPos;
    }

    /** Get y position of the moving object */
    public int getYPos() {
        return yPos;
    }

    /* Returns position object of x, y coordinate of the moving object */
    public Position getPosition() {
        return new Position(getXPos(), getYPos());
    }
}
