package byow.Core.BackGround;

import byow.Core.Graph.*;
import byow.Core.Position;
import byow.TileEngine.*;

import java.util.*;
public class Hallway implements BackGround {

    /* Key Position: middle position of the room that is at the one end of the minimum spanning tree
     * Value ArrayList<Position>: ArrayList of Positions at the other end of the minimum spanning tree */
    HashMap<Position, ArrayList<Position>> positionPairs;
    HashMap<Position, Position> fromTo;
    TETile[][] tiles;
    Room room;
    KruskalMST kruskal;
    ArrayList<Position> vertex;

    /** Constructor */
    public Hallway(TETile[][] tiles, Room room, KruskalMST kruskal) {
        this.tiles = tiles;
        this.room = room;
        this.kruskal = kruskal;
        this.vertex = room.getVertex();
        this.positionPairs = new HashMap<>();
        this.fromTo = new HashMap<>();

        /* Find pairs of starting and ending positions */
        ArrayList<Edge> mst = kruskal.MinimumSpanningTree();
        closestPositionPairs(mst); // update positionPairs HashMap
        updateFromTo(positionPairs); // update fromTo HashMap
    }

    /** Draw all hallways with given pairs of from and to positions */
    @Override
    public void drawAll() {
        for (Map.Entry<Position, Position> entry : fromTo.entrySet()) {
            draw(entry.getKey(), entry.getValue());
        }
    }

    /** Draw a hallway in connecting start position and the end position
     * Draw Horizontal Hallway if y value of the two positions are the same
     * Draw Vertical Hallway if x value of the two positions are the same
     * Otherwise, draw L-Shaped Hallway */
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

    /** Update the closestPositionpairs HashMap. Iterate over the mst object to find index of
     * each ends of edges and puts one end as a Key and the other end as a value in the HashMap.
     * @Param ArrayList<Edge> mst: ArrayList of edges that compose minimum spanning tree */
    public void closestPositionPairs(ArrayList<Edge> mst) {
        for (Edge e : mst) {
            int v = e.either(); // index of one end of the edge in the vertex object
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

    /** Update fromTo. fromTo Hashmap contains pairs of the starting position
     * and the ending position of where the hallway is to be drawn. */
    public void updateFromTo(HashMap<Position, ArrayList<Position>> positionPairs) {
        for (Map.Entry<Position, ArrayList<Position>> entry : positionPairs.entrySet()) {
            for (Position pos : entry.getValue()) {
                Position newStart = new Position(entry.getKey().getX(),entry.getKey().getY());
                Position newEnd = new Position(pos.getX(),pos.getY());
                fromTo.put(newStart, newEnd);
            }
        }
    }

    /** Draw Horizontal line by adding floor from the minimum column to maximum column.
     * The row of the horizontal hallway is the minimum y value of the two positions as default.
     * Add Walls to the one cell above and below if respective cell is empty.
     * @Param TETile[][] tiles: map where the hallway is drawn on
     * @Param Position from: the starting x, y coordinate where horizontal hallway is drawn
     * @Param Position to: the other end of the horizontal hallway */
    public void drawHorizontal(TETile[][] tiles, Position from, Position to) {
        int row = Position.minRow(from, to);
        int startCol = Position.minCol(from, to);
        int endCol = Position.maxCol(from, to);

        for (int col = startCol; col < endCol; col++) {
            if (!Tileset.backgroundObject.contains(tiles[col][row])) {
                tiles[col][row] = Tileset.FLOOR;
                addWall(tiles, col, row, true);
            }
        }
    }

    /** Draw Vertical line by adding the floor tile from the minimum row to maximum row.
     * Add walls to the one cell left and right if respective cell is empty.
     * If the parameter right is true, use maximum column to draw, otherwise use minimum column
     * @Param TETile[][] tiles: map to draw vertical hallway on
     * @Param Position from: the starting x, y coordinate where vertical hallway is drawn
     * @Param boolean right: draw on the maximum x coordinate of from and to Positions if true,
     * otherwise draw on the minimum x coordinate of the given two Positions */
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
            if (!Tileset.backgroundObject.contains(tiles[col][row])) {
                tiles[col][row] = Tileset.FLOOR;
                addWall(tiles, col, row, false);
            }
        }
    }

    /** Draw L shaped Hallway by first checking whether two pair of rooms are located in the
     * 1st quadrant and 3rd quadrant or in the 2nd quadrant and the 4th quadrant. Horizontal
     * Hallway is drawn at the minimum row value of the two positions, while the vertical
     * Hallway is drawn based on the value of what the isRight method returns. If isRight method
     * returns true, draw on the maximum column value of the two, otherwise draw on the minimum
     * column value of the two positions. */
    public void drawL(TETile[][] tiles, Position from, Position to) {
        boolean right = isRight(from, to);
        drawHorizontal(tiles, from, to);
        drawVertical(tiles, from, to, right);
    }

    /** When drawing walls on the horizontal floor, only draw Wall tile if the tile above
     * and below of the given col and row value is Nothing tile. When drawing walls on the
     * Vertical Hallway, only draw Wall tile if the tile to the left and right is Nothing tile.
     * Lastly, if corner of the given position is Nothing tile, add Walls. */
    public void addWall(TETile[][] tiles, int col, int row, boolean horizontal) {

        /* Draw Wall on either horizontal or vertical Hallway */
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

        /* If corners are nothing tiles, add walls to the corners */
        if (isNothingTiles(tiles[col - 1][row - 1])) {
            tiles[col - 1][row - 1] = Tileset.WALL;
        } if (isNothingTiles(tiles[col][row - 1])) {
            tiles[col][row - 1] = Tileset.WALL;
        } if (isNothingTiles(tiles[col + 1][row - 1])) {
            tiles[col + 1][row - 1] = Tileset.WALL;
        }
    }

    /** Return true if minimum row and minimum column is the same Position
     * If so, two positions are each located in the 1st and 3rd quadrant.
     * Otherwise, two positions are each located in the 2nd and 4th quadrant. */
    public boolean isRight(Position from, Position to) {
        Position minRow = Position.minYPos(from, to);
        Position minCol = Position.minXPos(from, to);

        return minRow.equals(minCol);
    }

    /** Return true if the tile is a nothing tile, otherwise false. */
    public boolean isNothingTiles(TETile tile) {
        if (tile.equals(Tileset.NOTHING)) {
            return true;
        } return false;
    }

    /** Get position pairs instance variable */
    public HashMap<Position, ArrayList<Position>> getClosestPositionPairs() {
        return positionPairs;
    }

    /** Get fromTo instance variable */
    public HashMap<Position, Position> getFromTo() {
        return fromTo;
    }
}
