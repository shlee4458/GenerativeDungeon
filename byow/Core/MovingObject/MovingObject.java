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
    int initialXPos; // Caches initial starting x position. Used when user restarts the game.
    int initialYPos; // Caches initial starting y position. Used when user restarts the game.
    int hp;
    Random random;
    private TETile tileType = Tileset.SAND;
    TETile[][] tiles;

    public MovingObject(TETile[][] tiles, Random random) {
        this.tiles = tiles;
        this.width = tiles.length;
        this.height = tiles[0].length;
        this.random = random;
    }

    @Override
    public void move(int x, int y, TETile tileType) {
        // Check if it is a valid move
        if (isValidMove(x, y)) {
            tiles[xPos][yPos] = Tileset.FLOOR;
            xPos = xPos + x;
            yPos = yPos + y;
            tiles[xPos][yPos] = tileType;
        }
    }

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
        this.initialXPos = x;
        this.initialYPos = y;
        tiles[x][y] = tileType;
    }

    public boolean isValidStart(int x, int y) {
        return tiles[x][y].equals(Tileset.FLOOR);
    }

    public boolean isValidMove(int x, int y) {
        int newX = xPos + x;
        int newY = yPos + y;
        return !tiles[newX][newY].equals(Tileset.WALL);
    }

    public void update(int x, int y) {
    }

    public void newStart() {
        int updateX = initialXPos - xPos;
        int updateY = initialYPos - yPos;
        update(updateX, updateY);
        xPos = initialXPos;
        yPos = initialYPos;
    }

    public int getXPos() {
        return xPos;
    }

    public int getYPos() {
        return yPos;
    }

    /* Get position of the avatar */
    public Position getPosition() {
        return new Position(getXPos(), getYPos());
    }
}
