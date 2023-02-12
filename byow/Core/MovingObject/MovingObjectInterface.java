package byow.Core.MovingObject;

import byow.TileEngine.TETile;

public interface MovingObjectInterface {
    void move(int x, int y, TETile tileType);
    void create(TETile tileType);

    boolean isValidStart(int x, int y);

    boolean isValidMove(int x, int y);

    int getXPos();
    int getYPos();
}
