package byow.Core.BackGround;

import byow.Core.Graph.Edge;
import byow.Core.Graph.KruskalMST;
import byow.Core.Position;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.*;

public class Hallway implements BackGround {
    TETile[][] tiles;
    public static Random random = new Random(100);
    Room room;
    KruskalMST kruskal;
    ArrayList<Position> vertex;
    HashMap<Position, ArrayList<Position>> positionPairs; // Pair of positions that is part of two ends of
                                                          // minimum spanning tree
    HashMap<Position, Position> fromTo;

    /* Constructor */
    public Hallway(TETile[][] tiles, Room room, KruskalMST kruskal) {
        this.tiles = tiles;
        this.room = room;
        this.kruskal = kruskal;
        this.vertex = room.getVertex();
        this.positionPairs = new HashMap<>();
        this.fromTo = new HashMap<>();

        /* Find pairs of starting and ending positions */
        ArrayList<Edge> mst = kruskal.MinimumSpanningTree();
        closestPositionPairs(mst); // update positionPair
        updateFromTo(positionPairs); // update fromTo
    }

    /* Draw all hallways with given pairs of from and to position */
    @Override
    public void drawAll() {
        for (Map.Entry<Position, Position> entry : fromTo.entrySet()) {
            draw(entry.getKey(), entry.getValue());
        }
    }

    /* Draw a hallway in connecting start position and the end position */
    @Override
    public void draw(Position start, Position end) {
        if (start.getY() == end.getY()) {
            drawHorizontal(tiles, start, end);
        } else if (start.getX() == end.getX()) {
            drawVertical(tiles, start, end, true);
        } else {
            drawL(tiles, start, end);
        }
    }

    /* Get the closest position pairs */
    public void closestPositionPairs(ArrayList<Edge> mst) {
        for (Edge e : mst) {
            int v = e.either();
            int w = e.other(v);
            Position x = vertex.get(v);
            Position y = vertex.get(w);

            if (positionPairs.containsKey(x)) {
                positionPairs.get(x).add(y);
            } else {
                ArrayList<Position> vals = new ArrayList<>();
                vals.add(y);
                positionPairs.put(x, vals);
            }
        }
    }

    /* Update fromTo. fromTo contain pairs of the starting position and the ending position. */
    public void updateFromTo(HashMap<Position, ArrayList<Position>> positionPairs) {
        for (Map.Entry<Position, ArrayList<Position>> entry : positionPairs.entrySet()) {
            for (Position pos : entry.getValue()) {
                Position newStart = new Position(entry.getKey().getX(),entry.getKey().getY());
                Position newEnd = new Position(pos.getX(),pos.getY());
                fromTo.put(newStart, newEnd);
            }
        }
    }

    /* Draw Horizontal line.
    * Start adding floor from the minimum column to maximum column.
    * Add Walls to the one cell above and below if respective cell is empty. */
    public void drawHorizontal(TETile[][] tiles, Position from, Position to) {
        int row = Position.minRow(from, to);
        int startCol = Position.minCol(from, to);
        int endCol = Position.maxCol(from, to);

        for (int col = startCol; col < endCol; col++) {
            tiles[col][row] = Tileset.FLOOR;
            addWall(tiles, col, row, true);
        }
    }

    /* Draw Vertical line
    * Start adding floor from the minimum row to maximum row.
    * Add walls to the one cell left and right if respective cell is empty.
    * If the parameter right is true, use maximum column to draw, otherwise use minimum column */
    public void drawVertical(TETile[][] tiles, Position from, Position to, boolean right) {
        int col;
        if (right) {
            col = Position.maxCol(from, to);
        } else {
            col = Position.minCol(from, to);
        }
        int startRow = Position.minRow(from, to);
        int endRow = Position.maxRow(from, to);

        for (int row = startRow; row < endRow; row++) {
            tiles[col][row] = Tileset.FLOOR;
            addWall(tiles, col, row, false);
        }
    }

    /* Draw L shaped */
    public void drawL(TETile[][] tiles, Position from, Position to) {
        boolean right = isRight(from, to);
        drawHorizontal(tiles, from, to);
        drawVertical(tiles, from, to, right);
    }

    /* TODO, efficient way of drawing walls?
    *  If horizontal; check if one cell above and below is nothing tiles.
    * If so add wall, otherwise do nothing.
    * If Vertical; check if one cell left and right is nothing tiles.
    * If so add wall, otherwise do nothing.
    * Check corners, and add walls accordingly */
    public void addWall(TETile[][] tiles, int col, int row, boolean horizontal) {
        if (horizontal) {
            if (isNothingTiles(tiles[col][row + 1])) {
                tiles[col][row + 1] = Tileset.WALL;
            }
            if (isNothingTiles(tiles[col][row - 1])) {
                tiles[col][row - 1] = Tileset.WALL;
            }
        } else {
            if (isNothingTiles(tiles[col + 1][row])) {
                tiles[col + 1][row] = Tileset.WALL;
            }
            if (isNothingTiles(tiles[col - 1][row])) {
                tiles[col - 1][row] = Tileset.WALL;
            }
        }

        // Checks if corners are nothing tiles.
        // If so, add walls to the corners.
        if (isNothingTiles(tiles[col - 1][row - 1])) {
            tiles[col - 1][row - 1] = Tileset.WALL;
        } if (isNothingTiles(tiles[col][row - 1])) {
            tiles[col][row - 1] = Tileset.WALL;
        } if (isNothingTiles(tiles[col + 1][row - 1])) {
            tiles[col + 1][row - 1] = Tileset.WALL;
        }
    }

    /* Return true if minimum row and minimum column is same Position
    * If so, two positions are each located in the 1st and 3rd quadrant.
    * Otherwise, two positions are each located in the 2nd and 4th quarant. */
    public boolean isRight(Position from, Position to) {
        Position minRow = Position.minYPos(from, to);
        Position minCol = Position.minXPos(from, to);

        return minRow.equals(minCol);
    }

    /* Return true if the tile is a nothing tile, otherwise false. */
    public boolean isNothingTiles(TETile tile) {
        if (tile.equals(Tileset.NOTHING)) {
            return true;
        } return false;
    }

    /* Get position pairs instance variable */
    public HashMap<Position, ArrayList<Position>> getClosestPositionPairs() {
        return positionPairs;
    }

    /* Get fromTo instance variable */
    public HashMap<Position, Position> getFromTo() {
        return fromTo;
    }
}
