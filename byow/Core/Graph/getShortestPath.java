package byow.Core.Graph;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class getShortestPath {
    int width;
    int height;
    TETile[][] tiles;
    Node source;
    Queue<Node> queue;
    boolean[][] tilesVisited; // Used for checking if the tile can be visited
    boolean[][] cacheTilesVisited;

    public getShortestPath(TETile[][] tiles) {
        this.tiles = tiles;
        this.width = tiles.length;
        this.height = tiles[0].length;
        this.tilesVisited = new boolean[width][height];
        this.source = new Node(0, 0, 0);
        this.queue = new LinkedList<>();

        /* Create tiles with default visited boolean values */
        createTiles();
    }

    /** Get next move of ghost given the ghost`s current position and the target position.
     * @Param int sX, sY: source(ghost)`s x, y coordinate
     * @Param int tX, tY: target(avatar)`s x coordinate
     * @Return char: directional character */
    public char getNextMove(int sX, int sY, int tX, int tY) {

        /* Corner case; returns non-directional value if source and target position is the same.
        * The ghost does not move if avatar and the ghost are placed in the same tile. */
        if (sX == tX && sY == tY) { return ' '; }

        char c = 'a';
        Node updatedSource = source.update(sX, sY, 0, c);
        queue.clear();
        queue.add(updatedSource);
        tilesVisited = copyOf(cacheTilesVisited);
        tilesVisited[sX][sY] = true;

        while (!queue.isEmpty()) {
            // Remove from the front of the linked list
            Node n = queue.remove();
            tilesVisited[n.x][n.y] = true;

            // If targetX and targetY is the same from deque return that
            if (n.x == tX && n.y == tY) {
                c = n.visited.remove(1);
                break;
            }

            // If up is valid add that to the queue with updated node
            if (isValid(n.x, n.y, 0, 1)) {
                queue.add(n.update(0,1,1,'w'));
            }

            // If down is valid, add to the queue
            if (isValid(n.x, n.y, 0, -1)) {
                queue.add(n.update(0,-1,1,'s'));
            }

            // If left is valid, add to the queue
            if (isValid(n.x, n.y, -1, 0)) {
                queue.add(n.update(-1,0,1,'a'));
            }
            
            // If right is valid, add to the queue
            if (isValid(n.x, n.y, 1, 0)) {
                queue.add(n.update(1,0,1,'d'));
            }
        }
        return c;
    }

    /* Check if moving from (sX, sY) to (sX + mX, sY + mY) is valid */
    public boolean isValid(int sX, int sY, int mX, int mY) {
        int newX = sX + mX;
        int newY = sY + mY;

        return (newX > 0 && newY > 0
                && newY < height - 1 && newX < width - 1 && !tilesVisited[newX][newY]);
    }

    /* Create tiles Visited
    *  Input true if the tile contains nothing tile or wall tile */
    public void createTiles() {
        for (int x = 0; x < tiles.length; x++) {
            for (int y = 0; y < tiles[0].length; y++) {
                if (tiles[x][y].equals(Tileset.NOTHING) || tiles[x][y].equals(Tileset.WALL)) {
                    tilesVisited[x][y] = true; // Set Nothing tiles and Wall tiles as visited tiles
                } else {
                    tilesVisited[x][y] = false;
                }
            }
        }
        cacheTilesVisited = copyOf(tilesVisited);
    }

    /* Create a copy of tiles */
    public static boolean[][] copyOf(boolean[][] tiles) {
        if (tiles == null) {
            return null;
        }

        boolean[][] copy = new boolean[tiles.length][];

        int i = 0;
        for (boolean[] column : tiles) {
            copy[i] = Arrays.copyOf(column, column.length);
            i += 1;
        }
        return copy;
    }

    public boolean[][] getTilesVisited() {
        return tilesVisited;
    }
}
