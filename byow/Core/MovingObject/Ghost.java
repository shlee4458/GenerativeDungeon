package byow.Core.MovingObject;

import byow.Core.Graph.*;
import byow.Core.Position;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.Random;

public class Ghost extends MovingObject implements MovingObjectInterface {
    private static final TETile TILETYPE = Tileset.GHOST;
    getShortestPath shortestPath;
    int onOff = 0; // a ghost moves only one step in every two turns

    /** Constructor */
    public Ghost(TETile[][] tiles, Random random) {
        super(tiles, random);
        create(TILETYPE);
        shortestPath = new getShortestPath(tiles); // initiates getShortestPath class with tiles
    }

    @Override
    public void move(int x, int y, TETile tile) {
        super.move(x, y, TILETYPE);
    }

    /** Update method is called in every two turns by turning on the onOff switch in every two turns.
     * Ghost finds the shortest path to the avatar given x, y coordinate of the ghost and the
     * x, y coordinate of the target(avatar). Ghost can randomly move 1 step to 2 steps.
     * @Param int x: x position of the target(avatar)
     * @Param int y: y position of the target(avatar) */
    @Override
    public void update(int x, int y) {
        if (onOff == 0) {
            onOff = 1;
        } else {
            int numSteps = random.nextInt(1,2);
            while (numSteps > 0) {
                char nextMove = shortestPath.getNextMove(getXPos(), getYPos(), x, y);
                int xMove = Position.direction(nextMove).getX();
                int yMove = Position.direction(nextMove).getY();
                onOff = 0;
                move(xMove, yMove, TILETYPE);
                numSteps -= 1;
            }
        }
    }

}
