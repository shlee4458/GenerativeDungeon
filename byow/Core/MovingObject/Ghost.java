package byow.Core.MovingObject;

import byow.Core.Graph.*;
import byow.Core.Position;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.Random;

public class Ghost extends MovingObject implements MovingObjectInterface {

    private static final int HP = 50;
    private static final TETile TILETYPE = Tileset.GHOST;
    getShortestPath shortestPath;
    int onOff = 0; // a monster moves one step in every two turns
    public Ghost(TETile[][] tiles, Random random) {
        super(tiles, random);
        this.hp = HP;
        create(TILETYPE);
        shortestPath = new getShortestPath(tiles);
    }

    @Override
    public void move(int x, int y, TETile tile) {
        super.move(x, y, TILETYPE);
    }

    @Override
    /* Move function is called every two turns
    * @Param int x: x position of the target
    * @Param int y: y position of the target */
    public void update(int x, int y) {
        if (onOff == 0) {  // Move every two turns
            onOff = 1;
        } else {
            char nextMove = shortestPath.getNextMove(getXPos(), getYPos(), x, y);
            int xMove = Position.direction(nextMove).getX();
            int yMove = Position.direction(nextMove).getY();
            onOff = 0;
            move(xMove, yMove, TILETYPE);
        }
    }

    @Override
    public void newStart() {
        int updateX = initialXPos - xPos;
        int updateY = initialYPos - yPos;
        super.move(updateX, updateY, TILETYPE);
        xPos = initialXPos;
        yPos = initialYPos;
    }
}
